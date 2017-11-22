package com.jungu.simplecalculator;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.widget.Toast;
import java.text.NumberFormat;
import java.text.ParseException;

/*
    AppCompatEditText를 상속받은 Custom View 클래스이며,
    피연산자와 연산자를 저장하여 연산합니다.
*/
public class EdtCalculator extends AppCompatEditText
{
    private NumberFormat numberFormat = NumberFormat.getInstance();
    private BtnOperator currentOperator;
    private Number leftOperand;

    /* Custom View 사용을 위한 생성자입니다. */
    public EdtCalculator(Context context)
    {
        super(context);
    }

    /* Custom View 사용을 위한 생성자입니다. */
    public EdtCalculator(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    /* Custom View 사용을 위한 생성자입니다. */
    public EdtCalculator(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    /* 입력 중인 피연산자를 Number 형식으로 반환하는 메소드입니다. */
    public Number getOperand()
    {
        return parseOperand(getFormattedOperand());
    }

    /* 입력 중인 피연산자를 서식화된 String 형식으로 반환하는 메소드입니다. */
    public String getFormattedOperand()
    {
        String string = getText().toString();
        return (string.isEmpty()) ? "0" : string;
    }

    /* 입력 중인 피연산자를 서식화되지 않은 String 형식으로 반환하는 메소드입니다. */
    public String getNonFormattedNumber()
    {
        return getOperand().toString();
    }

    /* Number 형식의 값을 피연산자로 등록하는 메소드입니다. */
    private void setOperand(Number number)
    {
        setText(numberFormat.format(number));
    }

    /* String 형식의 값을 피연산자로 등록하는 메소드입니다. */
    private void setOperand(String string)
    {
        setText(numberFormat.format(parseOperand(string)));
    }

    /* 입력 중인 피연산자에 Number 형식의 값을 덧붙이는 메소드입니다. */
    public void addOperand(Number number)
    {
        addOperand(number.toString());
    }

    /* 입력 중인 피연산자에 String 형식의 값을 덧붙이는 메소드입니다. */
    public void addOperand(String string)
    {
        setOperand(getNonFormattedNumber() + string);
    }

    /* 입력 중인 피연산자의 가장 최근에 눌린 피연산자를 지우는 메소드입니다. */
    public void deleteOperand()
    {
        String string = getNonFormattedNumber();
        setOperand(string.substring(0, string.length() - 1));
    }

    /* 서식화된 String 형식의 값을 Number 형식으로 변환해주는 메소드입니다. */
    private Number parseOperand(String string)
    {
        Number number;
        try {
            number = numberFormat.parse(string);
        }
        catch (ParseException e) {
            number = (string.isEmpty()) ? 0 : Double.NaN;
        }
        return number;
    }

    /* 계산을 위해 BtnOperator를 등록하는 메소드입니다. */
    public void setOperator(BtnOperator btnOperator)
    {
        if (currentOperator == null)
        {
            currentOperator = btnOperator;
            leftOperand = getOperand(); // 입력 중이었던 피연산자를 좌편의 피연산자로 옮깁니다.
            setText(null);
        }
        else {
            Toast.makeText(getContext(), R.string.duplicateOperator, Toast.LENGTH_SHORT).show();
        }
    }

    /* 등록된 BtnOperator를 통해 피연산자들을 연산하는 메소드입니다. */
    public void calculate()
    {
        if (!getText().toString().isEmpty())
        {
            if (currentOperator != null)
            {
                Number result = currentOperator.calculate(leftOperand, getOperand());
                if (!Double.isNaN(result.doubleValue())) {
                    // 연산이 정상적으로 처리되었다면, 결과값으로 계산기를 초기화합니다.
                    reset(result);
                }
            }
            else {
                Toast.makeText(getContext(), R.string.noOperator, Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getContext(), R.string.noOperand, Toast.LENGTH_SHORT).show();
        }
    }

    /* 계산기를 초기화하는 메소드입니다. */
    public void reset(Number number)
    {
        currentOperator = null;
        setOperand(number);
    }
}