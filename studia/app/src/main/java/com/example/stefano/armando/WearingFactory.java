package com.example.stefano.armando;

public class WearingFactory {
    //given some specific characteristics, an instance of this class returns a wearing that has that characteristics
    
    private static final WearingFactory uniqueInstance = new WearingFactory();
    
    private WearingFactory()
    {}
    
    public static WearingFactory getInstanceOf()
    {
        return uniqueInstance;
    }
    
    public void getWearing(int color, int wtype)
    {
        //neuralNetwork.NeuralNetwork.Color.;
        System.out.println("I'm giving you a "+color+" wear");
        System.out.println("I'm giving you a "+wtype);
        
    }
}
