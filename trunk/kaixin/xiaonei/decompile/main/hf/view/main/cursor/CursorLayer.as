package hf.view.main.cursor
{
    import flash.display.*;
    import flash.events.*;
    import flash.ui.*;
    import hf.control.*;

    public class CursorLayer extends Sprite
    {
        private var currentCursor:IBaseCursor;

        public function CursorLayer()
        {
            addEventListener(Event.ADDED_TO_STAGE, onAddedToStage);
            return;
        }// end function

        private function stageDown(param1:MouseEvent) : void
        {
            if (currentCursor == null)
            {
                return;
            }// end if
            if ((currentCursor as DisplayObject).visible == false)
            {
                return;
            }// end if
            currentCursor.down();
            return;
        }// end function

        private function setCursorHandler(param1:CursorEvent) : void
        {
            var _loc_3:Class;
            var _loc_4:BaseCursor;
            var _loc_2:Boolean;
            if (param1.cursorName == "")
            {
                _loc_2 = true;
            }// end if
            if (true)
            {
                _loc_3 = CursorClassLib.getClass(param1.cursorName);
                if (_loc_3 != null)
                {
                    _loc_4 = new _loc_3 as BaseCursor;
                    if (param1.cursorArgument != "")
                    {
                        _loc_4.cursorArgument = param1.cursorArgument;
                    }// end if
                    setCurrentCursor(_loc_4);
                }
                else
                {
                    _loc_2 = true;
                }// end if
            }// end else if
            if (_loc_2)
            {
                Mouse.show();
                if (currentCursor != null)
                {
                    removeChild(currentCursor as DisplayObject);
                    currentCursor = null;
                }// end if
            }// end if
            return;
        }// end function

        private function onAddedToStage(param1:Event) : void
        {
            removeEventListener(Event.ADDED_TO_STAGE, onAddedToStage);
            mouseEnabled = false;
            mouseChildren = false;
            ViewControl.getInstance().addEventListener(CursorEvent.CURSOR, setCursorHandler, false, 0, true);
            ViewControl.getInstance().addEventListener(CursorEvent.USE_TYPE, setTypeHandler, false, 0, true);
            stage.addEventListener(MouseEvent.MOUSE_DOWN, stageDown, false, 0, true);
            stage.addEventListener(MouseEvent.MOUSE_UP, stageUp, false, 0, true);
            stage.addEventListener(MouseEvent.MOUSE_MOVE, cursorMove, false, 0, true);
            stage.addEventListener(MouseEvent.CLICK, stageClick, false, 0, true);
            return;
        }// end function

        private function setTypeHandler(param1:CursorEvent) : void
        {
            if (param1.cursorName == "system")
            {
                if (currentCursor != null)
                {
                    (currentCursor as DisplayObject).visible = false;
                }// end if
                Mouse.show();
            }
            else if (currentCursor != null)
            {
                (currentCursor as DisplayObject).visible = true;
                (currentCursor as DisplayObject).x = mouseX;
                (currentCursor as DisplayObject).y = mouseY;
                Mouse.hide();
            }
            else
            {
                Mouse.show();
            }// end else if
            return;
        }// end function

        private function cursorMove(param1:MouseEvent) : void
        {
            if (currentCursor == null)
            {
                return;
            }// end if
            if ((currentCursor as DisplayObject).visible == false)
            {
                return;
            }// end if
            (currentCursor as DisplayObject).x = param1.stageX;
            (currentCursor as DisplayObject).y = param1.stageY;
            param1.updateAfterEvent();
            return;
        }// end function

        private function stageUp(param1:MouseEvent) : void
        {
            if (currentCursor == null)
            {
                return;
            }// end if
            if ((currentCursor as DisplayObject).visible == false)
            {
                return;
            }// end if
            currentCursor.up();
            return;
        }// end function

        private function stageClick(param1:MouseEvent) : void
        {
            if (currentCursor == null)
            {
                return;
            }// end if
            if ((currentCursor as DisplayObject).visible == false)
            {
                return;
            }// end if
            currentCursor.click();
            return;
        }// end function

        private function setCurrentCursor(param1:IBaseCursor) : void
        {
            Mouse.hide();
            if (currentCursor != null)
            {
                removeChild(currentCursor as DisplayObject);
                currentCursor = null;
            }// end if
            currentCursor = param1;
            addChild(currentCursor as DisplayObject);
            (currentCursor as DisplayObject).x = mouseX;
            (currentCursor as DisplayObject).y = mouseY;
            return;
        }// end function

    }
}
