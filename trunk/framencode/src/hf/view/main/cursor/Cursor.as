package hf.view.main.cursor
{
    import hf.control.*;

    public class Cursor extends Object
    {
        public static var cursorArgument:String = "";
        public static var name:String = "";
        public static var preName:String = "";

        public function Cursor()
        {
            return;
        }// end function

        public static function useSystem(param1:Boolean = true) : void
        {
            var _loc_2:* = new CursorEvent(CursorEvent.USE_TYPE);
            if (param1)
            {
                _loc_2.cursorName = "system";
            }
            else
            {
                _loc_2.cursorName = "customize";
            }// end else if
            ViewControl.getInstance().dispatchEvent(_loc_2);
            return;
        }// end function

        public static function setCursor(param1:String, param2:String = "") : void
        {
            if (name != "")
            {
            }// end if
            name = param1;
            cursorArgument = param2;
            var _loc_3:* = new CursorEvent(CursorEvent.CURSOR);
            _loc_3.cursorName = param1;
            _loc_3.cursorArgument = cursorArgument;
            ViewControl.getInstance().dispatchEvent(_loc_3);
            return;
        }// end function

    }
}
