package hf.view.main.tip
{
    import flash.display.*;
    import flash.text.*;
    import hf.view.*;
    import hf.view.common.*;

    public class DiyMouseTip extends Tip
    {
        private var tipBg:Sprite;
        private var tipText:TextField;
        private var tipText2:TextField;

        public function DiyMouseTip()
        {
            tipBg = MaterialLib.getInstance().getMaterial("TipBg") as Sprite;
            addChild(tipBg);
            tipText = new TextField();
            tipText.x = 15;
            tipText.y = 5;
            tipText.defaultTextFormat = new TextFormat("Tahoma", 12, 0);
            addChild(tipText);
            tipText2 = new TextField();
            tipText2.x = 5;
            tipText2.y = 25;
            tipText2.width = 130;
            tipText2.defaultTextFormat = new TextFormat("Tahoma", 12, 0);
            addChild(tipText2);
            return;
        }// end function

        private function doubleString(param1:int) : String
        {
            if (param1 < 10)
            {
                return "0" + param1;
            }// end if
            return param1.toString();
        }// end function

        private function dateFormat(param1:int) : String
        {
            var _loc_2:* = new Date(param1 * 1000);
            return doubleString(_loc_2.getFullYear()) + "-" + doubleString(_loc_2.getMonth() + 1) + "-" + doubleString(_loc_2.getDate()) + " " + doubleString(_loc_2.getHours()) + ":" + doubleString(_loc_2.getMinutes());
        }// end function

        override public function set mX(param1:Number) : void
        {
            super.mX = param1;
            x = param1 - 5;
            return;
        }// end function

        override public function set data(param1:Object) : void
        {
            tipText.text = param1.itemName;
            if (param1.validTime == 0)
            {
                tipText2.text = Language.L["到期时间：无限期"];
            }
            else
            {
                tipText2.text = Language.L["到期时间："] + dateFormat(param1.validTime) + "11";
            }// end else if
            if (tipText.width > tipText2.width)
            {
                tipBg.width = tipText.width + 20;
            }
            else
            {
                tipBg.width = tipText2.width + 20;
            }// end else if
            tipBg.height = 50;
            super.data = param1;
            return;
        }// end function

        override public function set mY(param1:Number) : void
        {
            super.mY = param1;
            y = param1 + 20;
            return;
        }// end function

    }
}
