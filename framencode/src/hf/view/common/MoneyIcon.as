package hf.view.common
{
    import flash.display.*;
    import hf.control.*;
    import hf.view.*;

    public class MoneyIcon extends Sprite
    {
        private var _type:String = "";
        public static const GOLD:String = "gold";
        public static const X:String = "unknown";
        public static const FB:String = "fb";

        public function MoneyIcon(param1:String = "gold")
        {
            if (param1 == X)
            {
                _type = param1;
                return;
            }// end if
            setIconType(param1);
            return;
        }// end function

        public function set type(param1:String) : void
        {
            if (_type == X)
            {
                setIconType(param1);
            }// end if
            return;
        }// end function

        private function setIconType(param1:String) : void
        {
            var _loc_2:String;
            var _loc_3:String;
            if (Version.SNS == Version.QQ)
            {
                _loc_2 = "GoldIcon";
                _loc_3 = "QB";
            }
            else
            {
                _loc_2 = "GoldIconX";
                _loc_3 = "FBX";
            }// end else if
            if (param1 == GOLD)
            {
                addChild(MaterialLib.getInstance().getMaterial(_loc_2) as Sprite);
            }
            else if (param1 == FB)
            {
                addChild(MaterialLib.getInstance().getMaterial(_loc_3) as Sprite);
            }// end else if
            _type = param1;
            return;
        }// end function

        public function get type() : String
        {
            return _type;
        }// end function

    }
}
