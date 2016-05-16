package si.klika.spoandro.animations;

import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.ImageView;

public class LineAnimation extends ImageView implements MatchPitchCallback {

    private int viewWidth;
    private int viewHeight;
    private Paint viewPaint;
    private TextPaint textPaint;
    private int oldHomeValue = 0;
    private int oldGuestValue = 0;
    private int newHomeValue;
    private int newGuestValue;
    private float textWidth;
    private Path labelPath;
    private String showText;

    private float textPositionX;
    private float textPositionY;

    private int textHeight;

    private Rect textBounds;

    private AnimatorSet animatorSet;

    public LineAnimation(Context context) {
        super(context);
        throw new UnsupportedOperationException("This view was only ment to be constructed from XML layout");
    }

    public LineAnimation(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineAnimation(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        viewPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        viewPaint.setStyle(Paint.Style.FILL);
        viewPaint.setColor(Color.WHITE);
        viewPaint.setAlpha(200);

        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setTextSize(200);

        labelPath = new Path();

        showText=getOldValue();

        textBounds = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(viewWidth, viewHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        textWidth = Math.max(textPaint.measureText(getNewValue()),textPaint.measureText(getOldValue()));

        textPaint.getTextBounds(showText, 0, showText.length(), textBounds);
        textHeight = textBounds.height();

        textPositionX = viewWidth+textWidth;
        textPositionY = viewHeight/2+textHeight/2;

        int centerX = viewWidth/2;

        ValueAnimator leftToCenterAnimator = ValueAnimator.ofPropertyValuesHolder(PropertyValuesHolder.ofFloat("Translate", viewWidth+textWidth, centerX-textWidth/2));
        leftToCenterAnimator.setDuration(500);
        leftToCenterAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                textPositionX = (Float) animation.getAnimatedValue("Translate");
                invalidate();
            }
        });

        ValueAnimator centerAnimator = ValueAnimator.ofPropertyValuesHolder(PropertyValuesHolder.ofInt("Alpha", 255, 0, 255));
        centerAnimator.setDuration(2500);
        centerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                int alpha = (Integer) animation.getAnimatedValue("Alpha");

                if(alpha < 10) {
                    oldHomeValue = newHomeValue;
                    oldGuestValue = newGuestValue;
                    showText = getNewValue();
                }

                textPaint.setAlpha(alpha);
                invalidate();
            }
        });

        ValueAnimator centerToRightAnimator = ValueAnimator.ofPropertyValuesHolder(PropertyValuesHolder.ofFloat("Translate", centerX-textWidth/2, -textWidth));
        centerToRightAnimator.setDuration(500);
        centerToRightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                textPositionX = (Float) animation.getAnimatedValue("Translate");
                invalidate();
            }
        });

        animatorSet = new AnimatorSet();
        animatorSet.playSequentially(leftToCenterAnimator,centerAnimator,centerToRightAnimator);

    }

    @Override
    public void setResult(int home, int away) {
        newHomeValue = home;
        newGuestValue = away;
        animatorSet.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(animatorSet.isRunning())
            canvas.drawRect(0,viewHeight/4,viewWidth,viewHeight/4*3,viewPaint);

        labelPath.reset();
        labelPath.moveTo(textPositionX, textPositionY);
        labelPath.lineTo(textPositionX+textWidth, textPositionY);

        canvas.drawTextOnPath(showText,labelPath,0,0,textPaint);

    }

    private String getNewValue() {
        return newHomeValue + ":" + newGuestValue;
    }

    private String getOldValue() {
        return oldHomeValue + ":" + oldGuestValue;
    }

}
