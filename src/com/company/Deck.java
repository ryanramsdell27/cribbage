package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

public class Deck implements Iterable {
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
        if(top + num >= deck.size()) System.exit(-1);

        Card [] ret = new Card[num];
        for(int i = 0; i < num; i++){
            ret[i] = deck.get(top + i);
        }
        top += num;
        return ret;
    }

    void shuffle(){
        this.top = 0;
        Collections.shuffle(deck, rnd);
    }

    @Override
    public Iterator<Card> iterator() {
        return deck.iterator();
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
