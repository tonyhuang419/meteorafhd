package com.minutes.ui.core
{
    import flash.events.*;

    public class UIEvent extends Event
    {
        private var _data:Object;
        public static const TEXT_CHANGE:String = "textChange";
        public static const RESIZE:String = "resize";
        public static const CLOSE:String = "close";
        public static const SCROLL_CONTENT_CHANGE:String = "scrollContentChange";

        public function UIEvent(param1:String, param2:Boolean = false, param3:Boolean = false)
        {
            super(param1, param2, param3);
            return;
        }// end function

        public function set data(param1:Object) : void
        {
            _data = param1;
            return;
        }// end function

        public function get data() : Object
        {
            return _data;
        }// end function

    }
}
