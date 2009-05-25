package hf.view.main.window.shop
{
    import com.minutes.ui.control.*;
    import com.minutes.ui.core.*;
    import flash.display.*;
    import flash.events.*;
    import flash.text.*;
    import hf.control.*;
    import hf.model.*;
    import hf.view.*;
    import hf.view.common.*;
    import hf.view.main.WindowControl.*;
    import hf.view.main.window.facebook.pay.*;

    public class ShopToolWindowForFaceBook extends AddButtonWindow
    {
        private var buyC:LipiButton;
        private var materialProxy:MaterialProxyBig;
        private var moneyIconA:MoneyIcon;
        private var moneyIconB:MoneyIcon;
        private var moneyIconC:MoneyIcon;
        private var errorText:TextField;
        private var _shopToolForm:Object;
        private var buyA:LipiButton;
        private var buyB:LipiButton;

        public function ShopToolWindowForFaceBook()
        {
            width = 400;
            height = 335;
            title = Language.L["shopToolWindow"];
            windowName = "ShopToolWindow";
            mode = true;
            return;
        }// end function

        private function addCredits(param1:Event) : void
        {
            closeHandler();
            var _loc_2:* = new FacebookPayWin();
            WControl.open(_loc_2);
            return;
        }// end function

        private function setPrice() : void
        {
            var _loc_1:* = _shopToolForm.priceA as TextField;
            var _loc_2:* = _shopToolForm.priceB as TextField;
            var _loc_3:* = _shopToolForm.priceC as TextField;
            _loc_1.text = data["FBPrice"];
            if (data["tId"] != 501)
            {
                _loc_2.text = String(data["FBPrice"] * 9);
                _loc_3.text = String(data["FBPrice"] * 70);
            }
            else
            {
                _loc_2.text = String(data["FBPrice"] * 7);
                _loc_3.text = String(data["FBPrice"] * 50);
            }// end else if
            setPriceFont();
            return;
        }// end function

        override public function init() : void
        {
            materialProxy = new MaterialProxyBig();
            materialProxy.x = 20;
            materialProxy.y = 50;
            addChild(materialProxy);
            _shopToolForm = MaterialLib.getInstance().getMaterial("ShopToolFormFB") as Object;
            _shopToolForm.x = 160;
            _shopToolForm.y = 40;
            addChild(_shopToolForm as Sprite);
            errorText = new TextField();
            errorText.mouseEnabled = true;
            errorText.selectable = false;
            errorText.x = 0;
            errorText.y = 260;
            errorText.width = 400;
            errorText.height = 21;
            errorText.defaultTextFormat = new TextFormat("Tahoma", 12, 13369344, null, null, null, null, null, TextFormatAlign.CENTER);
            errorText.htmlText = "";
            addChild(errorText);
            addButton("Cancel", "ButtonBlue", 75, 25, closeHandler);
            super.init();
            if (data != null)
            {
                setData();
            }// end if
            return;
        }// end function

        private function useFBBuyDogFoodC(param1:Event) : void
        {
            var _loc_2:* = parseInt(data["tId"]);
            var _loc_3:* = parseInt(data["type"]);
            Command.getInstance().mainCommand.buyTool(_loc_2, 30, _loc_3, true);
            closeHandler();
            return;
        }// end function

        private function useFBBuyA(param1:Event) : void
        {
            var _loc_2:* = parseInt(data["tId"]);
            var _loc_3:* = parseInt(data["type"]);
            Command.getInstance().mainCommand.buyTool(_loc_2, 1, _loc_3, true);
            closeHandler();
            return;
        }// end function

        private function useFBBuyB(param1:Event) : void
        {
            var _loc_2:* = parseInt(data["tId"]);
            var _loc_3:* = parseInt(data["type"]);
            Command.getInstance().mainCommand.buyTool(_loc_2, 10, _loc_3, true);
            closeHandler();
            return;
        }// end function

        private function useFBBuyC(param1:Event) : void
        {
            var _loc_2:* = parseInt(data["tId"]);
            var _loc_3:* = parseInt(data["type"]);
            Command.getInstance().mainCommand.buyTool(_loc_2, 100, _loc_3, true);
            closeHandler();
            return;
        }// end function

        private function useFBBuyDogFoodB(param1:Event) : void
        {
            var _loc_2:* = parseInt(data["tId"]);
            var _loc_3:* = parseInt(data["type"]);
            Command.getInstance().mainCommand.buyTool(_loc_2, 7, _loc_3, true);
            closeHandler();
            return;
        }// end function

        private function setData() : void
        {
            var _loc_1:DisplayObject;
            var _loc_2:TextField;
            var _loc_3:TextField;
            if (data == null)
            {
                return;
            }// end if
            if (data["tId"] == "501")
            {
                _loc_1 = removeChild(_shopToolForm as DisplayObject);
                _shopToolForm = MaterialLib.getInstance().getMaterial("ShopToolFormDogFoodFB") as Object;
                _shopToolForm.x = _loc_1.x;
                _shopToolForm.y = _loc_1.y;
                _loc_1 = null;
                addChild(_shopToolForm as Sprite);
                buyA = addBuyButton(useFBBuyDogFoodA, 1);
                buyB = addBuyButton(useFBBuyDogFoodB, 7);
                buyC = addBuyButton(useFBBuyDogFoodC, 30);
                setPriceFont();
            }
            else if (data["FBPrice"] && data["FBPrice"] > 0)
            {
                buyA = addBuyButton(useFBBuyA, Number(data["FBPrice"]));
                buyB = addBuyButton(useFBBuyB, Number(data["FBPrice"]));
                buyC = addBuyButton(useFBBuyC, Number(data["FBPrice"]));
                setPrice();
            }// end else if
            if (buyA != null)
            {
                buyA.x = 315;
                buyA.y = 175;
            }// end if
            if (buyB != null)
            {
                buyB.x = 315;
                buyB.y = 203;
            }// end if
            if (buyC != null)
            {
                buyC.x = 315;
                buyC.y = 231;
            }// end if
            if (data["depict"])
            {
                _loc_2 = _shopToolForm.toolDirection as TextField;
                _loc_2.htmlText = data["depict"];
            }// end if
            if (data["tName"])
            {
                _loc_3 = _shopToolForm.toolName as TextField;
                _loc_3.text = data["tName"];
                _loc_3.setTextFormat(new TextFormat("Tahoma", 16, 3381555, true));
            }// end if
            if (data["tId"] && data["type"])
            {
                materialProxy.setContent(data["type"], data["tId"]);
            }// end if
            return;
        }// end function

        private function emptyFn(param1:Event) : void
        {
            errorText.htmlText = "<body>Insufficient credits. </body>";
            var _loc_2:* = MaterialLib.getInstance().getMaterial("AddCoins") as SimpleButton;
            _loc_2.addEventListener(MouseEvent.CLICK, addCredits);
            addChild(_loc_2);
            _loc_2.x = width - _loc_2.width - 25;
            _loc_2.y = errorText.y;
            return;
        }// end function

        private function setPriceFont() : void
        {
            var _loc_1:* = _shopToolForm.priceA as TextField;
            var _loc_2:* = _shopToolForm.priceB as TextField;
            var _loc_3:* = _shopToolForm.priceC as TextField;
            _loc_1.setTextFormat(new TextFormat("fontForte", 22, 26367));
            _loc_2.setTextFormat(new TextFormat("fontForte", 22, 26367));
            _loc_3.setTextFormat(new TextFormat("fontForte", 22, 26367));
            _loc_1.embedFonts = true;
            _loc_2.embedFonts = true;
            _loc_3.embedFonts = true;
            return;
        }// end function

        private function addBuyButton(param1:Function, param2:Number) : LipiButton
        {
            var _loc_3:* = new LipiButton();
            _loc_3.bgAlpha = 0;
            _loc_3.bgSkin = new LipiSkin(MaterialLib.getInstance().getClass("ButtonOrange"));
            _loc_3.width = 55;
            _loc_3.height = 23;
            _loc_3.label = Language.L["购买"];
            _loc_3.textColor = 16777215;
            var _loc_4:* = Number(MData.getInstance().mainData.host["FB"]);
            if (Number(MData.getInstance().mainData.host["FB"]) > param2)
            {
                _loc_3.addEventListener(MouseEvent.CLICK, param1);
            }
            else
            {
                _loc_3.addEventListener(MouseEvent.CLICK, emptyFn);
            }// end else if
            addChild(_loc_3);
            return _loc_3;
        }// end function

        private function useFBBuyDogFoodA(param1:Event) : void
        {
            var _loc_2:* = parseInt(data["tId"]);
            var _loc_3:* = parseInt(data["type"]);
            Command.getInstance().mainCommand.buyTool(_loc_2, 1, _loc_3, true);
            closeHandler();
            return;
        }// end function

    }
}
