/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opiframe.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ohjelmistokehitys
 */
@Entity
@Table(name = "opiframe_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OpiframeUser.findAll", query = "SELECT o FROM OpiframeUser o"),
    @NamedQuery(name = "OpiframeUser.findByUserId", query = "SELECT o FROM OpiframeUser o WHERE o.userId = :userId"),
    @NamedQuery(name = "OpiframeUser.findByName", query = "SELECT o FROM OpiframeUser o WHERE o.name = :name")})
public class OpiframeUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "user_id")
    private Integer userId;
    @Size(max = 50)
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "userId")
    private Collection<DailyWeather> dailyWeatherCollection;

    public OpiframeUser() {
    }

    public OpiframeUser(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Collection<DailyWeather> getDailyWeatherCollection() {
        return dailyWeatherCollection;
    }

    public void setDailyWeatherCollection(Collection<DailyWeather> dailyWeatherCollection) {
        this.dailyWeatherCollection = dailyWeatherCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OpiframeUser)) {
            return false;
        }
        OpiframeUser other = (OpiframeUser) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.opiframe.entity.OpiframeUser[ userId=" + userId + " ]";
    }
    
}
