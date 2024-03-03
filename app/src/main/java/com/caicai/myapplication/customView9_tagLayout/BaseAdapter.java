package com.caicai.myapplication.customView9_tagLayout;


import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseAdapter {

    // 有多少条目
    public abstract int getCount();

    // 通过position获得view
    public abstract View getView(int  position, ViewGroup parent);

    // 观察者模式及时通知更新

    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }
}
