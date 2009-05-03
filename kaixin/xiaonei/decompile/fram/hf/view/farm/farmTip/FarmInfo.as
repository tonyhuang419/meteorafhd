package hf.view.farm.farmTip
{
    import flash.display.*;
    import flash.events.*;
    import hf.model.*;
    import hf.view.*;
    import hf.view.common.*;
    import hf.view.farm.land.*;
    import hf.view.main.tip.*;

    public class FarmInfo extends Tip
    {
        private var farmInfo:MovieClip;

        public function FarmInfo()
        {
            MData.getInstance().mainData.addEventListener(MainData.FARMLAND_DATA_INDEX, dataChange, false, 0, true);
            return;
        }// end function

        private function sortArray(param1:Object, param2:Object) : int
        {
            if (Number(param1.time) > Number(param2.time))
            {
                return 1;
            }// end if
            return -1;
        }// end function

        private function addFarmInfo() : void
        {
            if (farmInfo == null)
            {
                farmInfo = MaterialLib.getInstance().getMaterial("FarmInfo") as MovieClip;
                addChild(farmInfo);
            }
            else
            {
                addChild(farmInfo);
            }// end else if
            return;
        }// end function

        private function countHealth(param1:int, param2:int, param3:int, param4:int, param5:int, param6:int, param7:Object) : int
        {
            var _loc_8:int;
            var _loc_9:int;
            var _loc_10:Number;
            var _loc_11:int;
            var _loc_12:int;
            var _loc_13:Array;
            var _loc_15:Array;
            var _loc_16:int;
            var _loc_14:int;
            if (param7 is Array)
            {
                param7 = {2228612959:null};
            }
            else
            {
                param7["2228612959"] = null;
            }// end else if
            _loc_13 = jsonToArray(param7);
            _loc_8 = 0;
            while (_loc_8 < _loc_13.length)
            {
                // label
                if (_loc_13[_loc_8]["action"] == null)
                {
                    _loc_9 = MData.getInstance().mainData.serverTime;
                }
                else
                {
                    _loc_9 = _loc_13[_loc_8]["time"];
                }// end else if
                _loc_10 = (param3 + param4) * 0.2;
                if (param5 == 0)
                {
                    _loc_10 = _loc_10 + 0.6;
                }// end if
                if (_loc_14 > 0)
                {
                    _loc_10 = _loc_10 + _loc_14 * 0.6;
                }// end if
                _loc_12 = 100 - _loc_10 * 50;
                if (_loc_10 == 0)
                {
                    param1 = 100;
                }
                else
                {
                    _loc_11 = (param1 - _loc_12) * 60 / _loc_10;
                    if (_loc_11 + param2 < _loc_9)
                    {
                        param1 = _loc_12;
                    }
                    else
                    {
                        param1 = param1 - (_loc_9 - param2) * _loc_10 / 60;
                    }// end else if
                }// end else if
                param2 = _loc_9;
                if (_loc_13[_loc_8]["action"] != null)
                {
                    _loc_15 = new Array();
                    _loc_15 = _loc_15.concat(_loc_13[_loc_8]["action"]);
                    _loc_16 = 0;
                    while (_loc_16 < _loc_15.length)
                    {
                        // label
                        if (int(_loc_15[_loc_16]) == 1)
                        {
                            param4 = param4 + 1;
                            if (param4 > 3)
                            {
                                param4 = 3;
                            }// end if
                        }
                        else if (int(_loc_15[_loc_16]) == 2)
                        {
                            param3 = param3 + 1;
                            if (param3 > 3)
                            {
                                param3 = 3;
                            }// end if
                        }
                        else if (int(_loc_15[_loc_16]) == 3)
                        {
                            param5 = 0;
                        }
                        else if (int(_loc_15[_loc_16]) == 4)
                        {
                            param5 = 1;
                            param1 = param1 + 30;
                        }
                        else if (int(_loc_15[_loc_16]) == 5)
                        {
                            if (--param4 < 0)
                            {
                                --param4 = 0;
                            }// end if
                            param1 = param1 + 10;
                        }
                        else if (int(_loc_15[_loc_16]) == 6)
                        {
                            if (param3-- < 0)
                            {
                                _loc_3 = 0;
                            }// end if
                            param1 = param1 + 10;
                        }
                        else if (int(_loc_15[_loc_16]) == 7)
                        {
                            _loc_14 = 1;
                        }
                        else if (int(_loc_15[_loc_16]) == 10)
                        {
                            _loc_14 = 0;
                            param1 = param1 + 30;
                        }// end else if
                        _loc_16++;
                    }// end while
                }// end if
                if (param1 > 100)
                {
                    param1 = 100;
                }// end if
                _loc_8++;
            }// end while
            return param1;
        }// end function

        private function removeFarmInfo() : void
        {
            if (farmInfo == null)
            {
                return;
            }// end if
            if (contains(farmInfo))
            {
                removeChild(farmInfo);
            }// end if
            return;
        }// end function

        override public function set data(param1:Object) : void
        {
            var _loc_4:int;
            var _loc_5:String;
            var _loc_6:Sprite;
            var _loc_7:Object;
            var _loc_8:int;
            var _loc_9:int;
            var _loc_10:int;
            var _loc_11:String;
            var _loc_12:Object;
            var _loc_13:int;
            var _loc_14:int;
            var _loc_15:int;
            var _loc_16:String;
            var _loc_17:String;
            var _loc_18:String;
            var _loc_19:String;
            var _loc_20:String;
            var _loc_21:Boolean;
            var _loc_22:String;
            var _loc_23:String;
            var _loc_24:String;
            var _loc_25:String;
            var _loc_26:MainData;
            var _loc_27:Object;
            var _loc_28:Object;
            var _loc_29:String;
            var _loc_30:Object;
            super.data = param1;
            var _loc_2:* = int(param1);
            var _loc_3:* = MData.getInstance().mainData.farmlandData;
            if (_loc_3 == null)
            {
                removeFarmInfo();
            }
            else if (_loc_2 >= _loc_3.length)
            {
                removeFarmInfo();
            }
            else if (_loc_3[_loc_2]["cId"] == 0)
            {
                removeFarmInfo();
            }
            else if (_loc_3[_loc_2]["step"] == 7)
            {
                removeFarmInfo();
            }
            else
            {
                addFarmInfo();
                _loc_4 = _loc_3[_loc_2]["cId"];
                _loc_5 = GetCropID.idToSeedName(_loc_4.toString());
                _loc_6 = MaterialLib.getInstance().getMaterial(_loc_5) as Sprite;
                _loc_6.scaleX = 0.6;
                _loc_6.scaleY = 0.6;
                _loc_7 = _loc_3[_loc_2];
                _loc_8 = 0;
                _loc_9 = MData.getInstance().mainData.serverTime;
                _loc_10 = countHealth(_loc_7["health"], _loc_7["updateTime"], _loc_7["oldweed"], _loc_7["oldpest"], _loc_7["oldhumidity"], _loc_9, _loc_7["action"]);
                _loc_11 = "";
                if (_loc_10 <= 60)
                {
                    _loc_11 = Language.L["状态(可怕)"];
                }
                else if (_loc_10 > 60 && _loc_10 <= 70)
                {
                    _loc_11 = Language.L["状态(糟糕)"];
                }
                else if (_loc_10 > 70 && _loc_10 <= 80)
                {
                    _loc_11 = Language.L["状态(欠佳)"];
                }
                else if (_loc_10 > 80 && _loc_10 <= 95)
                {
                    _loc_11 = Language.L["状态(良好)"];
                }
                else if (_loc_10 > 95)
                {
                    _loc_11 = Language.L["状态(优秀)"];
                }// end else if
                _loc_12 = GetCropID.remainTime(_loc_4, _loc_7["plantTime"]);
                _loc_13 = _loc_12["remain"];
                _loc_14 = Math.floor(_loc_13 / 3600);
                _loc_15 = Math.ceil(_loc_13 % 3600 / 60);
                _loc_16 = "";
                _loc_17 = "";
                if (_loc_14 > 0)
                {
                    _loc_17 = Language.replaceText("hrsText", {hrs:_loc_14});
                }// end if
                if (_loc_15 > 0)
                {
                    _loc_15 = _loc_15 == 60 ? (59) : (_loc_15);
                    _loc_17 = _loc_17 + Language.replaceText("minsText", {mins:_loc_15});
                }// end if
                _loc_18 = String(_loc_7["harvestTimes"] - 0 + 1);
                _loc_19 = _loc_12["text"];
                _loc_20 = "";
                _loc_21 = false;
                if (_loc_15 == 0 && _loc_14 == 0)
                {
                    _loc_21 = true;
                }
                else
                {
                    _loc_21 = false;
                }// end else if
                _loc_22 = "";
                if (_loc_21)
                {
                    _loc_23 = _loc_3[_loc_2]["output"];
                    _loc_24 = _loc_3[_loc_2]["leavings"];
                    _loc_25 = "";
                    _loc_26 = MData.getInstance().mainData;
                    if (!_loc_26.me)
                    {
                        _loc_28 = _loc_7["thief"];
                        _loc_29 = _loc_26.host["uId"];
                        if (_loc_28.hasOwnProperty(_loc_29))
                        {
                            _loc_25 = Language.L["-已偷过"];
                        }// end if
                    }// end if
                    _loc_27 = {};
                    _loc_27["season"] = _loc_18;
                    _loc_27["units"] = _loc_23;
                    _loc_27["left"] = _loc_24;
                    _loc_22 = Language.replaceText("ripeText", _loc_27) + _loc_25;
                    farmInfo.setOutput(1, _loc_22, _loc_6);
                    farmInfo.setPest(0, 0, "");
                }
                else
                {
                    _loc_30 = {};
                    _loc_30["season"] = _loc_18;
                    _loc_30["timeText"] = _loc_17;
                    _loc_30["section"] = _loc_19;
                    _loc_22 = Language.replaceText("growText", _loc_30);
                    farmInfo.setInfo(_loc_10 / 100, _loc_11 + _loc_10 + "/100", _loc_12["progress"], _loc_22, _loc_6);
                    if (_loc_3[_loc_2]["pId"] == null || _loc_3[_loc_2]["pId"] == 0)
                    {
                        farmInfo.setPest(0, 0, "");
                    }
                    else
                    {
                        farmInfo.setPest(_loc_3[_loc_2]["nph"], _loc_3[_loc_2]["mph"], Language.L["青虫血量"]);
                    }// end else if
                }// end else if
            }// end else if
            return;
        }// end function

        private function jsonToArray(param1:Object) : Array
        {
            var _loc_3:String;
            var _loc_4:Object;
            var _loc_2:Array;
            for (_loc_3 in param1)
            {
                // label
                _loc_4 = new Object();
                _loc_4.time = _loc_3;
                _loc_4.action = param1[_loc_3];
                _loc_2.push(_loc_4);
            }// end of for ... in
            _loc_2.sort(sortArray);
            return _loc_2;
        }// end function

        override public function set mY(param1:Number) : void
        {
            super.mY = param1;
            y = param1 - 120;
            return;
        }// end function

        private function dataChange(param1:Event) : void
        {
            if (MData.getInstance().mainData.farmlandDataIndex == data)
            {
                data = data;
            }// end if
            return;
        }// end function

        override public function set mX(param1:Number) : void
        {
            super.mX = param1;
            x = param1 - 60;
            return;
        }// end function

    }
}
