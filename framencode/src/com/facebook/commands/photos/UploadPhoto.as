package com.facebook.commands.photos
{
    import com.facebook.data.photos.*;
    import com.facebook.net.*;

    public class UploadPhoto extends FacebookCall
    {
        public var uid:String;
        public var caption:String;
        public var data:Object;
        public var uploadedPhoto:FacebookPhoto;
        public var aid:String;
        public var uploadType:String = "jpeg";
        public var uploadQuality:uint = 80;
        public static var SCHEMA:Array = ["data", "aid", "caption", "uid"];
        public static var METHOD_NAME:String = "photos.upload";

        public function UploadPhoto(param1:Object = null, param2:String = null, param3:String = null, param4:String = null)
        {
            uploadType = UploadPhotoTypes.JPEG;
            uploadQuality = 80;
            super(METHOD_NAME);
            this.data = param1;
            this.aid = param2;
            this.caption = param3;
            this.uid = param4;
            return;
        }// end function

        override function initialize() : void
        {
            applySchema(SCHEMA, data, aid, caption, uid);
            super.initialize();
            return;
        }// end function

    }
}
