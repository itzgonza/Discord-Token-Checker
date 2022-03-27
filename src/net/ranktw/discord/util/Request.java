package net.ranktw.discord.util;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.net.Proxy;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Response;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;

import net.ranktw.discord.User;
import net.ranktw.http.HttpRequest;

public class Request {

	private final String token;
	private int runs;
	private int max = 5;
	private static String UserAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36";
	private static String SuperProperties = "eyJvcyI6IldpbmRvd3MiLCJicm93c2VyIjoiQ2hyb21lIiwiZGV2aWNlIjoiIiwiYnJvd3Nlcl91c2VyX2FnZW50IjoiTW96aWxsYS81LjAgKFdpbmRvd3MgTlQgMTAuMDsgV2luNjQ7IHg2NCkgQXBwbGVXZWJLaXQvNTM3LjM2IChLSFRNTCwgbGlrZSBHZWNrbykgQ2hyb21lLzc2LjAuMzgwOS4xMzIgU2FmYXJpLzUzNy4zNiIsImJyb3dzZXJfdmVyc2lvbiI6Ijc2LjAuMzgwOS4xMzIiLCJvc192ZXJzaW9uIjoiMTAiLCJyZWZlcnJlciI6IiIsInJlZmVycmluZ19kb21haW4iOiIiLCJyZWZlcnJlcl9jdXJyZW50IjoiIiwicmVmZXJyaW5nX2RvbWFpbl9jdXJyZW50IjoiIiwicmVsZWFzZV9jaGFubmVsIjoic3RhYmxlIiwiY2xpZW50X2J1aWxkX251bWJlciI6NDQ5MzAsImNsaWVudF9ldmVudF9zb3VyY2UiOm51bGx9";
	private static String Track = "eyJvcyI6IldpbmRvd3MiLCJicm93c2VyIjoiQ2hyb21lIiwiZGV2aWNlIjoiIiwiYnJvd3Nlcl91c2VyX2FnZW50IjoiTW96aWxsYS81LjAgKFdpbmRvd3MgTlQgMTAuMDsgV2luNjQ7IHg2NCkgQXBwbGVXZWJLaXQvNTM3LjM2IChLSFRNTCwgbGlrZSBHZWNrbykgQ2hyb21lLzc2LjAuMzgwOS4xMzIgU2FmYXJpLzUzNy4zNiIsImJyb3dzZXJfdmVyc2lvbiI6Ijc2LjAuMzgwOS4xMzIiLCJvc192ZXJzaW9uIjoiMTAiLCJyZWZlcnJlciI6IiIsInJlZmVycmluZ19kb21haW4iOiIiLCJyZWZlcnJlcl9jdXJyZW50IjoiIiwicmVmZXJyaW5nX2RvbWFpbl9jdXJyZW50IjoiIiwicmVsZWFzZV9jaGFubmVsIjoic3RhYmxlIiwiY2xpZW50X2J1aWxkX251bWJlciI6OTk5OSwiY2xpZW50X2V2ZW50X3NvdXJjZSI6bnVsbH0=";
	private static String contentType = "application/json";
	private static String Language = "en-US";

	public Request(String token) {
		this.token = token;
	}

	public User getUser() throws Exception {
		try {
			ResponseContent response = Request.getRequest("https://discordapp.com/api/v6/users/@me",
					Headers.create().setDefaultContentType().setAuthorization(this.token).get());
			this.debug(response.print());
			if (response.getCode() == 200) {
				User user = (User) Request.fromJson(response.getContent());
				if (user.hasError()) {
					throw new Exception(user.getErrorMessage());
				}
				return user;
			}
			if (response.getContent().contains("\"message\": \"401: Unauthorized\"")) {
				throw new Unauthorized();
			}
			throw new Exception(response.getContent());
		} catch (SocketException | UnknownHostException | HttpRequest.HttpRequestException e) {
			if (this.runs++ < this.max) {
				if (Config.isDebug()) {
					System.out.println("Bad Proxy retry (" + this.runs + ") " + e.getMessage());
				}
				return this.getUser();
			}
			throw new Exception("Failed to connect discord, proxy might bad. " + e.getMessage());
		} catch (Unauthorized e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public List<String> getGuildID() throws Exception {
		try {
			ResponseContent response = Request.getRequest("https://discordapp.com/api/v6/users/@me/guilds",
					Headers.create().setAuthorization(this.token).setDefaultContentType().get());
			this.debug(response.print());
			if (response.getCode() == 200) {
				if (response.getContent().startsWith("[")) {
					ArrayList<String> list = new ArrayList<String>();
					if (response.getContent().equals("[]")) {
						return list;
					}
					JsonArray json = JsonUtil.parseJsonArray(response.getContent());
					for (JsonElement element : json) {
						list.add(element.getAsJsonObject().get("id").getAsString());
					}
					return list;
				}
				throw new Exception(response.getContent());
			}
			if (response.getContent().contains("\"message\": \"401: Unauthorized\"")) {
				throw new Unauthorized();
			}
			if (response.getContent().startsWith("{")) {
				User discordResponse = (User) Request.fromJson(response.getContent());
				throw new Exception(
						discordResponse.hasError() ? discordResponse.getErrorMessage() : response.getContent());
			}
			throw new Exception(response.getContent());
		} catch (SocketException | UnknownHostException | HttpRequest.HttpRequestException e) {
			if (this.runs++ < this.max) {
				if (Config.isDebug()) {
					System.out.println("Bad Proxy retry (" + this.runs + ") " + e.getMessage());
				}
				return this.getGuildID();
			}
			throw new Exception("Failed to connect discord, proxy might bad. " + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static void log(String log) {
		System.out.println(log);
	}

	private void debug(String debug) {
		if (Config.isDebug()) {
			System.out.println(debug);
		}
	}

	private static <T> T fromJson(String json) throws Exception {
		try {
			JsonReader reader = new JsonReader(new StringReader(json));
			reader.setLenient(true);
			return new Gson().fromJson(reader, (Type) ((Object) User.class));
		} catch (Exception e) {
			throw new Exception("Failed to parse Json messages: " + json + "\n" + e.getMessage());
		}
	}

	public static ResponseContent getRequest(String url) throws Exception {
		return Request.getRequest(url, null);
	}

	public static ResponseContent getRequest(String url, Map<String, String> headers) throws Exception {
		long start = System.currentTimeMillis();
		if (Config.isUseProxy()) {
			ProxyHttp proxyHttp = new ProxyHttp(Config.getProxy());
			switch (Config.getProxyType()) {
			case HTTP: {
				HttpHost proxy = new HttpHost(proxyHttp.getHost(), proxyHttp.getPort());
				Executor executor = Executor.newInstance();
				if (proxyHttp.isUserPass()) {
					executor.auth(proxy, proxyHttp.getUsername(), proxyHttp.getPassword());
				}
				org.apache.http.client.fluent.Request request = org.apache.http.client.fluent.Request.Get(url)
						.viaProxy(proxy).addHeader("Accept-Language", Language).userAgent(UserAgent);
				if (headers != null && !headers.isEmpty()) {
					for (Map.Entry<String, String> entry : headers.entrySet()) {
						request.addHeader(entry.getKey(), entry.getValue());
					}
				}
				Response response = executor.execute(request);
				return new ResponseContent(url, response.returnResponse(), System.currentTimeMillis() - start,
						proxyHttp.getProxyText());
			}
			}
			HttpRequest request = HttpRequest.get(url).useProxy(Proxy.Type.SOCKS, proxyHttp.getProxyLine())
					.userAgent(UserAgent).header("Accept-Language", Language);
			if (headers != null && !headers.isEmpty()) {
				request.headers(headers);
			}
			request.code();
			return new ResponseContent(url, request, System.currentTimeMillis() - start, proxyHttp.getProxyText());
		}
		HttpRequest request = HttpRequest.get(url).userAgent(UserAgent).header("Accept-Language", Language)
				.acceptJson();
		if (headers != null && !headers.isEmpty()) {
			request.headers(headers);
		}
		request.code();
		return new ResponseContent(url, request, System.currentTimeMillis() - start);
	}

	private static class Headers {
		Map<String, String> entry = new HashMap<String, String>();

		private Headers() {
		}

		public static Headers create() {
			return new Headers();
		}

		public Headers setAuthorization(String value) {
			this.entry.put("Authorization", value);
			return this;
		}

		public Headers setFingerprint(String value) {
			this.entry.put("x-fingerprint", value);
			return this;
		}

		public Headers setSuperProperties(String value) {
			this.entry.put("x-super-properties", value);
			return this;
		}

		public Headers setContextProperties(String value) {
			this.entry.put("x-context-properties", value);
			return this;
		}

		public Headers setContentType(String value) {
			this.entry.put("content-type", value);
			return this;
		}

		public Headers addHeader(String name, String value) {
			this.entry.put(name, value);
			return this;
		}

		public Headers setDefaultSuperProperties() {
			this.entry.put("x-super-properties", SuperProperties);
			return this;
		}

		public Headers setDefaultContentType() {
			this.entry.put("content-type", contentType);
			return this;
		}

		public Map<String, String> get() {
			return this.entry;
		}
	}

	public static class ProxyHttp {
		String hostname;
		int port;
		String username;
		String password;

		public ProxyHttp(String proxy) throws Exception {
			block4: {
				try {
					String[] split = proxy.split(":");
					if (split.length == 2) {
						this.hostname = split[0];
						this.port = Integer.parseInt(split[1]);
						break block4;
					}
					if (split.length == 4) {
						this.hostname = split[0];
						this.port = Integer.parseInt(split[1]);
						this.username = split[2];
						this.password = split[3];
						break block4;
					}
					throw new Exception("Bad Proxy formatted:");
				} catch (Exception e) {
					throw new Exception("Proxy Exception: " + e.getMessage() + " " + proxy);
				}
			}
		}

		public String getHost() {
			return this.hostname;
		}

		public int getPort() {
			return this.port;
		}

		public String getProxyLine() {
			return this.hostname + ":" + this.port;
		}

		public String getProxyText() {
			return this.hostname + ":" + this.port
					+ (this.isUserPass() ? " " + this.getUsername() + ":" + this.getPassword() : "");
		}

		public boolean isUserPass() {
			return this.username != null;
		}

		public String getUsername() {
			return this.username;
		}

		public String getPassword() {
			return this.password;
		}
	}
}