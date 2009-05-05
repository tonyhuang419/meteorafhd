package hf.control
{
    import hf.model.*;

    public class Version extends Object
    {
        public static const QQ:String = "qq";
        public static const MANYOU:String = "manyou";
        public static const XIAOYOU:String = "xiaoyou";
        public static const FACEBOOK:String = "facebook";
        public static const XIAONEI:String = "xiaonei";
        private static var _sns:String = "xiaonei";
        public static const COM_51:String = "51";
        public static const QZONE:String = "qzone";

        public function Version()
        {
            return;
        }// end function

        public static function set SNS(param1:String) : void
        {
            _sns = param1;
            return;
        }// end function

        public static function get value() : String
        {
            var _loc_1:* = INI.getInstance().data;
            var _loc_2:* = _loc_1.version.@value;
            return _loc_2;
        }// end function

        public static function get SNS() : String
        {
            var _loc_1:* = INI.getInstance().data;
            var _loc_2:* = _loc_1.version.@value;
            var _loc_3:String;
            if (_loc_2 == "51" || _loc_2 == "xiaonei" || _loc_2 == "manyou" || _loc_2 == "facebook")
            {
                _loc_3 = "xiaonei";
            }
            else if (_loc_2 == "xiaoyou" || _loc_2 == "qzone")
            {
                _loc_3 = "qq";
            }// end else if
            return _loc_3;
        }// end function

    }
}
