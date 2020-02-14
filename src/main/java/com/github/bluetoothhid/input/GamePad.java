package com.github.bluetoothhid.input;

import com.github.bluetoothhid.gamepad.Button;

import java.nio.ByteBuffer;

public class GamePad implements HidDescription {
	@Override
	public byte[] getDescription(byte id) {
		return new byte[] {
				(byte)0x05, (byte)0x01,        // USAGE_PAGE (Generic Desktop)
				(byte)0x09, (byte)0x05,        // USAGE (Game Pad)
				(byte)0xa1, (byte)0x01,        // COLLECTION (Application)
				(byte)0x85, id,                //    Report ID
				(byte)0xa1, (byte)0x00,        //   COLLECTION (Physical)
				(byte)0x05, (byte)0x09,        //     USAGE_PAGE (Button)
				(byte)0x19, (byte)0x01,        //     USAGE_MINIMUM (Button 1)
				(byte)0x29, (byte)0x04,        //     USAGE_MAXIMUM (Button 4)
				(byte)0x15, (byte)0x00,        //     LOGICAL_MINIMUM (0)
				(byte)0x25, (byte)0x01,        //     LOGICAL_MAXIMUM (1)
				(byte)0x75, (byte)0x01,        //     REPORT_SIZE (1)
				(byte)0x95, (byte)0x04,        //     REPORT_COUNT (4)
				(byte)0x81, (byte)0x02,        //     INPUT (Data,Var,Abs)
				(byte)0x75, (byte)0x04,        //     REPORT_SIZE (4)
				(byte)0x95, (byte)0x01,        //     REPORT_COUNT (1)
				(byte)0x81, (byte)0x03,        //     INPUT (Cnst,Var,Abs)
				(byte)0x05, (byte)0x01,        //     USAGE_PAGE (Generic Desktop)
				(byte)0x09, (byte)0x30,        //     USAGE (X)
				(byte)0x09, (byte)0x31,        //     USAGE (Y)
				(byte)0x09, (byte)0x32,        //     USAGE (Z)
				(byte)0x09, (byte)0x33,        //     USAGE (Rx)
				(byte)0x15, (byte)0x81,        //     LOGICAL_MINIMUM (-127)
				(byte)0x25, (byte)0x7f,        //     LOGICAL_MAXIMUM (127)
				(byte)0x75, (byte)0x08,        //     REPORT_SIZE (8)
				(byte)0x95, (byte)0x04,        //     REPORT_COUNT (4)
				(byte)0x81, (byte)0x02,        //     INPUT (Data,Var,Abs)
				(byte)0xc0,                    //   END_COLLECTION
				(byte)0xc0                     // END_COLLECTION
		};
	}

	final public static class Report implements com.github.bluetoothhid.input.Report {
		final private ByteBuffer report = ByteBuffer.allocate(5);
		public Report(byte channel, @Button byte button, byte x, byte y, byte z, byte Rx) {
			report.put((byte)((channel << 4) | button));
			report.put(x);
			report.put(y);
			report.put(z);
			report.put(Rx);
		}

		public byte getChannel() {
			return (byte)(report.get(0) >> 4);
		}

		public void setChannel(byte channel) {
			report.put(0, (byte)(report.get(0) | (channel << 4)));
		}

		public @Button byte getButton() {
			return (byte)(report.get(0) & 0x0f);
		}

		public void setButton(@Button byte button) {
			report.put((byte) ((report.get(0) & 0xf0) | (button & 0x0f)));
		}

		public void addButton(@Button byte button) {
			report.put((byte) (report.get(0) | (button & 0x0f)));
		}

		public void removeButton(@Button byte button) {
			report.put((byte) (report.get(0) & ~(button & 0x0f)));
		}

		public byte getX() {
			return report.get(1);
		}

		public void setX(byte x) {
			report.put(1, x);
		}

		public byte getY() {
			return report.get(2);
		}

		public void setY(byte y) {
			report.put(2, y);
		}

		public byte getZ() {
			return report.get(3);
		}

		public void setZ(byte z) {
			report.put(3, z);
		}

		public byte getRx() {
			return report.get(4);
		}

		public void setRx(byte rX) {
			report.put(4, rX);
		}
		@Override
		public byte[] build() {
			return report.array();
		}

		@Override
		public Class getHidDescription() {
			return GamePad.class;
		}
	}
}
