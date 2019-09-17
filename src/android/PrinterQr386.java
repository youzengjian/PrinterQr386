package com.atknit.printer.qr386;
import java.util.Set;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import com.qr.print.*;

/**
 * This class echoes a string called from JavaScript.
 */
public class PrinterQr386 extends CordovaPlugin {
    private BluetoothAdapter mBtAdapter;
    private PrintPP_CPCL printPP_cpcl = null;
    
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("getPariedDevices")) {
            this.getPariedDevices(callbackContext);
            return true;
        } else if (action.equals("connectPrinter")) {
            String printer_name = args.getString(0);
            String printer_addr = args.getString(1);
            this.connectPrinter(printer_name, printer_addr, callbackContext);
            return true;
        }
        return false;
    }

    private void getPariedDevices(CallbackContext callbackContext) {
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
        callbackContext.success(ret);
    }

    private void connectPrinter(String printer_name, String printer_addr, CallbackContext callbackContext) {
        if (null == this.printPP_cpcl) {
            this.printPP_cpcl = new PrintPP_CPCL();
        }
        JSONObject ret = new JSONObject();
        try{
            boolean result = this.printPP_cpcl.connect(printer_name, printer_addr)
            ret.put("result", result);
        } catch (JSONException e) {
            // do nothing
        }
        callbackContext.success(ret);
    }
}
