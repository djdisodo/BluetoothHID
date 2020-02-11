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

package com.github.bluetoothhid.keyboard;

import androidx.annotation.NonNull;

import com.github.bluetoothhid.bluetooth.HidDataSender;
import com.github.bluetoothhid.keyboard.Key;
import com.github.bluetoothhid.keyboard.Modifier;
import com.github.bluetoothhid.report.KeyboardReport;
import com.google.common.collect.ImmutableMap;

import org.apache.commons.codec.binary.Hex;

/**
 * Helper class that allows sending less key press states, keeps some handy constants and translates
 * characters to scan codes.
 */
public class KeyboardHelper {

    private static final ImmutableMap<Character, Byte> keyMap =
            new ImmutableMap.Builder<Character, Byte>()
                    .put('a', (byte)0x04)
                    .put('b', (byte)0x05)
                    .put('c', (byte)0x06)
                    .put('d', (byte)0x07)
                    .put('e', (byte)0x08)
                    .put('f', (byte)0x09)
                    .put('g', (byte)0x0A)
                    .put('h', (byte)0x0B)
                    .put('i', (byte)0x0C)
                    .put('j', (byte)0x0D)
                    .put('k', (byte)0x0E)
                    .put('l', (byte)0x0F)
                    .put('m', (byte)0x10)
                    .put('n', (byte)0x11)
                    .put('o', (byte)0x12)
                    .put('p', (byte)0x13)
                    .put('q', (byte)0x14)
                    .put('r', (byte)0x15)
                    .put('s', (byte)0x16)
                    .put('t', (byte)0x17)
                    .put('u', (byte)0x18)
                    .put('v', (byte)0x19)
                    .put('w', (byte)0x1A)
                    .put('x', (byte)0x1B)
                    .put('y', (byte)0x1C)
                    .put('z', (byte)0x1D)
                    .put('1', (byte)0x1E)
                    .put('2', (byte)0x1F)
                    .put('3', (byte)0x20)
                    .put('4', (byte)0x21)
                    .put('5', (byte)0x22)
                    .put('6', (byte)0x23)
                    .put('7', (byte)0x24)
                    .put('8', (byte)0x25)
                    .put('9', (byte)0x26)
                    .put('0', (byte)0x27)
                    .put(' ', (byte)0x2C)
                    .put('-', (byte)0x2D)
                    .put('=', (byte)0x2E)
                    .put('[', (byte)0x2F)
                    .put(']', (byte)0x30)
                    .put('\\', (byte)0x31)
                    .put(';', (byte)0x33)
                    .put('\'', (byte)0x34)
                    .put('`', (byte)0x35)
                    .put(',', (byte)0x36)
                    .put('.', (byte)0x37)
                    .put('/', (byte)0x38)
                    .build();

    private static final ImmutableMap<Character, Byte> shiftKeyMap =
            new ImmutableMap.Builder<Character, Byte>()
                    .put('A', (byte)0x04)
                    .put('B', (byte)0x05)
                    .put('C', (byte)0x06)
                    .put('D', (byte)0x07)
                    .put('E', (byte)0x08)
                    .put('F', (byte)0x09)
                    .put('G', (byte)0x0A)
                    .put('H', (byte)0x0B)
                    .put('I', (byte)0x0C)
                    .put('J', (byte)0x0D)
                    .put('K', (byte)0x0E)
                    .put('L', (byte)0x0F)
                    .put('M', (byte)0x10)
                    .put('N', (byte)0x11)
                    .put('O', (byte)0x12)
                    .put('P', (byte)0x13)
                    .put('Q', (byte)0x14)
                    .put('R', (byte)0x15)
                    .put('S', (byte)0x16)
                    .put('T', (byte)0x17)
                    .put('U', (byte)0x18)
                    .put('V', (byte)0x19)
                    .put('W', (byte)0x1A)
                    .put('X', (byte)0x1B)
                    .put('Y', (byte)0x1C)
                    .put('Z', (byte)0x1D)
                    .put('!', (byte)0x1E)
                    .put('@', (byte)0x1F)
                    .put('#', (byte)0x20)
                    .put('$', (byte)0x21)
                    .put('%', (byte)0x22)
                    .put('^', (byte)0x23)
                    .put('&', (byte)0x24)
                    .put('*', (byte)0x25)
                    .put('(', (byte)0x26)
                    .put(')', (byte)0x27)
                    .put('_', (byte)0x2D)
                    .put('+', (byte)0x2E)
                    .put('{', (byte)0x2F)
                    .put('}', (byte)0x30)
                    .put('|', (byte)0x31)
                    .put(':', (byte)0x33)
                    .put('"', (byte)0x34)
                    .put('~', (byte)0x35)
                    .put('<', (byte)0x36)
                    .put('>', (byte)0x37)
                    .put('?', (byte)0x38)
                    .build();

    private final HidDataSender dataSender;

    /**
     * @param dataSender Interface to send the Keyboard data with.
     */
    public KeyboardHelper(@NonNull HidDataSender dataSender) {
        this.dataSender = dataSender;
    }
    /**
     * Send a key press event, followed by an immediate release event, for the specified character.
     *
     * @param character Character to send.
     */
    public void sendChar(char character) {
        Modifier.Builder builder = new Modifier.Builder();
        @Key Byte key = keyMap.get(character);
        if (key == null) {
        	builder.setLeftShift();
            key = shiftKeyMap.get(character);

			System.out.println(character);
            if (key == null) {
                return;
            }
        }
        dataSender.sendReport(new KeyboardReport(builder.build(), key));
        dataSender.sendReport(KeyboardReport.NONE);
    }
    public void sendString(String text) {
		for (char character :
			 text.replace("\r\n", "\n").toCharArray()) {
			if (character == '\n') {
				dataSender.sendReport(new KeyboardReport(Modifier.NONE, Key.ENTER));
				dataSender.sendReport(KeyboardReport.NONE);
			} else {
				sendChar(character);
			}
		}
	}
}
