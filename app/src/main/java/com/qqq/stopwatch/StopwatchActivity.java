package com.qqq.stopwatch;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StopwatchActivity extends AppCompatActivity {
    private TextView mTimer;
    private Button mStartButton;
    private Button mStopButton;
    private Button mResetButton;
    private boolean isRunning;
    private int mSeconds;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        mTimer = (TextView) findViewById(R.id.time_view);
        mStartButton = (Button) findViewById(R.id.start_button);
        mStopButton = (Button) findViewById(R.id.stop_button);
        mResetButton = (Button) findViewById(R.id.reset_button);

        if (savedInstanceState != null) {
            isRunning = savedInstanceState.getBoolean(getString(R.string.is_running_key));
            mSeconds = savedInstanceState.getInt(getString(R.string.seconds_key));
            wasRunning = savedInstanceState.getBoolean(getString(R.string.was_running_key));
        }

        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = true;
            }
        });

        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = false;
            }
        });

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = false;
                mSeconds = 0;
            }
        });

        runTimer();
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if (wasRunning) {
//            isRunning = true;
//        }
//    }

//    @Override
//    protected void onStop() {
//        super.onStop();
//        wasRunning = isRunning;
//        isRunning = false;
//    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = isRunning;
        isRunning = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            isRunning = true;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(getString(R.string.is_running_key), isRunning);
        outState.putInt(getString(R.string.seconds_key), mSeconds);
        outState.putBoolean(getString(R.string.was_running_key), wasRunning);
    }

    private void runTimer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = mSeconds / 3600;
                int minutes = (mSeconds % 3600) / 60;
                int seconds = mSeconds % 60;
                String time = String.format("%d:%02d:%02d",
                        hours, minutes, seconds);
                mTimer.setText(time);
                if (isRunning) {
                    mSeconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }
}
