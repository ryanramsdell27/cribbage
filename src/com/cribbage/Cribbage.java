package com.cribbage;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the game Cribbage
 */
public class Cribbage implements Game {

    Player [] players;
    int dealer;
    Deck deck;
    Hand crib;

    /**
     * Create a new deck, shuffle, and deal to players
     */
    public Cribbage(){
        this.crib = new Hand();
        this.deck = new Deck();
        this.deck.shuffle();

        this.players = new Player[2];
        this.players[0] = new CPUPlayer();
        this.players[1] = new CPUPlayer();

        this.dealer = 0;
    }

    /**
     * Steps through one hand of the game, starting with the deal,
     * then discarding, pegging, and finally scoring
     */
    public void step(){
        deck.shuffle();
        Card starter = deck.deal(1)[0];

        /* Deal and discard, also set up the crib */
        for(Player p : this.players){
            p.hand.clear();
            p.hand.add(deck.deal(6));
            crib.add(p.discard());
            p.setPeg();
            p.hand.add(starter);
            p.hand.setStarter(starter);
            System.out.println(p.toString());
            System.out.println(p.hand.scoreHand());
        }
        this.crib.clear();
        if(this.players.length == 3) this.crib.add(this.deck.deal(1));
        this.crib.add(starter);
        this.crib.setStarter(starter);

        /* Pegging */
        boolean can_peg = true;
        int pegger = this.dealer;
        int running_sum = 0;
        ArrayList<Card> peg_pile = new ArrayList<Card>();
        while(can_peg) {
            Card played = players[pegger].peg(peg_pile);
            if(played != null) {
                peg_pile.add(played);
                running_sum += played.value;
                System.out.printf("Player %d pegged %s for total %d\n", pegger, played, running_sum);

                /* Score pegged card */
                if (Hand.isTotal(peg_pile, 15)) this.players[pegger].increaseScore(2);
                // Longest possible run is A,2,3,4,5,6,7 which adds to 28
                // Largest pile is 13 (for >2 players) of A,A,A,A,2,2,2,2,3,3,3,3,4 - also 28
                for (int i = 2; i < peg_pile.size(); i++) {
                    List<Card> sub_list = peg_pile.subList(peg_pile.size() - i - 1, peg_pile.size() - 1);
                    if (Hand.isDuplicate(sub_list)) this.players[pegger].increaseScore(2);
                    if (Hand.isRun(sub_list)) {
                        if (i == 3) this.players[pegger].increaseScore(3);
                        else this.players[pegger].increaseScore(1);
                    }
                }

                if (Hand.isTotal(peg_pile, 31)) this.players[pegger].increaseScore(1);
                if (!this.canPeg(peg_pile)) {
                    this.players[pegger].increaseScore(1);
                    peg_pile.clear();
                    running_sum = 0;
                    System.out.println("Cleared peg pile");
                }
            }

            can_peg = this.canPeg(peg_pile);

            pegger = (pegger+1) % this.players.length;
        }

        /* Score hands and crib */
        for(int i = 0; i < this.players.length; i++){
            Player cur_play = this.players[(this.dealer+i+1)%this.players.length];
            cur_play.increaseScore(cur_play.hand.scoreHand());
        }
        this.players[this.dealer].increaseScore(this.crib.scoreHand());

        /* Set up next deal */
        this.dealer = (this.dealer+1)%this.players.length;

        for(Player p:this.players) System.out.printf("Player %s scored %d\n", p.toString(), p.score);

    }

    boolean canPeg(ArrayList<Card> peg_pile){
        boolean can_peg = false;
        for (Player p : this.players) {
            can_peg |= p.canPeg(peg_pile);
        }
        return can_peg;
    }

    /**
     * Determine if the game is done
     * @return True if any player has met the standard score limit of 121
     */
    public boolean isDone(){
        for(Player p: players){
            if(p.score >= 121) return true;
        }
        return false;
    }

    /*TODO for pegging run count use Hand.isRun(set) where set increasingly becomes last n elements in
    *  pegging pile (only continue if method returns true) then if number of recursions > 3 add that
    *  number to the score. Will need ot make static, and add additional param to isFlush for crib*/
}
