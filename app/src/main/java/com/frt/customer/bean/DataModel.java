package com.frt.customer.bean;

/**
 * Created by techiestown on 21/12/17.
 */

public class DataModel {

    public boolean isItemSelected = false;
    public int icon;
    public String name;

    // Constructor.
    public DataModel(int icon, String name, boolean isItemSelected) {

        this.icon = icon;
        this.name = name;
        this.isItemSelected = isItemSelected;
    }

    public boolean isItemSelected() {
        return isItemSelected;
    }

    public void setItemSelected(boolean itemSelected) {
        isItemSelected = itemSelected;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
