/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opiframe.custom;

import java.io.IOException;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

/**
 *
 * @author Ohjelmistokehitys
 */
@FacesComponent("com.opiframe.custom")
public class MySpecialComponent extends UIComponentBase {
    String headerText;
    
    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        //super.encodeBegin(context); //To change body of generated methods, choose Tools | Templates.
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("h1", this);
        writer.write(this.getHeaderText());
        writer.endElement("h1");
    }
    
    @Override
    public String getFamily() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //return COMPONENT_TYPE;
    }

    public String getHeaderText() {
        return headerText;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }
}
