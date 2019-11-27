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
        if (info instanceof NotYourTurnInfo) return;
        BSState theState = new BSState();

        Logger.log(TAG,"My Turn");

       //int rand = (int)(3*Math.random());
        int xLoc = (int)(10*Math.random());
        int yLoc = (int)(10*Math.random());

        //int xLoc = 1;
        //int yLoc = 1;

        if (theState.checkSpot(yLoc,xLoc,0)==2||theState.checkSpot(yLoc,xLoc,0)==3){
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++){
                    game.sendAction(new BSFire(this, j, i));
                    break;

                }
            }

        }else if (theState.checkSpot(yLoc,xLoc,0)==1||theState.checkSpot(yLoc,xLoc,0)==4){
            //game.sendAction(new BSFire(this,xLoc,yLoc));
        }


    }
}
