package com.facebook.data.users
{
    import com.facebook.utils.*;

    public class AffiliationCollection extends FacebookArrayCollection
    {

        public function AffiliationCollection()
        {
            super(null, AffiliationData);
            return;
        }// end function

        public function addAffiliation(param1:AffiliationData) : void
        {
            this.addItem(param1);
            return;
        }// end function

    }
}
