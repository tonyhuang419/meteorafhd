package com.facebook.errors
{
    import flash.events.*;
    import flash.net.*;

    public class FacebookError extends Object
    {
        public var rawResult:String;
        public var error:Error;
        public var reason:String;
        public var requestArgs:URLVariables;
        public var errorEvent:ErrorEvent;
        public var errorCode:Number;
        public var errorMsg:String;

        public function FacebookError()
        {
            return;
        }// end function

    }
}
