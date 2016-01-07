package pers.ksy.common.model;

import java.util.List;

public class Page<T> {
	private int pageIndex; // start with 0
	private long total;
	private long pageAmount;
	private int pageSize;
	private List<T> list;

	public Page(int pageIndex, long total, int pageSize, List<T> list) {
		super();
		this.pageIndex = pageIndex;
		this.total = total;
		this.pageSize = pageSize;
		this.list = list;
		double a = (double) total / pageSize;
		pageAmount = Math.round(Math.ceil(a));
	}

	public long getPageAmount() {
		return pageAmount;
	}

	public void setPageAmount(long pageAmount) {
		this.pageAmount = pageAmount;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
}