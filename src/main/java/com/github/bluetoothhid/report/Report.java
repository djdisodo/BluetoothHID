package com.github.bluetoothhid.report;

public interface Report {

	public @ID byte getID();
	public byte[] build();
}
