package com.example.battleshipgamestate.battleship;

import com.example.battleshipgamestate.game.GameFramework.GameHumanPlayer;
import com.example.battleshipgamestate.game.GameFramework.GameMainActivity;
import com.example.battleshipgamestate.game.GameFramework.infoMessage.GameInfo;
import com.example.battleshipgamestate.game.GameFramework.infoMessage.IllegalMoveInfo;
import com.example.battleshipgamestate.game.GameFramework.infoMessage.NotYourTurnInfo;
import com.example.battleshipgamestate.game.GameFramework.utilities.Logger;
import com.example.battleshipgamestate.R;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
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
public class BSHumanPlayer1 extends GameHumanPlayer implements View.OnTouchListener {
    //Tag for logging
    private static final String TAG = "BSHumanPlayer1";
    // the current activity
    private Activity myActivity;

    // the surface view
    private BSSurfaceView surfaceView;

    // the ID for the layout to use
    private int layoutId;

    /**
     * constructor
     *
     * @param name
     * 		the player's name
     * @param layoutId
     *      the id of the layout to use
     */
    public BSHumanPlayer1(String name, int layoutId) {
        super(name);
        this.layoutId = layoutId;
    }

    /**
     * Callback method, called when player gets a message
     *
     * @param info
     * 		the message
     */
    @Override
    public void receiveInfo(GameInfo info) {

        if (surfaceView == null) return;

        if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
            // if the move was out of turn or otherwise illegal, flash the screen
            surfaceView.flash(Color.RED, 50);
        }
        else if (!(info instanceof BSState))
            // if we do not have a TTTState, ignore
            return;
        else {
            surfaceView.setState((BSState)info);
            surfaceView.invalidate();
            Logger.debugLog(TAG, "receiving");
        }
    }

    /**
     * sets the current player as the activity's GUI
     */
    public void setAsGui(GameMainActivity activity) {

        // remember our activitiy
        myActivity = activity;

        // Load the layout resource for the new configuration
        activity.setContentView(layoutId);

        // set the surfaceView instance variable
        surfaceView = (BSSurfaceView)myActivity.findViewById(R.id.surfaceView);
        Logger.log("set listener","OnTouch");
        surfaceView.setOnTouchListener(this);
    }

    /**
     * returns the GUI's top view
     *
     * @return
     * 		the GUI's top view
     */
    @Override
    public View getTopView() {
        return myActivity.findViewById(R.id.top_gui_layout);
    }

    /**
     * perform any initialization that needs to be done after the player
     * knows what their game-position and opponents' names are.
     */
    protected void initAfterReady() {
        myActivity.setTitle("Tic-Tac-Toe: "+allPlayerNames[0]+" vs. "+allPlayerNames[1]);
    }

    /**
     * callback method when the screen it touched. We're
     * looking for a screen touch (which we'll detect on
     * the "up" movement" onto a tic-tac-tie square
     *
     * @param event
     * 		the motion event that was detected
     */
    public boolean onTouch(View v, MotionEvent event) {
        // ignore if not an "up" event
        if (event.getAction() != MotionEvent.ACTION_UP) return true;
        // get the x and y coordinates of the touch-location;
        // convert them to square coordinates (where both
        // values are in the range 0..2)
        int x = (int) event.getX();
        int y = (int) event.getY();
        Point p = surfaceView.mapPixelToSquare(x, y);

        // if the location did not map to a legal square, flash
        // the screen; otherwise, create and send an action to
        // the game
        if (p == null) {
            surfaceView.flash(Color.RED, 50);
        } else {
            BSMoveAction action = new BSMoveAction(this, p.y, p.x);
            Logger.log("onTouch", "Human player sending TTTMA ...");
            game.sendAction(action);
            surfaceView.invalidate();
        }

        // register that we have handled the event
        return true;

    }


}