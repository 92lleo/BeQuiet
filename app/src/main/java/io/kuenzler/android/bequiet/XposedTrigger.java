package io.kuenzler.android.bequiet;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class XposedTrigger implements IXposedHookLoadPackage {

    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        if (lpparam.packageName.equals("android.media.AudioManager")) {

            findAndHookMethod("android.media.AudioManager", lpparam.classLoader, "setStreamVolume", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    XposedBridge.log("Volume change detected");
                }
            });
        }
        if (lpparam.packageName.equals("io.kuenzler.android.bequiet")) {

            findAndHookMethod("io.kuenzler.android.bequiet.Controller", lpparam.classLoader, "isModuleActive", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    param.setResult(true);
                    XposedBridge.log("Xposed Module in \"BeQuiet\" is enabled");
                }
            });
        }
    }
}

