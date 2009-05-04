package hf.view.farm.land
{
    import flash.display.*;
    import flash.events.*;
    import flash.filters.*;
    import flash.utils.*;
    import hf.view.*;

    public class FarmlandItem extends Sprite
    {
        private var _fertilize:Boolean = false;
        private var lib:MaterialLib;
        private var _wasteland:Wasteland;
        private var _land:Land;
        private var _humidity:Boolean = true;
        private var _sundries:Sundries;
        private var _waste:Boolean = false;
        private var useFilter:Boolean = false;
        private var _crop:Crop;

        public function FarmlandItem()
        {
            lib = MaterialLib.getInstance();
            init();
            cacheAsBitmap = true;
            return;
        }// end function

        private function setStatus() : void
        {
            if (humidity && !fertilize)
            {
                status = Land.SUITABLE;
            }
            else if (humidity && fertilize)
            {
                status = Land.FERTILE;
            }
            else if (!humidity && fertilize)
            {
                status = Land.ARID_FERTILE;
            }
            else
            {
                status = Land.ARID;
            }// end else if
            return;
        }// end function

        public function get bigInsect() : String
        {
            if (_sundries == null)
            {
                return null;
            }// end if
            return _sundries.bigInsect;
        }// end function

        private function init() : void
        {
            addEventListener(MouseEvent.ROLL_OVER, onOver);
            addEventListener(MouseEvent.ROLL_OUT, onOut);
            return;
        }// end function

        public function set reclaim(param1:Boolean) : void
        {
            if (_wasteland != null)
            {
                _wasteland.reclaim = param1;
            }// end if
            return;
        }// end function

        private function onOver(param1:MouseEvent) : void
        {
            var _loc_2:Timer;
            if (_land != null && waste == false)
            {
                if (!useFilter)
                {
                    useFilter = true;
                    _loc_2 = new Timer(200, 1);
                    _loc_2.addEventListener(TimerEvent.TIMER, onTimer);
                    _loc_2.start();
                }// end if
            }// end if
            return;
        }// end function

        public function set bigInsect(param1:String) : void
        {
            if (_sundries == null)
            {
                _sundries = new Sundries();
                addChild(_sundries);
            }// end if
            _sundries.bigInsect = param1;
            return;
        }// end function

        public function set step(param1:int) : void
        {
            if (_crop != null)
            {
                _crop.step = param1;
            }// end if
            return;
        }// end function

        public function set weed(param1:int) : void
        {
            if (_sundries == null)
            {
                _sundries = new Sundries();
                addChild(_sundries);
            }// end if
            _sundries.weed = param1;
            return;
        }// end function

        public function clearAll() : void
        {
            if (_crop != null)
            {
                removeChild(_crop);
                _crop = null;
            }// end if
            if (_sundries != null)
            {
                removeChild(_sundries);
                _sundries = null;
            }// end if
            if (_land != null)
            {
                _land.status = Land.SUITABLE;
            }// end if
            return;
        }// end function

        public function set insect(param1:int) : void
        {
            if (_sundries == null)
            {
                _sundries = new Sundries();
                addChild(_sundries);
            }// end if
            _sundries.insect = param1;
            return;
        }// end function

        public function set fertilize(param1:Boolean) : void
        {
            _fertilize = param1;
            setStatus();
            return;
        }// end function

        public function get waste() : Boolean
        {
            return _waste;
        }// end function

        public function set insectPosition(param1:Array) : void
        {
            if (_sundries == null)
            {
                _sundries = new Sundries();
                addChild(_sundries);
            }// end if
            _sundries.insectPosition = param1;
            return;
        }// end function

        public function get step() : int
        {
            if (_crop != null)
            {
                return _crop.step;
            }// end if
            return 0;
        }// end function

        private function onTimer(param1:TimerEvent) : void
        {
            if (useFilter)
            {
                filters = [new ColorMatrixFilter([1.34, 0, 0, 0, -21.59, 0, 1.34, 0, 0, -21.59, 0, 0, 1.34, 0, -21.59, 0, 0, 0, 1, 0])];
            }// end if
            return;
        }// end function

        public function get insect() : int
        {
            if (_sundries == null)
            {
                return 0;
            }// end if
            return _sundries.insect;
        }// end function

        public function get reclaim() : Boolean
        {
            if (_wasteland != null)
            {
                return _wasteland.reclaim;
            }// end if
            return false;
        }// end function

        public function get weed() : int
        {
            if (_sundries == null)
            {
                return 0;
            }// end if
            return _sundries.weed;
        }// end function

        public function get fertilize() : Boolean
        {
            return _fertilize;
        }// end function

        public function get insectPosition() : Array
        {
            if (_sundries == null)
            {
                return [];
            }// end if
            return _sundries.insectPosition;
        }// end function

        public function get status() : String
        {
            if (_land != null)
            {
                return _land.status;
            }// end if
            return "";
        }// end function

        public function set waste(param1:Boolean) : void
        {
            _waste = param1;
            if (param1)
            {
                if (_land != null)
                {
                    removeChild(_land);
                    _land = null;
                }// end if
                if (_crop != null)
                {
                    removeChild(_crop);
                    _crop = null;
                }// end if
                if (_sundries != null)
                {
                    removeChild(_sundries);
                    _sundries = null;
                }// end if
                if (_wasteland == null)
                {
                    _wasteland = new Wasteland();
                    addChild(_wasteland);
                }// end if
            }
            else
            {
                if (_wasteland != null)
                {
                    removeChild(_wasteland);
                    _wasteland = null;
                }// end if
                if (_land == null)
                {
                    _land = new Land();
                    addChild(_land);
                }// end if
            }// end else if
            return;
        }// end function

        public function set humidity(param1:Boolean) : void
        {
            _humidity = param1;
            setStatus();
            return;
        }// end function

        private function onOut(param1:MouseEvent) : void
        {
            if (_land != null && waste == false)
            {
                filters = [];
            }// end if
            useFilter = false;
            return;
        }// end function

        public function get humidity() : Boolean
        {
            return _humidity;
        }// end function

        public function get cropName() : String
        {
            if (_crop != null)
            {
                return _crop.cropName;
            }// end if
            return "";
        }// end function

        public function set status(param1:String) : void
        {
            if (_land != null)
            {
                _land.status = param1;
            }// end if
            return;
        }// end function

        public function set cropName(param1:String) : void
        {
            if (_crop == null)
            {
                _crop = new Crop();
                addChild(_crop);
            }// end if
            _crop.cropName = param1;
            return;
        }// end function

    }
}
