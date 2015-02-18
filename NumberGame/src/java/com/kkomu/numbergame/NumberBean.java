/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kkomu.numbergame;

import java.io.Serializable;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author kyosti
 */
@ManagedBean(name="numberBean", eager=true)
@ApplicationScoped
public class NumberBean implements Serializable {
    private int randomNumber;
    private int userNumber;
    private int min = 1, max = 100;
    private int guessCount = -1;
    
    public NumberBean() {
        this.randomNumber();
    }
    
    public void randomNumber() {
        int range = (this.max - this.min) + 1;
        randomNumber = (int)(Math.random() * range) + min;
        //randomNumber = 5;
        this.userNumber = 0;
        this.guessCount = 0;
    }
    
    public String checkResult() {
        String result = "";
        if(userNumber >= this.min && userNumber <= this.max ) {
            this.guessCount++;
            if (userNumber < randomNumber ) {
                result = "Guess too low!";
            }
            else if (userNumber > randomNumber ) {
                result = "Guess to high!";
            }
            else if (userNumber == randomNumber) {
                result = "You got it right on " + Integer.toString(this.guessCount) + " guesses!";
            }
        
        }
        return result;
    }
    
    public int getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(int userNumber) {
        this.userNumber = userNumber;
    }
}
