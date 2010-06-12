package waterKingCovToNoSql.tools;

public class StringUtils {

	public static String getEmptyString(Object obj){
		if(obj==null){
			return "";
		}
		return obj.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(getEmptyString("132"));
		System.out.println(getEmptyString(null));
		System.out.println(getEmptyString("132"));
	}
	
}
