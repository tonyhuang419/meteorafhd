package hf.view.main.window.pay
{
    import flash.display.*;
    import hf.control.*;
    import hf.view.main.window.facebook.pay.*;
    import hf.view.main.window.xiaonei.pay.*;

    public class PayButton extends Sprite
    {

        public function PayButton()
        {
            if (Version.value == Version.FACEBOOK)
            {
                addChild(new FaceBookPayLayer());
            }
            else if (Version.value == Version.XIAONEI)
            {
                addChild(new XiaoneiPayLayer());
            }// end else if
            return;
        }// end function

    }
}
