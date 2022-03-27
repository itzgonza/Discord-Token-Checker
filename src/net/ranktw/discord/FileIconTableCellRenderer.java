package net.ranktw.discord;

import java.awt.Component;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;

class FileIconTableCellRenderer extends DefaultTableCellRenderer {

	private final FileSystemView fileSystemView;

	protected FileIconTableCellRenderer(FileSystemView fileSystemView) {
		this.fileSystemView = fileSystemView;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		l.setHorizontalAlignment(2);
		l.setIcon(null);
		File file = (File) value;
		int c = table.convertColumnIndexToModel(column);
		switch (c) {
		case 0: {
			l.setIcon(this.fileSystemView.getSystemIcon(file));
			l.setText(this.fileSystemView.getSystemDisplayName(file));
			break;
		}
		case 1: {
			l.setHorizontalAlignment(4);
			l.setText(file.isDirectory() ? null : Long.toString(file.length()) + "   ");
			break;
		}
		case 2: {
			l.setText(file.getAbsolutePath());
			break;
		}
		}
		return l;
	}
}