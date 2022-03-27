package net.ranktw.discord;

import java.awt.Color;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jdesktop.layout.GroupLayout;

public class CustomOutputs extends JFrame {

	public JCheckBox checkBoxWorked;
	public JCheckBox checkBoxWorked1;
	public JCheckBox checkBoxWorked2;
	public JCheckBox checkBoxWorked3;
	private JPanel panelContent;
	private Point initialClick;

	public CustomOutputs() {
		this.initComponents();
		this.setLocationRelativeTo(null);
		this.move(this, this.panelContent);
	}

	private void initComponents() {
		this.panelContent = new JPanel();
		this.checkBoxWorked = new JCheckBox();
		this.checkBoxWorked1 = new JCheckBox();
		this.checkBoxWorked2 = new JCheckBox();
		this.checkBoxWorked3 = new JCheckBox();
		this.setDefaultCloseOperation(1);
		this.panelContent.setBackground(new Color(35, 39, 42));
		this.checkBoxWorked.setForeground(new Color(187, 185, 185));
		this.checkBoxWorked.setSelected(true);
		this.checkBoxWorked.setText("Has Email");
		this.checkBoxWorked.setFocusable(false);
		this.checkBoxWorked.setIcon(new ImageIcon(this.getClass().getResource("/images/unok.png")));
		this.checkBoxWorked.setIconTextGap(7);
		this.checkBoxWorked.setMargin(new Insets(0, 0, 0, 0));
		this.checkBoxWorked.setOpaque(false);
		this.checkBoxWorked.setSelectedIcon(new ImageIcon(this.getClass().getResource("/images/okI.png")));
		this.checkBoxWorked.setVerifyInputWhenFocusTarget(false);
		this.checkBoxWorked.setVerticalAlignment(1);
		this.checkBoxWorked1.setForeground(new Color(187, 185, 185));
		this.checkBoxWorked1.setSelected(true);
		this.checkBoxWorked1.setText("Has Avatar");
		this.checkBoxWorked1.setFocusable(false);
		this.checkBoxWorked1.setIcon(new ImageIcon(this.getClass().getResource("/images/unok.png")));
		this.checkBoxWorked1.setIconTextGap(7);
		this.checkBoxWorked1.setMargin(new Insets(0, 0, 0, 0));
		this.checkBoxWorked1.setOpaque(false);
		this.checkBoxWorked1.setSelectedIcon(new ImageIcon(this.getClass().getResource("/images/okI.png")));
		this.checkBoxWorked1.setVerifyInputWhenFocusTarget(false);
		this.checkBoxWorked1.setVerticalAlignment(1);
		this.checkBoxWorked2.setForeground(new Color(187, 185, 185));
		this.checkBoxWorked2.setSelected(true);
		this.checkBoxWorked2.setText("Email Verified");
		this.checkBoxWorked2.setFocusable(false);
		this.checkBoxWorked2.setIcon(new ImageIcon(this.getClass().getResource("/images/unok.png")));
		this.checkBoxWorked2.setIconTextGap(7);
		this.checkBoxWorked2.setMargin(new Insets(0, 0, 0, 0));
		this.checkBoxWorked2.setOpaque(false);
		this.checkBoxWorked2.setSelectedIcon(new ImageIcon(this.getClass().getResource("/images/okI.png")));
		this.checkBoxWorked2.setVerifyInputWhenFocusTarget(false);
		this.checkBoxWorked2.setVerticalAlignment(1);
		this.checkBoxWorked3.setForeground(new Color(187, 185, 185));
		this.checkBoxWorked3.setSelected(true);
		this.checkBoxWorked3.setText("Phone Verified");
		this.checkBoxWorked3.setFocusable(false);
		this.checkBoxWorked3.setIcon(new ImageIcon(this.getClass().getResource("/images/unok.png")));
		this.checkBoxWorked3.setIconTextGap(7);
		this.checkBoxWorked3.setMargin(new Insets(0, 0, 0, 0));
		this.checkBoxWorked3.setOpaque(false);
		this.checkBoxWorked3.setSelectedIcon(new ImageIcon(this.getClass().getResource("/images/okI.png")));
		this.checkBoxWorked3.setVerifyInputWhenFocusTarget(false);
		this.checkBoxWorked3.setVerticalAlignment(1);
		GroupLayout panelContentLayout = new GroupLayout(this.panelContent);
		this.panelContent.setLayout(panelContentLayout);
		panelContentLayout.setHorizontalGroup(panelContentLayout.createParallelGroup(1).add(panelContentLayout
				.createSequentialGroup().addContainerGap()
				.add(panelContentLayout.createParallelGroup(1, false).add(this.checkBoxWorked1, -1, -1, Short.MAX_VALUE)
						.add(this.checkBoxWorked, -1, -1, Short.MAX_VALUE)
						.add(this.checkBoxWorked2, -1, -1, Short.MAX_VALUE)
						.add(this.checkBoxWorked3, -1, 121, Short.MAX_VALUE))
				.addContainerGap(53, Short.MAX_VALUE)));
		panelContentLayout.setVerticalGroup(panelContentLayout.createParallelGroup(1)
				.add(panelContentLayout.createSequentialGroup().addContainerGap().add(this.checkBoxWorked, -2, 24, -2)
						.addPreferredGap(0).add(this.checkBoxWorked1, -2, 24, -2).addPreferredGap(0)
						.add(this.checkBoxWorked2, -2, 24, -2).addPreferredGap(0).add(this.checkBoxWorked3, -2, 24, -2)
						.addContainerGap(-1, Short.MAX_VALUE)));
		GroupLayout layout = new GroupLayout(this.getContentPane());
		this.getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(1).add(this.panelContent, -1, -1, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(1).add(this.panelContent, -1, -1, Short.MAX_VALUE));
		this.pack();
	}

	private void move(final JFrame frame, JPanel panel) {
		panel.addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent evt) {
				int thisX = frame.getLocation().x;
				int thisY = frame.getLocation().y;
				int xMoved = thisX + evt.getX() - (thisX + ((CustomOutputs) CustomOutputs.this).initialClick.x);
				int yMoved = thisY + evt.getY() - (thisY + ((CustomOutputs) CustomOutputs.this).initialClick.y);
				int X = thisX + xMoved;
				int Y = thisY + yMoved;
				frame.setLocation(X, Y);
			}
		});
		panel.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent evt) {
				CustomOutputs.this.initialClick = evt.getPoint();
				frame.getComponentAt(CustomOutputs.this.initialClick);
			}
		});
	}
}