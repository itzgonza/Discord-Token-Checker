package components;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JTextArea;

import org.jdesktop.swingx.border.DropShadowBorder;

public class HintTextArea extends JTextArea {

	private String hint = null;
	private Color hintForeground = new Color(-1598769996, true);
	private Color textBackground = new Color(0x252525);
	private boolean hideOnFocus = false;

	public HintTextArea() {
		this.setForeground(new Color(204, 204, 204));
		this.setForeground(new Color(204, 204, 204));
		DropShadowBorder shadowBorder = new DropShadowBorder();
		shadowBorder.setShadowSize(4);
		shadowBorder.setShowLeftShadow(true);
		shadowBorder.setShowTopShadow(true);
		this.setBorder(shadowBorder);
		this.setCaretColor(new Color(204, 204, 204));
		this.setOpaque(false);
		this.setSelectionColor(new Color(75, 111, 175));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if (!(this.hint == null || this.getText().length() != 0 || this.hideOnFocus && this.hasFocus())) {
			g.setColor(this.hintForeground);
			int padding = (this.getHeight() - this.getFont().getSize()) / 2;
			int width = g.getFontMetrics().stringWidth(this.hint);
			g.drawString(this.hint, this.getWidth() / 2 - width / 2, this.getHeight() - padding - 1);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
	}

	public String getHint() {
		return this.hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	public boolean isHideOnFocus() {
		return this.hideOnFocus;
	}

	public void setHideOnFocus(boolean hideOnFocus) {
		this.hideOnFocus = hideOnFocus;
	}

	public Color getHintForeground() {
		return this.hintForeground;
	}

	public void setHintForeground(Color hintForeground) {
		this.hintForeground = hintForeground;
	}

	public void setTextBackground(Color textBackground) {
		this.textBackground = textBackground;
	}

	public Color getTextBackground() {
		return this.textBackground;
	}
}