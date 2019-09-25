package com.together.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class CommentVO {

	
	private int cmt_cd;
	private int post_cd;
	private String cmt_info;
	private Timestamp cmt_dt;
	private String user_id;
}
