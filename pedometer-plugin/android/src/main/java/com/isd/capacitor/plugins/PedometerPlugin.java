package com.isd.capacitor.plugins;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.ComponentName;
import android.Manifest;
import android.os.Looper;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;

@CapacitorPlugin(name = "Pedometer", permissions = {
        @Permission(alias = "activity_recognition", strings = { Manifest.permission.ACTIVITY_RECOGNITION })
})
public class PedometerPlugin extends Plugin {

    private PedometerService service;
    private boolean bounded = false;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            Log.i("Binder", "Binded service.");
            PedometerService.MyBinder myBinder = (PedometerService.MyBinder) binder;
            service = myBinder.getService();
            service.setCallback(PedometerPlugin.this);
            bounded = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bounded = false;
            service.removeCallback();
            service = null;
        }
    };

    @PluginMethod
    public void ping(PluginCall call) {
        Log.i("Plugin", "ping received");

        String value = call.getString("value");

        if (value != "ping") {
            call.reject("message is not correct.");
        } else {
            JSObject ret = new JSObject();
            ret.put("value", "pong");

            call.resolve(ret);
        }
    }

    @PluginMethod
    public void register(PluginCall call) {
        Log.i("Register", "called");

        if (!hasRequiredPermissions()) {
            requestAllPermissions(call, "registerCallback");
        } else {
            internalRegister(call);
            call.resolve();
        }
    }

    @PluginMethod
    public void unregister(PluginCall call) {
        Log.i("Unregister", "called");

        getContext().unbindService(connection);
    }

    @PermissionCallback
    private void registerCallback(PluginCall call) {
        if (!hasRequiredPermissions()) {
            Log.i("Register", "user deny permission request.");
            call.reject("user deny permission request.");
        } else {
            internalRegister(call);
            call.resolve();
        }
    }

    private void internalRegister(PluginCall call) {
        Log.i("Register", "permission granted, trying to bind service.");

        getContext().bindService(new Intent(getContext(), PedometerService.class), connection,
                Context.BIND_AUTO_CREATE);

    }

    public void fireSteps(int steps, long startTime, long endTime) {
        JSObject ret = new JSObject();
        ret.put("value", steps);
        ret.put("startTime", startTime);
        ret.put("endTime", endTime);

        notifyListeners("step", ret);
    }
}
