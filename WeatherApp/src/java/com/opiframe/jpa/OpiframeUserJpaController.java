/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opiframe.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.opiframe.entity.DailyWeather;
import com.opiframe.entity.OpiframeUser;
import com.opiframe.jpa.exceptions.NonexistentEntityException;
import com.opiframe.jpa.exceptions.RollbackFailureException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author Ohjelmistokehitys
 */
public class OpiframeUserJpaController implements Serializable {

    public OpiframeUserJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(OpiframeUser opiframeUser) throws RollbackFailureException, Exception {
        if (opiframeUser.getDailyWeatherCollection() == null) {
            opiframeUser.setDailyWeatherCollection(new ArrayList<DailyWeather>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<DailyWeather> attachedDailyWeatherCollection = new ArrayList<DailyWeather>();
            for (DailyWeather dailyWeatherCollectionDailyWeatherToAttach : opiframeUser.getDailyWeatherCollection()) {
                dailyWeatherCollectionDailyWeatherToAttach = em.getReference(dailyWeatherCollectionDailyWeatherToAttach.getClass(), dailyWeatherCollectionDailyWeatherToAttach.getWeatherId());
                attachedDailyWeatherCollection.add(dailyWeatherCollectionDailyWeatherToAttach);
            }
            opiframeUser.setDailyWeatherCollection(attachedDailyWeatherCollection);
            em.persist(opiframeUser);
            for (DailyWeather dailyWeatherCollectionDailyWeather : opiframeUser.getDailyWeatherCollection()) {
                OpiframeUser oldUserIdOfDailyWeatherCollectionDailyWeather = dailyWeatherCollectionDailyWeather.getUserId();
                dailyWeatherCollectionDailyWeather.setUserId(opiframeUser);
                dailyWeatherCollectionDailyWeather = em.merge(dailyWeatherCollectionDailyWeather);
                if (oldUserIdOfDailyWeatherCollectionDailyWeather != null) {
                    oldUserIdOfDailyWeatherCollectionDailyWeather.getDailyWeatherCollection().remove(dailyWeatherCollectionDailyWeather);
                    oldUserIdOfDailyWeatherCollectionDailyWeather = em.merge(oldUserIdOfDailyWeatherCollectionDailyWeather);
                }
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

    public void edit(OpiframeUser opiframeUser) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            OpiframeUser persistentOpiframeUser = em.find(OpiframeUser.class, opiframeUser.getUserId());
            Collection<DailyWeather> dailyWeatherCollectionOld = persistentOpiframeUser.getDailyWeatherCollection();
            Collection<DailyWeather> dailyWeatherCollectionNew = opiframeUser.getDailyWeatherCollection();
            Collection<DailyWeather> attachedDailyWeatherCollectionNew = new ArrayList<DailyWeather>();
            for (DailyWeather dailyWeatherCollectionNewDailyWeatherToAttach : dailyWeatherCollectionNew) {
                dailyWeatherCollectionNewDailyWeatherToAttach = em.getReference(dailyWeatherCollectionNewDailyWeatherToAttach.getClass(), dailyWeatherCollectionNewDailyWeatherToAttach.getWeatherId());
                attachedDailyWeatherCollectionNew.add(dailyWeatherCollectionNewDailyWeatherToAttach);
            }
            dailyWeatherCollectionNew = attachedDailyWeatherCollectionNew;
            opiframeUser.setDailyWeatherCollection(dailyWeatherCollectionNew);
            opiframeUser = em.merge(opiframeUser);
            for (DailyWeather dailyWeatherCollectionOldDailyWeather : dailyWeatherCollectionOld) {
                if (!dailyWeatherCollectionNew.contains(dailyWeatherCollectionOldDailyWeather)) {
                    dailyWeatherCollectionOldDailyWeather.setUserId(null);
                    dailyWeatherCollectionOldDailyWeather = em.merge(dailyWeatherCollectionOldDailyWeather);
                }
            }
            for (DailyWeather dailyWeatherCollectionNewDailyWeather : dailyWeatherCollectionNew) {
                if (!dailyWeatherCollectionOld.contains(dailyWeatherCollectionNewDailyWeather)) {
                    OpiframeUser oldUserIdOfDailyWeatherCollectionNewDailyWeather = dailyWeatherCollectionNewDailyWeather.getUserId();
                    dailyWeatherCollectionNewDailyWeather.setUserId(opiframeUser);
                    dailyWeatherCollectionNewDailyWeather = em.merge(dailyWeatherCollectionNewDailyWeather);
                    if (oldUserIdOfDailyWeatherCollectionNewDailyWeather != null && !oldUserIdOfDailyWeatherCollectionNewDailyWeather.equals(opiframeUser)) {
                        oldUserIdOfDailyWeatherCollectionNewDailyWeather.getDailyWeatherCollection().remove(dailyWeatherCollectionNewDailyWeather);
                        oldUserIdOfDailyWeatherCollectionNewDailyWeather = em.merge(oldUserIdOfDailyWeatherCollectionNewDailyWeather);
                    }
                }
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
                Integer id = opiframeUser.getUserId();
                if (findOpiframeUser(id) == null) {
                    throw new NonexistentEntityException("The opiframeUser with id " + id + " no longer exists.");
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
            OpiframeUser opiframeUser;
            try {
                opiframeUser = em.getReference(OpiframeUser.class, id);
                opiframeUser.getUserId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The opiframeUser with id " + id + " no longer exists.", enfe);
            }
            Collection<DailyWeather> dailyWeatherCollection = opiframeUser.getDailyWeatherCollection();
            for (DailyWeather dailyWeatherCollectionDailyWeather : dailyWeatherCollection) {
                dailyWeatherCollectionDailyWeather.setUserId(null);
                dailyWeatherCollectionDailyWeather = em.merge(dailyWeatherCollectionDailyWeather);
            }
            em.remove(opiframeUser);
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

    public List<OpiframeUser> findOpiframeUserEntities() {
        return findOpiframeUserEntities(true, -1, -1);
    }

    public List<OpiframeUser> findOpiframeUserEntities(int maxResults, int firstResult) {
        return findOpiframeUserEntities(false, maxResults, firstResult);
    }

    private List<OpiframeUser> findOpiframeUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(OpiframeUser.class));
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

    public OpiframeUser findOpiframeUser(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OpiframeUser.class, id);
        } finally {
            em.close();
        }
    }

    public int getOpiframeUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<OpiframeUser> rt = cq.from(OpiframeUser.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
