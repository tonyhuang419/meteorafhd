package com.minutes.ui.control
{
    import com.minutes.ui.constant.*;
    import com.minutes.ui.core.*;
    import flash.display.*;
    import flash.events.*;

    public class JerryNavigation extends LipiContainer
    {
        private var _spaceBetween:int = 0;
        private var indexChanged:Boolean = false;
        private var _leftDistance:Number = 0;
        private var _defaultColor:uint = 0;
        private var buttonList:Array;
        private var _selectedSkin:Class = null;
        private var _selectedColor:uint = 10027008;
        private var _defaultButtonHeight:Number = 40;
        private var _defaultSkin:Class = null;
        private var layerChanged:Boolean = false;
        private var _selectedBold:Boolean = false;
        private var _defaultBold:Boolean = false;
        private var _defaultSize:uint = 12;
        private var _selectedSize:uint = 12;
        private var _defaultButtonWidth:Number = 120;
        private var SkinChanged:Boolean = false;
        private var _topHeight:Number = 10;
        private var _defaultWidthSet:Boolean = false;
        private var _selectedFont:String = "";
        private var _defaultFont:String = "";
        private var _trueHeight:Number = 20;
        private var _selectedIndex:int;
        public static const MOUSE_DOWN:String = "mousedown";

        public function JerryNavigation()
        {
            buttonList = [];
            this.bgAlpha = 0;
            this.width = 0;
            this.height = 0;
            this.verticalScrollPolicy = ScrollPolicy.OFF;
            this.horizontalScrollPolicy = ScrollPolicy.OFF;
            menuHeight = 30;
            return;
        }// end function

        public function get menuButtonHeight() : Number
        {
            return _defaultButtonHeight;
        }// end function

        public function set menuButtonHeight(param1:Number) : void
        {
            _defaultButtonHeight = param1;
            return;
        }// end function

        override public function addChildAt(param1:DisplayObject, param2:int) : DisplayObject
        {
            super.addChildAt(param1, param2);
            layerChanged = true;
            invalidateDisplayList();
            return param1;
        }// end function

        public function get leftDistance() : Number
        {
            return _leftDistance;
        }// end function

        public function get spaceBetween() : int
        {
            return _spaceBetween;
        }// end function

        public function set menuButtonWidth(param1:Number) : void
        {
            _defaultButtonWidth = param1;
            _defaultWidthSet = true;
            autoLayer();
            return;
        }// end function

        private function setSelectedButton() : void
        {
            var _loc_1:int;
            while (_loc_1 < buttonList.length)
            {
                // label
                (buttonList[_loc_1] as ToggleButton).selected = false;
                (buttonList[_loc_1] as ToggleButton).textSize = _defaultSize;
                (buttonList[_loc_1] as ToggleButton).textColor = _defaultColor;
                if (_defaultFont != "")
                {
                    (buttonList[_loc_1] as ToggleButton).textFont = _defaultFont;
                }// end if
                (buttonList[_loc_1] as ToggleButton).textBold = _defaultBold;
                _loc_1++;
            }// end while
            (buttonList[selectedIndex] as ToggleButton).selected = true;
            (buttonList[selectedIndex] as ToggleButton).textSize = _selectedSize;
            (buttonList[selectedIndex] as ToggleButton).textColor = _selectedColor;
            if (_selectedFont != "")
            {
                (buttonList[selectedIndex] as ToggleButton).textFont = _selectedFont;
            }// end if
            (buttonList[selectedIndex] as ToggleButton).textBold = _selectedBold;
            return;
        }// end function

        private function skinPack() : void
        {
            var _loc_1:int;
            while (_loc_1 < buttonList.length)
            {
                // label
                buttonList[_loc_1].defaultSkin = new LipiSkin(defaultSkin);
                buttonList[_loc_1].selectedSkin = new LipiSkin(selectedSkin);
                _loc_1++;
            }// end while
            return;
        }// end function

        public function get selectedSkin() : Class
        {
            return _selectedSkin;
        }// end function

        public function set defaultSkin(param1:Class) : void
        {
            _defaultSkin = param1;
            SkinChanged = true;
            invalidateDisplayList();
            return;
        }// end function

        public function set leftDistance(param1:Number) : void
        {
            _leftDistance = param1;
            layerChanged = true;
            invalidateDisplayList();
            return;
        }// end function

        private function getSelectedIndex(param1:ToggleButton) : int
        {
            var _loc_2:int;
            while (_loc_2 < buttonList.length)
            {
                // label
                if (buttonList[_loc_2] == param1)
                {
                    return _loc_2;
                }// end if
                _loc_2++;
            }// end while
            return selectedIndex;
        }// end function

        public function set spaceBetween(param1:int) : void
        {
            _spaceBetween = param1;
            layerChanged = true;
            invalidateDisplayList();
            return;
        }// end function

        public function selectedFontStyle(param1:uint = 12, param2:uint = 13369344, param3:String = "", param4:Boolean = false) : void
        {
            _selectedSize = param1;
            _selectedColor = param2;
            _selectedFont = param3;
            _selectedBold = param4;
            return;
        }// end function

        public function set menuHeight(param1:Number) : void
        {
            _trueHeight = param1;
            super.height = param1;
            return;
        }// end function

        public function set topHeight(param1:Number) : void
        {
            _topHeight = param1;
            autoLayer();
            return;
        }// end function

        public function addItem(param1:String, param2:Number = 0, param3:Number = 0) : void
        {
            var _loc_4:* = new ToggleButton();
            new ToggleButton().bgAlpha = 0;
            _loc_4.label = param1;
            _loc_4.textSize = _defaultSize;
            _loc_4.textColor = _defaultColor;
            if (_defaultFont != "")
            {
                _loc_4.textFont = _defaultFont;
            }// end if
            buttonList.push(_loc_4);
            if (param2 != 0 && param3 != 0)
            {
                _loc_4.width = param2;
                _loc_4.height = param3;
                _loc_4.name = "selfSize";
            }
            else
            {
                _loc_4.width = _defaultButtonWidth;
                _loc_4.height = _defaultButtonHeight;
                _loc_4.name = "defaultSize";
            }// end else if
            addChild(_loc_4);
            _loc_4.addEventListener(MouseEvent.MOUSE_DOWN, mouseDownDispatcher);
            SkinChanged = true;
            invalidateDisplayList();
            return;
        }// end function

        public function get menuButtonWidth() : Number
        {
            return _defaultButtonWidth;
        }// end function

        public function get topHeight() : Number
        {
            return _topHeight;
        }// end function

        public function defaultFontStyle(param1:uint = 12, param2:uint = 0, param3:String = "", param4:Boolean = false) : void
        {
            _defaultSize = param1;
            _defaultColor = param2;
            _defaultFont = param3;
            _defaultBold = param4;
            return;
        }// end function

        public function get defaultSkin() : Class
        {
            return _defaultSkin;
        }// end function

        public function get menuHeight() : Number
        {
            return _trueHeight;
        }// end function

        private function mouseDownDispatcher(param1:MouseEvent) : void
        {
            var _loc_2:* = ToggleButton(param1.currentTarget);
            selectedIndex = getSelectedIndex(_loc_2);
            var _loc_3:* = new UIEvent(JerryNavigation.MOUSE_DOWN);
            _loc_3.data = this;
            this.dispatchEvent(_loc_3);
            return;
        }// end function

        public function set selectedSkin(param1:Class) : void
        {
            _selectedSkin = param1;
            SkinChanged = true;
            invalidateDisplayList();
            return;
        }// end function

        public function set selectedIndex(param1:int) : void
        {
            if (param1 < 0)
            {
                param1 = 0;
            }// end if
            if (param1 < buttonList.length)
            {
                _selectedIndex = param1;
                indexChanged = true;
                invalidateDisplayList();
            }// end if
            return;
        }// end function

        public function get selectedIndex() : int
        {
            return _selectedIndex;
        }// end function

        private function autoLayer() : void
        {
            var _loc_4:DisplayObject;
            var _loc_1:* = _leftDistance;
            var _loc_2:int;
            var _loc_3:* = this.numChildren;
            while (_loc_2 < _loc_3)
            {
                // label
                _loc_4 = this.getChildAt(_loc_2);
                if (_loc_4.name == "defaultSize")
                {
                    _loc_4.width = _defaultButtonWidth;
                    _loc_4.height = _defaultButtonHeight;
                }// end if
                _loc_4.x = _loc_1;
                _loc_4.y = _topHeight;
                _loc_1 = _loc_1 + (_loc_4.width + _spaceBetween);
                _loc_2++;
            }// end while
            resetSize();
            return;
        }// end function

        override public function addChild(param1:DisplayObject) : DisplayObject
        {
            super.addChild(param1);
            layerChanged = true;
            invalidateDisplayList();
            return param1;
        }// end function

        override protected function updateDisplayList(param1:Number, param2:Number) : void
        {
            super.updateDisplayList(param1, param2);
            if (SkinChanged)
            {
                skinPack();
                SkinChanged = false;
            }// end if
            if (indexChanged)
            {
                setSelectedButton();
                indexChanged = false;
            }// end if
            if (layerChanged)
            {
                autoLayer();
                layerChanged = false;
            }// end if
            return;
        }// end function

    }
}
