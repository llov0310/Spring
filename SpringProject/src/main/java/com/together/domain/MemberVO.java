package com.together.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class MemberVO {
	
	private String user_id;
	private String password;
	private String user_ph;
	private String user_nm;
	private Timestamp user_dt;
	private String user_gt;
	
	
}
