package hf.security
{
	import flash.display.Bitmap;
	import flash.display.Loader;
	import flash.events.Event;
	import flash.net.URLRequest;
	

    public class SecurityKey extends Object
    {
        public static var decodeKey:int = 0;
        private static var KEYJPG:Class = SecurityKey_KEYJPG;
        public static var _encodeKey:String = "inu";

        public function SecurityKey()
        {
            return;
        }// end function

		static var _loader1:Loader = new Loader();
		static var _loc_1:Bitmap
		
        public static function get encodeKey() : String
        {
            var _loc_2:String;
            _loader1.contentLoaderInfo.addEventListener(Event.INIT,initListener);
            _loader1.load(new URLRequest("c:\image.jpg"));
            if (_encodeKey != "inu")
            {
                return _encodeKey;
            }// end if
//            _loc_1 = new KEYJPG();
//			_loc_1 = Bitmap(_loader1.content);
            _loc_2 = _loc_1.bitmapData.getPixel32(3, 5).toString(23);
            return _loc_2;
        }// end function

		private static function initListener (e:Event):void {
		     //addChild(loader.content);
		    _loc_1 = Bitmap(_loader1.content);
		    
		 }

        public static function set encodeKey(param1:String) : void
        {
            _encodeKey = param1;
            return;
        }// end function

    }
}
