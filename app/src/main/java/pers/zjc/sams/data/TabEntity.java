package pers.zjc.sams.data;

import com.flyco.tablayout.listener.CustomTabEntity;

public class TabEntity implements CustomTabEntity {

    private String tabTitle;
    private int tabSelectedIcon;
    private int tabUnselectedIcon;

    public TabEntity(String tabTitle, int tabSelectedIcon, int tabUnselectedIcon) {
        this.tabTitle = tabTitle;
        this.tabSelectedIcon = tabSelectedIcon;
        this.tabUnselectedIcon = tabUnselectedIcon;
    }

    @Override
    public String getTabTitle() {
        return tabTitle;
    }

    @Override
    public int getTabSelectedIcon() {
        return tabSelectedIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return tabUnselectedIcon;
    }
}
