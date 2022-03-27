package net.ranktw.discord;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;
import javax.swing.text.TextAction;

import components.Notification;
import net.ranktw.discord.old.Main;
import net.ranktw.discord.util.Config;
import net.ranktw.discord.util.DiscordIconTableCellRenderer;
import net.ranktw.discord.util.FileUtil;
import net.ranktw.discord.util.Request;
import net.ranktw.discord.util.TablesPopupMenu;
import net.ranktw.discord.util.TextPopupMenu;
import net.ranktw.discord.util.ThreadUtil;
import net.ranktw.discord.util.Unauthorized;
import net.ranktw.utilities.Util;

public class Control {

	private UI control;
	private CustomOutputs customOutputs;
	int scrollPaneTextMaximumValue;
	int scrollPaneDarkMaximumValue;
	private List<String> loaded = new ArrayList<String>();
	private BlockingQueue<User> queue;
	private boolean finished = false;

	public static void main(String[] argument) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		EventQueue.invokeLater(() -> new Control(new UI()));
	}

	Control(UI control) {
		try {
			Config.initSettings();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ThreadUtil.threadPool = Executors.newFixedThreadPool(Config.getThreads());
		this.control = control;
		this.customOutputs = new CustomOutputs();
		control.setVisible(true);
		control.hintTextFieldWorked.setText("Token Worked.txt");
		control.hintTextFieldUnverified.setText("Token Unverified.txt");
		control.hintTextFieldInvalid.setText("Token Invalid.txt");
		control.hintTextFieldUnverified.setEnabled(false);
		control.hintTextFieldInvalid.setEnabled(false);
		control.buttonOutputUnverified.setEnabled(false);
		control.buttonOutputInvalid.setEnabled(false);
		control.buttonCustomOutput.setEnabled(false);
		control.buttonAdvanced.setEnabled(false);
		control.tableLogs.setRowHeight(36);
		control.tableLogs.setSelectionBackground(control.tableLogs.getBackground().brighter());
		control.tableLogs.setDefaultRenderer(Object.class, new DiscordIconTableCellRenderer());
		control.tableLogs.setComponentPopupMenu(new TablesPopupMenu());
		control.textAreaLogs.setFont(new Font("Arial", 1, 11));
		control.textAreaLogs.setComponentPopupMenu(new TextPopupMenu());
		this.initPopupMenu(control.hintTextFieldWorked);
		this.initPopupMenu(control.hintTextFieldUnverified);
		this.initPopupMenu(control.hintTextFieldInvalid);
		URL url = this.getClass().getResource("/images/icon.png");
		control.setIconImage(Toolkit.getDefaultToolkit().getImage(url));
		URL urll = this.getClass().getResource("/images/icon.png");
		this.customOutputs.setIconImage(Toolkit.getDefaultToolkit().getImage(urll));
		Notification.setFrame(control);
		this.initListeners();
		new Thread(this::update).start();
	}

	private void update() {
		while (true) {
			try {
				Control.clearDone();
			} catch (Exception e) {
				System.out.println("Has an Error while update ControlThread: \n |_ " + e.getMessage());
				e.printStackTrace();
			}
			Util.sleep(100L);
		}
	}

	public static void clearDone() {
		if (ThreadUtil.run.isEmpty()) {
			return;
		}
		boolean done = false;
		block0: while (!done) {
			for (Future future : ThreadUtil.run) {
				if (!future.isDone())
					continue;
				ThreadUtil.run.remove(future);
				done = false;
				continue block0;
			}
			done = true;
		}
	}

	private void initListeners() {
		this.scrollPaneTextMaximumValue = this.control.scrollPaneText.getVerticalScrollBar().getMaximum();
		this.control.scrollPaneText.getVerticalScrollBar().addAdjustmentListener(e -> {
			if (this.scrollPaneTextMaximumValue - e.getAdjustable().getMaximum() == 0) {
				return;
			}
			e.getAdjustable().setValue(e.getAdjustable().getMaximum());
			this.scrollPaneTextMaximumValue = this.control.scrollPaneText.getVerticalScrollBar().getMaximum();
		});
		this.scrollPaneDarkMaximumValue = this.control.darkScrollPane.getVerticalScrollBar().getMaximum();
		this.control.darkScrollPane.getVerticalScrollBar().addAdjustmentListener(e -> {
			if (this.scrollPaneDarkMaximumValue - e.getAdjustable().getMaximum() == 0) {
				return;
			}
			e.getAdjustable().setValue(e.getAdjustable().getMaximum());
			this.scrollPaneDarkMaximumValue = this.control.darkScrollPane.getVerticalScrollBar().getMaximum();
		});
		this.control.checkBoxWorked.addActionListener(e -> {
			this.control.hintTextFieldWorked.setEnabled(this.control.checkBoxWorked.isSelected());
			this.control.buttonOutputWorked.setEnabled(this.control.checkBoxWorked.isSelected());
		});
		this.control.checkBoxUnverified.addActionListener(e -> {
			this.control.hintTextFieldUnverified.setEnabled(this.control.checkBoxUnverified.isSelected());
			this.control.buttonOutputUnverified.setEnabled(this.control.checkBoxUnverified.isSelected());
		});
		this.control.checkBoxInvalid.addActionListener(e -> {
			this.control.hintTextFieldInvalid.setEnabled(this.control.checkBoxInvalid.isSelected());
			this.control.buttonOutputInvalid.setEnabled(this.control.checkBoxInvalid.isSelected());
		});
		this.control.checkBoxCustomOutput.addActionListener(e -> {
			boolean selected = this.control.checkBoxCustomOutput.isSelected();
			this.control.buttonCustomOutput.setEnabled(this.control.checkBoxCustomOutput.isSelected());
			this.control.checkBoxWorked.setText(selected ? "Matched" : "Worked");
			this.control.checkBoxUnverified.setText(selected ? "Unmatched" : "Unverified");
			this.control.hintTextFieldWorked.setText(this.control.hintTextFieldWorked.getText()
					.replace(selected ? "Worked" : "Matched", selected ? "Matched" : "Worked"));
			this.control.hintTextFieldUnverified.setText(this.control.hintTextFieldUnverified.getText()
					.replace(selected ? "Unverified" : "Unmatched", selected ? "Unmatched" : "Unverified"));
		});
		this.control.buttonInputToken.addActionListener(e -> new InputToken(this).setVisible(true));
		this.control.buttonCustomOutput.addActionListener(e -> this.customOutputs.setVisible(true));
		this.control.buttonOutputWorked.addActionListener(e -> {
			String path = FileUtil.chooseFile(Main.currFolder, this.control, new FileUtil.TxtFileFilter());
			if (path == null) {
				return;
			}
			this.control.hintTextFieldWorked.setText(path);
		});
		this.control.buttonOutputUnverified.addActionListener(e -> {
			String path = FileUtil.chooseFile(Main.currFolder, this.control, new FileUtil.TxtFileFilter());
			if (path == null) {
				return;
			}
			this.control.hintTextFieldUnverified.setText(path);
		});
		this.control.buttonOutputInvalid.addActionListener(e -> {
			String path = FileUtil.chooseFile(Main.currFolder, this.control, new FileUtil.TxtFileFilter());
			if (path == null) {
				return;
			}
			this.control.hintTextFieldInvalid.setText(path);
		});
		this.control.buttonCheck.addActionListener(e -> {
			if (this.loaded.isEmpty()) {
				return;
			}
			this.control.buttonCheck.setEnabled(false);
			this.control.buttonCheck.setBackground(new Color(255, 162, 43));
			this.set(false);
			this.check();
		});
	}

	private void initPopupMenu(JTextField textField) {
		JPopupMenu menu = new JPopupMenu() {

			@Override
			public void show(Component c, int x, int y) {
				if (c instanceof JTextField && c.isEnabled()) {
					c.requestFocusInWindow();
					super.show(c, x, y);
				}
			}
		};
		DefaultEditorKit.CutAction cut = new DefaultEditorKit.CutAction();
		cut.putValue("Name", "Cut");
		cut.putValue("AcceleratorKey", KeyStroke.getKeyStroke("control X"));
		menu.add(cut);
		DefaultEditorKit.CopyAction copy = new DefaultEditorKit.CopyAction();
		copy.putValue("Name", "Copy");
		copy.putValue("AcceleratorKey", KeyStroke.getKeyStroke("control C"));
		menu.add(copy);
		DefaultEditorKit.PasteAction paste = new DefaultEditorKit.PasteAction();
		paste.putValue("Name", "Paste");
		paste.putValue("AcceleratorKey", KeyStroke.getKeyStroke("control V"));
		menu.add(paste);
		SelectAll selectAll = new SelectAll();
		menu.add(selectAll);
		textField.setComponentPopupMenu(menu);
	}

	public void inputTokens(List<String> tokens) {
		this.loaded.clear();
		tokens = Control.fTokens(tokens);
		int all = tokens.size();
		tokens = this.checkTokens(tokens);
		int duplicate = all - tokens.size();
		Control.showInfo("Total Input: " + all + (duplicate == 0 ? "" : "\nDuplicate: " + duplicate));
		this.loaded = tokens;
		this.control.labelStatus.setText("Total Input - " + this.loaded.size());
	}

	/*
	 * Enabled aggressive block sorting
	 */
	private List<String> checkTokens(List<String> tokens) {
		ArrayList<String> checked = new ArrayList<String>();
		List<String> all = this.loaded;
		Iterator<String> iterator = tokens.iterator();
		block0: while (iterator.hasNext()) {
			String token = iterator.next();
			if (token.equals(""))
				continue;
			if (token.contains(":")) {
				String[] stringArray = token.split(":");
				int n = stringArray.length;
				int n2 = 0;
				while (true) {
					if (n2 >= n)
						continue block0;
					String check = stringArray[n2];
					if (check.length() == 59) {
						token = check;
						break;
					}
					if (check.length() == 88 && check.startsWith("mfa.")) {
						token = check;
						break;
					}
					++n2;
				}
			}
			if (Config.isDuplicateTokens()) {
				if (checked.contains(token) || all.contains(token))
					continue;
				checked.add(token);
				continue;
			}
			checked.add(token);
		}
		return checked;
	}

	private static List<String> fTokens(List<String> tokens) {
		ArrayList<String> checked = new ArrayList<String>();
		for (String token : tokens) {
			if (token.equals(""))
				continue;
			checked.add(token);
		}
		return checked;
	}

	public static void showInfo(String info) {
		System.out.println("Info: " + info);
		Notification.info(info);
	}

	public static void showError(String e) {
		System.out.println("Error: " + e);
		Notification.error(e);
	}

	public static void showError(Exception e) {
		e.printStackTrace();
		System.out.println("Error: " + e.getMessage());
		System.out.println(" |  " + e.getLocalizedMessage());
		System.out.println(" |_ " + e.toString());
		Notification.error(e.toString().replace("java.lang.Exception: ", "Error: "));
	}

	private void check() {
		final AtomicInteger work = new AtomicInteger();
		final AtomicInteger unverified = new AtomicInteger();
		final AtomicInteger invalid = new AtomicInteger();
		this.queue = new LinkedBlockingQueue<User>();
		this.finished = false;
		new Thread(this::track).start();
		new ThreadUtil(this.loaded) {

			@Override
			public void update() {
				((Control) Control.this).control.buttonCheck.setText(this.getStatus());
			}

			@Override
			public void finished() {
				Control.this.finished = true;
				Control.this.set(true);
				((Control) Control.this).control.buttonCheck.setEnabled(true);
				((Control) Control.this).control.buttonCheck.setText("Check");
				((Control) Control.this).control.buttonCheck.setBackground(new Color(4437377));
			}

			@Override
			public void start(String token) {
				StringBuilder text;
				block22: {
					text = new StringBuilder();
					text.append("#").append(this.getTokenIn()).append("\n");
					text.append("Token: ").append(token).append("\n\n");
					try {
						boolean matched;
						block24: {
							User user;
							block25: {
								block23: {
									Request request = new Request(token);
									user = request.getUser();
									user.setToken(token);
									text.append(user.toString()).append("\n");
									text.append("Creation Time: ").append(user.getCreatedDate()).append("\n");
									text.append("Email Verified: ").append(user.isEmailVerified()).append("\n");
									text.append("Email: ").append(user.getEmail()).append("\n");
									text.append("Phone: ").append(user.getPhone()).append("\n");
									text.append("Avatar: ").append(user.isDefaultAvatar() ? "Null" : "\"Has Avatar\"")
											.append("\n");
									text.append("Badges: ").append(user.getFlags()).append("\n");
									text.append("Premium: ").append(user.getPremium()).append("\n");
									try {
										int size = request.getGuildID().size();
										text.append("Guild Count: ").append(size).append("\n\n");
										user.setGuilds(size);
									} catch (Exception e) {
										if (e.getMessage().equals(
												"You need to verify your account in order to perform this action.")) {
											text.append("\"Required verify account\"").append("\n\n");
											user.setRequiredVerified(true);
										}
										throw e;
									}
									Control.this.queue(user);
									if (!Control.this.isCustom()) {
										if (!user.isRequiredVerified()) {
											work.incrementAndGet();
											if (Control.this.isOutputWorked()) {
												Control.this.save(Control.this.getWorkedPath(), token);
											}
										} else {
											unverified.incrementAndGet();
											if (Control.this.isOutputUnverified()) {
												Control.this.save(Control.this.getUnverifiedPath(), token);
											}
										}
										break block22;
									}
									matched = true;
									if (!Control.this.hasEmail())
										break block23;
									boolean bl = matched = user.getEmail() != null;
									if (!matched)
										break block24;
								}
								if (!Control.this.hasAvatar())
									break block25;
								boolean bl = matched = !user.isDefaultAvatar();
								if (!matched)
									break block24;
							}
							if ((!Control.this.isEmailVerified() || (matched = user.isEmailVerified()))
									&& Control.this.isPhoneVerified()) {
								matched = user.isPhoneVerified();
							}
						}
						if (matched) {
							work.incrementAndGet();
							if (Control.this.isOutputWorked()) {
								Control.this.save(Control.this.getWorkedPath(), token);
							}
						} else {
							unverified.incrementAndGet();
							if (Control.this.isOutputUnverified()) {
								Control.this.save(Control.this.getUnverifiedPath(), token);
							}
						}
					} catch (Unauthorized e) {
						text.append(e.getMessage()).append("\n");
						if (Control.this.isOutputInvalid()) {
							Control.this.save(Control.this.getInvalidPath(), token);
						}
						invalid.incrementAndGet();
					} catch (Exception e) {
						text.append(e.toString().replace("java.lang.Exception: ", "Error: ")).append("\n");
						invalid.incrementAndGet();
						e.printStackTrace();
					}
				}
				((Control) Control.this).control.labelStatus.setText((Control.this.isCustom() ? "Matched" : "Worked")
						+ ": " + work.get() + " " + (Control.this.isCustom() ? "Unmatched" : "Unverified") + ": "
						+ unverified.get() + " Invalid: " + invalid.get());
				((Control) Control.this).control.textAreaLogs.append(text.toString());
				System.out.println(text.toString());
			}
		};
	}

	private void queue(User user) {
		this.queue.offer(user);
	}

	private void track() {
		while (!this.finished || !this.queue.isEmpty()) {
			try {
				if (!this.queue.isEmpty()) {
					this.control.addDate(Collections.nCopies(6, this.queue.poll()).toArray());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			Util.sleep(90L);
		}
	}

	public void save(String file, String token) {
		try {
			Path path = Paths.get(file, new String[0]);
			Files.write(path, Collections.singletonList(token), StandardCharsets.UTF_8,
					Files.exists(path, new LinkOption[0]) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean hasEmail() {
		return this.customOutputs.checkBoxWorked.isSelected();
	}

	public boolean hasAvatar() {
		return this.customOutputs.checkBoxWorked1.isSelected();
	}

	public boolean isEmailVerified() {
		return this.customOutputs.checkBoxWorked2.isSelected();
	}

	public boolean isPhoneVerified() {
		return this.customOutputs.checkBoxWorked3.isSelected();
	}

	public boolean isOutputWorked() {
		return this.control.checkBoxWorked.isSelected();
	}

	public boolean isOutputUnverified() {
		return this.control.checkBoxUnverified.isSelected();
	}

	public boolean isOutputInvalid() {
		return this.control.checkBoxInvalid.isSelected();
	}

	public boolean isCustom() {
		return this.control.checkBoxCustomOutput.isSelected();
	}

	public String getWorkedPath() {
		if (this.control.hintTextFieldWorked.getText().equals("")) {
			this.control.hintTextFieldWorked.setText(this.isCustom() ? "Token Matched.txt" : "Token Worked.txt");
		}
		return this.control.hintTextFieldWorked.getText();
	}

	public String getUnverifiedPath() {
		if (this.control.hintTextFieldUnverified.getText().equals("")) {
			this.control.hintTextFieldUnverified
					.setText(this.isCustom() ? "Token Unmatched.txt" : "Token Unverified.txt");
		}
		return this.control.hintTextFieldUnverified.getText();
	}

	public String getInvalidPath() {
		if (this.control.hintTextFieldInvalid.getText().equals("")) {
			this.control.hintTextFieldInvalid.setText("Token Invalid.txt");
		}
		return this.control.hintTextFieldInvalid.getText();
	}

	private void set(boolean b) {
		this.control.checkBoxWorked.setEnabled(b);
		this.control.checkBoxUnverified.setEnabled(b);
		this.control.checkBoxInvalid.setEnabled(b);
		this.control.hintTextFieldWorked.setEnabled(b);
		this.control.hintTextFieldUnverified.setEnabled(b);
		this.control.hintTextFieldInvalid.setEnabled(b);
		this.control.checkBoxCustomOutput.setEnabled(b);
		this.control.buttonCustomOutput.setEnabled(b);
		this.control.buttonOutputWorked.setEnabled(b);
		this.control.buttonOutputUnverified.setEnabled(b);
		this.control.buttonOutputInvalid.setEnabled(b);
		this.control.buttonInputToken.setEnabled(b);
	}

	static class SelectAll extends TextAction {
		SelectAll() {
			super("Select All");
			this.putValue("AcceleratorKey", KeyStroke.getKeyStroke("control S"));
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JTextComponent component = this.getFocusedComponent();
			component.selectAll();
			component.requestFocusInWindow();
		}
	}
}