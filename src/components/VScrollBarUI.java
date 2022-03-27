package components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.metal.MetalScrollBarUI;

public class VScrollBarUI extends MetalScrollBarUI {

	private boolean setX;
	private Color color = new Color(30, 30, 30);

	public VScrollBarUI setSetX(boolean setX) {
		this.setX = setX;
		return this;
	}

	public VScrollBarUI setColor(Color c) {
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
	protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
		if (this.setX) {
			r.x += r.width - 5;
		}
		r.width = 5;
		Graphics2D g2d = (Graphics2D) g;
		g2d.setPaint(Color.WHITE);
		g2d.fillRoundRect(r.x, r.y, r.width, r.height, 3, 3);
	}
}