package com.example.stefano.antonello;

import com.example.stefano.armando.WearingFactory;

import java.util.Map;
import java.util.TreeMap;

public class Antonello {
    private static final int BLUE=0;
    private static final int BLACK=1;
    private static final int GREY=2;
    private static final int WHITE=3;
    
    private static final int nColors = 4;
    
    private static final int PANTALONE = 0;
    private static final int SCARPE = 1;
    private static final int MAGLIETTA = 2;
    private static final int CAPPELLO = 3;
    
    private static final int nWearTypes = 4;
    
    private Map<Integer, Double> Colors;
    private Map<Integer, Double> wearType;
    
    private static final double speed= 2d;
    
    private WearingFactory factory;
    public Antonello() {
        //initializing
        
        double initialValueColors= 1d/(double)nColors;
        Colors = new TreeMap<>();
        
        for(int i=0; i<nColors; i++)
            Colors.put(i, initialValueColors);
        
        double initialValueWear= 1/(double)nWearTypes;
        wearType = new TreeMap<>();
        
        for(int i=0; i< nWearTypes; i++)
            wearType.put(i, initialValueWear);
        //(every possibility is equally probable)
        
        factory= WearingFactory.getInstanceOf();
    }
    
    public int[] calculateNextWearing()
    {
        double randResult1= Math.random();
        int i, j;
        for(i=0; i<nColors; i++)
        {
            if(randResult1 < Colors.get(i))
                break;
            randResult1 -= Colors.get(i);
        }
        
        double randResult2= Math.random();
        for(j=0; j<nWearTypes; j++)
        {
            if(randResult2 < wearType.get(j))
                break;
            randResult2 -= wearType.get(j);
        }
        
        factory.getWearing(i, j);
        
        int [] result = {i, j};
        
        System.out.println();
        
        return result;
    }
    
    public void positiveFeedback(int[] wearCharacteristics)
    {
        int color=  wearCharacteristics[0];
        int wType = wearCharacteristics[1];
        
        double previousValue;
        for(int i=0; i<nColors; i++)
        {
            previousValue = Colors.get(i);
            if(i==color)
                //the winning color
                Colors.put(color, previousValue + (1d-previousValue)/speed);
            else
                Colors.put(i, previousValue/speed);
        }
        
        for(int i=0; i<nWearTypes; i++)
        {
            previousValue = wearType.get(i);
            if(i==wType)
                //the winning wear
                wearType.put(wType, previousValue + (1d-previousValue)/speed);
            else
                wearType.put(i, previousValue/speed);
        }
        
        return;
    }
    
    public void negativeFeedback(int[] wearCharacteristics)
    {
        int color=  wearCharacteristics[0];
        int wType = wearCharacteristics[1];
        
        double loserColorValue= Colors.get(color)/speed;
        Colors.put(color, loserColorValue);
        
        double otherColorGain=loserColorValue/(double)(nColors-1);

        double previousValue;
        for(int i=0; i<nColors; i++)
        {
            previousValue = Colors.get(i);
            if(i!=color)
                Colors.put(i, previousValue + otherColorGain);
        }
        
        double loserWearValue= wearType.get(wType)/speed;
        wearType.put(wType, loserWearValue);
        
        double otherWearGain=loserWearValue/(double)(nWearTypes-1);
        
        for(int i=0; i<nWearTypes; i++)
        {
            previousValue = wearType.get(i);
            if(i!=wType)
                wearType.put(i, previousValue + otherWearGain);
        }
        return;
    }
    
    public void printStatus()
    {
        System.out.println(Colors);
        System.out.println(wearType);
    }
}
