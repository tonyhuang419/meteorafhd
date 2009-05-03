package hf.view.main.window.div
{
    import flash.display.*;
    import flash.text.*;
    import hf.control.*;
    import hf.model.*;
    import hf.view.*;
    import hf.view.common.*;
    import hf.view.main.window.shop.*;

    public class Renew extends AddButtonWindow
    {
        private var diyData:Object;
        public static const BUYDIY_FB:String = "buyDiyUseFb";
        public static const BUYDIY:String = "buyDiy";

        public function Renew(param1:Object)
        {
            this.width = 400;
            if (Version.SNS == Version.QQ)
            {
                this.height = 260;
            }
            else if (Version.SNS == Version.XIAONEI)
            {
                this.height = 300;
            }// end else if
            this.height = 300;
            this.title = Language.L["装饰续费"];
            this.diyData = param1;
            return;
        }// end function

        override public function init() : void
        {
            myInit();
            super.init();
            return;
        }// end function

        private function showWindow(param1:ViewEvent) : void
        {
            this.visible = true;
            return;
        }// end function

        private function viewBg() : void
        {
            var _loc_1:* = new PreviewBackWindow();
            var _loc_2:* = new WindowEvent(WindowEvent.OPEN);
            _loc_2.window = _loc_1;
            ViewControl.getInstance().dispatchEvent(_loc_2);
            this.visible = false;
            ViewControl.getInstance().dispatchEvent(new ViewEvent("hideWindow"));
            Command.getInstance().mainCommand.preview(diyData);
            return;
        }// end function

        private function myInit() : void
        {
            var _loc_1:MaterialProxy;
            var _loc_4:Sprite;
            var _loc_9:Sprite;
            var _loc_15:MoneyIcon;
            removeAllButton();
            addButton(Language.L["续费"], "ButtonOrange", 60, 25, useGoldBuy);
            addButton(Language.L["取消"], "ButtonBlue", 60, 25, closeWindow);
            _loc_1 = new MaterialProxy(MaterialProxy.BIG);
            var _loc_2:* = MaterialLib.getInstance().getClass("ItemBorder");
            var _loc_3:* = new _loc_2 as Sprite;
            _loc_4 = new _loc_2 as Sprite;
            _loc_3.width = 120;
            _loc_3.height = 120;
            _loc_4.width = 120;
            _loc_4.height = 120;
            _loc_1.setContent("2", diyData.itemId, true);
            _loc_1.x = 30;
            _loc_1.y = 50;
            _loc_3.x = 30;
            _loc_3.y = 50;
            _loc_4.x = 30;
            _loc_4.y = 50;
            _loc_1.mask = _loc_3;
            addWindowChild(_loc_1);
            addWindowChild(_loc_3);
            addWindowChild(_loc_4);
            var _loc_5:* = new TextField();
            new TextField().text = diyData.itemName;
            _loc_5.setTextFormat(new TextFormat("Tahoma", 24, 3381555, true, null, null, null, null, "center"));
            _loc_5.width = 200;
            _loc_5.x = 180;
            _loc_5.y = 45;
            _loc_5.selectable = false;
            addWindowChild(_loc_5);
            var _loc_6:* = diyData.validTime - MData.getInstance().mainData.serverTime;
            var _loc_7:* = new TextField();
            new TextField().text = Language.replaceText("outtime", {day:(_loc_6 - _loc_6 % 86400) / 86400});
            _loc_7.setTextFormat(new TextFormat("Tahoma", 14, 3355443, false, null, null, null, null, "center"));
            _loc_7.width = 200;
            _loc_7.x = 180;
            _loc_7.y = 90;
            _loc_7.selectable = false;
            addWindowChild(_loc_7);
            var _loc_8:* = new TextField();
            new TextField().text = "现在续费可享受             优惠";
            _loc_8.setTextFormat(new TextFormat("Tahoma", 14, 3355443, false, null, null, null, null, "center"));
            _loc_8.width = 200;
            _loc_8.x = 180;
            _loc_8.y = 120;
            _loc_8.selectable = false;
            addWindowChild(_loc_8);
            _loc_9 = MaterialLib.getInstance().getMaterial("DiscountText") as Sprite;
            _loc_9.x = 288;
            _loc_9.y = 120;
            addWindowChild(_loc_9);
            var _loc_10:* = new TextField();
            new TextField().text = Language.L["原价："];
            _loc_10.setTextFormat(new TextFormat("Tahoma", 12, 3355443, false, null, null, null, null, "right"));
            _loc_10.width = 75;
            _loc_10.x = 190;
            _loc_10.y = 150;
            _loc_10.selectable = false;
            addWindowChild(_loc_10);
            var _loc_11:* = new TextField();
            new TextField().text = Language.L["折后价："];
            _loc_11.setTextFormat(new TextFormat("Tahoma", 12, 3355443, false, null, null, null, null, "right"));
            _loc_11.width = 75;
            _loc_11.x = 190;
            _loc_11.y = 175;
            _loc_11.selectable = false;
            addWindowChild(_loc_11);
            var _loc_12:* = new TextField();
            new TextField().text = Language.L["立即节省："];
            _loc_12.setTextFormat(new TextFormat("Tahoma", 12, 3355443, false, null, null, null, null, "right"));
            _loc_12.width = 75;
            _loc_12.x = 190;
            _loc_12.y = 200;
            _loc_12.selectable = false;
            addWindowChild(_loc_12);
            var _loc_13:* = new TextField();
            new TextField().text = Language.L["可获得经验："];
            _loc_13.setTextFormat(new TextFormat("Tahoma", 12, 3355443, false, null, null, null, null, "right"));
            _loc_13.width = 75;
            _loc_13.x = 190;
            _loc_13.y = 225;
            _loc_13.selectable = false;
            addWindowChild(_loc_13);
            var _loc_14:* = new TextField();
            new TextField().text = "" + int(parseInt(diyData.exp) * 0.6);
            _loc_14.setTextFormat(new TextFormat("Tahoma", 12, 3355443, false, null, null, null, null, "left"));
            _loc_14.width = 85;
            _loc_14.x = 265;
            _loc_14.y = 225;
            _loc_14.selectable = false;
            addWindowChild(_loc_14);
            _loc_15 = new MoneyIcon();
            _loc_15.x = 260;
            _loc_15.y = 150;
            addWindowChild(_loc_15);
            var _loc_16:* = new TextField();
            new TextField().text = diyData.price;
            _loc_16.setTextFormat(new TextFormat("fontForte", 22, 10066329, true));
            _loc_16.embedFonts = true;
            _loc_16.width = 100;
            _loc_16.x = 295;
            _loc_16.y = 142;
            _loc_16.selectable = false;
            addWindowChild(_loc_16);
            var _loc_17:* = MaterialLib.getInstance().getMaterial("DelLine") as Sprite;
            (MaterialLib.getInstance().getMaterial("DelLine") as Sprite).x = 300;
            _loc_17.y = 152;
            _loc_17.width = 50;
            _loc_17.height = 15;
            addWindowChild(_loc_17);
            var _loc_18:* = new MoneyIcon();
            new MoneyIcon().x = 260;
            _loc_18.y = 175;
            addWindowChild(_loc_18);
            var _loc_19:* = new TextField();
            var _loc_20:* = parseInt(diyData.price);
            var _loc_21:* = int(_loc_20 * 0.6);
            _loc_19.text = "" + _loc_21;
            _loc_19.setTextFormat(new TextFormat("fontForte", 22, 16737792, true));
            _loc_19.embedFonts = true;
            _loc_19.width = 100;
            _loc_19.x = 295;
            _loc_19.y = 167;
            _loc_19.selectable = false;
            addWindowChild(_loc_19);
            var _loc_22:* = new MoneyIcon();
            new MoneyIcon().x = 260;
            _loc_22.y = 200;
            addWindowChild(_loc_22);
            var _loc_23:* = new TextField();
            new TextField().text = "" + (_loc_20 - _loc_21);
            _loc_23.setTextFormat(new TextFormat("fontForte", 22, 3381504, true));
            _loc_23.embedFonts = true;
            _loc_23.width = 100;
            _loc_23.x = 295;
            _loc_23.y = 192;
            _loc_23.selectable = false;
            addWindowChild(_loc_23);
            return;
        }// end function

        private function empty() : void
        {
            return;
        }// end function

        private function useGoldBuy() : void
        {
            Command.getInstance().mainCommand.diyRenew(diyData.id);
            closeWindow();
            return;
        }// end function

    }
}
