package com.facebook.delegates
{
    import com.adobe.images.*;
    import com.facebook.commands.photos.*;
    import com.facebook.net.*;
    import com.facebook.session.*;
    import com.facebook.utils.*;
    import flash.display.*;
    import flash.events.*;
    import flash.net.*;
    import flash.utils.*;

    public class WebImageUploadDelegate extends WebDelegate
    {
        protected var count:Number = 0;
        protected var ba:ByteArray;

        public function WebImageUploadDelegate(param1:FacebookCall, param2:WebSession)
        {
            ba = new ByteArray();
            count = 0;
            super(param1, param2);
            return;
        }// end function

        protected function onUploadComplete(param1:DataEvent) : void
        {
            handleResult(param1.data);
            return;
        }// end function

        protected function uploadByteArray(param1:ByteArray) : void
        {
            var _loc_2:PostRequest;
            var _loc_3:String;
            var _loc_4:URLRequest;
            _loc_2 = new PostRequest();
            for (_loc_3 in call.args)
            {
                // label
                if (_loc_3 != "data")
                {
                    _loc_2.writePostData(_loc_3, call.args[_loc_3]);
                }// end if
            }// end of for ... in
            _loc_2.writeFileData("fn" + call.args["call_id"] + ".jpg", param1);
            _loc_2.close();
            _loc_4 = new URLRequest();
            _loc_4.method = URLRequestMethod.POST;
            _loc_4.contentType = "multipart/form-data; boundary=" + _loc_2.boundary;
            _loc_4.data = _loc_2.getPostData();
            _loc_4.url = _session.rest_url;
            createURLLoader();
            loader.dataFormat = URLLoaderDataFormat.BINARY;
            loader.load(_loc_4);
            connectTimer.start();
            return;
        }// end function

        protected function onImageLoaded(param1:Event) : void
        {
            fileRef = call.args.data as FileReference;
            uploadByteArray(fileRef["data"]);
            return;
        }// end function

        override protected function sendRequest() : void
        {
            var _loc_1:ByteArray;
            var _loc_2:URLRequest;
            var _loc_3:Object;
            var _loc_4:JPGEncoder;
            _loc_2 = new URLRequest(_session.rest_url);
            _loc_3 = call.args.data;
            if (PlayerUtils.majorVersion == 9 && _loc_3 is FileReference)
            {
                throw new TypeError("Uploading FileReference with Player 9 is unsupported.  Use either an BitmapData or ByteArray.");
            }// end if
            if (_loc_3 is Bitmap)
            {
                _loc_3 = (_loc_3 as Bitmap).bitmapData;
            }// end if
            if (PlayerUtils.majorVersion == 10 && _loc_3 is FileReference)
            {
                var _loc_5:* = _loc_3 as FileReference;
                _loc_1 = _loc_5.(_loc_3 as FileReference)["load"]();
                fileRef = _loc_3 as FileReference;
                fileRef.addEventListener(Event.COMPLETE, onImageLoaded);
            }
            else if (_loc_3 is ByteArray)
            {
                uploadByteArray(_loc_3 as ByteArray);
            }
            else
            {
                switch((call as UploadPhoto).uploadType)
                {
                    case UploadPhotoTypes.JPEG:
                    {
                        break;
                    }// end case
                    case UploadPhotoTypes.PNG:
                    {
                        break;
                    }// end case
                    default:
                    {
                        break;
                    }// end default
                }// end switch
            }// end else if
            return;
        }// end function

    }
}
