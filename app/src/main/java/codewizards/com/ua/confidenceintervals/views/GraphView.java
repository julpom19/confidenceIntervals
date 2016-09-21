package codewizards.com.ua.confidenceintervals.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

import java.util.List;

import codewizards.com.ua.confidenceintervals.MainActivity;
import codewizards.com.ua.confidenceintervals.logic.Interval;

/**
 * Created by Интернет on 07.09.2016.
 */

public class GraphView extends View {
    private Paint paint;
    private Paint dashedPaint;
    private Paint cornerPaint;
    private Paint fontPaint;
    private Paint intervalsPaint;
    private Path path;
    private DashPathEffect dashPathEffect;
    private CornerPathEffect cornerPathEffect;
    private boolean generateDots;
    //int TOTAL_Y__AMOUNT_OF_DIVISION = 3;
    int LEFT_X = Integer.MAX_VALUE;
    int RIGHT_X = Integer.MIN_VALUE;
    int TOTAL_X;
    float FONT_SIZE = 20;
    int centerX;
    int centerY;
    int lenghtOfDivisionX, lenghtOfDivisionY;
    Interval operand1, operand2, result;
    double min = Integer.MAX_VALUE;
    double max = Integer.MIN_VALUE;


    public GraphView(Context context) {
        super(context);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2);

        dashedPaint = new Paint();
        path = new Path();
        float[] intervals = new float[] { 5.0f, 5.0f };
        float phase = 0;
        dashPathEffect = new DashPathEffect(intervals, phase);
        dashedPaint.setPathEffect(dashPathEffect);
        dashedPaint.setColor(Color.BLACK);
        dashedPaint.setStyle(Paint.Style.STROKE);
        dashedPaint.setStrokeWidth(2);

        cornerPaint = new Paint();
        cornerPathEffect = new CornerPathEffect(20);
        cornerPaint.setPathEffect(cornerPathEffect);
        cornerPaint.setColor(Color.BLACK);
        cornerPaint.setStyle(Paint.Style.STROKE);
        cornerPaint.setStrokeWidth(2);

        fontPaint = new Paint();
        fontPaint.setTextSize(FONT_SIZE);
        fontPaint.setStyle(Paint.Style.STROKE);

        intervalsPaint = new Paint();
        intervalsPaint.setColor(Color.RED);
        intervalsPaint.setStrokeWidth(3);
    }

    public void setIntervals(List<Interval> intervals) throws Exception {
        if(intervals == null) {
            throw new Exception();
        }
        if(intervals.size() < 3) {
            throw new Exception();
        }
        operand1 = intervals.get(0);
        operand2 = intervals.get(1);
        result = intervals.get(2);
        for(Interval interval : intervals) {
            if(interval.getLeftLimit() < min) {
                min = Math.floor(interval.getLeftLimit());
                LEFT_X = Math.abs((int) Math.floor(interval.getLeftLimit()));
            }
            if(interval.getRightLimit() > max) {
                max = Math.ceil(interval.getRightLimit());
                RIGHT_X = Math.abs((int) Math.ceil(interval.getRightLimit()));
            }
        }
        TOTAL_X = LEFT_X + RIGHT_X + 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int height = canvas.getHeight();
        int width = canvas.getWidth();
        //int minMeasure = height < width ? height : width;
        lenghtOfDivisionX = width / TOTAL_X;
        lenghtOfDivisionY = height / 4;
        if(min < 0) {
            centerX = LEFT_X * lenghtOfDivisionX;
        } else {
            centerX = lenghtOfDivisionX;
        }
        centerY = height - 20;


        //рисуем ось У
        canvas.drawLine(centerX, 0, centerX, height, paint);
//        canvas.drawText("X2", centerX + 7, 22, fontPaint);
        //рисуем ось Х
        canvas.drawLine(0, centerY, width, centerY, paint);
//        canvas.drawText("X1", width - 20, centerY - 5, fontPaint);
        //рисуем черточки и циферки
        if(TOTAL_X <= 20) {
            int xCoord = 2 * lenghtOfDivisionX;
            int x = 1;
            if(min < 0) {
                x = -LEFT_X + 1;
                xCoord = lenghtOfDivisionX;
            }
            while (xCoord < width) {
                canvas.drawLine(xCoord, centerY - 4, xCoord, centerY + 4, paint);
                if(xCoord + lenghtOfDivisionX < width) {
                    canvas.drawText("" + x, xCoord, getYCoord(-0.2f), fontPaint);
                }
                x++;
                xCoord += lenghtOfDivisionX;
            }
        }
//        int yCoord = height - lenghtOfDivision;
//        int y = 0;
//        while (yCoord > 0) {
//            canvas.drawLine(centerX - 4, yCoord, centerX + 4, yCoord, paint);
//            if(y > 0 && y % 2 == 0 && yCoord + lenghtOfDivision < height) {
//                if(y < 10) {
//                    canvas.drawText("" + y, getXCoord(-1), yCoord, fontPaint);
//                } else {
//                    canvas.drawText("" + y, getXCoord(-1.5f), yCoord, fontPaint);
//                }
//            }
//            y++;
//            yCoord -= lenghtOfDivision;
//        }
        //operand1
        canvas.drawLine(getXCoord((float) operand1.getLeftLimit()), getYCoord(1), getXCoord((float) operand1.getRightLimit()), getYCoord(1), paint);
        if(operand1.getLeftLimit() == operand1.getRightLimit()) {
            canvas.drawText("a", getXCoord((float) operand1.getLeftLimit()), getYCoord(0.8f), fontPaint);
        } else {
            canvas.drawText("a1", getXCoord((float) operand1.getLeftLimit()), getYCoord(0.8f), fontPaint);
            canvas.drawText("a2", getXCoord((float) operand1.getRightLimit()), getYCoord(0.8f), fontPaint);
        }
        //operand1
        canvas.drawLine(getXCoord((float) operand2.getLeftLimit()), getYCoord(2), getXCoord((float) operand2.getRightLimit()), getYCoord(2), paint);
        if(operand2.getLeftLimit() == operand2.getRightLimit()) {
            canvas.drawText("b", getXCoord((float) operand2.getLeftLimit()), getYCoord(1.8f), fontPaint);
        } else {
            canvas.drawText("b1", getXCoord((float) operand2.getLeftLimit()), getYCoord(1.8f), fontPaint);
            canvas.drawText("b2", getXCoord((float) operand2.getRightLimit()), getYCoord(1.8f), fontPaint);
        }
        //operand1
        canvas.drawLine(getXCoord((float) result.getLeftLimit()), getYCoord(3), getXCoord((float) result.getRightLimit()), getYCoord(3), paint);
        if(result.getLeftLimit() == result.getRightLimit()) {
            canvas.drawText("r", getXCoord((float) result.getLeftLimit()), getYCoord(2.8f), fontPaint);
        } else {
            canvas.drawText("r1", getXCoord((float) result.getLeftLimit()), getYCoord(2.8f), fontPaint);
            canvas.drawText("r2", getXCoord((float) result.getRightLimit()), getYCoord(2.8f), fontPaint);
        }
        //стрелочки
        //X
        canvas.drawLine(width - 10, centerY + 5, width, centerY, paint);
        canvas.drawLine(width - 10, centerY - 5, width, centerY, paint);
        //Y
        canvas.drawLine(centerX + 5, 10, centerX, 0, paint);
        canvas.drawLine(centerX - 5, 10, centerX, 0, paint);
    }

    float getXCoord(float x) {
        return centerX + x * lenghtOfDivisionX;
    }

    float getYCoord(float y) {
        return centerY - y * lenghtOfDivisionY;
    }

}
