package hf.view.common
{
    import flash.display.*;

    public class Line extends Shape
    {

        public function Line(param1:int = 100, param2:int = 0)
        {
            graphics.lineStyle(1, 16777215);
            graphics.moveTo(param2, 0);
            graphics.lineTo(param1, 0);
            graphics.endFill();
            return;
        }// end function

    }
}
