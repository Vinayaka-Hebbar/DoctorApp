package com.example.vinayakahebbar.doctorapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Px;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;

import com.example.vinayakahebbar.doctorapp.R;

/**
 * Created by Vinayaka Hebbar on 10-01-2018.
 */

public class LinearProgressBar extends View {
    private int dotRadius = 8;
    private int bounceDotRadius = 10;
    private int dotPosition = 0;
    private int dotSpacing = 30;
    private int dotAmount = 5;
    public LinearProgressBar(Context context) {
        super(context);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnimation();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth()/2,getHeight()/2 - getPaddingTop()/2);
        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
        createDotCircle(canvas,paint);
    }

    private void createDotCircle(Canvas canvas, Paint paint) {
        for(int i=0;i<dotAmount;i++){
            float x = dotSpacing * i;
            float y = dotRadius;
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

        width = 75 + (dotSpacing + dotRadius) * dotAmount;
        height = 25 + dotRadius + getPaddingBottom() + getPaddingTop();

        setMeasuredDimension(width, height);
    }

    @Override
    public void setPadding(@Px int left, @Px int top, @Px int right, @Px int bottom) {
        super.setPadding(left, top, right, bottom);
    }

    private void startAnimation() {
        LinearProgressBar.BounceAnimation bounceAnimation = new LinearProgressBar.BounceAnimation();
        bounceAnimation.setDuration(150);
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
                    dotPosition = 0;
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
