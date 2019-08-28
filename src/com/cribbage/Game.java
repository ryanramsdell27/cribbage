package com.cribbage;

/**
 * A basic interface for driving a game.
 * Assumes that a game can be broken into atomic steps which
 * can run repeatedly until the game is done
 *
 * @author Ryan
 */
public interface Game {

    /**
     * Steps a single round/hand in a game
     */
    void step();

    /**
     * Determines if a game has finished
     * @return Whether or not the game has finished
     */
    boolean isDone();

}
