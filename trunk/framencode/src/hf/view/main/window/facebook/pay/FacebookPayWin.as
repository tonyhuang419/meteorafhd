package hf.view.main.window.facebook.pay
{
    import com.adobe.crypto.*;
    import flash.display.*;
    import flash.events.*;
    import flash.net.*;
    import flash.text.*;
    import hf.model.*;
    import hf.security.*;
    import hf.view.*;
    import hf.view.common.*;

    public class FacebookPayWin extends AddButtonWindow
    {

        public function FacebookPayWin()
        {
            width = 440;
            height = 350;
            return;
        }// end function

        override public function init() : void
        {
            var _loc_1:* = MaterialLib.getInstance().getMaterial("PayWay") as Sprite;
            var _loc_2:* = MaterialLib.getInstance().getMaterial("Add_100_Coins") as SimpleButton;
            var _loc_3:* = MaterialLib.getInstance().getMaterial("Add_200_Coins") as SimpleButton;
            var _loc_4:* = MaterialLib.getInstance().getMaterial("Add_500_Coins") as SimpleButton;
            addChild(_loc_1);
            addChild(_loc_2);
            addChild(_loc_3);
            addChild(_loc_4);
            _loc_1.x = (width - _loc_1.width) / 2;
            _loc_1.y = 40;
            _loc_2.x = (width - _loc_2.width - _loc_3.width - (width - _loc_2.width - _loc_3.width) / 3) / 2;
            _loc_2.y = _loc_1.y + _loc_1.height + 20;
            _loc_3.x = _loc_2.x + _loc_2.width + (width - _loc_2.width - _loc_3.width) / 3;
            _loc_3.y = _loc_2.y;
            _loc_4.x = (width - _loc_4.width) / 2;
            _loc_4.y = _loc_2.y + _loc_2.height + 20;
            var _loc_5:* = new TextField();
            new TextField().text = "You paid but did not get credits?  \n Please email us at: pay@fminutes.com";
            _loc_5.setTextFormat(new TextFormat("Tahoma", 14, 3355443));
            _loc_5.selectable = false;
            _loc_5.width = 260;
            _loc_5.height = 40;
            addChild(_loc_5);
            _loc_5.x = (width - _loc_5.width) / 2;
            _loc_5.y = _loc_4.y + _loc_4.height + 15;
            addButton("Cancel", "ButtonBlue", 80, 25, closeHandler);
            super.init();
            _loc_2.addEventListener(MouseEvent.CLICK, payFor100);
            _loc_3.addEventListener(MouseEvent.CLICK, payFor200);
            _loc_4.addEventListener(MouseEvent.CLICK, payFor500);
            return;
        }// end function

        private function payFor200(param1:Event) : void
        {
            send("20");
            return;
        }// end function

        private function send(param1:String) : void
        {
            var _loc_2:* = INI.getInstance().data.pay.payment.@url;
            var _loc_3:* = new URLRequest(_loc_2);
            var _loc_4:* = new URLVariables();
            var _loc_5:* = MData.getInstance().mainData.serverTime;
            var _loc_6:* = MD5.hash(_loc_5 + SecurityKey.encodeKey);
            var _loc_7:* = String(_loc_5);
            _loc_4.inuId = SessionKey.value;
            _loc_4.amount = param1;
            _loc_4.farmKey = _loc_6;
            _loc_4.farmTime = _loc_7;
            _loc_3.data = _loc_4;
            navigateToURL(_loc_3, "_top");
            return;
        }// end function

        private function payFor500(param1:Event) : void
        {
            send("50");
            return;
        }// end function

        private function payFor100(param1:Event) : void
        {
            send("10");
            return;
        }// end function

    }
}
