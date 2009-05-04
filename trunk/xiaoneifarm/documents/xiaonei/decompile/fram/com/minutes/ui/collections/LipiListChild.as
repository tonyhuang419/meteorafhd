package com.minutes.ui.collections
{
    import flash.display.*;

    public class LipiListChild extends Sprite implements ILipiTileChild
    {
        private var _fixedX:Number;
        private var _data:Object;
        private var _fixedY:Number;

        public function LipiListChild()
        {
            return;
        }// end function

        public function set fixedY(param1:Number) : void
        {
            _fixedY = param1;
            return;
        }// end function

        public function get fixedY() : Number
        {
            return _fixedY;
        }// end function

        public function set fixedX(param1:Number) : void
        {
            _fixedX = param1;
            return;
        }// end function

        public function get fixedX() : Number
        {
            return _fixedX;
        }// end function

        public function get data() : Object
        {
            return _data;
        }// end function

        public function set data(param1:Object) : void
        {
            _data = param1;
            return;
        }// end function

    }
}
