package edu.fau.warren.ch24_25fragment;

import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentActivityFragment frag = new FragmentActivityFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, frag).commit();

        final TextView t1 = (TextView) findViewById(R.id.textView);
        final TextView t2 = (TextView) findViewById(R.id.textView2);
        final TextView t3 = (TextView) findViewById(R.id.textView5);
        final TextView t4 = (TextView) findViewById(R.id.textView6);
        final TextView t5 = (TextView) findViewById(R.id.textView7);
        final TextView countdown = (TextView) findViewById(R.id.textView11);

        final Button start = (Button) findViewById(R.id.button);
        final Button stop = (Button) findViewById(R.id.button3);
        final Button reset = (Button) findViewById(R.id.button2);

        final CountDownTimer count = new CountDownTimer(540000, 1000){
            int i = 0;
            String q;
            @Override
            public void onTick(long millisUntilFinished) {
                ++i;
                q = String.valueOf(i);
                countdown.setText(q);
            }

            @Override
            public void onFinish() {
                i = 0;
                q = String.valueOf(i);
                countdown.setText(q);
            }
        };


        start.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
            count.start();
            }
        });

        stop.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
                count.cancel();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                count.onFinish();
            }
        });

        t1.setBackgroundColor(Color.LTGRAY);


        t1.setOnClickListener(new View.OnClickListener() {
            FragmentActivityFragment frag = new FragmentActivityFragment();

            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, frag);
                transaction.commitAllowingStateLoss();
                t1.setBackgroundColor(Color.LTGRAY);
                t2.setBackgroundColor(Color.WHITE);
                t3.setBackgroundColor(Color.WHITE);
                t4.setBackgroundColor(Color.WHITE);
                t5.setBackgroundColor(Color.WHITE);
                count.cancel();
                count.onFinish();
            }
        });

        t2.setOnClickListener(new View.OnClickListener(){
            FragmentActivityFragment2 frag2 = new FragmentActivityFragment2();

            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, frag2);
                transaction.commitAllowingStateLoss();
                t2.setBackgroundColor(Color.LTGRAY);
                t1.setBackgroundColor(Color.WHITE);
                t3.setBackgroundColor(Color.WHITE);
                t4.setBackgroundColor(Color.WHITE);
                t5.setBackgroundColor(Color.WHITE);
                count.cancel();
                count.onFinish();
            }
        });

        t3.setOnClickListener(new View.OnClickListener(){
            FragmentActivityFragment3 frag3 = new FragmentActivityFragment3();

            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, frag3);
                transaction.commitAllowingStateLoss();
                t3.setBackgroundColor(Color.LTGRAY);
                t1.setBackgroundColor(Color.WHITE);
                t2.setBackgroundColor(Color.WHITE);
                t4.setBackgroundColor(Color.WHITE);
                t5.setBackgroundColor(Color.WHITE);
                count.cancel();
                count.onFinish();
            }
        });

        t4.setOnClickListener(new View.OnClickListener(){
            FragmentActivityFragment4 frag4 = new FragmentActivityFragment4();

            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, frag4);
                transaction.commitAllowingStateLoss();
                t4.setBackgroundColor(Color.LTGRAY);
                t1.setBackgroundColor(Color.WHITE);
                t2.setBackgroundColor(Color.WHITE);
                t3.setBackgroundColor(Color.WHITE);
                t5.setBackgroundColor(Color.WHITE);
                count.cancel();
                count.onFinish();
            }
        });

        t5.setOnClickListener(new View.OnClickListener(){
            FragmentActivityFragment5 frag5 = new FragmentActivityFragment5();

            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, frag5);
                transaction.commitAllowingStateLoss();
                t5.setBackgroundColor(Color.LTGRAY);
                t1.setBackgroundColor(Color.WHITE);
                t2.setBackgroundColor(Color.WHITE);
                t3.setBackgroundColor(Color.WHITE);
                t4.setBackgroundColor(Color.WHITE);
                count.cancel();
                count.onFinish();
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
