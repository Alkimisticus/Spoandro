package si.klika.spoandro;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set data
        List<TabData> tabsData = new ArrayList<TabData>();
        tabsData.add(new TabData(getString(R.string.basketball_button),R.color.color_basketball,R.color.color_basketball_dark,R.drawable.basketball_court,R.layout.line_animation));
        tabsData.add(new TabData(getString(R.string.football_button),R.color.color_football,R.color.color_football_dark,R.drawable.football_court,R.layout.slug_animation));
        tabsData.add(new TabData(getString(R.string.tennis_button),R.color.color_tennis,R.color.color_tennis_dark,R.drawable.tennis_court,R.layout.no_animation));
        tabsData.add(new TabData("Kr neki",R.color.color_football,R.color.color_tennis_dark,R.drawable.basketball_court,R.layout.slug_animation));

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new TabsAdapter(getSupportFragmentManager(),MainActivity.this,tabsData));

        tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        // remove Actionbar shadow
        getSupportActionBar().setElevation(0);
    }

    public void changeStyle(int actionBarColor, int toolbarColor) {

        actionBarColor = ContextCompat.getColor(this,actionBarColor);

        //Action bar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(actionBarColor));

        //Toolbar
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(ContextCompat.getColor(this,toolbarColor));
        }

        //Tabs
        tabLayout.setBackgroundColor(actionBarColor);
    }

}
