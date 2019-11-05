package com.example.battleshipgamestate.battleship;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.battleshipgamestate.R;

public class BSSurfaceView extends SurfaceView {

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
    public RectF[][] board1 = new RectF[10][10]; //get canvas locations for p1's board, 0-9
    public RectF[][] board2 = new RectF[10][10]; //get canvas locations for p2's board, 0-9

    //background image variables
    private SurfaceHolder holder;
    private Bitmap bmp;
    private Bitmap ship1;

    public BSSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setWillNotDraw(false); //enable drawing

        //setBackgroundColor(Color.rgb(32, 32, 32)); //set background to selected value

        boardPaint.setColor(Color.WHITE); //set color for board to black

        boardPaint.setStyle(Paint.Style.STROKE); //set so boardPaint draws lines, not filled

        boardPaint.setStrokeWidth(5); //set board line thickness

        dividerPaint.setStrokeWidth(8); //set divider thickness

        waterPaint.setColor(Color.rgb(0, 255, 255)); //set water color to teal

        shipPaint.setColor(Color.GRAY); //set ship color to gray

        hitPaint.setColor(Color.RED); //set hit color to red

        missPaint.setColor(Color.rgb(224, 224, 224)); //set miss color to shade of white

        //test
        /** Citation: https://www.thepolyglotdeveloper.com/2015/05/dr
         * aw-a-graphic-to-a-surfaceview-using-native-android/ **/
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


    }

    /** public method onDraw will draw the GUI **/
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
        RectF shipRect1 = new RectF(board1[0][0].left, board1[0][0].top, board1[1][1].right, board1[1][1].bottom);
        canvas.drawBitmap(this.ship1, null, shipRect1, null); //draw ship

    }

    /** drawBoard method: draws the playing boards on the specified canvas
     *  Parameters: canvas to draw on **/
    public void drawBoard(Canvas canvas){
        // draw board
        for (int i = 0; i < num_row; i++) {
            for (int j = 0; j < num_col; j++){
                drawCell(canvas, i, j, 1); //draw left board (player 1)
                drawCell(canvas, (num_row + 1) + i, j, 2); //draw right board (player 2)

            }
        }

    }

    /** drawCell method: draws a single cell of the playing board
     *  Parameters: canvas to draw on, current row of board, current column of board, current player **/
    public void drawCell(Canvas canvas, float row, float col, int player) {

        float cell_left = left_margin + (row * cell_width); //left of cell
        float cell_right = cell_left + cell_width; //right of cell is 1 cell_width away from left of cell
        float cell_top = top_margin + (col * cell_height); //top of cell
        float cell_bottom = cell_top + cell_height; //bottom of cell is 1 cell_height away from top of cell
        RectF myCell = new RectF(cell_left, cell_top, cell_right, cell_bottom); //cell to be drawn
        canvas.drawRect(myCell, boardPaint); //draw the cell

        //set locations of each cell to the correct board for use in gameState
        if (player == 1) {
            board1[(int) row][(int) col] = myCell; //assign area of the cell to 2d board array
        } else if (player == 2) {
            board2[(int) row][(int) col] = myCell; //assign area of the cell to p2's 2d board array
        }
    }

    /** drawShips method: draws ships on the surfaceView
     * Parameters: canvas to draw on **/
    public void drawShips(Canvas canvas){
        //draw ships here

    }

}
