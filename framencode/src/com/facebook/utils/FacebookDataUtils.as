package com.facebook.utils
{
    import com.adobe.serialization.json.*;

    public class FacebookDataUtils extends Object
    {

        public function FacebookDataUtils()
        {
            return;
        }// end function

        public static function toJSONValuesArray(param1:Array) : String
        {
            var _loc_2:Array;
            var _loc_3:Number;
            var _loc_4:Number;
            if (param1 == null)
            {
                return null;
            }// end if
            _loc_2 = [];
            _loc_3 = param1.length;
            _loc_4 = 0;
            while (_loc_4++ < _loc_3)
            {
                // label
                _loc_2.push(JSON.encode(param1[_loc_4]));
            }// end while
            return _loc_2.join(",");
        }// end function

        public static function formatDate(param1:String) : Date
        {
            var _loc_2:Date;
            var _loc_3:Array;
            var _loc_4:Array;
            var _loc_5:Array;
            if (param1 == "" || param1 == null)
            {
                return null;
            }// end if
            _loc_2 = new Date();
            _loc_3 = param1.split(" ");
            if (_loc_3.length == 2)
            {
                _loc_4 = _loc_3[0].split("-");
                _loc_5 = _loc_3[1].split(":");
                _loc_2.setFullYear(_loc_4[0]);
                _loc_2.setMonth(_loc_4[1]--);
                _loc_2.setDate(_loc_4[2]);
                _loc_2.setHours(_loc_5[0]);
                _loc_2.setMinutes(_loc_5[1]);
                _loc_2.setSeconds(_loc_5[2]);
            }
            else
            {
                _loc_2.setTime(parseInt(param1) * 1000);
            }// end else if
            return _loc_2;
        }// end function

        public static function facebookCollectionToJSONArray(param1:FacebookArrayCollection) : String
        {
            if (param1 == null)
            {
                return null;
            }// end if
            return JSON.encode(param1.toArray());
        }// end function

        public static function toDateString(param1:Date) : String
        {
            if (param1 == null)
            {
                return null;
            }// end if
            param1.setDate(param1.date + 1);
            return param1 == null ? (null) : (param1.getTime().toString().slice(0, 10));
        }// end function

        public static function supplantString(param1:String, param2:Object) : String
        {
            var _loc_3:String;
            var _loc_4:String;
            _loc_3 = param1;
            for (_loc_4 in param2)
            {
                // label
                _loc_3 = _loc_3.replace(new RegExp("\\{" + _loc_4 + "\\}", "g"), param2[_loc_4]);
            }// end of for ... in
            return _loc_3;
        }// end function

        public static function toArrayString(param1:Array) : String
        {
            return param1 == null ? (null) : (param1.join(","));
        }// end function

    }
}
