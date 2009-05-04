package hf.view.common
{
    import com.minutes.ui.control.*;
    import com.minutes.ui.core.*;
    import flash.display.*;
    import flash.events.*;
    import flash.text.*;
    import hf.view.*;
    import hf.view.main.WindowControl.*;

    public class ConfirmWindow extends BaseWindow
    {
        private var alertText:TextField;
        private var _confirmEnable:Boolean = true;
        private var confirmButton:LipiButton;
        private var errorText:TextField;
        private var _confirmFn:Function;

        public function ConfirmWindow()
        {
            width = 320;
            height = 240;
            title = Language.L["确定"];
            windowName = "ConfirmWindow";
            mode = true;
            return;
        }// end function

        override public function keyEnter() : void
        {
            confirmButtonClick();
            return;
        }// end function

        public function get confirmEnable() : Boolean
        {
            return _confirmEnable;
        }// end function

        public function set confirmEnable(param1:Boolean) : void
        {
            if (confirmButton != null)
            {
                confirmButton.enable = param1;
            }// end if
            _confirmEnable = param1;
            return;
        }// end function

        private function setData() : void
        {
            if (alertText != null && data != null)
            {
                if (data.hasOwnProperty("text"))
                {
                    alertText.htmlText = data["text"];
                }// end if
                if (data.hasOwnProperty("error"))
                {
                    errorText.text = data["error"];
                    errorText.x = (width - errorText.width) / 2;
                }// end if
            }// end if
            if (confirmButton != null)
            {
                confirmButton.enable = confirmEnable;
            }// end if
            return;
        }// end function

        public function get confirmFn() : Function
        {
            return _confirmFn;
        }// end function

        override public function init() : void
        {
            var _loc_4:LipiButton;
            var _loc_1:* = MaterialLib.getInstance().getMaterial("ConfirmIcon") as Sprite;
            _loc_1.x = (width - _loc_1.width) / 2;
            _loc_1.y = 40;
            addChild(_loc_1);
            alertText = new TextField();
            alertText.selectable = false;
            var _loc_2:* = new TextFormat("Tahoma", 14, 3355443, null, null, null, null, null, TextFormatAlign.CENTER);
            _loc_2.leading = 8;
            alertText.defaultTextFormat = _loc_2;
            alertText.width = width - 80;
            alertText.height = 80;
            alertText.x = (width - alertText.width) / 2;
            alertText.y = 120;
            alertText.wordWrap = true;
            alertText.multiline = true;
            alertText.htmlText = "";
            addChild(alertText);
            errorText = new TextField();
            errorText.autoSize = TextFieldAutoSize.LEFT;
            errorText.selectable = false;
            var _loc_3:* = new TextFormat("Tahoma", 12, 10027008, null, null, null, null, null, TextFormatAlign.CENTER);
            _loc_3.leading = 8;
            errorText.defaultTextFormat = _loc_3;
            errorText.height = 80;
            errorText.x = (width - errorText.width) / 2;
            errorText.y = height - 70;
            errorText.htmlText = "";
            addChild(errorText);
            confirmButton = new LipiButton();
            confirmButton.bgAlpha = 0;
            confirmButton.bgSkin = new LipiSkin(MaterialLib.getInstance().getClass("ButtonOrange"));
            confirmButton.width = 64;
            confirmButton.height = 25;
            confirmButton.x = width / 2 - confirmButton.width - 10;
            confirmButton.y = height - 45;
            confirmButton.label = Language.L["确定"];
            confirmButton.textColor = 16777215;
            confirmButton.addEventListener(MouseEvent.CLICK, confirmButtonClick);
            addChild(confirmButton);
            _loc_4 = new LipiButton();
            _loc_4.bgAlpha = 0;
            _loc_4.bgSkin = new LipiSkin(MaterialLib.getInstance().getClass("ButtonBlue"));
            _loc_4.width = 64;
            _loc_4.height = 25;
            _loc_4.x = width / 2 + 10;
            _loc_4.y = height - 45;
            _loc_4.label = Language.L["取消"];
            _loc_4.textColor = 16777215;
            _loc_4.addEventListener(MouseEvent.CLICK, cancelButtonClick);
            addChild(_loc_4);
            setData();
            return;
        }// end function

        override public function set data(param1:Object) : void
        {
            super.data = param1;
            setData();
            return;
        }// end function

        private function confirmButtonClick(param1:MouseEvent = null) : void
        {
            if (confirmButton.enable == true)
            {
                WControl.close(this);
                confirmFn();
            }// end if
            return;
        }// end function

        public function set confirmFn(param1:Function) : void
        {
            _confirmFn = param1;
            return;
        }// end function

        private function cancelButtonClick(param1:MouseEvent) : void
        {
            WControl.close(this);
            return;
        }// end function

    }
}
