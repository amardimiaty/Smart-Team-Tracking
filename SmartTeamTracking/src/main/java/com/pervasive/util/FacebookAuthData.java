package com.pervasive.util;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class FacebookAuthData {
    
    private String app_id;
    private String application;
    private String expires_at;
    private String is_valid;
    private String issued_at;
    private String user_id;
    
    public FacebookAuthData(){
    	
    }

	public String getApp_id() {
		return app_id;
	}

	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getExpires_at() {
		return expires_at;
	}

	public void setExpires_at(String expires_at) {
		this.expires_at = expires_at;
	}

	public String getIs_valid() {
		return is_valid;
	}

	public void setIs_valid(String is_valid) {
		this.is_valid = is_valid;
	}

	public String getIssued_at() {
		return issued_at;
	}

	public void setIssued_at(String issued_at) {
		this.issued_at = issued_at;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	@Override
	public String toString() {
		return "FacebookAuthData [app_id=" + app_id + ", application=" + application + ", expires_at=" + expires_at
				+ ", is_valid=" + is_valid + ", issued_at=" + issued_at + ", user_id=" + user_id + "]";
	}
    
    

	
}
