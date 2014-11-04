package com.wjs.eraser;

import java.util.ArrayList;
import java.util.List;

public class FilledListMaker<T> {

	List<T> create(T t,int n)
	{
		List<T> result=new ArrayList<T>();
		for(int i=0;i<n;i++)
			result.add(t);
		//即使编译器无法知道有关create()中的T的任何信息，但是它仍旧可以在编译器确保你放置到result中的对象具有T类型，使其适合ArrayList<T>。
		//因此，即使擦出在方法或者类内部移除了有关实际类型的信息，编译器仍旧可以确保在方法或类中使用的类型的内部一致性
		return result;
	}
	public static void main(String[] args)
	{
		FilledListMaker<String> stringMaker=new FilledListMaker<String>();
		List<String> list=stringMaker.create("hello", 4);
		System.out.println(list);
	}
}
