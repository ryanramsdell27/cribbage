package com.cribbage;

import java.util.*;

/**
 * An iterator for the elements in the power set of this hand. Enumerates the power set
 * by creating an n-bit binary representation of the given set and cycling through all 2^n
 * values of the representation.
 * Assumes that the given set will not change before use of the iterator is done. Changes
 * in cardinality or in ordering will break this
 */
class PowerSetIterator<E> implements Iterator{
    int loc = 0;
    int num_bits;
    ArrayList<E> set;

    /**
     * Creates a new iterator to enumerate the 2^num_bits elements in the power set
     * @param set The set
     */
    PowerSetIterator(ArrayList<E> set){
        this.num_bits = set.size();
        this.set = set;
    }

    /**
     * Track that our location in the enumeration of the power set is less
     * than it's cardinality of 2^num_bits
     * @return True if there are still elements to enumerate
     */
    @Override
    public boolean hasNext() {
        return this.loc < 1<<num_bits;
    }

    /**
     * Generates the next set in the enumeration of the Power set
     * Converts the loc marker into a bit array, any set bits indicate
     * that that element will be in the returned set. Constructs said
     * set, increases the marker, and returns the set.
     * @return An element of the power set of the given set, i.e., a subset of the given set
     */
    @Override
    public Object next() {
        ArrayList<E> ret = new ArrayList<E>();
        BitSet bs = BitSet.valueOf( new long[]{this.loc});
        //System.out.print(bs.toString() + " ");
        for(int i = 0; i < this.num_bits; i++){
            if(bs.get(i)) ret.add(this.set.get(i));
        }
        loc++;
        //System.out.print(loc + " ");
        return ret;
    }
}

/**
 * A playable hand class. Stores a collection of cards with the ability to add and remove from the collection.
 * Maintains the starter card, and has scoring functionality
 */
public class Hand implements Iterable<Card> {
    private final int max_hand_size = 7;
    private ArrayList<Card> hand;
    boolean is_crib;
    Card starter;

    /**
     * Constructor creates a new array of max size
     */
    Hand(){
        this.hand = new ArrayList<>(max_hand_size);
        this.starter = null;
        this.is_crib = false;
    }

    /**
     * Creates an iterator for the list of card in the hand
     * @return Inherited iterator from the hand member
     */
    @Override
    public Iterator<Card> iterator() {
        return this.hand.iterator();
    }

    /**
     * Creates an iterator to go through elements of power set of the hand
     * @return An iterator that enumerates the power set
     */
    Iterator<ArrayList<Card>> getPowerSetIterator(){
        PowerSetIterator<Card[]> psi = new PowerSetIterator(this.hand);
        return psi;
    }

    /**
     * Find the number of cards in the hand by looking at size of card list
     * @return The number of cards in the hand
     */
    int size(){
        return this.hand.size();
    }

    /**
     * Adds a card to the hand
     * @param c The Card object to add
     */
    void add(Card c){
        this.hand.add(c);
    }

    /**
     * Add several cards to the hand
     * @param cards The array of Cards
     */
    void add(Card [] cards){
        Collections.addAll(this.hand, cards);
    }

    /**
     * Removes a card from the hand
     * @param c The Card object to remove
     */
    void remove(Card c){
        this.hand.remove(c);
    }

    void setStarter(Card c){
        this.starter = c;
    }


    /**
     * Scores the hand and returns the value
     * TODO keep list(s) of scoring sets to display to user which parts scored
     * @return The value of the hand
     */
    int scoreHand(){
        int score = 0;
        Iterator<ArrayList<Card>> psi = this.getPowerSetIterator();
        while(psi.hasNext()){
            int old_score = score;
            ArrayList<Card> set = psi.next();

            if(isFifteen(set)) score += 2;
            if(isDuplicate(set)) score += 2;
            // TODO investigate. Should count the first 3 cards of the set as a full run then adds on for the rest
            if(isRun(set)){
                int ss = set.size();
                if(ss == 3) score += 3;
                if(ss == 4) score -= 2; // [1,2,3,4] counts [1,2,3] + 3 and [2,3,4] +3 = 6 so we subtract 2 to get 4
                if(ss == 5) score += 0; // [1,2,3,4,5] counts 3 sets of 3 and 2 sets of 4; +3 +3 +3 -2 -2 = 5. Hence 0
            }
            if(isFlush(set)) {
                if (set.size() == 5 && this.is_crib) score += 5;
                else if (set.size() == 5 && !this.is_crib) score += 1;
                else score += 4;
            }
            if(isNob(set)) score++;

            if(score > old_score) System.out.printf("%s +%d\n", set,score-old_score);
        }
        return score;
    }

    /**
     * Checks if the set of cards' ranks add up to 15
     * @param set A list of cards
     * @return True if the sum of the cards add to 15
     */
    boolean isFifteen(ArrayList<Card> set){
        int sum = 0;
        for(Card c : set){
            sum += c.value;
        }
        return sum == 15;
    }

    /**
     * Checks if a pair of cards are a duplicate (same rank)
     * @param set A set of cards
     * @return True if duplicate
     */
    boolean isDuplicate(ArrayList<Card> set){
        if(set.size() != 2) return false;
        return set.get(0).rank == set.get(1).rank;
    }

    /**
     * Checks if a set of cards' ranks form a run
     * @param set A list of cards
     * @return True if there is a run
     */
    boolean isRun(ArrayList<Card> set){
        if(set.size() < 3) return false;
        Collections.sort(set);
        for(int i = 0; i < set.size()-1; i++){
            if(set.get(i).rank != set.get(i+1).rank-1) return false;
        }
        return true;
    }

    /**
     * Determine if the set is a flush. Takes into consideration that cribs can only score flushes of 5
     * Also checks that a flush of 4 does not include the starter.
     * TODO ensure that the starter is initialized/not null
     * @param set A list of cards
     * @return True if a valid flush exists in the hand
     */
    boolean isFlush(ArrayList<Card> set){
        if((!this.is_crib && set.size() < 4) || (this.is_crib && set.size() < 5)) return false;
        int base = set.get(0).suit;
        for(Card c : set){
            if(c.suit != base) return false;
        }
        if(set.size() == 4 && set.contains(this.starter)) return false;
        return true;
    }

    /**
     * Checks if the set is a jack (non-starter) and if it matches the suit of the starter
     * Assumes set is not null.
     * @param set
     * @return True if the set awards nob
     */
    boolean isNob(ArrayList<Card> set){
        if(set.size() != 1) return false;
        Card c = set.get(0);
        if(c == this.starter || c.rank != 11) return false;
        if(c.suit == this.starter.suit) return true;
        return false;
    }

}
