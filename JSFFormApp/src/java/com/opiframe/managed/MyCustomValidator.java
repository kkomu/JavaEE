/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.opiframe.managed;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;


/**
 *
 * @author Ohjelmistokehitys
 */
@FacesValidator("com.opiframe.managed.MyValidator")
public class MyCustomValidator implements Validator {
    
    // Patter for matching email-address.
    // http://www.mkyong.com/regular-expressions/how-to-validate-email-address-with-regular-expression/
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    // A compiled representation of a regular expression.
    // Compiles the given regular expression into a pattern.
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    
    // An engine that performs match operations on a character sequence by interpreting a Pattern.
    private Matcher matcher;
        
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
        matcher = pattern.matcher(value.toString());
        if(!matcher.matches()) {
             FacesMessage msg = new FacesMessage("Not valid email address","Please give valid email address");
        
            throw new ValidatorException(msg);
        }
       
    }
  
}
