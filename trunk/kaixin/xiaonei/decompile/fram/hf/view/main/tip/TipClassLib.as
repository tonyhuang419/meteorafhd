﻿package hf.view.main.tip
{

    public class TipClassLib extends Object
    {
        private static var _lib:Object = {MouseTip:MouseTip};

        public function TipClassLib()
        {
            return;
        }// end function

        public static function getClass(param1:String) : Class
        {
            if (_lib == null)
            {
                return null;
            }// end if
            return _lib[param1];
        }// end function

        public static function register(param1:String, param2:Class) : void
        {
            if (_lib == null)
            {
                _lib = new Object();
            }// end if
            _lib[param1] = param2;
            return;
        }// end function

    }
}
