package com.itheima.bean;

import java.util.Date;

public class Comment {
	private int cid;
	private String pid;
	private String  date;
	private String username;
	private String comment;
	private Integer good;
	
	public Integer getGood() {
		return good;
	}

	public void setGood(Integer good) {
		this.good = good;
	}

	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "Comment [cid=" + cid + ", pid=" + pid + ", date=" + date + ", username=" + username + ", comment="
				+ comment + ", good=" + good + "]";
	}

		
}
