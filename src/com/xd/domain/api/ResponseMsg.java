package com.xd.domain.api;


public class ResponseMsg {

	private int errno;          //状态码
	private String errmsg;      //错误信息
	private Integer pageNumber; //第几页
	private Integer pageSize;   //每页数据条数
	private Long count;      //数据总条数
	private Object data;        //数据

		
			
	public ResponseMsg() {
		super();
	}
	
	public ResponseMsg(int errno, String errmsg){
		this.errno = errno;
		this.errmsg = errmsg;
	}
	
	public ResponseMsg(int errno, String errmsg, Object data) {
		super();
		this.errno = errno;
		this.errmsg = errmsg;
		this.data = data;
	}



	public int getErrno() {
		return errno;
	}
	public void setErrno(int errno) {
		this.errno = errno;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	
	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
}
