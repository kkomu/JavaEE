/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opiframe.beans;

import com.opiframe.entity.DailyWeather;
import com.opiframe.jpa.DailyWeatherJpaController;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.UserTransaction;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.DateAxis;
import org.primefaces.model.chart.LineChartSeries;
 
@ManagedBean
@RequestScoped
public class ChartBean implements Serializable {
    
    @PersistenceContext
    EntityManager em;
    @Resource
    UserTransaction utx;
    
    private LineChartModel dateModel;
   
    private HttpServletRequest request;
    
    private String userId;
    private String name;
    
    public ChartBean() {
        request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        this.setUserId(request.getParameter("id"));
        this.setName(request.getParameter("name"));
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @PostConstruct
    public void init() {
        createDateModel();
    }
 
    public LineChartModel getDateModel() {
        return dateModel;
    }
    
    private void createDateModel() {
       
        DailyWeatherJpaController jpa = new DailyWeatherJpaController(utx,em.getEntityManagerFactory());
        List<DailyWeather> weatherData = jpa.findDailyWeatherEntities();

        if(!weatherData.isEmpty()) {
        
            SimpleDateFormat simpleDate = new SimpleDateFormat("yyyy-MM-dd");

            dateModel = new LineChartModel();
            LineChartSeries series1 = new LineChartSeries();
            series1.setLabel("Series 1");

            for(DailyWeather w: weatherData) {
                if(w.getUserId().getUserId() == Integer.parseInt(this.getUserId())) {
                    series1.set(simpleDate.format(w.getDate()), w.getRainAmount());
                }
            }

            dateModel.addSeries(series1);

            dateModel.setTitle("Zoom for Details");
            dateModel.setZoom(true);
            dateModel.getAxis(AxisType.Y).setLabel("Rain amount");
            DateAxis axis = new DateAxis("Dates");
            axis.setTickAngle(-50);
            axis.setMax("2015-03-01");
            axis.setTickFormat("%b %#d, %y");

            dateModel.getAxes().put(AxisType.X, axis);
        }
    }
}
