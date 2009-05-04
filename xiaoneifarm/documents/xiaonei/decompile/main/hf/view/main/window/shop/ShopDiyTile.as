package hf.view.main.window.shop
{
    import com.minutes.ui.collections.*;
    import com.minutes.ui.constant.*;
    import flash.display.*;
    import flash.text.*;
    import hf.view.*;
    import hf.view.common.*;

    public class ShopDiyTile extends Sprite
    {
        private var _dataList:Array;
        private var dataLoading:DataLoading;
        private var _top:int = 18;
        private var divaCache:LipiCanvas = null;

        public function ShopDiyTile()
        {
            _dataList = [];
            addText();
            divaCache = addChildContaner();
            dataLoading = new DataLoading();
            dataLoading.addEventListener(ViewEvent.LINK_CLICK, linkClick);
            dataLoading.x = 250;
            dataLoading.y = 130;
            addChild(dataLoading);
            return;
        }// end function

        private function showCurrentDivChild(param1:Array) : void
        {
            var _loc_2:OneDivShow;
            var _loc_15:int;
            initDivChildCache(param1);
            hideAllDivChild();
            var _loc_3:int;
            var _loc_4:int;
            var _loc_5:int;
            var _loc_6:int;
            var _loc_7:int;
            _loc_7 = 65;
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
                                _loc_2.visible = true;
                                break;
                            }// end case
                            case "2":
                            {
                                _loc_2.x = _loc_4;
                                _loc_2.y = _loc_9;
                                _loc_9 = _loc_9 + (60 + _loc_7);
                                _loc_2.visible = true;
                                break;
                            }// end case
                            case "3":
                            {
                                _loc_2.x = _loc_5;
                                _loc_2.y = _loc_10;
                                _loc_10 = _loc_10 + (60 + _loc_7);
                                _loc_2.visible = true;
                                break;
                            }// end case
                            case "4":
                            {
                                _loc_2.x = _loc_6;
                                _loc_2.y = _loc_11;
                                _loc_11 = _loc_11 + (60 + _loc_7);
                                _loc_2.visible = true;
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

        public function get dataList() : Array
        {
            return _dataList;
        }// end function

        private function addChildContaner() : LipiCanvas
        {
            var _loc_1:* = new LipiCanvas();
            _loc_1.horizontalScrollPolicy = ScrollPolicy.OFF;
            _loc_1.verticalScrollPolicy = ScrollPolicy.AUTO;
            _loc_1.x = 4;
            _loc_1.y = _top + 20;
            _loc_1.width = 492;
            _loc_1.height = 255;
            _loc_1.bgAlpha = 0;
            addChild(_loc_1);
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

        public function set loadingVisible(param1:Boolean) : void
        {
            dataLoading.visible = param1;
            divaCache.visible = !param1;
            return;
        }// end function

        private function addline() : Sprite
        {
            var _loc_1:* = new Sprite();
            _loc_1.graphics.beginFill(16777215, 1);
            _loc_1.graphics.drawRect(0, 0, this.width - 4, 1);
            _loc_1.graphics.endFill();
            addChild(_loc_1);
            return _loc_1;
        }// end function

        private function linkClick(param1:ViewEvent) : void
        {
            var _loc_2:* = new ViewEvent(ViewEvent.LINK_CLICK);
            _loc_2.data = param1.data;
            dispatchEvent(_loc_2);
            return;
        }// end function

        private function initTitle() : void
        {
            var _loc_1:* = addChildTitle(Language.L["背景"]);
            _loc_1.selectable = false;
            _loc_1.x = 5;
            _loc_1.y = _top;
            var _loc_2:* = addChildTitle(Language.L["房子"]);
            _loc_2.selectable = false;
            _loc_2.x = 115;
            _loc_2.y = _top;
            var _loc_3:* = addChildTitle(Language.L["狗窝"]);
            _loc_3.selectable = false;
            _loc_3.x = 235;
            _loc_3.y = _top;
            var _loc_4:* = addChildTitle(Language.L["栅栏"]);
            addChildTitle(Language.L["栅栏"]).selectable = false;
            _loc_4.x = 346;
            _loc_4.y = _top;
            var _loc_5:* = addline();
            addline().x = 2;
            _loc_5.y = _top + 19;
            return;
        }// end function

        public function set dataList(param1:Array) : void
        {
            _dataList = param1;
            showCurrentDivChild(param1);
            return;
        }// end function

        private function initDivChildCache(param1:Array) : void
        {
            var _loc_4:Boolean;
            var _loc_5:int;
            var _loc_6:int;
            var _loc_7:OneDivShow;
            var _loc_8:OneDivShow;
            var _loc_2:* = MaterialLib.getInstance().getClass("ItemBorder");
            var _loc_3:int;
            while (_loc_3 < param1.length)
            {
                // label
                _loc_4 = true;
                _loc_5 = divaCache.numChildren;
                if (_loc_5 > 0)
                {
                    _loc_6 = 0;
                    while (_loc_6 < _loc_5)
                    {
                        // label
                        _loc_7 = divaCache.getChildAt(_loc_6) as OneDivShow;
                        if (_loc_7.divData.itemId == param1[_loc_3].itemId)
                        {
                            _loc_7.divData = param1[_loc_3];
                            _loc_4 = false;
                            break;
                        }// end if
                        _loc_6++;
                    }// end while
                }// end if
                if (_loc_4)
                {
                    _loc_8 = new OneDivShow(new MaterialProxy(), _loc_2, param1[_loc_3]);
                    _loc_8.visible = false;
                    divaCache.addChild(_loc_8);
                }// end if
                _loc_3++;
            }// end while
            return;
        }// end function

        public function set errText(param1:String) : void
        {
            dataLoading.errorText = param1;
            return;
        }// end function

        private function addText() : void
        {
            var _loc_1:* = new TextField();
            _loc_1.defaultTextFormat = new TextFormat("Tahoma", 14, 13369344, null, null, null, null, null, TextFormatAlign.CENTER);
            _loc_1.text = Language.L["diyTileDirection"];
            _loc_1.selectable = false;
            _loc_1.width = 500;
            _loc_1.height = 20;
            _loc_1.x = 0;
            _loc_1.y = 0;
            addChild(_loc_1);
            initTitle();
            return;
        }// end function

        private function addChildTitle(param1:String) : TextField
        {
            var _loc_2:* = new TextField();
            _loc_2.text = param1;
            _loc_2.setTextFormat(new TextFormat("Tahoma", 14, 39423, null, null, null, null, null, "center"));
            _loc_2.width = 100;
            _loc_2.height = 25;
            addChild(_loc_2);
            return _loc_2;
        }// end function

    }
}
