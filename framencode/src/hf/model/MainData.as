package hf.model
{
    import flash.events.*;
    import flash.utils.*;

    public class MainData extends EventDispatcher
    {
        private var _potatoChips:String = "";
        private var _sessionTimeout:String = "";
        private var _getFriendListErr:String;
        private var _levelReward:Object;
        private var _seedInfo:Array;
        private var _welcome:Boolean = false;
        private var _addFB:int = 0;
        private var _diyInfo:Array;
        private var _searchFriendValue:String = "";
        private var _potatoNumber:int = 0;
        private var startMakePotatoChips:Boolean = false;
        private var _killBigInsect:Boolean = false;
        private var _serverTime:Number = 0;
        private var _farmlandData:Array;
        private var _userCrop:Array;
        private var _shopDiyErr:String = "";
        private var _profile:Object;
        private var _shopSeedLoading:Boolean = false;
        private var _dogFood:Object;
        private var _sortField:String = "exp";
        private var dogDizzyData:Object;
        private var _host:Object;
        private var _setUserError:String = "";
        private var _profileErr:String = "";
        private var _farmlandDataIndex:int;
        private var _userCropErr:String = "";
        private var _weather:String = "";
        private var _moduleName:String = "farm";
        private var _items:Object;
        private var _unread:Object;
        private var _clientTime:Number = 0;
        private var _addExp:int = 0;
        private var _hasPotatoChips:int = 0;
        private var _runComp:Boolean = false;
        private var _selfDogFood:Object;
        private var _shopToolErr:String = "";
        public var reloadUserItems:Boolean = true;
        private var _currentUser:Object;
        private var _userItems:Array;
        private var _userChanging:Boolean = false;
        private var _giftPackage:Object;
        private var _userItemErr:String = "";
        private var _showFriendPage:int = 1;
        private var _showFriendList:Array;
        private var _hasDog:Boolean = false;
        private var _shopToolLoading:Boolean = false;
        private var _getFriendListLoading:Boolean = false;
        private var _profileLoading:Boolean = false;
        private var _userCropLoading:Boolean = false;
        private var _dogInitFirst:Boolean = true;
        private var _userItemLoading:Boolean = false;
        private var _shopSeedErr:String = "";
        private var _toolsInfo:Array;
        private var _runError:String = "";
        public var reloadUserCrop:Boolean = true;
        private var _shopDiyLoading:Boolean = false;
        private var _dogData:Object;
        private var _killBigInsectExp:int = 0;
        private var potatoMachineData:Boolean = false;
        private var _friendList:Array;
        private var _addMoney:int = 0;
        public static const LEVEL_REWARD:String = "levelReward";
        public static const USER_CROP:String = "userCrop";
        public static const DOG_DATA:String = "dogData";
        public static const PROFILE:String = "profile";
        public static const USER_CROP_ERR:String = "userCropErr";
        public static const DOG_FOOD_FEED_IN_SHOP:String = "dogFoodFeedInShop";
        public static const SHOP_DIY_LOADING:String = "shopDiyLoading";
        public static const DOG_DIZZY:String = "dogDizzy";
        public static const RUN_ERROR:String = "runError";
        public static const PROFILE_ERR:String = "profileErr";
        public static const GIFT_PACKAGE:String = "giftPackage";
        public static const SET_USER_ERROR:String = "setUserError";
        public static const DIY_INFO:String = "diyInfo";
        public static const USER_ITEM_ERR:String = "userItemErr";
        public static const SHOP_TOOL_LOADING:String = "shopToolLoading";
        public static const ITEMS:String = "items";
        public static const GET_FRIEND_LIST_ERR:String = "getFriendListErr";
        public static const HOST_CHANGE:String = "hostChange";
        public static const DOG_FOOD_DATA:String = "dogFoodData";
        public static const USER_ITEMS:String = "userItems";
        public static const SHOP_SEED_ERR:String = "shopSeedErr";
        public static const ADD_FB:String = "addFB";
        public static const FARMLAND_DATA_INDEX:String = "farmlandDataIndex";
        public static const POTATO_AWARD:String = "potatoAward";
        public static const CURRENT_USER_CHANGE:String = "currentUserChange";
        public static const ADD_EXP:String = "addExp";
        public static const UNREAD_DATA:String = "unreadData";
        public static const SEED_INFO:String = "seedInfo";
        public static const FARMLAND_DATA_CHANGE:String = "farmlandDataChange";
        public static const WELCOME:String = "welcome";
        public static const POTATO_MAKE_DATA:String = "potatoMakeData";
        public static const SHOP_DIY_ERR:String = "shopDiyErr";
        public static const WEATHER:String = "weather";
        public static const POTATO_MACHINE:String = "potatoMachine";
        public static const SHOP_TOOL_ERR:String = "shopToolErr";
        public static const POTATO_CHIPS_OPEN:String = "potatoChipsOpen";
        public static const USER_CROP_LOADING:String = "userCropLoading";
        public static const MODULE_CHANGE:String = "moduleChange";
        public static const USER_ITEM_LOADING:String = "userItemLoading";
        public static const PROFILE_LOADING:String = "profileLoading";
        public static const USER_CHANGING:String = "userChanging";
        public static const ADD_MONEY:String = "addMoney";
        public static const RUN_COMP:String = "runComp";
        public static const POTATO_MACHINE_RUNNING:String = "potatoMachineRunning";
        public static const FRIEND_CHANGE:String = "friendChange";
        public static const GET_FRIEND_LIST_LOADING:String = "getFriendListLoading";
        public static const TOOL_INFO:String = "toolInfo";
        public static const SHOP_SEED_LOADING:String = "shopSeedLoading";
        public static const SESSION_TIMEOUT:String = "sessionTimeout";

        public function MainData()
        {
            _farmlandData = [];
            return;
        }// end function

        public function get friendPageNum() : int
        {
            return 50;
        }// end function

        public function set dogFeed(param1:Object) : void
        {
            if (param1["code"] == 1 && me)
            {
                dogData.isHungry = 0;
                dogData = dogData;
            }// end if
            return;
        }// end function

        public function set userCrop(param1:Array) : void
        {
            _userCrop = param1;
            dispatchEvent(new ModelEvent(MainData.USER_CROP));
            return;
        }// end function

        public function get sortField() : String
        {
            return _sortField;
        }// end function

        public function set dogData(param1:Object) : void
        {
            if (_dogInitFirst)
            {
                _dogInitFirst = false;
                if (param1["dogId"] != 0)
                {
                    hasDog = true;
                }
                else
                {
                    hasDog = false;
                }// end if
            }// end else if
            _dogData = param1;
            dispatchEvent(new ModelEvent(MainData.DOG_DATA));
            return;
        }// end function

        public function set userItemErr(param1:String) : void
        {
            _userItemErr = param1;
            dispatchEvent(new ModelEvent(MainData.USER_ITEM_ERR));
            return;
        }// end function

        public function get filterFriendList() : Array
        {
            var _loc_1:* = friendList.filter(friendFilter);
            return _loc_1;
        }// end function

        public function get potatoMachine() : Boolean
        {
            return potatoMachineData;
        }// end function

        public function set moduleName(param1:String) : void
        {
            _moduleName = param1;
            dispatchEvent(new ModelEvent(MainData.MODULE_CHANGE));
            return;
        }// end function

        public function get runComp() : Boolean
        {
            return _runComp;
        }// end function

        public function get runError() : String
        {
            return _runError;
        }// end function

        public function set potatoMachine(param1:Boolean) : void
        {
            potatoMachineData = param1;
            dispatchEvent(new ModelEvent(MainData.POTATO_MACHINE));
            return;
        }// end function

        public function set addFB(param1:int) : void
        {
            _addFB = param1;
            dispatchEvent(new ModelEvent(MainData.ADD_FB));
            return;
        }// end function

        public function get hasDog() : Boolean
        {
            return _hasDog;
        }// end function

        public function set farmlandDataIndex(param1:int) : void
        {
            _farmlandDataIndex = param1;
            dispatchEvent(new ModelEvent(MainData.FARMLAND_DATA_INDEX));
            return;
        }// end function

        public function get showFriendSum() : int
        {
            var _loc_1:int;
            if (friendList != null)
            {
                if (searchFriendValue == "")
                {
                    _loc_1 = Math.ceil(friendList.length / friendPageNum);
                }
                else
                {
                    _loc_1 = Math.ceil(filterFriendList.length / friendPageNum);
                }// end if
            }// end else if
            return _loc_1 == 0 ? (1) : (_loc_1);
        }// end function

        public function set toolsInfo(param1:Array) : void
        {
            _toolsInfo = param1;
            dispatchEvent(new ModelEvent(MainData.TOOL_INFO));
            return;
        }// end function

        public function set showFriendPage(param1:int) : void
        {
            _showFriendPage = param1;
            return;
        }// end function

        public function get potatoNumber() : int
        {
            return _potatoNumber;
        }// end function

        public function get killBigInsect() : Boolean
        {
            return _killBigInsect;
        }// end function

        public function get friendList() : Array
        {
            return _friendList;
        }// end function

        public function get userCropErr() : String
        {
            return _userCropErr;
        }// end function

        public function get farmlandData() : Array
        {
            return _farmlandData;
        }// end function

        public function set showFriendList(param1:Array) : void
        {
            _showFriendList = param1;
            dispatchEvent(new ModelEvent(MainData.FRIEND_CHANGE));
            return;
        }// end function

        public function set runComp(param1:Boolean) : void
        {
            _runComp = param1;
            dispatchEvent(new ModelEvent(MainData.RUN_COMP));
            return;
        }// end function

        public function set runError(param1:String) : void
        {
            _runError = param1;
            dispatchEvent(new ModelEvent(MainData.RUN_ERROR));
            return;
        }// end function

        public function set hasDog(param1:Boolean) : void
        {
            _hasDog = param1;
            return;
        }// end function

        public function get getFriendListLoading() : Boolean
        {
            return _getFriendListLoading;
        }// end function

        public function get addMoney() : int
        {
            return _addMoney;
        }// end function

        public function set potatoNumber(param1:int) : void
        {
            _potatoNumber = param1;
            reloadUserCrop = true;
            return;
        }// end function

        public function set killBigInsect(param1:Boolean) : void
        {
            _killBigInsect = param1;
            return;
        }// end function

        public function set profileLoading(param1:Boolean) : void
        {
            _profileLoading = param1;
            dispatchEvent(new ModelEvent(MainData.PROFILE_LOADING));
            return;
        }// end function

        public function get searchFriendValue() : String
        {
            return _searchFriendValue;
        }// end function

        public function set userChanging(param1:Boolean) : void
        {
            _userChanging = param1;
            dispatchEvent(new ModelEvent(MainData.USER_CHANGING));
            return;
        }// end function

        public function get serverTime() : Number
        {
            if (_serverTime == 0)
            {
                return Math.floor(new Date().getTime() / 1000);
            }// end if
            return _serverTime + (Math.floor(getTimer() / 1000) - _clientTime);
        }// end function

        public function set friendList(param1:Array) : void
        {
            _friendList = param1;
            return;
        }// end function

        public function get dogDizzyState() : Object
        {
            return dogDizzyData;
        }// end function

        public function get setUserError() : String
        {
            return _setUserError;
        }// end function

        public function set shopSeedErr(param1:String) : void
        {
            _shopSeedErr = param1;
            dispatchEvent(new ModelEvent(MainData.SHOP_SEED_ERR));
            return;
        }// end function

        public function set welcome(param1:Boolean) : void
        {
            _welcome = param1;
            dispatchEvent(new ModelEvent(MainData.WELCOME));
            return;
        }// end function

        public function get seedInfo() : Array
        {
            return _seedInfo;
        }// end function

        public function get shopDiyErr() : String
        {
            return _shopDiyErr;
        }// end function

        public function set diyInfo(param1:Array) : void
        {
            _diyInfo = param1;
            dispatchEvent(new ModelEvent(MainData.DIY_INFO));
            return;
        }// end function

        public function get shopSeedLoading() : Boolean
        {
            return _shopSeedLoading;
        }// end function

        public function set userCropErr(param1:String) : void
        {
            _userCropErr = param1;
            dispatchEvent(new ModelEvent(MainData.USER_CROP_ERR));
            return;
        }// end function

        public function get selfDogFood() : Object
        {
            return _selfDogFood;
        }// end function

        public function set farmlandData(param1:Array) : void
        {
            _farmlandData = param1;
            dispatchEvent(new ModelEvent(MainData.FARMLAND_DATA_CHANGE));
            return;
        }// end function

        public function get userItems() : Array
        {
            return _userItems;
        }// end function

        public function chipsEventDispatch(param1:Object) : void
        {
            var _loc_2:* = new ModelEvent(MainData.POTATO_AWARD);
            _loc_2.data = param1;
            dispatchEvent(_loc_2);
            return;
        }// end function

        public function get currentUser() : Object
        {
            return _currentUser;
        }// end function

        public function get shopToolErr() : String
        {
            return _shopToolErr;
        }// end function

        public function get hasPotatoChips() : int
        {
            return _hasPotatoChips;
        }// end function

        public function set killBigInsectExp(param1:int) : void
        {
            _killBigInsectExp = param1;
            return;
        }// end function

        public function get giftPackage() : Object
        {
            return _giftPackage;
        }// end function

        public function get userCrop() : Array
        {
            return _userCrop;
        }// end function

        public function get profileErr() : String
        {
            return _profileErr;
        }// end function

        public function get userItemErr() : String
        {
            return _userItemErr;
        }// end function

        public function get dogData() : Object
        {
            return _dogData;
        }// end function

        public function set getFriendListLoading(param1:Boolean) : void
        {
            _getFriendListLoading = param1;
            dispatchEvent(new ModelEvent(MainData.GET_FRIEND_LIST_LOADING));
            return;
        }// end function

        public function get moduleName() : String
        {
            return _moduleName;
        }// end function

        public function get addFB() : int
        {
            return _addFB;
        }// end function

        public function get currentUserId() : String
        {
            if (currentUser != null)
            {
                return currentUser["uId"];
            }// end if
            return "";
        }// end function

        public function openWinEventDispatch() : void
        {
            dispatchEvent(new ModelEvent(MainData.POTATO_CHIPS_OPEN));
            return;
        }// end function

        public function get farmlandDataIndex() : int
        {
            return _farmlandDataIndex;
        }// end function

        public function get toolsInfo() : Array
        {
            return _toolsInfo;
        }// end function

        public function get showFriendPage() : int
        {
            return _showFriendPage;
        }// end function

        public function get showFriendList() : Array
        {
            return _showFriendList;
        }// end function

        public function set shopDiyLoading(param1:Boolean) : void
        {
            _shopDiyLoading = param1;
            dispatchEvent(new ModelEvent(MainData.SHOP_DIY_LOADING));
            return;
        }// end function

        public function set addMoney(param1:int) : void
        {
            _addMoney = param1;
            dispatchEvent(new ModelEvent(MainData.ADD_MONEY));
            return;
        }// end function

        public function set profile(param1:Object) : void
        {
            _profile = param1;
            dispatchEvent(new ModelEvent(MainData.PROFILE));
            return;
        }// end function

        public function get profileLoading() : Boolean
        {
            return _profileLoading;
        }// end function

        public function set searchFriendValue(param1:String) : void
        {
            _searchFriendValue = param1;
            return;
        }// end function

        public function set host(param1:Object) : void
        {
            _host = param1;
            dispatchEvent(new ModelEvent(MainData.HOST_CHANGE));
            return;
        }// end function

        public function get userChanging() : Boolean
        {
            return _userChanging;
        }// end function

        public function set shopToolLoading(param1:Boolean) : void
        {
            _shopToolLoading = param1;
            dispatchEvent(new ModelEvent(MainData.SHOP_TOOL_LOADING));
            return;
        }// end function

        public function get shopSeedErr() : String
        {
            return _shopSeedErr;
        }// end function

        public function set serverTime(param1:Number) : void
        {
            _serverTime = param1;
            _clientTime = int(getTimer() / 1000);
            return;
        }// end function

        public function get welcome() : Boolean
        {
            return _welcome;
        }// end function

        public function set getFriendListErr(param1:String) : void
        {
            _getFriendListErr = param1;
            dispatchEvent(new ModelEvent(MainData.GET_FRIEND_LIST_ERR));
            return;
        }// end function

        public function get diyInfo() : Array
        {
            return _diyInfo;
        }// end function

        public function set shopSeedLoading(param1:Boolean) : void
        {
            _shopSeedLoading = param1;
            dispatchEvent(new ModelEvent(MainData.SHOP_SEED_LOADING));
            return;
        }// end function

        public function set potatoMachineRuning(param1:Boolean) : void
        {
            startMakePotatoChips = param1;
            dispatchEvent(new ModelEvent(MainData.POTATO_MACHINE_RUNNING));
            return;
        }// end function

        public function set dogDizzyState(param1:Object) : void
        {
            dogDizzyData = param1;
            dispatchEvent(new ModelEvent(MainData.DOG_DIZZY));
            return;
        }// end function

        public function get killBigInsectExp() : int
        {
            return _killBigInsectExp;
        }// end function

        public function set dogFood(param1:Object) : void
        {
            if (me)
            {
                _selfDogFood = param1;
            }// end if
            _dogFood = param1;
            dispatchEvent(new ModelEvent(MainData.DOG_FOOD_DATA));
            return;
        }// end function

        public function set setUserError(param1:String) : void
        {
            _setUserError = param1;
            dispatchEvent(new ModelEvent(MainData.SET_USER_ERROR));
            return;
        }// end function

        public function set addExp(param1:int) : void
        {
            _addExp = param1;
            dispatchEvent(new ModelEvent(MainData.ADD_EXP));
            return;
        }// end function

        public function set seedInfo(param1:Array) : void
        {
            _seedInfo = param1;
            dispatchEvent(new ModelEvent(MainData.SEED_INFO));
            return;
        }// end function

        public function set items(param1:Object) : void
        {
            _items = param1;
            dispatchEvent(new ModelEvent(MainData.ITEMS));
            return;
        }// end function

        public function set potatoChips(param1:String) : void
        {
            _potatoChips = param1;
            dispatchEvent(new ModelEvent(MainData.POTATO_MAKE_DATA));
            return;
        }// end function

        public function set userCropLoading(param1:Boolean) : void
        {
            _userCropLoading = param1;
            dispatchEvent(new ModelEvent(MainData.USER_CROP_LOADING));
            return;
        }// end function

        public function set userItems(param1:Array) : void
        {
            _userItems = param1;
            dispatchEvent(new ModelEvent(MainData.USER_ITEMS));
            return;
        }// end function

        public function set shopDiyErr(param1:String) : void
        {
            _shopDiyErr = param1;
            dispatchEvent(new ModelEvent(MainData.SHOP_DIY_ERR));
            return;
        }// end function

        public function set hasPotatoChips(param1:int) : void
        {
            _hasPotatoChips = param1;
            reloadUserCrop = true;
            return;
        }// end function

        public function get me() : Boolean
        {
            var _loc_1:Boolean;
            if (currentUser != null && host != null)
            {
                if (currentUser["uId"] != host["uId"])
                {
                    _loc_1 = false;
                }// end if
            }// end if
            return _loc_1;
        }// end function

        public function get shopDiyLoading() : Boolean
        {
            return _shopDiyLoading;
        }// end function

        public function set selfDogFood(param1:Object) : void
        {
            _selfDogFood = param1;
            dispatchEvent(new ModelEvent(MainData.DOG_FOOD_FEED_IN_SHOP));
            return;
        }// end function

        public function get host() : Object
        {
            return _host;
        }// end function

        public function get profile() : Object
        {
            return _profile;
        }// end function

        public function set levelReward(param1:Object) : void
        {
            _levelReward = param1;
            dispatchEvent(new ModelEvent(MainData.LEVEL_REWARD));
            return;
        }// end function

        public function get getFriendListErr() : String
        {
            return _getFriendListErr;
        }// end function

        public function get shopToolLoading() : Boolean
        {
            return _shopToolLoading;
        }// end function

        private function friendFilter(param1, param2:int, param3:Array) : Boolean
        {
            var _loc_4:* = param1["userName"];
            return new RegExp(".*?" + searchFriendValue + ".*", "i").test(_loc_4);
        }// end function

        public function get potatoMachineRuning() : Boolean
        {
            return startMakePotatoChips;
        }// end function

        public function get dogFood() : Object
        {
            return _dogFood;
        }// end function

        public function set userItemLoading(param1:Boolean) : void
        {
            _userItemLoading = param1;
            dispatchEvent(new ModelEvent(MainData.USER_ITEM_LOADING));
            return;
        }// end function

        public function get items() : Object
        {
            return _items;
        }// end function

        public function get userCropLoading() : Boolean
        {
            return _userCropLoading;
        }// end function

        public function get addExp() : int
        {
            return _addExp;
        }// end function

        public function set shopToolErr(param1:String) : void
        {
            _shopToolErr = param1;
            dispatchEvent(new ModelEvent(MainData.SHOP_TOOL_ERR));
            return;
        }// end function

        public function set currentUser(param1:Object) : void
        {
            _currentUser = param1;
            dispatchEvent(new ModelEvent(MainData.CURRENT_USER_CHANGE));
            return;
        }// end function

        public function get potatoChips() : String
        {
            return _potatoChips;
        }// end function

        public function get levelReward() : Object
        {
            return _levelReward;
        }// end function

        public function set sessionTimeout(param1:String) : void
        {
            _sessionTimeout = param1;
            dispatchEvent(new ModelEvent(MainData.SESSION_TIMEOUT));
            return;
        }// end function

        public function get userItemLoading() : Boolean
        {
            return _userItemLoading;
        }// end function

        public function set weather(param1:String) : void
        {
            _weather = param1;
            dispatchEvent(new ModelEvent(MainData.WEATHER));
            return;
        }// end function

        public function get sessionTimeout() : String
        {
            return _sessionTimeout;
        }// end function

        public function set giftPackage(param1:Object) : void
        {
            _giftPackage = param1;
            dispatchEvent(new ModelEvent(MainData.GIFT_PACKAGE));
            return;
        }// end function

        public function set unreadData(param1:Object) : void
        {
            _unread = param1;
            dispatchEvent(new ModelEvent(MainData.UNREAD_DATA));
            return;
        }// end function

        public function get weather() : String
        {
            return _weather;
        }// end function

        public function set sortField(param1:String) : void
        {
            _sortField = param1;
            return;
        }// end function

        public function get unreadData() : Object
        {
            return _unread;
        }// end function

        public function set profileErr(param1:String) : void
        {
            _profileErr = param1;
            dispatchEvent(new ModelEvent(MainData.PROFILE_ERR));
            return;
        }// end function

    }
}
