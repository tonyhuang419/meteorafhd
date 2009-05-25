package com.xiaonei.farmAssist.Utils;

import java.util.Date;

public class FramKeyUtil {
	
	public static void main(String[] args){
		FramKeyUtil framKeyUtil = new FramKeyUtil();
		System.out.println(framKeyUtil.getFarmKey());
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(framKeyUtil.getFarmKey());
	}
	
	public String getFarmKey(){
//		CommonsUtils commonsUtils = new CommonsUtils();
		//15l3h4kh
		System.out.println(this.serverTime());
		return  this.hash(this.serverTime() + "15l3h4kh");
	}


	public long serverTime() {
		return new Date().getTime() / 1000; 
	}

	private  int rol(int param1, int param2){
		return param1 << param2 | param1 >>> 32 - param2;
	}

	public String toHex(int param1){
		String  hexChars = "0123456789abcdef";
		Boolean param2 = false;
		int _loc_4;
		int _loc_5;
		String _loc_3="";
		if (param2)
		{
			_loc_4 = 0;
			while (_loc_4 < 4)
			{
				_loc_3 = _loc_3 + (hexChars.charAt(param1 >> (3 - _loc_4) * 8 + 4 & 15) + hexChars.charAt(param1 >> (3 - _loc_4) * 8 & 15));
				_loc_4++;
			}
		}
		else
		{
			_loc_5 = 0;
			while (_loc_5 < 4)
			{
				_loc_3 = _loc_3 + (hexChars.charAt(param1 >> _loc_5 * 8 + 4 & 15)+"" + hexChars.charAt(param1 >> _loc_5 * 8 & 15));
				_loc_5++;
			}
		}
		return _loc_3;
	}

	private int transform(int param1, int param2, int param3, int param4, int param5, int param6, int param7, int param8) {
		int _loc_9 = param2 + param1 + param6 + param8;
		return this.rol(_loc_9, param7) + param3;
	}

	private int  g(int param1, int param2, int param3) {
		return param1 & param3 | param2 & ~param3;
	}

	private int gg(int param1,int param2,int param3,int param4,int param5, int param6,int param7){
		return transform(this.g(param2, param3, param4), param1, param2, param3, param4, param5, param6, param7);
	}

	private int  f(int param1, int param2, int param3)  {
		return param1 & param2 | ~param1 & param3;
	}

	private  int ff(int param1, int param2, int param3, int param4 ,int param5, int param6, int param7) {
		return transform(this.f(param2, param3, param4), param1, param2, param3, param4, param5, param6, param7);
	}


	private int h(int param1, int param2, int param3){
		return param1 ^ param2 ^ param3;
	}

	private int hh(int param1, int param2, int param3, int param4, int param5, int param6, int param7){
		return transform(this.h(param2, param3, param4), param1, param2, param3, param4, param5, param6, param7);
	}

	private int i(int param1, int param2, int param3){
		return param2 ^ (param1 | ~param3);
	}

	private int ii(int param1, int param2, int param3, int param4, int param5, int param6, int param7){
		return transform(this.i(param2, param3, param4), param1, param2, param3, param4, param5, param6, param7);
	}

	private int[] createBlocks(String str){
		int[] _loc_2 = new int[16];
		int _loc_3 = str.length() * 8;
		int _loc_4=0;
		int _loc_5=0;
        while (_loc_5 < _loc_3)
        {
            // label
            _loc_2[_loc_5 >> 5] = _loc_2[_loc_5 >> 5] | (str.charAt(_loc_5 / 8) & _loc_4) << _loc_5 % 32;
            _loc_5 = _loc_5 + 8;
        }// end while
        _loc_2[_loc_3 >> 5] = _loc_2[_loc_3 >> 5] | 128 << _loc_3 % 32;
        _loc_2[(_loc_3 + 64 >>> 9 << 4) + 14] = _loc_3;
        return _loc_2;
    }// end function

	private String hash(String str){
		int _loc_6=0;
		int _loc_7=0;
		int _loc_8=0;
		int _loc_9=0;
		int _loc_2=0;
		int _loc_3=0;
		int _loc_4=0;
		int _loc_5=0;
		int[] _loc_10 = createBlocks(str);
		int _loc_11 = createBlocks(str).length;
		int _loc_12=0;
		while (_loc_12 < _loc_11-1)
		{
			 _loc_6 = _loc_2;
             _loc_7 = _loc_3;
             _loc_8 = _loc_4;
             _loc_9 = _loc_5;
             _loc_2 = ff(_loc_2, _loc_3, _loc_4, _loc_5, _loc_10[_loc_12 + 0], 7, -680876936);
             _loc_5 = ff(_loc_5, _loc_2, _loc_3, _loc_4, _loc_10[_loc_12 + 1], 12, -389564586);
             _loc_4 = ff(_loc_4, _loc_5, _loc_2, _loc_3, _loc_10[_loc_12 + 2], 17, 606105819);
             _loc_3 = ff(_loc_3, _loc_4, _loc_5, _loc_2, _loc_10[_loc_12 + 3], 22, -1044525330);
             _loc_2 = ff(_loc_2, _loc_3, _loc_4, _loc_5, _loc_10[_loc_12 + 4], 7, -176418897);
             _loc_5 = ff(_loc_5, _loc_2, _loc_3, _loc_4, _loc_10[_loc_12 + 5], 12, 1200080426);
             _loc_4 = ff(_loc_4, _loc_5, _loc_2, _loc_3, _loc_10[_loc_12 + 6], 17, -1473231341);
             _loc_3 = ff(_loc_3, _loc_4, _loc_5, _loc_2, _loc_10[_loc_12 + 7], 22, -45705983);
             _loc_2 = ff(_loc_2, _loc_3, _loc_4, _loc_5, _loc_10[_loc_12 + 8], 7, 1770035416);
             _loc_5 = ff(_loc_5, _loc_2, _loc_3, _loc_4, _loc_10[_loc_12 + 9], 12, -1958414417);
             _loc_4 = ff(_loc_4, _loc_5, _loc_2, _loc_3, _loc_10[_loc_12 + 10], 17, -42063);
             _loc_3 = ff(_loc_3, _loc_4, _loc_5, _loc_2, _loc_10[_loc_12 + 11], 22, -1990404162);
             _loc_2 = ff(_loc_2, _loc_3, _loc_4, _loc_5, _loc_10[_loc_12 + 12], 7, 1804603682);
             _loc_5 = ff(_loc_5, _loc_2, _loc_3, _loc_4, _loc_10[_loc_12 + 13], 12, -40341101);
             _loc_4 = ff(_loc_4, _loc_5, _loc_2, _loc_3, _loc_10[_loc_12 + 14], 17, -1502002290);
             _loc_3 = ff(_loc_3, _loc_4, _loc_5, _loc_2, _loc_10[_loc_12 + 15], 22, 1236535329);
             _loc_2 = gg(_loc_2, _loc_3, _loc_4, _loc_5, _loc_10[_loc_12 + 1], 5, -165796510);
             _loc_5 = gg(_loc_5, _loc_2, _loc_3, _loc_4, _loc_10[_loc_12 + 6], 9, -1069501632);
             _loc_4 = gg(_loc_4, _loc_5, _loc_2, _loc_3, _loc_10[_loc_12 + 11], 14, 643717713);
             _loc_3 = gg(_loc_3, _loc_4, _loc_5, _loc_2, _loc_10[_loc_12 + 0], 20, -373897302);
             _loc_2 = gg(_loc_2, _loc_3, _loc_4, _loc_5, _loc_10[_loc_12 + 5], 5, -701558691);
             _loc_5 = gg(_loc_5, _loc_2, _loc_3, _loc_4, _loc_10[_loc_12 + 10], 9, 38016083);
             _loc_4 = gg(_loc_4, _loc_5, _loc_2, _loc_3, _loc_10[_loc_12 + 15], 14, -660478335);
             _loc_3 = gg(_loc_3, _loc_4, _loc_5, _loc_2, _loc_10[_loc_12 + 4], 20, -405537848);
             _loc_2 = gg(_loc_2, _loc_3, _loc_4, _loc_5, _loc_10[_loc_12 + 9], 5, 568446438);
             _loc_5 = gg(_loc_5, _loc_2, _loc_3, _loc_4, _loc_10[_loc_12 + 14], 9, -1019803690);
             _loc_4 = gg(_loc_4, _loc_5, _loc_2, _loc_3, _loc_10[_loc_12 + 3], 14, -187363961);
             _loc_3 = gg(_loc_3, _loc_4, _loc_5, _loc_2, _loc_10[_loc_12 + 8], 20, 1163531501);
             _loc_2 = gg(_loc_2, _loc_3, _loc_4, _loc_5, _loc_10[_loc_12 + 13], 5, -1444681467);
             _loc_5 = gg(_loc_5, _loc_2, _loc_3, _loc_4, _loc_10[_loc_12 + 2], 9, -51403784);
             _loc_4 = gg(_loc_4, _loc_5, _loc_2, _loc_3, _loc_10[_loc_12 + 7], 14, 1735328473);
             _loc_3 = gg(_loc_3, _loc_4, _loc_5, _loc_2, _loc_10[_loc_12 + 12], 20, -1926607734);
             _loc_2 = hh(_loc_2, _loc_3, _loc_4, _loc_5, _loc_10[_loc_12 + 5], 4, -378558);
             _loc_5 = hh(_loc_5, _loc_2, _loc_3, _loc_4, _loc_10[_loc_12 + 8], 11, -2022574463);
             _loc_4 = hh(_loc_4, _loc_5, _loc_2, _loc_3, _loc_10[_loc_12 + 11], 16, 1839030562);
             _loc_3 = hh(_loc_3, _loc_4, _loc_5, _loc_2, _loc_10[_loc_12 + 14], 23, -35309556);
             _loc_2 = hh(_loc_2, _loc_3, _loc_4, _loc_5, _loc_10[_loc_12 + 1], 4, -1530992060);
             _loc_5 = hh(_loc_5, _loc_2, _loc_3, _loc_4, _loc_10[_loc_12 + 4], 11, 1272893353);
             _loc_4 = hh(_loc_4, _loc_5, _loc_2, _loc_3, _loc_10[_loc_12 + 7], 16, -155497632);
             _loc_3 = hh(_loc_3, _loc_4, _loc_5, _loc_2, _loc_10[_loc_12 + 10], 23, -1094730640);
             _loc_2 = hh(_loc_2, _loc_3, _loc_4, _loc_5, _loc_10[_loc_12 + 13], 4, 681279174);
             _loc_5 = hh(_loc_5, _loc_2, _loc_3, _loc_4, _loc_10[_loc_12 + 0], 11, -358537222);
             _loc_4 = hh(_loc_4, _loc_5, _loc_2, _loc_3, _loc_10[_loc_12 + 3], 16, -722521979);
             _loc_3 = hh(_loc_3, _loc_4, _loc_5, _loc_2, _loc_10[_loc_12 + 6], 23, 76029189);
             _loc_2 = hh(_loc_2, _loc_3, _loc_4, _loc_5, _loc_10[_loc_12 + 9], 4, -640364487);
             _loc_5 = hh(_loc_5, _loc_2, _loc_3, _loc_4, _loc_10[_loc_12 + 12], 11, -421815835);
             _loc_4 = hh(_loc_4, _loc_5, _loc_2, _loc_3, _loc_10[_loc_12 + 15], 16, 530742520);
             _loc_3 = hh(_loc_3, _loc_4, _loc_5, _loc_2, _loc_10[_loc_12 + 2], 23, -995338651);
             _loc_2 = ii(_loc_2, _loc_3, _loc_4, _loc_5, _loc_10[_loc_12 + 0], 6, -198630844);
             _loc_5 = ii(_loc_5, _loc_2, _loc_3, _loc_4, _loc_10[_loc_12 + 7], 10, 1126891415);
             _loc_4 = ii(_loc_4, _loc_5, _loc_2, _loc_3, _loc_10[_loc_12 + 14], 15, -1416354905);
             _loc_3 = ii(_loc_3, _loc_4, _loc_5, _loc_2, _loc_10[_loc_12 + 5], 21, -57434055);
             _loc_2 = ii(_loc_2, _loc_3, _loc_4, _loc_5, _loc_10[_loc_12 + 12], 6, 1700485571);
             _loc_5 = ii(_loc_5, _loc_2, _loc_3, _loc_4, _loc_10[_loc_12 + 3], 10, -1894986606);
             _loc_4 = ii(_loc_4, _loc_5, _loc_2, _loc_3, _loc_10[_loc_12 + 10], 15, -1051523);
             _loc_3 = ii(_loc_3, _loc_4, _loc_5, _loc_2, _loc_10[_loc_12 + 1], 21, -2054922799);
             _loc_2 = ii(_loc_2, _loc_3, _loc_4, _loc_5, _loc_10[_loc_12 + 8], 6, 1873313359);
             _loc_5 = ii(_loc_5, _loc_2, _loc_3, _loc_4, _loc_10[_loc_12 + 15], 10, -30611744);
             _loc_4 = ii(_loc_4, _loc_5, _loc_2, _loc_3, _loc_10[_loc_12 + 6], 15, -1560198380);
             _loc_3 = ii(_loc_3, _loc_4, _loc_5, _loc_2, _loc_10[_loc_12 + 13], 21, 1309151649);
             _loc_2 = ii(_loc_2, _loc_3, _loc_4, _loc_5, _loc_10[_loc_12 + 4], 6, -145523070);
             _loc_5 = ii(_loc_5, _loc_2, _loc_3, _loc_4, _loc_10[_loc_12 + 11], 10, -1120210379);
             _loc_4 = ii(_loc_4, _loc_5, _loc_2, _loc_3, _loc_10[_loc_12 + 2], 15, 718787259);
             _loc_3 = ii(_loc_3, _loc_4, _loc_5, _loc_2, _loc_10[_loc_12 + 9], 21, -343485551);
             _loc_2 = _loc_2 + _loc_6;
             _loc_3 = _loc_3 + _loc_7;
             _loc_4 = _loc_4 + _loc_8;
             _loc_5 = _loc_5 + _loc_9;
             _loc_12 = _loc_12 + 16;
		}
		return this.toHex(_loc_2) + this.toHex(_loc_3) + this.toHex(_loc_4) + this.toHex(_loc_5);
	}
}
