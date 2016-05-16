package si.klika.spoandro;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class TabsAdapter extends FragmentPagerAdapter {

    private Context context;
    private List<TabData> tabsData;

    public TabsAdapter(FragmentManager fm, Context context, List<TabData> tabsData) {
        super(fm);
        this.context = context;
        this.tabsData = tabsData;
    }

    @Override
    public int getCount() {
        return tabsData.size();
    }

    @Override
    public Fragment getItem(int position) {
        return TabFragment.newInstance(tabsData.get(position).getBaseColor()
                ,tabsData.get(position).getHighlightColor()
                ,tabsData.get(position).getBackgroundImage()
                ,tabsData.get(position).getAnimation());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabsData.get(position).getTitle();
    }

}
