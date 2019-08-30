package com.cribbage;

import java.util.ArrayList;
import java.util.Iterator;

public class CPUPlayer extends Player {

    @Override
    public Card[] discard() {
        // make a copy of the hand
        Card [] dis = new Card[2];
        Iterator<Card> itt = this.hand.iterator();
        dis[0] = itt.next();
        dis[1] = itt.next();
        super.discard(dis);
        return dis;
    }

    @Override
    public Card peg(ArrayList<Card> peg_pile) {
        int total = 0;
        for(Card c:peg_pile){
            total += c.value;
        }
        //TODO iterate over each card seeing what will return max points
        for(Card c:this.peg){
            if(c.value + total <= 31) {
                this.peg.remove(c);
                return c;
            }
        }
        return null;
    }
}
