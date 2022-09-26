package com.github.theprogmatheus.zonadelivery.util;

import lombok.Getter;

@Getter
public class ProcessedServiceResult<R> {

	private ProcessedServiceResultType status;
	private String message;
	private R result;

	public ProcessedServiceResult<R> status(ProcessedServiceResultType status) {
		this.status = status;
		return this;
	}

	public ProcessedServiceResult<R> message(String message) {
		this.message = message;
		return this;
	}

	public ProcessedServiceResult<R> result(R result) {
		this.result = result;
		return this;
	}

	public enum ProcessedServiceResultType {
		SUCCESS, FAIL
	}

}
