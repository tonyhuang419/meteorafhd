package hf.view.main.window.warehouse
{
    import com.minutes.ui.collections.*;
    import flash.display.*;
    import flash.events.*;
    import flash.text.*;
    import hf.view.*;
    import hf.view.common.*;

    public class FruitItem extends LipiListChild
    {
        private var amountText:TextField;
        private var _material:MaterialProxy;
        public static var CHILD_MOVE:String = "childMove";
        public static var CHILD_OVER:String = "childOver";
        public static var CHILD_OUT:String = "childOut";

        public function FruitItem()
        {
            var _loc_2:Shape;
            this.buttonMode = true;
            this.useHandCursor = true;
            var _loc_1:* = MaterialLib.getInstance().getMaterial("ItemBg") as Sprite;
            _loc_1.name = "ItemBg";
            addChild(_loc_1);
            amountText = new TextField();
            amountText.defaultTextFormat = new TextFormat("fontForte", 16, 39423, true, null, null, null, null, TextFormatAlign.CENTER);
            amountText.embedFonts = true;
            amountText.selectable = false;
            amountText.mouseEnabled = false;
            amountText.width = _loc_1.width;
            amountText.height = 23;
            amountText.x = 0;
            amountText.y = _loc_1.height + 0;
            amountText.text = "20";
            addChild(amountText);
            _loc_2 = new Shape();
            _loc_2.graphics.beginFill(0, 0);
            _loc_2.graphics.drawRect(0, 0, 10, 1);
            _loc_2.graphics.endFill();
            _loc_2.y = 90;
            addChild(_loc_2);
            _material = new MaterialProxy();
            addChild(_material);
            addEventListener(MouseEvent.CLICK, onClick);
            addEventListener(MouseEvent.ROLL_OVER, onOver);
            addEventListener(MouseEvent.ROLL_OUT, onOut);
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
            amountText.text = param1["amount"];
            if (param1["cId"] == 37)
            {
                resetBg();
            }
            else
            {
                notPepsicoSeed();
            }// end else if
            _material.setContent("1", param1["cId"]);
            return;
        }// end function

        private function onClick(param1:MouseEvent) : void
        {
            var _loc_2:* = new ViewEvent(ViewEvent.CHILD_CLICK, true);
            _loc_2.data = data;
            dispatchEvent(_loc_2);
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

        private function onOut(param1:MouseEvent) : void
        {
            var _loc_2:* = new ViewEvent(ViewEvent.CHILD_OUT, true);
            _loc_2.data = data;
            dispatchEvent(_loc_2);
            return;
        }// end function

    }
}
