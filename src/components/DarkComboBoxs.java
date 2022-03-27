package components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.jdesktop.swingx.VerticalLayout;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

import com.alee.extended.window.WebPopOver;
import com.alee.laf.label.WebLabel;

import dev.Main;

public class DarkComboBoxs extends JPanel {

	private JFrame frame;
	private int selectedIndex;
	private List<String> items = new ArrayList<String>();
	private List<JLabel> labels = new ArrayList<JLabel>();
	private String selectedItem;
	private JLabel selectedLabel;
	private int highestWidth = 0;
	private int in;
	private boolean selected = false;
	private boolean focusLosted = false;
	float alpha = 1.0f;
	private GroupLayout popOverLayout;
	private WebLabel comboBoxIcon;
	private WebLabel comboBoxLabel;
	private BackgroundPanel background;
	private JPanel popTopPanel;
	private JPanel popPanel;
	private JScrollPane popScrollPane;
	private WebPopOver popOver;
	private DefaultComboBoxModel comboBoxModel;
	private JComboBox<String> comboBox;
	private WebLabel label;
	ImageIcon combo;
	ImageIcon uncombo;

	public DarkComboBoxs() {
		this((JFrame) null);
	}

	public DarkComboBoxs(JFrame frame) {
		this(frame, "Default");
	}

	public DarkComboBoxs(JFrame frame, String Default) {
		this(frame, Collections.singletonList(Default));
	}

	public DarkComboBoxs(final JFrame frame, List<String> Default) {
		this.setFrame(frame);
		this.initComponents();
		this.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent evt) {
				DarkComboBoxs.this.componentResize(evt);
			}
		});
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent evt) {
				if (!DarkComboBoxs.this.isEnabled()) {
					return;
				}
				DarkComboBoxs.this.background.setDark(true);
				frame.repaint();
			}

			@Override
			public void mouseExited(MouseEvent evt) {
				if (!DarkComboBoxs.this.isEnabled()) {
					return;
				}
				if (DarkComboBoxs.this.selected) {
					return;
				}
				DarkComboBoxs.this.background.setDark(false);
				frame.repaint();
			}
		});
		for (String item : Default) {
			this.addList(item);
		}
		new Thread(() -> {
			try {
				Thread.sleep(500L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			frame.repaint();
		}).start();
	}

	private void componentResize(ComponentEvent evt) {
		this.remove(this.comboBoxLabel);
		this.add((Component) this.comboBoxLabel, new AbsoluteConstraints(0, 0, this.getWidth() - 23, this.getHeight()));
		this.remove(this.comboBoxIcon);
		this.add((Component) this.comboBoxIcon, new AbsoluteConstraints(this.getWidth() - 22, 0, 22, this.getHeight()));
		this.background.setWidthAndHeight(this.getWidth(), this.getHeight());
		this.remove(this.background);
		this.add((Component) this.background, new AbsoluteConstraints(0, 0, this.getWidth(), this.getHeight()));
		this.frame.repaint();
	}

	private void initComponents() {
		this.label = new WebLabel();
		this.popOver = new WebPopOver();
		this.popScrollPane = new JScrollPane();
		this.popPanel = new JPanel();
		this.popTopPanel = new JPanel();
		this.comboBoxLabel = new WebLabel();
		this.comboBoxIcon = new WebLabel();
		this.background = new BackgroundPanel();
		this.label.setBackground(new Color(43, 46, 50));
		this.label.setForeground(new Color(247, 247, 247));
		this.label.setText("Label");
		this.label.setFont(Main.loadFont("/fonts/OpenSans-SemiBold.ttf", 11.0f, 0));
		this.popOver.setAlwaysOnTop(true);
		this.popOver.setAutoRequestFocus(false);
		this.popOver.setBorderColor(new Color(48, 50, 55));
		this.popOver.setCloseOnFocusLoss(true);
		this.popOver.setContentBackground(new Color(48, 50, 55));
		this.popOver.setMovable(false);
		this.popOver.setResizable(false);
		this.popOver.setRound(1);
		this.popOver.setShadeTransparency(1.0f);
		this.popOver.setShadeWidth(5);
		this.popOver.setTransparency(1.0f);
		this.popOver.addWindowFocusListener(new WindowFocusListener() {

			@Override
			public void windowGainedFocus(WindowEvent evt) {
			}

			@Override
			public void windowLostFocus(WindowEvent evt) {
				DarkComboBoxs.this.popOverWindowLostFocus(evt);
			}
		});
		this.popScrollPane.setBorder(null);
		this.popScrollPane.setForeground(new Color(48, 50, 55));
		this.popScrollPane.setHorizontalScrollBarPolicy(31);
		this.popScrollPane.setOpaque(false);
		this.popScrollPane.addMouseWheelListener(this::popScrollPaneMouseWheelMoved);
		this.popPanel.setBackground(new Color(48, 50, 55));
		this.popPanel.setLayout(new VerticalLayout());
		this.popTopPanel.setMaximumSize(new Dimension(Short.MAX_VALUE, 3));
		this.popTopPanel.setMinimumSize(new Dimension(64, 3));
		this.popTopPanel.setOpaque(false);
		this.popTopPanel.setPreferredSize(new Dimension(64, 3));
		GroupLayout popTopPanelLayout = new GroupLayout(this.popTopPanel);
		this.popTopPanel.setLayout(popTopPanelLayout);
		popTopPanelLayout.setHorizontalGroup(
				popTopPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 65, Short.MAX_VALUE));
		popTopPanelLayout.setVerticalGroup(
				popTopPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 3, Short.MAX_VALUE));
		this.popPanel.add(this.popTopPanel);
		this.popScrollPane.setViewportView(this.popPanel);
		Color dark = new Color(2829618);
		this.popScrollPane.getVerticalScrollBar().setUI(new VScrollBarUI().setSetX(true));
		this.popScrollPane.getVerticalScrollBar().setBackground(dark);
		this.popScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(5, 0));
		this.popScrollPane.getHorizontalScrollBar().setUI(new HScrollBarUI());
		this.popScrollPane.getHorizontalScrollBar().setBackground(dark);
		this.popScrollPane.setBackground(dark);
		this.popScrollPane.getViewport().setBackground(dark);
		GroupLayout popOverLayout = new GroupLayout(this.popOver.getContentPane());
		this.popOver.getContentPane().setLayout(popOverLayout);
		popOverLayout.setHorizontalGroup(popOverLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(this.popScrollPane, -1, 65, Short.MAX_VALUE));
		popOverLayout.setVerticalGroup(popOverLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(popOverLayout.createSequentialGroup()
						.addComponent(this.popScrollPane, -1, 87, Short.MAX_VALUE).addGap(0, 0, 0)));
		this.popOverLayout = popOverLayout;
		this.setCursor(new Cursor(12));
		this.setPreferredSize(new Dimension(85, 24));
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent evt) {
				DarkComboBoxs.this.comboBoxPanelMouseReleased(evt);
			}
		});
		this.setLayout(new AbsoluteLayout());
		this.comboBoxLabel.setForeground(this.label.getForeground());
		this.comboBoxLabel.setText(" ");
		this.comboBoxLabel.setFont(this.label.getFont());
		this.comboBoxLabel.setHorizontalTextPosition(10);
		this.comboBoxLabel.setMargin(new Insets(0, 9, 0, 0));
		this.add((Component) this.comboBoxLabel, new AbsoluteConstraints(0, 0, 62, 24));
		this.combo = new ImageIcon(this.getClass().getResource("/images/combo.png"));
		this.uncombo = new ImageIcon(this.getClass().getResource("/images/uncombo.png"));
		this.comboBoxIcon.setForeground(this.label.getForeground());
		this.comboBoxIcon.setHorizontalAlignment(11);
		this.comboBoxIcon.setIcon(this.combo);
		this.comboBoxIcon.setFont(this.label.getFont());
		this.comboBoxIcon.setMargin(new Insets(0, 0, 0, 7));
		this.background.setBgWidth(85);
		this.background.setBgHeight(24);
		this.add((Component) this.comboBoxIcon, new AbsoluteConstraints(63, 0, 22, 24));
		this.add((Component) this.background, new AbsoluteConstraints(0, 0, 85, 24));
		this.comboBox = new JComboBox();
		this.comboBoxModel = new DefaultComboBoxModel<String>(new String[0]);
		this.comboBox.setModel(this.comboBoxModel);
	}

	private void popOverWindowLostFocus(WindowEvent evt) {
		this.popOver.dispose();
		this.comboBoxIcon.setIcon(this.combo);
		this.background.setDark(false);
		this.frame.repaint();
		this.focusLosted = true;
		new Thread(() -> {
			try {
				Thread.sleep(300L);
				this.focusLosted = false;
			} catch (InterruptedException ex) {
				Logger.getLogger(DarkComboBoxs.class.getName()).log(Level.SEVERE, null, ex);
			}
		}).start();
		this.selected = this.popOver.isShowing();
	}

	private void popScrollPaneMouseWheelMoved(MouseWheelEvent evt) {
		int amount = evt.getWheelRotation() > 0 ? evt.getScrollAmount() : -evt.getScrollAmount();
		this.popScrollPane.getVerticalScrollBar()
				.setValue(this.popScrollPane.getVerticalScrollBar().getValue() + (amount *= 2));
		evt.consume();
	}

	private void comboBoxPanelMouseReleased(MouseEvent evt) {
		if (!this.isEnabled()) {
			return;
		}
		if (this.focusLosted) {
			if (this.popOver.isShowing()) {
				this.popOver.dispose();
			}
			this.focusLosted = false;
		} else {
			this.selected = true;
			this.popOver.show(this.frame);
			this.popOver.setLocation(((JComponent) evt.getSource()).getLocationOnScreen().x - 15 + 10 + 5,
					((JComponent) evt.getSource()).getLocationOnScreen().y - 5 + 12 + 5);
			this.comboBoxIcon.setIcon(this.uncombo);
			this.background.setDark(true);
			this.frame.repaint();
		}
		this.selected = this.popOver.isShowing();
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JFrame getFrame() {
		return this.frame;
	}

	public int getSelectedIndex() {
		return this.selectedIndex;
	}

	public String getSelectedItem() {
		return this.selectedItem;
	}

	public void setSelectedIndex(int selectedIndex) {
		this.changeItem(selectedIndex);
		if (this.selectedLabel != null) {
			this.selectedLabel.setIcon(new ImageIcon(this.getClass().getResource("/images/unclick.png")));
		}
		this.selectedLabel = this.labels.get(selectedIndex);
		this.selectedLabel.setIcon(new ImageIcon(this.getClass().getResource("/images/click.png")));
		this.popOver.dispose();
		this.selected = false;
	}

	private void changeItem(int i) {
		this.selectedIndex = i;
		if (!this.items.isEmpty()) {
			this.selectedItem = this.items.get(this.selectedIndex);
			this.comboBox.setSelectedIndex(this.selectedIndex);
		}
		this.comboBoxIcon.setIcon(this.combo);
		this.background.setDark(false);
		this.comboBoxLabel.setText(this.selectedItem);
		if (this.frame != null) {
			this.frame.repaint();
		}
	}

	public void addList(String item) {
		final int index = this.in++;
		this.items.add(item);
		this.comboBoxModel.addElement(item);
		this.comboBox.setModel(this.comboBoxModel);
		final JLabel labelList = new JLabel();
		labelList.setBackground(new Color(48, 50, 55));
		labelList.setFont(this.label.getFont());
		labelList.setForeground(this.label.getForeground());
		labelList.setIcon(new ImageIcon(this.getClass().getResource("/images/unclick.png")));
		labelList.setText(item);
		labelList.setPreferredSize(new Dimension(0, 21));
		labelList.setAlignmentX(0.5f);
		labelList.setOpaque(true);
		labelList.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent evt) {
				((JLabel) evt.getComponent()).setBackground(new Color(2500653));
			}

			@Override
			public void mouseExited(MouseEvent evt) {
				((JLabel) evt.getComponent()).setBackground(new Color(48, 50, 55));
			}

			@Override
			public void mouseReleased(MouseEvent evt) {
				DarkComboBoxs.this.changeItem(index);
				if (DarkComboBoxs.this.selectedLabel != null) {
					DarkComboBoxs.this.selectedLabel
							.setIcon(new ImageIcon(this.getClass().getResource("/images/unclick.png")));
				}
				DarkComboBoxs.this.selectedLabel = labelList;
				DarkComboBoxs.this.selectedLabel
						.setIcon(new ImageIcon(this.getClass().getResource("/images/click.png")));
				DarkComboBoxs.this.popOver.dispose();
				DarkComboBoxs.this.selected = false;
			}
		});
		if (index == 0) {
			this.changeItem(index);
			if (this.selectedLabel != null) {
				this.selectedLabel.setIcon(new ImageIcon(this.getClass().getResource("/images/unclick.png")));
			}
			this.selectedLabel = labelList;
			this.selectedLabel.setIcon(new ImageIcon(this.getClass().getResource("/images/click.png")));
			this.selectedLabel.setBackground(new Color(2500653));
		}
		int w = 0;
		int h = 0;
		if (this.highestWidth < item.length()) {
			this.highestWidth = item.length();
			w = this.highestWidth * 7 + 18;
			if (w > 65) {
				this.popOverLayout
						.setHorizontalGroup(this.popOverLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(this.popScrollPane, w, w, Short.MAX_VALUE));
			}
		}
		if (index < 5) {
			h = index * 21 + 16;
			this.popOverLayout.setVerticalGroup(this.popOverLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
					.addGroup(this.popOverLayout.createSequentialGroup()
							.addComponent(this.popScrollPane, -1, h, Short.MAX_VALUE).addGap(0, 0, 0)));
		}
		this.popOver.setMinimumSize(new Dimension(w + 20, h + 22));
		this.labels.add(labelList);
		this.popPanel.add(labelList);
	}

	public void addItemListener(ItemListener aListener) {
		this.comboBox.addItemListener(aListener);
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		this.setAlpha(enabled ? 1.0f : 0.1f);
		Color fg = enabled ? this.label.getForeground() : new Color(109, 109, 109);
		this.comboBoxLabel.setForeground(fg);
		this.background.setEnabled(enabled);
	}

	public float getAlpha() {
		return this.alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
		this.repaint();
	}
}