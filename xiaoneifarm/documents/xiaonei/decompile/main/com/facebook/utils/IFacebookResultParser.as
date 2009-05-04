package com.facebook.utils
{
    import com.facebook.data.*;
    import com.facebook.errors.*;

    public interface IFacebookResultParser
    {

        public function IFacebookResultParser();

        function parse(param1:String, param2:String) : FacebookData;

        function validateFacebookResponce(param1:String) : FacebookError;

    }
}
