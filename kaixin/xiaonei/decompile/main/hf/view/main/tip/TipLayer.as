package hf.view.main.tip
{
    import flash.display.*;
    import flash.events.*;
    import flash.utils.*;
    import hf.control.*;

    public class TipLayer extends Sprite
    {
        private var waitArg:Object;
        private var waitTip:String = "";
        private var waitTime:int = 0;
        private var tipInstance:Object;
        private var waitTX:int = 0;
        private var waitTY:int = 0;
        private var tip:Tip;

        public function TipLayer()
        {
            addEventListener(Event.ADDED_TO_STAGE, addedToStage);
            return;
        }// end function

        private function addedToStage(param1:Event) : void
        {
            removeEventListener(Event.ADDED_TO_STAGE, addedToStage);
            init();
            return;
        }// end function

        private function timerHandler(param1:TimerEvent) : void
        {
            if (waitTip != "")
            {
                if (getTimer() - waitTime > 500)
                {
                    tip = getTip(waitTip);
                    if (tip != null)
                    {
                        tip.data = waitArg;
                        tip.tX = waitTX;
                        tip.tY = waitTY;
                        addChild(tip);
                        tip.mX = mouseX;
                        tip.mY = mouseY;
                    }// end if
                    waitTip = "";
                }// end if
            }// end if
            return;
        }// end function

        private function stageMove(param1:MouseEvent) : void
        {
            if (tip != null)
            {
                tip.mX = param1.stageX;
                tip.mY = param1.stageY;
                param1.updateAfterEvent();
            }// end if
            return;
        }// end function

        private function getTip(param1:String) : Tip
        {
            var _loc_2:Tip;
            if (param1 == "")
            {
                return null;
            }// end if
            if (tipInstance == null || !tipInstance.hasOwnProperty(param1))
            {
                _loc_2 = new TipClassLib.getClass(param1) as Class as Tip;
                if (tipInstance == null)
                {
                    tipInstance = new Object();
                }// end if
                tipInstance[param1] = _loc_2;
            }
            else
            {
                _loc_2 = tipInstance[param1];
            }// end else if
            return _loc_2;
        }// end function

        private function init() : void
        {
            ViewControl.getInstance().addEventListener(TipEvent.TIP_SHOW, tipShow, false, 0, true);
            ViewControl.getInstance().addEventListener(TipEvent.TIP_HIDE, tipHide, false, 0, true);
            stage.addEventListener(MouseEvent.MOUSE_MOVE, stageMove, false, 0, true);
            var _loc_1:* = new Timer(100);
            _loc_1.addEventListener(TimerEvent.TIMER, timerHandler);
            _loc_1.start();
            return;
        }// end function

        private function tipShow(param1:TipEvent) : void
        {
            if (tip != null)
            {
                removeChild(tip);
                tip = null;
            }// end if
            waitTip = param1.tipType;
            waitArg = param1.tipArgument;
            waitTX = param1.tX;
            waitTY = param1.tY;
            waitTime = getTimer();
            return;
        }// end function

        private function tipHide(param1:TipEvent) : void
        {
            if (tip != null)
            {
                removeChild(tip);
                tip = null;
            }// end if
            if (waitTip != "")
            {
                waitTip = "";
            }// end if
            return;
        }// end function

    }
}
