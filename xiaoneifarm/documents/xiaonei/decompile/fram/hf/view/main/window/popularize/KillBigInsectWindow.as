package hf.view.main.window.popularize
{
    import com.minutes.ui.control.*;
    import com.minutes.ui.core.*;
    import flash.display.*;
    import flash.events.*;
    import flash.net.*;
    import hf.view.*;
    import hf.view.common.*;

    public class KillBigInsectWindow extends BaseWindow
    {

        public function KillBigInsectWindow()
        {
            width = 400;
            height = 250;
            title = "Notice";
            windowName = "KillBigInsect";
            mode = true;
            return;
        }// end function

        override public function init() : void
        {
            var _loc_1:* = MaterialLib.getInstance().getMaterial("KillBigInsect") as Sprite;
            _loc_1.x = 21;
            _loc_1.y = 43;
            addChild(_loc_1);
            var _loc_2:* = new LipiButton();
            _loc_2.bgAlpha = 0;
            _loc_2.bgSkin = new LipiSkin(MaterialLib.getInstance().getClass("ButtonOrange"));
            _loc_2.width = 66;
            _loc_2.height = 27;
            _loc_2.x = (width - _loc_2.width) / 2;
            _loc_2.y = height - 50;
            _loc_2.label = "Yes";
            _loc_2.textColor = 16777215;
            _loc_2.textBold = true;
            _loc_2.textSize = 14;
            _loc_2.addEventListener(MouseEvent.CLICK, confirmButtonClick);
            addChild(_loc_2);
            return;
        }// end function

        private function toUrl() : void
        {
            navigateToURL(new URLRequest("javascript:callFeed()"), "_self");
            return;
        }// end function

        private function confirmButtonClick(param1:MouseEvent) : void
        {
            toUrl();
            closeHandler();
            return;
        }// end function

    }
}
