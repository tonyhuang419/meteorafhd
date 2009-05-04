package hf.view.main.head
{
    import flash.display.*;
    import flash.events.*;
    import flash.net.*;
    import hf.control.*;
    import hf.view.common.*;
    import hf.view.main.WindowControl.*;
    import hf.view.main.task.*;

    public class PersonInfo extends Sprite
    {
        private var level:Level;
        private var _color:String = "blue";
        private var loader:Loader;
        private var nameText:NameText;
        private var _exp:int = 0;
        private var propertyText:PropertyText;
        private var _fbValue:Number = 0;
        private var _nextExp:int = 0;
        private var _url:String = "";
        private var _goldValue:Number = 0;
        private var expBar:ExpBar;
        private var fbPropertyText:PropertyText;

        public function PersonInfo()
        {
            expBar = new ExpBar();
            expBar.addEventListener(MouseEvent.ROLL_OVER, onRollOver);
            expBar.addEventListener(MouseEvent.ROLL_OUT, onRollOut);
            expBar.addEventListener(MouseEvent.CLICK, onClick);
            expBar.buttonMode = true;
            expBar.useHandCursor = true;
            expBar.x = 65;
            expBar.y = 25;
            expBar.percent = 50;
            addChild(expBar);
            level = new Level();
            level.addEventListener(MouseEvent.ROLL_OVER, onRollOver);
            level.addEventListener(MouseEvent.ROLL_OUT, onRollOut);
            level.addEventListener(MouseEvent.CLICK, onClick);
            level.buttonMode = true;
            level.useHandCursor = true;
            level.x = 190;
            level.y = 15;
            level.level = 9;
            addChild(level);
            propertyText = new PropertyText();
            addChild(propertyText);
            fbPropertyText = new PropertyText(MoneyIcon.FB);
            addChild(fbPropertyText);
            if (Version.SNS == Version.QQ)
            {
                propertyText.x = 65;
                propertyText.y = 43;
                fbPropertyText.visible = false;
            }
            else
            {
                propertyText.x = 65;
                propertyText.y = 40;
                fbPropertyText.x = 160;
                fbPropertyText.y = 40;
            }// end else if
            nameText = new NameText();
            nameText.x = 65;
            nameText.y = 5;
            addChild(nameText);
            return;
        }// end function

        public function set exp(param1:int) : void
        {
            _exp = param1;
            level.level = expToGrade(param1);
            var _loc_2:* = gradeToExp(level.level);
            var _loc_3:* = gradeToExp(level.level + 1);
            _nextExp = param1 - _loc_2;
            expBar.percent = (_exp - _loc_2) / (_loc_3 - _loc_2) * 100;
            expBar.exp = _loc_3 - _loc_2;
            expBar.nextExp = _nextExp;
            return;
        }// end function

        private function ioError(param1:IOErrorEvent) : void
        {
            return;
        }// end function

        private function loaderComp(param1:Event) : void
        {
            loader.width = 50;
            loader.height = 50;
            return;
        }// end function

        public function set color(param1:String) : void
        {
            _color = param1;
            expBar.color = param1;
            level.color = param1;
            return;
        }// end function

        private function gradeToExp(param1:int) : uint
        {
            return 100 * param1 * (param1 + 1);
        }// end function

        public function get fbValue() : Number
        {
            return _fbValue;
        }// end function

        public function get exp() : int
        {
            return _exp;
        }// end function

        public function get color() : String
        {
            return _color;
        }// end function

        public function get vip() : String
        {
            return nameText.vip;
        }// end function

        private function onClick(param1:MouseEvent) : void
        {
            var _loc_2:* = new LevelAndExperience();
            WControl.open(_loc_2);
            return;
        }// end function

        public function set fbValue(param1:Number) : void
        {
            if (!isNaN(param1))
            {
                _fbValue = param1;
                fbPropertyText.goldValue = param1;
            }// end if
            return;
        }// end function

        public function set url(param1:String) : void
        {
            var _loc_2:Sprite;
            var _loc_3:Sprite;
            if (loader == null)
            {
                loader = new Loader();
                loader.mouseEnabled = false;
                loader.x = 0;
                loader.y = 0;
                loader.contentLoaderInfo.addEventListener(Event.COMPLETE, loaderComp);
                loader.contentLoaderInfo.addEventListener(IOErrorEvent.IO_ERROR, ioError);
                _loc_2 = new Sprite();
                _loc_2.x = 1;
                _loc_2.y = 8;
                _loc_2.graphics.clear();
                _loc_2.graphics.beginFill(15658734);
                _loc_2.graphics.drawRoundRect(0, 0, 50, 50, 10, 10);
                _loc_2.graphics.endFill();
                addChild(_loc_2);
                _loc_3 = new Sprite();
                _loc_3.x = 1;
                _loc_3.y = 8;
                _loc_3.buttonMode = true;
                _loc_3.useHandCursor = true;
                _loc_3.graphics.clear();
                _loc_3.graphics.beginFill(15658734, 1);
                _loc_3.graphics.drawRect(0, 0, 50, 50);
                _loc_3.graphics.endFill();
                _loc_3.addEventListener(MouseEvent.CLICK, loaderClick);
                _loc_3.mask = _loc_2;
                _loc_3.addChild(loader);
                addChildAt(_loc_3, 0);
            }// end if
            if (param1 != "" && param1 != null)
            {
                loader.load(new URLRequest(param1));
            }// end if
            return;
        }// end function

        public function get url() : String
        {
            return _url;
        }// end function

        public function set goldValue(param1:Number) : void
        {
            _goldValue = param1;
            propertyText.goldValue = param1;
            return;
        }// end function

        private function onRollOut(param1:MouseEvent) : void
        {
            return;
        }// end function

        private function loaderClick(param1:MouseEvent) : void
        {
            WControl.openForName("profile", -1);
            return;
        }// end function

        public function get userName() : String
        {
            return nameText.text;
        }// end function

        private function onRollOver(param1:MouseEvent) : void
        {
            return;
        }// end function

        public function get goldValue() : Number
        {
            return _goldValue;
        }// end function

        public function set vip(param1:String) : void
        {
            nameText.vip = param1;
            return;
        }// end function

        public function set userName(param1:String) : void
        {
            nameText.text = param1;
            return;
        }// end function

        private function expToGrade(param1:uint) : int
        {
            return Math.sqrt((param1 + 25) / 100) - 0.5;
        }// end function

    }
}
