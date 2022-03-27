package components;

import java.awt.Dimension;

import javax.swing.JButton;

class ZeroSizeButton extends JButton {

	private static final Dimension ZERO_SIZE = new Dimension();

	ZeroSizeButton() {
	}

	@Override
	public Dimension getPreferredSize() {
		return ZERO_SIZE;
	}
}