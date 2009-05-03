package hf.view.farm.packBar
{
    import com.minutes.ui.collections.*;
    import flash.display.*;
    import flash.events.*;
    import flash.text.*;
    import hf.view.*;
    import hf.view.common.*;

    public class PackItem extends LipiListChild
    {
        private var amountText:TextField;
        private var childObjcet:MaterialProxy;
        private var lib:MaterialLib;

        public function PackItem()
        {
            lib = MaterialLib.getInstance();
            buttonMode = true;
            useHandCursor = true;
            addChild(lib.getMaterial("ItemBg") as DisplayObject);
            childObjcet = new MaterialProxy();
            addChild(childObjcet);
            addEventListener(MouseEvent.CLICK, childClick);
            addEventListener(MouseEvent.ROLL_OVER, childOver);
            addEventListener(MouseEvent.ROLL_OUT, childOut);
            return;
        }// end function

        private function childClick(param1:MouseEvent) : void
        {
            var _loc_2:* = new ViewEvent(ViewEvent.CHILD_CLICK, true);
            _loc_2.data = data;
            dispatchEvent(_loc_2);
            return;
        }// end function

        private function childOver(param1:MouseEvent) : void
        {
            var _loc_2:* = new ViewEvent(ViewEvent.CHILD_OVER, true);
            _loc_2.data = data;
            dispatchEvent(_loc_2);
            return;
        }// end function

        private function childOut(param1:MouseEvent) : void
        {
            var _loc_2:* = new ViewEvent(ViewEvent.CHILD_OUT, true);
            _loc_2.data = data;
            dispatchEvent(_loc_2);
            return;
        }// end function

        override public function set data(param1:Object) : void
        {
            super.data = param1;
            var _loc_2:int;
            var _loc_3:int;
            if (param1.hasOwnProperty("type"))
            {
                _loc_2 = param1["type"];
            }// end if
            if (param1.hasOwnProperty("cId"))
            {
                _loc_3 = param1["cId"];
            }
            else if (param1.hasOwnProperty("tId"))
            {
                _loc_3 = param1["tId"];
            }// end else if
            childObjcet.setContent(String(_loc_2), _loc_3);
            if (param1.hasOwnProperty("amount"))
            {
                setAmount(param1["amount"]);
            }// end if
            return;
        }// end function

        override public function get data() : Object
        {
            return super.data;
        }// end function

        private function setAmount(param1:int) : void
        {
            if (amountText == null)
            {
                amountText = new TextField();
                amountText.defaultTextFormat = new TextFormat("fontForte", 14, 3355443, null, null, null, null, null, TextFormatAlign.RIGHT);
                amountText.embedFonts = true;
                amountText.width = 30;
                amountText.height = 20;
                amountText.text = param1.toString();
                amountText.selectable = false;
                amountText.mouseEnabled = false;
                addChild(amountText);
            }// end if
            amountText.text = param1.toString();
            amountText.x = width - amountText.width;
            amountText.y = 63 - amountText.height;
            return;
        }// end function

    }
}
