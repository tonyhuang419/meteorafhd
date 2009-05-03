package hf.view.farm.farmAnimal
{
    import flash.display.*;
    import flash.events.*;
    import flash.text.*;
    import hf.control.*;
    import hf.model.*;
    import hf.view.*;
    import hf.view.common.*;
    import hf.view.main.WindowControl.*;

    public class DogFoodWindow extends AddButtonWindow
    {
        private var mainData:MainData;

        public function DogFoodWindow()
        {
            mainData = MData.getInstance().mainData;
            this.width = 400;
            this.height = 250;
            this.title = Language.L["狗狗粮食"];
            this.mode = true;
            mainData.addEventListener(MainData.DOG_FEED, alertFn);
            return;
        }// end function

        override public function init() : void
        {
            var _loc_3:Sprite;
            var _loc_5:Sprite;
            var _loc_8:TextField;
            var _loc_9:TextField;
            var _loc_10:MoneyIcon;
            var _loc_11:TextField;
            var _loc_12:TextField;
            removeAllButton();
            if (mainData.selfDogFood.hours < 24)
            {
                addButton(Language.L["确定"], "ButtonOrange", 60, 25, addFood);
            }
            else
            {
                addButton(Language.L["确定"], "ButtonOrange", 60, 25, emptyFn).enable = false;
            }// end else if
            addButton(Language.L["取消"], "ButtonBlue", 60, 25, closeWindow);
            var _loc_1:* = MaterialLib.getInstance().getClass("ItemBorder");
            var _loc_2:* = new _loc_1 as Sprite;
            _loc_3 = new _loc_1 as Sprite;
            var _loc_4:String;
            if (mainData.dogData.isHungry == 0)
            {
                _loc_4 = "DogFood";
            }// end if
            _loc_5 = MaterialLib.getInstance().getMaterial(_loc_4) as Sprite;
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
            new TextField().height = 50;
            _loc_6.wordWrap = true;
            _loc_6.text = Language.L["剩余狗粮还可以给狗狗吃"];
            _loc_6.setTextFormat(new TextFormat("Tahoma", 14, 3355443, false, null, null, null, null, "left"));
            _loc_6.width = 200;
            _loc_6.x = 170;
            _loc_6.y = 45;
            _loc_6.selectable = false;
            addWindowChild(_loc_6);
            var _loc_7:* = new TextField();
            if (mainData.selfDogFood.hours != null)
            {
                _loc_7.text = "" + mainData.selfDogFood.hours;
            }
            else
            {
                _loc_7.text = "0";
            }// end else if
            _loc_7.setTextFormat(new TextFormat("fontForte", 22, 16737792, true, null, null, null, null, "right"));
            _loc_7.embedFonts = true;
            _loc_7.width = 40;
            _loc_7.x = 220;
            _loc_7.y = 80;
            _loc_7.selectable = false;
            addWindowChild(_loc_7);
            _loc_8 = new TextField();
            _loc_8.text = Language.L["小时"];
            _loc_8.setTextFormat(new TextFormat("Tahoma", 16, 26367, true, null, null, null, null, "center"));
            _loc_8.width = 70;
            _loc_8.x = 260;
            _loc_8.y = 82;
            _loc_8.selectable = false;
            addWindowChild(_loc_8);
            if (mainData.selfDogFood.hours < 24)
            {
                _loc_9 = new TextField();
                _loc_9.autoSize = TextFieldAutoSize.LEFT;
                _loc_9.text = Language.L["补充狗粮需要"];
                _loc_9.setTextFormat(new TextFormat("Tahoma", 14, 3355443, false, null, null, null, null, "left"));
                _loc_9.x = 170;
                _loc_9.y = 120;
                _loc_9.selectable = false;
                addChild(_loc_9);
                _loc_10 = new MoneyIcon();
                _loc_10.x = _loc_9.width + _loc_9.x + 10;
                _loc_10.y = 120;
                addWindowChild(_loc_10);
                _loc_11 = new TextField();
                _loc_11.text = "" + mainData.selfDogFood.money;
                _loc_11.setTextFormat(new TextFormat("fontForte", 22, 16737792, true));
                _loc_11.embedFonts = true;
                _loc_11.width = 50;
                _loc_11.x = _loc_10.x + _loc_10.width + 10;
                _loc_11.y = 115;
                _loc_11.selectable = false;
                addWindowChild(_loc_11);
            }
            else
            {
                _loc_12 = new TextField();
                _loc_12.text = Language.L["狗粮充足，不需要补充狗粮了。"];
                _loc_12.setTextFormat(new TextFormat("Tahoma", 14, 3355443, false, null, null, null, null, "center"));
                _loc_12.width = 200;
                _loc_12.x = 170;
                _loc_12.y = 120;
                _loc_12.selectable = false;
                addWindowChild(_loc_12);
            }// end else if
            super.init();
            return;
        }// end function

        private function alertFn(param1:Event) : void
        {
            if (mainData.dogFeed.code == 0)
            {
                WControl.openForName("AlertWindow", {type:"error", text:mainData.dogFeed.direction});
            }
            else
            {
                WControl.openForName("AlertWindow", {type:"success", text:mainData.dogFeed.direction});
            }// end else if
            return;
        }// end function

        private function emptyFn() : void
        {
            return;
        }// end function

        private function addFood() : void
        {
            Command.getInstance().mainCommand.feedDog();
            closeWindow();
            return;
        }// end function

    }
}
