package hf.view.farm.pepsico
{
    import com.minutes.ui.control.*;
    import com.minutes.ui.core.*;
    import flash.display.*;
    import flash.text.*;
    import hf.control.*;
    import hf.model.*;
    import hf.view.*;
    import hf.view.common.*;

    public class MakePotatoChips extends AddButtonWindow
    {
        private var mainData:MainData;
        private var numbericStepper:NumbericStepper;

        public function MakePotatoChips()
        {
            width = 400;
            height = 335;
            title = "乐事薯片工厂";
            mode = true;
            return;
        }// end function

        override public function init() : void
        {
            mainData = MData.getInstance().mainData;
            var _loc_1:* = mainData.potatoNumber;
            var _loc_2:* = int(_loc_1 / 6);
            addImg(37, 45, 40);
            var _loc_3:* = new TextField();
            _loc_3.selectable = false;
            _loc_3.x = 0;
            _loc_3.y = 105;
            _loc_3.width = 150;
            _loc_3.height = 21;
            _loc_3.defaultTextFormat = new TextFormat("Tahoma", 12, 3355443, null, null, null, null, null, "center");
            _loc_3.text = "库存：" + _loc_1;
            addChild(_loc_3);
            addImg(1002, 45, 130);
            if (_loc_2 > 0)
            {
                numbericStepper = new NumbericStepper();
                numbericStepper.max_num = _loc_2;
                numbericStepper.get_num = _loc_2;
                numbericStepper.x = 30;
                numbericStepper.y = 200;
                numbericStepper.addEventListener(UIEvent.TEXT_CHANGE, numChange);
                addChild(numbericStepper);
            }// end if
            var _loc_4:* = new TextField();
            new TextField().selectable = false;
            _loc_4.width = 150;
            _loc_4.height = 21;
            _loc_4.x = 0;
            if (_loc_2 > 0)
            {
                _loc_4.y = 230;
                _loc_4.defaultTextFormat = new TextFormat("Tahoma", 12, 3355443, null, null, null, null, null, "center");
                _loc_4.text = "输入生产数量（1~" + _loc_2 + "）";
            }
            else
            {
                _loc_4.y = 200;
                _loc_4.defaultTextFormat = new TextFormat("Tahoma", 12, 13369344, null, null, null, null, null, "center");
                _loc_4.text = "原材料不足";
            }// end else if
            addChild(_loc_4);
            var _loc_5:* = new TextField();
            new TextField().text = "乐事薯片工厂";
            _loc_5.setTextFormat(new TextFormat("Tahoma", 16, 3381555, true, null, null, null, null, "center"));
            _loc_5.width = 200;
            _loc_5.x = 160;
            _loc_5.y = 35;
            _loc_5.selectable = false;
            addChild(_loc_5);
            var _loc_6:* = new TextField();
            new TextField().selectable = false;
            _loc_6.x = 160;
            _loc_6.y = 70;
            _loc_6.width = 200;
            _loc_6.height = 21;
            _loc_6.defaultTextFormat = new TextFormat("Tahoma", 12, 3355443, null, null, null, null, null, "left");
            _loc_6.htmlText = "有效期：<font color=\'#ff0000\'>6月5日</font>";
            addChild(_loc_6);
            var _loc_7:* = new TextField();
            new TextField().selectable = false;
            _loc_7.x = 160;
            _loc_7.y = 95;
            _loc_7.width = 200;
            _loc_7.height = 21;
            _loc_7.defaultTextFormat = new TextFormat("Tahoma", 12, 3355443, null, null, null, null, null, "left");
            _loc_7.text = "已生产：         袋薯片";
            addChild(_loc_7);
            var _loc_8:* = new TextField();
            new TextField().selectable = false;
            _loc_8.defaultTextFormat = new TextFormat("fontForte", 16, 16737792, null, null, null, null, null, "right");
            _loc_8.embedFonts = true;
            _loc_8.text = "" + mainData.hasPotatoChips;
            _loc_8.width = 50;
            _loc_8.height = 22;
            _loc_8.x = 190;
            _loc_8.y = 93;
            addChild(_loc_8);
            var _loc_9:* = new TextField();
            new TextField().selectable = false;
            _loc_9.multiline = true;
            _loc_9.wordWrap = true;
            _loc_9.x = 160;
            _loc_9.y = 125;
            _loc_9.width = 200;
            _loc_9.height = 150;
            _loc_9.defaultTextFormat = new TextFormat("Tahoma", 12, 3355443, null, null, null, null, null, "left", null, null, null, 8);
            _loc_9.htmlText = "你可以在这里将100%天然土豆直接切片加工成乐事薯片，加工后的薯片将放在仓库里。<br>6个100%天然土豆=1包乐事薯片=310金币，生产出50袋乐事薯片可获得乐享天然100%背景奖励，加油哦！";
            addChild(_loc_9);
            if (_loc_2 > 0)
            {
                addButton("加工薯片", "ButtonOrange", 85, 25, makeFn);
            }
            else
            {
                addButton("加工薯片", "ButtonOrange", 85, 25, empty, false);
            }// end else if
            addButton("取消", "ButtonBlue", 65, 25, closeWindow);
            super.init();
            return;
        }// end function

        private function addImg(param1:int, param2:int = 0, param3:int = 0) : void
        {
            var _loc_5:Sprite;
            var _loc_4:* = MaterialLib.getInstance().getMaterial("ItemBorder") as Sprite;
            if (param1 == 37)
            {
                _loc_5 = MaterialLib.getInstance().getMaterial("PepsicoSeedBG") as Sprite;
            }
            else
            {
                _loc_5 = MaterialLib.getInstance().getMaterial("ItemBorder") as Sprite;
            }// end else if
            var _loc_6:* = new MaterialProxy();
            new MaterialProxy().x = param2;
            _loc_6.y = param3;
            _loc_6.x = param2;
            _loc_6.y = param3;
            _loc_4.x = param2;
            _loc_4.y = param3;
            _loc_5.x = param2;
            _loc_5.y = param3;
            _loc_4.width = 60;
            _loc_4.height = 60;
            _loc_5.width = 60;
            _loc_5.height = 60;
            addChild(_loc_5);
            addChild(_loc_6);
            addChild(_loc_4);
            _loc_6.mask = _loc_4;
            _loc_6.setContent("1", param1, true);
            return;
        }// end function

        private function empty() : void
        {
            return;
        }// end function

        private function numChange(param1:UIEvent) : void
        {
            return;
        }// end function

        private function makeFn() : void
        {
            MData.getInstance().mainData.potatoMachineRuning = true;
            Command.getInstance().mainCommand.makePotatoChipsRequest(numbericStepper.get_num);
            closeWindow(null);
            return;
        }// end function

    }
}
