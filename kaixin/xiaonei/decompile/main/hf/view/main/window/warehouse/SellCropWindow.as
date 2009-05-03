package hf.view.main.window.warehouse
{
    import com.minutes.ui.control.*;
    import com.minutes.ui.core.*;
    import flash.display.*;
    import flash.events.*;
    import flash.text.*;
    import hf.control.*;
    import hf.view.*;
    import hf.view.common.*;

    public class SellCropWindow extends BaseWindow
    {
        private var _form:Object;
        private var materialProxy:MaterialProxyBig;
        private var directionText:TextField;
        private var numbericStepper:NumbericStepper;

        public function SellCropWindow()
        {
            width = 380;
            height = 280;
            title = Language.L["sellCropWindow"];
            windowName = "SellCropWindow";
            mode = true;
            return;
        }// end function

        private function cancelButtonClick(param1:MouseEvent) : void
        {
            var _loc_2:* = new WindowEvent(WindowEvent.CLOSE);
            _loc_2.window = this;
            ViewControl.getInstance().dispatchEvent(_loc_2);
            return;
        }// end function

        override public function init() : void
        {
            var _loc_2:LipiButton;
            var _loc_1:* = Version.SNS == Version.XIAONEI ? ("WarehouseFormX") : ("WarehouseForm");
            _form = MaterialLib.getInstance().getMaterial(_loc_1) as Object;
            _form.x = 190;
            _form.y = 45;
            addChild(_form as Sprite);
            materialProxy = new MaterialProxyBig();
            materialProxy.x = 20;
            materialProxy.y = 40;
            addChild(materialProxy);
            numbericStepper = new NumbericStepper();
            numbericStepper.addEventListener(UIEvent.TEXT_CHANGE, numChange);
            numbericStepper.x = 40;
            numbericStepper.y = 170;
            addChild(numbericStepper);
            directionText = new TextField();
            directionText.mouseEnabled = false;
            directionText.selectable = false;
            directionText.x = 25;
            directionText.y = 195;
            directionText.autoSize = TextFieldAutoSize.LEFT;
            directionText.defaultTextFormat = new TextFormat("Tahoma", 12, 3355443);
            directionText.text = Language.replaceText("sellNum", {minNum:1, maxNum:99});
            addChild(directionText);
            setData();
            _loc_2 = new LipiButton();
            _loc_2.bgAlpha = 0;
            _loc_2.bgSkin = new LipiSkin(MaterialLib.getInstance().getClass("ButtonOrange"));
            _loc_2.width = 64;
            _loc_2.height = 25;
            _loc_2.x = width / 2 - _loc_2.width - 10;
            _loc_2.y = height - 50;
            _loc_2.label = Language.L["确定"];
            _loc_2.textColor = 16777215;
            _loc_2.addEventListener(MouseEvent.CLICK, confirmButtonClick);
            addChild(_loc_2);
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
            _form.income.text = data["price"] * numbericStepper.get_num;
            return;
        }// end function

        override public function keyEnter() : void
        {
            confirmButtonClick();
            return;
        }// end function

        private function setData() : void
        {
            if (data == null)
            {
                return;
            }// end if
            if (materialProxy != null)
            {
                materialProxy.setContent("1", data["cId"]);
            }// end if
            if (_form != null)
            {
                numbericStepper.max_num = data["amount"];
                numbericStepper.get_num = data["amount"];
                if (data["cId"] == 1002 || data["cId"] == 37)
                {
                    (_form.cropName as TextField).defaultTextFormat = new TextFormat("Tahoma", 12, 3381555, true);
                }
                else
                {
                    (_form.cropName as TextField).defaultTextFormat = new TextFormat("Tahoma", 26, 3381555, true);
                }// end else if
                _form.cropName.text = data["cName"];
                _form.price.text = data["price"];
                _form.income.text = data["price"] * numbericStepper.get_num;
                directionText.text = Language.replaceText("sellNum", {minNum:1, maxNum:data["amount"]});
            }// end if
            return;
        }// end function

        private function confirmButtonClick(param1:MouseEvent = null) : void
        {
            Command.getInstance().mainCommand.sale(data["cId"], numbericStepper.get_num);
            var _loc_2:* = new WindowEvent(WindowEvent.CLOSE);
            _loc_2.window = this;
            ViewControl.getInstance().dispatchEvent(_loc_2);
            return;
        }// end function

    }
}
