package com.wjs.coffee;

import java.util.Iterator;
import java.util.Random;

public class CoffeeGenerator implements Generator<Coffee>,Iterable<Coffee>{

	private Class[] types={Latte.class,Mocha.class,Cappuccino.class,Americano.class,Breve.class};
	private static Random rand=new Random(47);
	public CoffeeGenerator()
	{
		
	}
	//for iteration
	private int size=0;
	public CoffeeGenerator(int sz)
	{
		size=sz;
	}
	@Override
	public Iterator<Coffee> iterator() {
		// TODO Auto-generated method stub
		return new CoffeeIterator();
	}
	@Override
	public Coffee next() {
		// TODO Auto-generated method stub
		try {
			return (Coffee)types[rand.nextInt(types.length)].newInstance();
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}
	class CoffeeIterator implements Iterator<Coffee>{

		int count=size;
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			//System.out.println("......");
			return count>0;
		}

		@Override
		public Coffee next() {
			// TODO Auto-generated method stub
			count--;
			return CoffeeGenerator.this.next();
		}
		public void move()
		{
			throw new UnsupportedOperationException();
		}
		
	}
	public static void main(String[] args)
	{
		CoffeeGenerator gen=new CoffeeGenerator();
//		for(int i=0;i<5;i++)
//			System.out.println(gen.next());
		for(Coffee c:new CoffeeGenerator(5))
			System.out.println(c);
	}
	
}
