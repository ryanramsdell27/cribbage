package com.company;

public class Main {

    public static void main(String[] args) {
        Game cribbage = new Game();
//        cribbage.step();

        while (!cribbage.done()){
            //cribbage.deal();
            cribbage.step();
        }
    }
}
