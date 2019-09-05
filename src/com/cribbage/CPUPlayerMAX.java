package com.cribbage;

import java.util.ArrayList;
import java.util.Iterator;

public class CPUPlayerMAX extends CPUPlayer {
    Deck deck = new Deck();

    @Override
    public Card[] discard() {
        Hand test = new Hand();
        Card[] copy_list = new Card[4];
        Iterator<ArrayList<Card>> combItt = this.hand.getCombinationIterator(4);
        int max_score = 0;
        ArrayList<Card> dis = new ArrayList<>(2);
        while (combItt.hasNext()) {
            test.clear();
            test.add(combItt.next().toArray(copy_list));
            Iterator<Card> deck_iterator = this.deck.iterator();
            while (deck_iterator.hasNext()) {
                Card possible = deck_iterator.next();
                if (this.hand.contains(possible)) continue;
                test.add(possible);
                test.setStarter(possible);
                int possible_score = test.scoreHand();
                if(possible_score > max_score){
                    ArrayList<Card> temp_dis = new ArrayList<>(dis.size());
                    for(Card c:this.hand){
                        if(!test.contains(c)) temp_dis.add(c);
                    }
                    int penalty = 0;
                    if(this.dealer && Hand.isTotal(temp_dis, 15)) penalty -= 2;
                    if(this.dealer && Hand.isDuplicate(temp_dis))penalty -= 2;
                    if(!this.dealer) penalty *= -1;
                    possible_score += penalty;
                    if(possible_score < max_score) continue;
                    max_score = possible_score;
                    dis.clear();
                    dis = temp_dis;

                }
                test.remove(possible);
            }
        }

        Card[] dis_array = new Card[dis.size()];
        super.discard(dis.toArray(dis_array));
        return dis_array;
    }
}

