package win.haotinayi.playviewlib;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.CycleInterpolator;

/**
 * @author HaoTianYi hao.ty@haotianyi.win
 * @version v1.0
 * @des 创建一个不断跳动的柱形图
 * @time 2016/10/20 15:17
 */

public class PlayView extends View {

    private Paint mPaint;
    private String paintColor;
    /**
     * 获得整个组件的高度和宽度
     */
    private float mWidth;
    private float mHeight;
    /**
     * 音乐的柱形的柱子数量
     */
    private int countBar = 4;
    /**
     * 柱子绘制完成的比例
     */
    private static float mAnimatedValue = 0f;
    private static float mAnimatedValueTwo = 0f;
    private static float mAnimatedValueThree = 0f;
    private static float mAnimatedValueFour = 0f;
    /**
     * 两个柱子之间的距离
     */
    private float offsetX;
    /**
     * 柱子最顶端距离x轴的高度
     */
    private float offsetY;
    /**
     * 条形住的高度
     */
    private float barHeight;
    /**
     * 不同柱子的不同动画
     */
    private ValueAnimator valueAnimator;
    private ValueAnimator valueAnimatorTwo;
    private ValueAnimator valueAnimatorThree;
    private ValueAnimator valueAnimatorFour;
    private String canvasColor;

    public PlayView(Context context) {
        super(context);
    }

    public PlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initColor(attrs);
    }


    public PlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initColor(attrs);
    }

    private void initColor(AttributeSet attrs) {
        for (int i = 0; i < 3; i++) {
            paintColor = "haotyBackground" == attrs.getAttributeValue(i) ? attrs.getAttributeValue(i) : "#80DEEA";
            canvasColor = "haotyColColor"== attrs.getAttributeValue(i) ? attrs.getAttributeValue(i) : "#304FFE";
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaint();

        canvas.drawColor(Color.parseColor(canvasColor));
        canvas.drawLine(offsetX * 2, mHeight, offsetX * 2, barHeight / 2 + offsetY, mPaint);
        canvas.drawLine(offsetX * 5, mHeight, offsetX * 5, barHeight * 3 / 4 + offsetY, mPaint);
        canvas.drawLine(offsetX * 8, mHeight, offsetX * 8, barHeight / 2 + offsetY, mPaint);
        canvas.drawLine(offsetX * 11, mHeight, offsetX * 11, barHeight * 3 / 4 + offsetY, mPaint);

        mPaint.setStrokeCap(Paint.Cap.ROUND);

        canvas.drawLine(offsetX * 2, barHeight / 2 + offsetY, offsetX * 2, barHeight / 2 + offsetY - mAnimatedValue * barHeight / 2, mPaint);
        canvas.drawLine(offsetX * 5, barHeight * 3 / 4 + offsetY, offsetX * 5, barHeight * 3 / 4 + offsetY - mAnimatedValueTwo * barHeight * 3 / 4, mPaint);
        canvas.drawLine(offsetX * 8, barHeight / 2 + offsetY, offsetX * 8, barHeight / 2 + offsetY - mAnimatedValueThree * barHeight / 2, mPaint);
        canvas.drawLine(offsetX * 11, barHeight * 3 / 4 + offsetY, offsetX * 11, barHeight * 3 / 4 + offsetY - mAnimatedValueFour * barHeight * 3 / 4, mPaint);
    }

    private void initPaint() {
        offsetX = mWidth / 13;
        offsetY = (float) (mHeight * 1.0 / 4);
        barHeight = mHeight - offsetY;

        mPaint = new Paint();
        mPaint.setColor(Color.parseColor(paintColor));
        //设置画到两端的形状
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        mPaint.setStrokeWidth(offsetX * 2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
    }

    public void stopAnim() {
        if (valueAnimator != null) {
            clearAnimation();

            valueAnimator.setRepeatCount(0);
            valueAnimator.cancel();
            valueAnimator.end();
            mAnimatedValue = 0f;

            valueAnimatorTwo.setRepeatCount(0);
            valueAnimatorTwo.cancel();
            valueAnimatorTwo.end();
            mAnimatedValueTwo = 0f;

            valueAnimatorThree.setRepeatCount(0);
            valueAnimatorThree.cancel();
            valueAnimatorThree.end();
            mAnimatedValueThree = 0f;

            valueAnimatorFour.setRepeatCount(0);
            valueAnimatorFour.cancel();
            valueAnimatorFour.end();
            mAnimatedValueFour = 0f;
            postInvalidate();
        }
    }

    public void startViewAnim() {
        stopAnim();

        AnimatorSet animatorSet = new AnimatorSet();

        AnimatorManager animatorManager = new AnimatorManager();
        valueAnimator = (ValueAnimator) animatorManager.createAnimator(630, 0);
        valueAnimatorTwo = (ValueAnimator) animatorManager.createAnimator(830, 1);
        valueAnimatorThree = (ValueAnimator) animatorManager.createAnimator(730, 2);
        valueAnimatorFour = (ValueAnimator) animatorManager.createAnimator(900, 3);

        animatorSet.play(valueAnimator).with(valueAnimatorTwo).with(valueAnimatorThree).with(valueAnimatorFour);

        if (!animatorSet.isRunning()) {
            animatorSet.start();
        }
    }

    public class AnimatorManager {

        public Animator createAnimator(long time, final int value) {

            ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
            valueAnimator.setDuration(time);
            valueAnimator.setInterpolator(new CycleInterpolator(0.5f));
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);//无限循环
            valueAnimator.setRepeatMode(ValueAnimator.RESTART);


            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    switch (value) {
                        case 0:
                            PlayView.mAnimatedValue = (float) animation.getAnimatedValue();
                            break;
                        case 1:
                            PlayView.mAnimatedValueTwo = (float) animation.getAnimatedValue();
                            break;
                        case 2:
                            PlayView.mAnimatedValueThree = (float) animation.getAnimatedValue();
                            break;
                        case 3:
                            PlayView.mAnimatedValueFour = (float) animation.getAnimatedValue();
                            break;
                        default:
                            break;
                    }
                    invalidate();
                }
            });
            return valueAnimator;
        }
    }
}
