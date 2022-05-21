package park.thirtydays.playaudio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playStart("printing.mp3");
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                playStart("print_finish.mp3");
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 10000); // 延时3s 执行TimeTask的run方法

    }

    public void play(View view) {
    }

    private void playStart(String filename) {
        try {
            AssetManager assetManager = this.getAssets();   ////获得该应用的AssetManager
            AssetFileDescriptor afd = assetManager.openFd(filename);   //根据文件名找到文件
            //对mediaPlayer进行实例化
            mediaPlayer = new MediaPlayer();
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.reset();    //如果正在播放，则重置为初始状态
            }
            mediaPlayer.setDataSource(afd.getFileDescriptor(),
                    afd.getStartOffset(), afd.getLength());     //设置资源目录
            mediaPlayer.prepare();//缓冲
            mediaPlayer.start();//开始或恢复播放
        } catch (IOException e) {
            Log.e("--------------", "没有找到这个文件");
            e.printStackTrace();
        }
    }

    //如果失去焦点，停止播放
    @Override
    protected void onPause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
        super.onPause();
    }

    //退出时，对mediaPlayer进行回收
    @Override
    protected void onDestroy() {
        mediaPlayer.release();
        super.onDestroy();
    }
}