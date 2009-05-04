package hf.view.farm.pepsico
{
    import flash.display.*;
    import flash.events.*;
    import hf.control.*;
    import hf.model.*;
    import hf.view.main.WindowControl.*;
    import hf.view.main.cursor.*;

    public class PotatoMachineLayer extends Sprite
    {
        private var potatoMachine:PotatoMachine;
        private var mainData:MainData;

        public function PotatoMachineLayer()
        {
            mainData = MData.getInstance().mainData;
            if (mainData.potatoMachine)
            {
                initPotatoMachine(null);
            }// end if
            mainData.addEventListener(MainData.POTATO_MACHINE, initPotatoMachine);
            mainData.addEventListener(MainData.POTATO_CHIPS_OPEN, winMakePotatoChips);
            addEventListener(MouseEvent.ROLL_OVER, hand);
            addEventListener(MouseEvent.ROLL_OUT, movhand);
            buttonMode = true;
            mouseEnabled = true;
            return;
        }// end function

        private function openAward(param1:ModelEvent) : void
        {
            WControl.open(new LayersReward(param1.data));
            return;
        }// end function

        private function openOther(param1:MouseEvent) : void
        {
            var _loc_2:* = new OtherMachine();
            WControl.open(_loc_2);
            return;
        }// end function

        private function initPotatoMachine(param1:Event) : void
        {
            if (mainData.potatoMachine)
            {
                if (this.getChildByName("potatoMachine") == null)
                {
                    potatoMachine = new PotatoMachine();
                    potatoMachine.name = "potatoMachine";
                    potatoMachine.x = 740;
                    potatoMachine.y = 140;
                    potatoMachine.scaleX = 0.6;
                    potatoMachine.scaleY = 0.6;
                    addChild(potatoMachine);
                }// end if
                if (this.getChildByName("potatoMachine") != null)
                {
                    if (mainData.me)
                    {
                        potatoMachine.removeEventListener(MouseEvent.CLICK, openOther);
                        potatoMachine.addEventListener(MouseEvent.CLICK, openMakePotatoChipsWindow);
                        mainData.addEventListener(MainData.POTATO_AWARD, openAward);
                    }
                    else
                    {
                        potatoMachine.addEventListener(MouseEvent.CLICK, openOther);
                        potatoMachine.removeEventListener(MouseEvent.CLICK, openMakePotatoChipsWindow);
                        mainData.removeEventListener(MainData.POTATO_AWARD, openAward);
                    }// end if
                }// end else if
            }
            else if (this.getChildByName("potatoMachine") != null)
            {
                removeChild(this.getChildByName("potatoMachine"));
            }// end else if
            return;
        }// end function

        private function openMakePotatoChipsWindow(param1:Event) : void
        {
            Command.getInstance().mainCommand.getPotatoChipsRequest();
            return;
        }// end function

        private function movhand(param1:MouseEvent) : void
        {
            Cursor.useSystem(false);
            return;
        }// end function

        private function winMakePotatoChips(param1:Event) : void
        {
            var _loc_2:* = new MakePotatoChips();
            WControl.open(_loc_2);
            return;
        }// end function

        private function hand(param1:MouseEvent) : void
        {
            Cursor.useSystem(true);
            return;
        }// end function

    }
}
