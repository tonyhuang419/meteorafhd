package com.minutes.ui.core
{
    import flash.display.*;
    import flash.events.*;

    public class LipiSkin extends Sprite
    {
        private var _enableMouse:Boolean = true;
        private var _backgroundImage:Class;
        private var _bgImage:DisplayObject;

        public function LipiSkin(param1:Class = null)
        {
            if (param1 != null)
            {
                backgroundImage = param1;
            }// end if
            addEventListener(Event.REMOVED_FROM_STAGE, removeFormStage);
            return;
        }// end function

        public function get backgroundDisplayObject() : DisplayObject
        {
            return _bgImage;
        }// end function

        public function get enableMouse() : Boolean
        {
            return _enableMouse;
        }// end function

        public function set enableMouse(param1:Boolean) : void
        {
            _enableMouse = param1;
            return;
        }// end function

        private function removeFormStage(param1:Event) : void
        {
            if (_bgImage is MovieClip)
            {
                (_bgImage as MovieClip).gotoAndStop(1);
            }// end if
            stage.removeEventListener(MouseEvent.MOUSE_UP, stageUpHandler);
            return;
        }// end function

        private function downHandler(param1:MouseEvent) : void
        {
            if (!enableMouse)
            {
                return;
            }// end if
            if ((param1.currentTarget as MovieClip).totalFrames >= 3)
            {
                (param1.currentTarget as MovieClip).gotoAndStop(3);
                this.stage.addEventListener(MouseEvent.MOUSE_UP, stageUpHandler);
            }// end if
            return;
        }// end function

        private function outHandler(param1:MouseEvent) : void
        {
            if (!enableMouse)
            {
                return;
            }// end if
            if (param1.buttonDown == true)
            {
                return;
            }// end if
            if ((param1.currentTarget as MovieClip).totalFrames >= 3)
            {
                (param1.currentTarget as MovieClip).gotoAndStop(1);
            }// end if
            return;
        }// end function

        override public function set width(param1:Number) : void
        {
            if (_bgImage == null)
            {
                return;
            }// end if
            _bgImage.width = param1;
            return;
        }// end function

        public function get backgroundImage() : Class
        {
            return _backgroundImage;
        }// end function

        override public function set height(param1:Number) : void
        {
            if (_bgImage == null)
            {
                return;
            }// end if
            _bgImage.height = param1;
            return;
        }// end function

        private function stageUpHandler(param1:MouseEvent) : void
        {
            if (!enableMouse)
            {
                return;
            }// end if
            if (_bgImage is MovieClip)
            {
                if ((_bgImage as MovieClip).totalFrames >= 3)
                {
                    if (param1.target == _bgImage)
                    {
                        (_bgImage as MovieClip).gotoAndStop(2);
                    }
                    else
                    {
                        (_bgImage as MovieClip).gotoAndStop(1);
                    }// end if
                }// end if
            }// end else if
            this.stage.removeEventListener(MouseEvent.MOUSE_UP, stageUpHandler);
            return;
        }// end function

        public function get bgImage() : DisplayObject
        {
            return _bgImage;
        }// end function

        public function set backgroundImage(param1:Class) : void
        {
            _backgroundImage = param1;
            _bgImage = new param1 as DisplayObject;
            _bgImage.x = 0;
            _bgImage.y = 0;
            addChild(_bgImage);
            if (_bgImage is MovieClip)
            {
                (_bgImage as MovieClip).gotoAndStop(1);
                (_bgImage as MovieClip).addEventListener(MouseEvent.MOUSE_OVER, overHandler);
                (_bgImage as MovieClip).addEventListener(MouseEvent.MOUSE_DOWN, downHandler);
                (_bgImage as MovieClip).addEventListener(MouseEvent.MOUSE_OUT, outHandler);
            }// end if
            return;
        }// end function

        private function overHandler(param1:MouseEvent) : void
        {
            if (!enableMouse)
            {
                return;
            }// end if
            if (param1.buttonDown == false)
            {
                if ((param1.currentTarget as MovieClip).totalFrames >= 3)
                {
                    (param1.currentTarget as MovieClip).gotoAndStop(2);
                }// end if
            }// end if
            return;
        }// end function

        public function set backgroundDisplayObject(param1:DisplayObject) : void
        {
            _bgImage = param1;
            return;
        }// end function

    }
}
