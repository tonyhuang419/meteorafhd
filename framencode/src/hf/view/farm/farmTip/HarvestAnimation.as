package hf.view.farm.farmTip
{
    import flash.events.*;
    import flash.text.*;
    import flash.utils.*;
    import hf.view.common.*;
    import hf.view.main.floating.*;

    public class HarvestAnimation extends FloatingBase
    {
        private var _sTime:int;
        private var _timer:Timer;
        private var materialProxy:MaterialProxy;
        private var _text:String = "";
        private var numText:TextField;
        private var easingY:Number = 0.2;
        private var currentValue:Number = 29;

        public function HarvestAnimation()
        {
            materialProxy = new MaterialProxy();
            addChild(materialProxy);
            numText = new TextField();
            numText.defaultTextFormat = new TextFormat("fontForte", 22, 16737792);
            numText.embedFonts = true;
            numText.autoSize = TextFieldAutoSize.LEFT;
            addChild(numText);
            return;
        }// end function

        public function set text(param1:String) : void
        {
            _text = param1;
            numText.text = "X " + param1;
            numText.x = 65;
            numText.y = (60 - numText.height) / 2;
            return;
        }// end function

        override public function start() : void
        {
            if (_timer == null)
            {
                _timer = new Timer(40);
                _timer.addEventListener(TimerEvent.TIMER, onTimer);
                _timer.start();
                _sTime = getTimer();
            }// end if
            return;
        }// end function

        public function get text() : String
        {
            return _text;
        }// end function

        public function setContent(param1:String, param2:int) : void
        {
            materialProxy.setContent(param1, param2);
            cacheAsBitmap = true;
            return;
        }// end function

        private function onTimer(param1:TimerEvent) : void
        {
            currentValue = currentValue - (30 - currentValue) * easingY;
            this.y = this.y - (30 - currentValue);
            if (currentValue < 0)
            {
                _timer.stop();
                _timer.removeEventListener(TimerEvent.TIMER, onTimer);
                _timer = null;
                Float.close(this);
            }// end if
            return;
        }// end function

    }
}
