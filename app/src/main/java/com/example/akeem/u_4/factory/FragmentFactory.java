package com.example.akeem.u_4.factory;

import com.example.akeem.u_4.base.BaseFragment;
import com.example.akeem.u_4.fragment.UIB;
import com.example.akeem.u_4.fragment.UIC;
import com.example.akeem.u_4.fragment.UID;
import com.example.akeem.u_4.fragment.UIE;
import com.example.akeem.u_4.fragment.imager_imp.CartoomFragment;
import com.example.akeem.u_4.fragment.imager_imp.GirlFragment;
import com.example.akeem.u_4.fragment.imager_imp.MusicFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Akeem on 2015/12/31.
 */
public class FragmentFactory {
    public static Map<Integer, BaseFragment> UICACHE = new HashMap<>();
    public static BaseFragment createFragment(int position) {
        BaseFragment fragment = null;
        if (UICACHE.get(position) == null) {
            switch (position) {
                case 0:
                    fragment = new GirlFragment();
                    UICACHE.put(0,fragment);
                    break;
                case 1:
                    fragment = new CartoomFragment();
                    UICACHE.put(1,fragment);
                    break;
                case 2:
                    fragment = new MusicFragment();
                    UICACHE.put(2,fragment);
                    break;
                case 3:
                    fragment = new UID();
                    UICACHE.put(3,fragment);
                    break;
                case 4:
                    fragment = new UIE();
                    UICACHE.put(4,fragment);
                    break;
            }
        } else {
            fragment = UICACHE.get(position);
        }

        return fragment;
    }


}
