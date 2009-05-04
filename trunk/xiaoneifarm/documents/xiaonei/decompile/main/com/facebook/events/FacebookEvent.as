package com.facebook.events
{
    import com.facebook.data.*;
    import com.facebook.errors.*;
    import flash.events.*;

    public class FacebookEvent extends Event
    {
        public var data:FacebookData;
        public var success:Boolean;
        public var error:FacebookError;
        public static const VERIFYING_SESSION:String = "verifyingSession";
        public static const WAITING_FOR_LOGIN:String = "waitingForLogin";
        public static const COMPLETE:String = "complete";
        public static const CONNECT:String = "connect";

        public function FacebookEvent(param1:String, param2:Boolean = false, param3:Boolean = false, param4:Boolean = false, param5:FacebookData = null, param6:FacebookError = null)
        {
            this.success = param4;
            this.data = param5;
            this.error = param6;
            super(param1, param2, param3);
            return;
        }// end function

        override public function toString() : String
        {
            return formatToString("FacebookEvent", "type", "success", "data", "error");
        }// end function

        override public function clone() : Event
        {
            return new FacebookEvent(type, bubbles, cancelable, success, data, error);
        }// end function

    }
}
