package components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.alee.extended.window.WebPopOver;
import com.alee.laf.label.WebLabel;

import dev.Main;

public class Notification {

	private static Color info = new Color(67, 181, 129);
	private static Color infoTimer = new Color(87, 192, 144);
	private static Color error = new Color(255, 85, 85);
	private static Color errorTimer = new Color(163, 58, 58);
	private Color bg;
	private Color bgTimer;
	private ImageIcon icon;
	private int type;
	private static JFrame frame;
	private static String text;
	private static int location;
	private static int sw;
	private int id;
	private boolean iconified;
	private boolean closed;
	private String message;
	private WebPopOver popOver;
	private WebLabel labelIcon;
	private JLabel labelText;
	private JLabel closeLabel;
	private JPanel content;
	private JPanel timer;
	private JEditorPane editorPane = new JEditorPane();
	private static List<Notification> locationX;
	private static AtomicInteger ids;
	private static JLabel CloseAllLabel;
	private static JPanel CloseAllcontent;
	private static WebPopOver CloseAllpopOver;

	public int getType() {
		return this.type;
	}

	public Notification(String message) {
		this(message, 0);
	}

	public Notification(String message, int type) {
		this.id = ids.incrementAndGet();
		this.type = type;
		switch (type) {
		case 1: {
			this.bg = error;
			this.bgTimer = errorTimer;
			this.icon = new ImageIcon(new ImageIcon(this.getClass().getResource("/images/WARNING.png")).getImage()
					.getScaledInstance(30, 30, 6));
			break;
		}
		default: {
			this.bg = info;
			this.bgTimer = infoTimer;
			this.icon = new ImageIcon(this.getClass().getResource("/images/info_30px.png"));
		}
		}
		this.message = message;
		this.initComponents();
	}

	private void initComponents() {
		this.popOver = new WebPopOver();
		this.content = new JPanel();
		this.labelIcon = new WebLabel();
		this.labelText = new JLabel();
		this.closeLabel = new JLabel();
		this.timer = new JPanel();
		this.popOver.setAlwaysOnTop(true);
		this.popOver.setContentBackground(this.bg);
		this.popOver.setFocusable(false);
		this.popOver.setMovable(false);
		this.popOver.setShadeWidth(10);
		this.content.setBackground(this.bg);
		this.labelIcon.setHorizontalAlignment(0);
		this.labelIcon.setIcon(this.icon);
		this.labelIcon.setMargin(new Insets(0, 4, 0, 0));
		this.labelIcon.setPreferredSize(new Dimension(42, 18));
		this.labelText.setForeground(new Color(255, 255, 255));
		this.labelText.setText("<html><span style=\"color: #FFFFFF;\">\nText...");
		this.closeLabel.setFont(Main.loadFont("/fonts/OpenSans-Bold.ttf", 12.0f, 1));
		this.closeLabel.setForeground(new Color(255, 255, 255, 127));
		this.closeLabel.setHorizontalAlignment(0);
		this.closeLabel.setText("x ");
		this.closeLabel.setCursor(new Cursor(12));
		this.closeLabel.setPreferredSize(new Dimension(16, 16));
		this.closeLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent evt) {
				Notification.this.closeLabelMouseReleased(evt);
			}
		});
		GroupLayout contentLayout = new GroupLayout(this.content);
		this.content.setLayout(contentLayout);
		contentLayout.setHorizontalGroup(contentLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(contentLayout.createSequentialGroup().addComponent(this.labelIcon, -2, 42, -2).addGap(3, 3, 3)
						.addComponent(this.labelText, -1, Notification.getLength(this.message), Short.MAX_VALUE)
						.addGap(6, 6, 6).addComponent(this.closeLabel, -2, 20, -2)));
		contentLayout.setVerticalGroup(contentLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(this.labelText, -1, 50, Short.MAX_VALUE)
				.addComponent(this.closeLabel, -1, -1, Short.MAX_VALUE)
				.addComponent(this.labelIcon, -1, -1, Short.MAX_VALUE));
		this.popOver.getContentPane().add((Component) this.content, "Center");
		this.timer.setBackground(this.bgTimer);
		this.timer.setPreferredSize(new Dimension(139, 2));
		GroupLayout timerLayout = new GroupLayout(this.timer);
		this.timer.setLayout(timerLayout);
		timerLayout.setHorizontalGroup(
				timerLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 139, Short.MAX_VALUE));
		timerLayout.setVerticalGroup(
				timerLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addGap(0, 2, Short.MAX_VALUE));
		this.popOver.getContentPane().add((Component) this.timer, "South");
		frame.setDefaultCloseOperation(3);
		frame.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentMoved(ComponentEvent evt) {
				Notification.this.formComponentMoved(evt);
			}

			@Override
			public void componentResized(ComponentEvent evt) {
				Notification.this.formComponentResized(evt);
			}
		});
		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowActivated(WindowEvent evt) {
				Notification.this.formWindowActivated(evt);
			}

			@Override
			public void windowDeactivated(WindowEvent evt) {
				Notification.this.formWindowDeactivated(evt);
			}

			@Override
			public void windowDeiconified(WindowEvent evt) {
				Notification.this.formWindowDeiconified(evt);
			}

			@Override
			public void windowIconified(WindowEvent evt) {
				Notification.this.formWindowIconified(evt);
			}
		});
	}

	private static void initCloseAll() {
		CloseAllpopOver = new WebPopOver();
		CloseAllpopOver.setAlwaysOnTop(true);
		CloseAllpopOver.setContentBackground(info);
		CloseAllpopOver.setFocusable(false);
		CloseAllpopOver.setMovable(false);
		CloseAllLabel = new JLabel();
		CloseAllLabel.setForeground(new Color(255, 255, 255));
		CloseAllLabel.setHorizontalAlignment(0);
		CloseAllLabel.setText("<html><span style=\"color: #FFFFFF;\">\nClose All");
		CloseAllLabel.setCursor(new Cursor(12));
		CloseAllLabel.setHorizontalTextPosition(0);
		CloseAllLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent evt) {
				Notification.CloseAllLabelMouseReleased(evt);
			}
		});
		CloseAllcontent = new JPanel();
		CloseAllcontent.setBackground(info);
		GroupLayout CloseAllLayout = new GroupLayout(CloseAllcontent);
		CloseAllcontent.setLayout(CloseAllLayout);
		CloseAllLayout.setHorizontalGroup(CloseAllLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(CloseAllLabel, -2, 111, -2));
		CloseAllLayout.setVerticalGroup(CloseAllLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(CloseAllLabel, -1, 26, Short.MAX_VALUE));
		CloseAllpopOver.getContentPane().add((Component) CloseAllcontent, "Center");
		frame.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentMoved(ComponentEvent evt) {
				if (!CloseAllpopOver.isShowing()) {
					return;
				}
				int x = Notification.getCloseAllLocationX() + location - 40;
				int y = Notification.getCloseAllLocationY() + location - 20;
				CloseAllpopOver.setLocation(x, y);
			}

			@Override
			public void componentResized(ComponentEvent evt) {
				if (!CloseAllpopOver.isShowing()) {
					return;
				}
				int x = Notification.getCloseAllLocationX() + location - 40;
				int y = Notification.getCloseAllLocationY() + location - 20;
				CloseAllpopOver.setLocation(x, y);
			}
		});
		frame.addWindowListener(new WindowAdapter() {
			boolean ic;

			@Override
			public void windowDeiconified(WindowEvent evt) {
				if (this.ic) {
					this.ic = false;
					int x = Notification.getCloseAllLocationX() + location;
					int y = Notification.getCloseAllLocationY() + location;
					CloseAllpopOver.show(x, y);
				}
			}

			@Override
			public void windowIconified(WindowEvent evt) {
				if (CloseAllpopOver.isShowing()) {
					this.ic = true;
					CloseAllpopOver.dispose();
				}
			}
		});
	}

	private static void CloseAllLabelMouseReleased(MouseEvent evt) {
		CloseAllpopOver.dispose();
		for (Notification n : locationX) {
			n.popOver.dispose();
			n.popOver = null;
			n.closed = true;
		}
		locationX.clear();
	}

	private void closeLabelMouseReleased(MouseEvent evt) {
		this.popOver.dispose();
		this.popOver = null;
		this.closed = true;
		locationX.remove(this);
		for (Notification n : locationX) {
			int x = n.getLocationX() + location - n.popOver.getShadeWidth() * 3;
			int y = n.getLocationY() + location - n.popOver.getShadeWidth() * 3;
			n.popOver.setLocation(x, y);
		}
		if (locationX.size() > 0) {
			Notification.showCloseAll(locationX.get(locationX.size() - 1));
		}
	}

	public void close() {
		this.closeLabelMouseReleased(null);
	}

	private void formComponentMoved(ComponentEvent evt) {
		if (this.closed) {
			return;
		}
		if (!this.popOver.isShowing()) {
			return;
		}
		int x = this.getLocationX() + location - this.popOver.getShadeWidth() * 3;
		int y = this.getLocationY() + location - this.popOver.getShadeWidth() * 3;
		this.popOver.setLocation(x, y);
	}

	private void formComponentResized(ComponentEvent evt) {
		if (this.closed) {
			return;
		}
		if (!this.popOver.isShowing()) {
			return;
		}
		int x = this.getLocationX() + location - this.popOver.getShadeWidth() * 3;
		int y = this.getLocationY() + location - this.popOver.getShadeWidth() * 3;
		this.popOver.setLocation(x, y);
	}

	private void formWindowIconified(WindowEvent evt) {
		if (this.closed) {
			return;
		}
		if (this.popOver.isShowing()) {
			this.iconified = true;
			this.popOver.dispose();
		}
	}

	private void formWindowDeiconified(WindowEvent evt) {
		if (this.closed) {
			return;
		}
		if (this.iconified) {
			this.iconified = false;
			int x = this.getLocationX() + location;
			int y = this.getLocationY() + location;
			this.popOver.show(x, y);
		}
	}

	private void formWindowActivated(WindowEvent evt) {
	}

	private void formWindowDeactivated(WindowEvent evt) {
	}

	private int getLocationX() {
		return Notification.frame.getLocationOnScreen().x + frame.getWidth() - this.popOver.getWidth();
	}

	private int getLocationY() {
		int xx = 0;
		for (Notification n : locationX) {
			if (n.equals(this))
				break;
			xx += n.popOver.getHeight() - (n.popOver.getShadeWidth() * 2 - 4);
		}
		return Notification.frame.getLocationOnScreen().y + frame.getHeight() - this.popOver.getHeight() - xx;
	}

	private static int getCloseAllLocationX() {
		return locationX.get(locationX.size() - 1).getLocationX();
	}

	private static int getCloseAllLocationY() {
		int yy = 0;
		for (Notification n : locationX) {
			yy += n.popOver.getHeight() - (n.popOver.getShadeWidth() * 2 - 4);
		}
		yy = Notification.frame.getLocationOnScreen().y + frame.getHeight() - CloseAllpopOver.getHeight() - yy;
		return yy;
	}

	private static void setCloseAllWidth(int width) {
		GroupLayout CloseAllLayout = new GroupLayout(CloseAllcontent);
		CloseAllcontent.setLayout(CloseAllLayout);
		CloseAllLayout.setHorizontalGroup(CloseAllLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(CloseAllLabel, -2, width, -2));
		CloseAllLayout.setVerticalGroup(CloseAllLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(CloseAllLabel, -1, 26, Short.MAX_VALUE));
		CloseAllcontent.setPreferredSize(new Dimension(width, Notification.CloseAllcontent.getPreferredSize().height));
		CloseAllcontent.setMinimumSize(new Dimension(width, 0));
		CloseAllpopOver.setSize(width + 40, 66);
	}

	private static void setCloseAllType(int type) {
		CloseAllpopOver.setContentBackground(type == 0 ? info : error);
		CloseAllcontent.setBackground(type == 0 ? info : error);
	}

	public static Notification info(String message) {
		Notification notification = new Notification(message);
		Notification.show(message, notification);
		return notification;
	}

	public static Notification error(String message) {
		Notification notification = new Notification(message, 1);
		Notification.show(message, notification);
		return notification;
	}

	private static void show(String message, Notification notification) {
		notification.editorPane.setContentType("text/html");
		notification.editorPane.setText(String.format(text, message));
		notification.labelText.setText(notification.editorPane.getText());
		notification.popOver.show(-50, -50);
		locationX.add(notification);
		int x = notification.getLocationX() + location - notification.popOver.getShadeWidth() * 3;
		int y = notification.getLocationY() + location - notification.popOver.getShadeWidth() * 3;
		notification.popOver.setLocation(x, y);
		Notification.showCloseAll(notification);
	}

	private static void showCloseAll(Notification n) {
		if (locationX.size() > 1) {
			if (!CloseAllpopOver.isShowing()) {
				CloseAllpopOver.show(-50, -50);
			}
			Notification.setCloseAllType(n.getType());
			CloseAllpopOver.setLocation(Notification.getCloseAllLocationX() - n.popOver.getShadeWidth(),
					Notification.getCloseAllLocationY() + n.popOver.getShadeWidth());
			int w = n.popOver.getWidth() - n.popOver.getShadeWidth() * 2;
			Notification.setCloseAllWidth(w);
		} else if (CloseAllpopOver.isShowing()) {
			CloseAllpopOver.dispose();
		}
	}

	private static int getH(String text) {
		if (text.contains("\n")) {
			int h = 0;
			for (String s : text.split("\n")) {
				if (s.length() <= h)
					continue;
				h = s.length();
			}
			return h;
		}
		return text.length();
	}

	private static int getLength(String text) {
		return Math.max(Notification.getH(text) * 6, 68);
	}

	public static void setFrame(JFrame frame) {
		Notification.frame = frame;
		Notification.initCloseAll();
	}

	public static void setLocation(int location) {
		Notification.location = location;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Notification) {
			Notification n = (Notification) obj;
			return n.id == this.id;
		}
		return false;
	}

	static {
		text = "<html>\r\n  <head>\r\n\r\n  </head>\r\n  <body>\r\n    <p style=\"color: #FFFFFF; margin-top: 0;\">\r\n      \r%s\n    </p>\r\n  </body>\r\n</html>\r\n";
		location = 20;
		sw = 10;
		locationX = new ArrayList<Notification>();
		ids = new AtomicInteger();
	}
}