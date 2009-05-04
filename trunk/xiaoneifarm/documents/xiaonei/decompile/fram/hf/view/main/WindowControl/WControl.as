package hf.view.main.WindowControl
{
    import hf.control.*;
    import hf.view.common.*;

    public class WControl extends Object
    {

        public function WControl()
        {
            return;
        }// end function

        public static function close(param1:BaseWindow) : void
        {
            var _loc_2:* = new WindowEvent(WindowEvent.CLOSE);
            _loc_2.window = param1;
            ViewControl.getInstance().dispatchEvent(_loc_2);
            return;
        }// end function

        public static function open(param1:BaseWindow) : void
        {
            var _loc_2:* = new WindowEvent(WindowEvent.OPEN);
            _loc_2.window = param1;
            ViewControl.getInstance().dispatchEvent(_loc_2);
            return;
        }// end function

        public static function openForName(param1:String, param2:Object = null) : void
        {
            var _loc_3:* = new WindowEvent(WindowEvent.OPEN);
            _loc_3.windowName = param1;
            _loc_3.windowArgument = param2;
            ViewControl.getInstance().dispatchEvent(_loc_3);
            return;
        }// end function

    }
}
