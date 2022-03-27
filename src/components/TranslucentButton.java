package components;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class TranslucentButton extends JButton {

	private static Color TL = new Color(1.0f, 1.0f, 1.0f, 0.0f);
	private static Color HT = new Color(0.7f, 0.7f, 0.7f, 0.07f);
	private static Color PT = new Color(0.7f, 0.7f, 0.7f, 0.11f);
	private static int round = 9;

	public TranslucentButton() {
	}

	public TranslucentButton(ImageIcon icon) {
		this.setIcon(icon);
		this.setSelectedIcon(icon);
		this.setPressedIcon(icon);
		this.setRolloverIcon(icon);
		this.setRolloverSelectedIcon(icon);
	}

	public static int getRound() {
		return round;
	}

	public static void setRound(int round) {
		TranslucentButton.round = round;
	}

	public static Color getPT() {
		return PT;
	}

	public static Color getTL() {
		return TL;
	}

	public static Color getHT() {
		return HT;
	}

	public static void setTL(Color TL) {
		TranslucentButton.TL = TL;
	}

	public static void setHT(Color HT) {
		TranslucentButton.HT = HT;
	}

	public static void setPT(Color PT) {
		TranslucentButton.PT = PT;
	}

	@Override
	public void updateUI() {
		super.updateUI();
		this.setContentAreaFilled(false);
		this.setFocusPainted(false);
		this.setBorderPainted(false);
		this.setOpaque(false);
		this.setForeground(Color.WHITE);
	}

	@Override
	protected void paintComponent(Graphics g) {
		int x = 0;
		int y = 0;
		int w = this.getWidth();
		int h = this.getHeight();
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		RoundRectangle2D.Double area = new RoundRectangle2D.Double(x, y, w, h, round, round);
		Color ssc = TL;
		ButtonModel m = this.getModel();
		if (m.isPressed()) {
			ssc = PT;
		} else if (m.isRollover()) {
			ssc = HT;
		}
		g2.setPaint(new GradientPaint((float) x, y, ssc, (float) x, y + h, ssc, true));
		g2.fill(area);
		g2.setPaint(TL);
		g2.draw(area);
		g2.dispose();
		super.paintComponent(g);
	}
}