package net.ranktw.discord.util;

import java.awt.Component;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

public class TextPopupMenu extends JPopupMenu {

	private final JMenuItem copy = this.add("Copy");
	private final JMenuItem delete;

	public TextPopupMenu() {
		this.copy.addActionListener(e -> {
			JTextArea table = (JTextArea) this.getInvoker();
			table.copy();
		});
		this.copy.setAccelerator(KeyStroke.getKeyStroke(67, 2));
		this.delete = this.add("Clear Logs");
		this.delete.addActionListener(e -> {
			JTextArea table = (JTextArea) this.getInvoker();
			table.setText("");
		});
	}

	@Override
	public void show(Component c, int x, int y) {
		if (c instanceof JTextArea) {
			this.copy.setEnabled(!((JTextArea) c).getSelectedText().isEmpty());
			this.delete.setEnabled(!((JTextArea) c).getText().isEmpty());
			super.show(c, x, y);
		}
	}
}