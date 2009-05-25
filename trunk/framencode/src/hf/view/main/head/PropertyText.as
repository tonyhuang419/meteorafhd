package hf.view.main.head
{
    import flash.display.*;
    import flash.text.*;
    import hf.control.*;
    import hf.view.common.*;

    public class PropertyText extends Sprite
    {
        private var _goldText:TextField;

        public function PropertyText(param1:String = "gold")
        {
            var _loc_2:* = new MoneyIcon(param1);
            _loc_2.y = 4;
            addChild(_loc_2);
            _goldText = new TextField();
            _goldText.selectable = false;
            _goldText.width = 80;
            _goldText.height = 25;
            _goldText.defaultTextFormat = new TextFormat("fontForte", 16, 16737792, null, null, null, null, null, TextFormatAlign.LEFT);
            _goldText.embedFonts = true;
            _goldText.x = 40;
            _goldText.y = 0;
            _goldText.text = "0";
            addChild(_goldText);
            if (Version.SNS == Version.QQ)
            {
                _goldText.x = 40;
                _goldText.y = 2;
            }
            else
            {
                _goldText.x = 25;
                _goldText.y = 4;
            }// end else if
            return;
        }// end function

        public function get goldValue() : Number
        {
            return Number(_goldText.text);
        }// end function

        public function set goldValue(param1:Number) : void
        {
            if (param1 < 0)
            {
                param1 = 0;
            }// end if
            _goldText.text = param1.toString();
            return;
        }// end function

    }
}
