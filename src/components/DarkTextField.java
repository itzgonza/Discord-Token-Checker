package components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.undo.UndoManager;

import org.jdesktop.swingx.HorizontalLayout;
import org.jdesktop.swingx.VerticalLayout;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

import com.alee.extended.label.WebHotkeyLabel;
import com.alee.extended.window.WebPopOver;
import com.alee.laf.label.WebLabel;
import com.alee.laf.text.WebTextField;

import dev.Main;

public class DarkTextField extends JPanel {

	private JFrame frame;
	private String text = "";
	private int position = 10;
	private String inputPromp = "";
	private Color inputPrompColor = new Color(192, 192, 192);
	private int inputPrompPosition = 0;
	private boolean focused;
	private boolean entered;
	private boolean show;
	private boolean canRightClick = true;
	float alpha = 1.0f;
	private WebLabel label;
	private BackgroundPanel background;
	private WebTextField textField;
	private WebPopOver editorPopup;
	private WebHotkeyLabel undoHotkey;
	private WebLabel undoLabel;
	private UndoManager undoManager;

	public DarkTextField() {
		this((JFrame) null);
	}

	public DarkTextField(JFrame frame) {
		this.initComponents();
		this.editerPopup();
		this.frame = frame;
	}

	private void initComponents() {
		this.label = new WebLabel();
		this.background = new BackgroundPanel();
		this.textField = new WebTextField();
		this.label.setBackground(new Color(43, 46, 50));
		this.label.setForeground(new Color(247, 247, 247));
		this.label.setText("Label");
		this.label.setFont(Main.loadFont("/fonts/OpenSans-SemiBold.ttf", 12.0f, 0));
		this.setCursor(new Cursor(2));
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent evt) {
				DarkTextField.this.textFieldPanelMouseEntered(evt);
			}

			@Override
			public void mouseExited(MouseEvent evt) {
				DarkTextField.this.textFieldPanelMouseExited(evt);
			}

			@Override
			public void mouseReleased(MouseEvent evt) {
				DarkTextField.this.textFieldMouseReleased(evt);
			}
		});
		this.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent evt) {
				DarkTextField.this.textFieldPanelComponentResized(evt);
			}
		});
		this.setLayout(new AbsoluteLayout());
		this.textField.setDrawBackground(false);
		this.textField.setDrawBorder(false);
		this.textField.setDrawFocus(false);
		this.textField.setDrawShade(false);
		this.textField.setBackground(new Color(47, 50, 55));
		this.textField.setForeground(this.label.getForeground());
		this.textField.setSelectedTextColor(new Color(255, 255, 255));
		this.textField.setSelectionColor(new Color(0, 150, 201));
		this.textField.setText(this.text);
		this.textField.setBorder(null);
		this.textField.setCaretColor(new Color(255, 255, 255));
		this.textField.setMargin(new Insets(2, 2, 2, 2));
		this.textField.setOpaque(false);
		this.textField.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent evt) {
				DarkTextField.this.textFieldFocusGained(evt);
			}

			@Override
			public void focusLost(FocusEvent evt) {
				DarkTextField.this.textFieldFocusLost(evt);
			}
		});
		this.textField.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent evt) {
				DarkTextField.this.textFieldMouseEntered(evt);
			}

			@Override
			public void mouseExited(MouseEvent evt) {
				DarkTextField.this.textFieldMouseExited(evt);
			}

			@Override
			public void mouseReleased(MouseEvent evt) {
				DarkTextField.this.textFieldMouseReleased(evt);
			}
		});
		this.textField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent evt) {
				DarkTextField.this.textFieldKeyTyped(evt);
			}
		});
		this.add((Component) this.textField, new AbsoluteConstraints(5, 5, 120, 20));
		this.background.setBackground(new Color(49, 52, 57));
		this.background.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent evt) {
				DarkTextField.this.textField.requestFocusInWindow();
			}
		});
		this.add((Component) this.background, new AbsoluteConstraints(0, 0, 130, 30));
	}

	public void setEditable(boolean editable) {
		this.textField.setEditable(editable);
	}

	public boolean isEditable() {
		return this.textField.isEditable();
	}

	private void textFieldPanelComponentResized(ComponentEvent evt) {
		this.remove(this.textField);
		this.add((Component) this.textField,
				new AbsoluteConstraints(5, 5, this.getWidth() - 10, this.getHeight() - 10));
		this.background.setWidthAndHeight(this.getWidth(), this.getHeight());
		this.remove(this.background);
		this.add((Component) this.background, new AbsoluteConstraints(0, 0, this.getWidth(), this.getHeight()));
		this.frame.repaint();
	}

	private void editerPopup() {
		this.editorPopup = new WebPopOver();
		this.editorPopup.setBorderColor(new Color(51, 51, 51));
		this.editorPopup.setCloseOnFocusLoss(true);
		this.editorPopup.setContentBackground(new Color(51, 51, 51));
		this.editorPopup.setMinimumSize(new Dimension(119, 70));
		this.editorPopup.setPreferredSize(new Dimension(119, 112));
		this.editorPopup.setMovable(false);
		this.editorPopup.setResizable(false);
		this.editorPopup.setRound(1);
		this.editorPopup.setShadeWidth(5);
		this.editorPopup.setTransparency(1.0f);
		JPanel editPanel = new JPanel();
		editPanel.setBackground(this.editorPopup.getBackground());
		editPanel.setMaximumSize(editPanel.getPreferredSize());
		editPanel.setMinimumSize(editPanel.getPreferredSize());
		editPanel.setPreferredSize(new Dimension(109, 102));
		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setGap(2);
		editPanel.setLayout(verticalLayout);
		this.editorPopup.getContentPane().add((Component) editPanel, "Center");
		this.undoManager = new UndoManager();
		this.textField.getDocument().addUndoableEditListener(this.undoManager);
		JPanel undoPanel = new JPanel();
		undoPanel.setBackground(this.editorPopup.getContentBackground());
		undoPanel.setMinimumSize(new Dimension(0, 14));
		undoPanel.setPreferredSize(new Dimension(100, 14));
		undoPanel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent evt) {
				DarkTextField.this.epanelMouseEntered(evt);
			}

			@Override
			public void mouseExited(MouseEvent evt) {
				DarkTextField.this.epanelMouseExited(evt);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (DarkTextField.this.undoManager.canUndo()) {
					DarkTextField.this.undoManager.undo();
				}
				DarkTextField.this.epanelMouseExited(e);
				DarkTextField.this.editorPopup.dispose();
			}
		});
		HorizontalLayout horizontalLayout6 = new HorizontalLayout();
		horizontalLayout6.setGap(1);
		undoPanel.setLayout(horizontalLayout6);
		this.undoLabel = new WebLabel();
		this.undoLabel.setForeground(new Color(153, 153, 153));
		this.undoLabel.setText("Undo");
		this.undoLabel.setFont(Main.loadFont("/fonts/OpenSans-Bold.ttf", 11.0f, 0));
		this.undoLabel.setMargin(new Insets(0, 2, 0, 0));
		this.undoLabel.setMaximumSize(new Dimension(60, 14));
		this.undoLabel.setMinimumSize(new Dimension(60, 14));
		this.undoLabel.setPreferredSize(new Dimension(60, 14));
		undoPanel.add(this.undoLabel);
		this.undoHotkey = new WebHotkeyLabel();
		this.undoHotkey.setText("Ctrl+Z");
		this.undoHotkey.setEnabled(false);
		this.undoHotkey.setForeground(new Color(0x343434));
		this.undoHotkey.setFont(new Font("Tahoma", 1, 10));
		undoPanel.add(this.undoHotkey);
		editPanel.add(this.separatorPanel());
		editPanel.add(undoPanel);
		editPanel.add(this.separatorPanel());
		editPanel.add(this.separator());
		editPanel.add(this.separatorPanel());
		editPanel.add(this.actionPanel("Cut", "Ctrl+X"));
		editPanel.add(this.actionPanel("Copy", "Ctrl+C"));
		editPanel.add(this.actionPanel("Paste", "Ctrl+V"));
		editPanel.add(this.separatorPanel());
		editPanel.add(this.separator());
		editPanel.add(this.separatorPanel());
		editPanel.add(this.actionPanel("Select All", "Ctrl+A"));
		editPanel.add(this.separatorPanel());
	}

	private void epanelMouseExited(MouseEvent evt) {
		((JComponent) evt.getComponent()).setBackground(new Color(51, 51, 51));
	}

	private void epanelMouseEntered(MouseEvent evt) {
		((JComponent) evt.getComponent()).setBackground(new Color(51, 51, 51).brighter());
	}

	private JPanel separatorPanel() {
		JPanel separatorPanel = new JPanel();
		separatorPanel.setBackground(this.editorPopup.getContentBackground());
		separatorPanel.setMinimumSize(new Dimension(0, 1));
		separatorPanel.setPreferredSize(new Dimension(0, 1));
		return separatorPanel;
	}

	private JPanel separator() {
		JPanel separator = new JPanel();
		separator.setBackground(new Color(204, 204, 204));
		separator.setMinimumSize(new Dimension(0, 1));
		separator.setPreferredSize(new Dimension(0, 1));
		return separator;
	}

	private JPanel actionPanel(final String name, String hotkey) {
		JPanel actionPanel = new JPanel();
		actionPanel.setBackground(this.editorPopup.getContentBackground());
		actionPanel.setMinimumSize(new Dimension(0, 14));
		actionPanel.setPreferredSize(new Dimension(100, 14));
		HorizontalLayout horizontalLayout = new HorizontalLayout();
		horizontalLayout.setGap(1);
		actionPanel.setLayout(horizontalLayout);
		actionPanel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent evt) {
				DarkTextField.this.epanelMouseEntered(evt);
			}

			@Override
			public void mouseExited(MouseEvent evt) {
				DarkTextField.this.epanelMouseExited(evt);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				DarkTextField.this.epanelMouseExited(e);
				DarkTextField.this.editorPopup.dispose();
				switch (name) {
				case "Cut": {
					DarkTextField.this.textField.requestFocusInWindow();
					DarkTextField.this.textField.cut();
					break;
				}
				case "Copy": {
					DarkTextField.this.textField.requestFocusInWindow();
					DarkTextField.this.textField.copy();
					break;
				}
				case "Paste": {
					DarkTextField.this.textFieldFocusGained(null);
					DarkTextField.this.textField.paste();
					break;
				}
				case "Select All": {
					DarkTextField.this.textField.requestFocusInWindow();
					DarkTextField.this.textField.selectAll();
				}
				}
			}
		});
		WebLabel cutLabel = new WebLabel();
		cutLabel.setForeground(this.label.getForeground());
		cutLabel.setText(name);
		cutLabel.setFont(this.label.getFont());
		cutLabel.setMargin(new Insets(0, 2, 0, 0));
		cutLabel.setMaximumSize(new Dimension(60, 14));
		cutLabel.setMinimumSize(new Dimension(60, 14));
		cutLabel.setPreferredSize(new Dimension(60, 14));
		WebHotkeyLabel cutHotkey = new WebHotkeyLabel();
		cutHotkey.setText(hotkey);
		cutHotkey.setFont(new Font("Tahoma", 1, 10));
		cutHotkey.setForeground(new Color(0x343434));
		actionPanel.add(cutLabel);
		actionPanel.add(cutHotkey);
		return actionPanel;
	}

	private void textFieldPanelMouseEntered(MouseEvent evt) {
		if (!this.isEnabled()) {
			return;
		}
		if (this.entered) {
			return;
		}
		this.entered = true;
		this.background.setDark(true);
		this.textField.setBackground(new Color(0x181817));
		this.frame.repaint();
	}

	private void textFieldPanelMouseExited(MouseEvent evt) {
		if (!this.isEnabled()) {
			return;
		}
		if (this.show) {
			this.show = false;
		}
		if (this.focused) {
			return;
		}
		this.entered = false;
		this.background.setDark(false);
		this.textField.setBackground(new Color(47, 50, 55));
		this.frame.repaint();
	}

	private void textFieldMouseEntered(MouseEvent evt) {
		if (!this.isEnabled()) {
			return;
		}
		if (this.entered) {
			return;
		}
		this.entered = true;
		this.background.setDark(true);
		this.textField.setBackground(new Color(0x181817));
		this.frame.repaint();
	}

	private void textFieldMouseExited(MouseEvent evt) {
		if (!this.isEnabled()) {
			return;
		}
		if (this.show) {
			this.show = false;
		}
		if (this.focused) {
			return;
		}
		this.entered = false;
		this.background.setDark(false);
		this.textField.setBackground(new Color(47, 50, 55));
		this.frame.repaint();
	}

	private void textFieldKeyTyped(KeyEvent evt) {
		this.frame.repaint();
	}

	private void textFieldMouseReleased(MouseEvent evt) {
		if (!this.isEnabled()) {
			return;
		}
		if (!this.isCanRightClick()) {
			return;
		}
		if (evt.getButton() == 3) {
			this.show = true;
			this.editorPopup.show(evt.getLocationOnScreen());
			if (this.undoManager.canUndo()) {
				this.undoLabel.setForeground(this.label.getForeground());
				this.undoHotkey.setEnabled(true);
			} else {
				this.undoLabel.setForeground(new Color(153, 153, 153));
				this.undoHotkey.setEnabled(false);
			}
		}
	}

	private void textFieldFocusGained(FocusEvent evt) {
		if (!this.isEditable()) {
			return;
		}
		if (!this.isEnabled()) {
			return;
		}
		if (!this.inputPromp.equals("") && this.textField.getText().equals(this.inputPromp)) {
			this.text = "";
			this.textField.setText("");
			this.textField.setForeground(this.label.getForeground());
			this.textField.setHorizontalAlignment(this.position);
		}
		this.focused = true;
		this.entered = true;
		this.frame.repaint();
	}

	private void textFieldFocusLost(FocusEvent evt) {
		if (!this.isEnabled()) {
			return;
		}
		if (this.show) {
			this.show = false;
			return;
		}
		if (!this.inputPromp.equals("") && this.textField.getText().equals("")) {
			this.textField.setText(this.inputPromp);
			this.textField.setForeground(this.inputPrompColor);
			this.textField.setHorizontalAlignment(this.inputPrompPosition);
		}
		this.focused = false;
		this.entered = false;
		this.background.setDark(false);
		this.textField.setBackground(new Color(47, 50, 55));
		this.frame.repaint();
	}

	public void setCanRightClick(boolean canRightClick) {
		this.canRightClick = canRightClick;
	}

	public boolean isCanRightClick() {
		return this.canRightClick;
	}

	public String getText() {
		if (this.textField.getText().equals(this.inputPromp)) {
			return "";
		}
		this.text = this.textField.getText();
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
		this.textField.setText(this.text);
		if (!this.inputPromp.equals("") && this.textField.getText().equals("")) {
			this.textField.setText(this.inputPromp);
			this.textField.setForeground(this.inputPrompColor);
			this.textField.setHorizontalAlignment(this.inputPrompPosition);
		}
	}

	public String getInputPromp() {
		return this.inputPromp;
	}

	public void setInputPromp(String inputPromp) {
		if (!inputPromp.equals("")) {
			if (this.textField.getText().equals("") || this.textField.getText().equals(this.inputPromp)) {
				this.textField.setText(inputPromp);
				this.textField.setForeground(this.inputPrompColor);
				this.textField.setHorizontalAlignment(this.inputPrompPosition);
			}
			this.inputPromp = inputPromp;
		}
	}

	public Color getInputPrompColor() {
		return this.inputPrompColor;
	}

	public void setInputPrompColor(Color inputPrompColor) {
		this.inputPrompColor = inputPrompColor;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JFrame getFrame() {
		return this.frame;
	}

	public float getAlpha() {
		return this.alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
		this.repaint();
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		this.setAlpha(enabled ? 1.0f : 0.1f);
		this.background.setEnabled(enabled);
		this.textField.setEnabled(enabled);
	}
}