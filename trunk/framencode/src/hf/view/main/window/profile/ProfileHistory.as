package hf.view.main.window.profile
{
    import com.minutes.ui.collections.*;
    import flash.display.*;
    import hf.view.*;
    import hf.view.main.tip.*;

    public class ProfileHistory extends Sprite
    {
        private var tile:LipiTile2;

        public function ProfileHistory()
        {
            tile = new LipiTile2(HistoryItem, 5, 90, 100);
            tile.addEventListener(LipiTile2.CHILD_OVER, onOver);
            tile.addEventListener(LipiTile2.CHILD_OUT, onOut);
            tile.x = 25;
            tile.y = 5;
            tile.bgAlpha = 0;
            tile.width = 470;
            tile.height = 210;
            addChild(tile);
            return;
        }// end function

        public function set dataList(param1:Array) : void
        {
            tile.dataList = param1;
            return;
        }// end function

        private function onOver(param1:ViewEvent) : void
        {
            TipClassLib.register("HistoryTip", HistoryTip);
            TipControl.show("HistoryTip", param1.data);
            return;
        }// end function

        public function get dataList() : Array
        {
            return tile.dataList;
        }// end function

        private function onOut(param1:ViewEvent) : void
        {
            TipControl.hide();
            return;
        }// end function

    }
}
