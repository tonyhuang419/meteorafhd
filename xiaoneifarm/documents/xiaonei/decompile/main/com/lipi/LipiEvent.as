package com.lipi
{
    import flash.events.*;

    public class LipiEvent extends Event
    {
        public var data:Object;
        public static const LOADEVENT_PROGRESS:String = "loadeventProgress";
        public static const LOADEVENT_COMP:String = "loadeventComp";

        public function LipiEvent(param1:String, param2:Boolean = false, param3:Boolean = false)
        {
            super(param1, param2, param3);
            return;
        }// end function

    }
}
