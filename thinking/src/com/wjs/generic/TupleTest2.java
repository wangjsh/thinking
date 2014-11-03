package com.wjs.generic;

public class TupleTest2 {
	//huang he yuan shang baiyun jian
	static TwoTuple<String,Integer> f()
	{
		return Tuple.tuple("hi",47);
	}
	static TwoTuple f2()
	{
		return Tuple.tuple("hi", 47);
	}
	static ThreeTuple<String,Integer,Double> f3()
	{
		return Tuple.tuple("hello", 12, 1.234);
	}
	public static void main(String[] args)
	{
		TwoTuple<String,Integer> ttsi=f();
		System.out.println(ttsi);
		System.out.println(f2());
		System.out.println(f3());
	}

}
