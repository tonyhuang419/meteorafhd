package 
{
    import com.minutes.ui.control.*;
    import com.minutes.ui.core.*;
    import flash.events.*;
    import hf.view.*;

    private class winButton extends LipiButton
    {
        private var ac:Function = null;

        private function winButton(param1:String = "Button", param2:String = null, param3:int = 100, param4:int = 25, param5:Function = null, param6:Boolean = true, param7:int = 16777215, param8:int = 12, param9:Boolean = false, param10:String = null, param11:int = 0)
        {
            if (param2 != null)
            {
                bgSkin = new LipiSkin(MaterialLib.getInstance().getClass(param2));
            }// end if
            this.label = param1;
            this.textColor = param7;
            this.textSize = param8;
            this.textBold = param9;
            this.textFont = param10;
            this.bgAlpha = param11;
            this.enable = param6;
            this.width = param3;
            this.height = param4;
            if (param5 == null)
            {
                this.ac = this.empty;
            }
            else
            {
                this.ac = param5;
            }// end else if
            addEventListener(MouseEvent.CLICK, operation);
            return;
        }// end function

        private function empty() : void
        {
            return;
        }// end function

        private function operation(param1:Event) : void
        {
            ac();
            return;
        }// end function

    }
}
