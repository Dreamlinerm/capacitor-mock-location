package com.mock.location;

import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.JSObject;
import android.provider.Settings;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;


@CapacitorPlugin(name = "Location")
public class LocationPlugin extends Plugin {

    @PluginMethod()
    public void isMocked(PluginCall call) {
        JSObject ret = new JSObject();
        LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        boolean isMock = false;
        if (android.os.Build.VERSION.SDK_INT >= 18) {
            isMock = location.isFromMockProvider();
        }else {
            isMock = isMockSettingsONLocal(getContext());
        }
        ret.put("value", isMock);
        call.resolve(ret);
    }

    @PluginMethod()
    public void isMockSettingsON(PluginCall call) {
        JSObject ret = new JSObject();
        ret.put("value", isMockSettingsONLocal(getContext()));
        call.resolve(ret);
    }

    private boolean isMockSettingsONLocal(Context context) {
        //Context context
        // returns true if mock location enabled, false if not enabled.
        return !(Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION).equals("0"));
    }
}
