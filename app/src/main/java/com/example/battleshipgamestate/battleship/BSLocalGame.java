package com.example.battleshipgamestate.battleship;

import com.example.battleshipgamestate.game.GameFramework.GamePlayer;
import com.example.battleshipgamestate.game.GameFramework.LocalGame;
import com.example.battleshipgamestate.game.GameFramework.actionMessage.GameAction;
import com.example.battleshipgamestate.game.GameFramework.utilities.Logger;

public class BSLocalGame extends LocalGame {
    //Tag for logging
    private static final String TAG = "BSLocalGame";
    // the game's state
    protected BSState state;
    private boolean p1Ready;
    private boolean p2Ready;



    /**
     * Constructor for the BSLocalGame.
     */
    public BSLocalGame() {

        // perform superclass initialization
        super();

        // create a new, unfilled-in BSState object
        state = new BSState();
        p1Ready=false;
        p2Ready=false;
    }

    /**
     * Check if the game is over. It is over, return a string that tells
     * who the winner(s), if any, are. If the game is not over, return null;
     *
     * @return
     * 		a message that tells who has won the game, or null if the
     * 		game is not over
     */
    @Override
    protected String checkIfGameOver() {
        int shipfound1 = 0;
        int shipfound2 = 0;
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                int result = state.checkSpot(row, col, 0);
                int result2 = state.checkSpot(row, col, 1);
                // player1's board
                if (result == 2){ //if square is a ship
                    shipfound1++;
                }
                // player2's board
                if (result2 == 2){ //if square is
                    shipfound2++;
                }

            }
        }
        if (shipfound1 == 0){
            Logger.log("Win Condition", "Player 1 has WON!");
            return playerNames[1]+" is the winner.";
        }
        else if (shipfound2 == 0){
            Logger.log("Win Condition", "Player 2 has WON!");
            return playerNames[0]+" is the winner.";
        }
        return null;

        //if(state.p1TotalHits==17){
          //  return playerNames[0]+" is the winner.";
        //}
        //else if(state.p2TotalHits==17){
          //  return playerNames[1]+" is the winner.";
        //}
        //else{
          //  return null;
        //}

    }

    /**
     * Notify the given player that its state has changed. This should involve sending
     * a GameInfo object to the player. If the game is not a perfect-information game
     * this method should remove any information from the game that the player is not
     * allowed to know.
     *
     * @param p
     * 			the player to notify
     */
    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {

        // make a copy of the state, and send it to the player
        BSState copy = new BSState(state);
        p.sendInfo(copy);

    }

    /**
     * Tell whether the given player is allowed to make a move at the
     * present point in the game.
     *
     * @param playerIdx
     * 		the player's player-number (ID)
     * @return
     * 		true if the player is allowed to move
     */
    protected boolean canMove(int playerIdx) {
        return playerIdx == state.getPlayerID();
    }

    /**
     * Makes a move on behalf of a player.
     *
     * @param action
     * 			The move that the player has sent to the game
     * @return
     * 			Tells whether the move was a legal one.
     */
    @Override
    protected boolean makeMove(GameAction action) {

        if (action instanceof BSFire && this.checkGamePhase()==2) {
          //  Logger.log("makeMove", "about to fire");
            //get the row and column position of the player's move
            BSFire bsm = (BSFire) action;
            int row = bsm.getRow();
            int col = bsm.getCol();
            boolean okayMove = state.fire(row, col);
            if (okayMove) {
              //  Logger.log("changeTurn", "moveisValid");
                state.changeTurn();
                return true;
            } else {
               // Logger.log("moveIsInvalid", "moveInvalid");
                //return false;
                return false;
            }
        }
        else if (action instanceof BSAddShip && this.checkGamePhase()==1){
            Logger.log(TAG,"action is addShip");
            BSAddShip bas = (BSAddShip) action;
            return state.addShip(getPlayerIdx(bas.getPlayer()), bas.getShip());
        }
        /**
        // get the 0/1 id of target player (player who's board is being attacked)
        int playerId = state.getPlayerTarget();

        // if that space is not water or ship, indicate an illegal move
        if (state.checkSpot(row, col, playerId) != 1 && state.checkSpot(row, col, playerId) != 2) {
            return false;
        }


        // get the 0/1 id of the player whose move it is
        int whoseMove = state.getPlayerID();

        // place the player's piece on the selected square
        state.setPiece(row, col, mark[playerId]);

        // make it the other player's turn
        state.changeTurn();

        // bump the move count
        moveCount++;

        // return true, indicating the it was a legal move
        return true;*/

        return false;
    }

    //checks the phase of the game. Will be used to check what moveActions can be used when
    protected int checkGamePhase(){
        if(state.phaseOfGame.equals("Setup")){
            return 1;
        }
        else if(state.phaseOfGame.equals("inPlay")){
            return 2;
        }
        return 0;
    }

    //if both players are ready the LocalGame will set the phase of game for it's state to inPlay
    protected void progressGame(){
        if(p1Ready && p2Ready){
            state.setPhaseOfGame(2);
        }
        else{
            state.setPhaseOfGame(1);
        }
    }
}
