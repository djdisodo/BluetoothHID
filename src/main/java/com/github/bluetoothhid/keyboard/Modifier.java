package com.github.bluetoothhid.keyboard;

public @interface Modifier {
	byte NONE = 0;
	byte LEFT_CTRL = 1;
	byte LEFT_SHIFT = (1 << 1);
	byte LEFT_ALT = (1 << 2);
	byte LEFT_GUI = (1 << 3);
	byte RIGHT_CTRL = (1 << 4);
	byte RIGHT_SHIFT = (1 << 5);
	byte RIGHT_ALT = (1 << 6);
	byte RIGHT_GUI = (byte)(1 << 7);
	class Builder {
		private byte modifier = NONE;
		public Builder setLeftCtrl() {
			modifier |= LEFT_CTRL;
			return this;
		}

		public Builder setLeftShift() {
			modifier |= LEFT_SHIFT;
			return this;
		}

		public Builder setLeftAlt() {
			modifier |= LEFT_ALT;
			return this;
		}

		public Builder setLeftGui() {
			modifier |= LEFT_GUI;
			return this;
		}

		public Builder setRightCtrl() {
			modifier |= RIGHT_CTRL;
			return this;
		}

		public Builder setRightShift() {
			modifier |= RIGHT_SHIFT;
			return this;
		}

		public Builder setRightAlt() {
			modifier |= RIGHT_ALT;
			return this;
		}

		public Builder setRightGui() {
			modifier |= RIGHT_GUI;
			return this;
		}

		public @Modifier byte build() {
			return this.modifier;
		}
	}
}
