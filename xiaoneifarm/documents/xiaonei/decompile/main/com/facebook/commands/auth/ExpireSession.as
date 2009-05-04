package com.facebook.commands.auth
{
    import com.facebook.net.*;

    public class ExpireSession extends FacebookCall
    {
        public static var SCHEMA:Array = [];
        public static var METHOD_NAME:String = "auth.expireSession";

        public function ExpireSession()
        {
            super(METHOD_NAME);
            return;
        }// end function

    }
}
