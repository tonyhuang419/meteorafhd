package com.facebook.session
{
    import com.facebook.*;
    import com.facebook.commands.photos.*;
    import com.facebook.delegates.*;
    import com.facebook.events.*;
    import com.facebook.net.*;
    import flash.events.*;

    public class WebSession extends EventDispatcher implements IFacebookSession
    {
        protected var _api_key:String;
        protected var _api_version:String = "1.0";
        var _uid:String;
        protected var _is_connected:Boolean = false;
        public var login_url:String = "http://www.facebook.com/login.php";
        protected var _secret:String;
        protected var _rest_url:String = "http://api.facebook.com/restserver.php";
        protected var _expires:Date;
        protected var _session_key:String;

        public function WebSession(param1:String, param2:String, param3:String = null)
        {
            _api_version = "1.0";
            _is_connected = false;
            _rest_url = "http://api.facebook.com/restserver.php";
            login_url = "http://www.facebook.com/login.php";
            this._api_key = param1;
            this._session_key = param3;
            this.secret = param2;
            return;
        }// end function

        public function get rest_url() : String
        {
            return _rest_url;
        }// end function

        public function set rest_url(param1:String) : void
        {
            _rest_url = param1;
            return;
        }// end function

        public function get waiting_for_login() : Boolean
        {
            return false;
        }// end function

        public function post(param1:FacebookCall) : IFacebookCallDelegate
        {
            if (param1.method == UploadPhoto.METHOD_NAME)
            {
                return new WebImageUploadDelegate(param1, this);
            }// end if
            return new WebDelegate(param1, this);
        }// end function

        public function get secret() : String
        {
            return _secret;
        }// end function

        public function get api_version() : String
        {
            return this._api_version;
        }// end function

        public function get is_connected() : Boolean
        {
            return _is_connected;
        }// end function

        public function get session_key() : String
        {
            return _session_key;
        }// end function

        public function get api_key() : String
        {
            return _api_key;
        }// end function

        public function get expires() : Date
        {
            return _expires;
        }// end function

        public function get uid() : String
        {
            return facebook_internal::_uid;
        }// end function

        public function refreshSession() : void
        {
            return;
        }// end function

        public function verifySession() : void
        {
            if (_session_key)
            {
                _is_connected = true;
                dispatchEvent(new FacebookEvent(FacebookEvent.CONNECT, false, false, true));
            }
            else
            {
                _is_connected = false;
                dispatchEvent(new FacebookEvent(FacebookEvent.CONNECT, false, false, false));
            }// end else if
            return;
        }// end function

        public function set secret(param1:String) : void
        {
            _secret = param1;
            return;
        }// end function

        public function set api_version(param1:String) : void
        {
            this._api_version = param1;
            return;
        }// end function

        public function login(param1:Boolean) : void
        {
            return;
        }// end function

        public function set session_key(param1:String) : void
        {
            _session_key = param1;
            return;
        }// end function

    }
}
