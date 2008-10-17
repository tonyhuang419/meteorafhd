package com.cheatkx;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class App 
{
	public static void main( String[] args )
	{
		new App().test();
	}

	public void test(){
		test1();
		test2();
	}

	public void  test1(){
		String ud = "{e:1}";	
		JSONObject jo = JSONObject.fromObject(ud);  
		System.out.println(jo.get("e"));
	}

	public void test2(){ 
		String ud = "{aa:[1,2]}";
		JSONObject jb = JSONObject.fromObject(ud); 
		JSONArray array = jb.getJSONArray("aa"); 
		for(int i=0;i<array.size();i++){
			System.out.println("array:"+array.get(i)); 
		}
	}

	
	
}
