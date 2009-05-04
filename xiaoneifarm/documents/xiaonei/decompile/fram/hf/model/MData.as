package hf.model
{
    import flash.events.*;

    public class MData extends EventDispatcher
    {
        private var _mainData:MainData;
        private var _farmData:FarmData;
        private static var instance:MData;

        public function MData()
        {
            return;
        }// end function

        public function get farmData() : FarmData
        {
            if (_farmData == null)
            {
                _farmData = new FarmData();
            }// end if
            return _farmData;
        }// end function

        public function get mainData() : MainData
        {
            if (_mainData == null)
            {
                _mainData = new MainData();
            }// end if
            return _mainData;
        }// end function

        public static function getInstance() : MData
        {
            if (instance == null)
            {
                instance = new MData;
            }// end if
            return instance;
        }// end function

    }
}
