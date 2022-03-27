package net.ranktw.discord;

import java.awt.Component;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

class TablePopupMenu extends JPopupMenu {

	private final JMenuItem delete = this.add("delete");

	protected TablePopupMenu() {
		this.delete.addActionListener(e -> {
			JTable table = (JTable) this.getInvoker();
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			int[] selection = table.getSelectedRows();
			for (int i = selection.length - 1; i >= 0; --i) {
				model.removeRow(table.convertRowIndexToModel(selection[i]));
			}
		});
	}

	@Override
	public void show(Component c, int x, int y) {
		if (c instanceof JTable) {
			this.delete.setEnabled(((JTable) c).getSelectedRowCount() > 0);
			super.show(c, x, y);
		}
	}
}