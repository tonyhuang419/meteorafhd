package hf.view.main.cursor
{

    public class CursorClassLib extends Object
    {
        private static var cusrsorLib:Object = {CursorArrow:CursorArrow, CursorWeed:CursorWeed, CursorInsect:CursorInsect, CursorHoe:CursorHoe, CursorHook:CursorHook, CursorPesticide:CursorPesticide, CursorWater:CursorWater, CursorHand:CursorHand, CursorCropSeed:CursorCropSeed, CursorTool:CursorTool};

        public function CursorClassLib()
        {
            return;
        }// end function

        public static function getClass(param1:String) : Class
        {
            if (cusrsorLib == null)
            {
                return null;
            }// end if
            return cusrsorLib[param1];
        }// end function

        public static function register(param1:String, param2:Class) : void
        {
            if (cusrsorLib == null)
            {
                cusrsorLib = new Object();
            }// end if
            cusrsorLib[param1] = param2;
            return;
        }// end function

    }
}
