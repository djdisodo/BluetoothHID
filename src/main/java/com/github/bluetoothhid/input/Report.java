package com.github.bluetoothhid.input;

import com.github.bluetoothhid.input.ID;

public interface Report {
	byte[] build();
	Class getHidDescription();
}
