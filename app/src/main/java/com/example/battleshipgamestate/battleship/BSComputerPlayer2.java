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
        BSState theState = (BSState)info;

        Logger.log(TAG,"My Turn");

        int xLoc = (int)(10*Math.random());
        int yLoc = (int)(10*Math.random());

        //int xLoc = 1;
        //int yLoc = 1;

       // game.sendAction(new BSMoveAction(this, xLoc, yLoc ));

        // anywhere not on edges for col
        if(theState.checkSpot(xLoc,yLoc,0)==3){
            //&& (yLoc<9 || yLoc>0)) {
                game.sendAction(new BSFire(this, 0, yLoc ));
        /**
        // if ship col 0
        } else if (theState.p1Board[xLoc][yLoc].isHit && yLoc == 0) {
            for (int i = 0; i > 3; i++) {
                game.sendAction(new BSMoveAction(this, xLoc, yLoc+i));
            }
            // if ship col 10
        } else if (theState.p1Board[xLoc][yLoc].isHit && yLoc == 10) {
            for (int i = 0; i > 3; i++) {
                game.sendAction(new BSMoveAction(this, xLoc, yLoc-i));
            }
            // anywhere not on edges for row
        } else if (theState.p1Board[xLoc][yLoc].isHit && (xLoc<10 || xLoc>0)) {
            for (int i = 0; i > 3; i++) {
                game.sendAction(new BSMoveAction(this, xLoc , yLoc+i));
            }
            // if ship row 0
        } else if (theState.p1Board[xLoc][yLoc].isHit && xLoc == 0){
            for (int i = 0; i > 3; i++) {
                    game.sendAction(new BSMoveAction(this, xLoc , yLoc+i));
            }
            // if ship row 10
        } else if (theState.p1Board[xLoc][yLoc].isHit && xLoc == 10 ){
            for (int i = 0; i > 3; i++) {
                game.sendAction(new BSMoveAction(this, xLoc , yLoc-i));
            }*/
        } else {
            game.sendAction(new BSFire(this,xLoc,yLoc));
        }

    }
}
