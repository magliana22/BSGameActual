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
        Logger.log("BSComputer","My turn!");

        int xVal = (int)(10*Math.random());
        int yVal = (int)(10*Math.random());

        BSState state = new BSState();
        state = (BSState) info; //get game info
        if (state.getPhaseOfGame() != "inPlay"){
            Logger.log("shipAction", "ai adding ship");
            int shipSize = 0; //variable for size of ship
                //xVal = (int) (10 * Math.random());
                //yVal = (int) (10 * Math.random());
                if (state.p2ShipsAlive == 0 || state.p2ShipsAlive == 1) {
                    shipSize = 1; //for first 2 ships, set size to 1 (ship's drawing size will be p.x + 1 = 2)
                } else if (state.p2ShipsAlive == 2) {
                    shipSize = 2;
                } else if (state.p2ShipsAlive == 3) {
                    shipSize = 3;
                } else if (state.p2ShipsAlive == 4) {
                    shipSize = 4;
                }
                int xEnd = xVal + shipSize;
                int yEnd = yVal;

                BSShip ship = new BSShip(xVal, xEnd, yVal, yEnd, 1); //p2's (AI's ship)

                if (xEnd > 9) { //bounds check right side of board, shift out-of-bounds ships to left
                    xEnd -= shipSize;
                    ship = new BSShip(xVal - shipSize, xEnd, yVal, yEnd, 1);
                }
                //this.p2Ships[x] = ship;
                BSAddShip action = new BSAddShip(this, ship);
                game.sendAction(action);
        } else{
            Logger.log("fire","ai sending fire");
            sleep(2);
            BSMoveAction action = new BSMoveAction(this, yVal, xVal);
            game.sendAction(action);
        }


        // Commented out b/c LocalGame incomplete
        game.sendAction(new BSMoveAction(this, xVal,yVal));
    }

}
