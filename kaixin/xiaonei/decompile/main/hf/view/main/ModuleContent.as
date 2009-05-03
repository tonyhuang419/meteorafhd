package hf.view.main
{
    import flash.display.*;
    import flash.events.*;
    import flash.net.*;
    import hf.model.*;
    import hf.view.*;

    public class ModuleContent extends Sprite
    {
        private var mainData:MainData;
        private var materialLoad:MaterialLoad;
        private var moduleLoader:Loader;

        public function ModuleContent()
        {
            mainData = MData.getInstance().mainData;
            init();
            return;
        }// end function

        private function progressLoad(param1:ViewEvent) : void
        {
            return;
        }// end function

        private function errorLoad(param1:ViewEvent) : void
        {
            trace("error---------------");
            return;
        }// end function

        private function init() : void
        {
            var _loc_1:* = INI.getInstance().getMaterialUrl(mainData.moduleName);
            materialLoad = new MaterialLoad(_loc_1);
            materialLoad.addEventListener(ViewEvent.PROGRESS_LOAD, progressLoad);
            materialLoad.addEventListener(ViewEvent.ERROR_LOAD, errorLoad);
            materialLoad.addEventListener(Event.COMPLETE, materialLoadComp);
            materialLoad.load();
            return;
        }// end function

        private function loadModule() : void
        {
            if (moduleLoader == null)
            {
                moduleLoader = new Loader();
                moduleLoader.contentLoaderInfo.addEventListener(Event.COMPLETE, moduleComp);
                moduleLoader.contentLoaderInfo.addEventListener(HTTPStatusEvent.HTTP_STATUS, moduleHttpStatus);
                moduleLoader.contentLoaderInfo.addEventListener(IOErrorEvent.IO_ERROR, moduleIOError);
            }// end if
            var _loc_1:* = INI.getInstance().getModuleUrl(mainData.moduleName);
            moduleLoader.load(new URLRequest(_loc_1["url"]));
            return;
        }// end function

        private function moduleHttpStatus(param1:HTTPStatusEvent) : void
        {
            return;
        }// end function

        private function moduleIOError(param1:IOErrorEvent) : void
        {
            return;
        }// end function

        private function moduleComp(param1:Event) : void
        {
            addChild(moduleLoader.contentLoaderInfo.content);
            dispatchEvent(new ViewEvent(ViewEvent.MODULE_COMP));
            return;
        }// end function

        private function materialLoadComp(param1:Event) : void
        {
            if (materialLoad == null)
            {
                return;
            }// end if
            materialLoad.removeEventListener(ViewEvent.PROGRESS_LOAD, progressLoad);
            materialLoad.removeEventListener(ViewEvent.ERROR_LOAD, errorLoad);
            materialLoad.removeEventListener(Event.COMPLETE, materialLoadComp);
            materialLoad = null;
            loadModule();
            return;
        }// end function

    }
}
