package com.example.battleshipgamestate.battleship;

import com.example.battleshipgamestate.R;
import com.example.battleshipgamestate.game.GameFramework.GameMainActivity;
import com.example.battleshipgamestate.game.GameFramework.GamePlayer;
import com.example.battleshipgamestate.game.GameFramework.LocalGame;
import com.example.battleshipgamestate.game.GameFramework.gameConfiguration.GameConfig;
import com.example.battleshipgamestate.game.GameFramework.gameConfiguration.GamePlayerType;

import java.util.ArrayList;


public class BSMainActivity extends GameMainActivity {

    public static final int PORT_NUMBER = 5213;

    @Override
    public GameConfig createDefaultConfig() {

        // Define the allowed player types
        ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

        // yellow-on-blue GUI
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
        // Create a game configuration class
        GameConfig defaultConfig = new GameConfig(playerTypes, 2, 2, "BattleShip", PORT_NUMBER);
        // Add the default players
        defaultConfig.addPlayer("Human", 0); // yellow-on-blue GUI
        defaultConfig.addPlayer("Computer 1", 1); // dumb computer player
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