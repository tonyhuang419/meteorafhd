package hf.view.main.window.profile
{
    import com.lipi.*;
    import com.minutes.ui.control.*;
    import flash.events.*;
    import hf.control.*;
    import hf.model.*;
    import hf.view.*;
    import hf.view.common.*;

    public class Profile extends BaseWindow
    {
        private var _selected:int = 0;
        private var mainData:MainData;
        private var profileLog:ProfileLog;
        private var profileMsg:ProfileMsg;
        private var profileHead:ProfileHead;
        private var profileHistory:ProfileHistory;
        private var dataLoading:DataLoading;
        private var profileNav:JerryTabNavigation;
        private var _me:Boolean = false;

        public function Profile()
        {
            title = Language.L["个人信息"];
            windowName = "profile";
            width = 500;
            height = 400;
            mode = true;
            return;
        }// end function

        public function set selected(param1:int) : void
        {
            if (param1 < 0)
            {
                me = true;
                param1 = 0;
            }
            else
            {
                me = false;
            }// end else if
            if (profileNav != null)
            {
                profileNav.selectedIndex = param1;
            }// end if
            _selected = param1;
            return;
        }// end function

        override public function init() : void
        {
            mainData = MData.getInstance().mainData;
            profileHead = new ProfileHead();
            profileHead.visible = !mainData.profileLoading;
            profileHead.x = 0;
            profileHead.y = 25;
            addChild(profileHead);
            if (mainData.profile != null)
            {
                profileHead.data = mainData.profile["user"];
            }// end if
            profileNav = new JerryTabNavigation();
            profileNav.visible = !mainData.profileLoading;
            profileNav.selectedFontStyle(12, 16777215, "", true);
            profileNav.defaultFontStyle(12, 39423);
            profileNav.menuLeftDistance = 25;
            profileNav.menuSpaceDistance = 3;
            profileNav.menuDefaultButtonWidth = 55;
            profileNav.menuDefaultButtonHeight = 22;
            profileNav.menuHeight = 25;
            profileNav.topHeight = 2;
            profileNav.x = 0;
            profileNav.y = 150;
            profileNav.width = 499;
            profileNav.menuBgClass = MaterialLib.getInstance().getClass("TabBg");
            profileNav.menuDefaultSkin = MaterialLib.getInstance().getClass("TabDefault");
            profileNav.menuSelectedSkin = MaterialLib.getInstance().getClass("TabSelected");
            addChild(profileNav);
            profileLog = new ProfileLog();
            var _loc_1:* = Language.L["消息"];
            var _loc_2:* = getStringWidth(_loc_1);
            profileNav.addItem(_loc_1, profileLog, _loc_2, 22);
            profileMsg = new ProfileMsg();
            me = me;
            _loc_1 = Language.L["留言"];
            _loc_2 = getStringWidth(_loc_1);
            profileNav.addItem(_loc_1, profileMsg, _loc_2, 22);
            profileHistory = new ProfileHistory();
            _loc_1 = Language.L["成果"];
            _loc_2 = getStringWidth(_loc_1);
            profileNav.addItem(_loc_1, profileHistory, _loc_2, 22);
            dataLoading = new DataLoading();
            dataLoading.addEventListener(ViewEvent.LINK_CLICK, linkClick);
            dataLoading.visible = mainData.profileLoading;
            dataLoading.x = width / 2;
            dataLoading.y = height / 2;
            addChild(dataLoading);
            mainData.addEventListener(MainData.PROFILE, profileChange, false, 0, true);
            mainData.addEventListener(MainData.PROFILE_LOADING, profileLoading, false, 0, true);
            mainData.addEventListener(MainData.PROFILE_ERR, profileErr, false, 0, true);
            return;
        }// end function

        private function profileLoading(param1:Event) : void
        {
            dataLoading.visible = mainData.profileLoading;
            var _loc_2:* = !mainData.profileLoading;
            profileNav.visible = !mainData.profileLoading;
            profileHead.visible = _loc_2;
            return;
        }// end function

        public function get me() : Boolean
        {
            return _me;
        }// end function

        public function get selected() : int
        {
            return _selected;
        }// end function

        private function linkClick(param1:ViewEvent) : void
        {
            if (param1.data == "reload")
            {
                if (me)
                {
                    Command.getInstance().mainCommand.getAllInfo(true);
                }
                else
                {
                    Command.getInstance().mainCommand.getAllInfo();
                }// end if
            }// end else if
            return;
        }// end function

        public function set me(param1:Boolean) : void
        {
            _me = param1;
            if (profileMsg != null)
            {
                profileMsg.me = param1;
            }// end if
            return;
        }// end function

        override public function addedToLayer() : void
        {
            super.addedToLayer();
            if (me)
            {
                Command.getInstance().mainCommand.getAllInfo(true);
            }
            else
            {
                Command.getInstance().mainCommand.getAllInfo();
            }// end else if
            profileNav.selectedIndex = selected;
            if (profileMsg != null)
            {
                profileMsg.hideReply();
            }// end if
            return;
        }// end function

        private function profileChange(param1:ModelEvent) : void
        {
            profileHead.data = mainData.profile["user"];
            profileLog.dataList = mainData.profile["log"];
            profileMsg.dataList = mainData.profile["chat"];
            profileHistory.dataList = mainData.profile["repertory"];
            return;
        }// end function

        private function profileErr(param1:Event) : void
        {
            dataLoading.errorText = mainData.profileErr;
            return;
        }// end function

        private function getStringWidth(param1:String, param2:int = 55, param3:int = 10) : Number
        {
            var _loc_4:* = LipiUtil.getTextLangth(param1);
            _loc_4 = LipiUtil.getTextLangth(param1) + param3 * 2;
            if (_loc_4 < param2)
            {
                _loc_4 = param2;
            }// end if
            return _loc_4;
        }// end function

    }
}
