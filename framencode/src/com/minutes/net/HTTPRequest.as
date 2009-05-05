package com.minutes.net
{
    import flash.events.*;
    import flash.net.*;
    import flash.utils.*;

    public class HTTPRequest extends Object
    {
        private var _timer:Timer;
        private var _httpUrl:String = "";
        public static const TIMEOUT:String = "timeOut";
        public static const IOERROR:String = "IOError";
        private static var instance:HTTPRequest;
        public static var REQUEST_ERROR:String = "requestError";
        public static const HTTPSTATUS:String = "httpStatus";
        public static var OVER_TIME:String = "overTime";

        public function HTTPRequest()
        {
            if (instance != null)
            {
                throw new Error("实例化单例类出错-HTTPRequest");
            }// end if
            _timer = new Timer(1000);
            _timer.start();
            return;
        }// end function

        public function set httpUrl(param1:String) : void
        {
            _httpUrl = param1;
            return;
        }// end function

        public function get httpUrl() : String
        {
            return _httpUrl;
        }// end function

        public function getRequest(param1:String, param2:Object, param3:Function, param4:uint = 60000, param5:Boolean = false, param6:String = "text") : void
        {
            request(URLRequestMethod.GET, param1, param2, param3, param4, param5, param6);
            return;
        }// end function

        public function postRequest(param1:String, param2:Object, param3:Function, param4:uint = 60000, param5:Boolean = false, param6:String = "text") : void
        {
            request(URLRequestMethod.POST, param1, param2, param3, param4, param5, param6);
            return;
        }// end function

        private function request(param1:String, param2:String, param3:Object, param4:Function, param5:uint = 60000, param6:Boolean = false, param7:String = "text") : void
        {
            var _postData:Object;
            var isHandler:Boolean;
            var aURLRequest:URLRequest;
            var aURLLoader:URLLoader;
            var ioerrorHandler:Function;
            var httpStatusHandler:Function;
            var loadCompHandler:Function;
            var timerFn:Function;
            var timerValue:int;
            var requestNum:int;
            var _uv:URLVariables;
            var _name:String;
            var method:* = param1;
            var url:* = param2;
            var value:* = param3;
            var handlerFun:* = param4;
            var timeout:* = param5;
            var reload:* = param6;
            var dataFormat:* = param7;
            var completeUrl:String;
            if (/^http.*/.test(url))
            {
                completeUrl = url;
            }
            else
            {
                completeUrl = httpUrl + url;
            }// end else if
            if (value is String)
            {
                _postData = value;
            }
            else if (value is URLVariables)
            {
                _postData = value;
            }
            else
            {
                _uv = new URLVariables();
                var _loc_9:int;
                var _loc_10:* = value;
                while (_loc_10 in _loc_9)
                {
                    // label
                    _name = _loc_10[_loc_9];
                    _uv[_name] = value[_name];
                }// end while
                _postData = _uv;
            }// end else if
            isHandler;
            aURLRequest = new URLRequest(completeUrl);
            aURLRequest.method = method;
            aURLRequest.data = _postData;
            aURLLoader = new URLLoader();
            aURLLoader.dataFormat = dataFormat;
            var clearAll:* = 
function () : void
{
    aURLLoader.close();
    _timer.removeEventListener(TimerEvent.TIMER, timerFn);
    aURLLoader.removeEventListener(IOErrorEvent.IO_ERROR, ioerrorHandler);
    aURLLoader.removeEventListener(HTTPStatusEvent.HTTP_STATUS, httpStatusHandler);
    aURLLoader.removeEventListener(Event.COMPLETE, loadCompHandler);
    return;
}// end function
;
            ioerrorHandler = 
function (param1:IOErrorEvent) : void
{
    if (!isHandler)
    {
        handlerFun("{\"errorType\":\"IOError\",\"errorContent\":\"\"}");
        isHandler = true;
    }// end if
    clearAll();
    return;
}// end function
;
            httpStatusHandler = 
function (param1:HTTPStatusEvent) : void
{
    if (param1.status != 0 && param1.status != 200)
    {
        if (!isHandler)
        {
            handlerFun("{\"errorType\":\"httpStatus\",\"errorContent\":" + param1.status + "}");
            isHandler = true;
        }// end if
    }// end if
    return;
}// end function
;
            loadCompHandler = 
function (param1:Event) : void
{
    if (!isHandler)
    {
        handlerFun(param1.currentTarget.data);
        isHandler = true;
    }// end if
    clearAll();
    return;
}// end function
;
            timerFn = 
function (param1:TimerEvent) : void
{
    if (reload)
    {
        if (getTimer() - timerValue > timeout && requestNum == 0)
        {
            aURLLoader.load(aURLRequest);
            timerValue = getTimer();
            requestNum = 1;
        }
        else if (getTimer() - timerValue > timeout && requestNum == 1)
        {
            if (!isHandler)
            {
                handlerFun("{\"errorType\":\"timeOut\",\"content\":\"\"}");
                isHandler = true;
            }// end if
            clearAll();
        }// end else if
    }
    else if (getTimer() - timerValue > timeout)
    {
        if (!isHandler)
        {
            handlerFun("{\"errorType\":\"timeOut\",\"content\":\"\"}");
            isHandler = true;
        }// end if
        clearAll();
    }// end else if
    return;
}// end function
;
            aURLLoader.addEventListener(IOErrorEvent.IO_ERROR, ioerrorHandler);
            aURLLoader.addEventListener(HTTPStatusEvent.HTTP_STATUS, httpStatusHandler);
            aURLLoader.addEventListener(Event.COMPLETE, loadCompHandler);
            aURLLoader.load(aURLRequest);
            timerValue = getTimer();
            requestNum;
            _timer.addEventListener(TimerEvent.TIMER, timerFn);
            return;
        }// end function

        public static function getInstance() : HTTPRequest
        {
            if (instance == null)
            {
                instance = new HTTPRequest;
            }// end if
            return instance;
        }// end function

    }
}
