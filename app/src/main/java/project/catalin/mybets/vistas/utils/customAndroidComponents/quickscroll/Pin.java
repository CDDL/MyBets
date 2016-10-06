package project.catalin.mybets.vistas.utils.customAndroidComponents.quickscroll;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import project.catalin.mybets.R;

public class Pin extends View {

    private static final int mPinColor = Color.argb(224, 66, 66, 66);
    private Paint mPaint;
    private Path mPath;

    public Pin(Context context) {
        super(context);
        init();
    }

    public Pin(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Pin(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public void setColor(int color) {
        mPaint.setColor(Color.RED);
    }

    private void init() {
//        mPath = new Path();
//        mPaint = new Paint();
//        mPaint.setAntiAlias(true);
//        mPaint.setStyle(Paint.Style.FILL);
//        setColor(mPinColor);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        if (changed) {
//            mPath.reset();
//            mPath.moveTo(0, getHeight());
//            mPath.lineTo(getWidth(), getHeight() / 2);
//            mPath.lineTo(0, 0);
//            mPath.close();
//        }
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable d = getResources().getDrawable(R.drawable.mybets_fondo_indicador_scrollbar_down);
        d.setBounds(0, 0, getWidth(), getHeight());
        d.draw(canvas);
        super.onDraw(canvas);
    }

}
