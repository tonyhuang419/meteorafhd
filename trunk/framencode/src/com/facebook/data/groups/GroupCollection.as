package com.facebook.data.groups
{
    import com.facebook.utils.*;

    public class GroupCollection extends FacebookArrayCollection
    {

        public function GroupCollection()
        {
            super(null, GroupData);
            return;
        }// end function

        public function addGroup(param1:GroupData) : void
        {
            this.addItem(param1);
            return;
        }// end function

    }
}
