package com.github.bluetoothhid.application;

import android.bluetooth.BluetoothHidDevice;
import android.bluetooth.BluetoothHidDeviceAppQosSettings;
import android.bluetooth.BluetoothHidDeviceAppSdpSettings;

import com.github.bluetoothhid.report.ID;

import java.util.concurrent.Executor;

public class Application {
	private BluetoothHidDeviceAppSdpSettings sdp;
	private BluetoothHidDeviceAppQosSettings inQos;
	private BluetoothHidDeviceAppQosSettings outQos;
	private Executor executor;
	private BluetoothHidDevice.Callback callback;

	public Application(BluetoothHidDeviceAppSdpSettings sdp, BluetoothHidDeviceAppQosSettings inQos, BluetoothHidDeviceAppQosSettings outQos, Executor executor, BluetoothHidDevice.Callback callback) {
		this.sdp = sdp;
		this.inQos = inQos;
		this.outQos = outQos;
		this.executor = executor;
		this.callback = callback;
	}

	public BluetoothHidDeviceAppSdpSettings getSdp() {
		return sdp;
	}

	public void setSdp(BluetoothHidDeviceAppSdpSettings sdp) {
		this.sdp = sdp;
	}

	public BluetoothHidDeviceAppQosSettings getInQos() {
		return inQos;
	}

	public void setInQos(BluetoothHidDeviceAppQosSettings inQos) {
		this.inQos = inQos;
	}

	public BluetoothHidDeviceAppQosSettings getOutQos() {
		return outQos;
	}

	public void setOutQos(BluetoothHidDeviceAppQosSettings outQos) {
		this.outQos = outQos;
	}

	public Executor getExecutor() {
		return executor;
	}

	public void setExecutor(Executor executor) {
		this.executor = executor;
	}

	public BluetoothHidDevice.Callback getCallback() {
		return callback;
	}

	public void setCallback(BluetoothHidDevice.Callback callback) {
		this.callback = callback;
	}

	final private static BluetoothHidDeviceAppQosSettings QOS_DEFAULT = new BluetoothHidDeviceAppQosSettings(1, 800, 9, 0, 11250, -1);

	public static Application Keyboard(String name, String description, String provider, BluetoothHidDevice.Callback callback) {
		return new Application(
				new BluetoothHidDeviceAppSdpSettings(name, description, provider, BluetoothHidDevice.SUBCLASS1_KEYBOARD, new byte[] {
						(byte) 0x05, (byte) 0x01, // Usage page (Generic Desktop)
						(byte) 0x09, (byte) 0x06, // Usage (Keyboard)
						(byte) 0xA1, (byte) 0x01, // Collection (Application)
						(byte) 0x85, ID.KEYBOARD, //    Report ID
						(byte) 0x05, (byte) 0x07, //       Usage page (Key Codes)
						(byte) 0x19, (byte) 0xE0, //       Usage minimum (224)
						(byte) 0x29, (byte) 0xE7, //       Usage maximum (231)
						(byte) 0x15, (byte) 0x00, //       Logical minimum (0)
						(byte) 0x25, (byte) 0x01, //       Logical maximum (1)
						(byte) 0x75, (byte) 0x01, //       Report size (1)
						(byte) 0x95, (byte) 0x08, //       Report count (8)
						(byte) 0x81, (byte) 0x02, //       Input (Data, Variable, Absolute) ; Modifier byte
						(byte) 0x75, (byte) 0x08, //       Report size (8)
						(byte) 0x95, (byte) 0x01, //       Report count (1)
						(byte) 0x81, (byte) 0x01, //       Input (Constant)                 ; Reserved byte
						(byte) 0x75, (byte) 0x08, //       Report size (8)
						(byte) 0x95, (byte) 0x06, //       Report count (6)
						(byte) 0x15, (byte) 0x00, //       Logical Minimum (0)
						(byte) 0x25, (byte) 0x65, //       Logical Maximum (101)
						(byte) 0x05, (byte) 0x07, //       Usage page (Key Codes)
						(byte) 0x19, (byte) 0x00, //       Usage Minimum (0)
						(byte) 0x29, (byte) 0x65, //       Usage Maximum (101)
						(byte) 0x81, (byte) 0x00, //       Input (Data, Array)              ; Key array (6 keys)
						(byte) 0xC0,              // End Collection
				}),
				null,
				QOS_DEFAULT,
				Runnable::run,
				callback
		);
	}


	public static Application Joystick(String name, String description, String provider, BluetoothHidDevice.Callback callback) {
		return new Application(
				new BluetoothHidDeviceAppSdpSettings(name, description, provider, BluetoothHidDevice.SUBCLASS2_JOYSTICK, new byte[] {
						0x05, 0x01,        // USAGE_PAGE (Generic Desktop)
						0x09, 0x05,        // USAGE (Game Pad)
						(byte) 0xa1, 0x01, // COLLECTION (Application)
						(byte) 0xa1, ID.JOYSTICK, //   COLLECTION (Physical)
						0x05, 0x09,        //     USAGE_PAGE (Button)
						0x19, 0x01,        //     USAGE_MINIMUM (Button 1)
						0x29, 0x04,        //     USAGE_MAXIMUM (Button 4)
						0x15, 0x00,        //     LOGICAL_MINIMUM (0)
						0x25, 0x01,        //     LOGICAL_MAXIMUM (1)
						0x75, 0x01,        //     REPORT_SIZE (1)
						(byte) 0x95, 0x04, //     REPORT_COUNT (4)
						(byte) 0x81, 0x02, //     INPUT (Data,Var,Abs)
						0x75, 0x04,        //     REPORT_SIZE (4)
						(byte) 0x95, 0x01, //     REPORT_COUNT (1)
						(byte) 0x81, 0x03, //     INPUT (Cnst,Var,Abs)
						0x05, 0x01,        //     USAGE_PAGE (Generic Desktop)
						0x09, 0x30,        //     USAGE (X)
						0x09, 0x31,        //     USAGE (Y)
						0x15, (byte) 0x81, //     LOGICAL_MINIMUM (-127)
						0x25, 0x7f,        //     LOGICAL_MAXIMUM (127)
						0x75, 0x08,        //     REPORT_SIZE (8)
						(byte) 0x95, 0x02, //     REPORT_COUNT (2)
						(byte) 0x81, 0x02, //     INPUT (Data,Var,Abs)
						(byte) 0xc0,       //   END_COLLECTION
						(byte) 0xc0        // END_COLLECTION
				}),
				null,
				QOS_DEFAULT,
				Runnable::run,
				callback
		);
	}
}
