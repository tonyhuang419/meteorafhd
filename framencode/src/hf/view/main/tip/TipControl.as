package hf.view.main.tip
{
    import hf.control.*;

    public class TipControl extends Object
    {

        public function TipControl()
        {
            return;
        }// end function

        public static function hide() : void
        {
            var _loc_1:* = new TipEvent(TipEvent.TIP_HIDE);
            ViewControl.getInstance().dispatchEvent(_loc_1);
            return;
        }// end function

        public static function show(param1:String, param2:Object, param3:int = 0, param4:int = 0) : void
        {
            var _loc_5:* = new TipEvent(TipEvent.TIP_SHOW);
            new TipEvent(TipEvent.TIP_SHOW).tipArgument = param2;
            _loc_5.tipType = param1;
            _loc_5.tX = param3;
            _loc_5.tY = param4;
            ViewControl.getInstance().dispatchEvent(_loc_5);
            return;
        }// end function

    }
}
