package com.everalbum.roliedex;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.Random;

public class RoliedexLayout extends LinearLayout {

    private static final String[] DIGITS = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    private long animDuration;
    private int  textSize;
    private int  textColor;
    private int  slideInAnimation;
    private int  slideOutAnimation;

    // digits[0] is the ones, digits[1] is tens, and so on
    private TextSwitcher[] digits;
    // decimals[0] is the tenths, decimals[1] is hundredths, and so on
    private TextSwitcher[] decimals;

    private TextSwitcher decimalPoint;
    private TextSwitcher decorator;

    // digNums[0] is the ones, digNums[1] is tens, and so on
    private int[] digNums;
    // decNums[0] is the tenths, decNums[1] is hundredths, and so on
    private int[] decNums;

    private Random random;

    private boolean forceDedimal;
    private double  value;
    private long    animationStartTime;
    private boolean animationEnded;

    public RoliedexLayout(Context context) {
        super(context, null);
        init(context, null, 0, 0);
    }

    public RoliedexLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0, 0);
    }

    public RoliedexLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RoliedexLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr, defStyleRes);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        TypedArray attr = context.obtainStyledAttributes(attrs,
                                                         R.styleable.RoliedexLayout,
                                                         defStyleAttr,
                                                         defStyleRes);
        animDuration = attr.getInt(R.styleable.RoliedexLayout_animDuraction, 500);
        textSize = attr.getInt(R.styleable.RoliedexLayout_textSize, 15);
        textColor = attr.getColor(R.styleable.RoliedexLayout_textColor,
                                  getColor(android.R.color.black));
        final int numberOfDigits = attr.getInt(R.styleable.RoliedexLayout_numberOfDigits, 3);
        final int numberOfDecimals = attr.getInt(R.styleable.RoliedexLayout_numberOfDecimals, 2);
        slideInAnimation = attr.getResourceId(R.styleable.RoliedexLayout_slideInAnimation,
                                              R.anim.slide_in_from_bottom);
        slideOutAnimation = attr.getResourceId(R.styleable.RoliedexLayout_slideOutAnimation,
                                               R.anim.slide_out_to_top);
        attr.recycle();

        random = new Random();
        digits = new TextSwitcher[numberOfDigits];
        decimals = new TextSwitcher[numberOfDecimals];

        if (isInEditMode()) {
            return;
        }

        TextSwitcher ts;

        for (int i = numberOfDigits - 1; i >= 0; i--) {
            ts = new TextSwitcher(context);
            setupTextSwitcher(ts);
            digits[i] = ts;
            addView(ts);
        }

        decimalPoint = new TextSwitcher(context);
        setupTextSwitcher(decimalPoint);
        decimalPoint.setInAnimation(context,
                                    slideInAnimation); // override it, don't want the callback
        addView(decimalPoint);

        for (int i = 0; i < numberOfDecimals; i++) {
            ts = new TextSwitcher(context);
            setupTextSwitcher(ts);
            decimals[i] = ts;
            addView(ts);
        }

        decorator = new TextSwitcher(context);
        setupTextSwitcher(decorator);
        decorator.setInAnimation(context,
                                 slideInAnimation); // override it, don't want the callback
        addView(decorator);
    }

    private int getColor(@ColorRes int id) {
        return ContextCompat.getColor(getContext(), id);
    }

    private void setupTextSwitcher(TextSwitcher textSwitcher) {
        textSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                TextView textView = new TextView(getContext());
                textView.setTextSize(textSize);
                textView.setTextColor(textColor);
                return textView;
            }
        });
        textSwitcher.setInAnimation(getInAnimation());
        textSwitcher.setOutAnimation(getContext(), slideOutAnimation);
    }

    private Animation getInAnimation() {
        Animation animation = AnimationUtils.loadAnimation(getContext(),
                                                           slideInAnimation);
        animation.setAnimationListener(new TextSwitcherAnim());
        return animation;
    }

    private class TextSwitcherAnim implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            startNextAnimation();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    }

    public void startNextAnimation() {
        // multiple animations will call this method
        if (animationEnded) {
            return;
        }
        final long animationTime = System.currentTimeMillis() - animationStartTime;
        if (animationTime >= animDuration) {
            finishAnimation();
            return;
        }

        spinAllSwitchers();
    }

    private void spinAllSwitchers() {
        // deal with digits
        digits[0].setText(DIGITS[random.nextInt(10)]);

        int multiplier = 1;
        for (int i = 1; i < digits.length; i++) {
            if (digits[i].getVisibility() == GONE) {
                // reached the end, quit
                break;
            }
            multiplier *= 10;
            if ((value * multiplier) > (multiplier - 1)) {
                digits[i].setText(DIGITS[random.nextInt(10)]);
            }
        }

        if (value % 1 > 0 || forceDedimal) {
            for (final TextSwitcher decimal : decimals) {
                decimal.setText(DIGITS[random.nextInt(10)]);
            }
        }
    }

    private void finishAnimation() {
        animationEnded = true;

        // always set at least one digit
        digits[0].setText(DIGITS[digNums[0]]);

        int multiplier = 1;
        for (int i = 1; i < digits.length; i++) {
            multiplier *= 10;
            if ((value * multiplier) > multiplier) {
                digits[i].setText(DIGITS[digNums[i]]);
            }
        }

        if (value % 1 > 0 || forceDedimal) {
            for (int i = 0; i < decimals.length; i++) {
                decimals[i].setText(DIGITS[decNums[i]]);
            }
        }
    }

    /**
     * Sets the text to be displayed. This text will spin before being shown at the end
     * of the animation.
     *
     * @param value        value of number to display
     * @param decorator    text to display behind the number
     * @param forceDecimal even if decimals are all zeros, show them. Else, they are hidden
     */
    public void setText(double value, @Nullable String decorator, boolean forceDecimal) {
        animationEnded = true;
        this.forceDedimal = forceDecimal;
        this.value = value;

        clearPastDigitsAndDecimals();

        setupNewDigitsAndDecimals();
        setupDigitsVisibility();
        setupDecimalsVisibility();
        setupDecorator(decorator);

        animationStartTime = System.currentTimeMillis();
        animationEnded = false;
        startNextAnimation();
    }

    protected void clearPastDigitsAndDecimals() {
        digNums = new int[digits.length];
        decNums = new int[decimals.length];
    }

    private void setupDecorator(String decorator) {
        if (decorator == null) {
            this.decorator.setVisibility(GONE);
        } else {
            this.decorator.setVisibility(VISIBLE);
            this.decorator.setText(decorator);
        }
    }

    private void setupDigitsVisibility() {
        for (TextSwitcher ts : digits) {
            ts.setVisibility(VISIBLE);
        }
        int zeroIndex = -1;
        for (int i = digNums.length - 1; i >= 0; i--) {
            if (digNums[i] != 0) {
                break;
            }
            zeroIndex = i;
        }

        if (zeroIndex == -1) {
            // they all have sig digits
            return;
        }
        if (zeroIndex == 0) {
            // all zeros, hide all minus last
            for (int i = 1; i < digits.length; i++) {
                digits[i].setVisibility(GONE);
            }
            return;
        }
        for (int i = zeroIndex; i < digits.length; i++) {
            digits[i].setVisibility(GONE);
        }
    }

    private void setupDecimalsVisibility() {
        if (value % 1 > 0 || forceDedimal) {
            decimalPoint.setVisibility(VISIBLE);
            for (TextSwitcher ts : decimals) {
                ts.setVisibility(VISIBLE);
            }
            decimalPoint.setText(getContext().getString(R.string.decimal));
        } else {
            decimalPoint.setVisibility(GONE);
            for (TextSwitcher ts : decimals) {
                ts.setVisibility(GONE);
            }
        }
    }

    private void setupNewDigitsAndDecimals() {
        final String valueAsString = Double.toString(value);
        int decimalIndex = valueAsString.indexOf('.');
        final char[] digits = valueAsString.toCharArray();
        if (decimalIndex == -1) {
            decimalIndex = digits.length;
        }

        int index = 0;
        for (int i = decimalIndex - 1; i >= 0 && index < digNums.length; i--, index++) {
            digNums[index] = ((int) digits[i]) - 48; // char ASCII 48 = Decimal 0
        }

        if (value % 1 > 0 || forceDedimal) {
            index = 0;
            for (int i = decimalIndex + 1; i < digits.length && index < decNums.length; i++, index++) {
                decNums[index] = ((int) digits[i]) - 48; // char ASCII 48 = Decimal 0
            }
        }
    }
}
