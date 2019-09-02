package com.cribbage;

import java.util.ArrayList;
import java.util.Iterator;

public class CPUPlayer extends Player {
    Deck deck = new Deck();

    @Override
    public Card[] discard() {
        Hand test = new Hand();
        Card [] copy_list = new Card[4];
        Iterator<ArrayList<Card>> combItt = this.hand.getCombinationIterator(4);
        int max_score = 0;
        ArrayList<Card> dis = new ArrayList<>(2);
        while(combItt.hasNext()){
            test.clear();
            test.add(combItt.next().toArray(copy_list));
            Iterator<Card> deck_iterator = this.deck.iterator();
            while(deck_iterator.hasNext()){
                Card possible = deck_iterator.next();
                if(this.hand.contains(possible)) continue;
                test.add(possible);
                test.setStarter(possible);
                int possible_score = test.scoreHand();
                //TODO factor in lost points from discards adding to 15 or being a pair
                // can also copy over out of this loop if we keep track of the score changing
                // since we only care about the cards in our hand and not what starter gets high score
                if(possible_score > max_score){
                    max_score = possible_score;
                    dis.clear();
                    for(Card c:this.hand){
                        if(!test.contains(c)) dis.add(c);
                    }
                }
                test.remove(possible);
            }
        }
//        Card [] dis = new Card[2];
//        Iterator<Card> itt = this.hand.iterator();
//        dis[0] = itt.next();
//        dis[1] = itt.next();
        Card [] dis_array = new Card[dis.size()];
        super.discard(dis.toArray(dis_array));
        return dis_array;
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
