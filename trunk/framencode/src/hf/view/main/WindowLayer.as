package hf.view.main
{
    import flash.display.*;
    import flash.events.*;
    import flash.utils.*;
    import hf.control.*;
    import hf.view.common.*;
    import hf.view.main.WindowControl.*;
    import hf.view.main.window.dial.*;
    import hf.view.main.window.div.*;
    import hf.view.main.window.profile.*;
    import hf.view.main.window.shop.*;
    import hf.view.main.window.warehouse.*;

    public class WindowLayer extends Sprite
    {
        private var animationArrayClose:Array;
        private var _shopWindow:BaseWindow;
        private var _profileWindow:BaseWindow;
        private var _warehouseWindow:BaseWindow;
        private var _dialWindow:BaseWindow;
        private var animationTimer:Timer;
        private var _diyWindow:BaseWindow;
        private var windowList:Object;
        private var animationArray:Array;
        private var viewControl:ViewControl;
        private var modeLayer:Sprite;

        public function WindowLayer()
        {
            windowList = new Object();
            animationArray = [];
            animationArrayClose = [];
            addEventListener(Event.ADDED_TO_STAGE, addedToStage);
            return;
        }// end function

        private function openDiyWindow() : void
        {
            if (_diyWindow != null)
            {
                openWindow(_diyWindow);
            }
            else
            {
                _diyWindow = new DiyWindow();
                _diyWindow.x = (stage.stageWidth - _diyWindow.width) / 2;
                _diyWindow.y = (stage.stageHeight - _diyWindow.height) / 2;
                openWindow(_diyWindow);
            }// end else if
            return;
        }// end function

        private function closeWindow(param1:BaseWindow, param2:String) : void
        {
            var _loc_3:BaseWindow;
            var _loc_5:int;
            if (param1 != null)
            {
                _loc_3 = param1;
            }
            else if (param2 != "")
            {
                _loc_5 = 0;
                while (_loc_5 < numChildren)
                {
                    // label
                    if (getChildAt(_loc_5) is BaseWindow)
                    {
                        if ((getChildAt(_loc_5) as BaseWindow).windowName == param2)
                        {
                            _loc_3 = getChildAt(_loc_5) as BaseWindow;
                            break;
                        }// end if
                    }// end if
                    _loc_5++;
                }// end while
            }// end else if
            var _loc_4:Object;
            {}["aWindow"] = _loc_3;
            _loc_4["aValue"] = 1;
            _loc_4["x"] = _loc_3.x;
            _loc_4["y"] = _loc_3.y;
            _loc_4["w"] = _loc_3.width;
            _loc_4["h"] = _loc_3.height;
            if (param1.windowMode != null)
            {
                if (contains(param1.windowMode))
                {
                    removeChild(param1.windowMode);
                }// end if
            }// end if
            animationArrayClose.push(_loc_4);
            param1.removeAll();
            return;
        }// end function

        private function init() : void
        {
            animationTimer = new Timer(50);
            animationTimer.addEventListener(TimerEvent.TIMER, onTimer);
            animationTimer.start();
            viewControl = ViewControl.getInstance();
            viewControl.addEventListener(WindowEvent.OPEN, windowOpen, false, 0, true);
            viewControl.addEventListener(WindowEvent.CLOSE, windowClose, false, 0, true);
            stage.addEventListener(KeyboardEvent.KEY_UP, stageKeyUp);
            return;
        }// end function

        private function openDial() : void
        {
            if (_dialWindow != null)
            {
                openWindow(_dialWindow);
                return;
            }// end if
            _dialWindow = new Dial();
            if (stage != null)
            {
                _dialWindow.x = (stage.stageWidth - _dialWindow.width) / 2;
                _dialWindow.y = (stage.stageHeight - _dialWindow.height) / 2;
            }
            else
            {
                _dialWindow.x = 150;
                _dialWindow.y = 100;
            }// end else if
            openWindow(_dialWindow);
            return;
        }// end function

        private function openWarehouse() : void
        {
            if (_warehouseWindow != null)
            {
                openWindow(_warehouseWindow);
            }
            else
            {
                _warehouseWindow = new WarehouseWindow();
                if (stage != null)
                {
                    _warehouseWindow.x = (stage.stageWidth - _warehouseWindow.width) / 2;
                    _warehouseWindow.y = (stage.stageHeight - _warehouseWindow.height) / 2;
                }
                else
                {
                    _warehouseWindow.x = 150;
                    _warehouseWindow.y = 100;
                }// end else if
                openWindow(_warehouseWindow);
            }// end else if
            return;
        }// end function

        private function openAnimation() : void
        {
            var _loc_3:Object;
            var _loc_4:BaseWindow;
            if (animationArray.length == 0)
            {
                return;
            }// end if
            var _loc_1:Array;
            var _loc_2:int;
            while (_loc_2 < animationArray.length)
            {
                // label
                _loc_3 = animationArray[_loc_2];
                _loc_4 = _loc_3["aWindow"] as BaseWindow;
                if (_loc_3["aValue"] >= 1)
                {
                    _loc_4.alpha = 1;
                    var _loc_5:int;
                    _loc_4.scaleY = 1;
                    _loc_4.scaleX = _loc_5;
                    _loc_4.x = _loc_3["x"];
                    _loc_4.y = _loc_3["y"];
                    if (_loc_4.isInit == false)
                    {
                        _loc_4.isInit = true;
                        _loc_4.init();
                    }
                    else
                    {
                        _loc_4.addAll();
                    }// end else if
                    _loc_4.addedToLayer();
                }
                else
                {
                    _loc_3["aValue"] = _loc_3["aValue"] + 0.34;
                    _loc_4.alpha = _loc_3["aValue"];
                    var _loc_5:* = _loc_3["aValue"];
                    _loc_4.scaleY = _loc_3["aValue"];
                    _loc_4.scaleX = _loc_5;
                    _loc_4.x = _loc_3["x"] + (_loc_3["w"] - _loc_3["w"] * _loc_3["aValue"]) / 2;
                    _loc_4.y = _loc_3["y"] + (_loc_3["h"] - _loc_3["h"] * _loc_3["aValue"]) / 2;
                    _loc_1.push(_loc_3);
                }// end else if
                _loc_2++;
            }// end while
            animationArray = _loc_1;
            return;
        }// end function

        private function openShop() : void
        {
            if (_shopWindow != null)
            {
                openWindow(_shopWindow);
                return;
            }// end if
            _shopWindow = new ShopWindow();
            if (stage != null)
            {
                _shopWindow.x = (stage.stageWidth - _shopWindow.width) / 2;
                _shopWindow.y = (stage.stageHeight - _shopWindow.height) / 2;
            }
            else
            {
                _shopWindow.x = 150;
                _shopWindow.y = 100;
            }// end else if
            openWindow(_shopWindow);
            return;
        }// end function

        private function addedToStage(param1:Event) : void
        {
            removeEventListener(Event.ADDED_TO_STAGE, addedToStage);
            init();
            return;
        }// end function

        private function getWindow(param1:String) : BaseWindow
        {
            var _loc_2:BaseWindow;
            var _loc_3:WindowClassLib;
            if (param1 == "")
            {
                return null;
            }// end if
            _loc_2 = new WindowClassLib.getClass(param1) as BaseWindow;
            return _loc_2;
        }// end function

        private function windowClose(param1:WindowEvent) : void
        {
            closeWindow(param1.window, param1.windowName);
            return;
        }// end function

        private function openWindow(param1:BaseWindow, param2:Boolean = false) : void
        {
            var _loc_3:Object;
            if (param1.mode)
            {
                if (param1.windowMode == null)
                {
                    param1.windowMode = new Sprite();
                    param1.windowMode.graphics.beginFill(16777215, 0);
                    param1.windowMode.graphics.drawRect(0, 0, stage.stageWidth, stage.stageHeight);
                    param1.windowMode.graphics.endFill();
                }// end if
                addChild(param1.windowMode);
            }// end if
            param1.x = (stage.stageWidth - param1.width) / 2;
            param1.y = (stage.stageHeight - param1.height) / 2;
            if (contains(param1))
            {
                addChild(param1);
            }
            else
            {
                param1.alpha = 0;
                addChild(param1);
                _loc_3 = {};
                _loc_3["aWindow"] = param1;
                _loc_3["aValue"] = 0;
                _loc_3["x"] = param1.x;
                _loc_3["y"] = param1.y;
                _loc_3["w"] = param1.width;
                _loc_3["h"] = param1.height;
                animationArray.push(_loc_3);
            }// end else if
            return;
        }// end function

        private function closeAnimation() : void
        {
            var _loc_3:Object;
            var _loc_4:BaseWindow;
            if (animationArrayClose.length == 0)
            {
                return;
            }// end if
            var _loc_1:Array;
            var _loc_2:int;
            while (_loc_2 < animationArrayClose.length)
            {
                // label
                _loc_3 = animationArrayClose[_loc_2];
                _loc_4 = _loc_3["aWindow"] as BaseWindow;
                if (_loc_3["aValue"] <= 0)
                {
                    _loc_4.removedFromLayer();
                    removeChild(_loc_4);
                }
                else
                {
                    _loc_3["aValue"] = _loc_3["aValue"] - 0.34;
                    _loc_4.alpha = _loc_3["aValue"];
                    var _loc_5:* = _loc_3["aValue"];
                    _loc_4.scaleY = _loc_3["aValue"];
                    _loc_4.scaleX = _loc_5;
                    _loc_4.x = _loc_3["x"] + (_loc_3["w"] - _loc_3["w"] * _loc_3["aValue"]) / 2;
                    _loc_4.y = _loc_3["y"] + (_loc_3["h"] - _loc_3["h"] * _loc_3["aValue"]) / 2;
                    _loc_1.push(_loc_3);
                }// end else if
                _loc_2++;
            }// end while
            animationArrayClose = _loc_1;
            return;
        }// end function

        private function onTimer(param1:Event) : void
        {
            openAnimation();
            closeAnimation();
            return;
        }// end function

        private function hasWindow(param1:String) : BaseWindow
        {
            var _loc_2:int;
            while (_loc_2 < numChildren)
            {
                // label
                if (getChildAt(_loc_2) is BaseWindow)
                {
                    if ((getChildAt(_loc_2) as BaseWindow).windowName == param1)
                    {
                        return getChildAt(_loc_2) as BaseWindow;
                    }// end if
                }// end if
                _loc_2++;
            }// end while
            return null;
        }// end function

        private function windowOpen(param1:WindowEvent) : void
        {
            if (param1.windowName == "warehouse")
            {
                openWarehouse();
            }
            else if (param1.windowName == "dial")
            {
                openDial();
            }
            else if (param1.windowName == "shop")
            {
                openShop();
            }
            else if (param1.windowName == "profile")
            {
                openProfile(int(param1.windowArgument));
            }
            else if (param1.windowName == "DiyWindow")
            {
                openDiyWindow();
            }
            else if (param1.window != null)
            {
                openWindow(param1.window);
            }
            else
            {
                openWindowForName(param1.windowName, param1.windowArgument);
            }// end else if
            return;
        }// end function

        private function openWindowForName(param1:String, param2:Object) : void
        {
            var _loc_3:* = hasWindow(param1);
            if (_loc_3 == null)
            {
                _loc_3 = getWindow(param1);
                _loc_3.data = param2;
                openWindow(_loc_3);
            }
            else
            {
                _loc_3.data = param2;
                openWindow(_loc_3, true);
            }// end else if
            return;
        }// end function

        private function stageKeyUp(param1:KeyboardEvent) : void
        {
            var _loc_2:int;
            if (param1.keyCode == 13)
            {
                while (_loc_2-- >= 0)
                {
                    // label
                    if (getChildAt(numChildren--) is BaseWindow)
                    {
                        (getChildAt(_loc_2) as BaseWindow).keyEnter();
                        break;
                    }// end if
                }// end while
            }// end if
            return;
        }// end function

        private function openProfile(param1:int) : void
        {
            if (_profileWindow != null)
            {
                (_profileWindow as Profile).selected = param1;
                openWindow(_profileWindow);
            }
            else
            {
                _profileWindow = new Profile();
                _profileWindow.x = (stage.stageWidth - _profileWindow.width) / 2;
                _profileWindow.y = (stage.stageHeight - _profileWindow.height) / 2;
                (_profileWindow as Profile).selected = param1;
                openWindow(_profileWindow);
            }// end else if
            return;
        }// end function

    }
}
