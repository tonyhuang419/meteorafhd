package com.facebook.commands.users
{
    import com.facebook.net.*;

    public class HasAppPermission extends FacebookCall
    {
        public var ext_perm:String;
        public var uid:String;
        public static var SCHEMA:Array = ["ext_perm", "uid"];
        public static var METHOD_NAME:String = "users.hasAppPermission";

        public function HasAppPermission(param1:String, param2:String = null)
        {
            super(METHOD_NAME);
            this.ext_perm = param1;
            this.uid = param2;
            return;
        }// end function

        override function initialize() : void
        {
            applySchema(SCHEMA, ext_perm, uid);
            super.initialize();
            return;
        }// end function

    }
}
