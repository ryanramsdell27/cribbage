package com.company;

import java.util.Arrays;

class Game {

    private Player [] players;
    // Score_Board
    private Deck deck;
    private int dealer = 0;
    private Card [] crib;
    private Card starter;

    Game(){
        deck = new Deck();
        crib = new Card[4];
        players = new Player[2];
        players[0] = new Player();
        players[1] = new CPUPlayer();
    }

    boolean done(){
        return players[0].getScore() >= 121 || players[1].getScore() >= 121;
    }
    private void deal(){
        deck.shuffle();
        players[0].set_hand(deck.get_sub(6));
        players[1].set_hand(deck.get_sub(6));
        starter = deck.get_sub(1)[0];
    }

    void step(){
        this.deal();
        players[0].discard(crib, 0);
        players[1].discard(crib, 2);
        peg();
        printScore();
        players[Math.abs(dealer-1)].score(starter);
        players[dealer].score(starter);
        players[dealer].score(crib, starter);

        players[0].cleanHand();
        players[1].cleanHand();
    }

    private void peg(){
        int current_count = 0;
        Player p1 = players[dealer];
        Player p2 = players[Math.abs(dealer-1)];
        System.out.println("Current count is " + current_count);

        int [] history = new int[8];
        int hist_count = 0;

        while(p1.hasCards() || p2.hasCards()){

            if(!p1.canPeg(current_count) && !p2.canPeg(current_count)){
                System.out.println("Count restart");
                current_count = 0;
                for(int i = 0; i < 8; i++) history[i] = 0;
                hist_count = 0;
                //TODO award point to last. Should be p2, p1 could get point between checks later
            }

            for(int i = 0; i < 2; i++) {
                int play = Math.abs(dealer-i);
                Player p = players[play];
                int p_play = p.peg(current_count); //TODO should be returning card, right now we return value which fails in pegging of face cards
                if (p_play > 0) {
                    current_count += p_play;
                    history[hist_count] = p_play;
                    hist_count++;
                    int pegged = score_peg(history, hist_count, current_count, this.players);
                    p.increaseScore(pegged);
                    System.out.printf("%d +%d from p%d for +%d points\n", current_count, p_play, play+1, pegged);
                }
            }
        }
    }

    private int score_peg(int[] history, int hist_count, int current_count, Player[] players){
        //TODO count nibs
        int score = 0;

        // check if magic number
        if(current_count == 15 || current_count == 31) score += 2;
        // check if not 31 and both say go
        if(current_count != 31 && !players[0].canPeg(current_count) && !players[1].canPeg(current_count)) score += 1;

        // check for runs, can be a max of 7 in a row
        int [] history_c = Arrays.copyOfRange(history, 0, hist_count);
        Arrays.sort(history_c);
        for(int i = 3; i <= hist_count; i++){
            boolean isRun = true;
            for(int j = 0; j < i-1; j++){
                if(history_c[j] != history_c[j+1]-1) isRun = false;
            }
            if(isRun) {
                if (i == 3) score += 3;
                else score++;
            }
        }
        int [] dups = {2,4,6};
        //TODO check on pegging of face cards, fails because of values?
        for(int i = 2; i <= Math.min(hist_count, 4); i++){
            boolean isDup = true;
            for(int j = hist_count-1; j > hist_count - i; j--){
                if(history[j] != history[j-1]) isDup = false;
            }
            if(isDup) score += dups[i-2];
        }
        return score;
    }

    private void printScore(){
        System.out.printf("p1 %d p2 %d\n", players[0].getScore(), players[1].getScore());
    }
}


