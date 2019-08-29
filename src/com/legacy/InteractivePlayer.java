package com.legacy;

import java.util.Scanner;

class InteractivePlayer extends Player {
    private Scanner scan;

    InteractivePlayer(){
        super();
        scan = new Scanner(System.in);
    }

    Card peg(int count){
        if(!this.canPeg(count) || !this.hasCards()) return null;
        Hand hand = this.getHand();
        Card [] full = hand.getFull();
        Card [] disp = new Card[4-pegCount];
        int j = 0;
        for(Card c: full){
            if(!c.getPlayed()){
                disp[j] = c;
                j++;
            }
        }
        System.out.println(Hand.handToString(disp));
        for(int i = 0; i < disp.length; i++){
            System.out.print(" " + (i+1));
            if(i != disp.length-1) System.out.print("  |");
            else System.out.println();
        }
        int a = getInput(1,4, null, "Select card to peg")-1;
        pegCount++;
        disp[a].setPlayed(true);
        return disp[a];
    }

    Card [] discard(){
        Hand hand = this.getHand();
        System.out.println(Hand.handToString(hand.getDeal()));
        System.out.println("-----------------------------");
        System.out.println(" 1  | 2  | 3  | 4  | 5  | 6  ");

        int a = getInput(1,6, null, "First index to discard");
        int b = getInput(1,6, a, "Second index to discard");
        System.out.printf("Discarding %d and %d\n", a, b);
        return hand.discard(a-1, b-1);
    }

    private int getInput(int lower, int upper, int reserved, String message){
        int [] res = {reserved};
        return getInput(lower,upper, res, message);
    }

    private int getInput(int lower, int upper, int [] reserved, String message){
        int out = -1;
        boolean invalid = true;
        while(invalid){
            System.out.print(message + ": ");
            if(scan.hasNextInt()) out = scan.nextInt();
            else{
                scan.next();
                System.out.println("Invalid input: non integer! Try again");
                continue;
            }
            if(out >= lower && out <= upper) invalid = false;

            if(reserved != null){
                for (int res: reserved) {
                    if(res == out) invalid = true;
                }
            }
            if(invalid) System.out.println("Invalid input: out of bounds or reserved! Try again");
        }
        return out;
    }
}
