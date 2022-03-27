package net.ranktw.discord.old;

import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;

public class AdvancedSettings extends JFrame {

	private JRadioButton HttpRadioButton;
	private JRadioButton SocksRadioButton;
	private JCheckBox UseProxyCheckBox;
	private JButton LoadProxyButton;
	private JLabel LoadedProxiesLabel;
	private JTextField ProxyFileTextField;
	private JCheckBox MultiThreadedCheckBox;
	private JLabel ThreadCountLabel;
	private JSpinner ThreadCountSpinner;
	private JButton ExportProxyButton;
	private JCheckBox RemoveDuplicateCheckBox;
	private JSeparator jSeparator1;
	private JSeparator jSeparator2;

	public AdvancedSettings() {
		this.initComponents();
	}

	private void initComponents() {
		this.MultiThreadedCheckBox = new JCheckBox();
		this.ThreadCountLabel = new JLabel();
		this.ThreadCountSpinner = new JSpinner();
		this.UseProxyCheckBox = new JCheckBox();
		this.HttpRadioButton = new JRadioButton();
		this.SocksRadioButton = new JRadioButton();
		this.jSeparator1 = new JSeparator();
		this.RemoveDuplicateCheckBox = new JCheckBox();
		this.jSeparator2 = new JSeparator();
		this.LoadProxyButton = new JButton();
		this.ProxyFileTextField = new JTextField();
		this.LoadedProxiesLabel = new JLabel();
		this.ExportProxyButton = new JButton();
		this.setDefaultCloseOperation(1);
		this.RemoveDuplicateCheckBox.setFont(new Font("Arial", 0, 10));
		this.RemoveDuplicateCheckBox.setSelected(true);
		this.RemoveDuplicateCheckBox.setText("Remove duplicate tokens");
		this.MultiThreadedCheckBox.setFont(new Font("Arial", 0, 11));
		this.MultiThreadedCheckBox.setText("Multi-threaded");
		this.MultiThreadedCheckBox.addChangeListener(this::MultiThreadedCheckBoxStateChanged);
		this.ThreadCountLabel.setFont(new Font("Arial", 0, 10));
		this.ThreadCountLabel.setText("Thread Count");
		this.ThreadCountSpinner.setValue(10);
		this.UseProxyCheckBox.setFont(new Font("Arial", 0, 10));
		this.UseProxyCheckBox.setText("Use Proxy");
		this.UseProxyCheckBox.addChangeListener(this::UseProxyCheckBoxStateChanged);
		this.HttpRadioButton.setText("Http");
		this.HttpRadioButton.addActionListener(this::HttpRadioButtonActionPerformed);
		this.SocksRadioButton.setText("Socks");
		this.SocksRadioButton.addActionListener(this::SocksRadioButtonActionPerformed);
		this.LoadProxyButton.setText("Load Proxy");
		this.LoadProxyButton.addActionListener(this::LoadProxyButtonActionPerformed);
		this.ProxyFileTextField.setEditable(false);
		this.LoadedProxiesLabel.setFont(new Font("Arial Black", 0, 12));
		this.LoadedProxiesLabel.setText("Loaded Proxies - 0");
		this.ExportProxyButton.setText("Export Proxy");
		this.ExportProxyButton.addActionListener(this::ExportProxyButtonActionPerformed);
		GroupLayout layout = new GroupLayout(this.getContentPane());
		this.getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(this.jSeparator1).addComponent(this.jSeparator2)
				.addGroup(layout.createSequentialGroup().addGroup(layout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addContainerGap()
								.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup().addGap(21, 21, 21)
												.addComponent(this.LoadedProxiesLabel, -1, 160, Short.MAX_VALUE))
										.addGroup(layout.createSequentialGroup().addGroup(layout
												.createParallelGroup(GroupLayout.Alignment.LEADING)
												.addGroup(layout.createSequentialGroup()
														.addComponent(this.UseProxyCheckBox)
														.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
														.addComponent(this.HttpRadioButton)
														.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(this.SocksRadioButton))
												.addGroup(layout.createSequentialGroup()
														.addComponent(this.LoadProxyButton)
														.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
														.addComponent(this.ProxyFileTextField, -2, 74, -2))
												.addGroup(
														layout.createSequentialGroup()
																.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
																		3, -2)
																.addComponent(this.ExportProxyButton)))
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED))))
						.addGroup(layout.createSequentialGroup().addGroup(layout
								.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(layout.createSequentialGroup().addGap(7, 7, 7).addGroup(layout
										.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addGroup(layout.createSequentialGroup().addComponent(this.ThreadCountLabel)
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(this.ThreadCountSpinner, -2, 41, -2))
										.addComponent(this.MultiThreadedCheckBox)))
								.addGroup(layout.createSequentialGroup().addContainerGap()
										.addComponent(this.RemoveDuplicateCheckBox)))
								.addGap(0, 0, Short.MAX_VALUE)))
						.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addGap(3, 3, 3).addComponent(this.MultiThreadedCheckBox).addGap(1, 1, 1)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.ThreadCountLabel)
						.addComponent(this.ThreadCountSpinner, -2, -1, -2))
				.addGap(8, 8, 8).addComponent(this.jSeparator1, -2, 4, -2)
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addComponent(this.RemoveDuplicateCheckBox)
				.addGap(9, 9, 9).addComponent(this.jSeparator2, -2, 4, -2)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.UseProxyCheckBox)
						.addComponent(this.HttpRadioButton).addComponent(this.SocksRadioButton))
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.LoadProxyButton)
						.addComponent(this.ProxyFileTextField, -2, -1, -2))
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.LoadedProxiesLabel)
				.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.ExportProxyButton)
				.addContainerGap(-1, Short.MAX_VALUE)));
		this.pack();
	}

	private void MultiThreadedCheckBoxStateChanged(ChangeEvent evt) {
	}

	private void UseProxyCheckBoxStateChanged(ChangeEvent evt) {
	}

	private void SocksRadioButtonActionPerformed(ActionEvent evt) {
	}

	private void HttpRadioButtonActionPerformed(ActionEvent evt) {
	}

	private void LoadProxyButtonActionPerformed(ActionEvent evt) {
	}

	private void ExportProxyButtonActionPerformed(ActionEvent evt) {
	}

	public static void start() {
		SwingUtilities.invokeLater(() -> {
			AdvancedSettings.onUIManager();
			AdvancedSettings advancedSettings = new AdvancedSettings();
			advancedSettings.setVisible(false);
			advancedSettings.setLocationRelativeTo(null);
			advancedSettings.setTitle("Advanced Settings");
			Main.advancedSettings = advancedSettings;
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