package hf.view.farm.farmTip
{
    import flash.display.*;
    import flash.events.*;
    import flash.text.*;
    import flash.utils.*;
    import hf.view.*;
    import hf.view.common.*;
    import hf.view.main.floating.*;

    public class FarmOperateFloating extends FloatingBase
    {
        private var _sTime:int;
        private var _timer:Timer;
        private var textField:TextField;

        public function FarmOperateFloating(param1:Boolean = true)
        {
            var _loc_2:Sprite;
            if (param1)
            {
                _loc_2 = MaterialLib.getInstance().getMaterial("TalkBoxMy1") as Sprite;
            }
            else
            {
                _loc_2 = MaterialLib.getInstance().getMaterial("TalkBoxHe1") as Sprite;
            }// end else if
            addChild(_loc_2);
            x = 100;
            y = 100;
            textField = new TextField();
            textField.wordWrap = true;
            if (param1)
            {
                textField.defaultTextFormat = new TextFormat("Tahoma", 12, 3355443);
            }
            else
            {
                textField.defaultTextFormat = new TextFormat("Tahoma", 12, 16777215);
            }// end else if
            if (Language.lang == Language.CN)
            {
                textField.width = 90;
                textField.height = 46;
                textField.x = -58;
                textField.y = -56;
                addChild(textField);
            }
            else
            {
                textField.width = 100;
                textField.height = 46;
                textField.x = -58;
                textField.y = -61;
                addChild(textField);
            }// end else if
            return;
        }// end function

        public function get text() : String
        {
            return textField.text;
        }// end function

        private function onTimer(param1:TimerEvent) : void
        {
            if (getTimer() - _sTime > 2500)
            {
                _timer.stop();
                _timer.removeEventListener(TimerEvent.TIMER, onTimer);
                _timer = null;
                Float.close(this);
            }// end if
            return;
        }// end function

        public function set text(param1:String) : void
        {
            textField.text = param1;
            return;
        }// end function

        override public function start() : void
        {
            if (_timer == null)
            {
                _timer = new Timer(100);
                _timer.addEventListener(TimerEvent.TIMER, onTimer);
                _timer.start();
                _sTime = getTimer();
            }// end if
            return;
        }// end function

    }
}
