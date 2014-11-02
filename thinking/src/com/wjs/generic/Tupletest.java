package com.wjs.generic;

public class Tupletest {

	static TwoTuple<String,Integer> f()
	{
		//autoboxing converts the int to Integer
		return new TwoTuple<String, Integer>("hi", 47);
	}
	static ThreeTuple<String,String,Integer> g()
	{
		return new ThreeTuple<String,String,Integer>("hi", "hi", 47);
	}
	static FiveTuple<String, String, Integer, Double, Double> k()
	{
		return new FiveTuple<String, String, Integer, Double, Double>("hello", "world", 11, 1.2, 1.3);
	}
	public static void main(String[] args)
	{
		TwoTuple<String, Integer> ttsi=f();
		System.out.println(ttsi);
		//ttsi.first="there"//compile error: final
		System.out.println(g());
		System.out.println(k());
	}
}
