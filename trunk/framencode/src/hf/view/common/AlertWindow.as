package hf.view.common
{
    import com.minutes.ui.control.*;
    import com.minutes.ui.core.*;
    import flash.display.*;
    import flash.events.*;
    import flash.net.*;
    import flash.text.*;
    import hf.control.*;
    import hf.view.*;

    public class AlertWindow extends BaseWindow
    {
        private var errorIcon:Sprite;
        private var alertText:TextField;
        private var successIcon:Sprite;

        public function AlertWindow()
        {
            width = 320;
            height = 240;
            title = Language.L["alertTitle"];
            windowName = "AlertWindow";
            mode = true;
            return;
        }// end function

        override public function init() : void
        {
            var _loc_2:LipiButton;
            successIcon = MaterialLib.getInstance().getMaterial("SuccessIcon") as Sprite;
            errorIcon = MaterialLib.getInstance().getMaterial("ErrorIcon") as Sprite;
            var _loc_3:* = (width - successIcon.width) / 2;
            errorIcon.x = (width - successIcon.width) / 2;
            successIcon.x = _loc_3;
            var _loc_3:int;
            errorIcon.y = 40;
            successIcon.y = _loc_3;
            addChild(successIcon);
            addChild(errorIcon);
            alertText = new TextField();
            alertText.selectable = false;
            var _loc_1:* = new TextFormat("Tahoma", 14, 3355443, null, null, null, null, null, TextFormatAlign.CENTER);
            _loc_1.leading = 5;
            alertText.defaultTextFormat = _loc_1;
            alertText.width = width - 50;
            alertText.height = 80;
            alertText.x = (width - alertText.width) / 2;
            alertText.y = 130;
            alertText.wordWrap = true;
            alertText.multiline = true;
            alertText.htmlText = "";
            addChild(alertText);
            _loc_2 = new LipiButton();
            _loc_2.bgAlpha = 0;
            _loc_2.bgSkin = new LipiSkin(MaterialLib.getInstance().getClass("ButtonOrange"));
            _loc_2.width = 65;
            _loc_2.height = 25;
            _loc_2.x = (width - _loc_2.width) / 2;
            _loc_2.y = height - 50;
            _loc_2.label = Language.L["确定"];
            _loc_2.textColor = 16777215;
            _loc_2.addEventListener(MouseEvent.CLICK, confirmButtonClick);
            addChild(_loc_2);
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
            var _loc_2:* = new WindowEvent(WindowEvent.CLOSE);
            _loc_2.window = this;
            ViewControl.getInstance().dispatchEvent(_loc_2);
            if (data.hasOwnProperty("gotourl"))
            {
                navigateToURL(new URLRequest(data["gotourl"]), data["target"]);
            }// end if
            return;
        }// end function

        private function setData() : void
        {
            if (successIcon != null && errorIcon != null && data != null)
            {
                if (data.hasOwnProperty("type") && data["type"] == "success")
                {
                    successIcon.visible = true;
                    errorIcon.visible = false;
                    alertText.y = 130;
                }
                else
                {
                    if (data.hasOwnProperty("type") && data["type"] == "error")
                    {
                        successIcon.visible = false;
                        errorIcon.visible = true;
                        alertText.y = 130;
                    }
                    else
                    {
                        successIcon.visible = false;
                        errorIcon.visible = false;
                        alertText.y = 110;
                    }// end else if
                }// end else if
                if (data.hasOwnProperty("text"))
                {
                    alertText.htmlText = data["text"];
                }// end if
                if (data.hasOwnProperty("text_y"))
                {
                    alertText.y = data["text_y"];
                }// end if
            }// end if
            return;
        }// end function

        override public function keyEnter() : void
        {
            confirmButtonClick();
            return;
        }// end function

    }
}
