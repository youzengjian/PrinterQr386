package com.atknit.printer.qr386;
import java.util.Set;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

/**
 * This class echoes a string called from JavaScript.
 */
public class PrinterQr386 extends CordovaPlugin {
    private BluetoothAdapter mBtAdapter;
    
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("getPariedDevices")) {
            String message = args.getString(0);
            this.getPariedDevices(message, callbackContext);
            return true;
        }
        return false;
    }

    private void getPariedDevices(String message, CallbackContext callbackContext) {
        // Get the local Bluetooth adapter
        this.mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        // Get a set of currently paired devices
        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
        // If there are paired devices, add each one to the ArrayAdapter
        JSONObject ret = new JSONObject();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                try{
                    ret.put(device.getName(), device.getAddress());
                } catch (JSONException e) {
                    continue;
                }
            }
        }
        callbackContext.success(ret.toString());
    }
}
