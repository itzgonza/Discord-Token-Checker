package components;

import java.awt.Color;

import com.alee.laf.button.WebButton;

public class DarkButton extends WebButton {

	public DarkButton() {
		this.setForeground(new Color(51, 51, 51));
		this.setSelectedForeground(new Color(39, 39, 39));
		this.setText("Button");
		this.setDrawFocus(false);
		this.setShadeColor(new Color(64, 64, 64));
		this.setShineColor(new Color(64, 64, 64));
		this.setTopBgColor(Color.white);
		this.setBottomBgColor(Color.white);
	}

	public DarkButton(boolean dark) {
		this.setForeground(new Color(255, 255, 255));
		this.setSelectedForeground(new Color(182, 182, 182));
		this.setText("Button");
		this.setDrawFocus(false);
		this.setShadeColor(new Color(64, 64, 64));
		this.setShineColor(new Color(64, 64, 64));
		this.setTopBgColor(Color.white);
		this.setBottomBgColor(Color.white);
		this.setRolloverDarkBorderOnly(true);
		this.setRolloverDecoratedOnly(true);
		this.setRolloverDarkBorderOnly(true);
		new Color(51, 56, 56);
		this.setBottomBgColor(new Color(72, 78, 78));
		this.setTopBgColor(new Color(72, 78, 78));
		this.setShadeColor(new Color(72, 78, 78));
		this.setShineColor(new Color(72, 78, 78));
		this.setInnerShadeColor(new Color(72, 78, 78));
		this.setBottomSelectedBgColor(new Color(72, 78, 78));
		this.setTopSelectedBgColor(new Color(72, 78, 78));
	}

	@Override
	public void setBackground(Color bg) {
		super.setBackground(bg);
		this.setForeground(new Color(255, 255, 255));
		this.setSelectedForeground(new Color(182, 182, 182));
		this.setBottomBgColor(bg);
		this.setTopBgColor(bg);
		this.setShadeColor(bg.brighter());
		this.setShineColor(bg.brighter());
		this.setInnerShadeColor(bg.brighter());
		this.setBottomSelectedBgColor(bg.darker());
		this.setTopSelectedBgColor(bg.darker());
	}
}