package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Deck {
    private ArrayList<Card> deck;
    private Random rnd = new Random(12);
    private int top = 0;

    Deck(){
        deck = new ArrayList<>(52);
        for(int i = 0; i < 52; i ++){
            deck.add(new Card(i));
        }
    }

    Card [] get_sub(int num){
        //TODO check that top + num is within capacity of deck
        Card [] ret = new Card[num];
        for(int i = 0; i < num; i++){
            ret[i] = deck.get(top + i);
        }
        top += num;
        return ret;
    }

    void shuffle(){
        Collections.shuffle(deck, rnd);
    }


    //TODO should this be moved to the Player class? it already has an overridden function,
    static int score(Card [] hand, Card starter){
        Card [] full = new Card[5];
        System.arraycopy(hand, 0, full, 0, hand.length);
        full[4] = starter;
        Arrays.sort(full, 0, 4);
        System.out.println(Arrays.toString(full));



        return 0;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Card card : deck) {
            sb.append(card.toString()).append("\n");
        }
        return sb.toString();
    }
}
