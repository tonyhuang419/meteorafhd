package hf.view.main.window.profile
{
    import flash.display.*;
    import flash.text.*;
    import hf.view.*;
    import hf.view.common.*;
    import hf.view.main.tip.*;

    public class HistoryTip extends Tip
    {
        private var tipBg:Sprite;
        private var text1:TextField;
        private var text2:TextField;
        private var cName:TextField;

        public function HistoryTip()
        {
            var _loc_1:TextField;
            tipBg = MaterialLib.getInstance().getMaterial("TipBg") as Sprite;
            tipBg.width = 140;
            tipBg.height = 70;
            tipBg.x = -20;
            tipBg.y = 15;
            addChild(tipBg);
            cName = new TextField();
            cName.x = -5;
            cName.y = 20;
            cName.width = 90;
            cName.height = 22;
            cName.defaultTextFormat = new TextFormat("Tahoma", 14, 3355443, true);
            cName.text = "";
            addChild(cName);
            _loc_1 = new TextField();
            _loc_1.autoSize = TextFieldAutoSize.LEFT;
            _loc_1.x = -10;
            _loc_1.y = 45;
            _loc_1.height = 22;
            _loc_1.defaultTextFormat = new TextFormat("Tahoma", 12, 3355443);
            _loc_1.text = Language.L["累计收获:"];
            addChild(_loc_1);
            var _loc_2:* = new TextField();
            _loc_2.autoSize = TextFieldAutoSize.LEFT;
            _loc_2.x = -10;
            _loc_2.y = 60;
            _loc_2.width = 90;
            _loc_2.height = 22;
            _loc_2.defaultTextFormat = new TextFormat("Tahoma", 12, 3355443);
            _loc_2.text = Language.L["累计偷窃:"];
            addChild(_loc_2);
            text1 = new TextField();
            text1.x = _loc_1.x + _loc_1.width + 3;
            text1.y = 45;
            text1.width = 90;
            text1.height = 22;
            text1.defaultTextFormat = new TextFormat("Tahoma", 12, 3355443);
            text1.text = "111";
            addChild(text1);
            text2 = new TextField();
            text2.x = _loc_2.x + _loc_2.width + 3;
            text2.y = 60;
            text2.width = 90;
            text2.height = 22;
            text2.defaultTextFormat = new TextFormat("Tahoma", 12, 3355443);
            text2.text = "111";
            addChild(text2);
            return;
        }// end function

        override public function set data(param1:Object) : void
        {
            if (param1 == null)
            {
                param1 = "";
            }// end if
            cName.text = param1["cName"];
            text1.text = param1["harvestNumber"];
            text2.text = param1["scroungeNumber"];
            return;
        }// end function

        override public function set mX(param1:Number) : void
        {
            super.mX = param1;
            x = param1 - 5;
            return;
        }// end function

        override public function set mY(param1:Number) : void
        {
            super.mY = param1;
            y = param1 + 15;
            return;
        }// end function

    }
}
