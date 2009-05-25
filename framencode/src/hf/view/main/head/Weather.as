package hf.view.main.head
{
    import flash.display.*;
    import flash.events.*;
    import hf.view.*;
    import hf.view.common.*;
    import hf.view.main.tip.*;

    public class Weather extends Sprite
    {
        private var _weather:String = "";
        private var _weatherDisplayObject:DisplayObject;
        public static const RAIN:String = "rain";
        public static const SUNNY:String = "sunny";
        public static const NIGHT:String = "night";

        public function Weather()
        {
            addEventListener(MouseEvent.ROLL_OVER, onRollOver);
            addEventListener(MouseEvent.ROLL_OUT, onRollOut);
            return;
        }// end function

        private function onRollOver(param1:MouseEvent) : void
        {
            var _loc_2:String;
            if (_weather == SUNNY)
            {
                _loc_2 = Language.L["晴天，有可能长虫、长草或干旱。"];
            }
            else if (_weather == RAIN)
            {
                _loc_2 = Language.L["雨天，土地不会干旱。"];
            }
            else if (_weather == NIGHT)
            {
                _loc_2 = Language.L["夜晚，你可以好好休息哦。"];
            }// end else if
            if (_loc_2 != "")
            {
                TipControl.show("MouseTip", _loc_2);
            }// end if
            return;
        }// end function

        private function onRollOut(param1:MouseEvent) : void
        {
            TipControl.hide();
            return;
        }// end function

        public function get weather() : String
        {
            return _weather;
        }// end function

        public function set weather(param1:String) : void
        {
            if (param1 == _weather)
            {
                return;
            }// end if
            _weather = param1;
            if (_weatherDisplayObject != null)
            {
                removeChild(_weatherDisplayObject);
                _weatherDisplayObject = null;
            }// end if
            if (param1 == SUNNY)
            {
                _weatherDisplayObject = MaterialLib.getInstance().getMaterial("Sunny") as DisplayObject;
                addChild(_weatherDisplayObject);
            }
            else if (param1 == RAIN)
            {
                _weatherDisplayObject = MaterialLib.getInstance().getMaterial("Rain") as DisplayObject;
                addChild(_weatherDisplayObject);
            }
            else if (param1 == NIGHT)
            {
                _weatherDisplayObject = MaterialLib.getInstance().getMaterial("Night") as DisplayObject;
                addChild(_weatherDisplayObject);
            }// end else if
            return;
        }// end function

    }
}
