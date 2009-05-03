package com.facebook.data.users
{
    import com.facebook.utils.*;

    public class FacebookUserCollection extends FacebookArrayCollection
    {

        public function FacebookUserCollection()
        {
            super(null, FacebookUser);
            return;
        }// end function

        public function getUserById(param1:int) : FacebookUser
        {
            return findItemByProperty("uid", param1) as FacebookUser;
        }// end function

        public function addUser(param1:FacebookUser) : void
        {
            addItem(param1);
            return;
        }// end function

    }
}
