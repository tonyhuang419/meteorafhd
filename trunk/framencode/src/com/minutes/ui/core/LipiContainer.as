package com.minutes.ui.core
{
    import com.minutes.ui.constant.*;
    import com.minutes.ui.control.*;
    import flash.display.*;
    import flash.events.*;
    import flash.external.*;

    public class LipiContainer extends LipiUIComponent
    {
        private var _vBarWidth:int = 17;
        private var _sSkin:ScrollBarSkin;
        private var _horizontalScrollPolicyChange:Boolean = false;
        private var _clipContentChange:Boolean = false;
        private var _verticalScrollPolicyChange:Boolean = false;
        private var _sizeChange:Boolean = false;
        private var verticalScrollBar:LipiScrollBar;
        private var _contentPane:Sprite = null;
        private var horizontalScrollBar:LipiScrollBar;
        private var _clipContent:Boolean = true;
        private var _mask:Sprite;
        private var _hBarWidthChange:Boolean = false;
        private var _horizontalScrollPolicy:int = 2;
        private var _verticalScrollPolicy:int = 2;
        private var _vBarWidthChange:Boolean = false;
        private var childChange:Boolean = false;
        private var _contentPaneSizeChange:Boolean = false;
        private var _hBarWidth:int = 17;

        public function LipiContainer()
        {
            _mask = new Sprite();
            init();
            return;
        }// end function

        override public function getChildByName(param1:String) : DisplayObject
        {
            if (_contentPane == null)
            {
                return null;
            }// end if
            return _contentPane.getChildByName(param1);
        }// end function

        public function get vBarWidth() : int
        {
            return _vBarWidth;
        }// end function

        override public function getChildIndex(param1:DisplayObject) : int
        {
            if (_contentPane == null)
            {
                return -1;
            }// end if
            return _contentPane.getChildIndex(param1);
        }// end function

        private function _setBarSkin() : void
        {
            if (_sSkin == null)
            {
                return;
            }// end if
            if (verticalScrollBar != null)
            {
                if (_sSkin.trackSkin != null)
                {
                    verticalScrollBar.trackSkin = _sSkin.trackSkin;
                }// end if
                if (_sSkin.upSkin != null)
                {
                    verticalScrollBar.upSkin = _sSkin.upSkin;
                }// end if
                if (_sSkin.downSkin != null)
                {
                    verticalScrollBar.downSkin = _sSkin.downSkin;
                }// end if
                if (_sSkin.barSkin != null)
                {
                    verticalScrollBar.barSkin = _sSkin.barSkin;
                }// end if
                if (_sSkin.barIcon != null)
                {
                    verticalScrollBar.barIcon = _sSkin.barIcon;
                }// end if
                verticalScrollBar.updateSkin();
            }// end if
            if (horizontalScrollBar != null)
            {
                if (_sSkin.barIcon != null)
                {
                    horizontalScrollBar.trackSkin = _sSkin.trackSkin;
                }// end if
                if (_sSkin.upSkin != null)
                {
                    horizontalScrollBar.upSkin = _sSkin.upSkin;
                }// end if
                if (_sSkin.downSkin != null)
                {
                    horizontalScrollBar.downSkin = _sSkin.downSkin;
                }// end if
                if (_sSkin.barSkin != null)
                {
                    horizontalScrollBar.barSkin = _sSkin.barSkin;
                }// end if
                if (_sSkin.barIcon != null)
                {
                    horizontalScrollBar.barIcon = _sSkin.barIcon;
                }// end if
                horizontalScrollBar.updateSkin();
            }// end if
            return;
        }// end function

        override public function addChildAt(param1:DisplayObject, param2:int) : DisplayObject
        {
            if (_contentPane == null)
            {
                return null;
            }// end if
            _contentPane.addChildAt(param1, param2);
            _contentPaneSizeChange = true;
            invalidateDisplayList();
            return param1;
        }// end function

        private function addVerticalScrollBar() : void
        {
            verticalScrollBar = new LipiScrollBar();
            verticalScrollBar.addEventListener(LipiScrollBar.SCROLL, verticalScroll);
            verticalScrollBar.visible = false;
            verticalScrollBar.container = this;
            verticalScrollBar.updateSkin();
            super.addChild(verticalScrollBar);
            _setBarSkin();
            return;
        }// end function

        private function init() : void
        {
            _contentPane = new Sprite();
            _contentPane.x = 0;
            _contentPane.y = 0;
            super.addChild(_contentPane);
            _mask = new Sprite();
            _mask.x = 0;
            _mask.y = 0;
            super.addChild(_mask);
            _contentPane.mask = _mask;
            _clipContentChange = true;
            _verticalScrollPolicyChange = true;
            _horizontalScrollPolicyChange = true;
            _contentPaneSizeChange = true;
            _sizeChange = true;
            invalidateDisplayList();
            addEventListener(UIEvent.RESIZE, onResize);
            addEventListener(MouseEvent.MOUSE_WHEEL, mouseWheelHandler);
            addEventListener(MouseEvent.MOUSE_OVER, scrollOver);
            addEventListener(MouseEvent.MOUSE_OUT, scrollOut);
            return;
        }// end function

        public function set vBarWidth(param1:int) : void
        {
            _vBarWidth = param1;
            _vBarWidthChange = true;
            invalidateDisplayList();
            return;
        }// end function

        public function get verticalScrollVisible() : Boolean
        {
            if (verticalScrollBar == null)
            {
                return false;
            }// end if
            return verticalScrollBar.visible;
        }// end function

        private function horizontalScroll(param1:UIEvent) : void
        {
            _contentPane.x = Number(param1.data) * _contentPane.width * -1;
            return;
        }// end function

        public function get horizontalScrollVisible() : Boolean
        {
            if (horizontalScrollBar == null)
            {
                return false;
            }// end if
            return horizontalScrollBar.visible;
        }// end function

        public function get horizontalScrollPolicy() : int
        {
            return _horizontalScrollPolicy;
        }// end function

        override public function get numChildren() : int
        {
            return _contentPane.numChildren;
        }// end function

        public function set clipContent(param1:Boolean) : void
        {
            _clipContent = param1;
            _clipContentChange = true;
            invalidateDisplayList();
            return;
        }// end function

        public function get vScrollPosition() : Number
        {
            if (verticalScrollBar != null)
            {
                return verticalScrollBar.scrollPosition;
            }// end if
            return 0;
        }// end function

        private function resizeScrollBar() : void
        {
            if (verticalScrollPolicy == ScrollPolicy.OFF || _clipContent == false)
            {
                if (verticalScrollBar != null)
                {
                    verticalScrollBar.visible = false;
                }// end if
            }
            else if (verticalScrollPolicy == ScrollPolicy.AUTO && this.height >= _contentPane.height)
            {
                if (verticalScrollBar != null)
                {
                    verticalScrollBar.visible = false;
                }// end if
            }
            else
            {
                if (verticalScrollBar == null)
                {
                    addVerticalScrollBar();
                }// end if
                verticalScrollBar.visible = true;
                verticalScrollBar.width = _vBarWidth;
                verticalScrollBar.x = this.width - verticalScrollBar.width;
                verticalScrollBar.y = 0;
                verticalScrollBar.visibleHeight = this.height;
                verticalScrollBar.pageHeight = _contentPane.height;
            }// end else if
            if (horizontalScrollPolicy == ScrollPolicy.OFF || _clipContent == false)
            {
                if (horizontalScrollBar != null)
                {
                    horizontalScrollBar.visible = false;
                }// end if
            }
            else if (horizontalScrollPolicy == ScrollPolicy.AUTO && this.width >= _contentPane.width)
            {
                if (horizontalScrollBar != null)
                {
                    horizontalScrollBar.visible = false;
                }// end if
            }
            else
            {
                if (horizontalScrollBar == null)
                {
                    addHorizontalScrollBar();
                }// end if
                horizontalScrollBar.visible = true;
                horizontalScrollBar.width = _hBarWidth;
                horizontalScrollBar.x = 0;
                horizontalScrollBar.y = this.height;
                horizontalScrollBar.visibleHeight = this.width;
                horizontalScrollBar.pageHeight = _contentPane.width;
            }// end else if
            if (horizontalScrollBar != null && horizontalScrollBar.visible == true)
            {
                if (verticalScrollBar != null)
                {
                    verticalScrollBar.height = this.height - verticalScrollBar.width;
                }// end if
            }
            else if (verticalScrollBar != null)
            {
                verticalScrollBar.height = this.height;
            }// end else if
            if (verticalScrollBar != null && verticalScrollBar.visible == true)
            {
                if (horizontalScrollBar != null)
                {
                    horizontalScrollBar.height = this.width - horizontalScrollBar.width;
                }// end if
            }
            else if (horizontalScrollBar != null)
            {
                horizontalScrollBar.height = this.width;
            }// end else if
            return;
        }// end function

        public function get clipContent() : Boolean
        {
            return _clipContent;
        }// end function

        public function setHBarWidth(param1:int) : void
        {
            _hBarWidth = param1;
            _vBarWidthChange = true;
            invalidateDisplayList();
            return;
        }// end function

        private function mouseWheelHandler(param1:MouseEvent) : void
        {
            if (verticalScrollBar == null || verticalScrollBar.visible == false)
            {
                return;
            }// end if
            if (param1.delta > 0)
            {
                verticalScrollBar.rollUp();
            }
            else
            {
                verticalScrollBar.rollDown();
            }// end else if
            return;
        }// end function

        override public function getChildAt(param1:int) : DisplayObject
        {
            if (_contentPane == null)
            {
                return null;
            }// end if
            return _contentPane.getChildAt(param1);
        }// end function

        private function scrollOut(param1:MouseEvent) : void
        {
            var e:* = param1;
            try
            {
                ExternalInterface.call("isRoll", true);
            }// end try
            catch (e:Error)
            {
            }// end catch
            return;
        }// end function

        public function set horizontalScrollPolicy(param1:int) : void
        {
            if (param1 > 3 || param1 < 0)
            {
                param1 = 3;
            }// end if
            _horizontalScrollPolicy = param1;
            _horizontalScrollPolicyChange = true;
            invalidateDisplayList();
            return;
        }// end function

        private function scrollOver(param1:MouseEvent) : void
        {
            var e:* = param1;
            try
            {
                ExternalInterface.call("isRoll", false);
            }// end try
            catch (e:Error)
            {
            }// end catch
            return;
        }// end function

        override public function removeChildAt(param1:int) : DisplayObject
        {
            if (_contentPane == null)
            {
                return null;
            }// end if
            var _loc_2:* = _contentPane.removeChildAt(param1);
            _contentPaneSizeChange = true;
            invalidateDisplayList();
            return _loc_2;
        }// end function

        public function set vScrollPosition(param1:Number) : void
        {
            if (verticalScrollBar != null)
            {
                verticalScrollBar.scrollPosition = param1;
            }// end if
            return;
        }// end function

        private function addHorizontalScrollBar() : void
        {
            horizontalScrollBar = new LipiScrollBar();
            horizontalScrollBar.direction = ScrollBarDirection.HORIZONTAL;
            horizontalScrollBar.addEventListener(LipiScrollBar.SCROLL, horizontalScroll);
            horizontalScrollBar.visible = false;
            horizontalScrollBar.container = this;
            super.addChild(horizontalScrollBar);
            _setBarSkin();
            return;
        }// end function

        private function verticalScroll(param1:UIEvent) : void
        {
            _contentPane.y = Number(param1.data) * _contentPane.height * -1;
            return;
        }// end function

        public function set verticalScrollPolicy(param1:int) : void
        {
            if (param1 > 3 || param1 < 0)
            {
                param1 = 3;
            }// end if
            _verticalScrollPolicy = param1;
            _verticalScrollPolicyChange = true;
            invalidateDisplayList();
            return;
        }// end function

        public function setBarSkin(param1:Class, param2:Class, param3:Class, param4:Class, param5:Class = null) : void
        {
            if (_sSkin == null)
            {
                _sSkin = new ScrollBarSkin();
            }// end if
            _sSkin.trackSkin = param1;
            _sSkin.upSkin = param2;
            _sSkin.downSkin = param3;
            _sSkin.barSkin = param4;
            _sSkin.barIcon = param5;
            _setBarSkin();
            return;
        }// end function

        public function resetSize() : void
        {
            _contentPaneSizeChange = true;
            invalidateDisplayList();
            return;
        }// end function

        public function get verticalScrollPolicy() : int
        {
            return _verticalScrollPolicy;
        }// end function

        override protected function updateDisplayList(param1:Number, param2:Number) : void
        {
            var _loc_3:Boolean;
            if (_clipContentChange == true)
            {
                if (_clipContent == true)
                {
                    _mask.visible = true;
                    _contentPane.mask = _mask;
                }
                else
                {
                    _mask.visible = false;
                    _contentPane.mask = null;
                }// end else if
                _loc_3 = true;
            }// end if
            if (_verticalScrollPolicyChange == true)
            {
                _loc_3 = true;
            }
            else if (_horizontalScrollPolicyChange == true)
            {
                _loc_3 = true;
            }
            else if (_contentPaneSizeChange == true)
            {
                _loc_3 = true;
            }// end else if
            if (_sizeChange == true)
            {
                _mask.graphics.clear();
                _mask.graphics.beginFill(0);
                _mask.graphics.drawRect(0, 0, width, height);
                _loc_3 = true;
            }// end if
            if (_vBarWidthChange || _hBarWidthChange)
            {
                _loc_3 = true;
                _vBarWidthChange = false;
                _hBarWidthChange = false;
            }// end if
            if (_loc_3 == true)
            {
                resizeScrollBar();
            }// end if
            _clipContentChange = false;
            _verticalScrollPolicyChange = false;
            _horizontalScrollPolicyChange = false;
            _contentPaneSizeChange = false;
            _sizeChange = false;
            super.updateDisplayList(param1, param2);
            return;
        }// end function

        private function onResize(param1:UIEvent) : void
        {
            _sizeChange = true;
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
            _contentPaneSizeChange = true;
            invalidateDisplayList();
            return param1;
        }// end function

        override public function removeChild(param1:DisplayObject) : DisplayObject
        {
            if (_contentPane == null)
            {
                return null;
            }// end if
            _contentPane.removeChild(param1);
            _contentPaneSizeChange = true;
            invalidateDisplayList();
            return param1;
        }// end function

    }
}
