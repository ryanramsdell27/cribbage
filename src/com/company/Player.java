package com.company;

import java.util.Arrays;

import static java.lang.System.exit;

public class Player {
    Card [] hand;
    Card [] pegHand;
    int pegCount;
    int score;

    public Player(){
        pegCount = 0;
    }

    public void increaseScore(int points){
        this.score += points;
    }
    public int getScore(){
        return this.score;
    }

    public void set_hand(Card [] hand){
        this.hand = hand;
    }

    public void discard(Card [] crib, int start){
        crib[start] = hand[4];
        crib[start+1]=hand[5];
        hand = Arrays.copyOfRange(hand, 0,4);
        this.pegHand = hand;
    }

    public int peg(int count){
        int ret = 0;
        if( !(this.canPeg(count) && this.hasCards()) ) return ret;
        for(int i = 0; i < 4; i++){
            if(pegHand[i].getPlayed()) continue;
            if(count + pegHand[i].getValue() <= 31) {
                ret = pegHand[i].getValue();
                pegHand[i].setPlayed(true);
                break;
            }
        }
        //TODO delete System.out.println("add" + ret);
        this.pegCount++;
        if(ret == 0) exit(1);
        return ret;
    }

    void score(Card starter){
        this.increaseScore(Deck.score(this.hand, starter));
    }
    void score(Card [] hand, Card starter){
        this.increaseScore(Deck.score(hand, starter));
    }

    public boolean canPeg(int count){
        for(int i = 0; i < 4; i++){
            if(!pegHand[i].getPlayed() && count + pegHand[i].getValue() <= 31) return true;
        }
        return false;
    }

    public boolean hasCards(){
        return pegCount < 4;
    }
}
