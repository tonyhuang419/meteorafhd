package hf.view.main.window.warehouse
{
    import com.lipi.*;
    import com.minutes.ui.collections.*;
    import com.minutes.ui.constant.*;
    import com.minutes.ui.control.*;
    import com.minutes.ui.core.*;
    import flash.events.*;
    import flash.text.*;
    import hf.control.*;
    import hf.model.*;
    import hf.view.*;
    import hf.view.common.*;
    import hf.view.main.WindowControl.*;
    import hf.view.main.head.*;
    import hf.view.main.tip.*;

    public class WarehouseWindow extends BaseWindow
    {
        private var _propertyText:PropertyText;
        private var mainData:MainData;
        private var lipiTile:LipiTile2;
        private var _button:LipiButton;
        private var emptyText:TextField;
        private var dataLoading:DataLoading;
        private var gotoButton:LipiButton;

        public function WarehouseWindow()
        {
            width = 500;
            height = 360;
            title = Language.L["warehouseTitle"];
            windowName = "warehouse";
            mode = true;
            return;
        }// end function

        private function addLipiTile() : void
        {
            lipiTile = new LipiTile2(FruitItem, 5, 90, 98, 23, 10);
            lipiTile.visible = !mainData.userCropLoading;
            lipiTile.horizontalScrollPolicy = ScrollPolicy.OFF;
            lipiTile.bgAlpha = 0;
            lipiTile.x = 0;
            lipiTile.y = 55;
            lipiTile.width = 470;
            lipiTile.height = 260;
            if (mainData.userCrop != null)
            {
                lipiTile.dataList = mainData.userCrop;
            }// end if
            lipiTile.addEventListener(ViewEvent.CHILD_OVER, childOver);
            lipiTile.addEventListener(ViewEvent.CHILD_OUT, childOut);
            lipiTile.addEventListener(ViewEvent.CHILD_CLICK, childClick);
            addChild(lipiTile);
            return;
        }// end function

        private function errLinkClick(param1:ViewEvent) : void
        {
            if (param1.data == "reload")
            {
                Command.getInstance().mainCommand.getUserCrop();
                dataLoading.visible = true;
            }// end if
            return;
        }// end function

        private function allSellFn() : void
        {
            Command.getInstance().mainCommand.saleAll();
            return;
        }// end function

        private function addAllSellButton() : void
        {
            _button = new LipiButton();
            _button.width = 85;
            _button.height = 25;
            _button.bgAlpha = 0;
            _button.bgSkin = new LipiSkin(MaterialLib.getInstance().getClass("ButtonGreen"));
            _button.x = width - _button.width - 15;
            _button.y = height - _button.height - 6;
            _button.label = Language.L["allSell"];
            _button.textColor = 16777215;
            _button.addEventListener(MouseEvent.CLICK, allSell);
            addChild(_button);
            return;
        }// end function

        private function childClick(param1:ViewEvent) : void
        {
            var _loc_2:* = new SellCropWindow();
            _loc_2.data = param1.data;
            WControl.open(_loc_2);
            return;
        }// end function

        private function userCropLoading(param1:Event) : void
        {
            if (mainData.userCropLoading == true)
            {
                if (dataLoading != null)
                {
                    dataLoading.visible = true;
                }// end if
                if (lipiTile != null)
                {
                    lipiTile.visible = false;
                }// end if
            }
            else
            {
                if (dataLoading != null)
                {
                    dataLoading.visible = false;
                }// end if
                if (lipiTile != null)
                {
                    lipiTile.visible = true;
                }// end if
            }// end else if
            return;
        }// end function

        private function childOut(param1:ViewEvent) : void
        {
            TipControl.hide();
            return;
        }// end function

        private function allSell(param1:MouseEvent) : void
        {
            var _loc_2:* = new ConfirmWindow();
            _loc_2.data = {text:Language.replaceText("allSellText", {goldValue:_propertyText.goldValue})};
            _loc_2.confirmFn = allSellFn;
            WControl.open(_loc_2);
            return;
        }// end function

        private function nullTextClick(param1:Event) : void
        {
            this.closeHandler();
            WControl.openForName("shop");
            return;
        }// end function

        override public function init() : void
        {
            mainData = MData.getInstance().mainData;
            mainData.addEventListener(MainData.USER_CROP_LOADING, userCropLoading, false, 0, true);
            mainData.addEventListener(MainData.USER_CROP, userCrop, false, 0, true);
            mainData.addEventListener(MainData.USER_CROP_ERR, userCropErr, false, 0, true);
            addText();
            addLine();
            addAllSellButton();
            addSumText();
            addLipiTile();
            addEmptyText();
            addDataLoading();
            return;
        }// end function

        private function addDataLoading() : void
        {
            dataLoading = new DataLoading();
            dataLoading.addEventListener(ViewEvent.LINK_CLICK, errLinkClick);
            dataLoading.visible = mainData.userCropLoading;
            dataLoading.x = width / 2;
            dataLoading.y = height / 2;
            addChild(dataLoading);
            return;
        }// end function

        private function childOver(param1:ViewEvent) : void
        {
            TipControl.show("MouseTip", param1.data["cName"]);
            return;
        }// end function

        private function addText() : void
        {
            var _loc_1:* = new TextField();
            _loc_1.defaultTextFormat = new TextFormat("Tahoma", 14, 13369344, null, null, null, null, null, TextFormatAlign.CENTER);
            if (Version.value == Version.XIAONEI)
            {
                _loc_1.text = "100%天然土豆可以用来生产乐事薯片，收益多多，好礼多多！";
            }
            else
            {
                _loc_1.text = Language.L["warehouseDirection"];
            }// end else if
            _loc_1.selectable = false;
            _loc_1.width = width;
            _loc_1.y = 30;
            addChild(_loc_1);
            return;
        }// end function

        private function userCropErr(param1:Event) : void
        {
            dataLoading.errorText = mainData.userCropErr;
            return;
        }// end function

        override public function addedToLayer() : void
        {
            super.addedToLayer();
            Command.getInstance().mainCommand.getUserCrop();
            return;
        }// end function

        private function addSumText() : void
        {
            var _loc_1:* = new TextField();
            _loc_1.selectable = false;
            _loc_1.defaultTextFormat = new TextFormat("Tahoma", 12, 39423, true);
            _loc_1.autoSize = TextFieldAutoSize.LEFT;
            _loc_1.text = Language.L["sumGold"];
            _loc_1.x = 15;
            _loc_1.y = height - _loc_1.height - 13;
            addChild(_loc_1);
            _propertyText = new PropertyText();
            _propertyText.x = _loc_1.width + _loc_1.x + 10;
            _propertyText.y = height - _propertyText.height - 10;
            addChild(_propertyText);
            return;
        }// end function

        private function addEmptyText() : void
        {
            emptyText = new TextField();
            emptyText.selectable = false;
            var _loc_1:* = new TextFormat("Tahoma", 16, 3355443);
            emptyText.visible = false;
            _loc_1.leading = 8;
            emptyText.defaultTextFormat = _loc_1;
            emptyText.width = 350;
            emptyText.height = 80;
            emptyText.text = Language.L["仓库里空空"];
            emptyText.wordWrap = true;
            emptyText.x = (width - emptyText.width) / 2;
            emptyText.y = (height - emptyText.height) / 2;
            addChild(emptyText);
            gotoButton = new LipiButton();
            gotoButton.visible = false;
            gotoButton.addEventListener(MouseEvent.CLICK, nullTextClick);
            gotoButton.bgAlpha = 0;
            gotoButton.bgSkin = new LipiSkin(MaterialLib.getInstance().getClass("ButtonBlue"));
            gotoButton.label = Language.L["去商店"];
            gotoButton.textColor = 16777215;
            gotoButton.x = 270;
            gotoButton.y = 166;
            gotoButton.width = LipiUtil.getTextLangth(Language.L["去商店"]) + 25;
            gotoButton.height = 24;
            addChild(gotoButton);
            return;
        }// end function

        private function userCrop(param1:Event) : void
        {
            lipiTile.dataList = mainData.userCrop;
            var _loc_2:* = mainData.userCrop;
            var _loc_3:Number;
            var _loc_4:int;
            while (_loc_4 < _loc_2.length)
            {
                // label
                _loc_3 = _loc_3 + Number(_loc_2[_loc_4]["price"]) * Number(_loc_2[_loc_4]["amount"]);
                _loc_4++;
            }// end while
            _propertyText.goldValue = _loc_3;
            if (mainData.userCrop.length == 0)
            {
                emptyText.visible = true;
                gotoButton.visible = true;
                lipiTile.visible = false;
                _button.visible = false;
            }
            else
            {
                emptyText.visible = false;
                gotoButton.visible = false;
                lipiTile.visible = true;
                _button.visible = true;
            }// end else if
            return;
        }// end function

        private function addLine() : void
        {
            var _loc_1:* = new Line(width - 4, 2);
            _loc_1.y = 50;
            addChild(_loc_1);
            var _loc_2:* = new Line(width - 4, 2);
            _loc_2.y = height - 40;
            addChild(_loc_2);
            return;
        }// end function

    }
}
