/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opiframe.ManagedBeans;

import com.opiframe.POJO.Person;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Opiframe
 */
@ManagedBean(name="dataBean")
@RequestScoped
public class DataBean {

    private final List<Person> persons;
    public DataBean() {
        
        persons = new ArrayList();
        persons.add(new Person("Markus Veijola","Rautatienkatu 40 as 30"));
        persons.add(new Person("Jussi Juonio","Juoniokatu 10"));
        persons.add(new Person("Kalle Kihveli","Kihvelitie 123 as 10"));
        persons.add(new Person("John Doe","Unknown"));
        persons.add(new Person("Jane Doe","Doe Street 6"));
    }

    public List<Person> getPersons() {
        return persons;
    }   
    
}
