function minimize_window()
{
  window.parent.hh1.Click();
}

function get_sys_time()
{
   var d, s = "";
   var tempstring;
   d = new Date();
   tempstring = d.getHours();
   if (tempstring.toString().length<2)   //如果小时只有一位，前面补"0"
   {
	 s += "0";
	 s += d.getHours();
   }
   else
   {
	 s += d.getHours();
   }
   s += "：";
   tempstring = d.getMinutes();
   if (tempstring.toString().length<2)   //如果分钟只有一位，前面补"0"
   {
	 s += "0";
	 s += d.getMinutes();
   }
   else
   {
	 s += d.getMinutes();
   }
   document.all("sys_time").innerText = s;
   //alert(document.all("time").innerText);
}
function minimize_left_window()
{
  parent.mainframe.cols='0,*';
}
function get_unit_name()
{
  if (parent.window.opener)
  {
	document.all("unit_name").innerText = parent.window.opener.document.all("login_name").value;
  }
}
function clean_opener()
{
  if (parent.window.opener)
  {
	 //parent.window.opener.document.all("login_name").innerText = "";
	 // parent.window.opener.document.all("pin").innerText = "";
  }
}
function do_shrink()
{
  parent.main.rows="17,*";
  document.all("main_menu_top").style.display = "none";
  document.body.style.backgroundImage = "url(images/pixel_bk_tall.jpg)";
  document.all("main_menu_expand").style.display = "block";
  document.all("main_menu_shrink").style.display = "none";
}
function do_expand()
{
  parent.main.rows="122,*";
  document.all("main_menu_top").style.display = "block";
  document.body.style.backgroundImage = "url(images/main_bk.jpg)";
  document.all("main_menu_expand").style.display = "none";
  document.all("main_menu_shrink").style.display = "block";
}

function set_help_file_name(code)
{
	document.all("help_file_name").value = "";
	document.all("help_file_name").value = code;
}
function set_help_code(code)
{
	document.all("help_code").value = "";
	document.all("help_code").value = code;
}
function show_help_file()
{
  var sUrl = "./help/help";
  var tempcode;
  var tempUrl;
  tempUrl = document.all("help_file_name").value;
  tempcode = document.all("help_code").value;
  if (tempUrl != "")
  {
	  sUrl = sUrl + tempUrl.substring(2,4);
	  sUrl = sUrl + ".jsp";
	  if (tempcode != "")
	  {
		  if (sUrl == "./help/help04.jsp")
			  alert("对不起,本模块暂时没有帮助文件!");
		  else
			  window.showModalDialog(sUrl,tempcode,"dialogHeight:500px; dialogWidth: 650px; dialogTop: 100px; dialogLeft: 150px; edge: Sunken;center: No; help: No; resizable: No; status: Yes;");
	  }
  }
}

	var FirstMenu = new Array(["物品管理","goods","0","9901"],
				  ["成本管理","cost","0","9902"],
				  ["报表管理","report","0","9903"],
				  ["系统管理","system","0","9904"],
				  ["接口系统","interface","0","9905"]);
	var SecondMenu = new Array([["录入","0","990101"],
						["支付","0","990102"]],
				   [["录入","0","990201"],
					["修改","0","990202"],
					["查询","0","990204"],
					["其它","0","990206"]],
				   [["台帐相关报表","0","990301"],
					["票据相关报表","0","990302"],
					["收费相关报表","0","990303"],
					["其他报表","0","990304"],
					["罚款报表","0","990305"],
					["物品库存报表","0","990306"],
					["物品处理报表","0","990307"],
					["拍卖报表","0","990308"]],
	 [["银行代收罚款","0","990401"],
	  ["单位代收罚款","0","990402"],
	  ["离线罚款","0","990403"],
	  ["罚款书维护","0","990404"],
	  ["申请书维护","0","990405"],
	  ["暂扣财物","0","990406"],
	  ["暂扣财物处理","0","990407"],
	  ["暂扣款管理","0","990408"],
	  ["案件管理","0","990409"],
	  ["没收物品拍卖","0","990410"]],
				   [["收费项目管理","0","990501"],
					["单位管理","0","990502"],
					["用户管理","0","990503"],
					["角色管理","0","990504"],
					["权限管理","0","990505"],
					["区域管理","0","990506"],
					["银行帐号管理","0","990507"],
	 ["系统维护","0","990508"],
	 ["罚没项目管理","0","990509"],
	 ["拍卖行管理","0","990509"]]
				   );
	var imgPath = "images/";
	function fnGetOpCode(sURL)
	{
		var opCode = sURL.substring(sURL.indexOf("opCode=")+7);
		opCode = opCode.substring(0,(opCode.indexOf("&")==-1?opCode.length:opCode.indexOf("&")));
		return opCode;
	}
	function fnInitTopMenu()
	{
		setTimeout("showTopMenu()",1000);
	}
	function showTopMenu()
	{
		var i,j,k,l;
		/*
		for(i=0;i<ThirdMenu.length; i++ )
		{
			for(j=0;j<ThirdMenu[i].length; j++)
			{
				for(k=0;k<ThirdMenu[i][j].length;k++)
				{
					var opCode = fnGetOpCode(ThirdMenu[i][j][k][1]);
					for(l=0;l<UserOpCode.length; l++)
						if(opCode==UserOpCode[l])
						{
							ThirdMenu[i][j][k][2]=UserOpCode[l];
							SecondMenu[i][j][1]="1";
							FirstMenu[i][2]="1";
							break;
						}
				}
			}
		}
		var k=-1;
		var prevJ=-1;*/
		for(l=0; l<UserOpCode.length; l++)
		{
			var opCode = UserOpCode[l];
			var i = parseInt(opCode.substring(0,1))-1;
			j = parseInt(opCode.substring(1,3),10);
			//by Legend, auto decide the third menu index, so the opcode donot need to be coded in sequence
			/*if (j==prevJ) {
				k++;
			} else {
				prevJ = j;
				k=0;
			}*/
						var k = 0;
			while (ThirdMenu[i][j][k][1].indexOf(opCode)==-1)
						{
						  k++;
						  if (k == ThirdMenu[i][j].length) break;
			}
			//k = parseInt(opCode.substring(3,5),10); //remove by Legend
			//end of modification by Legend
						if (k < ThirdMenu[i][j].length)
						{
						  ThirdMenu[i][j][k][2]=opCode;
						  SecondMenu[i][j][1]="1";
						  FirstMenu[i][2]="1";
						}
		}
		var sHtml;
		sHtml = "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" name=\"main_menu_table\" id=\"main_menu_table\">\n";
		sHtml += "<tr>\n";
		for(i=0;i<FirstMenu.length;i++)
			if(FirstMenu[i][2]=="1")
				sHtml += "<td><img id=\"TopImg\" style=\"CURSOR: hand\" src=\"images/"+FirstMenu[i][1]+"_white.gif\" border=\"0\" alt=\""+FirstMenu[i][0]+"\" onMouseOver=\"playSound(0)\" onMouseOut=\"stopSound(0)\" MenuIndex=\""+i+"\" onclick=\"set_help_file_name("+FirstMenu[i][3]+");set_help_code("+FirstMenu[i][3]+")\"></td>\n";
				sHtml += "<td valign=\"bottom\" style=\"width:110px;\">&nbsp;&nbsp;<a href=\"#\" onMouseOver=\"playSound(0)\" onMouseOut=\"stopSound(0)\" onclick=\"playSound(1);\" id=\"main2\"><img src=\"images/help.gif\" border=0 onclick=\"show_help_file();\"></a>&nbsp;<a href=\"#\" onMouseOver=\"playSound(0)\" onMouseOut=\"stopSound(0)\" onclick=\"playSound(1);onLogout();\" id=\"main2\"><img src=\"images/exit.gif\" border=0></a>&nbsp;&nbsp;<a href=\"#\" onMouseOver=\"playSound(0)\" onMouseOut=\"stopSound(0)\" onclick=\"playSound(1);minimize_window();\" id=\"main2\"><img src=\"images/minimize.gif\" border=0></a></td>\n";
		sHtml += "</tr>\n";
		sHtml += "</table>";
		window.document.all("TopMenu").innerHTML = sHtml;
	}
	function ShowSecondMenu()
	{
		if(event.srcElement.id == "TopImg")
		{
			if(typeof(window.parent.main_text.RemindFlag)=="undefined" || window.parent.main_text.RemindFlag==false)
			{
				recover_left_window();
				playSound(1);
				var oItemList = window.document.all("TopImg");
				var i,j;
				i = event.srcElement.MenuIndex;
				for(j=0;j<oItemList.length;j++)
				{
					var srcImg = oItemList[j].src;
					srcImg = srcImg.substring(0,srcImg.indexOf("_"));
					srcImg = srcImg + "_white.gif";
					oItemList[j].src = srcImg;
				}
				var evtImg = event.srcElement.src;
				evtImg = evtImg.substring(0,evtImg.indexOf("_"));
				evtImg = evtImg + "_blue.gif";
				event.srcElement.src = evtImg;
				showmenu('main_text',FirstMenu[i][1]+"_welcome.htm");
				clean_prompt();
								//window.parent.secondary_menu.document.all("MenuTable").style.display = "block";
								//window.parent.secondary_menu.document.all("menu_expand").style.display = "none";
								//window.parent.secondary_menu.document.all("menu_shrink").style.display = "block";
								window.parent.secondary_menu.do_menu_expand();
				window.parent.secondary_menu.fnInit(SecondMenu[i],ThirdMenu[i],FirstMenu[i][1]);
			}
			else
			{
				alert(window.parent.main_text.RemindMsg);

			}
		}
	}
	function clean_prompt()
	{
		var frm = window.parent.frames;
		for (i=0; i < frm.length; i++)
		 {
			if (frm(i).name == "prompt")
				if(frm(i))
					if(frm(i).document)
						if(frm(i).document.all.prompt_text)
						{
							frm(i).document.all.prompt_text.innerText="";
							}
		}
	}
	function showmenu(frame_name,addr)
	{
		if(typeof(window.parent.main_text.RemindFlag)=="undefined" || window.parent.main_text.RemindFlag==false)
		{
			var frm = window.parent.frames;
			for (i=0; i < frm.length; i++)
			{
				if (frm(i).name == frame_name)
				{
					frm(i).location.href=addr;
				}
			}
		}
		else
		{
			alert(window.parent.main_text.RemindMsg);
		}
	}
	function recover_left_window()
	{
		parent.mainframe.cols='155,*';
	}
