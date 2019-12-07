package com.example.battleshipgamestate.battleship;

import android.util.Pair;

import com.example.battleshipgamestate.game.GameFramework.GameComputerPlayer;
import com.example.battleshipgamestate.game.GameFramework.infoMessage.GameInfo;
import com.example.battleshipgamestate.game.GameFramework.utilities.Logger;

import java.util.ArrayList;

public class BSComputerPlayer2 extends GameComputerPlayer {

    // Tag for logging
    private static  final String TAG = "BSComputerPlayer2";
    // array list for locations to fire
    protected ArrayList<Pair<Integer,Integer>> toFire = new ArrayList<>();
    // pair for last location fired on
    protected Pair<Integer,Integer> lastFired = null;



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
        BSState state;
        state = (BSState) info; //get game info

        //  if not my turn do nothing
        if(state.playerID != this.playerNum) return;

        Logger.log(TAG,"CP turn now!");

        int xVal = (int)(10*Math.random());
        int yVal = (int)(10*Math.random());

        if (this.playerNum == 1) {
        if (state.getPhaseOfGame() != "inPlay") {
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
                for (int i = xVal; i <= xEnd; i++) {
                    for (int j = yVal; j <= yEnd; j++) {
                        if (!state.validLocation(ship, i, j, 1)) {
                            Logger.log("invalidPlacementAI", "ship exists already at: " + xVal + " " + yVal);
                            this.receiveInfo(state); //recursively call receiveInfo on this state so AI places ship in a different spot
                        }
                    }
                }

                BSAddShip action = new BSAddShip(this, ship);

                game.sendAction(action);
            } else {
                Logger.log("fire", "ai sending fire");
                //Logger.log("Spot Type Is",""+state.checkSpot(xVal,yVal,0));


                /**
                 External Citation
                 Date: 3 December 2019
                 Problem:
                 Resource: Dr. Triblehorn
                 Solution:
                 */

                if (lastFired != null) {
                    if (state.checkSpot(lastFired.first, lastFired.second, 0) == 3) {
                        //add all neighbors that aren't hits or misses to the toFire list
                        int type = state.checkSpot(lastFired.first + 1, lastFired.second, 0);
                        if (type != -1 && type != 3 && type != 4) {
                            toFire.add(new Pair<Integer, Integer>(lastFired.first + 1, lastFired.second));
                        }
                        type = state.checkSpot(lastFired.first, lastFired.second + 1, 0);
                        if (type != -1 && type != 3 && type != 4) {
                            toFire.add(new Pair<Integer, Integer>(lastFired.first, lastFired.second + 1));
                        }
                        type = state.checkSpot(lastFired.first - 1, lastFired.second, 0);
                        if (type != -1 && type != 3 && type != 4) {
                            toFire.add(new Pair<Integer, Integer>(lastFired.first - 1, lastFired.second));
                        }
                        type = state.checkSpot(lastFired.first, lastFired.second - 1, 0);
                        if (type != -1 && type != 3 && type != 4) {
                            toFire.add(new Pair<Integer, Integer>(lastFired.first, lastFired.second - 1));
                        }
                        lastFired = null;
                    }
                }

                if (toFire.isEmpty()) {
                    BSFire action = new BSFire(this, yVal, xVal);
                    lastFired = new Pair<>(yVal, xVal);
                    game.sendAction(action);

                } else {
                    Pair<Integer, Integer> loc = toFire.get(0);
                    toFire.remove(0);
                    BSFire action = new BSFire(this, loc.first, loc.second);
                    game.sendAction(action);

                }

            }
        }
        else if(this.playerNum==0){
            if (state.getPhaseOfGame() != "inPlay") {
                Logger.log("shipAction", "ai adding ship");
                int shipSize = 0; //variable for size of ship
                if (state.p1ShipsAlive == 0) {
                    shipSize = 1; //for first 2 ships, set size to 1 (ship's drawing size will be p.x + 1 = 2)
                } else if (state.p1ShipsAlive == 1 || state.p1ShipsAlive == 2) {
                    shipSize = 2;
                } else if (state.p1ShipsAlive == 3) {
                    shipSize = 3;
                } else if (state.p1ShipsAlive == 4) {
                    shipSize = 4;
                }
                int xEnd = xVal + shipSize;
                int yEnd = yVal;

                BSShip ship = new BSShip(xVal, xEnd, yVal, yEnd, 0); //p1's (AI's ship)

                if (xVal + shipSize > 9) { //bounds check right side of board, shift out-of-bounds ships to left
                    xVal = 9 - shipSize;
                    xEnd = xVal + shipSize;
                    ship = new BSShip(xVal, xEnd, yVal, yEnd, 0);
                }

                //check if all locations of ship are empty (no ship is there already)
                for (int i = xVal; i <= xEnd; i++) {
                    for (int j = yVal; j <= yEnd; j++) {
                        if (!state.validLocation(ship, i, j, 0)) {
                            Logger.log("invalidPlacementAI", "ship exists already at: " + xVal + " " + yVal);
                            this.receiveInfo(state); //recursively call receiveInfo on this state so AI places ship in a different spot
                        }
                    }
                }

                BSAddShip action = new BSAddShip(this, ship);

                game.sendAction(action);
            } else {
                Logger.log("fire", "ai sending fire");



                /**
                 External Citation
                 Date: 3 December 2019
                 Problem:
                 Resource: Dr. Triblehorn
                 Solution:
                 */

                if (lastFired != null) {
                    if (state.checkSpot(lastFired.first, lastFired.second, 1) == 3) {
                        //add all neighbors that aren't hits or misses to the toFire list
                        int type = state.checkSpot(lastFired.first + 1, lastFired.second, 1);
                        if (type != -1 && type != 3 && type != 4) {
                            toFire.add(new Pair<Integer, Integer>(lastFired.first + 1, lastFired.second));
                        }
                        type = state.checkSpot(lastFired.first, lastFired.second + 1, 1);
                        if (type != -1 && type != 3 && type != 4) {
                            toFire.add(new Pair<Integer, Integer>(lastFired.first, lastFired.second + 1));
                        }
                        type = state.checkSpot(lastFired.first - 1, lastFired.second, 1);
                        if (type != -1 && type != 3 && type != 4) {
                            toFire.add(new Pair<Integer, Integer>(lastFired.first - 1, lastFired.second));
                        }
                        type = state.checkSpot(lastFired.first, lastFired.second - 1, 1);
                        if (type != -1 && type != 3 && type != 4) {
                            toFire.add(new Pair<Integer, Integer>(lastFired.first, lastFired.second - 1));
                        }
                        lastFired = null;
                    }
                }

                if (toFire.isEmpty()) {
                    BSFire action = new BSFire(this, yVal, xVal);
                    lastFired = new Pair<>(yVal, xVal);
                    game.sendAction(action);

                } else {
                    Pair<Integer, Integer> loc = toFire.get(0);
                    toFire.remove(0);
                    BSFire action = new BSFire(this, loc.first, loc.second);
                    game.sendAction(action);

                }

            }
            }
        }
    }

