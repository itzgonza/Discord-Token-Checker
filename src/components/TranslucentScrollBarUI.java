package components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;

class TranslucentScrollBarUI extends BasicScrollBarUI {

	private static final Color DEFAULT_COLOR = new Color(220, 220, 220, 100);
	private static final Color DRAGGING_COLOR = new Color(200, 200, 200, 100);
	private static final Color ROLLOVER_COLOR = new Color(255, 250, 245, 100);

	TranslucentScrollBarUI() {
	}

	@Override
	protected JButton createDecreaseButton(int orientation) {
		return new ZeroSizeButton();
	}

	@Override
	protected JButton createIncreaseButton(int orientation) {
		return new ZeroSizeButton();
	}

	@Override
	protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
	}

	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
		JScrollBar sb = (JScrollBar) c;
		r.width = 7;
		r.x = 5;
		if (!sb.isEnabled() || r.width > r.height) {
			return;
		}
		Color color = this.isDragging ? DRAGGING_COLOR : (this.isThumbRollover() ? ROLLOVER_COLOR : DEFAULT_COLOR);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setPaint(color);
		g2.fillRoundRect(r.x, r.y, r.width - 1, r.height - 1, 3, 3);
		g2.setPaint(new Color(844782170, true));
		g2.drawRect(r.x, r.y, r.width - 1, r.height - 1);
		g2.dispose();
	}
}