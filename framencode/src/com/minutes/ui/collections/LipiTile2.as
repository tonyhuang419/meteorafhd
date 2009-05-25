package com.minutes.ui.collections
{
    import com.minutes.ui.core.*;

    public class LipiTile2 extends LipiContainer
    {
        private var _dataList:Array;
        private var _itemHeight:Number = 0;
        private var aReservoir:Reservoir;
        private var _leftSpace:Number = 0;
        private var _topSpace:Number = 0;
        private var _itemWidth:Number = 0;
        private var _colAmount:int = 0;
        private var _childItem:Class;
        public static var CHILD_CLICK:String = "childClick";
        public static var CHILD_OVER:String = "childOver";
        public static var CHILD_OUT:String = "childOut";

        public function LipiTile2(param1:Class, param2:int, param3:Number, param4:Number, param5:Number = 0, param6:Number = 0)
        {
            this.childItem = param1;
            this.colAmount = param2;
            this.leftSpace = param5;
            this.topSpace = param6;
            this.itemWidth = param3;
            this.itemHeight = param4;
            aReservoir = new Reservoir(param1);
            return;
        }// end function

        public function set dataList(param1:Array) : void
        {
            _dataList = param1;
            setChild();
            return;
        }// end function

        public function get childItem() : Class
        {
            return _childItem;
        }// end function

        public function get itemHeight() : Number
        {
            return _itemHeight;
        }// end function

        public function set childItem(param1:Class) : void
        {
            _childItem = param1;
            return;
        }// end function

        private function getPosition(param1:int) : Array
        {
            var _loc_2:* = Math.floor(param1 / this.colAmount);
            var _loc_3:* = param1 % this.colAmount;
            var _loc_4:* = leftSpace + _loc_3 * itemWidth;
            var _loc_5:* = topSpace + _loc_2 * itemHeight;
            return new Array(_loc_4, _loc_5);
        }// end function

        private function setChild() : void
        {
            var _loc_3:LipiListChild;
            var _loc_1:int;
            while (_loc_1 < dataList.length)
            {
                // label
                if (numChildren > _loc_1)
                {
                    _loc_3 = getChildAt(_loc_1) as LipiListChild;
                    _loc_3.data = dataList[_loc_1];
                }
                else
                {
                    _loc_3 = aReservoir.getInstance() as LipiListChild;
                    _loc_3.data = dataList[_loc_1];
                    addChild(_loc_3);
                }// end else if
                _loc_1++;
            }// end while
            while (_loc_2-- >= dataList.length)
            {
                // label
                aReservoir.delInstance(getChildAt(numChildren--));
                removeChildAt(_loc_2);
            }// end while
            setChildPosition();
            return;
        }// end function

        public function set topSpace(param1:Number) : void
        {
            _topSpace = param1;
            return;
        }// end function

        private function setChildPosition() : void
        {
            var _loc_1:Number;
            var _loc_2:int;
            while (_loc_2 < numChildren)
            {
                // label
                getChildAt(_loc_2).x = getPosition(_loc_2)[0];
                getChildAt(_loc_2).y = getPosition(_loc_2)[1];
                _loc_2++;
            }// end while
            return;
        }// end function

        public function set itemWidth(param1:Number) : void
        {
            _itemWidth = param1;
            return;
        }// end function

        public function get dataList() : Array
        {
            return _dataList;
        }// end function

        public function set colAmount(param1:int) : void
        {
            _colAmount = param1;
            return;
        }// end function

        public function get topSpace() : Number
        {
            return _topSpace;
        }// end function

        public function get itemWidth() : Number
        {
            return _itemWidth;
        }// end function

        public function get colAmount() : int
        {
            return _colAmount;
        }// end function

        public function set itemHeight(param1:Number) : void
        {
            _itemHeight = param1;
            return;
        }// end function

        public function set leftSpace(param1:Number) : void
        {
            _leftSpace = param1;
            return;
        }// end function

        public function get leftSpace() : Number
        {
            return _leftSpace;
        }// end function

    }
}
