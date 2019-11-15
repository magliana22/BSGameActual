package com.example.battleshipgamestate.battleship;

import com.example.battleshipgamestate.game.GameFramework.GameComputerPlayer;
import com.example.battleshipgamestate.game.GameFramework.infoMessage.GameInfo;
import com.example.battleshipgamestate.game.GameFramework.infoMessage.NotYourTurnInfo;
import com.example.battleshipgamestate.game.GameFramework.utilities.Logger;

public class BSComputerPlayer2 extends GameComputerPlayer {

    // Tag for logging
    private static  final String TAG = "BSComputerPlayer2";


    /**
     * constructor for a computer player
     *
     * @param name
     * 		the player's name
     */
    public BSComputerPlayer2(String name){
        super(name);
    }


    @Override
    protected void receiveInfo(GameInfo info) {
        //if (!(info instanceof BSState)) return;
        //BSState myState = (BSState)info;

        // if it's not our move, ignore it
        //if (myState.getPlayerID() != this.playerNum) return;

        //sleep(5);

        if (info instanceof NotYourTurnInfo) return;
        Logger.log("BSComputer2","My turn!");

        int xVal = (int)(10*Math.random());
        int yVal = (int)(10*Math.random());


        // delay for 2 seconds to mimic thinking
        sleep(2);


        // Commented out b/c LocalGame incomplete
        game.sendAction(new BSMoveAction(this, xVal,yVal));
    }
}
