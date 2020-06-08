package me.xujichang.lib.crash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import me.xujichang.lib.crash.databinding.ActivityMainBinding;
import me.xujichang.lib.crash.handler.CrashHandler;
import me.xujichang.lib.crash.room.entity.CrashLog;
import me.xujichang.lib.crash.ui.CrashInfoActivity;

/**
 * @author xujichang
 */
public class MainActivity extends AppCompatActivity {
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private TextView mTextView;
    private ActivityMainBinding mBinding;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mBinding.tvHello.setText(stringFromJNI());

        mBinding.tvHello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showJavaCrash();
            }
        });

        mBinding.tvLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLog();
            }
        });
        mBinding.tvCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLog();
            }
        });
    }

    private void checkLog() {
        if (CrashHandler.getInstance().checkNewCrash()) {
            Toast.makeText(this, "有新的Crash日志", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "没有新的Crash日志", Toast.LENGTH_SHORT).show();
        }
    }

    private void showLog() {
        startActivity(new Intent(MainActivity.this, CrashInfoActivity.class));
    }

    private void showJavaCrash() {
        mTextView.setText("");
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}