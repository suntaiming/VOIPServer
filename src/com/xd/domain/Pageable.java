package com.xd.domain;

import java.util.List;

public class Pageable<T> {
	private long count;     //总数
	private List<T> list;   //数据
	public Pageable() {
		super();
	}
	
	public Pageable(long count, List<T> list) {
		super();
		this.count = count;
		this.list = list;
	}


	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	
}
