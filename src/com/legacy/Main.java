package com.legacy;

public class Main {

    public static void main(String[] args) {
        Game cribbage = new Game(0,1);
//        cribbage.step();

        while (!cribbage.done()){
            //cribbage.deal();
            cribbage.step();
        }
    }
}
