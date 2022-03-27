package net.ranktw.discord;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;
import javax.swing.table.DefaultTableModel;

class FileTransferHandler extends TransferHandler {

	FileTransferHandler() {
	}

	@Override
	public boolean importData(TransferHandler.TransferSupport support) {
		try {
			if (this.canImport(support)) {
				DefaultTableModel model = (DefaultTableModel) ((JTable) support.getComponent()).getModel();
				for (Object o : (List) support.getTransferable().getTransferData(DataFlavor.javaFileListFlavor)) {
					File file;
					if (!(o instanceof File) || (file = (File) o).isDirectory())
						continue;
					model.addRow(Collections.nCopies(3, file).toArray());
				}
				return true;
			}
		} catch (UnsupportedFlavorException | IOException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean canImport(TransferHandler.TransferSupport support) {
		return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
	}

	@Override
	public int getSourceActions(JComponent component) {
		return 1;
	}
}