package com.wjs.eraser;

import java.util.ArrayList;
import java.util.List;

public class FilledListMaker<T> {

	List<T> create(T t,int n)
	{
		List<T> result=new ArrayList<T>();
		for(int i=0;i<n;i++)
			result.add(t);
		//��ʹ�������޷�֪���й�create()�е�T���κ���Ϣ���������Ծɿ����ڱ�����ȷ������õ�result�еĶ������T���ͣ�ʹ���ʺ�ArrayList<T>��
		//��ˣ���ʹ�����ڷ����������ڲ��Ƴ����й�ʵ�����͵���Ϣ���������Ծɿ���ȷ���ڷ���������ʹ�õ����͵��ڲ�һ����
		return result;
	}
	public static void main(String[] args)
	{
		FilledListMaker<String> stringMaker=new FilledListMaker<String>();
		List<String> list=stringMaker.create("hello", 4);
		System.out.println(list);
	}
}
