package ibeclienttest;


import java.util.ArrayList;
import java.util.List;

import com.travelsky.ibe.client.AV;
import com.travelsky.ibe.client.AvItem;
import com.travelsky.ibe.client.AvResult;
import com.travelsky.ibe.client.AvSegment;

public class TestIbcClient {
	

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
	 * 舱位过滤
	 */
	public List<AvSegment> filterAvSegmentList( List<AvSegment> avsList,List<String> include, List<String> exclude ){
		for( AvSegment avs :  avsList ){
//			if( includeChar(include , avs.ge) )
		}
		return null;
	}
	
	private boolean includeChar(List<String> listStr , char spaceCode){
		for(String s: listStr ){
			char c = s.charAt(0);
			System.out.println(c);
			if( c == spaceCode ){
				return true;
			}
		}
		return false;
	}
	
	private boolean excludeChar(List<String> listStr , char spaceCode){
		for(String s: listStr ){
			char c = s.charAt(1);
			if( c != spaceCode ){
				continue;
			}
			else{
				
			}
		}
		return true;
	}

	
	public static void main(String[] args){
		TestIbcClient t = new TestIbcClient();
		List<String> strArr = new ArrayList<String>();
		strArr.add("sy");
		strArr.add("c");
		System.out.println(t.includeChar(strArr, 'd'));
	}
	
	
	/**
	 * 订票
	 */
	
	/**
	 * 取消预定
	 */
	
	
	/**
	 * 出票
	 */
	
	
}	
	
