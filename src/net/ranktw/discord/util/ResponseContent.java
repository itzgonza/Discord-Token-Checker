package net.ranktw.discord.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import net.ranktw.http.HttpRequest;

public class ResponseContent {

	private String content;
	private String status;
	private String url;
	private String proxy;
	private int code;
	private long connection_timed;

	public ResponseContent(String url, HttpResponse httpResponse, long connection_timed) throws Exception {
		this(url, httpResponse, connection_timed, null);
	}

	public ResponseContent(String url, HttpResponse httpResponse, long connection_timed, String proxy)
			throws Exception {
		this.url = url;
		this.proxy = proxy;
		this.code = httpResponse.getStatusLine().getStatusCode();
		this.connection_timed = connection_timed;
		this.status = String.format("[Status %s/%s]", httpResponse.getStatusLine().getStatusCode(),
				httpResponse.getStatusLine().getReasonPhrase());
		if (this.code == 204) {
			return;
		}
		try {
			int length;
			InputStream is = httpResponse.getEntity().getContent();
			ByteArrayOutputStream result = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			while ((length = is.read(buffer)) != -1) {
				result.write(buffer, 0, length);
			}
			result.close();
			EntityUtils.consume(httpResponse.getEntity());
			this.content = result.toString("UTF-8");
		} catch (Exception e) {
			throw new Exception("Has an Error while parsing Http Response: \n |? Status: " + this.getStatus()
					+ (this.content == null ? "" : "\n |? Content: " + this.content) + "\n |_ " + e.getMessage());
		}
	}

	public ResponseContent(String url, HttpRequest request, long connection_timed) throws Exception {
		this(url, request, connection_timed, null);
	}

	public ResponseContent(String url, HttpRequest request, long connection_timed, String proxy) throws Exception {
		this.url = url;
		this.proxy = proxy;
		this.code = request.code();
		this.connection_timed = connection_timed;
		this.status = String.format("[Status /%s]", this.code);
		this.content = request.body();
	}

	public String getContent() {
		return this.content;
	}

	public String getStatus() {
		return this.status;
	}

	public int getCode() {
		return this.code;
	}

	public long getConnectionTimed() {
		return this.connection_timed;
	}

	public String getProxy() {
		return this.proxy;
	}

	public boolean usedProxy() {
		return this.proxy != null;
	}

	private boolean isHtml() {
		return this.content.toLowerCase().startsWith("<!doctype html");
	}

	private boolean isJson() {
		return this.content.startsWith("{") && this.content.endsWith("}")
				|| this.content.startsWith("[{") && this.content.endsWith("}]");
	}

	private String getHTML() {
		return Jsoup.parse(this.content.replace("<br>", ";#;").replace("</div>", ";#;")).text().replace(";#;", "\n");
	}

	private String getJson() {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(new JsonParser().parse(this.content));
	}

	public String print() {
		StringBuilder value = new StringBuilder();
		if (this.content != null) {
			String text = this.isHtml() ? this.getHTML() : (this.isJson() ? this.getJson() : this.content);
			String[] split = text.split("\n");
			for (int i = 0; i < split.length; ++i) {
				String s = split[i];
				if (i == split.length - 1) {
					value.append("\n |_ ").append(s);
					continue;
				}
				if (s.equals("") || s.equals(" "))
					continue;
				value.append("\n |  ").append(s);
			}
		}
		return String.format("%s %s %s%s%s", this.getStatus(), this.connection_timed + "ms", this.url,
				this.usedProxy() ? " Proxy: " + this.proxy : "",
				this.content == null || this.content.equals("") ? "" : value.toString());
	}
}