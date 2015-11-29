package com.lbex.charaka.multimath;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import javax.xml.transform.Result;

/**
 * Created by Charaka on 26/11/2015.
 *
 */
public class Rapid extends Activity{
    CountDownTimer c;
    int answer;
    int regenLevel;
    int numWins;
    int counter;
    int milTime=60000;
    int TotalTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        //Sets up all of the widgets
        final TextView displayLevel=(TextView) findViewById(R.id.tLevel);
        final TextView questions = (TextView) findViewById(R.id.tQuestions);
        final Button checkAnswer = (Button) findViewById(R.id.bCheck);
        final TextView Num1 = (TextView) findViewById(R.id.tNum1);
        final TextView Num2 = (TextView) findViewById(R.id.tNum2);
        final TextView Timer = (TextView) findViewById(R.id.Timer);
        final TextView Operator = (TextView) findViewById(R.id.tOp);
        final EditText value = (EditText) findViewById(R.id.editText);
        //Setups all widgets

        //Calls makeValue method which returns an int value of the answer
        answer = makeValues(Num1, Num2, Operator);

        checkAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    public boolean checkAnswer(String answer, String value) {
        //This method checks whether the two string values are equal and returns a boolean
        if (Objects.equals(answer, value)) {
            //Adds to the counter
            numWins++;
            return true;

        } else {
            //Indicates the answer is wrong
            return false;

        }

    }
    public int makeValues(TextView Number1, TextView Number2, TextView Operator) {
        //Generates a two random ints depending on the level
        int n1 = (int) (Math.random() * regenLevel);
        int n2 = (int) (Math.random() * regenLevel);

        int num1=0;
        int num2=0;

        //Validate ints and assign them depending on the larger number
        //This prevents minus numbers from being entered
        if (n1>n2){
            num1 =n1;
            num2 =n2;
        }
        else if (n1<n2){
            num1 =n2;
            num2 =n1;
        }

        int answ = 0;
        String[] op = {"-", "*", "+"};
        //Gets a random operator from the string array
        int opindex = (int) (Math.random() * op.length);
        String operator = op[opindex];

        //Depending on the operator the appropriate calculation is done
        if (Objects.equals(operator, "/")) {
            //Division
            answ = num1 / num2;
        } else if (Objects.equals(operator, "-")) {
            //Subtraction
            answ = num1 - num2;
        } else if (Objects.equals(operator, "*")) {
            //Multiplication
            answ = num1 * num2;
        } else if (Objects.equals(operator, "+")) {
            //Addition
            answ = num1 + num2;
        }

        //The screen is setup
        //Number 1 is displayed
        Number1.setText(Integer.toString(num1));
        //Number 2 is displayed
        Number2.setText(Integer.toString(num2));
        //Operator is displayed
        Operator.setText(operator);

        //The answer for the question is returned
        return answ;
    }

    public void StartTimer(final TextView timer, final TextView Num1, final TextView Num2, final TextView Operator,final EditText value){
        c = new CountDownTimer(milTime, 1000) {

            public void onTick(long millisUntilFinished) {
                //Dispalys time to the  =user
                timer.setText("00:" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                //Adds to the game counter
                counter++;
                //Must end game if time is up and counter>=10

                //Sets up the results page if the game ends (counter exceeds 10)
                String userAnswer = value.getText().toString();
                checkAnswer(Integer.toString(answer), userAnswer);
                Intent result = new Intent(Rapid.this, Results.class);
                //Send relevant data to the results page
                result.putExtra("wins", Integer.toString(numWins));
                result.putExtra("time", Integer.toString(TotalTime));

                startActivity(result);
                //Cancels the timer
                c.cancel();

                //Adds corresponding time to the total timer
                TotalTime+=(milTime/1000);
                //Sets up page and provides an answer value
                answer = makeValues(Num1, Num2, Operator);
                //cancels previous timer
                c.cancel();
                //Starts new timer
                StartTimer(timer, Num1, Num2, Operator,value);
            }
        };
        c.start();
    }
}
