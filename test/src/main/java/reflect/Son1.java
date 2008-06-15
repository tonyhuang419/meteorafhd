package reflect;

public class Son1 extends Base{
	public int ii;
	private int id ;
	private String name ;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public void son1Method(String s){
		System.out.println(s) ;
	}
} 