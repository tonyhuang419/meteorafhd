package hf.view.main.window.div
{
    import com.minutes.ui.collections.*;
    import com.minutes.ui.constant.*;
    import flash.display.*;
    import flash.events.*;
    import flash.text.*;
    import hf.control.*;
    import hf.model.*;
    import hf.view.*;
    import hf.view.common.*;

    public class DiyWindow extends BaseWindow
    {
        private var dataMObject:MainData;
        private var loading:DataLoading;
        private var addChildCache:Array;
        private var firstInit:Boolean = true;
        private var divaCache:LipiCanvas = null;

        public function DiyWindow()
        {
            addChildCache = [];
            this.width = 400;
            this.height = 300;
            this.title = Language.L["农场装饰"];
            this.mode = true;
            return;
        }// end function

        private function userItemErr(param1:Event) : void
        {
            loading.errorText = dataMObject.userItemErr;
            return;
        }// end function

        private function dataLoaded(param1:Event) : void
        {
            if (firstInit)
            {
                initDiv();
                initDivChildCache(dataMObject.userItems);
                showCurrentDivChild(dataMObject.userItems);
            }
            else
            {
                divAdorn(dataMObject.userItems);
            }// end else if
            return;
        }// end function

        override public function init() : void
        {
            dataMObject = MData.getInstance().mainData;
            loading = new DataLoading();
            loading.addEventListener(ViewEvent.LINK_CLICK, linkClick);
            loading.x = (this.width - loading.width) * 0.5;
            loading.y = (this.height - loading.height) * 0.5;
            loading.visible = dataMObject.userItemLoading;
            addChild(loading);
            dataMObject.addEventListener(MainData.USER_ITEM_LOADING, isLoading);
            dataMObject.addEventListener(MainData.USER_ITEMS, dataLoaded);
            dataMObject.addEventListener(MainData.USER_ITEM_ERR, userItemErr);
            return;
        }// end function

        private function addline() : Sprite
        {
            var _loc_1:* = new Sprite();
            _loc_1.graphics.beginFill(16777215, 1);
            _loc_1.graphics.drawRect(0, 0, this.width - 4, 1);
            _loc_1.graphics.endFill();
            divWindowAddChild(_loc_1);
            return _loc_1;
        }// end function

        private function divAdorn(param1:Array) : void
        {
            if (initDivChildCache(param1))
            {
                showCurrentDivChild(param1);
            }// end if
            return;
        }// end function

        private function addChildTitle(param1:String) : TextField
        {
            var _loc_2:* = new TextField();
            _loc_2.text = param1;
            _loc_2.setTextFormat(new TextFormat("Tahoma", 14, 39423, null, null, null, null, null, "center"));
            _loc_2.width = 100;
            _loc_2.height = 25;
            divWindowAddChild(_loc_2);
            return _loc_2;
        }// end function

        private function initDivChildCache(param1:Array) : Boolean
        {
            var _loc_5:Boolean;
            var _loc_6:int;
            var _loc_7:int;
            var _loc_8:OneDivShow;
            var _loc_9:OneDivShow;
            var _loc_2:* = MaterialLib.getInstance().getClass("ItemBorder");
            var _loc_3:Boolean;
            var _loc_4:int;
            while (_loc_4 < param1.length)
            {
                // label
                _loc_5 = true;
                _loc_6 = divaCache.numChildren;
                if (_loc_6 > 0)
                {
                    _loc_7 = 0;
                    while (_loc_7 < _loc_6)
                    {
                        // label
                        _loc_8 = divaCache.getChildAt(_loc_7) as OneDivShow;
                        if (_loc_8.divData.itemId == param1[_loc_4].itemId)
                        {
                            _loc_8.divData = param1[_loc_4];
                            _loc_5 = false;
                            break;
                        }// end if
                        _loc_7++;
                    }// end while
                }// end if
                if (_loc_5)
                {
                    _loc_3 = true;
                    _loc_9 = new OneDivShow(new MaterialProxy(), _loc_2, param1[_loc_4]);
                    divaCache.addChild(_loc_9);
                }// end if
                _loc_4++;
            }// end while
            return _loc_3;
        }// end function

        private function initDiv() : void
        {
            firstInit = false;
            var _loc_1:int;
            var _loc_2:* = addChildTitle(Language.L["背景"]);
            _loc_2.selectable = false;
            _loc_2.x = 5;
            _loc_2.y = _loc_1;
            var _loc_3:* = addChildTitle(Language.L["房子"]);
            _loc_3.selectable = false;
            _loc_3.x = 95;
            _loc_3.y = _loc_1;
            var _loc_4:* = addChildTitle(Language.L["狗窝"]);
            addChildTitle(Language.L["狗窝"]).selectable = false;
            _loc_4.x = 185;
            _loc_4.y = _loc_1;
            var _loc_5:* = addChildTitle(Language.L["栅栏"]);
            addChildTitle(Language.L["栅栏"]).selectable = false;
            _loc_5.x = 275;
            _loc_5.y = _loc_1;
            var _loc_6:* = addline();
            addline().x = 2;
            _loc_6.y = _loc_1 + 20;
            divaCache = addChildContaner();
            divaCache.x = 2;
            divaCache.y = _loc_1 + 20;
            addItemsCaches();
            return;
        }// end function

        private function toCenterPositon() : void
        {
            x = stage.stageWidth * 0.5 - width * 0.5;
            y = stage.stageHeight * 0.5 - height * 0.5;
            return;
        }// end function

        private function addChildContaner() : LipiCanvas
        {
            var _loc_1:* = new LipiCanvas();
            _loc_1.horizontalScrollPolicy = ScrollPolicy.OFF;
            _loc_1.verticalScrollPolicy = ScrollPolicy.AUTO;
            _loc_1.height = 230;
            _loc_1.width = this.width - 4;
            _loc_1.bgAlpha = 0;
            divWindowAddChild(_loc_1);
            return _loc_1;
        }// end function

        private function hideAllDivChild() : void
        {
            var _loc_2:int;
            var _loc_1:* = divaCache.numChildren;
            if (_loc_1 > 0)
            {
                _loc_2 = 0;
                while (_loc_2 < _loc_1)
                {
                    // label
                    divaCache.getChildAt(_loc_2).visible = false;
                    _loc_2++;
                }// end while
            }// end if
            return;
        }// end function

        private function linkClick(param1:ViewEvent) : void
        {
            if (param1.data == "reload")
            {
                Command.getInstance().mainCommand.getUserItems();
            }// end if
            return;
        }// end function

        private function addItemsCaches() : void
        {
            if (addChildCache.length <= 0)
            {
                return;
            }// end if
            var _loc_1:int;
            while (_loc_1 < addChildCache.length)
            {
                // label
                this.addChild(addChildCache[_loc_1]);
                _loc_1++;
            }// end while
            return;
        }// end function

        private function isLoading(param1:Event) : void
        {
            loading.visible = dataMObject.userItemLoading;
            return;
        }// end function

        override public function addedToLayer() : void
        {
            super.addedToLayer();
            Command.getInstance().mainCommand.getUserItems();
            return;
        }// end function

        public function divWindowAddChild(param1:DisplayObject) : DisplayObject
        {
            addChildCache.push(param1);
            return param1;
        }// end function

        private function showCurrentDivChild(param1:Array) : void
        {
            var _loc_2:OneDivShow;
            var _loc_15:int;
            var _loc_3:int;
            var _loc_4:int;
            var _loc_5:int;
            var _loc_6:int;
            var _loc_7:int;
            var _loc_8:int;
            var _loc_9:int;
            var _loc_10:int;
            var _loc_11:int;
            var _loc_12:* = param1.length;
            var _loc_13:* = divaCache.numChildren;
            var _loc_14:int;
            while (_loc_14 < _loc_12)
            {
                // label
                _loc_15 = 0;
                while (_loc_15 < _loc_13)
                {
                    // label
                    _loc_2 = divaCache.getChildAt(_loc_15) as OneDivShow;
                    if (_loc_2.divData.itemId == param1[_loc_14].itemId)
                    {
                        switch(_loc_2.divData.itemType.toString())
                        {
                            case "1":
                            {
                                _loc_2.x = _loc_3;
                                _loc_2.y = _loc_8;
                                _loc_8 = _loc_8 + (60 + _loc_7);
                                break;
                            }// end case
                            case "2":
                            {
                                _loc_2.x = _loc_4;
                                _loc_2.y = _loc_9;
                                _loc_9 = _loc_9 + (60 + _loc_7);
                                break;
                            }// end case
                            case "3":
                            {
                                _loc_2.x = _loc_5;
                                _loc_2.y = _loc_10;
                                _loc_10 = _loc_10 + (60 + _loc_7);
                                break;
                            }// end case
                            case "4":
                            {
                                _loc_2.x = _loc_6;
                                _loc_2.y = _loc_11;
                                _loc_11 = _loc_11 + (60 + _loc_7);
                                break;
                            }// end case
                            default:
                            {
                                break;
                            }// end default
                        }// end switch
                    }// end if
                    _loc_15++;
                }// end while
                _loc_14++;
            }// end while
            return;
        }// end function

    }
}
