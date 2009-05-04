package hf.model
{
    import flash.events.*;

    public class FarmData extends EventDispatcher
    {
        private var _userSeedLoading:Boolean = false;
        private var _userSeed:Array;
        private var _addHealth:Object;
        private var _farmOperate:Object;
        private var _userSeedErr:String = "";
        private var _harvestData:Object;
        private var _reloadUserSeed:Boolean = true;
        public static const HARVEST_DATA:String = "harvestData";
        public static const USER_SEED:String = "userSeedChange";
        public static const USER_SEED_LOADING:String = "userSeedLoading";
        public static const USER_SEED_ERR:String = "userSeedErr";
        public static const ADD_HEALTH:String = "addHealth";
        public static const FARM_OPERATE:String = "farmOperate";

        public function FarmData()
        {
            return;
        }// end function

        public function set userSeedLoading(param1:Boolean) : void
        {
            _userSeedLoading = param1;
            dispatchEvent(new ModelEvent(FarmData.USER_SEED_LOADING));
            return;
        }// end function

        public function get userSeedLoading() : Boolean
        {
            return _userSeedLoading;
        }// end function

        public function set addHealth(param1:Object) : void
        {
            _addHealth = param1;
            dispatchEvent(new ModelEvent(FarmData.ADD_HEALTH));
            return;
        }// end function

        public function set userSeedErr(param1:String) : void
        {
            _userSeedErr = param1;
            dispatchEvent(new ModelEvent(FarmData.USER_SEED_ERR));
            return;
        }// end function

        public function get reloadUserSeed() : Boolean
        {
            return _reloadUserSeed;
        }// end function

        public function set farmOperate(param1:Object) : void
        {
            _farmOperate = param1;
            dispatchEvent(new ModelEvent(FarmData.FARM_OPERATE));
            return;
        }// end function

        public function get addHealth() : Object
        {
            return _addHealth;
        }// end function

        public function get userSeed() : Array
        {
            return _userSeed;
        }// end function

        public function set harvestData(param1:Object) : void
        {
            _harvestData = param1;
            dispatchEvent(new ModelEvent(FarmData.HARVEST_DATA));
            return;
        }// end function

        public function get userSeedErr() : String
        {
            return _userSeedErr;
        }// end function

        public function set reloadUserSeed(param1:Boolean) : void
        {
            _reloadUserSeed = param1;
            return;
        }// end function

        public function get farmOperate() : Object
        {
            return _farmOperate;
        }// end function

        public function set userSeed(param1:Array) : void
        {
            _userSeed = param1;
            dispatchEvent(new ModelEvent(FarmData.USER_SEED));
            return;
        }// end function

        public function get harvestData() : Object
        {
            return _harvestData;
        }// end function

    }
}
