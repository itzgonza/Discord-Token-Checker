package components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.undo.UndoManager;

import org.jdesktop.swingx.HorizontalLayout;
import org.jdesktop.swingx.VerticalLayout;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

import com.alee.extended.label.WebHotkeyLabel;
import com.alee.extended.panel.WebOverlay;
import com.alee.extended.window.WebPopOver;
import com.alee.laf.label.WebLabel;
import com.alee.laf.text.WebTextArea;
import com.alee.utils.GraphicsUtils;
import com.alee.utils.SwingUtils;

import dev.Main;

public class DarkTextArea extends WebOverlay {

	private DarkTextArea darkTextArea = this;
	private boolean canResize = true;
	private JComponent resize;
	private boolean canRightClick = true;
	private String text = "Text...";
	private String inputPromp = "";
	private Color inputPrompColor = new Color(192, 192, 192);
	private boolean focused;
	private boolean entered;
	private boolean show;
	private JFrame frame;
	float alpha = 1.0f;
	private BackgroundPanel background;
	private JScrollPane scrollPane;
	private WebTextArea textArea;
	private JPanel textAreaPanel;
	private Color color;
	private WebPopOver editorPopup;
	private WebHotkeyLabel undoHotkey;
	private WebLabel undoLabel;
	private UndoManager undoManager;
	private WebLabel label;

	public DarkTextArea() {
		this((JFrame) null);
	}

	public DarkTextArea(JFrame frame) {
		this(frame, true);
	}

	public DarkTextArea(JFrame frame, boolean canResize) {
		this.canResize = canResize;
		this.initComponents();
		this.setFrame(frame);
		this.editerPopup();
		this.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent evt) {
				DarkTextArea.this.textAreaPanelComponentResized(evt);
			}
		});
		this.setName("DarkTextArea");
	}

	private void initComponents() {
		this.color = new Color(47, 50, 55);
		this.label = new WebLabel();
		this.label.setBackground(new Color(43, 46, 50));
		this.label.setForeground(new Color(247, 247, 247));
		this.label.setText("Label");
		this.label.setFont(Main.loadFont("/fonts/OpenSans-SemiBold.ttf", 12.0f, 0));
		this.textAreaPanel = new JPanel();
		this.scrollPane = new JScrollPane();
		this.textArea = new WebTextArea();
		this.background = new BackgroundPanel();
		this.textAreaPanel.setLayout(new AbsoluteLayout());
		this.textAreaPanel.setBackground(new Color(51, 51, 51));
		this.scrollPane.setBackground(new Color(51, 51, 51));
		this.scrollPane.setBorder(BorderFactory.createCompoundBorder());
		this.scrollPane.setHorizontalScrollBarPolicy(31);
		Color DARK = new Color(0x1E1E1E);
		this.scrollPane.getVerticalScrollBar().setUI(new VScrollBarUI().setColor(Color.WHITE));
		this.scrollPane.getVerticalScrollBar().setBackground(new Color(0x2E2E2E));
		this.scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(5, 0));
		this.scrollPane.getHorizontalScrollBar().setUI(new HScrollBarUI().setColor(Color.WHITE));
		this.scrollPane.getHorizontalScrollBar().setBackground(DARK);
		this.scrollPane.setBackground(DARK);
		this.scrollPane.getViewport().setBackground(DARK);
		this.textArea.setText(this.text);
		this.textArea.setBackground(new Color(47, 50, 55));
		this.textArea.setSelectedTextColor(new Color(255, 255, 255));
		this.textArea.setSelectionColor(new Color(0, 150, 201));
		this.textArea.setBorder(BorderFactory.createCompoundBorder());
		this.textArea.setColumns(20);
		this.textArea.setForeground(new Color(255, 255, 255));
		this.textArea.setLineWrap(true);
		this.textArea.setRows(1);
		this.textArea.setTabSize(1);
		this.textArea.setFont(Main.loadFont("/fonts/OpenSans-Regular.ttf", 11.0f, 0));
		this.textArea.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent evt) {
				DarkTextArea.this.textAreaFocusGained(evt);
			}

			@Override
			public void focusLost(FocusEvent evt) {
				DarkTextArea.this.textAreaFocusLost(evt);
			}
		});
		this.textArea.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent evt) {
				DarkTextArea.this.textAreaMouseEntered(evt);
			}

			@Override
			public void mouseExited(MouseEvent evt) {
				DarkTextArea.this.textAreaMouseExited(evt);
			}

			@Override
			public void mouseReleased(MouseEvent evt) {
				DarkTextArea.this.textAreaMouseReleased(evt);
			}
		});
		this.scrollPane.setViewportView(this.textArea);
		this.background.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent evt) {
				DarkTextArea.this.textArea.requestFocusInWindow();
			}
		});
		this.textAreaPanel.add((Component) this.scrollPane, new AbsoluteConstraints(5, 5, 160, 120));
		this.textAreaPanel.add((Component) this.background, new AbsoluteConstraints(0, 0, 170, 130));
		this.setBackground(this.color);
		this.setComponent(this.textAreaPanel);
		if (this.canResize) {
			this.resize = new JComponent() {

				@Override
				protected void paintComponent(Graphics g) {
					Graphics2D g2d = (Graphics2D) g;
					Object aa = GraphicsUtils.setupAntialias(g2d);
					g.setColor(DarkTextArea.this.color);
					g.fillRect(0, 0, this.getWidth(), this.getHeight());
					g.setColor(Color.WHITE);
					g2d.setPaint(this.isEnabled() ? Color.WHITE : Color.WHITE);
					if (this.getComponentOrientation().isLeftToRight()) {
						g2d.drawLine(6, 16, 16, 6);
						g2d.drawLine(10, 16, 16, 10);
						g2d.drawLine(14, 16, 16, 14);
					} else {
						g2d.drawLine(11, 10, 1, 0);
						g2d.drawLine(7, 10, 1, 4);
						g2d.drawLine(3, 10, 1, 8);
					}
					GraphicsUtils.restoreAntialias(g2d, aa);
				}

				@Override
				public Dimension getPreferredSize() {
					return new Dimension(19, 19);
				}
			};
			this.resize.setCursor(Cursor.getPredefinedCursor(5));
			this.resize.addPropertyChangeListener("componentOrientation", evt -> this.resize.setCursor(
					Cursor.getPredefinedCursor(this.resize.getComponentOrientation().isLeftToRight() ? 5 : 4)));
			MouseAdapter mouseAdapter = new MouseAdapter() {
				private Dimension startDim = null;
				private Point start = null;

				@Override
				public void mousePressed(MouseEvent e) {
					if (DarkTextArea.this.resize.isEnabled() && SwingUtilities.isLeftMouseButton(e)) {
						this.startDim = DarkTextArea.this.getSize();
						this.start = MouseInfo.getPointerInfo().getLocation();
					}
				}

				@Override
				public void mouseDragged(MouseEvent e) {
					if (this.start != null) {
						boolean ltr = DarkTextArea.this.resize.getComponentOrientation().isLeftToRight();
						Point p = MouseInfo.getPointerInfo().getLocation();
						Dimension ps = DarkTextArea.this.getLayout().minimumLayoutSize(DarkTextArea.this.darkTextArea);
						Dimension newPs = new Dimension(
								this.startDim.width + (ltr ? p.x - this.start.x : this.start.x - p.x),
								this.startDim.height + (p.y - this.start.y));
						DarkTextArea.this.setSize(SwingUtils.max(ps, newPs));
					}
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					if (this.start != null) {
						this.startDim = null;
						this.start = null;
					}
				}
			};
			this.resize.addMouseListener(mouseAdapter);
			this.resize.addMouseMotionListener(mouseAdapter);
			this.addOverlay(this.resize, 11, 3);
		}
		this.setOverlayMargin(5);
	}

	private void textAreaPanelComponentResized(ComponentEvent evt) {
		this.textAreaPanel.remove(this.scrollPane);
		this.textAreaPanel.add((Component) this.scrollPane,
				new AbsoluteConstraints(5, 5, this.getWidth() - 10, this.getHeight() - 10));
		this.background.setWidthAndHeight(this.getWidth(), this.getHeight());
		this.textAreaPanel.remove(this.background);
		this.textAreaPanel.add((Component) this.background,
				new AbsoluteConstraints(0, 0, this.getWidth(), this.getHeight()));
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
		this.textArea.getDocument().addUndoableEditListener(this.undoManager);
		JPanel undoPanel = new JPanel();
		undoPanel.setBackground(this.editorPopup.getContentBackground());
		undoPanel.setMinimumSize(new Dimension(0, 14));
		undoPanel.setPreferredSize(new Dimension(100, 14));
		undoPanel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent evt) {
				DarkTextArea.this.epanelMouseEntered(evt);
			}

			@Override
			public void mouseExited(MouseEvent evt) {
				DarkTextArea.this.epanelMouseExited(evt);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (DarkTextArea.this.undoManager.canUndo()) {
					DarkTextArea.this.undoManager.undo();
				}
				DarkTextArea.this.epanelMouseExited(e);
				DarkTextArea.this.editorPopup.dispose();
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
				DarkTextArea.this.epanelMouseEntered(evt);
			}

			@Override
			public void mouseExited(MouseEvent evt) {
				DarkTextArea.this.epanelMouseExited(evt);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				DarkTextArea.this.epanelMouseExited(e);
				DarkTextArea.this.editorPopup.dispose();
				switch (name) {
				case "Cut": {
					DarkTextArea.this.textArea.requestFocusInWindow();
					DarkTextArea.this.textArea.cut();
					break;
				}
				case "Copy": {
					DarkTextArea.this.textArea.requestFocusInWindow();
					DarkTextArea.this.textArea.copy();
					break;
				}
				case "Paste": {
					DarkTextArea.this.textAreaFocusGained(null);
					DarkTextArea.this.textArea.paste();
					break;
				}
				case "Select All": {
					DarkTextArea.this.textArea.requestFocusInWindow();
					DarkTextArea.this.textArea.selectAll();
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

	public void setCanRightClick(boolean canRightClick) {
		this.canRightClick = canRightClick;
	}

	public boolean isCanRightClick() {
		return this.canRightClick;
	}

	private void textAreaMouseEntered(MouseEvent evt) {
		if (!this.isEnabled()) {
			return;
		}
		if (this.entered) {
			return;
		}
		this.entered = true;
		this.background.setDark(true);
		this.color = new Color(3224633);
		this.textArea.setBackground(this.color);
		this.frame.repaint();
	}

	private void textAreaMouseExited(MouseEvent evt) {
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
		this.color = new Color(47, 50, 55);
		this.textArea.setBackground(this.color);
		this.frame.repaint();
	}

	private void textAreaFocusGained(FocusEvent evt) {
		if (!this.inputPromp.equals("") && this.textArea.getText().equals(this.inputPromp)) {
			this.text = "";
			this.textArea.setText("");
			this.textArea.setForeground(new Color(255, 255, 255));
		}
		this.focused = true;
		this.entered = true;
	}

	private void textAreaFocusLost(FocusEvent evt) {
		if (!this.isEnabled()) {
			return;
		}
		if (this.show) {
			this.show = false;
			return;
		}
		if (!this.inputPromp.equals("") && this.textArea.getText().equals("")) {
			this.textArea.setText(this.inputPromp);
			this.textArea.setForeground(this.inputPrompColor);
		}
		this.focused = false;
		this.entered = false;
		this.background.setDark(false);
		this.color = new Color(47, 50, 55);
		this.textArea.setBackground(this.color);
		this.frame.repaint();
	}

	private void textAreaMouseReleased(MouseEvent evt) {
		if (!this.isEnabled()) {
			return;
		}
		if (!this.canRightClick) {
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
			this.background.setDark(true);
			this.color = new Color(3224633);
			this.textArea.setBackground(this.color);
			this.frame.repaint();
		}
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JFrame getFrame() {
		return this.frame;
	}

	public String getText() {
		if (this.textArea.getText().equals(this.inputPromp)) {
			return "";
		}
		this.text = this.textArea.getText();
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
		this.textArea.setText(this.text);
		if (!this.inputPromp.equals("") && this.textArea.getText().equals("")) {
			this.textArea.setText(this.inputPromp);
			this.textArea.setForeground(this.inputPrompColor);
		}
	}

	public String getInputPromp() {
		return this.inputPromp;
	}

	public void setInputPromp(String inputPromp) {
		this.inputPromp = inputPromp;
	}

	public Color getInputPrompColor() {
		return this.inputPrompColor;
	}

	public void setInputPrompColor(Color inputPrompColor) {
		this.inputPrompColor = inputPrompColor;
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
		this.textArea.setEnabled(enabled);
		Color bg = enabled ? new Color(3093047) : new Color(3422012);
		this.textArea.setBackground(bg);
		if (this.resize != null) {
			this.resize.setBackground(bg);
		}
	}
}