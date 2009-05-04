package com.facebook.utils
{
    import flash.utils.*;

    public class FacebookArrayCollection extends Object
    {
        protected var _source:Array;
        protected var hash:Dictionary;
        protected var _type:Class;

        public function FacebookArrayCollection(param1:Array = null, param2:Class = null)
        {
            reset();
            _type = param2;
            initilizeSource(param1);
            return;
        }// end function

        protected function verifyIndex(param1:uint) : void
        {
            if (_source.length < param1)
            {
                throw new RangeError("Index: " + param1 + ", is out of range.");
            }// end if
            return;
        }// end function

        public function addItem(param1:Object) : void
        {
            addItemAt(param1, length);
            return;
        }// end function

        public function get length() : int
        {
            return _source.length;
        }// end function

        public function removeItemAt(param1:uint) : void
        {
            var _loc_2:Object;
            verifyIndex(param1);
            _loc_2 = _source[param1];
            delete hash[_loc_2];
            _source.splice(param1, 1);
            return;
        }// end function

        public function getItemAt(param1:uint) : Object
        {
            verifyIndex(param1);
            return _source[param1];
        }// end function

        public function toString() : String
        {
            return _source.join(", ");
        }// end function

        public function indexOf(param1:Object) : int
        {
            return _source.indexOf(param1);
        }// end function

        public function reset() : void
        {
            hash = new Dictionary(true);
            _source = [];
            return;
        }// end function

        protected function initilizeSource(param1:Array) : void
        {
            var _loc_2:uint;
            var _loc_3:uint;
            _source = [];
            if (param1 == null)
            {
                return;
            }// end if
            _loc_2 = param1.length;
            _loc_3 = 0;
            while (_loc_3++ < _loc_2)
            {
                // label
                addItem(param1[_loc_3]);
            }// end while
            return;
        }// end function

        public function addItemAt(param1:Object, param2:uint) : void
        {
            if (hash[param1] != null)
            {
                throw new Error("Item already exists.");
            }// end if
            if (_type !== null && !(param1 is _type))
            {
                throw new TypeError("This collection requires " + _type + " as the type.");
            }// end if
            hash[param1] = true;
            _source.splice(param2, 0, param1);
            return;
        }// end function

        public function findItemByProperty(param1:String, param2:Object, param3:Boolean = false) : Object
        {
            var _loc_4:Object;
            for (_loc_4 in hash)
            {
                // label
                if (param3 && param1 in _loc_4 && _loc_4[param1] === param2)
                {
                    return _loc_4;
                }// end if
                if (!param3 && param1 in _loc_4 && _loc_4[param1] == param2)
                {
                    return _loc_4;
                }// end if
            }// end of for ... in
            return null;
        }// end function

        public function get type() : Class
        {
            return _type;
        }// end function

        public function get source() : Array
        {
            return _source;
        }// end function

        public function toArray() : Array
        {
            var _loc_1:Array;
            var _loc_2:uint;
            var _loc_3:uint;
            _loc_1 = [];
            _loc_2 = length;
            _loc_3 = 0;
            while (_loc_3++ < _loc_2)
            {
                // label
                _loc_1.push(getItemAt(_loc_3));
            }// end while
            return _loc_1;
        }// end function

        public function contains(param1:Object) : Boolean
        {
            return hash[param1] === true;
        }// end function

    }
}
