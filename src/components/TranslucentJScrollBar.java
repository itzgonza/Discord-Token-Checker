package components;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JScrollPane;

public final class TranslucentJScrollBar {

	public static Component make(Component c) {
		return new JScrollPane(c) {

			@Override
			public boolean isOptimizedDrawingEnabled() {
				return false;
			}

			@Override
			public void updateUI() {
				super.updateUI();
				EventQueue.invokeLater(() -> {
					this.getVerticalScrollBar().setUI(new TranslucentScrollBarUI());
					this.setComponentZOrder(this.getVerticalScrollBar(), 0);
					this.setComponentZOrder(this.getViewport(), 1);
					this.getVerticalScrollBar().setOpaque(false);
				});
				this.setBorder(null);
				this.setVerticalScrollBarPolicy(22);
				this.setHorizontalScrollBarPolicy(31);
				this.setLayout(new TranslucentScrollPaneLayout());
			}
		};
	}
}