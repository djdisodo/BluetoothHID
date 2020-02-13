package com.github.bluetoothhid.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHidDevice;
import android.bluetooth.BluetoothProfile;

import androidx.annotation.NonNull;

import com.github.bluetoothhid.application.Application;
import com.github.bluetoothhid.report.Report;

public class HidDataSender implements BluetoothProfile.ServiceListener {
	private BluetoothHidDevice bluetoothHidDevice;
	private BluetoothDevice device;

	public HidDataSender(@NonNull BluetoothHidDevice bluetoothHidDevice) {
	}

	public void sendReport(Report report) {
		getBluetoothHidDevice().sendReport(device, report.getID(), report.build());
	}

	@Override
	public void onServiceConnected(int profile, BluetoothProfile proxy) {
		if (profile == BluetoothProfile.HID_DEVICE) {
			this.bluetoothHidDevice = (BluetoothHidDevice)proxy;
		}
	}

	@Override
	public void onServiceDisconnected(int profile) {

	}

	public BluetoothHidDevice getBluetoothHidDevice() {
		return bluetoothHidDevice;
	}

	public void registerApp(Application application) {
		getBluetoothHidDevice().registerApp(application.getSdp(), application.getInQos(), application.getOutQos(), application.getExecutor(), application.getCallback());
	}

	public void connect(BluetoothDevice device) {
		this.device = device;
	}
}
