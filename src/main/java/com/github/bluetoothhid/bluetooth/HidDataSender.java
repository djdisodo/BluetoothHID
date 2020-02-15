package com.github.bluetoothhid.bluetooth;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHidDevice;
import android.bluetooth.BluetoothHidDeviceAppQosSettings;
import android.bluetooth.BluetoothHidDeviceAppSdpSettings;
import android.bluetooth.BluetoothProfile;

import com.github.bluetoothhid.input.HidDescription;
import com.github.bluetoothhid.input.Report;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class HidDataSender implements BluetoothProfile.ServiceListener {
	private BluetoothHidDevice bluetoothHidDevice;
	private BluetoothDevice device;
	private ArrayList<Class> registeredHidDescriptions = new ArrayList<>();
	private String name;
	private String descriptor;
	private String providor;
	private BluetoothHidDevice.Callback callback;
	private byte subClass;

	public HidDataSender(String name, String descriptor, String providor, byte subClass, BluetoothHidDevice.Callback callback) {
		this.name = name;
		this.descriptor = descriptor;
		this.providor = providor;
		this.subClass = subClass;
		this.callback = callback;
	}

	public void sendReport(Report report) {
		if (bluetoothHidDevice == null) {
			return;
		}
		getBluetoothHidDevice().sendReport(device, registeredHidDescriptions.indexOf(report.getHidDescription()) + 1, report.build());
	}

	@Override
	public void onServiceConnected(int profile, BluetoothProfile proxy) {
		if (profile == BluetoothProfile.HID_DEVICE) {
			this.bluetoothHidDevice = (BluetoothHidDevice)proxy;
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			char index = 1;
			for (Class hidDescription : this.registeredHidDescriptions) {
				try {
					byteArrayOutputStream.write(((HidDescription)hidDescription.newInstance()).getDescription((byte)index));
				} catch (IllegalAccessException | InstantiationException | IOException e) {
					e.printStackTrace();
				}
				index++;
			}
			getBluetoothHidDevice().registerApp(
					new BluetoothHidDeviceAppSdpSettings(name, descriptor, providor, subClass, byteArrayOutputStream.toByteArray()),
					null,
					new BluetoothHidDeviceAppQosSettings(1, 800, 9, 0, 11250, -1),
					Runnable::run,
					callback
			);
		}
	}

	@Override
	public void onServiceDisconnected(int profile) {

	}

	public BluetoothHidDevice getBluetoothHidDevice() {
		return bluetoothHidDevice;
	}

	public void register(Class hidDescription) {
		this.registeredHidDescriptions.add(hidDescription);
	}

	public void connect(BluetoothDevice device) {
		if (bluetoothHidDevice == null) {
			return;
		}
		this.device = device;
		boolean needConnect = true;
		for (BluetoothDevice d : bluetoothHidDevice.getConnectedDevices()) {
			if (d == device) {
				needConnect = false;
			}
		}
		if (needConnect) {
			getBluetoothHidDevice().connect(device);
		}
	}
}
