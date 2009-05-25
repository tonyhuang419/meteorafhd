package hf.view.main.window.flower.xml
{
    import hf.model.*;

    public class Configure extends Object
    {
        private var xml:XML;
        private static var configure:Configure;

        public function Configure()
        {
            xml = INI.getInstance().data;
            return;
        }// end function

        public function getGiftId(param1:String, param2:String, param3:String) : String
        {
            var fclass:* = param1;
            var fid:* = param2;
            var num:* = param3;
            var className:* = fclass + "_" + fid + "_" + num;
            var _loc_6:int;
            var _loc_9:int;
            var _loc_10:* = xml.gifts.gift;
            var _loc_8:* = new XMLList("");
            for each (_loc_11 in _loc_10)
            {
                // label
                var _loc_12:* = _loc_10[_loc_9];
                with (_loc_10[_loc_9])
                {
                    if (@fclass == fclass)
                    {
                        _loc_8[_loc_9] = _loc_11;
                    }// end if
                }// end with
            }// end of for each ... in
            var _loc_7:* = _loc_8.item;
            var _loc_5:* = new XMLList("");
            for each (_loc_8 in _loc_7)
            {
                // label
                var _loc_9:* = _loc_7[_loc_6];
                with (_loc_7[_loc_6])
                {
                    if (@className == className)
                    {
                        _loc_5[_loc_6] = _loc_8;
                    }// end if
                }// end with
            }// end of for each ... in
            return _loc_5.@id;
        }// end function

        public function getFlowerClassName(param1:String) : String
        {
            var value:* = param1;
            var _loc_4:int;
            var _loc_5:* = xml.crops.crop;
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
            return _loc_3.@className;
        }// end function

        public function isFlower(param1:String) : Boolean
        {
            var value:* = param1;
            var _loc_4:int;
            var _loc_5:* = xml.crops.crop;
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
            if (_loc_3.@type == "flower")
            {
                return true;
            }// end if
            return false;
        }// end function

        public function getFlowerClass(param1:String) : String
        {
            var value:* = param1;
            var _loc_4:int;
            var _loc_5:* = xml.crops.crop;
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
            return _loc_3.@fclass;
        }// end function

        public function getFlowerNumberList(param1:String) : Array
        {
            var str:String;
            var value:* = param1;
            var _loc_4:int;
            var _loc_5:* = xml.gifts.gift;
            var _loc_3:* = new XMLList("");
            for each (_loc_6 in _loc_5)
            {
                // label
                var _loc_7:* = _loc_5[_loc_4];
                with (_loc_5[_loc_4])
                {
                    if (@fclass == value)
                    {
                        _loc_3[_loc_4] = _loc_6;
                    }// end if
                }// end with
            }// end of for each ... in
            str = _loc_3.@number;
            return str.split(",");
        }// end function

        public function getFlowerIdList(param1:String) : Array
        {
            var str:String;
            var value:* = param1;
            var _loc_4:int;
            var _loc_5:* = xml.gifts.gift;
            var _loc_3:* = new XMLList("");
            for each (_loc_6 in _loc_5)
            {
                // label
                var _loc_7:* = _loc_5[_loc_4];
                with (_loc_5[_loc_4])
                {
                    if (@fclass == value)
                    {
                        _loc_3[_loc_4] = _loc_6;
                    }// end if
                }// end with
            }// end of for each ... in
            str = _loc_3.@flowerId;
            return str.split(",");
        }// end function

        public static function getInstance() : Configure
        {
            if (configure == null)
            {
                configure = new Configure;
            }// end if
            return configure;
        }// end function

    }
}
