<%@ page contentType="text/html;charset=UTF-8"%>
<object id=factory viewastext style="display:none"
classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814"
  codebase="/yx/commons/cab/smsx.cab#Version=6,4,438,06">
</object>
<script type="text/javascript">
/**
选项
horizontal boolean型 true横打，false纵打
*/
	function preparePrintOption(printOption){
	    factory.printing.header = "";
	    factory.printing.footer = "";
	    if(printOption != null && printOption.horizontal != null){
	  	  factory.printing.portrait = !printOption.horizontal; //true纵打，false横打
	    }
	}
	function Preview(printOption) {
	  try{
		  preparePrintOption(printOption);
		  factory.printing.Preview()
	  }catch(e){
	  	  alert("scriptx控件没有正确安装[错误："+e.message+"],请重新安装或使用浏览器提供的打印");
	  }
	}
	function Print(printOption) {
	  try{
		  preparePrintOption(printOption);
		  factory.printing.Print()
	  }catch(e){
	  	  alert("scriptx控件没有正确安装[错误："+e.message+"],请重新安装或使用浏览器提供的打印");
	  }
	}
</script>