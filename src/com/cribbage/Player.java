package com.cribbage;

/**
 * Player objects used for making play decisions
 */
public abstract class Player {
    int score;
    Hand hand;

    abstract Card peg();
    abstract Card [] discard();

    Card[] discard(Card [] cards){
        Card [] ret = new Card[cards.length];
        int index = 0;
        for(Card c :cards){
            ret[index] = c;
            this.hand.remove(c);
        }
        return ret;
    }

    /**
     * Constructor for Player that creates hand
     */
    Player(){
        this.hand = new Hand();
    }

    /**
     * Increases the score of the Player by the specified amount
     * @param score Amount to increase score by
     */
    void increaseScore(int score){
        this.score += score;
    }

    /**
     * Query the score value
     * @return The score of the Player
     */
    int getScore(){
        return this.score;
    }

    /**
     * Player representation as the current Hand
     * @return String displaying the current Hand
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Card c : this.hand){
            sb.append(c.toString());
            sb.append(" ");
        }
        return sb.toString();
    }
}
