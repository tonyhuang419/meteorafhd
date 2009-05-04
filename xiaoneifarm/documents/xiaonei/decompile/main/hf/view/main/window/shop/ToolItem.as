package hf.view.main.window.shop
{
    import com.minutes.ui.collections.*;
    import flash.display.*;
    import flash.events.*;
    import flash.text.*;
    import hf.control.*;
    import hf.view.*;
    import hf.view.common.*;

    public class ToolItem extends LipiListChild
    {
        private var bg:Sprite;
        private var _material:MaterialProxy;
        private var priceText:TextField;
        private var goldIcon:MoneyIcon;
        public static var CHILD_MOVE:String = "childMove";
        public static var CHILD_OVER:String = "childOver";
        public static var CHILD_OUT:String = "childOut";

        public function ToolItem()
        {
            this.buttonMode = true;
            this.useHandCursor = true;
            bg = MaterialLib.getInstance().getMaterial("ItemBg") as Sprite;
            addChild(bg);
            priceText = new TextField();
            priceText.defaultTextFormat = new TextFormat("fontForte", 16, 16737792, true, null, null, null, null, TextFormatAlign.CENTER);
            priceText.embedFonts = true;
            priceText.selectable = false;
            priceText.width = 45;
            priceText.height = 23;
            priceText.text = "20";
            addChild(priceText);
            if (Version.SNS == Version.QQ)
            {
                priceText.x = 27;
                priceText.y = bg.height + 3;
            }
            else
            {
                priceText.x = 20;
                priceText.y = bg.height + 5;
            }// end else if
            _material = new MaterialProxy();
            addChild(_material);
            addEventListener(MouseEvent.CLICK, onClick);
            addEventListener(MouseEvent.ROLL_OVER, onOver);
            addEventListener(MouseEvent.ROLL_OUT, onOut);
            var _loc_1:* = new Shape();
            _loc_1.graphics.beginFill(16711680, 0);
            _loc_1.graphics.drawRect(0, 0, 10, 1);
            _loc_1.graphics.endFill();
            _loc_1.y = 100;
            addChild(_loc_1);
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

        override public function set data(param1:Object) : void
        {
            super.data = param1;
            if (int(param1["FBPrice"]) == 0)
            {
                if (goldIcon == null)
                {
                    goldIcon = new MoneyIcon();
                    addChild(goldIcon);
                }
                else if (goldIcon.type != MoneyIcon.GOLD)
                {
                    removeChild(goldIcon);
                    goldIcon = null;
                    goldIcon = new MoneyIcon();
                    addChild(goldIcon);
                }// end else if
                priceText.defaultTextFormat = new TextFormat("fontForte", 16, 16737792, true, null, null, null, null, TextFormatAlign.CENTER);
                priceText.text = param1["price"];
            }
            else
            {
                if (goldIcon == null)
                {
                    goldIcon = new MoneyIcon(MoneyIcon.FB);
                    addChild(goldIcon);
                }
                else if (goldIcon.type != MoneyIcon.FB)
                {
                    removeChild(goldIcon);
                    goldIcon = null;
                    goldIcon = new MoneyIcon(MoneyIcon.FB);
                    addChild(goldIcon);
                }// end else if
                priceText.defaultTextFormat = new TextFormat("fontForte", 16, 39423, true, null, null, null, null, TextFormatAlign.CENTER);
                priceText.text = param1["FBPrice"];
            }// end else if
            if (Version.SNS == Version.QQ)
            {
                if (goldIcon != null)
                {
                    goldIcon.x = -5;
                    goldIcon.y = bg.height + 5;
                }// end if
            }
            else if (goldIcon != null)
            {
                goldIcon.x = 0;
                goldIcon.y = bg.height + 5;
            }// end else if
            _material.setContent(param1["type"], param1["tId"]);
            return;
        }// end function

        private function onOver(param1:MouseEvent) : void
        {
            var _loc_2:* = new ViewEvent(ViewEvent.CHILD_OVER, true);
            _loc_2.data = data;
            dispatchEvent(_loc_2);
            return;
        }// end function

    }
}
