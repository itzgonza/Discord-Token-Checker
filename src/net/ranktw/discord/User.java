package net.ranktw.discord;

import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import com.google.gson.annotations.SerializedName;

public class User {

	static String AVATAR_URL = "https://cdn.discordapp.com/avatars/%s/%s.%s";
	static String DEFAULT_AVATAR_URL = "https://cdn.discordapp.com/embed/avatars/%s.png";
	@SerializedName(value = "id")
	private String id;
	@SerializedName(value = "username")
	private String username;
	@SerializedName(value = "avatar")
	private String avatar;
	@SerializedName(value = "discriminator")
	private String discriminator;
	@SerializedName(value = "email")
	private String email;
	@SerializedName(value = "phone")
	private String phone;
	@SerializedName(value = "verified")
	private boolean verified;
	@SerializedName(value = "flags")
	private int flags;
	@SerializedName(value = "premium_type")
	private int premium_type;
	private boolean requiredVerified;
	private int guilds;
	private int dms;
	private String token;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy - HH:mm:ss");
	@SerializedName(value = "message")
	private String message;
	@SerializedName(value = "code")
	private String code;

	public void setRequiredVerified(boolean requiredVerified) {
		this.requiredVerified = requiredVerified;
	}

	public boolean isRequiredVerified() {
		return this.requiredVerified;
	}

	public void setGuilds(int guilds) {
		this.guilds = guilds;
	}

	public int getGuilds() {
		return this.guilds;
	}

	public void setDMs(int dms) {
		this.dms = dms;
	}

	public int getDMs() {
		return this.dms;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return this.token;
	}

	public String getId() {
		return this.id;
	}

	public String getUsername() {
		return this.username;
	}

	public String getDiscriminator() {
		return this.discriminator;
	}

	public boolean isEmailVerified() {
		return this.verified;
	}

	public boolean isPhoneVerified() {
		return this.phone != null;
	}

	public boolean isDefaultAvatar() {
		return this.avatar == null;
	}

	public String getAvatarUrl() {
		String avatarId = this.avatar;
		return avatarId == null ? String.format(DEFAULT_AVATAR_URL, Integer.parseInt(this.discriminator) % 5)
				: String.format(AVATAR_URL, this.getId(), avatarId, avatarId.startsWith("a_") ? "gif" : "png");
	}

	public String getCreatedDate() {
		return User.getTimeDate(Long.parseLong(this.getId()));
	}

	public String getEmail() {
		return this.email;
	}

	public String getPhone() {
		return this.phone;
	}

	public String getFlags() {
		return this.flags == 0 ? "Nah" : Flags.getFlags(this.flags);
	}

	public String getPremium() {
		return Premium.getPremium((int) this.premium_type).DESCRIPTION;
	}

	public String toString() {
		return this.username + "#" + this.discriminator;
	}

	private static OffsetDateTime getCreationTime(long entityId) {
		long timestamp = (entityId >>> 22) + 1420070400000L;
		Calendar gmt = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		gmt.setTimeInMillis(timestamp);
		return OffsetDateTime.ofInstant(gmt.toInstant(), gmt.getTimeZone().toZoneId());
	}

	private static String getTimeDate(long time) {
		return dateFormat.format(User.getCreationTime(time).toInstant().toEpochMilli());
	}

	public boolean hasError() {
		return this.message != null;
	}

	public String getErrorMessage() {
		return this.message;
	}

	static enum Premium {
		None("None", 0), NitroClassic("Nitro Classic", 1), Nitro("Nitro", 2), Unknown("Unknown", -1);

		String DESCRIPTION;
		int VALUE;

		private Premium(String DESCRIPTION, int VALUE) {
			this.DESCRIPTION = DESCRIPTION;
			this.VALUE = VALUE;
		}

		public static Premium getPremium(int i) {
			for (Premium p : Premium.values()) {
				if (p.VALUE != i)
					continue;
				return p;
			}
			return Unknown;
		}
	}

	static enum Flags {
		None("None", 0), DiscordStaff("Discord Staff", 1), DiscordPartner("Discord Partner", 2), HypeSquadEvents(
				"HypeSquad Events", 4), BugHunter("Bug Hunter", 8), Subscriber("Subscriber", 32), HouseBravery(
						"House Bravery", 64), HouseBrilliance("House Brilliance", 128), HouseBalance("House Balance",
								256), EarlySupporter("Early Supporter", 512), Unknown("Unknown", -1);

		String DESCRIPTION;
		int VALUE;

		private Flags(String DESCRIPTION, int VALUE) {
			this.DESCRIPTION = DESCRIPTION;
			this.VALUE = VALUE;
		}

		public static String getFlags(int flag) {
			ArrayList<String> list = new ArrayList<String>();
			for (int i = 9; i >= 0; --i) {
				if (flag >> i != 1)
					continue;
				flag -= 1 << i;
				list.add(Flags.intToFlag(i));
			}
			return String.valueOf(list);
		}

		private static String intToFlag(int i) {
			switch (i) {
			case 9: {
				return Flags.EarlySupporter.DESCRIPTION;
			}
			case 8: {
				return Flags.HouseBalance.DESCRIPTION;
			}
			case 7: {
				return Flags.HouseBrilliance.DESCRIPTION;
			}
			case 6: {
				return Flags.HouseBravery.DESCRIPTION;
			}
			case 5: {
				return Flags.Subscriber.DESCRIPTION;
			}
			case 4: {
				return Flags.BugHunter.DESCRIPTION;
			}
			case 3: {
				return Flags.HypeSquadEvents.DESCRIPTION;
			}
			case 2: {
				return Flags.DiscordPartner.DESCRIPTION;
			}
			case 1: {
				return Flags.DiscordStaff.DESCRIPTION;
			}
			case 0: {
				return Flags.None.DESCRIPTION;
			}
			}
			return Flags.Unknown.DESCRIPTION;
		}
	}
}