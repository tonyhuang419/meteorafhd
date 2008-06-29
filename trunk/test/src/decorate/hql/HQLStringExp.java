package decorate.hql;

public class HQLStringExp {

	public static void main(String[] args) {
		AbsHQLString hsf = new HQLString("select * from Exp");

		hsf = new HQLStringF(hsf);
		
		System.out.println(hsf.getHqlString());
	}
}
