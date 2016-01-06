package com.example.akeem.u_4.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Akeem on 2016/1/4.
 */
public class MusicBean   {




    public int is_show_quick_start;
    public int r;
    public int version_max;


    public List<SongBean> song;



    public static class SongBean     implements Serializable ,Parcelable {
        public String aid;
        public String album;
        public String albumtitle;
        public String alert_msg;
        public String artist;
        public String file_ext;
        public String kbps;
        public int length;
        public int like;
        public String picture;
        public String sha256;
        public String sid;
        public String ssid;
        public int status;
        public String subtype;
        public String title;
        public String url;
        /**
         * id : 18498
         * is_site_artist : false
         * name : The Cranberries
         * related_site_id : 0
         */

        public List<SingersBean> singers;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

        }

        public static class SingersBean {
            public String id;
            public boolean is_site_artist;
            public String name;
            public int related_site_id;
        }
    }
}
