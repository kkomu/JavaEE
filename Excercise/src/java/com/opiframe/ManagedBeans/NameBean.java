/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opiframe.ManagedBeans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Opiframe
 */
@ManagedBean(name="nameBean")
@RequestScoped
public class NameBean{

    private String name;
    private Date date;
    public NameBean() {
        
        name = "";
        date = new Date();
    }

    public void setName(String name) {
        this.name = name;
    }
    
    // 1.
    public String getName() {
        return name;
    }

    public String getDate() {
        DateFormat now = new SimpleDateFormat("yyyy-MM-dd");
        return now.format(date);
    }
    
    
}
