package hf.control
{
    import flash.events.*;
    import hf.view.main.floating.*;

    public class FloatingEvent extends Event
    {
        private var _floating:FloatingBase;
        public static const FLOATING_OPEN:String = "floatingOpen";
        public static const FLOATING_CLOSE:String = "floatingClose";

        public function FloatingEvent(param1:String, param2:Boolean = false, param3:Boolean = false)
        {
            super(param1, param2, param3);
            return;
        }// end function

        public function set floating(param1:FloatingBase) : void
        {
            _floating = param1;
            return;
        }// end function

        public function get floating() : FloatingBase
        {
            return _floating;
        }// end function

    }
}
