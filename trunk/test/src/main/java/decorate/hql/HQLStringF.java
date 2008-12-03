package decorate.hql;

public class HQLStringF extends AbsHQLString {
	
	AbsHQLString hs;
	HQLStringF(AbsHQLString _hs){
		this.hs = _hs;
	}
	
	public String getHqlString(){
		return hs.getHqlString() ;
	}
	
}
