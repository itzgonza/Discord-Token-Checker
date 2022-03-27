package components;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class TranslucentPanel extends JPanel {

	float alpha = 0.1f;
	boolean hover;

	public TranslucentPanel() {
		this.setForeground(Color.WHITE);
		this.setBackground(new Color(51, 51, 51));
		this.addMouseListener(new ML());
	}

	public float getAlpha() {
		return this.alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
		this.repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		if (this.hover) {
			g.setColor(new Color(30, 30, 30, 0).brighter().brighter());
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		} else {
			g.setColor(new Color(30, 30, 30, 0).brighter());
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
		Graphics2D g2 = (Graphics2D) g;
		g2.setComposite(AlphaComposite.getInstance(3, this.alpha));
		super.paintComponent(g2);
	}

	private static void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (Exception exception) {
			// empty catch block
		}
	}

	public class ML extends MouseAdapter {
		@Override
		public void mouseExited(MouseEvent me) {
			TranslucentPanel.this.hover = false;
			new Thread(() -> {
				for (float i = 1.0f; i >= 0.7f; i -= 0.03f) {
					TranslucentPanel.this.setAlpha(i);
					TranslucentPanel.sleep(10L);
				}
			}).start();
		}

		@Override
		public void mouseEntered(MouseEvent me) {
			TranslucentPanel.this.hover = true;
			new Thread(() -> {
				for (float i = 0.2f; i <= 1.0f; i += 0.03f) {
					TranslucentPanel.this.setAlpha(i);
					TranslucentPanel.sleep(10L);
				}
			}).start();
		}

		@Override
		public void mousePressed(MouseEvent me) {
			new Thread(() -> {
				for (float i = 1.0f; i >= 0.7f; i -= 0.1f) {
					TranslucentPanel.this.setAlpha(i);
					TranslucentPanel.sleep(1L);
				}
			}).start();
		}

		@Override
		public void mouseReleased(MouseEvent me) {
			new Thread(() -> {
				for (float i = 0.2f; i <= 1.0f; i += 0.03f) {
					TranslucentPanel.this.setAlpha(i);
					TranslucentPanel.sleep(10L);
				}
			}).start();
		}
	}
}