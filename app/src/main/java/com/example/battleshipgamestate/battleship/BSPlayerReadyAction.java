package com.example.battleshipgamestate.battleship;


import com.example.battleshipgamestate.game.GameFramework.GamePlayer;
import com.example.battleshipgamestate.game.GameFramework.actionMessage.GameAction;

public class BSPlayerReadyAction extends GameAction {
    public BSPlayerReadyAction(GamePlayer player){
        super(player);
    }
}
