package com.minutes.ui.core
{

    public class Reservoir extends Object
    {
        private var useArray:Array;
        private var ClassObject:Class;
        private var unusedArray:Array;

        public function Reservoir(param1:Class)
        {
            this.ClassObject = param1;
            useArray = new Array();
            unusedArray = new Array();
            return;
        }// end function

        public function delInstance(param1:Object) : void
        {
            var _loc_2:int;
            while (_loc_2 < useArray.length)
            {
                // label
                if (useArray[_loc_2] == param1)
                {
                    unusedArray.push(useArray.splice(_loc_2, 1)[0]);
                    break;
                }// end if
                _loc_2++;
            }// end while
            return;
        }// end function

        public function getInstance() : Object
        {
            var _loc_1:Object;
            if (unusedArray.length > 0)
            {
                _loc_1 = unusedArray.pop();
            }
            else
            {
                _loc_1 = new ClassObject();
            }// end else if
            useArray.push(_loc_1);
            return _loc_1;
        }// end function

    }
}
