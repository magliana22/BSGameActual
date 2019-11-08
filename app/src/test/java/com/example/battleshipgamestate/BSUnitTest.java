package com.example.battleshipgamestate;

import com.example.battleshipgamestate.battleship.BSLocation;
import com.example.battleshipgamestate.battleship.BSState;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class BSUnitTest {
    @Test
    public void checkSpot() throws Exception{

        BSState testState=new BSState();
        testState.p1Board[1][1].setSpot(2);
        int spotVal=testState.checkSpot(1,1,testState.playerID);
        assertTrue(spotVal==1);
    }

    public void changeTurn() throws Exception{
        BSState testState = new BSState();
        testState.changeTurn();
        assertEquals(0,1);
        assertEquals(1,0);
    }

    public void spotString () throws Exception {
        BSState testState = new BSState();
        testState.spotString(1,1,testState.playerID);
        assertEquals("Location " + 1 + "," + 1 + " is Water",
                testState.spotString(1,1,testState.playerID));
    }
}