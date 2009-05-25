package hf.view.main.window.facebook.pay
{
    import flash.display.*;
    import flash.events.*;
    import hf.control.*;
    import hf.view.*;
    import hf.view.main.WindowControl.*;

    public class FaceBookPayLayer extends Sprite
    {

        public function FaceBookPayLayer()
        {
            var _loc_1:SimpleButton;
            if (Version.value == Version.FACEBOOK)
            {
                _loc_1 = MaterialLib.getInstance().getMaterial("AddCoins") as SimpleButton;
                _loc_1.addEventListener(MouseEvent.CLICK, openPayWin);
                addChild(_loc_1);
            }// end if
            return;
        }// end function

        private function openPayWin(param1:Event) : void
        {
            var _loc_2:* = new FacebookPayWin();
            WControl.open(_loc_2);
            return;
        }// end function

    }
}
