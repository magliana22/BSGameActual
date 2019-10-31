package com.example.battleshipgamestate.battleship;

import com.example.battleshipgamestate.game.GameFramework.LocalGame;

public class BSLocalGame extends LocalGame {
    //Tag for logging
    private static final String TAG= "BSLocalGame";
    //the game's state
    protected BSState state;

    /**
     * Constructor for the BSLocalGame
     */
    public BSLocalGame(){
        //use super initialization
        super();

        //create blank BSState object
        state= new BSState();
    }

    /**
     * check if game is over. If it is over return a String that tells
     * who the winner is. If game not over return null
     */
    @Override
    protected String checkIfGameOver(){

    }

}
