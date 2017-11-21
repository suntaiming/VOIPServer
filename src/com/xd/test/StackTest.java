package com.xd.test;

public class StackTest {

	
	public static void main(String[] args) {
		Stack<String> strSta = new Stack<>();
		strSta.push("123");
		strSta.push("456");
		strSta.push("qwe");
		
		
	    System.out.println(strSta.pop());
	    System.out.println(strSta.size());
	    
	    Stack<Number> sta = new Stack<>();
	    sta.push(new Integer("1"));
	    System.out.println(sta.pop());
	}
}
