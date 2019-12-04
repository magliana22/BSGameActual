package com.example.battleshipgamestate.battleship;

import java.io.Serializable;

public class BSLocation implements Serializable {
    private static final long serialVersionUID = -5109179064333136954L;
    public boolean isWater;
    public boolean isShip;
    public boolean isHit;
    public boolean isMiss;

    public BSState myGameState;

    public BSLocation() {
        this.isWater = true;
        this.isShip = false;
        this.isHit = false;
        this.isMiss = false;
    }

    public BSLocation(BSLocation original) {
        this.isWater = original.isWater;
        this.isShip = original.isShip;
        this.isHit = original.isHit;
        this.isMiss = original.isMiss;
    }

    /**
     * to be called on a location object residing in gameState location array.
     * Example: gameState.p1Board[][].setSpot(2) will set isShip boolean true and set isMiss, isHit, and isWater false
     * When the input is set true, the rest will be false so checkspot can later determine what to tell GUI to do
     * isWater is by default true and will never need to be set again
     */
    public void setSpot(int spotType) {
        if (spotType == 2) {
            this.isShip = true;

            this.isHit = false;
            this.isMiss = false;
            this.isWater = false;
        }
        if (spotType == 3) {
            this.isHit = true;

            this.isShip = false;
            this.isWater = false;
            this.isMiss = false;
        }
        if (spotType == 4) {
            this.isMiss = true;

            this.isShip = false;
            this.isHit = false;
            this.isWater = false;
        }
    }


}
