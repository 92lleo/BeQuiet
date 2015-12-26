package io.kuenzler.android.bequiet;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by Leonhard on 14.10.2015.
 */
public class VolumeService extends IntentService{


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public VolumeService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        // Gets data from the incoming Intent
        String dataString = workIntent.getDataString();

        // Do work here, based on the contents of dataString

    }
}

