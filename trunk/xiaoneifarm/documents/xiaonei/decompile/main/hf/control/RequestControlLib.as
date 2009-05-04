package hf.control
{

    public class RequestControlLib extends Object
    {
        private var farmReqCount:Object;
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

        public function regFarmReqCount(param1:String, param2:int, param3:int, param4:int = 0) : Boolean
        {
            if (!farmReqCount[param1])
            {
                farmReqCount[param1] = {};
            }// end if
            if (!farmReqCount[param1][param2])
            {
                farmReqCount[param1][param2] = {};
            }// end if
            if (!farmReqCount[param1][param2]["req"])
            {
                farmReqCount[param1][param2]["max"] = param3;
                farmReqCount[param1][param2]["req"] = 0;
            }// end if
            if (farmReqCount[param1][param2]["max"] != param3)
            {
                throw new Error("最大请求次数不一致，请查证。");
            }// end if
            if (farmReqCount[param1][param2]["req"] + param4 >= param3)
            {
                return true;
            }// end if
            var _loc_5:* = farmReqCount[param1][param2];
            var _loc_6:String;
            _loc_5[_loc_6] = farmReqCount[param1][param2]["req"]++;
            return false;
        }// end function

        public function farmReqSuccess(param1:String, param2:Object) : void
        {
            var _loc_3:Object;
            if (param2.hasOwnProperty("farmlandIndex"))
            {
                var _loc_4:* = farmReqCount[param1][param2["farmlandIndex"]];
                var _loc_5:String;
                _loc_4[_loc_5] = farmReqCount[param1][param2["farmlandIndex"]]["req"]--;
            }
            else
            {
                for each (_loc_3 in farmReqCount[param1])
                {
                    // label
                    _loc_3["req"] = 0;
                }// end of for each ... in
            }// end else if
            return;
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
