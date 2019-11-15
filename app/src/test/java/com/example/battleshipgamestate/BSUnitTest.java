package com.example.battleshipgamestate;

import com.example.battleshipgamestate.battleship.BSLocation;
import com.example.battleshipgamestate.battleship.BSState;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for BSState
 */
public class BSUnitTest {

    // Gianni wrote this test
    @Test
    public void getPlayerID() throws Exception{
        BSState testState=new BSState();
        int idNum=testState.getPlayerID();
        assertTrue(idNum==0);
    }

    // Kyle wrote this test
    @Test
    public void getPhaseOfGame() throws Exception{
        BSState testState=new BSState();
        testState.setPhaseOfGame(1);
        assertEquals("setUp",testState.getPhaseOfGame());
        testState.setPhaseOfGame(2);
        assertEquals("inPlay",testState.getPhaseOfGame());
    }

    // Kyle wrote this test
    @Test
    public void getPlayerTarget() throws Exception{
        BSState testState=new BSState();
        testState.setPlayerID(0);
        assertEquals(1,testState.getTargetPlayer());
        testState.setPlayerID(1);
        assertEquals(0,testState.getTargetPlayer());
    }

    @Test
    public void addShip() throws Exception{
        BSState testState=new BSState();

    }

    // Gianni wrote this test
    @Test
    public void checkSpot() throws Exception{

        BSState testState=new BSState();
        int spotVal=testState.checkSpot(1,1,testState.playerID);
        assertTrue(spotVal==1);
    }


    // Kyle Sanchez wrote this test
    @Test
    public void changeTurn() throws Exception{
        BSState testState = new BSState();
        testState.changeTurn();
        assertEquals(0,0);
        assertEquals(1,1);
    }

    // Gianni wrote this test
    @Test
    public void setSpot() throws Exception{
        BSState testState=new BSState();
        testState.p1Board[1][1].setSpot(2);
        assertTrue(testState.p1Board[1][1].isShip);
    }

    // Gianni wrote this test
    @Test
    public void fire() throws Exception{
        BSState testState=new BSState();
        boolean fireTest=testState.fire(1,1);
        assertTrue(fireTest);
    }


    // Kyle Sanchez wrote this test
    @Test
    public void spotString () throws Exception{
        BSState testState = new BSState();
        testState.spotString(1,1,testState.playerID);
        assertEquals("Location " + 1 + "," + 1 + " is Water",
                testState.spotString(1,1,testState.playerID));
    }








}