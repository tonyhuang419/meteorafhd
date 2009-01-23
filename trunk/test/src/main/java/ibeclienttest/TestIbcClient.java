package ibeclienttest;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.travelsky.ibe.client.AV;
import com.travelsky.ibe.client.AvItem;
import com.travelsky.ibe.client.AvResult;
import com.travelsky.ibe.client.AvSegment;
import com.travelsky.ibe.client.FD;
import com.travelsky.ibe.client.FD;

public class TestIbcClient {

//===========================查票===========================
	/**
	 * 获取AV
	 */
	public AV getAV(){
		return new AV();
		//		AvResult avr = this.getAV().getAvailability("xx", "yy", new Date());
	}

	/**
	 * 获取AvItem by AvResult
	 */
	public List<AvItem> getListItemResults(AvResult avr){
		int itemAmount = avr.getItemamount();
		List<AvItem> avItemList = new ArrayList<AvItem>();
		for(int i=0;i<itemAmount;i++){
			avItemList.add(avr.getItem(i));
		}
		return avItemList;
	}

	/**
	 * 获取AvSegment by avItem
	 */
	public List<AvSegment>  getListAvSegment(List<AvItem> avItem) {
		List<AvSegment>  avsList = new ArrayList<AvSegment>();
		int segmentCount;
		for(AvItem avi : avItem){
			segmentCount = avi.getSegmentnumber();
			for(int i=0;i<segmentCount;i++){
				avsList.add(avi.getSegment(i));
			}
		}
		return avsList;
	}

	/**
	 * 获取AvSegment by AvResult
	 */
	public List<AvSegment>  getListAvSegment(AvResult avr) {
		List<AvItem> avItemList = this.getListItemResults(avr);
		return this.getListAvSegment(avItemList);
	}

	/**
	 * 舱位过滤include
	 * Ｆ――头等舱。　Ｃ――公务舱　　Ｙ——经济舱
	 * exp:获取头等舱  filterAvSegmentListInclude(avs , 'F')
	 */
	public Map<String,String> filterAvSegmentListInclude( AvSegment avs,List<String> include ){
		Map<String,String> spaceMap = new HashMap<String,String>();
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
	public Map<String,String> filterAvSegmentListExclude( AvSegment avs,List<String> exclude ){
		Map<String,String> spaceMap = new HashMap<String,String>();
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



	private boolean includeChar( char spaceCode , List<String> listStr ){
		for(String s: listStr ){
			char c = s.charAt(0);
			System.out.println(c);
			if( c == spaceCode ){
				return true;
			}
		}
		return false;
	}

	private boolean excludeChar( char spaceCode , List<String> listStr ){
		for(String s: listStr ){
			char c = s.charAt(0);
			if( c == spaceCode ){
				return false;
			}
		}
		return true;
	}

	
	//===========================查询票价===========================
	public FD getFD(){
		return new FD();
	}
	
	
	
	
	
	//===========================订票===========================
	
	
	
	
	
	
	
	
	public static void main(String[] args){
		TestIbcClient t = new TestIbcClient();
		List<String> strArr = new ArrayList<String>();
		strArr.add("sy");
		strArr.add("c");
		System.out.println(t.includeChar( 'd' , strArr));
		System.out.println(t.excludeChar('d' , strArr ));

		char a = 'a';
		int i = a;
		System.out.println(i);

	}
	
	

	/**
	 * 取消预定
	 */


	/**
	 * 出票
	 */


}	

