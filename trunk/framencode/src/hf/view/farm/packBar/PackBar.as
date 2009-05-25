package hf.view.farm.packBar
{
    import com.lipi.*;
    import com.minutes.ui.collections.*;
    import com.minutes.ui.control.*;
    import com.minutes.ui.core.*;
    import flash.display.*;
    import flash.events.*;
    import flash.filters.*;
    import flash.text.*;
    import flash.utils.*;
    import hf.control.*;
    import hf.model.*;
    import hf.view.*;
    import hf.view.common.*;
    import hf.view.main.WindowControl.*;
    import hf.view.main.cursor.*;

    public class PackBar extends SimpleCollection
    {
        private var farmData:FarmData;
        private var rButton:SimpleButton;
        private var lib:MaterialLib;
        private var hlist:HList;
        private var nullText:TextField;
        private var childBar:Sprite;
        private var hideTimer:uint;
        private var dataLoading:DataLoading;
        private var gotoButton:LipiButton;
        private var lButton:SimpleButton;

        public function PackBar()
        {
            lib = MaterialLib.getInstance();
            bg = lib.getMaterial("PackBarBg") as Sprite;
            width = 400;
            height = 70;
            lButton = lib.getMaterial("LeftButton") as SimpleButton;
            lButton.addEventListener(MouseEvent.CLICK, lButtonClick);
            lButton.x = 10;
            lButton.y = (70 - lButton.height) / 2;
            addChild(lButton);
            rButton = lib.getMaterial("RightButton") as SimpleButton;
            rButton.addEventListener(MouseEvent.CLICK, rButtonClick);
            rButton.x = width - rButton.width - 10;
            rButton.y = (70 - rButton.height) / 2;
            addChild(rButton);
            hlist = new HList(PackItem, 6);
            hlist.y = 5;
            hlist.x = 35;
            hlist.width = 330;
            hlist.height = 62;
            addChild(hlist);
            setButtonEnabled();
            nullText = new TextField();
            nullText.autoSize = TextFieldAutoSize.LEFT;
            nullText.visible = false;
            nullText.height = 35;
            nullText.y = 25;
            nullText.x = 20;
            addChild(nullText);
            gotoButton = new LipiButton();
            gotoButton.visible = false;
            gotoButton.bgAlpha = 0;
            gotoButton.bgSkin = new LipiSkin(MaterialLib.getInstance().getClass("ButtonBlue"));
            gotoButton.label = Language.L["去商店"];
            gotoButton.textColor = 16777215;
            gotoButton.addEventListener(MouseEvent.CLICK, nullTextClick);
            gotoButton.y = 24;
            gotoButton.width = LipiUtil.getTextLangth(Language.L["去商店"]) + 25;
            gotoButton.height = 24;
            addChild(gotoButton);
            dataLoading = new DataLoading();
            dataLoading.addEventListener(ViewEvent.LINK_CLICK, linkClick);
            dataLoading.x = width / 2;
            dataLoading.y = height / 2;
            dataLoading.visible = false;
            addChild(dataLoading);
            farmData = MData.getInstance().farmData;
            if (farmData.userSeed != null)
            {
                hlist.dataList = farmData.userSeed;
            }// end if
            farmData.addEventListener(FarmData.USER_SEED, userSeedChange, false, 0, true);
            farmData.addEventListener(FarmData.USER_SEED_LOADING, userSeedLoading, false, 0, true);
            farmData.addEventListener(FarmData.USER_SEED_ERR, userSeedErr, false, 0, true);
            addEventListener(MouseEvent.ROLL_OVER, onOver);
            addEventListener(MouseEvent.ROLL_OUT, onOut);
            addEventListener(MouseEvent.CLICK, onClick);
            addEventListener(ViewEvent.CHILD_CLICK, childClick);
            addEventListener(ViewEvent.CHILD_OVER, childOver);
            addEventListener(ViewEvent.CHILD_OUT, childOut);
            setVisible();
            return;
        }// end function

        private function lButtonClick(param1:MouseEvent) : void
        {
            hlist.leftMove2();
            setButtonEnabled();
            return;
        }// end function

        private function toShopTool(param1:Event) : void
        {
            WControl.openForName("shop", {selectTab:1});
            return;
        }// end function

        private function hideThis() : void
        {
            this.visible = false;
            return;
        }// end function

        private function childOver(param1:ViewEvent) : void
        {
            var _loc_2:* = new TipEvent(TipEvent.TIP_SHOW);
            _loc_2.tipType = "MouseTip";
            if (param1.data.hasOwnProperty("type"))
            {
                _loc_2.tipArgument = param1.data["tName"];
                if (Version.SNS == Version.QQ)
                {
                    if (param1.data["type"] == "1")
                    {
                        _loc_2.tipArgument = param1.data["cName"] + "种子" + "\n" + param1.data["level"] + " 级作物，" + param1.data["lifecycle"] + " 小时成熟";
                    }
                    else if (param1.data["type"] == "3")
                    {
                        _loc_2.tipArgument = param1.data["tName"] + "\n" + param1.data["depict"];
                    }// end else if
                }
                else if (param1.data["type"] == "1")
                {
                    _loc_2.tipArgument = Language.replaceText("cropSeedName", {cName:param1.data["cName"]});
                }
                else if (param1.data["type"] == "3")
                {
                    _loc_2.tipArgument = param1.data["tName"];
                }// end else if
            }// end else if
            ViewControl.getInstance().dispatchEvent(_loc_2);
            return;
        }// end function

        private function rButtonClick(param1:MouseEvent) : void
        {
            hlist.rightMove2();
            setButtonEnabled();
            return;
        }// end function

        private function childOut(param1:ViewEvent) : void
        {
            var _loc_2:* = new TipEvent(TipEvent.TIP_HIDE);
            ViewControl.getInstance().dispatchEvent(_loc_2);
            return;
        }// end function

        private function childClick(param1:ViewEvent) : void
        {
            if (param1.data.hasOwnProperty("type"))
            {
                if (param1.data["type"] == "1")
                {
                    Cursor.setCursor("CursorCropSeed", param1.data["cId"]);
                }
                else if (param1.data["type"] == "3")
                {
                    Cursor.setCursor("CursorTool", param1.data["tId"]);
                }
                else if (param1.data["type"] == 21)
                {
                    CursorClassLib.register("CursorHitDogStick", CursorHitDogStick);
                    Cursor.setCursor("CursorHitDogStick", param1.data["tId"]);
                }
                else if (param1.data["type"] == 22)
                {
                    CursorClassLib.register("CursorBetterSpraying", CursorBetterSpraying);
                    Cursor.setCursor("CursorBetterSpraying", param1.data["tId"]);
                }// end else if
            }// end else if
            visible = false;
            return;
        }// end function

        private function userSeedLoading(param1:Event) : void
        {
            setVisible();
            setButtonEnabled();
            return;
        }// end function

        private function onOver(param1:MouseEvent) : void
        {
            Cursor.useSystem(true);
            clearTimeout(hideTimer);
            return;
        }// end function

        private function nullTextClick(param1:Event) : void
        {
            WControl.openForName("shop");
            return;
        }// end function

        private function userSeedErr(param1:Event) : void
        {
            dataLoading.errorText = farmData.userSeedErr;
            return;
        }// end function

        override public function set visible(param1:Boolean) : void
        {
            if (stage != null)
            {
                stage.addEventListener(MouseEvent.CLICK, stageClick);
            }// end if
            super.visible = param1;
            return;
        }// end function

        private function userSeedChange(param1:Event) : void
        {
            var _loc_2:Array;
            var _loc_3:int;
            if (farmData.userSeed != null)
            {
                _loc_2 = new Array();
                _loc_3 = 0;
                while (_loc_3 < farmData.userSeed.length)
                {
                    // label
                    if (!farmData.userSeed[_loc_3].hasOwnProperty("view") || farmData.userSeed[_loc_3]["view"] == null)
                    {
                        farmData.userSeed[_loc_3]["view"] = 1;
                    }// end if
                    if (farmData.userSeed[_loc_3]["view"] == 1)
                    {
                        if (MData.getInstance().mainData.me)
                        {
                            _loc_2.push(farmData.userSeed[_loc_3]);
                        }// end if
                    }
                    else if (farmData.userSeed[_loc_3]["view"] == 2)
                    {
                        if (!MData.getInstance().mainData.me)
                        {
                            _loc_2.push(farmData.userSeed[_loc_3]);
                        }// end if
                    }
                    else if (farmData.userSeed[_loc_3]["view"] == 0)
                    {
                        _loc_2.push(farmData.userSeed[_loc_3]);
                    }// end else if
                    _loc_3++;
                }// end while
                hlist.dataList = _loc_2;
            }// end if
            setVisible();
            setButtonEnabled();
            return;
        }// end function

        private function setButtonEnabled() : void
        {
            if (hlist.leftEnabled)
            {
                lButton.enabled = true;
                lButton.filters = [];
            }
            else
            {
                lButton.enabled = false;
                lButton.filters = [new ColorMatrixFilter([0.2225, 0.7169, 0.0606, 0, 0, 0.2225, 0.7169, 0.0606, 0, 0, 0.2225, 0.7169, 0.0606, 0, 0, 0, 0, 0, 1, 0])];
            }// end else if
            if (hlist.rightEnabled)
            {
                rButton.enabled = true;
                rButton.filters = [];
            }
            else
            {
                rButton.enabled = false;
                rButton.filters = [new ColorMatrixFilter([0.2225, 0.7169, 0.0606, 0, 0, 0.2225, 0.7169, 0.0606, 0, 0, 0.2225, 0.7169, 0.0606, 0, 0, 0, 0, 0, 1, 0])];
            }// end else if
            return;
        }// end function

        private function onOut(param1:MouseEvent) : void
        {
            hideTimer = setTimeout(hideThis, 100);
            return;
        }// end function

        private function onClick(param1:MouseEvent) : void
        {
            param1.stopPropagation();
            return;
        }// end function

        private function stageClick(param1:MouseEvent) : void
        {
            visible = false;
            stage.removeEventListener(MouseEvent.CLICK, stageClick);
            return;
        }// end function

        private function setVisible() : void
        {
            if (farmData.userSeedLoading == true)
            {
                lButton.visible = false;
                rButton.visible = false;
                hlist.visible = false;
                dataLoading.visible = true;
                var _loc_1:Boolean;
                nullText.visible = false;
                gotoButton.visible = _loc_1;
            }
            else if (hlist.dataList != null && hlist.dataList.length > 0)
            {
                lButton.visible = true;
                rButton.visible = true;
                hlist.visible = true;
                dataLoading.visible = false;
                var _loc_1:Boolean;
                nullText.visible = false;
                gotoButton.visible = _loc_1;
            }
            else
            {
                lButton.visible = false;
                rButton.visible = false;
                hlist.visible = false;
                dataLoading.visible = false;
                if (MData.getInstance().mainData.me)
                {
                    nullText.defaultTextFormat = new TextFormat("Tahoma", 16, null, null, null, null, null, null, TextFormatAlign.CENTER);
                    nullText.htmlText = "<a href=\'event:1\'><font color=\'#FF6600\'>" + Language.L["包裹里什么都没有，去商店里逛逛吧！"] + "</font></a>";
                    var _loc_1:Boolean;
                    nullText.visible = true;
                    gotoButton.visible = _loc_1;
                    gotoButton.x = nullText.x + nullText.width + 5;
                    nullText.selectable = false;
                    nullText.removeEventListener(MouseEvent.CLICK, toShopTool);
                    nullText.addEventListener(MouseEvent.CLICK, nullTextClick);
                }
                else
                {
                    nullText.defaultTextFormat = new TextFormat("Tahoma", 16, null, null, null, null, null, null, TextFormatAlign.CENTER);
                    nullText.htmlText = "<a href=\"event:1\"><font color=\'#FF6600\'>" + Language.L["没有对好友可以使用的道具！"] + "</font></a>";
                    gotoButton.visible = false;
                    nullText.visible = true;
                    nullText.selectable = false;
                    nullText.mouseEnabled = true;
                    nullText.removeEventListener(MouseEvent.CLICK, nullTextClick);
                    nullText.addEventListener(MouseEvent.CLICK, toShopTool);
                }// end else if
            }// end else if
            return;
        }// end function

        override public function get visible() : Boolean
        {
            return super.visible;
        }// end function

        private function linkClick(param1:ViewEvent) : void
        {
            if (param1.data == "reload")
            {
                Command.getInstance().farmCommand.getUserSeed(true);
                dataLoading.visible = true;
            }// end if
            return;
        }// end function

    }
}
