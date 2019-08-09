package com.company;

import java.util.Arrays;

import static java.lang.System.exit;

class Player {
    //private Card [] hand;
    private Hand hand;
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

    void setHand(Card [] hand, Card starter){
        //this.hand = hand;
        this.hand = new Hand(hand, starter);
        this.pegCount = 0;
    }

    Card [] discard(){
        return this.hand.discard(4,5);
    }

    Card peg(int count){
        //int ret = 0;
        Card nextCard = null;
        Card [] pegHand = hand.getHand();
        if( !(this.canPeg(count) && this.hasCards()) ) return nextCard;
        for(int i = 0; i < 4; i++){
            if(pegHand[i].getPlayed()) continue;
            if(count + pegHand[i].getValue() <= 31) {
                nextCard = pegHand[i];
                //ret = pegHand[i].getValue();
                pegHand[i].setPlayed(true);
                break;
            }
        }
        //TODO delete System.out.println("add" + ret);
        this.pegCount++;
        //if(ret == 0) exit(1);
        if(nextCard == null) exit(-1);
        return nextCard;
        //return ret;
    }

    int score(Hand hand){
        return hand.score();
    }

    int score(){
        return this.hand.score();
    }

    boolean canPeg(int count){
        return this.hand.canPeg(count);
    }

    boolean hasCards(){
        return pegCount < 4;
    }

    void cleanHand(){
        this.hand.cleanHand();
    }
}
