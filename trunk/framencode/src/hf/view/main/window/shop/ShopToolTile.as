package hf.view.main.window.shop
{
    import com.minutes.ui.collections.*;
    import flash.display.*;
    import flash.events.*;
    import flash.text.*;
    import hf.control.*;
    import hf.model.*;
    import hf.view.*;
    import hf.view.common.*;
    import hf.view.farm.farmAnimal.*;
    import hf.view.main.WindowControl.*;

    public class ShopToolTile extends Sprite
    {
        private var tile:LipiTile2;
        private var dataLoading:DataLoading;

        public function ShopToolTile()
        {
            tile = new LipiTile2(ToolItem, 5, 83, 98, 23, 10);
            tile.addEventListener(ViewEvent.CHILD_CLICK, tileChildClick);
            tile.y = 22;
            tile.width = 495;
            tile.height = 273;
            tile.bgAlpha = 0;
            addChild(tile);
            dataLoading = new DataLoading();
            dataLoading.addEventListener(ViewEvent.LINK_CLICK, linkClick);
            dataLoading.x = tile.width / 2;
            dataLoading.y = tile.height / 2;
            addChild(dataLoading);
            addText();
            return;
        }// end function

        public function get dataList() : Array
        {
            return tile.dataList;
        }// end function

        public function set errText(param1:String) : void
        {
            dataLoading.errorText = param1;
            return;
        }// end function

        public function set loadingVisible(param1:Boolean) : void
        {
            dataLoading.visible = param1;
            tile.visible = !param1;
            return;
        }// end function

        public function set dataList(param1:Array) : void
        {
            tile.dataList = param1;
            return;
        }// end function

        private function addText() : void
        {
            var _loc_1:* = new TextField();
            _loc_1.defaultTextFormat = new TextFormat("Tahoma", 14, 13369344, null, null, null, null, null, TextFormatAlign.CENTER);
            if (Version.value == Version.XIAONEI)
            {
                _loc_1.text = "购买薯片工厂可以生产乐事薯片。";
            }
            else
            {
                _loc_1.text = Language.L["toolTileDirection"];
            }// end else if
            _loc_1.selectable = false;
            _loc_1.width = tile.width;
            _loc_1.height = 20;
            _loc_1.x = 0;
            _loc_1.y = 0;
            addChild(_loc_1);
            return;
        }// end function

        private function openFoodWinInShopFn(param1:Event) : void
        {
            var _loc_2:* = new DogFoodWindow();
            WControl.open(_loc_2);
            return;
        }// end function

        private function tileChildClick(param1:ViewEvent) : void
        {
            var _loc_3:WindowEvent;
            var _loc_4:WindowEvent;
            var _loc_5:MainData;
            var _loc_2:* = param1.data;
            if (_loc_2["tId"] != 909090)
            {
                if (Version.value == Version.FACEBOOK && _loc_2["FBPrice"] != "0" && _loc_2["type"] != "4")
                {
                    WindowClassLib.register("ShopToolWindowForFaceBook", ShopToolWindowForFaceBook);
                    _loc_3 = new WindowEvent(WindowEvent.OPEN);
                    _loc_3.windowName = "ShopToolWindowForFaceBook";
                    _loc_3.windowArgument = param1.data;
                    ViewControl.getInstance().dispatchEvent(_loc_3);
                }
                else
                {
                    WindowClassLib.register("ShopToolWindow", ShopToolWindow);
                    _loc_4 = new WindowEvent(WindowEvent.OPEN);
                    _loc_4.windowName = "ShopToolWindow";
                    _loc_4.windowArgument = param1.data;
                    ViewControl.getInstance().dispatchEvent(_loc_4);
                }// end else if
            }
            else
            {
                _loc_5 = MData.getInstance().mainData;
                if (!_loc_5.hasEventListener("DOG_FOOD_FEED_IN_SHOP"))
                {
                    _loc_5.addEventListener(MainData.DOG_FOOD_FEED_IN_SHOP, openFoodWinInShopFn);
                }// end if
                Command.getInstance().mainCommand.selfDogFood();
            }// end else if
            return;
        }// end function

        private function linkClick(param1:ViewEvent) : void
        {
            var _loc_2:* = new ViewEvent(ViewEvent.LINK_CLICK);
            _loc_2.data = param1.data;
            dispatchEvent(_loc_2);
            return;
        }// end function

    }
}
