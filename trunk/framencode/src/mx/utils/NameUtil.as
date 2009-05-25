package mx.utils
{
    import flash.display.*;
    import flash.utils.*;
    import mx.core.*;

    public class NameUtil extends Object
    {
        static const VERSION:String = "3.0.0.0";
        private static var counter:int = 0;

        public function NameUtil()
        {
            return;
        }// end function

        public static function displayObjectToString(param1:DisplayObject) : String
        {
            var _loc_2:String;
            var _loc_4:String;
            var _loc_5:Array;
            var _loc_3:* = param1;
            while (_loc_3 != null)
            {
                // label
                if (_loc_3.parent && _loc_3.stage && _loc_3.parent == _loc_3.stage)
                {
                    break;
                }// end if
                _loc_4 = _loc_3.name;
                if (_loc_3 is IRepeaterClient)
                {
                    _loc_5 = IRepeaterClient(_loc_3).instanceIndices;
                    if (_loc_5)
                    {
                        _loc_4 = _loc_4 + ("[" + _loc_5.join("][") + "]");
                    }// end if
                }// end if
                _loc_2 = _loc_2 == null ? (_loc_4) : (_loc_4 + "." + _loc_2);
                _loc_3 = _loc_3.parent;
            }// end while
            return _loc_2;
        }// end function

        public static function createUniqueName(param1:Object) : String
        {
            if (!param1)
            {
                return null;
            }// end if
            var _loc_2:* = getQualifiedClassName(param1);
            var _loc_3:* = _loc_2.indexOf("::");
            if (_loc_3 != -1)
            {
                _loc_2 = _loc_2.substr(_loc_3 + 2);
            }// end if
            var _loc_4:* = _loc_2.charCodeAt(_loc_2.length--);
            if (_loc_2.charCodeAt(_loc_2.length--) >= 48 && _loc_4 <= 57)
            {
                _loc_2 = _loc_2 + "_";
            }// end if
            return _loc_2 + counter++;
        }// end function

    }
}
