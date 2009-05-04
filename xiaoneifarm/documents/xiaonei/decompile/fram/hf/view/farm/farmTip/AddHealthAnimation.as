package hf.view.farm.farmTip
{
    import flash.display.*;
    import flash.events.*;
    import flash.utils.*;
    import hf.view.*;
    import hf.view.main.floating.*;

    public class AddHealthAnimation extends FloatingBase
    {
        private var _timer:Timer;
        private var addHealth:MovieClip;

        public function AddHealthAnimation()
        {
            addHealth = MaterialLib.getInstance().getMaterial("AddHealth") as MovieClip;
            addChild(addHealth);
            return;
        }// end function

        override public function start() : void
        {
            addHealth.gotoAndPlay(2);
            if (_timer == null)
            {
                _timer = new Timer(3000, 1);
                _timer.addEventListener(TimerEvent.TIMER_COMPLETE, timerComp);
                _timer.start();
            }// end if
            return;
        }// end function

        private function timerComp(param1:TimerEvent) : void
        {
            _timer.removeEventListener(TimerEvent.TIMER_COMPLETE, timerComp);
            _timer = null;
            Float.close(this);
            return;
        }// end function

    }
}
