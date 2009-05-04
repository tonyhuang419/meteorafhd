package hf.control
{
    import flash.events.*;

    public class Command extends EventDispatcher
    {
        private var _mainCommand:MainCommand;
        private var _farmCommand:FarmCommand;
        private var _taskCommand:TaskCommand = null;
        private static var instance:Command;

        public function Command()
        {
            if (instance != null)
            {
                throw new Error("实例化单例类出错-Command");
            }// end if
            return;
        }// end function

        public function get mainCommand() : MainCommand
        {
            if (_mainCommand == null)
            {
                _mainCommand = new MainCommand();
            }// end if
            return _mainCommand;
        }// end function

        public function get farmCommand() : FarmCommand
        {
            if (_farmCommand == null)
            {
                _farmCommand = new FarmCommand();
            }// end if
            return _farmCommand;
        }// end function

        public function get taskCommand() : TaskCommand
        {
            if (_taskCommand == null)
            {
                _taskCommand = new TaskCommand();
            }// end if
            return _taskCommand;
        }// end function

        public static function getInstance() : Command
        {
            if (instance == null)
            {
                instance = new Command;
            }// end if
            return instance;
        }// end function

    }
}
