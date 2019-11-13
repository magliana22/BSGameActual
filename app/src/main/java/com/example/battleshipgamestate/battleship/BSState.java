package com.example.battleshipgamestate.battleship;

import com.example.battleshipgamestate.game.GameFramework.infoMessage.GameState;

import java.util.Random;

public class BSState extends GameState {



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

    //default constructor
    public BSState() {
        this.playerID = 0;
        this.p1TotalHits = 0;
        this.p2TotalHits = 0;
        this.p1ShipsAlive = 10;
        this.p1ShipsSunk = 0;
        this.p2ShipsAlive = 10;
        this.p2ShipsSunk = 0;
        this.phaseOfGame = "SetUp";
        this.p1Board = new BSLocation[10][10];
        this.p2Board = new BSLocation[10][10];

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                this.p1Board[row][col] = new BSLocation();
                this.p2Board[row][col] = new BSLocation();
            }
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

        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                this.p1Board[row][col] = original.p1Board[row][col];
                this.p2Board[row][col] = original.p2Board[row][col];
            }
        }


    }


    // gets which players turn it is
    public int getPlayerID() {
        return this.playerID;
    }

    //gets the player whose turn it is NOT
    public int getPlayerTarget(){
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
        //if (playerNum == this.getPlayerID()) {
        for (int row = ship.gety1(); row < ship.gety2(); row++) {
            for (int col = ship.getx1(); row < ship.getx2(); col++) {
                if (playerNum == 0) {
                    this.p1Board[row - 1][col - 1].setSpot(2);
                } else {
                    this.p2Board[row - 1][col - 1].setSpot(2);
                }
            }
        }
        return false;
    }

    //checks value of a location object at a coordinate in a player's board
    public int checkSpot(int x, int y, int playerNum) {
        if (this.playerID == playerNum && playerNum == 0) {
            if (this.p1Board[y][x].isWater == true) {
                return 1;
            } else if (this.p1Board[y][x].isShip == true) {
                return 2;
            } else if (this.p1Board[y][x].isHit == true) {
                return 3;
            } else if (this.p1Board[y][x].isMiss == true) {
                return 4;
            }
        } else if (this.playerID == playerNum && playerNum == 1) {
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

        // generate random values for starting x coords of ships
        int patrolboatX = rand.nextInt(max - min + 1) + min;
        int submarineX = rand.nextInt(max - min + 1) + min;
        int cruiserX = rand.nextInt(max3 - min + 1) + min;
        int destroyerX = rand.nextInt(max4 - min + 1) + min;
        int carrierX = rand.nextInt(max5 - min + 1) + min;

        int patrolboatXend = submarineX + 1;
        int submarineXend = submarineX + 1;
        int cruiserXend =  cruiserX + 2;
        int destroyerXend = destroyerX + 3;
        int carrierXend = carrierX + 4;

        int patrolboatY = rand.nextInt(max - min + 1) + min;
        int submarineY = rand.nextInt(max - min + 1) + min;
        int cruiserY = rand.nextInt(max - min + 1) + min;
        int destroyerY = rand.nextInt(max - min + 1) + min;
        int carrierY =rand.nextInt(max - min + 1) + min;

        int patrolboatYend = patrolboatY;
        int submarineYend = submarineY;
        int cruiserYend = cruiserY;
        int destroyerYend = destroyerY;
        int carrierYend = carrierY;

                //if (this.playerID == playerNum) {
        BSShip carrier = new BSShip(carrierX, carrierXend, carrierY, carrierYend, playerNum, 5);
        BSShip destroyer = new BSShip(destroyerX, destroyerXend, destroyerY, destroyerYend, playerNum, 4);
        BSShip cruiser = new BSShip(cruiserX, cruiserXend, cruiserY, cruiserYend, playerNum, 3);
        BSShip submarine = new BSShip(submarineX, submarineXend, submarineY, submarineYend, playerNum, 2);
        BSShip patrolboat = new BSShip(patrolboatX, patrolboatXend, patrolboatY, patrolboatYend, playerNum, 2);

        this.addShip(playerNum, carrier);
        this.addShip(playerNum, destroyer);
        this.addShip(playerNum, cruiser);
        this.addShip(playerNum, submarine);
        this.addShip(playerNum, patrolboat);

        return true;
    }


    @Override
    public String toString() {

        return "P1 Hits: " + this.p1TotalHits + " P2 Hits: " + this.p2TotalHits
                + " Player Turn:" + this.playerID + " P1 Ships Alive: " + this.p1ShipsAlive + " P2 Ships Alive: " + this.p2ShipsAlive
                + " P1 Ships Sunk: " + this.p1ShipsSunk + " P2 Ships Sunk: " + this.p2ShipsSunk + " Phase:" + this.phaseOfGame;


    }


    //fire method checks value of a location object at a coordinate in an opponent's board, changes water to miss and ship to hit
    public boolean fire(int y, int x) {

        boolean valid=false;

        if (this.getPlayerID() == 1) {
            BSLocation temp = new BSLocation(this.p1Board[y][x]);

            if (checkSpot(x, y, 0) == 3 || checkSpot(x, y, 0) == 4) {
                this.p1Board[y][x] = temp;
                valid=false;
            } else if (checkSpot(x, y, 0) == 2) {
                this.p2TotalHits += 1;
                temp.setSpot(3);
                this.p1Board[y][x] = temp;
                valid=true;
            } else if (checkSpot(x, y, 0) == 1) {
                temp.setSpot(4);
                this.p1Board[y][x] = temp;
                valid=true;
            }
        } else if (this.getPlayerID() == 0) {
            BSLocation temp = this.p2Board[y][x];
            if (checkSpot(x, y, 1) == 3 || checkSpot(x, y, 1) == 4) {
                this.p2Board[y][x] = temp;
                valid=false;
            } else if (checkSpot(x, y, 1) == 2) {
                this.p1TotalHits += 1;
                temp.setSpot(3);
                this.p2Board[y][x] = temp;
                valid=true;
            } else if (checkSpot(x, y, 1) == 1) {
                temp.setSpot(4);
                this.p2Board[y][x] = temp;
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
}
