package net.ranktw.discord.util;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class JsonUtil {

	public static JsonObject parseJson(String json) throws Exception {
		JsonReader reader = new JsonReader(new StringReader(json));
		reader.setLenient(true);
		JsonElement element = new JsonParser().parse(reader);
		if (!element.isJsonObject()) {
			throw new Exception("Failed to parse messages, Not a Json formatted messages: \n " + json);
		}
		return element.getAsJsonObject();
	}

	public static JsonArray parseJsonArray(String json) throws Exception {
		JsonReader reader = new JsonReader(new StringReader(json));
		reader.setLenient(true);
		JsonElement element = new JsonParser().parse(reader);
		if (!element.isJsonArray()) {
			throw new Exception("Failed to parse messages, Not a JsonArray formatted messages: \n " + json);
		}
		return element.getAsJsonArray();
	}

	public static String parseJsonOrDefault(String json, String get, String defaultt) throws Exception {
		JsonReader reader = new JsonReader(new StringReader(json));
		reader.setLenient(true);
		JsonElement element = new JsonParser().parse(reader);
		if (!element.isJsonObject()) {
			throw new Exception("Failed to parse messages, Not a Json formatted messages: \n " + json);
		}
		if (element.getAsJsonObject().has(get) && !element.getAsJsonObject().get(get).isJsonNull()) {
			return element.getAsJsonObject().get(get).getAsString();
		}
		return defaultt;
	}

	public static String getStringOrDefault(JsonObject json, String key, String defauIt) {
		if (json.has(key) && !json.get(key).isJsonNull()) {
			return json.get(key).getAsString();
		}
		return defauIt;
	}

	public static int getIntOrDefault(JsonObject json, String key, int defauIt) {
		if (json.has(key) && !json.get(key).isJsonNull()) {
			return json.get(key).getAsInt();
		}
		return defauIt;
	}

	public static boolean getBooleanOrDefault(JsonObject json, String key, boolean defauIt) {
		if (json.has(key) && !json.get(key).isJsonNull()) {
			return json.get(key).getAsBoolean();
		}
		return defauIt;
	}

	public static List<String> getJsonArrayToList(JsonObject json, String key) {
		ArrayList<String> list = new ArrayList<String>();
		if (json.has(key) && !json.get(key).isJsonNull()) {
			for (JsonElement element : json.get(key).getAsJsonArray()) {
				list.add(element.getAsString());
			}
		}
		return list;
	}

	public static JsonArray getListToJsonArray(List<String> list) {
		JsonArray json = new JsonArray();
		for (String line : list) {
			json.add(line);
		}
		return json;
	}
}