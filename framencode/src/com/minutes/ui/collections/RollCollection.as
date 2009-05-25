package com.minutes.ui.collections
{
    import flash.display.*;
    import flash.events.*;
    import flash.geom.*;
    import hf.view.main.cursor.*;

    public class RollCollection extends Sprite
    {
        private var downLeft:Number = 0;
        private var _height:Number = 100;
        private var downTop:Number = 0;
        private var childList:Sprite;
        private var _top:Number = 0;
        private var _maxWidth:Number = 10000;
        private var _width:Number = 100;
        private var _maxHeight:Number = 10000;
        private var _left:Number = 0;
        private var downX:Number = 0;
        private var downY:Number = 0;

        public function RollCollection()
        {
            childList = new Sprite();
            super.addChild(childList);
            reRoll();
            addEventListener(MouseEvent.MOUSE_DOWN, onMouseDown);
            addEventListener(MouseEvent.ROLL_OVER, onRollOver);
            addEventListener(MouseEvent.ROLL_OUT, onRollOut);
            return;
        }// end function

        private function onMouseUp(param1:MouseEvent) : void
        {
            removeEventListener(MouseEvent.MOUSE_MOVE, onMouseMove);
            stage.removeEventListener(MouseEvent.MOUSE_UP, onMouseUp);
            return;
        }// end function

        public function get maxWidth() : Number
        {
            return _maxWidth;
        }// end function

        public function set maxWidth(param1:Number) : void
        {
            _maxWidth = param1;
            return;
        }// end function

        override public function get width() : Number
        {
            return _width;
        }// end function

        public function set left(param1:Number) : void
        {
            _left = param1;
            return;
        }// end function

        private function onMouseDown(param1:MouseEvent) : void
        {
            addEventListener(MouseEvent.MOUSE_MOVE, onMouseMove);
            stage.addEventListener(MouseEvent.MOUSE_UP, onMouseUp, false, 0, true);
            downX = param1.stageX;
            downY = param1.stageY;
            downLeft = left;
            downTop = top;
            return;
        }// end function

        override public function addChild(param1:DisplayObject) : DisplayObject
        {
            return childList.addChild(param1);
        }// end function

        public function reRoll() : void
        {
            this.scrollRect = new Rectangle(left, top, width, height);
            return;
        }// end function

        public function get top() : Number
        {
            return _top;
        }// end function

        public function set maxHeight(param1:Number) : void
        {
            _maxHeight = param1;
            return;
        }// end function

        override public function set width(param1:Number) : void
        {
            _width = param1;
            return;
        }// end function

        public function get left() : Number
        {
            return _left;
        }// end function

        override public function set height(param1:Number) : void
        {
            _height = param1;
            return;
        }// end function

        override public function get height() : Number
        {
            return _height;
        }// end function

        public function set top(param1:Number) : void
        {
            _top = param1;
            return;
        }// end function

        public function get maxHeight() : Number
        {
            return _maxHeight;
        }// end function

        public function getBitmapData(param1:int, param2:int, param3:Number = 1) : BitmapData
        {
            var _loc_4:* = new BitmapData(param1 * param3, param2 * param3, false);
            new BitmapData(param1 * param3, param2 * param3, false).draw(childList, new Matrix(param3, 0, 0, param3));
            return _loc_4;
        }// end function

        private function onRollOver(param1:MouseEvent) : void
        {
            Cursor.useSystem(false);
            return;
        }// end function

        private function onRollOut(param1:MouseEvent) : void
        {
            Cursor.useSystem(true);
            return;
        }// end function

        private function getRealitySize() : Array
        {
            var _loc_4:Number;
            var _loc_5:Number;
            var _loc_1:Number;
            var _loc_2:Number;
            var _loc_3:int;
            while (_loc_3 < numChildren)
            {
                // label
                _loc_4 = getChildAt(_loc_3).width + getChildAt(_loc_3).x;
                if (_loc_4 > _loc_1)
                {
                    _loc_1 = _loc_4;
                }// end if
                _loc_5 = getChildAt(_loc_3).height + getChildAt(_loc_3).y;
                if (_loc_5 > _loc_2)
                {
                    _loc_2 = _loc_5;
                }// end if
                _loc_3++;
            }// end while
            return [_loc_1, _loc_2];
        }// end function

        private function onMouseMove(param1:MouseEvent) : void
        {
            var _loc_2:Number;
            var _loc_3:Number;
            var _loc_4:Array;
            var _loc_5:Number;
            var _loc_6:Number;
            if (param1.buttonDown == true)
            {
                _loc_2 = param1.stageX - downX;
                _loc_3 = param1.stageY - downY;
                _loc_4 = getRealitySize();
                _loc_5 = _loc_4[0] > _maxWidth ? (_maxWidth) : (_loc_4[0]);
                _loc_6 = _loc_4[1] > _maxHeight ? (_maxHeight) : (_loc_4[1]);
                if (downLeft - _loc_2 >= 0 && downLeft - _loc_2 + this.width <= _loc_5)
                {
                    left = downLeft - _loc_2;
                }// end if
                if (downTop - _loc_3 >= 0 && downTop - _loc_3 + this.height <= _loc_6)
                {
                    top = downTop - _loc_3;
                }// end if
                reRoll();
            }
            else
            {
                removeEventListener(MouseEvent.MOUSE_MOVE, onMouseMove);
                stage.removeEventListener(MouseEvent.MOUSE_UP, onMouseUp);
            }// end else if
            return;
        }// end function

    }
}
