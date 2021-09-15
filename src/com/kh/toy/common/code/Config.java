package com.kh.toy.common.code;

public enum Config {
	
	//DOMAIN("http://www.pclass.com"),
	DOMAIN("http://localhost:7070"),
	SMTP_AUTHENTICATION_ID("kim37171@cuk.edu"),
	SMTP_AUTHENTICATION_PASSWORD("hyunjin95*"),
	COMPANY_EMAIL("kim37171@cuk.edu"),
	//UPLOAD_PATH("C:\\CODE\\upload\\"); 운영서버
	UPLOAD_PATH("C:\\CODE\\upload\\"); //개발서버
	
	public final String DESC;
	
	private Config(String desc) {
		this.DESC = desc;
	}

}
