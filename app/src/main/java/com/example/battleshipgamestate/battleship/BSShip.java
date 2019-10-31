package com.example.battleshipgamestate.battleship;

public class BSShip {
    private int xCoord1; //starting x coordinate for ship
    private int yCoord1; //starting y coordinate for ship
    private int xCoord2; //ending  coordinate for ship
    private int yCoord2; //ending y coordinate for ship
    private int Owner;

    public BSShip(int xLocationStart, int xLocationEnd, int yLocationStart, int yLocationEnd, int idOfOwner, int shipSize) {
        this.xCoord1 = xLocationStart;
        this.yCoord1 = yLocationStart;
        this.xCoord2 = xLocationEnd;
        this.yCoord2 = yLocationEnd;
        this.Owner = idOfOwner;
    }


    public int getx1() {
        return this.xCoord1;
    }

    public int getx2() {
        return this.xCoord2;
    }

    public int gety1() {
        return this.yCoord1;
    }

    public int gety2() {
        return this.yCoord2;
    }
}
