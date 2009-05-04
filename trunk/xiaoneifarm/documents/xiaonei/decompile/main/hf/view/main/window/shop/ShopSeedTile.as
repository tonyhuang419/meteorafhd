package hf.view.main.window.shop
{
    import com.minutes.ui.collections.*;
    import flash.display.*;
    import hf.control.*;
    import hf.view.*;
    import hf.view.common.*;
    import hf.view.main.WindowControl.*;

    public class ShopSeedTile extends Sprite
    {
        private var dataLoading:DataLoading;
        private var tile:LipiTile2;

        public function ShopSeedTile()
        {
            tile = new LipiTile2(CropItem, 5, 90, 98, 23, 10);
            tile.y = 1;
            tile.width = 495;
            tile.height = 295;
            tile.bgAlpha = 0;
            tile.addEventListener(ViewEvent.CHILD_CLICK, tileChildClick);
            addChild(tile);
            dataLoading = new DataLoading();
            dataLoading.addEventListener(ViewEvent.LINK_CLICK, linkClick);
            dataLoading.x = tile.width / 2;
            dataLoading.y = tile.height / 2;
            addChild(dataLoading);
            return;
        }// end function

        public function set dataList(param1:Array) : void
        {
            tile.dataList = param1;
            return;
        }// end function

        public function set errText(param1:String) : void
        {
            dataLoading.errorText = param1;
            return;
        }// end function

        public function get dataList() : Array
        {
            return tile.dataList;
        }// end function

        private function tileChildClick(param1:ViewEvent) : void
        {
            WindowClassLib.register("ShopSeedWindow", ShopSeedWindow);
            var _loc_2:* = new WindowEvent(WindowEvent.OPEN);
            _loc_2.windowName = "ShopSeedWindow";
            _loc_2.windowArgument = param1.data;
            ViewControl.getInstance().dispatchEvent(_loc_2);
            return;
        }// end function

        public function set loadingVisible(param1:Boolean) : void
        {
            dataLoading.visible = param1;
            tile.visible = !param1;
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
