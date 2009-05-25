package hf.view.main.window.flower.made
{
    import com.minutes.ui.collections.*;
    import flash.display.*;
    import flash.text.*;
    import hf.view.common.*;
    import hf.view.main.tip.*;
    import hf.view.main.window.flower.tip.*;

    public class MadeBouquetWin extends BaseWindow
    {
        private var complete:Sprite;
        private var makePanelA:Sprite;
        private var makePanelB:Sprite;
        private var flowerList:LipiTile2;

        public function MadeBouquetWin()
        {
            width = 600;
            height = 400;
            title = "鲜花技能";
            TipClassLib.register("MadeFlowerTip", MadeFlowerTip);
            return;
        }// end function

        public function get dataList() : Array
        {
            return flowerList.dataList;
        }// end function

        override public function init() : void
        {
            var _loc_1:* = new TextField();
            _loc_1.text = "选择需要制作的花的种类：";
            _loc_1.x = 10;
            _loc_1.y = 35;
            _loc_1.width = 250;
            _loc_1.selectable = false;
            _loc_1.setTextFormat(new TextFormat("Tahoma", 14, 39423, true));
            addChild(_loc_1);
            flowerList = new LipiTile2(FlowerItem, 3, 80, 75);
            flowerList.x = 10;
            flowerList.y = 60;
            flowerList.width = 250;
            flowerList.height = 220;
            flowerList.bgAlpha = 0;
            flowerList.dataList = [{cId:37, cName:"玫瑰"}, {cId:37, cName:"玫瑰"}, {cId:37, cName:"玫瑰"}, {cId:37, cName:"玫瑰"}, {cId:37, cName:"玫瑰"}, {cId:37, cName:"玫瑰"}, {cId:37, cName:"玫瑰"}, {cId:37, cName:"玫瑰"}, {cId:37, cName:"玫瑰"}, {cId:37, cName:"玫瑰"}, {cId:37, cName:"玫瑰"}, {cId:37, cName:"玫瑰"}, {cId:37, cName:"玫瑰"}, {cId:37, cName:"玫瑰"}, {cId:37, cName:"玫瑰"}, {cId:37, cName:"玫瑰"}, {cId:37, cName:"玫瑰"}];
            addChild(flowerList);
            makePanelA = new GeneralMade();
            makePanelA.x = 270;
            makePanelA.y = 35;
            addChild(makePanelA);
            super.init();
            return;
        }// end function

        public function set dataList(param1:Array) : void
        {
            flowerList.dataList = param1;
            return;
        }// end function

    }
}
