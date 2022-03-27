package net.ranktw.discord.old;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import net.ranktw.discord.util.FileUtil;

public class GuiInput extends JFrame {

	private String path;
	private JButton fileInputButton;
	private JButton AddButton;
	private JRadioButton FromFileButton;
	private JRadioButton FromTextButton;
	private JScrollPane scrollPane;
	private JTextArea TextTextArea;
	private JTextField FileTextField;

	GuiInput() {
		this.initComponents();
	}

	private void initComponents() {
		this.FromFileButton = new JRadioButton();
		this.FileTextField = new JTextField();
		this.fileInputButton = new JButton();
		this.FromTextButton = new JRadioButton();
		this.scrollPane = new JScrollPane();
		this.TextTextArea = new JTextArea();
		this.AddButton = new JButton();
		this.FileTextField.setBackground(new Color(0x5E5E5B));
		this.TextTextArea.setBackground(new Color(0x5E5E5B));
		this.setDefaultCloseOperation(1);
		this.setBackground(new Color(0x333333));
		this.FromFileButton.setText("From File");
		this.FromFileButton.setBackground(Color.DARK_GRAY);
		this.FromFileButton.setFont(new Font("Arial Black", 0, 12));
		this.FromFileButton.setForeground(new Color(255, 255, 255));
		this.FromFileButton.addActionListener(this::FromFileButtonActionPerformed);
		this.fileInputButton.setText("...");
		this.fileInputButton.addActionListener(this::FileInputButtonActionPerformed);
		this.FromTextButton.setText("From Text");
		this.FromTextButton.setBackground(Color.DARK_GRAY);
		this.FromTextButton.setFont(new Font("Arial Black", 0, 12));
		this.FromTextButton.setForeground(new Color(255, 255, 255));
		this.FromTextButton.addActionListener(this::FromTextButtonActionPerformed);
		this.TextTextArea.setColumns(20);
		this.TextTextArea.setRows(5);
		this.TextTextArea.setForeground(Color.white);
		this.scrollPane.setViewportView(this.TextTextArea);
		this.AddButton.setText("Add");
		this.AddButton.addActionListener(this::AddButtonActionPerformed);
		GroupLayout layout = new GroupLayout(this.getContentPane());
		this.getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.scrollPane)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(this.FromTextButton).addComponent(this.FromFileButton)
										.addGroup(layout.createSequentialGroup()
												.addComponent(this.FileTextField, -2, 241, Short.MAX_VALUE)
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(this.fileInputButton, -2, 25, -2))
										.addGroup(layout.createSequentialGroup().addGap(65, 65, 65)
												.addComponent(this.AddButton, -2, 130, -2)))
								.addGap(0, 0, Short.MAX_VALUE)))
				.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap(-1, Short.MAX_VALUE)
						.addComponent(this.FromFileButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(this.FileTextField, -2, -1, -2).addComponent(this.fileInputButton))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.FromTextButton)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(this.scrollPane, -2, 240, -2)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.AddButton)));
		this.pack();
	}

	private void FileInputButtonActionPerformed(ActionEvent evt) {
		this.path = FileUtil.chooseFile(Main.currFolder, this, new FileUtil.TxtFileFilter());
		if (this.path != null) {
			this.FileTextField.setText(this.path);
			Main.currFolder = new File(this.path);
			String s = this.path;
			String[] ss = s.split("\\.");
			String w = s.replace("." + ss[ss.length - 1], " Worked." + ss[ss.length - 1]);
			String i = s.replace("." + ss[ss.length - 1], " Invalid." + ss[ss.length - 1]);
			String u = s.replace("." + ss[ss.length - 1], " Unverified." + ss[ss.length - 1]);
			Main.guiMain.Workedpath = w;
			Main.guiMain.WorkedTextField.setText(w);
			Main.guiMain.Invalidpath = i;
			Main.guiMain.InvalidTextField.setText(i);
			Main.guiMain.Unverifiedpath = u;
			Main.guiMain.UnverifiedTextField.setText(u);
		}
	}

	private void FromFileButtonActionPerformed(ActionEvent evt) {
		this.FromFileButton.setSelected(true);
		this.FileTextField.setEnabled(true);
		this.fileInputButton.setEnabled(true);
		this.FromTextButton.setSelected(false);
		this.TextTextArea.setEnabled(false);
		this.FromFileButton.setForeground(new Color(255, 255, 255));
		this.FromTextButton.setForeground(new Color(168, 168, 168));
	}

	private void FromTextButtonActionPerformed(ActionEvent evt) {
		this.FromFileButton.setSelected(false);
		this.FileTextField.setEnabled(false);
		this.fileInputButton.setEnabled(false);
		this.FromTextButton.setSelected(true);
		this.TextTextArea.setEnabled(true);
		this.FromTextButton.setForeground(new Color(255, 255, 255));
		this.FromFileButton.setForeground(new Color(168, 168, 168));
	}

	private void AddButtonActionPerformed(ActionEvent evt) {
		if (this.FromFileButton.isSelected()) {
			if (!FileUtil.checkFileExist(this.path)) {
				JOptionPane.showMessageDialog(this, "Input File doesn't exist!", "ERROR", 0);
				return;
			}
			Main.tokens.clear();
			this.loadFile();
		} else {
			Main.tokens.clear();
			if (!this.TextTextArea.getText().equals("")) {
				Main.tokens.addAll(Arrays.asList(this.TextTextArea.getText().split("\n")));
			}
		}
		this.setVisible(false);
		Main.guiMain.TotalInput.setText("Total Input - " + Main.tokens.size());
	}

	private void loadFile() {
		try {
			this.path = this.FileTextField.getText();
			File file = new File(this.path);
			try (BufferedReader reader = new BufferedReader(new FileReader(file));) {
				reader.lines().forEach(line -> Main.tokens.add((String) line));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			GuiInput.onUIManager();
			GuiInput input = new GuiInput();
			input.setVisible(true);
			input.getContentPane().setBackground(Color.darkGray);
			input.setLocationRelativeTo(null);
			input.setTitle("Input Tokens");
		});
	}

	static void start() {
		SwingUtilities.invokeLater(() -> {
			GuiInput.onUIManager();
			GuiInput input = new GuiInput();
			input.setVisible(false);
			input.getContentPane().setBackground(Color.darkGray);
			input.setLocationRelativeTo(null);
			input.setTitle("Input Tokens");
			input.FromFileButton.setSelected(true);
			input.FileTextField.setEnabled(true);
			input.fileInputButton.setEnabled(true);
			input.FromTextButton.setSelected(false);
			input.TextTextArea.setEnabled(false);
			input.FromFileButton.setForeground(new Color(255, 255, 255));
			input.FromTextButton.setForeground(new Color(168, 168, 168));
			Main.guiInput = input;
		});
	}

	private static void onUIManager() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}