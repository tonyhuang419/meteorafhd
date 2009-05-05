package hf.view.main.window.facebook
{
    import com.adobe.images.*;
    import com.facebook.*;
    import com.facebook.commands.photos.*;
    import com.facebook.events.*;
    import com.facebook.session.*;
    import com.minutes.ui.control.*;
    import com.minutes.ui.core.*;
    import flash.display.*;
    import flash.events.*;
    import flash.net.*;
    import flash.text.*;
    import flash.utils.*;
    import hf.control.*;
    import hf.model.*;
    import hf.view.*;
    import hf.view.common.*;
    import hf.view.farm.land.*;

    public class TakeWindow extends BaseWindow
    {
        private var logoPhoto:Object;
        private var takeingText:TextField;
        private var bitmapData:BitmapData;
        private var _confirmFn:Function;
        private var captionLabel:TextField;
        private var bitmap:Bitmap;
        private var photo:ByteArray;
        private var captionText:TextField;

        public function TakeWindow()
        {
            width = 450;
            height = 330;
            title = "Take a Photo";
            windowName = "TakeWindow";
            mode = true;
            return;
        }// end function

        private function takeComdHandler(param1:ViewEvent) : void
        {
            bitmapData = param1.data as BitmapData;
            photo = PNGEncoder.encode(bitmapData);
            bitmap.bitmapData = bitmapData;
            bitmap.x = (width - bitmap.width) / 2;
            return;
        }// end function

        private function loadLogoPhoto() : void
        {
            var _loc_1:* = new Loader();
            _loc_1.contentLoaderInfo.addEventListener(Event.COMPLETE, loadLogoFn);
            _loc_1.contentLoaderInfo.addEventListener(IOErrorEvent.IO_ERROR, loadLogoerError);
            _loc_1.load(new URLRequest(GetCropID.getSwfUrl() + "main/outswf/logo_en.jpg"));
            return;
        }// end function

        private function loadLogoerError(param1:IOErrorEvent) : void
        {
            trace(param1);
            return;
        }// end function

        private function uploadLogo() : void
        {
            var _loc_1:WebSession;
            var _loc_2:Facebook;
            var _loc_3:UploadPhoto;
            if (logoPhoto != null && logoPhoto is Bitmap)
            {
                _loc_1 = new WebSession(FacebookArguments.api_key, FacebookArguments.ss, FacebookArguments.session_key);
                _loc_2 = new Facebook();
                _loc_2.startSession(_loc_1);
                _loc_3 = _loc_2.post(new UploadPhoto(logoPhoto, null, "Happy Farm" + " http://apps.facebook.com/happyfarmers/", null)) as UploadPhoto;
            }// end if
            return;
        }// end function

        override protected function closeHandler() : void
        {
            ViewControl.getInstance().removeEventListener("takeComd", takeComdHandler);
            bitmapData.dispose();
            super.closeHandler();
            return;
        }// end function

        private function confirmButtonClick(param1:MouseEvent) : void
        {
            if (photo == null)
            {
                return;
            }// end if
            if (logoPhoto == null && FacebookArguments.albumExist == false)
            {
                return;
            }// end if
            closeHandler();
            uploadLogo();
            uploadPhoto();
            confirmFn();
            return;
        }// end function

        override public function init() : void
        {
            var _loc_2:LipiButton;
            takeingText = new TextField();
            takeingText.selectable = false;
            takeingText.defaultTextFormat = new TextFormat("Tahoma", 18, 39423, true);
            takeingText.autoSize = TextFieldAutoSize.LEFT;
            takeingText.text = "Take a Photo";
            addChild(takeingText);
            takeingText.x = (width - takeingText.width) / 2;
            takeingText.y = 100;
            bitmap = new Bitmap();
            bitmap.x = 80;
            bitmap.y = 35;
            bitmap.scaleX = 0.35;
            bitmap.scaleY = 0.35;
            addChild(bitmap);
            captionLabel = new TextField();
            captionLabel.selectable = false;
            captionLabel.defaultTextFormat = new TextFormat("Tahoma", 14, 39423, true);
            captionLabel.autoSize = TextFieldAutoSize.LEFT;
            captionLabel.text = "Caption:";
            captionLabel.x = 25;
            captionLabel.y = 200;
            addChild(captionLabel);
            captionText = new TextField();
            captionText.maxChars = 150;
            captionText.type = TextFieldType.INPUT;
            captionText.wordWrap = true;
            captionText.border = true;
            captionText.borderColor = 39423;
            captionText.defaultTextFormat = new TextFormat("Tahoma");
            captionText.x = 25;
            captionText.y = 223;
            captionText.width = 400;
            captionText.height = 50;
            addChild(captionText);
            var _loc_1:* = new LipiButton();
            _loc_1.bgAlpha = 0;
            _loc_1.bgSkin = new LipiSkin(MaterialLib.getInstance().getClass("ButtonOrange"));
            _loc_1.width = 64;
            _loc_1.height = 25;
            _loc_1.x = width / 2 - _loc_1.width - 10;
            _loc_1.y = height - 45;
            _loc_1.label = Language.L["确定"];
            _loc_1.textColor = 16777215;
            _loc_1.addEventListener(MouseEvent.CLICK, confirmButtonClick);
            addChild(_loc_1);
            _loc_2 = new LipiButton();
            _loc_2.bgAlpha = 0;
            _loc_2.bgSkin = new LipiSkin(MaterialLib.getInstance().getClass("ButtonBlue"));
            _loc_2.width = 64;
            _loc_2.height = 25;
            _loc_2.x = width / 2 + 10;
            _loc_2.y = height - 45;
            _loc_2.label = Language.L["取消"];
            _loc_2.textColor = 16777215;
            _loc_2.addEventListener(MouseEvent.CLICK, cancelButtonClick);
            addChild(_loc_2);
            if (!FacebookArguments.albumExist)
            {
                loadLogoPhoto();
            }// end if
            var _loc_3:* = new Timer(100, 1);
            _loc_3.addEventListener(TimerEvent.TIMER, timerEventHandler);
            _loc_3.start();
            return;
        }// end function

        private function loadLogoFn(param1:Event) : void
        {
            logoPhoto = param1.currentTarget.content;
            FacebookArguments.albumExist = true;
            trace(param1);
            return;
        }// end function

        public function set confirmFn(param1:Function) : void
        {
            _confirmFn = param1;
            return;
        }// end function

        private function cancelButtonClick(param1:MouseEvent) : void
        {
            closeHandler();
            return;
        }// end function

        public function get confirmFn() : Function
        {
            return _confirmFn;
        }// end function

        private function uploadPhoto(param1:Event = null) : void
        {
            var _loc_2:* = new WebSession(FacebookArguments.api_key, FacebookArguments.ss, FacebookArguments.session_key);
            var _loc_3:* = new Facebook();
            _loc_3.startSession(_loc_2);
            var _loc_4:* = _loc_3.post(new UploadPhoto(photo, null, captionText.text + " http://apps.facebook.com/happyfarmers/", null)) as UploadPhoto;
            (_loc_3.post(new UploadPhoto(photo, null, captionText.text + " http://apps.facebook.com/happyfarmers/", null)) as UploadPhoto).addEventListener(FacebookEvent.COMPLETE, facebookComp);
            return;
        }// end function

        private function timerEventHandler(param1:TimerEvent) : void
        {
            ViewControl.getInstance().addEventListener("takeComd", takeComdHandler);
            ViewControl.getInstance().dispatchEvent(new Event("take"));
            return;
        }// end function

        private function facebookComp(param1:FacebookEvent) : void
        {
            trace(param1);
            return;
        }// end function

    }
}
