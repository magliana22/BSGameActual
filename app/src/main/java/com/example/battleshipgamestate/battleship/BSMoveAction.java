package com.example.battleshipgamestate.battleship;

import com.example.battleshipgamestate.game.GameFramework.GamePlayer;
import com.example.battleshipgamestate.game.GameFramework.actionMessage.GameAction;

public class BSMoveAction extends GameAction {

    //Tag for logging
    // Not sure if needed
    private static final String TAG = "BSMoveAction";

    // instance variables: the selected row and column
    private int row;
    private int col;

    /**
     * Constructor for BSMoveAction
     *
     * @param player the player who created the action
     * @param row the row of the square selected (0-9)
     * @param col the column of the square selected
     */
    public BSMoveAction(GamePlayer player, int row, int col) {
        super(player);

        this.row = Math.max(0, Math.min(9,row));
        this.col = Math.max(0, Math.min(9,col));



    }

    public int getRow(){
        return this.row;
    }

    public int getCol(){
        return this.col;
    }
}
