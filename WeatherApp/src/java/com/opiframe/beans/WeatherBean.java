/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opiframe.beans;

import com.opiframe.entity.DailyWeather;
import com.opiframe.entity.OpiframeUser;
import com.opiframe.jpa.DailyWeatherJpaController;
import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.UserTransaction;

/**
 *
 * @author Opiframe
 */
@ManagedBean
@SessionScoped
public class WeatherBean {
    
    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;
    
    private HttpServletRequest request;
    private String name;
    private String userId;
    private Date date;
    private BigDecimal rainAmount;
    private String rainType;
    private Long wind;
    private Long temperature;
    
    public WeatherBean() {
        
        request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        userId = request.getParameter("id");
        name = request.getParameter("name");
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public String getRainType() {
        return rainType;
    }

    public void setRainType(String rainType) {
        this.rainType = rainType;
    }

    public Long getWind() {
        return wind;
    }

    public Long getTemperature() {
        return temperature;
    }

    public BigDecimal getRainAmount() {
        return rainAmount;
    }

    public void setRainAmount(BigDecimal rainAmount) {
        this.rainAmount = rainAmount;
    }



    public void setWind(Long wind) {
        this.wind = wind;
    }



    public void setTemperature(Long temperature) {
        this.temperature = temperature;
    }
    
    

    
    public void saveWeather(){
        System.out.println("Save the weather");
        System.out.println(this.getUserId());
        DailyWeatherJpaController jpa = new DailyWeatherJpaController(utx,em.getEntityManagerFactory());
        DailyWeather daily = new DailyWeather();
        daily.setDate(this.getDate());
        daily.setRainAmount(this.getRainAmount());
        daily.setRainType(this.getRainType());
        daily.setTemperature(this.getTemperature());
        daily.setWind(this.getWind());
        daily.setUserId(new OpiframeUser(new Integer(this.getUserId())));
        
        try {
            jpa.create(daily);
        } catch (Exception ex) {
            Logger.getLogger(WeatherBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}