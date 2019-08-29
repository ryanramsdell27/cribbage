package com.Testing;
import com.cribbage.*;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Basic testing framework
 */
public class Main {

    public static void main(String[] args){
        boolean pass = true;
        pass &= combinationIteratorTest();

        if(pass) System.out.println("Passed all tests");
        else System.out.println("Failed at least one test");
    }

    /**
     * Tests if 100 choose 97 produces 161700 sets in the enumeration from CombinationIterator
     * Does not test that the sets are all the correct combination subsets
     * @return True if 161700 sets are produced by iterator
     */
    static boolean combinationIteratorTest(){
        int var_n = 100;
        int var_r  = 97;
        ArrayList<Integer> test_set = new ArrayList<>(var_n);
        for(int i = 0; i < var_n; i++){
            test_set.add(i);
        }
        Iterator<ArrayList<Integer>> itt = new CombinationIterator(test_set, var_r);
        int count = 0;
        while(itt.hasNext()){
            itt.next();
            count++;
        }
        return count == 161700;
    }
}
