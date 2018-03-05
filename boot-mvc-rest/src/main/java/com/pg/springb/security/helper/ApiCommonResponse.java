package com.citi.frontier.rest.helper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ApiCommonResponse<T> {

	private String error_num = Error.NUM.val();
	private String error_msg = Error.MSG.val();
	private T result = null;

	public ApiCommonResponse(T result, Error...errs) {
		
		List<Error> errLst = Arrays.asList(errs);
		Optional<Error> errNumOpts = errLst.stream()
											.filter(err -> Error.NUM.toString().equals(err.toString()))
											.findAny();

		Optional<Error> errMsgOpts = errLst.stream()
											.filter(err -> Error.MSG.toString().equals(err.toString()))
											.findAny();
									
		if(errNumOpts.isPresent()) {
			this.error_num = errNumOpts.get().val();
		}else {
			Error.NUM.set("0");
			this.error_num = Error.NUM.val();
		}
		
		if(errMsgOpts.isPresent()) {
			this.error_msg = errMsgOpts.get().val();
		}else {
			Error.MSG.set("");
			this.error_msg = Error.MSG.val();
		}
		/*for(Error err : errs) {
			if(Error.NUM.toString().equals(err.toString())){
				this.error_num = err.val();
			}else if(Error.MSG.toString().equals(err.toString())) {
				this.error_msg = err.val();
			}
		}*/
		this.result = result;
	}
	/*
	public static <T> ApiCommonResponse<T> create(T result, Error...errs) {
		final ApiCommonResponse<T> _this = new ApiCommonResponse<T>();
		
		for(Error err : errs) {
			if(Error.NUM.toString().equals(err.toString())){
				_this.error_num = err.val();
			}else if(Error.MSG.toString().equals(err.toString())) {
				_this.error_msg = err.val();
			}
		}

		_this.result = result;

		return _this;
	}*/
	
	public String toString(){
		return "error_num " + this.error_num + " , error_msg " + this.error_msg + " , result " + this.result.toString();
	}

	public String getError_num() {
		return error_num;
	}

	public void setError_num(String error_num) {
		this.error_num = error_num;
	}

	public String getError_msg() {
		return error_msg;
	}

	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
}