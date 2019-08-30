package com.cribbage;

public class Main {
    public static void main(String[] args){
        Cribbage cribbage = new Cribbage();
        while(!cribbage.isDone()) {
            cribbage.step();
        }
    }
}
