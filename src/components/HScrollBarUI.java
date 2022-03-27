package components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.metal.MetalScrollBarUI;

public class HScrollBarUI extends MetalScrollBarUI {

	private Color color = new Color(30, 30, 30);

	public HScrollBarUI setColor(Color c) {
		this.color = c;
		return this;
	}

	private JButton zero() {
		JButton b = new JButton();
		Dimension d = new Dimension(0, 0);
		b.setPreferredSize(d);
		b.setMaximumSize(d);
		b.setMinimumSize(d);
		return b;
	}

	@Override
	protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
	}

	@Override
	protected void paintDecreaseHighlight(Graphics g) {
	}

	@Override
	protected void paintIncreaseHighlight(Graphics g) {
	}

	@Override
	protected JButton createIncreaseButton(int orientation) {
		return this.zero();
	}

	@Override
	protected JButton createDecreaseButton(int orientation) {
		return this.zero();
	}

	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
		thumbBounds.y += thumbBounds.height - 5;
		thumbBounds.height = 5;
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(this.color);
		g2d.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 3, 3);
	}
}