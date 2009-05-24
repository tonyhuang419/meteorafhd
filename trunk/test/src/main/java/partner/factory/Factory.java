package partner.factory;

public class Factory {
	
	static public People getPeople(String str){
		try {
			Class cla=Class.forName(str);
			try {
				Object obj=cla.newInstance();
				return (People)obj;
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
