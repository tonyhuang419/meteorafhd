package hf.security
{
    import flash.events.*;

    public class RequestKeyEvent extends Event
    {
        private var _decodeKey:int = 0;
        private var _encodeKey:String = "";
        public static const COMP:String = "requestKeyComp";

        public function RequestKeyEvent(param1:String, param2:Boolean = false, param3:Boolean = false)
        {
            super(param1, param2, param3);
            return;
        }// end function

        public function get encodeKey() : String
        {
            return _encodeKey;
        }// end function

        public function get decodeKey() : int
        {
            return _decodeKey;
        }// end function

        public function set decodeKey(param1:int) : void
        {
            _decodeKey = param1;
            return;
        }// end function

        public function set encodeKey(param1:String) : void
        {
            _encodeKey = param1;
            return;
        }// end function

    }
}
