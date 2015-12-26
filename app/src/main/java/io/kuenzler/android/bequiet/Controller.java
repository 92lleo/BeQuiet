package io.kuenzler.android.bequiet;

import android.widget.Toast;

/**
 * Created by Leonhard on 14.10.2015.
 *
 * @author Leonhard Künzler
 * @version
 * @date
 */
public class Controller {
    MainActivity main;

    boolean xposedActive, useXposed;

    /**
     *
     * @param main
     */
    public Controller(MainActivity main) {
        this.main = main;
        xposedActive = isModuleActive();
        main.toast("XposedActive="+String.valueOf(xposedActive), true);
    }

    /**
     *
     * @return
     */
    private boolean isModuleActive(){
        return false;
    }
}
