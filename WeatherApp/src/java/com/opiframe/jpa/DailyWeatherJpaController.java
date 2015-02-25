/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opiframe.jpa;

import com.opiframe.entity.DailyWeather;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.opiframe.entity.OpiframeUser;
import com.opiframe.jpa.exceptions.NonexistentEntityException;
import com.opiframe.jpa.exceptions.RollbackFailureException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Ohjelmistokehitys
 */
public class DailyWeatherJpaController implements Serializable {

    public DailyWeatherJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DailyWeather dailyWeather) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            OpiframeUser userId = dailyWeather.getUserId();
            if (userId != null) {
                userId = em.getReference(userId.getClass(), userId.getUserId());
                dailyWeather.setUserId(userId);
            }
            em.persist(dailyWeather);
            if (userId != null) {
                userId.getDailyWeatherCollection().add(dailyWeather);
                userId = em.merge(userId);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(DailyWeather dailyWeather) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            DailyWeather persistentDailyWeather = em.find(DailyWeather.class, dailyWeather.getWeatherId());
            OpiframeUser userIdOld = persistentDailyWeather.getUserId();
            OpiframeUser userIdNew = dailyWeather.getUserId();
            if (userIdNew != null) {
                userIdNew = em.getReference(userIdNew.getClass(), userIdNew.getUserId());
                dailyWeather.setUserId(userIdNew);
            }
            dailyWeather = em.merge(dailyWeather);
            if (userIdOld != null && !userIdOld.equals(userIdNew)) {
                userIdOld.getDailyWeatherCollection().remove(dailyWeather);
                userIdOld = em.merge(userIdOld);
            }
            if (userIdNew != null && !userIdNew.equals(userIdOld)) {
                userIdNew.getDailyWeatherCollection().add(dailyWeather);
                userIdNew = em.merge(userIdNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = dailyWeather.getWeatherId();
                if (findDailyWeather(id) == null) {
                    throw new NonexistentEntityException("The dailyWeather with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            DailyWeather dailyWeather;
            try {
                dailyWeather = em.getReference(DailyWeather.class, id);
                dailyWeather.getWeatherId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dailyWeather with id " + id + " no longer exists.", enfe);
            }
            OpiframeUser userId = dailyWeather.getUserId();
            if (userId != null) {
                userId.getDailyWeatherCollection().remove(dailyWeather);
                userId = em.merge(userId);
            }
            em.remove(dailyWeather);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<DailyWeather> findDailyWeatherEntities() {
        return findDailyWeatherEntities(true, -1, -1);
    }

    public List<DailyWeather> findDailyWeatherEntities(int maxResults, int firstResult) {
        return findDailyWeatherEntities(false, maxResults, firstResult);
    }

    private List<DailyWeather> findDailyWeatherEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DailyWeather.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public DailyWeather findDailyWeather(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DailyWeather.class, id);
        } finally {
            em.close();
        }
    }

    public int getDailyWeatherCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DailyWeather> rt = cq.from(DailyWeather.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
