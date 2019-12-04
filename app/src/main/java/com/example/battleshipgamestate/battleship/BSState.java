package com.example.battleshipgamestate.battleship;

import com.example.battleshipgamestate.game.GameFramework.infoMessage.GameState;
import com.example.battleshipgamestate.game.GameFramework.utilities.Logger;

import java.io.Serializable;
import java.util.Random;

public class BSState extends GameState implements Serializable{

    public int p1TotalHits;
    public int p2TotalHits;
    public int playerID;
    public int p1ShipsAlive;
    public int p1ShipsSunk;
    public int p2ShipsAlive;
    public int p2ShipsSunk;
    public String phaseOfGame;

    public BSLocation[][] p1Board;
    public BSLocation[][] p2Board;

    public BSShip[] p1Ships;
    public BSShip[] p2Ships;

    //default constructor
    public BSState() {
        this.playerID = 0;
        this.p1TotalHits = 0;
        this.p2TotalHits = 0;
        this.p1ShipsAlive = 0;
        this.p1ShipsSunk = 0;
        this.p2ShipsAlive = 0;
        this.p2ShipsSunk = 0;
        this.phaseOfGame = "SetUp";
        this.p1Board = new BSLocation[10][10];
        this.p2Board = new BSLocation[10][10];
        this.p1Ships = new BSShip[5];
        this.p2Ships = new BSShip[5];

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                this.p1Board[row][col] = new BSLocation();
                this.p2Board[row][col] = new BSLocation();
            }
        }

        // RANDOMLY PLACE P2'S SHIPS
        int shipSize2 = 0; //variable for size of ship
        int shipNum2 = 0;
        for (int i = 0; i < 5; i++) {
            // set size of ship depending on order placed (go from smallest to largest)
            int xVal = (int) (10 * Math.random());
            int yVal = (int) (10 * Math.random());
            if (shipNum2 == 0) {
                shipSize2 = 1; //for first 2 ships, set size to 1 (ship's drawing size will be p.x + 1 = 2)
            } else if (shipNum2 == 1 || shipNum2 == 2) {
                shipSize2 = 2;
            } else if (shipNum2 == 3) {
                shipSize2 = 3;
            } else if (shipNum2 == 4) {
                shipSize2 = 4;
            }
            int xEnd = xVal + shipSize2;
            int yEnd = yVal;
            BSShip ship = new BSShip(xVal, xEnd, yVal, yEnd, 1); //p1's

            if (xVal + shipSize2 > 9) { //bounds check right side of board
                xEnd -= shipSize2;
                ship = new BSShip(xVal - shipSize2, xEnd, yVal, yEnd, 1);
            }
            p2Ships[i] = ship;
            shipNum2++;
        }
    }

    // Copy Constructor
    public BSState(BSState original) {
        this.playerID = original.playerID;
        this.p1TotalHits = original.p1TotalHits;
        this.p2TotalHits = original.p2TotalHits;
        this.p1ShipsAlive = original.p1ShipsAlive;
        this.p1ShipsSunk = original.p1ShipsSunk;
        this.p2ShipsAlive = original.p2ShipsAlive;
        this.p2ShipsSunk = original.p2ShipsSunk;
        this.phaseOfGame = original.phaseOfGame;
        this.p1Board = new BSLocation[10][10];
        this.p2Board = new BSLocation[10][10];
        this.p1Ships = new BSShip[5];
        this.p2Ships = new BSShip[5];

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                this.p1Board[row][col] = new BSLocation(original.p1Board[row][col]);
                this.p2Board[row][col] = new BSLocation(original.p2Board[row][col]);
            }
        }

        for (int i = 0; i < p1ShipsAlive; i++){
                this.p1Ships[i] = new BSShip(original.p1Ships[i]);
        }
        for (int i = 0; i < p2ShipsAlive; i++){
                this.p2Ships[i] = new BSShip(original.p2Ships[i]);
        }

    }

    public void updateShipLocations(){
        for (int i = 0; i < p1ShipsAlive; i++){
            for (int x = p1Ships[i].getx1(); x <= p1Ships[i].getx2(); x++){
                for (int y = p1Ships[i].gety1(); y <= p1Ships[i].gety2(); y++){
                    p1Board[x][y].setSpot(2);
                    Logger.log("zzzupdateShipLocations",""+x+" "+y);
                }
            }
        }
        for (int i = 0; i < p2ShipsAlive; i++){
            for (int x = p2Ships[i].getx1(); x <= p2Ships[i].getx2(); x++){
                for (int y = p2Ships[i].gety1(); y <= p2Ships[i].gety2(); y++){
                    p2Board[x][y].setSpot(2);
                }
            }
        }
    }

    // gets which players turn it is
    public int getPlayerID() {
        return this.playerID;
    }

    //changes phase of game to either setUp(1) or inPlay(2)
    public void setPhaseOfGame(int phase){
        if(phase==2){
            this.phaseOfGame="inPlay";
        }
        else if(phase==1){
            this.phaseOfGame="setUp";
        }
    }

    //gets phase of game String from state
    public String getPhaseOfGame(){
        return this.phaseOfGame;
    }

    //gets the player whose turn it is NOT
    public int getTargetPlayer(){
        int passivePlayer=3;
        if(this.playerID==0){
            passivePlayer= 1;
        }
        else if(this.playerID==1){
            passivePlayer=0;
        }
        return passivePlayer;
    }

    // sets current players turn
    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    //changes coordinates of a player's board to store ship values of a given ship
    public boolean addShip(int playerNum, BSShip ship) {
        //this does no checks to see if ships are in a valid location
        Logger.log("addShip","adding a ship");
        if(getPhaseOfGame() == "inPlay")
        {
            return false;
        }
        if (playerNum == 0 && p1ShipsAlive < 5){
            p1Ships[p1ShipsAlive++] = ship;
            updateShipLocations();
                setPlayerID(1);
        } else if (playerNum == 1 && p2ShipsAlive < 5){
            p2Ships[p2ShipsAlive++] = ship;
            updateShipLocations();
                setPlayerID(0);
        }

        if (p1ShipsAlive == 5 && p2ShipsAlive == 5){
            Logger.log("changePhase","set phase to inPlay");
            setPhaseOfGame(2);
        }
        return true;
    }

    //checks value of a location object at a coordinate in a player's board
    public int checkSpot(int x, int y, int playerNum) {
        if(x>=10||y>=10||x<0||y<0){
            return -1;
        }

        if (playerNum == 0) {
            if (this.p1Board[y][x].isWater == true) {
                return 1;
            } else if (this.p1Board[y][x].isShip == true) {
                return 2;
            } else if (this.p1Board[y][x].isHit == true) {
                return 3;
            } else if (this.p1Board[y][x].isMiss == true) {
                return 4;
            }
        } else if (playerNum == 1) {
            if (this.p2Board[y][x].isWater == true) {
                return 1;
            } else if (this.p2Board[y][x].isShip == true) {
                return 2;
            } else if (this.p2Board[y][x].isHit == true) {
                return 3;
            } else if (this.p2Board[y][x].isMiss == true) {
                return 4;
            }
        }
        return 0;

    }

    // changes the current players turn after a valid move
    public void changeTurn() {
        if (this.playerID == 0) {
            this.setPlayerID(1);
        } else {
            this.setPlayerID(0);
        }
    }

    //method that adds all the ships to a player, to make the main look a tad bit cleaner
    public boolean addAllShips(int playerNum) {
        int min = 0;
        int max = 9;
        int max3 = 8;
        int max4 = 7;
        int max5 = 6;
        Random rand = new Random(); //create new random object
        return true;
    }

    // toString method leftover from GameState HW (for testing purposes)
    @Override
    public String toString() {

        return "P1 Hits: " + this.p1TotalHits + " P2 Hits: " + this.p2TotalHits
                + " Player Turn:" + this.playerID + " P1 Ships Alive: " + this.p1ShipsAlive + " P2 Ships Alive: " + this.p2ShipsAlive
                + " P1 Ships Sunk: " + this.p1ShipsSunk + " P2 Ships Sunk: " + this.p2ShipsSunk + " Phase:" + this.phaseOfGame;


    }

    //fire method checks value of a location object at a coordinate in an opponent's board, changes water to miss and ship to hit
    public boolean fire(int y, int x) {
        if(getPhaseOfGame() == "setUp"){
            return false;
        }
        boolean valid=false;
        Logger.log("zzzfire",""+x+", "+y);
        if (this.getPlayerID() == 1) {
            BSLocation temp = new BSLocation(this.p1Board[y][x]);

            if (checkSpot(x, y, 0) == 3 || checkSpot(x, y, 0) == 4) {
                this.p1Board[y][x] = temp;
                Logger.log("Coordinate","spot is hit/miss");
                valid=false;
            } else if (checkSpot(x, y, 0) == 2) {
                this.p2TotalHits += 1;
                temp.setSpot(3);
                this.p1Board[y][x] = temp;
                Logger.log("spot","spot is ship");
                valid=true;
            } else if (checkSpot(x, y, 0) == 1) {
                temp.setSpot(4);
                this.p1Board[y][x] = temp;
                Logger.log("zzzspot2","spot is water "+x+" "+y);
                valid=true;
            }
        } else if (this.getPlayerID() == 0) {
            BSLocation temp = this.p2Board[y][x];
            if (checkSpot(x, y, 1) == 3 || checkSpot(x, y, 1) == 4) {
                this.p2Board[y][x] = temp;
                Logger.log("spot3","spot is hit/miss");
                valid=false;
            } else if (checkSpot(x, y, 1) == 2) {
                this.p1TotalHits += 1;
                temp.setSpot(3);
                this.p2Board[y][x] = temp;
                Logger.log("spot4","spot is ship");
                valid=true;
            } else if (checkSpot(x, y, 1) == 1) {
                temp.setSpot(4);
                this.p2Board[y][x] = temp;
                Logger.log("spot5","spot is water");
                valid=true;
            }
        }
        return valid;
    }

    //creates a String describing the value of a location object
    public String spotString(int x, int y, int playerNum) {
        String spot = new String("");
        if (this.playerID == playerNum && playerNum == 0)
            switch (checkSpot(x, y, playerNum)) {
                case 1:
                    spot = "Location " + y + "," + x + " is Water";
                    break;

                case 2:
                    spot = "Location " + y + "," + x + " is Ship";
                    break;

                case 3:
                    spot = "Location " + y + "," + x + " is Hit";
                    break;

                case 4:
                    spot = "Location " + y + "," + x + " is Miss";
                    break;
            }
        return spot;
    }

    /** validLocation method: checks if ship already exists in a specified location
     *
     * @param x x coordinate to check
     * @param y y coordinate to check
     * @return true if valid (ship can be placed), false if invalid (ship can't be placed)
     */
    public boolean validLocation(BSShip ship, int x, int y, int playerNum){
        if (ship.getx1() <= x && ship.getx2() >= x && ship.gety1() <= y && ship.gety2() >= y){
            if (playerNum == 0 && p1Board[x][y].isShip)
                return false; //if ship is not placeable
            if (playerNum == 1 && p2Board[x][y].isShip)
                return false; //if ship is not placeable
        }
        return true; //default return
    }

    //DO THIS!!!!!!!!!!!
    //make this return a boolean: true for a valid move and false for an invalid move
    //before setting orientation check that the end coordinates are less than 10 and greater than -1
    //also iterate through coordinates and make sure that no ships occupy the spaces that would be rotated into
    public void rotateShip(int playerId){
        if(playerId==0){
            //in setup, the number of ships alive corresponds to what ship is currently being placed due to a fixed order of ship placement
            //if 0 that means no ships are placed so the player is trying to rotate the first ship which is the patrol boat
            //orientation is either 1 for horizontal or 2 for vertical
            switch(p1ShipsAlive){
                case 0:
                    if(p1Ships[0].getOrientation()==1){//if horizontal
                        p1Ships[0].setyCoord2(p1Ships[0].gety1()+(p1Ships[0].getx2()-p1Ships[0].getx1()));
                        p1Ships[0].setxCoord2(p1Ships[0].getx1());
                        p1Ships[0].setOrientation(2);//set to vertical
                    }
                    else if(p1Ships[0].getOrientation()==2){//if vertical
                        p1Ships[0].setxCoord2(p1Ships[0].getx1()+(p1Ships[0].gety2()-p1Ships[0].gety1()));
                        p1Ships[0].setyCoord2(p1Ships[0].gety1());
                        p1Ships[0].setOrientation(2);//set to horizontal
                    }
                case 1:
                    if(p1Ships[1].getOrientation()==1){
                        p1Ships[1].setyCoord2(p1Ships[1].gety1()+(p1Ships[1].getx2()-p1Ships[1].getx1()));
                        p1Ships[1].setxCoord2(p1Ships[1].getx1());
                        p1Ships[1].setOrientation(2);
                    }
                    else if(p1Ships[1].getOrientation()==2){
                        p1Ships[1].setxCoord2(p1Ships[1].getx1()+(p1Ships[1].gety2()-p1Ships[1].gety1()));
                        p1Ships[1].setyCoord2(p1Ships[1].gety1());
                        p1Ships[1].setOrientation(1);
                    }
                case 2:
                    if(p1Ships[2].getOrientation()==1){
                        p1Ships[2].setyCoord2(p1Ships[2].gety1()+(p1Ships[2].getx2()-p1Ships[2].getx1()));
                        p1Ships[2].setxCoord2(p1Ships[2].getx1());
                        p1Ships[2].setOrientation(2);
                    }
                    else if(p1Ships[2].getOrientation()==2){
                        p1Ships[2].setxCoord2(p1Ships[2].getx1()+(p1Ships[2].gety2()-p1Ships[2].gety1()));
                        p1Ships[2].setyCoord2(p1Ships[2].gety1());
                        p1Ships[2].setOrientation(1);
                    }
                case 3:
                    if(p1Ships[3].getOrientation()==1){
                        p1Ships[3].setyCoord2(p1Ships[3].gety1()+(p1Ships[3].getx2()-p1Ships[3].getx1()));
                        p1Ships[3].setxCoord2(p1Ships[3].getx1());
                        p1Ships[3].setOrientation(2);
                    }
                    else if(p1Ships[3].getOrientation()==2){
                        p1Ships[3].setxCoord2(p1Ships[3].getx1()+(p1Ships[3].gety2()-p1Ships[3].gety1()));
                        p1Ships[3].setyCoord2(p1Ships[3].gety1());
                        p1Ships[3].setOrientation(1);

                    }
                case 4:
                    if(p1Ships[4].getOrientation()==1){
                        p1Ships[4].setyCoord2(p1Ships[4].gety1()+(p1Ships[4].getx2()-p1Ships[4].getx1()));
                        p1Ships[4].setxCoord2(p1Ships[4].getx1());
                        p1Ships[4].setOrientation(2);
                    }
                    else if(p1Ships[4].getOrientation()==2){
                        p1Ships[4].setxCoord2(p1Ships[4].getx1()+(p1Ships[4].gety2()-p1Ships[4].gety1()));
                        p1Ships[4].setyCoord2(p1Ships[4].gety1());
                        p1Ships[4].setOrientation(1);
                    }
            }
        }
        else if(playerId==1){
            switch(p2ShipsAlive){
                case 0:
                    if(p2Ships[0].getOrientation()==1){//if horizontal
                        p2Ships[0].setyCoord2(p2Ships[0].gety1()+(p2Ships[0].getx2()-p2Ships[0].getx1()));
                        p2Ships[0].setxCoord2(p2Ships[0].getx1());
                        p2Ships[0].setOrientation(2);//set to vertical
                    }
                    else if(p2Ships[0].getOrientation()==2){//if vertical
                        p2Ships[0].setxCoord2(p2Ships[0].getx1()+(p2Ships[0].gety2()-p2Ships[0].gety1()));
                        p2Ships[0].setyCoord2(p2Ships[0].gety1());
                        p2Ships[0].setOrientation(2);//set to horizontal
                    }
                case 1:
                    if(p2Ships[1].getOrientation()==1){
                        p2Ships[1].setyCoord2(p2Ships[1].gety1()+(p2Ships[1].getx2()-p2Ships[1].getx1()));
                        p2Ships[1].setxCoord2(p2Ships[1].getx1());
                        p2Ships[1].setOrientation(2);
                    }
                    else if(p2Ships[1].getOrientation()==2){
                        p2Ships[1].setxCoord2(p2Ships[1].getx1()+(p2Ships[1].gety2()-p2Ships[1].gety1()));
                        p2Ships[1].setyCoord2(p2Ships[1].gety1());
                        p2Ships[1].setOrientation(1);
                    }
                case 2:
                    if(p2Ships[2].getOrientation()==1){
                        p2Ships[2].setyCoord2(p2Ships[2].gety1()+(p2Ships[2].getx2()-p2Ships[2].getx1()));
                        p2Ships[2].setxCoord2(p2Ships[2].getx1());
                        p2Ships[2].setOrientation(2);
                    }
                    else if(p2Ships[2].getOrientation()==2){
                        p2Ships[2].setxCoord2(p2Ships[2].getx1()+(p2Ships[2].gety2()-p2Ships[2].gety1()));
                        p2Ships[2].setyCoord2(p2Ships[2].gety1());
                        p2Ships[2].setOrientation(1);
                    }
                case 3:
                    if(p2Ships[3].getOrientation()==1){
                        p2Ships[3].setyCoord2(p2Ships[3].gety1()+(p2Ships[3].getx2()-p2Ships[3].getx1()));
                        p2Ships[3].setxCoord2(p2Ships[3].getx1());
                        p2Ships[3].setOrientation(2);
                    }
                    else if(p2Ships[3].getOrientation()==2){
                        p2Ships[3].setxCoord2(p2Ships[3].getx1()+(p2Ships[3].gety2()-p2Ships[3].gety1()));
                        p2Ships[3].setyCoord2(p2Ships[3].gety1());
                        p2Ships[3].setOrientation(1);

                    }
                case 4:
                    if(p2Ships[4].getOrientation()==1){
                        p2Ships[4].setyCoord2(p2Ships[4].gety1()+(p2Ships[4].getx2()-p2Ships[4].getx1()));
                        p2Ships[4].setxCoord2(p2Ships[4].getx1());
                        p2Ships[4].setOrientation(2);
                    }
                    else if(p2Ships[4].getOrientation()==2){
                        p2Ships[4].setxCoord2(p2Ships[4].getx1()+(p2Ships[4].gety2()-p2Ships[4].gety1()));
                        p2Ships[4].setyCoord2(p2Ships[4].gety1());
                        p2Ships[4].setOrientation(1);
                    }
            }
        }
    }
}
