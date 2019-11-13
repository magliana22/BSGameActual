package com.example.battleshipgamestate.battleship;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.battleshipgamestate.R;
import com.example.battleshipgamestate.game.GameFramework.utilities.FlashSurfaceView;

public class BSSurfaceView extends FlashSurfaceView {

    Paint boardPaint = new Paint();
    Paint dividerPaint = new Paint();
    Paint waterPaint = new Paint();
    Paint shipPaint = new Paint();
    Paint hitPaint = new Paint();
    Paint missPaint = new Paint();

    /** Declare/initialize variables for grid here **/
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

        missPaint.setColor(Color.rgb(224, 224, 224)); //set miss color to shade of white

        //test
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
            public void surfaceDestroyed(SurfaceHolder holder) { }

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                Canvas canvas = holder.lockCanvas();
                if (canvas != null) {
                    draw(canvas);
                    holder.unlockCanvasAndPost(canvas);
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { }

        });

        this.ship1 = BitmapFactory.decodeResource(getResources(), R.drawable.battleship1);
        this.ship2 = BitmapFactory.decodeResource(getResources(), R.drawable.battleship2);
        this.ship3 = BitmapFactory.decodeResource(getResources(), R.drawable.battleship5);
        this.ship4 = BitmapFactory.decodeResource(getResources(), R.drawable.battleship4);
        this.ship5 = BitmapFactory.decodeResource(getResources(), R.drawable.battleship3);

    }

    protected BSState state;

    /** setState method: sets the local state variable
     */
    public void setState(BSState state) {
        this.state = state;
    }

    /** public method onDraw will draw the GUI
     * @param canvas
     *              canvas to draw on
     */
    public void onDraw(Canvas canvas){

        width = getWidth(); //get width of canvas
        height = getHeight(); //get height of canvas
        left_margin = width/5; //space between left edge and start of 1st board
        right_margin = width - (width/5); //space between right edge and end of 2nd board
        top_margin = height/5; //space between top edge and top of boards
        bottom_margin = height - (height/5); //space between bottom edge and bottom of boards
        cell_width = width/35; //width of 1 cell
        cell_height = height/17; //height of 1 cell

        RectF newRect = new RectF(0,0,width,height); //rectangle for background

        canvas.drawBitmap(this.bmp, null, newRect, null); //draw background

        //draw grid boards
        drawBoard(canvas);

        //draw ships

        //state.addAllShips(0);
        //state.addAllShips(1);

        RectF shipRect1 = new RectF(left_margin, top_margin, left_margin+(2*cell_width), top_margin+ cell_height);
        RectF shipRect2 = new RectF(left_margin+cell_width, top_margin+(5*cell_height), left_margin+cell_width+(2*cell_width), top_margin+(5*cell_height)+cell_height);
        RectF shipRect3 = new RectF(left_margin, top_margin+(8*cell_height), left_margin+(3*cell_width), top_margin+(8*cell_height)+(cell_height));
        RectF shipRect4 = new RectF(left_margin+(6*cell_width), top_margin+(7*cell_height), left_margin+(6*cell_width)+(4*cell_width), top_margin+(7*cell_height)+cell_height);
        RectF shipRect5 = new RectF(left_margin+(2*cell_width), top_margin+(2*cell_width), (left_margin+(2*cell_width))+(5*cell_width), top_margin+(2*cell_width)+ cell_height);

        canvas.drawBitmap(this.ship1, null, shipRect1, null); //draw ship
        canvas.drawBitmap(this.ship2, null, shipRect2, null); //draw ship
        canvas.drawBitmap(this.ship3, null, shipRect3, null); //draw ship
        canvas.drawBitmap(this.ship4, null, shipRect4, null); //draw ship
        canvas.drawBitmap(this.ship5, null, shipRect5, null); //draw ship

        //draw hits
        //drawHit(canvas, 0, 1);
        //drawHit(canvas, 7, 7);

        //drawHit(canvas, 1, 14);
        //draw misses
        //drawMiss(canvas, 1, 4);

        //drawMiss(canvas, 5, 15);
        //drawMiss(canvas, 8, 18);

        //if we don't have any state, there's nothing more to draw, so return
        if (state == null) {
            return;
        }
        // for each square that has a hit or miss, draw it on the appropriate place on the canvas
        for (int row = 0; row < num_row; row++){
            for (int col = 0; col < num_col; col++){
                int result = state.checkSpot(row, col, 1);
                int result2 = state.checkSpot(row, col, 2);
                // player1's board
                if (result == 3){
                    drawHit(canvas, row, col);
                }
                if (result == 4){
                    drawMiss(canvas, row, col);
                }
                // player2's board
                if (result2 == 3){
                    drawHit(canvas, row, (num_col + 1) + col);
                }
                if (result == 4){
                    drawMiss(canvas, row, (num_col + 1) + col);
                }
            }
        }
    }

    /** drawBoard method: draws the playing boards on the specified canvas
     *  @param canvas
     *              referenced canvas to draw on **/
    public void drawBoard(Canvas canvas){
        // draw board
        for (int i = 0; i < num_row; i++) {
            for (int j = 0; j < num_col; j++){
                drawCell(canvas, i, j); //draw left board (player 1)
                drawCell(canvas, i, (num_col + 1) + j); //draw right board (player 2)

            }
        }

    }

    /** drawCell method: draws a single cell of the playing board
     * @param canvas
     *                  canvas to draw on
     * @param row
     *                  row of board to draw the cell
     * @param col
     *                  column of board to draw the cell
     */
    public void drawCell(Canvas canvas, float row, float col) {

        float cell_left = left_margin + (col * cell_width); //left of cell
        float cell_right = cell_left + cell_width; //right of cell is 1 cell_width away from left of cell
        float cell_top = top_margin + (row * cell_height); //top of cell
        float cell_bottom = cell_top + cell_height; //bottom of cell is 1 cell_height away from top of cell
        RectF myCell = new RectF(cell_left, cell_top, cell_right, cell_bottom); //cell to be drawn
        canvas.drawRect(myCell, boardPaint); //draw the cell

    }

    /** drawHit method: draws an x where a player hits a ship
     * @param canvas
     *          the canvas referenced to draw on
     * @param row
     *          the row the hit will be drawn on
     * @param col
     *          the column the hit will be drawn on
     */
    public void drawHit(Canvas canvas, float row, float col) {
        float cell_left = left_margin + (col * cell_width); //left of cell
        float cell_right = cell_left + cell_width; //right of cell is 1 cell_width away from left of cell
        float cell_top = top_margin + (row * cell_height); //top of cell
        float cell_bottom = cell_top + cell_height; //bottom of cell is 1 cell_height away from top of cell

        //draw the X
        canvas.drawLine(cell_left,cell_top,cell_right,cell_bottom, hitPaint);
        canvas.drawLine(cell_left,cell_bottom,cell_right,cell_top, hitPaint);
    }

    /** drawMiss method: draws an o where a player misses **/
    public void drawMiss(Canvas canvas, float row, float col) {
        float cell_left = left_margin + (col * cell_width); //left of cell
        float cell_right = cell_left + cell_width; //right of cell is 1 cell_width away from left of cell
        float cell_top = top_margin + (row * cell_height); //top of cell
        float cell_bottom = cell_top + cell_height; //bottom of cell is 1 cell_height away from top of cell
        RectF myOval = new RectF(cell_left, cell_top, cell_right, cell_bottom); //circle to be drawn
        canvas.drawOval(myOval, missPaint);
    }

    /** mapPixelToSquare method: converts a touch into a square on the board
     * @param x
     *              x coordinate where screen is touched
     * @param y
     *              y coordinate where screen is touched
     */
    public Point mapPixelToSquare(int x, int y) {

        for (int i = 0; i < num_row; i++){ //player taps on second board
            for (int j = 0; j < num_col; j++){
                //float left = left_margin + (j * cell_width); // use this value of left for first board
                float left = left_margin + (j * cell_width) + (11*cell_width); //left of cell (right board starts at col 11)
                float right = left + cell_width; //right of cell is 1 cell_width away from left of cell
                float top = top_margin + (i * cell_height); //top of cell
                float bottom = top + cell_height; //bottom of cell is 1 cell_height away from top of cell

                if ((x > left) != (x > right) && (y > top) != (y > bottom)) {
                    return new Point(i, j); //if point is in square, return point
                }
            }
        }
        return null; //if not on board, return null
    }
}
