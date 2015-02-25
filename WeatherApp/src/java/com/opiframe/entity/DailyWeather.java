/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opiframe.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ohjelmistokehitys
 */
@Entity
@Table(name = "daily_weather")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DailyWeather.findAll", query = "SELECT d FROM DailyWeather d"),
    @NamedQuery(name = "DailyWeather.findByWeatherId", query = "SELECT d FROM DailyWeather d WHERE d.weatherId = :weatherId"),
    @NamedQuery(name = "DailyWeather.findByRainAmount", query = "SELECT d FROM DailyWeather d WHERE d.rainAmount = :rainAmount"),
    @NamedQuery(name = "DailyWeather.findByRainType", query = "SELECT d FROM DailyWeather d WHERE d.rainType = :rainType"),
    @NamedQuery(name = "DailyWeather.findByDate", query = "SELECT d FROM DailyWeather d WHERE d.date = :date"),
    @NamedQuery(name = "DailyWeather.findByTemperature", query = "SELECT d FROM DailyWeather d WHERE d.temperature = :temperature"),
    @NamedQuery(name = "DailyWeather.findByWind", query = "SELECT d FROM DailyWeather d WHERE d.wind = :wind")})
public class DailyWeather implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "weather_id")
    private Integer weatherId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "rain_amount")
    private BigDecimal rainAmount;
    @Size(max = 255)
    @Column(name = "rain_type")
    private String rainType;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "temperature")
    private Long temperature;
    @Column(name = "wind")
    private Long wind;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private OpiframeUser userId;

    public DailyWeather() {
    }

    public DailyWeather(Integer weatherId) {
        this.weatherId = weatherId;
    }

    public Integer getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(Integer weatherId) {
        this.weatherId = weatherId;
    }

    public BigDecimal getRainAmount() {
        return rainAmount;
    }

    public void setRainAmount(BigDecimal rainAmount) {
        this.rainAmount = rainAmount;
    }

    public String getRainType() {
        return rainType;
    }

    public void setRainType(String rainType) {
        this.rainType = rainType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getTemperature() {
        return temperature;
    }

    public void setTemperature(Long temperature) {
        this.temperature = temperature;
    }

    public Long getWind() {
        return wind;
    }

    public void setWind(Long wind) {
        this.wind = wind;
    }

    public OpiframeUser getUserId() {
        return userId;
    }

    public void setUserId(OpiframeUser userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (weatherId != null ? weatherId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DailyWeather)) {
            return false;
        }
        DailyWeather other = (DailyWeather) object;
        if ((this.weatherId == null && other.weatherId != null) || (this.weatherId != null && !this.weatherId.equals(other.weatherId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.opiframe.entity.DailyWeather[ weatherId=" + weatherId + " ]";
    }
    
}
