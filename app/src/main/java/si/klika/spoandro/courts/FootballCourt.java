package si.klika.spoandro.courts;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.ImageView;

public class FootballCourt extends ImageView implements MatchPitchCallback {

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

    private float rectLeft;
    private float rectRight;
    private float rectTop;
    private float rectBottom;

    private int textHeight;

    private float square;

    private Rect textBounds;

    private Path rectPath;

    private AnimatorSet animatorSet;

    public FootballCourt(Context context) {
        super(context);
        throw new UnsupportedOperationException("This view was only ment to be constructed from XML layout");
    }

    public FootballCourt(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FootballCourt(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        viewPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        viewPaint.setStyle(Paint.Style.FILL);
        viewPaint.setColor(Color.WHITE);

        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setTextSize(200);

        labelPath = new Path();
        rectPath = new Path();

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

        rectTop = textPositionY - textHeight - 50;
        rectBottom = textPositionY + 50;

        square = rectBottom-rectTop;

        rectLeft = viewWidth+square/2;
        rectRight = viewWidth+textWidth;

        int centerX = viewWidth/2;

        int[] colors = new int[]{Color.TRANSPARENT,Color.BLACK,Color.BLACK,Color.BLACK,Color.TRANSPARENT};
        float[] positions = new float[] {0.1f,0.15f,0.9f,0.95f,1f};

        textPaint.setShader(new LinearGradient(centerX, rectTop, centerX, rectBottom, colors, null, Shader.TileMode.CLAMP));

        ValueAnimator preLeftToCenterAnimator = ValueAnimator.ofPropertyValuesHolder(PropertyValuesHolder.ofFloat("Translate", viewWidth+textWidth-square/2, centerX-textWidth/2));
        preLeftToCenterAnimator.setDuration(500);
        preLeftToCenterAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                rectLeft = (Float) animation.getAnimatedValue("Translate");
                invalidate();
            }
        });

        ValueAnimator leftToCenterAnimator = ValueAnimator.ofPropertyValuesHolder(PropertyValuesHolder.ofFloat("Translate", viewWidth+textWidth, centerX-textWidth/2),PropertyValuesHolder.ofFloat("TranslateRect", viewWidth+textWidth, centerX+textWidth/2));
        leftToCenterAnimator.setDuration(500);
        leftToCenterAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                textPositionX = (Float) animation.getAnimatedValue("Translate");
                rectRight = (Float) animation.getAnimatedValue("TranslateRect");
                invalidate();
            }
        });

        ValueAnimator centerAnimator = ValueAnimator.ofPropertyValuesHolder(PropertyValuesHolder.ofFloat("TranslateY", viewHeight/2+textHeight/2, viewHeight/2+textHeight/2+square));
        centerAnimator.setDuration(2500);
        centerAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                textPositionY = (Float) animation.getAnimatedValue("TranslateY");
                invalidate();
            }
        });

        ValueAnimator centerToRightAnimator = ValueAnimator.ofPropertyValuesHolder(PropertyValuesHolder.ofFloat("Translate", centerX-textWidth/2, -textWidth),PropertyValuesHolder.ofFloat("TranslateRect", centerX-textWidth/2, -textWidth-square/2));
        centerToRightAnimator.setDuration(500);
        centerToRightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                textPositionX = (Float) animation.getAnimatedValue("Translate");
                rectLeft = (Float) animation.getAnimatedValue("TranslateRect");
                invalidate();
            }
        });

        ValueAnimator postCenterToRightAnimator = ValueAnimator.ofPropertyValuesHolder(PropertyValuesHolder.ofFloat("Translate", centerX+textWidth/2, -textWidth-square/2));
        postCenterToRightAnimator.setDuration(500);
        postCenterToRightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                rectRight = (Float) animation.getAnimatedValue("Translate");
                invalidate();
            }
        });

        animatorSet = new AnimatorSet();
        animatorSet.playSequentially(preLeftToCenterAnimator,leftToCenterAnimator,centerAnimator,centerToRightAnimator,postCenterToRightAnimator);

        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                    oldHomeValue = newHomeValue;
                    oldGuestValue = newGuestValue;
            }
        });

    }

    @Override
    public void setResult(int home, int away) {
        newHomeValue = home;
        newGuestValue = away;
        rectLeft = viewWidth+square/2;
        rectRight = viewWidth+textWidth;
        textPositionY = viewHeight/2+textHeight/2;
        animatorSet.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        rectPath.reset();

        rectPath.moveTo(rectLeft, rectTop);
        rectPath.lineTo(rectRight, rectTop);
        rectPath.arcTo(new RectF(rectRight-square/2,rectTop,rectRight+square/2,rectBottom),-90,180);
        rectPath.lineTo(rectLeft, rectBottom);
        rectPath.arcTo(new RectF(rectLeft-square/2,rectTop,rectLeft+square/2,rectBottom),90,180);

        canvas.drawPath(rectPath,viewPaint);

        labelPath.reset();
        labelPath.moveTo(textPositionX, textPositionY);
        labelPath.lineTo(textPositionX+textWidth, textPositionY);

        canvas.drawTextOnPath(getOldValue(),labelPath,0,0,textPaint);

        labelPath.reset();
        labelPath.moveTo(textPositionX, textPositionY-square);
        labelPath.lineTo(textPositionX+textWidth, textPositionY-square);

        canvas.drawTextOnPath(getNewValue(),labelPath,0,0,textPaint);

    }

    private String getNewValue() {
        return newHomeValue + ":" + newGuestValue;
    }

    private String getOldValue() {
        return oldHomeValue + ":" + oldGuestValue;
    }

}
