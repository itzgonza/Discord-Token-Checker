package net.ranktw.discord.util;

import java.awt.Component;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;

import net.ranktw.discord.User;
import net.ranktw.utilities.Util;

public class TablesPopupMenu extends JPopupMenu {

	private final JMenuItem copy = this.add("Copy Token(s)");
	private final JMenuItem script;
	private final JMenuItem delete;
	private final JMenuItem selectAll;
	private final JMenuItem selectNone;
	private final JMenu sort;
	private final JMenuItem byDiscrim;
	private final JMenuItem byCreatedDate;
	private final JMenuItem byEmailVerified;
	private final JMenuItem byPhoneVerified;
	private final JMenuItem byAvatar;
	private final JMenuItem byRequiredVerify;

	public TablesPopupMenu() {
		this.copy.addActionListener(e -> {
			StringBuilder tokens = new StringBuilder();
			boolean first = true;
			JTable table = (JTable) this.getInvoker();
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			int[] selection = table.getSelectedRows();
			for (int i = 0; i < selection.length; ++i) {
				tokens.append(first ? "" : "\n")
						.append(((User) model.getValueAt(table.convertRowIndexToModel(selection[i]), 0)).getToken());
				first = false;
			}
			Util.setCopyTextToClipboard(tokens.toString());
		});
		this.copy.setAccelerator(KeyStroke.getKeyStroke(67, 2));
		this.script = this.add("Copy Login Script");
		this.script.addActionListener(e -> {
			JTable table = (JTable) this.getInvoker();
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			int[] selection = table.getSelectedRows();
			String token = ((User) model.getValueAt(table.convertRowIndexToModel(selection[0]), 0)).getToken();
			Util.setCopyTextToClipboard(String.format(
					"(function() {window.t = \"%s\";window.localStorage = document.body.appendChild(document.createElement `iframe`).contentWindow.localStorage;window.setInterval(() => window.localStorage.token = `\"${window.t}\"`); window.location.reload();})();",
					token));
		});
		this.delete = this.add("Delete");
		this.delete.setAccelerator(KeyStroke.getKeyStroke(127, 0));
		this.delete.addActionListener(e -> {
			JTable table = (JTable) this.getInvoker();
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			int[] selection = table.getSelectedRows();
			for (int i = selection.length - 1; i >= 0; --i) {
				model.removeRow(table.convertRowIndexToModel(selection[i]));
			}
		});
		this.addSeparator();
		this.selectAll = this.add("Select All");
		this.selectAll.addActionListener(e -> {
			JTable table = (JTable) this.getInvoker();
			table.selectAll();
		});
		this.selectNone = this.add("Select None");
		this.selectNone.addActionListener(e -> {
			JTable table = (JTable) this.getInvoker();
			table.clearSelection();
		});
		this.addSeparator();
		this.sort = new JMenu("Sort");
		this.add(this.sort);
		this.byDiscrim = this.sort.add("by Discrim");
		this.byCreatedDate = this.sort.add("by Created Date");
		this.byEmailVerified = this.sort.add("by Email Verified");
		this.byPhoneVerified = this.sort.add("by Phone Verified");
		this.byAvatar = this.sort.add("by Avatar");
		this.byRequiredVerify = this.sort.add("by Required Verify");
	}

	@Override
	public void show(Component c, int x, int y) {
		if (c instanceof JTable) {
			this.copy.setEnabled(((JTable) c).getSelectedRowCount() > 0);
			this.delete.setEnabled(((JTable) c).getSelectedRowCount() > 0);
			this.sort.setEnabled(((JTable) c).getRowCount() > 1);
			super.show(c, x, y);
		}
	}
}