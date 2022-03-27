package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JTextField;

import org.jdesktop.swingx.border.DropShadowBorder;

public class HintTextField extends JTextField {

	private String hint = null;
	private Color hintForeground = new Color(-1598769996, true);
	private Color textBackground = new Color(0x252525);
	private boolean hideOnFocus = false;

	public HintTextField() {
		this.setForeground(new Color(204, 204, 204));
		this.setHorizontalAlignment(0);
		this.setForeground(new Color(204, 204, 204));
		this.setHorizontalAlignment(0);
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
		int w = this.getWidth() - 8;
		int h = this.getHeight() - 8;
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setPaint(this.textBackground);
		g2.fillRect(4, 4, w, h);
		g2.dispose();
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