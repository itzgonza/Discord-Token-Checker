package components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

import com.alee.laf.checkbox.WebCheckBox;

import dev.Main;

public class DarkCheckBox extends JPanel {

	private JFrame frame;
	private String text = "Discord";
	private BackgroundPanel background;
	private WebCheckBox checkBox;
	private JLabel label;

	public DarkCheckBox() {
		this((JFrame) null);
	}

	public DarkCheckBox(JFrame frame) {
		this.initComponents();
		this.setFrame(frame);
	}

	private void initComponents() {
		this.label = new JLabel();
		this.checkBox = new WebCheckBox();
		this.background = new BackgroundPanel();
		this.label.setBackground(new Color(43, 46, 50));
		this.label.setForeground(new Color(255, 255, 255));
		this.label.setText("Label");
		this.label.setFont(Main.loadFont("/fonts/OpenSans-Bold.ttf", 11.0f, 0));
		this.setBackground(new Color(54, 57, 63));
		this.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent evt) {
				DarkCheckBox.this.panelComponentResized(evt);
			}
		});
		this.setLayout(new AbsoluteLayout());
		this.checkBox.setBackground(new Color(47, 50, 55));
		this.checkBox.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		this.checkBox.setForeground(this.label.getForeground());
		this.checkBox.setText("Discord");
		this.checkBox.setFont(this.label.getFont());
		this.checkBox.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/images/unok.png")).getImage()
				.getScaledInstance(22, 22, 1)));
		this.checkBox.setPressedIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/images/unok.png"))
				.getImage().getScaledInstance(22, 22, 1)));
		this.checkBox.setSelectedIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/images/ok-2.png"))
				.getImage().getScaledInstance(22, 22, 1)));
		this.checkBox.addActionListener(this::checkBoxActionPerformed);
		this.add((Component) this.checkBox, new AbsoluteConstraints(3, 3, 114, 24));
		this.background.setPreferredSize(new Dimension(99, 30));
		this.add((Component) this.background, new AbsoluteConstraints(0, 0, 120, 30));
	}

	public void addActionListener(ActionListener l) {
		this.checkBox.addActionListener(l);
	}

	private void panelComponentResized(ComponentEvent evt) {
		this.remove(this.checkBox);
		this.add((Component) this.checkBox, new AbsoluteConstraints(3, 3, this.getWidth() - 6, this.getHeight() - 6));
		this.background.setWidthAndHeight(this.getWidth(), this.getHeight());
		this.remove(this.background);
		this.add((Component) this.background, new AbsoluteConstraints(0, 0, this.getWidth(), this.getHeight()));
		this.frame.repaint();
	}

	private void checkBoxActionPerformed(ActionEvent evt) {
		this.background.setDark(this.checkBox.isSelected());
		this.frame.repaint();
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JFrame getFrame() {
		return this.frame;
	}

	public String getText() {
		this.text = this.checkBox.getText();
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
		this.checkBox.setText(this.text);
	}

	public boolean isSelected() {
		return this.checkBox.isSelected();
	}

	public void setSelected(boolean selected) {
		this.checkBox.setSelected(selected);
		this.checkBoxActionPerformed(null);
	}
}