<%@ taglib uri="/WEB-INF/baozi.tld" prefix="baozi"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:set name="ctx" value="/yx"/>
<s:set name="IMG_PATH" value="/yx/commons/images"/>
<script language="javascript" for="document" event="onkeydown">
if(event.keyCode==13 && event.srcElement.type!='button' && event.srcElement.type!='submit' && event.srcElement.type!='reset' && event.srcElement.type!='textarea' && event.srcElement.type!=''){
	event.keyCode=9;
}
</script>

<script type="text/javascript">

<%--
function closeCalendar()
{
	alert(calendarSign);
	var event = window.event || e;
    var ele = event.srcElement || event.target;
    if(ele.id == "CalendarFrame"  ||  ele.id == "Calendar" ){
    }else{
    	if(calendarSign == 1){
    		calendarSign = 0;
    	}
    	else{
    		HiddenCalendar();
    	}
    }
}

document.attachEvent("onclick", closeCalendar);
--%> 
 
</script>