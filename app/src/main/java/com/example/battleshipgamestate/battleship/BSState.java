package com.example.battleshipgamestate.battleship;

public class BSState {

    private int p1TotalHits;
    private int p2TotalHits;
    private int playerTurn;
    private int shipsAlive;
    private int shipsSunk;
    private int playerNum;
    private int boatHealth;
    private boolean isHit;
    private String phaseOfGame;
    private String[][] shipLocations;
    private boolean isVisible;
    private String[][] shotLocations;
    private int shipType;
    private String[][] board;
    //private GamePlayer player1;
    //private GamePlayer player2;


    public BSState() {
        this.playerTurn=1;
        this.p1TotalHits=0;
        this.p2TotalHits=0;
        this.shipsAlive=10;
        this.shipsSunk=0;
        this.isHit=false;
        this.phaseOfGame="SetUp";
        this.shotLocations=null;
        this.shipLocations=null;
        this.shipType=1;
        this.board=new String[10][20];
        //this.player1=new HumanPlayer;
        //this.player2=new ComputerPlayer;

    }

    public boolean rotate(int playerID, int shipType, String xCoord, String yCoord, BSState gameState){
        boolean valid=false;
        if(playerID==gameState.getPlayerTurn()){
            switch(shipType){
                case 1: //code
                    valid=true;
                    break;

                case 2: //code
                    valid=true;
                    break;

                case 3: //code
                    valid=true;
                    break;

                case 4: //code
                    valid=true;
                    break;
                case 5: //code
                    valid=true;
                    break;

                default: //code
                    valid=false;
                    break;
            }
        }
        return valid;
    }


    public int getPlayerTurn() {
        return this.playerTurn;
    }

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }


}
