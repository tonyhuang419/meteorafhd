﻿package com.facebook.data.auth
{
    import com.facebook.data.*;

    public class GetSessionData extends FacebookData
    {
        public var uid:String;
        public var expires:Date;
        public var session_key:String;
        public var secret:String;

        public function GetSessionData()
        {
            return;
        }// end function

    }
}
