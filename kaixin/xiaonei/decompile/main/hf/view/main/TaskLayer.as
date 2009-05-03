package hf.view.main
{
    import flash.display.*;
    import flash.events.*;
    import flash.text.*;
    import hf.control.*;
    import hf.model.*;
    import hf.view.*;
    import hf.view.common.*;
    import hf.view.main.WindowControl.*;
    import hf.view.main.task.*;
    import hf.view.main.tip.*;

    public class TaskLayer extends Sprite
    {
        private var _newTask:MovieClip = null;
        private var task:Task = null;
        private var _tasking:Sprite = null;
        public static const TASK_REQUEST:String = "taskRequest";

        public function TaskLayer()
        {
            this.buttonMode = true;
            this.addEventListener(Event.ADDED_TO_STAGE, initPosition);
            TaskData.getInstance().addEventListener(TaskData.TASKDATA_CHANGE, currentTaskDataChange);
            TaskData.getInstance().addEventListener(TaskData.COMPLETE, currentTaskDataComplete);
            ViewControl.getInstance().addEventListener(ViewEvent.TASK_START, taskStart);
            return;
        }// end function

        private function rollOver2(param1:MouseEvent) : void
        {
            TipControl.show("MouseTip", Language.L["查看当前任务"]);
            return;
        }// end function

        private function coseTaskWindow() : void
        {
            task.closeWindow(null);
            return;
        }// end function

        public function acceptNewTask() : void
        {
            task = new Task();
            task.title = Language.L["领取任务"];
            task.operationAction = actionAccept;
            var _loc_1:Sprite;
            _loc_1 = MaterialLib.getInstance().getMaterial("task" + (TaskData.getInstance().currentTask.taskId + 1)) as Sprite;
            if (_loc_1 != null)
            {
                _loc_1.y = 40;
                _loc_1.x = 20;
                task.taskAddChild(_loc_1);
                task.height = _loc_1.height < 155 ? (245) : (_loc_1.height + 90);
            }// end if
            var _loc_2:* = new WindowEvent(WindowEvent.OPEN);
            _loc_2.window = task;
            ViewControl.getInstance().dispatchEvent(_loc_2);
            return;
        }// end function

        private function allTaskCompleted() : void
        {
            coseTaskWindow();
            WControl.openForName("AlertWindow", {type:"success", text:Language.L["你已完成全部新手任务！"]});
            return;
        }// end function

        private function currentTaskDataChange(param1:Event) : void
        {
            if (TaskData.getInstance().currentTask.taskFlag == 0)
            {
                hidTaskButton();
            }
            else
            {
                initControlButton(TaskData.getInstance().currentTask.taskFlag);
            }// end else if
            return;
        }// end function

        private function rollOut2(param1:MouseEvent) : void
        {
            TipControl.hide();
            return;
        }// end function

        public function activeTask() : void
        {
            task = new Task();
            task.title = Language.L["任 务"];
            task.operationAction = coseTaskWindow;
            task.operationButtonLabel = Language.L["确定"];
            task.addCancelButton = false;
            var _loc_1:* = new TextField();
            _loc_1.text = Language.L["(此任务进行中)"];
            _loc_1.selectable = false;
            _loc_1.mouseEnabled = false;
            _loc_1.width = 150;
            _loc_1.height = 25;
            _loc_1.setTextFormat(new TextFormat("Tahoma", 14, 13369344, true));
            var _loc_2:Sprite;
            _loc_2 = MaterialLib.getInstance().getMaterial("task" + TaskData.getInstance().currentTask.taskId) as Sprite;
            if (_loc_2 != null)
            {
                _loc_2.y = 40;
                _loc_2.x = 20;
                task.taskAddChild(_loc_2);
                _loc_2.addChild(_loc_1);
                task.height = _loc_2.height < 155 ? (245) : (_loc_2.height + 90);
                if (Language.lang == Language.CN)
                {
                    _loc_1.x = _loc_2.getChildAt(0).x + _loc_2.getChildAt(0).width;
                    _loc_1.y = -3;
                }
                else
                {
                    _loc_1.x = _loc_2.getChildAt(0).x + _loc_2.getChildAt(0).width - 25;
                    _loc_1.y = 0;
                }// end if
            }// end else if
            var _loc_3:* = new WindowEvent(WindowEvent.OPEN);
            _loc_3.window = task;
            ViewControl.getInstance().dispatchEvent(_loc_3);
            return;
        }// end function

        private function showWindow(param1:Event = null) : void
        {
            if (TaskData.getInstance().currentTask.taskFlag == 2)
            {
                acceptNewTask();
            }
            else if (TaskData.getInstance().currentTask.taskFlag == 1)
            {
                activeTask();
            }
            else if (TaskData.getInstance().currentTask.taskFlag == 0)
            {
            }// end else if
            return;
        }// end function

        private function currentTaskDataComplete(param1:Event) : void
        {
            var _loc_2:Object;
            var _loc_3:TextField;
            var _loc_6:int;
            var _loc_7:int;
            var _loc_8:int;
            var _loc_9:int;
            var _loc_10:MaterialProxy;
            var _loc_11:TextField;
            initControlButton(2);
            task = new Task();
            task.title = Language.L["完成任务"];
            if (TaskData.getInstance().updata["taskId"] != 0)
            {
                task.operationButtonLabel = Language.L["进行下一个任务"];
                task.operationButtonWidth = 120;
                task.operationAction = nextTask;
            }
            else
            {
                task.operationButtonLabel = Language.L["确定"];
                task.operationAction = allTaskCompleted;
            }// end else if
            _loc_2 = TaskData.getInstance().updata;
            _loc_3 = new TextField();
            if (!_loc_2.direction)
            {
            }// end if
            _loc_3.text = "";
            _loc_3.x = 20;
            _loc_3.y = 37;
            _loc_3.width = 315;
            _loc_3.height = 40;
            _loc_3.wordWrap = true;
            _loc_3.setTextFormat(new TextFormat("Tahoma", 14, 39423, true));
            task.taskAddChild(_loc_3);
            var _loc_4:* = _loc_2.item.length;
            if (_loc_2.item.length > 0)
            {
                _loc_6 = 50;
                _loc_7 = 20;
                _loc_8 = (task.width - (_loc_6 * _loc_4 + _loc_7 * _loc_4--)) * 0.5;
                _loc_9 = 0;
                while (_loc_9 < _loc_4)
                {
                    // label
                    _loc_10 = new MaterialProxy();
                    _loc_10.setContent(_loc_2.item[_loc_9]["eType"], _loc_2.item[_loc_9]["eParam"]);
                    _loc_10.x = _loc_8;
                    _loc_10.y = 80;
                    _loc_10.scaleX = _loc_6 / _loc_10.width;
                    _loc_10.scaleY = _loc_6 / _loc_10.width;
                    task.taskAddChild(_loc_10);
                    _loc_11 = new TextField();
                    _loc_11.x = _loc_8 - _loc_7 * 0.5;
                    _loc_11.y = _loc_10.y + 60;
                    _loc_11.width = _loc_6 + _loc_7;
                    _loc_11.height = 50;
                    _loc_11.wordWrap = true;
                    _loc_11.htmlText = _loc_2.item[_loc_9].eNum;
                    _loc_11.setTextFormat(new TextFormat("fontForte", 22, 16737792, false, null, null, null, null, "center"));
                    _loc_11.embedFonts = true;
                    task.taskAddChild(_loc_11);
                    _loc_8 = _loc_8 + _loc_6 + _loc_7;
                    _loc_9++;
                }// end while
            }// end if
            var _loc_5:* = new WindowEvent(WindowEvent.OPEN);
            new WindowEvent(WindowEvent.OPEN).window = task;
            ViewControl.getInstance().dispatchEvent(_loc_5);
            return;
        }// end function

        private function rollOver(param1:MouseEvent) : void
        {
            TipControl.show("MouseTip", Language.L["查看当前任务"]);
            return;
        }// end function

        private function actionAccept() : void
        {
            coseTaskWindow();
            ViewControl.getInstance().dispatchEvent(new Event(TaskLayer.TASK_REQUEST));
            Command.getInstance().taskCommand.run();
            return;
        }// end function

        private function rollOut(param1:MouseEvent) : void
        {
            TipControl.hide();
            return;
        }// end function

        private function initPosition(param1:Event) : void
        {
            this.x = 20;
            this.y = stage.stageHeight - 50;
            if (TaskData.getInstance().currentTask != null && TaskData.getInstance().currentTask.taskFlag != 0)
            {
                initControlButton(TaskData.getInstance().currentTask.taskFlag);
            }// end if
            return;
        }// end function

        private function hidTaskButton() : void
        {
            if (_newTask != null)
            {
                removeChild(_newTask);
                _newTask = null;
            }// end if
            if (_tasking != null)
            {
                removeChild(_tasking);
                _tasking = null;
            }// end if
            return;
        }// end function

        private function nextTask() : void
        {
            coseTaskWindow();
            showWindow(null);
            return;
        }// end function

        private function taskStart(param1:Event) : void
        {
            showWindow();
            return;
        }// end function

        public function initControlButton(param1:int) : void
        {
            hidTaskButton();
            if (param1 == 2)
            {
                _newTask = MaterialLib.getInstance().getMaterial("taskAwokeButton") as MovieClip;
                _newTask.name = "newTask";
                _newTask.width = 63.9;
                _newTask.height = 49;
                _newTask.y = -10;
                _newTask.x = -5;
                _newTask.addEventListener(MouseEvent.CLICK, showWindow);
                _newTask.addEventListener(MouseEvent.ROLL_OVER, rollOver);
                _newTask.addEventListener(MouseEvent.ROLL_OUT, rollOut);
                addChild(_newTask);
            }// end if
            if (param1 == 1)
            {
                _tasking = MaterialLib.getInstance().getMaterial("taskingButton") as Sprite;
                _tasking.name = "Tasking";
                _tasking.width = 46.4;
                _tasking.height = 36.5;
                _tasking.addEventListener(MouseEvent.CLICK, showWindow);
                _tasking.addEventListener(MouseEvent.ROLL_OVER, rollOver2);
                _tasking.addEventListener(MouseEvent.ROLL_OUT, rollOut2);
                addChild(_tasking);
            }// end if
            return;
        }// end function

    }
}
