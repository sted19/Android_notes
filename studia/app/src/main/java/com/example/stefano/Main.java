package com.example.stefano.antonello;

public class Main {
    public static void main(String[] args) {
        //main for testing neural network
        Antonello nn = new Antonello();
        
        int [] wear = nn.calculateNextWearing();
        
        nn.negativeFeedback(wear);
        nn.negativeFeedback(wear);
        
        nn.printStatus();
        
        nn.calculateNextWearing();
        nn.calculateNextWearing();
        nn.calculateNextWearing();
        nn.calculateNextWearing();
        nn.calculateNextWearing();
        nn.calculateNextWearing();
        
    }
}
