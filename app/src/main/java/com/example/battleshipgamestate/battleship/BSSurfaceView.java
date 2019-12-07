package com.example.battleshipgamestate.battleship;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


import com.example.battleshipgamestate.R;
import com.example.battleshipgamestate.game.GameFramework.utilities.FlashSurfaceView;
import com.example.battleshipgamestate.game.GameFramework.utilities.Logger;

public class BSSurfaceView extends FlashSurfaceView {

    final Paint boardPaint = new Paint();
    final Paint dividerPaint = new Paint();
    final Paint waterPaint = new Paint();
    final Paint shipPaint = new Paint();
    final Paint hitPaint = new Paint();
    final Paint missPaint = new Paint();

    /**
     * Declare/initialize variables for grid here
     **/
    public int num_row = 10; //number of rows in a board
    public int num_col = 10; //number of columns in a board
    public float width; //width of canvas
    public float height; //height of canvas
    public float left_margin; //space between left edge and start of 1st board
    public float right_margin; //space between right edge and end of 2nd board
    public float top_margin; //space between top edge and top of boards
    public float bottom_margin; //space between bottom edge and bottom of boards
    public float cell_width; //width of 1 cell
    public float cell_height; //height of 1 cell
    public float[] xBoard1start = new float[10]; //x value for start of p1's cells
    public float[] xBoard1end = new float[10];
    public float[] yBoard1start = new float[10];
    public float[] yBoard1end = new float[10];
    public float[] xBoard2start = new float[10];
    public float[] xBoard2end = new float[10];
    public float[] yBoard2start = new float[10];
    public float[] yBoard2end = new float[10];

    private boolean cheatmode = false;

    public SoundPool soundPool;
    public int soundBoom;
    public int soundTheme;
    public MediaPlayer mediaPlayer;

    //background image variables
    private SurfaceHolder holder;
    private Bitmap bmp;
    private Bitmap ship1; //2cell ship
    private Bitmap ship2; //2cell ship
    private Bitmap ship3; //3cell ship
    private Bitmap ship4; //4cell ship
    private Bitmap ship5; //55cell ship

    public BSSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setWillNotDraw(false); //enable drawing

        //setBackgroundColor(Color.rgb(32, 32, 32)); //set background to selected value

        boardPaint.setColor(Color.WHITE); //set color for board to black

        boardPaint.setStyle(Paint.Style.STROKE); //set so boardPaint draws lines, not filled

        boardPaint.setStrokeWidth(5); //set board line thickness

        boardPaint.setAlpha(150); //set board alpha value

        dividerPaint.setStrokeWidth(8); //set divider thickness

        waterPaint.setColor(Color.rgb(0, 255, 255)); //set water color to teal

        shipPaint.setColor(Color.GRAY); //set ship color to gray

        hitPaint.setColor(Color.RED); //set hit color to red

        hitPaint.setStrokeWidth(7); //set hit X thickness

        missPaint.setColor(Color.argb(125, 224, 224, 224)); //set miss color to shade of white

        /**
         External Citation
         Date: 20 October 2019
         Problem: Drawing images to the surfaceView
         Resource:
         https://www.thepolyglotdeveloper.com/2015/05/
         draw-a-graphic-to-a-surfaceView-using-native-android/
         Solution: I used this post to help draw images on the surfaceView
         */

        this.bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ocean_background);
        holder = getHolder();
        holder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Canvas canvas = holder.lockCanvas();
                if (canvas != null) {
                    draw(canvas);
                    holder.unlockCanvasAndPost(canvas);
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

        });

        this.ship1 = BitmapFactory.decodeResource(getResources(), R.drawable.battleship1); //2 tile sized ship
        this.ship2 = BitmapFactory.decodeResource(getResources(), R.drawable.battleship2); //2 tile sized ship
        this.ship3 = BitmapFactory.decodeResource(getResources(), R.drawable.battleship5); //cruiser (3 tiles)
        this.ship4 = BitmapFactory.decodeResource(getResources(), R.drawable.battleship4); //destroyer (4 tiles)
        this.ship5 = BitmapFactory.decodeResource(getResources(), R.drawable.battleship3); //carrier (5 tiles)

    }

    protected BSState state; //local state variable

    /**
     * setState method: sets the local state variable
     *
     * @param theState state to reference for setting local state
     */
    public void setState(BSState theState) {
        this.state = theState;
    }

    /**
     * public method onDraw will draw the GUI
     *
     * @param canvas canvas to draw on
     */
    public void onDraw(Canvas canvas) {

        if (state == null) {
            Logger.log("NullState", "state is null");
            return;
        } else {
            Logger.log("State is good", "state is not null");
            Logger.log("onDraw", "about to draw");
            width = getWidth(); //get width of canvas
            height = getHeight(); //get height of canvas
            left_margin = width / 5; //space between left edge and start of 1st board
            right_margin = width - (width / 5); //space between right edge and end of 2nd board
            top_margin = height / 5; //space between top edge and top of boards
            bottom_margin = height - (height / 5); //space between bottom edge and bottom of boards
            cell_width = width / 35; //width of 1 cell
            cell_height = height / 17; //height of 1 cell

            RectF newRect = new RectF(0, 0, width, height); //rectangle for background

            canvas.drawBitmap(this.bmp, null, newRect, null); //draw background

            //draw grid boards
            drawBoard(canvas);


            RectF[] shipRectP1 = new RectF[5]; //create array of ships for p1
            for (int i = 0; i < state.p1ShipsAlive; i++) { //create ships for the array
                BSShip theShip = state.p1Ships[i];
                shipRectP1[i] = new RectF(left_margin + (cell_width * theShip.getx1()), top_margin + (cell_height * theShip.gety1()),
                        left_margin + (cell_width * (theShip.getx2() + 1)), top_margin + (cell_height * (1 + theShip.gety2())));
            }

            RectF shipRectP2[] = new RectF[5];
            for (int i = 0; i < state.p2ShipsAlive; i++) {
                BSShip theShip = state.p2Ships[i];
                shipRectP2[i] = new RectF(left_margin + (11 * cell_width) + (cell_width * theShip.getx1()), top_margin + (cell_height * theShip.gety1()),
                        left_margin + (11 * cell_width) + (cell_width * (theShip.getx2() + 1)), top_margin + (cell_height * (1 + theShip.gety2())));
            }

            // draw ships
            if (shipRectP1[0] != null) {
                canvas.drawBitmap(this.ship1, null, shipRectP1[0], null); //draw ship
            }
            if (shipRectP1[1] != null) {
                canvas.drawBitmap(this.ship2, null, shipRectP1[1], null); //draw ship
            }
            if (shipRectP1[2] != null) {
                canvas.drawBitmap(this.ship3, null, shipRectP1[2], null); //draw ship
            }
            if (shipRectP1[3] != null) {
                canvas.drawBitmap(this.ship4, null, shipRectP1[3], null); //draw ship
            }
            if (shipRectP1[4] != null) {
                canvas.drawBitmap(this.ship5, null, shipRectP1[4], null); //draw ship
            }
            if (cheatmode) {

                if (shipRectP2[0] != null) {
                    canvas.drawBitmap(this.ship1, null, shipRectP2[0], null); //draw ship
                }
                if (shipRectP2[1] != null) {
                    canvas.drawBitmap(this.ship2, null, shipRectP2[1], null); //draw ship
                }
                if (shipRectP2[2] != null) {
                    canvas.drawBitmap(this.ship3, null, shipRectP2[2], null); //draw ship
                }
                if (shipRectP2[3] != null) {
                    canvas.drawBitmap(this.ship4, null, shipRectP2[3], null); //draw ship
                }
                if (shipRectP2[4] != null) {
                    canvas.drawBitmap(this.ship5, null, shipRectP2[4], null); //draw ship
                }
            }



            // for each square that has a hit or miss, draw it on the appropriate place on the canvas
            for (int row = 0; row < num_row; row++) {
                for (int col = 0; col < num_col; col++) {
                    int result = state.checkSpot(row, col, 0);
                    int result2 = state.checkSpot(row, col, 1);
                    // player1's board
                    if (result == 3) { //if square is a hit
                        drawHit(canvas, row, col);
                    }
                    if (result == 4) { //if square is a miss
                        drawMiss(canvas, row, col);
                    }
                    // player2's board
                    if (result2 == 3) { //if square is a hit
                        drawHit(canvas, row, (num_col + 1) + col);
                    }
                    if (result2 == 4) { //if square is a miss
                        drawMiss(canvas, row, (num_col + 1) + col);
                    }
                }
            }

        }
    }




    /**
     * drawBoard method: draws the playing boards on the specified canvas
     *
     * @param canvas referenced canvas to draw on
     **/
    public void drawBoard(Canvas canvas) {

        for (int i = 0; i < num_row; i++) {
            for (int j = 0; j < num_col; j++) {
                drawCell(canvas, i, j); //draw left board (player 1)
                drawCell(canvas, i, (num_col + 1) + j); //draw right board (player 2)
            }
        }

    }

    /**
     * drawCell method: draws a single cell of the playing board
     *
     * @param canvas canvas to draw on
     * @param row    row of board to draw the cell
     * @param col    column of board to draw the cell
     */
    public void drawCell(Canvas canvas, float row, float col) {

        float cell_left = left_margin + (col * cell_width); //left of cell
        float cell_right = cell_left + cell_width; //right of cell is 1 cell_width away from left of cell
        float cell_top = top_margin + (row * cell_height); //top of cell
        float cell_bottom = cell_top + cell_height; //bottom of cell is 1 cell_height away from top of cell
        RectF myCell = new RectF(cell_left, cell_top, cell_right, cell_bottom); //cell to be drawn
        canvas.drawRect(myCell, boardPaint); //draw the cell

    }

    /**
     * drawHit method: draws an x where a player hits a ship
     *
     * @param canvas the canvas referenced to draw on
     * @param row    the row the hit will be drawn on
     * @param col    the column the hit will be drawn on
     */
    public void drawHit(Canvas canvas, float row, float col) {
        float cell_left = left_margin + (col * cell_width); //left of cell
        float cell_right = cell_left + cell_width; //right of cell is 1 cell_width away from left of cell
        float cell_top = top_margin + (row * cell_height); //top of cell
        float cell_bottom = cell_top + cell_height; //bottom of cell is 1 cell_height away from top of cell

        //draw the X
        canvas.drawLine(cell_left, cell_top, cell_right, cell_bottom, hitPaint); //line from top left to bottom right of cell
        canvas.drawLine(cell_left, cell_bottom, cell_right, cell_top, hitPaint); //line from bottom left to top right of cell


    }

    /**
     * drawMiss method: draws an o where a player misses
     *
     * @param canvas canvas reference to draw on
     * @param row    row to draw miss on
     * @param col    col to draw miss on
     */
    public void drawMiss(Canvas canvas, float row, float col) {
        float cell_left = left_margin + (col * cell_width); //left of cell
        float cell_right = cell_left + cell_width; //right of cell is 1 cell_width away from left of cell
        float cell_top = top_margin + (row * cell_height); //top of cell
        float cell_bottom = cell_top + cell_height; //bottom of cell is 1 cell_height away from top of cell
        RectF myOval = new RectF(cell_left, cell_top, cell_right, cell_bottom); //circle to be drawn
        canvas.drawOval(myOval, missPaint);
    }

    /**
     * mapPixelToSquare method: converts a touch into a square on the board
     *
     * @param x x coordinate where screen is touched
     * @param y y coordinate where screen is touched
     */
    public Point mapPixelToSquare(int x, int y) {

        for (int row = 0; row < num_row; row++) {
            for (int col = 0; col < num_col + 11; col++) {
                float left = left_margin + (col * cell_width); // use this value of left for first board
                //float left = left_margin + (j * cell_width) + (11*cell_width); //left of cell (right board starts at col 11)
                float right = left + cell_width; //right of cell is 1 cell_width away from left of cell
                float top = top_margin + (row * cell_height); //top of cell
                float bottom = top + cell_height; //bottom of cell is 1 cell_height away from top of cell

                if ((x > left) && (x < right) && (y > top) && (y < bottom)) {
                    if (state.getPlayerID() == 0) {
                        if (state.getPhaseOfGame() == "inPlay" && col < 10) { //return null for left board during inPlay phase
                            return null;
                        } else if (state.getPhaseOfGame() != "inPlay" && col > 10) { //return null for right board during setUp phase
                            return null;
                        }
                    } else if (state.getPlayerID() == 1) {
                        if (state.getPhaseOfGame() == "inPlay" && col > 10) { //return null for right board during inPlay phase
                            return null;
                        } else if (state.getPhaseOfGame() != "inPlay" && col < 10) { //return null for left board during setUp phase
                            return null;
                        }
                    }
                    if (col == 10){ //for empty column between boards, return null
                        return null;
                    }
                    Logger.log("tapPointBeforeCol-11", " " + col + " " + row);
                    if(col > 10){
                        col -=11;
                    }
                    Logger.log("tapPoint"," " + col + " " + row);
                    return new Point(col,row); //if point is in square, return point
                }
            }
        }
        return null; //if not on board, return null
    }
}