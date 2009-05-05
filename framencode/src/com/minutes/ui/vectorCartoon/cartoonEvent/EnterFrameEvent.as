package com.minutes.ui.vectorCartoon.cartoonEvent
{
    import flash.events.*;

    public class EnterFrameEvent extends Event
    {
        public static const VECTOR_CARTOON_ENTER:String = "vectorCartoonEnter";

        public function EnterFrameEvent(param1:String, param2:Boolean = false, param3:Boolean = false)
        {
            super(param1, param2, param3);
            return;
        }// end function

    }
}
