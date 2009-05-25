package com.facebook.data
{
    import com.adobe.serialization.json.*;
    import com.facebook.commands.data.*;
    import com.facebook.data.admin.*;
    import com.facebook.data.application.*;
    import com.facebook.data.auth.*;
    import com.facebook.data.batch.*;
    import com.facebook.data.data.*;
    import com.facebook.data.events.*;
    import com.facebook.data.fbml.*;
    import com.facebook.data.feed.*;
    import com.facebook.data.friends.*;
    import com.facebook.data.groups.*;
    import com.facebook.data.notes.*;
    import com.facebook.data.notifications.*;
    import com.facebook.data.pages.*;
    import com.facebook.data.photos.*;
    import com.facebook.data.users.*;
    import com.facebook.errors.*;
    import com.facebook.utils.*;
    import flash.events.*;
    import flash.net.*;

    public class XMLDataParser extends Object implements IFacebookResultParser
    {
        protected var fb_namespace:Namespace;

        public function XMLDataParser()
        {
            fb_namespace = new Namespace("http://api.facebook.com/1.0/");
            return;
        }// end function

        protected function getVenueData(param1:XMLList) : VenueData
        {
            var _loc_2:VenueData;
            _loc_2 = new VenueData();
            _loc_2.street = fb_namespace::name;
            _loc_2.city = fb_namespace::city;
            _loc_2.state = fb_namespace::state;
            _loc_2.country = fb_namespace::country;
            return _loc_2;
        }// end function

        protected function parseGetPhotos(param1:XML) : GetPhotosData
        {
            var _loc_2:GetPhotosData;
            var _loc_3:PhotoCollection;
            var _loc_4:*;
            var _loc_5:PhotoData;
            _loc_2 = new GetPhotosData();
            _loc_3 = new PhotoCollection();
            for each (_loc_4 in param1..fb_namespace::photo)
            {
                // label
                _loc_5 = new PhotoData();
                _loc_5.pid = fb_namespace::pid;
                _loc_5.aid = fb_namespace::aid;
                _loc_5.owner = fb_namespace::owner;
                _loc_5.src = fb_namespace::src;
                _loc_5.src_big = fb_namespace::src_big;
                _loc_5.src_small = fb_namespace::src_small;
                _loc_5.caption = fb_namespace::caption;
                _loc_5.created = toDate(fb_namespace::created);
                _loc_3.addPhoto(_loc_5);
            }// end of for each ... in
            _loc_2.photoCollection = _loc_3;
            return _loc_2;
        }// end function

        protected function parseGetNotifications(param1:XML) : GetNotificationData
        {
            var _loc_2:GetNotificationData;
            var _loc_3:NotificationCollection;
            var _loc_4:*;
            var _loc_5:*;
            var _loc_6:*;
            var _loc_7:NotificationMessageData;
            var _loc_8:NotificationPokeData;
            var _loc_9:NotificationShareData;
            _loc_2 = new GetNotificationData();
            _loc_3 = new NotificationCollection();
            for each (_loc_4 in fb_namespace::messages)
            {
                // label
                _loc_7 = new NotificationMessageData();
                _loc_7.unread = fb_namespace::unread;
                _loc_7.most_recent = fb_namespace::most_recent;
                _loc_3.addItem(_loc_7);
            }// end of for each ... in
            for each (_loc_5 in fb_namespace::pokes)
            {
                // label
                _loc_8 = new NotificationPokeData();
                _loc_8.unread = fb_namespace::unread;
                _loc_8.most_recent = fb_namespace::most_recent;
                _loc_3.addItem(_loc_8);
            }// end of for each ... in
            for each (_loc_6 in fb_namespace::pokes)
            {
                // label
                _loc_9 = new NotificationShareData();
                _loc_9.unread = fb_namespace::unread;
                _loc_9.most_recent = fb_namespace::most_recent;
                _loc_3.addItem(_loc_9);
            }// end of for each ... in
            _loc_2.friendsRequests = toUIDArray(fb_namespace::friend_requests[0]);
            _loc_2.group_invites = toUIDArray(fb_namespace::group_invites[0]);
            _loc_2.event_invites = toUIDArray(fb_namespace::event_invites[0]);
            _loc_2.notificationCollection = _loc_3;
            return _loc_2;
        }// end function

        protected function parseSendEmail(param1:XML) : ArrayResultData
        {
            var _loc_2:ArrayResultData;
            _loc_2 = new ArrayResultData();
            _loc_2.arrayResult = toArray(param1);
            return _loc_2;
        }// end function

        protected function parseGetUserPreferences(param1:XML) : GetUserPreferencesData
        {
            var _loc_2:GetUserPreferencesData;
            var _loc_3:PreferenceCollection;
            var _loc_4:*;
            var _loc_5:PreferenceData;
            _loc_2 = new GetUserPreferencesData();
            _loc_3 = new PreferenceCollection();
            for each (_loc_4 in param1..fb_namespace::preference)
            {
                // label
                _loc_5 = new PreferenceData();
                _loc_5.pref_id = fb_namespace::pref_id;
                _loc_5.value = fb_namespace::value;
                _loc_3.addItem(_loc_5);
            }// end of for each ... in
            _loc_2.perferenceCollection = _loc_3;
            return _loc_2;
        }// end function

        protected function toUIDArray(param1:XML) : Array
        {
            var _loc_2:Array;
            var _loc_3:XMLList;
            var _loc_4:uint;
            var _loc_5:uint;
            _loc_2 = [];
            if (param1 == null)
            {
                return _loc_2;
            }// end if
            _loc_3 = param1.children();
            _loc_4 = _loc_3.length();
            _loc_5 = 0;
            while (_loc_5++ < _loc_4)
            {
                // label
                _loc_2.push(toNumber(_loc_3[_loc_5]));
            }// end while
            return _loc_2;
        }// end function

        protected function parseGener(param1:XML) : GenreData
        {
            var _loc_2:GenreData;
            var _loc_3:*;
            _loc_2 = new GenreData();
            for each (_loc_3 in param1)
            {
                // label
                _loc_2.dance = toBoolean(_loc_3.dance);
                _loc_2.party = toBoolean(_loc_3.party);
                _loc_2.relax = toBoolean(_loc_3.dance);
                _loc_2.talk = toBoolean(_loc_3.relax);
                _loc_2.think = toBoolean(_loc_3.dance);
                _loc_2.workout = toBoolean(_loc_3.think);
                _loc_2.sing = toBoolean(_loc_3.sing);
                _loc_2.intimate = toBoolean(_loc_3.intimate);
                _loc_2.raunchy = toBoolean(_loc_3.raunchy);
                _loc_2.headphones = toBoolean(_loc_3.headphones);
            }// end of for each ... in
            return _loc_2;
        }// end function

        protected function parseGetRegisteredTemplateBundles(param1:XML) : GetRegisteredTemplateBundleData
        {
            var _loc_2:GetRegisteredTemplateBundleData;
            var _loc_3:TemplateBundleCollection;
            var _loc_4:TemplateCollection;
            var _loc_5:*;
            _loc_2 = new GetRegisteredTemplateBundleData();
            _loc_3 = new TemplateBundleCollection();
            _loc_4 = new TemplateCollection();
            for each (_loc_5 in param1..fb_namespace::template_bundle)
            {
                // label
                getTemplate(fb_namespace::one_line_story_template, _loc_4);
                getTemplate(fb_namespace::short_story_templates, _loc_4);
                getTemplate(fb_namespace::full_story_template, _loc_4);
                _loc_4.template_bundle_id = fb_namespace::template_bundle_id;
                _loc_4.time_created = toDate(fb_namespace::time_created);
            }// end of for each ... in
            _loc_2.bundleCollection = _loc_4;
            return _loc_2;
        }// end function

        protected function responseNodeNameToMethodName(param1:String) : String
        {
            var _loc_2:Array;
            _loc_2 = param1.split("_");
            _loc_2.pop();
            return _loc_2.join(".");
        }// end function

        protected function parseFacebookPhoto(param1:XML) : FacebookPhoto
        {
            var _loc_2:FacebookPhoto;
            _loc_2 = new FacebookPhoto();
            _loc_2.pid = toStringValue(fb_namespace::pid[0]);
            _loc_2.aid = toStringValue(fb_namespace::aid[0]);
            _loc_2.owner = toNumber(fb_namespace::owner[0]);
            _loc_2.src = toStringValue(fb_namespace::src[0]);
            _loc_2.src_big = toStringValue(fb_namespace::src_big[0]);
            _loc_2.src_small = toStringValue(fb_namespace::src_small[0]);
            _loc_2.link = toStringValue(fb_namespace::link[0]);
            _loc_2.caption = toStringValue(fb_namespace::caption[0]);
            return _loc_2;
        }// end function

        protected function parseGetObjectType(param1:XML) : GetObjectTypeData
        {
            var _loc_2:GetObjectTypeData;
            _loc_2 = new GetObjectTypeData();
            _loc_2.name = fb_namespace::name;
            _loc_2.data_type = fb_namespace::data_type;
            _loc_2.index_type = fb_namespace::index_type;
            return _loc_2;
        }// end function

        protected function toArray(param1:XML) : Array
        {
            if (param1 == null)
            {
                return null;
            }// end if
            return param1.toString().split(",");
        }// end function

        protected function parseGetMetrics(param1:XML) : GetMetricsData
        {
            var _loc_2:GetMetricsData;
            var _loc_3:MetricsDataCollection;
            var _loc_4:*;
            var _loc_5:MetricsData;
            _loc_2 = new GetMetricsData();
            _loc_3 = new MetricsDataCollection();
            for each (_loc_4 in param1..fb_namespace::metrics)
            {
                // label
                _loc_5 = new MetricsData();
                _loc_5.end_time = toDate(fb_namespace::end_time);
                _loc_5.active_users = fb_namespace::active_users;
                _loc_5.canvas_page_views = fb_namespace::canvas_page_views;
                _loc_3.addItem(_loc_5);
            }// end of for each ... in
            _loc_2.metricsCollection = _loc_3;
            return _loc_2;
        }// end function

        protected function toNumber(param1:XML) : Number
        {
            if (param1 == null)
            {
                return NaN;
            }// end if
            return Number(param1.toString());
        }// end function

        protected function parseCreateAlbum(param1:XML) : GetCreateAlbumData
        {
            var _loc_2:GetCreateAlbumData;
            var _loc_3:AlbumData;
            _loc_2 = new GetCreateAlbumData();
            _loc_3 = new AlbumData();
            _loc_3.aid = fb_namespace::aid;
            _loc_3.cover_pid = fb_namespace::cover_pid;
            _loc_3.owner = fb_namespace::owner;
            _loc_3.name = fb_namespace::name;
            _loc_3.created = toDate(fb_namespace::created);
            _loc_3.modified = toDate(fb_namespace::modified);
            _loc_3.description = fb_namespace::description;
            _loc_3.location = fb_namespace::location;
            _loc_3.link = fb_namespace::link;
            _loc_3.size = fb_namespace::size;
            _loc_2.albumData = _loc_3;
            return _loc_2;
        }// end function

        protected function parseGetCustomTags(param1:XML) : GetCustomTagsData
        {
            var _loc_2:Array;
            var _loc_3:GetCustomTagsData;
            var _loc_4:TagCollection;
            var _loc_5:*;
            _loc_2 = ["type", "name", "open_tag_fbml", "description", "close_tag_fbml", "is_public", "attributes", "header_fbml", "footer_fbml", "fbml"];
            _loc_3 = new GetCustomTagsData();
            _loc_4 = new TagCollection();
            for each (_loc_5 in param1..fb_namespace::custom_tag)
            {
                // label
                _loc_4.addItem(createTagObject(_loc_5, _loc_2));
            }// end of for each ... in
            _loc_3.tagCollection = _loc_4;
            return _loc_3;
        }// end function

        protected function parseGetLists(param1:XML) : GetListsData
        {
            var _loc_2:GetListsData;
            var _loc_3:FriendsCollection;
            var _loc_4:*;
            var _loc_5:ListsData;
            _loc_2 = new GetListsData();
            _loc_3 = new FriendsCollection();
            for each (_loc_4 in param1..fb_namespace::friendlist)
            {
                // label
                _loc_5 = new ListsData();
                _loc_5.flid = fb_namespace::flid;
                _loc_5.name = fb_namespace::name;
                _loc_3.addItem(_loc_5);
            }// end of for each ... in
            _loc_2.friendsListCollection = _loc_3;
            return _loc_2;
        }// end function

        protected function parseAreFriends(param1:XML) : AreFriendsData
        {
            var _loc_2:AreFriendsData;
            var _loc_3:FriendsCollection;
            var _loc_4:*;
            var _loc_5:FriendsData;
            _loc_2 = new AreFriendsData();
            _loc_3 = new FriendsCollection();
            for each (_loc_4 in param1..fb_namespace::friend_info)
            {
                // label
                _loc_5 = new FriendsData();
                _loc_5.uid1 = fb_namespace::uid1;
                _loc_5.uid2 = fb_namespace::uid2;
                _loc_5.are_friends = toBoolean(fb_namespace::are_friends);
                _loc_3.addItem(_loc_5);
            }// end of for each ... in
            _loc_2.friendsCollection = _loc_3;
            return _loc_2;
        }// end function

        protected function parseGetEvent(param1:XML) : GetEventsData
        {
            var _loc_2:GetEventsData;
            var _loc_3:EventCollection;
            var _loc_4:*;
            var _loc_5:EventData;
            _loc_2 = new GetEventsData();
            _loc_3 = new EventCollection();
            for each (_loc_4 in param1..fb_namespace::event)
            {
                // label
                _loc_5 = new EventData();
                _loc_5.eid = fb_namespace::eid;
                _loc_5.name = fb_namespace::name;
                _loc_5.tagline = fb_namespace::tagline;
                _loc_5.nid = fb_namespace::nid;
                _loc_5.pic = fb_namespace::pic;
                _loc_5.pic_big = fb_namespace::pic_big;
                _loc_5.pic_small = fb_namespace::pic_small;
                _loc_5.host = fb_namespace::host;
                _loc_5.description = fb_namespace::description;
                _loc_5.event_type = fb_namespace::event_type;
                _loc_5.event_subtype = fb_namespace::event_subtype;
                _loc_5.start_time = toDate(fb_namespace::start_time);
                _loc_5.end_time = toDate(fb_namespace::end_time);
                _loc_5.creator = fb_namespace::end_time;
                _loc_5.update_time = toDate(fb_namespace::update_time);
                _loc_5.location = fb_namespace::location;
                _loc_5.venue = getVenueData(fb_namespace::venue);
                _loc_3.addItem(_loc_5);
            }// end of for each ... in
            _loc_2.eventCollection = _loc_3;
            return _loc_2;
        }// end function

        protected function getAffiliation(param1:XML) : AffiliationCollection
        {
            var _loc_2:AffiliationCollection;
            var _loc_3:*;
            var _loc_4:AffiliationData;
            _loc_2 = new AffiliationCollection();
            for each (_loc_3 in param1..fb_namespace::afflication)
            {
                // label
                _loc_4 = new AffiliationData();
                _loc_4.nid = fb_namespace::nid;
                _loc_4.name = fb_namespace::name;
                _loc_4.type = fb_namespace::type;
                _loc_4.status = fb_namespace::status;
                _loc_4.year = fb_namespace::year;
                _loc_2.addAffiliation(_loc_4);
            }// end of for each ... in
            return _loc_2;
        }// end function

        protected function toBoolean(param1:XML) : Boolean
        {
            if (param1 == null)
            {
                return false;
            }// end if
            return param1.toString() == "1";
        }// end function

        protected function parseGetPublicInfo(param1:XML) : GetPublicInfoData
        {
            var _loc_2:GetPublicInfoData;
            _loc_2 = new GetPublicInfoData();
            _loc_2.app_id = fb_namespace::app_id;
            _loc_2.api_key = fb_namespace::api_key;
            _loc_2.canvas_name = fb_namespace::canvas_name;
            _loc_2.display_name = fb_namespace::display_name;
            _loc_2.icon_url = fb_namespace::icon_url;
            _loc_2.logo_url = fb_namespace::logo_url;
            _loc_2.developers = fb_namespace::developers;
            _loc_2.company_name = fb_namespace::company_name;
            _loc_2.developers = fb_namespace::developers;
            _loc_2.daily_active_users = fb_namespace::daily_active_users;
            _loc_2.weekly_active_users = fb_namespace::weekly_active_users;
            _loc_2.monthly_active_users = fb_namespace::monthly_active_users;
            return _loc_2;
        }// end function

        protected function parseBatchRun(param1:XML) : FacebookData
        {
            var _loc_2:XMLList;
            var _loc_3:uint;
            var _loc_4:Array;
            var _loc_5:uint;
            var _loc_6:BatchResult;
            var _loc_7:String;
            var _loc_8:XML;
            var _loc_9:FacebookError;
            var _loc_10:String;
            var _loc_11:FacebookData;
            _loc_2 = param1..fb_namespace::batch_run_response_elt;
            _loc_3 = _loc_2.length();
            _loc_4 = [];
            _loc_5 = 0;
            while (_loc_5++ < _loc_3)
            {
                // label
                _loc_7 = _loc_2[_loc_5].toString();
                _loc_8 = new XML(_loc_7);
                _loc_9 = validateFacebookResponce(_loc_7);
                if (_loc_9 === null)
                {
                    _loc_10 = responseNodeNameToMethodName(_loc_8.localName().toString());
                    _loc_11 = parse(_loc_7, _loc_10);
                    _loc_4.push(_loc_11);
                    continue;
                }// end if
                _loc_4.push(_loc_9);
            }// end while
            _loc_6 = new BatchResult();
            _loc_6.results = _loc_4;
            return _loc_6;
        }// end function

        public function createFacebookError(param1:Object, param2:String) : FacebookError
        {
            var _loc_3:FacebookError;
            _loc_3 = new FacebookError();
            _loc_3.rawResult = param2;
            _loc_3.errorCode = FacebookErrorCodes.SERVER_ERROR;
            if (param1 is Error)
            {
                _loc_3.error = param1 as Error;
            }
            else
            {
                _loc_3.errorEvent = param1 as ErrorEvent;
            }// end else if
            return _loc_3;
        }// end function

        protected function toDate(param1:String) : Date
        {
            var _loc_2:String;
            var _loc_3:Date;
            if (param1 == null)
            {
                return null;
            }// end if
            _loc_2 = param1;
            while (_loc_2.length < 13)
            {
                // label
                _loc_2 = _loc_2 + "0";
            }// end while
            _loc_3 = new Date(Number(_loc_2));
            return _loc_3;
        }// end function

        public function parse(param1:String, param2:String) : FacebookData
        {
            var _loc_3:FacebookData;
            var _loc_4:XML;
            _loc_4 = new XML(param1);
            switch(param2)
            {
                case "application.getPublicInfo":
                {
                    _loc_3 = parseGetPublicInfo(_loc_4);
                    break;
                }// end case
                case "data.getCookies":
                {
                    _loc_3 = parseGetCookies(_loc_4);
                    break;
                }// end case
                case "admin.getAllocation":
                {
                    _loc_3 = parseGetAllocation(_loc_4);
                    break;
                }// end case
                case "admin.getAppProperties":
                {
                    _loc_3 = parseGetAppProperties(_loc_4);
                    break;
                }// end case
                case "admin.getMetrics":
                {
                    _loc_3 = parseGetMetrics(_loc_4);
                    break;
                }// end case
                case "auth.getSession":
                {
                    _loc_3 = new GetSessionData();
                    (_loc_3 as GetSessionData).expires = toDate(fb_namespace::expires);
                    (_loc_3 as GetSessionData).uid = toStringValue(fb_namespace::uid[0]);
                    (_loc_3 as GetSessionData).session_key = fb_namespace::session_key.toString();
                    (_loc_3 as GetSessionData).secret = String(fb_namespace::secret);
                    break;
                }// end case
                case "feed.getRegisteredTemplateBundles":
                {
                    _loc_3 = parseGetRegisteredTemplateBundles(_loc_4);
                    break;
                }// end case
                case "friends.areFriends":
                {
                    _loc_3 = parseAreFriends(_loc_4);
                    break;
                }// end case
                case "notes.get":
                {
                    _loc_3 = parseGetNotes(_loc_4);
                    break;
                }// end case
                case "friends.get":
                {
                    _loc_3 = parseGetFriends(_loc_4);
                    break;
                }// end case
                case "friends.getAppUsers":
                {
                    _loc_3 = parseGetAppUsersData(_loc_4);
                    break;
                }// end case
                case "friends.getLists":
                {
                    _loc_3 = parseGetLists(_loc_4);
                    break;
                }// end case
                case "groups.get":
                {
                    _loc_3 = parseGetGroups(_loc_4);
                    break;
                }// end case
                case "data.getAssociationDefinitions":
                {
                    _loc_3 = new FacebookData();
                    break;
                }// end case
                case "data.getAssociationDefinition":
                {
                    _loc_3 = new FacebookData();
                    break;
                }// end case
                case "data.getObject":
                case "data.getObjects":
                {
                    _loc_3 = new FacebookData();
                    break;
                }// end case
                case "groups.getMembers":
                {
                    _loc_3 = parseGetGroupMembers(_loc_4);
                    break;
                }// end case
                case "users.getInfo":
                {
                    _loc_3 = parseGetInfo(_loc_4);
                    break;
                }// end case
                case "data.createObject":
                case "data.setHashValue":
                case "connect.getUnconnectedFriendsCount":
                case "feed.registerTemplateBundle":
                {
                    _loc_3 = new NumberResultData();
                    (_loc_3 as NumberResultData).value = toNumber(_loc_4);
                    break;
                }// end case
                case "notifications.get":
                {
                    _loc_3 = parseGetNotifications(_loc_4);
                    break;
                }// end case
                case "feed.getRegisteredTemplateBundleByID":
                {
                    _loc_3 = parseGetRegisteredTemplateBundleByID(_loc_4);
                    break;
                }// end case
                case "users.getStandardInfo":
                {
                    _loc_3 = parseGetStandardInfo(_loc_4);
                    break;
                }// end case
                case "feed.getRegisteredTemplateBundles":
                {
                    _loc_3 = parseGetRegisteredTemplateBundles(_loc_4);
                    break;
                }// end case
                case "data.getUserPreferences":
                {
                    _loc_3 = parseGetUserPreferences(_loc_4);
                    break;
                }// end case
                case "users.isAppUser":
                case "users.hasAppPermission":
                case "users.setStatus":
                case "pages.isFan":
                case "pages.isAppAdded":
                case "pages.isAdmin":
                case "admin.setAppProperties":
                case "auth.expireSession":
                case "auth.revokeAuthorization":
                case "events.cancel":
                case "events.edit":
                case "events.rsvp":
                case "liveMessage.send":
                case "data.undefineAssociation":
                case "data.defineAssociation":
                case "data.removeHashKeys":
                case "data.removeHashKey":
                case "data.incHashValue":
                case "data.updateObject":
                case "data.deleteObject":
                case "data.deleteObjects":
                case "data.renameAssociation":
                case "data.setObjectProperty":
                case "profile.setInfo":
                case "profile.setInfoOptions":
                case "feed.deactivateTemplateBundleByID":
                case "feed.publishTemplatizedAction":
                case "admin.setRestrictionInfo":
                case "data.setCookie":
                case "data.createObjectType":
                case "notes.delete":
                case "notes.edit":
                case "data.setUserPreference":
                case "data.dropObjectType":
                case "data.renameObjectType":
                case "fbml.registerCustomTags":
                case "fbml.deleteCustomTags":
                case "fbml.refreshRefUrl":
                case "fbml.refreshImgSrc":
                case "fbml.setRefHandle":
                case "data.setUserPreferences":
                case "data.defineObjectProperty":
                {
                    _loc_3 = new BooleanResultData();
                    (_loc_3 as BooleanResultData).value = toBoolean(_loc_4);
                    break;
                }// end case
                case "feed.publishUserAction":
                {
                    _loc_3 = new BooleanResultData();
                    (_loc_3 as BooleanResultData).value = toBoolean(_loc_4.children()[0]);
                    break;
                }// end case
                case "notifications.sendEmail":
                {
                    _loc_3 = parseSendEmail(_loc_4);
                    break;
                }// end case
                case "data.getObjectTypes":
                {
                    _loc_3 = parseGetObjectTypes(_loc_4);
                    break;
                }// end case
                case "users.getStandardInfo":
                {
                    _loc_3 = parseGetStandardInfo(_loc_4);
                    break;
                }// end case
                case "data.getObjectType":
                {
                    _loc_3 = parseGetObjectType(_loc_4);
                    break;
                }// end case
                case "events.get":
                {
                    _loc_3 = parseGetEvent(_loc_4);
                    break;
                }// end case
                case "events.getMembers":
                {
                    _loc_3 = parseGetMembers(_loc_4);
                    break;
                }// end case
                case "fql.query":
                {
                    _loc_3 = new FacebookData();
                    break;
                }// end case
                case "photos.createAlbum":
                {
                    _loc_3 = parseCreateAlbum(_loc_4);
                    break;
                }// end case
                case "photos.get":
                {
                    _loc_3 = parseGetPhotos(_loc_4);
                    break;
                }// end case
                case "photos.getTags":
                {
                    _loc_3 = parseGetTags(_loc_4);
                    break;
                }// end case
                case "photos.getAlbums":
                {
                    _loc_3 = parseGetAlbums(_loc_4);
                    break;
                }// end case
                case "photos.upload":
                {
                    _loc_3 = parseFacebookPhoto(_loc_4);
                    break;
                }// end case
                case "pages.getInfo":
                {
                    _loc_3 = parsePageGetInfo(_loc_4);
                    break;
                }// end case
                case "batch.run":
                {
                    _loc_3 = parseBatchRun(_loc_4);
                    break;
                }// end case
                case "fbml.getCustomTags":
                {
                    _loc_3 = parseGetCustomTags(_loc_4);
                    break;
                }// end case
                case "connect.unregisterUsers":
                case "connect.registerUsers":
                {
                    _loc_3 = new ArrayResultData();
                    (_loc_3 as ArrayResultData).arrayResult = toArray(_loc_4);
                    break;
                }// end case
                case "auth.createToken":
                case "events.create":
                case "links.post":
                case "auth.promoteSession":
                case "admin.getRestrictionInfo":
                case "data.getObjectProperty":
                case "notifications.send":
                case "notes.create":
                case "data.getUserPreference":
                case "profile.setFBML":
                case "users.getLoggedInUser":
                {
                    _loc_3 = new StringResultData();
                    (_loc_3 as StringResultData).value = toStringValue(_loc_4);
                    break;
                }// end case
                default:
                {
                    throw new Error("methodName " + param2 + " cannot be found.");
                    break;
                }// end default
            }// end switch
            _loc_3.rawResult = param1;
            return _loc_3;
        }// end function

        protected function parseGetStandardInfo(param1:XML) : GetStandardInfoData
        {
            var _loc_2:GetStandardInfoData;
            var _loc_3:UserCollection;
            var _loc_4:*;
            var _loc_5:UserData;
            _loc_2 = new GetStandardInfoData();
            _loc_3 = new UserCollection();
            for each (_loc_4 in param1..fb_namespace::user)
            {
                // label
                _loc_5 = new UserData();
                _loc_5.uid = fb_namespace::uid;
                _loc_5.affiations = getAffiliation(fb_namespace::affiliations);
                _loc_5.first_name = fb_namespace::first_name;
                _loc_5.last_name = fb_namespace::last_name;
                _loc_5.name = fb_namespace::name;
                _loc_5.timezone = fb_namespace::timezone;
                _loc_3.addItem(_loc_5);
            }// end of for each ... in
            _loc_2.userCollection = _loc_3;
            return _loc_2;
        }// end function

        protected function parseGetAppProperties(param1:XML) : GetAppPropertiesData
        {
            var _loc_2:GetAppPropertiesData;
            _loc_2 = new GetAppPropertiesData();
            _loc_2.appProperties = JSON.decode(param1.toString());
            return _loc_2;
        }// end function

        protected function parseGetRegisteredTemplateBundleByID(param1:XML) : GetRegisteredTemplateBundleByIDData
        {
            var _loc_2:GetRegisteredTemplateBundleByIDData;
            var _loc_3:TemplateCollection;
            _loc_2 = new GetRegisteredTemplateBundleByIDData();
            _loc_3 = new TemplateCollection();
            getTemplate(fb_namespace::one_line_story_template, _loc_3);
            getTemplate(fb_namespace::short_story_templates, _loc_3);
            getTemplate(fb_namespace::full_story_template, _loc_3);
            _loc_3.template_bundle_id = fb_namespace::template_bundle_id;
            _loc_3.time_created = toDate(fb_namespace::time_created);
            _loc_2.templateCollection = _loc_3;
            return _loc_2;
        }// end function

        protected function parseGetObjectTypes(param1:XML) : GetObjectTypesData
        {
            var _loc_2:GetObjectTypesData;
            var _loc_3:ObjectTypesCollection;
            var _loc_4:*;
            var _loc_5:ObjectTypesData;
            _loc_2 = new GetObjectTypesData();
            _loc_3 = new ObjectTypesCollection();
            for each (_loc_4 in param1..fb_namespace::object_type_info)
            {
                // label
                _loc_5 = new ObjectTypesData();
                _loc_5.name = fb_namespace::name;
                _loc_5.object_class = fb_namespace::object_class;
                _loc_3.addItem(_loc_5);
            }// end of for each ... in
            _loc_2.objectTypeCollection = _loc_3;
            return _loc_2;
        }// end function

        public function xmlToUrlVariables(param1:XMLList) : URLVariables
        {
            var _loc_2:URLVariables;
            var _loc_3:XML;
            _loc_2 = new URLVariables();
            for each (_loc_3 in param1)
            {
                // label
                _loc_2[_loc_3.key.valueOf()] = _loc_3.value.valueOf();
            }// end of for each ... in
            return _loc_2;
        }// end function

        protected function parseGetTags(param1:XML) : GetTagsData
        {
            var _loc_2:GetTagsData;
            var _loc_3:PhotoTagCollection;
            var _loc_4:*;
            var _loc_5:TagData;
            _loc_2 = new GetTagsData();
            _loc_3 = new PhotoTagCollection();
            for each (_loc_4 in param1..fb_namespace::photo_tag)
            {
                // label
                _loc_5 = new TagData();
                _loc_5.pid = fb_namespace::pid;
                _loc_5.subject = fb_namespace::subject;
                _loc_5.xcoord = fb_namespace::xcoord;
                _loc_5.ycoord = fb_namespace::ycoord;
                _loc_5.created = toDate(fb_namespace::created);
                _loc_3.addPhotoTag(_loc_5);
            }// end of for each ... in
            _loc_2.photoTagsCollection = _loc_3;
            return _loc_2;
        }// end function

        protected function createTagObject(param1:XML, param2:Array)
        {
            var _loc_3:Number;
            var _loc_4:String;
            var _loc_5:AbstractTagData;
            var _loc_6:Number;
            var _loc_7:Object;
            _loc_3 = param1.children().length();
            _loc_4 = param1.children()[0].toLowerCase();
            if (_loc_4 == "leaf")
            {
                _loc_5 = new LeafTagData(null, null, null, null, null);
                (_loc_5 as LeafTagData).fbml = param1.children()[9];
            }
            else
            {
                _loc_5 = new ContainerTagData(null, null, null, null, null, null, null);
                (_loc_5 as ContainerTagData).open_tag_fbml = param1.children()[2];
                (_loc_5 as ContainerTagData).close_tag_fbml = param1.children()[4];
            }// end else if
            _loc_6 = 0;
            while (_loc_6++ < _loc_3)
            {
                // label
                _loc_7 = param1.children()[_loc_6];
                switch(param2[_loc_6])
                {
                    case "name":
                    case "type":
                    case "description":
                    case "is_public":
                    case "header_fbml":
                    case "footer_fbml":
                    {
                        _loc_5[param2[_loc_6]] = _loc_7.text();
                        break;
                    }// end case
                    case "attributes":
                    {
                        if (_loc_7.children() is XMLList)
                        {
                            if (_loc_7.children().length() == 0)
                            {
                                _loc_5[param2[_loc_6]] = null;
                            }// end if
                        }// end if
                        break;
                    }// end case
                    default:
                    {
                        break;
                    }// end default
                }// end switch
            }// end while
            return _loc_5;
        }// end function

        protected function parseGetNotes(param1:XML) : GetNotesData
        {
            var _loc_2:GetNotesData;
            var _loc_3:NotesCollection;
            var _loc_4:*;
            var _loc_5:NoteData;
            _loc_2 = new GetNotesData();
            _loc_3 = new NotesCollection();
            for each (_loc_4 in param1..fb_namespace::note)
            {
                // label
                _loc_5 = new NoteData();
                _loc_5.note_id = fb_namespace::note_id;
                _loc_5.title = fb_namespace::title;
                _loc_5.content = fb_namespace::content;
                _loc_5.created_time = toDate(fb_namespace::created_time);
                _loc_5.updated_time = toDate(fb_namespace::updated_time);
                _loc_5.uid = fb_namespace::uid;
                _loc_3.addItem(_loc_5);
            }// end of for each ... in
            _loc_2.notesCollection = _loc_3;
            return _loc_2;
        }// end function

        protected function parsePageGetInfo(param1:XML) : GetPageInfoData
        {
            var _loc_2:GetPageInfoData;
            var _loc_3:PageInfoCollection;
            var _loc_4:*;
            var _loc_5:PageInfoData;
            _loc_2 = new GetPageInfoData();
            _loc_3 = new PageInfoCollection();
            for each (_loc_4 in fb_namespace::page)
            {
                // label
                _loc_5 = new PageInfoData();
                _loc_5.page_id = fb_namespace::page_id;
                _loc_5.name = fb_namespace::name;
                _loc_5.pic_small = fb_namespace::pic_small;
                _loc_5.pic_big = fb_namespace::pic_big;
                _loc_5.pic_square = fb_namespace::pic_square;
                _loc_5.pic_large = fb_namespace::pic_large;
                _loc_5.type = fb_namespace::type;
                _loc_5.website = fb_namespace::website;
                _loc_5.location = fb_namespace::location;
                _loc_5.hours = fb_namespace::hours;
                _loc_5.band_members = fb_namespace::band_members;
                _loc_5.bio = fb_namespace::bio;
                _loc_5.hometown = fb_namespace::hometown;
                _loc_5.genre = parseGener(fb_namespace::genre[0]);
                _loc_5.record_label = fb_namespace::record_label;
                _loc_5.influences = fb_namespace::influences;
                _loc_5.has_added_app = toBoolean(fb_namespace::has_added_app[0]);
                _loc_5.founded = fb_namespace::founded;
                _loc_5.company_overview = fb_namespace::company_overview;
                _loc_5.mission = fb_namespace::mission;
                _loc_5.products = fb_namespace::products;
                _loc_5.release_date = fb_namespace::release_date;
                _loc_5.starring = fb_namespace::starring;
                _loc_5.written_by = fb_namespace::written_by;
                _loc_5.directed_by = fb_namespace::directed_by;
                _loc_5.produced_by = fb_namespace::produced_by;
                _loc_5.studio = fb_namespace::studio;
                _loc_5.awards = fb_namespace::awards;
                _loc_5.plot_outline = fb_namespace::plot_outline;
                _loc_5.network = fb_namespace::network;
                _loc_5.season = fb_namespace::season;
                _loc_5.schedule = fb_namespace::schedule;
                _loc_3.addPageInfo(_loc_5);
            }// end of for each ... in
            _loc_2.pageInfoCollection = _loc_3;
            return _loc_2;
        }// end function

        protected function parseGetAllocation(param1:XML) : GetAllocationData
        {
            var _loc_2:GetAllocationData;
            _loc_2 = new GetAllocationData();
            _loc_2.allocationLimit = Number(param1.toString());
            return _loc_2;
        }// end function

        protected function parseGetGroupMembers(param1:XML) : GetMemberData
        {
            var _loc_2:GetMemberData;
            _loc_2 = new GetMemberData();
            _loc_2.members = toUIDArray(fb_namespace::members[0]);
            _loc_2.admins = toUIDArray(fb_namespace::admins[0]);
            _loc_2.officers = toUIDArray(fb_namespace::officers[0]);
            _loc_2.notReplied = toUIDArray(fb_namespace::not_replied[0]);
            return _loc_2;
        }// end function

        protected function toStringValue(param1:XML) : String
        {
            if (param1 == null)
            {
                return null;
            }// end if
            return param1.toString();
        }// end function

        protected function parseGetGroups(param1:XML) : GetGroupData
        {
            var _loc_2:GetGroupData;
            var _loc_3:GroupCollection;
            var _loc_4:*;
            var _loc_5:GroupData;
            _loc_2 = new GetGroupData();
            _loc_3 = new GroupCollection();
            for each (_loc_4 in param1..fb_namespace::group)
            {
                // label
                _loc_5 = new GroupData();
                _loc_5.gid = fb_namespace::gid;
                _loc_5.name = fb_namespace::name;
                _loc_5.nid = fb_namespace::nid;
                _loc_5.description = fb_namespace::description;
                _loc_5.group_type = fb_namespace::group_type;
                _loc_5.group_subtype = fb_namespace::group_subtype;
                _loc_5.recent_news = fb_namespace::recent_news;
                _loc_5.pic = fb_namespace::pic;
                _loc_5.pic_big = fb_namespace::pic_big;
                _loc_5.pic_small = fb_namespace::pic_small;
                _loc_5.creator = fb_namespace::creator;
                _loc_5.update_time = toDate(fb_namespace::update_time);
                _loc_5.office = fb_namespace::office;
                _loc_5.website = fb_namespace::website;
                _loc_5.venue = fb_namespace::venue;
                _loc_5.privacy = fb_namespace::privacy;
                _loc_3.addGroup(_loc_5);
            }// end of for each ... in
            _loc_2.groups = _loc_3;
            return _loc_2;
        }// end function

        protected function parseGetAlbums(param1:XML) : GetAlbumsData
        {
            var _loc_2:GetAlbumsData;
            var _loc_3:AlbumCollection;
            var _loc_4:*;
            var _loc_5:AlbumData;
            _loc_2 = new GetAlbumsData();
            _loc_3 = new AlbumCollection();
            for each (_loc_4 in param1..fb_namespace::album)
            {
                // label
                _loc_5 = new AlbumData();
                _loc_5.aid = toStringValue(fb_namespace::aid[0]);
                _loc_5.cover_pid = toStringValue(fb_namespace::cover_pid[0]);
                _loc_5.owner = fb_namespace::owner;
                _loc_5.name = fb_namespace::name;
                _loc_5.created = toDate(fb_namespace::created);
                _loc_5.modified = toDate(fb_namespace::modified);
                _loc_5.description = fb_namespace::description;
                _loc_5.location = fb_namespace::location;
                _loc_5.link = fb_namespace::link;
                _loc_5.size = fb_namespace::size;
                _loc_5.visible = fb_namespace::visible;
                _loc_3.addAlbum(_loc_5);
            }// end of for each ... in
            _loc_2.albumCollection = _loc_3;
            return _loc_2;
        }// end function

        protected function parseGetCookies(param1:XML) : GetCookiesData
        {
            var _loc_2:GetCookiesData;
            _loc_2 = new GetCookiesData();
            _loc_2.uid = fb_namespace::uid;
            _loc_2.name = fb_namespace::name;
            _loc_2.value = fb_namespace::value;
            _loc_2.expires = fb_namespace::expires;
            _loc_2.path = fb_namespace::path;
            return _loc_2;
        }// end function

        protected function getTemplate(param1:XMLList, param2:TemplateCollection) : void
        {
            var _loc_3:*;
            var _loc_4:TemplateData;
            for each (_loc_3 in param1)
            {
                // label
                _loc_4 = new TemplateData();
                _loc_4.type = _loc_3.localName();
                _loc_4.template_body = fb_namespace::template_body;
                _loc_4.template_title = fb_namespace::template_title;
                param2.addTemplateData(_loc_4);
            }// end of for each ... in
            return;
        }// end function

        protected function parseGetInfo(param1:XML) : GetInfoData
        {
            var _loc_2:FacebookUserCollection;
            var _loc_3:XMLList;
            var _loc_4:uint;
            var _loc_5:uint;
            var _loc_6:GetInfoData;
            var _loc_7:FacebookUser;
            _loc_2 = new FacebookUserCollection();
            _loc_3 = param1..fb_namespace::user;
            _loc_4 = _loc_3.length();
            _loc_5 = 0;
            while (_loc_5++ < _loc_4)
            {
                // label
                _loc_7 = FacebookUserXMLParser.createFacebookUser(_loc_3[_loc_5], fb_namespace);
                _loc_2.addItem(_loc_7);
            }// end while
            _loc_6 = new GetInfoData();
            _loc_6.userCollection = _loc_2;
            return _loc_6;
        }// end function

        protected function parseGetFriends(param1:XML) : GetFriendsData
        {
            var _loc_2:GetFriendsData;
            var _loc_3:FacebookUserCollection;
            var _loc_4:*;
            var _loc_5:FacebookUser;
            _loc_2 = new GetFriendsData();
            _loc_3 = new FacebookUserCollection();
            for each (_loc_4 in param1..fb_namespace::uid)
            {
                // label
                _loc_5 = new FacebookUser();
                _loc_5.uid = _loc_4;
                _loc_3.addItem(_loc_5);
            }// end of for each ... in
            _loc_2.friends = _loc_3;
            return _loc_2;
        }// end function

        protected function parseGetAppUsersData(param1:XML) : GetAppUserData
        {
            var _loc_2:Array;
            var _loc_3:GetAppUserData;
            _loc_2 = toUIDArray(param1);
            _loc_3 = new GetAppUserData();
            _loc_3.uids = _loc_2;
            return _loc_3;
        }// end function

        protected function parseGetMembers(param1:XML) : GetMembersData
        {
            var _loc_2:GetMembersData;
            _loc_2 = new GetMembersData();
            _loc_2.attending = toUIDArray(param1..fb_namespace::attending[0]);
            _loc_2.unsure = toUIDArray(param1..fb_namespace::unsure[0]);
            _loc_2.declined = toUIDArray(param1..fb_namespace::declined[0]);
            _loc_2.not_replied = toUIDArray(param1..fb_namespace::not_replied[0]);
            return _loc_2;
        }// end function

        public function validateFacebookResponce(param1:String) : FacebookError
        {
            var error:FacebookError;
            var xml:XML;
            var xmlError:Error;
            var hasXMLError:Boolean;
            var result:* = param1;
            error;
            hasXMLError;
            try
            {
                xml = new XML(result);
            }// end try
            catch (e)
            {
                xmlError = e;
                hasXMLError;
            }// end catch
            if (hasXMLError == false)
            {
                if (xml.localName() == "error_response")
                {
                    error = new FacebookError();
                    error.rawResult = result;
                    error.errorCode = Number(fb_namespace::error_code);
                    error.errorMsg = fb_namespace::error_msg;
                    error.requestArgs = xmlToUrlVariables(xml..arg);
                }// end if
                return error;
            }// end if
            if (hasXMLError == true)
            {
                error = new FacebookError();
                error.error = xmlError;
                error.errorCode = -1;
            }// end if
            return error;
        }// end function

    }
}
