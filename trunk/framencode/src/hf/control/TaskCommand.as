package hf.control
{
    import flash.events.*;
    import hf.FBridge.*;
    import hf.model.*;

    public class TaskCommand extends EventDispatcher
    {
        private var fr:FRequest;
        private var taskData:TaskData;

        public function TaskCommand()
        {
            fr = FRequest.getInstance();
            taskData = TaskData.getInstance();
            return;
        }// end function

        public function run() : void
        {
            fr.postRequest("mod=task&act=accept", null, taskAccept);
            return;
        }// end function

        private function taskAccept(param1:Object) : void
        {
            if (!param1.taskId)
            {
                return;
            }// end if
            taskData.currentTask = {taskId:param1.taskId, taskFlag:1};
            return;
        }// end function

    }
}
