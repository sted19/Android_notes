package com.example.SwipeUp.neuralNetwork;

public class Main {
    public static void main(String[] args) {
        //main for testing neural network
        NeuralNetwork nn = new NeuralNetwork();
        
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
