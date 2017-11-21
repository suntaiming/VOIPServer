package com.xd.test;

import java.util.ArrayList;
import java.util.List;

public class Stack<E> {
	private List<E> items = new ArrayList<>();
	
	public void push(E item){
		items.add(item);
	}

	public E pop(){
		E top = items.get(items.size()-1);
		items.remove(items.size()-1);
		return top;
	}
	
	public int size(){
		return items.size();
	}
	
}
