package hf.view.main.leftInfo
{
    import com.facebook.*;
    import com.facebook.commands.batch.*;
    import com.facebook.commands.photos.*;
    import com.facebook.commands.users.*;
    import com.facebook.data.*;
    import com.facebook.data.batch.*;
    import com.facebook.data.photos.*;
    import com.facebook.data.users.*;
    import com.facebook.events.*;
    import com.facebook.session.*;
    import com.minutes.global.*;
    import flash.display.*;
    import flash.events.*;
    import hf.control.*;
    import hf.model.*;
    import hf.view.common.*;
    import hf.view.farm.toolBar.*;
    import hf.view.main.WindowControl.*;
    import hf.view.main.tip.*;
    import hf.view.main.window.facebook.*;

    public class MyIconBar extends Sprite
    {
        private var _informationIcon:InformationIcon;
        private var _msg:Boolean = false;
        private var _gift:Boolean = false;
        private var _postIcon:PostIcon;
        private var _msgIcon:MsgIcon;
        private var _giftIcon:GiftIcon;
        private var _information:Boolean = false;
        private var cameraIcon:CameraIcon;
        private var _post:Object = false;

        public function MyIconBar()
        {
            _informationIcon = new InformationIcon();
            _informationIcon.tipText = Language.L["消息"];
            _informationIcon.addEventListener(MouseEvent.CLICK, informationIconClick);
            _informationIcon.addEventListener(MouseEvent.ROLL_OVER, onOver);
            _informationIcon.addEventListener(MouseEvent.ROLL_OUT, onOut);
            _informationIcon.x = 0;
            _informationIcon.y = 4;
            addChild(_informationIcon);
            _msgIcon = new MsgIcon();
            _msgIcon.tipText = Language.L["留言"];
            _msgIcon.addEventListener(MouseEvent.CLICK, msgIconClick);
            _msgIcon.addEventListener(MouseEvent.ROLL_OVER, onOver);
            _msgIcon.addEventListener(MouseEvent.ROLL_OUT, onOut);
            _msgIcon.x = 45;
            _msgIcon.y = 5;
            addChild(_msgIcon);
            if (Version.value == Version.FACEBOOK)
            {
                cameraIcon = new CameraIcon();
                cameraIcon.visible = false;
                cameraIcon.tipText = "Take a Photo";
                cameraIcon.addEventListener(MouseEvent.ROLL_OVER, onOver);
                cameraIcon.addEventListener(MouseEvent.ROLL_OUT, onOut);
                cameraIcon.addEventListener(MouseEvent.CLICK, cameraIconClick);
                cameraIcon.x = 0;
                cameraIcon.y = 43;
                addChild(cameraIcon);
                getPermission();
            }// end if
            MData.getInstance().mainData.addEventListener(MainData.GIFT_PACKAGE, giftPackage, false, 0, true);
            return;
        }// end function

        public function set post(param1:Object) : void
        {
            var _loc_2:Object;
            _post = param1;
            if (Boolean(param1))
            {
                if (_postIcon == null)
                {
                    _postIcon = new PostIcon();
                    _postIcon.tipText = Language.L["公告"];
                    _postIcon.y = 2;
                    _postIcon.addEventListener(MouseEvent.CLICK, postClick);
                    _postIcon.addEventListener(MouseEvent.ROLL_OVER, onOver);
                    _postIcon.addEventListener(MouseEvent.MOUSE_OUT, onOut);
                    addChild(_postIcon);
                    setPostPosition();
                }// end if
                _loc_2 = LocalData.getInstance().getObject("postId");
                if (_loc_2 == null || _loc_2.toString() != param1.toString())
                {
                    _postIcon.playIcon();
                }// end if
            }
            else if (_postIcon != null)
            {
                removeChild(_postIcon);
                _postIcon = null;
            }// end else if
            return;
        }// end function

        private function openTakeWindow() : void
        {
            var _loc_1:* = new TakeWindow();
            _loc_1.confirmFn = takeConfirmFn;
            WControl.open(_loc_1);
            return;
        }// end function

        public function set gift(param1:Boolean) : void
        {
            _gift = param1;
            if (_giftIcon == null)
            {
                if (param1)
                {
                    _giftIcon = new GiftIcon();
                    _giftIcon.tipText = Language.L["礼包"];
                    _giftIcon.addEventListener(MouseEvent.CLICK, giftClick);
                    _giftIcon.addEventListener(MouseEvent.ROLL_OVER, onOver);
                    _giftIcon.addEventListener(MouseEvent.MOUSE_OUT, onOut);
                    _giftIcon.x = 86;
                    _giftIcon.y = -3;
                    addChild(_giftIcon);
                    setPostPosition();
                }// end if
            }
            else if (!param1)
            {
                removeChild(_giftIcon);
                _giftIcon = null;
                setPostPosition();
            }// end else if
            return;
        }// end function

        private function informationIconClick(param1:MouseEvent) : void
        {
            var _loc_2:* = MData.getInstance().mainData.unreadData;
            _loc_2["a"] = 0;
            _loc_2["c"] = 0;
            MData.getInstance().mainData.unreadData = _loc_2;
            WControl.openForName("profile", 0);
            return;
        }// end function

        private function getPermission() : void
        {
            var _loc_1:* = new WebSession(FacebookArguments.api_key, FacebookArguments.ss, FacebookArguments.session_key);
            var _loc_2:* = new Facebook();
            _loc_2.startSession(_loc_1);
            var _loc_3:* = new BatchCollection();
            _loc_3.addItem(new HasAppPermission(HasAppPermissionValues.PHOTO_UPLOAD));
            _loc_3.addItem(new GetAlbums());
            var _loc_4:* = _loc_2.post(new BatchRun(_loc_3)) as BatchRun;
            (_loc_2.post(new BatchRun(_loc_3)) as BatchRun).addEventListener(FacebookEvent.COMPLETE, onBatchComplete);
            return;
        }// end function

        private function postClick(param1:MouseEvent) : void
        {
            var _loc_2:* = new PostWindow();
            _loc_2.getPostData();
            WControl.open(_loc_2);
            _postIcon.stopIcon();
            LocalData.getInstance().setObject("postId", post.toString());
            return;
        }// end function

        private function msgIconClick(param1:MouseEvent) : void
        {
            var _loc_2:* = MData.getInstance().mainData.unreadData;
            _loc_2["a"] = 0;
            _loc_2["c"] = 0;
            MData.getInstance().mainData.unreadData = _loc_2;
            WControl.openForName("profile", 1);
            return;
        }// end function

        public function set msg(param1:Boolean) : void
        {
            _msg = param1;
            if (param1)
            {
                _msgIcon.playIcon();
            }
            else
            {
                _msgIcon.stopIcon();
            }// end else if
            return;
        }// end function

        private function onOver(param1:MouseEvent) : void
        {
            TipControl.show("MouseTip", (param1.currentTarget as ToolBase).tipText);
            return;
        }// end function

        private function openTakeWindowAndPermission() : void
        {
            openTakeWindow();
            var _loc_1:* = new WebSession(FacebookArguments.api_key, FacebookArguments.ss, FacebookArguments.session_key);
            var _loc_2:* = new Facebook();
            _loc_2.startSession(_loc_1);
            _loc_2.grantExtendedPermission(HasAppPermissionValues.PHOTO_UPLOAD);
            return;
        }// end function

        public function get msg() : Boolean
        {
            return _msg;
        }// end function

        public function set information(param1:Boolean) : void
        {
            _information = param1;
            if (param1)
            {
                _informationIcon.playIcon();
            }
            else
            {
                _informationIcon.stopIcon();
            }// end else if
            return;
        }// end function

        private function giftWindowConfirm() : void
        {
            var _loc_1:* = MData.getInstance().mainData.unreadData;
            _loc_1["d"] = 0;
            MData.getInstance().mainData.unreadData = _loc_1;
            Command.getInstance().mainCommand.getPackageEnd();
            return;
        }// end function

        private function takeConfirmFn() : void
        {
            var _loc_1:* = new AlertWindow();
            _loc_1.title = "Take a Photo";
            _loc_1.data = {text:"We are uploading the photo to Facebook. It might take a few minutes... " + "Click Confirm to continue the game. ", text_y:70};
            WControl.open(_loc_1);
            return;
        }// end function

        public function get post() : Object
        {
            return _post;
        }// end function

        private function cameraIconClick(param1:MouseEvent) : void
        {
            var _loc_2:PermissionConfirm;
            if (FacebookArguments.permission)
            {
                openTakeWindow();
            }
            else
            {
                _loc_2 = new PermissionConfirm();
                _loc_2.confirmFn = openTakeWindowAndPermission;
                _loc_2.cancelFn = openTakeWindow;
                WControl.open(_loc_2);
            }// end else if
            return;
        }// end function

        private function setPostPosition() : void
        {
            if (_giftIcon == null && _postIcon != null)
            {
                _postIcon.x = 89;
            }
            else if (_giftIcon != null && _postIcon != null)
            {
                _postIcon.x = 133;
            }// end else if
            return;
        }// end function

        public function get information() : Boolean
        {
            return _information;
        }// end function

        private function onOut(param1:MouseEvent) : void
        {
            TipControl.hide();
            return;
        }// end function

        private function onBatchComplete(param1:FacebookEvent) : void
        {
            var _loc_2:Array;
            var _loc_3:int;
            var _loc_4:Object;
            var _loc_5:Array;
            var _loc_6:int;
            cameraIcon.visible = true;
            if (param1.success)
            {
                if (param1.data != null && param1.data.hasOwnProperty("results"))
                {
                    _loc_2 = (param1.data as BatchResult).results;
                    _loc_3 = 0;
                    while (_loc_3 < _loc_2.length)
                    {
                        // label
                        _loc_4 = _loc_2[_loc_3];
                        if (_loc_4 is BooleanResultData)
                        {
                            FacebookArguments.permission = _loc_4["value"];
                        }
                        else if (_loc_4 is GetAlbumsData)
                        {
                            _loc_5 = _loc_4["albumCollection"]["source"];
                            _loc_6 = 0;
                            while (_loc_6 < _loc_5.length)
                            {
                                // label
                                if (_loc_5[_loc_6]["name"] == "Happy Farm Photos")
                                {
                                    FacebookArguments.albumExist = true;
                                }// end if
                                _loc_6++;
                            }// end while
                        }// end else if
                        _loc_3++;
                    }// end while
                }// end if
            }// end if
            return;
        }// end function

        private function giftPackage(param1:Event) : void
        {
            var _loc_2:* = new GiftWindow();
            _loc_2.confirmHandler = giftWindowConfirm;
            _loc_2.giftData = MData.getInstance().mainData.giftPackage;
            WControl.open(_loc_2);
            return;
        }// end function

        public function get gift() : Boolean
        {
            return _gift;
        }// end function

        private function giftClick(param1:MouseEvent) : void
        {
            var _loc_2:GiftWindow;
            if (MData.getInstance().mainData.giftPackage == null)
            {
                Command.getInstance().mainCommand.getPackage();
            }
            else
            {
                _loc_2 = new GiftWindow();
                _loc_2.confirmHandler = giftWindowConfirm;
                _loc_2.giftData = MData.getInstance().mainData.giftPackage;
                WControl.open(_loc_2);
            }// end else if
            return;
        }// end function

    }
}
