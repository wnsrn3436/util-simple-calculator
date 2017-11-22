package com.jungu.simplecalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    private EdtCalculator edtCalculator;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtCalculator = (EdtCalculator) findViewById(R.id.edtCalculator);

        /* 숫자 Buttion과 OnClickListener를 등록합니다. */
        addOnClickInBtnIds(new int[] {
                R.id.btnNumber0, R.id.btnNumber1, R.id.btnNumber2, R.id.btnNumber3, R.id.btnNumber4,
                R.id.btnNumber5, R.id.btnNumber6, R.id.btnNumber7, R.id.btnNumber8, R.id.btnNumber9
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 눌린 숫자 Buttion의 텍스트를 EdtCalculator 객체의 피연산자에 추가합니다.
                edtCalculator.addOperand(((Button) view).getText().toString());
            }
        });

        /* BtnOperator와 OnClickListener를 등록합니다. */
        addBtnOperator(new int[]{
                R.id.btnOperatorPlus, R.id.btnOperatorSubtract,
                R.id.btnOperatorMultiply, R.id.btnOperatorDivision
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 눌린 BtnOperator를 EdtCalculator 객체에 등록합니다.
                edtCalculator.setOperator((BtnOperator) view);
            }
        });

        /* AC Buttion의 OnClickListener를 등록합니다. */
        ((Button) findViewById(R.id.btnUtilAC)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EdtCalculator 객체를 초기화합니다.
                edtCalculator.reset(0);
            }
        });

        /* C Buttion의 OnClickListener를 등록합니다. */
        ((Button) findViewById(R.id.btnUtilC)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EdtCalculator 객체의 최근에 눌린 피연산자를 지웁니다.
                edtCalculator.deleteOperand();
            }
        });

        /* = Buttion의 OnClickListener를 등록합니다. */
        ((Button) findViewById(R.id.btnUtilResult)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EdtCalculator 객체로 연산합니다.
                edtCalculator.calculate();
            }
        });
    }

    /* Buttion의 ID(int[])를 받아 일괄적으로 OnClickListener를 등록해주는 메소드입니다.  */
    private void addOnClickInBtnIds(int[] btnNumberIds, View.OnClickListener callback)
    {
        for (int btnNumberID : btnNumberIds)
        {
            Button btnNumber = (Button) findViewById(btnNumberID);
            btnNumber.setOnClickListener(callback);
        }
    }

    /*
        BtnOperator의 ID(int[])를 받아 일괄적으로 OnClickListener를 등록해주며,
        계산할 때 사용할 OnCalculateListener를 등록해주는 메소드입니다.
     */
    private void addBtnOperator(int[] btnOperatorIds, View.OnClickListener callback)
    {
        /* OnClickListener를 등록합니다. */
        addOnClickInBtnIds(btnOperatorIds, callback);
        /*
            연산자 텍스트를 찾아 알맞은 OnCalculateListener를 등록하며,
            등록된 리스너는 EdtCalculator 객체의 calculate 메소드를 통해 Callback 됩니다.
        */
        for (int btnOperatorID : btnOperatorIds)
        {
            BtnOperator btnOperator = (BtnOperator) findViewById(btnOperatorID);
            switch (btnOperator.getText().toString())
            {
                case "+":
                    btnOperator.setOnCalculateListener(new BtnOperator.OnCalculateListener() {
                        @Override
                        public Number onCalculate(Number number1, Number number2) {
                            return (number1.doubleValue() + number2.doubleValue());
                        }
                    });
                    break;

                case "-":
                    btnOperator.setOnCalculateListener(new BtnOperator.OnCalculateListener() {
                        @Override
                        public Number onCalculate(Number number1, Number number2) {
                            return (number1.doubleValue() - number2.doubleValue());
                        }
                    });
                    break;

                case "*":
                    btnOperator.setOnCalculateListener(new BtnOperator.OnCalculateListener() {
                        @Override
                        public Number onCalculate(Number number1, Number number2) {
                            return (number1.doubleValue() * number2.doubleValue());
                        }
                    });
                    break;

                case "/":
                    btnOperator.setOnCalculateListener(new BtnOperator.OnCalculateListener() {
                        @Override
                        public Number onCalculate(Number number1, Number number2) {
                            if (number2.doubleValue() != 0) {
                                return (number1.doubleValue() / number2.doubleValue());
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), R.string.divideByZero, Toast.LENGTH_SHORT).show();
                                return Double.NaN;
                            }
                        }
                    });
                    break;
            }
        }
    }
}