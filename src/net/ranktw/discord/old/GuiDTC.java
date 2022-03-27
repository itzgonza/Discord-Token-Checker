package net.ranktw.discord.old;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import net.ranktw.discord.util.FileUtil;

public class GuiDTC extends JFrame {

	private boolean checking = false;
	String Workedpath = "Token Worked.txt";
	String Invalidpath = "Token Invalid.txt";
	String Unverifiedpath = "Token Unverified.txt";
	BufferedWriter WorkedWriter = null;
	BufferedWriter InvalidWriter = null;
	BufferedWriter UnverifiedWriter = null;
	private int w;
	private int i;
	private int u;
	private JButton InputTokensButton;
	private JButton WorkedBrowse;
	private JButton InvalidBrowse;
	private JButton CheckButton;
	private JButton UnverifiedBrowse;
	private JCheckBox WorkedCheckBox;
	private JCheckBox InvalidCheckBox;
	private JCheckBox UnverifiedCheckBox;
	JLabel TotalInput;
	private JPanel panel;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	JTextField WorkedTextField;
	JTextField InvalidTextField;
	JTextField UnverifiedTextField;
	private JButton AdvancedSettingButton;
	private JButton CustomOutputButton;
	private static JCheckBox CustomOutputCheckBox;
	private JSeparator jSeparator1;
	private JButton ClearLogButton;

	GuiDTC() {
		this.initComponents();
		this.CustomOutputButton.setEnabled(false);
		this.textArea.setFont(new Font("Arial", 1, 11));
	}

	private void initComponents() {
		this.TotalInput = new JLabel();
		this.InputTokensButton = new JButton();
		this.panel = new JPanel();
		this.panel.setBackground(new Color(0x5E5E5B));
		this.WorkedCheckBox = new JCheckBox();
		this.WorkedTextField = new JTextField();
		this.WorkedBrowse = new JButton();
		this.InvalidCheckBox = new JCheckBox();
		this.InvalidBrowse = new JButton();
		this.InvalidTextField = new JTextField();
		this.scrollPane = new JScrollPane();
		this.textArea = new JTextArea();
		this.CheckButton = new JButton();
		this.CustomOutputButton = new JButton();
		this.AdvancedSettingButton = new JButton();
		CustomOutputCheckBox = new JCheckBox();
		this.jSeparator1 = new JSeparator();
		this.ClearLogButton = new JButton();
		this.UnverifiedCheckBox = new JCheckBox();
		this.UnverifiedTextField = new JTextField();
		this.UnverifiedBrowse = new JButton();
		this.UnverifiedCheckBox.setFont(new Font("Arial Black", 0, 10));
		this.UnverifiedCheckBox.setForeground(new Color(255, 255, 255));
		this.UnverifiedCheckBox.setText("Unverified");
		this.UnverifiedCheckBox.setMargin(new Insets(2, 0, 2, 0));
		this.UnverifiedCheckBox.setBackground(new Color(0x5E5E5B));
		this.UnverifiedCheckBox.addActionListener(this::UnverifiedCheckBoxActionPerformed);
		this.UnverifiedBrowse.setFont(this.UnverifiedBrowse.getFont());
		this.UnverifiedBrowse.setText("Browse");
		this.UnverifiedBrowse.addActionListener(this::UnverifiedFileButtonActionPerformed);
		this.scrollPane.getVerticalScrollBar().addAdjustmentListener(e -> {
			if (this.checking) {
				e.getAdjustable().setValue(e.getAdjustable().getMaximum());
			}
		});
		this.setDefaultCloseOperation(3);
		this.setBackground(new Color(51, 51, 51));
		this.ClearLogButton.setText("Clear Log");
		this.ClearLogButton.addActionListener(this::ClearLogButtonActionPerformed);
		this.TotalInput.setBackground(new Color(51, 51, 51));
		this.TotalInput.setFont(new Font("Arial Black", 1, 12));
		this.TotalInput.setForeground(new Color(255, 255, 255));
		this.TotalInput.setHorizontalAlignment(0);
		this.TotalInput.setText("Total Input - 0");
		this.TotalInput.setToolTipText("");
		this.InputTokensButton.setFont(this.InputTokensButton.getFont());
		this.InputTokensButton.setText("Input Tokens");
		this.InputTokensButton.addActionListener(this::InputTokensActionPerformed);
		this.panel.setBorder(BorderFactory.createTitledBorder(null, "Output", 0, 0, new Font("Arial Black", 0, 12),
				new Color(255, 255, 255)));
		this.WorkedCheckBox.setFont(new Font("Arial Black", 0, 12));
		this.WorkedCheckBox.setForeground(new Color(255, 255, 255));
		this.WorkedCheckBox.setText("Worked");
		this.WorkedCheckBox.setMargin(new Insets(2, 0, 2, 0));
		this.WorkedCheckBox.setBackground(new Color(0x5E5E5B));
		this.WorkedCheckBox.addActionListener(this::WorkedCheckBoxActionPerformed);
		this.WorkedBrowse.setFont(this.WorkedBrowse.getFont());
		this.WorkedBrowse.setText("Browse");
		this.WorkedBrowse.addActionListener(this::WorkedFileButtonActionPerformed);
		this.InvalidCheckBox.setFont(new Font("Arial Black", 0, 12));
		this.InvalidCheckBox.setForeground(new Color(255, 255, 255));
		this.InvalidCheckBox.setText("Invalid");
		this.InvalidCheckBox.setMargin(new Insets(2, 0, 2, 0));
		this.InvalidCheckBox.setBackground(new Color(0x5E5E5B));
		this.InvalidCheckBox.addActionListener(this::InvalidCheckBoxActionPerformed);
		this.InvalidBrowse.setFont(this.InvalidBrowse.getFont());
		this.InvalidBrowse.setText("Browse");
		this.InvalidBrowse.addActionListener(this::InvalidFileButtonActionPerformed);
		this.CustomOutputButton.setFont(this.CustomOutputButton.getFont());
		this.CustomOutputButton.setText("Custom Output");
		this.CustomOutputButton.addActionListener(this::CustomOutputButtonActionPerformed);
		this.AdvancedSettingButton.setFont(this.AdvancedSettingButton.getFont());
		this.AdvancedSettingButton.setText("Advanced Settings");
		this.AdvancedSettingButton.addActionListener(this::AdvancedSettingButtonActionPerformed);
		CustomOutputCheckBox.setFont(new Font("Arial Black", 0, 12));
		CustomOutputCheckBox.setForeground(new Color(255, 255, 255));
		CustomOutputCheckBox.setMargin(new Insets(2, 0, 2, 0));
		CustomOutputCheckBox.setBackground(new Color(0x5E5E5B));
		CustomOutputCheckBox.addActionListener(this::CustomOutputCheckBoxActionPerformed);
		this.jSeparator1.setOrientation(1);
		this.textArea.setEditable(false);
		this.textArea.setColumns(20);
		this.textArea.setRows(5);
		this.textArea.setBackground(new Color(51, 51, 51));
		this.textArea.setForeground(new Color(255, 255, 255));
		this.textArea.setSelectionColor(new Color(6135662));
		this.scrollPane.setViewportView(this.textArea);
		this.CheckButton.setText("Check");
		this.CheckButton.addActionListener(this::CheckButtonActionPerformed);
		GroupLayout jPanel1Layout = new GroupLayout(this.panel);
		this.panel.setLayout(jPanel1Layout);
		jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup().addGroup(jPanel1Layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(jPanel1Layout.createSequentialGroup().addComponent(this.InvalidCheckBox, -2, 81, -2)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(this.InvalidTextField))
						.addGroup(GroupLayout.Alignment.TRAILING,
								jPanel1Layout.createSequentialGroup().addComponent(this.WorkedCheckBox, -2, 81, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(this.WorkedTextField))
						.addGroup(GroupLayout.Alignment.TRAILING,
								jPanel1Layout.createSequentialGroup().addComponent(this.UnverifiedCheckBox, -2, 81, -2)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(this.UnverifiedTextField)))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
								.addComponent(this.InvalidBrowse, -1, -1, Short.MAX_VALUE)
								.addComponent(this.UnverifiedBrowse, GroupLayout.Alignment.TRAILING, -1, -1,
										Short.MAX_VALUE)
								.addComponent(this.WorkedBrowse, GroupLayout.Alignment.TRAILING, -1, -1,
										Short.MAX_VALUE)))
				.addGroup(jPanel1Layout.createSequentialGroup().addComponent(CustomOutputCheckBox)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(this.CustomOutputButton, -1, 162, Short.MAX_VALUE).addGap(16, 16, 16)
						.addComponent(this.jSeparator1, -2, 10, -2)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(this.AdvancedSettingButton)));
		jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(jPanel1Layout.createSequentialGroup()
						.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(this.WorkedCheckBox).addComponent(this.WorkedTextField, -2, -1, -2)
								.addComponent(this.WorkedBrowse))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(this.UnverifiedCheckBox)
								.addComponent(this.UnverifiedTextField, -2, -1, -2).addComponent(this.UnverifiedBrowse))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jPanel1Layout
								.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.InvalidCheckBox)
								.addComponent(this.InvalidTextField, -2, -1, -2).addComponent(this.InvalidBrowse))
						.addGap(3, 3, 3)
						.addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(this.CustomOutputButton).addComponent(this.AdvancedSettingButton)
								.addComponent(CustomOutputCheckBox).addComponent(this.jSeparator1, -2, 23, -2))
						.addGap(1, 1, 1)));
		GroupLayout layout = new GroupLayout(this.getContentPane());
		this.getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addComponent(this.TotalInput, -1, -1, Short.MAX_VALUE)
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(this.InputTokensButton, -2, 98, -2))
				.addComponent(this.panel, -1, -1, Short.MAX_VALUE).addComponent(this.scrollPane)
				.addGroup(layout.createSequentialGroup().addComponent(this.CheckButton, -1, -1, Short.MAX_VALUE)
						.addGap(1, 1, 1).addComponent(this.ClearLogButton, -2, 89, -2)));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(this.TotalInput, -2, 24, -2).addComponent(this.InputTokensButton))
						.addGap(1, 1, 1).addComponent(this.panel, -2, -1, -2).addGap(0, 0, 0)
						.addComponent(this.scrollPane, -1, 200, Short.MAX_VALUE).addGap(0, 0, 0)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(this.CheckButton, -2, 23, -2)
								.addComponent(this.ClearLogButton, -2, 23, -2))));
		this.pack();
	}

	private void AdvancedSettingButtonActionPerformed(ActionEvent evt) {
		Main.advancedSettings.setVisible(true);
	}

	private void CustomOutputButtonActionPerformed(ActionEvent evt) {
		Main.customOutput.setVisible(true);
	}

	private void InputTokensActionPerformed(ActionEvent evt) {
		Main.guiInput.setVisible(true);
		Main.guiInput.setAlwaysOnTop(true);
	}

	private void ClearLogButtonActionPerformed(ActionEvent evt) {
		this.textArea.setText("");
	}

	private void WorkedCheckBoxActionPerformed(ActionEvent evt) {
		this.WorkedTextField.setEnabled(this.WorkedCheckBox.isSelected());
		this.WorkedBrowse.setEnabled(this.WorkedCheckBox.isSelected());
	}

	private void InvalidCheckBoxActionPerformed(ActionEvent evt) {
		this.InvalidTextField.setEnabled(this.InvalidCheckBox.isSelected());
		this.InvalidBrowse.setEnabled(this.InvalidCheckBox.isSelected());
	}

	private void UnverifiedCheckBoxActionPerformed(ActionEvent evt) {
		this.UnverifiedTextField.setEnabled(this.UnverifiedCheckBox.isSelected());
		this.UnverifiedBrowse.setEnabled(this.UnverifiedCheckBox.isSelected());
	}

	private void CustomOutputCheckBoxActionPerformed(ActionEvent evt) {
		this.CustomOutputButton.setEnabled(CustomOutputCheckBox.isSelected());
		if (CustomOutputCheckBox.isSelected()) {
			this.WorkedCheckBox.setText("Matched");
			this.UnverifiedCheckBox.setText("Unmatched");
			this.UnverifiedCheckBox.setFont(new Font("Arial Black", 0, 9));
			this.WorkedTextField.setText(this.WorkedTextField.getText().replace("Worked", "Matched"));
			this.UnverifiedTextField.setText(this.UnverifiedTextField.getText().replace("Unverified", "Unmatched"));
		} else {
			this.WorkedCheckBox.setText("Worked");
			this.UnverifiedCheckBox.setText("Unverified");
			this.UnverifiedCheckBox.setFont(new Font("Arial Black", 0, 10));
			this.WorkedTextField.setText(this.WorkedTextField.getText().replace("Matched", "Worked"));
			this.UnverifiedTextField.setText(this.UnverifiedTextField.getText().replace("Unmatched", "Unverified"));
		}
	}

	public static boolean isCustom() {
		return CustomOutputCheckBox.isSelected();
	}

	private void WorkedFileButtonActionPerformed(ActionEvent evt) {
		this.Workedpath = FileUtil.chooseFile(Main.currFolder, this, new FileUtil.TxtFileFilter());
		if (this.Workedpath != null) {
			this.WorkedTextField.setText(this.Workedpath);
			Main.currFolder = new File(this.Workedpath);
		}
	}

	private void InvalidFileButtonActionPerformed(ActionEvent evt) {
		this.Invalidpath = FileUtil.chooseFile(Main.currFolder, this, new FileUtil.TxtFileFilter());
		if (this.Invalidpath != null) {
			this.InvalidTextField.setText(this.Invalidpath);
			Main.currFolder = new File(this.Invalidpath);
		}
	}

	private void UnverifiedFileButtonActionPerformed(ActionEvent evt) {
		this.Unverifiedpath = FileUtil.chooseFile(Main.currFolder, this, new FileUtil.TxtFileFilter());
		if (this.Unverifiedpath != null) {
			this.UnverifiedTextField.setText(this.Unverifiedpath);
			Main.currFolder = new File(this.Unverifiedpath);
		}
	}

	private void CheckButtonActionPerformed(ActionEvent evt) {
		if (Main.tokens.size() == 0) {
			return;
		}
		this.Workedpath = this.WorkedTextField.getText();
		this.Invalidpath = this.InvalidTextField.getText();
		this.Unverifiedpath = this.UnverifiedTextField.getText();
		if (this.WorkedCheckBox.isSelected() && !FileUtil.checkFileExistOrCreate(new File(this.Workedpath))) {
			JOptionPane.showMessageDialog(this, "Worked output isn't a valid File path", "ERROR", 0);
			return;
		}
		if (this.InvalidCheckBox.isSelected() && !FileUtil.checkFileExistOrCreate(new File(this.Invalidpath))) {
			JOptionPane.showMessageDialog(this, "Invalid output isn't a valid File path", "ERROR", 0);
			return;
		}
		if (this.UnverifiedCheckBox.isSelected() && !FileUtil.checkFileExistOrCreate(new File(this.Unverifiedpath))) {
			JOptionPane.showMessageDialog(this, "Unverified output isn't a valid File path", "ERROR", 0);
			return;
		}
		if (this.checking) {
			return;
		}
		this.checking = true;
		this.CheckButton.setEnabled(false);
		this.check();
	}

	private void check() {
		new Thread(() -> {
			this.WorkedWriter = null;
			this.InvalidWriter = null;
			this.UnverifiedWriter = null;
			try {
				if (this.WorkedCheckBox.isSelected()) {
					this.WorkedWriter = new BufferedWriter(new FileWriter(this.Workedpath));
				}
				if (this.InvalidCheckBox.isSelected()) {
					this.InvalidWriter = new BufferedWriter(new FileWriter(this.Invalidpath));
				}
				if (this.UnverifiedCheckBox.isSelected()) {
					this.UnverifiedWriter = new BufferedWriter(new FileWriter(this.Unverifiedpath));
				}
				int i = 0;
				for (String token : Main.tokens) {
					this.CheckButton.setText("(" + i + "/" + Main.tokens.size() + ")");
					this.write(Main.InfoToken(token, ++i), token);
				}
				this.w = 0;
				this.u = 0;
				this.i = 0;
				this.CheckButton.setText("Check");
				this.checking = false;
				this.CheckButton.setEnabled(true);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (this.WorkedWriter != null) {
					try {
						this.WorkedWriter.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (this.InvalidWriter != null) {
					try {
						this.InvalidWriter.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (this.UnverifiedWriter != null) {
					try {
						this.UnverifiedWriter.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	private void write(Status status, String token) throws IOException {
		switch (status) {
		case Worked: {
			++this.w;
			if (!this.WorkedCheckBox.isSelected())
				break;
			this.WorkedWriter.write((this.w == 1 ? "" : "\n") + token);
			break;
		}
		case Unverified: {
			++this.u;
			if (!this.UnverifiedCheckBox.isSelected())
				break;
			this.UnverifiedWriter.write((this.u == 1 ? "" : "\n") + token);
			break;
		}
		case Invalid: {
			++this.i;
			if (!this.InvalidCheckBox.isSelected())
				break;
			this.InvalidWriter.write((this.i == 1 ? "" : "\n") + token);
		}
		}
		if (GuiDTC.isCustom()) {
			this.TotalInput.setText("Matched: " + this.w + " Unmatched: " + this.u + " Invalid: " + this.i);
		} else {
			this.TotalInput.setText("Worked: " + this.w + " Unverified: " + this.u + " Invalid: " + this.i);
		}
	}

	public void addLine(String s) {
		this.textArea.append(s + "\n");
		System.out.println(s);
	}

	public static void main(String[] args) {
		GuiDTC.onGUI();
	}

	private static void onUIManager() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void start() {
		GuiDTC.onGUI();
	}

	private static void onGUI() {
		SwingUtilities.invokeLater(() -> {
			GuiDTC.onUIManager();
			GuiDTC main = new GuiDTC();
			main.setVisible(true);
			main.getContentPane().setBackground(new Color(0x5E5E5B));
			main.setLocationRelativeTo(null);
			main.setTitle("Discord Token Checker - ?? ??????");
			URL url = GuiDTC.class.getResource("/Head.png");
			main.setIconImage(Toolkit.getDefaultToolkit().getImage(url));
			main.WorkedCheckBox.setSelected(true);
			main.InvalidTextField.setEnabled(false);
			main.InvalidBrowse.setEnabled(false);
			main.UnverifiedTextField.setEnabled(false);
			main.UnverifiedBrowse.setEnabled(false);
			main.WorkedTextField.setText("Token Worked.txt");
			main.InvalidTextField.setText("Token Invalid.txt");
			main.UnverifiedTextField.setText("Token Unverified.txt");
			Main.guiMain = main;
		});
	}

	public static enum Status {
		Worked, Invalid, Unverified;
	}
}