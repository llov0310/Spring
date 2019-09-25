package com.together.domain;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class PostVO {
	private int post_cd;
	private String user_id;
	private String post_info;
	private String post_title;
	private int post_cont;
	private Timestamp post_dt;
	private String post_dt_char;
	
	
	private int cmt_cd;
	private String cmt_info;
	private Timestamp cmt_dt;
	private String cmt_dt_char;
	
	private String equalsuid;

	
	
}
