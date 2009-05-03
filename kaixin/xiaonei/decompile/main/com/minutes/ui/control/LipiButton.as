package com.minutes.ui.control
{
    import com.minutes.ui.core.*;
    import flash.display.*;
    import flash.filters.*;
    import flash.text.*;

    public class LipiButton extends LipiUIComponent
    {
        private var formatChange:Boolean = false;
        private var _textStyleChange:Boolean = false;
        private var _enable:Boolean = true;
        private var _textSize:int = 12;
        private var _sizeChange:Boolean = false;
        private var _textBold:Boolean = false;
        private var _textColor:uint = 0;
        private var labelText:TextField;
        private var _icon:DisplayObject;
        private var _textModeChange:Boolean = false;
        private var _label:String = "";
        private var _textFont:String = null;
        private var _iconLeft:int = 0;

        public function LipiButton()
        {
            init();
            return;
        }// end function

        public function get enable() : Boolean
        {
            return _enable;
        }// end function

        public function set enable(param1:Boolean) : void
        {
            _enable = param1;
            _textModeChange = true;
            invalidateDisplayList();
            return;
        }// end function

        override public function set width(param1:Number) : void
        {
            _sizeChange = true;
            super.width = param1;
            return;
        }// end function

        private function setTextFormat() : void
        {
            if (labelText == null)
            {
                return;
            }// end if
            labelText.setTextFormat(new TextFormat(textFont, textSize, textColor, textBold));
            return;
        }// end function

        private function init() : void
        {
            this.height = 30;
            this.width = 100;
            this.bgColor = 16711680;
            this.buttonMode = true;
            this.useHandCursor = true;
            return;
        }// end function

        public function set textSize(param1:int) : void
        {
            _textSize = param1;
            formatChange = true;
            invalidateDisplayList();
            return;
        }// end function

        public function get iconLeft() : int
        {
            return _iconLeft;
        }// end function

        public function get textFont() : String
        {
            return _textFont;
        }// end function

        public function set textBold(param1:Boolean) : void
        {
            _textBold = param1;
            formatChange = true;
            invalidateDisplayList();
            return;
        }// end function

        private function createTextField() : void
        {
            labelText = new TextField();
            labelText.mouseEnabled = false;
            labelText.autoSize = TextFieldAutoSize.LEFT;
            labelText.selectable = false;
            labelText.defaultTextFormat = new TextFormat("Tahoma");
            addChild(labelText);
            formatChange = true;
            invalidateDisplayList();
            return;
        }// end function

        public function get textColor() : uint
        {
            return _textColor;
        }// end function

        public function set textFont(param1:String) : void
        {
            _textFont = param1;
            formatChange = true;
            invalidateDisplayList();
            return;
        }// end function

        private function textModeChange() : void
        {
            if (_enable)
            {
                filters = [];
            }
            else
            {
                filters = [new ColorMatrixFilter([0.3086, 0.6094, 0.082, 0, 0, 0.3086, 0.6094, 0.082, 0, 0, 0.3086, 0.6094, 0.082, 0, 0, 0, 0, 0, 1, 0])];
            }// end else if
            if (bgSkin != null)
            {
                bgSkin.enableMouse = enable;
            }// end if
            return;
        }// end function

        public function set iconLeft(param1:int) : void
        {
            _iconLeft = param1;
            _sizeChange = true;
            invalidateDisplayList();
            return;
        }// end function

        override public function set bgSkin(param1:LipiSkin) : void
        {
            super.bgSkin = param1;
            if (bgSkin != null)
            {
                bgSkin.enableMouse = enable;
            }// end if
            return;
        }// end function

        public function get textSize() : int
        {
            return _textSize;
        }// end function

        public function get textBold() : Boolean
        {
            return _textBold;
        }// end function

        override public function set height(param1:Number) : void
        {
            _sizeChange = true;
            super.height = param1;
            return;
        }// end function

        public function set label(param1:String) : void
        {
            if (labelText == null)
            {
                createTextField();
            }// end if
            _label = param1;
            if (!_label)
            {
            }// end if
            labelText.text = "";
            formatChange = true;
            invalidateDisplayList();
            return;
        }// end function

        public function set textColor(param1:uint) : void
        {
            _textColor = param1;
            formatChange = true;
            invalidateDisplayList();
            return;
        }// end function

        public function set icon(param1:DisplayObject) : void
        {
            if (_icon != null)
            {
                _icon = null;
                removeChild(_icon);
            }// end if
            if (param1 != null)
            {
                _icon = param1;
                if (param1 is Sprite)
                {
                    Sprite(param1).mouseEnabled = false;
                }// end if
                addChild(param1);
            }// end if
            _sizeChange = true;
            invalidateDisplayList();
            return;
        }// end function

        public function get label() : String
        {
            return _label;
        }// end function

        override protected function updateDisplayList(param1:Number, param2:Number) : void
        {
            super.updateDisplayList(param1, param2);
            if (formatChange)
            {
                setTextFormat();
                setTextPosition();
                formatChange = false;
            }// end if
            if (_sizeChange)
            {
                setTextPosition();
                _sizeChange = false;
            }// end if
            if (_textModeChange)
            {
                textModeChange();
                _textModeChange = false;
            }// end if
            return;
        }// end function

        protected function setTextPosition() : void
        {
            if (labelText == null)
            {
                return;
            }// end if
            if (_icon != null)
            {
                _icon.x = iconLeft + _icon.width / 2;
                _icon.y = (this.height - _icon.height) / 2;
                labelText.x = iconLeft + _icon.width * 2;
                labelText.y = (this.height - labelText.height) / 2;
            }
            else
            {
                labelText.x = (this.width - labelText.width) / 2;
                labelText.y = (this.height - labelText.height) / 2.5;
            }// end else if
            return;
        }// end function

        public function get icon() : DisplayObject
        {
            return _icon;
        }// end function

    }
}
