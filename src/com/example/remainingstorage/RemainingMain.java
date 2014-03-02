package com.example.remainingstorage;

import java.util.Locale;

import android.app.Activity;
import android.hardware.Camera.Size;
import android.media.CamcorderProfile;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.example.remainingmanager.R;

public class RemainingMain extends Activity {
    private static final String TAG = "RemainingManagerStudy";

    private static final int TYPE_COUNT = 0;
    private static final int TYPE_TIME = 1;
    private static final Long REMAIND_THRESHOLD = 100L;
    private static final int REMAIND_HOLD = 10;
    private WorkerHandler mWorkerHandler;
    private TextView mRemainingView;
    private int mType = TYPE_COUNT;
    private String mRemainingText;
    private boolean mResumed;
    private boolean mParametersReady;

    private static final int MSG_UPDATE_STORAGE = 0;
    private Long mAvaliableSpace;

    private static final int DELAY_MSG_MS = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remaining_manager);
        mRemainingView = (TextView) findViewById(R.id.textview);
    }

    private class WorkerHandler extends Handler {
        public WorkerHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            case MSG_UPDATE_STORAGE:
                // mAvaliableSpace = Storage.getAvailableSpace();
                removeMessages(MSG_UPDATE_STORAGE);
                sendEmptyMessageDelayed(MSG_UPDATE_STORAGE, DELAY_MSG_MS);
                break;
            default:
                break;
            }
        }
    }

    public long pictureSize() {
        String pictureFormat;
        // switch (getContext().getCameraActor().getMode()) {
        // case ModePicker.MODE_PANORAMA:
        // pictureFormat = "autorama";
        // break;
        // case ModePicker.MODE_MAV:
        // pictureFormat = "mav";
        // break;
        // default:
        // Size size = getContext().getParameters().getPictureSize();
        // String pictureSize = ((size.width > size.height) ? (size.width + "x"
        // + size.height)
        // : (size.height + "x" + size.width));
        // pictureFormat = pictureSize + "-" + "superfine";
        pictureFormat = "1920x1080" + "-" + "superfine";
        // break;
        // }
        long psize = Storage.getSize(pictureFormat);
        Log.v(TAG, "pictureSize() pictureFormat=" + pictureFormat + " return "
                + psize);
        return psize;
    }

    public long videoFrameRate() {
        // CamcorderProfile profile = getContext().getProfile();
        CamcorderProfile profile = CamcorderProfile
                .get(CamcorderProfile.QUALITY_1080P);
        long bytePerMs = ((profile.videoBitRate + profile.audioBitRate) >> 3) / 1000;
        return bytePerMs;
    }

    private static String stringForTime(final long millis) {
        final int totalSeconds = (int) millis / 1000;
        final int seconds = totalSeconds % 60;
        final int minutes = (totalSeconds / 60) % 60;
        final int hours = totalSeconds / 3600;
        if (hours > 0) {
            return String.format(Locale.ENGLISH, "%d:%02d:%02d", hours,
                    minutes, seconds);
        } else {
            return String.format(Locale.ENGLISH, "%02d:%02d", minutes, seconds);
        }
    }

    private long computeStorage(long avaliableSpace) {
        if (avaliableSpace > Storage.LOW_STORAGE_THRESHOLD) {
            avaliableSpace = (avaliableSpace - Storage.LOW_STORAGE_THRESHOLD)
                    / (mType == TYPE_COUNT ? pictureSize() : videoFrameRate());
        } else if (avaliableSpace > 0) {
            avaliableSpace = 0;
        }
        Storage.setLeftSpace(avaliableSpace);
        Log.v(TAG, "computeStorage(" + avaliableSpace + ") return "
                + avaliableSpace);

        return avaliableSpace;
    }

}
