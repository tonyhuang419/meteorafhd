package hf.view.main.head
{
    import flash.display.*;
    import flash.text.*;
    import hf.view.*;

    public class Level extends Sprite
    {
        private var yellowBg:Sprite;
        private var lib:MaterialLib;
        private var levelTextField:TextField;
        private var _color:String = "blue";
        private var _level:int = 0;
        private var blueBg:Sprite;
        public static const BLUE:String = "blue";
        public static const YELLOW:String = "yellow";

        public function Level(param1:String = "")
        {
            lib = MaterialLib.getInstance();
            levelTextField = new TextField();
            levelTextField.mouseEnabled = false;
            levelTextField.width = 30;
            levelTextField.height = 30;
            levelTextField.defaultTextFormat = new TextFormat("fontForte", 20, 39423, null, null, null, null, null, TextFormatAlign.CENTER);
            levelTextField.embedFonts = true;
            levelTextField.y = 2;
            levelTextField.text = "0";
            addChild(levelTextField);
            if (param1 != "")
            {
                this.color = param1;
            }// end if
            return;
        }// end function

        public function get color() : String
        {
            return _color;
        }// end function

        public function get level() : int
        {
            return _level;
        }// end function

        private function addBg() : void
        {
            if (color == BLUE)
            {
                if (yellowBg != null)
                {
                    removeChild(yellowBg);
                    yellowBg = null;
                }// end if
                if (blueBg == null)
                {
                    blueBg = lib.getMaterial("LevelBlue") as Sprite;
                }// end if
                addChildAt(blueBg, 0);
                levelTextField.textColor = 39423;
            }
            else
            {
                if (blueBg != null)
                {
                    removeChild(blueBg);
                    blueBg = null;
                }// end if
                if (yellowBg == null)
                {
                    yellowBg = lib.getMaterial("LevelYellow") as Sprite;
                }// end if
                addChildAt(yellowBg, 0);
                levelTextField.textColor = 13408512;
            }// end else if
            return;
        }// end function

        public function set level(param1:int) : void
        {
            _level = param1;
            levelTextField.text = String(param1);
            return;
        }// end function

        public function set color(param1:String) : void
        {
            _color = param1;
            addBg();
            return;
        }// end function

    }
}
