package com.crossover.trial.weather.parser;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.Test;

import com.crossover.trial.weather.model.AirportData;

public class FileParserTest {

	@Test
	public void testParse() throws IOException {
		String test = "1,\"General Edward Lawrence Logan Intl\",\"Boston\",\"United States\","
				+ "\"BOS\",\"KBOS\",42.364347,-71.005181,19,-5,\"A\"";
		InputStream stream = new ByteArrayInputStream(test.getBytes(StandardCharsets.UTF_8));
		
		List<AirportData> datas = FileParser.parse(stream, AirportData.class);
		
		assertEquals(1, datas.size());
		
		AirportData data = datas.get(0);
		assertEquals("BOS", data.getIata());
		assertEquals(42.364347, data.getLatitude(), 0.0);
		assertEquals(-71.005181, data.getLongitude(), 0.0);
	}

}
