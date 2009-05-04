package hf.view
{
    import flash.system.*;
    import hf.view.common.*;

    public class MaterialLib extends Object
    {
        private var _materialArray:Array;
        private var _materialUrlArr:Array;
        private static var instance:MaterialLib;

        public function MaterialLib()
        {
            _materialUrlArr = [];
            _materialArray = [];
            if (instance != null)
            {
                throw new Error("实例化单例类出错-MaterialLib");
            }// end if
            return;
        }// end function

        public function push(param1:ApplicationDomain, param2:String = "") : void
        {
            if (param2 != "")
            {
                _materialUrlArr.push(param2);
            }// end if
            _materialArray.push(param1);
            return;
        }// end function

        public function getClass(param1:String) : Class
        {
            var _loc_2:Class;
            if (Language.lang == Language.EN)
            {
                _loc_2 = searchClass(param1 + "_" + Language.lang);
                if (_loc_2 == null)
                {
                    return searchClass(param1);
                }// end if
                return _loc_2;
            }
            else
            {
                return searchClass(param1);
            }// end else if
        }// end function

        public function hasUrl(param1:String) : Boolean
        {
            var _loc_2:Boolean;
            var _loc_3:int;
            var _loc_4:* = _materialUrlArr.length;
            while (_loc_3 < _loc_4)
            {
                // label
                if (_materialUrlArr[_loc_3] == param1)
                {
                    return true;
                }// end if
                _loc_3++;
            }// end while
            return false;
        }// end function

        private function searchClass(param1:String) : Class
        {
            var _loc_2:int;
            var _loc_3:* = _materialArray.length;
            while (_loc_2 < _loc_3)
            {
                // label
                if ((_materialArray[_loc_2] as ApplicationDomain).hasDefinition(param1))
                {
                    return (_materialArray[_loc_2] as ApplicationDomain).getDefinition(param1) as Class;
                }// end if
                _loc_2++;
            }// end while
            return null;
        }// end function

        public function getMaterial(param1:String) : Object
        {
            var _loc_2:* = getClass(param1);
            if (_loc_2 != null)
            {
                return new _loc_2;
            }// end if
            return null;
        }// end function

        public static function getInstance() : MaterialLib
        {
            if (instance == null)
            {
                instance = new MaterialLib;
            }// end if
            return instance;
        }// end function

    }
}
