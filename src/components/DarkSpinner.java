package components;

import java.awt.Color;
import java.awt.Component;
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
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

import com.alee.laf.button.WebButton;
import com.alee.laf.text.WebTextField;

import dev.Main;

public class DarkSpinner extends JPanel {

	private JFrame frame;
	boolean focused;
	boolean entered;
	boolean buttoned;
	private boolean buttonUpPressed;
	private boolean buttonDownPressed;
	private boolean keyTyped;
	private Thread buttonPressedThread;
	int min = Integer.MIN_VALUE;
	int max = Integer.MAX_VALUE;
	int valve = 0;
	int next = 1;
	float alpha = 1.0f;
	private BackgroundPanel background;
	private WebButton buttonDown;
	private WebButton buttonUp;
	private JLabel label;
	private WebTextField textField;

	public DarkSpinner() {
		this((JFrame) null);
	}

	public DarkSpinner(JFrame frame) {
		this.initComponents();
		this.setFrame(frame);
		this.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent evt) {
				DarkSpinner.this.panelComponentResized(evt);
			}
		});
		this.setValve(0);
		this.setMin(0);
		this.setMax(Integer.MAX_VALUE);
	}

	private void initComponents() {
		this.label = new JLabel();
		this.buttonUp = new WebButton();
		this.buttonDown = new WebButton();
		this.textField = new WebTextField();
		this.background = new BackgroundPanel();
		this.setBackground(new Color(54, 57, 63));
		this.setMinimumSize(new Dimension(85, 30));
		this.setPreferredSize(new Dimension(85, 30));
		this.setLayout(new AbsoluteLayout());
		this.label.setBackground(new Color(43, 46, 50));
		this.label.setForeground(new Color(247, 247, 247));
		this.label.setText("Label");
		this.label.setFont(Main.loadFont("/fonts/OpenSans-SemiBold.ttf", 12.0f, 0));
		this.buttonUp.setForeground(new Color(51, 51, 51));
		this.buttonUp.setText("?");
		this.buttonUp.setFont(new Font("Tahoma", 0, 9));
		this.buttonUp.setMargin(new Insets(-4, -5, -2, -5));
		this.buttonUp.setDrawFocus(false);
		this.buttonUp.setShadeColor(new Color(64, 64, 64));
		this.buttonUp.setShineColor(new Color(64, 64, 64));
		this.buttonUp.setTopBgColor(Color.white);
		this.buttonUp.setBottomBgColor(Color.white);
		this.add((Component) this.buttonUp, new AbsoluteConstraints(63, 3, 19, 13));
		this.buttonDown.setForeground(new Color(51, 51, 51));
		this.buttonDown.setText("?");
		this.buttonDown.setFont(new Font("Tahoma", 0, 9));
		this.buttonDown.setMargin(new Insets(-2, -5, -4, -5));
		this.buttonDown.setDrawFocus(false);
		this.buttonUp.setDrawBottom(false);
		this.buttonDown.setDrawTop(false);
		this.buttonDown.setShadeColor(new Color(64, 64, 64));
		this.buttonDown.setShineColor(new Color(64, 64, 64));
		this.buttonDown.setTopBgColor(Color.white);
		this.buttonDown.setBottomBgColor(Color.white);
		this.add((Component) this.buttonDown, new AbsoluteConstraints(63, 14, 19, 13));
		this.buttonUp.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent evt) {
				DarkSpinner.this.buttonMouseEntered(evt);
			}

			@Override
			public void mouseExited(MouseEvent evt) {
				DarkSpinner.this.buttonMouseExited(evt);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				DarkSpinner.this.buttonUpmousePressed(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				DarkSpinner.this.buttonUpmouseReleased(e);
			}
		});
		this.buttonDown.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent evt) {
				DarkSpinner.this.buttonMouseEntered(evt);
			}

			@Override
			public void mouseExited(MouseEvent evt) {
				DarkSpinner.this.buttonMouseExited(evt);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				DarkSpinner.this.buttonDownmousePressed(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				DarkSpinner.this.buttonDownmouseReleased(e);
			}
		});
		this.textField.setDrawBackground(false);
		this.textField.setDrawBorder(false);
		this.textField.setDrawFocus(false);
		this.textField.setDrawShade(false);
		this.textField.setBackground(new Color(47, 50, 55));
		this.textField.setFont(this.label.getFont());
		this.textField.setForeground(this.label.getForeground());
		this.textField.setBorder(null);
		this.textField.setCaretColor(new Color(255, 255, 255));
		this.textField.setMargin(new Insets(1, 2, 1, 1));
		this.textField.setOpaque(false);
		this.textField.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent evt) {
				DarkSpinner.this.textFieldFocusGained(evt);
			}

			@Override
			public void focusLost(FocusEvent evt) {
				DarkSpinner.this.textFieldFocusLost(evt);
			}
		});
		this.textField.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent evt) {
				DarkSpinner.this.textFieldMouseEntered(evt);
			}

			@Override
			public void mouseExited(MouseEvent evt) {
				DarkSpinner.this.textFieldMouseExited(evt);
			}
		});
		this.textField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent evt) {
				DarkSpinner.this.textFieldKeyTyped(evt);
			}
		});
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent evt) {
				DarkSpinner.this.textFieldMouseEntered(evt);
			}

			@Override
			public void mouseExited(MouseEvent evt) {
				DarkSpinner.this.textFieldMouseExited(evt);
			}
		});
		this.add((Component) this.textField, new AbsoluteConstraints(3, 5, 58, 20));
		this.background.setBackground(new Color(49, 52, 57));
		this.add((Component) this.background, new AbsoluteConstraints(0, 0, 85, 30));
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public JFrame getFrame() {
		return this.frame;
	}

	private void textFieldFocusGained(FocusEvent evt) {
		if (!this.isEnabled()) {
			return;
		}
		this.focused = true;
		this.entered = true;
		this.frame.repaint();
	}

	private void textFieldFocusLost(FocusEvent evt) {
		if (!this.isEnabled()) {
			return;
		}
		this.checkValve();
		if (this.buttoned) {
			return;
		}
		this.focused = false;
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
		this.textField.setBackground(new Color(3224633));
		this.frame.repaint();
	}

	private void textFieldMouseExited(MouseEvent evt) {
		if (!this.isEnabled()) {
			return;
		}
		if (this.focused) {
			return;
		}
		this.entered = false;
		this.background.setDark(false);
		this.textField.setBackground(new Color(47, 50, 55));
		this.frame.repaint();
	}

	private void panelComponentResized(ComponentEvent evt) {
		this.remove(this.buttonUp);
		this.add((Component) this.buttonUp,
				new AbsoluteConstraints(this.getWidth() - 22, 3, 19, this.getHeight() - 17));
		this.remove(this.buttonDown);
		this.add((Component) this.buttonDown,
				new AbsoluteConstraints(this.getWidth() - 22, this.getHeight() / 2, 19, this.getHeight() - 17));
		this.remove(this.textField);
		this.add((Component) this.textField,
				new AbsoluteConstraints(3, 5, this.getWidth() - 32 + 5, this.getHeight() - 10));
		this.background.setWidthAndHeight(this.getWidth(), this.getHeight());
		this.remove(this.background);
		this.add((Component) this.background, new AbsoluteConstraints(0, 0, this.getWidth(), this.getHeight()));
		this.frame.repaint();
	}

	private void textFieldKeyTyped(KeyEvent evt) {
		this.keyTyped = true;
		if (!String.valueOf(evt.getKeyChar()).matches("[0-9]")) {
			evt.consume();
			return;
		}
	}

	private void buttonMouseExited(MouseEvent evt) {
		if (!this.isEnabled()) {
			return;
		}
		this.buttoned = false;
		if (this.focused) {
			return;
		}
		this.entered = false;
		this.background.setDark(false);
		this.textField.setBackground(new Color(47, 50, 55));
		this.frame.repaint();
	}

	private void buttonMouseEntered(MouseEvent evt) {
		if (!this.isEnabled()) {
			return;
		}
		this.buttoned = true;
		if (this.entered) {
			return;
		}
		this.entered = true;
		this.background.setDark(true);
		this.textField.setBackground(new Color(3224633));
		this.frame.repaint();
	}

	private void buttonUpmousePressed(MouseEvent evt) {
		evt.consume();
		this.textField.requestFocusInWindow();
		if (!this.isEnabled()) {
			return;
		}
		this.checkValve();
		this.buttoned = true;
		this.buttonUpPressed = true;
		AtomicInteger valve = new AtomicInteger(this.valve);
		this.textField.requestFocusInWindow();
		this.buttonUp.setSelected(true);
		if (valve.get() + this.next > this.max) {
			this.setValve(valve.get());
			return;
		}
		this.setValve(valve.addAndGet(this.next));
		if (this.buttonPressedThread != null) {
			this.buttonPressedThread.suspend();
		}
		this.buttonPressedThread = new Thread(() -> {
			try {
				try {
					Thread.sleep(600L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				while (this.buttonUpPressed) {
					try {
						Thread.sleep(70L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (!this.buttonUpPressed)
						continue;
					if (valve.get() + this.next > this.max) {
						return;
					}
					this.setValve(valve.addAndGet(this.next));
				}
			} catch (Exception wtf) {
				wtf.printStackTrace();
			}
		});
		this.buttonPressedThread.start();
	}

	private void buttonUpmouseReleased(MouseEvent e) {
		if (!this.isEnabled()) {
			return;
		}
		this.buttonUp.setSelected(false);
		this.buttonUpPressed = false;
		if (this.buttonPressedThread != null) {
			this.buttonPressedThread.suspend();
		}
	}

	private void buttonDownmousePressed(MouseEvent evt) {
		evt.consume();
		this.textField.requestFocusInWindow();
		if (!this.isEnabled()) {
			return;
		}
		this.checkValve();
		this.buttoned = true;
		this.buttonDownPressed = true;
		AtomicInteger valve = new AtomicInteger(this.valve);
		this.textField.requestFocusInWindow();
		this.buttonDown.setSelected(true);
		if (valve.get() - this.next < this.min) {
			this.setValve(valve.get());
			return;
		}
		this.setValve(valve.addAndGet(-this.next));
		if (this.buttonPressedThread != null) {
			this.buttonPressedThread.suspend();
		}
		this.buttonPressedThread = new Thread(() -> {
			try {
				try {
					Thread.sleep(600L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				while (this.buttonDownPressed) {
					try {
						Thread.sleep(70L);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (!this.buttonDownPressed)
						continue;
					if (valve.get() - this.next < this.min) {
						return;
					}
					this.setValve(valve.addAndGet(-this.next));
				}
			} catch (Exception wtf) {
				wtf.printStackTrace();
			}
		});
		this.buttonPressedThread.start();
	}

	private void buttonDownmouseReleased(MouseEvent e) {
		if (!this.isEnabled()) {
			return;
		}
		this.buttonDown.setSelected(false);
		this.buttonDownPressed = false;
		if (this.buttonPressedThread != null) {
			this.buttonPressedThread.suspend();
		}
	}

	private void checkValve() {
		if (!this.keyTyped) {
			return;
		}
		this.keyTyped = false;
		try {
			this.valve = Integer.parseInt(this.textField.getText());
		} catch (Exception exception) {
			// empty catch block
		}
		if (this.valve > this.max) {
			this.setValve(this.max);
		} else if (this.valve < this.min) {
			this.setValve(this.min);
		} else {
			this.setValve(this.valve);
		}
	}

	public int getValve() {
		try {
			this.valve = Integer.parseInt(this.textField.getText());
		} catch (Exception exception) {
			// empty catch block
		}
		return this.valve;
	}

	public void setValve(int valve) {
		this.valve = valve;
		this.textField.setText(valve + "");
	}

	public void setMin(int min) {
		this.min = min;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public void setNext(int next) {
		this.next = next;
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		this.setAlpha(enabled ? 1.0f : 0.1f);
		Color fg = enabled ? new Color(51, 51, 51) : new Color(109, 109, 109);
		this.buttonUp.setForeground(fg);
		this.buttonDown.setForeground(fg);
		Color bg = enabled ? Color.white : Color.white.darker();
		this.buttonUp.setTopBgColor(bg);
		this.buttonUp.setBottomBgColor(bg);
		this.buttonDown.setTopBgColor(bg);
		this.buttonDown.setBottomBgColor(bg);
		this.background.setEnabled(enabled);
		this.textField.setEnabled(enabled);
	}

	public float getAlpha() {
		return this.alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
		this.repaint();
	}

	public void addFocusListener(FocusAdapter focusAdapter) {
		this.textField.addFocusListener(focusAdapter);
	}
}