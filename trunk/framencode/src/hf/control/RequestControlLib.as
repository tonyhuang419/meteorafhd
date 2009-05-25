package hf.control
{
    import flash.utils.*;

    public class RequestControlLib extends Object
    {
        private var farmReqCount:Object;
        private var maxTimeOut:int = 5000;
        private static var instance:RequestControlLib;

        public function RequestControlLib()
        {
            if (instance == null)
            {
                farmReqCount = new Object();
            }
            else
            {
                throw new Error("实例化单例类出错-RequestControlLib");
            }// end else if
            return;
        }// end function

        public function farmReqSuccess(param1:String, param2:Object) : void
        {
            var _loc_3:Object;
            if (param2.hasOwnProperty("farmlandIndex"))
            {
                var _loc_4:* = farmReqCount[param1][param2["farmlandIndex"]];
                var _loc_5:String;
                _loc_4[_loc_5] = farmReqCount[param1][param2["farmlandIndex"]]["req"]--;
                farmReqCount[param1][param2["farmlandIndex"]]["timeout"] = false;
            }
            else
            {
                for each (_loc_3 in farmReqCount[param1])
                {
                    // label
                    _loc_3 = {};
                }// end of for each ... in
            }// end else if
            return;
        }// end function

        public function regFarmReqCount(param1:String, param2:int, param3:int, param4:int = 0, param5:String = "", param6:int = 0) : Boolean
        {
            var _loc_7:int;
            if (!farmReqCount.hasOwnProperty(param1))
            {
                farmReqCount[param1] = {};
            }// end if
            if (!farmReqCount[param1].hasOwnProperty(param2))
            {
                farmReqCount[param1][param2] = {};
            }// end if
            if (!farmReqCount[param1][param2].hasOwnProperty("req"))
            {
                farmReqCount[param1][param2]["req"] = 0;
            }// end if
            if (!farmReqCount[param1][param2].hasOwnProperty("timeout"))
            {
                farmReqCount[param1][param2]["timeout"] = false;
                farmReqCount[param1][param2]["start"] = getTimer();
            }// end if
            if (farmReqCount[param1][param2].hasOwnProperty("timeout"))
            {
                if (getTimer() - farmReqCount[param1][param2]["start"] > maxTimeOut)
                {
                    farmReqCount[param1][param2]["timeout"] = false;
                    farmReqCount[param1][param2]["req"] = 0;
                }// end if
            }// end if
            if (param6 != 0 && !farmReqCount[param1][param2].hasOwnProperty(param5))
            {
                farmReqCount[param1][param2][param5] = getTimer();
                var _loc_8:* = farmReqCount[param1][param2];
                var _loc_9:String;
                _loc_8[_loc_9] = farmReqCount[param1][param2]["req"]++;
                return false;
            }// end if
            if (farmReqCount[param1][param2].hasOwnProperty(param5))
            {
                _loc_7 = getTimer();
                if (_loc_7 - farmReqCount[param1][param2][param5] < param6)
                {
                    return true;
                }// end if
                farmReqCount[param1][param2][param5] = _loc_7;
            }// end if
            if (farmReqCount[param1][param2]["req"] + param4 >= param3)
            {
                return true;
            }// end if
            var _loc_8:* = farmReqCount[param1][param2];
            var _loc_9:String;
            _loc_8[_loc_9] = farmReqCount[param1][param2]["req"]++;
            farmReqCount[param1][param2]["timeout"] = true;
            farmReqCount[param1][param2]["start"] = getTimer();
            return false;
        }// end function

        public static function getInstance() : RequestControlLib
        {
            if (instance == null)
            {
                instance = new RequestControlLib;
            }// end if
            return instance;
        }// end function

    }
}
