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
            return "15l3h4kh";
        }// end function

        public static function set encodeKey(param1:String) : void
        {
            _encodeKey = param1;
            return;
        }// end function

    }
}
