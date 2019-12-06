package com.example.battleshipgamestate.battleship;

import com.example.battleshipgamestate.game.GameFramework.GameComputerPlayer;
import com.example.battleshipgamestate.game.GameFramework.infoMessage.GameInfo;
import com.example.battleshipgamestate.game.GameFramework.utilities.Logger;

public class BSComputerPlayer1 extends GameComputerPlayer{

    /*
     * Constructor for the BSComputerPlayer1 class
     */

    private Boolean horizontal = true;

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
        if (!(info instanceof BSState)) return;
        Logger.log("BSComputer","CP turn now!");

        BSState state;
        state = (BSState) info; //get game info

        //  if not my turn do nothing
        if(state.playerID != this.playerNum) return;

        int xVal = (int)(10*Math.random());
        int yVal = (int)(10*Math.random());
        int randomOrientation = (int)(Math.random());


        if (state.getPhaseOfGame() != "inPlay"){
            Logger.log("shipAction", "ai adding ship");
            int shipSize = 0; //variable for size of ship
                if (randomOrientation == 0){
                    horizontal = true;
                } else{
                    horizontal = false;
                }
                if (state.p2ShipsAlive == 0) {
                    shipSize = 1; //for first 2 ships, set size to 1 (ship's drawing size will be p.x + 1 = 2)
                } else if (state.p2ShipsAlive == 1 || state.p2ShipsAlive == 2) {
                    shipSize = 2;
                } else if (state.p2ShipsAlive == 3) {
                    shipSize = 3;
                } else if (state.p2ShipsAlive == 4) {
                    shipSize = 4;
                }
                int xEnd = 0;
                int yEnd = 0;
                if (horizontal) {
                    xEnd = xVal + shipSize;
                    yEnd = yVal;
                } else{
                    xEnd = xVal;
                    yEnd = yVal + shipSize;
                }
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
            BSFire action = new BSFire(this, yVal, xVal);
            game.sendAction(action);
        }
    }
}
