package hf.view
{
    import flash.events.*;

    public class ViewEvent extends Event
    {
        private var _data:Object;
        public static const MODULE_COMP:String = "moduleComp";
        public static const CHILD_OUT:String = "childOut";
        public static const CHILD_CLICK:String = "childClick";
        public static const TASK_START:String = "taskStart";
        public static const RECLAIM_CLICK:String = "reclaimClick";
        public static const CHILD_OVER:String = "childOver";
        public static const PROGRESS_LOAD:String = "progressLoad";
        public static const LINK_CLICK:String = "linkClick";
        public static const ERROR_LOAD:String = "errorLoad";

        public function ViewEvent(param1:String, param2:Boolean = false, param3:Boolean = false)
        {
            super(param1, param2, param3);
            return;
        }// end function

        public function set data(param1:Object) : void
        {
            _data = param1;
            return;
        }// end function

        public function get data() : Object
        {
            return _data;
        }// end function

    }
}
