package ibe;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.travelsky.ibe.client.AV;
import com.travelsky.ibe.client.AvItem;
import com.travelsky.ibe.client.AvResult;
import com.travelsky.ibe.client.AvSegment;
import com.travelsky.ibe.client.FD;
import com.travelsky.ibe.client.FDResult;

public final class IbeClientTools {

	
	public static AV getAV(){
		return new AV();
	}
	
	public static FD getFD(){
		return new FD();
	}
	
	//===========================查票===========================
	/**
	 * 获取AV
	 */

	public AvResult doGetAvResult(String takeOff,String land) {
		AvResult avr = null;
		try{
			avr = getAV().getAvailability(takeOff, land, new Date());
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return avr;
	}


	/**
	 * 获取AvItem by AvResult
	 */
	public static List getListItemResults(AvResult avr){
		int itemAmount = avr.getItemamount();
		List avItemList = new ArrayList();
		for(int i=0;i<itemAmount;i++){
			avItemList.add(avr.getItem(i));
		}
		return avItemList;
	}

	/**
	 * 获取AvSegment by avItem
	 */
	public static List  getListAvSegment(List avItem ,  SearchHanBanModel searchmodel) {
		List  avsList = new ArrayList();
		int segmentCount;
		for(Iterator iter=avsList.iterator();iter.hasNext();){
			AvItem avi = (AvItem)iter.next();
			segmentCount = avi.getSegmentnumber();
			for(int i=0;i<segmentCount;i++){
				AvSegment avs = avi.getSegment(i);
				if( (searchmodel.getOrg().equals(avs.getOrgcity())) &&  (searchmodel.getOrg().equals(avs.getDstcity() ))   ){
					avsList.add(avs);
				}
			}
		}
		return avsList;
	}

	/**
	 * 获取AvSegment by AvResult
	 */
	public static List  getListAvSegment(AvResult avr , SearchHanBanModel searchmodel) {
		List avItemList = getListItemResults(avr);
		return getListAvSegment(avItemList  , searchmodel);
	}

	/**
	 * 舱位过滤include
	 * exp:获取F舱  filterAvSegmentListInclude(avs , 'F')
	 */
	public Map filterAvSegmentListInclude( AvSegment avs,List include ){
		Map spaceMap = new HashMap();
		//		StringBuffer str = new StringBuffer();
		int k = 0;
		while (k < 26) {
			char cangwei = avs.getCangweiCodeSort(k);
			if (cangwei == '-'){
				break;
			}
			if( include==null ){
				spaceMap.put( cangwei+"", avs.getCangweiinfoOfSort(cangwei)+"" );
				k++;
			}
			else if( this.includeChar(cangwei , include) ){
				//				str.append(cangwei);
				//				str.append(avs.getCangweiinfoOfSort(cangwei));
				//				str.append(' ');
				spaceMap.put( cangwei+"", avs.getCangweiinfoOfSort(cangwei)+"" );
				k++;
			}
		}
		return spaceMap;
	}

	/**
	 * 舱位过滤exclude
	 */
	public Map filterAvSegmentListExclude( AvSegment avs,List exclude ){
		Map spaceMap = new HashMap();
		int k = 0;
		while (k < 26) {
			char cangwei = avs.getCangweiCodeSort(k);
			if (cangwei == '-'){
				break;
			}
			if( exclude == null ){
				spaceMap.put( cangwei+"", avs.getCangweiinfoOfSort(cangwei)+"" );
				k++;
			}
			else if( ! ( exclude!=null && this.excludeChar(cangwei , exclude)) ){
				spaceMap.put( cangwei+"", avs.getCangweiinfoOfSort(cangwei)+"" );
				k++;
			}
		}
		return spaceMap;
	}



	private boolean includeChar( char spaceCode , List listStr ){
		for(Iterator iter = listStr.iterator(); iter.hasNext();){
			char c = iter.next().toString().charAt(0);
			if( c == spaceCode ){
				return true;
			}
		}
		return false;
	}

	private boolean excludeChar( char spaceCode , List listStr ){
		for(Iterator iter = listStr.iterator(); iter.hasNext();){
			char c = iter.next().toString().charAt(0);
			if( c == spaceCode ){
				return false;
			}
		}
		return true;
	}


	//===========================查询票价===========================
	/**
	 * 获取FDResult by AvSegment
	 */
	public static FDResult doGetFDResultByAvSegment(AvSegment avs){
		FDResult fdr = null;
		try{
			fdr =  getFD().findPrice(avs.getOrgcity(),avs.getDstcity(),
					avs.getDeptime(),avs.getAirline(),avs.getPlanestyle(),"passType" ,true);
			//passType - java.lang.String 旅客类型(""或null：成人；"IN"：婴儿；"CH"：儿童)
		}
		catch(Exception e){
			System.out.println("查询票价出错");
			e.printStackTrace();
		}
		return fdr;
	}

	/**
	 * 获取Map<AvSegment , FDResult> by AvSegment
	 */
	public static Map doGetAvsAndfds(List avSegmentList){
		Map m = new HashMap();
		for(Iterator iter = avSegmentList.iterator(); iter.hasNext();){
			AvSegment  avs= (AvSegment)iter.next();
			m.put(avs, doGetFDResultByAvSegment(avs));
		}
		return m;
	}




	//===========================订票===========================








	public static void main(String[] args){
		IbeClientTools t = new IbeClientTools();
//		List strArr = new ArrayList();
//		strArr.add("sy");
//		strArr.add("c");
//		System.out.println(t.includeChar( 'd' , strArr));
//		System.out.println(t.excludeChar('d' , strArr ));
//
//		char a = 'a';
//		int i = a;
//		System.out.println(i);
		
		t.doGetAvResult("PEK","CAN");

	}



	/**
	 * 取消预定
	 */


	/**
	 * 出票
	 */


}	

