package com.minutes.ui.control
{
    import com.minutes.ui.core.*;
    import flash.display.*;
    import flash.events.*;
    import flash.text.*;
    import flash.utils.*;

    public class NumbericStepper extends Sprite
    {
        private var timer:Timer;
        private var bg:LipiUIComponent;
        private var downTime:int = 0;
        private var numText:TextField;
        private var addValue:int = 0;
        private var leftButton:LipiUIComponent;
        private var _min_num:int = 1;
        private var _max_num:int = 99;
        private var rightButton:LipiUIComponent;

        public function NumbericStepper()
        {
            init();
            return;
        }// end function

        public function set get_num(param1:int) : void
        {
            if (param1 > max_num)
            {
                numText.text = max_num.toString();
            }
            else if (param1 < min_num)
            {
                numText.text = min_num.toString();
            }
            else
            {
                numText.text = param1.toString();
            }// end else if
            dispatchEvent(new UIEvent(UIEvent.TEXT_CHANGE));
            return;
        }// end function

        public function get max_num() : int
        {
            return _max_num;
        }// end function

        private function init() : void
        {
            leftButton = new LipiUIComponent();
            rightButton = new LipiUIComponent();
            bg = new LipiUIComponent();
            if (NUmbericStepperSkin.DecSkin != null)
            {
                leftButton.bgSkin = new LipiSkin(NUmbericStepperSkin.DecSkin);
                leftButton.addEventListener(MouseEvent.CLICK, leftButtonClick);
                leftButton.addEventListener(MouseEvent.MOUSE_DOWN, leftButtonDown);
                leftButton.addEventListener(MouseEvent.MOUSE_UP, leftButtonUp);
                leftButton.addEventListener(MouseEvent.ROLL_OUT, leftButtonUp);
                leftButton.bgAlpha = 0;
                leftButton.width = 20;
                leftButton.height = 20;
                leftButton.y = 1;
            }// end if
            if (NUmbericStepperSkin.IncSkin != null)
            {
                rightButton.bgSkin = new LipiSkin(NUmbericStepperSkin.IncSkin);
                rightButton.addEventListener(MouseEvent.CLICK, rightButtonClick);
                rightButton.addEventListener(MouseEvent.MOUSE_DOWN, rightButtonDown);
                rightButton.addEventListener(MouseEvent.MOUSE_UP, rightButtonUp);
                rightButton.addEventListener(MouseEvent.ROLL_OUT, rightButtonUp);
                rightButton.bgAlpha = 0;
                rightButton.width = 20;
                rightButton.height = 20;
                rightButton.y = 1;
            }// end if
            if (NUmbericStepperSkin.BgSkin != null)
            {
                bg.bgSkin = new LipiSkin(NUmbericStepperSkin.BgSkin);
                bg.bgAlpha = 0;
                bg.width = 50;
                bg.height = 22;
            }// end if
            addChild(leftButton);
            addChild(rightButton);
            addChild(bg);
            leftButton.x = 0;
            bg.x = 20;
            rightButton.x = 70;
            numText = new TextField();
            numText.restrict = "0-9";
            numText.type = TextFieldType.INPUT;
            numText.defaultTextFormat = new TextFormat("Tahoma", 12, 3355443, null, null, null, null, null, TextFormatAlign.CENTER);
            numText.text = "1";
            numText.width = bg.width;
            numText.height = bg.width;
            numText.x = bg.x;
            numText.y = 2;
            numText.addEventListener(Event.CHANGE, numTextChange);
            numText.addEventListener(MouseEvent.MOUSE_WHEEL, textWheel);
            numText.addEventListener(FocusEvent.FOCUS_OUT, focusOut);
            numText.addEventListener(FocusEvent.FOCUS_IN, foncusIn);
            addChild(numText);
            timer = new Timer(60);
            timer.addEventListener(TimerEvent.TIMER, onTimer);
            return;
        }// end function

        private function leftButtonUp(param1:MouseEvent) : void
        {
            downTime = 0;
            addValue = 0;
            timer.stop();
            return;
        }// end function

        private function focusOut(param1:FocusEvent) : void
        {
            if (get_num < min_num)
            {
                get_num = min_num;
            }// end if
            return;
        }// end function

        private function leftButtonDown(param1:MouseEvent) : void
        {
            addValue = -1;
            downTime = getTimer();
            timer.start();
            return;
        }// end function

        public function get get_num() : int
        {
            return int(numText.text);
        }// end function

        private function foncusIn(param1:FocusEvent) : void
        {
            var _loc_2:* = new Timer(10, 1);
            _loc_2.addEventListener(TimerEvent.TIMER, allSelection);
            _loc_2.start();
            return;
        }// end function

        private function numTextChange(param1:Event) : void
        {
            if (get_num > max_num)
            {
                get_num = max_num;
            }// end if
            dispatchEvent(new UIEvent(UIEvent.TEXT_CHANGE));
            return;
        }// end function

        public function set max_num(param1:int) : void
        {
            _max_num = param1;
            return;
        }// end function

        public function get min_num() : int
        {
            return _min_num;
        }// end function

        private function rightButtonUp(param1:MouseEvent) : void
        {
            downTime = 0;
            addValue = 0;
            timer.stop();
            return;
        }// end function

        private function allSelection(param1:Event) : void
        {
            if (stage != null)
            {
                stage.focus = numText;
            }// end if
            numText.setSelection(0, numText.text.length);
            return;
        }// end function

        private function onTimer(param1:TimerEvent) : void
        {
            if (downTime == 0)
            {
                return;
            }// end if
            if (getTimer() - 500 > downTime)
            {
                get_num = get_num + addValue;
            }// end if
            return;
        }// end function

        private function leftButtonClick(param1:MouseEvent) : void
        {
            get_num--;
            return;
        }// end function

        private function rightButtonDown(param1:MouseEvent) : void
        {
            addValue = 1;
            downTime = getTimer();
            timer.start();
            return;
        }// end function

        private function textWheel(param1:MouseEvent) : void
        {
            if (param1.delta > 0)
            {
                get_num = get_num + 1;
            }
            else
            {
                get_num--;
            }// end else if
            return;
        }// end function

        public function set min_num(param1:int) : void
        {
            _min_num = param1;
            return;
        }// end function

        private function rightButtonClick(param1:MouseEvent) : void
        {
            get_num = get_num + 1;
            return;
        }// end function

    }
}
