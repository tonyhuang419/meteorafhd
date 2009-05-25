package com.minutes.ui.vectorCartoon
{
    import com.minutes.ui.vectorCartoon.cartoonEvent.*;
    import com.minutes.ui.vectorCartoon.cartoonFrameRate.*;
    import flash.display.*;
    import flash.events.*;
    import flash.geom.*;

    public class VectorCartoon extends Bitmap implements Cartoon
    {
        private var _rect:Rectangle;
        private var _timer:CartoonFrameRate;
        private var _point:Point;
        private var _buffer:BitmapData;
        private var _isPlaying:Boolean;
        private var _frameAmount:int;
        private var _frame:int;

        public function VectorCartoon(param1:BitmapData, param2:int, param3:int, param4:CartoonFrameRate, param5:Boolean = true, param6:String = "auto", param7:Boolean = false)
        {
            super(new BitmapData(param2, param3, param5, 0), param6, param7);
            _buffer = param1.clone();
            _frameAmount = _buffer.width / width;
            _frame = 1;
            _isPlaying = false;
            _timer = param4;
            _point = new Point(0, 0);
            _rect = new Rectangle(0, 0, width, height);
            bitmapData.copyPixels(_buffer, _rect, _point);
            return;
        }// end function

        public function stop() : void
        {
            if (_isPlaying)
            {
                _timer.stop();
                _timer.removeEventListener(TimerEvent.TIMER, playForward);
                _isPlaying = false;
            }// end if
            return;
        }// end function

        private function draw() : void
        {
            dispatchEvent(new EnterFrameEvent(EnterFrameEvent.VECTOR_CARTOON_ENTER));
            _rect = new Rectangle(_frame-- * width, 0, width, height);
            bitmapData.copyPixels(_buffer, _rect, _point);
            return;
        }// end function

        public function gotoAndPlay(param1:int) : void
        {
            _frame = param1--;
            play();
            return;
        }// end function

        public function prevFrame() : void
        {
            if (_isPlaying)
            {
                stop();
            }// end if
            _frame--;
            if (_frame < 1)
            {
                _frame = 1;
            }// end if
            draw();
            return;
        }// end function

        public function getTotalFrames() : int
        {
            return _frameAmount;
        }// end function

        public function getCartoonFrameRate() : int
        {
            return _timer.getFrameRate();
        }// end function

        public function gotoAndStop(param1:int) : void
        {
            if (param1 >= _frame)
            {
                _frame = param1--;
                nextFrame();
            }
            else
            {
                _frame = param1 + 1;
                prevFrame();
            }// end else if
            return;
        }// end function

        public function setCartoonFrameRate(param1:CartoonFrameRate) : void
        {
            if (_isPlaying)
            {
                stop();
                _timer = param1;
                play();
            }
            else
            {
                _timer = param1;
            }// end else if
            return;
        }// end function

        public function isPlaying() : Boolean
        {
            return _isPlaying;
        }// end function

        public function getCurrentFrame() : int
        {
            return _frame;
        }// end function

        public function play() : void
        {
            if (!_isPlaying)
            {
                _isPlaying = true;
                _timer.addEventListener(TimerEvent.TIMER, playForward);
                _timer.start();
            }
            else
            {
                stop();
                play();
            }// end else if
            return;
        }// end function

        private function playForward(param1:TimerEvent = null) : void
        {
            _frame++;
            if (_frame > _frameAmount)
            {
                _frame = 1;
            }
            else if (_frame < 1)
            {
                _frame = _frameAmount;
            }// end else if
            draw();
            return;
        }// end function

        public function nextFrame() : void
        {
            if (_isPlaying)
            {
                stop();
            }// end if
            _frame++;
            if (_frame > _frameAmount)
            {
                _frame = _frameAmount;
            }// end if
            draw();
            return;
        }// end function

    }
}
