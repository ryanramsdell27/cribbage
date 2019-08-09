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
        this.top = 0;
        Collections.shuffle(deck, rnd);
    }


    //TODO should this be moved to the Player class? it already has an overridden function,
    static int score(Card [] hand, Card starter){
        Card [] full = new Card[5];
        System.arraycopy(hand, 0, full, 0, hand.length);
        full[4] = starter;
        Arrays.sort(full, 0, 4);
        System.out.println(Arrays.toString(full));
        int score = 0;

        // Count 15s
        // Count runs of 3 or 4
        // Count doubles or triples or quads
        // Count nobs

        for(int num_group = 2; num_group <= 4; num_group++){

            if(num_group > 2){

            }
        }

        // count 15s and repeats and nobs
        int dups = 0;
        for(int a = 0; a < 5; a++){
            if(full[a] != starter && full[a].getRank() == 10 && full[a].getSuit() == starter.getSuit()) score += 1;
            for(int b = a+1; b < 5; b++){
                if(full[a].getValue()+full[b].getValue() == 15) score += 2;
                if(full[a].getRank() == full[b].getRank()) dups += 2;
                for(int c = b+1; c < 5; c++){
                    if(full[a].getValue()+full[b].getValue()+full[c].getValue()== 15) score += 2;
                    if(full[a].getRank() == full[b].getRank() && full[a].getRank() == full[c].getRank() ) dups += 4;
                    for(int d = c+1; d < 5; d++){
                        if(full[a].getValue()+full[b].getValue()+full[c].getValue()+full[d].getValue()== 15) score += 2;
                        if(full[a].getRank() == full[b].getRank() && full[a].getRank() == full[c].getRank() && full[a].getRank() == full[d].getRank()) dups += 6;
                        for(int e = d+1; e < 5; e++){
                            if(full[a].getValue()+full[b].getValue()+full[c].getValue()+full[d].getValue()+full[e].getValue()== 15) score += 2;
                        }
                    }
                }
            }
        }
        score = score += dups;

        // counts runs
        Card [] hand_c = Arrays.copyOf(full, full.length);
        Arrays.sort(hand_c);
        for(int i = 3; i <= 5; i++){
            boolean isRun = true;
            for(int j = 0; j < i-1; j++){
                if(hand_c[j].getRank() != hand_c[j+1].getRank()-1) isRun = false;
            }
            if(isRun) {
                if (i == 3) score += 3;
                else score++;
            }
        }

        System.out.println("Scored +" + score);
        return score;
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
