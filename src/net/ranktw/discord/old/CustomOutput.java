package net.ranktw.discord.old;

import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.LayoutStyle;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class CustomOutput extends JFrame {

	private static JCheckBox EmailVerifiedCheckBox;
	private static JCheckBox PhoneVerifiedCheckBox;
	private static JCheckBox hasAvatarCheckBox;
	private static JCheckBox hasEmailCheckBox;
	private static JCheckBox NotRequiredVerifyCheckBox;

	public CustomOutput() {
		this.initComponents();
	}

	private void initComponents() {
		hasEmailCheckBox = new JCheckBox();
		hasAvatarCheckBox = new JCheckBox();
		EmailVerifiedCheckBox = new JCheckBox();
		PhoneVerifiedCheckBox = new JCheckBox();
		NotRequiredVerifyCheckBox = new JCheckBox();
		this.setDefaultCloseOperation(1);
		NotRequiredVerifyCheckBox.setText("Not Required Verify");
		hasEmailCheckBox.setText("has Email");
		hasAvatarCheckBox.setText("has Avatar");
		EmailVerifiedCheckBox.setText("Email Verified");
		PhoneVerifiedCheckBox.setText("Phone Verified");
		GroupLayout layout = new GroupLayout(this.getContentPane());
		this.getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(hasEmailCheckBox)
						.addComponent(hasAvatarCheckBox).addComponent(EmailVerifiedCheckBox)
						.addComponent(PhoneVerifiedCheckBox).addComponent(NotRequiredVerifyCheckBox))
				.addContainerGap(85, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap().addComponent(hasEmailCheckBox)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(hasAvatarCheckBox)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(EmailVerifiedCheckBox)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(PhoneVerifiedCheckBox)
						.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(NotRequiredVerifyCheckBox).addContainerGap(16, Short.MAX_VALUE)));
		this.pack();
	}

	public static void start() {
		SwingUtilities.invokeLater(() -> {
			CustomOutput.onUIManager();
			CustomOutput customOutput = new CustomOutput();
			customOutput.setVisible(false);
			customOutput.setLocationRelativeTo(null);
			customOutput.setTitle("Custom Output");
			Main.customOutput = customOutput;
		});
	}

	private static void onUIManager() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean getEmailVerifiedCheckBox() {
		return EmailVerifiedCheckBox.isSelected();
	}

	public static boolean getPhoneVerifiedCheckBox() {
		return PhoneVerifiedCheckBox.isSelected();
	}

	public static boolean gethasAvatarCheckBox() {
		return hasAvatarCheckBox.isSelected();
	}

	public static boolean gethasEmailCheckBox() {
		return hasEmailCheckBox.isSelected();
	}

	public static boolean getNotRequiredVerifyCheckBox() {
		return NotRequiredVerifyCheckBox.isSelected();
	}
}