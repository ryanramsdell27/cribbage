package com.cribbage;

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
    public Card peg() {
        return null;
    }
}
