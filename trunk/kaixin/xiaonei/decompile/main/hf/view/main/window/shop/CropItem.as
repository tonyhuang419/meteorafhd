package hf.view.main.window.shop
{
    import com.minutes.ui.collections.*;
    import flash.display.*;
    import flash.events.*;
    import flash.text.*;
    import hf.control.*;
    import hf.view.*;
    import hf.view.common.*;

    public class CropItem extends LipiListChild
    {
        private var priceText:TextField;
        private var _material:MaterialProxy;
        public static var CHILD_MOVE:String = "childMove";
        public static var CHILD_OVER:String = "childOver";
        public static var CHILD_OUT:String = "childOut";

        public function CropItem()
        {
            this.buttonMode = true;
            this.useHandCursor = true;
            var _loc_1:* = MaterialLib.getInstance().getMaterial("ItemBg") as Sprite;
            _loc_1.name = "ItemBg";
            addChild(_loc_1);
            priceText = new TextField();
            priceText.defaultTextFormat = new TextFormat("fontForte", 16, 16737792, true, null, null, null, null, TextFormatAlign.CENTER);
            priceText.embedFonts = true;
            priceText.selectable = false;
            priceText.width = 45;
            priceText.height = 23;
            priceText.text = "20";
            addChild(priceText);
            var _loc_2:* = new MoneyIcon();
            addChild(_loc_2);
            if (Version.SNS == Version.QQ)
            {
                priceText.x = 27;
                priceText.y = _loc_1.height + 3;
                _loc_2.x = -5;
                _loc_2.y = _loc_1.height + 5;
            }
            else
            {
                priceText.x = 20;
                priceText.y = _loc_1.height + 5;
                _loc_2.x = 0;
                _loc_2.y = _loc_1.height + 5;
            }// end else if
            _material = new MaterialProxy();
            addChild(_material);
            addEventListener(MouseEvent.CLICK, onClick);
            addEventListener(MouseEvent.ROLL_OVER, onOver);
            addEventListener(MouseEvent.ROLL_OUT, onOut);
            var _loc_3:* = new Shape();
            _loc_3.graphics.beginFill(16711680, 0);
            _loc_3.graphics.drawRect(0, 0, 10, 1);
            _loc_3.graphics.endFill();
            _loc_3.y = 100;
            addChild(_loc_3);
            return;
        }// end function

        private function notPepsicoSeed() : void
        {
            var _loc_1:DisplayObject;
            if (getChildByName("PepsicoSeedBG") != null)
            {
                _loc_1 = getChildByName("PepsicoSeedBG");
                removeChild(_loc_1);
            }// end if
            return;
        }// end function

        private function onOver(param1:MouseEvent) : void
        {
            var _loc_2:* = new ViewEvent(ViewEvent.CHILD_OVER, true);
            _loc_2.data = data;
            dispatchEvent(_loc_2);
            return;
        }// end function

        override public function set data(param1:Object) : void
        {
            super.data = param1;
            if (param1["cId"] == 37)
            {
                resetBg();
            }
            else
            {
                notPepsicoSeed();
            }// end else if
            priceText.text = param1["price"];
            _material.setContent("1", param1["cId"]);
            return;
        }// end function

        private function resetBg() : void
        {
            var _loc_3:Sprite;
            var _loc_1:* = getChildByName("ItemBg") as Sprite;
            var _loc_2:* = getChildIndex(_loc_1);
            if (getChildByName("PepsicoSeedBG") == null)
            {
                _loc_3 = MaterialLib.getInstance().getMaterial("PepsicoSeedBG") as Sprite;
                _loc_3.name = "PepsicoSeedBG";
                addChildAt(_loc_3, _loc_2 + 1);
            }// end if
            return;
        }// end function

        private function onClick(param1:MouseEvent) : void
        {
            var _loc_2:* = new ViewEvent(ViewEvent.CHILD_CLICK, true);
            _loc_2.data = data;
            dispatchEvent(_loc_2);
            return;
        }// end function

        private function onOut(param1:MouseEvent) : void
        {
            var _loc_2:* = new ViewEvent(ViewEvent.CHILD_OUT, true);
            _loc_2.data = data;
            dispatchEvent(_loc_2);
            return;
        }// end function

    }
}
