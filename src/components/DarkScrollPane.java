package components;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;

public class DarkScrollPane extends JScrollPane {

	public DarkScrollPane() {
		Color DARK = new Color(0x1E1E1E);
		this.setBorder(BorderFactory.createCompoundBorder());
		this.getVerticalScrollBar().setUI(new VScrollBarUI().setColor(Color.WHITE));
		this.getVerticalScrollBar().setBackground(DARK.darker());
		this.getVerticalScrollBar().setPreferredSize(new Dimension(5, 0));
		this.getHorizontalScrollBar().setUI(new HScrollBarUI().setColor(Color.WHITE));
		this.getHorizontalScrollBar().setBackground(DARK);
		this.setBackground(DARK.brighter());
		this.getViewport().setBackground(DARK);
	}
}