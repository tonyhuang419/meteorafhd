package hf.view.main.friend
{
    import com.minutes.ui.collections.*;
    import com.minutes.ui.constant.*;
    import com.minutes.ui.control.*;
    import com.minutes.ui.core.*;
    import flash.display.*;
    import flash.events.*;
    import flash.text.*;
    import flash.utils.*;
    import hf.control.*;
    import hf.model.*;
    import hf.view.*;
    import hf.view.common.*;
    import hf.view.main.tip.*;

    public class PaginationFriend extends Sprite
    {
        private var pageTextShow:TextField;
        private var sortByGoldButtonB:Sprite;
        private var searchTextField:TextField;
        private var _page:int = 0;
        private var searchButton:LipiButton;
        private var level_me_skin:Class = null;
        private var _friendList:Array;
        private var nextButton:LipiButton;
        private var refurbishButton:LipiButton;
        private var currentlyTimer:Timer = null;
        private var level_2_skin:Class = null;
        private var shrinkageButton:ToggleButton;
        private var level_3_skin:Class = null;
        private var leftContainer:LipiCanvas;
        private var loading:DataLoading;
        private var childContainer:LipiCanvas;
        private var childCache:Array;
        private var _pageData:Array;
        private var ChildClass:Class;
        private var level_0_skin:Class = null;
        private var level_1_skin:Class = null;
        private var sortByExpButtonA:MovieClip;
        private var sortByExpButtonB:Sprite;
        private var _countPage:int = 0;
        private var backButton:LipiButton;
        private var sortByGoldButtonA:MovieClip;
        public static var BACK:String = "back";
        public static var SEARCH:String = "search";
        public static var NEXT:String = "next";
        public static var SHRINKAGE:String = "shrinkage";
        public static var REFURBISH:String = "refurbish";
        public static var SORT_BY_EXP:String = "sortByExp";
        public static var FRIEND:String = "friend";
        public static var SORT_BY_GOLD:String = "sortByGold";

        public function PaginationFriend(param1:Class = null)
        {
            var _loc_2:int;
            var _loc_3:Friend;
            _friendList = [];
            if (param1 == null)
            {
                param1 = Friend;
            }// end if
            childCache = new Array();
            if (param1 == Friend)
            {
                this.ChildClass = param1;
                _loc_2 = 0;
                while (_loc_2 < 10)
                {
                    // label
                    _loc_3 = new param1;
                    childCache.push(_loc_3);
                    _loc_2++;
                }// end while
            }
            else
            {
                throw new Error("参数错误，只能是 hf.view.main.Friend");
            }// end else if
            UIinit();
            return;
        }// end function

        private function rollOver_next(param1:Event) : void
        {
            TipControl.show("MouseTip", Language.L["下一页"]);
            return;
        }// end function

        public function show(param1:Array, param2:int, param3:int) : void
        {
            var _loc_8:Timer;
            this.page = param2;
            this.countPage = param3;
            if (param1 == null)
            {
                throw new Error("show(参数) --> 参数错误!");
            }// end if
            del();
            childContainer.vScrollPosition = 0;
            _friendList = [];
            var _loc_4:int;
            while (_loc_4 < param1.length)
            {
                // label
                _friendList.push(param1[_loc_4]);
                _loc_4++;
            }// end while
            var _loc_5:* = new EmptyBox(1, _friendList.length * 32);
            new EmptyBox(1, _friendList.length * 32).name = "emptyBox";
            childContainer.addChild(_loc_5);
            if (currentlyTimer != null)
            {
                currentlyTimer.stop();
                currentlyTimer = null;
            }// end if
            var _loc_6:int;
            if (_friendList.length < 10)
            {
                _loc_6 = _friendList.length;
            }// end if
            var _loc_7:int;
            while (_loc_7 < _loc_6)
            {
                // label
                add(null);
                _loc_7++;
            }// end while
            if (_friendList.length > 0)
            {
                _loc_8 = new Timer(50, _friendList.length);
                _loc_8.addEventListener("timer", add);
                _loc_8.start();
                currentlyTimer = _loc_8;
            }// end if
            return;
        }// end function

        private function del() : void
        {
            if (currentlyTimer != null)
            {
                currentlyTimer.stop();
            }// end if
            var _loc_1:* = childContainer.numChildren;
            var _loc_2:int;
            while (_loc_2 < _loc_1)
            {
                // label
                if (childContainer.getChildAt(0).name != "emptyBox")
                {
                    childCache.push(childContainer.removeChildAt(0));
                }
                else
                {
                    childContainer.removeChildAt(0);
                }// end else if
                _loc_2++;
            }// end while
            return;
        }// end function

        public function get page() : int
        {
            return _page;
        }// end function

        public function sortByGold(param1:Event) : void
        {
            sortByGoldButtonA.visible = false;
            sortByGoldButtonB.visible = true;
            sortByExpButtonA.visible = true;
            sortByExpButtonB.visible = false;
            var _loc_2:* = new ViewEvent("sortByGold");
            this.dispatchEvent(_loc_2);
            loading.visible = MData.getInstance().mainData.getFriendListLoading;
            return;
        }// end function

        private function rollOut_search(param1:Event) : void
        {
            TipControl.hide();
            return;
        }// end function

        private function resetSeleted(param1:Event) : void
        {
            var _loc_2:* = childContainer.numChildren;
            var _loc_3:int;
            while (_loc_3 < _loc_2)
            {
                // label
                if (childContainer.getChildAt(_loc_3).name != "emptyBox")
                {
                    (childContainer.getChildAt(_loc_3) as Friend).selected = MData.getInstance().mainData.currentUserId;
                }// end if
                _loc_3++;
            }// end while
            return;
        }// end function

        private function add(param1:TimerEvent) : void
        {
            var _loc_2:Friend;
            var _loc_3:Object;
            if (childCache.length > 0)
            {
                _loc_2 = childCache.pop() as Friend;
            }
            else
            {
                _loc_2 = new Friend();
            }// end else if
            _loc_3 = _friendList[0];
            _friendList = _friendList.splice(1, _friendList.length--);
            _loc_2.fiendData = _loc_3;
            if (!_loc_3.me)
            {
                if (_loc_3.sort > 3)
                {
                    _loc_2.levelBg = level_0_skin;
                }
                else if (_loc_3.sort == 1)
                {
                    _loc_2.levelBg = level_1_skin;
                }
                else if (_loc_3.sort == 2)
                {
                    _loc_2.levelBg = level_2_skin;
                }
                else if (_loc_3.sort == 3)
                {
                    _loc_2.levelBg = level_3_skin;
                }// end else if
            }
            else
            {
                _loc_2.levelBg = level_me_skin;
            }// end else if
            _loc_2.selected = MData.getInstance().mainData.currentUserId;
            _loc_2.x = 0;
            _loc_2.y = childContainer.numChildren-- * 32;
            childContainer.addChildAt(_loc_2, 0);
            resetSeleted(null);
            return;
        }// end function

        private function UIinit() : void
        {
            shrinkageButton = new ToggleButton();
            leftContainer = new LipiCanvas();
            childContainer = new LipiCanvas();
            nextButton = new LipiButton();
            backButton = new LipiButton();
            searchTextField = new TextField();
            searchTextField.defaultTextFormat = new TextFormat("Tahoma");
            searchButton = new LipiButton();
            refurbishButton = new LipiButton();
            pageTextShow = new TextField();
            pageTextShow.defaultTextFormat = new TextFormat("Tahoma");
            loading = new DataLoading();
            var _loc_1:* = MaterialLib.getInstance();
            sortByGoldButtonA = _loc_1.getMaterial("GoldDefaultButton") as MovieClip;
            sortByGoldButtonA.buttonMode = true;
            sortByGoldButtonA.useHandCursor = true;
            sortByGoldButtonB = _loc_1.getMaterial("GoldSelectedButton") as Sprite;
            sortByGoldButtonB.buttonMode = true;
            sortByGoldButtonB.useHandCursor = true;
            sortByGoldButtonB.visible = false;
            sortByExpButtonA = _loc_1.getMaterial("ExpDefaultButton") as MovieClip;
            sortByExpButtonA.buttonMode = true;
            sortByExpButtonA.useHandCursor = true;
            sortByExpButtonB = _loc_1.getMaterial("ExpSelectedButton") as Sprite;
            sortByExpButtonB.buttonMode = true;
            sortByExpButtonB.useHandCursor = true;
            sortByExpButtonA.visible = false;
            addChild(shrinkageButton);
            addChild(leftContainer);
            leftContainer.addChild(childContainer);
            leftContainer.addChild(nextButton);
            leftContainer.addChild(backButton);
            leftContainer.addChild(refurbishButton);
            leftContainer.addChild(searchTextField);
            leftContainer.addChild(searchButton);
            leftContainer.addChild(pageTextShow);
            leftContainer.addChild(sortByGoldButtonA);
            leftContainer.addChild(sortByGoldButtonB);
            leftContainer.addChild(sortByExpButtonA);
            leftContainer.addChild(sortByExpButtonB);
            leftContainer.verticalScrollPolicy = ScrollPolicy.OFF;
            leftContainer.horizontalScrollPolicy = ScrollPolicy.OFF;
            childContainer.horizontalScrollPolicy = ScrollPolicy.OFF;
            shrinkageButton.width = 28.1;
            shrinkageButton.height = 73.2;
            shrinkageButton.x = 0;
            shrinkageButton.y = 160;
            shrinkageButton.bgColor = 16776960;
            shrinkageButton.bgAlpha = 0;
            shrinkageButton.addEventListener("mouseDown", shrinkage);
            leftContainer.width = 160.3;
            leftContainer.height = 392.4;
            leftContainer.x = 27;
            leftContainer.y = 0;
            leftContainer.bgAlpha = 0;
            searchTextField.border = true;
            searchTextField.type = TextFieldType.INPUT;
            searchTextField.borderColor = 10066329;
            searchTextField.x = 8;
            searchTextField.y = 5;
            searchTextField.width = 120;
            searchTextField.height = 18;
            searchTextField.addEventListener("change", search);
            var _loc_2:* = new Sprite();
            _loc_2.graphics.beginFill(16777215, 0.5);
            _loc_2.graphics.drawRect(0, 0, 17, 17);
            _loc_2.graphics.lineStyle(0.5, 26367, 0.8);
            _loc_2.graphics.moveTo(2, 2);
            _loc_2.graphics.lineTo(14, 14);
            _loc_2.graphics.moveTo(14, 2);
            _loc_2.graphics.lineTo(2, 14);
            _loc_2.graphics.endFill();
            _loc_2.x = 138;
            _loc_2.y = 6;
            _loc_2.mouseEnabled = true;
            _loc_2.buttonMode = true;
            _loc_2.visible = false;
            _loc_2.addEventListener(Event.ENTER_FRAME, showDel);
            _loc_2.addEventListener(MouseEvent.CLICK, delSearchText);
            _loc_2.addEventListener(MouseEvent.ROLL_OVER, rollOver_del);
            _loc_2.addEventListener(MouseEvent.ROLL_OUT, rollOut_del);
            addChild(_loc_2);
            searchButton.x = 137.5;
            searchButton.y = 5;
            searchButton.width = 20;
            searchButton.height = 20;
            searchButton.bgColor = 65535;
            searchButton.addEventListener("mouseDown", search);
            searchButton.bgAlpha = 0;
            searchButton.addEventListener(MouseEvent.ROLL_OVER, rollOver_search);
            searchButton.addEventListener(MouseEvent.ROLL_OUT, rollOut_search);
            sortByGoldButtonA.x = 72;
            sortByGoldButtonA.y = 31;
            sortByGoldButtonB.x = 72;
            sortByGoldButtonB.y = 31;
            sortByGoldButtonA.addEventListener(MouseEvent.MOUSE_OUT, gplay1);
            sortByGoldButtonA.addEventListener(MouseEvent.MOUSE_OVER, gplay2);
            sortByGoldButtonA.addEventListener(MouseEvent.MOUSE_DOWN, gplay3);
            sortByGoldButtonA.addEventListener(MouseEvent.MOUSE_UP, sortByGold);
            sortByExpButtonA.x = 8;
            sortByExpButtonA.y = 31;
            sortByExpButtonB.x = 8;
            sortByExpButtonB.y = 31;
            sortByExpButtonA.addEventListener(MouseEvent.MOUSE_OUT, eplay1);
            sortByExpButtonA.addEventListener(MouseEvent.MOUSE_OVER, eplay2);
            sortByExpButtonA.addEventListener(MouseEvent.MOUSE_DOWN, eplay3);
            sortByExpButtonA.addEventListener(MouseEvent.MOUSE_UP, sortByExp);
            refurbishButton.x = 137.5;
            refurbishButton.y = 31;
            refurbishButton.width = 20;
            refurbishButton.height = 20;
            refurbishButton.bgColor = 65535;
            refurbishButton.addEventListener("mouseDown", refurbish);
            refurbishButton.bgAlpha = 0;
            refurbishButton.addEventListener(MouseEvent.ROLL_OVER, rollOver_refurbish);
            refurbishButton.addEventListener(MouseEvent.ROLL_OUT, rollOut_refurbish);
            childContainer.x = 2;
            childContainer.y = 55;
            childContainer.width = 155.3;
            childContainer.height = 310;
            childContainer.bgColor = 13421772;
            childContainer.bgAlpha = 0.1;
            backButton.x = 40.5;
            backButton.y = 368;
            backButton.width = 20;
            backButton.height = 20;
            backButton.bgColor = 65520;
            backButton.addEventListener("mouseDown", back);
            backButton.bgAlpha = 0;
            backButton.addEventListener(MouseEvent.ROLL_OVER, rollOver_back);
            backButton.addEventListener(MouseEvent.ROLL_OUT, rollOut_back);
            pageTextShow.text = "0 / 0";
            pageTextShow.x = 61.6;
            pageTextShow.y = 368;
            pageTextShow.width = 38;
            pageTextShow.height = 20;
            pageTextShow.autoSize = "center";
            nextButton.x = 101.5;
            nextButton.y = 368;
            nextButton.width = 20;
            nextButton.height = 20;
            nextButton.bgColor = 65535;
            nextButton.addEventListener("mouseDown", next);
            nextButton.bgAlpha = 0;
            nextButton.addEventListener(MouseEvent.ROLL_OVER, rollOver_next);
            nextButton.addEventListener(MouseEvent.ROLL_OUT, rollOut_next);
            initSkin();
            loading.x = 110;
            loading.y = 200;
            loading.addEventListener(ViewEvent.LINK_CLICK, linkClick);
            loading.visible = MData.getInstance().mainData.getFriendListLoading;
            addChild(loading);
            MData.getInstance().mainData.addEventListener(MainData.CURRENT_USER_CHANGE, resetSeleted);
            MData.getInstance().mainData.addEventListener(MainData.GET_FRIEND_LIST_LOADING, dataLoaded);
            MData.getInstance().mainData.addEventListener(MainData.GET_FRIEND_LIST_ERR, friendListErr);
            return;
        }// end function

        private function rollOut_refurbish(param1:Event) : void
        {
            TipControl.hide();
            return;
        }// end function

        public function set page(param1:int) : void
        {
            _page = param1;
            pageTextShow.text = _page.toString() + " / " + _countPage.toString();
            if (_page == 1)
            {
                backButton.enable = false;
                backButton.buttonMode = false;
            }
            else
            {
                backButton.enable = true;
                backButton.buttonMode = true;
            }// end else if
            return;
        }// end function

        private function linkClick(param1:ViewEvent) : void
        {
            var _loc_2:ViewEvent;
            if (param1.data == "reload")
            {
                _loc_2 = new ViewEvent("refurbish");
                dispatchEvent(_loc_2);
            }// end if
            return;
        }// end function

        private function search(param1:Event) : void
        {
            var _loc_2:* = new ViewEvent("search");
            _loc_2.data = searchTextField.text;
            this.dispatchEvent(_loc_2);
            loading.visible = MData.getInstance().mainData.getFriendListLoading;
            return;
        }// end function

        public function set pageData(param1:Array) : void
        {
            _pageData = param1;
            return;
        }// end function

        private function initSkin() : void
        {
            var _loc_1:* = MaterialLib.getInstance();
            var _loc_2:* = shrinkageButton.y;
            var _loc_3:* = shrinkageButton.height;
            if (Version.value == Version.XIAOYOU)
            {
                shrinkageButton.selectedSkin = new LipiSkin(_loc_1.getClass("ShrinkageButtonSelectedTTXiaoYou"));
                shrinkageButton.defaultSkin = new LipiSkin(_loc_1.getClass("ShrinkageButtonDefaultTTXiaoYou"));
                shrinkageButton.y = _loc_2 - (118.2 - _loc_3) / 2;
                shrinkageButton.height = 118.2;
            }
            else if (Version.value == Version.QZONE)
            {
                shrinkageButton.selectedSkin = new LipiSkin(_loc_1.getClass("ShrinkageButtonSelectedTTQzone"));
                shrinkageButton.defaultSkin = new LipiSkin(_loc_1.getClass("ShrinkageButtonDefaultTTQzone"));
                shrinkageButton.y = _loc_2 - (88.2 - _loc_3) / 2;
                shrinkageButton.height = 88.2;
            }
            else
            {
                shrinkageButton.selectedSkin = new LipiSkin(_loc_1.getClass("ShrinkageButtonSelected"));
                shrinkageButton.defaultSkin = new LipiSkin(_loc_1.getClass("ShrinkageButtonDefault"));
            }// end else if
            leftContainer.bgSkin = new LipiSkin(_loc_1.getClass("FriendBg"));
            nextButton.bgSkin = new LipiSkin(_loc_1.getClass("FriendNextButton"));
            backButton.bgSkin = new LipiSkin(_loc_1.getClass("FriendBackButton"));
            searchButton.bgSkin = new LipiSkin(_loc_1.getClass("SearchButton"));
            refurbishButton.bgSkin = new LipiSkin(_loc_1.getClass("RefurbishButton"));
            level_0_skin = _loc_1.getClass("FriendSort0");
            level_1_skin = _loc_1.getClass("FriendSort1");
            level_2_skin = _loc_1.getClass("FriendSort2");
            level_3_skin = _loc_1.getClass("FriendSort3");
            level_me_skin = _loc_1.getClass("FriendSortMe");
            return;
        }// end function

        private function rollOver_search(param1:Event) : void
        {
            TipControl.show("MouseTip", Language.L["查找"]);
            return;
        }// end function

        private function eplay2(param1:Event) : void
        {
            sortByExpButtonA.gotoAndStop(2);
            return;
        }// end function

        private function rollOver_del(param1:Event) : void
        {
            TipControl.show("MouseTip", Language.L["删除"]);
            return;
        }// end function

        public function get countPage() : int
        {
            return _countPage;
        }// end function

        private function eplay1(param1:Event) : void
        {
            sortByExpButtonA.gotoAndStop(1);
            return;
        }// end function

        private function eplay3(param1:Event) : void
        {
            sortByExpButtonA.gotoAndStop(3);
            return;
        }// end function

        public function get seachText() : String
        {
            return searchTextField.text;
        }// end function

        private function refurbish(param1:Event) : void
        {
            var _loc_2:* = new ViewEvent("refurbish");
            this.dispatchEvent(_loc_2);
            loading.visible = MData.getInstance().mainData.getFriendListLoading;
            return;
        }// end function

        private function dataLoaded(param1:Event) : void
        {
            loading.visible = MData.getInstance().mainData.getFriendListLoading;
            return;
        }// end function

        private function showDel(param1:Event) : void
        {
            var _loc_2:* = param1.target as Sprite;
            _loc_2.visible = true;
            if (searchTextField.text != "")
            {
                _loc_2.visible = true;
            }
            else
            {
                _loc_2.visible = false;
            }// end else if
            return;
        }// end function

        private function shrinkage(param1:Event) : void
        {
            var _loc_2:* = new ViewEvent("shrinkage");
            if (!this.shrinkageButton.selected)
            {
                this.shrinkageButton.selected = true;
            }
            else
            {
                this.shrinkageButton.selected = false;
            }// end else if
            _loc_2.data = this.shrinkageButton.selected;
            this.dispatchEvent(_loc_2);
            return;
        }// end function

        public function get pageData() : Array
        {
            return _pageData;
        }// end function

        private function rollOut_del(param1:Event) : void
        {
            TipControl.hide();
            return;
        }// end function

        private function delSearchText(param1:Event) : void
        {
            searchTextField.text = "";
            search(null);
            return;
        }// end function

        private function rollOut_next(param1:Event) : void
        {
            TipControl.hide();
            return;
        }// end function

        private function rollOut_back(param1:Event) : void
        {
            TipControl.hide();
            return;
        }// end function

        private function friendListErr(param1:Event) : void
        {
            loading.errorText = MData.getInstance().mainData.getFriendListErr;
            return;
        }// end function

        public function sortByExp(param1:Event) : void
        {
            sortByGoldButtonA.visible = true;
            sortByGoldButtonB.visible = false;
            sortByExpButtonA.visible = false;
            sortByExpButtonB.visible = true;
            var _loc_2:* = new ViewEvent("sortByExp");
            this.dispatchEvent(_loc_2);
            loading.visible = MData.getInstance().mainData.getFriendListLoading;
            return;
        }// end function

        private function back(param1:Event) : void
        {
            if (_page == 1)
            {
                return;
            }// end if
            var _loc_2:* = new ViewEvent("back");
            _loc_2.data = searchTextField.text;
            this.dispatchEvent(_loc_2);
            loading.visible = MData.getInstance().mainData.getFriendListLoading;
            return;
        }// end function

        private function next(param1:Event) : void
        {
            if (_page == _countPage)
            {
                return;
            }// end if
            var _loc_2:* = new ViewEvent("next");
            _loc_2.data = searchTextField.text;
            this.dispatchEvent(_loc_2);
            loading.visible = MData.getInstance().mainData.getFriendListLoading;
            return;
        }// end function

        private function rollOver_back(param1:Event) : void
        {
            TipControl.show("MouseTip", Language.L["上一页"]);
            return;
        }// end function

        private function gplay1(param1:Event) : void
        {
            sortByGoldButtonA.gotoAndStop(1);
            return;
        }// end function

        private function gplay2(param1:Event) : void
        {
            sortByGoldButtonA.gotoAndStop(2);
            return;
        }// end function

        private function gplay3(param1:Event) : void
        {
            sortByGoldButtonA.gotoAndStop(3);
            return;
        }// end function

        public function set countPage(param1:int) : void
        {
            _countPage = param1;
            pageTextShow.text = _page.toString() + " / " + _countPage.toString();
            if (_page == _countPage)
            {
                nextButton.enable = false;
                nextButton.buttonMode = false;
            }
            else
            {
                nextButton.enable = true;
                nextButton.buttonMode = false;
            }// end else if
            return;
        }// end function

        private function rollOver_refurbish(param1:Event) : void
        {
            TipControl.show("MouseTip", Language.L["刷新"]);
            return;
        }// end function

    }
}
