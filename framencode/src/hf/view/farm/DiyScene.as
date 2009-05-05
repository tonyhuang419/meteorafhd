package hf.view.farm
{
    import com.minutes.net.*;
    import flash.display.*;
    import flash.events.*;
    import hf.view.*;

    public class DiyScene extends Sprite
    {
        private var _bgD:DisplayObject;
        private var _house:String = "null";
        private var _dogHouse:String = "null";
        private var _bg:String = "null";
        private var _houseD:DisplayObject;
        private var fileLoader:FileLoader;
        private var _dogHouseD:DisplayObject;
        private var _fence:String = "null";
        private var _fenceD:DisplayObject;
        private var diyUrl:Array;

        public function DiyScene()
        {
            diyUrl = [];
            this.cacheAsBitmap = true;
            this.opaqueBackground = true;
            var _loc_1:* = MaterialLib.getInstance().getClass("DefaultBg") as Class;
            var _loc_2:* = new _loc_1(938, 713);
            var _loc_3:* = new Bitmap(_loc_2);
            addChild(_loc_3);
            return;
        }// end function

        private function onComp(param1:Event) : void
        {
            var _loc_2:* = fileLoader.contents;
            if (_loc_2[_bg] != "" && _loc_2[_bg] != "error")
            {
                if (_bgD != null)
                {
                    removeChild(_bgD);
                    _bgD = null;
                }// end if
                _bgD = _loc_2[_bg];
                _bgD.x = 0;
                _bgD.y = 0;
                addChild(_bgD);
            }// end if
            if (_fenceD != null)
            {
                removeChild(_fenceD);
                _fenceD = null;
            }// end if
            if (_loc_2[_fence] != "" && _loc_2[_fence] != "error")
            {
                _fenceD = _loc_2[_fence];
                _fenceD.x = 260 + 120;
                _fenceD.y = 86 + 100;
                addChild(_fenceD);
            }// end if
            if (_loc_2[_house] != "" && _loc_2[_house] != "error")
            {
                if (_houseD != null)
                {
                    removeChild(_houseD);
                    _houseD = null;
                }// end if
                _houseD = _loc_2[_house];
                _houseD.x = 501 + 70;
                _houseD.y = 162 + 80;
                addChild(_houseD);
            }// end if
            if (_dogHouseD != null)
            {
                removeChild(_dogHouseD);
                _dogHouseD = null;
            }// end if
            if (_loc_2[_dogHouse] != "" && _loc_2[_dogHouse] != "error")
            {
                _dogHouseD = _loc_2[_dogHouse];
                _dogHouseD.x = 344 + 120;
                _dogHouseD.y = 131 + 100;
                addChild(_dogHouseD);
            }// end if
            return;
        }// end function

        public function setDIYUrl(param1:String = "", param2:String = "", param3:String = "", param4:String = "") : void
        {
            diyUrl = [];
            if (_bg != param1)
            {
                _bg = param1;
                diyUrl.push(param1);
            }// end if
            if (_fence != param2)
            {
                _fence = param2;
                diyUrl.push(param2);
            }// end if
            if (_house != param3)
            {
                _house = param3;
                diyUrl.push(param3);
            }// end if
            if (_dogHouse != param4)
            {
                _dogHouse = param4;
                diyUrl.push(param4);
            }// end if
            if (fileLoader == null)
            {
                fileLoader = new FileLoader();
                fileLoader.addEventListener(Event.COMPLETE, onComp);
            }// end if
            fileLoader.load(diyUrl);
            return;
        }// end function

    }
}
