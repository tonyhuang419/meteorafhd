package com.minutes.ui.collections
{
    import com.minutes.ui.core.*;

    public class LipiList extends LipiContainer
    {
        private var _dataList:Array;
        private var aReservoir:Reservoir;
        private var _childItem:Class;
        public static var CHILD_LINK:String = "childLink";

        public function LipiList(param1:Class)
        {
            this.childItem = param1;
            aReservoir = new Reservoir(param1);
            return;
        }// end function

        private function setChildPosition() : void
        {
            var _loc_1:Number;
            var _loc_2:int;
            while (_loc_2 < numChildren)
            {
                // label
                if (getChildAt(_loc_2).visible)
                {
                    getChildAt(_loc_2).y = _loc_1;
                    _loc_1 = _loc_1 + getChildAt(_loc_2).height;
                }// end if
                _loc_2++;
            }// end while
            return;
        }// end function

        public function get dataList() : Array
        {
            return _dataList;
        }// end function

        public function get childItem() : Class
        {
            return _childItem;
        }// end function

        public function set dataList(param1:Array) : void
        {
            _dataList = param1;
            setChild();
            vScrollPosition = 0;
            return;
        }// end function

        public function filter(param1:String, param2:RegExp) : void
        {
            var _loc_4:String;
            var _loc_3:int;
            while (_loc_3 < numChildren)
            {
                // label
                _loc_4 = (getChildAt(_loc_3) as LipiListChild).data[param1];
                if (param2.test(_loc_4))
                {
                    (getChildAt(_loc_3) as LipiListChild).visible = true;
                }
                else
                {
                    (getChildAt(_loc_3) as LipiListChild).visible = false;
                }// end else if
                _loc_3++;
            }// end while
            setChildPosition();
            this.vScrollPosition = 0;
            return;
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

        public function set childItem(param1:Class) : void
        {
            _childItem = param1;
            return;
        }// end function

    }
}
