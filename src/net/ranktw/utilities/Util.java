package net.ranktw.utilities;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.jsoup.Jsoup;

import com.google.gson.JsonObject;

import net.ranktw.http.HttpRequest;

public class Util {

	public static final boolean debug = true;
	public static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd ? HH:mm:ss");
	public static DecimalFormat numberFormat = new DecimalFormat("###,##0.###");
	public static DecimalFormat $ = new DecimalFormat("$###,##0.###");
	public static String USERAGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36";
	public static String SUPERPROPERTIES = "eyJvcyI6IldpbmRvd3MiLCJicm93c2VyIjoiQ2hyb21lIiwiZGV2aWNlIjoiIiwiYnJvd3Nlcl91c2VyX2FnZW50IjoiTW96aWxsYS81LjAgKFdpbmRvd3MgTlQgMTAuMDsgV2luNjQ7IHg2NCkgQXBwbGVXZWJLaXQvNTM3LjM2IChLSFRNTCwgbGlrZSBHZWNrbykgQ2hyb21lLzc2LjAuMzgwOS4xMDAgU2FmYXJpLzUzNy4zNiIsImJyb3dzZXJfdmVyc2lvbiI6Ijc2LjAuMzgwOS4xMDAiLCJvc192ZXJzaW9uIjoiMTAiLCJyZWZlcnJlciI6IiIsInJlZmVycmluZ19kb21haW4iOiIiLCJyZWZlcnJlcl9jdXJyZW50IjoiIiwicmVmZXJyaW5nX2RvbWFpbl9jdXJyZW50IjoiIiwicmVsZWFzZV9jaGFubmVsIjoic3RhYmxlIiwiY2xpZW50X2J1aWxkX251bWJlciI6NDM4ODQsImNsaWVudF9ldmVudF9zb3VyY2UiOm51bGx9";
	public static long ONE_SECOND = 1000L;
	public static long ONE_MINUTE = ONE_SECOND * 60L;
	public static long ONE_HOUR = ONE_MINUTE * 60L;
	public static long ONE_DAY = ONE_HOUR * 24L;
	private boolean b;

	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static String millisFormatted(long millis) {
		long secondsInMilli = 1000L;
		long minutesInMilli = secondsInMilli * 60L;
		long hoursInMilli = minutesInMilli * 60L;
		long daysInMilli = hoursInMilli * 24L;
		long elapsedDays = millis / daysInMilli;
		long elapsedHours = (millis %= daysInMilli) / hoursInMilli;
		long elapsedMinutes = (millis %= hoursInMilli) / minutesInMilli;
		long elapsedSeconds = (millis %= minutesInMilli) / secondsInMilli;
		return String.format("%d days %d hours %d minutes %d seconds", elapsedDays, elapsedHours, elapsedMinutes,
				elapsedSeconds);
	}

	public static String millisToLongDHMS(long duration) {
		StringBuilder res = new StringBuilder();
		long temp = 0L;
		if (duration >= ONE_SECOND) {
			temp = duration / ONE_DAY;
			if (temp > 0L) {
				res.append(temp).append(" day").append(temp > 1L ? "s" : "")
						.append((duration -= temp * ONE_DAY) >= ONE_MINUTE ? ", " : "");
			}
			if ((temp = duration / ONE_HOUR) > 0L) {
				res.append(temp).append(" hour").append(temp > 1L ? "s" : "")
						.append((duration -= temp * ONE_HOUR) >= ONE_MINUTE ? ", " : "");
			}
			if ((temp = duration / ONE_MINUTE) > 0L) {
				duration -= temp * ONE_MINUTE;
				res.append(temp).append(" min").append(temp > 1L ? "s" : "");
			}
			if (!res.toString().equals("") && duration >= ONE_SECOND) {
				res.append(" and ");
			}
			if ((temp = duration / ONE_SECOND) > 0L) {
				res.append(temp).append(" sec");
			}
			return res.toString();
		}
		return "0 second";
	}

	public static String getIP() {
		return HttpRequest.get("https://api.myip.com/").acceptJson().userAgent(USERAGENT).body();
	}

	public static String getIP(String proxy) {
		return HttpRequest.get("https://api.myip.com/").useProxy(Proxy.Type.HTTP, proxy).acceptJson()
				.userAgent(USERAGENT).body();
	}

	public static void openLink(String link) {
		try {
			Desktop.getDesktop().browse(new URL(link).toURI());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void openFile(String path) {
		try {
			Desktop.getDesktop().open(new File(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String base64Encoding(File file) throws IOException {
		byte[] data = Util.readFully(file);
		return "data:image/png;base64," + new String(Base64.getEncoder().encode(data), "UTF-8");
	}

	private static byte[] readFully(File file) throws IOException {
		int offset;
		int numRead;
		FileInputStream is = new FileInputStream(file);
		long length = file.length();
		if (length > Integer.MAX_VALUE) {
			throw new IOException("Cannot read the file into memory completely due to it being too large!");
		}
		byte[] bytes = new byte[(int) length];
		boolean var7 = false;
		for (offset = 0; offset < bytes.length
				&& (numRead = ((InputStream) is).read(bytes, offset, bytes.length - offset)) >= 0; offset += numRead) {
		}
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file " + file.getName());
		}
		((InputStream) is).close();
		byte[] var8 = bytes;
		return var8;
	}

	public static String urlEncode(String str) {
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String parseHTML(Object message) {
		if (message.toString().toLowerCase().startsWith("<!doctype html>")) {
			return Jsoup.parse(message.toString().replaceAll("^\\[[0-9]+] ", "")).text();
		}
		return message.toString();
	}

	public static String getHTML(String message) {
		return Jsoup.parse(message.replace("<br>", ";#;")).text().replace(";#;", "\n");
	}

	public static String getRequest(String url) {
		return HttpRequest.get((CharSequence) url, true, new Object[0]).acceptJson().userAgent(USERAGENT).body();
	}

	public static List<String> loadFile(String filepath) {
		ArrayList<String> list = new ArrayList<String>();
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

	public static List<File> loadImageFiles(String directoryPath) throws Exception {
		ArrayList<File> list = new ArrayList<File>();
		try {
			File file = new File(directoryPath);
			if (!file.isDirectory()) {
				throw new Exception("'" + directoryPath + "' is not a directory.");
			}
			if (file.listFiles() != null) {
				for (File f : file.listFiles()) {
					if (!f.isFile() || !f.getName().endsWith(".png") && !f.getName().endsWith(".jpg")
							&& !f.getName().endsWith(".jpeg") && !f.getName().endsWith(".gif"))
						continue;
					list.add(f);
				}
			}
		} catch (FileNotFoundException ex) {
			throw new Exception("Unable to open file '" + directoryPath + "'");
		} catch (IOException ex) {
			throw new Exception("Error reading file '" + directoryPath + "'");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return list;
	}

	public static List<File> loadTxtFiles(String directoryPath) throws Exception {
		ArrayList<File> list;
		block8: {
			list = new ArrayList<File>();
			try {
				File fileAll = new File(directoryPath);
				if (!fileAll.isDirectory()) {
					throw new Exception("'" + directoryPath + "' is not a directory.");
				}
				if (fileAll.listFiles() == null)
					break block8;
				for (File fileCheck : fileAll.listFiles()) {
					if (fileCheck.isDirectory()) {
						if (fileCheck.listFiles() == null)
							continue;
						for (File fin : fileCheck.listFiles()) {
							if (!fin.isFile() || !fin.getName().endsWith("accounts.txt"))
								continue;
							list.add(fin);
						}
						continue;
					}
					if (!fileCheck.isFile() || !fileCheck.getName().endsWith(".txt"))
						continue;
					list.add(fileCheck);
				}
			} catch (FileNotFoundException ex) {
				throw new Exception("Unable to open file '" + directoryPath + "'");
			} catch (IOException ex) {
				throw new Exception("Error reading file '" + directoryPath + "'");
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}
		return list;
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

	public static void createDirectory(String filepath) throws Exception {
		try {
			new File(filepath).mkdirs();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean isExists(String filepath) {
		return new File(filepath).exists();
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

	public static void addLineToFile(String filepath, List<String> list) throws Exception {
		try {
			List<String> load = Util.loadFile(filepath);
			File file = Util.readFileIfNotExists(filepath);
			FileWriter writer = new FileWriter(file, false);
			Util util = new Util();
			for (String line : load) {
				writer.write(util.nextLine() + line);
			}
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

	public static void setCopyTextToClipboard(String text) {
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(text), null);
	}

	public static String getClipBoard() {
		try {
			return (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static List<String> getClipBoardList() throws IOException, UnsupportedFlavorException {
		ArrayList<String> list = new ArrayList<String>();
		try {
			String[] o = ((String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor))
					.split("\n");
			list.addAll(Arrays.asList(o));
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}

	public static void showERRORMessage(Component parentComponent, String message) {
		JOptionPane.showMessageDialog(parentComponent, message, "Error", 0);
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

	public static void end(int status) {
		System.exit(status);
	}

	public static class HWID {
		private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

		public static String getOS() {
			JsonObject json = new JsonObject();
			json.addProperty("os", System.getProperty("os.name"));
			json.addProperty("computer", System.getenv("COMPUTERNAME"));
			json.addProperty("username", System.getProperty("user.name"));
			json.addProperty("java", Runtime.class.getPackage().getImplementationVersion());
			return json.toString();
		}

		public static String getHexHWID() {
			return HWID.bytesToHex(HWID.generateHWID());
		}

		private static byte[] generateHWID() {
			try {
				MessageDigest hash = MessageDigest.getInstance("MD5");
				String s = System.getProperty("os.name") + System.getProperty("os.arch")
						+ System.getProperty("os.version") + Runtime.getRuntime().availableProcessors()
						+ System.getenv("PROCESSOR_IDENTIFIER") + System.getenv("PROCESSOR_ARCHITECTURE")
						+ System.getenv("PROCESSOR_ARCHITEW6432") + System.getenv("NUMBER_OF_PROCESSORS")
						+ System.getenv("COMPUTERNAME") + System.getProperty("user.name");
				String OS = System.getProperty("os.name").toLowerCase();
				try {
					if (OS.contains("win")) {
						s = s + HWID.win();
					} else if (OS.contains("mac")) {
						s = s + HWID.mac();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return hash.digest(s.getBytes());
			} catch (Exception e) {
				throw new Error("Algorithm wasn't found.", e);
			}
		}

		private static byte[] hexStringToByteArray(String s) {
			int len = s.length();
			byte[] data = new byte[len / 2];
			for (int i = 0; i < len; i += 2) {
				data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
			}
			return data;
		}

		private static String bytesToHex(byte[] bytes) {
			char[] hexChars = new char[bytes.length * 2];
			for (int j = 0; j < bytes.length; ++j) {
				int v = bytes[j] & 0xFF;
				hexChars[j * 2] = hexArray[v >>> 4];
				hexChars[j * 2 + 1] = hexArray[v & 0xF];
			}
			return new String(hexChars);
		}

		private static String win() throws IOException {
			String command = "wmic csproduct get UUID";
			StringBuilder output = new StringBuilder();
			Process SerNumProcess = Runtime.getRuntime().exec(command);
			BufferedReader sNumReader = new BufferedReader(new InputStreamReader(SerNumProcess.getInputStream()));
			String line = "";
			while ((line = sNumReader.readLine()) != null) {
				output.append(line).append("\n");
			}
			return output.toString().substring(output.indexOf("\n"), output.length()).trim();
		}

		private static String mac() throws IOException, InterruptedException {
			String command = "system_profiler SPHardwareDataType | awk '/UUID/ { print $3; }'";
			StringBuilder output = new StringBuilder();
			Process SerNumProcess = Runtime.getRuntime().exec(command);
			BufferedReader sNumReader = new BufferedReader(new InputStreamReader(SerNumProcess.getInputStream()));
			String line = "";
			while ((line = sNumReader.readLine()) != null) {
				output.append(line).append("\n");
			}
			String MachineID = output.toString().substring(output.indexOf("UUID: "), output.length()).replace("UUID: ",
					"");
			SerNumProcess.waitFor();
			sNumReader.close();
			return MachineID;
		}

		private static String hwid() {
			String s = null;
			try {
				Process exec = Runtime.getRuntime().exec("wmic cpu get ProcessorId");
				exec.getOutputStream().close();
				Scanner scanner = new Scanner(exec.getInputStream());
				if (scanner.hasNext()) {
					scanner.next();
				}
				String s2 = scanner.hasNext() ? scanner.next() : "";
				scanner.close();
				Process exec2 = Runtime.getRuntime().exec("wmic diskdrive get serialnumber");
				exec2.getOutputStream().close();
				Scanner scanner2 = new Scanner(exec2.getInputStream());
				if (scanner2.hasNext()) {
					scanner2.next();
				}
				String s3 = scanner2.hasNext() ? scanner2.next() : "";
				scanner2.close();
				s = Base64.getEncoder().encodeToString((s2 + s3).getBytes());
				s = s + "\n" + s2 + " " + s3;
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			return s;
		}
	}
}