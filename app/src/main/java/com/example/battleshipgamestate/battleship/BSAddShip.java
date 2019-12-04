package com.example.battleshipgamestate.battleship;

import com.example.battleshipgamestate.game.GameFramework.GamePlayer;
import com.example.battleshipgamestate.game.GameFramework.actionMessage.GameAction;

import java.io.Serializable;

public class BSAddShip extends GameAction implements Serializable{
    private static final long serialVersionUID = -5109179064333136954L;
    private BSShip ship;

    public BSAddShip(GamePlayer player, BSShip ship){
        super(player);
        this.ship = ship;
    }
    public BSShip getShip(){return ship;}
}
