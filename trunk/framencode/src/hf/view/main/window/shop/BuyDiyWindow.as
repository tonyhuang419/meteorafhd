package hf.view.main.window.shop
{
    import com.lipi.*;
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
            var _loc_6:TextField;
            var _loc_7:MaterialProxy;
            var _loc_11:TextField;
            var _loc_19:MoneyIcon;
            var _loc_20:TextField;
            var _loc_21:Sprite;
            var _loc_22:MoneyIcon;
            var _loc_23:TextField;
            removeAllButton();
            addButton(Language.L["预览"], "ButtonBlue", 60, 25, viewBg);
            var _loc_1:* = LipiUtil.getTextLangth(Language.L["用金币购买"]) + 30;
            var _loc_2:* = LipiUtil.getTextLangth(Language.L["用F币购买"]) + 30;
            var _loc_3:* = MData.getInstance().mainData.host;
            var _loc_4:Boolean;
            var _loc_5:Boolean;
            if (_loc_3["money"] - 0 < diyData.price)
            {
                _loc_4 = true;
                addButton(Language.L["用金币购买"], "ButtonOrange", _loc_1, 25, empty).enable = false;
            }
            else
            {
                addButton(Language.L["用金币购买"], "ButtonOrange", _loc_1, 25, useGoldBuy);
            }// end else if
            if (diyData.FBPrice != 0)
            {
                if (_loc_3["FB"] - 0 < diyData.FBPrice)
                {
                    _loc_5 = true;
                    if (Version.SNS == Version.QQ)
                    {
                        addButton("用Q点购买", "ButtonOrange", 100, 25, empty).enable = false;
                    }
                    else
                    {
                        addButton(Language.L["用F币购买"], "ButtonOrange", _loc_2, 25, empty).enable = false;
                    }// end else if
                }
                else if (Version.SNS == Version.QQ)
                {
                    addButton("用Q点购买", "ButtonOrange", 100, 25, useFbBuy);
                }
                else
                {
                    addButton(Language.L["用F币购买"], "ButtonOrange", _loc_2, 25, useFbBuy);
                }// end else if
            }// end else if
            _loc_6 = new TextField();
            if (_loc_4 && !_loc_5)
            {
                _loc_6.text = Language.L["你的金币不足。"];
            }
            else if (!_loc_4 && _loc_5)
            {
                if (Version.SNS == Version.QQ)
                {
                    _loc_6.text = "你的Q点不足。";
                }
                else
                {
                    _loc_6.text = Language.L["你的F币不足。"];
                }// end else if
            }
            else if (_loc_4 && _loc_5)
            {
                if (Version.SNS == Version.QQ)
                {
                    _loc_6.text = "你的Q点和金币都不足。";
                }
                else
                {
                    _loc_6.text = Language.L["你的F币和金币都不足。"];
                }// end else if
            }// end else if
            _loc_6.setTextFormat(new TextFormat("Tahoma", 12, 13369344, false, null, null, null, null, "center"));
            _loc_6.width = 390;
            _loc_6.x = 10;
            _loc_6.y = 220;
            _loc_6.selectable = false;
            addWindowChild(_loc_6);
            addButton(Language.L["取消"], "ButtonBlue", 60, 25, closeWindow);
            _loc_7 = new MaterialProxy(MaterialProxy.BIG);
            var _loc_8:* = MaterialLib.getInstance().getClass("ItemBorder");
            var _loc_9:* = new MaterialLib.getInstance().getClass("ItemBorder") as Sprite;
            var _loc_10:* = new _loc_8 as Sprite;
            _loc_9.width = 120;
            _loc_9.height = 120;
            _loc_10.width = 120;
            _loc_10.height = 120;
            _loc_7.setContent("2", diyData.itemId, true);
            _loc_7.x = 30;
            _loc_7.y = 50;
            _loc_9.x = 30;
            _loc_9.y = 50;
            _loc_10.x = 30;
            _loc_10.y = 50;
            _loc_7.mask = _loc_9;
            addWindowChild(_loc_7);
            addWindowChild(_loc_9);
            addWindowChild(_loc_10);
            _loc_11 = new TextField();
            _loc_11.text = diyData.itemName;
            _loc_11.setTextFormat(new TextFormat("Tahoma", 16, 3381555, true, null, null, null, null, "center"));
            _loc_11.width = 200;
            _loc_11.x = 180;
            _loc_11.y = 45;
            _loc_11.selectable = false;
            addWindowChild(_loc_11);
            var _loc_12:* = new MoneyIcon();
            new MoneyIcon().x = 240;
            _loc_12.y = 90;
            addWindowChild(_loc_12);
            var _loc_13:* = new TextField();
            new TextField().text = diyData.price;
            _loc_13.setTextFormat(new TextFormat("fontForte", 22, 16737792, true));
            _loc_13.embedFonts = true;
            _loc_13.width = 100;
            _loc_13.x = 276;
            _loc_13.y = 85;
            _loc_13.selectable = false;
            addWindowChild(_loc_13);
            var _loc_14:int;
            if (diyData.FBPrice != 0)
            {
                if (!_loc_3.hasOwnProperty("yellowstatus"))
                {
                    _loc_14 = 0;
                }
                else if (_loc_3["yellowstatus"] != 0)
                {
                    _loc_14 = -60;
                    _loc_21 = MaterialLib.getInstance().getMaterial("FBDiamond") as Sprite;
                    addChild(_loc_21);
                    _loc_21.x = 240;
                    _loc_21.y = 120;
                    _loc_22 = new MoneyIcon(MoneyIcon.FB);
                    _loc_22.x = 325;
                    _loc_22.y = 120;
                    addWindowChild(_loc_22);
                    _loc_23 = new TextField();
                    _loc_23.defaultTextFormat = new TextFormat("fontForte", 22, 26367, true);
                    _loc_23.embedFonts = true;
                    _loc_23.selectable = false;
                    _loc_23.width = 45;
                    _loc_23.height = 23;
                    _loc_23.x = 355;
                    _loc_23.y = 115;
                    _loc_23.text = String(diyData["YFBPrice"]);
                    addWindowChild(_loc_23);
                }// end else if
                _loc_19 = new MoneyIcon(MoneyIcon.FB);
                _loc_19.x = 240 + _loc_14;
                _loc_19.y = 120;
                addWindowChild(_loc_19);
                _loc_20 = new TextField();
                _loc_20.defaultTextFormat = new TextFormat("fontForte", 22, 26367, true);
                _loc_20.embedFonts = true;
                _loc_20.selectable = false;
                _loc_20.width = 45;
                _loc_20.height = 23;
                _loc_20.x = 270 + _loc_14;
                _loc_20.y = 115;
                _loc_20.text = diyData.FBPrice;
                addWindowChild(_loc_20);
            }// end if
            var _loc_15:* = new TextField();
            new TextField().text = Language.L["有效期："];
            _loc_15.setTextFormat(new TextFormat("Tahoma", 12, 3355443, false, null, null, null, null, "right"));
            _loc_15.width = 85;
            _loc_15.x = 220;
            _loc_15.y = 155;
            _loc_15.selectable = false;
            addWindowChild(_loc_15);
            var _loc_16:* = new TextField();
            new TextField().text = Language.replaceText("validTime", {day:diyData.itemValidTime / 86400});
            _loc_16.setTextFormat(new TextFormat("Tahoma", 12, 3355443, false, null, null, null, null, "left"));
            _loc_16.width = 85;
            _loc_16.x = 305;
            _loc_16.y = 155;
            _loc_16.selectable = false;
            addWindowChild(_loc_16);
            var _loc_17:* = new TextField();
            new TextField().text = Language.L["可获得经验："];
            _loc_17.setTextFormat(new TextFormat("Tahoma", 12, 3355443, false, null, null, null, null, "right"));
            _loc_17.width = 120;
            _loc_17.x = 185;
            _loc_17.y = 175;
            _loc_17.selectable = false;
            addWindowChild(_loc_17);
            var _loc_18:* = new TextField();
            new TextField().text = "" + diyData.exp;
            _loc_18.setTextFormat(new TextFormat("Tahoma", 12, 3355443, false, null, null, null, null, "left"));
            _loc_18.width = 85;
            _loc_18.x = 305;
            _loc_18.y = 175;
            _loc_18.selectable = false;
            addWindowChild(_loc_18);
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
