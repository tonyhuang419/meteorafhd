package com.minutes.ui.vectorCartoon.cartoonFrameRate
{
    import flash.utils.*;

    public class CartoonFrameRate extends Timer
    {
        private var _fps:int;

        public function CartoonFrameRate(param1:int = -1)
        {
            _fps = param1 > -1 ? (param1) : (24);
            super(Math.round(1000 / _fps), 0);
            return;
        }// end function

        public function getFrameRate() : int
        {
            return _fps;
        }// end function

    }
}
