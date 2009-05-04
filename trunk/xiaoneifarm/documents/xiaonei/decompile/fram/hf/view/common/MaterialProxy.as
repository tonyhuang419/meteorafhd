package hf.view.common
{
    import flash.display.*;
    import flash.filters.*;
    import flash.net.*;
    import hf.view.*;
    import hf.view.farm.land.*;

    public class MaterialProxy extends Sprite
    {
        private var _size:String;
        private var _enable:Boolean = true;
        private var _h:int = 60;
        private var currentId:int = 0;
        private var childObjcet:DisplayObject;
        private var _w:int = 60;
        private var currentType:String = "";
        public static const BIG:String = "big";
        public static const SMALL:String = "small";

        public function MaterialProxy(param1:String = "small")
        {
            _size = param1;
            if (_size == SMALL)
            {
                var _loc_2:int;
                _h = 60;
                _w = _loc_2;
            }
            else
            {
                var _loc_2:int;
                _h = 120;
                _w = _loc_2;
            }// end else if
            return;
        }// end function

        private function setDog(param1:int) : void
        {
            var _loc_2:* = GetCropID.idToDogName(param1.toString());
            if (childObjcet != null)
            {
                removeChild(childObjcet);
                childObjcet = null;
            }// end if
            if (_loc_2 == "")
            {
                return;
            }// end if
            var _loc_3:* = MaterialLib.getInstance();
            childObjcet = _loc_3.getMaterial(_loc_2) as DisplayObject;
            if (_size == SMALL)
            {
                childObjcet.scaleX = 0.5;
                childObjcet.scaleY = 0.5;
            }
            else if (_size == BIG)
            {
                childObjcet.scaleX = 1;
                childObjcet.scaleY = 1;
            }// end else if
            childObjcet.x = (_w - childObjcet.width) / 2;
            childObjcet.y = (_h - childObjcet.height) / 2;
            if (_enable)
            {
                childObjcet.filters = [];
            }
            else
            {
                childObjcet.filters = [new ColorMatrixFilter([0.3086, 0.6094, 0.082, 0, 0, 0.3086, 0.6094, 0.082, 0, 0, 0.3086, 0.6094, 0.082, 0, 0, 0, 0, 0, 1, 0])];
            }// end else if
            addChild(childObjcet);
            return;
        }// end function

        private function setMachine() : void
        {
            var _loc_1:String;
            if (childObjcet != null)
            {
                removeChild(childObjcet);
                childObjcet = null;
            }// end if
            var _loc_2:* = MaterialLib.getInstance();
            childObjcet = _loc_2.getMaterial(_loc_1) as DisplayObject;
            if (_size == SMALL)
            {
                childObjcet.scaleX = 0.5;
                childObjcet.scaleY = 0.5;
            }
            else if (_size == BIG)
            {
                childObjcet.scaleX = 1;
                childObjcet.scaleY = 1;
            }// end else if
            childObjcet.x = (_w - childObjcet.width) / 2;
            childObjcet.y = (_h - childObjcet.height) / 2;
            if (_enable)
            {
                childObjcet.filters = [];
            }
            else
            {
                childObjcet.filters = [new ColorMatrixFilter([0.3086, 0.6094, 0.082, 0, 0, 0.3086, 0.6094, 0.082, 0, 0, 0.3086, 0.6094, 0.082, 0, 0, 0, 0, 0, 1, 0])];
            }// end else if
            addChild(childObjcet);
            return;
        }// end function

        private function setDiy(param1:int) : void
        {
            var _loc_2:* = _size == SMALL ? ("") : ("b");
            var _loc_3:* = GetCropID.idToDiyUrl(param1.toString(), _loc_2);
            if (childObjcet != null)
            {
                if (childObjcet is Loader == false)
                {
                    removeChild(childObjcet);
                    childObjcet = null;
                }// end if
            }// end if
            if (_loc_3 == "")
            {
                return;
            }// end if
            if (param1 != currentId)
            {
                childObjcet = new Loader();
                (childObjcet as Loader).load(new URLRequest(_loc_3));
            }// end if
            if (_enable)
            {
                childObjcet.filters = [];
            }
            else
            {
                childObjcet.filters = [new ColorMatrixFilter([0.3086, 0.6094, 0.082, 0, 0, 0.3086, 0.6094, 0.082, 0, 0, 0.3086, 0.6094, 0.082, 0, 0, 0, 0, 0, 1, 0])];
            }// end else if
            addChild(childObjcet);
            return;
        }// end function

        private function setGold(param1:int) : void
        {
            setChildObject("GoldBigIcon");
            return;
        }// end function

        private function setChildObject(param1:String) : void
        {
            if (childObjcet != null)
            {
                removeChild(childObjcet);
                childObjcet = null;
            }// end if
            childObjcet = MaterialLib.getInstance().getMaterial(param1) as DisplayObject;
            if (_size == SMALL)
            {
                childObjcet.scaleX = 1;
                childObjcet.scaleY = 1;
            }
            else if (_size == BIG)
            {
                childObjcet.scaleX = 2;
                childObjcet.scaleY = 2;
            }// end else if
            childObjcet.x = (_w - childObjcet.width) / 2;
            childObjcet.y = (_h - childObjcet.height) / 2;
            if (_enable)
            {
                childObjcet.filters = [];
            }
            else
            {
                childObjcet.filters = [new ColorMatrixFilter([0.3086, 0.6094, 0.082, 0, 0, 0.3086, 0.6094, 0.082, 0, 0, 0.3086, 0.6094, 0.082, 0, 0, 0, 0, 0, 1, 0])];
            }// end else if
            addChild(childObjcet);
            return;
        }// end function

        private function setExp(param1:int) : void
        {
            setChildObject("ExpIcon");
            return;
        }// end function

        private function setTool(param1:int) : void
        {
            var _loc_2:* = GetCropID.idToToolName(param1.toString());
            if (childObjcet != null)
            {
                removeChild(childObjcet);
                childObjcet = null;
            }// end if
            if (_loc_2 == "")
            {
                return;
            }// end if
            var _loc_3:* = MaterialLib.getInstance();
            childObjcet = _loc_3.getMaterial(_loc_2) as DisplayObject;
            if (_size == SMALL)
            {
                childObjcet.scaleX = 1;
                childObjcet.scaleY = 1;
            }
            else if (_size == BIG)
            {
                childObjcet.scaleX = 2;
                childObjcet.scaleY = 2;
            }// end else if
            childObjcet.x = (_w - childObjcet.width) / 2;
            childObjcet.y = (_h - childObjcet.height) / 2;
            if (_enable)
            {
                childObjcet.filters = [];
            }
            else
            {
                childObjcet.filters = [new ColorMatrixFilter([0.3086, 0.6094, 0.082, 0, 0, 0.3086, 0.6094, 0.082, 0, 0, 0.3086, 0.6094, 0.082, 0, 0, 0, 0, 0, 1, 0])];
            }// end else if
            addChild(childObjcet);
            return;
        }// end function

        public function setContent(param1:String, param2:int, param3:Boolean = true) : void
        {
            if (currentType == param1 && currentId == param2 && param3 == _enable)
            {
                return;
            }// end if
            _enable = param3;
            if (param1 == "1")
            {
                setCrop(param2);
            }
            else if (param1 == "3" || param1 == "21" || param1 == "22" || param1 == "909090")
            {
                setTool(param2);
            }
            else if (param1 == "2")
            {
                setDiy(param2);
            }
            else if (param1 == "4")
            {
                setDog(param2);
            }
            else if (param1 == "5")
            {
            }
            else if (param1 == "6")
            {
                setGold(param2);
            }
            else if (param1 == "7")
            {
                setExp(param2);
            }
            else if (param1 == "1000")
            {
                setMachine();
            }// end else if
            currentType = param1;
            currentId = param2;
            return;
        }// end function

        private function setCrop(param1:int) : void
        {
            var _loc_2:* = GetCropID.idToSeedName(param1.toString());
            if (childObjcet != null)
            {
                removeChild(childObjcet);
                childObjcet = null;
            }// end if
            if (_loc_2 == "")
            {
                return;
            }// end if
            var _loc_3:* = MaterialLib.getInstance();
            childObjcet = _loc_3.getMaterial(_loc_2) as DisplayObject;
            if (_size == SMALL)
            {
                childObjcet.scaleX = 1;
                childObjcet.scaleY = 1;
            }
            else if (_size == BIG)
            {
                childObjcet.scaleX = 2;
                childObjcet.scaleY = 2;
            }// end else if
            childObjcet.x = (_w - childObjcet.width) / 2;
            childObjcet.y = (_h - childObjcet.height) / 2;
            if (_enable)
            {
                childObjcet.filters = [];
            }
            else
            {
                childObjcet.filters = [new ColorMatrixFilter([0.3086, 0.6094, 0.082, 0, 0, 0.3086, 0.6094, 0.082, 0, 0, 0.3086, 0.6094, 0.082, 0, 0, 0, 0, 0, 1, 0])];
            }// end else if
            addChild(childObjcet);
            return;
        }// end function

    }
}
