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

    /**
     * Called when the player receives a game-state (or other info) from the
     * game.
     *
     * @param info
     * 		the message from the game
     */
    @Override
    protected void receiveInfo(GameInfo info) {
        if (!(info instanceof BSState)) return;
        Logger.log(TAG,"CP turn now!");

        int xVal = (int)(10*Math.random());
        int yVal = (int)(10*Math.random());

        BSState state;
        state = (BSState) info; //get game info
        if (state.getPhaseOfGame() != "inPlay"){
            Logger.log("shipAction", "ai adding ship");
            int shipSize = 0; //variable for size of ship

            if (state.p2ShipsAlive == 0) {
                shipSize = 1; //for first 2 ships, set size to 1 (ship's drawing size will be p.x + 1 = 2)
            } else if (state.p2ShipsAlive == 1 || state.p2ShipsAlive == 2) {
                shipSize = 2;
            } else if (state.p2ShipsAlive == 3) {
                shipSize = 3;
            } else if (state.p2ShipsAlive == 4) {
                shipSize = 4;
            }
            int xEnd = xVal + shipSize;
            int yEnd = yVal;

            BSShip ship = new BSShip(xVal, xEnd, yVal, yEnd, 1); //p2's (AI's ship)

            if (xVal + shipSize > 9) { //bounds check right side of board, shift out-of-bounds ships to left
                xVal = 9 - shipSize;
                xEnd = xVal + shipSize;
                ship = new BSShip(xVal, xEnd, yVal, yEnd, 1);
            }

            //check if all locations of ship are empty (no ship is there already)
            for (int i = xVal; i <= xEnd; i++){
                for (int j = yVal; j <= yEnd; j++){
                    if (!state.validLocation(ship,i, j, 1)){
                        Logger.log("invalidPlacementAI","ship exists already at: " + xVal + " " + yVal);
                        this.receiveInfo(state); //recursively call receiveInfo on this state so AI places ship in a different spot
                    }
                }
            }

            BSAddShip action = new BSAddShip(this, ship);

            game.sendAction(action);
        } else{
            Logger.log("fire","ai sending fire");
            BSMoveAction action = new BSMoveAction(this, yVal, xVal);
            game.sendAction(action);
        }
    }
}
