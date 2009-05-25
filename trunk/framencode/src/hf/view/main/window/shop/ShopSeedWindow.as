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
    import hf.view.farm.land.*;
    import hf.view.main.WindowControl.*;

    public class ShopSeedWindow extends BaseWindow
    {
        private var materialProxy:MaterialProxyBig;
        private var _shopSeedForm:Object;
        private var confirmButton:LipiButton;
        private var errorText:TextField;
        private var cropPrice:TextField;
        private var cLevel:int = 99;
        private var _shopSeedForm2:Object;
        private var numbericStepper:NumbericStepper;
        private var userMoney:int = 500000;
        private var isinit:Boolean = false;

        public function ShopSeedWindow()
        {
            width = 400;
            height = 336;
            title = Language.L["shopSeedWindow"];
            windowName = "ShopSeedWindow";
            mode = true;
            return;
        }// end function

        private function numChange(param1:UIEvent) : void
        {
            var _loc_2:* = data["price"] * numbericStepper.get_num;
            if (cLevel >= int(data["cLevel"]))
            {
                if (userMoney < _loc_2)
                {
                    errorText.text = Language.L["对不起，你的金币不足。"];
                    confirmButton.enable = false;
                }
                else
                {
                    errorText.text = "";
                    confirmButton.enable = true;
                }// end if
            }// end else if
            cropPrice.text = String(_loc_2);
            return;
        }// end function

        override public function init() : void
        {
            var _loc_1:MoneyIcon;
            isinit = true;
            materialProxy = new MaterialProxyBig();
            materialProxy.x = 25;
            materialProxy.y = 40;
            addChild(materialProxy);
            _loc_1 = new MoneyIcon();
            addChild(_loc_1);
            cropPrice = new TextField();
            cropPrice.selectable = false;
            cropPrice.defaultTextFormat = new TextFormat("fontForte", 22, 16737792);
            cropPrice.embedFonts = true;
            cropPrice.text = "300";
            cropPrice.width = 100;
            cropPrice.height = 22;
            addChild(cropPrice);
            if (Version.SNS == Version.QQ)
            {
                _loc_1.x = 40;
                _loc_1.y = 170;
                cropPrice.x = 80;
                cropPrice.y = 165;
            }
            else
            {
                _loc_1.x = 45;
                _loc_1.y = 168;
                cropPrice.x = 76;
                cropPrice.y = 165;
            }// end else if
            numbericStepper = new NumbericStepper();
            numbericStepper.max_num = 99;
            numbericStepper.x = 30;
            numbericStepper.y = 200;
            numbericStepper.get_num = buyMaxNum();
            numbericStepper.addEventListener(UIEvent.TEXT_CHANGE, numChange);
            addChild(numbericStepper);
            var _loc_2:* = new TextField();
            _loc_2.mouseEnabled = false;
            _loc_2.selectable = false;
            _loc_2.x = 25;
            _loc_2.y = 225;
            _loc_2.autoSize = TextFieldAutoSize.LEFT;
            _loc_2.defaultTextFormat = new TextFormat("Tahoma", 12, 3355443);
            _loc_2.text = Language.replaceText("buyNum", {minNum:1, maxNum:99});
            addChild(_loc_2);
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
            confirmButton = new LipiButton();
            confirmButton.bgAlpha = 0;
            confirmButton.bgSkin = new LipiSkin(MaterialLib.getInstance().getClass("ButtonOrange"));
            confirmButton.width = 64;
            confirmButton.height = 25;
            confirmButton.x = width / 2 - confirmButton.width - 10;
            confirmButton.y = height - 50;
            confirmButton.label = Language.L["确定"];
            confirmButton.textColor = 16777215;
            confirmButton.addEventListener(MouseEvent.CLICK, confirmButtonClick);
            addChild(confirmButton);
            var _loc_3:* = new LipiButton();
            _loc_3.bgAlpha = 0;
            _loc_3.bgSkin = new LipiSkin(MaterialLib.getInstance().getClass("ButtonBlue"));
            _loc_3.width = 64;
            _loc_3.height = 25;
            _loc_3.x = width / 2 + 10;
            _loc_3.y = height - 50;
            _loc_3.label = Language.L["取消"];
            _loc_3.textColor = 16777215;
            _loc_3.addEventListener(MouseEvent.CLICK, cancelButtonClick);
            addChild(_loc_3);
            setData();
            numChange(null);
            return;
        }// end function

        override public function set data(param1:Object) : void
        {
            super.data = param1;
            setData();
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
            if (confirmButton.enable == true)
            {
                Command.getInstance().mainCommand.buySeed(data["cId"], numbericStepper.get_num);
                WControl.close(this);
            }// end if
            return;
        }// end function

        private function setData() : void
        {
            var _loc_9:String;
            var _loc_10:Number;
            var _loc_1:* = data;
            if (_loc_1 == null)
            {
                return;
            }// end if
            if (isinit == false)
            {
                return;
            }// end if
            if (_shopSeedForm == null)
            {
                _loc_9 = "";
                if (data["maturingTime"] > 1)
                {
                    _loc_9 = Version.SNS == Version.XIAONEI ? ("ShopSeedForm2X") : ("ShopSeedForm2");
                    _shopSeedForm = MaterialLib.getInstance().getMaterial(_loc_9) as Object;
                }
                else
                {
                    _loc_9 = Version.SNS == Version.XIAONEI ? ("ShopSeedFormX") : ("ShopSeedForm");
                    _shopSeedForm = MaterialLib.getInstance().getMaterial(_loc_9) as Object;
                }// end else if
                _shopSeedForm.x = 190;
                _shopSeedForm.y = 40;
                addChild(_shopSeedForm as DisplayObject);
            }// end if
            var _loc_2:* = GetCropID.cropTime(_loc_1["cId"]);
            var _loc_3:* = _loc_2.split(",");
            if (_loc_1["maturingTime"] > 1)
            {
                _loc_10 = int((_loc_3[4] - _loc_3[2]) / 3600);
                _shopSeedForm.time2.text = _loc_10;
            }// end if
            if (cropPrice != null)
            {
                cropPrice.text = _loc_1["price"];
            }// end if
            if (materialProxy != null)
            {
                materialProxy.setContent("1", _loc_1["cId"]);
            }// end if
            _shopSeedForm.time.text = int(_loc_3[4] / 3600);
            if (data["cId"] == 1002 || data["cId"] == 37)
            {
                (_shopSeedForm.cropName as TextField).defaultTextFormat = new TextFormat("Tahoma", 12, 3381555, true);
            }
            else
            {
                (_shopSeedForm.cropName as TextField).defaultTextFormat = new TextFormat("Tahoma", 16, 3381555, true);
            }// end else if
            _shopSeedForm.cropName.text = _loc_1["cName"];
            _shopSeedForm.type.text = Language.replaceText("maturingTime", {maturingNum:_loc_1["maturingTime"]});
            _shopSeedForm.amount.text = _loc_1["output"];
            _shopSeedForm.price.text = _loc_1["sale"];
            _shopSeedForm.income.text = int(_loc_1["expect"]);
            _shopSeedForm.exp.text = _loc_1["cropExp"];
            _shopSeedForm.level.text = _loc_1["cLevel"];
            var _loc_4:* = MData.getInstance().mainData.host;
            var _loc_5:* = int(_loc_4["exp"]);
            var _loc_6:* = int(_loc_4["money"]);
            var _loc_7:* = int(_loc_1["cLevel"]);
            var _loc_8:* = int(_loc_1["price"]);
            cLevel = Math.sqrt((_loc_5 + 25) / 100) - 0.5;
            userMoney = _loc_6;
            if (cLevel < _loc_7)
            {
                confirmButton.enable = false;
                errorText.text = Language.L["对不起，你的等级不足。"];
            }
            else if (userMoney < _loc_8)
            {
                confirmButton.enable = false;
                errorText.text = Language.L["对不起，你的金币不足。"];
            }
            else
            {
                confirmButton.enable = true;
            }// end else if
            return;
        }// end function

        private function buyMaxNum() : int
        {
            var _loc_1:* = int(MData.getInstance().mainData.host["money"]);
            var _loc_2:* = MData.getInstance().mainData.farmlandData.length;
            if (data["price"] * _loc_2 > _loc_1)
            {
                _loc_2 = _loc_1 / data["price"];
            }// end if
            return _loc_2;
        }// end function

        override public function keyEnter() : void
        {
            confirmButtonClick();
            return;
        }// end function

    }
}
