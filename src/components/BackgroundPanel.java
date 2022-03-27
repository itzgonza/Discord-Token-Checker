package components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {

	int BgHeight = 24;
	int BgWidth = 24;
	private Image ic = this.getImage("/images/border/c.png");
	private Image it = this.getImage("/images/border/t.png");
	private Image ib = this.getImage("/images/border/b.png");
	private Image il = this.getImage("/images/border/l.png");
	private Image ir = this.getImage("/images/border/r.png");
	private JLabel l1;
	private JLabel l2;
	private JLabel l3;
	private JLabel l4;
	private JLabel lt;
	private JLabel lb;
	private JLabel lc;
	private JLabel ll;
	private JLabel lr;

	public BackgroundPanel() {
		this.initComponents();
		this.addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent evt) {
				BackgroundPanel.this.componentResize(evt);
			}
		});
	}

	private void componentResize(ComponentEvent e) {
		if (e != null) {
			this.BgHeight = this.getHeight();
			this.BgWidth = this.getWidth();
		}
		if (this.BgHeight < 24) {
			this.BgHeight = 24;
		}
		if (this.BgWidth < 24) {
			this.BgWidth = 24;
		}
		this.lt.setIcon(new ImageIcon(this.it.getScaledInstance(this.BgWidth - 16, 8, 1)));
		this.lt.setPreferredSize(new Dimension(this.BgWidth - 16, 8));
		this.lb.setIcon(new ImageIcon(this.ib.getScaledInstance(this.BgWidth - 16, 8, 1)));
		this.lb.setPreferredSize(new Dimension(this.BgWidth - 16, 8));
		this.ll.setIcon(new ImageIcon(this.il.getScaledInstance(8, this.BgHeight - 16, 1)));
		this.ll.setPreferredSize(new Dimension(8, this.BgHeight - 16));
		this.lr.setIcon(new ImageIcon(this.ir.getScaledInstance(8, this.BgHeight - 16, 1)));
		this.lr.setPreferredSize(new Dimension(8, this.BgHeight - 16));
		this.lc.setIcon(new ImageIcon(this.ic.getScaledInstance(this.BgWidth - 16, this.BgHeight - 16, 1)));
		this.lc.setPreferredSize(new Dimension(this.BgWidth - 16, this.BgHeight - 16));
	}

	public void setDark(boolean dark) {
		if (dark) {
			this.ic = this.getImage("/images/border/black/c.png");
			this.it = this.getImage("/images/border/black/t.png");
			this.ib = this.getImage("/images/border/black/b.png");
			this.il = this.getImage("/images/border/black/l.png");
			this.ir = this.getImage("/images/border/black/r.png");
			this.l1.setIcon(new ImageIcon(this.getClass().getResource("/images/border/black/1.png")));
			this.l2.setIcon(new ImageIcon(this.getClass().getResource("/images/border/black/2.png")));
			this.l3.setIcon(new ImageIcon(this.getClass().getResource("/images/border/black/3.png")));
			this.l4.setIcon(new ImageIcon(this.getClass().getResource("/images/border/black/4.png")));
			this.lc.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/images/border/black/c.png"))
					.getImage().getScaledInstance(this.BgWidth - 16, this.BgHeight - 16, 1)));
			this.lr.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/images/border/black/r.png"))
					.getImage().getScaledInstance(8, this.BgHeight - 16, 1)));
			this.lb.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/images/border/black/b.png"))
					.getImage().getScaledInstance(this.BgWidth - 16, 8, 1)));
			this.ll.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/images/border/black/l.png"))
					.getImage().getScaledInstance(8, this.BgHeight - 16, 1)));
			this.lt.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/images/border/black/t.png"))
					.getImage().getScaledInstance(this.BgWidth - 16, 8, 1)));
		} else {
			this.ic = this.getImage("/images/border/c.png");
			this.it = this.getImage("/images/border/t.png");
			this.ib = this.getImage("/images/border/b.png");
			this.il = this.getImage("/images/border/l.png");
			this.ir = this.getImage("/images/border/r.png");
			this.l1.setIcon(new ImageIcon(this.getClass().getResource("/images/border/1.png")));
			this.l2.setIcon(new ImageIcon(this.getClass().getResource("/images/border/2.png")));
			this.l3.setIcon(new ImageIcon(this.getClass().getResource("/images/border/3.png")));
			this.l4.setIcon(new ImageIcon(this.getClass().getResource("/images/border/4.png")));
			this.lc.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/images/border/c.png")).getImage()
					.getScaledInstance(this.BgWidth - 16, this.BgHeight - 16, 1)));
			this.lr.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/images/border/r.png")).getImage()
					.getScaledInstance(8, this.BgHeight - 16, 1)));
			this.lb.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/images/border/b.png")).getImage()
					.getScaledInstance(this.BgWidth - 16, 8, 1)));
			this.ll.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/images/border/l.png")).getImage()
					.getScaledInstance(8, this.BgHeight - 16, 1)));
			this.lt.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/images/border/t.png")).getImage()
					.getScaledInstance(this.BgWidth - 16, 8, 1)));
		}
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if (!enabled) {
			this.ic = this.getImage("/images/border/disabled/c.png");
			this.it = this.getImage("/images/border/disabled/t.png");
			this.ib = this.getImage("/images/border/disabled/b.png");
			this.il = this.getImage("/images/border/disabled/l.png");
			this.ir = this.getImage("/images/border/disabled/r.png");
			this.l1.setIcon(new ImageIcon(this.getClass().getResource("/images/border/disabled/1.png")));
			this.l2.setIcon(new ImageIcon(this.getClass().getResource("/images/border/disabled/2.png")));
			this.l3.setIcon(new ImageIcon(this.getClass().getResource("/images/border/disabled/3.png")));
			this.l4.setIcon(new ImageIcon(this.getClass().getResource("/images/border/disabled/4.png")));
			this.lc.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/images/border/disabled/c.png"))
					.getImage().getScaledInstance(this.BgWidth - 16, this.BgHeight - 16, 1)));
			this.lr.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/images/border/disabled/r.png"))
					.getImage().getScaledInstance(8, this.BgHeight - 16, 1)));
			this.lb.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/images/border/disabled/b.png"))
					.getImage().getScaledInstance(this.BgWidth - 16, 8, 1)));
			this.ll.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/images/border/disabled/l.png"))
					.getImage().getScaledInstance(8, this.BgHeight - 16, 1)));
			this.lt.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/images/border/disabled/t.png"))
					.getImage().getScaledInstance(this.BgWidth - 16, 8, 1)));
		} else {
			this.ic = this.getImage("/images/border/c.png");
			this.it = this.getImage("/images/border/t.png");
			this.ib = this.getImage("/images/border/b.png");
			this.il = this.getImage("/images/border/l.png");
			this.ir = this.getImage("/images/border/r.png");
			this.l1.setIcon(new ImageIcon(this.getClass().getResource("/images/border/1.png")));
			this.l2.setIcon(new ImageIcon(this.getClass().getResource("/images/border/2.png")));
			this.l3.setIcon(new ImageIcon(this.getClass().getResource("/images/border/3.png")));
			this.l4.setIcon(new ImageIcon(this.getClass().getResource("/images/border/4.png")));
			this.lc.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/images/border/c.png")).getImage()
					.getScaledInstance(this.BgWidth - 16, this.BgHeight - 16, 1)));
			this.lr.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/images/border/r.png")).getImage()
					.getScaledInstance(8, this.BgHeight - 16, 1)));
			this.lb.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/images/border/b.png")).getImage()
					.getScaledInstance(this.BgWidth - 16, 8, 1)));
			this.ll.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/images/border/l.png")).getImage()
					.getScaledInstance(8, this.BgHeight - 16, 1)));
			this.lt.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/images/border/t.png")).getImage()
					.getScaledInstance(this.BgWidth - 16, 8, 1)));
		}
	}

	private void initComponents() {
		this.l3 = new JLabel();
		this.lc = new JLabel();
		this.lr = new JLabel();
		this.lb = new JLabel();
		this.ll = new JLabel();
		this.l1 = new JLabel();
		this.l4 = new JLabel();
		this.l2 = new JLabel();
		this.lt = new JLabel();
		this.setLayout(new GridBagLayout());
		this.l1.setIcon(new ImageIcon(this.getClass().getResource("/images/border/1.png")));
		GridBagConstraints gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		this.add((Component) this.l1, gridBagConstraints);
		this.l2.setIcon(new ImageIcon(this.getClass().getResource("/images/border/2.png")));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		this.add((Component) this.l2, gridBagConstraints);
		this.l3.setIcon(new ImageIcon(this.getClass().getResource("/images/border/3.png")));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		this.add((Component) this.l3, gridBagConstraints);
		this.l4.setIcon(new ImageIcon(this.getClass().getResource("/images/border/4.png")));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 2;
		this.add((Component) this.l4, gridBagConstraints);
		this.lc.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/images/border/c.png")).getImage()
				.getScaledInstance(this.BgWidth - 16, this.BgHeight - 16, 1)));
		this.lc.setPreferredSize(new Dimension(this.BgWidth - 16, this.BgHeight - 16));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		this.add((Component) this.lc, gridBagConstraints);
		this.lr.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/images/border/r.png")).getImage()
				.getScaledInstance(8, this.BgHeight - 16, 1)));
		this.lr.setPreferredSize(new Dimension(8, this.BgHeight - 16));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		this.add((Component) this.lr, gridBagConstraints);
		this.lb.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/images/border/b.png")).getImage()
				.getScaledInstance(this.BgWidth - 16, 8, 1)));
		this.lb.setPreferredSize(new Dimension(this.BgWidth - 16, 8));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		this.add((Component) this.lb, gridBagConstraints);
		this.ll.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/images/border/l.png")).getImage()
				.getScaledInstance(8, this.BgHeight - 16, 1)));
		this.ll.setPreferredSize(new Dimension(8, this.BgHeight - 16));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		this.add((Component) this.ll, gridBagConstraints);
		this.lt.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/images/border/t.png")).getImage()
				.getScaledInstance(this.BgWidth - 16, 8, 1)));
		this.lt.setPreferredSize(new Dimension(this.BgWidth - 16, 8));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		this.add((Component) this.lt, gridBagConstraints);
	}

	public void setWidthAndHeight(int width, int height) {
		this.setBgHeight(height);
		this.setBgWidth(width);
		this.componentResize(null);
	}

	public void setBgHeight(int bgHeight) {
		this.BgHeight = bgHeight;
	}

	public int getBgHeight() {
		return this.BgHeight;
	}

	public void setBgWidth(int bgWidth) {
		this.BgWidth = bgWidth;
	}

	public int getBgWidth() {
		return this.BgWidth;
	}

	private Image getImage(String path) {
		return new ImageIcon(this.getClass().getResource(path)).getImage();
	}
}