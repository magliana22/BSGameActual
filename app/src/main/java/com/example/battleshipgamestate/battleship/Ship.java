package com.example.battleshipgamestate.battleship;

public class Ship {

   private int shipHealth;
   private int shipHits;
   private boolean isSunk;
  private int xCoord;
  private int yCoord;
  private int Owner;

   public Ship(int xLocation, int yLocation, int idOfOwner, int shipSize){
       this.xCoord=xLocation;
       this.yCoord=yLocation;
       this.Owner=idOfOwner;
       this.shipHealth=shipSize;
       this.isSunk=false;
   }
}
