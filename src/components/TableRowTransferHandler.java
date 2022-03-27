package components;

import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DragSource;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;
import javax.swing.table.DefaultTableModel;

public class TableRowTransferHandler extends TransferHandler {

	public final DataFlavor localObjectFlavor = new DataFlavor(List.class, "List of items");
	public int[] indices;
	public int addIndex = -1;
	public int addCount;
	public JComponent source;

	@Override
	public Transferable createTransferable(JComponent c) {
		c.getRootPane().getGlassPane().setVisible(true);
		this.source = c;
		JTable table = (JTable) c;
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		this.indices = table.getSelectedRows();
		final List transferedObjects = Arrays.stream(this.indices).mapToObj(model.getDataVector()::get)
				.collect(Collectors.toList());
		return new Transferable() {

			@Override
			public DataFlavor[] getTransferDataFlavors() {
				return new DataFlavor[] { TableRowTransferHandler.this.localObjectFlavor };
			}

			@Override
			public boolean isDataFlavorSupported(DataFlavor flavor) {
				return Objects.equals(TableRowTransferHandler.this.localObjectFlavor, flavor);
			}

			@Override
			public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
				if (this.isDataFlavorSupported(flavor)) {
					return transferedObjects;
				}
				throw new UnsupportedFlavorException(flavor);
			}
		};
	}

	@Override
	public boolean canImport(TransferHandler.TransferSupport info) {
		boolean isDroppable = info.isDrop() && info.isDataFlavorSupported(this.localObjectFlavor);
		Component glassPane = ((JComponent) info.getComponent()).getRootPane().getGlassPane();
		glassPane.setCursor(isDroppable ? DragSource.DefaultMoveDrop : DragSource.DefaultMoveNoDrop);
		return isDroppable;
	}

	@Override
	public int getSourceActions(JComponent c) {
		return 2;
	}

	@Override
	public boolean importData(TransferHandler.TransferSupport info) {
		if (!this.canImport(info)) {
			return false;
		}
		TransferHandler.DropLocation tdl = info.getDropLocation();
		if (!(tdl instanceof JTable.DropLocation)) {
			return false;
		}
		JTable.DropLocation dl = (JTable.DropLocation) tdl;
		JTable target = (JTable) info.getComponent();
		DefaultTableModel model = (DefaultTableModel) target.getModel();
		int max = model.getRowCount();
		int index = dl.getRow();
		index = index < 0 ? max : index;
		this.addIndex = index = Math.min(index, max);
		try {
			List values = (List) info.getTransferable().getTransferData(this.localObjectFlavor);
			if (Objects.equals(this.source, target)) {
				this.addCount = values.size();
			}
			for (Object o : values) {
				int i = index++;
				model.insertRow(i, (Vector) o);
				target.getSelectionModel().addSelectionInterval(i, i);
			}
			return true;
		} catch (UnsupportedFlavorException | IOException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public void exportDone(JComponent c, Transferable data, int action) {
		this.cleanup(c, action == 2);
	}

	private void cleanup(JComponent c, boolean remove) {
		c.getRootPane().getGlassPane().setVisible(false);
		if (remove && Objects.nonNull(this.indices)) {
			int i;
			DefaultTableModel model = (DefaultTableModel) ((JTable) c).getModel();
			if (this.addCount > 0) {
				for (i = 0; i < this.indices.length; ++i) {
					if (this.indices[i] < this.addIndex)
						continue;
					int n = i;
					this.indices[n] = this.indices[n] + this.addCount;
				}
			}
			for (i = this.indices.length - 1; i >= 0; --i) {
				model.removeRow(this.indices[i]);
			}
		}
		this.indices = null;
		this.addCount = 0;
		this.addIndex = -1;
	}
}