/*
 * Copyright 2018 Google LLC All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.bluetoothhid.report;

import com.github.bluetoothhid.keyboard.Key;
import com.github.bluetoothhid.keyboard.Modifier;

import java.nio.ByteBuffer;

/**
 * Helper class to store the keyboard state and retrieve the binary report.
 */
public class KeyboardReport implements Report{

    final private ByteBuffer report = ByteBuffer.allocate(8);

    final public static KeyboardReport NONE = new KeyboardReport();
    
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
    public KeyboardReport(@Modifier byte modifier, @Key byte key1, @Key byte key2, @Key byte key3, @Key byte key4, @Key byte key5, @Key byte key6) {
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

	public KeyboardReport(@Modifier byte modifier, @Key byte key1, @Key byte key2, @Key byte key3, @Key byte key4, @Key byte key5) {
		report.clear();
		report.put(modifier);
		report.put((byte)0);
		report.put(key1);
		report.put(key2);
		report.put(key3);
		report.put(key4);
		report.put(key5);
	}

	public KeyboardReport(@Modifier byte modifier, @Key byte key1, @Key byte key2, @Key byte key3, @Key byte key4) {
		report.clear();
		report.put(modifier);
		report.put((byte)0);
		report.put(key1);
		report.put(key2);
		report.put(key3);
		report.put(key4);
	}

	public KeyboardReport(@Modifier byte modifier, @Key byte key1, @Key byte key2, @Key byte key3) {
		report.clear();
		report.put(modifier);
		report.put((byte)0);
		report.put(key1);
		report.put(key2);
		report.put(key3);
	}

	public KeyboardReport(@Modifier byte modifier, @Key byte key1, @Key byte key2) {
		report.clear();
		report.put(modifier);
		report.put((byte)0);
		report.put(key1);
		report.put(key2);
	}

	public KeyboardReport(@Modifier byte modifier, @Key byte key1) {
		report.clear();
		report.put(modifier);
		report.put((byte)0);
		report.put(key1);
	}

	public KeyboardReport(@Modifier byte modifier) {
		report.clear();
		report.put(modifier);
	}

	public KeyboardReport() {
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
	public @ID byte getID() {
		return ID.KEYBOARD;
	}

	@Override
	public byte[] build() {
		return this.report.array();
	}
}
