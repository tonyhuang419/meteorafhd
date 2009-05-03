package hf.view.main.tip
{
    import flash.display.*;

    public class Tip extends Sprite implements ITip
    {
        private var _tX:Number = 0;
        private var _tY:Number = 0;
        private var _data:Object;
        private var _mX:Number = 0;
        private var _mY:Number = 0;

        public function Tip()
        {
            return;
        }// end function

        public function set tX(param1:Number) : void
        {
            _tX = param1;
            return;
        }// end function

        public function get mY() : Number
        {
            return _mY;
        }// end function

        public function set tY(param1:Number) : void
        {
            _tY = param1;
            return;
        }// end function

        public function get mX() : Number
        {
            return _mX;
        }// end function

        public function set mX(param1:Number) : void
        {
            _mX = param1;
            return;
        }// end function

        public function set data(param1:Object) : void
        {
            _data = param1;
            return;
        }// end function

        public function set mY(param1:Number) : void
        {
            _mY = param1;
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

        public function get data() : Object
        {
            return _data;
        }// end function

    }
}
