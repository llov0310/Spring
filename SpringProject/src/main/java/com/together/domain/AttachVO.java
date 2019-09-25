package com.together.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class AttachVO {

	private int at_cd;
	private int post_cd;
	private Timestamp at_dt;
	private String at_uid;
	private String at_name;
	private String at_dt_char;
}
