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

/**
 * -------------------------------------------------------------------------------------------------
 * Created by Charaka on 13/10/2015.
 * Core Functionality has been completed and implemented
 * Results page is complete with the ability to exchange information
 * -------------------------------------------------------------------------------------------------
 */
public class Game extends Activity {
    //setup level and add division to sting array after level 3

    //initial time of 20 seconds which decreases as the game progresses
    int milTime=20000;
    //level counter which is incremented as the game progresses
    int level = 1;
    //The range of difficulty: random number range is set using this var
    int regenLevel=11;
    //Total question counter
    int counter = 0;
    //Number of questions answered correctly
    int numWins = 0;
    //The answer to the provided question on screen
    int answer;
    //Total time taken by the user per level which is passed to the results page
    int TotalTime = 0;
    //CountDowTimer object reference
    CountDownTimer c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Binds java file with game.xml
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

        //Retrieving the level value from Results.java
        Bundle extras = getIntent().getExtras();
        //Checks if there is data available to use else skips this
        if (extras != null) {
            //Setting value equal to local var Slevel
            String Slevel = extras.getString("levels");
            level = Integer.parseInt(Slevel);
        }

        //Calls makeValue method which returns an int value of the answer
        answer = makeValues(Num1, Num2, Operator);

        //Sets up the level difficulty
        levelSetup(level);

        //Displays current level to the user
        displayLevel.setText("Level: " + level);
        //Starts the game counter
        StartTimer(Timer, Num1, Num2, Operator, value);

        //OnClickListener to perform the following when the user enters an answer
        checkAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Returns text from the editText value and sets it to a var
                String userAnswer = value.getText().toString();
                ++counter;
                //Gets the current time value
                String times = (String) Timer.getText();
                String[] test = times.split(":");
                times = test[1];

                //Adds to the TotalTime var the time taken by the user to answer the question
                TotalTime += ((milTime/1000) - Integer.parseInt(times));

                if (counter >= 10) {
                    //If max number of questions(10) per level is reached then...
                    //Check the users final answer
                    checkAnswer(Integer.toString(answer), userAnswer);

                    //Switch to results page and pass relevant data to setup results page(results.xml)
                    Intent result = new Intent(Game.this, Results.class);
                    result.putExtra("level", Integer.toString(level));
                    result.putExtra("wins", Integer.toString(numWins));
                    result.putExtra("time", Integer.toString(TotalTime));
                    startActivity(result);

                    //Cancels the timer and stops it
                    c.cancel();

                } else {
                    //checkAnswer method returns a true value if the answer is right
                    if (checkAnswer(Integer.toString(answer), userAnswer)) {
                        //If answer is correct then display a toast verifying it is correct
                        Toast.makeText(getApplicationContext(), "Your answer is correct.", Toast.LENGTH_SHORT).show();
                    } else {
                        //If answer is wrong then display a toast verifying it is wrong
                        Toast.makeText(getApplicationContext(), "Your answer was incorrect", Toast.LENGTH_SHORT).show();
                    }

                    //Displays current question
                    questions.setText((counter + 1) + "/10");
                    //Cancels the timer and stops it
                    c.cancel();

                    //Displays a new question and sets the answer equal to the answer(int) var
                    answer = makeValues(Num1, Num2, Operator);
                    StartTimer(Timer, Num1, Num2, Operator, value);

                    //Blanks the edit text
                    value.setText("");
                }
                System.out.println(TotalTime);
                //---DEBUG---
                //System.out.println(counter);
                //System.out.println(numWins);
                //---DEBUG---
            }
        });


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
                if (counter>=10) {

                    //Sets up the results page if the game ends (counter exceeds 10)
                    String userAnswer = value.getText().toString();
                    checkAnswer(Integer.toString(answer), userAnswer);
                    Intent result = new Intent(Game.this, Results.class);
                    //Send relevant data to the results page
                    result.putExtra("level", Integer.toString(level));
                    result.putExtra("wins", Integer.toString(numWins));
                    result.putExtra("time", Integer.toString(TotalTime));

                    startActivity(result);
                    //Cancels the timer
                    c.cancel();
                }

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
    public void levelSetup(int level){
        //Increase the level difficulty
        if(level!=1) {
            //increases the range of random values
            regenLevel += (level * 4);
        }
        if(level>5){
            //Reduces the range of random values
            milTime-=((level-4)*1000);
            //Ensures that the minimum time per question remains at 10 seconds
            //Can have unlimited levels
            if(milTime<=10000){
                milTime=10000;
            }
        }
    }
}
