package com.crossover.trial.weather.exception;

/**
 * An internal exception marker
 */
public class WeatherException extends Exception {
	private static final long serialVersionUID = 4054970260244633647L;

	public WeatherException(String exceptionMsg) {
		super(exceptionMsg);
	}

	public WeatherException(String exceptionMsg, Throwable cause) {
		super(exceptionMsg, cause);
	}
}
