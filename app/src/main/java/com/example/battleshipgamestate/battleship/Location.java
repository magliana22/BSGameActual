package com.example.battleshipgamestate.battleship;

public class Location {

    private boolean isWater;
    private boolean isShip;
    private boolean isHit;
    private boolean isMiss;

    public Location(){
        this.isWater=true;
        this.isShip=false;
        this.isHit=false;
        this.isMiss=false;
    }

    public int checkSpot(Location location){
        if(location.isWater){
            return 1;
        }
        else if(location.isShip){
            return 2;
        }
        else if(location.isHit){
            return 3;
        }
        else if(location.isMiss){
            return 4;
        }
        else{
            return 0;
        }
    }


}
