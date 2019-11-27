package com.example.battleshipgamestate.battleship;

import com.example.battleshipgamestate.R;
import com.example.battleshipgamestate.game.GameFramework.GameMainActivity;
import com.example.battleshipgamestate.game.GameFramework.GamePlayer;
import com.example.battleshipgamestate.game.GameFramework.LocalGame;
import com.example.battleshipgamestate.game.GameFramework.gameConfiguration.GameConfig;
import com.example.battleshipgamestate.game.GameFramework.gameConfiguration.GamePlayerType;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.util.ArrayList;


public class BSMainActivity extends GameMainActivity {


    /**
     External Citation
     Date: 1 November 2019
     Problem: Changing App Icon
     Resource:
     https://dev.to/sfarias051/how-to-create-adaptive-icons-for-android-using-android-studio-459h
     draw-a-graphic-to-a-surfaceView-using-native-android/
     Solution: I used this post to help draw the app icon and the image below
     https://static.thenounproject.com/png/9270-200.png
     */

    public static final int PORT_NUMBER = 5213;


    @Override
    public GameConfig createDefaultConfig() {

        // Define the allowed player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();


        // human player 1 (left hand grid)
        playerTypes.add(new GamePlayerType("Local Human Player (human)") {
            public GamePlayer createPlayer(String name) {
                return new BSHumanPlayer1(name, R.layout.activity_main);
            }
        });

        // human player 2 (right hand grid)
        playerTypes.add(new GamePlayerType("Local Human Player (human 2)"){
            public GamePlayer createPlayer(String name){
                return new BSHumanPlayer2(name,R.layout.activity_main);
            }
        });

        // dumb computer player
        playerTypes.add(new GamePlayerType("Computer Player (dumb)") {
            public GamePlayer createPlayer(String name) {
                return new BSComputerPlayer1(name);
            }
        });

        // smarter computer player
        playerTypes.add(new GamePlayerType("Computer Player (smart)") {
            public GamePlayer createPlayer(String name) {
                return new BSComputerPlayer2(name);
            }
        });

        // Create a game configuration class
        GameConfig defaultConfig = new GameConfig(playerTypes, 2, 2, "BattleShip", PORT_NUMBER);
        // Add the default players
        defaultConfig.addPlayer("Human", 0); // left hand grid
        defaultConfig.addPlayer("Computer 1", 1); // dumb computer player

        defaultConfig.addPlayer("Computer 2",2); // smart computer player
        // Set the initial information for the remote player
        defaultConfig.setRemoteData("Remote Player", "", 1); // red-on-yellow GUI
        //done!
        return defaultConfig;
    }

    @Override
    public LocalGame createLocalGame() {
        return new BSLocalGame();
    }

}