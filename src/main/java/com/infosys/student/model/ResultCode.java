package com.infosys.student.model;

public enum ResultCode {
	SUCCESS("200", "OK"),

	CREATED("201", "CREATED"),

	ACCEPTED("202", "ACCEPTED"),

	NO_CONTENT("204", "NO CONTENT"),

	BAD_REQUEST("400", "BAD REQUEST"),

	UNAUTHORIZED("401", "UNAUTHORIZED"),

	PARAMS_ERROR("402", "INVALID PARAMETER"),

	FORBIDDEN("403", "FORBIDDEN"),

	NOT_FUND("404", "RESOURCE NOT FUND"),

	NOT_ALLOWED("405", "THE CLIENT TRIED TO USE A HTTP METHOD THAT THE RESOURCE DOES NOT ALLOWED"),

	NOT_ACCEPTABLE("406", "API IS NOT ABLE TO GENERATE ANY OF CLIENT'S PREFFERRED MEDIA TYPES"),

	CONFLICT("409", "EXISTING/CONFLICT RESOURCE"),

	PRECONDITION_FAILD("412", "PRECONDITION FAILD"),

	NOT_SUPPORTED("415", "NOT SUPPORTED"),

	NOT_LOGIN("443", "NOT LOGIN"),

	EXCEPTION("444", "ERROR OCCURD"), SYS_ERROR("445", "SYSTEM ERROR"),

	INVALID_PARAMETER("444", "INVALID PARAMETER"),

	TOO_FREQUENT("445", "TOO REQUENT, TRY LATER"),

	UNKOWN_ERROR("499", "UNKOWN ERROR"),

	SERVER_ERROR("500", "Internal Server Error"),

	NOT_IMPLEMENTED("501", "NOR IMPLEMENTED CURRENTLY");

	private ResultCode(String value, String msg) {
		this.val = value;
		this.msg = msg;
	}

	public String val() {
		return val;
	}

	public String msg() {
		return msg;
	}

	private String val;
	private String msg;
}
