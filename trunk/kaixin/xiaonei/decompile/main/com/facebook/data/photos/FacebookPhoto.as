package com.facebook.data.photos
{
    import com.facebook.data.*;

    public class FacebookPhoto extends FacebookData
    {
        public var pid:String;
        public var tags:Array;
        public var src:String;
        public var created:Date;
        public var src_big:String;
        public var caption:String;
        public var owner:Number;
        public var link:String;
        public var src_small:String;
        public var aid:String;

        public function FacebookPhoto()
        {
            tags = [];
            return;
        }// end function

    }
}
