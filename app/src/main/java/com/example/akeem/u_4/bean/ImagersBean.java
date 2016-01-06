package com.example.akeem.u_4.bean;

import java.util.List;

/**
 * Created by Akeem on 2016/1/2.
 */
public class ImagersBean {



    public String col;
    public int returnNumber;
    public String sort;
    public int startIndex;
    public String tag;
    public String tag3;
    public int totalNum;

    public List<ImgsBean> imgs;

    public static class ImgsBean   {
        public String albumDi;
        public String albumId;
        public String albumName;
        public int albumNum;
        public String albumObjNum;
        public String appId;
        public String bookId;
        public String canAlbumId;
        public String column;
        public String contentSign;
        public String dataSrc;
        public String date;
        public String desc;
        public String downloadUrl;
        public String dressBuyLink;
        public int dressDiscount;
        public String dressExtInfo;
        public String dressId;
        public int dressImgNum;
        public int dressNum;
        public int dressPrice;
        public String dressTag;
        public String fashion;
        public int fromName;
        public String fromPageTitle;
        public String fromUrl;
        public String hostName;
        public String id;
        public int imageHeight;
        public String imageUrl;
        public int imageWidth;
        public int isAlbum;
        public String isBook;
        public int isDapei;
        public int isVip;
        public String objTag;
        public String objUrl;
        /**
         * budgetNum :
         * cert :
         * contactName :
         * isHunjia :
         * isJiaju :
         * isLanv : 0
         * isSelf : 0
         * isVip : 0
         * lanvName :
         * orgName :
         * portrait : 09bc6733626f737331192f
         * resUrl :
         * userId : 790215689
         * userName : g3boss1
         * userSign : 3154116608 790448095
         */

        public OwnerBean owner;
        public String parentTag;
        public String photoId;
        public String pictureId;
        public String pictureSign;
        public String setId;
        public String shareUrl;
        public String siteLogo;
        public String siteName;
        public String siteUrl;
        public int thumbLargeHeight;
        public int thumbLargeTnHeight;
        public String thumbLargeTnUrl;
        public int thumbLargeTnWidth;
        public String thumbLargeUrl;
        public int thumbLargeWidth;
        public int thumbnailHeight;
        public String thumbnailUrl;
        public int thumbnailWidth;
        public String title;
        public String userId;
        public List<String> tags;

        public static class OwnerBean {
            public String budgetNum;
            public String cert;
            public String contactName;
            public String isHunjia;
            public String isJiaju;
            public String isLanv;
            public String isSelf;
            public String isVip;
            public String lanvName;
            public String orgName;
            public String portrait;
            public String resUrl;
            public String userId;
            public String userName;
            public String userSign;
        }
    }
}
