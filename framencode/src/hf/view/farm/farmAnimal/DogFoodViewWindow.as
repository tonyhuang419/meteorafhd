package hf.view.farm.farmAnimal
{
    import flash.display.*;
    import flash.events.*;
    import flash.text.*;
    import hf.model.*;
    import hf.view.*;
    import hf.view.common.*;
    import hf.view.main.WindowControl.*;

    public class DogFoodViewWindow extends AddButtonWindow
    {
        private var mainData:MainData;

        public function DogFoodViewWindow()
        {
            mainData = MData.getInstance().mainData;
            this.width = 400;
            this.height = 250;
            this.title = Language.L["狗狗粮食"];
            this.mode = true;
            return;
        }// end function

        override public function init() : void
        {
            var _loc_10:TextField;
            removeAllButton();
            var _loc_1:* = MaterialLib.getInstance().getClass("ItemBorder");
            var _loc_2:* = new _loc_1 as Sprite;
            var _loc_3:* = new _loc_1 as Sprite;
            var _loc_4:String;
            if (mainData.dogData.isHungry == 0)
            {
                _loc_4 = "DogFood";
            }// end if
            var _loc_5:* = MaterialLib.getInstance().getMaterial(_loc_4) as Sprite;
            addChild(_loc_5);
            _loc_5.x = 30;
            _loc_5.y = 70;
            _loc_2.x = 20;
            _loc_2.y = 45;
            _loc_3.x = 20;
            _loc_3.y = 45;
            _loc_2.width = 120;
            _loc_2.height = 120;
            _loc_3.width = 120;
            _loc_3.height = 120;
            _loc_5.mask = _loc_2;
            addWindowChild(_loc_5);
            addWindowChild(_loc_2);
            addWindowChild(_loc_3);
            var _loc_6:* = new TextField();
            new TextField().wordWrap = true;
            _loc_6.multiline = true;
            _loc_6.width = 200;
            _loc_6.height = 80;
            _loc_6.x = 170;
            _loc_6.y = 45;
            _loc_6.selectable = false;
            addWindowChild(_loc_6);
            var _loc_7:* = mainData.serverTime;
            var _loc_8:* = mainData.selfDogFood.hours;
            var _loc_9:* = mainData.selfDogFood.hours - _loc_7;
            if (mainData.selfDogFood != null && mainData.selfDogFood["hours"] - mainData.serverTime > 0)
            {
                _loc_6.text = Language.L["剩余狗粮还可以给狗狗吃"] + "\n \n " + Language.L["到期时间："];
                _loc_10 = new TextField();
                _loc_10.text = "" + dateFormat(mainData.selfDogFood.hours);
                _loc_10.setTextFormat(new TextFormat("fontForte", 22, 16737792, true, null, null, null, null, "center"));
                _loc_10.embedFonts = true;
                _loc_10.width = 200;
                _loc_10.x = 160;
                _loc_10.y = 110;
                _loc_10.selectable = false;
                addWindowChild(_loc_10);
            }
            else
            {
                _loc_6.text = Language.L["补充狗粮需要"];
            }// end else if
            addButton(Language.L["购买"], "ButtonOrange", 60, 25, gotoShop);
            addButton(Language.L["取消"], "ButtonBlue", 60, 25, closeWindow);
            _loc_6.setTextFormat(new TextFormat("Tahoma", 14, 3355443, false, null, null, null, null, "left"));
            super.init();
            return;
        }// end function

        private function doubleString(param1:int) : String
        {
            if (param1 < 10)
            {
                return "0" + param1;
            }// end if
            return param1.toString();
        }// end function

        private function dateFormat(param1:int) : String
        {
            var _loc_2:* = new Date(param1 * 1000);
            return doubleString(_loc_2.getFullYear()) + "-" + doubleString(_loc_2.getMonth() + 1) + "-" + doubleString(_loc_2.getDate()) + " " + doubleString(_loc_2.getHours()) + ":" + doubleString(_loc_2.getMinutes());
        }// end function

        private function gotoShop(param1:Event = null) : void
        {
            WControl.openForName("shop", {selectTab:1});
            closeWindow(null);
            return;
        }// end function

    }
}
