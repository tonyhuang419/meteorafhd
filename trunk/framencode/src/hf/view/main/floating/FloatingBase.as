package hf.view.main.floating
{
    import flash.display.*;

    public class FloatingBase extends Sprite
    {
        private var _fid:int = -1;

        public function FloatingBase()
        {
            return;
        }// end function

        public function get fid() : int
        {
            return _fid;
        }// end function

        public function start() : void
        {
            return;
        }// end function

        public function set fid(param1:int) : void
        {
            _fid = param1;
            return;
        }// end function

    }
}
