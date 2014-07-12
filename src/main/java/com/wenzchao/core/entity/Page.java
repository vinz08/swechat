package com.wenzchao.core.entity;

import java.io.Serializable;

public class Page implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3041436827544897993L;
	private int page = 1; // 当前页码,默认1
	private int pageCount = 1; // 总页数
	private int count = 0; // 总记录数
	private int pageRecord = 15; // 每页记录数
	private int start = 1; // 开始记录数
	private int end = 15; // 结束记录数

	/**
	 * 初使化
	 * 
	 * @param count 总记录数
	 */
	public void init(int count) {
		setCount(count); // 总记录数
		if (getPageRecord() < 15 || getPageRecord() > 100) {
			setPageRecord(15); // 最小每页记录数
		}
		setStart((page - 1) * this.pageRecord); // 查询开始记录数
		setEnd(this.start + this.pageRecord - 1);
		if (count % this.pageRecord == 0) {
			setPageCount(count / this.pageRecord); // 总页数
		} else {
			setPageCount(count / this.pageRecord + 1); // 总页数
		}

	}

	/**
	 * 构建分页
	 * 
	 * @param page
	 * @param count
	 */
	public static void buildPage(Page page, int count) {
		if (page == null) {
			page = new Page();
		}
		page.init(count);
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getPageRecord() {
		return pageRecord;
	}

	public void setPageRecord(int pageRecord) {
		this.pageRecord = pageRecord;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

}
