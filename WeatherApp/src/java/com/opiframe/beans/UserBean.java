/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opiframe.beans;


import com.opiframe.entity.OpiframeUser;
import com.opiframe.jpa.OpiframeUserJpaController;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author Ohjelmistokehitys
 */@
ManagedBean
@RequestScoped
public class UserBean implements Serializable {
    
    @PersistenceContext
    EntityManager emf;
    @Resource
    UserTransaction utx;
    
    private String name;
    
    /**
     * Creates a new instance of UserBean
     */
    public UserBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void saveUser(ActionEvent e) {
        
        OpiframeUserJpaController jpa = new OpiframeUserJpaController(utx, emf.getEntityManagerFactory());
        OpiframeUser temp = new OpiframeUser();
        temp.setName(this.getName());
        
        try {
            jpa.create(temp);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    public List<OpiframeUser> getAllUsers() {
        OpiframeUserJpaController jpa = new OpiframeUserJpaController(utx, emf.getEntityManagerFactory());
        return jpa.findOpiframeUserEntities();
    }
}
