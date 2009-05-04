package com.facebook.commands.photos
{
    import com.facebook.net.*;
    import com.facebook.utils.*;

    public class GetAlbums extends FacebookCall
    {
        public var uid:String;
        public var aids:Array;
        public static var SCHEMA:Array = ["uid", "aids"];
        public static var METHOD_NAME:String = "photos.getAlbums";

        public function GetAlbums(param1:String = "", param2:Array = null)
        {
            super(METHOD_NAME);
            this.uid = param1;
            this.aids = param2;
            return;
        }// end function

        override function initialize() : void
        {
            applySchema(SCHEMA, uid, FacebookDataUtils.toArrayString(aids));
            super.initialize();
            return;
        }// end function

    }
}
