package hf.view.main.leftInfo
{
    import com.minutes.ui.control.*;
    import com.minutes.ui.core.*;
    import flash.events.*;
    import flash.text.*;
    import hf.control.*;
    import hf.model.*;
    import hf.view.*;
    import hf.view.common.*;

    public class GiftWindow extends BaseWindow
    {
        private var _giftItemList:Array;
        private var _confirmHandler:Function;
        private var _directionText:String;
        private var directionTextField:TextField;
        private var confirmButton:LipiButton;
        private var _vipGiftItemList:Array;
        private var _giftData:Object;
        private var vipGiftList:HList;
        private var giftList:HList;
        private var vipTextField:TextField;
        private var _vipText:String = "";

        public function GiftWindow()
        {
            width = 380;
            height = 300;
            mode = true;
            title = Language.L["礼包"];
            return;
        }// end function

        public function set giftItemList(param1:Array) : void
        {
            _giftItemList = param1;
            if (giftList != null)
            {
                giftList.dataList = param1;
            }// end if
            return;
        }// end function

        public function get directionText() : String
        {
            return _directionText;
        }// end function

        override public function init() : void
        {
            var _loc_3:Object;
            var _loc_4:TextField;
            var _loc_5:String;
            directionTextField = new TextField();
            directionTextField.selectable = false;
            var _loc_1:* = new TextFormat("Tahoma", 14, 3355443);
            _loc_1.leading = 8;
            directionTextField.defaultTextFormat = _loc_1;
            directionTextField.width = 340;
            directionTextField.height = 90;
            directionTextField.wordWrap = true;
            directionTextField.multiline = true;
            directionTextField.x = 20;
            directionTextField.y = 35;
            addChild(directionTextField);
            giftList = new HList(GiftItem, 5);
            giftList.align = "center";
            giftList.width = 340;
            giftList.height = 90;
            giftList.x = 10;
            giftList.y = 115;
            addChild(giftList);
            vipTextField = new TextField();
            vipTextField.selectable = false;
            vipTextField.y = 215;
            vipTextField.width = 340;
            vipTextField.height = 53;
            vipTextField.x = 20;
            var _loc_2:* = new TextFormat("Tahoma", 12, 10027008);
            _loc_2.leading = 5;
            vipTextField.defaultTextFormat = _loc_2;
            vipTextField.wordWrap = true;
            vipTextField.multiline = true;
            addChild(vipTextField);
            vipGiftList = new HList(GiftItem, 5);
            vipGiftList.visible = false;
            vipGiftList.align = "center";
            vipGiftList.width = 340;
            vipGiftList.height = 90;
            vipGiftList.x = 10;
            vipGiftList.y = 240;
            addChild(vipGiftList);
            confirmButton = new LipiButton();
            confirmButton.bgAlpha = 0;
            confirmButton.bgSkin = new LipiSkin(MaterialLib.getInstance().getClass("ButtonOrange"));
            confirmButton.width = 65;
            confirmButton.height = 25;
            confirmButton.x = (width - confirmButton.width) / 2;
            confirmButton.y = height - 40;
            confirmButton.label = Language.L["确定"];
            confirmButton.textColor = 16777215;
            confirmButton.addEventListener(MouseEvent.CLICK, confirmButtonClick);
            addChild(confirmButton);
            if (directionText != null)
            {
                directionTextField.htmlText = directionText;
            }// end if
            if (giftItemList != null)
            {
                giftList.dataList = giftItemList;
            }// end if
            if (vipGiftItemList != null && vipGiftItemList.length > 0)
            {
                vipGiftList.dataList = vipGiftItemList;
                vipGiftList.visible = true;
                height = 380;
                confirmButton.y = height - 40;
                this.y = this.y - 30;
            }
            else
            {
                vipGiftList.visible = false;
                height = 300;
                confirmButton.y = height - 40;
            }// end else if
            vipTextField.htmlText = vipText;
            if (Version.value == Version.FACEBOOK)
            {
                _loc_3 = MData.getInstance().parametersData.parameters;
                if (_loc_3 != null)
                {
                    if (_loc_3.hasOwnProperty("invite") && parseInt(_loc_3["invite"]) < 10)
                    {
                        _loc_4 = new TextField();
                        _loc_4.selectable = false;
                        _loc_4.mouseEnabled = true;
                        _loc_4.width = width;
                        _loc_4.height = 20;
                        _loc_4.x = 0;
                        _loc_4.y = confirmButton.y - 25;
                        _loc_4.text = "Do you want to get more gifts everyday ？";
                        _loc_5 = INI.getInstance().data.version.@loginurl + "/?act=invite";
                        _loc_4.setTextFormat(new TextFormat("Tahoma", 12, 26367, true, null, true, _loc_5, "_top", "center"));
                        addChild(_loc_4);
                    }// end if
                }// end if
            }// end if
            return;
        }// end function

        public function set confirmHandler(param1:Function) : void
        {
            _confirmHandler = param1;
            return;
        }// end function

        public function set directionText(param1:String) : void
        {
            _directionText = param1;
            if (directionTextField != null)
            {
                directionTextField.htmlText = param1;
            }// end if
            return;
        }// end function

        public function set vipText(param1:String) : void
        {
            _vipText = param1;
            if (vipTextField != null)
            {
                vipTextField.htmlText = param1;
            }// end if
            return;
        }// end function

        public function get vipGiftItemList() : Array
        {
            return _vipGiftItemList;
        }// end function

        public function get giftItemList() : Array
        {
            return _giftItemList;
        }// end function

        private function confirmButtonClick(param1:MouseEvent) : void
        {
            closeHandler();
            if (_confirmHandler != null)
            {
                _confirmHandler();
            }// end if
            return;
        }// end function

        public function get vipText() : String
        {
            return _vipText;
        }// end function

        public function set giftData(param1:Object) : void
        {
            _giftData = param1;
            title = param1["title"];
            directionText = param1["direction"];
            giftItemList = param1["item"];
            if (param1.hasOwnProperty("vipText"))
            {
                vipText = param1["vipText"];
            }// end if
            if (param1.hasOwnProperty("vipItem") && param1["vipItem"] != false)
            {
                vipGiftItemList = param1["vipItem"];
            }// end if
            return;
        }// end function

        public function get giftData() : Object
        {
            return _giftData;
        }// end function

        public function set vipGiftItemList(param1:Array) : void
        {
            _vipGiftItemList = param1;
            if (vipGiftList != null)
            {
                if (param1 != null && param1.length > 0)
                {
                    vipGiftList.dataList = param1;
                    vipGiftList.visible = true;
                    height = 380;
                    confirmButton.y = height - 40;
                    this.y = this.y - 30;
                }
                else
                {
                    vipGiftList.visible = false;
                    height = 300;
                    confirmButton.y = height - 40;
                }// end if
            }// end else if
            return;
        }// end function

    }
}
