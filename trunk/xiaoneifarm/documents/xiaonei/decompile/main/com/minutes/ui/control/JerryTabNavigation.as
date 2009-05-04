package com.minutes.ui.control
{
    import com.minutes.ui.core.*;
    import flash.display.*;
    import flash.events.*;

    public class JerryTabNavigation extends LipiUIComponent
    {
        private var unInit:Boolean = true;
        private var menu:JerryNavigation;
        private var tabContainer:JerryTabContainer;
        private var _selectedIndex:int = 0;
        public static const TAB_SELECT:String = "tabSelect";

        public function JerryTabNavigation()
        {
            this.bgAlpha = 0;
            menu = new JerryNavigation();
            tabContainer = new JerryTabContainer();
            menu.addEventListener(JerryNavigation.MOUSE_DOWN, dosomething);
            return;
        }// end function

        private function show() : void
        {
            if (unInit)
            {
                unInit = false;
                addChild(tabContainer);
                addChild(menu);
            }// end if
            autoLayer();
            return;
        }// end function

        public function set menuDefaultSkin(param1:Class) : void
        {
            menu.defaultSkin = param1;
            return;
        }// end function

        public function selectedFontStyle(param1:uint = 12, param2:uint = 13369344, param3:String = "", param4:Boolean = false) : void
        {
            menu.selectedFontStyle(param1, param2, param3, param4);
            return;
        }// end function

        override public function set width(param1:Number) : void
        {
            super.width = param1;
            menu.width = param1 - menu.x;
            tabContainer.width = param1;
            return;
        }// end function

        override public function set height(param1:Number) : void
        {
            super.height = param1;
            menu.height = menu.menuHeight;
            tabContainer.height = param1 - menu.menuHeight;
            return;
        }// end function

        private function dosomething(param1:Event) : void
        {
            tabContainer.selectedIndex = JerryNavigation(param1.currentTarget).selectedIndex;
            var _loc_2:* = new UIEvent(JerryTabNavigation.TAB_SELECT, true);
            _loc_2.data = JerryNavigation(param1.currentTarget).selectedIndex;
            dispatchEvent(_loc_2);
            return;
        }// end function

        public function set topHeight(param1:Number) : void
        {
            menu.topHeight = param1;
            return;
        }// end function

        public function set menuHeight(param1:Number) : void
        {
            menu.menuHeight = param1;
            autoLayer();
            return;
        }// end function

        public function addItem(param1:String, param2:DisplayObject, param3:Number = 0, param4:Number = 0) : void
        {
            menu.addItem(param1, param3, param4);
            tabContainer.addItem(param2);
            selectedIndex = 0;
            show();
            return;
        }// end function

        public function get menuLeftDistance() : int
        {
            return menu.leftDistance;
        }// end function

        public function get topHeight() : Number
        {
            return menu.topHeight;
        }// end function

        public function defaultFontStyle(param1:uint = 12, param2:uint = 0, param3:String = "", param4:Boolean = false) : void
        {
            menu.defaultFontStyle(param1, param2, param3, param4);
            return;
        }// end function

        private function autoLayer() : void
        {
            tabContainer.width = this.width;
            tabContainer.height = this.height - menu.menuHeight;
            tabContainer.x = 0;
            tabContainer.y = menu.menuHeight;
            return;
        }// end function

        public function set menuDefaultButtonWidth(param1:Number) : void
        {
            menu.menuButtonWidth = param1;
            return;
        }// end function

        public function set bgClass(param1:Class) : void
        {
            this.bgSkin = new LipiSkin(param1);
            return;
        }// end function

        public function set menuBgClass(param1:Class) : void
        {
            menu.bgSkin = new LipiSkin(param1);
            return;
        }// end function

        public function set selectedIndex(param1:int) : void
        {
            _selectedIndex = param1;
            menu.selectedIndex = param1;
            tabContainer.selectedIndex = param1;
            return;
        }// end function

        public function get menuDefaultButtonWidth() : Number
        {
            return menu.menuButtonWidth;
        }// end function

        public function set contentBgClass(param1:Class) : void
        {
            tabContainer.bgSkin = new LipiSkin(param1);
            return;
        }// end function

        public function set menuSelectedSkin(param1:Class) : void
        {
            menu.selectedSkin = param1;
            return;
        }// end function

        public function set menuDefaultButtonHeight(param1:Number) : void
        {
            menu.menuButtonHeight = param1;
            return;
        }// end function

        public function get selectedIndex() : int
        {
            return _selectedIndex;
        }// end function

        public function set menuSpaceDistance(param1:int) : void
        {
            menu.spaceBetween = param1;
            return;
        }// end function

        public function get menuDefaultButtonHeight() : Number
        {
            return menu.menuButtonHeight;
        }// end function

        public function get menuSpaceDistance() : int
        {
            return menu.spaceBetween;
        }// end function

        public function set menuLeftDistance(param1:int) : void
        {
            menu.leftDistance = param1;
            return;
        }// end function

    }
}
