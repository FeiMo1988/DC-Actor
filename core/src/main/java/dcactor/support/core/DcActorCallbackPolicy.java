package dcactor.support.core;

import android.util.Log;

import java.io.File;
import java.util.Map;

/**
 * Created by chendingwei on 17/3/14.
 */

public class DcActorCallbackPolicy {


    private static final String TAG = DcActorCallbackPolicy.class.getSimpleName();

    public static class DcActorDumpStacks implements DcActor.DcActorCallback {

        @Override
        public final void onThreadBlock(Thread uiThread) {
            this.onPrintStart();
            StackTraceElement[] eles = uiThread.getStackTrace();
            for (int index = 0; index < eles.length; index ++) {
                StackTraceElement ele = eles[index];
                onPrintStackElements(index,ele);
            }
            this.onPrintEnd();
        }

        protected void onPrintStart() {
            Log.v(TAG,"DcActorDumpStacks.PRINT START>>");
        }
        protected void onPrintEnd() {
            Log.v(TAG,"<<<DcActorDumpStacks.PRINT FINISH\r\n");
        }

        protected void onPrintStackElements(int index ,StackTraceElement ele) {
            Log.v("DcActorCallbackPolicy","["+index+"]"+ele.getClassName()+"."+ele.getMethodName()+"() #"+ele.getLineNumber());
        }

    }

    public static class DcActorDumpCpu implements DcActor.DcActorCallback {

        //private static final String CPU_STAT_FILE_PATH = "/proc/stat";

        @Override
        public void onThreadBlock(Thread uiThread) {
            //TODO
        }

    }

    public static class DcActorDumpMem implements DcActor.DcActorCallback {

        @Override
        public void onThreadBlock(Thread uiThread) {
            //TODO
        }

    }

}
