package hf.view.main.window.facebook.pay
{
    import flash.text.*;
    import hf.view.common.*;

    public class PaypalNodeWin extends AddButtonWindow
    {
        private var pay:String = "";

        public function PaypalNodeWin(param1:String = "10")
        {
            pay = param1;
            this.title = "Congratulations";
            return;
        }// end function

        override public function init() : void
        {
            var _loc_1:* = new TextField();
            _loc_1.multiline = true;
            _loc_1.selectable = false;
            _loc_1.htmlText = "<body><b>Congratulations!</b><br><br>We have received your payments!<br>Thanks for your support! <br><br>If you paid but did not get credits?  <br> Please email us at: <b>pay@fminutes.com</b></body>";
            _loc_1.setTextFormat(new TextFormat("Tahoma", 14, 3355443));
            _loc_1.width = 270;
            _loc_1.height = 150;
            _loc_1.x = 30;
            _loc_1.y = 45;
            addChild(_loc_1);
            addButton("Cancel", "ButtonBlue", 80, 25, closeHandler);
            super.init();
            return;
        }// end function

    }
}
