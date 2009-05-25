package hf.model
{
    import flash.events.*;

    public class ModelEvent extends Event
    {
        private var _data:Object;

        public function ModelEvent(param1:String, param2:Boolean = false, param3:Boolean = false)
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
