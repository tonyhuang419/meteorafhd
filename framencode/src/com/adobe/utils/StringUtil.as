package com.adobe.utils
{

    public class StringUtil extends Object
    {

        public function StringUtil()
        {
            return;
        }// end function

        public static function beginsWith(param1:String, param2:String) : Boolean
        {
            return param2 == param1.substring(0, param2.length);
        }// end function

        public static function trim(param1:String) : String
        {
            return StringUtil.ltrim(StringUtil.rtrim(param1));
        }// end function

        public static function stringsAreEqual(param1:String, param2:String, param3:Boolean) : Boolean
        {
            if (param3)
            {
                return param1 == param2;
            }// end if
            return param1.toUpperCase() == param2.toUpperCase();
        }// end function

        public static function replace(param1:String, param2:String, param3:String) : String
        {
            var _loc_9:Number;
            var _loc_4:* = new String();
            var _loc_5:Boolean;
            var _loc_6:* = param1.length;
            var _loc_7:* = param2.length;
            var _loc_8:Number;
            while (_loc_8++ < _loc_6)
            {
                // label
                if (param1.charAt(_loc_8) == param2.charAt(0))
                {
                    _loc_5 = true;
                    _loc_9 = 0;
                    while (_loc_9++ < _loc_7)
                    {
                        // label
                        if (param1.charAt(_loc_8 + _loc_9) != param2.charAt(_loc_9))
                        {
                            _loc_5 = false;
                            break;
                        }// end if
                    }// end while
                    if (_loc_5)
                    {
                        _loc_4 = _loc_4 + param3;
                        _loc_8 = _loc_8 + _loc_7--;
                        continue;
                    }// end if
                }// end if
                _loc_4 = _loc_4 + param1.charAt(_loc_8);
            }// end while
            return _loc_4;
        }// end function

        public static function rtrim(param1:String) : String
        {
            var _loc_2:* = param1.length;
            var _loc_3:* = _loc_2;
            while (_loc_3-- > 0)
            {
                // label
                if (param1.charCodeAt(_loc_3--) > 32)
                {
                    return param1.substring(0, _loc_3);
                }// end if
            }// end while
            return "";
        }// end function

        public static function endsWith(param1:String, param2:String) : Boolean
        {
            return param2 == param1.substring(param1.length - param2.length);
        }// end function

        public static function remove(param1:String, param2:String) : String
        {
            return StringUtil.replace(param1, param2, "");
        }// end function

        public static function ltrim(param1:String) : String
        {
            var _loc_2:* = param1.length;
            var _loc_3:Number;
            while (_loc_3++ < _loc_2)
            {
                // label
                if (param1.charCodeAt(_loc_3) > 32)
                {
                    return param1.substring(_loc_3);
                }// end if
            }// end while
            return "";
        }// end function

    }
}
