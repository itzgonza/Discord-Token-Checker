package components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;

import com.alee.laf.label.WebLabel;

public class TopToolbars extends JPanel {

	private JFrame frame;
	private Point initialClick;
	private JPanel close;
	private JLabel closeLabel;
	private WebLabel label;
	private WebLabel copyright;
	private JPanel minimize;
	private JLabel minimizeLabel;

	public TopToolbars() {
		this.initComponents();
	}

	public TopToolbars(JFrame frame) {
		this();
		this.frame = frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JFrame getFrame() {
		return this.frame;
	}

	private void initComponents() {
		this.label = new WebLabel();
		this.close = new JPanel();
		this.closeLabel = new JLabel();
		this.minimize = new JPanel();
		this.minimizeLabel = new JLabel();
		this.copyright = new WebLabel();
		this.setBackground(new Color(32, 34, 37));
		this.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent evt) {
				TopToolbars.this.formMouseDragged(evt);
			}
		});
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent evt) {
				TopToolbars.this.formMousePressed(evt);
			}
		});
		this.label.setForeground(new Color(255, 255, 255));
		this.label.setText("Discord Client");
		this.label.setFont(TopToolbars.loadFont("/fonts/Discordia.otf", 16.0f, 0));
		this.label.setMargin(new Insets(4, 0, 0, 0));
		this.close.setBackground(new Color(255, 255, 255, 0));
		this.close.setCursor(new Cursor(12));
		this.close.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent evt) {
				TopToolbars.this.closeMouseEntered(evt);
			}

			@Override
			public void mouseExited(MouseEvent evt) {
				TopToolbars.this.closeMouseExited(evt);
			}

			@Override
			public void mouseReleased(MouseEvent evt) {
			}
		});
		this.closeLabel.setFont(TopToolbars.loadFont("/fonts/OpenSans-Bold.ttf", 12.0f, 1));
		this.closeLabel.setForeground(new Color(255, 255, 255, 127));
		this.closeLabel.setHorizontalAlignment(0);
		this.closeLabel.setText("x");
		this.minimize.setBackground(new Color(255, 255, 255, 0));
		this.minimize.setCursor(new Cursor(12));
		this.minimize.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent evt) {
				TopToolbars.this.minimizeMouseEntered(evt);
			}

			@Override
			public void mouseExited(MouseEvent evt) {
				TopToolbars.this.minimizeMouseExited(evt);
			}

			@Override
			public void mouseReleased(MouseEvent evt) {
				TopToolbars.this.minimizeMouseReleased(evt);
			}
		});
		this.minimizeLabel.setFont(TopToolbars.loadFont("/fonts/OpenSans-Bold.ttf", 12.0f, 1));
		this.minimizeLabel.setForeground(new Color(255, 255, 255, 127));
		this.minimizeLabel.setHorizontalAlignment(0);
		this.minimizeLabel.setText("-");
		this.copyright.setForeground(new Color(255, 255, 255, 127));
		this.copyright.setText("© RANKTW  -  shoppy.gg/@RANKTW");
		this.copyright.setFont(TopToolbars.loadFont("/fonts/Discordia.otf", 8.0f, 0));
		this.copyright.setMargin(new Insets(7, 0, 0, 0));
		GroupLayout closeLayout = new GroupLayout(this.close);
		this.close.setLayout(closeLayout);
		closeLayout.setHorizontalGroup(closeLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING, closeLayout.createSequentialGroup()
						.addGap(0, 0, Short.MAX_VALUE).addComponent(this.closeLabel, -2, 33, -2)));
		closeLayout.setVerticalGroup(closeLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(this.closeLabel, -1, -1, Short.MAX_VALUE));
		GroupLayout minimizeLayout = new GroupLayout(this.minimize);
		this.minimize.setLayout(minimizeLayout);
		minimizeLayout.setHorizontalGroup(minimizeLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING, minimizeLayout.createSequentialGroup()
						.addGap(0, 0, Short.MAX_VALUE).addComponent(this.minimizeLabel, -2, 33, -2)));
		minimizeLayout.setVerticalGroup(minimizeLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(this.minimizeLabel, GroupLayout.Alignment.TRAILING, -1, -1, Short.MAX_VALUE));
		GroupLayout layout = new GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(7, 7, 7).addComponent(this.label, -2, -1, -2)
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(this.copyright, -2, -1, -2)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
						.addComponent(this.minimize, -2, -1, -2).addGap(0, 0, 0).addComponent(this.close, -2, -1, -2)));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(this.label, -1, -1, Short.MAX_VALUE)
								.addComponent(this.close, -1, -1, Short.MAX_VALUE)
								.addComponent(this.minimize, -1, -1, Short.MAX_VALUE)
								.addComponent(this.copyright, -1, 33, Short.MAX_VALUE))
						.addGap(0, 0, 0)));
	}

	private void closeMouseEntered(MouseEvent evt) {
		evt.getComponent().setBackground(new Color(15746887));
		this.closeLabel.setForeground(Color.white);
	}

	private void closeMouseExited(MouseEvent evt) {
		evt.getComponent().setBackground(new Color(32, 34, 37));
		this.closeLabel.setForeground(new Color(255, 255, 255, 127));
	}

	private void minimizeMouseEntered(MouseEvent evt) {
		evt.getComponent().setBackground(new Color(3553338));
		this.minimizeLabel.setForeground(Color.white);
	}

	private void minimizeMouseExited(MouseEvent evt) {
		evt.getComponent().setBackground(new Color(32, 34, 37));
		this.minimizeLabel.setForeground(new Color(255, 255, 255, 127));
	}

	private void formMousePressed(MouseEvent evt) {
		this.initialClick = evt.getPoint();
		this.frame.getComponentAt(this.initialClick);
	}

	private void formMouseDragged(MouseEvent evt) {
		int thisX = this.frame.getLocation().x;
		int thisY = this.frame.getLocation().y;
		int xMoved = thisX + evt.getX() - (thisX + this.initialClick.x);
		int yMoved = thisY + evt.getY() - (thisY + this.initialClick.y);
		int X = thisX + xMoved;
		int Y = thisY + yMoved;
		this.frame.setLocation(X, Y);
	}

	private void closeMouseReleased(MouseEvent evt) {
		if (evt.getButton() == 1) {
			System.exit(-1);
		}
	}

	private void minimizeMouseReleased(MouseEvent evt) {
		if (evt.getButton() == 1) {
			this.frame.setState(1);
		}
	}

	public void addCloseMouseListener(MouseListener listener) {
		this.close.addMouseListener(listener);
	}

	public void setTitle(String title) {
		this.label.setText(title);
	}

	/*
	 * Enabled aggressive block sorting Enabled unnecessary exception pruning
	 * Enabled aggressive exception aggregation
	 */
	private static Font loadFont(String fontName, float size, int style) {
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