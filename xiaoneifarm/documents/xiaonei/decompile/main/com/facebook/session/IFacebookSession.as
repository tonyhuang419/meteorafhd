package com.facebook.session
{
    import com.facebook.delegates.*;
    import com.facebook.net.*;

    public interface IFacebookSession extends IEventDispatcher
    {

        public function IFacebookSession();

        function get rest_url() : String;

        function get waiting_for_login() : Boolean;

        function get api_key() : String;

        function get is_connected() : Boolean;

        function get uid() : String;

        function set rest_url(param1:String) : void;

        function verifySession() : void;

        function set secret(param1:String) : void;

        function post(param1:FacebookCall) : IFacebookCallDelegate;

        function login(param1:Boolean) : void;

        function set session_key(param1:String) : void;

        function get secret() : String;

        function get expires() : Date;

        function get session_key() : String;

        function get api_version() : String;

        function refreshSession() : void;

    }
}
