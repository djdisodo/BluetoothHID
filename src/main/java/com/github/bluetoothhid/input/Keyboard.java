package com.github.bluetoothhid.input;

import com.github.bluetoothhid.keyboard.Key;
import com.github.bluetoothhid.keyboard.Modifier;

import java.nio.ByteBuffer;

public class Keyboard implements HidDescription {
	@Override
	public byte[] getDescription(byte id) {
		return new byte[] {
				(byte) 0x05, (byte) 0x01, // Usage page (Generic Desktop)
				(byte) 0x09, (byte) 0x06, // Usage (Keyboard)
				(byte) 0xA1, (byte) 0x01, // Collection (Application)
				(byte) 0x85, id, //    Report ID
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
		};
	}

	final public static class Report implements com.github.bluetoothhid.input.Report {
		final private ByteBuffer report = ByteBuffer.allocate(8);

		/**
		 * Send Keyboard data to the connected HID Host device. Up to six buttons pressed
		 * simultaneously are supported (not including modifier keys).
		 *
		 * @param modifier Modifier keys bit mask (Ctrl/Shift/Alt/GUI).
		 * @param key1     Scan code of the 1st button that is currently pressed (or 0 if none).
		 * @param key2     Scan code of the 2nd button that is currently pressed (or 0 if none).
		 * @param key3     Scan code of the 3rd button that is currently pressed (or 0 if none).
		 * @param key4     Scan code of the 4th button that is currently pressed (or 0 if none).
		 * @param key5     Scan code of the 5th button that is currently pressed (or 0 if none).
		 * @param key6     Scan code of the 6th button that is currently pressed (or 0 if none).
		 */
		public Report(@Modifier byte modifier, @Key byte key1, @Key byte key2, @Key byte key3, @Key byte key4, @Key byte key5, @Key byte key6) {
			report.clear();
			report.put(modifier);
			report.put((byte)0);
			report.put(key1);
			report.put(key2);
			report.put(key3);
			report.put(key4);
			report.put(key5);
			report.put(key6);
		}

		public Report(@Modifier byte modifier, @Key byte key1, @Key byte key2, @Key byte key3, @Key byte key4, @Key byte key5) {
			report.clear();
			report.put(modifier);
			report.put((byte)0);
			report.put(key1);
			report.put(key2);
			report.put(key3);
			report.put(key4);
			report.put(key5);
		}

		public Report(@Modifier byte modifier, @Key byte key1, @Key byte key2, @Key byte key3, @Key byte key4) {
			report.clear();
			report.put(modifier);
			report.put((byte)0);
			report.put(key1);
			report.put(key2);
			report.put(key3);
			report.put(key4);
		}

		public Report(@Modifier byte modifier, @Key byte key1, @Key byte key2, @Key byte key3) {
			report.clear();
			report.put(modifier);
			report.put((byte)0);
			report.put(key1);
			report.put(key2);
			report.put(key3);
		}

		public Report(@Modifier byte modifier, @Key byte key1, @Key byte key2) {
			report.clear();
			report.put(modifier);
			report.put((byte)0);
			report.put(key1);
			report.put(key2);
		}

		public Report(@Modifier byte modifier, @Key byte key1) {
			report.clear();
			report.put(modifier);
			report.put((byte)0);
			report.put(key1);
		}

		public Report(@Modifier byte modifier) {
			report.clear();
			report.put(modifier);
		}

		public Report() {
			report.clear();
		}

		public @Modifier byte getModifier() {
			return this.report.get(0);
		}

		public void setModifier(@Modifier byte modifier) {
			this.report.put(0, modifier);
		}

		public void addModifier(@Modifier byte modifier) {
			this.report.array()[0] |= modifier;
		}

		public void removeModifier(@Modifier byte modifier) {
			this.report.array()[0] &= ~modifier;
		}

		/**
		 * index <= 5
		 * @param index
		 * @return
		 */
		public @Key byte getKey(int index) {
			return this.report.get(index);
		}

		/**
		 * index <= 5
		 * @param index
		 * @return
		 */
		public void setKey(int index, @Key byte key) {
			this.report.put(index + 2, key);
		}

		public boolean addKey(@Key byte key) {
			Character index = null;
			for (char i = 2; i < 7; i++) {
				if (getKey(i) == Key.NONE && index == null) {
					index = i;
				} else if (getKey(i) == key) {
					return true;
				}
			}
			if (index != null) {
				setKey(index, key);
				return true;
			} else {
				return false;
			}
		}

		public boolean hasKey(@Key byte key) {
			for (char i = 2; i < 7; i++) {
				if (getKey(i) == key) {
					return true;
				}
			}
			return false;
		}

		public boolean removeKey(@Key byte key) {
			boolean removed = false;
			for (char i = 2; i < 7; i++) {
				if (getKey(i) == key) {
					setKey(i, Key.NONE);
					removed = true;
				}
			}
			return removed;
		}

		@Override
		public byte[] build() {
			return this.report.array();
		}

		@Override
		public Class getHidDescription() {
			return Keyboard.class;
		}
	}
}
