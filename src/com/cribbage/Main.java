package com.cribbage;

import com.Testing.Plot;

import java.util.Arrays;

public class Main {
    public static void main(String[] args){
        Integer [] scores = {0,0};
        for(int i = 0; i < 50; i++) {
            Cribbage cribbage = new Cribbage(i%2);
            while (!cribbage.isDone()) {
                cribbage.step();
            }
            int [] tmp_scores = cribbage.getScore();
            System.out.println(Arrays.toString(tmp_scores));
            if(tmp_scores[0] > tmp_scores[1]) scores[0]++;
            else scores[1]++;
        }
        Plot.plotBar(scores, 50, scores);

    }
    /* TODO Do not trust the stats reported thus far, max should not be beating out the
        average by that much (7 to 493). Possibly always
     */
}
