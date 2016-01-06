package com.example.akeem.u_4.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akeem.u_4.R;
import com.example.akeem.u_4.bean.MusicBean;
import com.example.akeem.u_4.blur.ImageBlurManager;
import com.example.akeem.u_4.utils.ThreadManager;
import com.example.akeem.u_4.utils.UiUtils;
import com.example.akeem.u_4.view.PlayerDiscView;
import com.lidroid.xutils.util.LogUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.IllegalFormatCodePointException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MuiscActivity extends AppCompatActivity implements View.OnClickListener {



    @Bind(R.id.musics_player_disc_view)
    PlayerDiscView mPlayerDiscView;
    @Bind(R.id.musics_player_background)
    ImageView mMusicsPlayerBackground;
    @Bind(R.id.player_disc)
    ImageView mPlayerDisc;
    @Bind(R.id.player_disc_image)
    ImageView mPlayerDiscImage;
    @Bind(R.id.player_disc_container)
    RelativeLayout mPlayerDiscContainer;
    @Bind(R.id.musics_player_name)
    TextView mMusicsPlayerName;
    @Bind(R.id.musics_player_songer_name)
    TextView mMusicsPlayerSongerName;
    @Bind(R.id.musics_player_current_time)
    TextView mMusicsPlayerCurrentTime;
    @Bind(R.id.musics_player_seekbar)
    SeekBar mMusicsPlayerSeekbar;
    @Bind(R.id.musics_player_total_time)
    TextView mMusicsPlayerTotalTime;
    @Bind(R.id.musics_player_progress_container)
    LinearLayout mMusicsPlayerProgressContainer;
    @Bind(R.id.musics_player_play_prev_btn)
    ImageButton mMusicsPlayerPlayPrevBtn;
    @Bind(R.id.musics_player_play_ctrl_btn)
    ImageButton mMusicsPlayerPlayCtrlBtn;
    @Bind(R.id.musics_player_play_next_btn)
    ImageButton mMusicsPlayerPlayNextBtn;
    @Bind(R.id.musics_player_loading_view)
    View mMusicsPlayerLoadingView;
    @Bind(R.id.musics_player_container)
    RelativeLayout mMusicsPlayerContainer;
    @Bind(R.id.headTitle)
    TextView mHeadTitle;






    private MediaPlayer mMediaPlayer;
    private int currentListItem;
    private Activity mActivity;
    private List<MusicBean> mMusic;
    private SimpleDateFormat mFormatter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mMediaPlayer =new MediaPlayer();
        mActivity = this;
        setContentView(R.layout.activity_musics);
        ButterKnife.bind(this);
        Bitmap bitmap = ImageBlurManager.doBlurJniArray(BitmapFactory.decodeResource(getResources(),
                R.drawable.player_bg),
                100,
                false);
        mMusicsPlayerBackground.setImageBitmap(bitmap);
        mFormatter = new SimpleDateFormat("mm:ss");
        initData();
        initViewEvent();

    }

    private void initViewEvent() {

        mMusicsPlayerPlayPrevBtn.setOnClickListener(this);
        mMusicsPlayerPlayCtrlBtn.setOnClickListener(this);
        mMusicsPlayerPlayNextBtn.setOnClickListener(this);

        mMusicsPlayerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private String  getMusicURL(int position) {
        return mMusic.get(position).song.get(0).url;
    }
    private void initData() {
        currentListItem = getIntent().getIntExtra("index", 0);
        mMusic = (List<MusicBean>) UiUtils.getMainMap().get("music");
          playMusic(getMusicURL(currentListItem));

        LogUtils.d("音乐模块的谁被电了"+currentListItem);
        LogUtils.d("音乐模块的谁被电-----"+mMusic.toString());
    mHeadTitle.setText(mMusic.get(currentListItem).song.get(0).title);
          isPlaying();
        setHeadTitle(currentListItem);
        mPlayerDiscView.startPlay();
    }


    private void setHeadTitle(int position) {
        mHeadTitle.setText(mMusic.get(position).song.get(0).title);
    }


    private String getMuiscPicture() {
       return mMusic.get(currentListItem).song.get(0).picture;
    }








    void lastMusic(){


        if (currentListItem > 0) {
            currentListItem--;
        } else {
            currentListItem=mMusic.size()-1;
        }
        playMusic(getMusicURL(currentListItem));
        setHeadTitle(currentListItem);
        mPlayerDiscView.pause();
        mPlayerDiscView.startPlay();


    }

    void nextMusic(){
        if(++currentListItem>=mMusic.size()){
            currentListItem=0;
        }

            playMusic(getMusicURL(currentListItem));
            setHeadTitle(currentListItem);
        mPlayerDiscView.pause();
        mPlayerDiscView.startPlay();
    }





//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        // TODO Auto-generated method stub
//        if(keyCode==KeyEvent.KEYCODE_BACK){
//            mHandler.removeMessages(0);
//            mMediaPlayer.stop();
//            mMediaPlayer.release();
//            mMediaPlayer = null;
//            this.finish();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    protected void onPause() {
        mHandler.removeMessages(0);
        if (mMediaPlayer!=null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }

        super.onPause();
    }

    void playMusic(String path){
        mPlayerDiscView.loadAlbumCover(getMuiscPicture());
        final String finalPath = path;
        new Thread(){
            @Override
            public void run(){
                try {
                    mMediaPlayer.reset();
                    mMediaPlayer.setDataSource(finalPath);
                    mMediaPlayer.prepare();
                    mMediaPlayer.start();
                    /*********************/
                    mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            // TODO Auto-generated method stub
                            nextMusic();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /******************************************************/
                UiUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mMediaPlayer!=null) {
                            final int duration =  mMediaPlayer.getDuration();
                            mMusicsPlayerSeekbar.setMax(duration);
                            mMusicsPlayerSongerName.setText(  mMusic.get(currentListItem).song.get(0).title);
                            mMusicsPlayerTotalTime.setText(""+ mFormatter.format(duration));
                        }

                    }
                });
                mHandler.removeMessages(0);
                mHandler.sendEmptyMessage(0);
            }
        }.start();
//        ThreadManager.getInstance().createLongPool().execute(new Runnable() {
//           @Override
//           public void run() {
//               try {
//
//                   mMediaPlayer.reset();
//                   mMediaPlayer.setDataSource(finalPath);
//                   mMediaPlayer.prepare();
//                   mMediaPlayer.start();
//                   /*********************/
//                   mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                       @Override
//                       public void onCompletion(MediaPlayer mp) {
//                           // TODO Auto-generated method stub
//                             nextMusic();
//                       }
//                   });
//               } catch (Exception e) {
//                   e.printStackTrace();
//                   Toast.makeText(mActivity, "网络错误", Toast.LENGTH_SHORT).show();
//               }
//               /******************************************************/
//        UiUtils.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                if (mMediaPlayer!=null) {
//                    final int duration =  mMediaPlayer.getDuration();
//                    mMusicsPlayerSeekbar.setMax(duration);
//                    mMusicsPlayerSongerName.setText(  mMusic.get(currentListItem).song.get(0).title);
//                    mMusicsPlayerTotalTime.setText(""+ mFormatter.format(duration));
//                }
//
//            }
//        });
//               mHandler.removeMessages(0);
//               mHandler.sendEmptyMessage(0);
//           }
//       });
    }



    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mMediaPlayer!=null && mMediaPlayer.isPlaying()) {
                mMusicsPlayerSeekbar.setProgress( mMediaPlayer.getCurrentPosition());
                mMusicsPlayerCurrentTime.setText(""+ mFormatter.format(mMediaPlayer.getCurrentPosition()));
                mHandler.sendEmptyMessageDelayed(0, 1000);
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) { //上一首
            case R.id.musics_player_play_prev_btn:
                Toast.makeText(mActivity, "上一首", Toast.LENGTH_SHORT).show();
                lastMusic();
                isPlaying();
                break;
            case R.id.musics_player_play_ctrl_btn:
                if(mMediaPlayer.isPlaying()){
                    mMediaPlayer.pause();
                    mMusicsPlayerPlayCtrlBtn.setImageResource(R.drawable.btn_play_selector);
                    mPlayerDiscView.pause();
                }else{
                    mMediaPlayer.start();
                    mHandler.removeMessages(0);
                    mHandler.sendEmptyMessage(0);
                    mMusicsPlayerPlayCtrlBtn.setImageResource(R.drawable.btn_pause_selector);
                    mPlayerDiscView.startPlay();
                }
                break;
            case R.id.musics_player_play_next_btn:
                nextMusic();
                isPlaying();
                Toast.makeText(mActivity, "下一首", Toast.LENGTH_SHORT).show();
                break;

        }
    }


    public void isPlaying() {
        LogUtils.i("----------------------------------"+currentListItem);
        mMusic.size();
        if(mMediaPlayer.isPlaying()){
            mMusicsPlayerPlayCtrlBtn.setImageResource(R.drawable.btn_play_selector);
        }else{
            mMusicsPlayerPlayCtrlBtn.setImageResource(R.drawable.btn_pause_selector);
        }
    }

}
