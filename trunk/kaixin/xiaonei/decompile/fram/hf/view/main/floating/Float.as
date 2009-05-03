package hf.view.main.floating
{
    import hf.control.*;

    public class Float extends Object
    {

        public function Float()
        {
            return;
        }// end function

        public static function close(param1:FloatingBase) : void
        {
            var _loc_2:* = new FloatingEvent(FloatingEvent.FLOATING_CLOSE);
            _loc_2.floating = param1;
            ViewControl.getInstance().dispatchEvent(_loc_2);
            return;
        }// end function

        public static function open(param1:FloatingBase) : void
        {
            var _loc_2:* = new FloatingEvent(FloatingEvent.FLOATING_OPEN);
            _loc_2.floating = param1;
            ViewControl.getInstance().dispatchEvent(_loc_2);
            return;
        }// end function

    }
}
