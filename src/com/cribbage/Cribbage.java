package com.cribbage;

/**
 * An implementation of the game Cribbage
 */
public class Cribbage implements Game {

    Player [] players;
    Deck deck;

    /**
     * Create a new deck, shuffle, and deal to players
     */
    public Cribbage(){
        deck = new Deck();
        deck.shuffle();

        this.players = new Player[2];
        this.players[0] = new InteractivePlayer();
        this.players[1] = new CPUPlayer();
    }

    /**
     * Steps through one hand of the game, starting with the deal,
     * then discarding, pegging, and finally scoring
     */
    public void step(){
        deck.shuffle();
        Card starter = deck.deal(1)[0];

        for(Player p : players){
            p.hand.add(deck.deal(6));
            p.discard();
            p.hand.add(starter);
            p.hand.setStarter(starter);
            System.out.println(p.toString());
            System.out.println(p.hand.scoreHand());
        }

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
