package com.android.lixiang.base.utils.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Gradient2Layout extends View {

    public Gradient2Layout(Context context) {
        super(context);
    }

    public Gradient2Layout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Gradient2Layout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取View的宽高
        super.onDraw(canvas);
        //获取View的宽高
        int width = getWidth();
        int height = getHeight();

        int colorStart = Color.parseColor("#0075FF");
        int colorEnd = Color.parseColor("#08AC96");

        Paint paint = new Paint();
        LinearGradient backGradient = new LinearGradient(0, 0, width, height, new int[]{colorStart ,colorEnd}, null, Shader.TileMode.CLAMP);
        paint.setShader(backGradient);
        canvas.drawRect(0, 0, width, height, paint);
    }
}
