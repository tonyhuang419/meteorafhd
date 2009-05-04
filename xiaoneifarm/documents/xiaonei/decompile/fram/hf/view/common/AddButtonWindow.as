package hf.view.common
{
    import com.minutes.ui.control.*;
    import flash.display.*;
    import flash.events.*;

    public class AddButtonWindow extends BaseWindow
    {
        private var addChildCache:Array;
        private var _buttonSpace:int = 10;
        private var buttonCache:Array;
        private var _bottomSpace:int = 45;

        public function AddButtonWindow()
        {
            addChildCache = [];
            buttonCache = [];
            width = 360;
            height = 240;
            mode = true;
            return;
        }// end function

        public function set betweenSpace(param1:int) : void
        {
            _buttonSpace = param1;
            return;
        }// end function

        private function toCenterPositon() : void
        {
            x = stage.stageWidth * 0.5 - width * 0.5;
            y = stage.stageHeight * 0.5 - height * 0.5;
            return;
        }// end function

        public function closeWindow(param1:Event = null) : void
        {
            this.closeHandler();
            return;
        }// end function

        public function addWindowChild(param1:DisplayObject) : DisplayObject
        {
            addChildCache.push(param1);
            return param1;
        }// end function

        public function removeAllWindowChild() : void
        {
            addChildCache = [];
            return;
        }// end function

        override public function init() : void
        {
            var _loc_5:int;
            var _loc_1:int;
            _loc_1 = 0;
            while (_loc_1 < addChildCache.length)
            {
                // label
                this.addChild(addChildCache[_loc_1]);
                _loc_1++;
            }// end while
            var _loc_2:int;
            var _loc_3:int;
            var _loc_4:* = buttonCache.length;
            if (buttonCache.length > 0)
            {
                _loc_1 = 0;
                while (_loc_1 < _loc_4--)
                {
                    // label
                    _loc_3 = _loc_3 + (buttonCache[_loc_1].width + _buttonSpace);
                    _loc_1++;
                }// end while
                _loc_3 = _loc_3 + buttonCache[_loc_5].width;
                _loc_2 = this.width * 0.5 - _loc_3 * 0.5;
                _loc_1 = 0;
                while (_loc_1 < _loc_4)
                {
                    // label
                    buttonCache[_loc_1].x = _loc_2;
                    _loc_2 = _loc_2 + (buttonCache[_loc_1].width + _buttonSpace);
                    buttonCache[_loc_1].y = this.height - _bottomSpace;
                    addChild(buttonCache[_loc_1]);
                    _loc_1++;
                }// end while
            }// end if
            toCenterPositon();
            return;
        }// end function

        public function addSpecialButton(param1:DisplayObject) : DisplayObject
        {
            buttonCache.push(param1);
            return param1;
        }// end function

        public function removeAllButton() : void
        {
            buttonCache = [];
            return;
        }// end function

        public function addButton(param1:String = "Button", param2:String = null, param3:int = 100, param4:int = 25, param5:Function = null, param6:Boolean = true, param7:int = 16777215, param8:int = 12, param9:Boolean = false, param10:String = null, param11:int = 0) : LipiButton
        {
            var _loc_12:* = new winButton(param1, param2, param3, param4, param5, param6, param7, param8, param9, param10, param11);
            buttonCache.push(_loc_12);
            return _loc_12;
        }// end function

        public function set bottomSpace(param1:int) : void
        {
            _bottomSpace = param1;
            return;
        }// end function

    }
}
