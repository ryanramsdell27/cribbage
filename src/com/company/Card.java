package com.company;

import java.util.Comparator;

public class Card implements Comparator<Card>, Comparable<Card> {
    int value;
    boolean played;

    public Card(Integer i){
        value = i;
        played = false;
    }

    @Override
    public String toString() {
        int suit_num = value / 13;
        int valu_num = value % 13;

        String suit = null;
        switch(suit_num){
            case 0: suit = Character.toString((char)9825); break; // heart
            case 1: suit = Character.toString((char)9826); break; // diamond
            case 2: suit = Character.toString((char)9827); break; // clubs
            case 3: suit = Character.toString((char)9824); break; // spades
            default: System.err.print("Bad suit, card int most likely greater than 52");
        }
        String value;
        switch(valu_num){
            case 0 : value = "A"; break;
            case 10: value = "J"; break;
            case 11: value = "Q"; break;
            case 12: value = "K"; break;
            default: value = Integer.toString(valu_num+1);
        }

        return value + suit;
    }

    public int getValue() {
        if(value < 0) return -1;

        int valu_num = value % 13;
        int value = 0;
        switch(valu_num){
            case 10:
            case 11:
            case 12:value = 10; break;
            default: value = valu_num + 1;
        }
        return value;
    }

    @Override
    public int compare(Card card1, Card card2) {
        return card1.getValue() - card2.getValue();
    }

    @Override
    public int compareTo(Card o) {
        return this.getValue() - o.getValue();
    }

    public void setValue(int val){
        this.value = val;
    }
    public void setPlayed(boolean play){
        this.played = play;
    }
    public boolean getPlayed(){
        return this.played;
    }
}