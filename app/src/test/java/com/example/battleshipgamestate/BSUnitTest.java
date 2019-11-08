package com.example.battleshipgamestate;

import com.example.battleshipgamestate.battleship.BSLocation;
import com.example.battleshipgamestate.battleship.BSState;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for BSState
 */
public class BSUnitTest {
    @Test
    public void checkSpot() throws Exception{

        BSState testState=new BSState();
        int spotVal=testState.checkSpot(1,1,testState.playerID);
        assertTrue(spotVal==1);
    }

    public void setSpot() throws Exception{
        BSState testState=new BSState();
        testState.p1Board[1][1].setSpot(2);
        assertTrue(testState.p1Board[1][1].isShip);
}
}