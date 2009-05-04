package com.facebook
{
    import com.facebook.commands.auth.*;
    import com.facebook.events.*;
    import com.facebook.net.*;
    import com.facebook.session.*;
    import flash.events.*;
    import flash.net.*;

    public class Facebook extends EventDispatcher
    {
        public var waiting_for_login:Boolean;
        public var connectionErrorMessage:String;
        protected var _currentSession:IFacebookSession;

        public function Facebook() : void
        {
            return;
        }// end function

        public function get api_version() : String
        {
            return _currentSession ? (this._currentSession.api_version) : (null);
        }// end function

        public function get expires() : Date
        {
            return _currentSession ? (this._currentSession.expires) : (new Date());
        }// end function

        public function get uid() : String
        {
            return _currentSession ? (this._currentSession.uid) : (null);
        }// end function

        public function grantPermission(param1:Boolean) : void
        {
            var _loc_2:String;
            _loc_2 = "http://www.facebook.com/login.php?return_session=" + (param1 ? (1) : (0)) + "&api_key=" + api_key;
            navigateToURL(new URLRequest(_loc_2), "_blank");
            return;
        }// end function

        public function refreshSession() : void
        {
            _currentSession.refreshSession();
            return;
        }// end function

        public function logout() : void
        {
            post(new ExpireSession());
            return;
        }// end function

        public function startSession(param1:IFacebookSession) : void
        {
            _currentSession = param1;
            if (_currentSession.is_connected)
            {
                dispatchEvent(new FacebookEvent(FacebookEvent.CONNECT, false, false, true));
            }
            else
            {
                _currentSession.addEventListener(FacebookEvent.CONNECT, onSessionConnected);
                _currentSession.addEventListener(FacebookEvent.WAITING_FOR_LOGIN, onWaitingForLogin);
            }// end else if
            return;
        }// end function

        public function post(param1:FacebookCall) : FacebookCall
        {
            var _loc_2:IFacebookCallDelegate;
            if (_currentSession)
            {
                param1.session = _currentSession;
                param1.initialize();
                _loc_2 = _currentSession.post(param1);
                param1.delegate = _loc_2;
            }
            else
            {
                throw new Error("Cannot post a call; no session has been set.");
            }// end else if
            return param1;
        }// end function

        protected function onSessionConnected(param1:FacebookEvent) : void
        {
            var _loc_2:IFacebookSession;
            _loc_2 = param1.target as IFacebookSession;
            dispatchEvent(param1);
            return;
        }// end function

        public function grantExtendedPermission(param1:String) : void
        {
            navigateToURL(new URLRequest("http://www.facebook.com/authorize.php?api_key=" + api_key + "&v=1" + api_version + "&ext_perm=" + param1), "_blank");
            return;
        }// end function

        public function login(param1:Boolean) : void
        {
            _currentSession.login(param1);
            return;
        }// end function

        protected function onWaitingForLogin(param1:FacebookEvent) : void
        {
            waiting_for_login = true;
            dispatchEvent(new FacebookEvent(FacebookEvent.WAITING_FOR_LOGIN));
            return;
        }// end function

        public function get secret() : String
        {
            return _currentSession ? (this._currentSession.secret) : (null);
        }// end function

        public function get session_key() : String
        {
            return _currentSession ? (this._currentSession.session_key) : (null);
        }// end function

        public function get api_key() : String
        {
            return _currentSession ? (this._currentSession.api_key) : (null);
        }// end function

        public function get is_connected() : Boolean
        {
            return _currentSession ? (this._currentSession.is_connected) : (false);
        }// end function

    }
}
