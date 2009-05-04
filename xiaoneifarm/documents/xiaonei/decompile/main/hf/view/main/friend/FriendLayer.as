package hf.view.main.friend
{
    import flash.display.*;
    import flash.events.*;
    import hf.control.*;
    import hf.model.*;
    import hf.view.*;

    public class FriendLayer extends Sprite
    {
        private var hideRight:Number = 26;
        private var mainData:MainData;
        private var getFriend:Boolean = false;
        private var paginationFriend:PaginationFriend;
        private var showRight:Number = 187;
        private var offsetValue:int = 0;
        private var easing:Number = 0.4;

        public function FriendLayer()
        {
            addEventListener(Event.ADDED_TO_STAGE, onAddedToStage);
            mainData = MData.getInstance().mainData;
            return;
        }// end function

        private function sortByExp(param1:Event) : void
        {
            Command.getInstance().mainCommand.sortBy("exp");
            return;
        }// end function

        private function refurbish(param1:Event) : void
        {
            Command.getInstance().mainCommand.getFriendList(true);
            return;
        }// end function

        public function sortByGold(param1:Event) : void
        {
            Command.getInstance().mainCommand.sortBy("money");
            return;
        }// end function

        private function shrinkage(param1:ViewEvent) : void
        {
            if (param1.data == true)
            {
                if (getFriend == false)
                {
                    Command.getInstance().mainCommand.getFriendList();
                    getFriend = true;
                }// end if
                effStart(-1);
            }
            else
            {
                effStart(1);
            }// end else if
            return;
        }// end function

        private function friend(param1:ViewEvent) : void
        {
            Command.getInstance().mainCommand.setUser(param1.data);
            return;
        }// end function

        private function onAddedToStage(param1:Event) : void
        {
            removeEventListener(Event.ADDED_TO_STAGE, onAddedToStage);
            createPaginationFriend();
            return;
        }// end function

        private function back(param1:ViewEvent) : void
        {
            Command.getInstance().mainCommand.friendBackPage(param1.data.toString());
            return;
        }// end function

        private function effHandler(param1:Event) : void
        {
            var _loc_2:Number;
            if (offsetValue > 0)
            {
                _loc_2 = stage.stageWidth - hideRight;
            }
            else
            {
                _loc_2 = stage.stageWidth - showRight;
            }// end else if
            paginationFriend.x = paginationFriend.x + (_loc_2 - paginationFriend.x) * easing;
            if (paginationFriend.x < stage.stageWidth - showRight - 2 || paginationFriend.x > (stage.stageWidth - hideRight)--)
            {
                removeEventListener(Event.ENTER_FRAME, effHandler);
            }// end if
            return;
        }// end function

        private function friendChange(param1:Event) : void
        {
            var _loc_2:* = mainData.showFriendPage;
            var _loc_3:* = mainData.showFriendSum;
            paginationFriend.show(MData.getInstance().mainData.showFriendList, _loc_2, _loc_3);
            return;
        }// end function

        private function search(param1:ViewEvent) : void
        {
            Command.getInstance().mainCommand.searchFriend(param1.data.toString());
            return;
        }// end function

        private function next(param1:ViewEvent) : void
        {
            Command.getInstance().mainCommand.friendNextPage(param1.data.toString());
            return;
        }// end function

        private function createPaginationFriend() : void
        {
            var _loc_1:int;
            var _loc_2:int;
            if (paginationFriend == null)
            {
                paginationFriend = new PaginationFriend();
                paginationFriend.addEventListener(PaginationFriend.SHRINKAGE, shrinkage);
                paginationFriend.addEventListener(PaginationFriend.BACK, back);
                paginationFriend.addEventListener(PaginationFriend.NEXT, next);
                paginationFriend.addEventListener(PaginationFriend.SEARCH, search);
                paginationFriend.addEventListener(PaginationFriend.REFURBISH, refurbish);
                paginationFriend.addEventListener(PaginationFriend.FRIEND, friend);
                paginationFriend.addEventListener(PaginationFriend.SORT_BY_EXP, sortByExp);
                paginationFriend.addEventListener(PaginationFriend.SORT_BY_GOLD, sortByGold);
                paginationFriend.x = stage.stageWidth - hideRight;
                paginationFriend.y = 80;
                addChild(paginationFriend);
                if (MData.getInstance().mainData.showFriendList != null)
                {
                    _loc_1 = mainData.showFriendPage;
                    _loc_2 = mainData.showFriendSum;
                    paginationFriend.show(MData.getInstance().mainData.showFriendList, _loc_1, _loc_2);
                }// end if
                mainData.addEventListener(MainData.FRIEND_CHANGE, friendChange);
            }// end if
            return;
        }// end function

        private function effStart(param1:int) : void
        {
            offsetValue = param1;
            addEventListener(Event.ENTER_FRAME, effHandler);
            return;
        }// end function

    }
}
