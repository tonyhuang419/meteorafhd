package hf.control
{
    import flash.events.*;

    public class TipEvent extends Event
    {
        private var _tX:Number = 0;
        private var _tY:Number = 0;
        private var _tipType:String = "";
        private var _tipArgument:Object;
        public static const TIP_SHOW:String = "tipShow";
        public static const TIP_HIDE:String = "tipHide";

        public function TipEvent(param1:String, param2:Boolean = false, param3:Boolean = false)
        {
            super(param1, param2, param3);
            return;
        }// end function

        public function get tX() : Number
        {
            return _tX;
        }// end function

        public function get tY() : Number
        {
            return _tY;
        }// end function

        public function set tX(param1:Number) : void
        {
            _tX = param1;
            return;
        }// end function

        public function set tY(param1:Number) : void
        {
            _tY = param1;
            return;
        }// end function

        public function set tipArgument(param1:Object) : void
        {
            _tipArgument = param1;
            return;
        }// end function

        public function set tipType(param1:String) : void
        {
            _tipType = param1;
            return;
        }// end function

        public function get tipType() : String
        {
            return _tipType;
        }// end function

        public function get tipArgument() : Object
        {
            return _tipArgument;
        }// end function

    }
}
