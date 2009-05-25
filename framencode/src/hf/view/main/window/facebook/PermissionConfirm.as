package hf.view.main.window.facebook
{
    import com.minutes.ui.control.*;
    import com.minutes.ui.core.*;
    import flash.events.*;
    import flash.text.*;
    import hf.view.*;
    import hf.view.common.*;
    import hf.view.main.WindowControl.*;

    public class PermissionConfirm extends BaseWindow
    {
        private var _cancelFn:Function;
        private var _confirmFn:Function;

        public function PermissionConfirm()
        {
            width = 350;
            height = 240;
            title = "Take a Photo";
            windowName = "PermissionConfirm";
            mode = true;
            return;
        }// end function

        override public function init() : void
        {
            var _loc_3:LipiButton;
            var _loc_4:LipiButton;
            var _loc_1:* = new TextField();
            _loc_1.selectable = false;
            var _loc_2:* = new TextFormat("Tahoma", 14, 39423, true, null, null, null, null, TextFormatAlign.LEFT);
            _loc_2.leading = 5;
            _loc_1.defaultTextFormat = _loc_2;
            _loc_1.width = width - 35;
            _loc_1.height = 120;
            _loc_1.x = (width - _loc_1.width) / 2;
            _loc_1.y = 50;
            _loc_1.wordWrap = true;
            _loc_1.multiline = true;
            _loc_1.htmlText = "Photos will go into your special Happyfarm Facebook album." + " Click Confirm to open a new window and allow all photos to be stored or " + "Cancel if you want to approve each photo individually.";
            addChild(_loc_1);
            _loc_3 = new LipiButton();
            _loc_3.bgAlpha = 0;
            _loc_3.bgSkin = new LipiSkin(MaterialLib.getInstance().getClass("ButtonOrange"));
            _loc_3.width = 64;
            _loc_3.height = 25;
            _loc_3.x = width / 2 - _loc_3.width - 10;
            _loc_3.y = height - 45;
            _loc_3.label = Language.L["确定"];
            _loc_3.textColor = 16777215;
            _loc_3.addEventListener(MouseEvent.CLICK, confirmButtonClick);
            addChild(_loc_3);
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
            return;
        }// end function

        public function set cancelFn(param1:Function) : void
        {
            _cancelFn = param1;
            return;
        }// end function

        private function confirmButtonClick(param1:MouseEvent) : void
        {
            WControl.close(this);
            confirmFn();
            return;
        }// end function

        public function set confirmFn(param1:Function) : void
        {
            _confirmFn = param1;
            return;
        }// end function

        public function get confirmFn() : Function
        {
            return _confirmFn;
        }// end function

        private function cancelButtonClick(param1:MouseEvent) : void
        {
            WControl.close(this);
            cancelFn();
            return;
        }// end function

        public function get cancelFn() : Function
        {
            return _cancelFn;
        }// end function

    }
}
