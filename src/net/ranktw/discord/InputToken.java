package net.ranktw.discord;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.DropMode;
import javax.swing.GroupLayout;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.LayoutStyle;
import javax.swing.event.HyperlinkEvent;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableModel;

import com.alee.laf.label.WebLabel;

import components.DarkComboBoxs;
import components.DarkScrollPane;
import components.DarkTable;
import components.DarkTextArea;
import components.DiscordButton;
import components.Notification;
import net.ranktw.discord.old.Main;
import net.ranktw.discord.util.FileUtil;
import net.ranktw.utilities.Util;

public class InputToken extends JFrame {

	private CardLayout cardLayout;
	private Control control;
	private DiscordButton buttonInput;
	private DiscordButton buttonInputFromClipboard;
	private JPanel cardPanel;
	private DarkComboBoxs comboBoxFrom;
	private WebLabel label;
	private JPanel panel;
	private DarkScrollPane scrollPane;
	private JPanel separatorPanel1;
	private JPanel separatorPanel2;
	private DarkTable tableFiles;
	private DarkTextArea textArea;

	public InputToken(Control control) {
		this.control = control;
		this.initComponents();
		this.setLocationRelativeTo(null);
		this.cardLayout = (CardLayout) this.cardPanel.getLayout();
		this.comboBoxFrom.addItemListener(e -> {
			if (e.getItem().toString().endsWith("Text")) {
				this.cardLayout.show(this.cardPanel, "Text");
			} else {
				this.cardLayout.show(this.cardPanel, "File");
			}
		});
		new Thread(() -> {
			try {
				Thread.sleep(500L);
			} catch (InterruptedException interruptedException) {
				// empty catch block
			}
			this.repaint();
		}).start();
		URL url = this.getClass().getResource("/images/icon.png");
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(url));
	}

	private void initComponents() {
		this.label = new WebLabel();
		this.panel = new JPanel();
		this.separatorPanel1 = new JPanel();
		this.separatorPanel2 = new JPanel();
		this.buttonInputFromClipboard = new DiscordButton();
		this.buttonInput = new DiscordButton();
		this.cardPanel = new JPanel();
		this.scrollPane = new DarkScrollPane();
		this.tableFiles = new DarkTable();
		this.textArea = new DarkTextArea(this, false);
		this.comboBoxFrom = new DarkComboBoxs((JFrame) this, Arrays.asList("Input From Files", "Input From Text"));
		this.label.setBackground(new Color(43, 46, 50));
		this.label.setForeground(new Color(255, 255, 255));
		this.label.setText("Label");
		this.label.setFont(dev.Main.loadFont("/fonts/OpenSans-Bold.ttf", 11.0f, 0));
		this.setTitle("Input");
		this.panel.setBackground(new Color(54, 57, 63));
		this.getBg(this.separatorPanel1);
		this.getBg(this.separatorPanel2);
		this.buttonInputFromClipboard.setBackground(new Color(255, 255, 255));
		this.buttonInputFromClipboard.setForeground(new Color(51, 51, 51));
		this.buttonInputFromClipboard.setText("Input From Clipboard");
		this.buttonInputFromClipboard.setFont(this.label.getFont());
		this.buttonInputFromClipboard.setPreferredSize(new Dimension(120, 30));
		this.buttonInputFromClipboard.addActionListener(this::buttonInputFromClipboardActionPerformed);
		this.buttonInput.setBackground(new Color(255, 255, 255));
		this.buttonInput.setForeground(new Color(51, 51, 51));
		this.buttonInput.setText("Input");
		this.buttonInput.setFont(this.label.getFont());
		this.buttonInput.setPreferredSize(new Dimension(240, 30));
		this.buttonInput.addActionListener(this::buttonInputActionPerformed);
		this.cardPanel.setPreferredSize(new Dimension(240, 70));
		this.cardPanel.setLayout(new CardLayout());
		this.scrollPane.setFont(this.label.getFont());
		this.scrollPane.setPreferredSize(new Dimension(240, 120));
		Object[] columnNames = new String[] { "Name", "Size", "Full Path" };
		DefaultTableModel model = new DefaultTableModel(null, columnNames) {
			boolean[] canEdit;
			{
				this.canEdit = new boolean[] { false, false, false };
			}

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return this.canEdit[columnIndex];
			}

			@Override
			public Class<?> getColumnClass(int column) {
				return File.class;
			}
		};
		this.tableFiles.setModel(model);
		this.tableFiles.putClientProperty("Table.isFileList", Boolean.TRUE);
		this.tableFiles.setCellSelectionEnabled(true);
		this.tableFiles.setIntercellSpacing(new Dimension());
		this.tableFiles.setComponentPopupMenu(new TablePopupMenu());
		this.tableFiles.setShowGrid(false);
		this.tableFiles.setFillsViewportHeight(true);
		this.tableFiles.setDropMode(DropMode.INSERT_ROWS);
		this.tableFiles.setTransferHandler(new FileTransferHandler());
		this.tableFiles.setDefaultRenderer(Object.class,
				new FileIconTableCellRenderer(FileSystemView.getFileSystemView()));
		if (this.tableFiles.getColumnModel().getColumnCount() > 0) {
			this.tableFiles.getColumnModel().getColumn(1).setPreferredWidth(30);
			this.tableFiles.getColumnModel().getColumn(2).setResizable(false);
		}
		String PLACEHOLDER = "<!DOCTYPE html>\n<html>\n<head>\n<style>\nbody {\n  background-color: linen;\n}\n\nspan {\n  color: #FFFFFF;\n}\n\na {\n  color: #DADADA;\n  font-weight:bold;\n  margin-left: 1px;\n} \n</style>\n</head>\n<body>\n<span>Drop Files to here</span>\n<a href='dummy'>...</a>\n</body>\n</html>";
		JEditorPane editor = new JEditorPane("text/html", PLACEHOLDER);
		editor.setOpaque(false);
		editor.setEditable(false);
		editor.putClientProperty("JEditorPane.honorDisplayProperties", Boolean.TRUE);
		editor.addHyperlinkListener(e -> {
			if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
				Toolkit.getDefaultToolkit().beep();
				String path = FileUtil.chooseFile(Main.currFolder, this, new FileUtil.TxtFileFilter());
				if (path == null) {
					return;
				}
				File file = new File(path);
				model.addRow(Collections.nCopies(3, file).toArray());
			}
		});
		this.tableFiles.getModel().addTableModelListener(e -> {
			DefaultTableModel m = (DefaultTableModel) e.getSource();
			editor.setVisible(m.getRowCount() == 0);
		});
		this.tableFiles.setFillsViewportHeight(true);
		this.tableFiles.setLayout(new GridBagLayout());
		this.tableFiles.add(editor);
		this.scrollPane.setViewportView(this.tableFiles);
		if (this.tableFiles.getColumnModel().getColumnCount() > 0) {
			this.tableFiles.getColumnModel().getColumn(2).setResizable(false);
		}
		this.cardPanel.add((Component) this.scrollPane, "File");
		this.textArea.setFrame(this);
		this.textArea.setStyleId("");
		this.textArea.setText("");
		this.cardPanel.add((Component) this.textArea, "Text");
		this.comboBoxFrom.setPreferredSize(new Dimension(240, 30));
		GroupLayout panelLayout = new GroupLayout(this.panel);
		this.panel.setLayout(panelLayout);
		panelLayout.setHorizontalGroup(panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING,
						panelLayout.createSequentialGroup().addGap(23, 23, 23)
								.addComponent(this.separatorPanel2, -1, 209, Short.MAX_VALUE).addGap(23, 23, 23))
				.addGroup(GroupLayout.Alignment.TRAILING,
						panelLayout.createSequentialGroup().addContainerGap()
								.addComponent(this.buttonInput, -2, 0, Short.MAX_VALUE).addContainerGap())
				.addGroup(panelLayout.createSequentialGroup().addContainerGap().addGroup(panelLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(panelLayout.createSequentialGroup().addGap(11, 11, 11)
								.addComponent(this.separatorPanel1, -1, 209, Short.MAX_VALUE).addGap(23, 23, 23))
						.addGroup(panelLayout.createSequentialGroup()
								.addComponent(this.buttonInputFromClipboard, -1, 228, Short.MAX_VALUE)
								.addGap(15, 15, 15))))
				.addGroup(panelLayout.createSequentialGroup().addContainerGap()
						.addComponent(this.cardPanel, -2, 0, Short.MAX_VALUE).addContainerGap())
				.addGroup(panelLayout.createSequentialGroup().addContainerGap()
						.addComponent(this.comboBoxFrom, -2, 0, Short.MAX_VALUE).addContainerGap()));
		panelLayout.setVerticalGroup(
				panelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING,
						panelLayout.createSequentialGroup().addContainerGap()
								.addComponent(this.buttonInputFromClipboard, -2, -1, -2).addGap(12, 12, 12)
								.addComponent(this.separatorPanel1, -2, -1, -2).addGap(9, 9, 9)
								.addComponent(this.comboBoxFrom, -2, -1, -2)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(this.cardPanel, -1, 123, Short.MAX_VALUE).addGap(12, 12, 12)
								.addComponent(this.separatorPanel2, -2, -1, -2).addGap(12, 12, 12)
								.addComponent(this.buttonInput, -2, -1, -2).addGap(12, 12, 12)));
		GroupLayout layout = new GroupLayout(this.getContentPane());
		this.getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.panel, -1,
				-1, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.panel, -1,
				-1, Short.MAX_VALUE));
		this.pack();
	}

	private void getBg(JPanel separatorPanel) {
		separatorPanel.setBackground(new Color(114, 116, 120));
		separatorPanel.setPreferredSize(new Dimension(199, 1));
		GroupLayout separatorPanel2Layout = new GroupLayout(separatorPanel);
		separatorPanel.setLayout(separatorPanel2Layout);
		separatorPanel2Layout.setHorizontalGroup(
				separatorPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 0, Short.MAX_VALUE));
		separatorPanel2Layout.setVerticalGroup(
				separatorPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 1, Short.MAX_VALUE));
	}

	private void buttonInputFromClipboardActionPerformed(ActionEvent evt) {
		try {
			this.control.inputTokens(Util.getClipBoardList());
			this.setVisible(false);
		} catch (Exception e) {
			this.showError(e);
		}
	}

	private void buttonInputActionPerformed(ActionEvent evt) {
		ArrayList<String> tokens = new ArrayList<String>();
		try {
			if (this.comboBoxFrom.getSelectedIndex() == 0) {
				DefaultTableModel model = (DefaultTableModel) this.tableFiles.getModel();
				for (int i = 0; i < model.getRowCount(); ++i) {
					tokens.addAll(Util.loadFile((File) model.getValueAt(i, 0)));
				}
			} else {
				tokens.addAll(Arrays.asList(this.textArea.getText().split("\n")));
			}
			this.control.inputTokens(tokens);
			this.setVisible(false);
		} catch (Exception e) {
			this.showError(e);
		}
	}

	public void showError(Exception e) {
		e.printStackTrace();
		Notification.error(e.toString());
	}
}