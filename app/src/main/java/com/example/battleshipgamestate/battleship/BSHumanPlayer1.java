package com.example.battleshipgamestate.battleship;


import com.example.battleshipgamestate.battleship.BSState;
import com.example.battleshipgamestate.battleship.BSSurfaceView;
import com.example.battleshipgamestate.game.GameFramework.GameHumanPlayer;
import com.example.battleshipgamestate.game.GameFramework.GameMainActivity;
import com.example.battleshipgamestate.game.GameFramework.actionMessage.GameOverAckAction;
import com.example.battleshipgamestate.game.GameFramework.actionMessage.MyNameIsAction;
import com.example.battleshipgamestate.game.GameFramework.actionMessage.ReadyAction;
import com.example.battleshipgamestate.game.GameFramework.infoMessage.BindGameInfo;
import com.example.battleshipgamestate.game.GameFramework.infoMessage.GameInfo;
import com.example.battleshipgamestate.game.GameFramework.infoMessage.GameOverInfo;
import com.example.battleshipgamestate.game.GameFramework.infoMessage.IllegalMoveInfo;
import com.example.battleshipgamestate.game.GameFramework.infoMessage.NotYourTurnInfo;
import com.example.battleshipgamestate.game.GameFramework.infoMessage.StartGameInfo;
import com.example.battleshipgamestate.game.GameFramework.infoMessage.TimerInfo;
import com.example.battleshipgamestate.game.GameFramework.utilities.GameTimer;
import com.example.battleshipgamestate.game.GameFramework.utilities.Logger;
import com.example.battleshipgamestate.game.GameFramework.utilities.MessageBox;
import com.example.battleshipgamestate.game.GameFramework.utilities.Tickable;
import com.example.battleshipgamestate.R;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * class GameHumanPlayer
 *
 * is an abstract base class for a player that is controlled by a human. For any
 * particular game, a subclass should be created that can display the current
 * game state and responds to user commands.
 *
 * @author Steven R. Vegdahl
 * @author Andrew Nuxoll
 * @version July 2013
 *
 */
public class BSHumanPlayer1 extends GameHumanPlayer{
    
}