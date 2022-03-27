package components;

import java.awt.Color;

import javax.swing.JTable;

public class DarkTable extends JTable {

	public DarkTable() {
		Color dark = new Color(30, 30, 30);
		this.setBackground(dark);
		this.setForeground(Color.white);
		this.getTableHeader().setOpaque(false);
		this.getTableHeader().setReorderingAllowed(false);
		this.getTableHeader().setBackground(dark.brighter());
		this.getTableHeader().setForeground(Color.WHITE);
		this.setSelectionBackground(dark.brighter().brighter());
		this.setGridColor(new Color(63, 63, 63));
		this.setOpaque(false);
		this.setShowHorizontalLines(false);
	}
}