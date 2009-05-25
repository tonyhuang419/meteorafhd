package hf.view.main.window.xiaonei.pay
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

    public class XiaoneiPayWin extends AddButtonWindow
    {

        public function XiaoneiPayWin()
        {
            width = 400;
            height = 280;
            title = "兑换F币";
            return;
        }// end function

        override public function init() : void
        {
            var _loc_4:TextField;
            var _loc_1:* = MaterialLib.getInstance().getMaterial("Add_100_Coins") as SimpleButton;
            var _loc_2:* = MaterialLib.getInstance().getMaterial("Add_200_Coins") as SimpleButton;
            var _loc_3:* = MaterialLib.getInstance().getMaterial("Add_500_Coins") as SimpleButton;
            addChild(_loc_1);
            addChild(_loc_2);
            addChild(_loc_3);
            _loc_1.x = (width / 2 - _loc_1.width) / 2;
            _loc_1.y = 40;
            _loc_2.x = _loc_1.x + _loc_1.width + (width - _loc_1.width - _loc_2.width) / 3;
            _loc_2.y = _loc_1.y;
            _loc_3.x = (width - _loc_3.width) / 2;
            _loc_3.y = _loc_1.y + _loc_1.height + 20;
            _loc_4 = new TextField();
            _loc_4.text = "F币可以用来购买特殊道具和装饰。";
            _loc_4.setTextFormat(new TextFormat("Tahoma", 14, 13369344, true, null, null, null, null, "center"));
            _loc_4.selectable = false;
            _loc_4.width = width;
            _loc_4.height = 40;
            addChild(_loc_4);
            _loc_4.x = 0;
            _loc_4.y = _loc_3.y + _loc_3.height + 15;
            addButton("充值校内豆", "ButtonOrange", 90, 25, increase);
            addButton("取消", "ButtonBlue", 70, 25, closeHandler);
            super.init();
            _loc_1.addEventListener(MouseEvent.CLICK, payFor100);
            _loc_2.addEventListener(MouseEvent.CLICK, payFor200);
            _loc_3.addEventListener(MouseEvent.CLICK, payFor500);
            return;
        }// end function

        private function payFor200(param1:Event) : void
        {
            send("20");
            return;
        }// end function

        private function increase() : void
        {
            var _loc_1:* = INI.getInstance().data.pay.increase.@url;
            var _loc_2:* = new URLRequest(_loc_1);
            navigateToURL(_loc_2, "_bank");
            closeHandler();
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

    }
}
