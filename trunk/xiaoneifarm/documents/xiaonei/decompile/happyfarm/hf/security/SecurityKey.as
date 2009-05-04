package hf.security
{

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
            var _loc_1:Bitmap;
            var _loc_2:String;
            if (_encodeKey != "inu")
            {
                return _encodeKey;
            }// end if
            _loc_1 = new KEYJPG();
            _loc_2 = _loc_1.bitmapData.getPixel32(3, 5).toString(23);
            return _loc_2;
        }// end function

        public static function set encodeKey(param1:String) : void
        {
            _encodeKey = param1;
            return;
        }// end function

    }
}
