package io.kuenzler.android.bequiet;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.Switch;
import android.widget.Toast;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    public HashMap streamData;
    private AudioManager audio;
    private SettingsContentObserver mSettingsContentObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        new Controller(this);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //                .setAction("Action", null).show();
        //    }
        //});

        streamData = new HashMap();
        fillStreamData();
        audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        setVolumeData(null);

        mSettingsContentObserver = new SettingsContentObserver(this, new Handler(), this);
        getApplicationContext().getContentResolver().registerContentObserver(android.provider.Settings.System.CONTENT_URI, true, mSettingsContentObserver);
    }

    private void blabla() {
        Toast.makeText(getApplicationContext(), "blabla :)", Toast.LENGTH_SHORT).show();
    }

    protected void onDestroy() {
        super.onDestroy();

        getApplicationContext().getContentResolver().unregisterContentObserver(mSettingsContentObserver);

    }

    private void fillStreamData() {
        streamData.put(AudioManager.STREAM_MUSIC, 0);
        streamData.put(AudioManager.STREAM_RING, 0);
        streamData.put(AudioManager.STREAM_ALARM, 0);
        streamData.put(AudioManager.STREAM_NOTIFICATION, 0);
        streamData.put(AudioManager.STREAM_SYSTEM, 0);
        streamData.put(AudioManager.STREAM_VOICE_CALL, 0);
        streamData.put(AudioManager.STREAM_DTMF, 0);
        streamData.put("sw_vibration", AudioManager.RINGER_MODE_SILENT);
    }

    /**
     *
     * @param view
     */
    public void setVolumeData(View view) {
        if (audio.getStreamVolume(AudioManager.STREAM_ALARM) == 0) {
            ((Switch) (findViewById(R.id.sw_alarms))).setChecked(true);
        }
        if (audio.getStreamVolume(AudioManager.STREAM_DTMF) == 0) {
            ((Switch) (findViewById(R.id.sw_dtmf))).setChecked(true);
        }
        if (audio.getStreamVolume(AudioManager.STREAM_MUSIC) == 0) {
            ((Switch) (findViewById(R.id.sw_media))).setChecked(true);
        }
        if (audio.getStreamVolume(AudioManager.STREAM_NOTIFICATION) == 0) {
            ((Switch) (findViewById(R.id.sw_notification))).setChecked(true);
        }
        if (audio.getStreamVolume(AudioManager.STREAM_RING) == 0) {
            ((Switch) (findViewById(R.id.sw_ringtone))).setChecked(true);
        }
        if (audio.getStreamVolume(AudioManager.STREAM_SYSTEM) == 0) {
            ((Switch) (findViewById(R.id.sw_system))).setChecked(true);
        }
        if (audio.getStreamVolume(AudioManager.STREAM_ALARM) == 0) {
            ((Switch) (findViewById(R.id.sw_alarms))).setChecked(true);
        }
        if (audio.getStreamVolume(AudioManager.STREAM_VOICE_CALL) == 0) {
            ((Switch) (findViewById(R.id.sw_voicecall))).setChecked(true);
        }
        int ringerMode = audio.getRingerMode();
        if (audio.getRingerMode() == AudioManager.RINGER_MODE_SILENT) {
            ((Switch) (findViewById(R.id.sw_vibration))).setChecked(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * @param view Action view (should be sw_muteAll)
     */
    public void muteAll(View view) {
        if (view.getTag().equals("sw_muteAll")) {
            if (((Switch) view).isChecked()) {
                toggleMuteAll(true);
            } else {
                toggleMuteAll(false);
            }
        }
    }

    public void setUseListener(View view) {
        boolean use;
        use = ((CheckBox)(findViewById(R.id.cb_useListener))).isChecked();
        Log.i("Boolean:", String.valueOf(use));
        mSettingsContentObserver.setUseListener(use);
    }

    /**
     * @param mute
     */
    public void toggleMuteAll(boolean mute) {
        if (mute) {
            ((Switch) (findViewById(R.id.sw_alarms))).setChecked(true);
            ((Switch) (findViewById(R.id.sw_dtmf))).setChecked(true);
            ((Switch) (findViewById(R.id.sw_media))).setChecked(true);
            ((Switch) (findViewById(R.id.sw_notification))).setChecked(true);
            ((Switch) (findViewById(R.id.sw_ringtone))).setChecked(true);
            ((Switch) (findViewById(R.id.sw_system))).setChecked(true);
            ((Switch) (findViewById(R.id.sw_vibration))).setChecked(true);
            ((Switch) (findViewById(R.id.sw_voicecall))).setChecked(true);
            mute(AudioManager.STREAM_RING);
            mute(AudioManager.STREAM_NOTIFICATION);
            mute(AudioManager.STREAM_ALARM);
            mute(AudioManager.STREAM_SYSTEM);
            mute(AudioManager.STREAM_VOICE_CALL);
            mute(AudioManager.STREAM_MUSIC);
            mute(AudioManager.STREAM_DTMF);
            //toggleVibration(null); //TODO CHANGE
        } else {
            ((Switch) (findViewById(R.id.sw_alarms))).setChecked(false);
            ((Switch) (findViewById(R.id.sw_dtmf))).setChecked(false);
            ((Switch) (findViewById(R.id.sw_media))).setChecked(false);
            ((Switch) (findViewById(R.id.sw_notification))).setChecked(false);
            ((Switch) (findViewById(R.id.sw_ringtone))).setChecked(false);
            ((Switch) (findViewById(R.id.sw_system))).setChecked(false);
            ((Switch) (findViewById(R.id.sw_vibration))).setChecked(false);
            ((Switch) (findViewById(R.id.sw_voicecall))).setChecked(false);
            unmute(AudioManager.STREAM_RING);
            unmute(AudioManager.STREAM_NOTIFICATION);
            unmute(AudioManager.STREAM_ALARM);
            unmute(AudioManager.STREAM_SYSTEM);
            unmute(AudioManager.STREAM_VOICE_CALL);
            unmute(AudioManager.STREAM_MUSIC);
            unmute(AudioManager.STREAM_DTMF);
            //toggleVibration(null);
        }
        setVolumeData(null);
    }


    /**
     * @param view Action view (should be switch)
     */
    public void switchFlipped(View view) {
        String tag = (String) view.getTag();
        int stream;
        switch (tag) {
            case "sw_ringtone":
                stream = AudioManager.STREAM_RING;
                break;
            case "sw_notification":
                stream = AudioManager.STREAM_NOTIFICATION;
                break;
            case "sw_alarms":
                stream = AudioManager.STREAM_ALARM;
                break;
            case "sw_system":
                stream = AudioManager.STREAM_SYSTEM;
                break;
            case "sw_voicecall":
                stream = AudioManager.STREAM_VOICE_CALL;
                break;
            case "sw_media":
                stream = AudioManager.STREAM_MUSIC;
                break;
            case "sw_dtmf":
                stream = AudioManager.STREAM_DTMF;
                break;
            default:
                return;
        }
        if (((Switch) view).isChecked()) {
            mute(stream);
        } else {
            unmute(stream);
        }
        //AudioManager.STREAM_MUSIC
        //AudioManager.STREAM_RING
        //AudioManager.STREAM_ALARM
        //AudioManager.STREAM_NOTIFICATION
        //AudioManager.STREAM_SYSTEM
        //AudioManager.STREAM_VOICECALL
        //FLAG_REMOVE_SOUND_AND_VIBRATE
        //FLAG_SHOW_UI
    }

    /**
     * @param stream Audio stream to mute
     */
    private void mute(int stream) {
        int currentVolume = audio.getStreamVolume(stream);
        streamData.put(stream, currentVolume);
        audio.setStreamVolume(stream, 0, AudioManager.FLAG_SHOW_UI | AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
        Log.i("Volume Data", "Stream:" + stream + ", Value: " + audio.getStreamVolume(stream));
    }

    /**
     * @param stream Audio stream to unmute
     */
    private void unmute(int stream) {
        int volume;
        volume = (int) streamData.get(stream);
        audio.setStreamVolume(stream, volume, AudioManager.FLAG_SHOW_UI | AudioManager.FLAG_VIBRATE);
        Log.i("Volume Data", "Stream:" + stream + ", Value: " + audio.getStreamVolume(stream));
    }

    /**
     *
     * @param view
     */
    public void toggleVibration(View view) {
        int ringerMode;
        if (((Switch) view).isChecked()) {
            ringerMode = audio.getRingerMode();
            streamData.put("sw_vibration", ringerMode);
            audio.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        } else {
            ringerMode = (int) streamData.get("sw_vibration");
            audio.setRingerMode(ringerMode);
        }
        Log.i("Volume Data", "Stream: -1, Value: " + audio.getRingerMode());
    }

    /**
     * @param stream  Audio stream to adjust
     * @param percent Volume from 0 to 100
     */
    private void setVolume(int stream, int percent) {
        float precicePercent;
        int maxVolume, volume;
        precicePercent = (float) percent / 100;
        maxVolume = audio.getStreamMaxVolume(stream);
        volume = (int) (maxVolume * precicePercent);
        audio.setStreamVolume(stream, volume, 0);
    }

    /**
     *
     * @param view
     */
    public void visitHomepage(View view) {
        String url = "http://www.kuenzler.io";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    /**
     *
     * @param message
     * @param length
     */
    public void toast(String message, boolean length){
        int duration = Toast.LENGTH_SHORT;
        if(length){
            duration = Toast.LENGTH_LONG;
        }
        Toast.makeText(getApplicationContext(), message, duration).show();
    }

}
