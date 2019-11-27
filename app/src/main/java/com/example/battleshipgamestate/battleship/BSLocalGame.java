package com.example.battleshipgamestate.battleship;

import com.example.battleshipgamestate.game.GameFramework.GameComputerPlayer;
import com.example.battleshipgamestate.game.GameFramework.GameHumanPlayer;
import com.example.battleshipgamestate.game.GameFramework.GamePlayer;
import com.example.battleshipgamestate.game.GameFramework.LocalGame;
import com.example.battleshipgamestate.game.GameFramework.actionMessage.GameAction;
import com.example.battleshipgamestate.game.GameFramework.utilities.Logger;

public class BSLocalGame extends LocalGame {
    //Tag for logging
    private static final String TAG = "BSLocalGame";
    // the game's state
    protected BSState state;



    /**
     * Constructor for the BSLocalGame.
     */
    public BSLocalGame() {

        // perform superclass initialization
        super();

        // create a new, unfilled-in BSState object
        state = new BSState();
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

            //get the row and column position of the player's move
            BSFire bsm = (BSFire) action;
            int row = bsm.getRow();
            int col = bsm.getCol();
            boolean okayMove = state.fire(row, col);
            if (okayMove) {

                state.changeTurn();
                return true;
            } else {

                //return false;
                return false;
            }
        }
        else if (action instanceof BSAddShip && this.checkGamePhase()==1){
            Logger.log(TAG,"action is addShip");
            BSAddShip bas = (BSAddShip) action;
            return state.addShip(getPlayerIdx(bas.getPlayer()), bas.getShip());
        }
        else if(action instanceof BSRotateAction && this.checkGamePhase()==1){
            BSRotateAction bsra= (BSRotateAction) action;
            GamePlayer player = action.getPlayer();
            int playerId = -1;
            if (player instanceof  BSHumanPlayer1){
                playerId = ((BSHumanPlayer1)player).getPlayerNum();
            }
            else if(player instanceof  BSComputerPlayer1){
                playerId=((BSComputerPlayer1)player).getPlayerNum();
            }
            else if(player instanceof BSComputerPlayer2){
                playerId=((BSComputerPlayer2)player).getPlayerNum();
            }
            state.rotateShip(playerId);
        }
        else if(action instanceof BSPlayerReadyAction && this.checkGamePhase()==1){
            int theReadyPlayer= -1;
            BSPlayerReadyAction playerReady= (BSPlayerReadyAction) action;
            GamePlayer player= action.getPlayer();
            if(player instanceof GameHumanPlayer){
                theReadyPlayer=((BSHumanPlayer1) player).getPlayerNum();
            }
            else if(player instanceof GameComputerPlayer){
                theReadyPlayer=((GameComputerPlayer) player).getPlayerNum();
            }
            if(theReadyPlayer==1){
                state.changeP1Ready();
            }
            else if(theReadyPlayer==2){
                state.changeP2Ready();
            }
            state.progressGame();
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
}
