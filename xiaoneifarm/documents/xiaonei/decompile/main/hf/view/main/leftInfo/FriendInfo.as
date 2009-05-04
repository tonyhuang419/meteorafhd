package hf.view.main.leftInfo
{
    import flash.display.*;
    import flash.events.*;
    import flash.net.*;
    import hf.view.common.*;
    import hf.view.farm.toolBar.*;
    import hf.view.main.WindowControl.*;
    import hf.view.main.head.*;
    import hf.view.main.tip.*;

    public class FriendInfo extends Sprite
    {
        private var level:Level;
        private var _color:String = "blue";
        private var loader:Loader;
        private var nameText:NameText;
        private var _url:String = "";
        private var msgIcon:MsgIcon;
        private var _exp:int = 0;
        private var propertyText:PropertyText;
        private var _nextExp:int = 0;
        private var _goldValue:Number = 0;
        private var expBar:ExpBar;

        public function FriendInfo()
        {
            expBar = new ExpBar();
            expBar.addEventListener(MouseEvent.ROLL_OVER, onRollOver);
            expBar.addEventListener(MouseEvent.ROLL_OUT, onRollOut);
            expBar.scaleX = 0.9;
            expBar.scaleY = 1;
            expBar.x = 0 + 60;
            expBar.y = 25 + 45 - 43;
            expBar.percent = 50;
            addChild(expBar);
            level = new Level();
            level.addEventListener(MouseEvent.ROLL_OVER, onRollOver);
            level.addEventListener(MouseEvent.ROLL_OUT, onRollOut);
            level.scaleX = 0.9;
            level.scaleY = 0.9;
            level.x = 190 - 80 + 60;
            level.y = 15 + 47 - 43;
            level.level = 9;
            addChild(level);
            nameText = new NameText();
            nameText.x = 0 + 60;
            nameText.y = 5 + 45 - 43;
            addChild(nameText);
            msgIcon = new MsgIcon();
            msgIcon.tipText = Language.L["给TA留言"];
            msgIcon.addEventListener(MouseEvent.CLICK, msgIconClick);
            msgIcon.addEventListener(MouseEvent.ROLL_OVER, onOver);
            msgIcon.addEventListener(MouseEvent.ROLL_OUT, onOut);
            msgIcon.x = 50 + 50 + 100;
            msgIcon.y = 20;
            var _loc_1:Number;
            msgIcon.scaleY = 0.8;
            msgIcon.scaleX = _loc_1;
            addChild(msgIcon);
            propertyText = new PropertyText();
            propertyText.x = 60;
            propertyText.y = 40;
            addChild(propertyText);
            return;
        }// end function

        public function set exp(param1:int) : void
        {
            _exp = param1;
            level.level = expToGrade(param1);
            var _loc_2:* = gradeToExp(level.level);
            var _loc_3:* = gradeToExp(level.level + 1);
            _nextExp = param1 - _loc_2;
            expBar.exp = _loc_3 - _loc_2;
            expBar.nextExp = _nextExp;
            expBar.percent = (_exp - _loc_2) / (_loc_3 - _loc_2) * 100;
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

        private function msgIconClick(param1:MouseEvent) : void
        {
            WControl.openForName("profile", 1);
            return;
        }// end function

        private function gradeToExp(param1:int) : uint
        {
            return 100 * param1 * (param1 + 1);
        }// end function

        public function get vip() : String
        {
            return nameText.vip;
        }// end function

        public function get color() : String
        {
            return _color;
        }// end function

        public function get exp() : int
        {
            return _exp;
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
            if (param1 != "")
            {
                loader.load(new URLRequest(param1));
            }// end if
            return;
        }// end function

        public function set goldValue(param1:Number) : void
        {
            _goldValue = param1;
            propertyText.goldValue = param1;
            return;
        }// end function

        private function onOut(param1:MouseEvent) : void
        {
            TipControl.hide();
            return;
        }// end function

        private function onRollOver(param1:MouseEvent) : void
        {
            return;
        }// end function

        public function get goldValue() : Number
        {
            return _goldValue;
        }// end function

        private function onRollOut(param1:MouseEvent) : void
        {
            return;
        }// end function

        private function onOver(param1:MouseEvent) : void
        {
            TipControl.show("MouseTip", (param1.currentTarget as ToolBase).tipText);
            return;
        }// end function

        public function get userName() : String
        {
            return nameText.text;
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

        private function loaderClick(param1:MouseEvent) : void
        {
            WControl.openForName("profile", 0);
            return;
        }// end function

        public function get url() : String
        {
            return _url;
        }// end function

        private function expToGrade(param1:uint) : int
        {
            return Math.sqrt((param1 + 25) / 100) - 0.5;
        }// end function

    }
}
