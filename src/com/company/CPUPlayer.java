package com.company;

import java.util.Iterator;

class CPUPlayer extends Player {
    Deck deck;
    int play_style; // 0 for best max; 1 for highest min

    CPUPlayer(int play_style){
        super();
        deck = new Deck();
        this.play_style = play_style;
    }

    @Override
    Card[] discard() {
        int [] dis = potentialScores();
        return this.getHand().discard(dis[0], dis[1]);
    }

    /* goal: find discard pair that would give highest scoring hand
            iterate over each subset |4| of the hand
            do this by treating hand as 6 bit number with 4 1s and 2 0s
                iterate over each possible starter
                update max
                update min
                keep running sum for averaging
                subtract discard that adds to 15

        */
    int [] potentialScores(){
        Hand hand = this.getHand();
        Card [] deal = hand.getDeal();

        System.out.println(Hand.handToString(deal));

        int [] best_scores = new int [3];
        int [] best_index  = new int [6];

        // these two outer loops create all 15 = 6 nCr 4 combinations of 6 bit numbers with exactly 2 0s
        for(int i = 0; i < 5; i++){ // this is 5 to account for the second 0
            for(int j = i+1; j < 6; j++){
                Card [] selected = new Card[5];
                int index = 0;
                for(int k = 0; k < 6; k++){
                    if(k != i && k != j){
                        selected[index] = deal[k];
                        index++;
                    }
                }
                int [] metrics = getScores(selected);
                if(this.isDealer && deal[i].getValue()+deal[j].getValue() == 15) {
                    for(int m = 0; m < metrics.length; m++){
                        metrics[m] -= 2;
                    }
                }

                for(int m = 0; m < metrics.length; m++){
                    if(metrics[m] > best_scores[m]) {
                        best_index[2*m] = i;
                        best_index[2*m+1] = j;
                        best_scores[m] = metrics[m];
                    }
                }

            }
        }
        int [] to_delete = {best_index[2*this.play_style], best_index[2*this.play_style+1]};
        System.out.printf("Discarding %s and %s for best score of %d\n", deal[to_delete[0]].toString(), deal[to_delete[1]].toString(), best_scores[this.play_style]);
        return to_delete;
    }
    int [] getScores(Card [] selected){
        Hand hand = this.getHand();
        Hand test_hand = new Hand();
        Iterator<Card> itt = deck.iterator();
        Card test;
        int test_score;
        int max_score = -10;
        int min_score = 29;
        while(itt.hasNext()){
            test = itt.next();
            if(hand.contains(test)) continue;

            selected[4] = test;
            test_hand.setStarter(test);
            test_hand.setCribFull(selected);

            test_score = test_hand.score();
            if(max_score < test_score){
                max_score = test_score;
            }
            if(min_score > test_score){
                min_score = test_score;
            }
        }
        int [] ret = {max_score, min_score};
        return ret;
    }

}
