package com.itheima.bean;

import java.util.List;

public class PageBean<T> {
	 private List<T> data;
	 //当前页数
	 private int curPage;
	 //总页数
	 private int totalPage ;
	 //每页显示的数据条数
	 private int pageSize;
	 //总数据条数
	 private int totalRecord;
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getTotalPage() {
		
		return (int) Math.ceil(totalRecord*1.0/pageSize);
	}
	public int getStartIndex(){
		//通过公式计算出查询商品的起始位置
		return (curPage-1)*pageSize;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	@Override
	public String toString() {
		return "PageBean [list=" + data + ", curPage=" + curPage + ", totalPage=" + totalPage + ", pageSize=" + pageSize
				+ ", totalRecord=" + totalRecord + "]";
	}
	
}
