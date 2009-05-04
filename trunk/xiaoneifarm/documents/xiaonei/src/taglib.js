null//This file contains Javascript functions used by XnmlTagLib
//@author Li Weibo, Hua Lu
//Last modified on 2008-7-21


	//这个方法用来配合xn:swf标签动态载入flash文件
	var generateSwf = function(appSwfVars,swfContainerId,swfImgId) {
		var swfObj = document.createElement('embed');
		for (name in appSwfVars) {
			var value = appSwfVars[name];
			swfObj.setAttribute(name, value); 
		}
		var container = document.getElementById(swfContainerId);
		var imgObj = document.getElementById(swfImgId);
		imgObj.style.display = 'none';
		container.appendChild(swfObj);
	}


	/*********************************************************/
	/****************** Global variables *********************/
	/*********************************************************/
	var globalSimpleFriendList = null;


	/*********************************************************/
	/************* xn:multi-friend-select-panel **************/
	/*********************************************************/
	var createMultiFriendSelectPanelNode = function (user, namespace) {
		
		var liObj = document.createElement("li");
		var uid = user.id;
		var liObjId = genId(namespace, uid);
		liObj.setAttribute("id",liObjId);
		liObj.onclick = function() {
			mfspOnClick(liObj, uid, namespace);
		};
		var html = '<a href="#nogo">' + 
			'<span class="picbox">' + 
			'<span class="pic" style="background-image: url(' + user.pic + ');">' + 
			'<img src="http://xnimg.cn/img/tiny_friend_selector_pic_selected.gif" class="added" />' + 
			'</span></span><h4>' + user.name + '</h4><span class="atnet">' + user.network + '</span></a>';
		
		liObj.innerHTML = html;
		return liObj;				
	}
	
	//connect original id with namespace
	var genId = function (namespace, oldId) {
		return namespace + "_" + oldId;
	}
	
	//mutil-friend-select-panel onClick
	var mfspOnClick = function(obj, uid, namespace) {
		var a = eval(genId(namespace,"selectedIds"));
		if (obj.className == "select") {//unselect operation
			obj.className = "";
			var offset = -1;
			for (var i = 0; i < a.length; i ++) {
				if (a[i] == uid) {
					offset = i;
					break;
				}
			}
			//remove from array
			if (offset != -1) {
				a.splice(offset,1);
			}
		} else {//select operation
			
			var max = eval(genId(namespace,"maxFriends"));
			if (a.length == max) {
				var msg = '<p>您一次最多只能选择' + max + '个好友。</p>';
				(new pop_dialog('explanation')).show_message('操作提示', {summary:'',body:msg}, '确定');
				return;
			}
			a.push(uid);
			obj.className = "select";
		}
		var inputObj = document.getElementById(genId(namespace,"multi_friend_hidden_input"))
		a.sort();
		inputObj.value = a.toString();
	}
	
	var createMultiFriendSelectPanel = function (namespace) {
		var divObj = document.getElementById(genId(namespace, "multi_friend_select_panel"));
		var ulObj = document.createElement("ul");
		divObj.appendChild(ulObj);
		
		var a = friendList;//eval(genId(namespace,"userList"));
		
		for (var i = 0; i < a.length; i ++) {
			if (a[i]) {
				var node = createMultiFriendSelectPanelNode(a[i],namespace);
				if (node) ulObj.appendChild(node);
			}
		}
		
		var container = document.getElementById(genId(namespace,"multi_friend_select_panel_root"));
		container.className = "friend-selector";
	}
	
	var mfsp_namespaces = [];
	var friendList = [];
	var friendListLoaded = false;
	var loadFriendList = function() {
		if (friendListLoaded) {
			return;
		}
		friendListLoaded = true;
		new Ajax.Request('/xnml/getfriendlist.do',   
			{	method:'get',
				onSuccess: function(transport){
					var response = transport.responseText || "[]";
					friendList = eval(response);
					for (var i = 0; i < mfsp_namespaces.length; i ++) {
						createMultiFriendSelectPanel(mfsp_namespaces[i]);
					}
				}
			}
		);
	}

	/*********************************************************/
	/********* End of xn:multi-friend-select-panel ***********/
	/*********************************************************/
	
	/*********************************************************/
	/********* xn:multi-friend-selector **********************/
	/*********************************************************/
	function multi_friends_selector(form,container,parameters){
		this.form = $(form);
		this.pre = container;
		this.container = $(container) || document.body;
		for( var p in parameters){
			this[p] = parameters[p];
		}
		this.init();
	}
	multi_friends_selector.prototype = {
		form:null,//表单的id
		inputName:'ids[]',//生成的input的name
		container:null,//好友选择框容器的id
		url:'/xnml/getsimfriendlist.do',//好友列表请求地址
		maxNum:1000,//最大数
		/******** Added by Li Weibo on 2008-7-20 ************/
		//预先选择好友id列表
		selectedIds:[],
		//要排除的好友列表
		excludeIds:[],
		//是否包括自己
		includeMe:false,
		/****************************************************/
		
		pre:null,
		currentNum:0,
		searchBar:null,
		search:null,
		unselectC:null,
		selectedC:null,
		friends:null,
		searchName:null,
		_empty:null,
		_error:null,
		init:function(){
			var headC,unselectC,selectedOut,selectedC,searchB,_a,_empty,_error;
				headC = document.createElement('div');
				unselectC =this.unselectC = headC.cloneNode(false);
				selectedC = this.selectedC = headC.cloneNode(false);
				selectedOut = headC.cloneNode(false);
				headC.className = 'search_holder';
				unselectC.className = 'unselected_list';
				selectedOut.className = 'selected_list';
				searchB = this.searchBar = document.createElement('input');
				searchB.onkeyup = this.searchFriends.bind(this);
				searchB.type = 'text';
				searchB.value = '输入好友姓名进行筛选...';
				searchB.onclick = function(){if(this.value == '输入好友姓名进行筛选...'){this.value = '';Element.removeClassName(this,'gray');}};
				searchB.onblur = function(){if(this.value == ''){this.value = '输入好友姓名进行筛选...';Element.addClassName(this,'gray');}};
				searchB.className = 'search_freinds gray';
				headC.appendChild(searchB);
				_empty = this._empty= document.createElement('div');
				_empty.className = 'empty-list';
				_empty.innerHTML = '请从左侧选择要添加的好友';
				_error = this._error = document.createElement('div');
				_error.className = 'error';
				_error.innerHTML = '您最多只能选择' + this.maxNum + '位好友';
				_error.style.display = 'none';
				selectedOut.appendChild(_empty);
				selectedOut.appendChild(selectedC);
				selectedOut.appendChild(_error);
				new Ajax.Request(this.url,
					{
						method:'post',
						postBody : this.prepareLoadFriendParams(),
						onSuccess:this.loadFriends.bind(this)
					}
				);
				this.container.className = 'condensed_multi_friend_selector clearfix';
				this.container.appendChild(headC);
				this.container.appendChild(unselectC);
				this.container.appendChild(selectedOut);
		},
		/* Added on 08-7-21
		 * @author Li Weibo
		 * 准备取好友列表的ajax请求的参数
		 */
		prepareLoadFriendParams : function () {
			var params = "v=_";
			if (this.includeMe) {
				params += "&includeMe=true";
			}
			if (this.excludeIds) {
				for (var i = 0; i < this.excludeIds.length; i ++) {
					params += "&excludeIds[]=" + this.excludeIds[i];
				}
			}
			return params;
		},
		searchFriends:function(){
			if(this.searchBar.value == ''){this.showSearchFriends(this.friends);this.searchName = null;return;}
			var sname = this.searchName = new RegExp(this.searchBar.value,'i');
			var r = [],friends = this.friends;
			for (var i = 0,j = friends.length;i < j;i++	){
				if(sname.test(friends[i].name)){
					r.push(friends[i]);
				}
			}
			this.showSearchFriends(r);
		},
		loadFriends:function(r){
			var friends,_s = this,sC,uC,s,u,id;
			var json = r.responseText;
			friends = eval(json);
			globalSimpleFriendList = eval(json);
			this.friends = friends;
			this.showFriends(friends);
			/******** Added by Li Weibo on 2008-7-20 ************/
			//预先选择一些好友
			this.selectUids(this.selectedIds);
			/****************************************************/
		},
		showSearchFriends:function(friends){
			var lis = this.unselectC.getElementsByTagName('DIV');
			for (var i = 0,j = lis.length;i < j;i++){
				Element.hide(lis[i]);
			}
			for(var k = 0,l = friends.length;k <l;k++){
				if(!friends[k].selected){
					Element.show(this.pre + 'condensed_u_' + friends[k].fid)
				}
			}
		},
		showFriends:function(friends){
			var sC,uC,s,u,id;
			this.selectedC.innerHTML = '';
			this.unselectC.innerHTML = '';
			s = document.createDocumentFragment();
			u = document.createDocumentFragment();
			for (var i = 0,j = friends.length;i < j;i++){
				friends[i].selected = false;
				friends[i].fid = i;
				sC = document.createElement('div');
				sC.appendChild(document.createTextNode(friends[i].name));
				uC = sC.cloneNode(true);
				sC.multi_friends_selector = this;
				sC.style.display = 'none';
				sC.fid = i;
				sC.id = this.pre + 'condensed_s_' + i;
				sC.className = 'selected';
				sC.onmouseover = function(){Element.addClassName(this,'shover');};
				sC.onmouseout = function(){Element.removeClassName(this,'shover');};
				uC.multi_friends_selector = this;
				uC.fid = i;
				uC.id = this.pre + 'condensed_u_' + i;
				uC.className = 'unselected';
				uC.onmouseover = function(){Element.addClassName(this,'uhover');};
				uC.onmouseout	= function(){Element.removeClassName(this,'uhover');};
	
				sC.onclick = this.sClick;
				uC.onclick = this.uClick;
				u.appendChild(uC);
				s.appendChild(sC);
	
			}
			this.selectedC.appendChild(s);
			this.unselectC.appendChild(u);
		},
		/* Added on 08-7-20
		 * @author Li Weibo
		 * 按用户id来选择好友的一个方法
		 */
		selectUids:function(uids) {
			var a = this.friends;
			for (var i = 0; i < a.length; i ++) {
				for (var j = 0; j < uids.length; j ++) {
					if (a[i].id == uids[j]) {
						this.select(i);
						break;
					}
				}
			}
		},
		//这里的id其实是index
		select:function(id){
			var _s = this;
			if(this.currentNum >= this.maxNum){Element.show(this._error);setTimeout(function(){Element.hide(_s._error);},2000);return;}
			Element.hide(this._empty);
			this.currentNum++;
			var input;
			this.friends[id].selected = true;
			Element.hide(this.pre + 'condensed_u_' + id);
			Element.show(this.pre + 'condensed_s_' + id);
			input = document.createElement('input');
			input.type = 'hidden';
			input.name = this.inputName;
			input.id = this.pre + '_cmsi_' + id;
			input.value = this.friends[id].id;
			this.form.appendChild(input);
		},
		selectAll:function() {
			for (var i = 0; i < this.friends.length; i ++) {
				this.select(i);
			}
		},
		deSelect:function(id){
			this.currentNum--;
			this.friends[id].selected = false;
			Element.hide(this.pre + 'condensed_s_' + id);	
			this.form.removeChild($(this.pre + '_cmsi_' + id));
			if(this.currentNum == 0){Element.show(this._empty);}
			if(this.searchName !== null && !this.searchName.test(this.friends[id].name))return;
			Element.show(this.pre + 'condensed_u_' + id);
	
		},
		sClick:function(){
			this.multi_friends_selector.deSelect(this.fid);
		},
		uClick:function(){
			this.multi_friends_selector.select(this.fid);
		}
	};
	/*********************************************************/
	/********* End of xn:multi-friend-selector ***************/
	/*********************************************************/
	
	/*********************************************************/
	/****************** xn:invite-form ***********************/
	/*********************************************************/	
	var onInviteFormSubmit = function(formObj) {
		var top = Position.cumulativeOffset(formObj)[1];		
		var height = formObj.offsetHeight;
		var dialogY = top + ((height - 80) >= 0 ? (height - 80) : 0 ) / 2;
		var ids = [];
		var appId = -1;
		for (var i = 0; i < formObj.elements.length; i ++) {
			var e = formObj.elements[i];
			if (e.name == "ids[]") {
				if (e.type == "checkbox" || e.type == "radio" ) {
					if (e.checked) {
						ids.push(e.value);
					}
				} else {
					ids.push(e.value);
				}
			} else if (e.name == "app_id") {
				appId = e.value;
			}
		}
		var subSlct = confirmSubmitInviteForm(ids, dialogY);
		if (subSlct) {
			if (subSlct == null || subSlct.length == 0) {
				//直接提交，防流氓邀请
				return true;
			}
			XN.DO.confirm('你确定要邀请好友 '+ subSlct + ' 安装这个应用程序吗？','邀请好友安装',function(t){
				if(t){sendAppInvitation(formObj, ids, appId);}
			},null, null, null, dialogY);
		}
		return false;
	}

	var sendAppInvitation = function (formObj, ids, appId) {
		var params = "app_id=" + appId;
		for (var i = 0; i < ids.length; i ++) {
			params += "&" + "ids[]=" + ids[i];
		}
		new Ajax.Request('/xnml/sendappinvitation.do',
			{	method:'post',
				postBody : params,
				onFailure: function(transport){
					alert("发生错误，邀请失败，请联系管理员。");
				},
				onSuccess: function(transport){
					formObj.submit();
				}
			}
		);
	}

	var confirmSubmitInviteForm = function(ids, dialogY) {
		/*
		if (ids.length == 0) {
			//XN.DO.alert('您至少应当选择一名好友！','邀请好友安装', null, dialogY);
			XN.DO.alert({
				message:'您至少应当选择一名好友！',
	 			title:'邀请好友安装',
	 			type:'error',
				Y:dialogY
			});
			return false;
		}*/
		if (ids.length > 10) {
			//XN.DO.alert('您一次最多只能邀请5个好友！','邀请好友安装', null, dialogY);
			XN.DO.alert({
				message:'您一次最多只能邀请10个好友！',
	 			title:'邀请好友安装',
	 			type:'error',
				Y:dialogY
			});
			return false;
		}
		var names = [];
		if (globalSimpleFriendList) {
			for (var i = 0; i < globalSimpleFriendList.length; i ++) {
				for (var j = 0; j < ids.length; j ++) {
					if (globalSimpleFriendList[i].id == ids[j]) {
						names.push(globalSimpleFriendList[i].name);
						break;
					}
				}
			}
		} else {
			for (var i = 0; i < ids.length; i ++) {
				names.push(ids[i] + "(id)");
			}
		}
		
		var nameList = names[0];
		for(var i = 1; i < names.length; i ++) {
			nameList += "、";
			nameList += names[i];
		}
		return names;
	}

	var onInviteFormProSubmit = function(formObj) {
		var top = Position.cumulativeOffset(formObj)[1];		
		var height = formObj.offsetHeight;
		var dialogY = top + ((height - 80) >= 0 ? (height - 80) : 0 ) / 2;
		var ids = [];
		var appId = -1;
		for (var i = 0; i < formObj.elements.length; i ++) {
			var e = formObj.elements[i];
			if (e.name == "ids[]") {
				if (e.type == "checkbox" || e.type == "radio" ) {
					if (e.checked) {
						ids.push(e.value);
					}
				} else {
					ids.push(e.value);
				}
			} else if (e.name == "app_id") {
				appId = e.value;
			}
		}
		XN.DO.confirm('你确定要邀请这些好友安装这个应用程序吗？','邀请好友安装',function(t){
			if(t){sendAppInvitation(formObj, ids, appId);}
		},null, null, null, dialogY);
		return false;
	}
	/*********************************************************/
	/*************End of xn:invite-form **********************/
	/*********************************************************/
   
function newMultiFriendSelector(reqParams){

    var type = reqParams['type'];
    var containerId = reqParams['containerId'];
    var handler = reqParams['handler'];
	
	var maxNum = reqParams['maxNum']
	//var buttonId = 'req_submit_button';
	var reqFormId = reqParams['reqFormId'];
	var previewUrl = reqParams['previewUrl'];
	var sendInvitationUrl = reqParams['sendInvitationUrl'];
	
	
	/*下面几个参数是取好友的Ajax用的*/
    var excludeIds = reqParams['excludeIds'];
    var includeMe = reqParams['includeMe'];
	var appId = reqParams['appId'];
    var friendsUrl = reqParams['friendsUrl'];
	/***********************************/
    var friendFilterParams = {'app_id':appId, 'exclude_ids': excludeIds, 'include_me': includeMe};
    
    
    var selector;

    if ( type == 'mfs' )
    {
        selector = new XN.ui.multiFriendSelector();
        initOver();    
    }
    else if ( type == 'mfsbox' )
    {
    	XN.loadFile( 'http://xnimg.cn/csspro/module/friendSelector.css' );
        XN.loadFile( 'http://xnimg.cn/jspro/xn.ui.pager.js' );
        XN.loadFile( 'http://xnimg.cn/jspro/xn.ui.multiFriendSelectorBox.js' , function() {
            selector = new XN.ui.multiFriendSelectorBox({
					noInputButton:true,
					noCancelButton:true,
					noCompleteButton:true,
					url : friendsUrl,
					inputName : 'ids[]',
					method:'post',
					maxNum: maxNum,
					param: friendFilterParams
                });
            initOver();
        });
    }
    else if ( type == 'mfslist' ) 
    {
        XN.loadFile( 'http://xnimg.cn/jspro/xn.ui.multiFriendSelectorForApp.js' , function() {
            selector = new XN.ui.multiFriendSelectorForApp();
            initOver();
        });
    }
    else
    {
        return;
    }
    
    function initOver()
    {
        $( containerId ).setContent( selector );
        window[ handler ] = selector;
		if ($(reqFormId)) {
			$(reqFormId).onsubmit = function() {
				//alert($(reqFormId).content);
        		//将重要对象向下传递
        		showPreviewDialog({
            		'appId':appId,
        			'selector':selector,
        			'reqFormId':reqFormId,
        			'friendsUrl':friendsUrl,
        			'previewUrl':previewUrl,
        			'sendInvitationUrl':sendInvitationUrl,
        			'friendFilterParams':friendFilterParams
        		});
        		return false;
	       };
		}
    }
}

(function(){
    var mfsfriendinput;
    showPreviewDialog = function(params) {
		var appId = params['appId'];
		var selector = params['selector'];
		var reqForm = $(params['reqFormId']);
		var friendsUrl = params['friendsUrl'];
		var previewUrl = params['previewUrl'];
		var friendFilterParams = params['friendFilterParams'];
		var sendInvitationUrl = params['sendInvitationUrl'];
		
		var selectedFriends = selector.getSelectedFriends();
		if (!selectedFriends || selectedFriends.length == 0) {
			XN.DO.alert({
				message: '您至少应该选择一名好友',
				title: '提示',
				type: 'error'	
				});
			return;
		}
		//取弹窗预览页面
		new XN.NET.xmlhttp({	
					url:previewUrl,
					method:'post',
					data : preparePreviewParam(),
					onFailure: function(transport){
						alert("发生错误，请联系管理员。");
					},
					onSuccess: onload
				}
			);

		function onload( transport )
		{
			var dialog = new XN.DO.confirm({
	            title: '发送请求',
	            message : transport.responseText,
	            callBack : dialogCallBack,
	            width : 550,
	            yes: '发送',
	            no: '取消'
	        });
	         //如果之前没有创建弹层里的选择器则初始化一个
	        //alert( mfsfriendinput );
	        if ( !mfsfriendinput ){
	        	mfsfriendinput = new XN.ui.multiFriendSelector({
	                noInput : false,
	                url: friendsUrl,
	                inputName : 'ids[]',
	                param: friendFilterParams
	            });
	
	            //与底下盒型的选择器同步
	            XN.ui.friendSelectorSynchronous( mfsfriendinput , selector );
	        }

	        $( 'mfscontainer' ).setContent( mfsfriendinput );
	        //alert( friendInput.isReady() );
	        //alert( friendInput._ID );
	        if ( !mfsfriendinput.isReady() ) {
	            //如果选择器还没有初始化,则加载好友
	            mfsfriendinput.loadFriends();
	            mfsfriendinput.addEvent( 'load' , init );
	        } else {
				//alert( 'ready do' );
	            init();
	        }

	        
	        //把reqForm的content属性拿过来放到弹窗上的form的隐藏input中
	        //XN.FORM.setValue('app_msg', reqForm.getAttribute('content'));
		}
        
        function preparePreviewParam() {
        	var ret = 'app_id=' + appId + '&app_msg=' + encodeURIComponent(reqForm.getAttribute('content'));
       		return ret;
        }
        
        function dialogCallBack( r ) {
            if ( r ) {
                //alert( friendInput.getSelectedFriends() );
                //alert(XN.FORM.serialize($('reqConfirmForm')));
                new XN.NET.xmlhttp({	
						url:sendInvitationUrl,
						method:'post',
						data : XN.FORM.serialize($('reqConfirmForm')),
						onFailure: function(transport){
							alert("发生错误，请联系管理员。");
						},
						onSuccess: function(transport) {
							$('reqConfirmForm').submit();
						}
					}
				);
            } else {
                //selector.reset();
            }
        }
       
        function init() {
            //选择下面选择器已经选择的好友
            mfsfriendinput.reset();
            mfsfriendinput.selectFriends( selector.getSelectedFriends() );
        }
    }
})();