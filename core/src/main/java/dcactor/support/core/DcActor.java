package dcactor.support.core;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.Printer;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by chendingwei on 17/3/14.
 */

public class DcActor extends Handler implements Printer {

    private static final DcActor INSTANCE = new DcActor();
    private AtomicBoolean mIsRegistorOver = new AtomicBoolean(false);
    private boolean mIsStarted = false;
    private static final int MSG_REGISTOR = 0x01;
    private MonitorThread mMonitorThread = new MonitorThread();
    private DcActorConfig mDcActorConfig = null;
    private MonitorAction mMonitorAction = new MonitorAction();
    private Handler mMonitorHandler = null;
    private Thread mUiThread;


    {
        mMonitorThread.start();
        mMonitorHandler = new Handler(mMonitorThread.getLooper());
    }


    private DcActor(){
        super(Looper.getMainLooper());
    }

    public static DcActor getInstance() {
        return INSTANCE;
    }

    @Override
    public void dispatchMessage(Message msg) {
        switch (msg.what) {
            case MSG_REGISTOR:
                mUiThread = Thread.currentThread();
                Looper.getMainLooper().setMessageLogging(this);
                break;
        }
    }

    private boolean isUiThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public final void watch(DcActorConfig config) {
        PreConditions.checkNull(config,"config",null);
        mDcActorConfig = config;
        watchInternal();
    }

    private void watchInternal() {
        if (!mIsRegistorOver.getAndSet(true)) {
            if (isUiThread()) {
                mUiThread = Thread.currentThread();
                Looper.getMainLooper().setMessageLogging(this);
            } else {
                this.sendEmptyMessage(MSG_REGISTOR);
            }
        }
    }


    @Override
    public final void println(String x) {
        if (!mIsStarted) {
            mIsStarted = true;
            mMonitorAction.start();
        } else {
            mIsStarted = false;
            mMonitorAction.cancel();
        }
    }

    private long getOverTime() {
        final DcActorConfig config = this.mDcActorConfig;
        if (config != null) {
            return config.getOvertime();
        }
        return DcActorConfig.DEFAULT_OVERTIME;
    }


    private class MonitorAction implements Runnable {

        public void start() {
            mMonitorHandler.removeCallbacks(this);
            mMonitorHandler.postDelayed(this,getOverTime());
        }

        public void cancel() {
            mMonitorHandler.removeCallbacks(this);
        }

        @Override
        public void run() {
            final DcActorConfig config = mDcActorConfig;
            if (config != null) {
                Set<DcActorCallback> callbacks = new HashSet<>(config.getCallbacks());
                if (callbacks != null) {
                    for (DcActorCallback c:callbacks) {
                        c.onThreadBlock(mUiThread);
                    }
                }
            }
        }
    }


    private class MonitorThread extends HandlerThread {
        public MonitorThread() {
            super("DC_ACTOR_MONITOR");
        }
    }

    public interface DcActorCallback {
        void onThreadBlock(Thread uiThread);
    }


}
