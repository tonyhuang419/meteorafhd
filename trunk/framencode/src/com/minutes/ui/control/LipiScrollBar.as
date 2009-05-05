package com.minutes.ui.control
{
    import com.minutes.ui.constant.*;
    import com.minutes.ui.core.*;
    import flash.display.*;
    import flash.events.*;
    import flash.geom.*;
    import flash.utils.*;

    public class LipiScrollBar extends LipiUIComponent
    {
        private var _container:LipiContainer;
        private var _upButton:LipiUIComponent;
        private var _direction:String;
        private var _downButton:LipiUIComponent;
        private var _trackBarTimer2:Timer;
        private var _heightChange:Boolean = false;
        private var _trackBarTimer1:Timer;
        private var _downSkin:Class;
        private var _trackBar:LipiUIComponent;
        private var _buttonHeightChange:Boolean = false;
        private var _visibleHeightChange:Boolean = false;
        private var _thumbBar:ThumbBar;
        private var _visibleHeight:Number = 0;
        private var _pageHeight:Number = 0;
        private var timer:Timer;
        private var oldHeight:Number;
        private var _pageHeightChange:Boolean = false;
        private var _widthChange:Boolean = false;
        private var _upSkin:Class;
        private var oldWidth:Number;
        private var _trackSkin:Class;
        private var _barIcon:Class;
        private var _barSkin:Class;
        private var _thumbIcon:Sprite;
        private var _buttonHeight:Number = 12;
        public static const SCROLL:String = "scrollEvent";

        public function LipiScrollBar()
        {
            timer = new Timer(250);
            _direction = ScrollBarDirection.VERTICAL;
            init();
            return;
        }// end function

        public function get buttonHeight() : Number
        {
            return _buttonHeight;
        }// end function

        public function set visibleHeight(param1:Number) : void
        {
            _visibleHeight = param1;
            _visibleHeightChange = true;
            invalidateDisplayList();
            return;
        }// end function

        private function thumbBarMouseUp(param1:MouseEvent) : void
        {
            _thumbBar.stopDrag();
            stage.removeEventListener(MouseEvent.MOUSE_UP, thumbBarMouseUp);
            removeEventListener(Event.ENTER_FRAME, thumbBarEnterFrame);
            return;
        }// end function

        public function set direction(param1:String) : void
        {
            _direction = param1;
            if (param1 == ScrollBarDirection.VERTICAL)
            {
                rotation = 0;
            }
            else
            {
                rotation = 270;
            }// end else if
            return;
        }// end function

        override protected function updateDisplayList(param1:Number, param2:Number) : void
        {
            super.updateDisplayList(param1, param2);
            if (_widthChange)
            {
                _downButton.width = this.width;
                _upButton.width = this.width;
                _trackBar.width = this.width;
                _thumbBar.width = this.width;
            }// end if
            if (_heightChange)
            {
                _downButton.y = this.height - _downButton.height;
                _trackBar.height = this.height;
            }// end if
            if (_buttonHeightChange)
            {
                _upButton.height = _buttonHeight;
                _downButton.height = _buttonHeight;
            }// end if
            if (_pageHeightChange || _visibleHeightChange)
            {
                if (pageHeight != 0)
                {
                    if (pageHeight < visibleHeight)
                    {
                        pageHeight = visibleHeight;
                    }// end if
                    _thumbBar.height = visibleHeight / pageHeight * (this.height - _upButton.height - _downButton.height);
                }// end if
            }// end if
            _pageHeightChange = true;
            _visibleHeightChange = true;
            _buttonHeightChange = true;
            _widthChange = true;
            _heightChange = true;
            return;
        }// end function

        public function rollUp() : void
        {
            upButtonClick();
            return;
        }// end function

        public function get container() : LipiContainer
        {
            return _container;
        }// end function

        public function set container(param1:LipiContainer) : void
        {
            _container = param1;
            this.height = param1.height;
            return;
        }// end function

        private function trackBarClickHandler(param1:MouseEvent) : void
        {
            trackBarMouseHandler();
            return;
        }// end function

        public function get direction() : String
        {
            return _direction;
        }// end function

        private function init() : void
        {
            timer.start();
            oldWidth = this.width;
            oldHeight = this.height;
            this.bgAlpha = 0;
            this.width = 17;
            addEventListener(UIEvent.RESIZE, resizeHandler);
            _barSkin = LipiScrollBarSkin.BarSkin;
            _barIcon = LipiScrollBarSkin.BarIcon;
            _upSkin = LipiScrollBarSkin.UpSkin;
            _downSkin = LipiScrollBarSkin.DownSkin;
            _trackSkin = LipiScrollBarSkin.TrackSkin;
            createTrackBar();
            createUpButton();
            createDownButton();
            createThumbBar();
            updateSkin();
            return;
        }// end function

        private function thumbBarMouseDown(param1:MouseEvent) : void
        {
            var _loc_2:* = Math.ceil(this.height - _thumbBar.height - _downButton.height - _upButton.height);
            _thumbBar.startDrag(false, new Rectangle(0, _upButton.height, 0, _loc_2));
            stage.addEventListener(MouseEvent.MOUSE_UP, thumbBarMouseUp);
            addEventListener(Event.ENTER_FRAME, thumbBarEnterFrame);
            return;
        }// end function

        private function createDownButton() : void
        {
            _downButton = new LipiUIComponent();
            _downButton.addEventListener(MouseEvent.CLICK, downButtonClick);
            _downButton.addEventListener(MouseEvent.MOUSE_DOWN, downButtonMouseDown);
            _downButton.width = this.width;
            _downButton.height = buttonHeight;
            _downButton.x = 0;
            _downButton.y = this.height - _downButton.height;
            addChild(_downButton);
            return;
        }// end function

        private function downButtonMouseUp(param1:Event) : void
        {
            timer.removeEventListener(TimerEvent.TIMER, downButtonTimer);
            return;
        }// end function

        public function get scrollPosition() : Number
        {
            return _thumbBar.y - _upButton.height;
        }// end function

        private function upButtonTimer(param1:TimerEvent) : void
        {
            upButtonClick();
            return;
        }// end function

        public function get pageHeight() : Number
        {
            return _pageHeight;
        }// end function

        public function set buttonHeight(param1:Number) : void
        {
            _buttonHeight = param1;
            _buttonHeightChange = true;
            invalidateDisplayList();
            return;
        }// end function

        public function enforceScrollPosition(param1:Number) : void
        {
            var _loc_2:* = param1 * (this.height - _upButton.height - _downButton.height);
            _thumbBar.y = _loc_2 + _upButton.height;
            return;
        }// end function

        private function downButtonTimer(param1:TimerEvent) : void
        {
            downButtonClick();
            return;
        }// end function

        private function thumbBarEnterFrame(param1:Event) : void
        {
            scrollPosition = scrollPosition;
            return;
        }// end function

        public function get visibleHeight() : Number
        {
            return _visibleHeight;
        }// end function

        private function upButtonMouseDown(param1:MouseEvent) : void
        {
            stage.addEventListener(MouseEvent.MOUSE_UP, upButtonMouseUp);
            timer.addEventListener(TimerEvent.TIMER, upButtonTimer);
            return;
        }// end function

        public function set scrollPosition(param1:Number) : void
        {
            _thumbBar.y = param1 + _upButton.height;
            var _loc_2:* = new UIEvent(LipiScrollBar.SCROLL);
            _loc_2.data = param1 / (this.height - _upButton.height - _downButton.height);
            dispatchEvent(_loc_2);
            return;
        }// end function

        private function trackBarMouse(param1:Event = null) : void
        {
            _trackBarTimer2 = new Timer(150);
            _trackBarTimer2.addEventListener(TimerEvent.TIMER, trackBarMouseHandler);
            _trackBarTimer2.start();
            return;
        }// end function

        public function rollDown() : void
        {
            downButtonClick();
            return;
        }// end function

        private function upButtonMouseUp(param1:Event) : void
        {
            timer.removeEventListener(TimerEvent.TIMER, upButtonTimer);
            return;
        }// end function

        private function createUpButton() : void
        {
            _upButton = new LipiUIComponent();
            _upButton.addEventListener(MouseEvent.CLICK, upButtonClick);
            _upButton.addEventListener(MouseEvent.MOUSE_DOWN, upButtonMouseDown);
            _upButton.width = this.width;
            _upButton.height = buttonHeight;
            _upButton.x = 0;
            _upButton.y = 0;
            addChild(_upButton);
            return;
        }// end function

        private function trackBarMouseHandler(param1:Event = null) : void
        {
            var _loc_2:* = mouseY;
            if (_loc_2 > _thumbBar.y && _loc_2 < _thumbBar.y + _thumbBar.height)
            {
                return;
            }// end if
            if (_loc_2 < _thumbBar.y)
            {
                if (scrollPosition - _thumbBar.height > 0)
                {
                    scrollPosition = scrollPosition - _thumbBar.height;
                }
                else
                {
                    scrollPosition = 0;
                }// end else if
            }
            else if (scrollPosition + _thumbBar.height > this.height - _downButton.height - _thumbBar.height - _upButton.height)
            {
                scrollPosition = this.height - _downButton.height - _thumbBar.height - _upButton.height;
            }
            else
            {
                scrollPosition = scrollPosition + _thumbBar.height;
            }// end else if
            return;
        }// end function

        public function set trackSkin(param1:Class) : void
        {
            _trackSkin = param1;
            return;
        }// end function

        private function downButtonMouseDown(param1:MouseEvent) : void
        {
            stage.addEventListener(MouseEvent.MOUSE_UP, downButtonMouseUp);
            timer.addEventListener(TimerEvent.TIMER, downButtonTimer);
            return;
        }// end function

        public function set barIcon(param1:Class) : void
        {
            _barIcon = param1;
            return;
        }// end function

        public function set pageHeight(param1:Number) : void
        {
            _pageHeight = param1;
            _pageHeightChange = true;
            invalidateDisplayList();
            return;
        }// end function

        private function upButtonClick(param1:MouseEvent = null) : void
        {
            if (_thumbBar.y - _upButton.height - _thumbBar.height / 10 > 0)
            {
                scrollPosition = scrollPosition - _thumbBar.height / 10;
            }
            else
            {
                scrollPosition = 0;
            }// end else if
            return;
        }// end function

        private function createTrackBar() : void
        {
            _trackBar = new LipiUIComponent();
            _trackBar.addEventListener(MouseEvent.CLICK, trackBarClickHandler);
            _trackBar.addEventListener(MouseEvent.MOUSE_DOWN, trackBarMouseDown);
            _trackBar.width = this.width;
            _trackBar.height = this.height;
            _trackBar.x = 0;
            _trackBar.y = 0;
            addChild(_trackBar);
            return;
        }// end function

        public function updateSkin() : void
        {
            if (_trackSkin == null)
            {
                _trackBar.bgColor = 15658734;
                _trackBar.bgAlpha = 0.5;
            }
            else
            {
                _trackBar.bgAlpha = 0;
                _trackBar.bgSkin = new LipiSkin(_trackSkin);
            }// end else if
            if (_upSkin == null)
            {
                _upButton.bgColor = 10027008;
            }
            else
            {
                _upButton.bgAlpha = 0;
                _upButton.bgSkin = new LipiSkin(_upSkin);
            }// end else if
            if (_downSkin == null)
            {
                _downButton.bgColor = 10027008;
            }
            else
            {
                _downButton.bgAlpha = 0;
                _downButton.bgSkin = new LipiSkin(_downSkin);
            }// end else if
            if (_barSkin == null)
            {
                _thumbBar.bgColor = 16711680;
            }
            else
            {
                _thumbBar.bgAlpha = 0;
                _thumbBar.bgSkin = new LipiSkin(_barSkin);
            }// end else if
            if (_barIcon != null)
            {
                _thumbBar.iconSkin = _barIcon;
            }// end if
            return;
        }// end function

        public function set barSkin(param1:Class) : void
        {
            _barSkin = param1;
            return;
        }// end function

        public function set upSkin(param1:Class) : void
        {
            _upSkin = param1;
            return;
        }// end function

        private function trackBarMouseUpHandler(param1:MouseEvent) : void
        {
            stage.removeEventListener(MouseEvent.MOUSE_UP, trackBarMouseUpHandler);
            if (_trackBarTimer1 != null)
            {
                _trackBarTimer1.removeEventListener(TimerEvent.TIMER, trackBarMouse);
                _trackBarTimer1.stop();
                _trackBarTimer1 = null;
            }// end if
            if (_trackBarTimer2 != null)
            {
                _trackBarTimer2.removeEventListener(TimerEvent.TIMER, trackBarMouseHandler);
                _trackBarTimer2.stop();
                _trackBarTimer2 = null;
            }// end if
            return;
        }// end function

        private function downButtonClick(param1:MouseEvent = null) : void
        {
            if (_thumbBar.y + _thumbBar.height / 10 < this.height - _downButton.height - _thumbBar.height)
            {
                scrollPosition = scrollPosition + _thumbBar.height / 10;
            }
            else
            {
                scrollPosition = this.height - _downButton.height - _thumbBar.height - _upButton.height;
            }// end else if
            return;
        }// end function

        private function resizeHandler(param1:UIEvent) : void
        {
            if (this.height != oldHeight)
            {
                _heightChange = true;
                oldHeight = this.height;
                invalidateDisplayList();
            }// end if
            if (this.width != oldWidth)
            {
                _widthChange = true;
                oldWidth = this.width;
                invalidateDisplayList();
            }// end if
            return;
        }// end function

        private function createThumbBar() : void
        {
            _thumbBar = new ThumbBar();
            _thumbBar.addEventListener(MouseEvent.MOUSE_DOWN, thumbBarMouseDown);
            _thumbBar.width = this.width;
            _thumbBar.height = this.height - _upButton.height - _downButton.height;
            _thumbBar.x = 0;
            _thumbBar.y = _upButton.height;
            addChild(_thumbBar);
            return;
        }// end function

        public function set downSkin(param1:Class) : void
        {
            _downSkin = param1;
            return;
        }// end function

        private function trackBarMouseDown(param1:MouseEvent) : void
        {
            _trackBarTimer1 = new Timer(300, 1);
            _trackBarTimer1.addEventListener(TimerEvent.TIMER, trackBarMouse);
            _trackBarTimer1.start();
            stage.addEventListener(MouseEvent.MOUSE_UP, trackBarMouseUpHandler);
            return;
        }// end function

    }
}
