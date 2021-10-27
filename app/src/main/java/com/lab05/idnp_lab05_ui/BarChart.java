package com.lab05.idnp_lab05_ui;

import android.app.appsearch.PackageIdentifier;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class BarChart extends View {

    Paint barPainter, axisPainter, guidePainter, xLabelPainter, yLabelPainter;
    int barColor, axisColor, guideColor;
    float padding, yLabelWidth, xLabelWidth;
    List<Float> series;


    public BarChart(Context context, AttributeSet attrs){
        super(context, attrs);

        //Escoger el color
        int h = Color.YELLOW;

        barColor = Color.LTGRAY;
        axisColor = Color.BLACK;
        guideColor = Color.GRAY;
        //axisThickness = 5f;
        //guideThickness = 3f;
        padding = 20f;

        barPainter = new Paint();
        barPainter.setStyle(Paint.Style.FILL);
        barPainter.setColor(h);

        axisPainter = new Paint();
        axisPainter.setStyle(Paint.Style.STROKE);
        axisPainter.setColor(Color.BLACK);
        axisPainter.setStrokeWidth(5f);

        guidePainter = new Paint();
        guidePainter.setStyle(Paint.Style.STROKE);
        guidePainter.setColor(Color.GRAY);
        axisPainter.setStrokeWidth(3f);

        xLabelPainter = new Paint(Paint.ANTI_ALIAS_FLAG);
        xLabelPainter.setColor(Color.BLACK);
        xLabelPainter.setTextSize(30f);
        xLabelPainter.setTextAlign(Paint.Align.RIGHT);

        yLabelPainter = new Paint(Paint.ANTI_ALIAS_FLAG);
        yLabelPainter.setColor(Color.BLACK);
        yLabelPainter.setTextSize(30f);
        yLabelPainter.setTextAlign(Paint.Align.RIGHT);

        xLabelWidth = 60f;

        series = new ArrayList<Float>(Arrays.asList(0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f, 1f));
        /*Random rand = new Random();
        for(int i=0; i<10; i++){
            series.add(rand.nextFloat());
        }*/
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int height = getHeight();
        int width = getWidth();
        float gridTopLeft = padding +yLabelWidth +10f;
        float gridBottom = height - padding -xLabelWidth;
        float gridRight = width - padding;

        drawGuides(canvas, gridBottom, gridRight);
        drawAxis(canvas, gridTopLeft, gridBottom, gridRight);
        drawBars(canvas, height, gridTopLeft, gridBottom, gridRight);
    }

    private void drawBars(Canvas canvas, float canvasHeight, float gridTopLeft, float gridBottom, float gridRight) {
        float space = 10f;
        float totalSpace = space * series.size();
        float width = (gridRight-gridTopLeft-totalSpace)/series.size();
        float left = gridTopLeft + space;
        float right = left+width;
        float height = canvasHeight - 2*padding -xLabelWidth;

        int a=150;
        for(float s: series){
            float top = padding + height * (1f - s);
            canvas.drawRect(left,top,right,gridBottom,barPainter);

            canvas.drawText(Integer.toString(Math.round(s*100)), a, 1880, yLabelPainter);
            a+=98;
            left = right + space;
            right = left + width;


        }
    }

    private void drawAxis(Canvas canvas, float gridTopLeft, float gridBottom, float gridRight) {
        canvas.drawLine(gridTopLeft, gridBottom, gridTopLeft, padding, axisPainter);
        canvas.drawLine(gridTopLeft, gridBottom, gridRight, gridBottom, axisPainter);
    }

    private void drawGuides(Canvas canvas, float gridBottom, float gridRight) {
        float spacing = (gridBottom - padding)/10f;
        float y;
        for(int i=0; i<10; i++){

            String label = Integer.toString(100-i*10);
            float width = yLabelPainter.measureText(label);
            if(yLabelWidth<width)yLabelWidth=width;
            Rect bound = new Rect();
            yLabelPainter.getTextBounds(label,0,label.length(),bound);

            y = padding+i*spacing;
            canvas.drawLine(padding+yLabelWidth,y,gridRight,y,guidePainter);
            canvas.drawText(label, padding+yLabelWidth, y+bound.height()/2, yLabelPainter);





        }
    }
}
