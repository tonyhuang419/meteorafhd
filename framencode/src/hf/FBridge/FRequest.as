package hf.FBridge
{
    import com.adobe.crypto.*;
    import com.adobe.serialization.json.*;
    import com.minutes.net.*;
    import flash.net.*;
    import flash.utils.*;
    import hf.model.*;
    import hf.security.*;

    public class FRequest extends Object
    {
        private static var instance:FRequest;

        public function FRequest()
        {
            if (instance != null)
            {
                throw new Error("实例化单例类出错-FRequest");
            }// end if
            return;
        }// end function

        public function postRequest2(param1:String, param2:Object, param3:Function) : void
        {
            request(param1, param2, param3, 8000, false, URLLoaderDataFormat.BINARY);
            return;
        }// end function

        public function set httpUrl(param1:String) : void
        {
            HTTPRequest.getInstance().httpUrl = param1;
            return;
        }// end function

        private function dataToString(param1:Object) : String
        {
            var _loc_5:ByteArray;
            var _loc_6:ByteArray;
            var _loc_7:int;
            var _loc_8:int;
            var _loc_9:String;
            var _loc_2:int;
            var _loc_3:int;
            var _loc_4:* = SecurityKey.decodeKey;
            if (param1 is ByteArray)
            {
                _loc_5 = param1 as ByteArray;
                _loc_5.position = 0;
                _loc_6 = new ByteArray();
                _loc_7 = 0;
                _loc_8 = _loc_5.length;
                while (_loc_7 < _loc_8)
                {
                    // label
                    if (_loc_5[_loc_7] == _loc_2)
                    {
                        if (_loc_7 + 1 != _loc_8 && _loc_5[_loc_7 + 1] == _loc_2)
                        {
                            _loc_7 = _loc_7 + 1;
                            _loc_6.writeByte(_loc_2 - _loc_4);
                        }
                        else if (_loc_7 + 1 != _loc_8 && _loc_5[_loc_7 + 1] == _loc_3)
                        {
                            _loc_7 = _loc_7 + 1;
                            _loc_6.writeByte(_loc_3 - _loc_4);
                        }
                        else
                        {
                            _loc_6.writeByte(_loc_5[_loc_7] - _loc_4);
                        }// end else if
                    }
                    else
                    {
                        _loc_6.writeByte(_loc_5[_loc_7] - _loc_4);
                    }// end else if
                    _loc_7++;
                }// end while
                _loc_6.position = 0;
                _loc_9 = _loc_6.readUTFBytes(_loc_6.length);
                return _loc_9;
            }// end if
            return param1.toString();
        }// end function

        public function get httpUrl() : String
        {
            return HTTPRequest.getInstance().httpUrl;
        }// end function

        public function getRequest(param1:String, param2:Object, param3:Function) : void
        {
            var url:* = param1;
            var value:* = param2;
            var handlerFun:* = param3;
            HTTPRequest.getInstance().getRequest(url, value, 
function (param1:String) : void
{
    handlerFun(JSON.decode(param1));
    return;
}// end function
);
            return;
        }// end function

        public function postRequest(param1:String, param2:Object, param3:Function) : void
        {
            request(param1, param2, param3, 60000, false, URLLoaderDataFormat.BINARY);
            return;
        }// end function

        private function request(param1:String, param2:Object, param3:Function, param4:int = 60000, param5:Boolean = false, param6:String = "text") : void
        {
            var url:* = param1;
            var value:* = param2;
            var handlerFun:* = param3;
            var timeout:* = param4;
            var reload:* = param5;
            var dataFormat:* = param6;
            var _timeValue:* = MData.getInstance().mainData.serverTime;
            var farmKey:* = MD5.hash(_timeValue + SecurityKey.encodeKey);
            var farmTime:* = String(_timeValue);
            url = url + ("&farmKey=" + farmKey + "&farmTime=" + farmTime + "&inuId=" + SessionKey.value);
            HTTPRequest.getInstance().postRequest(url, value, 
function (param1:Object) : void
{
    var _data:Object;
    var loadStr:String;
    var data:* = param1;
    try
    {
        loadStr = dataToString(data);
        if (/^[\{\[].*/.test(loadStr) == false)
        {
            loadStr = "{\"errorType\":\"PHPError\",\"errorContent\":\"" + loadStr + "\"}";
        }// end if
        _data = JSON.decode(loadStr);
    }// end try
    catch (e:Error)
    {
        trace(e);
    }// end catch
    if (_data.hasOwnProperty("errorType") && _data["errorType"] == "session")
    {
        MData.getInstance().mainData.sessionTimeout = _data["errorContent"];
    }// end if
    handlerFun(_data);
    return;
}// end function
, timeout, reload, dataFormat);
            return;
        }// end function

        public static function getInstance() : FRequest
        {
            if (instance == null)
            {
                instance = new FRequest;
            }// end if
            return instance;
        }// end function

    }
}
