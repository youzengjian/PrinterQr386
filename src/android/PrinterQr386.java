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
        } else if (action.equals("connect")) {
            String printer_name = args.getString(0);
            String printer_addr = args.getString(1);
            this.connect(printer_name, printer_addr, callbackContext);
            return true;
        } else if (action.equals("disconnect")) {
            this.disconnect(callbackContext);
            return true;
        } else if (action.equals("isConnected")) {
            this.isConnected(callbackContext);
            return true;
        } else if (action.equals("print")) {
            int horizontal = args.getInt(0);
            int skip = args.getInt(1);
            this.print(horizontal, skip, callbackContext);
            return true;
        } else if (action.equals("pageSetup")) {
            int pageWidth = args.getInt(0);
            int pageHeight = args.getInt(1);
            this.pageSetup(pageWidth, pageHeight, callbackContext);
            return true;
        } else if (action.equals("drawText")) {
            int text_x = args.getInt(0);
            int text_y = args.getInt(1);
            String text = args.getString(2);
            int fontSize = args.getInt(3);
            int rotate = args.getInt(4);
            int bold = args.getInt(5);
            boolean reverse = args.getBoolean(6);
            boolean underline = args.getBoolean(7);

            this.drawText(text_x, text_y, text, fontSize, rotate, bold, reverse, underline, callbackContext);
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

    private void connect(String printer_name, String printer_addr, CallbackContext callbackContext) {
        if (null == this.printPP_cpcl) {
            this.printPP_cpcl = new PrintPP_CPCL();
        }
        JSONObject ret = new JSONObject();
        try{
            boolean result = this.printPP_cpcl.connect(printer_name, printer_addr);
            ret.put("result", result);
        } catch (JSONException e) {
            // do nothing
        }
        callbackContext.success(ret);
    }

    private void disconnect(CallbackContext callbackContext) {
        JSONObject ret = new JSONObject();
        try{
            ret.put("result", true);
        } catch (JSONException e) {
            // do nothing
        }
        if (null != this.printPP_cpcl) {
            this.printPP_cpcl.disconnect();
        }
        callbackContext.success(ret);
    }

    private void isConnected(CallbackContext callbackContext) {
        boolean isConnected = false;
        if (null != this.printPP_cpcl) {
            isConnected = this.printPP_cpcl.isConnected();
        }

        JSONObject ret = new JSONObject();
        try{
            ret.put("result", isConnected);
        } catch (JSONException e) {
            // do nothing
        }
        callbackContext.success(ret);
    }

    private void print(int horizontal, int skip, CallbackContext callbackContext) {
        boolean result = false;
        String message = "Unknown Error";
        if (null == this.printPP_cpcl || !this.printPP_cpcl.isConnected()) {
            message = "Device Not Connected";
        } else {
            message = this.printPP_cpcl.print(horizontal, skip);
            if (message.equals("Ok")) {
                result = true;
            }
        }
        JSONObject ret = new JSONObject();
        try{
            ret.put("result", result);
            ret.put("message", message);
        } catch (JSONException e) {
            // do nothing
        }
        callbackContext.success(ret);
    }

    private void pageSetup(int pageWidth, int pageHeight,CallbackContext callbackContext) {
        JSONObject ret = new JSONObject();
        try{
            ret.put("result", true);
        } catch (JSONException e) {
            // do nothing
        }
        if (null != this.printPP_cpcl) {
            this.printPP_cpcl.pageSetup(pageWidth, pageHeight);
        }
        callbackContext.success(ret);
    }

    private void drawText(int text_x, int text_y, String text, int fontSize, int rotate, int bold, boolean reverse, boolean underline, CallbackContext callbackContext) {
        boolean result = false;
        String message = "Unknown Error";
        if (null == this.printPP_cpcl || !this.printPP_cpcl.isConnected()) {
            message = "Device Not Connected";
        } else {
            this.printPP_cpcl.drawText(text_x, text_y, text, fontSize, rotate, bold, reverse, underline);
            result = true;
            message = "Success";
        }
        JSONObject ret = new JSONObject();
        try{
            ret.put("result", result);
            ret.put("message", message);
        } catch (JSONException e) {
            // do nothing
        }
        callbackContext.success(ret);
    }
}
