package com.facebook.utils
{
    import flash.system.*;

    public class PlayerUtils extends Object
    {
        static var versionObj:Object;

        public function PlayerUtils()
        {
            return;
        }// end function

        public static function get internalBuildNumber() : Number
        {
            return parseVersionString().internalBuildNumber;
        }// end function

        public static function get platform() : String
        {
            return parseVersionString().platform;
        }// end function

        public static function get buildNumber() : Number
        {
            return parseVersionString().buildNumber;
        }// end function

        public static function get minorVersion() : Number
        {
            return parseVersionString().minorVersion;
        }// end function

        public static function parseVersionString() : Object
        {
            var _loc_1:String;
            var _loc_2:Array;
            if (versionObj != null)
            {
                return versionObj;
            }// end if
            _loc_1 = Capabilities.version;
            versionObj = {};
            _loc_2 = _loc_1.split(" ");
            versionObj.platform = _loc_2[0];
            _loc_2.shift();
            _loc_2 = _loc_2[0].split(",");
            versionObj.majorVersion = Number(_loc_2[0]);
            versionObj.minorVersion = Number(_loc_2[1]);
            versionObj.buildNumber = Number(_loc_2[2]);
            versionObj.internalBuildNumber = Number(_loc_2[3]);
            return versionObj;
        }// end function

        public static function get majorVersion() : Number
        {
            return parseVersionString().majorVersion;
        }// end function

    }
}
