package hf.model
{
    import flash.events.*;

    public class TaskData extends EventDispatcher
    {
        private var _updata:Object;
        private var _currentTask:Object;
        public static const TASKDATA_CHANGE:String = "taskDataChange";
        private static var instance:TaskData;
        public static const COMPLETE:String = "complete";
        public static const TASK_DOING:String = "taskDoing";

        public function TaskData()
        {
            _currentTask = {taskId:1, taskFlag:2};
            return;
        }// end function

        public function set updata(param1:Object) : void
        {
            _updata = param1;
            dispatchEvent(new ModelEvent(TaskData.COMPLETE));
            return;
        }// end function

        public function set currentTask(param1:Object) : void
        {
            _currentTask = param1;
            dispatchEvent(new ModelEvent(TaskData.TASKDATA_CHANGE));
            return;
        }// end function

        public function get updata() : Object
        {
            return _updata;
        }// end function

        public function get currentTask() : Object
        {
            return _currentTask;
        }// end function

        public static function getInstance() : TaskData
        {
            if (instance == null)
            {
                instance = new TaskData;
            }// end if
            return instance;
        }// end function

    }
}
