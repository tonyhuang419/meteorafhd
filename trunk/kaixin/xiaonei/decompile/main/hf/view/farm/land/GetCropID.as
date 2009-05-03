package hf.view.farm.land
{
    import hf.model.*;

    public class GetCropID extends Object
    {
        private static var _toolList:Object = {1:"Fertilizer", 2:"FertilizerFast", 3:"FertilizerVeryFast", 4:"FriendManure", 5:"HitDogStick", 6:"BetterSpraying", 40001:"Dog", 909090:"ShopDogFood"};
        private static var _xml:XML;

        public function GetCropID()
        {
            return;
        }// end function

        public static function idToDiyUrl(param1:String, param2:String = "") : String
        {
            var _loc_3:String;
            if (_xml == null)
            {
                _xml = INI.getInstance().data;
            }// end if
            _loc_3 = _xml.folder.@url;
            _loc_3 = _loc_3 + ("farm/diy/" + param1 + param2 + ".jpg");
            return _loc_3;
        }// end function

        public static function idToName(param1:String) : String
        {
            var value:* = param1;
            var _name:String;
            if (_xml == null)
            {
                _xml = INI.getInstance().data;
            }// end if
            var _loc_4:int;
            var _loc_5:* = _xml.crops.crop;
            var _loc_3:* = new XMLList("");
            for each (_loc_6 in _loc_5)
            {
                // label
                var _loc_7:* = _loc_5[_loc_4];
                with (_loc_5[_loc_4])
                {
                    if (@id == value)
                    {
                        _loc_3[_loc_4] = _loc_6;
                    }// end if
                }// end with
            }// end of for each ... in
            _name = _loc_3.@className;
            return _name;
        }// end function

        public static function getSwfUrl() : String
        {
            var _loc_1:String;
            if (_xml == null)
            {
                _xml = INI.getInstance().data;
            }// end if
            _loc_1 = _xml.folder.@url;
            return _loc_1;
        }// end function

        public static function getInsectPosition(param1:String, param2:int = 1) : Array
        {
            var positionString:String;
            var positionArr:Array;
            var positionArr2:Array;
            var id:* = param1;
            var step:* = param2;
            if (_xml == null)
            {
                _xml = INI.getInstance().data;
            }// end if
            var _loc_5:int;
            var _loc_6:* = _xml.crops.crop;
            var _loc_4:* = new XMLList("");
            for each (_loc_7 in _loc_6)
            {
                // label
                var _loc_8:* = _loc_6[_loc_5];
                with (_loc_6[_loc_5])
                {
                    if (@id == id)
                    {
                        _loc_4[_loc_5] = _loc_7;
                    }// end if
                }// end with
            }// end of for each ... in
            positionString = _loc_4.insect.@position;
            positionArr = positionString.split("|")[step].split(",");
            positionArr2;
            return positionArr2;
        }// end function

        public static function idToDiyUrl2(param1:String) : String
        {
            var _loc_2:String;
            if (_xml == null)
            {
                _xml = INI.getInstance().data;
            }// end if
            if (param1 == "11" || param1 == "26" || param1 == "21" || param1 == "31" || param1 == "1001")
            {
                param1 = param1 + "f.jpg";
            }
            else
            {
                param1 = param1 + ".swf";
            }// end else if
            _loc_2 = _xml.folder.@url;
            _loc_2 = _loc_2 + ("farm/diy/" + param1);
            return _loc_2;
        }// end function

        public static function idToDogName(param1:String) : String
        {
            var _loc_2:String;
            if (int(param1) < 10000)
            {
                param1 = "4000" + "1";
            }// end if
            if (_toolList.hasOwnProperty(param1))
            {
                _loc_2 = _toolList[param1];
            }// end if
            return _loc_2;
        }// end function

        public static function idToSeedName(param1:String) : String
        {
            var value:* = param1;
            var _name:String;
            if (_xml == null)
            {
                _xml = INI.getInstance().data;
            }// end if
            var _loc_4:int;
            var _loc_5:* = _xml.crops.crop;
            var _loc_3:* = new XMLList("");
            for each (_loc_6 in _loc_5)
            {
                // label
                var _loc_7:* = _loc_5[_loc_4];
                with (_loc_5[_loc_4])
                {
                    if (@id == value)
                    {
                        _loc_3[_loc_4] = _loc_6;
                    }// end if
                }// end with
            }// end of for each ... in
            _name = _loc_3.@seed;
            return _name;
        }// end function

        public static function idState(param1:String, param2:Number, param3:String, param4:int = 0) : int
        {
            var timeString:String;
            var _a:Array;
            var _sTime:Number;
            var _cTime:Number;
            var i:int;
            var _state:int;
            var id:* = param1;
            var plantTime:* = param2;
            var state:* = param3;
            var harvestTimes:* = param4;
            if (_xml == null)
            {
                _xml = INI.getInstance().data;
            }// end if
            if (id == "0")
            {
                return 0;
            }// end if
            if (state == "7")
            {
                return 7;
            }// end if
            var _loc_7:int;
            var _loc_8:* = _xml.crops.crop;
            var _loc_6:* = new XMLList("");
            for each (_loc_9 in _loc_8)
            {
                // label
                var _loc_10:* = _loc_8[_loc_7];
                with (_loc_8[_loc_7])
                {
                    if (@id == id)
                    {
                        _loc_6[_loc_7] = _loc_9;
                    }// end if
                }// end with
            }// end of for each ... in
            timeString = _loc_6.cropGrow.@value;
            _a = timeString.split(",");
            _sTime = MData.getInstance().mainData.serverTime;
            _cTime = _sTime - plantTime;
            i = _a.length--;
            while (i >= 0)
            {
                // label
                if (_cTime >= _a[i])
                {
                    _state = i + 1;
                    if (harvestTimes > 0)
                    {
                        if (_state < 3)
                        {
                            return 3;
                        }// end if
                    }// end if
                    return i + 1;
                }// end if
                i = i--;
            }// end while
            return 0;
        }// end function

        public static function idToToolName(param1:String) : String
        {
            var _loc_2:String;
            if (_toolList.hasOwnProperty(param1))
            {
                _loc_2 = _toolList[param1];
            }// end if
            return _loc_2;
        }// end function

        public static function remainTime(param1:int, param2:Number) : Object
        {
            var timeString:String;
            var textString:String;
            var _a:Array;
            var _sTime:Number;
            var _cTime:Number;
            var _textArr:Array;
            var allTime:Number;
            var _p:Number;
            var i:int;
            var _remain:int;
            var id:* = param1;
            var plantTime:* = param2;
            if (_xml == null)
            {
                _xml = INI.getInstance().data;
            }// end if
            if (id == 0)
            {
                return {remain:0, text:"", progress:1};
            }// end if
            var _loc_5:int;
            var _loc_6:* = _xml.crops.crop;
            var _loc_4:* = new XMLList("");
            for each (_loc_7 in _loc_6)
            {
                // label
                var _loc_8:* = _loc_6[_loc_5];
                with (_loc_6[_loc_5])
                {
                    if (@id == id)
                    {
                        _loc_4[_loc_5] = _loc_7;
                    }// end if
                }// end with
            }// end of for each ... in
            timeString = _loc_4.cropGrow.@value;
            var _loc_5:int;
            var _loc_6:* = _xml.crops.crop;
            var _loc_4:* = new XMLList("");
            for each (_loc_7 in _loc_6)
            {
                // label
                var _loc_8:* = _loc_6[_loc_5];
                with (_loc_6[_loc_5])
                {
                    if (@id == id)
                    {
                        _loc_4[_loc_5] = _loc_7;
                    }// end if
                }// end with
            }// end of for each ... in
            textString = _loc_4.nextText.@value;
            _a = timeString.split(",");
            _sTime = MData.getInstance().mainData.serverTime;
            _cTime = _sTime - plantTime;
            _textArr = textString.split(",");
            allTime = _a[_a.length - 2];
            _p = _cTime / allTime;
            if (_p > 1)
            {
                _p;
            }// end if
            i;
            while (i < _a.length)
            {
                // label
                if (_cTime < _a[i])
                {
                    _remain = _a[i] - _cTime;
                    if (i == _a.length--)
                    {
                        _remain;
                    }// end if
                    return {remain:_remain, text:_textArr[i + 1], progress:_p};
                }// end if
                i = i++;
            }// end while
            return {remain:0, text:"", progress:1};
        }// end function

        public static function cropTime(param1:String) : String
        {
            var id:* = param1;
            if (_xml == null)
            {
                _xml = INI.getInstance().data;
            }// end if
            if (id == "0")
            {
                return "";
            }// end if
            var _loc_4:int;
            var _loc_5:* = _xml.crops.crop;
            var _loc_3:* = new XMLList("");
            for each (_loc_6 in _loc_5)
            {
                // label
                var _loc_7:* = _loc_5[_loc_4];
                with (_loc_5[_loc_4])
                {
                    if (@id == id)
                    {
                        _loc_3[_loc_4] = _loc_6;
                    }// end if
                }// end with
            }// end of for each ... in
            var timeString:* = _loc_3.cropGrow.@value;
            return timeString;
        }// end function

    }
}
