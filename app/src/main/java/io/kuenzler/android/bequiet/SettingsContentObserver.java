package io.kuenzler.android.bequiet;

import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Leonhard on 12.10.2015.
 */
public class SettingsContentObserver extends ContentObserver {
    int previousVolume;
    Context context;
    MainActivity main;
    private boolean useListener;

    public SettingsContentObserver(Context c, Handler handler, MainActivity main) {
        super(handler);
        context = c;
        this.main = main;
        useListener = false;

        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        previousVolume = audio.getStreamVolume(AudioManager.STREAM_RING);
    }

    @Override
    public boolean deliverSelfNotifications() {
        return super.deliverSelfNotifications();
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);

        if(useListener) {
            AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            int currentVolume = audio.getStreamVolume(AudioManager.STREAM_RING);

            int delta = previousVolume - currentVolume;

            int volume = audio.getStreamVolume(AudioManager.STREAM_RING);
            if (volume == 0 && previousVolume > 0) {
                main.toggleMuteAll(true);
                Toast.makeText(main.getApplicationContext(), "Mutet phone", Toast.LENGTH_SHORT).show();
            } else if (volume > 0 && previousVolume == 0) {
                main.streamData.put(AudioManager.STREAM_RING, 2);
                main.toggleMuteAll(false);
                Toast.makeText(main.getApplicationContext(), "Unmuted Phone", Toast.LENGTH_SHORT).show();
            }

            if (delta > 0) {
                Log.d("VolumeChange", "Decreased");
                previousVolume = currentVolume;
            } else if (delta < 0) {
                Log.d("VolumeChange", "Increased");
                previousVolume = currentVolume;
            }
        }
    }

    public void setUseListener(boolean use){
        useListener = use;
    }
}