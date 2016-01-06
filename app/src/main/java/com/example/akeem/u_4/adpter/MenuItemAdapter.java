package com.example.akeem.u_4.adpter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.TextViewCompat;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.akeem.u_4.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Akeem on 2016/1/5.
 */
public class MenuItemAdapter  extends BaseAdapter {


    private final int mIconSize;
    private LayoutInflater mInflater;
    private Context mContext;

    public MenuItemAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mIconSize = context.getResources().getDimensionPixelSize(R.dimen.drawer_icon_size);
    }

    private List<LvMenuItem> mItems = new ArrayList<LvMenuItem>(
            Arrays.asList(
                    new LvMenuItem(R.drawable.ic_dashboard, "Home"),
                    new LvMenuItem(R.drawable.ic_event, "Messages"),
                    new LvMenuItem(R.drawable.ic_headset, "Music"),
                    new LvMenuItem(R.drawable.ic_forum, "Discussion"),
                    new LvMenuItem(),
                    new LvMenuItem("Sub Items"),
                    new LvMenuItem(R.drawable.ic_dashboard, "Sub Item 1"),
                    new LvMenuItem(R.drawable.ic_forum, "Sub Item 2")
            ));


    @Override
    public int getCount() {
        return mItems.size();
    }


    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LvMenuItem item = mItems.get(position);
        switch (item.type) {
            case LvMenuItem.TYPE_NORMAL:
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.design_drawer_item, parent,
                            false);
                }
                TextView itemView = (TextView) convertView;
                itemView.setText(item.name);
                Drawable icon = mContext.getResources().getDrawable(item.icon);
                setIconColor(icon);
                if (icon != null) {
                    icon.setBounds(0, 0, mIconSize, mIconSize);
                    TextViewCompat.setCompoundDrawablesRelative(itemView, icon, null, null, null);
                }

                break;
            case LvMenuItem.TYPE_NO_ICON:
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.design_drawer_item_subheader,
                            parent, false);
                }
                TextView subHeader = (TextView) convertView;
                subHeader.setText(item.name);
                break;
            case LvMenuItem.TYPE_SEPARATOR:
                if (convertView == null) {
                    convertView = mInflater.inflate(R.layout.design_drawer_item_separator,
                            parent, false);
                }
                break;
        }

        return convertView;
    }

    public void setIconColor(Drawable icon) {
        int textColorSecondary = android.R.attr.textColorSecondary;
        TypedValue value = new TypedValue();
        if (!mContext.getTheme().resolveAttribute(textColorSecondary, value, true)) {
            return;
        }
        int baseColor = mContext.getResources().getColor(value.resourceId);
        icon.setColorFilter(baseColor, PorterDuff.Mode.MULTIPLY);
    }

    private static class LvMenuItem {
        public LvMenuItem(int icon, String name) {
            this.icon = icon;
            this.name = name;

            if (icon == NO_ICON && TextUtils.isEmpty(name)) {
                type = TYPE_SEPARATOR;
            } else if (icon == NO_ICON) {
                type = TYPE_NO_ICON;
            } else {
                type = TYPE_NORMAL;
            }

            if (type != TYPE_SEPARATOR && TextUtils.isEmpty(name)) {
                throw new IllegalArgumentException("you need set a name for a non-SEPARATOR item");
            }


        }

        public LvMenuItem(String name) {
            this(NO_ICON, name);
        }

        public LvMenuItem() {
            this(null);
        }

        private static final int NO_ICON = 0;
        public static final int TYPE_NORMAL = 0;
        public static final int TYPE_NO_ICON = 1;
        public static final int TYPE_SEPARATOR = 2;

        int type;
        String name;
        int icon;


    }
}
