var bMoveable=true;  
var _VersionInfo="Version:2.02&#13;2.01&02 Author: Athos;2.0 Author:walkingpoison&#13;1.0 Author: F.R.Huang(meizz)&#13;MAIL: meizz@hzcnc.com";
var strFrame;  
var iMinYear =1990;
var iMaxYear = 2020;
var strDateFormat = "yyyy-MM-dd";

document.writeln('<iframe id=meizzDateLayer Author=wayx frameborder=0 style="position: absolute; width: 250;height:130; z-index: 9998; display: none;border:1px solid #000000" scrolling=no></iframe>');
strFrame='<style>';
strFrame += 'body{margin:0}';
strFrame+='INPUT.button{BORDER: #cccccc 1px solid;BACKGROUND-COLOR: #fff8ec;}';
strFrame+='TD{FONT-SIZE: 10px;font-family:verdana}';
strFrame += 'select{font-size:10px;font-family:verdana}';
strFrame+='</style>';
strFrame+='<scr' + 'ipt>';
strFrame+='var datelayerx,datelayery; ';
strFrame+='var bDrag; ';
strFrame+='function document.onmousemove() ';
strFrame+='{if(bDrag && window.event.button==1)';
strFrame+=' {var DateLayer=parent.document.all.meizzDateLayer.style;';
strFrame+='  DateLayer.posLeft += window.event.clientX-datelayerx;';
strFrame+='  DateLayer.posTop += window.event.clientY-datelayery;}}';
strFrame+='function DragStart()  ';
strFrame+='{var DateLayer=parent.document.all.meizzDateLayer.style;';
strFrame+=' datelayerx=window.event.clientX;';
strFrame+=' datelayery=window.event.clientY;';
strFrame+=' bDrag=true;}';
strFrame+='function DragEnd(){  ';
strFrame+=' bDrag=false;}';
strFrame+='</scr' + 'ipt>';
strFrame+='<div style="z-index:9999;position: absolute; left:0; top:0;" onselectstart="return false"><span id=tmpSelectYearLayer Author=wayx style="z-index: 9999;position: absolute;top: 1; left: 15;display: none"></span>';
strFrame+='<span id=tmpSelectMonthLayer Author=wayx style="z-index: 9999;position: absolute;top: 1; left: 135;display: none"></span>';
strFrame+='<table border=0 cellspacing=0 cellpadding=0 bgcolor=#cccccc Author="wayx" id=pontustable width=100% align=center>';
strFrame+='  <tr Author="wayx"><td Author="wayx"><table border=0 cellspacing=0 cellpadding=0 width=100% Author="wayx" height=20>';
strFrame+='      <tr align=center Author="wayx"><td width=16 align=center bgcolor=#cccccc style="cursor: hand;" ';
strFrame+='        onclick="parent.meizzPrevM()" title="1 Month Before" Author=meizz><b Author=meizz>&lt;</b>';
strFrame+='        </td><td align=left style="cursor:default;font-family:verdana" Author=meizz ';
strFrame+='onmouseover="style.backgroundColor=\'#FFD700\'" onmouseout="style.backgroundColor=\'#cccccc\'" ';
strFrame+='onclick="parent.tmpSelectYearInnerHTML(this.innerText)" title="Click to select year"><span Author=meizz id=meizzYearHead></span></td>';
strFrame+='<td align=right style="cursor:default" Author=meizz onmouseover="style.backgroundColor=\'#FFD700\'" ';
strFrame+=' onmouseout="style.backgroundColor=\'#cccccc\'" onclick="parent.tmpSelectMonthInnerHTML(parent.athosMonthNameToNum(this.innerText))"';
strFrame+='        title="Click to select month"><span id=meizzMonthHead Author=meizz></span></td>';
strFrame+='        <td width=16 bgcolor=#cccccc align=center style="cursor: hand;" ';
strFrame+='         onclick="parent.meizzNextM()" title="1 Month Later" Author=meizz><b Author=meizz>&gt;</b></td></tr>';
strFrame+='    </table></td></tr>';
strFrame+='  <tr Author="wayx"><td height=18 Author="wayx">';
strFrame+='<table border=0 cellspacing=1 cellpadding=0 ' + (bMoveable? 'onmousedown="DragStart()" onmouseup="DragEnd()"':'');
strFrame+=' height=20 Author="wayx" style="cursor:' + (bMoveable ? 'move':'default') + '">';
strFrame+='<tr Author="wayx" align=center style="font-size:6px;color:#FFffff;font-family:verdana;font-weight:bold" bgcolor=#999999>';
strFrame+='<td width=35 Author=meizz title="Sunday">Sun</td>';
strFrame+='<td width=35 Author=meizz title="Monday">Mon</td>';
strFrame+='<td width=35 Author=meizz title="Tuesday">Tue</td>';
strFrame+='<td width=35 Author=meizz title="Wednesday">Wed</td>';
strFrame+='<td width=35 Author=meizz title="Thursday">Thu</td>';
strFrame+='<td width=35 width=30 Author=meizz title="Friday">Fri</td>';
strFrame+='<td width=35 Author=meizz title="Saturday">Sat</td>';
strFrame+='</tr>'; 

var n=0; for (j=0;j<5;j++){ strFrame+= ' <tr align=center Author="wayx">'; for (i=0;i<7;i++){
strFrame+='<td id=meizzDay'+n+'  Author=meizz onclick=parent.meizzDayClick(this.innerText,0) height=15></td>';n++;}
strFrame+='</tr>';}
strFrame+='      <tr align=center Author="wayx">';
for (i=35;i<40;i++){strFrame+='<td id=meizzDay'+i+' Author=wayx onclick="parent.meizzDayClick(this.innerText,0)" height=15></td>'};
strFrame+='        <td Author=meizz><span onclick=parent.clearAndCloseLayer() style="cursor: hand;color:#00aaaa;"';
strFrame+='         Author=meizz title="Clear"><b>C</b></span></td>';
strFrame+='        <td Author=meizz><span onclick=parent.closeLayer() style="cursor: hand;color:Red;"';
strFrame+='         Author=meizz title="Close"><b>X</b></span></td>';
//strFrame+='        <td Author=meizz><a href="mailto: athos.liu@gmail.com; meizz@hzcnc.com" style="text-decoration:none"';
//strFrame+='         Author=meizz title="' + _VersionInfo + '"><i>&copy;</i></a>&nbsp;</td>';
strFrame+='</tr>';
strFrame+='</table></td></tr>';
/*
strFrame+='  <tr Author="wayx"><td Author="wayx"><table border=0 cellspacing=1 cellpadding=0 width=100% Author="wayx" bgcolor=#FFFFFF>';
strFrame+='    <tr Author="wayx"><td Author=meizz align=left>';
strFrame+='       <input Author=meizz type=button class=button value="&larr;" title="10 Year Before" onclick="parent.meizzPrevY(10)" ';
strFrame+='          onfocus="this.blur()" style="font-size: 12px; height: 20px">';
strFrame+='       <input Author=meizz type=button class=button value="&#171;" title="1 Year Before" onclick="parent.meizzPrevY(1)" ';
strFrame+='          onfocus="this.blur()" style="font-size: 12px; height: 20px">';
strFrame+='        <input Author=meizz class=button title="1 Month Before" type=button ';
strFrame+='          value="&lsaquo;" onclick="parent.meizzPrevM()" onfocus="this.blur()" style="font-size: 12px; height: 20px"></td><td ';
strFrame+='             Author=meizz align=center><input Author=meizz type=button class=button value=Tod style="color:#00007f;background-color:FFD700;font:italic bolder 10pt;" onclick="parent.meizzToday()" ';
strFrame+='             onfocus="this.blur()" title="Today" style="font-size: 12px; height: 20px; cursor:hand"></td><td ';
strFrame+='             Author=meizz align=right>';
strFrame+='        <input Author=meizz type=button class=button value="&rsaquo;" onclick="parent.meizzNextM()" ';
strFrame+='             onfocus="this.blur()" title="1 Month Later" class=button style="font-size: 12px; height: 20px">';
strFrame+='        <input Author=meizz type=button class=button value="&#187;" title="1 Year Later" onclick="parent.meizzNextY(1)"';
strFrame+='             onfocus="this.blur()" style="font-size: 12px; height: 20px">';
strFrame+='       <input Author=meizz type=button class=button value="&rarr;" title="10 Year Later" onclick="parent.meizzNextY(10)" ';
strFrame+='          onfocus="this.blur()" style="font-size: 12px; height: 20px"></td></tr></table></td></tr>';
*/
strFrame+='      </table></div>'; 

window.frames.meizzDateLayer.document.writeln(strFrame);
window.frames.meizzDateLayer.document.close();  

var outObject;
var outButton; 
var outDate=""; 
var odatelayer=window.frames.meizzDateLayer.document.all;  

//setDay
function setday(tt,obj,sy,ey,df) 
{
	 iMinYear = sy? sy:1990;
	 iMaxYear = ey?ey:2020;
	 strDateFormat = df;

	 if (arguments.length >  5){alert("Sorry, too many parameters");return;}
	 if (arguments.length == 0){alert("Sorry, none parameter!");return;}
	 var dads  = document.all.meizzDateLayer.style;
	 var th = tt;
	 var ttop  = obj.offsetTop;
	 var thei  = obj.offsetHeight;  
	 var tleft = obj.offsetLeft;  
	 var ttyp  = tt.type;     
	 
	 var t="";
	 var ys =0;
	 var yl=0;
	 var ms = 0;
	 var ml = 0;
	 var ds = 0;
	 var dl=0;
     
	 while (tt = tt.offsetParent){ttop+=tt.offsetTop; tleft+=tt.offsetLeft;}
	 
	 dads.top  = (ttyp=="image")? ttop+thei : ttop+thei+1;
	 dads.left = tleft;
	 outObject = (arguments.length == 1) ? th : obj;
	 outButton = (arguments.length == 1) ? null : th; 
	 
	 //
	
	 for(i=0;i<strDateFormat.length;i++)
	 {
		 te = strDateFormat.substr(i,1);

		 if(te == "y")
		 {
			 yl+=1;
			 if(te != t)
			 {
				 ys = i;
				 t = te;
			 }
		 }

		 if(te == "M")
		 {
			 ml+=1;
			 if(te != t)
			 {
				 ms = i;
				 t = te;
			 }
		 }

		 if(te == "d")
		 {
			 dl+=1;
			 if(te != t)
			 {
				 ds = i;
				 t = te;
			 }
		 }
		
		 
		 
	 }
	 //
	 
	 
	
	 if(obj.value != ""){
	  

	  yy = obj.value.substr(ys,yl)
	  mm = obj.value.substr(ms,ml)
	  dd = obj.value.substr(ds,dl)

	  mm=mm-1; 
	  var d= new Date(yy, mm,dd); 
	  if(d.getFullYear()==yy && d.getMonth()==mm && d.getDate()==dd){
	   outDate=d;
	  }
	  else outDate="";
	   meizzSetDay(yy,mm+1);
	 }
	 else{
	  outDate="";
	  meizzSetDay(new Date().getFullYear(), new Date().getMonth() + 1);
	 }
	 dads.display = '';
	 event.returnValue=false;
}

var MonHead = new Array(12);         
    MonHead[0] = 31; MonHead[1] = 28; MonHead[2] = 31; MonHead[3] = 30; MonHead[4]  = 31; MonHead[5]  = 30;
    MonHead[6] = 31; MonHead[7] = 31; MonHead[8] = 30; MonHead[9] = 31; MonHead[10] = 30; MonHead[11] = 31;

var meizzTheYear=new Date().getFullYear(); 
var meizzTheMonth=new Date().getMonth()+1; 
var meizzWDay=new Array(40);               

function document.onclick() 
{ 
  with(window.event)
  { if (srcElement.getAttribute("Author")==null && srcElement != outObject && srcElement != outButton)
    closeLayer();
  }
}

function document.onkeyup() 
  {
    if (window.event.keyCode==27){
  if(outObject)outObject.blur();
  closeLayer();
 }
 else if(document.activeElement)
  if(document.activeElement.getAttribute("Author")==null && document.activeElement != outObject && document.activeElement != outButton)
  {
   closeLayer();
  }
  }

function meizzWriteHead(yy,mm) 
  {
 odatelayer.meizzYearHead.innerText  = String(yy);
    odatelayer.meizzMonthHead.innerText = athosMonthNumToName(String(mm));
  }

function athosMonthNameToNum(mn)
{
 switch (String(mn)) 
 {
   case "January" :
      return String(1);
   case "Feburary" :
      return String(2);
   case "March" :
   return String(3);
   case "April" :
      return String(4);
   case "May" :
      return String(5);
   case "June" :
      return String(6);
   case "July" :
   return String(7);
   case "August" :
      return String(8);
   case "September" :
      return String(9);
   case "October" :
      return String(10);
   case "November" :
   return String(11);
   case "December" :
      return String(12);
   default :
      return String(0);
 } 
}

function athosMonthNumToName(mm)
{
 switch (mm) 
 {
   case "1":
  return String("1-January");
   case "2":
  return String("2-Feburary");
   case "3":
  return String("3-March");  
   case "4":
  return String("4-April");
   case "5":
  return String("5-May");
   case "6":
  return String("6-June");
   case "7":
  return String("7-July");
   case "8":
  return String("8-August");
   case "9":
  return String("9-September");
   case "10":
  return String("10-October");
   case "11":
  return String("11-November");
   case "12":
  return String("12-December");
   default :
      return String("UnknownMonth");
 } 
}


function tmpSelectYearInnerHTML(strYear) 
{
  if (strYear.match(/\D/)!=null){alert("Year shall be a number.");return;}
  var m = (strYear) ? strYear : new Date().getFullYear();
  if (m < 1000 || m > 9999) {alert("Year shall between 1000 to 9999.");return;}
  //var n = m - 10;
  //if (n < 1000) n = 1000;
  //if (n + 26 > 9999) n = 9974;
  var s = "<select Author=meizz name=tmpSelectYear "
     s += "onblur='document.all.tmpSelectYearLayer.style.display=\"none\"' "
     s += "onchange='document.all.tmpSelectYearLayer.style.display=\"none\";"
     s += "parent.meizzTheYear = this.value; parent.meizzSetDay(parent.meizzTheYear,parent.meizzTheMonth)'>\r\n";
  var selectInnerHTML = s;
  for (var i = iMinYear; i <= iMaxYear; i++)
  {
    if (i == m)
       {selectInnerHTML += "<option Author=wayx value='" + i + "' selected>" + i + "</option>\r\n";}
    else {selectInnerHTML += "<option Author=wayx value='" + i + "'>" + i + "</option>\r\n";}
  }
  selectInnerHTML += "</select>";
  odatelayer.tmpSelectYearLayer.style.display="";
  odatelayer.tmpSelectYearLayer.innerHTML = selectInnerHTML;
  odatelayer.tmpSelectYear.focus();
}

function tmpSelectMonthInnerHTML(strMonth)
{
 if (strMonth.match(/\D/)!=null){alert("Month shall be a number");return;}
  var m = (strMonth) ? strMonth : new Date().getMonth() + 1;
  var s = "<select Author=meizz name=tmpSelectMonth "
     s += "onblur='document.all.tmpSelectMonthLayer.style.display=\"none\"' "
     s += "onchange='document.all.tmpSelectMonthLayer.style.display=\"none\";"
     s += "parent.meizzTheMonth = this.value; parent.meizzSetDay(parent.meizzTheYear,parent.meizzTheMonth)'>\r\n";
  var selectInnerHTML = s;
  for (var i = 1; i < 13; i++)
  {
    if (i == m)
       {selectInnerHTML += "<option Author=wayx value='"+i+"' selected>"+  athosMonthNumToName(String(i))        +"</option>\r\n";}
    else {selectInnerHTML += "<option Author=wayx value='"+i+"'>"+        athosMonthNumToName(String(i))         +"</option>\r\n";}
  }
  selectInnerHTML += "</select>";
  odatelayer.tmpSelectMonthLayer.style.display="";
  odatelayer.tmpSelectMonthLayer.innerHTML = selectInnerHTML;
  odatelayer.tmpSelectMonth.focus();
}

function closeLayer()               
  {
    document.all.meizzDateLayer.style.display="none";
  }
function clearAndCloseLayer()               
  {
  if (outObject)
  {
   outObject.value= ""; 
   closeLayer(); 
  }
  else {closeLayer(); alert("None control to output!");}    
  }

function IsPinYear(year)            
  {
    if (0==year%4&&((year%100!=0)||(year%400==0))) return true;else return false;
  }

function GetMonthCount(year,month)  
  {
    var c=MonHead[month-1];if((month==2)&&IsPinYear(year)) c++;return c;
  }
function GetDOW(day,month,year)     
  {
    var dt=new Date(year,month-1,day).getDay()/7; return dt;
  }


function meizzPrevY(intYears)  
  {
    if(meizzTheYear > 999 && meizzTheYear <10000){meizzTheYear-=intYears;}
    else{alert("Year beyond (1000-9999)!");}
    meizzSetDay(meizzTheYear,meizzTheMonth);
  }
function meizzNextY(intYears)  
  {
    if(meizzTheYear > 999 && meizzTheYear <10000){meizzTheYear+=intYears;}
    else{alert("Year beyond (1000-9999)!");}
    meizzSetDay(meizzTheYear,meizzTheMonth);
  }
function meizzToday()  
  {
 var today;
    meizzTheYear = new Date().getFullYear();
    meizzTheMonth = new Date().getMonth()+1;
    today=new Date().getDate();
    //meizzSetDay(meizzTheYear,meizzTheMonth);
    if(outObject){
  outObject.value=meizzTheYear + "-" + meizzTheMonth + "-" + today;
    }
    closeLayer();
  }
function meizzPrevM()  
  {
    if(meizzTheMonth>1){meizzTheMonth--}else{meizzTheYear--;meizzTheMonth=12;}
    meizzSetDay(meizzTheYear,meizzTheMonth);
  }
function meizzNextM()  
  {
    if(meizzTheMonth==12){meizzTheYear++;meizzTheMonth=1}else{meizzTheMonth++}
    meizzSetDay(meizzTheYear,meizzTheMonth);
  }

function meizzSetDay(yy,mm)   
{
  meizzWriteHead(yy,mm);
  meizzTheYear=yy;
  meizzTheMonth=mm;
  
  for (var i = 0; i < 40; i++){meizzWDay[i]=""};  
  var day1 = 1,day2=1,firstday = new Date(yy,mm-1,1).getDay();  
  for (i=0;i<firstday;i++)meizzWDay[i]=GetMonthCount(mm==1?yy-1:yy,mm==1?12:mm-1)-firstday+i+1 
  for (i = firstday; day1 < GetMonthCount(yy,mm)+1; i++){meizzWDay[i]=day1;day1++;}
  for (i=firstday+GetMonthCount(yy,mm);i<40;i++){meizzWDay[i]=day2;day2++}
  for (i = 0; i < 40; i++)
  { var da = eval("odatelayer.meizzDay"+i)
    if (meizzWDay[i]!="")
      { 
  //da.borderColorLight="#FF9900";
  //da.borderColorDark="#FFFFFF";
  if(i<firstday)  
  {
   da.innerHTML="<b><font color=gray>" + meizzWDay[i] + "</font></b>";
   da.title=(mm==1?12:mm-1) +"Mn" + meizzWDay[i] + "Dt";
   
   da.onclick=Function("meizzDayClick(this.innerText,-1)");
   if(!outDate)
    da.style.backgroundColor = ((mm==1?yy-1:yy) == new Date().getFullYear() && 
     (mm==1?12:mm-1) == new Date().getMonth()+1 && meizzWDay[i] == new Date().getDate()) ?
      "#FFD700":"#efefef";
   else
   {
    da.style.backgroundColor =((mm==1?yy-1:yy)==outDate.getFullYear() && (mm==1?12:mm-1)== outDate.getMonth() + 1 && 
    meizzWDay[i]==outDate.getDate())? "#ff0000" :
    (((mm==1?yy-1:yy) == new Date().getFullYear() && (mm==1?12:mm-1) == new Date().getMonth()+1 && 
    meizzWDay[i] == new Date().getDate()) ? "#FFD700":"#efefef");
    if((mm==1?yy-1:yy)==outDate.getFullYear() && (mm==1?12:mm-1)== outDate.getMonth() + 1 && 
    meizzWDay[i]==outDate.getDate())
    {
     //da.borderColorLight="#FFFFFF";
    // da.borderColorDark="#FF9900";
    }
   }
  }
  else if (i>=firstday+GetMonthCount(yy,mm))
  {
   da.innerHTML="<b><font color=gray>" + meizzWDay[i] + "</font></b>";
   da.title=(mm==12?1:mm+1) +"Mn" + meizzWDay[i] + "Dt";
   da.onclick=Function("meizzDayClick(this.innerText,1)");
   if(!outDate)
    da.style.backgroundColor = ((mm==12?yy+1:yy) == new Date().getFullYear() && 
     (mm==12?1:mm+1) == new Date().getMonth()+1 && meizzWDay[i] == new Date().getDate()) ?
      "#FFD700":"#efefef";
   else
   {
    da.style.backgroundColor =((mm==12?yy+1:yy)==outDate.getFullYear() && (mm==12?1:mm+1)== outDate.getMonth() + 1 && 
    meizzWDay[i]==outDate.getDate())? "#ff0000" :
    (((mm==12?yy+1:yy) == new Date().getFullYear() && (mm==12?1:mm+1) == new Date().getMonth()+1 && 
    meizzWDay[i] == new Date().getDate()) ? "#FFD700":"#efefef");
    if((mm==12?yy+1:yy)==outDate.getFullYear() && (mm==12?1:mm+1)== outDate.getMonth() + 1 && 
    meizzWDay[i]==outDate.getDate())
    {
    // da.borderColorLight="#FFFFFF";
     //da.borderColorDark="#FF9900";
    }
   }
  }
  else  
  {
   da.innerHTML="<b>" + meizzWDay[i] + "</b>";
   da.title=mm +"Mn" + meizzWDay[i] + "Dt";
   da.onclick=Function("meizzDayClick(this.innerText,0)");  
   
   if(!outDate)
    da.style.backgroundColor = (yy == new Date().getFullYear() && mm == new Date().getMonth()+1 && meizzWDay[i] == new Date().getDate())?
     "#FFD700":"#efefef";
   else
   {
    da.style.backgroundColor =(yy==outDate.getFullYear() && mm== outDate.getMonth() + 1 && meizzWDay[i]==outDate.getDate())?
     "#ff0000":((yy == new Date().getFullYear() && mm == new Date().getMonth()+1 && meizzWDay[i] == new Date().getDate())?
     "#FFD700":"#efefef");
    if(yy==outDate.getFullYear() && mm== outDate.getMonth() + 1 && meizzWDay[i]==outDate.getDate())
    {
     //da.borderColorLight="#FFFFFF";
     //da.borderColorDark="#FF9900";
    }
   }
  }
        da.style.cursor="hand"
      }
    else{da.innerHTML="";da.style.backgroundColor="";da.style.cursor="default"}
  }
}

//===================pontus 0523=

function pontusDayMouseOver(col)
{
	alert("a");
	eval(col).style.border = " 1px solid #000000";

}

//==============

function meizzDayClick(n,ex)
{
	var yy=meizzTheYear;
	var theDate = strDateFormat;
	var mm = parseInt(meizzTheMonth)+ex; 
	if(mm<1){
		yy--;
		mm=12+mm;
	}
	else if(mm>12){
		yy++;
		mm=mm-12;
	}
 
	if (outObject)
	{

		//theDate = yy+"-"+mm + "-" + n;

		//nian
		theDate = theDate.replace(/yyyy/g, yy);
		
		theDate = theDate.replace(/yy/g, yy.toString().substring(2,2));
		//yue
		
		if (mm < 10){
			theDate = theDate.replace(/MM/g, "0" + mm);
		}else{
			theDate = theDate.replace(/MM/g, mm);
		}
		theDate = theDate.replace(/M/g, mm);

		//ri
		if (n < 10){
			theDate = theDate.replace(/dd/g, "0" + n);
		}else{
			theDate = theDate.replace(/dd/g, n);
		}
		theDate = theDate.replace(/d/g, n);

		outObject.value= theDate ; 
		closeLayer(); 
	}
	else {closeLayer(); alert("None control to output!");}
}