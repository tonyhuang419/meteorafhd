package com.facebook.delegates
{
    import com.adobe.crypto.*;
    import com.facebook.net.*;
    import flash.display.*;
    import flash.net.*;
    import flash.utils.*;

    public class RequestHelper extends Object
    {
        static var callID:int = 0;

        public function RequestHelper()
        {
            return;
        }// end function

        public static function formatRequest(param1:FacebookCall) : void
        {
            var _loc_2:IFacebookSession;
            var _loc_3:String;
            _loc_2 = param1.session;
            param1.setRequestArgument("v", _loc_2.api_version);
            if (_loc_2.api_key != null)
            {
                param1.setRequestArgument("api_key", _loc_2.api_key);
            }// end if
            if (_loc_2.session_key != null)
            {
                param1.setRequestArgument("session_key", _loc_2.session_key);
            }// end if
            _loc_3 = new Date().valueOf().toString() + (callID++).toString();
            param1.setRequestArgument("call_id", _loc_3);
            param1.setRequestArgument("method", param1.method);
            param1.setRequestArgument("sig", formatSig(param1));
            return;
        }// end function

        public static function formatSig(param1:FacebookCall) : String
        {
            var _loc_2:IFacebookSession;
            var _loc_3:Array;
            var _loc_4:String;
            var _loc_5:String;
            var _loc_6:*;
            _loc_2 = param1.session;
            _loc_3 = [];
            for (_loc_4 in param1.args)
            {
                // label
                _loc_6 = param1.args[_loc_4];
                if (_loc_4 !== "sig" && !(_loc_6 is ByteArray) && !(_loc_6 is FileReference) && !(_loc_6 is BitmapData) && !(_loc_6 is Bitmap))
                {
                    _loc_3.push(_loc_4 + "=" + _loc_6.toString());
                }// end if
            }// end of for ... in
            _loc_3.sort();
            _loc_5 = _loc_3.join("");
            if (_loc_2.secret != null)
            {
                _loc_5 = _loc_5 + _loc_2.secret;
            }// end if
            return MD5.hash(_loc_5);
        }// end function

    }
}
