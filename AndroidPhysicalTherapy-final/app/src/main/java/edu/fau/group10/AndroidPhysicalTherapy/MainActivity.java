package edu.fau.group10.AndroidPhysicalTherapy;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText usernameInput;
    EditText vistaIDInput;
    EditText passwordInput;
    TextView notificationText;
    MyDBHandler dbHandler;

    public static final String BASE_URL = "http://162.252.122.230:3010/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameInput = (EditText) findViewById(R.id.usernameInput);
        vistaIDInput = (EditText) findViewById(R.id.vistaIDInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
        notificationText = (TextView) findViewById(R.id.notificationText);
        dbHandler = new MyDBHandler(this, null, null, 1);

        Button button = (Button) findViewById(R.id.signupButton);
        Button button1 = (Button) findViewById(R.id.loginButton);

        button.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        SignupButtonClicked();

                    }
                }
        );
        button1.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        //Login(); // Login method for data in SQLite
                        loginProcessWithRetrofit();
                    }
                }
        );

    }


    //add new user to database
    public void SignupButtonClicked() {
        if (dbHandler.checkforUser(usernameInput.getText().toString()) == true) {

            Users user = new Users(usernameInput.getText().toString(), passwordInput.getText().toString(), vistaIDInput.getText().toString());
            dbHandler.addUser(user);
            notificationText.setText(usernameInput.getText().toString() + " has been registered");
            vistaIDInput.setText(""); //resets input
        }
        else{
            notificationText.setText(usernameInput.getText().toString() + " is not an available username - Try again");
            vistaIDInput.setText("");
            usernameInput.setText("");
            passwordInput.setText("");
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void Login(user_model user){

        //String storedPassword = dbHandler.getSingleEntry(usernameInput.getText().toString());
        String enteredPassword = passwordInput.getText().toString();

        String storedPassword = user.password;
        String email = user.email;

        if(Objects.equals(enteredPassword, storedPassword)){
            notificationText.setText("Login Successful!");
            setContentView(edu.fau.group10.AndroidPhysicalTherapy.R.layout.activity_main);
            //Load the youtube video as an in app iframe webview
            WebView web = (WebView) findViewById(edu.fau.group10.AndroidPhysicalTherapy.R.id.webView);
            WebSettings websettings = web.getSettings();
            websettings.setJavaScriptEnabled(true);
            //web.loadUrl("https://www.youtube.com/watch?v=2bjt-0FevRY");
            String webpage = "<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/2bjt-0FevRY\"</iframe>";
            web.loadData(webpage, "text/html", null);

            FragmentActivityFragment frag = new FragmentActivityFragment();
            getSupportFragmentManager().beginTransaction().add(edu.fau.group10.AndroidPhysicalTherapy.R.id.fragment_container, frag).commit();
            RelativeLayout myLayout = (RelativeLayout) findViewById(edu.fau.group10.AndroidPhysicalTherapy.R.id.myrel);
            TextView name = (TextView) findViewById(edu.fau.group10.AndroidPhysicalTherapy.R.id.textView10);
            //Dynamic loading points, Insert Name would be replaced to a call to the database to get the users name
            name.setText(email);
            //Load array determines what excercises are loaded.  First number is how many excercises, the rest are ids
            //These numbers would also be replaced by calls to the database.
            //int [] Load = {11, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
            List Load = user.exercises;

            final TextView countdownsec = (TextView) findViewById(edu.fau.group10.AndroidPhysicalTherapy.R.id.textView11);
            final TextView countdownmin = (TextView) findViewById(edu.fau.group10.AndroidPhysicalTherapy.R.id.textView16);
            int TextCount = Load.size();
            final TextView [] exercises = new TextView[TextCount];

            //Load the different textviews' int ids.
            int [] getviews = {0,0,0,0,0,0,0,0,0,0,0,0};
            getviews[0] = edu.fau.group10.AndroidPhysicalTherapy.R.id.textView;
            getviews[1] = edu.fau.group10.AndroidPhysicalTherapy.R.id.textView2;
            getviews[2] = edu.fau.group10.AndroidPhysicalTherapy.R.id.textView5;
            getviews[3] = edu.fau.group10.AndroidPhysicalTherapy.R.id.textView6;
            getviews[4] = edu.fau.group10.AndroidPhysicalTherapy.R.id.textView7;
            getviews[5] = edu.fau.group10.AndroidPhysicalTherapy.R.id.textView12;
            getviews[6] = edu.fau.group10.AndroidPhysicalTherapy.R.id.textView13;
            getviews[7] = edu.fau.group10.AndroidPhysicalTherapy.R.id.textView14;
            getviews[8] = edu.fau.group10.AndroidPhysicalTherapy.R.id.textView17;
            getviews[9] = edu.fau.group10.AndroidPhysicalTherapy.R.id.textView18;
            getviews[10] = edu.fau.group10.AndroidPhysicalTherapy.R.id.textView19;
            getviews[11] = edu.fau.group10.AndroidPhysicalTherapy.R.id.textView20;

            //Change the text on needed textviews as determined by Load
            for (int z = 0; z < TextCount; ++z)
            {
                exercises[z] = (TextView) findViewById(getviews[z]);
                exercises[z].setText(getexer((Integer) Load.get(z+1)));
            }

            //Remove any unused textviews
            TextView temp;
            for (int a = TextCount; a < 12; ++a)
            {
                temp = (TextView) findViewById(getviews[a]);
                myLayout.removeView(temp);
            }

            //Stopwatch implementation
            final ImageView start = (ImageView) findViewById(edu.fau.group10.AndroidPhysicalTherapy.R.id.imageView2);
            final ImageView stop = (ImageView) findViewById(edu.fau.group10.AndroidPhysicalTherapy.R.id.imageView);
            final ImageView reset = (ImageView) findViewById(edu.fau.group10.AndroidPhysicalTherapy.R.id.imageView3);

            final CountDownTimer count = new CountDownTimer(540000, 1000){
                int sec = 0;
                int min = 0;
                String q;
                String l;
                @Override
                public void onTick(long millisUntilFinished) {
                    ++sec;
                    if(sec == 60)
                    {
                        sec = 0;
                        ++min;
                    }
                    q = String.valueOf(sec);
                    l = String.valueOf(min);
                    countdownsec.setText(q);
                    countdownmin.setText(l);
                }

                @Override
                public void onFinish() {
                    sec = 0;
                    min = 0;
                    q = String.valueOf(sec);
                    l = String.valueOf(min);
                    countdownsec.setText(q);
                    countdownmin.setText(l);
                }
            };

            start.setOnClickListener(new View.OnClickListener(){


                @Override
                public void onClick(View v) {
                    count.start();
                }
            });

            stop.setOnClickListener(new View.OnClickListener() {


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

            //Sets the onclick listener for all loaded exercises
            for(int f = 0; f < TextCount; ++f) {
                final int w = f;
                exercises[w].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (exercises[w].getText() == "Ankle Pumps") {
                            FragmentActivityFragment frag = new FragmentActivityFragment();
                            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(edu.fau.group10.AndroidPhysicalTherapy.R.id.fragment_container, frag);
                            transaction.commitAllowingStateLoss();
                            count.cancel();
                            count.onFinish();
                        } else if (exercises[w].getText() == "Quad Sets") {
                            FragmentActivityFragment2 frag = new FragmentActivityFragment2();
                            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(edu.fau.group10.AndroidPhysicalTherapy.R.id.fragment_container, frag);
                            transaction.commitAllowingStateLoss();
                            count.cancel();
                            count.onFinish();
                        } else if (exercises[w].getText() == "Gluteal Sets") {
                            FragmentActivityFragment3 frag = new FragmentActivityFragment3();
                            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(edu.fau.group10.AndroidPhysicalTherapy.R.id.fragment_container, frag);
                            transaction.commitAllowingStateLoss();
                            count.cancel();
                            count.onFinish();
                        } else if (exercises[w].getText() == "Adduction") {
                            FragmentActivityFragment4 frag = new FragmentActivityFragment4();
                            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(edu.fau.group10.AndroidPhysicalTherapy.R.id.fragment_container, frag);
                            transaction.commitAllowingStateLoss();
                            count.cancel();
                            count.onFinish();
                        }else if (exercises[w].getText() == "Short Arc Quads") {
                            FragmentActivityFragment6 frag = new FragmentActivityFragment6();
                            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(edu.fau.group10.AndroidPhysicalTherapy.R.id.fragment_container, frag);
                            transaction.commitAllowingStateLoss();
                            count.cancel();
                            count.onFinish();
                        }else if (exercises[w].getText() == "Seated Range of Motion") {
                            FragmentActivityFragment7 frag = new FragmentActivityFragment7();
                            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(edu.fau.group10.AndroidPhysicalTherapy.R.id.fragment_container, frag);
                            transaction.commitAllowingStateLoss();
                            count.cancel();
                            count.onFinish();
                        }else if (exercises[w].getText() == "Seated Flexion stretch") {
                            FragmentActivityFragment8 frag = new FragmentActivityFragment8();
                            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(edu.fau.group10.AndroidPhysicalTherapy.R.id.fragment_container, frag);
                            transaction.commitAllowingStateLoss();
                            count.cancel();
                            count.onFinish();
                        }else if (exercises[w].getText() == "Straight Leg Raise") {
                            FragmentActivityFragment9 frag = new FragmentActivityFragment9();
                            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(edu.fau.group10.AndroidPhysicalTherapy.R.id.fragment_container, frag);
                            transaction.commitAllowingStateLoss();
                            count.cancel();
                            count.onFinish();
                        }else if (exercises[w].getText() == "Knee extension stretch") {
                            FragmentActivityFragment10 frag = new FragmentActivityFragment10();
                            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(edu.fau.group10.AndroidPhysicalTherapy.R.id.fragment_container, frag);
                            transaction.commitAllowingStateLoss();
                            count.cancel();
                            count.onFinish();
                        }else if (exercises[w].getText() == "Patellar Mobilization") {
                            FragmentActivityFragment11 frag = new FragmentActivityFragment11();
                            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(edu.fau.group10.AndroidPhysicalTherapy.R.id.fragment_container, frag);
                            transaction.commitAllowingStateLoss();
                            count.cancel();
                            count.onFinish();
                        }
                        else {
                            FragmentActivityFragment5 frag = new FragmentActivityFragment5();
                            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                            transaction.replace(edu.fau.group10.AndroidPhysicalTherapy.R.id.fragment_container, frag);
                            transaction.commitAllowingStateLoss();
                            count.cancel();
                            count.onFinish();
                        }
                    }
                });
            }
        }
        else {
            notificationText.setText("Incorrect login credentials - Try again");

        }
    }


    public void printDatabase() {
        String dbString = dbHandler.databaseToString();
        notificationText.setText(dbString);
        vistaIDInput.setText(""); //resets input
        passwordInput.setText("");
    }



    String getexer(int i)
    {
        if (i == 1)
        {
            return "Ankle Pumps";
        }
        else if (i == 2)
        {
            return "Quad Sets";
        }
        else if (i == 3)
        {
            return "Gluteal Sets";
        }
        else if (i == 4)
        {
            return "Adduction";
        }
        else if (i == 5)
        {
            return "Heel Slides";
        }
        else if (i == 6)
        {
            return "Short Arc Quads";
        }
        else if (i == 7)
        {
            return "Seated Range of Motion";
        }
        else if (i == 8)
        {
            return "Seated Flexion stretch";
        }
        else if (i == 9)
        {
            return "Straight Leg Raise";
        }
        else if (i == 10)
        {
            return "Knee extension stretch";
        }
        else if (i == 11)
        {
            return "Patellar Mobilization";
        }
        return "";
    }

    private rest_api getInterfaceService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final rest_api mInterfaceService = retrofit.create(rest_api.class);
        return mInterfaceService;
    }
    private void loginProcessWithRetrofit(){
        String email = usernameInput.getText().toString();
        rest_api mApiService = this.getInterfaceService();
        Call<user_model> mService = mApiService.getUser(email);
        mService.enqueue(new Callback<user_model>() {
            @Override
            public void onResponse(Call<user_model> call, Response<user_model> response) {
                user_model userObject = response.body();
                Login(userObject);
            }

/*
                //String returnedResponse = mLoginObject.isLogin;
                Toast.makeText(MainActivity.this, "Returned " + returnedResponse, Toast.LENGTH_LONG).show();
                //showProgress(false);
                if(returnedResponse.trim().equals("1")){
                    // redirect to Main Activity page
                    Intent loginIntent = new Intent(MainActivity.this, MainActivity.class);
                    loginIntent.putExtra("EMAIL", email);
                    startActivity(loginIntent);
                }
                if(returnedResponse.trim().equals("0")){
                    // use the registration button to register
                    failedLoginMessage.setText(getResources().getString(R.string.registration_message));
                    mPasswordView.requestFocus();
                }*/
            @Override
            public void onFailure(Call<user_model> call, Throwable t) {
                call.cancel();
                Toast.makeText(MainActivity.this, "Please check your network connection and internet permission", Toast.LENGTH_LONG).show();
            }
        });
    }

}



    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(edu.fau.group10.AndroidPhysicalTherapy.R.layout.activity_main);
        //Load the youtube video as an in app iframe webview
        WebView web = (WebView) findViewById(edu.fau.group10.AndroidPhysicalTherapy.R.id.webView);
        WebSettings websettings = web.getSettings();
        websettings.setJavaScriptEnabled(true);
        //web.loadUrl("https://www.youtube.com/watch?v=2bjt-0FevRY");
        String webpage = "<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/2bjt-0FevRY\"</iframe>";
        web.loadData(webpage, "text/html", null);

        FragmentActivityFragment frag = new FragmentActivityFragment();
        getSupportFragmentManager().beginTransaction().add(edu.fau.group10.AndroidPhysicalTherapy.R.id.fragment_container, frag).commit();
        RelativeLayout myLayout = (RelativeLayout) findViewById(edu.fau.group10.AndroidPhysicalTherapy.R.id.myrel);
        TextView name = (TextView) findViewById(edu.fau.group10.AndroidPhysicalTherapy.R.id.textView10);
        //Dynamic loading points, Insert Name would be replaced to a call to the database to get the users name
        name.setText("John Smith");
        //Load array determines what excercises are loaded.  First number is how many excercises, the rest are ids
        //These numbers would also be replaced by calls to the database.
        int [] Load = {11, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        final TextView countdownsec = (TextView) findViewById(edu.fau.group10.AndroidPhysicalTherapy.R.id.textView11);
        final TextView countdownmin = (TextView) findViewById(edu.fau.group10.AndroidPhysicalTherapy.R.id.textView16);
        int TextCount = Load[0];
        final TextView [] exercises = new TextView[TextCount];

        //Load the different textviews' int ids.
        int [] getviews = {0,0,0,0,0,0,0,0,0,0,0,0};
        getviews[0] = edu.fau.group10.AndroidPhysicalTherapy.R.id.textView;
        getviews[1] = edu.fau.group10.AndroidPhysicalTherapy.R.id.textView2;
        getviews[2] = edu.fau.group10.AndroidPhysicalTherapy.R.id.textView5;
        getviews[3] = edu.fau.group10.AndroidPhysicalTherapy.R.id.textView6;
        getviews[4] = edu.fau.group10.AndroidPhysicalTherapy.R.id.textView7;
        getviews[5] = edu.fau.group10.AndroidPhysicalTherapy.R.id.textView12;
        getviews[6] = edu.fau.group10.AndroidPhysicalTherapy.R.id.textView13;
        getviews[7] = edu.fau.group10.AndroidPhysicalTherapy.R.id.textView14;
        getviews[8] = edu.fau.group10.AndroidPhysicalTherapy.R.id.textView17;
        getviews[9] = edu.fau.group10.AndroidPhysicalTherapy.R.id.textView18;
        getviews[10] = edu.fau.group10.AndroidPhysicalTherapy.R.id.textView19;
        getviews[11] = edu.fau.group10.AndroidPhysicalTherapy.R.id.textView20;

        //Change the text on needed textviews as determined by Load
        for (int z = 0; z < TextCount; ++z)
        {
            exercises[z] = (TextView) findViewById(getviews[z]);
            exercises[z].setText(getexer(Load[z+1]));
        }

        //Remove any unused textviews
        TextView temp;
        for (int a = TextCount; a < 12; ++a)
        {
            temp = (TextView) findViewById(getviews[a]);
            myLayout.removeView(temp);
        }



        //Stopwatch implementation
        final ImageView start = (ImageView) findViewById(edu.fau.group10.AndroidPhysicalTherapy.R.id.imageView2);
        final ImageView stop = (ImageView) findViewById(edu.fau.group10.AndroidPhysicalTherapy.R.id.imageView);
        final ImageView reset = (ImageView) findViewById(edu.fau.group10.AndroidPhysicalTherapy.R.id.imageView3);

        final CountDownTimer count = new CountDownTimer(540000, 1000){
            int sec = 0;
            int min = 0;
            String q;
            String l;
            @Override
            public void onTick(long millisUntilFinished) {
                ++sec;
                if(sec == 60)
                {
                    sec = 0;
                    ++min;
                }
                q = String.valueOf(sec);
                l = String.valueOf(min);
                countdownsec.setText(q);
                countdownmin.setText(l);
            }

            @Override
            public void onFinish() {
                sec = 0;
                min = 0;
                q = String.valueOf(sec);
                l = String.valueOf(min);
                countdownsec.setText(q);
                countdownmin.setText(l);
            }
        };


        start.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {
            count.start();
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {


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


        //Sets the onclick listener for all loaded exercises
        for(int f = 0; f < TextCount; ++f) {
            final int w = f;
            exercises[w].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (exercises[w].getText() == "Ankle Pumps") {
                        FragmentActivityFragment frag = new FragmentActivityFragment();
                        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(edu.fau.group10.AndroidPhysicalTherapy.R.id.fragment_container, frag);
                        transaction.commitAllowingStateLoss();
                        count.cancel();
                        count.onFinish();
                    } else if (exercises[w].getText() == "Quad Sets") {
                        FragmentActivityFragment2 frag = new FragmentActivityFragment2();
                        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(edu.fau.group10.AndroidPhysicalTherapy.R.id.fragment_container, frag);
                        transaction.commitAllowingStateLoss();
                        count.cancel();
                        count.onFinish();
                    } else if (exercises[w].getText() == "Gluteal Sets") {
                        FragmentActivityFragment3 frag = new FragmentActivityFragment3();
                        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(edu.fau.group10.AndroidPhysicalTherapy.R.id.fragment_container, frag);
                        transaction.commitAllowingStateLoss();
                        count.cancel();
                        count.onFinish();
                    } else if (exercises[w].getText() == "Adduction") {
                        FragmentActivityFragment4 frag = new FragmentActivityFragment4();
                        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(edu.fau.group10.AndroidPhysicalTherapy.R.id.fragment_container, frag);
                        transaction.commitAllowingStateLoss();
                        count.cancel();
                        count.onFinish();
                    }else if (exercises[w].getText() == "Short Arc Quads") {
                        FragmentActivityFragment6 frag = new FragmentActivityFragment6();
                        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(edu.fau.group10.AndroidPhysicalTherapy.R.id.fragment_container, frag);
                        transaction.commitAllowingStateLoss();
                        count.cancel();
                        count.onFinish();
                    }else if (exercises[w].getText() == "Seated Range of Motion") {
                        FragmentActivityFragment7 frag = new FragmentActivityFragment7();
                        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(edu.fau.group10.AndroidPhysicalTherapy.R.id.fragment_container, frag);
                        transaction.commitAllowingStateLoss();
                        count.cancel();
                        count.onFinish();
                    }else if (exercises[w].getText() == "Seated Flexion stretch") {
                        FragmentActivityFragment8 frag = new FragmentActivityFragment8();
                        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(edu.fau.group10.AndroidPhysicalTherapy.R.id.fragment_container, frag);
                        transaction.commitAllowingStateLoss();
                        count.cancel();
                        count.onFinish();
                    }else if (exercises[w].getText() == "Straight Leg Raise") {
                        FragmentActivityFragment9 frag = new FragmentActivityFragment9();
                        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(edu.fau.group10.AndroidPhysicalTherapy.R.id.fragment_container, frag);
                        transaction.commitAllowingStateLoss();
                        count.cancel();
                        count.onFinish();
                    }else if (exercises[w].getText() == "Knee extension stretch") {
                        FragmentActivityFragment10 frag = new FragmentActivityFragment10();
                        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(edu.fau.group10.AndroidPhysicalTherapy.R.id.fragment_container, frag);
                        transaction.commitAllowingStateLoss();
                        count.cancel();
                        count.onFinish();
                    }else if (exercises[w].getText() == "Patellar Mobilization") {
                        FragmentActivityFragment11 frag = new FragmentActivityFragment11();
                        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(edu.fau.group10.AndroidPhysicalTherapy.R.id.fragment_container, frag);
                        transaction.commitAllowingStateLoss();
                        count.cancel();
                        count.onFinish();
                    }
                    else {
                        FragmentActivityFragment5 frag = new FragmentActivityFragment5();
                        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(edu.fau.group10.AndroidPhysicalTherapy.R.id.fragment_container, frag);
                        transaction.commitAllowingStateLoss();
                        count.cancel();
                        count.onFinish();
                    }
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(edu.fau.group10.AndroidPhysicalTherapy.R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == edu.fau.group10.AndroidPhysicalTherapy.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    String getexer(int i)
    {
        if (i == 1)
        {
            return "Ankle Pumps";
        }
        else if (i == 2)
        {
            return "Quad Sets";
        }
        else if (i == 3)
        {
            return "Gluteal Sets";
        }
        else if (i == 4)
        {
            return "Adduction";
        }
        else if (i == 5)
        {
            return "Heel Slides";
        }
        else if (i == 6)
        {
            return "Short Arc Quads";
        }
        else if (i == 7)
        {
            return "Seated Range of Motion";
        }
        else if (i == 8)
        {
            return "Seated Flexion stretch";
        }
        else if (i == 9)
        {
            return "Straight Leg Raise";
        }
        else if (i == 10)
        {
            return "Knee extension stretch";
        }
        else if (i == 11)
        {
            return "Patellar Mobilization";
        }
        return "";
    }
}*/
