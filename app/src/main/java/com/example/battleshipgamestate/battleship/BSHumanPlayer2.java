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
import android.widget.Button;

public class BSHumanPlayer2 extends GameHumanPlayer implements View.OnTouchListener, View.OnClickListener {

    //Tag for logging
    private static final String TAG = "BSHumanPlayer2";
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
    public BSHumanPlayer2(String name,int layoutId) {
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
            // if we do not have a BSState, ignore
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

        // remember our activity
        myActivity = activity;

        // Load the layout resource for the new configuration
        activity.setContentView(layoutId);

        // set the surfaceView instance variable
        surfaceView = (BSSurfaceView)myActivity.findViewById(R.id.surfaceView);
        //  Logger.log("set listener","OnTouch");
        surfaceView.setOnTouchListener(this);
        Button p2ReadyUp=myActivity.findViewById(R.id.p2_ready_button);
        p2ReadyUp.setOnClickListener(this);
        Button rotateButton=myActivity.findViewById(R.id.rotate_button);
        rotateButton.setOnClickListener(this);
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
        myActivity.setTitle("Battleship: "+allPlayerNames[0]+" vs. "+allPlayerNames[1]);
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
        // values are in the range 0..9)
        int x = (int) event.getX();
        int y = (int) event.getY();
        Point p = surfaceView.mapPixelToSquare(x, y);

        if (p == null) {
            surfaceView.flash(Color.RED, 50);
        } else {
            if (surfaceView.state.getPhaseOfGame() == "inPlay") {
                //if (surfaceView.state.getPhaseOfGame() == "inPlay") {
                BSFire action = new BSFire(this, p.y, p.x);
                Logger.log("onTouch", "Human player sending Fire ...");
                surfaceView.state.winCondition();
                game.sendAction(action);
                //surfaceView.invalidate();
            } else {
                //we are in setup phase
                int shipSize = 0; //variable for size of ship
                // set size of ship depending on order placed (go from smallest to largest)
                if (surfaceView.state.p2ShipsAlive == 0 || surfaceView.state.p2ShipsAlive == 1) {
                    shipSize = 1; //for first 2 ships, set size to 1 (ship's drawing size will be p.x + 1 = 2)
                } else if (surfaceView.state.p2ShipsAlive == 2) {
                    shipSize = 2;
                } else if (surfaceView.state.p2ShipsAlive == 3) {
                    shipSize = 3;
                } else if (surfaceView.state.p2ShipsAlive == 4) {
                    shipSize = 4;
                }
                int xEnd = p.x + shipSize;
                int yEnd = p.y;
                BSShip ship = new BSShip(p.x, xEnd, p.y, yEnd, 0, 1, shipSize + 1); //p2's

                if (p.x + shipSize > 9) { //bounds check right side of board
                    xEnd -= shipSize;
                    ship = new BSShip(p.x - shipSize, xEnd, p.y, yEnd, 0, 1, shipSize + 1);
                }
                BSAddShip action = new BSAddShip(this, ship);

                Logger.log("onTouch", "Human player sending placeShip action ...");
                surfaceView.state.progressGame();
                game.sendAction(action);

            }

        }
        // register that we have handled the event
        return true;

    }

    public void onClick(View v){
        if(v.getId()==R.id.p2_ready_button && surfaceView.state.phaseOfGame.equals("setUp")){
            Logger.log("Tag","p2 is ready");
            BSPlayerReadyAction action= new BSPlayerReadyAction(this);
            game.sendAction(action);
        }
        else if(v.getId()==R.id.rotate_button && surfaceView.state.phaseOfGame.equals("setUp")){
            BSRotateAction action= new BSRotateAction(this);
            game.sendAction(action);
        }
    }

}
