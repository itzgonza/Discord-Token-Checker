package net.ranktw.discord.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import net.ranktw.discord.User;
import net.ranktw.http.HttpRequest;

public class DiscordIconTableCellRenderer extends DefaultTableCellRenderer {

	private static Map<String, Image> imageMap = new HashMap<String, Image>();
	private static List<String> loading = new ArrayList<String>();
	private static Color banned = new Color(0xFF5555);

	private Image getImage(String url) throws Exception {
		if (imageMap.containsKey(url)) {
			return imageMap.get(url);
		}
		if (loading.contains(url)) {
			return new ImageIcon(this.getClass().getResource("/images/u.png")).getImage();
		}
		loading.add(url);
		System.out.println(url);
		String USER_AGENT = "Mozilla/5.0";
		BufferedImage image = ImageIO.read(HttpRequest.get(url).userAgent(USER_AGENT).stream());
		imageMap.put(url, image);
		return image;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		if (value instanceof User) {
			JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			l.setHorizontalAlignment(2);
			l.setIcon(null);
			User user = (User) value;
			int c = table.convertColumnIndexToModel(column);
			switch (c) {
			case 0: {
				l.setText(user.getDiscriminator());
				l.setHorizontalAlignment(0);
				break;
			}
			case 1: {
				if (user.hasError()) {
					l.setForeground(banned);
				}
				if (Config.isAvatar()) {
					if (!imageMap.containsKey(user.getAvatarUrl())) {
						l.setIcon(new ImageIcon(new ImageIcon(this.getClass().getResource("/images/u.png")).getImage()
								.getScaledInstance(16, 16, 1)));
						if (!loading.contains(user.getAvatarUrl())) {
							new Thread(() -> {
								try {
									Image image = this.getImage(user.getAvatarUrl());
									l.setIcon(new ImageIcon(image.getScaledInstance(16, 16, 1)));
								} catch (Exception e) {
									e.printStackTrace();
								}
							}).start();
						}
					} else {
						l.setIcon(new ImageIcon(imageMap.get(user.getAvatarUrl()).getScaledInstance(16, 16, 1)));
					}
				}
				l.setText(user.getUsername());
				break;
			}
			case 2: {
				l.setText(user.getCreatedDate());
				break;
			}
			case 3: {
				l.setText(String.format("<html>Email Verified: %s<br>Phone Verified: %s", user.isEmailVerified(),
						user.isPhoneVerified()).replaceAll("true",
								"<span style=\"color: #5d9f6e;\"><span style=\"font-weight:bold;\">true</span></span>"));
				break;
			}
			case 4: {
				l.setText(user.isRequiredVerified()
						? "<html><span style=\"color: #FF5555;\"><span style=\";\">Required Verify"
						: String.format("<html>Guild Count: %s<br>DM History: %s", user.getGuilds(), user.getDMs()));
				break;
			}
			case 5: {
				l.setText(user.getToken());
				break;
			}
			}
			return l;
		}
		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}
}