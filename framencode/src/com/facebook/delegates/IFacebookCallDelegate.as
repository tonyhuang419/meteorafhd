package com.facebook.delegates
{
    import com.facebook.net.*;
    import com.facebook.session.*;

    public interface IFacebookCallDelegate extends IEventDispatcher
    {

        public function IFacebookCallDelegate();

        function set session(param1:IFacebookSession) : void;

        function get session() : IFacebookSession;

        function close() : void;

        function set call(param1:FacebookCall) : void;

        function get call() : FacebookCall;

    }
}
