package com.minutes.ui.vectorCartoon.cartoonInterface
{
    import com.minutes.ui.vectorCartoon.cartoonFrameRate.*;

    public interface Cartoon
    {

        public function Cartoon();

        function setCartoonFrameRate(param1:CartoonFrameRate) : void;

        function stop() : void;

        function isPlaying() : Boolean;

        function gotoAndPlay(param1:int) : void;

        function prevFrame() : void;

        function getCurrentFrame() : int;

        function play() : void;

        function getCartoonFrameRate() : int;

        function gotoAndStop(param1:int) : void;

        function nextFrame() : void;

        function getTotalFrames() : int;

    }
}
