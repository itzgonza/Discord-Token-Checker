package components;

import java.awt.Container;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.Objects;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;

class TranslucentScrollPaneLayout extends ScrollPaneLayout {

	TranslucentScrollPaneLayout() {
	}

	@Override
	public void layoutContainer(Container parent) {
		if (parent instanceof JScrollPane) {
			JScrollPane scrollPane = (JScrollPane) parent;
			Rectangle availR = scrollPane.getBounds();
			availR.setLocation(0, 0);
			Insets insets = parent.getInsets();
			availR.x = insets.left;
			availR.y = insets.top;
			availR.width -= insets.left + insets.right;
			availR.height -= insets.top + insets.bottom;
			Rectangle vsbR = new Rectangle();
			vsbR.width = 12;
			vsbR.height = availR.height;
			vsbR.x = availR.x + availR.width - vsbR.width;
			vsbR.y = availR.y;
			if (Objects.nonNull(this.viewport)) {
				this.viewport.setBounds(availR);
			}
			if (Objects.nonNull(this.vsb)) {
				this.vsb.setVisible(true);
				this.vsb.setBounds(vsbR);
			}
		}
	}
}