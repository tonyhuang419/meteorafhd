package hf.view.main.head
{
    import com.minutes.ui.collections.*;
    import flash.display.*;
    import flash.events.*;
    import flash.geom.*;
    import hf.control.*;
    import hf.model.*;
    import hf.view.*;
    import hf.view.main.floating.*;

    public class HeadBar extends SimpleCollection
    {
        private var personInfo:PersonInfo;
        private var _headToolBar:HeadToolBar;
        private var mainData:MainData;
        private var _weather:Weather;

        public function HeadBar()
        {
            bg = MaterialLib.getInstance().getMaterial("HeadBg") as Sprite;
            addEventListener(Event.ADDED_TO_STAGE, onAddedToStage);
            return;
        }// end function

        private function onAddedToStage(param1:Event) : void
        {
            removeEventListener(Event.ADDED_TO_STAGE, onAddedToStage);
            init();
            return;
        }// end function

        private function weatherChange(param1:ModelEvent) : void
        {
            _weather.weather = mainData.weather;
            return;
        }// end function

        private function setPersonInfo() : void
        {
            if (mainData.host != null)
            {
                personInfo.userName = mainData.host["userName"];
                if (!mainData.host.hasOwnProperty("yellowstatus") || mainData.host["yellowstatus"] == 0)
                {
                    personInfo.color = Level.BLUE;
                }
                else
                {
                    personInfo.color = Level.YELLOW;
                    personInfo.vip = "VipL" + mainData.host["yellowlevel"];
                }// end else if
                personInfo.url = mainData.host["headPic"];
                personInfo.goldValue = mainData.host["money"];
                personInfo.fbValue = mainData.host["FB"];
                personInfo.exp = mainData.host["exp"];
            }// end if
            return;
        }// end function

        private function hostChange(param1:ModelEvent) : void
        {
            setPersonInfo();
            return;
        }// end function

        private function headToolBarChildClick(param1:ViewEvent) : void
        {
            var _loc_3:DisplayObject;
            var _loc_4:Point;
            var _loc_5:WindowEvent;
            var _loc_2:* = param1.data;
            if (_loc_2 == null)
            {
                return;
            }// end if
            if (_loc_2["name"] == "warehouse")
            {
                _loc_5 = new WindowEvent(WindowEvent.OPEN);
                _loc_5.windowName = "warehouse";
                if (_loc_2["target"] is DisplayObject)
                {
                    _loc_3 = _loc_2["target"] as DisplayObject;
                    _loc_4 = _headToolBar.localToGlobal(new Point(_loc_3.x, _loc_3.y));
                    _loc_5.startX = _loc_4.x;
                    _loc_5.startY = _loc_4.y;
                }// end if
                ViewControl.getInstance().dispatchEvent(_loc_5);
            }
            else if (_loc_2["name"] == "shop")
            {
                _loc_5 = new WindowEvent(WindowEvent.OPEN);
                _loc_5.windowName = "shop";
                ViewControl.getInstance().dispatchEvent(_loc_5);
            }
            else if (_loc_2["name"] == "farm")
            {
                Command.getInstance().mainCommand.setUser();
            }
            else if (_loc_2["name"] == "decorate")
            {
                if (!mainData.me)
                {
                    Command.getInstance().mainCommand.setUser();
                }// end if
                _loc_5 = new WindowEvent(WindowEvent.OPEN);
                _loc_5.windowName = "DiyWindow";
                ViewControl.getInstance().dispatchEvent(_loc_5);
            }
            else if (_loc_2["name"] == "dial")
            {
                _loc_5 = new WindowEvent(WindowEvent.OPEN);
                _loc_5.windowName = "dial";
                ViewControl.getInstance().dispatchEvent(_loc_5);
            }
            else if (_loc_2["name"] == "flower")
            {
                _loc_5 = new WindowEvent(WindowEvent.OPEN);
                _loc_5.windowName = "flower";
                ViewControl.getInstance().dispatchEvent(_loc_5);
            }// end else if
            return;
        }// end function

        private function init() : void
        {
            mainData = MData.getInstance().mainData;
            mainData.addEventListener(MainData.HOST_CHANGE, hostChange, false, 0, true);
            mainData.addEventListener(MainData.WEATHER, weatherChange, false, 0, true);
            mainData.addEventListener(MainData.ADD_EXP, addExp, false, 0, true);
            mainData.addEventListener(MainData.ADD_MONEY, addMoney, false, 0, true);
            mainData.addEventListener(MainData.ADD_FB, addFB, false, 0, true);
            personInfo = new PersonInfo();
            personInfo.x = 15;
            addChild(personInfo);
            setPersonInfo();
            _weather = new Weather();
            _weather.weather = mainData.weather;
            addChild(_weather);
            _weather.x = (stage.stageWidth - 60) / 2;
            _weather.y = 5;
            _headToolBar = new HeadToolBar();
            var _loc_1:int;
            if (stage.stageWidth < 700)
            {
                _loc_1 = 3;
            }
            else if (stage.stageWidth >= 700 && stage.stageWidth < 800)
            {
                _loc_1 = 8;
            }
            else
            {
                _loc_1 = 11;
            }// end else if
            _headToolBar.setPosition(_loc_1);
            _headToolBar.x = stage.stageWidth - _headToolBar.width - _loc_1;
            _headToolBar.addEventListener(ViewEvent.CHILD_CLICK, headToolBarChildClick);
            addChild(_headToolBar);
            return;
        }// end function

        private function addExp(param1:Event) : void
        {
            var _loc_2:AddMoneyFloating;
            personInfo.exp = mainData.host["exp"];
            if (mainData.addExp != 0)
            {
                _loc_2 = new AddMoneyFloating();
                _loc_2.fid = 1;
                _loc_2.x = 240;
                _loc_2.y = 17;
                _loc_2.data = String(mainData.addExp);
                Float.open(_loc_2);
            }// end if
            return;
        }// end function

        private function addFB(param1:Event) : void
        {
            var _loc_2:AddMoneyFloating;
            new NumAnimation(personInfo, "fbValue", personInfo.fbValue, mainData.host["FB"]);
            if (personInfo.fbValue < 0)
            {
                personInfo.fbValue = 0;
            }// end if
            if (mainData.addFB != 0)
            {
                _loc_2 = new AddMoneyFloating();
                _loc_2.fid = 2;
                _loc_2.x = 180;
                _loc_2.y = 65;
                _loc_2.data = String(mainData.addFB);
                Float.open(_loc_2);
            }// end if
            return;
        }// end function

        private function addMoney(param1:Event) : void
        {
            var _loc_2:AddMoneyFloating;
            new NumAnimation(personInfo, "goldValue", personInfo.goldValue, mainData.host["money"]);
            if (personInfo.goldValue < 0)
            {
                personInfo.goldValue = 0;
            }// end if
            if (mainData.addMoney != 0)
            {
                _loc_2 = new AddMoneyFloating();
                _loc_2.fid = 2;
                _loc_2.x = 100;
                _loc_2.y = 65;
                _loc_2.data = String(mainData.addMoney);
                Float.open(_loc_2);
            }// end if
            return;
        }// end function

    }
}
