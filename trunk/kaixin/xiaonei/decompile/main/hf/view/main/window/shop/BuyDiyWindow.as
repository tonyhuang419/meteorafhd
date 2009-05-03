package hf.view.main.window.shop
{
    import flash.display.*;
    import flash.text.*;
    import hf.control.*;
    import hf.model.*;
    import hf.view.*;
    import hf.view.common.*;

    public class BuyDiyWindow extends AddButtonWindow
    {
        private var diyData:Object;
        public static const BUYDIY_FB:String = "buyDiyUseFb";
        public static const BUYDIY:String = "buyDiy";

        public function BuyDiyWindow(param1:Object)
        {
            this.width = 400;
            this.height = 300;
            this.title = Language.L["购买装饰"];
            diyData = param1;
            diyData.FBPrice = diyData.FBPrice - 0;
            ViewControl.getInstance().addEventListener(PreviewBackWindow.PREVIEW, showWindow);
            return;
        }// end function

        override public function init() : void
        {
            myInit();
            super.init();
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

        private function showWindow(param1:ViewEvent) : void
        {
            this.visible = true;
            return;
        }// end function

        private function useFbBuy() : void
        {
            var _loc_1:* = new ViewEvent(BuyDiyWindow.BUYDIY_FB);
            _loc_1.data = {exp:diyData.exp, diyName:diyData.itemName};
            dispatchEvent(_loc_1);
            closeWindow();
            return;
        }// end function

        private function myInit() : void
        {
            var _loc_7:Sprite;
            var _loc_9:TextField;
            var _loc_17:MoneyIcon;
            var _loc_18:TextField;
            var _loc_19:Sprite;
            var _loc_20:MoneyIcon;
            var _loc_21:TextField;
            removeAllButton();
            addButton(Language.L["预览"], "ButtonBlue", 60, 25, viewBg);
            var _loc_1:* = MData.getInstance().mainData.host;
            var _loc_2:Boolean;
            var _loc_3:Boolean;
            if (_loc_1["money"] - 0 < diyData.price)
            {
                _loc_2 = true;
                addButton(Language.L["用金币购买"], "ButtonOrange", 100, 25, empty).enable = false;
            }
            else
            {
                addButton(Language.L["用金币购买"], "ButtonOrange", 100, 25, useGoldBuy);
            }// end else if
            if (diyData.FBPrice != 0)
            {
                if (_loc_1["FB"] - 0 < diyData.FBPrice)
                {
                    _loc_3 = true;
                    if (Version.SNS == Version.QQ)
                    {
                        addButton("用Q点购买", "ButtonOrange", 100, 25, empty).enable = false;
                    }
                    else
                    {
                        addButton(Language.L["用F币购买"], "ButtonOrange", 100, 25, empty).enable = false;
                    }// end else if
                }
                else if (Version.SNS == Version.QQ)
                {
                    addButton("用Q点购买", "ButtonOrange", 100, 25, useFbBuy);
                }
                else
                {
                    addButton(Language.L["用F币购买"], "ButtonOrange", 100, 25, useFbBuy);
                }// end else if
            }// end else if
            var _loc_4:* = new TextField();
            if (_loc_2 && !_loc_3)
            {
                _loc_4.text = Language.L["你的金币不足。"];
            }
            else if (!_loc_2 && _loc_3)
            {
                if (Version.SNS == Version.QQ)
                {
                    _loc_4.text = "你的Q点不足。";
                }
                else
                {
                    _loc_4.text = Language.L["你的F币不足。"];
                }// end else if
            }
            else if (_loc_2 && _loc_3)
            {
                if (Version.SNS == Version.QQ)
                {
                    _loc_4.text = "你的Q点和金币都不足。";
                }
                else
                {
                    _loc_4.text = Language.L["你的F币和金币都不足。"];
                }// end else if
            }// end else if
            _loc_4.setTextFormat(new TextFormat("Tahoma", 12, 13369344, false, null, null, null, null, "center"));
            _loc_4.width = 390;
            _loc_4.x = 10;
            _loc_4.y = 220;
            _loc_4.selectable = false;
            addWindowChild(_loc_4);
            addButton(Language.L["取消"], "ButtonBlue", 60, 25, closeWindow);
            var _loc_5:* = new MaterialProxy(MaterialProxy.BIG);
            var _loc_6:* = MaterialLib.getInstance().getClass("ItemBorder");
            _loc_7 = new MaterialLib.getInstance().getClass("ItemBorder") as Sprite;
            var _loc_8:* = new _loc_6 as Sprite;
            _loc_7.width = 120;
            _loc_7.height = 120;
            _loc_8.width = 120;
            _loc_8.height = 120;
            _loc_5.setContent("2", diyData.itemId, true);
            _loc_5.x = 30;
            _loc_5.y = 50;
            _loc_7.x = 30;
            _loc_7.y = 50;
            _loc_8.x = 30;
            _loc_8.y = 50;
            _loc_5.mask = _loc_7;
            addWindowChild(_loc_5);
            addWindowChild(_loc_7);
            addWindowChild(_loc_8);
            _loc_9 = new TextField();
            _loc_9.text = diyData.itemName;
            _loc_9.setTextFormat(new TextFormat("Tahoma", 24, 3381555, true, null, null, null, null, "center"));
            _loc_9.width = 200;
            _loc_9.x = 180;
            _loc_9.y = 45;
            _loc_9.selectable = false;
            addWindowChild(_loc_9);
            var _loc_10:* = new MoneyIcon();
            new MoneyIcon().x = 240;
            _loc_10.y = 90;
            addWindowChild(_loc_10);
            var _loc_11:* = new TextField();
            new TextField().text = diyData.price;
            _loc_11.setTextFormat(new TextFormat("fontForte", 22, 16737792, true));
            _loc_11.embedFonts = true;
            _loc_11.width = 100;
            _loc_11.x = 276;
            _loc_11.y = 85;
            _loc_11.selectable = false;
            addWindowChild(_loc_11);
            var _loc_12:int;
            if (diyData.FBPrice != 0)
            {
                if (!_loc_1.hasOwnProperty("yellowstatus"))
                {
                    _loc_12 = 0;
                }
                else if (_loc_1["yellowstatus"] != 0)
                {
                    _loc_12 = -60;
                    _loc_19 = MaterialLib.getInstance().getMaterial("FBDiamond") as Sprite;
                    addChild(_loc_19);
                    _loc_19.x = 240;
                    _loc_19.y = 120;
                    _loc_20 = new MoneyIcon(MoneyIcon.FB);
                    _loc_20.x = 325;
                    _loc_20.y = 120;
                    addWindowChild(_loc_20);
                    _loc_21 = new TextField();
                    _loc_21.defaultTextFormat = new TextFormat("fontForte", 22, 26367, true);
                    _loc_21.embedFonts = true;
                    _loc_21.selectable = false;
                    _loc_21.width = 45;
                    _loc_21.height = 23;
                    _loc_21.x = 355;
                    _loc_21.y = 115;
                    _loc_21.text = String(diyData["YFBPrice"]);
                    addWindowChild(_loc_21);
                }// end else if
                _loc_17 = new MoneyIcon(MoneyIcon.FB);
                _loc_17.x = 240 + _loc_12;
                _loc_17.y = 120;
                addWindowChild(_loc_17);
                _loc_18 = new TextField();
                _loc_18.defaultTextFormat = new TextFormat("fontForte", 22, 26367, true);
                _loc_18.embedFonts = true;
                _loc_18.selectable = false;
                _loc_18.width = 45;
                _loc_18.height = 23;
                _loc_18.x = 270 + _loc_12;
                _loc_18.y = 115;
                _loc_18.text = diyData.FBPrice;
                addWindowChild(_loc_18);
            }// end if
            var _loc_13:* = new TextField();
            new TextField().text = Language.L["有效期："];
            _loc_13.setTextFormat(new TextFormat("Tahoma", 12, 3355443, false, null, null, null, null, "right"));
            _loc_13.width = 85;
            _loc_13.x = 220;
            _loc_13.y = 155;
            _loc_13.selectable = false;
            addWindowChild(_loc_13);
            var _loc_14:* = new TextField();
            new TextField().text = Language.replaceText("validTime", {day:diyData.itemValidTime / 86400});
            _loc_14.setTextFormat(new TextFormat("Tahoma", 12, 3355443, false, null, null, null, null, "left"));
            _loc_14.width = 85;
            _loc_14.x = 305;
            _loc_14.y = 155;
            _loc_14.selectable = false;
            addWindowChild(_loc_14);
            var _loc_15:* = new TextField();
            new TextField().text = Language.L["可获得经验："];
            _loc_15.setTextFormat(new TextFormat("Tahoma", 12, 3355443, false, null, null, null, null, "right"));
            _loc_15.width = 120;
            _loc_15.x = 185;
            _loc_15.y = 175;
            _loc_15.selectable = false;
            addWindowChild(_loc_15);
            var _loc_16:* = new TextField();
            new TextField().text = "" + diyData.exp;
            _loc_16.setTextFormat(new TextFormat("Tahoma", 12, 3355443, false, null, null, null, null, "left"));
            _loc_16.width = 85;
            _loc_16.x = 305;
            _loc_16.y = 175;
            _loc_16.selectable = false;
            addWindowChild(_loc_16);
            return;
        }// end function

        private function empty() : void
        {
            return;
        }// end function

        private function useGoldBuy() : void
        {
            var _loc_1:* = new ViewEvent(BuyDiyWindow.BUYDIY);
            dispatchEvent(_loc_1);
            closeWindow();
            return;
        }// end function

    }
}
