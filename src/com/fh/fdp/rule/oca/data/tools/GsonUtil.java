package com.fh.fdp.rule.oca.data.tools;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

public class GsonUtil {

	private static Gson gson = new GsonBuilder().disableHtmlEscaping().serializeNulls().create();

	private GsonUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static String toStr(Object obj) {
		try {
			return gson.toJson(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static <T> T fromJson(String src, Class<T> classT) {
		return gson.fromJson(src, classT);
	}

	public static <T> T fromJson(JsonElement obj, Class<T> classT) {
		return gson.fromJson(obj, classT);
	}

	public static <T> T fromJson(String src, Type typeOfT) {
		return gson.fromJson(src, typeOfT);
	}

	public static <T> T fromJson(JsonElement src, Type typeOfT) {
		return gson.fromJson(src, typeOfT);
	}
}
