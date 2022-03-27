package components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JLabel;

public class DiscordButton extends JButton {

	private Color dforeground = Color.white;
	private Color dbackground = new Color(4437377);
	private static final String uiClassID = "DiscordButtonUI";
	private Color TL = new Color(1.0f, 1.0f, 1.0f, 0.0f);
	private int round = 7;

	public DiscordButton() {
		this.setName("discord.button");
		this.dforeground = Color.white;
		this.setForeground(this.dforeground);
		this.setBackground(new Color(4437377));
		this.setCursor(new Cursor(12));
	}

	@Override
	public void setForeground(Color fg) {
		super.setForeground(fg);
		this.dforeground = fg;
	}

	@Override
	public void setBackground(Color bg) {
		super.setBackground(bg);
		this.dbackground = bg;
	}

	public int getRound() {
		return this.round;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public Color getTL() {
		return this.TL;
	}

	public void setTL(Color TL) {
		this.TL = TL;
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
		RoundRectangle2D.Double area = new RoundRectangle2D.Double(x, y, w, h, this.round, this.round);
		Color bg = this.getBackground();
		ButtonModel m = this.getModel();
		if (m.isPressed()) {
			bg = bg.darker();
		} else if (m.isRollover()) {
			bg = new Color(bg.getRed() - 18, bg.getGreen() - 18, bg.getBlue() - 18);
		}
		g2.setPaint(new GradientPaint((float) x, y, bg, (float) x, y + h, bg, true));
		g2.fill(area);
		g2.setPaint(this.TL);
		g2.draw(area);
		g2.dispose();
		super.paintComponent(g);
	}

	@Override
	public void setEnabled(boolean b) {
		super.setEnabled(b);
		super.setForeground(b ? this.dforeground : new Color(109, 109, 109));
		super.setBackground(b ? this.dbackground : this.dbackground.darker());
	}

	/*
	 * Enabled aggressive block sorting Enabled unnecessary exception pruning
	 * Enabled aggressive exception aggregation
	 */
	public static Font loadFont(String fontName, float size, int style) {
		try (InputStream is = JLabel.class.getResourceAsStream(fontName);) {
			Font font2 = Font.createFont(0, is);
			font2 = font2.deriveFont(size);
			Font font = font2 = font2.deriveFont(style);
			return font;
		} catch (FontFormatException ex) {
			System.out.println(fontName + " Font is null FontFormatException");
			return null;
		} catch (IOException e) {
			System.out.println(fontName + " Font is null IOException");
		}
		return null;
	}
}