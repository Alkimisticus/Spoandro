package si.klika.spoandro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import si.klika.spoandro.animations.MatchPitchCallback;

public class TabFragment extends Fragment implements View.OnClickListener {

    public static final String INT_HOME_SCORE = "INT_HOME_SCORE";
    public static final String INT_GUEST_SCORE = "INT_GUEST_SCORE";
    public static final String ARG_BASE_COLOR = "ARG_BASE_COLOR";
    public static final String ARG_HIGHLIGHT_COLOR = "ARG_HIGHLIGHT_COLOR";
    public static final String ARG_BACKGROUND_IMAGE = "ARG_BACKGROUND_IMAGE";
    public static final String ARG_ANIMATION = "ARG_ANIMATION";

    private int intHomeScore = 0;
    private int intGuestScore = 0;

    private int baseColor;
    private int highlightColor;
    private int backgroundImage;
    private int animation;

    private EditText homeScore;
    private EditText guestScore;
    private FrameLayout courts;

    public static TabFragment newInstance(int baseColor,int highlightColor, int backgroundImage, int animation) {
        Bundle args = new Bundle();
        args.putInt(ARG_BASE_COLOR, baseColor);
        args.putInt(ARG_HIGHLIGHT_COLOR, highlightColor);
        args.putInt(ARG_BACKGROUND_IMAGE, backgroundImage);
        args.putInt(ARG_ANIMATION, animation);
        TabFragment fragment = new TabFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseColor = getArguments().getInt(ARG_BASE_COLOR);
        highlightColor = getArguments().getInt(ARG_HIGHLIGHT_COLOR);
        backgroundImage = getArguments().getInt(ARG_BACKGROUND_IMAGE);
        animation = getArguments().getInt(ARG_ANIMATION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment, container, false);

        homeScore = (EditText) view.findViewById(R.id.homeScore);
        guestScore = (EditText) view.findViewById(R.id.guestScore);

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

        // Add view
        courts = (FrameLayout) view.findViewById(R.id.courts);
        ImageView newView = (ImageView) inflater.inflate(animation, courts, false);
        newView.setImageResource(backgroundImage);
        courts.addView(newView,0);

        // Set background
        courts.setBackgroundColor(ContextCompat.getColor(getActivity(),baseColor));

        //Send button
        Button sendButton = (Button) view.findViewById(R.id.sendButton);
        sendButton.setTextColor(ContextCompat.getColor(getActivity(),baseColor));
        sendButton.setOnClickListener(this);

        return view;
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            ((MainActivity)getActivity()).changeStyle(baseColor,highlightColor);
        }
    }

    @Override
    public void onClick(View v) {
        homeScore.clearFocus();
        guestScore.clearFocus();

        int homeRes = Integer.valueOf(homeScore.getText().toString());
        int guestRes = Integer.valueOf(guestScore.getText().toString());

        ((MatchPitchCallback) courts.getChildAt(0)).setResult(homeRes,guestRes);
    }
}
