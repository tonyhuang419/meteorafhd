package hf.view.farm.pepsico
{
    import flash.text.*;
    import hf.view.common.*;

    public class OtherMachine extends AddButtonWindow
    {

        public function OtherMachine()
        {
            width = 380;
            height = 235;
            title = "乐事薯片工厂";
            mode = true;
            return;
        }// end function

        override public function init() : void
        {
            var _loc_1:* = new MaterialProxyBig();
            _loc_1.setContent("1000", 1000);
            _loc_1.x = 20;
            _loc_1.y = 40;
            addChild(_loc_1);
            var _loc_2:* = new TextField();
            _loc_2.text = "乐事薯片工厂";
            _loc_2.setTextFormat(new TextFormat("Tahoma", 16, 3381555, true, null, null, null, null, "center"));
            _loc_2.width = 200;
            _loc_2.x = 150;
            _loc_2.y = 35;
            _loc_2.selectable = false;
            addChild(_loc_2);
            var _loc_3:* = new TextField();
            _loc_3.selectable = false;
            _loc_3.multiline = true;
            _loc_3.wordWrap = true;
            _loc_3.x = 150;
            _loc_3.y = 65;
            _loc_3.width = 200;
            _loc_3.height = 150;
            _loc_3.defaultTextFormat = new TextFormat("Tahoma", 12, 3355443, null, null, null, null, null, "left", null, null, null, 8);
            _loc_3.text = "购买乐事薯片工厂，用100%天然土豆加工薯片，创造更高收入。6个100%天然土豆=1包乐事薯片=310金币，生产50袋乐事薯片可获得乐享天然100%背景奖励，赶快去买吧。";
            addChild(_loc_3);
            addButton("确定", "ButtonBlue", 65, 25, closeWindow);
            super.init();
            return;
        }// end function

    }
}
