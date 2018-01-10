package com.example.vinayakahebbar.doctorapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.LinearLayout;

import com.example.vinayakahebbar.doctorapp.R;

public class CircularProgressBar extends View {
    private int dotRadius = 8;

    private int bounceDotRadius = 10;

    private int dotPosition = 1;

    private int dotAmount = 10;

    private int circleRadius = 45;

    public CircularProgressBar(Context context) {
        super(context);
    }

    public CircularProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CircularProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(getWidth()/2,getHeight()/2);

        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(this.getContext(), R.color.colorAccent));

        createDotCircle(canvas,paint);
    }

    private void createDotCircle(Canvas canvas, Paint paint) {
        int angle = 36;

        for(int i=1;i<=dotAmount;i++){
            float x = (float)(circleRadius * (Math.cos((angle * i)*(Math.PI/180))));
            float y = (float)(circleRadius * (Math.sin((angle * i)*(Math.PI/180))));
            if(i==dotPosition){
                canvas.drawCircle(x,y,bounceDotRadius,paint);
            }
            else {
                canvas.drawCircle(x,y,dotRadius,paint);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width;
        int height;

        width = 100 + (dotRadius * 3);
        height = 100 + (dotRadius * 3);

        setMeasuredDimension(width,height);
    }

    private void startAnimation() {
        BounceAnimation bounceAnimation = new BounceAnimation();
        bounceAnimation.setDuration(125);
        bounceAnimation.setRepeatCount(Animation.INFINITE);
        bounceAnimation.setInterpolator(new LinearInterpolator());
        bounceAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                dotPosition++;
                if(dotPosition > dotAmount){
                    dotPosition = 1;
                }
            }
        });
        startAnimation(bounceAnimation);
    }

    private class  BounceAnimation extends Animation{
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            invalidate();
        }
    }
}
