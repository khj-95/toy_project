package com.kh.toy.common.exception;

import com.kh.toy.common.code.ErrorCode;

// 예외처리가 강제되지 않는 UnCheckedException
// DAO에서 SQL Exception 대신 DataAccessException 을 반환해서, Service단에서 예외처리가 강제되는 것을 방지
public class DataAccessException extends HandlableException{
	
	private static final long serialVersionUID = 1L;

	public DataAccessException(Exception e) {
		super(ErrorCode.DATABASE_ACESS_ERROR, e);
	}
}
