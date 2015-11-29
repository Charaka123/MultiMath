package com.lbex.charaka.multimath;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView quoter = (TextView) findViewById(R.id.dailyq);
        ArrayList<String> quote = new ArrayList<String>();
        quote.add("The only way to learn maths is to do it");
        quote.add("Pure mathematics is, in its way, the poetry of logical ideas.  ~Albert Einstein");
        quote.add("Math is radical!  ~Bumper Sticker");
        quote.add("The human mind has never invented a labor-saving machine equal to algebra.  ");
        quote.add("A mathematician is a device for turning coffee into theorems. ~Paul Erdos");
        quote.add("Mathematics is as much an aspect of culture as it is a collection of algorithms. ~Carl Boyer");
        quote.add("Mathematics is the music of reason. ~James Joseph Sylvester");
        quote.add("Mathematics is the most beautiful and most powerful creation of the human spirit.");
        int randomnum = (int) (Math.random()*quote.size());
        quoter.setText(quote.get(randomnum));
        final Button startGame=(Button) findViewById(R.id.bnew);
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent game = new Intent(MainActivity.this,Game.class);
                startActivity(game);
            }
        });

        final Button exitGame=(Button) findViewById(R.id.bexit);
        exitGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
