package hf.view.main.leftInfo
{
    import flash.display.*;
    import flash.events.*;
    import hf.model.*;
    import hf.view.main.head.*;

    public class LeftInfo extends Sprite
    {
        private var iconBar:MyIconBar;
        private var friendInfo:FriendInfo;

        public function LeftInfo()
        {
            iconBar = new MyIconBar();
            iconBar.y = 5;
            addChild(iconBar);
            friendInfo = new FriendInfo();
            friendInfo.visible = false;
            addChild(friendInfo);
            MData.getInstance().mainData.addEventListener(MainData.CURRENT_USER_CHANGE, currentUserChange);
            MData.getInstance().mainData.addEventListener(MainData.UNREAD_DATA, setIconState);
            setIconState();
            return;
        }// end function

        private function setIconState(param1:Event = null) : void
        {
            if (iconBar == null)
            {
                return;
            }// end if
            var _loc_2:* = MData.getInstance().mainData.unreadData;
            if (_loc_2 != null)
            {
                iconBar.information = _loc_2["a"];
                iconBar.post = _loc_2["b"];
                iconBar.msg = _loc_2["c"];
                iconBar.gift = _loc_2["d"];
            }// end if
            return;
        }// end function

        private function currentUserChange(param1:ModelEvent) : void
        {
            var _loc_2:Object;
            if (MData.getInstance().mainData.me)
            {
                iconBar.visible = true;
                friendInfo.visible = false;
            }
            else
            {
                iconBar.visible = false;
                friendInfo.visible = true;
                _loc_2 = MData.getInstance().mainData.currentUser;
                friendInfo.url = _loc_2["headPic"];
                if (!_loc_2.hasOwnProperty("yellowstatus") || _loc_2["yellowstatus"] == 0 || _loc_2["yellowstatus"] == null)
                {
                    friendInfo.color = Level.BLUE;
                    friendInfo.vip = "";
                }
                else
                {
                    friendInfo.color = Level.YELLOW;
                    friendInfo.vip = "VipL" + _loc_2["yellowlevel"];
                }// end else if
                friendInfo.userName = _loc_2["userName"];
                friendInfo.exp = _loc_2["exp"];
                friendInfo.goldValue = _loc_2["money"];
            }// end else if
            return;
        }// end function

    }
}
