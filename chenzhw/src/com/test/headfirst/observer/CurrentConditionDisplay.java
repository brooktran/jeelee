package com.test.headfirst.observer;

/**
 *
 * @author Administrator
 */
public class CurrentConditionDisplay implements Observer,DisplayElement {
    private float temperature;
    private float humidity;
    private Subject weatherData;
    
    public CurrentConditionDisplay(Subject weatherData){
        this.weatherData=weatherData;
        weatherData.registerObserver(this);
    }
    
    public void update(float temperature,float humidity,float pressure){
        this.temperature=temperature;
        this.humidity=humidity;
        display();
    }
    
    public void display(){
        System.out.println("Current conditions :"+temperature+"F degress and "+humidity+"% humidity");
    }
    

}
