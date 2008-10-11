package ioc;

public class Factory {
	public final static String CHINESE = "Chinese";
	public final static String AMERICAN = "American";

	public Human getHuman(String ethnic) {
		if (ethnic.equals(CHINESE))
			return new Chinese();
		else if (ethnic.equals(AMERICAN))
			return new American();
		else
			throw new IllegalArgumentException("参数(人种)错误");
	}
}
