package com.company;

import java.util.Arrays;

import static java.lang.System.exit;

class Player {
    private Card [] hand;
    private Card [] pegHand;
    private int pegCount;
    private int score;

    Player(){
        pegCount = 0;
    }

    void increaseScore(int points){
        this.score += points;
    }
    int getScore(){
        return this.score;
    }

    void set_hand(Card [] hand){
        this.hand = hand;
        this.pegCount = 0;
    }

    void discard(Card [] crib, int start){
        crib[start] = hand[4];
        crib[start+1]=hand[5];
        hand = Arrays.copyOfRange(hand, 0,4);
        this.pegHand = hand;
    }

    int peg(int count){
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

    boolean canPeg(int count){
        for(int i = 0; i < 4; i++){
            if(!pegHand[i].getPlayed() && count + pegHand[i].getValue() <= 31) return true;
        }
        return false;
    }

    boolean hasCards(){
        return pegCount < 4;
    }

    void cleanHand(){
        for(int i = 0; i < 4; i++){
            this.hand[i].setPlayed(false);
        }
    }
}
