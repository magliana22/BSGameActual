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
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

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
public class BSHumanPlayer1 extends GameHumanPlayer implements View.OnTouchListener, View.OnClickListener {
    //Tag for logging
    private static final String TAG = "BSHumanPlayer1";
    // the current activity
    private Activity myActivity;

    // the surface view
    private BSSurfaceView surfaceView;

    // the ID for the layout to use
    private int layoutId;

    //buttons
    private Button myButton;


    public SoundPool soundPool;
    public int soundBoom;

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
        else if (!(info instanceof BSState)) {
            // if we do not have a BSState, ignore
        }
        else if(info instanceof BSState){
            BSState currentState= new BSState((BSState) info);
            Logger.log("state change","current state of surface has changed");
            surfaceView.setState(currentState);
            surfaceView.invalidate();
            Logger.debugLog(TAG, "surfaceView is redrawn");
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

        myButton = (Button)myActivity.findViewById(R.id.play_button);
        myButton.setOnClickListener(this);

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
        /**
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(1)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }else {
            soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        }
        soundBoom = soundPool.load(myActivity,R.raw.boom,1);
        */

        // ignore if not an "up" event
        if (event.getAction() != MotionEvent.ACTION_UP) return true;
            // get the x and y coordinates of the touch-location;
            // convert them to square coordinates (where both
            // values are in the range 0..9)
            int x = (int) event.getX();
            int y = (int) event.getY();
            Point p = surfaceView.mapPixelToSquare(x, y);
            //Logger.log("pointX", ""+x);
            //Logger.log("pointY", ""+y);
            //Logger.log("pointP",p.toString());
            // if the location did not map to a legal square, flash
            // the screen; otherwise, create and send an action to
            // the game
            if (p == null) {
                surfaceView.flash(Color.RED, 500);
            } else {
                if (surfaceView.state.getPhaseOfGame().equals("inPlay")) {
                    /**
                    int check1 = surfaceView.state.checkSpot(p.x,p.y,0);
                    int check2 = surfaceView.state.checkSpot(p.x,p.y,1);
                    if(check1==3&&check1!=-1&&check1!=1&&check1!=2&&check1!=4){
                        soundPool.play(soundBoom,1,1,0,0,1);
                    }
                    if(check2==3){
                        soundPool.play(soundBoom,1,1,0,0,1);
                    }
                    */
                    BSFire action = new BSFire(this, p.x, p.y);
                    Logger.log("onTouch", "Human player sending fireAction ...");

                    game.sendAction(action);

                    return true;
                } else {
                    //we are in setup phase
                    int shipSize = 0; //variable for size of ship
                    // set size of ship depending on order placed (go from smallest to largest)
                    if (surfaceView.state.p1ShipsAlive == 0 || surfaceView.state.p1ShipsAlive == 1) {
                        shipSize = 1; //for first 2 ships, set size to 1 (ship's drawing size will be p.x + 1 = 2)
                    } else if (surfaceView.state.p1ShipsAlive == 2) {
                        shipSize = 2;
                    } else if (surfaceView.state.p1ShipsAlive == 3) {
                        shipSize = 3;
                    } else if (surfaceView.state.p1ShipsAlive == 4) {
                        shipSize = 4;
                    }
                    int xEnd = p.x + shipSize;
                    int yEnd = p.y;
                    BSShip ship = new BSShip(p.x, xEnd, p.y, yEnd, this.playerNum); //p1's

                    if (p.x + shipSize > 9) { //bounds check right side of board, shift out-of-bounds ships to left
                        p.x = 9 - shipSize;
                        xEnd = p.x + shipSize;
                        ship = new BSShip(p.x, xEnd, p.y, yEnd, this.playerNum);
                    }

                    //check if all locations of ship are empty (no ship is there already)
                    for (int i = p.x; i <= xEnd; i++){
                        for (int j = p.y; j <= yEnd; j++){
                            if (!surfaceView.state.validLocation(ship,i, j, 0)){
                                Logger.log("invalidPlacement","ship is already there");
                                return false; //if not empty, return false
                            }
                        }
                    }

                    BSAddShip action = new BSAddShip(this, ship);

                    Logger.log("onTouch", "Human player sending addShipAction ...");
                    game.sendAction(action);
                    return true;

                }
            }
        // register that we have handled the event
        return true;

    }

    @Override
    public void onClick(View v) {
        if(surfaceView.findViewById(R.id.play_button).equals(v.getId())){
            Logger.log("play", "setting phase to play");
            surfaceView.state.setPhaseOfGame(2); //set to play phase when button is pressed.
        }
        else if(surfaceView.findViewById(R.id.rotate_button).equals(v.getId())) {
            surfaceView.state.rotateShip(this.playerNum);
        }
    }
}