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

    public class ShopToolWindow extends BaseWindow
    {
        private var materialProxy:MaterialProxyBig;
        private var directionText:TextField;
        private var confirmButton:LipiButton;
        private var priceText:TextField;
        private var moneyIcon:MoneyIcon;
        private var errorText:TextField;
        private var userFB:int = 10000;
        private var diamondMoneyIcon:MoneyIcon;
        private var diamondPriceText:TextField;
        private var _shopToolForm:Object;
        private var userMoney:int = 500000;
        private var aFBDiamond:Sprite;
        private var numbericStepper:NumbericStepper;

        public function ShopToolWindow()
        {
            width = 400;
            height = 335;
            title = Language.L["shopToolWindow"];
            windowName = "ShopToolWindow";
            mode = true;
            return;
        }// end function

        override public function keyEnter() : void
        {
            confirmButtonClick();
            return;
        }// end function

        private function overflow(param1:int) : void
        {
            if (data["price"] != 0)
            {
                if (userMoney < data["price"] * param1)
                {
                    if (confirmButton != null)
                    {
                        confirmButton.enable = false;
                    }// end if
                    if (errorText != null)
                    {
                        errorText.text = Language.L["对不起，你的金币不足。"];
                    }// end if
                }
                else
                {
                    if (confirmButton != null)
                    {
                        confirmButton.enable = true;
                    }// end if
                    if (errorText != null)
                    {
                        errorText.text = "";
                    }// end if
                }// end else if
            }
            else if (userFB > 0 || Version.SNS != Version.QQ)
            {
                if (userFB < data["FBPrice"] * param1)
                {
                    if (confirmButton != null)
                    {
                        confirmButton.enable = false;
                    }// end if
                    if (errorText != null)
                    {
                        errorText.text = Language.L["你的F币不足。"];
                    }// end if
                }
                else
                {
                    if (confirmButton != null)
                    {
                        confirmButton.enable = true;
                    }// end if
                    if (errorText != null)
                    {
                        errorText.text = "";
                    }// end if
                }// end else if
            }// end else if
            return;
        }// end function

        override public function init() : void
        {
            materialProxy = new MaterialProxyBig();
            materialProxy.x = 20;
            materialProxy.y = 50;
            addChild(materialProxy);
            moneyIcon = new MoneyIcon(MoneyIcon.X);
            addChild(moneyIcon);
            priceText = new TextField();
            priceText.selectable = false;
            priceText.defaultTextFormat = new TextFormat("fontForte", 22, 16737792);
            priceText.embedFonts = true;
            priceText.text = "300";
            priceText.width = 100;
            priceText.height = 22;
            addChild(priceText);
            if (Version.SNS == Version.QQ)
            {
                moneyIcon.x = 225;
                moneyIcon.y = 80;
                priceText.x = 258;
                priceText.y = 74;
            }
            else
            {
                moneyIcon.x = 45;
                moneyIcon.y = 168;
                priceText.x = 76;
                priceText.y = 165;
            }// end else if
            var _loc_1:* = MData.getInstance().mainData.currentUser;
            if (!_loc_1.hasOwnProperty("yellowstatus") || _loc_1["yellowstatus"] == 0 || _loc_1["yellowstatus"] == null)
            {
            }
            else
            {
                aFBDiamond = MaterialLib.getInstance().getMaterial("FBDiamond") as Sprite;
                aFBDiamond.visible = false;
                aFBDiamond.x = 240;
                aFBDiamond.y = 80;
                addChild(aFBDiamond);
                diamondMoneyIcon = new MoneyIcon(MoneyIcon.FB);
                diamondMoneyIcon.visible = false;
                diamondMoneyIcon.x = 320;
                diamondMoneyIcon.y = 80;
                addChild(diamondMoneyIcon);
                diamondPriceText = new TextField();
                diamondPriceText.x = 350;
                diamondPriceText.y = 74;
                diamondPriceText.selectable = false;
                diamondPriceText.defaultTextFormat = new TextFormat("fontForte", 22, 39423);
                diamondPriceText.embedFonts = true;
                diamondPriceText.text = "300";
                diamondPriceText.width = 100;
                diamondPriceText.height = 22;
                diamondPriceText.visible = false;
                addChild(diamondPriceText);
            }// end else if
            numbericStepper = new NumbericStepper();
            numbericStepper.max_num = 99;
            numbericStepper.x = 220;
            numbericStepper.y = 105;
            numbericStepper.addEventListener(UIEvent.TEXT_CHANGE, numChange);
            addChild(numbericStepper);
            _shopToolForm = MaterialLib.getInstance().getMaterial("ShopToolForm") as Object;
            _shopToolForm.x = 160;
            _shopToolForm.y = 40;
            addChild(_shopToolForm as Sprite);
            errorText = new TextField();
            errorText.mouseEnabled = false;
            errorText.selectable = false;
            errorText.x = 0;
            errorText.y = 260;
            errorText.width = 400;
            errorText.height = 21;
            errorText.defaultTextFormat = new TextFormat("Tahoma", 12, 13369344, null, null, null, null, null, TextFormatAlign.CENTER);
            errorText.text = "";
            addChild(errorText);
            directionText = new TextField();
            directionText.selectable = false;
            directionText.x = 205;
            directionText.y = 126;
            directionText.width = 150;
            directionText.height = 22;
            directionText.defaultTextFormat = new TextFormat("Tahoma", 12, 6710886);
            directionText.text = Language.replaceText("buyNum", {minNum:1, maxNum:99});
            addChild(directionText);
            confirmButton = new LipiButton();
            confirmButton.bgAlpha = 0;
            confirmButton.bgSkin = new LipiSkin(MaterialLib.getInstance().getClass("ButtonOrange"));
            confirmButton.width = 64;
            confirmButton.height = 25;
            confirmButton.x = width / 2 - confirmButton.width - 10;
            confirmButton.y = height - 40;
            confirmButton.label = Language.L["确定"];
            confirmButton.textColor = 16777215;
            confirmButton.addEventListener(MouseEvent.CLICK, confirmButtonClick);
            addChild(confirmButton);
            var _loc_2:* = new LipiButton();
            _loc_2.bgAlpha = 0;
            _loc_2.bgSkin = new LipiSkin(MaterialLib.getInstance().getClass("ButtonBlue"));
            _loc_2.width = 64;
            _loc_2.height = 25;
            _loc_2.x = width / 2 + 10;
            _loc_2.y = height - 40;
            _loc_2.label = Language.L["取消"];
            _loc_2.textColor = 16777215;
            _loc_2.addEventListener(MouseEvent.CLICK, cancelButtonClick);
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

        private function numChange(param1:UIEvent) : void
        {
            var _loc_2:int;
            if (data["price"] != 0)
            {
                _loc_2 = data["price"] * numbericStepper.get_num;
            }
            else
            {
                _loc_2 = data["FBPrice"] * numbericStepper.get_num;
                if (diamondPriceText != null)
                {
                    diamondPriceText.text = String(int(data["YFBPrice"]) * numbericStepper.get_num);
                }// end if
            }// end else if
            priceText.text = _loc_2.toString();
            overflow(numbericStepper.get_num);
            return;
        }// end function

        private function cancelButtonClick(param1:MouseEvent) : void
        {
            var _loc_2:* = new WindowEvent(WindowEvent.CLOSE);
            _loc_2.window = this;
            ViewControl.getInstance().dispatchEvent(_loc_2);
            return;
        }// end function

        private function confirmButtonClick(param1:MouseEvent = null) : void
        {
            var _loc_2:Boolean;
            var _loc_3:WindowEvent;
            if (confirmButton.enable == true)
            {
                _loc_2 = false;
                if (data["price"] == 0)
                {
                    _loc_2 = true;
                }// end if
                Command.getInstance().mainCommand.buyTool(data["tId"], numbericStepper.get_num, data["type"], _loc_2, data["tName"]);
                _loc_3 = new WindowEvent(WindowEvent.CLOSE);
                _loc_3.window = this;
                ViewControl.getInstance().dispatchEvent(_loc_3);
            }// end if
            return;
        }// end function

        private function setData() : void
        {
            if (data == null)
            {
                return;
            }// end if
            if (priceText != null)
            {
                moneyIcon.x = 225;
                moneyIcon.y = 80;
                priceText.x = 258;
                priceText.y = 74;
                if (data["price"] != 0)
                {
                    priceText.defaultTextFormat = new TextFormat("fontForte", 22, 16737792);
                    priceText.text = data["price"];
                    moneyIcon.type = MoneyIcon.GOLD;
                    if (aFBDiamond != null)
                    {
                        aFBDiamond.visible = false;
                        diamondMoneyIcon.visible = false;
                        diamondPriceText.visible = false;
                    }// end if
                }
                else
                {
                    priceText.defaultTextFormat = new TextFormat("fontForte", 22, 39423);
                    priceText.text = data["FBPrice"];
                    moneyIcon.type = MoneyIcon.FB;
                    if (aFBDiamond != null)
                    {
                        moneyIcon.x = 165;
                        moneyIcon.y = 80;
                        priceText.x = 198;
                        priceText.y = 74;
                        aFBDiamond.visible = true;
                        diamondMoneyIcon.visible = true;
                        diamondPriceText.visible = true;
                        diamondPriceText.text = data["YFBPrice"];
                    }// end if
                }// end if
            }// end else if
            if (materialProxy != null)
            {
                materialProxy.setContent(data["type"], data["tId"]);
            }// end if
            var _loc_1:* = MData.getInstance().mainData.host;
            userMoney = _loc_1["money"];
            userFB = _loc_1["FB"];
            overflow(1);
            if (_shopToolForm != null)
            {
                (_shopToolForm.toolName as TextField).defaultTextFormat = new TextFormat("Tahoma", 16, 3381555, true);
                _shopToolForm.toolName.text = data["tName"];
                if (data["type"] == 3)
                {
                    _shopToolForm.toolType.text = Language.L["化肥"];
                    if (data["tId"] == 6 || data["tId"] == 5)
                    {
                        _shopToolForm.toolType.text = Language.L["特殊道具"];
                    }// end if
                    if (directionText != null)
                    {
                        directionText.visible = true;
                    }// end if
                    if (numbericStepper != null)
                    {
                        numbericStepper.visible = true;
                    }// end if
                }
                else if (data["type"] == 1000)
                {
                    _shopToolForm.toolType.text = "乐事薯片工厂";
                    if (directionText != null)
                    {
                        directionText.visible = false;
                    }// end if
                    if (numbericStepper != null)
                    {
                        numbericStepper.visible = false;
                    }// end if
                }
                else
                {
                    _shopToolForm.toolType.text = Language.L["狗"];
                    if (directionText != null)
                    {
                        directionText.visible = false;
                    }// end if
                    if (numbericStepper != null)
                    {
                        numbericStepper.visible = false;
                    }// end else if
                }// end else if
                _shopToolForm.toolDirection.htmlText = data["depict"];
            }// end if
            return;
        }// end function

    }
}
