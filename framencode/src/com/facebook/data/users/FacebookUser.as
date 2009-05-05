package com.facebook.data.users
{
    import com.facebook.data.*;

    public class FacebookUser extends FacebookData
    {
        public var timezone:int;
        public var tv:String;
        public var affiliations:Array;
        public var profile_update_time:Date;
        public var interests:String;
        public var pic:String;
        public var name:String = "";
        public var birthday:String;
        public var grad_year:String;
        public var about_me:String;
        public var pic_small:String;
        public var wall_count:int;
        public var education_history:Array;
        public var notes_count:int;
        public var last_name:String = "";
        public var music:String;
        public var books:String;
        public var hs1_name:String;
        public var is_app_user:Boolean;
        public var religion:String;
        public var has_added_app:Boolean;
        public var friends:Array;
        public var first_name:String = "";
        public var current_location:FacebookLocation;
        public var significant_other_id:int;
        public var movies:String;
        public var meeting_for:Array;
        public var pic_big:String;
        public var uid:Number;
        public var hometown_location:FacebookLocation;
        public var hs2_id:int;
        public var networkAffiliations:Array;
        public var relationship_status:String;
        public var hs2_name:String;
        public var sex:String;
        public var meeting_sex:Array;
        public var activities:String;
        public var pic_square:String;
        public var work_history:Array;
        public var quotes:String;
        public var status:StatusData;
        public var hs1_id:int;
        public var isLoggedInUser:Boolean;
        public var political:String;

        public function FacebookUser() : void
        {
            name = "";
            first_name = "";
            last_name = "";
            return;
        }// end function

    }
}
