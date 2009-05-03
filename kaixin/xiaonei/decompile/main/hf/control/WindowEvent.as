package hf.control
{
    import flash.events.*;
    import hf.view.common.*;

    public class WindowEvent extends Event
    {
        private var _windowArgument:Object;
        private var _window:BaseWindow;
        private var _startX:int = 0;
        private var _windowName:String = "";
        private var _startY:int = 0;
        public static const CLOSE:String = "windowClose";
        public static const OPEN:String = "windowOpen";

        public function WindowEvent(param1:String, param2:Boolean = false, param3:Boolean = false)
        {
            super(param1, param2, param3);
            return;
        }// end function

        public function set window(param1:BaseWindow) : void
        {
            _window = param1;
            return;
        }// end function

        public function set windowArgument(param1:Object) : void
        {
            _windowArgument = param1;
            return;
        }// end function

        public function get windowName() : String
        {
            return _windowName;
        }// end function

        public function set windowName(param1:String) : void
        {
            _windowName = param1;
            return;
        }// end function

        public function get windowArgument() : Object
        {
            return _windowArgument;
        }// end function

        public function set startY(param1:int) : void
        {
            _startY = param1;
            return;
        }// end function

        public function get window() : BaseWindow
        {
            return _window;
        }// end function

        public function get startX() : int
        {
            return _startX;
        }// end function

        public function get startY() : int
        {
            return _startY;
        }// end function

        public function set startX(param1:int) : void
        {
            _startX = param1;
            return;
        }// end function

    }
}
