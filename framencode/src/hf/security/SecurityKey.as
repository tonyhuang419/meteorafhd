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

        public static function get encodeKey() : String
        {
        	var _loader1:Loader = new Loader();
            var _loc_1:Bitmap
            var _loc_2:String;
            if (_encodeKey != "inu")
            {
                return _encodeKey;
            }
//           _loc_1 = new KEYJPG();
			var pix:uint = new uint(0xFF464826);
            _loc_2 = pix.toString(23);

//			_loader1.load(new URLRequest("c:\image.jpg"));
//			_loader1.contentLoaderInfo.addEventListener(Event.COMPLETE,
//			function  onComplete(e:Event):void {
//				_loc_1 = _loader1.content as Bitmap;
//			}); 
//			_loc_1 = Bitmap(_loader1.content);

//            _loc_2 = _loc_1.bitmapData.getPixel32(3, 5).toString(23);
            return _loc_2;
        }


        public static function set encodeKey(param1:String) : void
        {
            _encodeKey = param1;
            return;
        }

    }
}
