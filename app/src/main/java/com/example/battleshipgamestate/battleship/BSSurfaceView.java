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

    //background image variables
    private SurfaceHolder holder;
    private Bitmap bmp;

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

        RectF newRect = new RectF(0,0,width,height);

        canvas.drawBitmap(this.bmp, null, newRect, null);

        //draw grid boards
        drawBoard(canvas);

        //draw ships
        //drawShips(canvas, width, height, gridHeight, gridWidth);

        //draw hits and misses
        //drawHits(canvas, width, height, gridHeight, gridWidth);
        //drawMisses(canvas, width, height, gridHeight, gridWidth);

    }

    public void drawBoard(Canvas canvas){
        // draw board
        for (int i = 0; i < num_row; i++) {
            for (int j = 0; j < num_col; j++){
                drawCell(canvas, i, j); //draw left board
                drawCell(canvas, (num_row + 1) + i, j); //draw right board
            }
        }

    }

    /** drawCell method: draws a single cell of the playing board **/
    public void drawCell(Canvas canvas, float row, float col){

        float cell_left = left_margin + (row * cell_width); //left of cell
        float cell_right = cell_left + cell_width; //right of cell is 1 cell_width away from left of cell
        float cell_top = top_margin + (col * cell_height); //top of cell
        float cell_bottom = cell_top + cell_height; //bottom of cell is 1 cell_height away from top of cell
        RectF myCell = new RectF(cell_left, cell_top, cell_right, cell_bottom); //cell to be drawn
        canvas.drawRect(myCell, boardPaint); //draw the cell
    }

    public void drawShips(Canvas canvas, float w, float h, float grid_height, float grid_width){

        //for drawing horizontal ships
        float carrier_width = (float) (w * (5 * 0.025));
        float battleship_width = (float) (w * (4 * 0.025));
        float submarine_width = (float) (w * (3 * 0.025));
        float patrolboat_width = (float) (w * (2 * 0.025));

        //canvas.drawRect(left,top,right,bottom,paint);

        //for drawing vertical ships
        float carrier_height = (float) (h * (5 * 0.06));
        float battleship_height = (float) (h * (4 * 0.06));
        float submarine_height = (float) (h * (3 * 0.06));
        float patrolboat_height = (float) (h * (2 * 0.06));

        //draw ships
        canvas.drawRect(w/4, h/5, w/4 + (carrier_width), h/5 + (grid_height), shipPaint); //player1 carrier (horizontal)

        canvas.drawRect(w/4 + (2 *grid_width), h/5 + (2 * grid_height), w/4 + (3*grid_width), h/5 + (2 * grid_height) + (battleship_height), shipPaint); //player1 battleship (vertical)

        canvas.drawRect(w/2 + (grid_width), h/5 + (grid_height), w/2 + (grid_width) + grid_width, h/5 + (grid_height) + submarine_height, shipPaint); //player2 sub (vertical)

        canvas.drawRect(w/2 + (7 * grid_width), h/5 + (8 *  grid_height), w/2 + (7 * grid_width) + patrolboat_width, h/5 + (9 * grid_height), shipPaint); //player2 patrol boat (horizontal)

        canvas.drawRect(w/2 + (5 * grid_width), h/5 + (2 * grid_height), w/2 + (6 * grid_width), h/5 + (2 * grid_height) + carrier_height, shipPaint); //player2 carrier (vertical)
    }

    public void drawHits(Canvas canvas, float w, float h, float grid_height, float grid_width){

        //player1's hit ships

        canvas.drawRect(w/4, h/5 + (4 * grid_height), w/4 + (1 * grid_width), h/5 + (7 * grid_height), hitPaint);

        canvas.drawRect(w/4 + (2 * grid_width), h/5 + (8 * grid_height), w/4 + (5 * grid_width), h/5 + (9 * grid_height), hitPaint);

        canvas.drawRect(w/4 + (6 * grid_width), h/5 + (3 * grid_height), w/4 + ( 8 * grid_width), h/5 + (4 * grid_height), hitPaint);

        canvas.drawRect(w/4 + (1 * grid_width), h/5, w/4 + (2 * grid_width), h/5 + (1 * grid_height), hitPaint);

        //player2's hit ships

        canvas.drawRect(w/2 + (2 * grid_width), h/5 + (6 * grid_height), w/2 + (5 * grid_width), h/5 + (7 * grid_height), hitPaint);

        canvas.drawRect(w/2 + (8 * grid_width), h/5 + (3 * grid_height), w/2 + (9 * grid_width), h/5 + (7 * grid_height), hitPaint);
    }

    public void drawMisses(Canvas canvas, float w, float h, float grid_height, float grid_width){
        //misses on player1's board

        canvas.drawRect(w/4 + (9 * grid_width), h/5, w/4 + (10 * grid_width), h/5 + (1 * grid_height), missPaint);

        canvas.drawRect(w/4 + (8 * grid_width), h/5 + (7 * grid_height), w/4 + (9 * grid_width), h/5 + (8 * grid_height), missPaint);

        canvas.drawRect(w/4 + (4 * grid_width), h/5 + (5 * grid_height), w/4 + (5 * grid_width), h/5 + (6 * grid_height), missPaint);

        //misses on player2's board

        canvas.drawRect(w/2 + (7 * grid_width), h/5, w/2 + (8 * grid_width), h/5 + (1 * grid_height), missPaint);

        canvas.drawRect(w/2 + (1 * grid_width), h/5 + (8 * grid_height), w/2 + (2 * grid_width), h/5 + (9 * grid_height), missPaint);

        canvas.drawRect(w/2 + (8 * grid_width), h/5, w/2 + (9 * grid_width), h/5 + (1 * grid_height), missPaint);

        canvas.drawRect(w/2 + (3 * grid_width), h/5 + (9 * grid_height), w/2 + (4 * grid_width), h/5 + (10 * grid_height), missPaint);

        canvas.drawRect(w/2 + (9 * grid_width), h/5 + (9 * grid_height), w/2 + (10 * grid_width), h/5 + (10 * grid_height), missPaint);

    }
}
