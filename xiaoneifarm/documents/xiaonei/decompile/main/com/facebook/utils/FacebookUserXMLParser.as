package com.facebook.utils
{
    import com.facebook.data.*;
    import com.facebook.data.users.*;

    public class FacebookUserXMLParser extends Object
    {

        public function FacebookUserXMLParser()
        {
            return;
        }// end function

        public static function createFacebookUser(param1:XML, param2:Namespace) : FacebookUser
        {
            var _loc_3:XMLList;
            var _loc_4:XMLList;
            var _loc_5:FacebookUser;
            var _loc_6:XML;
            var _loc_7:StatusData;
            var _loc_8:XMLList;
            var _loc_9:*;
            var _loc_10:FacebookNetwork;
            var _loc_11:XMLList;
            var _loc_12:Number;
            var _loc_13:Number;
            var _loc_14:XMLList;
            var _loc_15:XML;
            var _loc_16:XMLList;
            var _loc_17:Object;
            var _loc_18:FacebookEducationInfo;
            var _loc_19:XML;
            var _loc_20:Object;
            var _loc_21:FacebookWorkInfo;
            _loc_5 = new FacebookUser();
            _loc_5.uid = param1..param2::uid.toString();
            if (param1.name)
            {
                _loc_5.name = param2::name.toString();
            }// end if
            if (param1..param2::first_name)
            {
                _loc_5.first_name = param1..param2::first_name.toString();
            }// end if
            if (param1..param2::last_name)
            {
                _loc_5.last_name = param1..param2::last_name.toString();
            }// end if
            if (param1..param2::pic_small)
            {
                _loc_5.pic_small = param1..param2::pic_small.toString();
            }// end if
            if (param1..param2::pic_big)
            {
                _loc_5.pic_big = param1..param2::pic_big.toString();
            }// end if
            if (param1..param2::pic_square)
            {
                _loc_5.pic_square = param1..param2::pic_square.toString();
            }// end if
            if (param1..param2::pic)
            {
                _loc_5.pic = param1.pic.toString();
            }// end if
            if (param2::status[0])
            {
                _loc_6 = param2::status[0];
                _loc_7 = new StatusData();
                _loc_7.message = String(param2::message);
                _loc_7.time = FacebookDataUtils.formatDate(String(param2::time));
                _loc_5.status = _loc_7;
            }// end if
            if (param1..param2::affiliations)
            {
                _loc_5.affiliations = [];
                _loc_8 = param1..param2::affiliation;
                for each (_loc_9 in _loc_8)
                {
                    // label
                    _loc_10 = new FacebookNetwork();
                    _loc_10.nid = parseInt(param2::nid);
                    _loc_10.name = String(param2::name);
                    _loc_10.type = String(param2::type);
                    _loc_10.status = String(param2::status);
                    _loc_10.year = String(param2::year);
                    _loc_5.affiliations.push(_loc_10);
                }// end of for each ... in
            }// end if
            if (param1..param2::hometown_location)
            {
                _loc_5.hometown_location = new FacebookLocation();
                _loc_5.hometown_location.city = String(param1..param2::hometown_location.city);
                _loc_5.hometown_location.state = String(param1..param2::hometown_location.state);
                _loc_5.hometown_location.country = String(param1..param2::hometown_location.country);
                _loc_5.hometown_location.zip = String(param1..param2::hometown_location.zip);
            }// end if
            if (param1..param2::profile_update_time)
            {
                _loc_5.profile_update_time = FacebookDataUtils.formatDate(param1..param2::profile_update_time.toString());
            }// end if
            if (param1..param2::timezone)
            {
                _loc_5.timezone = parseInt(param1..param2::timezone);
            }// end if
            if (param1..param2::religion)
            {
                _loc_5.religion = param1..param2::religion.toString();
            }// end if
            if (param1..param2::birthday)
            {
                _loc_5.birthday = param1..param2::birthday.toString();
            }// end if
            if (param2::sex)
            {
                _loc_5.sex = param2::sex.toString();
            }// end if
            if (param1..param2::political)
            {
                _loc_5.political = param1..param2::political.toString();
            }// end if
            if (param1..param2::notes_count)
            {
                _loc_5.notes_count = param1..param2::notes_count.toString();
            }// end if
            if (param1..param2::wall_count)
            {
                _loc_5.wall_count = param1..param2::wall_count.toString();
            }// end if
            if (param2::meeting_sex)
            {
                _loc_5.meeting_sex = [];
                _loc_11 = param2::meeting_sex.children();
                _loc_12 = _loc_11.length();
                _loc_13 = 0;
                while (_loc_13++ < _loc_12)
                {
                    // label
                    _loc_5.meeting_sex.push(_loc_11[_loc_13].toString());
                }// end while
            }// end if
            if (param2::meeting_for)
            {
                _loc_5.meeting_for = [];
                _loc_14 = param2::meeting_for.children();
                _loc_12 = _loc_14.length();
                _loc_13 = 0;
                while (_loc_13++ < _loc_12)
                {
                    // label
                    _loc_5.meeting_for.push(_loc_14[_loc_13].toString());
                }// end while
            }// end if
            if (param2::relationship_status)
            {
                _loc_5.relationship_status = param2::relationship_status.toString();
            }// end if
            if (param2::significant_other_id)
            {
                _loc_5.significant_other_id = parseInt(param2::significant_other_id);
            }// end if
            if (param1..param2::hometown_location)
            {
                _loc_5.hometown_location = new FacebookLocation();
                _loc_3 = param1..param2::hometown_location;
                _loc_5.hometown_location.city = _loc_3..param2::city;
                _loc_5.hometown_location.state = _loc_3..param2::state;
                _loc_5.hometown_location.country = _loc_3..param2::country;
                _loc_5.hometown_location.zip = _loc_3..param2::zip;
            }// end if
            if (param1..param2::current_location)
            {
                _loc_5.current_location = new FacebookLocation();
                _loc_4 = param1..param2::current_location;
                _loc_5.current_location.city = _loc_4..param2::city;
                _loc_5.current_location.state = _loc_4..param2::state;
                _loc_5.current_location.country = _loc_4..param2::country;
                _loc_5.current_location.zip = _loc_4..param2::zip;
            }// end if
            if (param1..param2::activities)
            {
                _loc_5.activities = param1..param2::activities.toString();
            }// end if
            if (param1..param2::interests)
            {
                _loc_5.interests = param1..param2::interests.toString();
            }// end if
            if (param1..param2::music)
            {
                _loc_5.music = param1..param2::music.toString();
            }// end if
            if (param1..param2::tv)
            {
                _loc_5.tv = param1..param2::tv.toString();
            }// end if
            if (param1..param2::movies)
            {
                _loc_5.movies = param1..param2::movies.toString();
            }// end if
            if (param1..param2::books)
            {
                _loc_5.books = param1..param2::books.toString();
            }// end if
            if (param1..param2::quotes)
            {
                _loc_5.quotes = param1..param2::quotes.toString();
            }// end if
            if (param1..param2::about_me)
            {
                _loc_5.about_me = param1..param2::about_me.toString();
            }// end if
            if (param1..param2::hs_info)
            {
                for each (_loc_15 in param1..param2::hs_info)
                {
                    // label
                    _loc_5.hs1_id = parseInt(param2::hs1_id);
                    _loc_5.hs1_name = String(param2::hs1_name);
                    _loc_5.hs2_id = parseInt(param2::hs2_id);
                    _loc_5.hs2_name = String(param2::hs2_name);
                    _loc_5.grad_year = String(param2::grad_year);
                }// end of for each ... in
            }// end if
            if (param1..param2::education_history)
            {
                _loc_5.education_history = [];
                _loc_16 = param1..param2::education_info;
                for each (_loc_17 in _loc_16)
                {
                    // label
                    _loc_18 = new FacebookEducationInfo();
                    _loc_18.name = param2::name;
                    _loc_18.year = param2::year;
                    _loc_18.degree = param2::degree;
                    _loc_18.concentrations = [];
                    for each (_loc_19 in _loc_17.concentration)
                    {
                        // label
                        _loc_18.concentrations.push(_loc_19);
                    }// end of for each ... in
                    _loc_5.education_history.push(_loc_18);
                }// end of for each ... in
            }// end if
            if (param1..param2::work_history)
            {
                _loc_5.work_history = [];
                for each (_loc_20 in param1..param2::work_info)
                {
                    // label
                    _loc_21 = new FacebookWorkInfo();
                    _loc_21.location = new FacebookLocation();
                    _loc_21.location.city = param2::location.city;
                    _loc_21.location.state = param2::location.state;
                    _loc_21.location.country = param2::location.country;
                    _loc_21.location.zip = param2::location.zip;
                    _loc_21.company_name = param2::company_name;
                    _loc_21.description = _loc_20..param2::description;
                    _loc_21.position = _loc_20..param2::position;
                    _loc_21.start_date = FacebookDataUtils.formatDate(param2::start_date);
                    _loc_21.end_date = FacebookDataUtils.formatDate(param2::end_date);
                    _loc_5.work_history.push(_loc_21);
                }// end of for each ... in
            }// end if
            if (param1..param2::has_added_app)
            {
                _loc_5.has_added_app = param1..param2::has_added_app == 1;
            }// end if
            if (param1..param2::is_app_user)
            {
                _loc_5.is_app_user = param1..param2::is_app_user == 1;
            }// end if
            return _loc_5;
        }// end function

    }
}
