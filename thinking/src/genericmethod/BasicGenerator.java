package genericmethod;
//古老的夜晚和远方的音乐是永恒的，但那些不属于我。
import com.wjs.coffee.Generator;

public class BasicGenerator<T> implements Generator<T> {

	private Class<T> type;
	public BasicGenerator(Class<T> type)
	{
		this.type=type;
	}
	@Override
	public T next() {
		// TODO Auto-generated method stub
		try {
			//assumes type is a public class
			return type.newInstance();
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
	}
	//produce a default generator given a type token
	public static <T> Generator<T> create(Class<T> type)
	{
		return new BasicGenerator<T>(type);
	}
}
