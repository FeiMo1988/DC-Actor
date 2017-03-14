package dcactor.support;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import dcactor.support.core.DcActor;
import dcactor.support.core.DcActorCallbackPolicy;
import dcactor.support.core.DcActorConfig;

public class SampleApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        DcActor.getInstance().watch(
                new DcActorConfig()
                        .setOverTime(5000)
                .addCallback(new DcActorCallbackPolicy.DcActorDumpStacks()));
    }
}
