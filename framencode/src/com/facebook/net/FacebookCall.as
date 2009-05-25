package com.facebook.net
{
    import com.facebook.data.*;
    import com.facebook.delegates.*;
    import com.facebook.errors.*;
    import com.facebook.events.*;
    import com.facebook.session.*;
    import flash.events.*;
    import flash.net.*;

    public class FacebookCall extends EventDispatcher
    {
        public var success:Boolean = false;
        public var delegate:IFacebookCallDelegate;
        public var error:FacebookError;
        public var session:IFacebookSession;
        public var result:FacebookData;
        public var method:String;
        public var args:URLVariables;

        public function FacebookCall(param1:String = "no_method_required", param2:URLVariables = null)
        {
            success = false;
            this.method = param1;
            this.args = param2 != null ? (param2) : (new URLVariables());
            return;
        }// end function

        function clearRequestArguments() : void
        {
            this.args = new URLVariables();
            return;
        }// end function

        function initialize() : void
        {
            return;
        }// end function

        function handleError(param1:FacebookError) : void
        {
            this.error = param1;
            success = false;
            dispatchEvent(new FacebookEvent(FacebookEvent.COMPLETE, false, false, false, null, param1));
            return;
        }// end function

        function handleResult(param1:FacebookData) : void
        {
            this.result = param1;
            success = true;
            dispatchEvent(new FacebookEvent(FacebookEvent.COMPLETE, false, false, true, param1));
            return;
        }// end function

        function setRequestArgument(param1:String, param2:Object) : void
        {
            if (param2 is Number && isNaN(param2 as Number))
            {
                return;
            }// end if
            if (param1 && param2 != null && String(param2).length > 0)
            {
                this.args[param1] = param2;
            }// end if
            return;
        }// end function

        protected function applySchema(param1:Array, ... args) : void
        {
            var _loc_3:uint;
            var _loc_4:uint;
            _loc_3 = param1.length;
            _loc_4 = 0;
            while (_loc_4++ < _loc_3)
            {
                // label
                setRequestArgument(param1[_loc_4], args[_loc_4]);
            }// end while
            return;
        }// end function

    }
}
