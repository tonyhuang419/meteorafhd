package com.minutes.ui.core
{
    import com.minutes.ui.control.*;
    import flash.display.*;
    import flash.events.*;
    import flash.geom.*;
    import flash.text.*;

    public class LipiPanel extends LipiUIComponent
    {
        private var _dragBar:Sprite;
        private var _closeButton:LipiUIComponent;
        private var _closeHeight:int = 18;
        private var _closeSkin:LipiSkin;
        private var _closeTop:int = 5;
        private var _closeRight:int = 7;
        private var _headHeightChange:Boolean = false;
        private var _titleText:TextField;
        private var _sizeChange:Boolean = false;
        private var _closeVisible:Boolean = true;
        private var _contentPane:Sprite = null;
        private var _closeWidth:int = 18;
        private var closePositionChange:Boolean = false;
        private var _headHeight:int = 25;
        private var _title:String = "";
        private var _titleChange:Boolean = false;
        private var childChange:Boolean = false;

        public function LipiPanel()
        {
            init();
            return;
        }// end function

        public function set closeRight(param1:int) : void
        {
            _closeRight = param1;
            closePositionChange = true;
            invalidateDisplayList();
            return;
        }// end function

        override public function addChildAt(param1:DisplayObject, param2:int) : DisplayObject
        {
            if (_contentPane == null)
            {
                return null;
            }// end if
            _contentPane.addChildAt(param1, param2);
            return param1;
        }// end function

        private function init() : void
        {
            _contentPane = new Sprite();
            _contentPane.x = 0;
            _contentPane.y = 0;
            super.addChild(_contentPane);
            _dragBar = new Sprite();
            _dragBar.graphics.beginFill(0, 0);
            _dragBar.graphics.drawRect(0, 0, 10, 10);
            _dragBar.graphics.endFill();
            _dragBar.width = width;
            _dragBar.height = headHeight;
            _dragBar.addEventListener(MouseEvent.MOUSE_DOWN, dragDown);
            super.addChild(_dragBar);
            _closeButton = new LipiUIComponent();
            _closeButton.addEventListener(MouseEvent.CLICK, _closeButtonClick);
            _closeButton.width = closeWidth;
            _closeButton.height = closeHeight;
            _closeButton.x = this.width - _closeButton.width - closeRight;
            _closeButton.y = closeTop;
            _closeButton.buttonMode = true;
            _closeButton.useHandCursor = true;
            super.addChild(_closeButton);
            addEventListener(UIEvent.RESIZE, onResize);
            if (LipiDefaultSkin.WindowBg != null)
            {
                bgSkin = new LipiSkin(LipiDefaultSkin.WindowBg);
            }// end if
            if (LipiDefaultSkin.WindowCloseBg != null)
            {
                closeSkin = new LipiSkin(LipiDefaultSkin.WindowCloseBg);
            }// end if
            return;
        }// end function

        public function get titleFormat() : TextFormat
        {
            return _titleText.defaultTextFormat;
        }// end function

        public function get closeRight() : int
        {
            return _closeRight;
        }// end function

        public function get closeSkin() : LipiSkin
        {
            return _closeSkin;
        }// end function

        override public function getChildByName(param1:String) : DisplayObject
        {
            if (_contentPane == null)
            {
                return null;
            }// end if
            return _contentPane.getChildByName(param1);
        }// end function

        override public function getChildIndex(param1:DisplayObject) : int
        {
            if (_contentPane == null)
            {
                return -1;
            }// end if
            return _contentPane.getChildIndex(param1);
        }// end function

        public function get headHeight() : int
        {
            return _headHeight;
        }// end function

        public function set closeSkin(param1:LipiSkin) : void
        {
            _closeSkin = param1;
            _closeButton.bgSkin = param1;
            return;
        }// end function

        public function get closeWidth() : int
        {
            return _closeWidth;
        }// end function

        public function set closeVisible(param1:Boolean) : void
        {
            _closeVisible = param1;
            return;
        }// end function

        public function set title(param1:String) : void
        {
            if (param1 == null)
            {
                param1 = "";
            }// end if
            _title = param1;
            if (param1 == "" && _titleText != null)
            {
                removeChild(_titleText);
                _titleText = null;
            }// end if
            if (_titleText == null && param1 != "")
            {
                _titleText = new TextField();
                _titleText.defaultTextFormat = new TextFormat("Tahoma", 16, 16777215, true);
                _titleText.mouseEnabled = false;
                _titleText.autoSize = TextFieldAutoSize.LEFT;
                addChild(_titleText);
            }// end if
            if (_titleText != null)
            {
                _titleText.text = param1;
            }// end if
            _titleChange = true;
            invalidateDisplayList();
            return;
        }// end function

        public function get closeTop() : int
        {
            return _closeTop;
        }// end function

        public function get closeHeight() : int
        {
            return _closeHeight;
        }// end function

        override public function getChildAt(param1:int) : DisplayObject
        {
            if (_contentPane == null)
            {
                return null;
            }// end if
            return _contentPane.getChildAt(param1);
        }// end function

        private function _closeButtonClick(param1:MouseEvent) : void
        {
            var _loc_2:* = new UIEvent(UIEvent.CLOSE);
            dispatchEvent(_loc_2);
            return;
        }// end function

        public function addChildToSuper(param1:DisplayObject) : void
        {
            super.addChild(param1);
            return;
        }// end function

        override public function get numChildren() : int
        {
            return _contentPane.numChildren;
        }// end function

        override public function removeChildAt(param1:int) : DisplayObject
        {
            if (_contentPane == null)
            {
                return null;
            }// end if
            var _loc_2:* = _contentPane.removeChildAt(param1);
            return _loc_2;
        }// end function

        public function set headHeight(param1:int) : void
        {
            _headHeight = param1;
            _headHeightChange = true;
            invalidateDisplayList();
            return;
        }// end function

        public function get title() : String
        {
            return _title;
        }// end function

        public function get closeVisible() : Boolean
        {
            return _closeVisible;
        }// end function

        public function set closeWidth(param1:int) : void
        {
            _closeWidth = param1;
            closePositionChange = true;
            invalidateDisplayList();
            return;
        }// end function

        public function set titleFormat(param1:TextFormat) : void
        {
            _titleText.defaultTextFormat = param1;
            title = _title;
            return;
        }// end function

        private function dragUp(param1:MouseEvent) : void
        {
            stopDrag();
            stage.removeEventListener(MouseEvent.MOUSE_UP, dragUp);
            return;
        }// end function

        private function onResize(param1:UIEvent) : void
        {
            _sizeChange = true;
            invalidateDisplayList();
            return;
        }// end function

        private function dragDown(param1:MouseEvent) : void
        {
            if (parent != null)
            {
                parent.addChild(this);
            }// end if
            startDrag(false, new Rectangle(this.mouseX * -1, this.mouseY * -1, stage.stageWidth - this.mouseY, stage.stageHeight - this.mouseY));
            if (stage != null)
            {
                stage.addEventListener(MouseEvent.MOUSE_UP, dragUp, false, 0, true);
            }// end if
            return;
        }// end function

        override protected function updateDisplayList(param1:Number, param2:Number) : void
        {
            super.updateDisplayList(param1, param2);
            if (_headHeightChange || _titleChange)
            {
                if (_titleText != null)
                {
                    _titleText.x = (param1 - _titleText.width) / 2;
                    _titleText.y = (_headHeight - _titleText.height) / 2;
                }// end if
                if (_headHeightChange)
                {
                    _contentPane.y = _headHeight;
                    _dragBar.width = width;
                    _dragBar.height = _headHeight;
                }// end if
                _titleChange = false;
                _headHeightChange = false;
            }// end if
            if (_sizeChange)
            {
                _closeButton.x = this.width - _closeButton.width - closeRight;
                _closeButton.y = closeTop;
                _dragBar.width = width;
                _sizeChange = false;
            }// end if
            if (closePositionChange)
            {
                _closeButton.width = closeWidth;
                _closeButton.height = closeHeight;
                _closeButton.x = this.width - _closeButton.width - closeRight;
                _closeButton.y = closeTop;
                closePositionChange = false;
            }// end if
            return;
        }// end function

        override public function removeChild(param1:DisplayObject) : DisplayObject
        {
            if (_contentPane == null)
            {
                return null;
            }// end if
            _contentPane.removeChild(param1);
            return param1;
        }// end function

        public function set closeTop(param1:int) : void
        {
            _closeTop = param1;
            closePositionChange = true;
            invalidateDisplayList();
            return;
        }// end function

        override public function addChild(param1:DisplayObject) : DisplayObject
        {
            if (_contentPane == null)
            {
                return null;
            }// end if
            _contentPane.addChild(param1);
            return param1;
        }// end function

        public function set closeHeight(param1:int) : void
        {
            _closeHeight = param1;
            closePositionChange = true;
            invalidateDisplayList();
            return;
        }// end function

    }
}
