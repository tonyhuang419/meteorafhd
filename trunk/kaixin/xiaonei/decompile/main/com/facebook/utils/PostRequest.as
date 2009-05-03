package com.facebook.utils
{
    import flash.utils.*;

    public class PostRequest extends Object
    {
        protected var postData:ByteArray;
        protected var _boundary:String = "-----";

        public function PostRequest()
        {
            _boundary = "-----";
            createPostData();
            return;
        }// end function

        public function getPostData() : ByteArray
        {
            postData.position = 0;
            return postData;
        }// end function

        protected function writeDoubleDash() : void
        {
            postData.writeShort(11565);
            return;
        }// end function

        public function writeFileData(param1:String, param2:ByteArray) : void
        {
            var _loc_3:String;
            var _loc_4:Number;
            writeBoundary();
            writeLineBreak();
            _loc_3 = "Content-Disposition: form-data; filename=\"";
            _loc_4 = 0;
            while (_loc_4++ < _loc_3.length)
            {
                // label
                postData.writeByte(_loc_3.charCodeAt(_loc_4));
            }// end while
            postData.writeUTFBytes(param1);
            writeQuotationMark();
            writeLineBreak();
            _loc_3 = "Content-Type: image/jpg";
            _loc_4 = 0;
            while (_loc_4++ < _loc_3.length)
            {
                // label
                postData.writeByte(_loc_3.charCodeAt(_loc_4));
            }// end while
            writeLineBreak();
            writeLineBreak();
            param2.position = 0;
            postData.writeBytes(param2, 0, param2.length);
            writeLineBreak();
            return;
        }// end function

        public function createPostData() : void
        {
            postData = new ByteArray();
            postData.endian = Endian.BIG_ENDIAN;
            return;
        }// end function

        public function writePostData(param1:String, param2:String) : void
        {
            var _loc_3:String;
            var _loc_4:uint;
            var _loc_5:Number;
            writeBoundary();
            writeLineBreak();
            _loc_3 = "Content-Disposition: form-data; name=\"" + param1 + "\"";
            _loc_4 = _loc_3.length;
            _loc_5 = 0;
            while (_loc_5++ < _loc_4)
            {
                // label
                postData.writeByte(_loc_3.charCodeAt(_loc_5));
            }// end while
            writeLineBreak();
            writeLineBreak();
            postData.writeUTFBytes(param2);
            writeLineBreak();
            return;
        }// end function

        protected function writeBoundary() : void
        {
            var _loc_1:Number;
            writeDoubleDash();
            _loc_1 = 0;
            while (_loc_1++ < boundary.length)
            {
                // label
                postData.writeByte(boundary.charCodeAt(_loc_1));
            }// end while
            return;
        }// end function

        protected function writeLineBreak() : void
        {
            postData.writeShort(3338);
            return;
        }// end function

        public function get boundary() : String
        {
            return _boundary;
        }// end function

        public function close() : void
        {
            writeBoundary();
            writeDoubleDash();
            return;
        }// end function

        protected function writeQuotationMark() : void
        {
            postData.writeByte(34);
            return;
        }// end function

        public function set boundary(param1:String) : void
        {
            _boundary = param1;
            return;
        }// end function

    }
}
