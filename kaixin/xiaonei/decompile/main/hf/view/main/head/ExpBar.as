package hf.view.main.head
{
    import flash.display.*;
    import flash.text.*;
    import hf.view.*;

    public class ExpBar extends Sprite
    {
        private var lib:MaterialLib;
        private var _percent:int = 0;
        private var blueBar:Sprite;
        private var _exp:int = 0;
        private var yellowBarBg:Sprite;
        private var _nextExp:int;
        private var expText:TextField;
        private var _color:String = "blue";
        private var blueBarBg:Sprite;
        private var yellowBar:Sprite;
        public static const BLUE:String = "blue";
        public static const YELLOW:String = "yellow";

        public function ExpBar(param1:String = "")
        {
            lib = MaterialLib.getInstance();
            if (param1 != "")
            {
                this.color = param1;
            }// end if
            return;
        }// end function

        public function set percent(param1:int) : void
        {
            _percent = param1;
            setPC();
            return;
        }// end function

        private function setPC() : void
        {
            if (yellowBar != null)
            {
                yellowBar.width = 119 * (percent * 0.01);
            }
            else if (blueBar != null)
            {
                blueBar.width = 119 * (percent * 0.01);
            }// end else if
            return;
        }// end function

        public function set nextExp(param1:int) : void
        {
            _nextExp = param1;
            expText.text = nextExp + " / " + exp;
            return;
        }// end function

        public function get exp() : int
        {
            return _exp;
        }// end function

        public function get color() : String
        {
            return _color;
        }// end function

        public function get percent() : int
        {
            return _percent;
        }// end function

        public function set color(param1:String) : void
        {
            _color = param1;
            addBar();
            setPC();
            return;
        }// end function

        public function get nextExp() : int
        {
            return _nextExp;
        }// end function

        private function addBar() : void
        {
            if (color == BLUE)
            {
                if (yellowBarBg != null)
                {
                    removeChild(yellowBarBg);
                    yellowBarBg = null;
                }// end if
                if (yellowBar != null)
                {
                    removeChild(yellowBar);
                    yellowBar = null;
                }// end if
                if (blueBarBg == null)
                {
                    blueBarBg = lib.getMaterial("ExpBlueBg") as Sprite;
                }// end if
                addChild(blueBarBg);
                if (blueBar == null)
                {
                    blueBar = lib.getMaterial("ExpBlue") as Sprite;
                }// end if
                blueBar.x = 1;
                addChild(blueBar);
            }
            else
            {
                if (blueBarBg != null)
                {
                    removeChild(blueBarBg);
                    blueBarBg = null;
                }// end if
                if (blueBar != null)
                {
                    removeChild(blueBar);
                    blueBar = null;
                }// end if
                if (yellowBarBg == null)
                {
                    yellowBarBg = lib.getMaterial("ExpYellowBg") as Sprite;
                }// end if
                addChild(yellowBarBg);
                if (yellowBar == null)
                {
                    yellowBar = lib.getMaterial("ExpYellow") as Sprite;
                }// end if
                yellowBar.x = 1;
                addChild(yellowBar);
            }// end else if
            expText = new TextField();
            expText.selectable = false;
            expText.mouseEnabled = false;
            expText.defaultTextFormat = new TextFormat("Tahoma", 12, null, null, null, null, null, null, TextFormatAlign.CENTER);
            expText.y = -4;
            expText.width = 120;
            expText.height = 18;
            expText.text = nextExp + " / " + exp;
            addChild(expText);
            return;
        }// end function

        public function set exp(param1:int) : void
        {
            _exp = param1;
            expText.text = nextExp + " / " + exp;
            return;
        }// end function

    }
}
