package complexmodel;

import java.util.ArrayList;

import com.wjs.generic.FourTuple;
import com.wjs.generic.TupleTest;

public class TupleList<A, B, C, D> extends ArrayList<FourTuple<A, B, C, D>>{

	public static void main(String[] args)
	{
		TupleList<String,String,Integer,Double> tl=new TupleList<String,String,Integer,Double>();
		tl.add(TupleTest.h());
		tl.add(TupleTest.h());
		for(FourTuple<String, String, Integer, Double> i:tl)
			System.out.println(i);
	}
}
