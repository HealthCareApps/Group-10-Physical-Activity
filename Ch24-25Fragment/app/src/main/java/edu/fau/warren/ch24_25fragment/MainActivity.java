package edu.fau.warren.ch24_25fragment;

import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.Fragment;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentActivityFragment frag = new FragmentActivityFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, frag).commit();

        final TextView t1 = (TextView) findViewById(R.id.textView);
        final TextView t2 = (TextView) findViewById(R.id.textView2);

        t1.setBackgroundColor(Color.LTGRAY);


        t1.setOnClickListener(new View.OnClickListener(){
            FragmentActivityFragment frag = new FragmentActivityFragment();
            @Override
            public void onClick(View v) {
                android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, frag);
                transaction.commitAllowingStateLoss();
                t1.setBackgroundColor(Color.LTGRAY);
                t2.setBackgroundColor(Color.WHITE);
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
