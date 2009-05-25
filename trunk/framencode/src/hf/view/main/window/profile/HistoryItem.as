package hf.view.main.window.profile
{
    import com.minutes.ui.collections.*;
    import flash.display.*;
    import flash.events.*;
    import flash.text.*;
    import hf.view.*;
    import hf.view.common.*;

    public class HistoryItem extends LipiListChild
    {
        private var _material:MaterialProxy;
        private var _data:Object;
        private var text:TextField;
        public static var CHILD_MOVE:String = "childMove";
        public static var CHILD_OVER:String = "childOver";
        public static var CHILD_OUT:String = "childOut";

        public function HistoryItem()
        {
            this.buttonMode = true;
            this.useHandCursor = true;
            init();
            return;
        }// end function

        override public function get data() : Object
        {
            return _data;
        }// end function

        private function onOut(param1:MouseEvent) : void
        {
            var _loc_2:* = new ViewEvent(ViewEvent.CHILD_OUT, true);
            _loc_2.data = data;
            dispatchEvent(_loc_2);
            return;
        }// end function

        private function init() : void
        {
            var _loc_1:* = MaterialLib.getInstance().getMaterial("ItemBg") as Sprite;
            addChild(_loc_1);
            _material = new MaterialProxy();
            addChild(_material);
            text = new TextField();
            text.selectable = false;
            text.width = 60;
            text.height = 22;
            text.defaultTextFormat = new TextFormat("fontForte", 18, 16737792, null, null, null, null, null, TextFormatAlign.CENTER);
            text.embedFonts = true;
            text.x = 0;
            text.y = 60;
            text.text = "0";
            addChild(text);
            addEventListener(MouseEvent.ROLL_OVER, onOver);
            addEventListener(MouseEvent.ROLL_OUT, onOut);
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
            _data = param1;
            _material.setContent("1", param1["cId"]);
            text.text = String(int(param1["harvestNumber"]) + int(param1["scroungeNumber"]));
            return;
        }// end function

    }
}
