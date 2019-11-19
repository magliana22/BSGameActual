package com.example.battleshipgamestate.battleship;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.example.battleshipgamestate.game.GameFramework.GamePlayer;
import com.example.battleshipgamestate.game.GameFramework.LocalGame;
import com.example.battleshipgamestate.game.GameFramework.actionMessage.EndTurnAction;
import com.example.battleshipgamestate.game.GameFramework.actionMessage.GameAction;
import com.example.battleshipgamestate.game.GameFramework.actionMessage.GameOverAckAction;
import com.example.battleshipgamestate.game.GameFramework.actionMessage.MyNameIsAction;
import com.example.battleshipgamestate.game.GameFramework.actionMessage.ReadyAction;
import com.example.battleshipgamestate.game.GameFramework.actionMessage.TimerAction;
import com.example.battleshipgamestate.game.GameFramework.infoMessage.BindGameInfo;
import com.example.battleshipgamestate.game.GameFramework.infoMessage.GameOverInfo;
import com.example.battleshipgamestate.game.GameFramework.infoMessage.IllegalMoveInfo;
import com.example.battleshipgamestate.game.GameFramework.infoMessage.NotYourTurnInfo;
import com.example.battleshipgamestate.game.GameFramework.infoMessage.StartGameInfo;
import com.example.battleshipgamestate.game.GameFramework.utilities.GameTimer;
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



        if(state.p1TotalHits==17){
            return playerNames[0]+" is the winner.";
        }
        else if(state.p2TotalHits==17){
            return playerNames[1]+" is the winner.";
        }
        else{
            return null;
        }

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
     * 		true iff the player is allowed to move
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

        if (action instanceof BSMoveAction) {
          //  Logger.log("makeMove", "about to fire");
            //get the row and column position of the player's move
            BSMoveAction bsm = (BSMoveAction) action;
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
        else if (action instanceof BSAddShip){
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
}
