package net.ranktw.discord;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.HyperlinkEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.jdesktop.layout.GroupLayout;
import org.jdesktop.swingx.border.DropShadowBorder;

import components.DarkScrollPane;
import components.DiscordButton;
import components.HintTextField;
import components.TranslucentJScrollBar;

public class UI extends JFrame {

	private CardLayout cardLayout;
	private boolean b = true;
	public DiscordButton buttonAdvanced;
	public DiscordButton buttonCheck;
	public DiscordButton buttonCustomOutput;
	public DiscordButton buttonInputToken;
	public DiscordButton buttonOutputInvalid;
	public DiscordButton buttonOutputUnverified;
	public DiscordButton buttonOutputWorked;
	public DiscordButton buttonSwap;
	public JCheckBox checkBoxCustomOutput;
	public JCheckBox checkBoxInvalid;
	public JCheckBox checkBoxUnverified;
	public JCheckBox checkBoxWorked;
	public DarkScrollPane darkScrollPane;
	public HintTextField hintTextFieldInvalid;
	public HintTextField hintTextFieldUnverified;
	public HintTextField hintTextFieldWorked;
	private JLabel label;
	private JLabel label1;
	public JLabel labelStatus;
	private JPanel panelContent;
	private JPanel panelLogs;
	private JPanel panelOutputs;
	private JScrollPane scrollPaneTable;
	public JScrollPane scrollPaneText;
	private JSeparator separator1;
	private JSeparator separator2;
	private JSeparator separator3;
	private JSeparator separator4;
	public JTable tableLogs;
	public JTextArea textAreaLogs;

	public UI() {
		this.initComponents();
		this.setLocationRelativeTo(null);
		this.labelStatus.setFont(this.loadFont("/fonts/OpenSans-Bold.ttf", 12.0f, 0));
		this.cardLayout = (CardLayout) this.panelLogs.getLayout();
		this.cardLayout.show(this.panelLogs, "text");
		this.setPreferredSize(new Dimension(300, 405));
		this.setMinimumSize(new Dimension(351, 290));
		this.setSize(new Dimension(355, 490));
		MouseAdapter mouseAdapter = new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent evt) {
				evt.getComponent().requestFocusInWindow();
			}
		};
		this.panelContent.addMouseListener(mouseAdapter);
		this.panelLogs.addMouseListener(mouseAdapter);
		this.panelOutputs.addMouseListener(mouseAdapter);
	}

	private void initComponents() {
		this.label = new JLabel();
		this.panelContent = new JPanel();
		this.buttonInputToken = new DiscordButton();
		this.labelStatus = new JLabel();
		this.panelOutputs = new JPanel();
		this.label1 = new JLabel();
		this.separator1 = new JSeparator();
		this.separator2 = new JSeparator();
		this.separator3 = new JSeparator();
		this.separator4 = new JSeparator();
		this.checkBoxWorked = new JCheckBox();
		this.checkBoxUnverified = new JCheckBox();
		this.checkBoxInvalid = new JCheckBox();
		this.checkBoxCustomOutput = new JCheckBox();
		this.hintTextFieldWorked = new HintTextField();
		this.hintTextFieldUnverified = new HintTextField();
		this.hintTextFieldInvalid = new HintTextField();
		this.buttonOutputWorked = new DiscordButton();
		this.buttonOutputUnverified = new DiscordButton();
		this.buttonOutputInvalid = new DiscordButton();
		this.buttonCustomOutput = new DiscordButton();
		this.buttonAdvanced = new DiscordButton();
		this.panelLogs = new JPanel();
		this.scrollPaneText = new JScrollPane();
		this.textAreaLogs = new JTextArea();
		this.darkScrollPane = new DarkScrollPane();
		this.tableLogs = new JTable() {
			private final Color evenColor = new Color(30, 30, 30);

			@Override
			public Component prepareRenderer(TableCellRenderer tcr, int row, int column) {
				Component c = super.prepareRenderer(tcr, row, column);
				if (this.isRowSelected(row)) {
					c.setForeground(this.getSelectionForeground());
					c.setBackground(this.getSelectionBackground());
				} else {
					c.setForeground(this.getForeground());
					c.setBackground(row % 2 == 0 ? this.evenColor : this.getBackground());
				}
				return c;
			}
		};
		this.buttonCheck = new DiscordButton();
		this.buttonSwap = new DiscordButton();
		this.label.setForeground(new Color(187, 185, 185));
		this.label.setText("l");
		this.setDefaultCloseOperation(3);
		this.setTitle("Discord Token Checker");
		this.panelContent.setBackground(new Color(35, 39, 42));
		this.buttonInputToken.setBackground(new Color(255, 255, 255));
		this.buttonInputToken.setForeground(new Color(51, 51, 51));
		this.buttonInputToken.setText("Input Tokens");
		this.buttonInputToken.setMargin(new Insets(2, 6, 2, 6));
		this.labelStatus.setFont(this.label.getFont());
		this.labelStatus.setForeground(new Color(255, 255, 255));
		this.labelStatus.setHorizontalAlignment(0);
		this.labelStatus.setText("Total Input - 0");
		this.panelOutputs.setBackground(this.panelContent.getBackground());
		DropShadowBorder dropShadowBorder1 = new DropShadowBorder();
		dropShadowBorder1.setShadowColor(new Color(15, 15, 15));
		dropShadowBorder1.setShowLeftShadow(true);
		dropShadowBorder1.setShowTopShadow(true);
		this.panelOutputs.setBorder(BorderFactory.createCompoundBorder(dropShadowBorder1, null));
		this.panelOutputs.setForeground(this.label.getForeground());
		this.panelOutputs.setOpaque(false);
		this.label1.setFont(this.label.getFont());
		this.label1.setForeground(new Color(187, 185, 185));
		this.label1.setText("Outputs");
		this.separator1.setForeground(new Color(153, 153, 153));
		this.separator2.setForeground(new Color(153, 153, 153));
		this.separator3.setForeground(new Color(153, 153, 153));
		this.separator4.setBackground(new Color(102, 102, 102));
		this.separator4.setForeground(new Color(102, 102, 102));
		this.separator4.setOrientation(1);
		this.checkBoxWorked.setFont(this.label.getFont());
		this.checkBoxWorked.setForeground(this.label.getForeground());
		this.checkBoxWorked.setSelected(true);
		this.checkBoxWorked.setText("Worked");
		this.checkBoxWorked.setFocusable(false);
		this.checkBoxWorked.setIcon(new ImageIcon(this.getClass().getResource("/images/unok.png")));
		this.checkBoxWorked.setIconTextGap(7);
		this.checkBoxWorked.setMargin(new Insets(0, 0, 0, 0));
		this.checkBoxWorked.setOpaque(false);
		this.checkBoxWorked.setSelectedIcon(new ImageIcon(this.getClass().getResource("/images/okI.png")));
		this.checkBoxWorked.setVerifyInputWhenFocusTarget(false);
		this.checkBoxWorked.setVerticalAlignment(1);
		this.checkBoxUnverified.setFont(this.label.getFont());
		this.checkBoxUnverified.setForeground(this.label.getForeground());
		this.checkBoxUnverified.setText("Unverified");
		this.checkBoxUnverified.setFocusable(false);
		this.checkBoxUnverified.setIcon(new ImageIcon(this.getClass().getResource("/images/unok.png")));
		this.checkBoxUnverified.setIconTextGap(7);
		this.checkBoxUnverified.setMargin(new Insets(0, 0, 0, 0));
		this.checkBoxUnverified.setOpaque(false);
		this.checkBoxUnverified.setSelectedIcon(new ImageIcon(this.getClass().getResource("/images/okI.png")));
		this.checkBoxUnverified.setVerifyInputWhenFocusTarget(false);
		this.checkBoxUnverified.setVerticalAlignment(1);
		this.checkBoxInvalid.setFont(this.label.getFont());
		this.checkBoxInvalid.setForeground(this.label.getForeground());
		this.checkBoxInvalid.setText("Invalid");
		this.checkBoxInvalid.setFocusable(false);
		this.checkBoxInvalid.setIcon(new ImageIcon(this.getClass().getResource("/images/unok.png")));
		this.checkBoxInvalid.setIconTextGap(7);
		this.checkBoxInvalid.setMargin(new Insets(0, 0, 0, 0));
		this.checkBoxInvalid.setOpaque(false);
		this.checkBoxInvalid.setSelectedIcon(new ImageIcon(this.getClass().getResource("/images/okI.png")));
		this.checkBoxInvalid.setVerifyInputWhenFocusTarget(false);
		this.checkBoxInvalid.setVerticalAlignment(1);
		this.checkBoxCustomOutput.setFont(this.label.getFont());
		this.checkBoxCustomOutput.setForeground(this.label.getForeground());
		this.checkBoxCustomOutput.setFocusable(false);
		this.checkBoxCustomOutput.setIcon(new ImageIcon(this.getClass().getResource("/images/unok.png")));
		this.checkBoxCustomOutput.setIconTextGap(7);
		this.checkBoxCustomOutput.setMargin(new Insets(0, 0, 0, 0));
		this.checkBoxCustomOutput.setOpaque(false);
		this.checkBoxCustomOutput.setSelectedIcon(new ImageIcon(this.getClass().getResource("/images/okI.png")));
		this.checkBoxCustomOutput.setVerifyInputWhenFocusTarget(false);
		this.checkBoxCustomOutput.setVerticalAlignment(1);
		this.hintTextFieldWorked.setBackground(this.panelContent.getBackground());
		this.hintTextFieldWorked.setText("Test");
		this.hintTextFieldWorked.setMargin(new Insets(6, 6, 6, 6));
		this.hintTextFieldUnverified.setBackground(this.panelContent.getBackground());
		this.hintTextFieldUnverified.setText("Test");
		this.hintTextFieldUnverified.setMargin(new Insets(6, 6, 6, 6));
		this.hintTextFieldInvalid.setBackground(this.panelContent.getBackground());
		this.hintTextFieldInvalid.setText("Test");
		this.hintTextFieldInvalid.setMargin(new Insets(6, 6, 6, 6));
		this.buttonOutputWorked.setBackground(new Color(255, 255, 255));
		this.buttonOutputWorked.setForeground(new Color(51, 51, 51));
		this.buttonOutputWorked.setText("...");
		this.buttonOutputWorked.setFont(this.label.getFont());
		this.buttonOutputWorked.setMargin(new Insets(2, 6, 2, 6));
		this.buttonOutputUnverified.setBackground(new Color(255, 255, 255));
		this.buttonOutputUnverified.setForeground(new Color(51, 51, 51));
		this.buttonOutputUnverified.setText("...");
		this.buttonOutputUnverified.setFont(this.label.getFont());
		this.buttonOutputUnverified.setMargin(new Insets(2, 6, 2, 6));
		this.buttonOutputInvalid.setBackground(new Color(255, 255, 255));
		this.buttonOutputInvalid.setForeground(new Color(51, 51, 51));
		this.buttonOutputInvalid.setText("...");
		this.buttonOutputInvalid.setFont(this.label.getFont());
		this.buttonOutputInvalid.setMargin(new Insets(2, 6, 2, 6));
		this.buttonCustomOutput.setBackground(new Color(255, 255, 255));
		this.buttonCustomOutput.setForeground(new Color(51, 51, 51));
		this.buttonCustomOutput.setText("Custom Output");
		this.buttonCustomOutput.setFont(this.label.getFont());
		this.buttonCustomOutput.setMargin(new Insets(2, 6, 2, 6));
		this.buttonAdvanced.setBackground(new Color(255, 255, 255));
		this.buttonAdvanced.setForeground(new Color(51, 51, 51));
		this.buttonAdvanced.setText("Advanced Settings");
		this.buttonAdvanced.setFont(this.label.getFont());
		this.buttonAdvanced.setMargin(new Insets(0, 0, 0, 0));
		GroupLayout panelOutputsLayout = new GroupLayout(this.panelOutputs);
		this.panelOutputs.setLayout(panelOutputsLayout);
		panelOutputsLayout.setHorizontalGroup(panelOutputsLayout.createParallelGroup(1)
				.add(panelOutputsLayout.createSequentialGroup().add(3, 3, 3).add(this.separator2, -2, 5, -2)
						.add(3, 3, 3).add(this.label1).add(2, 2, 2).add(this.separator1).add(3, 3, 3))
				.add(panelOutputsLayout.createSequentialGroup().add(3, 3, 3).add(this.separator3).add(3, 3, 3))
				.add(panelOutputsLayout.createSequentialGroup().add(5, 5, 5).add(this.checkBoxCustomOutput, -2, 30, -2)
						.add(0, 0, 0).add(this.buttonCustomOutput, -2, 120, -2).add(9, 9, 9).add(this.separator4)
						.add(6, 6, 6).add(this.buttonAdvanced, -2, 110, -2).add(5, 5, 5))
				.add(panelOutputsLayout.createSequentialGroup().add(5, 5, 5)
						.add(panelOutputsLayout.createParallelGroup(1)
								.add(panelOutputsLayout.createSequentialGroup()
										.add(panelOutputsLayout.createParallelGroup(1)
												.add(panelOutputsLayout.createSequentialGroup().add(85, 85, 85)
														.add(this.hintTextFieldInvalid, -1, -1, Short.MAX_VALUE))
												.add(this.checkBoxInvalid, -2, 90, -2))
										.add(this.buttonOutputInvalid, -2, 20, -2))
								.add(panelOutputsLayout.createSequentialGroup()
										.add(panelOutputsLayout.createParallelGroup(1)
												.add(panelOutputsLayout.createSequentialGroup().add(85, 85, 85)
														.add(this.hintTextFieldWorked, -1, -1, Short.MAX_VALUE))
												.add(this.checkBoxWorked, -2, 90, -2))
										.add(this.buttonOutputWorked, -2, 20, -2))
								.add(panelOutputsLayout.createSequentialGroup()
										.add(panelOutputsLayout.createParallelGroup(1)
												.add(panelOutputsLayout.createSequentialGroup().add(85, 85, 85)
														.add(this.hintTextFieldUnverified, -1, -1, Short.MAX_VALUE))
												.add(this.checkBoxUnverified, -2, 90, -2))
										.add(this.buttonOutputUnverified, -2, 20, -2)))
						.add(5, 5, 5)));
		panelOutputsLayout.setVerticalGroup(panelOutputsLayout.createParallelGroup(1)
				.add(panelOutputsLayout.createSequentialGroup().add(1, 1, 1)
						.add(panelOutputsLayout.createParallelGroup(1).add(this.label1)
								.add(panelOutputsLayout.createSequentialGroup().add(6, 6, 6)
										.add(panelOutputsLayout.createParallelGroup(1).add(this.separator2, -2, 10, -2)
												.add(this.separator1, -2, 10, -2))))
						.add(4, 4, 4)
						.add(panelOutputsLayout.createParallelGroup(1).add(this.hintTextFieldWorked, -2, 30, -2)
								.add(panelOutputsLayout.createSequentialGroup().add(4, 4, 4)
										.add(panelOutputsLayout.createParallelGroup(1)
												.add(this.checkBoxWorked, -2, 24, -2)
												.add(this.buttonOutputWorked, -2, 21, -2))))
						.add(panelOutputsLayout.createParallelGroup(1).add(this.hintTextFieldUnverified, -2, 30, -2)
								.add(panelOutputsLayout.createSequentialGroup().add(4, 4, 4)
										.add(panelOutputsLayout.createParallelGroup(1)
												.add(this.checkBoxUnverified, -2, 24, -2)
												.add(this.buttonOutputUnverified, -2, 21, -2))))
						.add(panelOutputsLayout.createParallelGroup(1).add(this.hintTextFieldInvalid, -2, 30, -2)
								.add(panelOutputsLayout.createSequentialGroup().add(4, 4, 4)
										.add(panelOutputsLayout.createParallelGroup(1)
												.add(this.checkBoxInvalid, -2, 24, -2)
												.add(this.buttonOutputInvalid, -2, 21, -2))))
						.add(4, 4, 4).add(this.separator3, -2, 3, -2).add(7, 7, 7)
						.add(panelOutputsLayout.createParallelGroup(1).add(this.checkBoxCustomOutput, -2, 24, -2)
								.add(this.buttonCustomOutput, -2, 23, -2).add(panelOutputsLayout.createSequentialGroup()
										.add(3, 3, 3).add(this.separator4, -2, 17, -2))
								.add(this.buttonAdvanced, -2, 23, -2))
						.add(5, 5, 5)));
		this.panelLogs.setOpaque(false);
		this.panelLogs.setLayout(new CardLayout());
		this.scrollPaneText.setBorder(null);
		this.textAreaLogs.setEditable(false);
		this.textAreaLogs.setBackground(new Color(30, 30, 30));
		this.textAreaLogs.setColumns(20);
		this.textAreaLogs.setFont(new Font("Ebrima", 1, 12));
		this.textAreaLogs.setForeground(new Color(255, 255, 255));
		this.textAreaLogs.setRows(12);
		this.textAreaLogs.setTabSize(3);
		this.textAreaLogs.setBorder(null);
		this.textAreaLogs.setCaretColor(new Color(187, 186, 187));
		this.textAreaLogs.setDisabledTextColor(new Color(153, 154, 153));
		this.textAreaLogs.setSelectedTextColor(new Color(187, 186, 187));
		this.textAreaLogs.setSelectionColor(new Color(75, 111, 175));
		this.scrollPaneText.setViewportView(this.textAreaLogs);
		this.panelLogs.add((Component) this.scrollPaneText, "text");
		this.panelLogs.add(TranslucentJScrollBar.make(this.textAreaLogs), "text");
		this.tableLogs.setBackground(new Color(30, 30, 30));
		this.tableLogs.setForeground(new Color(187, 186, 187));
		this.tableLogs.setModel(new DefaultTableModel(new Object[0][],
				new String[] { "Discrim", "Username", "Created Date", "Verified", "Info", "Token" }) {
			boolean[] canEdit;
			{
				this.canEdit = new boolean[] { false, false, false, false, false, false };
			}

			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return this.canEdit[columnIndex];
			}
		});
		this.tableLogs.setGridColor(new Color(39, 39, 39));
		String PLACEHOLDER = "<!DOCTYPE html>\n<html>\n<head>\n<style>\nbody {\n  background-color: linen;\n}\n\nspan {\n  color: #FFFFFF;\n}\n\na {\n  color: #FF484D;\n  font-weight:bold;\n  margin-left: 6px;\n} \n</style>\n</head>\n<body>\n<span>No data!</span>\n<a href='dummy'>Input</a>\n</body>\n</html>";
		JEditorPane editor = new JEditorPane("text/html", PLACEHOLDER);
		editor.setOpaque(false);
		editor.setEditable(false);
		editor.putClientProperty("JEditorPane.honorDisplayProperties", Boolean.TRUE);
		editor.addHyperlinkListener(e -> {
			if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
				Toolkit.getDefaultToolkit().beep();
			}
		});
		this.tableLogs.getModel().addTableModelListener(e -> {
			DefaultTableModel m = (DefaultTableModel) e.getSource();
			editor.setVisible(m.getRowCount() == 0);
		});
		this.tableLogs.setRowMargin(0);
		this.tableLogs.setFillsViewportHeight(true);
		this.tableLogs.setLayout(new GridBagLayout());
		this.tableLogs.add(editor);
		this.darkScrollPane.setViewportView(this.tableLogs);
		if (this.tableLogs.getColumnModel().getColumnCount() > 0) {
			this.tableLogs.getColumnModel().getColumn(0).setMinWidth(45);
			this.tableLogs.getColumnModel().getColumn(0).setPreferredWidth(45);
			this.tableLogs.getColumnModel().getColumn(0).setMaxWidth(45);
			this.tableLogs.getColumnModel().getColumn(2).setMaxWidth(121);
		}
		this.panelLogs.add((Component) this.darkScrollPane, "table");
		this.buttonCheck.setText("Check");
		this.buttonCheck.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				UI.this.buttonCheckActionPerformed(evt);
			}
		});
		this.buttonSwap.setBackground(new Color(255, 255, 255));
		this.buttonSwap.setForeground(new Color(51, 51, 51));
		this.buttonSwap.setText("Text");
		this.buttonSwap.setFont(this.label.getFont());
		this.buttonSwap.setMargin(new Insets(0, 0, 0, 0));
		this.buttonSwap.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent evt) {
				UI.this.buttonSwapActionPerformed(evt);
			}
		});
		this.scrollPaneText = (JScrollPane) TranslucentJScrollBar.make(this.textAreaLogs);
		this.panelLogs.add((Component) this.scrollPaneText, "text");
		GroupLayout panelContentLayout = new GroupLayout(this.panelContent);
		this.panelContent.setLayout(panelContentLayout);
		panelContentLayout.setHorizontalGroup(panelContentLayout.createParallelGroup(1)
				.add(panelContentLayout.createSequentialGroup().add(3, 3, 3)
						.add(this.labelStatus, -1, -1, Short.MAX_VALUE).addPreferredGap(0)
						.add(this.buttonInputToken, -2, 113, -2).add(3, 3, 3))
				.add(this.panelOutputs, -1, -1, Short.MAX_VALUE).add(this.panelLogs, -1, -1, Short.MAX_VALUE)
				.add(panelContentLayout.createSequentialGroup().add(this.buttonCheck, -1, -1, Short.MAX_VALUE)
						.addPreferredGap(0).add(this.buttonSwap, -2, 95, -2)));
		panelContentLayout.setVerticalGroup(panelContentLayout.createParallelGroup(1)
				.add(panelContentLayout.createSequentialGroup().add(panelContentLayout.createParallelGroup(1)
						.add(panelContentLayout.createSequentialGroup().add(3, 3, 3).add(this.labelStatus, -2, 29, -2))
						.add(panelContentLayout.createSequentialGroup().add(6, 6, 6)
								.add(this.buttonInputToken, -2, 23, -2))
						.add(panelContentLayout.createSequentialGroup().add(31, 31, 31)
								.add(this.panelOutputs, -2, -1, -2)))
						.add(0, 0, 0).add(this.panelLogs, -1, 172, Short.MAX_VALUE).addPreferredGap(0)
						.add(panelContentLayout.createParallelGroup(1, false)
								.add(this.buttonCheck, -1, -1, Short.MAX_VALUE)
								.add(this.buttonSwap, -1, -1, Short.MAX_VALUE))));
		GroupLayout layout = new GroupLayout(this.getContentPane());
		this.getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(1).add(this.panelContent, -1, -1, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(1).add(this.panelContent, -1, -1, Short.MAX_VALUE));
		this.pack();
	}

	private void buttonSwapActionPerformed(ActionEvent evt) {
		this.buttonSwap.setText(this.b ? "Table" : "Text");
		this.cardLayout.show(this.panelLogs, this.b ? "table" : "text");
		this.b = !this.b;
		System.out.println(String.format("%s, %s", this.getSize().width, this.getSize().height));
	}

	private void buttonCheckActionPerformed(ActionEvent evt) {
	}

	public void addDate(Object[] data) {
		DefaultTableModel model = (DefaultTableModel) this.tableLogs.getModel();
		model.addRow(data);
	}

	/*
	 * Enabled aggressive block sorting Enabled unnecessary exception pruning
	 * Enabled aggressive exception aggregation
	 */
	private Font loadFont(String fontName, float size, int style) {
		try (InputStream is = JLabel.class.getResourceAsStream(fontName);) {
			Font font2 = Font.createFont(0, is);
			font2 = font2.deriveFont(size);
			Font font = font2 = font2.deriveFont(style);
			return font;
		} catch (FontFormatException ex) {
			System.out.println(fontName + " Font is null FontFormatException");
			return null;
		} catch (Exception e) {
			System.out.println(fontName + " Font is null, " + e.toString());
		}
		return null;
	}
}