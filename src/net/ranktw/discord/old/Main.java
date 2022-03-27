package net.ranktw.discord.old;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.ranktw.http.HttpRequest;

public class Main {

	static GuiDTC guiMain;
	static GuiInput guiInput;
	static CustomOutput customOutput;
	static AdvancedSettings advancedSettings;
	static List<String> tokens;
	public static File currFolder;
	private static final String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36 OPR/53.0.2907.43";
	static SimpleDateFormat dateFormat;

	/**
	 * 
	 * @param OLD
	 *            VERSION
	 * 
	 *            public static void main(String[] argument)
	 *            System.out.println(Main.getTimeDate(535388001535000577L));
	 *            GuiDTC.start(); GuiInput.start(); AdvancedSettings.start();
	 *            CustomOutput.start();
	 *
	 **/

	public static GuiDTC.Status InfoToken(String token, int in) {
		JsonArray jsona;
		String email;
		String message;
		String og = token;
		if (token.contains(":")) {
			for (String check : token.split(":")) {
				if (check.length() == 59) {
					token = check;
					break;
				}
				if (check.length() != 88 || !check.startsWith("mfa."))
					continue;
				token = check;
				break;
			}
		}
		String response = HttpRequest.get("https://discordapp.com/api/v6/users/@me").contentType("application/json")
				.userAgent(userAgent).authorization(token).body();
		guiMain.addLine("# " + in);
		guiMain.addLine("Token: " + token);
		guiMain.addLine("");
		JsonObject json = new JsonParser().parse(response).getAsJsonObject();
		String string = message = json.get("message") != null ? json.get("message").getAsString() : "";
		if (!message.equals("")) {
			guiMain.addLine(message);
			guiMain.addLine("");
			return GuiDTC.Status.Invalid;
		}
		String username = json.get("username").getAsString();
		String discriminator = json.get("discriminator").getAsString();
		String verified = json.get("verified").getAsString();
		String string2 = email = json.get("email").isJsonNull() ? "null" : json.get("email").getAsString();
		String phone = json.get("phone") == null ? "null"
				: (json.get("phone").isJsonNull() ? "null" : json.get("phone").getAsString());
		String avatar = json.get("avatar").isJsonNull() ? "Null" : "\"Has Avatar\"";
		guiMain.addLine(username + "#" + discriminator);
		guiMain.addLine("Creation Time: " + Main.getTimeDate(json.get("id").getAsLong()));
		guiMain.addLine("Email Verified: " + verified);
		guiMain.addLine("Email: " + email);
		guiMain.addLine("Phone: " + phone);
		guiMain.addLine("Avatar: " + avatar);
		GuiDTC.Status status = GuiDTC.Status.Worked;
		if (GuiDTC.isCustom() && CustomOutput.gethasAvatarCheckBox() && avatar.equals("Null")) {
			status = GuiDTC.Status.Unverified;
		}
		if (GuiDTC.isCustom() && CustomOutput.gethasEmailCheckBox() && email.equals("null")) {
			status = GuiDTC.Status.Unverified;
		}
		if (GuiDTC.isCustom() && CustomOutput.getEmailVerifiedCheckBox() && verified.equals("false")) {
			status = GuiDTC.Status.Unverified;
		}
		if (GuiDTC.isCustom() && CustomOutput.getPhoneVerifiedCheckBox() && phone.equals("null")) {
			status = GuiDTC.Status.Unverified;
		}
		String flags = Flags.getFlags(json.get("flags") == null ? 0 : json.get("flags").getAsInt());
		guiMain.addLine("Badges: " + flags);
		String premium_type = Premium.getPremium((int) (json.get((String) "premium_type") == null ? 0
				: json.get((String) "premium_type").getAsInt())).DESCRIPTION;
		guiMain.addLine("Premium: " + premium_type);
		response = HttpRequest.get("https://discordapp.com/api/v6/users/@me/guilds").contentType("application/json")
				.userAgent(userAgent).authorization(token).body();
		System.out.println(response);
		JsonArray jsonArray = jsona = new JsonParser().parse(response).isJsonArray()
				? new JsonParser().parse(response).getAsJsonArray()
				: null;
		if (jsona != null) {
			guiMain.addLine("Guild Count: " + jsona.size());
			guiMain.addLine("");
			return status;
		}
		guiMain.addLine("Need to verify account");
		guiMain.addLine("");
		if (!GuiDTC.isCustom()) {
			return GuiDTC.Status.Unverified;
		}
		if (CustomOutput.getNotRequiredVerifyCheckBox()) {
			return GuiDTC.Status.Unverified;
		}
		return status;
	}

	private static OffsetDateTime getCreationTime(long entityId) {
		long timestamp = (entityId >>> 22) + 1420070400000L;
		Calendar gmt = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		gmt.setTimeInMillis(timestamp);
		return OffsetDateTime.ofInstant(gmt.toInstant(), gmt.getTimeZone().toZoneId());
	}

	public static String getTimeDate(long time) {
		return dateFormat.format(Main.getCreationTime(time).toInstant().toEpochMilli());
	}

	@Test
	public void time() {
		System.out.println(Main.getTimeDate(Long.parseLong("534955696370876416")));
	}

	static {
		tokens = new ArrayList<String>();
		dateFormat = new SimpleDateFormat("MMM dd, yyyy - HH:mm:ss");
	}

	private static enum Premium {
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

	private static enum Flags {
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