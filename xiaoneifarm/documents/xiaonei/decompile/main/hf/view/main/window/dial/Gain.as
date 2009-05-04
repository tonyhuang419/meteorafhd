package hf.view.main.window.dial
{
    import hf.view.common.*;

    public class Gain extends AddButtonWindow
    {

        public function Gain()
        {
            width = 500;
            height = 360;
            title = Language.L["gainTitle"];
            windowName = "gain";
            mode = true;
            return;
        }// end function

        override public function init() : void
        {
            return;
        }// end function

    }
}
