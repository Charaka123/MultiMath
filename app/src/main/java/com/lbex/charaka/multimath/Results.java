package com.lbex.charaka.multimath;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Charaka on 08/11/2015.
 */
public class Results extends Activity {
    int level=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Binds java file with results.xml
        setContentView(R.layout.results);

        //Sets up all of the widgets
        final Button Level =(Button) findViewById(R.id.bLevel);
        final Button Menu =(Button) findViewById(R.id.bMenu);
        final TextView tLevel =(TextView) findViewById(R.id.tLevel);
        final TextView timetake = (TextView) findViewById(R.id.tTime);
        final TextView result = (TextView) findViewById(R.id.tScore);
        final TextView average = (TextView) findViewById(R.id.tAverage);
        final TextView grade = (TextView) findViewById(R.id.tSkill);

        //Grabs Data from the game page
        Bundle extras = getIntent().getExtras();
        String time = extras.getString("time");
        String wins = extras.getString("wins");
        String levels = extras.getString("level");

        //Sets level from previous XML
        level=Integer.parseInt(levels);
        //Sets the time taken by the user from previous XML
        String Timetaken = ("Time Taken: "+time+" Seconds");
        //Sets users score
        String score = ("You Scored " +wins+"/10");

        //Display level
        tLevel.setText("Level: "+levels);
        //Display the time taken per second ratio by calculating the average using the Method AverageTime()
        average.setText("Time Per Question:"+ AverageTime(Integer.parseInt(time),10)+" Seconds");
        //Sets users grade/rating depending on the score
        grade.setText("Skill Level: "+GradeValue(Integer.parseInt(wins)));
        //Display the users score
        result.setText(score);
        //Display the total time taken
        timetake.setText(Timetaken);

        //Takes the user to the game page when the user clicks the button
        Level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Starts the next level and passes the previous level number to the game page
                Intent nextLevel = new Intent(Results.this,Game.class);
                nextLevel.putExtra("levels", Integer.toString(level + 1));
                startActivity(nextLevel);
            }
        });
        //Takes the user to the Main Menu
        Menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Menu = new Intent(Results.this,MainActivity.class);
                startActivity(Menu);
            }
        });
    }
    public String AverageTime(int time,int questions){
        int average = time/questions;
        return Integer.toString(average);
    }
    public String GradeValue(int mark){
        String grade="Grade";
        if(mark==10){
            grade = "Ace";
        }
        else if (mark>=8){
            grade = "Maths Wiz";
        }
        else if (mark>=5){
            grade = "Bit more effort";
        }
        else if (mark>=3){
            grade = "Can do better";
        }
        else if (mark>0){
            grade = "Try again";
        }
        else if (mark==0){
            grade = "Practice makes perfect";
        }
        return grade;
    }
}
