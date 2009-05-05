package com.facebook.data.users
{

    public class UserData extends Object
    {
        public var affiations:AffiliationCollection;
        public var name:String;
        public var uid:Number;
        public var timezone:Number;
        public var first_name:String;
        public var last_name:String;

        public function UserData()
        {
            return;
        }// end function

        public function toString() : String
        {
            return "[ UserData uid: " + uid + " affiation:" + affiations + " first_name:" + first_name + " last_name:" + last_name + " name:" + name + " timezone: " + timezone + "]";
        }// end function

    }
}
