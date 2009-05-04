package hf.control
{
    import flash.events.*;

    public class CursorEvent extends Event
    {
        private var _cursorName:String = "";
        private var _cursorArgument:String = "";
        public static const USE_TYPE:String = "useType";
        public static const CURSOR:String = "setCursor";

        public function CursorEvent(param1:String, param2:Boolean = false, param3:Boolean = false)
        {
            super(param1, param2, param3);
            return;
        }// end function

        public function get cursorName() : String
        {
            return _cursorName;
        }// end function

        public function set cursorName(param1:String) : void
        {
            _cursorName = param1;
            return;
        }// end function

        public function set cursorArgument(param1:String) : void
        {
            _cursorArgument = param1;
            return;
        }// end function

        public function get cursorArgument() : String
        {
            return _cursorArgument;
        }// end function

    }
}
