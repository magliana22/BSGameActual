package com.example.battleshipgamestate.battleship;

import android.media.MediaPlayer;

import com.example.battleshipgamestate.R;
import com.example.battleshipgamestate.game.GameFramework.GameMainActivity;
import com.example.battleshipgamestate.game.GameFramework.GamePlayer;
import com.example.battleshipgamestate.game.GameFramework.LocalGame;
import com.example.battleshipgamestate.game.GameFramework.gameConfiguration.GameConfig;
import com.example.battleshipgamestate.game.GameFramework.gameConfiguration.GamePlayerType;

import java.util.ArrayList;


public class BSMainActivity extends GameMainActivity {

    public static final int PORT_NUMBER = 5213;
    public MediaPlayer mediaPlayer;

    @Override
    public GameConfig createDefaultConfig() {

        // Define the allowed player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

        // GUI
        playerTypes.add(new GamePlayerType("Local Human Player (human)") {
            public GamePlayer createPlayer(String name) {
                return new BSHumanPlayer1(name, R.layout.activity_main);
            }
        });

        // dumb computer player
        playerTypes.add(new GamePlayerType("Computer Player (dumb)") {
            public GamePlayer createPlayer(String name) {
                return new BSComputerPlayer1(name);
            }
        });

        // smart computer player
        playerTypes.add(new GamePlayerType("Computer Player (smart)") {
            public GamePlayer createPlayer(String name) {
                return new BSComputerPlayer2(name);
            }
        });

        // Create a game configuration class
        GameConfig defaultConfig = new GameConfig(playerTypes, 2, 2, "BattleShip", PORT_NUMBER);
        // Add the default players
        defaultConfig.addPlayer("Human", 0); // GUI
        defaultConfig.addPlayer("Computer 1", 1); // dumb computer player
        defaultConfig.addPlayer("Computer 2",2); // smart computer player
        // Set the initial information for the remote player
        defaultConfig.setRemoteData("Remote Player", "", 1); // remote player GUI
        //done!
        return defaultConfig;
    }

    @Override
    public LocalGame createLocalGame() {

        /**
         External Citation
         Date: 5 December 2019
         Problem: Needed to loop battleship themesong
         Resource: https://stackoverflow.com/questions/9461270/media-player-looping-android
         Solution: added the mediaPlayer.setLooping(true); line
         */


        mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.battleship);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
        return new BSLocalGame();
    }
}