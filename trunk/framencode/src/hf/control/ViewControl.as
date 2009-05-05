package hf.control
{
    import flash.events.*;

    public class ViewControl extends EventDispatcher
    {
        private static var instance:ViewControl;

        public function ViewControl()
        {
            return;
        }// end function

        public static function getInstance() : ViewControl
        {
            if (instance == null)
            {
                instance = new ViewControl;
            }// end if
            return instance;
        }// end function

    }
}
