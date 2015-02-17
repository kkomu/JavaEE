/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opiframe.ManagedBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Ohjelmistokehitys
 */
@ManagedBean(name="nameBean")
@RequestScoped
public class NameBean {
    private String name;
    
    /**
     * Creates a new instance of NameBean
     */
    public NameBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
