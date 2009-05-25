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
        private var errorDogIcon:Sprite;
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

        private function setData() : void
        {
            if (successIcon != null && errorIcon != null && errorDogIcon != null && data != null)
            {
                if (data.hasOwnProperty("type") && data["type"] == "success")
                {
                    successIcon.visible = true;
                    errorIcon.visible = false;
                    errorDogIcon.visible = false;
                    alertText.y = 130;
                }
                else
                {
                    if (data.hasOwnProperty("type") && data["type"] == "error")
                    {
                        successIcon.visible = false;
                        errorIcon.visible = true;
                        errorDogIcon.visible = false;
                        alertText.y = 130;
                    }
                    else
                    {
                        if (data.hasOwnProperty("type") && data["type"] == "bite")
                        {
                            successIcon.visible = false;
                            errorIcon.visible = false;
                            errorDogIcon.visible = true;
                            alertText.y = 130;
                        }
                        else
                        {
                            successIcon.visible = false;
                            errorIcon.visible = false;
                            errorDogIcon.visible = false;
                            alertText.y = 110;
                        }// end else if
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

        override public function init() : void
        {
            successIcon = MaterialLib.getInstance().getMaterial("SuccessIcon") as Sprite;
            errorIcon = MaterialLib.getInstance().getMaterial("ErrorIcon") as Sprite;
            errorDogIcon = MaterialLib.getInstance().getMaterial("Dog") as Sprite;
            var _loc_4:* = (width - successIcon.width) / 2;
            errorDogIcon.x = (width - successIcon.width) / 2;
            var _loc_4:* = _loc_4;
            errorIcon.x = _loc_4;
            successIcon.x = _loc_4;
            var _loc_4:int;
            errorDogIcon.y = 40;
            var _loc_4:* = _loc_4;
            errorIcon.y = _loc_4;
            successIcon.y = _loc_4;
            var _loc_1:* = successIcon.width / errorDogIcon.width;
            errorDogIcon.scaleX = _loc_1;
            errorDogIcon.scaleY = _loc_1;
            addChild(successIcon);
            addChild(errorIcon);
            addChild(errorDogIcon);
            alertText = new TextField();
            alertText.selectable = false;
            var _loc_2:* = new TextFormat("Tahoma", 14, 3355443, null, null, null, null, null, TextFormatAlign.CENTER);
            _loc_2.leading = 5;
            alertText.defaultTextFormat = _loc_2;
            alertText.width = width - 50;
            alertText.height = 80;
            alertText.x = (width - alertText.width) / 2;
            alertText.y = 130;
            alertText.wordWrap = true;
            alertText.multiline = true;
            alertText.htmlText = "";
            addChild(alertText);
            var _loc_3:* = new LipiButton();
            _loc_3.bgAlpha = 0;
            _loc_3.bgSkin = new LipiSkin(MaterialLib.getInstance().getClass("ButtonOrange"));
            _loc_3.width = 65;
            _loc_3.height = 25;
            _loc_3.x = (width - _loc_3.width) / 2;
            _loc_3.y = height - 50;
            _loc_3.label = Language.L["确定"];
            _loc_3.textColor = 16777215;
            _loc_3.addEventListener(MouseEvent.CLICK, confirmButtonClick);
            addChild(_loc_3);
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

        override public function keyEnter() : void
        {
            confirmButtonClick();
            return;
        }// end function

    }
}
