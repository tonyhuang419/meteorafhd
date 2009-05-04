package com.minutes.ui.core
{
    import flash.display.*;
    import flash.events.*;

    public class LipiUIComponent extends Sprite
    {
        private var _bgSkin:LipiSkin;
        private var _height:Number = 100;
        private var _width:Number = 100;
        private var _bgSkinChange:Boolean = false;
        private var _heightChange:Boolean = true;
        private var _widthChange:Boolean = true;
        private var _bgAlpha:Number = 1;
        private var _bgColor:uint = 16777215;
        private var _bgAlphaChange:Boolean = false;
        private var _bgColorChange:Boolean = false;
        private var _isUpdate:Boolean = false;

        public function LipiUIComponent()
        {
            addEventListener(Event.ADDED_TO_STAGE, onAdded);
            return;
        }// end function

        public function set bgSkin(param1:LipiSkin) : void
        {
            if (param1 == _bgSkin)
            {
                return;
            }// end if
            if (_bgSkin != null && contains(_bgSkin))
            {
                removeChild(_bgSkin);
            }// end if
            _bgSkinChange = true;
            _bgSkin = param1;
            invalidateDisplayList();
            return;
        }// end function

        public function invalidateDisplayList() : void
        {
            _isUpdate = true;
            if (stage != null)
            {
                stage.invalidate();
            }// end if
            return;
        }// end function

        override public function get width() : Number
        {
            return _width;
        }// end function

        override public function set height(param1:Number) : void
        {
            _height = param1;
            _heightChange = true;
            invalidateDisplayList();
            return;
        }// end function

        override public function set width(param1:Number) : void
        {
            _width = param1;
            _widthChange = true;
            invalidateDisplayList();
            return;
        }// end function

        private function onRender(param1:Event) : void
        {
            _update();
            return;
        }// end function

        public function get bgAlpha() : Number
        {
            return _bgAlpha;
        }// end function

        public function get bgColor() : uint
        {
            return _bgColor;
        }// end function

        private function onAdded(param1:Event) : void
        {
            removeEventListener(Event.ADDED_TO_STAGE, onAdded);
            stage.addEventListener(Event.ENTER_FRAME, onRender);
            _isUpdate = true;
            _update();
            return;
        }// end function

        override public function get height() : Number
        {
            return _height;
        }// end function

        private function _update() : void
        {
            if (_isUpdate == true)
            {
                updateDisplayList(width, height);
            }// end if
            return;
        }// end function

        public function get bgSkin() : LipiSkin
        {
            return _bgSkin;
        }// end function

        public function set bgAlpha(param1:Number) : void
        {
            _bgAlpha = param1;
            _bgAlphaChange = true;
            invalidateDisplayList();
            return;
        }// end function

        protected function updateDisplayList(param1:Number, param2:Number) : void
        {
            var _loc_3:UIEvent;
            _isUpdate = false;
            if (_bgAlphaChange || _bgColorChange)
            {
                if (bgAlpha == 0)
                {
                    graphics.clear();
                }
                else
                {
                    graphics.clear();
                    graphics.beginFill(bgColor, bgAlpha);
                    graphics.drawRect(0, 0, param1, param2);
                    graphics.endFill();
                }// end if
            }// end else if
            if (_widthChange || _heightChange)
            {
                _loc_3 = new UIEvent(UIEvent.RESIZE);
                dispatchEvent(_loc_3);
                if (_bgSkin != null)
                {
                    _bgSkin.height = this.height;
                    _bgSkin.width = this.width;
                }
                else
                {
                    graphics.clear();
                    graphics.beginFill(bgColor, bgAlpha);
                    graphics.drawRect(0, 0, param1, param2);
                    graphics.endFill();
                }// end if
            }// end else if
            if (_bgSkinChange && _bgSkin != null)
            {
                _bgSkin.width = this.width;
                _bgSkin.height = this.height;
                super.addChildAt(_bgSkin, 0);
            }// end if
            _bgSkinChange = false;
            _bgAlphaChange = false;
            _bgColorChange = false;
            _widthChange = false;
            _heightChange = false;
            return;
        }// end function

        public function set bgColor(param1:uint) : void
        {
            _bgColor = param1;
            _bgColorChange = true;
            invalidateDisplayList();
            return;
        }// end function

    }
}
