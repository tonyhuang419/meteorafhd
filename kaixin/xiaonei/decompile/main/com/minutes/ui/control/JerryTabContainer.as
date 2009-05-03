package com.minutes.ui.control
{
    import com.minutes.ui.core.*;
    import flash.display.*;

    public class JerryTabContainer extends LipiUIComponent
    {
        private var indexChange:Boolean = false;
        private var _index:int = 0;
        private var _list:Array;

        public function JerryTabContainer()
        {
            _list = [];
            this.bgAlpha = 0;
            return;
        }// end function

        public function getDisplayObject(param1:int) : DisplayObject
        {
            if (param1 >= 0 && _list.length > 0 && param1 < _list.length)
            {
                return _list[param1];
            }// end if
            return null;
        }// end function

        public function set selectedIndex(param1:int) : void
        {
            if (param1 >= 0 && _list.length > 0 && param1 < _list.length)
            {
                _index = param1;
                indexChange = true;
                invalidateDisplayList();
            }// end if
            return;
        }// end function

        public function addItem(param1:DisplayObject) : void
        {
            _list.push(param1);
            param1.visible = false;
            addChild(param1);
            return;
        }// end function

        private function refurbishDisplay() : void
        {
            var _loc_1:int;
            while (_loc_1 < _list.length)
            {
                // label
                _list[_loc_1].visible = false;
                _loc_1++;
            }// end while
            _list[selectedIndex].visible = true;
            return;
        }// end function

        public function get selectedIndex() : int
        {
            return _index;
        }// end function

        override protected function updateDisplayList(param1:Number, param2:Number) : void
        {
            super.updateDisplayList(param1, param2);
            if (indexChange)
            {
                refurbishDisplay();
                indexChange = false;
            }// end if
            return;
        }// end function

    }
}
