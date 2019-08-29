package com.legacy;

import static java.lang.System.exit;

class Player {
    //private Card [] hand;
    private Hand hand;
    int pegCount;
    private int score;
    boolean isDealer;

    Player(){
        this.pegCount = 0;
        this.isDealer = false;
    }

    Card peg(int count){
        Card nextCard = null;
        Card [] pegHand = hand.getFull();
        if( !(this.canPeg(count) && this.hasCards()) ) return nextCard;
        for(int i = 0; i < 4; i++){
            if(pegHand[i].getPlayed()) continue;
            if(count + pegHand[i].getValue() <= 31) {
                nextCard = pegHand[i];
                pegHand[i].setPlayed(true);
                break;
            }
        }
        this.pegCount++;
        if(nextCard == null) exit(-1);
        return nextCard;
    }

    Card [] discard(){
        return this.hand.discard(4,5);
    }

    Hand getHand(){
        return this.hand;
    }

    void setHand(Card [] hand, Card starter){
        //this.hand = hand;
        this.hand = new Hand(hand, starter);
        this.pegCount = 0;
    }

    void increaseScore(int points){
        this.score += points;
    }

    int getScore(){
        return this.score;
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

    void setDealer(boolean val){
        this.isDealer = val;
    }
}
