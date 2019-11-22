package com.example.battleshipgamestate.battleship;

import com.example.battleshipgamestate.game.GameFramework.GameComputerPlayer;
import com.example.battleshipgamestate.game.GameFramework.infoMessage.GameInfo;
import com.example.battleshipgamestate.game.GameFramework.infoMessage.NotYourTurnInfo;
import com.example.battleshipgamestate.game.GameFramework.utilities.Logger;

public class BSComputerPlayer1 extends GameComputerPlayer{

    /*
     * Constructor for the BSComputerPlayer1 class
     */

    public BSComputerPlayer1(String name){
        // invoke superclass constructor
        super(name);
        // invoke superclass constructor
    }

    /**
     * Called when the player receives a game-state (or other info) from the
     * game.
     *
     * @param info
     * 		the message from the game
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        if (info instanceof NotYourTurnInfo) return;
      //  Logger.log("BSComputer","My turn!");

        int xVal = (int)(10*Math.random());
        int yVal = (int)(10*Math.random());

        // delay for 2 seconds to mimic thinking
        //sleep(0.5);


        game.sendAction(new BSMoveAction(this, xVal,yVal));
    }

}
