package com.github.bluetoothhid.input;

import com.github.bluetoothhid.gamepad.Button;

import java.nio.ByteBuffer;

public class GamePad implements HidDescription {
	@Override
	public byte[] getDescription(byte id) {
		return new byte[] {
				0x05, 0x01,        // USAGE_PAGE (Generic Desktop)
				0x09, 0x05,        // USAGE (Game Pad)
				(byte) 0xa1, 0x01, // COLLECTION (Application)
				(byte) 0x85, id, //    Report ID
				(byte) 0xa1, 0x00, //   COLLECTION (Physical)
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
		};
	}

	final public static class Report implements com.github.bluetoothhid.input.Report {
		final private ByteBuffer report = ByteBuffer.allocate(3);
		public Report(byte channel, @Button byte button, byte x, byte y) {
			report.put((byte)((channel << 4) | button));
			report.put(x);
			report.put(y);
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
