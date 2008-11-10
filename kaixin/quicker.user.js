// ==UserScript==
// @name           kaixin Quicker
// @namespace      http://www.iam.com
// @include        http://www.kaixin001.com/slave/*
// @include        http://www.kaixin001.com/app/app.php?aid=1028*
// @include        http://www.kaixin001.com/bite/*
// @include        http://www.kaixin001.com/app/app.php?aid=1040*
//
// @exclude		   http://www.kaixin001.com/app/app.php?aid=1028*uid=*
// @exclude		   http://www.kaixin001.com/app/app.php?aid=1040&url=help.php*
// @exclude		   http://www.kaixin001.com/app/app.php?aid=1028&url=top.php*
// @author		   avidmouse@gmail.com
// @version		   0.4
// ==/UserScript==

/*qObj*/
var qObj = function(/*String*/path, /*Dom*/dom){
	var domain = dom ? dom : document;
	this.nodes = domain.evaluate(path, domain, null, 6, null);
};

qObj.prototype = {
	item: function(/*int*/index){
		return this.nodes.snapshotItem(index);
	},
	length: function(){
		return this.nodes.snapshotLength;
	},
	each: function(/*func*/act){
		for(var i = 0; i < this.nodes.snapshotLength; i++){
			act(this.item(i));
		}
	}
};

function query(/*String*/path, /*Dom*/ dom){
	return new qObj(path, dom);
}
window.query = query;

/*JsFlow*/
var JsFlow = function(){
	this.acts = [];
	this.loopParams = [];
};

JsFlow.prototype = {
	delay: function(millisec){
		this.delay = millisec;	
		return this;
	},
	add: function(/*func*/act){
		this.acts[this.acts.length] = act;
		return this;
	},
	loop: function(p){
		this.loopParams.push(p);
		return this;
	},
	loopForParams: function(loopParams){
		this.loopParams = loopParams;
		return this;
	},
	end: function(/*func*/end){
		this.end = end; 
		return this;
	},
	go_on: function(params){
		if (this.acts.length == this.flowIndex){
			this.next(params);
			return;
		}
		var flow = this;
		setTimeout(function(){
			flow.acts[flow.flowIndex ++](params, flow);
		}, flow.delay);
	},
	next: function(params){
		var p = this.getParams(params);
		if (!p){
			this.end(params);
			return;
		}
		this.flowIndex = 0;
		this.go_on(p);
	},
	getParams: function(params){
		if (this.loopParams.length == this.loopIndex){
			return null;
		}
		return this.loopParams[this.loopIndex ++];
	},
	setParamsGeter: function(getterFunc){
		this.getParams = getterFunc;
		return this;
	},
	start: function(params){
		this.loopIndex = 0;
		this.finished = false;
		if (params){
			this.loopParams = [params];
		}
		this.next();
	}
};
//this.JsFlow = JsFlow;

/*serializer*/
window.qSerializer = function(){
	this.s = [];
};

window.qSerializer.prototype = {
	add: function(key, value){
		this.s[ this.s.length ] = encodeURIComponent(key) + '=' + encodeURIComponent(value);		 
		return this;
	},
	addObj: function(obj){
		for(var p in obj)
			this.add(p, obj[p]);
		return this;
	},
	toString: function(){
		return this.s.join('&').replace(/%20/g, "+");
	}
};

function arrayInsert(arr, index, value){
	var ba = arr.slice(0, index);
	var ea = arr.slice(index);
	ba[index] = value;
	return ba.concat(ea);
}

function arrayDel(arr, delIndex){
	var ba = arr.slice(0, delIndex);
	var ea = arr.slice(delIndex);
	ea.shift();
	return ba.concat(ea);
}

function arrayChange(arr, from, to){
	var result = arrayInsert(arr, to, arr[from]);
	return arrayDel(result, from + ((from - to) > 0 ? 1 : 0));
}

function addTimer(name, act, millisec){
}

function evalStr(str){
	return eval('(' + str + ')');
}

function searchVal(text, regex, begin, end){
	var result = text.match(regex).toString();
	return result.substring(begin, result.length - end);
}
window.searchVal = searchVal;

function addCheckBox(/*String*/beAppendPath, /*func*/click, /*function(appendNodeChildNodes, checkBox)*/initBox, /*int*/appendBeforeItemNum){
	window.selectedArr = [];
	window.checkBoxClick =click;
	query(beAppendPath).each(function(node){
			var sItems = node.childNodes;
			var selBox = document.createElement('input');
			selBox.type = "checkbox";
			initBox(sItems, selBox);
			selBox.addEventListener('click', window.checkBoxClick, false);
			sItems.item(0).parentNode.insertBefore(selBox, sItems.item(appendBeforeItemNum));
		});
}

function checkBoxId(sid){
	return "checkBox_" + sid;
}
window.checkBoxId = checkBoxId;
function addBtn(/*String*/beAppendPath, /*String*/value, /*function*/click, /*String*/id){
	var beAppendNode = query(beAppendPath);
	var actBtn = document.createElement('input');
	actBtn.type = 'button';
	actBtn.value = value;
	actBtn.id = id;
	window[value] = click;
	actBtn.className = beAppendNode.item(0).className;
	actBtn.addEventListener('click', window[value], false); 
	beAppendNode.item(0).parentNode.insertBefore(actBtn, beAppendNode.item(0));
}

function addLnk(/*String*/beAppendPath, /*String*/value, /*function*/click, /*String*/id, className){
	var beAppendNode = query(beAppendPath);
	var lnk = document.createElement('a');
	lnk.appendChild(document.createTextNode(value));
	lnk.id = id;
	window[value] = click;
	lnk.className = className ? className : beAppendNode.item(0).className;
	lnk.addEventListener('click', window[value], false); 
	beAppendNode.item(0).appendChild(lnk);
	return lnk;
}

function createSelect(id, checkBox){
	var result = document.createElement('select');
	result.id = id;
	result.style.visibility = 'hidden';
	result.style.width = '50px';
	checkBox.parentNode.insertBefore(result, checkBox.nextSibling);
	return result;
}

function initOption(sel, text, regex, /*function(option, lineText*/initFunc){
	var rs = text.match(regex);
	if(rs && rs.length > 0){
		for(var i = 0; i < rs.length; i++){
			var o = document.createElement('option');
			initFunc(o, rs[i]);
			sel.add(o, null);
		}
		return true;
	}
	return false;
}

function initSelParams(){
	window.selectedArr = [];

	window.closePopWindow = function(href, id){
		var allSucceed = true;
		var checkBoxs = window.query("//input[@type='checkbox']");
		for(var i = 0; i < checkBoxs.length(); i++){
			if(checkBoxs.item(i).title == "Error"){
				allSucceed = false;
				break;
			}
		}
		if (allSucceed && confirm("搞定了！返回主页面?"))
			parent.window.location.href = href;
		document.getElementById(id).disabled = false;
		if (!allSucceed && confirm("部分操作没有成功，是否返回主页面？"))
			parent.window.location.href = href;
	};

	window.reportActStatus = function(sid, succeed){
		window.selectedArr[sid] = false;
		var checkBox = document.getElementById(window.checkBoxId(sid));	
		if(succeed){
			checkBox.checked = false;
			checkBox.disabled = true;
			checkBox.parentNode.style.backgroundColor = "blue";
		}else{
			checkBox.parentNode.style.backgroundColor = "red";
			checkBox.title = 'Error';
		}
	};
	window.initLoop = function(flow){
		for(var id in window.selectedArr){
			if (window.selectedArr[id])
				flow.loop(id);
		}
	};
}

window.openQDialog = function(title, head){
	unsafeWindow.openWindow('', 460, 460, title);
	document.getElementById('dialogBody').innerHTML = '\
<div style="display:block;width:445px;margin:0 auto;">\
		<div style="height:390px;">\
			<div class="tac f14 mb10"><b>' + head + '</b></div>\
			<div id="q_dialog_body" class="comp_list mb10">\
			</div>\
		</div>\
		<div style="height:40px;border-top:1px solid #ccc;background:#F2F2F2;">\
				<div class="r" style="width:10px;">&nbsp;</div>\
				<div class="rbs1 mt5" style="float:right;">\
						<input id="q_dlg_ok" type="button" value="确定" class="rb1-12" onmouseover="this.className=\'rb2-12\';" onmouseout="this\
						.className=\'rb1-12\';"  style="padding:4px 10px;" />\
						<input type="button" value="取消" class="rb1-12" onmouseover="this.className=\'rb2-12\';" onmouseout="this\
						.className=\'rb1-12\';"  style="padding:4px 10px;" onclick="new parent.dialog().reset();" />\
						</div>\
		</div>\
</div>\
<style>\
		.rbs1{border:1px solid #d7e7fe; float:left;}\
		.rb1-12,.rb2-12{height:23px; color:#fff; font-size:12px; background:#355582; padding:3px 5px; border-left\
				:1px solid #fff; border-top:1px solid #fff; border-right:1px solid #6a6a6a; border-bottom:1px solid \
				#6a6a6a; cursor:pointer;}\
		.rb2-12{background:#355582;}\
		.comp_list{border:1px solid #cccccc; width:270px; height:330px; overflow-y:scroll; overflow-x:hidden\
				; margin:0 auto;} \
		.comp_list ul.fc{width:270px;list-style:none; margin:0px; padding:5px 0px; height:16px; border-bottom\
				:dotted 1px #ccc;}\
</style>\
		';
}

window.qOpenWindowAddItem = function(itemLi){
	var ul = document.createElement("ul");
	ul.className = "fc";
	ul.appendChild(itemLi);
	document.getElementById("q_dialog_body").appendChild(ul);
};

// sel slave
function doSelSlave(){
	query("//div[@class='czhy_tx']").item(0).style.height = '370px';
	initSelParams();
	window.currentTotal = query("//strong[@class='dgreen']").item(0).innerHTML.substr(1);

	window.qBuySlave = function(){
		var flow = new JsFlow().add(window.getVerify).add(window.initBuyData).add(window.doBuy).delay(1000).end(function(){
				window.closePopWindow('http://www.kaixin001.com/app/app.php?aid=1028', 'buyBtn');
			});
		window.initLoop(flow);
		flow.start();
	};

	window.getVerify = function(sid, f){
		GM_xmlhttpRequest({
			method: 'GET',
			url: 'http://www.kaixin001.com/app/app.php?aid=1028&url=index.php&uid=' + sid,
			onload: function(response){
				f.go_on({sid: sid, verify: window.searchVal(response.responseText, /g_verify = \"\w*\"/, 12, 1)});
			}
		});
	
	};

	window.initBuyData = function(p, f){
		var buyUrl = 'http://www.kaixin001.com/slave/buy_dialog.php?slaveuid='+p.sid+'&verify='+p.verify+'&rand='+Math.random();
		GM_xmlhttpRequest({
			method: 'GET',
			url: buyUrl,
			onload: function(dr){
				var rText = dr.responseText;
				var varIndex = rText.indexOf("var  ");
				var accFun = rText.substring(varIndex, rText.indexOf("script", varIndex) - 2);
				eval(accFun);
				var vIndex = rText.indexOf('name="verify" value="');
				var verify = rText.substring(vIndex + 21, rText.indexOf('"', vIndex + 21));
				var d = new window.qSerializer().add('acc', acc()).add('verify', verify).add('slaveuid', p.sid).add('nick', document.getElementById('nick_' + p.sid).value).add('verify', verify).add('slaveuid', p.sid).toString();
				f.go_on({sid: p.sid, d: d});
			}
		});
	};

	window.doBuy = function(p, f){
		GM_xmlhttpRequest({
			method:	'POST',
			url: 'http://www.kaixin001.com/slave/buy.php',
			headers: {
				'User-agent': 'Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3',
				'Content-type': 'application/x-www-form-urlencoded'
			},
			data: p.d,
			onload: function(br){
				window.reportActStatus(p.sid, br.responseText.indexOf("已经成为") > 0);
				f.go_on();
			}
		});
	};

	addCheckBox("//div[@class='mt30']/div", function(){
			var vo = eval(this.value);
			var val = vo.val;
			var sid = vo.sid;
			var nickEle = document.getElementById("nick_" + sid);
			if (!nickEle){
				nickEle = document.createElement('input');
				nickEle.type = 'text';
				nickEle.id = "nick_" + sid;
				nickEle.style.visibility = 'hidden';
				nickEle.style.width = '80px';
				var name = this.previousSibling.firstChild.text;
				name = name.substr(name.length -1);
				nickEle.value = name + name;
				this.parentNode.insertBefore(nickEle, this.nextSibling);
			}
			if (window.selectedArr[sid] && !this.checked){
				window.currentTotal += val;
				nickEle.style.visibility = 'hidden';
			} else {
				if (window.currentTotal - val < 0){
					alert('太贪心了吧？就剩:' + window.currentTotal + '大毛了!!!');
					this.checked = false;
				} else{
					window.currentTotal -= val;
					nickEle.style.visibility = 'visible';
				}
			}
			window.selectedArr[sid] = this.checked;
		},
		function(appendNodeChildNodes, checkBox){
			var val = appendNodeChildNodes.item(3).innerHTML.substr(5, appendNodeChildNodes.item(3).innerHTML.length -6);
			var sid = appendNodeChildNodes.item(1).innerHTML.match(/\(\d+/);
			checkBox.value = '({val:' + val + ',sid:' + sid.toString().substr(1) + '})';		
			checkBox.id = checkBoxId(sid.toString().substr(1));
		}, 2); 

	addBtn("//input[@type='button']", '购买选中奴隶！', function(){
			this.disabled = true;
			window.qBuySlave();
		}, 'buyBtn');
}

// append slave index
function doAppendSlaveIndex(){
	initSelParams();
	window.comfortCheckBoxClick = function(){
		window.checkBoxClick(this, 'http://www.kaixin001.com/slave/comfort_dialog.php?slaveuid=', /type=\"radio\" name=\"comforttype\".*/g);
	}
	window.painCheckBoxClick = function(){
		window.checkBoxClick(this, 'http://www.kaixin001.com/slave/pain_dialog.php?slaveuid=', /type=\"radio\" name=\"paintype\".*/g);
	}

	window.checkBoxClick = function(checkBox, url, opRegEx){
			var sid = checkBox.value;
			var sEle = document.getElementById("c_" + sid);
			if (!sEle){
				sEle = createSelect("c_" + sid, checkBox);
				sEle.style.width = "150px";
				var u = url + sid + '&verify=' + window.searchVal(parent.document.body.innerHTML, /g_verify = \"\w*\"/, 12, 1) + '&rand=' + Math.random();
				GM_xmlhttpRequest({
					method: 'GET',
					url: u,
					onload: function(resp){
						if (!initOption(sEle, resp.responseText, opRegEx, function(o, s){
							var valueBegin = s.indexOf('value="') + 7;
							var valueEnd = s.indexOf('"', valueBegin);
							o.value = s.substring(valueBegin, valueEnd);
							o.text = s.substring(valueEnd + 2, s.indexOf("</li>"));	
						})){
							checkBox.checked = false;
							checkBox.disabled = true;
						} 
						sEle.style.visibility = checkBox.checked ? 'visible' : 'hidden';
						window.selectedArr[sid] = checkBox.checked;
					}
				});
			} else {
				sEle.style.visibility = checkBox.checked ? 'visible' : 'hidden';
				window.selectedArr[sid] = checkBox.checked;
			}
	};

	window.comfortClick = function(){
		startFlow(window.qComfort);
	};

	window.painClick = function(){
		startFlow(window.qPain);
	};

	function startFlow(act){
		var flow = new JsFlow().add(act).delay(500).end(function(){
				window.closePopWindow('http://www.kaixin001.com/app/app.php?aid=1028', 'q_dlg_ok');
			});
		window.initLoop(flow);
		flow.start();
	}

	window.qComfort = function(sid, f){
		GM_xmlhttpRequest({
			method:	'GET',
			url: 'http://www.kaixin001.com/slave/comfort1.php?verify=' + window.searchVal(parent.document.body.innerHTML, /g_verify = \"\w*\"/, 12, 1) + '&slaveuid=' + sid + '&comforttype=' + document.getElementById("c_" + sid).value,
			onload: function(br){
				window.reportActStatus(sid, br.responseText.indexOf("挣回") > 0);
				f.go_on();
			}
		});
	}

	window.qPain = function(sid, f){
		GM_xmlhttpRequest({
			method:	'GET',
			url: 'http://www.kaixin001.com/slave/pain1_submit.php?verify=' + window.searchVal(parent.document.body.innerHTML, /g_verify = \"\w*\"/, 12, 1) + '&slaveuid=' + sid + '&paintype=' + document.getElementById("c_" + sid).value,
			onload: function(r){
				window.reportActStatus(sid, r.responseText.indexOf('$("flagright') > 0);
				f.go_on();
			}
		});
	};

	function initDialog(title, desc, selAct, subAct){
		window.openQDialog(title, desc);
		window.query("//strong/a[@class='sl2']").each(function(item){
				var li = document.createElement("li");
				var span = document.createElement("div");
				span.style.width = "60px";
				span.style.cssFloat = "left";
				span.appendChild(document.createTextNode(item.innerHTML));
				li.appendChild(span);
			
				var selBox = document.createElement('input');
				selBox.type = "checkbox";
				selBox.value = item.href.substr(item.href.indexOf("uid=") + 4);
				selBox.id = checkBoxId(selBox.value);
				selBox.addEventListener('click', selAct, false);

				li.appendChild(selBox);

				window.qOpenWindowAddItem(li);
			});
		document.getElementById("q_dlg_ok").addEventListener('click', subAct, false);
	}

	addLnk("//h3[@class='wdnl']", '安抚所有奴隶', function(){
		initDialog('安抚所有奴隶', '选择要安抚奴隶及安抚方式！', window.comfortCheckBoxClick, window.comfortClick);
	}, 'comfortLnk', 'wysft');

	addLnk("//h3[@class='wdnl']", '整所有奴隶', function(){
		initDialog('整所有奴隶', '选择要整的奴隶及他最害怕的手段，嘿嘿！', window.painCheckBoxClick, window.painClick);
	}, 'comfortLnk', 'wysft');

}

// bite
function doBite(){
	initSelParams();

	window.beginBite = function(){
		var flow = new JsFlow().add(window.initData).add(window.bite).delay(1500).end(function(){
				window.closePopWindow('http://www.kaixin001.com/app/app.php?aid=1048', 'bitBtn');
			});
		window.initLoop(flow);
		flow.start();
	};

	window.initData = function(/*String*/sid, f){
		GM_xmlhttpRequest({
			method: 'GET',
			url: 'http://www.kaixin001.com/app/app.php?aid=1048&url=index.php&touid=' + sid,
			onload: function(dr){
				var rText = dr.responseText;
				var varIndex = rText.indexOf("var  ");
				var accFun = rText.substring(varIndex, rText.indexOf("if", varIndex));
				eval(accFun);
				var vIndex = rText.indexOf('g_verify = "');
				var verify = rText.substring(vIndex + 13, rText.indexOf('"', vIndex + 13));
				var d = new window.qSerializer().add('verify', verify).add('touid', sid).add('style', document.getElementById('bStyle_' + sid).value).add('position', document.getElementById('bPosition_' + sid).value).add('acc', acc()).add('_', '').toString();
				f.go_on({sid: sid, d: d});
			}
		});
	};

	window.bite = function(p, f){
		GM_xmlhttpRequest({
			method:	'POST',
			url: 'http://www.kaixin001.com/bite/bite.php',
			headers: {
				'User-agent': 'Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3',
				'Content-type': 'application/x-www-form-urlencoded'
			},
			data: p.d,
			onload: function(br){
				window.reportActStatus(p.sid, br.responseText.indexOf("icon_6.gif") > 0)
				f.go_on();
			}
		});
	};

	addCheckBox("//div[@class='mt5']/div", function(){
			var sid = this.value;
			var bStyleEle = document.getElementById("bStyle_" + sid);
			var checkBox = this;
			function initOptionValue(o, s){
				var quiteIndex = s.indexOf('"') + 1;
				o.value = s.substring(quiteIndex, s.indexOf('"', quiteIndex));
				o.text = s.substr(s.indexOf('>') + 1);
			}

			if (!bStyleEle){
				var bPositionEle = createSelect("bPosition_" + sid, this);
				bStyleEle = createSelect("bStyle_" + sid, this);
				GM_xmlhttpRequest({
					method: 'GET',
					url: 'http://www.kaixin001.com/bite/style.php?verify=' + window.searchVal(parent.document.body.innerHTML, /g_verify = \"\w*\"/, 12, 1) + '&touid=' + sid,
					onload: function(resp){
						if (!initOption(bStyleEle, resp.responseText, /type=radio name=style.*/g, initOptionValue)){
							checkBox.checked = false;
							checkBox.disabled = true;
						} else 
							initOption(bPositionEle, resp.responseText, /type=radio name=position.*/g, initOptionValue);
						bStyleEle.style.visibility = checkBox.checked ? 'visible' : 'hidden';
						bPositionEle.style.visibility = checkBox.checked ? 'visible' : 'hidden';
						window.selectedArr[sid] = checkBox.checked;
					}
				});
			} else {
				var bPositionEle = document.getElementById("bPosition_" + sid);
				bStyleEle.style.visibility = checkBox.checked ? 'visible' : 'hidden';
				bPositionEle.style.visibility = checkBox.checked ? 'visible' : 'hidden';
				window.selectedArr[sid] = checkBox.checked;
			}
		},
		function(appendNodeChildNodes, checkBox){
			var sid = appendNodeChildNodes.item(1).innerHTML.match(/\(\d+/);
			checkBox.value = sid.toString().substr(1);		
			checkBox.id = checkBoxId(checkBox.value);
		}, 2); 

	addBtn("//input[@type='button']", '开咬！', function(){
			window.beginBite();
		}, 'bitBtn');
}

//park
function doPark(){
	window.rptCars = function(cars){
		window.openQDialog('乾坤大挪移','需要挪移的车！');
		for(var i = 0; i < cars.length; i++){
			var c = cars[i];
			var li = document.createElement("li");
			li.id = 'li_' + c.carid;
			li.innerHTML = '<img src="' + c.car_logo_small + '" width="20" height="20" />' + c.car_name;
			window.qOpenWindowAddItem(li);
		}
		document.getElementById("q_dlg_ok").addEventListener('click', function(){parent.window.location.reload();}, false);
	};

	window.rptStatus = function(car, succeed){
		GM_log('rptStatus car:' + car.car_name + ':::' + succeed);
		document.getElementById('li_' + car.carid).style.backgroundColor = succeed ? 'blue' : 'red';
	};

	var lnk = addLnk("//h3[@class='wdqc']", '乾坤大挪移', function(){
		var result = window.autoPark(unsafeWindow.v_userdata, unsafeWindow.v_frienddata, unsafeWindow.g_verify, unsafeWindow.acc(), window.rptCars, window.rptStatus, function(){if (confirm('搞定了，是否返回主页面？')) window.location.reload();});

		if (result && result.error)
			alert(result.error);
	}, 'parkLnk', 's12');
	lnk.style.color="#D01E3B"
	lnk.style.marginLeft = '30px';

//	var setLnk = addLnk("//h3[@class='wdqc']", '定时(分钟)>>', function(){
//		document.getElementById("park_interval").value = GM_getValue("park_interval", 0);
//		var s = document.getElementById('park_time_span');
//		s.style.display = s.style.display == 'none' ? '' : 'none';
//	}, 'parkSettingLnk', 's12');
//	setLnk.style.color="#D01E3B"
//	setLnk.style.marginLeft = '30px';
//
//	Window.park_setClk = function(){
//		GM_setValue('park_interval', document.getElementById('park_interval').value * 1000 * 60);
//		var s = document.getElementById('park_time_span');
//		s.style.display = s.style.display == 'none' ? '' : 'none';
//	};
//	var span = document.createElement('span');
//	span.id = "park_time_span";
//	span.style.display = 'none';
//	span.innerHTML = '<input type="text" id="park_interval" style="width: 30px" /><input type="button" id="park_setBtn" class="s12" value="设置" />';
//	setLnk.parentNode.appendChild(span);
//	document.getElementById('park_setBtn').addEventListener('click', window.park_setClk, false); 
}

/*
 * return {error: String, finished: boolean}
 */
window.autoPark = function(userData, friendData, verify, acc, rptCars, rptStatus, finishedAct){
	var notFullArr = [];
	for(var i = 0; i < friendData.length; i++){
		if(friendData[i].full == 0)
			notFullArr.push(friendData[i]);
	}
	if (notFullArr.length == 0){
		return {error: "没有收费车位，免费车位偶不会找，自己停吧！"};
	}

	var beParkCars = [];
	for(var i = 0; i < userData.car.length; i++){
		if(userData.car[i].parkid == 0 || isFree(userData.car[i].parkid) || userData.car[i].park_profit > 150){
			userData.car[i].q_index = beParkCars.length;
			beParkCars.push(userData.car[i]);
		}
	}
	if (beParkCars.length == 0){
		return {error: "刚停完，待会再说吧！"};
	}
	beParkCars.sort(function(a,b){return b.park_moneyminute - a.park_moneyminute;});
	if(rptCars)
		rptCars(beParkCars);

	var friendsParks = [];
	function getFriendPark(friend, f){
		getUserData(friend.uid, function(ud){friendsParks.push(ud);f.go_on();});
	}

	function initLeavedPark(p, f){
		if (p){
			getUserData(p.uid, function(ud){
				for(var i = 0; i < ud.parking.length; i++){
					if (ud.parking[i].parkid = p.parkid){
						var pi = i;
						break;
					}
				}
				for(var i = 0; i < parks.length; i++){
					if (ud.config.money_minute >= friendsParks[parks[i].fi].config.money_minute){
						var psi = i;
						break;
					}
				}
				parks = arrayInsert(parks, psi, {fi: friendsParks.push(ud) - 1, pi: pi});
				f.go_on();
			});
		}else
			f.go_on();
	}

	function getUserData(uid, dataAct){
		GM_xmlhttpRequest({
			method:	'POST',
			url: 'http://www.kaixin001.com/parking/user.php',
			headers: {
				'User-agent': 'Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3',
				'Content-type': 'application/x-www-form-urlencoded'
			},
			data: new qSerializer().add('verify', verify).add('puid', uid).add('_', '').toString(),
			onload: function(resp){
				var result = evalStr(resp.responseText);
				dataAct(result);
			}
		});
	} 

	function friendParkSorter(fa, fb){
		return fb.config.money_minute - fa.config.money_minute;
	}

	function isFree(parkId){
		return (parkId >> 16)&0xff;
	}

	var parks = [];
	function parkCar(pc, flow){
		var p = parks[pc.psi];
		var f = friendsParks[p.fi];
		var c = beParkCars[pc.ci];
		GM_xmlhttpRequest({
			method:	'POST',
			url: 'http://www.kaixin001.com/parking/park.php',
			headers: {
				'User-agent': 'Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.0.3) Gecko/2008092417 Firefox/3.0.3',
				'Content-type': 'application/x-www-form-urlencoded'
			},
			data: new qSerializer().add('verify', verify).add('park_uid', f.user.uid).add('parkid', f.parking[p.pi].parkid).add('carid', c.carid).add('neighbor',f.user.neighbor).add('acc', acc).add('first_fee_parking', f.user.first_fee_parking).add('_', '').toString(),
			onload: function(resp){
				var result = evalStr(resp.responseText); 
				//succeed
				if (result.errno == 0){
					if (rptStatus)
						rptStatus(c, true);
					parks = arrayDel(parks, pc.psi);
					beParkCars = arrayDel(beParkCars, pc.ci);
					if (c.parkid != 0 && !isFree(c.parkid)){
						flow.go_on({uid: c.park_uid, parkid: c.parkid});
						return;
					}
				}else{
					if (rptStatus)
						rptStatus(c, false);
					beParkCars = arrayChange(beParkCars, pc.ci, beParkCars.length -1);
				}
				flow.go_on();
			}
		});
	}

	function selcar(params, f){
		GM_xmlhttpRequest({
			method: 'GET',
			url: 'http://www.kaixin001.com/parking/selcar.php?verify=' + verify,
			onload: function(resp){
				f.go_on();
			}
		});
	}

	function getCarAndPark(){
		for (var i = 0; i < parks.length; i++){
			for(var j = 0; j < beParkCars.length; j++){
				if (friendsParks[parks[i].fi].user.uid != beParkCars[j].park_uid){
					return {psi: i, ci: j};
				}
			}
		}
	}

	new JsFlow().add(getFriendPark).delay(50).loopForParams(notFullArr).end(function(){
		friendsParks.sort(friendParkSorter);
		for(var i = 0; i < friendsParks.length; i++){
			var ps = friendsParks[i].parking;
			for(var j = 0; j < ps.length; j++){
				if (ps[j].car_name == '' && !isFree(ps[j].parkid))
					parks.push({fi: i, pi: j});
			}
		}
		new JsFlow().add(parkCar).add(initLeavedPark).add(selcar).delay(1500).end(finishedAct).setParamsGeter(getCarAndPark).start();
	}).start();
};


// init
var currUrl = window.location.href;
if (currUrl.indexOf("selslave_dialog.php") > 0)
	doSelSlave();
else if (currUrl.indexOf("aid=1028") > 0)
	doAppendSlaveIndex();
else if (currUrl.indexOf("bitable.php") > 0)
	doBite();
else if (currUrl.indexOf("aid=1040") > 0)
	doPark();
