package net.ranktw.discord.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;

public class Config {

	private static Config config = Config.initDefaultSettings();
	private static String path = "config";
	private static List<String> proxies;
	@SerializedName(value = "proxy_type")
	private Proxy.Type type;
	@SerializedName(value = "use_proxy")
	private boolean useProxy;
	@SerializedName(value = "use_multi_threaded")
	private boolean useMultiThreaded;
	@SerializedName(value = "threads")
	private int threads;
	@SerializedName(value = "duplicate_tokens")
	private boolean duplicateTokens;
	@SerializedName(value = "location")
	private boolean location;
	@SerializedName(value = "avatar")
	private boolean avatar;
	@SerializedName(value = "debug")
	private boolean debug;
	@SerializedName(value = "ui")
	private UI ui;

	private static Config getInstance() {
		return config;
	}

	private static Config initDefaultSettings() {
		Config config = new Config();
		config.type = Proxy.Type.HTTP;
		config.useProxy = false;
		proxies = new ArrayList<String>();
		config.useMultiThreaded = true;
		config.threads = 3;
		config.duplicateTokens = true;
		config.location = true;
		config.avatar = true;
		config.debug = true;
		return config;
	}

	private static Config fromJson(String json) {
		JsonReader reader = new JsonReader(new StringReader(json));
		reader.setLenient(true);
		return (Config) new Gson().fromJson(reader, (Type) ((Object) Config.class));
	}

	public static void initSettings() throws Exception {
		String json = null;
		System.out.println("Loading Settings");
		try {
			if (Util.isExists(path)) {
				json = Util.loadFileToString(path);
				config = Config.fromJson(json);
				FileUtil.checkFileExistOrCreate(new File("proxies.txt"));
				if (new File("proxies.txt").exists()) {
					Config.loadProxies(Util.loadFile(Util.readFileIfNotExists("proxies.txt")));
				}
				return;
			}
		} catch (Exception e) {
			config = Config.initDefaultSettings();
			if (json != null) {
				json = Util.toErrorValue(json);
			}
			throw new Exception("Has an Error while loading Config: " + (json == null ? "" : "\n |? Value: " + json)
					+ "\n |_ " + e.getMessage());
		}
		config = Config.initDefaultSettings();
		System.out.println("Saving Default Settings");
		Config.saveSettings();
	}

	public static void saveSettings() throws Exception {
		Util.writeFile(path, Config.toJson());
	}

	public static String toJson() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson((Object) Config.getInstance(), (Type) ((Object) Config.class));
	}

	public static void loadProxies(List<String> proxies) {
		Config.proxies = proxies;
	}

	public static void saveProxies() throws Exception {
		Util.writeListToFile("config/proxies.txt", proxies);
	}

	public static int getProxiesSize() {
		return proxies.size();
	}

	public static boolean isUseProxy() {
		return Entry.getOrDefault(Config.config.useProxy, false);
	}

	public static Proxy.Type getProxyType() {
		return Entry.getOrDefault(Config.config.type, Proxy.Type.HTTP);
	}

	public static int getProxyTypeId() {
		return Config.getProxyType() == Proxy.Type.HTTP ? 0 : 1;
	}

	public static String getProxy() throws Exception {
		if (proxies.isEmpty()) {
			throw new Exception("No Proxy Loaded.");
		}
		return proxies.get(new Random().nextInt(proxies.size()));
	}

	public static boolean isMultiThreaded() {
		return Entry.getOrDefault(Config.config.useMultiThreaded, false);
	}

	public static int getThreads() {
		return Entry.getOrDefault(Config.config.threads, 6);
	}

	public static boolean isDuplicateTokens() {
		return Entry.getOrDefault(Config.config.duplicateTokens, true);
	}

	public static void setUseProxy(boolean useProxy) {
		Config.config.useProxy = useProxy;
	}

	public static void setProxyType(int type) {
		Config.config.type = type == 0 ? Proxy.Type.HTTP : Proxy.Type.SOCKS;
	}

	public static void setUseMultiThreaded(boolean useMultiThreaded) {
		Config.config.useMultiThreaded = useMultiThreaded;
	}

	public static void setThreads(int threads) {
		Config.config.threads = threads;
	}

	public static void setDuplicateTokens(boolean duplicateTokens) {
		Config.config.duplicateTokens = duplicateTokens;
	}

	public static boolean isLocation() {
		return Entry.getOrDefault(Config.config.location, true);
	}

	public static void setLocation(boolean location) {
		Config.config.location = location;
	}

	public static boolean isAvatar() {
		return Entry.getOrDefault(Config.config.avatar, true);
	}

	public static void setAvatar(boolean avatar) {
		Config.config.avatar = avatar;
	}

	public static boolean isDebug() {
		return Entry.getOrDefault(Config.config.debug, true);
	}

	public static void setDebug(boolean debug) {
		Config.config.debug = debug;
	}

	public static UI getUI() {
		return Entry.getOrDefault(Config.config.ui, null);
	}

	public static void setUI(UI x) {
		Config.config.ui = x;
	}

	public static class Util {
		private boolean b;

		public static boolean isExists(String filepath) {
			return new File(filepath).exists();
		}

		public static String loadFileToString(String filepath) throws Exception {
			StringBuilder text = new StringBuilder();
			try {
				FileReader fileReader = new FileReader(filepath);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				String line = null;
				boolean first = true;
				while ((line = bufferedReader.readLine()) != null) {
					if (!first) {
						text.append("\n");
					}
					first = false;
					text.append(line);
				}
				bufferedReader.close();
			} catch (FileNotFoundException ex) {
				throw new Exception("Unable to open file '" + filepath + "'");
			} catch (IOException ex) {
				throw new Exception("Error reading file '" + filepath + "'");
			}
			return text.toString();
		}

		public static File readFileIfNotExists(String filepath) throws Exception {
			File file = new File(filepath);
			if (!file.exists()) {
				boolean exception = false;
				try {
					file.getParentFile().mkdirs();
				} catch (Exception exception2) {
					// empty catch block
				}
				try {
					file.getParentFile().mkdir();
				} catch (Exception exception3) {
					// empty catch block
				}
				try {
					file.createNewFile();
				} catch (Exception ignored) {
					exception = true;
				}
				if (exception) {
					throw new Exception("Failed to create new file, Path: " + filepath);
				}
			}
			return file;
		}

		public static void writeFile(String filepath, String write) throws Exception {
			try {
				File file = Util.readFileIfNotExists(filepath);
				FileWriter writer = new FileWriter(file, false);
				writer.write(write);
				writer.close();
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}

		public static void writeListToFile(String filepath, List<String> list) throws Exception {
			try {
				File file = Util.readFileIfNotExists(filepath);
				FileWriter writer = new FileWriter(file, false);
				Util util = new Util();
				for (String line : list) {
					writer.write(util.nextLine() + line);
					writer.flush();
				}
				writer.close();
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}

		private String nextLine() {
			if (!this.b) {
				this.b = true;
				return "";
			}
			return System.lineSeparator();
		}

		public static List<String> loadFile(File file) {
			ArrayList<String> list = new ArrayList<String>();
			String filepath = file.getAbsolutePath();
			try {
				FileReader fileReader = new FileReader(filepath);
				BufferedReader bufferedReader = new BufferedReader(fileReader);
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
					list.add(line);
				}
				bufferedReader.close();
			} catch (FileNotFoundException ex) {
				System.out.println("Unable to open file '" + filepath + "'");
			} catch (IOException ex) {
				System.out.println("Error reading file '" + filepath + "'");
			}
			return list;
		}

		public static String toErrorValue(String error) {
			StringBuilder value = new StringBuilder();
			boolean first = true;
			for (String s : error.split("\n")) {
				if (first) {
					first = false;
					value.append(s);
					continue;
				}
				value.append("\n | ").append(s);
			}
			return value.toString();
		}
	}

	public static class UI {
		@SerializedName(value = "x")
		private int x;
		@SerializedName(value = "y")
		private int y;
		@SerializedName(value = "width")
		private int width;
		@SerializedName(value = "height")
		private int height;
		@SerializedName(value = "divider_location")
		private int dividerLocation;
		@SerializedName(value = "table0_width")
		private int tableWidth0;
		@SerializedName(value = "table1_width")
		private int tableWidth1;

		public int getX() {
			return this.x;
		}

		public int getY() {
			return this.y;
		}

		public int getWidth() {
			return this.width;
		}

		public int getHeight() {
			return this.height;
		}

		public int getDividerLocation() {
			return this.dividerLocation;
		}

		public int getTableWidth0() {
			return this.tableWidth0;
		}

		public int getTableWidth1() {
			return this.tableWidth1;
		}

		public void setX(int x) {
			this.x = x;
		}

		public void setY(int y) {
			this.y = y;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public void setDividerLocation(int dividerLocation) {
			this.dividerLocation = dividerLocation;
		}

		public void setTableWidth0(int tableWidth0) {
			this.tableWidth0 = tableWidth0;
		}

		public void setTableWidth1(int tableWidth1) {
			this.tableWidth1 = tableWidth1;
		}
	}

	private static class Entry {
		private Entry() {
		}

		static <T> T getOrDefault(T value, T defaultValue) {
			return value != null ? value : defaultValue;
		}
	}
}