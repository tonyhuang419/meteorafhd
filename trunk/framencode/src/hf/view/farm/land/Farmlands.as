package hf.view.farm.land
{
    import com.minutes.global.*;
    import flash.display.*;
    import flash.events.*;
    import flash.geom.*;
    import hf.control.*;
    import hf.model.*;
    import hf.view.*;
    import hf.view.common.*;
    import hf.view.farm.farmTip.*;
    import hf.view.main.WindowControl.*;
    import hf.view.main.cursor.*;
    import hf.view.main.floating.*;
    import hf.view.main.tip.*;
    import hf.view.main.window.popularize.*;

    public class Farmlands extends Sprite
    {
        private var farmData:FarmData;
        private var mainData:MainData;
        private var reclaiming:Boolean = false;

        public function Farmlands()
        {
            mainData = MData.getInstance().mainData;
            farmData = MData.getInstance().farmData;
            mainData.addEventListener(MainData.FARMLAND_DATA_CHANGE, farmlandDataChange, false, 0, true);
            mainData.addEventListener(MainData.FARMLAND_DATA_INDEX, farmlandDataIndex, false, 0, true);
            farmData.addEventListener(FarmData.FARM_OPERATE, farmOperate, false, 0, true);
            farmData.addEventListener(FarmData.HARVEST_DATA, harvestData, false, 0, true);
            farmData.addEventListener(FarmData.ADD_HEALTH, addHealth, false, 0, true);
            addEventListener(ViewEvent.RECLAIM_CLICK, reclaimClick);
            initFarmlands();
            cacheAsBitmap = true;
            return;
        }// end function

        private function initFarmlands() : void
        {
            var _loc_2:int;
            var _loc_3:int;
            var _loc_4:int;
            var _loc_11:FarmlandItem;
            var _loc_1:Number;
            var _loc_5:int;
            var _loc_6:int;
            var _loc_7:Number;
            var _loc_8:Number;
            var _loc_9:* = 50 + _loc_6-- * (_loc_7 / 2);
            var _loc_10:Number;
            _loc_4 = 0;
            while (_loc_4 < _loc_1)
            {
                // label
                _loc_2 = _loc_4 / _loc_6;
                _loc_3 = _loc_4 % _loc_6;
                _loc_11 = new FarmlandItem();
                _loc_11.addEventListener(MouseEvent.CLICK, farmLandClick);
                _loc_11.addEventListener(MouseEvent.ROLL_OVER, farmLandOver);
                _loc_11.addEventListener(MouseEvent.ROLL_OUT, farmLandOut);
                _loc_11.x = _loc_9 + _loc_2 * _loc_7 / 2 - _loc_3 * _loc_7 / 2;
                _loc_11.y = _loc_10 + _loc_2 * _loc_8 / 2 + _loc_3 * _loc_8 / 2;
                addChild(_loc_11);
                _loc_4++;
            }// end while
            setFarmland();
            return;
        }// end function

        private function addHealth(param1:Event) : void
        {
            var _loc_2:* = farmData.addHealth;
            var _loc_3:* = new AddHealthAnimation();
            var _loc_4:* = _loc_2["index"];
            var _loc_5:* = getChildAt(_loc_4).x + 25;
            var _loc_6:* = getChildAt(_loc_4).y + 10;
            var _loc_7:* = localToGlobal(new Point(_loc_5, _loc_6));
            _loc_3.x = _loc_7.x;
            _loc_3.y = _loc_7.y;
            Float.open(_loc_3);
            return;
        }// end function

        private function confirmReclaim() : void
        {
            Command.getInstance().farmCommand.reclaim();
            return;
        }// end function

        private function setFarmlandInIndex(param1:int) : void
        {
            var _loc_5:int;
            var _loc_6:int;
            var _loc_2:* = param1;
            var _loc_3:* = getChildAt(_loc_2) as FarmlandItem;
            var _loc_4:* = GetCropID.idToName(mainData.farmlandData[_loc_2]["cId"]);
            if (GetCropID.idToName(mainData.farmlandData[_loc_2]["cId"]) != "")
            {
                _loc_3.cropName = _loc_4;
                _loc_5 = mainData.farmlandData[_loc_2]["step"];
                if (mainData.farmlandData[_loc_2]["cropStatus"] >= 7)
                {
                    _loc_3.step = 7--;
                }
                else
                {
                    _loc_3.step = _loc_5;
                }// end else if
                if (_loc_3.step == 6 || _loc_3.step == 5)
                {
                    _loc_3.weed = 0;
                    _loc_3.insect = 0;
                    _loc_3.humidity = true;
                    _loc_3.fertilize = false;
                    _loc_3.bigInsect = "";
                }
                else
                {
                    _loc_3.weed = mainData.farmlandData[_loc_2]["weed"];
                    if (_loc_3.step != 3 && _loc_3.step != 4)
                    {
                        _loc_3.insect = 0;
                    }
                    else
                    {
                        _loc_3.insect = mainData.farmlandData[_loc_2]["pest"];
                    }// end else if
                    _loc_3.humidity = mainData.farmlandData[_loc_2]["humidity"];
                    _loc_3.fertilize = mainData.farmlandData[_loc_2]["fertilize"];
                    _loc_3.bigInsect = mainData.farmlandData[_loc_2]["pId"];
                }// end else if
                _loc_6 = 0;
                if (_loc_5 == 3)
                {
                    _loc_6 = 1;
                }
                else if (_loc_5 == 4)
                {
                    _loc_6 = 2;
                }// end else if
                if (_loc_6 != 0)
                {
                    _loc_3.insectPosition = GetCropID.getInsectPosition(mainData.farmlandData[_loc_2]["cId"], _loc_6);
                }// end if
            }
            else
            {
                _loc_3.clearAll();
            }// end else if
            return;
        }// end function

        private function farmLandOut(param1:MouseEvent) : void
        {
            var _loc_2:TipEvent;
            if (!(param1.currentTarget as FarmlandItem).waste)
            {
                _loc_2 = new TipEvent(TipEvent.TIP_HIDE);
                ViewControl.getInstance().dispatchEvent(_loc_2);
            }// end if
            return;
        }// end function

        private function farmLandClick(param1:MouseEvent) : void
        {
            var _loc_3:FarmCommand;
            if ((param1.currentTarget as FarmlandItem).waste == true)
            {
                return;
            }// end if
            if (Cursor.name == "")
            {
                return;
            }// end if
            var _loc_2:* = getChildIndex(param1.currentTarget as DisplayObject);
            _loc_3 = Command.getInstance().farmCommand;
            switch(Cursor.name)
            {
                case "CursorWater":
                {
                    _loc_3.water(_loc_2);
                    break;
                }// end case
                case "CursorHook":
                {
                    _loc_3.clearWeed(_loc_2);
                    break;
                }// end case
                case "CursorWeed":
                {
                    _loc_3.scatterWeed(_loc_2);
                    break;
                }// end case
                case "CursorInsect":
                {
                    _loc_3.pest(_loc_2);
                    break;
                }// end case
                case "CursorPesticide":
                {
                    _loc_3.spraying(_loc_2);
                    break;
                }// end case
                case "CursorBetterSpraying":
                {
                    _loc_3.spraying(_loc_2, 6);
                    break;
                }// end case
                case "CursorHand":
                {
                    _loc_3.harvest(_loc_2);
                    break;
                }// end case
                case "CursorHoe":
                {
                    _loc_3.scarify(_loc_2);
                    break;
                }// end case
                case "CursorCropSeed":
                {
                    _loc_3.planting(_loc_2, int(Cursor.cursorArgument));
                    break;
                }// end case
                case "CursorTool":
                {
                    if (Cursor.cursorArgument == "6")
                    {
                        _loc_3.spraying(_loc_2, 6);
                        break;
                    }// end if
                    _loc_3.fertilize(_loc_2, int(Cursor.cursorArgument));
                    break;
                }// end case
                case "CursorStick":
                {
                    break;
                }// end case
                default:
                {
                    break;
                    break;
                }// end default
            }// end switch
            return;
        }// end function

        private function setFarmland() : void
        {
            var _loc_3:FarmlandItem;
            var _loc_4:String;
            var _loc_5:int;
            var _loc_6:int;
            if (mainData.farmlandData.length == 0)
            {
                return;
            }// end if
            var _loc_1:* = mainData.farmlandData;
            openBigInsect();
            var _loc_2:int;
            while (_loc_2 < numChildren)
            {
                // label
                _loc_3 = getChildAt(_loc_2) as FarmlandItem;
                if (_loc_2 < _loc_1.length)
                {
                    _loc_3.waste = false;
                    _loc_4 = GetCropID.idToName(mainData.farmlandData[_loc_2]["cId"]);
                    if (_loc_4 != "")
                    {
                        _loc_3.cropName = _loc_4;
                        _loc_5 = mainData.farmlandData[_loc_2]["step"];
                        if (mainData.farmlandData[_loc_2]["cropStatus"] >= 7)
                        {
                            _loc_3.step = 7--;
                        }
                        else
                        {
                            _loc_3.step = _loc_5;
                        }// end else if
                        if (_loc_3.step == 6 || _loc_3.step == 5)
                        {
                            _loc_3.weed = 0;
                            _loc_3.insect = 0;
                            _loc_3.bigInsect = "";
                            _loc_3.humidity = true;
                            _loc_3.fertilize = false;
                        }
                        else
                        {
                            _loc_3.weed = mainData.farmlandData[_loc_2]["weed"];
                            if (_loc_3.step != 3 && _loc_3.step != 4)
                            {
                                _loc_3.insect = 0;
                            }
                            else
                            {
                                _loc_3.insect = mainData.farmlandData[_loc_2]["pest"];
                            }// end else if
                            _loc_3.humidity = mainData.farmlandData[_loc_2]["humidity"];
                            _loc_3.fertilize = mainData.farmlandData[_loc_2]["fertilize"];
                            _loc_3.bigInsect = mainData.farmlandData[_loc_2]["pId"];
                        }// end else if
                        _loc_6 = 0;
                        if (_loc_5 == 3)
                        {
                            _loc_6 = 1;
                        }
                        else if (_loc_5 == 4)
                        {
                            _loc_6 = 2;
                        }// end else if
                        if (_loc_6 != 0)
                        {
                            _loc_3.insectPosition = GetCropID.getInsectPosition(mainData.farmlandData[_loc_2]["cId"], _loc_6);
                        }// end if
                    }
                    else
                    {
                        _loc_3.clearAll();
                    }// end else if
                }
                else
                {
                    _loc_3.waste = true;
                    _loc_3.reclaim = false;
                    if (mainData.farmlandData.length == _loc_2)
                    {
                        if (mainData.me)
                        {
                            _loc_3.reclaim = true;
                        }// end if
                    }// end if
                }// end else if
                _loc_2++;
            }// end while
            return;
        }// end function

        private function farmLandOver(param1:MouseEvent) : void
        {
            var _loc_2:int;
            var _loc_3:TipEvent;
            if (!(param1.currentTarget as FarmlandItem).waste)
            {
                _loc_2 = getChildIndex(param1.currentTarget as DisplayObject);
                TipClassLib.register("FarmInfo", FarmInfo);
                _loc_3 = new TipEvent(TipEvent.TIP_SHOW);
                _loc_3.tipType = "FarmInfo";
                _loc_3.tipArgument = _loc_2;
                ViewControl.getInstance().dispatchEvent(_loc_3);
            }// end if
            return;
        }// end function

        private function farmlandDataIndex(param1:Event) : void
        {
            setFarmlandInIndex(mainData.farmlandDataIndex);
            return;
        }// end function

        private function reclaimPay(param1:Object) : void
        {
            reclaiming = false;
            var _loc_2:* = MData.getInstance().mainData.host;
            var _loc_3:* = Math.sqrt((_loc_2["exp"] + 25) / 100) - 0.5;
            var _loc_4:* = _loc_2["money"];
            var _loc_5:* = param1["level"];
            var _loc_6:* = param1["money"];
            var _loc_7:String;
            if (_loc_3 < _loc_5 && _loc_4 < _loc_6)
            {
                _loc_7 = Language.L["对不起，你的金币和等级均不足。"];
            }
            else if (_loc_3 < _loc_5)
            {
                _loc_7 = Language.L["对不起，你的等级不足。"];
            }
            else if (_loc_4 < _loc_6)
            {
                _loc_7 = Language.L["对不起，你的金币不足。"];
            }// end else if
            var _loc_8:Object;
            var _loc_9:* = Language.replaceText("reclaimPayText", _loc_8);
            var _loc_10:* = new ConfirmWindow();
            new ConfirmWindow().width = 360;
            _loc_10.height = 250;
            _loc_10.title = Language.L["扩建农场"];
            if (_loc_7 == "")
            {
                _loc_10.data = {text:_loc_9};
            }
            else
            {
                _loc_10.confirmEnable = false;
                _loc_10.data = {text:_loc_9, error:_loc_7};
            }// end else if
            _loc_10.confirmFn = confirmReclaim;
            WControl.open(_loc_10);
            return;
        }// end function

        private function harvestData(param1:Event) : void
        {
            var _loc_2:* = farmData.harvestData;
            var _loc_3:* = new HarvestAnimation();
            _loc_3.text = _loc_2["text"];
            _loc_3.setContent("1", _loc_2["cId"]);
            var _loc_4:* = _loc_2["index"];
            var _loc_5:* = getChildAt(_loc_4).x;
            var _loc_6:* = getChildAt(_loc_4).y + 20;
            var _loc_7:* = localToGlobal(new Point(_loc_5, _loc_6));
            _loc_3.x = _loc_7.x;
            _loc_3.y = _loc_7.y;
            Float.open(_loc_3);
            return;
        }// end function

        private function farmlandDataChange(param1:ModelEvent) : void
        {
            setFarmland();
            return;
        }// end function

        private function farmOperate(param1:Event) : void
        {
            var _loc_2:* = new FarmOperateFloating(farmData.farmOperate["me"]);
            _loc_2.text = farmData.farmOperate["text"];
            var _loc_3:* = farmData.farmOperate["index"];
            _loc_2.fid = _loc_3;
            var _loc_4:* = getChildAt(_loc_3).x + getChildAt(_loc_3).width / 2;
            var _loc_5:* = getChildAt(_loc_3).y + 20;
            var _loc_6:* = localToGlobal(new Point(_loc_4, _loc_5));
            _loc_2.x = _loc_6.x;
            _loc_2.y = _loc_6.y;
            Float.open(_loc_2);
            return;
        }// end function

        private function openBigInsect() : void
        {
            var _loc_3:Array;
            var _loc_4:int;
            if (!mainData.me)
            {
                return;
            }// end if
            if (Version.value != Version.FACEBOOK)
            {
                return;
            }// end if
            var _loc_1:* = LocalData.getInstance().getObject("openTime");
            var _loc_2:* = new Date().getTime();
            if (_loc_1 == null || _loc_2 > Number(_loc_1) + 3600000)
            {
                _loc_3 = mainData.farmlandData;
                _loc_4 = 0;
                while (_loc_4 < _loc_3.length)
                {
                    // label
                    if (_loc_3[_loc_4]["pId"] != null && _loc_3[_loc_4]["pId"] != 0)
                    {
                        WControl.open(new KillBigInsectWindow());
                        LocalData.getInstance().setObject("openTime", _loc_2);
                        return;
                    }// end if
                    _loc_4++;
                }// end while
            }// end if
            return;
        }// end function

        private function reclaimClick(param1:Event) : void
        {
            if (!reclaiming)
            {
                reclaiming = true;
                Command.getInstance().farmCommand.reclaimPay(reclaimPay);
            }// end if
            return;
        }// end function

    }
}
