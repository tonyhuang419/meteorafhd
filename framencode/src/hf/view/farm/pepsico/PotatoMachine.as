package hf.view.farm.pepsico
{
    import flash.display.*;
    import flash.events.*;
    import hf.model.*;
    import hf.view.*;
    import hf.view.main.WindowControl.*;

    public class PotatoMachine extends Sprite
    {
        private var potatoMachine:MovieClip;
        private var makeChipsNumber:int = 0;
        private var mainData:MainData;

        public function PotatoMachine()
        {
            addMachine();
            mainData = MData.getInstance().mainData;
            mainData.addEventListener(MainData.POTATO_MACHINE_RUNNING, machineControl);
            mainData.addEventListener(MainData.POTATO_MAKE_DATA, makeEndDataLoaded);
            return;
        }// end function

        private function startMachine() : void
        {
            potatoMachine.gotoAndPlay(2);
            potatoMachine.addEventListener(Event.ENTER_FRAME, ifEndFrame);
            return;
        }// end function

        private function stopMachine() : void
        {
            potatoMachine.gotoAndStop(1);
            return;
        }// end function

        private function addMachine() : void
        {
            potatoMachine = MaterialLib.getInstance().getMaterial("PotatoMachine") as MovieClip;
            potatoMachine.name = "potatoMachine";
            addChild(potatoMachine);
            return;
        }// end function

        private function ifEndFrame(param1:Event) : void
        {
            if (potatoMachine.currentLabel == "end" || !mainData.me)
            {
                MData.getInstance().mainData.potatoMachineRuning = false;
                potatoMachine.removeEventListener(Event.ENTER_FRAME, ifEndFrame);
                stopMachine();
            }// end if
            return;
        }// end function

        private function machineControl(param1:Event) : void
        {
            if (mainData.potatoMachineRuning)
            {
                startMachine();
            }
            else
            {
                stopMachine();
            }// end else if
            return;
        }// end function

        private function alertResult() : void
        {
            if (mainData.me)
            {
                WControl.openForName("AlertWindow", {type:"success", text:mainData.potatoChips});
            }// end if
            return;
        }// end function

        private function makeEndDataLoaded(param1:Event) : void
        {
            alertResult();
            return;
        }// end function

    }
}
