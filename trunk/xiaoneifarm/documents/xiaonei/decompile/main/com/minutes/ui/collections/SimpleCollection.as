package com.minutes.ui.collections
{
    import flash.display.*;

    public class SimpleCollection extends Sprite
    {
        private var _bg:DisplayObject;
        private var _width:Number = 100;
        private var _height:Number = 100;

        public function SimpleCollection()
        {
            return;
        }// end function

        public function get bg() : DisplayObject
        {
            return _bg;
        }// end function

        override public function get width() : Number
        {
            return _width;
        }// end function

        public function set bg(param1:DisplayObject) : void
        {
            if (_bg == null)
            {
                _bg = param1;
                _bg.width = width;
                _bg.height = height;
                addChild(_bg);
            }
            else
            {
                removeChild(_bg);
                _bg = param1;
                _bg.width = width;
                _bg.height = height;
                addChild(_bg);
            }// end else if
            return;
        }// end function

        override public function set height(param1:Number) : void
        {
            _height = param1;
            if (_bg != null)
            {
                _bg.width = width;
                _bg.height = height;
            }// end if
            return;
        }// end function

        override public function get height() : Number
        {
            return _height;
        }// end function

        override public function set width(param1:Number) : void
        {
            _width = param1;
            if (_bg != null)
            {
                _bg.width = width;
                _bg.height = height;
            }// end if
            return;
        }// end function

    }
}
