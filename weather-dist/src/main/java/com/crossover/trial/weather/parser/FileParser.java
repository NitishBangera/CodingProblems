package com.crossover.trial.weather.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class FileParser {
	private final static Logger LOGGER = Logger.getLogger(FileParser.class);

	public static <T extends FileReadable> List<T> parse(InputStream airportDataStream, Class<T> clazz)
			throws IOException {
		Field[] fields = clazz.getDeclaredFields();
		Map<Integer, Field> mapOfIndexAndField = new HashMap<>();
		for (Field field : fields) {
			field.setAccessible(true);
			if (field.isAnnotationPresent(Index.class)) {
				Index indexVal = field.getAnnotation(Index.class);
				mapOfIndexAndField.put(indexVal.value(), field);
			}
		}

		List<T> parsedData = new LinkedList<>();
		String className = clazz.getSimpleName();
		LOGGER.info("Loading " + className + " data.");
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(airportDataStream))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] data = line.split(",");
				T obj = clazz.newInstance();
				for (Map.Entry<Integer, Field> entry : mapOfIndexAndField.entrySet()) {
					int index = entry.getKey();
					Field field = entry.getValue();
					Object value = parseValue(field, data[index]);
					field.set(obj, value);
				}
				parsedData.add(obj);
			}
		} catch (InstantiationException e) {
			LOGGER.error(e);
		} catch (IllegalAccessException e) {
			LOGGER.error(e);
		}

		LOGGER.info(String.format("Loaded %s %s entries", parsedData.size(), className));
		return parsedData;
	}

	private static Object parseValue(Field field, String data) {
		Class<?> clazz = field.getType();
		switch (clazz.getSimpleName().toLowerCase()) {
		case "string":
			return data.replaceAll("^\"|\"$", "");
		case "long":
			return Long.parseLong(data);
		case "integer":
			return Integer.parseInt(data);
		case "int":
			return Integer.parseInt(data);
		case "double":
			return Double.parseDouble(data);
		case "biginteger":
			return new BigInteger(data);
		default:
			LOGGER.error("Type cannot be parsed : " + clazz);
		}
		return data;
	}
}
