package com.company;

import java.util.Arrays;

class Hand {
    private Card [] hand;
    private Card starter;
    private Card [] full;

    Hand(Card [] hand, Card starter){
        this.hand = new Card[6];
        System.arraycopy(hand, 0, this.hand, 0, hand.length );
        //this.hand[4] = starter;
        full = new Card[5];
        this.starter = starter;
    }
    Hand(){
        this.hand = new Card[5];
        full = new Card[5];
    }

    void setStarter(Card starter){
        this.starter = starter;
    }

    void setCribFull(Card [] crib){
        System.arraycopy(crib, 0, this.full, 0, crib.length );
        this.full[4] = this.starter;
    }

    Card [] discard(int a, int b){
        int j = 0;
        for(int i = 0; i < hand.length; i++){
            if(i != a && i != b) {
                full[j] = hand[j];
                j++;
            }

        }
        full[4] = starter;

        Card [] disc = new Card[2];
        disc[0] = hand[a];
        disc[1] = hand[b];
        return disc;
    }

    Card [] getHand(){
        return this.full;
    }

    int score(){
        Arrays.sort(full, 0, 4);
        System.out.println(Arrays.toString(full));
        int score = 0;

        // Count 15s
        // Count runs of 3 or 4
        // Count doubles or triples or quads
        // Count nobs

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
        score += dups;

        // counts runs
        Card [] hand_c = Arrays.copyOf(full, full.length);
        Arrays.sort(hand_c);
        int max_run = 5;
        score += getRunScore(hand_c, max_run);

        System.out.println("Scored +" + score);
        return score;
    }

    static int getRunScore(Card[] hand_c, int max_run) {
        int score = 0;
        for(int i = 3; i <= max_run; i++){
            boolean isRun = true;
            for(int j = 0; j < i-1; j++){
                if(hand_c[j].getRank() != hand_c[j+1].getRank()-1) isRun = false;
            }
            if(isRun) {
                if (i == 3) score += 3;
                else score++;
            }
        }
        return score;
    }

    void cleanHand(){
        for (Card card : full) {
            card.setPlayed(false);
        }
    }

    boolean canPeg(int count){
        for (Card card : full) {
            if (!card.getPlayed() && count + card.getValue() <= 31) return true;
        }
        return false;
    }
}
