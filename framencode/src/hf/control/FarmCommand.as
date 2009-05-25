package hf.control
{
    import com.adobe.utils.*;
    import flash.events.*;
    import flash.utils.*;
    import hf.FBridge.*;
    import hf.model.*;
    import hf.view.common.*;
    import hf.view.farm.land.*;
    import hf.view.main.WindowControl.*;
    import hf.view.main.cursor.*;

    public class FarmCommand extends EventDispatcher
    {
        private var mainData:MainData;
        private var farmData:FarmData;
        private var fr:FRequest;
        private var hasBigInsetID:int = 0;
        private var requestTime:Object;
        private var _clearWeedNum:int = 0;
        private var _sprayingNum:int = 0;

        public function FarmCommand()
        {
            requestTime = {};
            fr = FRequest.getInstance();
            farmData = MData.getInstance().farmData;
            mainData = MData.getInstance().mainData;
            return;
        }// end function

        private function getUserSeedFn(param1:Object) : void
        {
            var _loc_6:String;
            if (param1.hasOwnProperty("errorType"))
            {
                _loc_6 = param1["errorType"];
                if (_loc_6 == "IOError" || _loc_6 == "httpStatus" || _loc_6 == "timeOut" || _loc_6 == "PHPError")
                {
                    farmData.userSeedErr = Language.L["请求超时，"] + "<a href=\'event:reload\'><u><font color=\'#ff6600\'>" + Language.L["点击重试"] + "</font></u></a>";
                }// end if
                return;
            }// end if
            var _loc_2:* = new Array();
            _loc_2 = _loc_2.concat(param1);
            var _loc_3:* = INI.getInstance().data.crops.@sort;
            var _loc_4:* = _loc_3.split(",");
            var _loc_5:int;
            while (_loc_5 < _loc_2.length)
            {
                // label
                if (_loc_2[_loc_5].hasOwnProperty("cId"))
                {
                    _loc_2[_loc_5]["sort"] = _loc_4.indexOf(String(_loc_2[_loc_5]["cId"]));
                }
                else
                {
                    _loc_2[_loc_5]["sort"] = 99;
                }// end else if
                _loc_5++;
            }// end while
            _loc_2 = _loc_2.sort(cropSort);
            farmData.userSeed = _loc_2;
            farmData.userSeedLoading = false;
            farmData.reloadUserSeed = false;
            return;
        }// end function

        private function clearWeedFn(param1:Object) : void
        {
            farmChange(param1, "weed", 6);
            floatHandler(param1);
            addExp(param1);
            addMoney(param1);
            Command.getInstance().mainCommand.addLevelReward(param1);
            Command.getInstance().mainCommand.taskComp("clearWeed", param1);
            if (param1["code"] == 1)
            {
                farmData.addHealth = {index:param1["farmlandIndex"]};
            }// end if
            RequestControlLib.getInstance().farmReqSuccess("除草", param1);
            return;
        }// end function

        private function alertWindow(param1:String, param2:String) : void
        {
            var _loc_3:WindowEvent;
            if (StringUtil.trim(param2) != "")
            {
                _loc_3 = new WindowEvent(WindowEvent.OPEN);
                _loc_3.windowName = "AlertWindow";
                _loc_3.windowArgument = {type:param1, text:param2};
                ViewControl.getInstance().dispatchEvent(_loc_3);
            }// end if
            return;
        }// end function

        private function addFailed(param1:Object) : void
        {
            if (param1.hasOwnProperty("poptype") && param1["poptype"] == 3)
            {
                if (param1.hasOwnProperty("direction"))
                {
                    alertWindow("bite", param1["direction"]);
                }// end if
            }// end if
            return;
        }// end function

        private function beatDogRt(param1:Object) : void
        {
            if (param1["code"] == 0)
            {
                addFailed(param1);
                mainData.dogDizzyState = param1;
                return;
            }// end if
            mainData.dogDizzyState = param1;
            parkThingsChanged("tId", 5);
            return;
        }// end function

        private function scarifyFn(param1:Object) : void
        {
            var _loc_2:int;
            var _loc_3:Object;
            if (param1["code"] == 1)
            {
                if (param1.hasOwnProperty("farmlandIndex"))
                {
                    _loc_2 = param1["farmlandIndex"];
                    _loc_3 = mainData.farmlandData[_loc_2];
                    _loc_3["cId"] = 0;
                    _loc_3["weed"] = 0;
                    _loc_3["pest"] = 0;
                    _loc_3["pId"] = 0;
                    _loc_3["mph"] = 0;
                    _loc_3["nph"] = 0;
                    _loc_3["humidity"] = 1;
                    _loc_3["cropStatus"] = 0;
                    _loc_3["fertilize"] = false;
                    _loc_3["step"] = 0;
                    mainData.farmlandDataIndex = _loc_2;
                }// end if
            }// end if
            floatHandler(param1);
            addExp(param1);
            addMoney(param1);
            Command.getInstance().mainCommand.addLevelReward(param1);
            Command.getInstance().mainCommand.taskComp("scarify", param1);
            RequestControlLib.getInstance().farmReqSuccess("翻土", param1);
            return;
        }// end function

        public function scatterWeed(param1:int) : void
        {
            var _loc_3:String;
            var _loc_4:Object;
            if (!canHandler(param1))
            {
                return;
            }// end if
            var _loc_2:* = mainData.farmlandData[param1];
            if (_loc_2["weed"] >= 3)
            {
                farmData.farmOperate = {text:Language.L["这块地无法种草啦！"], index:param1, me:mainData.me};
            }
            else if (_loc_2["step"] == 5 || _loc_2["step"] == 6)
            {
                farmData.farmOperate = {text:Language.L["作物这个阶段不会长草！"], index:param1, me:mainData.me};
            }
            else
            {
                if (RequestControlLib.getInstance().regFarmReqCount("放草", param1, 3, _loc_2["weed"]))
                {
                    return;
                }// end if
                _loc_3 = mainData.currentUserId;
                _loc_4 = {ownerId:_loc_3, place:param1};
                fr.postRequest("mod=farmlandstatus&act=scatterSeed", _loc_4, scatterWeedFn);
            }// end else if
            return;
        }// end function

        private function farmChange(param1:Object, param2:String, param3:int = 0) : void
        {
            var _loc_4:int;
            var _loc_5:Array;
            var _loc_6:String;
            var _loc_7:Array;
            var _loc_8:String;
            var _loc_9:Array;
            if (param1.hasOwnProperty(param2))
            {
                _loc_5 = mainData.farmlandData;
                _loc_4 = param1["farmlandIndex"];
                if (_loc_5[_loc_4][param2] != param1[param2] || param1["pId"] != 0)
                {
                    if (param1["code"] == 1)
                    {
                        if (param3 != 0)
                        {
                            if (param3 == 6)
                            {
                                if (param1["pId"] > _loc_5[_loc_4]["pId"])
                                {
                                    param3 = 7;
                                }
                                else if (param1["pId"] < _loc_5[_loc_4]["pId"])
                                {
                                    param3 = 10;
                                }// end if
                            }// end else if
                            if (_loc_5[_loc_4]["action"] is Array)
                            {
                                _loc_5[_loc_4]["action"] = {};
                            }// end if
                            _loc_6 = String(mainData.serverTime);
                            _loc_7 = [];
                            _loc_7.push(param3);
                            _loc_5[_loc_4]["action"][_loc_6] = _loc_7;
                        }// end if
                    }// end if
                    if (param1.hasOwnProperty("pId"))
                    {
                        _loc_5[_loc_4]["pId"] = param1["pId"];
                        _loc_5[_loc_4]["mph"] = param1["mph"];
                        _loc_5[_loc_4]["nph"] = param1["nph"];
                    }// end if
                    if (param1.hasOwnProperty("humidity"))
                    {
                        _loc_5[_loc_4]["humidity"] = param1["humidity"];
                    }// end if
                    if (param1.hasOwnProperty("weed"))
                    {
                        _loc_5[_loc_4]["weed"] = param1["weed"];
                    }// end if
                    if (param1.hasOwnProperty("pest"))
                    {
                        _loc_5[_loc_4]["pest"] = param1["pest"];
                    }// end if
                    mainData.farmlandDataIndex = _loc_4;
                }
                else if (param1["pId"] == 0 && _loc_5[_loc_4][param2] == param1[param2])
                {
                    if (param1["code"] == 1)
                    {
                        if (param3 != 0)
                        {
                            if (param3 == 6)
                            {
                                if (param1["pId"] > _loc_5[_loc_4]["pId"])
                                {
                                    param3 = 7;
                                }
                                else if (param1["pId"] < _loc_5[_loc_4]["pId"])
                                {
                                    param3 = 10;
                                }// end if
                            }// end else if
                            if (_loc_5[_loc_4]["action"] is Array)
                            {
                                _loc_5[_loc_4]["action"] = {};
                            }// end if
                            _loc_8 = String(mainData.serverTime);
                            _loc_9 = [];
                            _loc_9.push(param3);
                            _loc_5[_loc_4]["action"][_loc_8] = _loc_9;
                        }// end if
                    }// end if
                    _loc_5[_loc_4]["pId"] = 0;
                    _loc_5[_loc_4]["mph"] = 0;
                    _loc_5[_loc_4]["nph"] = 0;
                    mainData.farmlandDataIndex = _loc_4;
                }// end if
            }// end else if
            return;
        }// end function

        private function plantingFn(param1:Object) : void
        {
            var _loc_2:int;
            var _loc_3:Object;
            if (param1["code"] == 1)
            {
                if (param1.hasOwnProperty("farmlandIndex"))
                {
                    _loc_2 = param1["farmlandIndex"];
                    _loc_3 = mainData.farmlandData[_loc_2];
                    _loc_3["cId"] = param1["cId"];
                    _loc_3["step"] = 0;
                    _loc_3["plantTime"] = mainData.serverTime;
                    _loc_3["harvestTimes"] = 0;
                    mainData.farmlandDataIndex = _loc_2;
                }// end if
                parkThingsChanged("cId", param1["cId"]);
            }// end if
            floatHandler(param1);
            addExp(param1);
            addMoney(param1);
            Command.getInstance().mainCommand.addLevelReward(param1);
            Command.getInstance().mainCommand.taskComp("planting", param1);
            RequestControlLib.getInstance().farmReqSuccess("种植", param1);
            return;
        }// end function

        private function harvestFn(param1:Object) : void
        {
            var _loc_2:int;
            var _loc_3:Number;
            var _loc_4:int;
            if (param1["code"] == 1)
            {
                if (param1.hasOwnProperty("status"))
                {
                    _loc_2 = param1["farmlandIndex"];
                    _loc_3 = param1["status"]["plantTime"];
                    _loc_4 = param1["status"]["harvestTimes"];
                    param1["status"]["step"] = GetCropID.idState(param1["status"]["cId"], _loc_3, param1["status"]["cropStatus"], _loc_4);
                    param1["status"]["fertilize"] = param1["status"]["fertilize"] == param1["status"]["step"] + 1 ? (true) : (false);
                    mainData.farmlandData[_loc_2] = param1["status"];
                    mainData.farmlandDataIndex = _loc_2;
                    if (param1["harvest"] > 0)
                    {
                        farmData.harvestData = {text:param1["harvest"], index:param1["farmlandIndex"], cId:param1["status"]["cId"]};
                    }// end if
                }// end if
                mainData.reloadUserCrop = true;
            }// end if
            floatHandler(param1);
            addExp(param1);
            addMoney(param1);
            addFailed(param1);
            Command.getInstance().mainCommand.addLevelReward(param1);
            Command.getInstance().mainCommand.taskComp("harvest", param1);
            RequestControlLib.getInstance().farmReqSuccess("收获", param1);
            return;
        }// end function

        public function spraying(param1:int, param2:int = 0) : void
        {
            var _loc_6:int;
            if (!canHandler(param1))
            {
                return;
            }// end if
            var _loc_3:* = mainData.farmlandData[param1];
            hasBigInsetID = _loc_3["pId"];
            var _loc_4:* = mainData.currentUserId;
            var _loc_5:Object;
            if (_loc_3["pest"] == 0 && _loc_3["pId"] == 0)
            {
                farmData.farmOperate = {text:Language.L["这块地不需要除虫啦！"], index:param1, me:mainData.me};
            }
            else
            {
                _loc_6 = 4 - _loc_3["pest"];
                if (_loc_3["pId"] != 0)
                {
                }// end if
                if (_loc_3["pest"] == 0 || param2 != 0)
                {
                    if (RequestControlLib.getInstance().regFarmReqCount("除虫", param1, 4, _loc_6--, _loc_4 + "_" + param2, 3600000))
                    {
                        farmData.farmOperate = {text:Language.L["这个道具每小时只能用一次。"], index:param1, me:mainData.me};
                        return;
                    }// end if
                }
                else if (RequestControlLib.getInstance().regFarmReqCount("除虫", param1, 4, _loc_6))
                {
                    return;
                }// end else if
                fr.postRequest("mod=farmlandstatus&act=spraying", _loc_5, sprayingFn);
            }// end else if
            return;
        }// end function

        public function water(param1:int) : void
        {
            var _loc_3:String;
            var _loc_4:String;
            if (!canHandler(param1))
            {
                return;
            }// end if
            var _loc_2:* = mainData.farmlandData[param1];
            if (_loc_2["humidity"] == 1)
            {
                farmData.farmOperate = {text:Language.L["这块地不需要浇水啦！"], index:param1, me:mainData.me};
            }
            else
            {
                if (RequestControlLib.getInstance().regFarmReqCount("浇水", param1, 1))
                {
                    return;
                }// end if
                _loc_3 = mainData.currentUserId;
                _loc_4 = "ownerId=" + _loc_3 + "&place=" + param1;
                fr.postRequest("mod=farmlandstatus&act=water", _loc_4, waterFn);
            }// end else if
            return;
        }// end function

        public function getUserSeed(param1:Boolean = false) : void
        {
            var _loc_2:* = farmData.reloadUserSeed;
            if (MData.getInstance().farmData.userSeed == null || param1 || _loc_2)
            {
                fr.postRequest2("mod=repertory&act=getUserSeed", null, getUserSeedFn);
                farmData.userSeedLoading = true;
            }// end if
            if (!_loc_2)
            {
                farmData.userSeed = farmData.userSeed;
            }// end if
            return;
        }// end function

        public function clearWeed(param1:int) : void
        {
            var _loc_3:int;
            var _loc_4:String;
            var _loc_5:Object;
            if (!canHandler(param1))
            {
                return;
            }// end if
            var _loc_2:* = mainData.farmlandData[param1];
            if (_loc_2["weed"] == 0)
            {
                farmData.farmOperate = {text:Language.L["这块地不需要除草啦！"], index:param1, me:mainData.me};
            }
            else
            {
                _loc_3 = 3 - _loc_2["weed"];
                if (RequestControlLib.getInstance().regFarmReqCount("除草", param1, 3, _loc_3))
                {
                    return;
                }// end if
                _loc_4 = mainData.currentUserId;
                _loc_5 = {ownerId:_loc_4, place:param1};
                fr.postRequest("mod=farmlandstatus&act=clearWeed", _loc_5, clearWeedFn);
            }// end else if
            return;
        }// end function

        public function scarify(param1:int) : void
        {
            var farmlandItemData:Object;
            var cw:ConfirmWindow;
            var place:* = param1;
            if (!canHandler(place))
            {
                return;
            }// end if
            if (mainData.me)
            {
                farmlandItemData = mainData.farmlandData[place];
                if (farmlandItemData["step"] == 6 || farmlandItemData["step"] == 7)
                {
                    executeScarify(place);
                }
                else
                {
                    cw = new ConfirmWindow();
                    cw.title = Language.L["铲除作物"];
                    cw.data = {text:Language.L["作物还没有收获，真的要铲除吗？"]};
                    cw.confirmFn = 
function () : void
{
    executeScarify(place);
    return;
}// end function
;
                    WControl.open(cw);
                }// end if
            }// end else if
            return;
        }// end function

        public function pest(param1:int) : void
        {
            var _loc_4:String;
            var _loc_5:Object;
            if (!canHandler(param1))
            {
                return;
            }// end if
            var _loc_2:* = mainData.farmlandData[param1];
            var _loc_3:* = _loc_2["step"];
            if (_loc_2["pest"] >= 3)
            {
                farmData.farmOperate = {text:Language.L["这块地无法放虫啦！"], index:param1, me:mainData.me};
            }
            else if (_loc_3 != 3 && _loc_3 != 4)
            {
                farmData.farmOperate = {text:Language.L["作物这个阶段不会生虫！"], index:param1, me:mainData.me};
            }
            else
            {
                if (RequestControlLib.getInstance().regFarmReqCount("放虫", param1, 3, _loc_2["pest"]))
                {
                    return;
                }// end if
                _loc_4 = mainData.currentUserId;
                _loc_5 = {ownerId:_loc_4, place:param1};
                fr.postRequest("mod=farmlandstatus&act=pest", _loc_5, pestFn);
            }// end else if
            return;
        }// end function

        public function planting(param1:int, param2:int) : void
        {
            var _loc_3:Object;
            var _loc_4:String;
            var _loc_5:Object;
            if (mainData.me)
            {
                _loc_3 = mainData.farmlandData[param1];
                if (_loc_3["cId"] != 0)
                {
                    farmData.farmOperate = {text:Language.L["不可以种在这里哦！"], index:param1, me:mainData.me};
                }
                else
                {
                    if (RequestControlLib.getInstance().regFarmReqCount("种植", param1, 1))
                    {
                        return;
                    }// end if
                    _loc_4 = mainData.currentUserId;
                    _loc_5 = {ownerId:_loc_4, place:param1, cId:param2};
                    fr.postRequest("mod=farmlandstatus&act=planting", _loc_5, plantingFn);
                }// end if
            }// end else if
            return;
        }// end function

        private function fertilizeFn(param1:Object) : void
        {
            var _loc_2:int;
            var _loc_3:int;
            if (param1["code"] == 1)
            {
                if (param1.hasOwnProperty("farmlandIndex"))
                {
                    _loc_2 = param1["farmlandIndex"];
                    _loc_3 = param1["status"]["harvestTimes"];
                    param1["status"]["step"] = GetCropID.idState(param1["status"]["cId"], param1["status"]["plantTime"], param1["status"]["cropStatus"], _loc_3);
                    param1["status"]["fertilize"] = param1["status"]["fertilize"] == param1["status"]["step"] + 1 ? (true) : (false);
                    mainData.farmlandData[_loc_2] = param1["status"];
                    mainData.farmlandDataIndex = _loc_2;
                }// end if
                parkThingsChanged("tId", param1["tId"]);
            }// end if
            floatHandler(param1);
            addExp(param1);
            addMoney(param1);
            RequestControlLib.getInstance().farmReqSuccess("施肥", param1);
            return;
        }// end function

        private function reclaimFn(param1:Object) : void
        {
            if (param1["code"] == 1)
            {
                addMoney(param1);
                Command.getInstance().mainCommand.getCropStatus();
            }
            else
            {
                alertWindow("error", param1["direction"]);
            }// end else if
            return;
        }// end function

        private function canHandler(param1:int) : Boolean
        {
            var _loc_2:* = mainData.farmlandData[param1];
            if (_loc_2["cId"] == 0)
            {
                farmData.farmOperate = {text:Language.L["不可以对空地进行这个操作啦！"], index:param1, me:mainData.me};
                return false;
            }// end if
            return true;
        }// end function

        private function cropSort(param1:Object, param2:Object) : int
        {
            if (int(param1["sort"]) < int(param2["sort"]))
            {
                return -1;
            }// end if
            return 1;
        }// end function

        private function parkThingsChanged(param1:String, param2:int) : void
        {
            var _loc_3:* = MData.getInstance().farmData.userSeed;
            var _loc_4:int;
            while (_loc_4 < _loc_3.length)
            {
                // label
                if (_loc_3[_loc_4].hasOwnProperty(param1))
                {
                    if (_loc_3[_loc_4][param1] == param2)
                    {
                        _loc_3[_loc_4]["amount"]--;
                        if (_loc_3[_loc_4]["amount"] <= 0)
                        {
                            _loc_3.splice(_loc_4, 1);
                            Cursor.setCursor("CursorArrow");
                        }// end if
                        break;
                    }// end if
                }// end if
                _loc_4++;
            }// end while
            MData.getInstance().farmData.userSeed = _loc_3;
            return;
        }// end function

        private function executeScarify(param1:int) : void
        {
            if (RequestControlLib.getInstance().regFarmReqCount("翻土", param1, 1))
            {
                return;
            }// end if
            var _loc_2:* = mainData.currentUserId;
            var _loc_3:Object;
            fr.postRequest("mod=farmlandstatus&act=scarify", _loc_3, scarifyFn);
            return;
        }// end function

        public function harvest(param1:int) : void
        {
            var _loc_3:String;
            var _loc_4:Object;
            var _loc_5:Object;
            var _loc_6:Object;
            var _loc_7:String;
            if (!canHandler(param1))
            {
                return;
            }// end if
            var _loc_2:* = mainData.farmlandData[param1];
            if (mainData.me)
            {
                if (_loc_2["step"] != 5)
                {
                    farmData.farmOperate = {text:Language.L["这块地没东西可收获！"], index:param1, me:mainData.me};
                }
                else
                {
                    if (RequestControlLib.getInstance().regFarmReqCount("收获", param1, 1))
                    {
                        return;
                    }// end if
                    _loc_3 = mainData.currentUserId;
                    _loc_4 = {ownerId:_loc_3, place:param1};
                    fr.postRequest("mod=farmlandstatus&act=harvest", _loc_4, harvestFn);
                }// end else if
            }
            else if (_loc_2["step"] != 5)
            {
                farmData.farmOperate = {text:Language.L["这块地没东西可偷！"], index:param1, me:mainData.me};
            }
            else
            {
                _loc_5 = mainData.farmlandData[param1];
                _loc_6 = _loc_5["thief"];
                _loc_7 = mainData.host["uId"];
                if (_loc_6.hasOwnProperty(_loc_7))
                {
                    if (_loc_6[_loc_7] == 0)
                    {
                        farmData.farmOperate = {text:Language.L["狗盯上了你，别做坏事了。"], index:param1, me:mainData.me};
                    }
                    else
                    {
                        farmData.farmOperate = {text:Language.L["做人不能贪得无厌！"], index:param1, me:mainData.me};
                    }// end else if
                }
                else if (_loc_5["leavings"] - 0 != 0 && _loc_5["leavings"] <= _loc_5["min"])
                {
                    farmData.farmOperate = {text:Language.L["行行好吧，我所剩无几了！"], index:param1, me:mainData.me};
                }
                else
                {
                    if (RequestControlLib.getInstance().regFarmReqCount("收获", param1, 1))
                    {
                        return;
                    }// end if
                    _loc_3 = mainData.currentUserId;
                    _loc_4 = {ownerId:_loc_3, place:param1};
                    fr.postRequest("mod=farmlandstatus&act=scrounge", _loc_4, harvestFn);
                }// end else if
            }// end else if
            return;
        }// end function

        public function reclaimPay(param1:Function) : void
        {
            fr.postRequest2("mod=user&act=reclaimPay", null, param1);
            return;
        }// end function

        public function beatDog(param1:String) : void
        {
            var _loc_2:Object;
            fr.postRequest("mod=Dog&act=unWorkDog", _loc_2, beatDogRt);
            return;
        }// end function

        private function addMoney(param1:Object) : void
        {
            if (param1.hasOwnProperty("money") && param1["money"] != 0)
            {
                mainData.host["money"] = int(mainData.host["money"]) + int(param1["money"]);
                mainData.addMoney = param1["money"];
            }// end if
            return;
        }// end function

        public function reclaim() : void
        {
            fr.postRequest("mod=user&act=reclaim", null, reclaimFn);
            return;
        }// end function

        public function fertilize(param1:int, param2:int) : void
        {
            var _loc_3:Object;
            var _loc_4:String;
            var _loc_5:Object;
            if (!canHandler(param1))
            {
                return;
            }// end if
            if (mainData.me && param2 == 4)
            {
                farmData.farmOperate = {text:"友情化肥只能对好友的作物使用", index:param1, me:mainData.me};
                return;
            }// end if
            if (mainData.me || param2 == 4 || Version.value == Version.FACEBOOK)
            {
                _loc_3 = mainData.farmlandData[param1];
                if (_loc_3["step"] == 5 || _loc_3["step"] == 6 || _loc_3["step"] == 7)
                {
                    farmData.farmOperate = {text:Language.L["作物这个阶段不需要施肥"], index:param1, me:mainData.me};
                }
                else if (_loc_3["fertilize"] == true)
                {
                    farmData.farmOperate = {text:Language.L["施过肥了，下个阶段再施吧"], index:param1, me:mainData.me};
                }
                else
                {
                    if (RequestControlLib.getInstance().regFarmReqCount("施肥", param1, 1))
                    {
                        return;
                    }// end if
                    _loc_4 = mainData.currentUserId;
                    _loc_5 = {ownerId:_loc_4, tId:param2, place:param1};
                    fr.postRequest("mod=farmlandstatus&act=fertilize", _loc_5, fertilizeFn);
                }// end else if
            }// end else if
            return;
        }// end function

        private function scatterWeedFn(param1:Object) : void
        {
            farmChange(param1, "weed", 2);
            floatHandler(param1);
            addExp(param1);
            addMoney(param1);
            addFailed(param1);
            RequestControlLib.getInstance().farmReqSuccess("放草", param1);
            return;
        }// end function

        private function addExp(param1:Object) : void
        {
            if (param1.hasOwnProperty("exp") && param1["exp"] != 0)
            {
                mainData.host["exp"] = int(mainData.host["exp"]) + int(param1["exp"]);
                mainData.addExp = param1["exp"];
            }// end if
            return;
        }// end function

        private function requestNum(param1:String, param2:int) : Boolean
        {
            if (!requestTime.hasOwnProperty(param1))
            {
                requestTime["type"] = {num:0, time:getTimer()};
            }// end if
            if (param2 > 0 && requestTime["type"]["num"] > 5)
            {
                if (getTimer() - requestTime["type"]["time"] < 10000)
                {
                    requestTime["type"]["time"] = getTimer();
                    return false;
                }// end if
                requestTime["type"]["num"] = 0;
            }// end if
            requestTime["type"]["num"] = requestTime["type"]["num"] + param2;
            requestTime["type"]["time"] = getTimer();
            return true;
        }// end function

        private function floatHandler(param1:Object) : void
        {
            var _loc_2:int;
            if (param1.hasOwnProperty("direction") && param1["direction"] != "")
            {
                if (param1.hasOwnProperty("poptype") && param1["poptype"] == 1)
                {
                    _loc_2 = param1["farmlandIndex"];
                    farmData.farmOperate = {text:param1["direction"], index:_loc_2, me:mainData.me};
                }// end if
            }// end if
            return;
        }// end function

        private function sprayingFn(param1:Object) : void
        {
            if (param1.hasOwnProperty("code") && param1["code"] == 0)
            {
                farmData.farmOperate = {text:param1["direction"], index:param1["farmlandIndex"], me:mainData.me};
                farmChange(param1, "pest", 5);
                RequestControlLib.getInstance().farmReqSuccess("除虫", param1);
                return;
            }// end if
            if (param1.hasOwnProperty("exp") && param1["exp"] != 0 && hasBigInsetID != 0 && param1["pId"] == 0)
            {
                mainData.killBigInsectExp = param1["exp"];
                hasBigInsetID = 0;
                mainData.killBigInsect = true;
            }// end if
            if (param1.hasOwnProperty("tId") && param1["tId"] != 0)
            {
                parkThingsChanged("tId", param1["tId"]);
            }// end if
            farmChange(param1, "pest", 5);
            floatHandler(param1);
            addExp(param1);
            addMoney(param1);
            Command.getInstance().mainCommand.addLevelReward(param1);
            Command.getInstance().mainCommand.taskComp("spraying", param1);
            if (param1["code"] == 1)
            {
                farmData.addHealth = {index:param1["farmlandIndex"]};
            }// end if
            RequestControlLib.getInstance().farmReqSuccess("除虫", param1);
            return;
        }// end function

        private function pestFn(param1:Object) : void
        {
            farmChange(param1, "pest", 1);
            floatHandler(param1);
            addExp(param1);
            addMoney(param1);
            addFailed(param1);
            RequestControlLib.getInstance().farmReqSuccess("放虫", param1);
            return;
        }// end function

        private function waterFn(param1:Object) : void
        {
            farmChange(param1, "humidity", 4);
            floatHandler(param1);
            addExp(param1);
            addMoney(param1);
            Command.getInstance().mainCommand.addLevelReward(param1);
            Command.getInstance().mainCommand.taskComp("water", param1);
            if (param1["code"] == 1)
            {
                farmData.addHealth = {index:param1["farmlandIndex"]};
            }// end if
            RequestControlLib.getInstance().farmReqSuccess("浇水", param1);
            return;
        }// end function

    }
}
