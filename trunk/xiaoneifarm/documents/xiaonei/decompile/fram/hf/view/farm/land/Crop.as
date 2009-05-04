package hf.view.farm.land
{
    import flash.display.*;
    import hf.view.*;

    public class Crop extends Sprite
    {
        private var lib:MaterialLib;
        private var _step:int = 0;
        private var _currentCrop:Sprite;
        private var _cropName:String = "";

        public function Crop()
        {
            lib = MaterialLib.getInstance();
            return;
        }// end function

        public function get step() : int
        {
            return _step;
        }// end function

        private function setState() : void
        {
            var _loc_1:int;
            if (_currentCrop != null)
            {
                _loc_1 = 0;
                while (_loc_1 < _currentCrop.numChildren)
                {
                    // label
                    if (_loc_1 == step)
                    {
                        _currentCrop.getChildAt(_loc_1).visible = true;
                    }
                    else
                    {
                        _currentCrop.getChildAt(_loc_1).visible = false;
                    }// end else if
                    _loc_1++;
                }// end while
            }// end if
            return;
        }// end function

        public function set cropName(param1:String) : void
        {
            if (param1 != "" && param1 == _cropName)
            {
                return;
            }// end if
            if (_currentCrop != null)
            {
                removeChild(_currentCrop);
                _currentCrop = null;
            }// end if
            if (param1 != "")
            {
                _currentCrop = lib.getMaterial(param1) as Sprite;
                addChild(_currentCrop);
                setState();
            }// end if
            _cropName = param1;
            return;
        }// end function

        public function set step(param1:int) : void
        {
            if (param1 == _step)
            {
                return;
            }// end if
            _step = param1;
            setState();
            return;
        }// end function

        public function get cropName() : String
        {
            return _cropName;
        }// end function

    }
}
