<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/jsp/taglibs.jsp"%>
<html>
<head>
<title>queryClient</title>
<%@ include file="/commons/jsp/meta.jsp"%>
<link href="../../shaoyx/css/style.css" rel="stylesheet" type="text/css">
<link href="ka.css" rel="stylesheet" type="text/css">
<script language="javascript">
　scores = new Array(20);
　　var numTotal=0;NS4 = (document.layers) ? 1 : 0;IE4 = (document.all) ? 1 : 0;ver4 = (NS4 || IE4) ? 1 : 0;if (ver4)　　　{    with (document) 
{       
　　 write("<STYLE TYPE='text/css'>");        
if (NS4) {            write(".parent {position:absolute; visibility:visible}");  
          write(".child {position:absolute; visibility:visible}"); 
          write(".regular {position:absolute; visibility:visible}")
        }        else {            write(".child {display:none}")
        }        write("</STYLE>");    }}function getIndex(el)
 {    ind = null;    for (i=0; i<document.layers.length; i++)
 {        whichEl = document.layers[i];
        if (whichEl.id == el)
 {            ind = i;            break;        }    
}
    return ind;}function arrange()
 {
    nextY = document.layers[firstInd].pageY +document.layers[firstInd].document.height;
    for (i=firstInd+1; i<document.layers.length; i++)
 {
        whichEl = document.layers[i];
        if (whichEl.visibility != "hide")
 {            whichEl.pageY = nextY;
            nextY += whichEl.document.height;
        }    }}function initIt(){    if (!ver4) return;
    if (NS4) {        for (i=0; i<document.layers.length; i++)
 {            whichEl = document.layers[i];
            if (whichEl.id.indexOf("Child") != -1) whichEl.visibility = "hide";
       }        arrange();
    }    else
 {        divColl = document.all.tags("DIV");
        for (i=0; i<divColl.length; i++)
 {            whichEl = divColl(i);
            if (whichEl.className == "child") whichEl.style.display = "none";
        }    }}function expandIt(el) {if (!ver4) return;
    if (IE4) {
        whichEl1 = eval(el + "Child");for(i=1;i<=numTotal;i++){whichEl = eval(scores[i] + "Child");if(whichEl!=whichEl1) {whichEl.style.display = "none";}}  
      whichEl1 = eval(el + "Child");  
      if (whichEl1.style.display == "none") { 
           whichEl1.style.display = "block";
        }       
 else {            whichEl1.style.display = "none";
        }    } 
   else {        whichEl = eval("document." + el + "Child");for(i=1;i<=numTotal;i++){whichEl = eval("document." + scores[i] + "Child");if(whichEl!=whichEl1) {whichEl.visibility = "hide";}}        if (whichEl.visibility == "hide") {            whichEl.visibility = "show"; 
       }        else {            whichEl.visibility = "hide";        }        arrange();    }}onload = initIt;
function openUrl(url){
	window.open(url,'','menubar=no,toolbar=no,location=no,directories=no,status=no,scrollbars=1,resizable=1,height=600,width=800');
}  
  NS4   =   (document.layers)   ?   1   :   0;   
  IE4   =   (document.all)   ?   1   :   0;   
  ver4   =   (NS4   ||   IE4)   ?   1   :   0;   
    
  if   (ver4)   {   
          with   (document)   {   
                  write("<STYLE   TYPE='text/css'>");   
                  if   (NS4)   {   
                          write(".parent   {position:absolute;   visibility:visible}");   
                          write(".child   {position:absolute;   visibility:visible}");   
                          write(".regular   {position:absolute;   visibility:visible}")   
                  }   
                  else   {   
                          write(".child   {display:none}")   
                  }   
                  write("</STYLE>");   
          }   
  }   
    
  function   getIndex(el)   {   
          ind   =   null;   
          for   (i=0;   i<document.layers.length;   i++)   {   
                  whichEl   =   document.layers[i];   
                  if   (whichEl.id   ==   el)   {   
                          ind   =   i;   
                          break;   
                  }   
          }   
          return   ind;   
  }   
    
  function   arrange()   {   
          nextY   =   document.layers[firstInd].pageY   +document.layers[firstInd].document.height;   
          for   (i=firstInd+1;   i<document.layers.length;   i++)   {   
                  whichEl   =   document.layers[i];   
                  if   (whichEl.visibility   !=   "hide")   {   
                          whichEl.pageY   =   nextY;   
                          nextY   +=   whichEl.document.height;   
                  }   
          }   
  }  
   
    
  function   initIt(){   
          if   (!ver4)   return;   
          if   (NS4)   {   
                  for   (i=0;   i<document.layers.length;   i++)   {   
                          whichEl   =   document.layers[i];   
                          if   (whichEl.id.indexOf("Child")   !=   -1)   whichEl.visibility   =   "hide";   
                }   
                  arrange();   
          }   
          else   {   
                  divColl   =   document.all.tags("DIV");   
                  for   (i=0;   i<divColl.length;   i++)   {   
                          whichEl   =   divColl(i);   
                          if   (whichEl.className   ==   "child")   whichEl.style.display   =   "none";   
                  }   
          }   
  }   
    
  function   expandIt(el)   {   
          if   (!ver4)   return;   
          if   (IE4)   {   
                  whichEl   =   eval(el   +   "Child");   
                  if   (whichEl.style.display   ==   "none")   {   
                          whichEl.style.display   =   "block";   
                  }   
                  else   {   
                          whichEl.style.display   =   "none";   
                  }   
          }   
          else   {   
                  whichEl   =   eval("document."   +   el   +   "Child");   
                  if   (whichEl.visibility   ==   "hide")   {   
                          whichEl.visibility   =   "show";   
                  }   
                  else   {   
                          whichEl.visibility   =   "hide";   
                  }   
                  arrange();   
          }   
  }   
  onload   =   initIt;    
  if   (NS4)   {   
                  firstEl   =   "KB1Parent";   
                  firstInd   =   getIndex(firstEl);   
                  arrange();   
  } 

function switchTag(tag,content)
{
//	alert(tag);
//	alert(content);
	for(i=1; i <6; i++)
	{
		if ("tag"+i==tag)
		{
			document.getElementById(tag).getElementsByTagName("a")[0].className="selectli"+i;
			document.getElementById(tag).getElementsByTagName("a")[0].getElementsByTagName("span")[0].className="selectspan"+i;
		}else{
			document.getElementById("tag"+i).getElementsByTagName("a")[0].className="";
			document.getElementById("tag"+i).getElementsByTagName("a")[0].getElementsByTagName("span")[0].className="";
		}
		if ("content"+i==content)
		{
			document.getElementById(content).className="";
		}else{
			document.getElementById("content"+i).className="hidecontent";
		}
		document.getElementById("content").className=content;
	}
}  
  </SCRIPT>
</head>
<style>
html { overflow-x:hidden;overflow-y:hidden;  }
</style>
<body>
<s:form action="supplyQuery" theme="simple" target="rightFrame">
<s:hidden name="resetCondition" value="true"/>

<table height="50%"  border=0  cellpadding=1 cellspacing=0>

<tr>
            	<td colspan="2" align="center" class="bg_table01"  height="0.5"><img src="./../images/temp.gif" width="1" height="1"></td>
       	  </tr>
	            <tr class="bg_table02">
                  <td align="center" nowrap class="bg_table02"><div align="right">供应商名称：</div></td>
	              <td nowrap> <s:textfield name="supplierName" size="15"></s:textfield>
  </tr>
	            <tr class="bg_table02">
                  <td align="center" nowrap class="bg_table02"><div align="right">供应商类别：</div></td>
	              <td nowrap><s:select name="supplierType" list="supplierTypeList" listKey="typeSmall"
				listValue="typeName" required="true" emptyOption="true" headerValue="">
                  
                </s:select>              </td>
  </tr>
	            
	            <tr class="bg_table02">
	              <td align="center" nowrap class="bg_table02"><div align="right">供应商地域：</div>	                
	                
	               <td nowrap><s:select name="eareCode" list="supplyArea" listKey="typeSmall"
				listValue="typeName" required="true" emptyOption="true" headerValue="">
                  
                </s:select>              </td>
	            </tr>
	            
	           

            <tr class="bg_table02">
			  <td colspan="2" nowrap class="bg_table04"><div align="center">
			    	<input class="button01" type="submit" name="Input" value="查  询"  />
		      </div></td>
  </tr>
		</table>
		</s:form>		
</body>
</html>
