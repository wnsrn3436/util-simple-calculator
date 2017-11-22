package com.jungu.simplecalculator;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

/*
    AppCompatButton를 상속받은 Custom View 클래스이며,
    연산할 때 사용할 OnCalculateListener를 등록할 수 있습니다.
*/
public class BtnOperator extends AppCompatButton
{
    private OnCalculateListener calculateListener;

    /* Custom View 사용을 위한 생성자입니다. */
    public BtnOperator(Context context)
    {
        super(context);
    }

    /* Custom View 사용을 위한 생성자입니다. */
    public BtnOperator(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    /* Custom View 사용을 위한 생성자입니다. */
    public BtnOperator(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    /* OnCalculateListener를 등록하는 메소드입니다. */
    public void setOnCalculateListener(OnCalculateListener callback)
    {
        calculateListener = callback;
    }

    /* 등록된 OnCalculateListener를 이용해 연산하여 결과를 반환하는 메소드입니다.  */
    public Number calculate(Number number1, Number number2)
    {
        if (calculateListener != null) {
            return calculateListener.onCalculate(number1, number2);
        }
        else {
            return Double.NaN;
        }
    }

    /* 계산할 때 사용될 Listener 인터페이스입니다. */
    interface OnCalculateListener
    {
        Number onCalculate(Number number1, Number number2);
    }
}