package com.example.battleshipgamestate.battleship;

import java.io.Serializable;

public class BSShip implements Serializable{
    private static final long serialVersionUID = -5109179064333136954L;
    private int xCoord1; //starting x coordinate for ship
    private int yCoord1; //starting y coordinate for ship
    private int xCoord2; //ending x coordinate for ship
    private int yCoord2; //ending y coordinate for ship
    private int Owner; //playerId of owner
    private int orientation;//1 is horizontal, 2 is vertical

    public BSShip(int xLocationStart, int xLocationEnd, int yLocationStart, int yLocationEnd, int idOfOwner) {
        this.xCoord1 = xLocationStart;
        this.yCoord1 = yLocationStart;
        this.xCoord2 = xLocationEnd;
        this.yCoord2 = yLocationEnd;
        this.Owner = idOfOwner;
        this.orientation=1;
    }

    public BSShip(BSShip original){
        this.xCoord1 = original.xCoord1;
        this.yCoord1 = original.yCoord1;
        this.xCoord2 = original.xCoord2;
        this.yCoord2 = original.yCoord2;
        this.Owner = original.Owner;
        this.orientation=original.orientation;

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

    public void setxCoord1(int newX1){this.xCoord1=newX1;}

    public void setyCoord1(int newY1){this.yCoord1=newY1;}

    public void setyCoord2(int newY2){this.yCoord2=newY2;}

    public void setxCoord2(int newX2){this.xCoord2=newX2;}

    public int getOrientation(){return this.orientation;}

    public void setOrientation(int orientation){this.orientation=orientation;}
}
