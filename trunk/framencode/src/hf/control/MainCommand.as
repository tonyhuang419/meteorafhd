package hf.control
{
    import com.adobe.utils.*;
    import com.lipi.*;
    import com.minutes.global.*;
    import flash.events.*;
    import flash.utils.*;
    import hf.FBridge.*;
    import hf.model.*;
    import hf.view.common.*;
    import hf.view.farm.land.*;
    import hf.view.main.WindowControl.*;
    import hf.view.main.cursor.*;
    import hf.view.main.head.*;
    import hf.view.main.window.facebook.pay.*;

    public class MainCommand extends EventDispatcher
    {
        private var mainData:MainData;
        private var preTime:Number = 0;
        private var isDogFoodRequesting:Boolean = false;
        private var _itemType:String;
        private var setUserData:Object;
        private var farmlandTimer:Timer;
        private var _itemId:String;
        private var refreshRestrict:Object;
        private var getPotatoChipsRequestRet:Boolean = false;
        private var taskList:Object;
        private var fr:FRequest;
        private var preBuyToolType:int = 0;
        private var buyDiyData:Object;
        private var _tasking:Boolean = false;

        public function MainCommand()
        {
            refreshRestrict = {refresh:false, start:0};
            buyDiyData = {};
            taskList = {1:{name:"harvest", me:true}, 2:{name:"scarify", me:true}, 3:{name:"sale", me:true}, 4:{name:"planting", me:true}, 5:{name:"water", me:true}, 6:{name:"clearWeed", me:true}, 7:{name:"spraying", me:true}, 8:{name:"water", me:false}, 9:{name:"clearWeed", me:false}, 10:{name:"spraying", me:false}, 11:{name:"harvest", me:false}};
            fr = FRequest.getInstance();
            mainData = MData.getInstance().mainData;
            farmlandTimer = new Timer(1000);
            farmlandTimer.addEventListener(TimerEvent.TIMER, timerHandler);
            farmlandTimer.start();
            return;
        }// end function

        public function sendChat(param1:String, param2:String, param3:int, param4:String) : void
        {
            var _loc_5:Object;
            fr.postRequest("mod=chat&act=sendChat", _loc_5, sendChatFn);
            return;
        }// end function

        private function diyRenewFn(param1:Object) : void
        {
            var _loc_2:String;
            if (param1.hasOwnProperty("errorType"))
            {
                _loc_2 = param1["errorType"];
                if (_loc_2 == "IOError" || _loc_2 == "httpStatus" || _loc_2 == "timeOut" || _loc_2 == "PHPError")
                {
                    alertWindow("error", Language.L["请求超时，稍后再试"]);
                    return;
                }// end if
            }// end if
            if (param1["code"] == 1)
            {
                mainData.reloadUserItems = true;
                getUserItems();
                alertWindow("success", param1["direction"]);
                addMoney(param1);
                addFB(param1);
                addExp(param1);
                addLevelReward(param1);
            }
            else
            {
                alertWindow("error", "" + param1["direction"]);
            }// end else if
            return;
        }// end function

        private function reGetOutput() : void
        {
            var _loc_3:Object;
            var _loc_4:Object;
            var _loc_1:* = mainData.farmlandData;
            var _loc_2:int;
            while (_loc_2 < _loc_1.length)
            {
                // label
                _loc_3 = _loc_1[_loc_2];
                if (_loc_3["step"] == 5 && _loc_3["output"] == 0)
                {
                    _loc_4 = {ownerId:mainData.currentUserId, place:_loc_2};
                    fr.postRequest2("mod=farmlandstatus&act=getOutput", _loc_4, reGetOutputFn);
                }// end if
                _loc_2++;
            }// end while
            return;
        }// end function

        private function setTask(param1:Object) : void
        {
            if (param1 == null)
            {
                TaskData.getInstance().currentTask = {taskId:0, taskFlag:0};
            }
            else
            {
                TaskData.getInstance().currentTask = {taskId:param1["taskId"], taskFlag:param1["taskFlag"]};
            }// end else if
            return;
        }// end function

        private function getDiyInfoFn(param1:Object) : void
        {
            var _loc_3:String;
            if (param1.hasOwnProperty("errorType"))
            {
                _loc_3 = param1["errorType"];
                if (_loc_3 == "IOError" || _loc_3 == "httpStatus" || _loc_3 == "timeOut" || _loc_3 == "PHPError")
                {
                    mainData.shopDiyErr = Language.L["请求超时，"] + "<a href=\'event:reload\'><u><font color=\'#ff6600\'>" + Language.L["点击重试"] + "</font></u></a>";
                    return;
                }// end if
            }// end if
            mainData.shopDiyLoading = false;
            var _loc_2:* = new Array();
            mainData.diyInfo = _loc_2.concat(param1);
            return;
        }// end function

        public function buySeed(param1:int, param2:int) : void
        {
            var _loc_3:Object;
            fr.postRequest("mod=repertory&act=buySeed", _loc_3, buySeedFn);
            return;
        }// end function

        private function getUserCropFn(param1:Object) : void
        {
            var _loc_6:String;
            if (param1.hasOwnProperty("errorType"))
            {
                _loc_6 = param1["errorType"];
                if (_loc_6 == "IOError" || _loc_6 == "httpStatus" || _loc_6 == "timeOut" || _loc_6 == "PHPError")
                {
                    mainData.userCropErr = Language.L["请求超时，"] + "<a href=\'event:reload\'><u><font color=\'#ff6600\'>" + Language.L["点击重试"] + "</font></u></a>";
                    return;
                }// end if
            }// end if
            mainData.reloadUserCrop = false;
            mainData.userCropLoading = false;
            var _loc_2:* = new Array();
            _loc_2 = _loc_2.concat(param1);
            var _loc_3:* = INI.getInstance().data.crops.@sort;
            var _loc_4:* = _loc_3.split(",");
            var _loc_5:int;
            while (_loc_5 < _loc_2.length)
            {
                // label
                _loc_2[_loc_5]["sort"] = _loc_4.indexOf(String(_loc_2[_loc_5]["cId"]));
                _loc_5++;
            }// end while
            _loc_2 = _loc_2.sort(cropSort);
            mainData.userCrop = _loc_2;
            return;
        }// end function

        private function getPackageFn(param1:Object) : void
        {
            if (param1.hasOwnProperty("code"))
            {
            }
            else
            {
                mainData.giftPackage = param1;
            }// end else if
            return;
        }// end function

        public function addFB(param1:Object) : void
        {
            if (param1.hasOwnProperty("FB") && param1["FB"] != 0)
            {
                mainData.host["FB"] = int(mainData.host["FB"]) + int(param1["FB"]);
                mainData.addFB = param1["FB"];
            }// end if
            return;
        }// end function

        private function getOutputFn(param1:Object) : void
        {
            var _loc_2:int;
            var _loc_3:int;
            if (param1.hasOwnProperty("farmlandIndex"))
            {
                _loc_2 = param1["farmlandIndex"];
                _loc_3 = param1["status"]["harvestTimes"];
                param1["status"]["step"] = GetCropID.idState(param1["status"]["cId"], param1["status"]["plantTime"], param1["status"]["cropStatus"], _loc_3);
                param1["status"]["fertilize"] = param1["status"]["fertilize"] == param1["status"]["step"] + 1 ? (true) : (false);
                mainData.farmlandData[_loc_2] = param1["status"];
                mainData.farmlandDataIndex = _loc_2;
                reGetOutput();
            }// end if
            return;
        }// end function

        private function setCurrentUser(param1:Object) : void
        {
            mainData.host = param1;
            mainData.currentUser = LipiUtil.clone(param1);
            return;
        }// end function

        private function getTaskFn(param1:Object) : void
        {
            if (!param1.hasOwnProperty("code") && param1["code"] != 0)
            {
                TaskData.getInstance().currentTask = {taskId:param1["taskId"], taskFlag:param1["taskFlag"]};
            }// end if
            return;
        }// end function

        private function feedDogFn(param1:Object) : void
        {
            if (param1["code"] == 0)
            {
                WControl.openForName("AlertWindow", {type:"error", text:param1["direction"]});
            }
            else
            {
                mainData.dogFeed = param1;
                addMoney(param1);
                WControl.openForName("AlertWindow", {type:"success", text:param1["direction"]});
            }// end else if
            return;
        }// end function

        private function getUserItemsFn(param1:Object) : void
        {
            var _loc_3:String;
            if (param1.hasOwnProperty("errorType"))
            {
                _loc_3 = param1["errorType"];
                if (_loc_3 == "IOError" || _loc_3 == "httpStatus" || _loc_3 == "timeOut" || _loc_3 == "PHPError")
                {
                    mainData.userItemErr = Language.L["请求超时，"] + "<a href=\'event:reload\'><u><font color=\'#ff6600\'>" + Language.L["点击重试"] + "</font></u></a>";
                    return;
                }// end if
            }// end if
            mainData.reloadUserItems = false;
            mainData.userItemLoading = false;
            var _loc_2:* = new Array();
            _loc_2 = _loc_2.concat(param1);
            mainData.userItems = _loc_2;
            return;
        }// end function

        public function getPackage() : void
        {
            fr.postRequest("mod=Feast&act=getPackageList", null, getPackageFn);
            return;
        }// end function

        private function dogFoodFn(param1:Object) : void
        {
            mainData.dogFood = param1;
            isDogFoodRequesting = false;
            return;
        }// end function

        public function getDiyInfo() : void
        {
            if (mainData.diyInfo != null)
            {
                return;
            }// end if
            mainData.shopDiyLoading = true;
            fr.postRequest2("mod=item&act=shop", null, getDiyInfoFn);
            return;
        }// end function

        public function addReward(param1:Array) : void
        {
            var _loc_2:int;
            while (_loc_2 < param1.length)
            {
                // label
                switch(int(param1[_loc_2]["eType"]))
                {
                    case 7:
                    {
                        addExp({exp:param1[_loc_2]["eNum"]});
                        break;
                    }// end case
                    case 6:
                    {
                        addMoney({money:param1[_loc_2]["eNum"]});
                        break;
                    }// end case
                    case 5:
                    {
                        break;
                    }// end case
                    case 4:
                    {
                        break;
                    }// end case
                    case 3:
                    {
                        MData.getInstance().farmData.reloadUserSeed = true;
                        break;
                    }// end case
                    case 2:
                    {
                        mainData.reloadUserItems = true;
                        break;
                    }// end case
                    case 1:
                    {
                        MData.getInstance().farmData.reloadUserSeed = true;
                        break;
                    }// end case
                    default:
                    {
                        break;
                        break;
                    }// end default
                }// end switch
                _loc_2++;
            }// end while
            return;
        }// end function

        public function welcomeEnd() : void
        {
            fr.postRequest("mod=user&act=welcome", null, welcomeEndFn);
            return;
        }// end function

        private function setWeather(param1:Object) : void
        {
            if (param1 == null)
            {
                return;
            }// end if
            if (new Date().getHours() > 6 && new Date().getHours() < 20)
            {
                if (param1["weatherId"] == 1)
                {
                    mainData.weather = Weather.SUNNY;
                }
                else if (param1["weatherId"] == 3)
                {
                    mainData.weather = Weather.RAIN;
                }// end else if
            }
            else
            {
                mainData.weather = Weather.NIGHT;
            }// end else if
            return;
        }// end function

        public function taskComp(param1:String, param2:Object) : void
        {
            var _loc_3:int;
            if (TaskData.getInstance().currentTask["taskFlag"] == 1)
            {
                _loc_3 = TaskData.getInstance().currentTask["taskId"];
            }
            else
            {
                return;
            }// end else if
            var _loc_4:* = taskList[String(_loc_3)];
            if (taskList[String(_loc_3)]["name"] == param1 && mainData.me == _loc_4["me"])
            {
                if (param2.hasOwnProperty("code") && param2["code"] == 1)
                {
                    updateTask();
                }// end if
            }// end if
            return;
        }// end function

        public function getSeedInfo() : void
        {
            if (mainData.seedInfo != null)
            {
                return;
            }// end if
            mainData.shopSeedLoading = true;
            fr.postRequest2("mod=repertory&act=getSeedInfo", null, getSeedInfoFn);
            return;
        }// end function

        private function feedSelfDogFoodFn(param1:Object) : void
        {
            mainData.selfDogFood = param1;
            isDogFoodRequesting = false;
            return;
        }// end function

        public function activeItem(param1:int) : void
        {
            immediate(param1);
            var _loc_2:Object;
            fr.postRequest("mod=item&act=activeItem", _loc_2, activeItemFn);
            return;
        }// end function

        public function makePotatoChipsRequest(param1:int) : void
        {
            fr.postRequest("mod=Lays&act=machining", {num:param1}, makePotatoChipsResult);
            return;
        }// end function

        public function buyTool(param1:int, param2:int, param3:int, param4:Boolean = false, param5:String = "") : void
        {
            var _loc_6:Object;
            var _loc_7:String;
            var _loc_8:Object;
            if (param1 == 6)
            {
                param3 = 22;
            }
            else if (param1 == 5)
            {
            }
            else if (param1 == 501)
            {
                param3 = 23;
            }// end else if
            if (!param4 || Version.SNS != Version.QQ)
            {
                _loc_6 = {tId:param1, number:param2, type:param3};
                fr.postRequest("mod=usertool&act=buyTool", _loc_6, buyToolFn);
            }
            else
            {
                preBuyToolType = param3;
                _loc_7 = "";
                _loc_7 = INI.getInstance().data.postUrl.@fbRequest;
                _loc_8 = {};
                _loc_8["app_key"] = "happyfarm";
                _loc_8["payitem"] = param3 * 10000 + param1 + "-" + param2 + "-" + param5;
                _loc_8["payvalue"] = 0;
                _loc_8["paytype"] = 0;
                _loc_8["accttype"] = 3;
                _loc_8["dnatime"] = 0;
                _loc_8["dnakey"] = 0;
                _loc_8["format"] = "JSON";
                fr.postRequest(_loc_7, _loc_8, qqBuyToolFn);
            }// end else if
            return;
        }// end function

        private function buySeedFn(param1:Object) : void
        {
            var _loc_2:Array;
            var _loc_3:Object;
            var _loc_4:String;
            var _loc_5:Boolean;
            var _loc_6:int;
            var _loc_7:String;
            if (param1["code"] == 1)
            {
                _loc_2 = MData.getInstance().farmData.userSeed;
                if (_loc_2 != null)
                {
                    _loc_5 = false;
                    _loc_6 = 0;
                    while (_loc_6 < _loc_2.length)
                    {
                        // label
                        if (_loc_2[_loc_6].hasOwnProperty("cId") && _loc_2[_loc_6]["cId"] == param1["cId"])
                        {
                            _loc_2[_loc_6]["amount"] = _loc_2[_loc_6]["amount"] + param1["num"];
                            _loc_5 = true;
                        }// end if
                        _loc_6++;
                    }// end while
                    if (true)
                    {
                        _loc_2.push({amount:param1["num"], cId:param1["cId"], cName:param1["cName"], type:1});
                    }// end if
                    MData.getInstance().farmData.userSeed = _loc_2;
                }// end if
                addMoney(param1);
                _loc_3 = {money:Math.abs(param1["money"]), num:param1["num"], cName:param1["cName"]};
                _loc_4 = Language.replaceText("buySeedFnText", _loc_3);
                alertWindow("success", _loc_4);
            }
            else
            {
                if (param1.hasOwnProperty("direction") && StringUtil.trim(param1["direction"]) != "")
                {
                    alertWindow("error", param1["direction"]);
                }// end if
            }// end else if
            if (param1.hasOwnProperty("errorType"))
            {
                _loc_7 = param1["errorType"];
                if (_loc_7 == "IOError" || _loc_7 == "httpStatus" || _loc_7 == "timeOut" || _loc_7 == "PHPError")
                {
                    alertWindow("error", Language.L["请求超时，稍后再试"]);
                }// end if
            }// end if
            return;
        }// end function

        private function saleAllFn(param1:Object) : void
        {
            var _loc_2:String;
            if (param1.hasOwnProperty("errorType"))
            {
                _loc_2 = param1["errorType"];
                if (_loc_2 == "IOError" || _loc_2 == "httpStatus" || _loc_2 == "timeOut" || _loc_2 == "PHPError")
                {
                    alertWindow("error", Language.L["请求超时，稍后再试"]);
                    return;
                }// end if
            }// end if
            if (param1["code"] - 0 == 1)
            {
                mainData.reloadUserCrop = true;
                alertWindow("success", Language.replaceText("saleAllFnText", {money:param1["money"]}));
                taskComp("sale", param1);
                addMoney(param1);
                getUserCrop();
            }
            else
            {
                if (param1.hasOwnProperty("direction") && param1["direction"] != "")
                {
                    alertWindow("error", param1["direction"]);
                }// end if
            }// end else if
            return;
        }// end function

        private function setDogData(param1:Object) : void
        {
            if (param1 != null)
            {
                mainData.dogData = param1;
            }
            else
            {
                mainData.dogData = {dogId:"0", dogUnWorkTime:0, isHungry:0};
            }// end else if
            return;
        }// end function

        private function timerHandler(param1:TimerEvent) : void
        {
            var _loc_4:Object;
            var _loc_5:int;
            var _loc_2:* = mainData.farmlandData;
            if (_loc_2 == null)
            {
                return;
            }// end if
            var _loc_3:int;
            while (_loc_3 < _loc_2.length)
            {
                // label
                _loc_4 = _loc_2[_loc_3];
                _loc_5 = GetCropID.idState(_loc_4["cId"], _loc_4["plantTime"], _loc_4["cropStatus"], _loc_4["harvestTimes"]);
                if (_loc_5 != _loc_4["step"])
                {
                    if (_loc_5 == 5)
                    {
                        getOutput(mainData.currentUserId, _loc_3);
                    }// end if
                    _loc_4["step"] = _loc_5;
                    _loc_4["fertilize"] = false;
                    mainData.farmlandDataIndex = _loc_3;
                }// end if
                _loc_3++;
            }// end while
            return;
        }// end function

        public function buyDiy(param1:int, param2:Boolean = false, param3:int = 0, param4:String = "") : void
        {
            var _loc_6:String;
            var _loc_7:Object;
            var _loc_5:Object;
            buyDiyData["exp"] = param3;
            if (param2)
            {
                _loc_5["useFB"] = true;
            }// end if
            if (!param2 || Version.SNS != Version.QQ)
            {
                fr.postRequest("mod=item&act=buy", _loc_5, buyDiyFn);
            }
            else
            {
                _loc_6 = "";
                _loc_6 = INI.getInstance().data.postUrl.@fbRequest;
                _loc_7 = {};
                _loc_7["app_key"] = "happyfarm";
                _loc_7["payitem"] = 20000 + param1 + "-1-" + param4;
                _loc_7["payvalue"] = 0;
                _loc_7["paytype"] = 0;
                _loc_7["accttype"] = 3;
                _loc_7["dnatime"] = 0;
                _loc_7["dnakey"] = 0;
                _loc_7["format"] = "JSON";
                fr.postRequest(_loc_6, _loc_7, qqBuyDiyFn);
            }// end else if
            return;
        }// end function

        public function sale(param1:int, param2:int) : void
        {
            var _loc_3:Object;
            fr.postRequest("mod=repertory&act=sale", _loc_3, saleFn);
            return;
        }// end function

        public function userReload() : void
        {
            if (setUserData != null)
            {
                setUser(setUserData);
            }// end if
            return;
        }// end function

        public function setUser(param1:Object = null) : void
        {
            var _loc_2:Object;
            if (param1 == null || param1["me"] == true)
            {
                if (mainData.me != true)
                {
                    Cursor.setCursor("CursorArrow");
                    Cursor.useSystem();
                }// end if
                _loc_2 = mainData.host;
                setUserData = _loc_2;
                setUserData["me"] = true;
                getCropStatus();
            }
            else
            {
                setUserData = param1;
                getCropStatus(param1["userId"]);
                Cursor.setCursor("CursorArrow");
                Cursor.useSystem();
            }// end else if
            return;
        }// end function

        private function cropSort(param1:Object, param2:Object) : int
        {
            if (int(param1["sort"]) < int(param2["sort"]))
            {
                return -1;
            }// end if
            return 1;
        }// end function

        private function getChipsNumResult(param1:Object) : void
        {
            getPotatoChipsRequestRet = false;
            mainData.hasPotatoChips = param1["sNum"];
            mainData.potatoNumber = param1["tNum"];
            mainData.openWinEventDispatch();
            return;
        }// end function

        public function clearChat() : void
        {
            fr.postRequest("mod=chat&act=clearChat", null, clearChatFn);
            return;
        }// end function

        private function updateTaskFn(param1:Object) : void
        {
            var _loc_2:Object;
            _tasking = false;
            if (!param1.hasOwnProperty("code") && param1["code"] != 0)
            {
                _loc_2 = param1["task"];
                if (_loc_2["taskId"] == 0 && _loc_2["taskFlag"] == 0)
                {
                    param1["taskId"] = 0;
                }// end if
                TaskData.getInstance().updata = param1;
                TaskData.getInstance().currentTask = {taskId:_loc_2["taskId"], taskFlag:_loc_2["taskFlag"]};
                addLevelReward(param1);
                addReward(param1["item"]);
            }// end if
            return;
        }// end function

        private function levelUp(param1:int) : void
        {
            var _loc_2:* = mainData.host["exp"];
            var _loc_3:* = Math.sqrt((_loc_2 + 25) / 100) - 0.5;
            var _loc_4:* = Math.sqrt((_loc_2 + param1 + 25) / 100) - 0.5;
            if (Math.sqrt((_loc_2 + param1 + 25) / 100) - 0.5 > _loc_3)
            {
                fr.postRequest("mod=feast&act=levelup", {level:_loc_4}, levelUpFn);
            }// end if
            return;
        }// end function

        public function getFriendList(param1:Boolean = false) : void
        {
            var _loc_2:Array;
            if (param1)
            {
                if (refreshRestrict["refresh"])
                {
                    if (getTimer() - refreshRestrict["start"] < 60000)
                    {
                        return;
                    }// end if
                    refreshRestrict["start"] = getTimer();
                }
                else
                {
                    refreshRestrict["refresh"] = true;
                    refreshRestrict["start"] = getTimer();
                }// end if
            }// end else if
            mainData.getFriendListLoading = true;
            if (param1)
            {
                fr.postRequest2("mod=friend", {refresh:param1}, getFriendListAndSeverFn);
                return;
            }// end if
            if (mainData.friendList != null)
            {
                getFriendListFn(mainData.friendList);
                return;
            }// end if
            if (Version.SNS == Version.QQ)
            {
                fr.postRequest2("mod=friend", {refresh:true}, getFriendListAndSeverFn);
            }
            else
            {
                _loc_2 = LocalData.getInstance().getObject(Version.value + mainData.host["uId"]) as Array;
                if (_loc_2 != null && _loc_2.length > 0)
                {
                    getFriendListFn(_loc_2);
                    return;
                }// end if
                fr.postRequest2("mod=friend", {refresh:false}, getFriendListAndSeverFn);
            }// end else if
            return;
        }// end function

        private function friendSort(param1:Object, param2:Object) : Number
        {
            var _loc_3:* = mainData.sortField;
            if (int(param1[_loc_3]) > int(param2[_loc_3]))
            {
                return -1;
            }// end if
            return 1;
        }// end function

        private function setDogDizzyOrNo(param1:Object) : void
        {
            var _loc_2:* = new Object();
            if (param1.hasOwnProperty("dog"))
            {
                if (param1["dog"]["dogUnWorkTime"] - mainData.serverTime > 0)
                {
                    _loc_2["dogUnWorkTime"] = param1["dog"]["dogUnWorkTime"];
                    _loc_2["requestTime"] = mainData.serverTime;
                    mainData.dogDizzyState = _loc_2;
                }
                else
                {
                    _loc_2["dogUnWorkTime"] = 0;
                    _loc_2["requestTime"] = 0;
                    mainData.dogDizzyState = _loc_2;
                }// end if
            }// end else if
            return;
        }// end function

        public function diyRenew(param1:int) : void
        {
            var _loc_2:Object;
            fr.postRequest("mod=item&act=renew", _loc_2, diyRenewFn);
            return;
        }// end function

        private function getAllInfoFn(param1:Object) : void
        {
            var _loc_2:String;
            if (param1.hasOwnProperty("errorType"))
            {
                _loc_2 = param1["errorType"];
                if (_loc_2 == "IOError" || _loc_2 == "httpStatus" || _loc_2 == "timeOut" || _loc_2 == "PHPError")
                {
                    mainData.profileErr = Language.L["请求超时，"] + "<a href=\'event:reload\'><u><font color=\'#ff6600\'>" + Language.L["点击重试"] + "</font></u></a>";
                    return;
                }// end if
            }// end if
            mainData.profileLoading = false;
            mainData.profile = param1;
            return;
        }// end function

        private function welcome(param1:Object) : void
        {
            if (param1 != null)
            {
                mainData.welcome = Boolean(param1);
            }// end if
            return;
        }// end function

        private function qqBuyToolFn(param1:Object) : void
        {
            var _loc_2:Object;
            if (param1["code"] == 0)
            {
                _loc_2["code"] = 1;
                _loc_2["direction"] = "购买成功";
                _loc_2["type"] = preBuyToolType;
                MData.getInstance().farmData.reloadUserSeed = true;
            }
            else
            {
                _loc_2["code"] = 0;
                _loc_2["direction"] = param1["msg"];
            }// end else if
            buyToolFn(_loc_2);
            return;
        }// end function

        private function levelUpFn(param1:Object) : void
        {
            addLevelReward({levelUp:param1});
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

        public function searchFriend(param1:String = "") : void
        {
            if (param1 != "")
            {
                mainData.searchFriendValue = param1;
                if (mainData.showFriendPage > mainData.showFriendSum)
                {
                    mainData.showFriendPage = 1;
                    pageHandler();
                    return;
                }// end if
            }// end if
            mainData.searchFriendValue = param1;
            pageHandler();
            return;
        }// end function

        private function saleFn(param1:Object) : void
        {
            var _loc_3:String;
            var _loc_2:String;
            if (param1["code"] == 1)
            {
                mainData.reloadUserCrop = true;
                getUserCrop();
                addMoney(param1);
                _loc_2 = "success";
                taskComp("sale", param1);
            }
            else
            {
                _loc_2 = "error";
            }// end else if
            if (param1.hasOwnProperty("direction") && StringUtil.trim(param1["direction"]) != "")
            {
                alertWindow("success", param1["direction"]);
            }// end if
            if (param1.hasOwnProperty("errorType"))
            {
                _loc_3 = param1["errorType"];
                if (_loc_3 == "IOError" || _loc_3 == "httpStatus" || _loc_3 == "timeOut" || _loc_3 == "PHPError")
                {
                    alertWindow("error", Language.L["请求超时，稍后再试"]);
                }// end if
            }// end if
            return;
        }// end function

        public function getUserCrop() : void
        {
            if (mainData.reloadUserCrop)
            {
                mainData.userCropLoading = true;
                fr.postRequest2("mod=repertory&act=getUserCrop", null, getUserCropFn);
            }
            else
            {
                getUserCropFn(mainData.userCrop);
            }// end else if
            return;
        }// end function

        public function addLevelReward(param1:Object) : void
        {
            if (param1.hasOwnProperty("levelUp") && param1["levelUp"] != null && param1["levelUp"] != false)
            {
                mainData.levelReward = param1["levelUp"];
                addReward(param1["levelUp"]["item"]);
                if (param1["levelUp"].hasOwnProperty("vipItem") && param1["levelUp"]["vipItem"] != false)
                {
                    addReward(param1["levelUp"]["vipItem"]);
                }// end if
            }// end if
            return;
        }// end function

        public function getToolsInfo() : void
        {
            if (mainData.toolsInfo != null)
            {
                return;
            }// end if
            mainData.shopToolLoading = true;
            fr.postRequest2("mod=usertool&act=getTools", null, getToolsInfoFn);
            return;
        }// end function

        private function setItems(param1:Object) : void
        {
            mainData.items = param1;
            return;
        }// end function

        public function run() : void
        {
            fr.postRequest2("mod=user&act=run", null, runFn);
            return;
        }// end function

        private function getCropStatusAllFn(param1:Object) : void
        {
            var _loc_2:String;
            var _loc_3:Object;
            if (param1.hasOwnProperty("errorType"))
            {
                _loc_2 = param1["errorType"];
                if (_loc_2 == "IOError" || _loc_2 == "httpStatus" || _loc_2 == "timeOut" || _loc_2 == "PHPError")
                {
                    alertWindow("error", Language.L["请求超时，稍后再试"]);
                }// end if
                mainData.userChanging = false;
                return;
            }// end if
            mainData.userChanging = false;
            if (setUserData != null)
            {
                _loc_3 = mainData.currentUser;
                _loc_3["exp"] = param1["exp"];
                _loc_3["headPic"] = setUserData["headPic"];
                _loc_3["uId"] = setUserData.hasOwnProperty("uId") ? (setUserData["uId"]) : (setUserData["userId"]);
                _loc_3["userName"] = setUserData["userName"];
                _loc_3["money"] = setUserData["money"];
                _loc_3["yellowlevel"] = setUserData["yellowlevel"];
                _loc_3["yellowstatus"] = setUserData["yellowstatus"];
                mainData.currentUser = _loc_3;
            }// end if
            setFarmlandData(param1["farmlandStatus"]);
            setItems(param1["items"]);
            unread(param1["a"], param1["b"], param1["c"], param1["d"]);
            setDogData(param1["dog"]);
            setPotatoMachine(param1);
            setDogDizzyOrNo(param1);
            return;
        }// end function

        public function sortBy(param1:String) : void
        {
            mainData.sortField = param1;
            getFriendListFn(mainData.friendList);
            return;
        }// end function

        private function buyDiyFn(param1:Object) : void
        {
            var _loc_2:String;
            if (param1["code"] == 1)
            {
                mainData.reloadUserItems = true;
                alertWindow("success", Language.L["购买装饰成功"]);
                addMoney(param1);
                addFB(param1);
                addExp(param1);
                addLevelReward(param1);
            }
            else
            {
                if (param1.hasOwnProperty("direction") && StringUtil.trim(param1["direction"]) != "")
                {
                    alertWindow("error", param1["direction"]);
                }// end if
            }// end else if
            if (param1.hasOwnProperty("errorType"))
            {
                _loc_2 = param1["errorType"];
                if (_loc_2 == "IOError" || _loc_2 == "httpStatus" || _loc_2 == "timeOut" || _loc_2 == "PHPError")
                {
                    alertWindow("error", Language.L["请求超时，稍后再试"]);
                }// end if
            }// end if
            return;
        }// end function

        private function getToolsInfoFn(param1:Object) : void
        {
            var _loc_3:String;
            if (param1.hasOwnProperty("errorType"))
            {
                _loc_3 = param1["errorType"];
                if (_loc_3 == "IOError" || _loc_3 == "httpStatus" || _loc_3 == "timeOut" || _loc_3 == "PHPError")
                {
                    mainData.shopToolErr = Language.L["请求超时，"] + "<a href=\'event:reload\'><u><font color=\'#ff6600\'>" + Language.L["点击重试"] + "</font></u></a>";
                    return;
                }// end if
            }// end if
            mainData.shopToolLoading = false;
            var _loc_2:* = new Array();
            mainData.toolsInfo = _loc_2.concat(param1);
            return;
        }// end function

        public function getCropStatus(param1:String = "0") : void
        {
            mainData.userChanging = true;
            fr.postRequest2("mod=user&act=run&flag=1", {ownerId:param1}, getCropStatusAllFn);
            return;
        }// end function

        public function getPotatoChipsRequest() : void
        {
            if (getPotatoChipsRequestRet)
            {
                return;
            }// end if
            getPotatoChipsRequestRet = true;
            fr.postRequest("mod=Lays&act=run", {}, getChipsNumResult);
            return;
        }// end function

        public function buyToolFn(param1:Object) : void
        {
            var _loc_2:String;
            if (param1["code"] == 1)
            {
                MData.getInstance().farmData.reloadUserSeed = true;
                addMoney(param1);
                addFB(param1);
                alertWindow("success", param1["direction"]);
                if (param1["type"] == 4)
                {
                    setUser();
                    mainData.hasDog = true;
                    mainData.toolsInfo = mainData.toolsInfo;
                }// end if
                if (param1["type"] == 1000)
                {
                    mainData.potatoMachine = true;
                }// end if
                if (param1["tId"] == 501)
                {
                    mainData.dogData["isHungry"] = 0;
                    mainData.dogData = mainData.dogData;
                }// end if
            }
            else
            {
                if (param1.hasOwnProperty("direction") && StringUtil.trim(param1["direction"]) != "")
                {
                    alertWindow("error", param1["direction"]);
                }// end if
            }// end else if
            if (param1.hasOwnProperty("errorType"))
            {
                _loc_2 = param1["errorType"];
                if (_loc_2 == "IOError" || _loc_2 == "httpStatus" || _loc_2 == "timeOut" || _loc_2 == "PHPError")
                {
                    alertWindow("error", Language.L["请求超时，稍后再试"]);
                }// end if
            }// end if
            return;
        }// end function

        public function getPackageEnd() : void
        {
            fr.postRequest("mod=Feast&act=getPackage", null, getPackageEndFn);
            return;
        }// end function

        public function addMoney(param1:Object) : void
        {
            if (param1.hasOwnProperty("money") && param1["money"] != 0)
            {
                mainData.host["money"] = int(mainData.host["money"]) + int(param1["money"]);
                mainData.addMoney = param1["money"];
            }// end if
            return;
        }// end function

        private function makePotatoChipsResult(param1:Object) : void
        {
            if (param1["code"] == 0)
            {
                WControl.openForName("AlertWindow", {type:"error", text:param1["direction"]});
            }
            else
            {
                mainData.hasPotatoChips = mainData.hasPotatoChips + (param1["sNum"] - 0);
                mainData.potatoChips = param1["direction"];
                mainData.reloadUserCrop = true;
                if (param1.hasOwnProperty("encourage"))
                {
                    mainData.chipsEventDispatch(param1["encourage"]);
                    mainData.reloadUserItems = true;
                }// end if
            }// end else if
            return;
        }// end function

        private function immediate(param1:int) : void
        {
            var _loc_4:int;
            var _loc_5:int;
            var _loc_6:int;
            var _loc_2:* = param1;
            var _loc_3:* = MData.getInstance().mainData.userItems;
            _loc_6 = 0;
            while (_loc_6 < _loc_3.length)
            {
                // label
                if (_loc_3[_loc_6]["id"] == _loc_2)
                {
                    _loc_4 = _loc_3[_loc_6]["itemType"];
                    _loc_5 = _loc_3[_loc_6]["itemId"];
                    break;
                }// end if
                _loc_6++;
            }// end while
            _loc_6 = 0;
            while (_loc_6 < _loc_3.length)
            {
                // label
                if (_loc_3[_loc_6]["itemType"] == _loc_4)
                {
                    if (_loc_3[_loc_6]["id"] == _loc_2)
                    {
                        _loc_3[_loc_6]["status"] = 1;
                    }
                    else
                    {
                        _loc_3[_loc_6]["status"] = 0;
                    }// end if
                }// end else if
                _loc_6++;
            }// end while
            MData.getInstance().mainData.userItems = _loc_3;
            var _loc_7:* = MData.getInstance().mainData.items;
            if (!MData.getInstance().mainData.items.hasOwnProperty(String(_loc_4)))
            {
                _loc_7[String(_loc_4)] = new Object();
            }// end if
            _loc_7[String(_loc_4)]["itemId"] = _loc_5;
            MData.getInstance().mainData.items = _loc_7;
            return;
        }// end function

        private function setServerTime(param1:Object) : void
        {
            if (param1 != null)
            {
                mainData.serverTime = param1["time"];
            }// end if
            return;
        }// end function

        private function runFn(param1:Object) : void
        {
            var _loc_2:String;
            if (param1.hasOwnProperty("errorType"))
            {
                _loc_2 = INI.getInstance().data.version.@loginurl;
                mainData.runError = Language.L["初始化错误，"] + "<a href=\'" + _loc_2 + "\' target=\'_top\'><u><font color=\'#ff6600\'>" + Language.L["点击重试"] + "</font></u></a>";
            }
            else
            {
                mainData.runComp = true;
                setServerTime(param1["serverTime"]);
                setCurrentUser(param1["user"]);
                setWeather(param1["weather"]);
                setFarmlandData(param1["farmlandStatus"]);
                setItems(param1["items"]);
                setTask(param1["task"]);
                unread(param1["a"], param1["b"], param1["c"], param1["d"]);
                welcome(param1["welcome"]);
                setDogData(param1["dog"]);
                setPotatoMachine(param1);
                setDogDizzyOrNo(param1);
                checkedParameters();
            }// end else if
            return;
        }// end function

        private function unread(param1:int, param2:int, param3:int, param4:int) : void
        {
            var _loc_5:* = new Object();
            new Object()["a"] = param1;
            _loc_5["b"] = param2;
            _loc_5["c"] = param3;
            _loc_5["d"] = param4;
            mainData.unreadData = _loc_5;
            return;
        }// end function

        public function getAllInfo(param1:Boolean = false) : void
        {
            var _loc_2:Object;
            mainData.profileLoading = true;
            if (!param1)
            {
                _loc_2 = {uId:MData.getInstance().mainData.currentUserId};
            }
            else
            {
                _loc_2 = {uId:MData.getInstance().mainData.host["uId"]};
            }// end else if
            fr.postRequest2("mod=chat&act=getAllInfo", _loc_2, getAllInfoFn);
            return;
        }// end function

        public function dogFood() : void
        {
            if (isDogFoodRequesting)
            {
                return;
            }// end if
            isDogFoodRequesting = true;
            fr.postRequest2("mod=dog&act=feedMoney", {}, dogFoodFn);
            return;
        }// end function

        private function pageHandler() : void
        {
            var _loc_1:* = mainData.friendPageNum;
            var _loc_2:* = mainData.showFriendPage-- * _loc_1;
            var _loc_3:* = mainData.showFriendPage * _loc_1;
            if (_loc_3 > mainData.friendList.length)
            {
                _loc_3 = mainData.friendList.length;
            }// end if
            if (mainData.searchFriendValue != "")
            {
                mainData.showFriendList = mainData.filterFriendList.slice(_loc_2, _loc_3);
            }
            else
            {
                mainData.showFriendList = mainData.friendList.slice(_loc_2, _loc_3);
            }// end else if
            return;
        }// end function

        public function feedDog() : void
        {
            fr.postRequest("mod=dog&act=feedDog", {}, feedDogFn);
            return;
        }// end function

        private function getFriendListFn(param1:Object) : void
        {
            mainData.getFriendListLoading = false;
            var _loc_2:* = new Array();
            _loc_2 = _loc_2.concat(param1);
            _loc_2.sort(friendSort);
            var _loc_3:int;
            while (_loc_3 < _loc_2.length)
            {
                // label
                _loc_2[_loc_3]["sort"] = _loc_3 + 1;
                if (_loc_2[_loc_3]["userId"] == mainData.host["uId"])
                {
                    _loc_2[_loc_3]["me"] = true;
                    _loc_2[_loc_3]["exp"] = mainData.host["exp"];
                    _loc_2[_loc_3]["money"] = mainData.host["money"];
                }
                else
                {
                    _loc_2[_loc_3]["me"] = false;
                }// end else if
                _loc_3++;
            }// end while
            mainData.friendList = _loc_2;
            pageHandler();
            return;
        }// end function

        public function addExp(param1:Object) : void
        {
            if (param1.hasOwnProperty("exp") && param1["exp"] != 0)
            {
                mainData.host["exp"] = int(mainData.host["exp"]) + int(param1["exp"]);
                mainData.addExp = param1["exp"];
            }// end if
            return;
        }// end function

        public function clearLog() : void
        {
            fr.postRequest("mod=chat&act=clearLog", null, clearLogFn);
            return;
        }// end function

        public function preview(param1:Object) : void
        {
            var _loc_2:Object;
            if (param1 != null)
            {
                _loc_2 = mainData.items;
                _itemType = param1["itemType"];
                if (_loc_2.hasOwnProperty(_itemType))
                {
                    _itemId = _loc_2[_itemType]["itemId"];
                }
                else
                {
                    _itemId = "";
                    _loc_2[_itemType] = new Object();
                }// end else if
                _loc_2[_itemType]["itemId"] = param1["itemId"];
                mainData.items = _loc_2;
            }
            else
            {
                _loc_2 = mainData.items;
                if (_itemId != "")
                {
                    _loc_2[_itemType]["itemId"] = _itemId;
                }
                else
                {
                    delete _loc_2[_itemType];
                }// end else if
                mainData.items = _loc_2;
            }// end else if
            return;
        }// end function

        public function selfDogFood() : void
        {
            if (isDogFoodRequesting)
            {
                return;
            }// end if
            isDogFoodRequesting = true;
            fr.postRequest2("mod=dog&act=feedMoney", {}, feedSelfDogFoodFn);
            return;
        }// end function

        private function welcomeEndFn(param1:Object) : void
        {
            trace("新手引导结束");
            return;
        }// end function

        private function getPackageEndFn(param1:Object) : void
        {
            if (param1.hasOwnProperty("code"))
            {
            }
            else
            {
                addReward(param1["item"]);
                if (param1.hasOwnProperty("vipItem") && param1["vipItem"] != false)
                {
                    addReward(param1["vipItem"]);
                }// end if
            }// end else if
            return;
        }// end function

        private function getFriendListAndSeverFn(param1:Object) : void
        {
            var _loc_4:String;
            refreshRestrict["refresh"] = false;
            if (param1.hasOwnProperty("errorType"))
            {
                _loc_4 = param1["errorType"];
                if (_loc_4 == "IOError" || _loc_4 == "httpStatus" || _loc_4 == "timeOut" || _loc_4 == "PHPError")
                {
                    mainData.getFriendListErr = Language.L["请求超时，"] + "<a href=\'event:reload\'><u><font color=\'#ff6600\'>" + Language.L["点击重试"] + "</font></u></a>";
                    return;
                }// end if
            }// end if
            mainData.getFriendListLoading = false;
            getFriendListFn(param1);
            var _loc_2:* = new Date();
            var _loc_3:* = _loc_2.getTime() + 86400000;
            if (Version.SNS == Version.QQ)
            {
            }
            else
            {
                LocalData.getInstance().setObject(Version.value + mainData.host["uId"], param1, _loc_3);
            }// end else if
            return;
        }// end function

        public function getNotice(param1:Function) : void
        {
            fr.postRequest2("mod=user&act=getNotice", null, param1);
            return;
        }// end function

        private function qqBuyDiyFn(param1:Object) : void
        {
            var _loc_2:Object;
            if (param1["code"] == 0)
            {
                if (buyDiyData.hasOwnProperty("exp") && buyDiyData["exp"] != 0)
                {
                    levelUp(buyDiyData["exp"]);
                    addExp(buyDiyData);
                    mainData.reloadUserItems = true;
                    alertWindow("success", Language.L["购买装饰成功"]);
                }// end if
            }
            else
            {
                _loc_2["code"] = 0;
                _loc_2["direction"] = param1["msg"];
            }// end else if
            buyDiyFn(_loc_2);
            return;
        }// end function

        public function saleAll() : void
        {
            fr.postRequest("mod=repertory&act=saleAll", null, saleAllFn);
            return;
        }// end function

        public function friendBackPage(param1:String = "") : void
        {
            if (param1 != "")
            {
                mainData.searchFriendValue = param1;
                if (mainData.showFriendPage-- > mainData.showFriendSum)
                {
                    mainData.showFriendPage = 1;
                    pageHandler();
                    return;
                }// end if
            }// end if
            if (mainData.showFriendPage-- > 0)
            {
                mainData.searchFriendValue = param1;
                mainData.showFriendPage--;
                pageHandler();
            }// end if
            return;
        }// end function

        private function getSeedInfoFn(param1:Object) : void
        {
            var _loc_2:String;
            var _loc_3:Array;
            if (param1.hasOwnProperty("errorType"))
            {
                _loc_2 = param1["errorType"];
                if (_loc_2 == "IOError" || _loc_2 == "httpStatus" || _loc_2 == "timeOut" || _loc_2 == "PHPError")
                {
                    mainData.shopSeedErr = Language.L["请求超时，"] + "<a href=\'event:reload\'><u><font color=\'#ff6600\'>" + Language.L["点击重试"] + "</font></u></a>";
                    return;
                }// end if
            }
            else
            {
                mainData.shopSeedLoading = false;
                _loc_3 = new Array();
                mainData.seedInfo = _loc_3.concat(param1);
            }// end else if
            return;
        }// end function

        private function setPotatoMachine(param1:Object) : void
        {
            if (param1.hasOwnProperty("m") && param1["m"] == 1)
            {
                mainData.potatoMachine = true;
            }
            else
            {
                mainData.potatoMachine = false;
            }// end else if
            return;
        }// end function

        private function clearLogFn(param1:Object) : void
        {
            getAllInfo(true);
            return;
        }// end function

        public function friendNextPage(param1:String = "") : void
        {
            mainData.searchFriendValue = param1;
            if (mainData.showFriendPage + 1 <= mainData.showFriendSum)
            {
                mainData.showFriendPage = mainData.showFriendPage + 1;
            }
            else
            {
                mainData.showFriendPage = 1;
            }// end else if
            pageHandler();
            return;
        }// end function

        private function activeItemFn(param1:Object) : void
        {
            if (param1["code"] - 0 == 1)
            {
            }
            else
            {
                alertWindow("error", Language.L["设置装饰失败"]);
            }// end else if
            return;
        }// end function

        private function sendChatFn(param1:Object) : void
        {
            var _loc_2:* = mainData.profile;
            if (_loc_2 != null)
            {
                _loc_2["chat"] = param1["chat"];
            }// end if
            mainData.profile = _loc_2;
            return;
        }// end function

        private function reGetOutputFn(param1:Object) : void
        {
            var _loc_2:int;
            var _loc_3:int;
            if (param1.hasOwnProperty("farmlandIndex"))
            {
                _loc_2 = param1["farmlandIndex"];
                _loc_3 = param1["status"]["harvestTimes"];
                param1["status"]["step"] = GetCropID.idState(param1["status"]["cId"], param1["status"]["plantTime"], param1["status"]["cropStatus"], _loc_3);
                param1["status"]["fertilize"] = param1["status"]["fertilize"] == param1["status"]["step"] + 1 ? (true) : (false);
                mainData.farmlandData[_loc_2] = param1["status"];
                mainData.farmlandDataIndex = _loc_2;
            }// end if
            return;
        }// end function

        private function setFarmlandData(param1:Object) : void
        {
            var _loc_4:Object;
            var _loc_6:Object;
            var _loc_2:* = new Array();
            _loc_2 = _loc_2.concat(param1);
            var _loc_3:* = new Array();
            var _loc_5:int;
            while (_loc_5 < _loc_2.length)
            {
                // label
                _loc_4 = new Object();
                _loc_4["cId"] = _loc_2[_loc_5]["a"];
                _loc_4["cropStatus"] = _loc_2[_loc_5]["b"];
                _loc_4["oldweed"] = _loc_2[_loc_5]["c"];
                _loc_4["oldpest"] = _loc_2[_loc_5]["d"];
                _loc_4["oldhumidity"] = _loc_2[_loc_5]["e"];
                _loc_4["weed"] = _loc_2[_loc_5]["f"];
                _loc_4["pest"] = _loc_2[_loc_5]["g"];
                _loc_4["humidity"] = _loc_2[_loc_5]["h"];
                _loc_4["health"] = _loc_2[_loc_5]["i"];
                _loc_4["harvestTimes"] = _loc_2[_loc_5]["j"];
                _loc_4["output"] = _loc_2[_loc_5]["k"];
                _loc_4["min"] = _loc_2[_loc_5]["l"];
                _loc_4["leavings"] = _loc_2[_loc_5]["m"];
                _loc_4["thief"] = _loc_2[_loc_5]["n"];
                _loc_6 = {};
                _loc_6[String(int(new Date().getTime() / 1000) - 60 * 5)] = [7];
                _loc_6[String(int(new Date().getTime() / 1000) - 60 * 3)] = [10];
                _loc_4["action"] = _loc_2[_loc_5]["p"];
                _loc_4["plantTime"] = _loc_2[_loc_5]["q"];
                _loc_4["step"] = GetCropID.idState(_loc_2[_loc_5]["a"], _loc_2[_loc_5]["q"], _loc_2[_loc_5]["b"], _loc_4["harvestTimes"]);
                _loc_4["fertilize"] = _loc_2[_loc_5]["o"] == _loc_4["step"] + 1 ? (true) : (false);
                _loc_4["updateTime"] = _loc_2[_loc_5]["r"] - 60 * 60;
                _loc_4["waste"] = false;
                _loc_4["pId"] = _loc_2[_loc_5]["s"];
                _loc_4["nph"] = _loc_2[_loc_5]["t"];
                _loc_4["mph"] = _loc_2[_loc_5]["u"];
                _loc_3.push(_loc_4);
                _loc_5++;
            }// end while
            mainData.farmlandData = _loc_3;
            reGetOutput();
            return;
        }// end function

        public function updateTask() : void
        {
            if (_tasking)
            {
                return;
            }// end if
            _tasking = true;
            fr.postRequest("mod=task&act=update", null, updateTaskFn);
            return;
        }// end function

        private function addRewardTool(param1:Object) : void
        {
            var _loc_3:Boolean;
            var _loc_4:int;
            var _loc_2:* = MData.getInstance().farmData;
            if (_loc_2.userSeed != null)
            {
                _loc_3 = false;
                _loc_4 = 0;
                while (_loc_4 < _loc_2.userSeed.length)
                {
                    // label
                    if (_loc_2.userSeed["tId"] == param1["eParam"])
                    {
                        _loc_2.userSeed[_loc_4]["amount"] = _loc_2.userSeed[_loc_4]["amount"] + param1["eNum"];
                        _loc_3 = true;
                    }// end if
                    _loc_4++;
                }// end while
                if (true)
                {
                    _loc_2.userSeed.push({amount:param1["eNum"], tId:param1["eParam"], tName:"", type:2});
                }// end if
            }// end if
            return;
        }// end function

        private function checkedParameters() : void
        {
            var _loc_2:PaypalNodeWin;
            var _loc_1:* = MData.getInstance().parametersData.parameters;
            if (_loc_1 != null)
            {
                if (_loc_1.hasOwnProperty("pay") && _loc_1["pay"] == "1")
                {
                    _loc_2 = new PaypalNodeWin();
                    WControl.open(_loc_2);
                }// end if
                if (_loc_1.hasOwnProperty("friend") && _loc_1["friend"] != "")
                {
                    getCropStatus(_loc_1["friend"]);
                }// end if
            }// end if
            return;
        }// end function

        private function clearChatFn(param1:Object) : void
        {
            var _loc_2:Object;
            if (param1["code"] == 1)
            {
                _loc_2 = mainData.profile;
                if (_loc_2 != null)
                {
                    _loc_2["chat"] = [];
                }// end if
                mainData.profile = _loc_2;
            }// end if
            return;
        }// end function

        public function getOutput(param1:String, param2:int) : void
        {
            var _loc_3:Object;
            fr.postRequest2("mod=farmlandstatus&act=getOutput", _loc_3, getOutputFn);
            return;
        }// end function

        public function getTask() : void
        {
            fr.postRequest("mod=task&act=run", null, getTaskFn);
            return;
        }// end function

        public function getUserItems() : void
        {
            if (mainData.reloadUserItems)
            {
                mainData.userItemLoading = true;
                fr.postRequest2("mod=item&act=getUserItems", null, getUserItemsFn);
            }
            else
            {
                getUserItemsFn(mainData.userItems);
            }// end else if
            return;
        }// end function

    }
}
