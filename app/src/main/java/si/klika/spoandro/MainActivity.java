package si.klika.spoandro;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import si.klika.spoandro.courts.BasketballCourt;
import si.klika.spoandro.courts.FootballCourt;
import si.klika.spoandro.courts.MatchPitchCallback;
import si.klika.spoandro.courts.TennisCourt;

public class MainActivity extends AppCompatActivity {

    private Button footballCourtButton;
    private Button basketballCourtButton;
    private Button tennisCourtButton;
    private Button sendButton;
    private LinearLayout buttons;
    private FrameLayout courts;
    private EditText homeScore;
    private EditText guestScore;
    private TennisCourt tennisCourt;
    private BasketballCourt basketballCourt;
    private FootballCourt footballCourt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Buttons
        buttons = (LinearLayout) findViewById(R.id.buttons);
        footballCourtButton = (Button) findViewById(R.id.FootballCourtButton);
        basketballCourtButton = (Button) findViewById(R.id.BasketballCourtButton);
        tennisCourtButton = (Button) findViewById(R.id.TennisCourtButton);

        // Courts
        courts = (FrameLayout) findViewById(R.id.courts);
        tennisCourt = (TennisCourt) findViewById(R.id.courtTennis);
        basketballCourt = (BasketballCourt) findViewById(R.id.courtBasketball);
        footballCourt = (FootballCourt) findViewById(R.id.courtFootball);

        // Set score
        homeScore = (EditText) findViewById(R.id.homeScore);
        guestScore = (EditText) findViewById(R.id.guestScore);
        sendButton = (Button) findViewById(R.id.sendButton);

        // Disable sendinf empty value, always set to 0
        homeScore.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus == false && homeScore.length()==0) {
                    homeScore.setText("0");
                }
            }
        });

        guestScore.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus == false && guestScore.length()==0) {
                    guestScore.setText("0");
                }
            }
        });

        // remove Actionbar shadow
        getSupportActionBar().setElevation(0);

        // football court is default court
        footballCourtButton.setActivated(true);
        footballCourt.setVisibility(View.VISIBLE);
        changeStyle(R.color.color_football,R.color.color_football_dark);

        footballCourtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!footballCourtButton.isActivated()) {
                    footballCourtButton.setActivated(true);
                    footballCourt.setVisibility(View.VISIBLE);
                    basketballCourtButton.setActivated(false);
                    basketballCourt.setVisibility(View.GONE);
                    tennisCourtButton.setActivated(false);
                    tennisCourt.setVisibility(View.GONE);
                    courts.setBackground(getDrawable(R.color.color_football));
                    changeStyle(R.color.color_football,R.color.color_football_dark);
                }

            }
        });

        basketballCourtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!basketballCourtButton.isActivated()) {
                    basketballCourtButton.setActivated(true);
                    basketballCourt.setVisibility(View.VISIBLE);
                    footballCourtButton.setActivated(false);
                    footballCourt.setVisibility(View.GONE);
                    tennisCourtButton.setActivated(false);
                    tennisCourt.setVisibility(View.GONE);
                    courts.setBackground(getDrawable(R.color.color_basketball));
                    changeStyle(R.color.color_basketball,R.color.color_basketball_dark);
                }

            }
        });

        tennisCourtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!tennisCourtButton.isActivated()) {
                    tennisCourtButton.setActivated(true);
                    tennisCourt.setVisibility(View.VISIBLE);
                    footballCourtButton.setActivated(false);
                    footballCourt.setVisibility(View.GONE);
                    basketballCourtButton.setActivated(false);
                    basketballCourt.setVisibility(View.GONE);
                    changeStyle(R.color.color_tennis,R.color.color_tennis_dark);
                }

            }
        });


    }

    private void changeStyle(int actionBarColor, int toolbarColor) {

        actionBarColor = getResources().getColor(actionBarColor);

        //Action bar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(actionBarColor));

        //Toolbar
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(getResources().getColor(toolbarColor));
        }

        //Buttons background
        buttons.setBackgroundColor(actionBarColor);

        //Courts background
        courts.setBackgroundColor(actionBarColor);

        //Send button
        sendButton.setTextColor(actionBarColor);

    }


    public void setScore(View v) {
        homeScore.clearFocus();
        guestScore.clearFocus();

        int homeRes = Integer.valueOf(homeScore.getText().toString());
        int guestRes = Integer.valueOf(guestScore.getText().toString());

        for(int x  = 0;  x < courts.getChildCount() ; x++) {
            if(courts.getChildAt(x) instanceof MatchPitchCallback && courts.getChildAt(x).getVisibility() == View.VISIBLE) {
                ((MatchPitchCallback) courts.getChildAt(x)).setResult(homeRes,guestRes);
            }

        }

    }

}
