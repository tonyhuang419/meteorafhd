package htmlUnit.waterKing;

public class WhoIsKing {

	/**
	 * need Addrress like this ： http://bbs.taisha.org/forum-74-1.html
	 */
	private String waterUrlPrefix = "http://bbs.taisha.org/forum-74-";
	private String waterUrlSuffix = ".html";
	
	
	public void scan(){
		String url;  
		for(int i=1;i<=1000;i++){
			url= waterUrlPrefix + i +  waterUrlSuffix;
			System.out.println(url);
		}
	}
	
	
	public static void main(String[] args){
		new WhoIsKing().scan();
	}
	
}
