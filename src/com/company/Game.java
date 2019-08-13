package com.company;

import java.util.Arrays;

class Game {

    private final Player [] players;
    // Score_Board
    private final Deck deck;
    private final int dealer = 0;
    private final Hand crib;

    Game(){
        deck = new Deck();
        crib = new Hand();
        players = new Player[2];
        //players[0] = new InteractivePlayer();
        players[0] = new CPUPlayer(0);
        players[1] = new CPUPlayer(1);
    }

    boolean done(){
        return players[0].getScore() >= 121 || players[1].getScore() >= 121;
    }

    private void deal(){
        deck.shuffle();
        Card starter = deck.get_sub(1)[0];
        if(starter.getRank() == 10) players[dealer].increaseScore(2); // nibs
        crib.setStarter(starter);
        starter.setPlayed(true); // avoids this being pegged
        players[0].setHand(deck.get_sub(6), starter);
        players[1].setHand(deck.get_sub(6), starter);
    }

    void step(){
        this.deal();
        Card [] crib_temp = new Card[4];
        System.arraycopy(players[0].discard(), 0,crib_temp, 0, 2);
        System.arraycopy(players[1].discard(), 0,crib_temp, 2, 2);
        crib.setCribFull(crib_temp);
        peg();
        printScore();
        players[Math.abs(dealer-1)].increaseScore(players[Math.abs(dealer-1)].score());
        players[dealer].increaseScore(players[dealer].score());
        players[dealer].increaseScore(players[dealer].score(crib));

        players[0].cleanHand();
        players[1].cleanHand();
    }

    private void peg(){
        int current_count = 0;
        Player p1 = players[dealer];
        Player p2 = players[Math.abs(dealer-1)];
//        System.out.println("Current count is " + current_count);

        Card [] history = new Card[8];
        int hist_count = 0;
        while(p1.hasCards() || p2.hasCards()){

            if(!p1.canPeg(current_count) && !p2.canPeg(current_count)){
//                System.out.println("Count restart");
                current_count = 0;
                for(int i = 0; i < 8; i++) history[i] = null;
                hist_count = 0;
                //TODO award point to last. Should be p2, p1 could get point between checks later
            }

            for(int i = 0; i < 2; i++) {
                int play = Math.abs(dealer-i);
                Player p = players[play];
                Card p_play = p.peg(current_count); //TODO should be returning card, right now we return value which fails in pegging of face cards
                if (p_play != null) {
                    current_count += p_play.getValue();
                    history[hist_count] = p_play;
                    hist_count++;
                    int pegged = score_peg(history, hist_count, current_count, this.players);
                    p.increaseScore(pegged);
//                    System.out.printf("%d +%d from p%d for +%d points\n", current_count, p_play.getValue(), play+1, pegged);
                }
            }
        }
    }

    private int score_peg(Card[] history, int hist_count, int current_count, Player[] players){
        //TODO count nibs
        int score = 0;

        // check if magic number
        if(current_count == 15 || current_count == 31) score += 2;
        // check if not 31 and both say go
        if(current_count != 31 && !players[0].canPeg(current_count) && !players[1].canPeg(current_count)) score += 1;

        // check for runs, can be a max of 7 in a row
        Card [] history_c = Arrays.copyOfRange(history, 0, hist_count);
        Arrays.sort(history_c);
        score += Hand.getRunScore(history_c, hist_count);
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


