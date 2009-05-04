package hf.view.main.friend
{
    import com.minutes.ui.core.*;
    import flash.display.*;
    import flash.events.*;
    import flash.net.*;
    import flash.text.*;
    import hf.view.*;

    public class Friend extends LipiUIComponent
    {
        private var _userLevel:int = 0;
        private var _fiendData:Object = null;
        private var _vipLevel:int = 0;
        private var _vipLevelMaterial:String = "VipL";
        private var leveBg:LipiUIComponent;
        private var _icon:String;
        private var _selected:Boolean = false;
        private var vip:Sprite;
        private var _isme:Boolean = false;
        private var userIcon:Sprite;
        private var leve:TextField;
        private var _vipStatus:int = 0;
        private var userName:TextField;

        public function Friend()
        {
            this.buttonMode = true;
            this.height = 32;
            this.width = 168;
            this.bgColor = 16777215;
            this.bgAlpha = 0.01;
            leve = new TextField();
            leve.defaultTextFormat = new TextFormat("Tahoma");
            userIcon = new Sprite();
            userName = new TextField();
            userName.defaultTextFormat = new TextFormat("Tahoma");
            leveBg = new LipiUIComponent();
            vip = new Sprite();
            addChild(leveBg);
            addChild(leve);
            addChild(userIcon);
            addChild(userName);
            leveBg.width = 25;
            leveBg.height = 23;
            leveBg.x = 6;
            leveBg.y = 5;
            leveBg.bgColor = 16777215;
            leveBg.bgAlpha = 0;
            leve.width = 32;
            leve.height = 32;
            leve.x = 0;
            leve.y = 5;
            leve.autoSize = "center";
            leve.mouseEnabled = false;
            userIcon.x = 35;
            userIcon.y = 4;
            userName.height = 32;
            userName.x = 70;
            userName.y = 6;
            userName.autoSize = "left";
            userName.mouseEnabled = false;
            userName.selectable = false;
            userName.textColor = 3355443;
            this.addEventListener(MouseEvent.MOUSE_OVER, changBg);
            this.addEventListener(MouseEvent.MOUSE_OUT, backBg);
            this.addEventListener(MouseEvent.CLICK, dispatchData);
            return;
        }// end function

        public function get icon() : String
        {
            return _icon;
        }// end function

        public function set isMe(param1:Boolean) : void
        {
            _isme = param1;
            if (_isme)
            {
                userName.setTextFormat(new TextFormat(null, 12, 13369344));
                userlevel = _userLevel;
            }
            else
            {
                userName.setTextFormat(new TextFormat(null, 12, 3355443));
                userlevel = _userLevel;
            }// end else if
            return;
        }// end function

        private function changBg(param1:MouseEvent) : void
        {
            if (!_selected)
            {
                this.bgAlpha = 0.5;
            }// end if
            return;
        }// end function

        private function ioErrorHandler(param1:IOErrorEvent) : void
        {
            var _loc_2:int;
            userIcon.graphics.clear();
            while (_loc_2 < userIcon.numChildren)
            {
                // label
                userIcon.removeChildAt(0);
                _loc_2++;
            }// end while
            return;
        }// end function

        public function get user() : String
        {
            return userName.text;
        }// end function

        public function set vipLevel(param1:int) : void
        {
            _vipLevel = param1;
            return;
        }// end function

        private function completeHandler(param1:Event) : void
        {
            var loader:Loader;
            var event:* = param1;
            try
            {
                loader = Loader(event.target.loader);
                loader.scaleX = 25 / loader.width;
                loader.scaleY = 25 / loader.height;
            }// end try
            catch (e:Error)
            {
            }// end catch
            return;
        }// end function

        public function set levelBg(param1:Class) : void
        {
            if (param1 != null)
            {
                leveBg.bgSkin = new LipiSkin(param1);
                leveBg.width = 22;
                leveBg.height = 22;
            }// end if
            return;
        }// end function

        private function backBg(param1:MouseEvent) : void
        {
            if (!_selected)
            {
                this.bgAlpha = 0.01;
            }// end if
            return;
        }// end function

        public function set user(param1:String) : void
        {
            var i:int;
            var f:DisplayObject;
            var vipDataClass:Class;
            var vipData:BitmapData;
            var value:* = param1;
            if (value == null)
            {
                value;
            }// end if
            userName.text = value;
            if (vip != null && vip.numChildren > 0)
            {
                i;
                while (i < vip.numChildren)
                {
                    // label
                    f = vip.removeChildAt(0);
                    f;
                    i = i++;
                }// end while
            }// end if
            if (vipStatus != 0)
            {
                try
                {
                    vip.y = 10;
                    vip.x = userName.x + userName.textWidth + 5;
                    vipDataClass = MaterialLib.getInstance().getClass(_vipLevelMaterial + vipLevel) as Class;
                    vipData = new vipDataClass(16, 13);
                    vip.addChild(new Bitmap(vipData));
                    addChild(vip);
                }// end try
                catch (e:Error)
                {
                }// end if
            }// end catch
            return;
        }// end function

        public function get userlevel() : int
        {
            return _userLevel;
        }// end function

        public function set vipStatus(param1:int) : void
        {
            _vipStatus = param1;
            return;
        }// end function

        public function get isMe() : Boolean
        {
            return _isme;
        }// end function

        public function set fiendData(param1:Object) : void
        {
            var _loc_4:int;
            var _loc_5:int;
            var _loc_6:String;
            var _loc_7:int;
            _fiendData = param1;
            var _loc_2:String;
            if (param1.yellowlevel == null)
            {
                vipLevel = 0;
            }
            else
            {
                vipLevel = param1.yellowlevel;
            }// end else if
            if (param1.yellowstatus == null)
            {
                vipStatus = 0;
            }
            else
            {
                vipStatus = param1.yellowstatus;
            }// end else if
            icon = param1.headPic;
            userlevel = param1.sort;
            var _loc_3:* = param1.userName.length;
            if (_loc_3 > 4)
            {
                _loc_4 = 0;
                _loc_5 = 0;
                _loc_6 = "";
                _loc_7 = 0;
                while (_loc_7 < _loc_3)
                {
                    // label
                    if (param1.userName.charCodeAt(_loc_7) > 255)
                    {
                        _loc_4 = _loc_4 + 1;
                    }
                    else
                    {
                        _loc_5 = _loc_5 + 1;
                    }// end else if
                    if (_loc_4 + _loc_5 / 2 > 4)
                    {
                        break;
                    }// end if
                    _loc_6 = _loc_6 + param1.userName.charAt(_loc_7);
                    _loc_7++;
                }// end while
                if (_loc_4 + _loc_5 / 2 > 4)
                {
                    _loc_2 = _loc_6 + "..";
                }
                else
                {
                    _loc_2 = param1.userName;
                }// end else if
            }
            else
            {
                _loc_2 = param1.userName;
            }// end else if
            user = _loc_2;
            isMe = param1.me;
            return;
        }// end function

        public function get vipLevel() : int
        {
            return _vipLevel;
        }// end function

        public function get vipStatus() : int
        {
            return _vipStatus;
        }// end function

        private function dispatchData(param1:MouseEvent) : void
        {
            var _loc_2:* = new ViewEvent("friend", true);
            _loc_2.data = _fiendData;
            this.dispatchEvent(_loc_2);
            return;
        }// end function

        public function set userlevel(param1:int) : void
        {
            _userLevel = param1;
            leve.text = param1.toString();
            var _loc_2:int;
            if (param1 < 10)
            {
                if (param1 <= 3)
                {
                    _loc_2 = 16777215;
                    if (isMe)
                    {
                        _loc_2 = 13369344;
                    }// end if
                    leve.setTextFormat(new TextFormat("fontForte", 18, _loc_2, true));
                }
                else
                {
                    if (isMe)
                    {
                        _loc_2 = 13369344;
                    }// end if
                    leve.setTextFormat(new TextFormat("fontForte", 18, _loc_2, true));
                }// end else if
                leve.y = 4;
            }
            else if (param1 >= 10 && param1 < 100)
            {
                if (isMe)
                {
                    _loc_2 = 13369344;
                }// end if
                leve.setTextFormat(new TextFormat("fontForte", 14, _loc_2, true));
                leve.y = 7;
            }
            else if (param1 >= 100 && param1 < 1000)
            {
                if (isMe)
                {
                    _loc_2 = 13369344;
                }// end if
                leve.setTextFormat(new TextFormat("fontForte", 12, _loc_2, true));
                leve.y = 8;
            }
            else if (param1 >= 1000 && param1 < 10000)
            {
                if (isMe)
                {
                    _loc_2 = 13369344;
                }// end if
                leve.setTextFormat(new TextFormat("fontForte", 11, _loc_2, false));
                leve.y = 9;
            }
            else
            {
                if (isMe)
                {
                    _loc_2 = 13369344;
                }// end if
                leve.setTextFormat(new TextFormat("fontForte", 8, _loc_2, false));
                leve.y = 9;
            }// end else if
            leve.embedFonts = true;
            return;
        }// end function

        public function set icon(param1:String) : void
        {
            var index:int;
            var i:int;
            var loader:Loader;
            var request:URLRequest;
            var value:* = param1;
            _icon = value;
            try
            {
                index = userIcon.numChildren;
                userIcon.graphics.clear();
                if (index > 0)
                {
                    while (i < index)
                    {
                        // label
                        userIcon.removeChildAt(0);
                        i = i++;
                    }// end while
                }// end if
            }// end try
            catch (e:Error)
            {
                try
                {
                }// end catch
                loader = new Loader();
                loader.contentLoaderInfo.addEventListener(Event.COMPLETE, completeHandler);
                loader.contentLoaderInfo.addEventListener(IOErrorEvent.IO_ERROR, ioErrorHandler);
                request = new URLRequest(value);
                loader.load(request);
                userIcon.addChild(loader);
            }// end try
            catch (e:Error)
            {
            }// end catch
            return;
        }// end function

        public function set selected(param1:String) : void
        {
            if (param1 == _fiendData.userId)
            {
                _selected = true;
                this.bgAlpha = 1;
            }
            else
            {
                _selected = false;
                this.bgAlpha = 0.01;
            }// end else if
            return;
        }// end function

    }
}
