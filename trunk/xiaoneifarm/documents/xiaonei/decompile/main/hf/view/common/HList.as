package hf.view.common
{
    import com.minutes.ui.collections.*;
    import flash.display.*;
    import flash.geom.*;

    public class HList extends SimpleCollection
    {
        private var _childSprite:Sprite;
        private var _align:String = "left";
        private var _width:Number = 100;
        private var _dataList:Array;
        private var _gap:int;
        private var itemPosition:int = 0;
        private var _height:Number = 100;
        private var offsetX:Number = 0;
        private var _itemClass:Class;
        private var offsetY:Number = 0;

        public function HList(param1:Class, param2:int = 0)
        {
            _dataList = [];
            _itemClass = param1;
            _gap = param2;
            _childSprite = new Sprite();
            addChild(_childSprite);
            return;
        }// end function

        public function leftMove2(param1:int = 1) : void
        {
            var _loc_3:Number;
            var _loc_2:* = _childSprite.getChildAt(itemPosition).x;
            if (itemPosition - param1 >= 0)
            {
                itemPosition = itemPosition - param1;
                _loc_3 = _childSprite.getChildAt(itemPosition).x;
                offsetX = offsetX + (_loc_3 - _loc_2);
                scrollRect = new Rectangle(_loc_3, 0, width, height);
            }// end if
            return;
        }// end function

        public function get align() : String
        {
            return _align;
        }// end function

        public function set align(param1:String) : void
        {
            _align = param1;
            setChildPosition();
            return;
        }// end function

        public function rightMove(param1:int) : Boolean
        {
            if (_childSprite.width < width)
            {
                return false;
            }// end if
            var _loc_2:Boolean;
            if (offsetX + width + param1 > _childSprite.width)
            {
                offsetX = _childSprite.width - width;
                _loc_2 = false;
            }
            else
            {
                offsetX = offsetX + param1;
            }// end else if
            scrollRect = new Rectangle(offsetX, offsetY, width, height);
            return _loc_2;
        }// end function

        public function set dataList(param1:Array) : void
        {
            itemPosition = 0;
            offsetX = 0;
            scrollRect = new Rectangle(offsetX, offsetY, width, height);
            _dataList = param1;
            setChild();
            return;
        }// end function

        public function get rightEnabled() : Boolean
        {
            if (offsetX >= _childSprite.width - width - 5)
            {
                return false;
            }// end if
            return true;
        }// end function

        override public function get width() : Number
        {
            return _width;
        }// end function

        private function setChild() : void
        {
            var _loc_2:DisplayObject;
            var _loc_1:int;
            while (_loc_1 < _dataList.length)
            {
                // label
                if (_loc_1 >= _childSprite.numChildren)
                {
                    _loc_2 = new _itemClass();
                    _childSprite.addChild(_loc_2);
                }// end if
                (_childSprite.getChildAt(_loc_1) as LipiListChild).data = _dataList[_loc_1];
                _loc_1++;
            }// end while
            while (_childSprite.numChildren > _dataList.length)
            {
                // label
                _childSprite.removeChildAt(_childSprite.numChildren--);
            }// end while
            setChildPosition();
            return;
        }// end function

        public function rightMove2(param1:int = 1) : void
        {
            var _loc_3:Number;
            var _loc_2:* = _childSprite.getChildAt(itemPosition).x;
            if (itemPosition + param1 < _childSprite.numChildren - 4)
            {
                itemPosition = itemPosition + param1;
                _loc_3 = _childSprite.getChildAt(itemPosition).x;
                offsetX = offsetX + (_loc_3 - _loc_2);
                scrollRect = new Rectangle(_loc_3, 0, width, height);
            }// end if
            return;
        }// end function

        private function setChildPosition() : void
        {
            var _loc_1:int;
            var _loc_2:int;
            if (align == "left")
            {
                _loc_1 = 0;
            }
            else if (align == "center")
            {
                if (_childSprite.numChildren == 0)
                {
                    _loc_1 = 0;
                }
                else
                {
                    _loc_1 = (width - (_childSprite.numChildren * (_childSprite.getChildAt(_loc_2).width + _gap))--) / 2;
                }// end else if
            }
            else
            {
                _loc_1 = width - (_childSprite.numChildren * (_childSprite.getChildAt(_loc_2).width + _gap))--;
            }// end else if
            _loc_2 = 0;
            while (_loc_2 < _childSprite.numChildren)
            {
                // label
                _childSprite.getChildAt(_loc_2).x = _loc_1;
                _loc_1 = _loc_1 + _childSprite.getChildAt(_loc_2).width + _gap;
                _loc_2++;
            }// end while
            return;
        }// end function

        override public function set width(param1:Number) : void
        {
            _width = param1;
            offsetX = 0;
            offsetY = 0;
            scrollRect = new Rectangle(offsetX, offsetY, width, height);
            return;
        }// end function

        override public function get height() : Number
        {
            return _height;
        }// end function

        public function get dataList() : Array
        {
            return _dataList;
        }// end function

        override public function set height(param1:Number) : void
        {
            _height = param1;
            offsetX = 0;
            offsetY = 0;
            scrollRect = new Rectangle(offsetX, offsetY, width, height);
            return;
        }// end function

        public function get leftEnabled() : Boolean
        {
            if (offsetX <= 0)
            {
                return false;
            }// end if
            return true;
        }// end function

        public function leftMove(param1:int) : Boolean
        {
            var _loc_2:Boolean;
            if (offsetX - param1 < 0)
            {
                offsetX = 0;
                _loc_2 = false;
            }
            else
            {
                offsetX = offsetX - param1;
            }// end else if
            scrollRect = new Rectangle(offsetX, offsetY, width, height);
            return _loc_2;
        }// end function

    }
}
