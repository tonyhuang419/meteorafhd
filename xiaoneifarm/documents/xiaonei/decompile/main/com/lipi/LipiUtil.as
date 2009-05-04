package com.lipi
{
    import flash.text.*;
    import flash.utils.*;

    public class LipiUtil extends Object
    {

        public function LipiUtil()
        {
            return;
        }// end function

        public static function randRange(param1:Number, param2:Number) : Number
        {
            var _loc_3:* = Math.floor(Math.random() * (param2 - param1 + 1)) + param1;
            return _loc_3;
        }// end function

        public static function getStringLength(param1:String) : int
        {
            var _loc_2:* = new ByteArray();
            _loc_2.writeUTFBytes(param1);
            return _loc_2.length;
        }// end function

        public static function clone(param1:Object)
        {
            var _loc_2:* = new ByteArray();
            _loc_2.writeObject(param1);
            _loc_2.position = 0;
            return _loc_2.readObject();
        }// end function

        public static function getTextLangth(param1:String, param2:TextFormat = null) : Number
        {
            var _loc_3:Number;
            var _loc_4:* = new TextField();
            if (param2 != null)
            {
                _loc_4.defaultTextFormat = param2;
            }// end if
            _loc_4.autoSize = TextFieldAutoSize.LEFT;
            _loc_4.text = param1;
            _loc_3 = _loc_4.width;
            return _loc_3;
        }// end function

    }
}
