package com.facebook.delegates
{
    import com.facebook.data.*;
    import com.facebook.errors.*;
    import com.facebook.events.*;
    import com.facebook.net.*;
    import com.facebook.session.*;
    import flash.events.*;
    import flash.net.*;
    import flash.utils.*;

    public class WebDelegate extends EventDispatcher implements IFacebookCallDelegate
    {
        protected var connectTimer:Timer;
        protected var loadTimer:Timer;
        protected var loader:URLLoader;
        protected var parser:XMLDataParser;
        protected var _call:FacebookCall;
        protected var _session:WebSession;
        protected var fileRef:FileReference;

        public function WebDelegate(param1:FacebookCall, param2:WebSession)
        {
            this.call = param1;
            this.session = param2;
            parser = new XMLDataParser();
            connectTimer = new Timer(1000 * 8, 1);
            connectTimer.addEventListener(TimerEvent.TIMER_COMPLETE, onConnectTimeout);
            loadTimer = new Timer(1000 * 30, 1);
            loadTimer.addEventListener(TimerEvent.TIMER_COMPLETE, onLoadTimeOut);
            execute();
            return;
        }// end function

        protected function onDataComplete(param1:Event) : void
        {
            handleResult(param1.target.data as String);
            return;
        }// end function

        public function get session() : IFacebookSession
        {
            return _session;
        }// end function

        protected function onLoadTimeOut(param1:TimerEvent) : void
        {
            var _loc_2:FacebookError;
            connectTimer.stop();
            close();
            _loc_2 = new FacebookError();
            _loc_2.errorCode = FacebookErrorCodes.SERVER_ERROR;
            _loc_2.reason = FacebookErrorReason.LOAD_TIMEOUT;
            _call.handleError(_loc_2);
            dispatchEvent(new FacebookEvent(FacebookEvent.COMPLETE, false, false, false, null, _loc_2));
            return;
        }// end function

        protected function post() : void
        {
            addOptionalArguments();
            RequestHelper.formatRequest(call);
            sendRequest();
            connectTimer.start();
            return;
        }// end function

        protected function addOptionalArguments() : void
        {
            call.setRequestArgument("ss", true);
            return;
        }// end function

        protected function onHTTPStatus(param1:HTTPStatusEvent) : void
        {
            return;
        }// end function

        public function set session(param1:IFacebookSession) : void
        {
            _session = param1 as WebSession;
            return;
        }// end function

        public function set call(param1:FacebookCall) : void
        {
            _call = param1;
            return;
        }// end function

        protected function createURLLoader() : void
        {
            loader = new URLLoader();
            loader.addEventListener(Event.COMPLETE, onDataComplete);
            loader.addEventListener(HTTPStatusEvent.HTTP_STATUS, onHTTPStatus);
            loader.addEventListener(IOErrorEvent.IO_ERROR, onError);
            loader.addEventListener(SecurityErrorEvent.SECURITY_ERROR, onError);
            loader.addEventListener(Event.OPEN, onOpen);
            return;
        }// end function

        protected function onOpen(param1:Event) : void
        {
            connectTimer.stop();
            loadTimer.start();
            return;
        }// end function

        protected function clean() : void
        {
            connectTimer.stop();
            loadTimer.stop();
            if (loader == null)
            {
                return;
            }// end if
            loader.removeEventListener(Event.COMPLETE, onDataComplete);
            loader.removeEventListener(IOErrorEvent.IO_ERROR, onError);
            loader.removeEventListener(SecurityErrorEvent.SECURITY_ERROR, onError);
            loader.removeEventListener(Event.OPEN, onOpen);
            return;
        }// end function

        protected function onConnectTimeout(param1:TimerEvent) : void
        {
            var _loc_2:FacebookError;
            _loc_2 = new FacebookError();
            _loc_2.errorCode = FacebookErrorCodes.SERVER_ERROR;
            _loc_2.reason = FacebookErrorReason.CONNECT_TIMEOUT;
            _call.handleError(_loc_2);
            dispatchEvent(new FacebookEvent(FacebookEvent.COMPLETE, false, false, false, null, _loc_2));
            loadTimer.stop();
            close();
            return;
        }// end function

        public function get call() : FacebookCall
        {
            return _call;
        }// end function

        protected function handleResult(param1:String) : void
        {
            var _loc_2:FacebookError;
            var _loc_3:FacebookData;
            clean();
            _loc_2 = parser.validateFacebookResponce(param1);
            if (_loc_2 == null)
            {
                _loc_3 = parser.parse(param1, call.method);
                call.handleResult(_loc_3);
            }
            else
            {
                call.handleError(_loc_2);
            }// end else if
            return;
        }// end function

        protected function sendRequest() : void
        {
            var _loc_1:URLRequest;
            createURLLoader();
            _loc_1 = new URLRequest(_session.rest_url);
            _loc_1.contentType = "application/x-www-form-urlencoded";
            _loc_1.method = URLRequestMethod.POST;
            _loc_1.data = call.args;
            loader.dataFormat = URLLoaderDataFormat.TEXT;
            loader.load(_loc_1);
            return;
        }// end function

        protected function onError(param1:ErrorEvent) : void
        {
            var _loc_2:FacebookError;
            clean();
            _loc_2 = parser.createFacebookError(param1, loader.data);
            call.handleError(_loc_2);
            dispatchEvent(new FacebookEvent(FacebookEvent.COMPLETE, false, false, false, null, _loc_2));
            return;
        }// end function

        protected function execute() : void
        {
            if (call == null)
            {
                throw new Error("No call defined.");
            }// end if
            post();
            return;
        }// end function

        public function close() : void
        {
            try
            {
                loader.close();
            }// end try
            catch (e)
            {
            }// end catch
            connectTimer.stop();
            loadTimer.stop();
            return;
        }// end function

    }
}
