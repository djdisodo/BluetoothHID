package com.github.bluetoothhid.gamepad;

public @interface Button {
	byte NONE = 0;
	byte A = 1;
	byte B = (1 << 1);
	byte C = (1 << 2);
	byte D = (1 << 3);
}