


Date.prototype.toStr = function(isDay){
	var str = this.getFullYear() + "-" + (this.getMonth()+1).format("##") + "-" + this.getDate().format("##");
	if(isDay){
		return str;
	}
	str = str + " " + this.getHours().format("##") + ":" + this.getMinutes().format("##") + ":" + this.getSeconds().format("##");
	return str;
};
Date.prototype.preOneHour =function(){
	var str = this.getFullYear() + "-" + (this.getMonth()+1).format("##") + "-" + this.getDate().format("##");
	str = str + " " + (this.getHours()-1).format("##") + ":" + this.getMinutes().format("##") + ":" + this.getSeconds().format("##");
	return str;
};
Date.prototype.preOneDay =function(isDay){
	var str = this.getFullYear() + "-" + (this.getMonth()+1).format("##") + "-" + (this.getDate()-1).format("##");
	if(isDay){
		return str;
	}
	str = str + " " + this.getHours().format("##") + ":" + this.getMinutes().format("##") + ":" + this.getSeconds().format("##");
	return str;
};
Date.prototype.preTwoDay =function(isDay){
	var str = this.getFullYear() + "-" + (this.getMonth()+1).format("##") + "-" + (this.getDate()-2).format("##");
	if(isDay){
		return str;
	}
	str = str + " " + this.getHours().format("##") + ":" + this.getMinutes().format("##") + ":" + this.getSeconds().format("##");
	return str;
};
Date.prototype.preOneWeek = function(isDay){
	var str = this.getFullYear() + "-" + (this.getMonth()+1).format("##") + "-" + (this.getDate()-7).format("##");
	if(isDay){
		return str;
	}
	str = str + " " + this.getHours().format("##") + ":" + this.getMinutes().format("##") + ":" + this.getSeconds().format("##");
	return str;
};
Date.prototype.preOneMonth = function(isDay){
	var str = this.getFullYear() + "-" + this.getMonth().format("##") + "-" + this.getDate().format("##");
	if(isDay){
		return str;
	}
	str = str + " " + this.getHours().format("##") + ":" + this.getMinutes().format("##") + ":" + this.getSeconds().format("##");
	return str;
};
Date.prototype.preTwoMonth = function(isDay){
	if(this.getMonth()=="1"){
		var str =( this.getFullYear()-1) + "-" + "12" + "-" + this.getDate().format("##");
	}else{
		var str = this.getFullYear() + "-" + (this.getMonth()-1).format("##") + "-" + this.getDate().format("##");
	}
	if(isDay){
		return str;
	}
	str = str + " " + this.getHours().format("##") + ":" + this.getMinutes().format("##") + ":" + this.getSeconds().format("##");
	return str;
};
Date.prototype.preOneYear = function(isDay){
	var str = (this.getFullYear()-1) + "-" + (this.getMonth()+1).format("##") + "-" + this.getDate().format("##");
	if(isDay){
		return str;
	}
	str = str + " " + this.getHours().format("##") + ":" + this.getMinutes().format("##") + ":" + this.getSeconds().format("##");
	return str;
};
Date.prototype.toSimpleStr = function(type){
	if(type==null || type==0){
		var str = this.getFullYear() + (this.getMonth()+1).format("##") + this.getDate().format("##");
		str = str + "T" + this.getHours().format("##") + this.getMinutes().format("##") + this.getSeconds().format("##") + this.getMilliseconds().format("##")+"Z";
		return str;
	}else if(type==1){
		var str = this.getFullYear() + (this.getMonth()+1).format("##") + this.getDate().format("##");
		str = str + "d" + this.getHours().format("##") + this.getMinutes().format("##") + this.getSeconds().format("##");
		return str;
	}else if(type=="T"){
		var str = this.getHours().format("##") + this.getMinutes().format("##") + this.getSeconds().format("##");
		return str;
	}
};


/**
 * 把日期转换成字符串
 * @param fmt
 * @returns
 */
Date.prototype.format = function(fmt) {
	  var o = {
	  		"M+" : this.getMonth()+1,                 //月份
	  		"d+" : this.getDate(),                    //日
	  		"h+" : this.getHours(),                   //小时
	  		"m+" : this.getMinutes(),                 //分
	  		"s+" : this.getSeconds(),                 //秒
	  		"q+" : Math.floor((this.getMonth()+3)/3), //季度
	  		"S"  : this.getMilliseconds()             //毫秒
	  };
	  if(/(y+)/.test(fmt))
	  		fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
	  for(var k in o)
	  		if(new RegExp("("+ k +")").test(fmt))
	  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
	  return fmt;

}


/**
 * 把字符串转换成日期
 * @returns
 */
String.prototype.toDate = function(){

	if(this.trim().length==0)
		return null;

	var ss = this.split(" ");

	var daySS =  ss[0].split("-");
	var value = new Date(0);
	value.setHours(0);
	value.setFullYear(1*daySS[0]);
	value.setMonth(1*daySS[1]-1);
	value.setDate(1*daySS[2]);
	if(ss.length>1){
		var timeSS = ss[1].split(":");
		value.setHours(1*timeSS[0]);
		value.setMinutes(1*timeSS[1]);
		if(timeSS.length==2)
			timeSS.push(0);
		value.setSeconds(1*timeSS[2]);
	}
	return value;
}

//去掉字符串首尾空格
String.prototype.trim = function () {
	var reg =  /(^\s*)|(\s*$)/g;
	return this.replace(reg, "");
};

//判断字符串是否以某个子串结尾的
String.prototype.endsWith = function(s){
	var p = this.lastIndexOf(s);
	if(p+s.length==this.length){
		return true;
	}else
		return false;
};

String.prototype.startsWith = function(s){
	var p = this.indexOf(s);
	return p==0;
};

//显示特殊符号
String.prototype.toShow = function () {
	 var RexStr = /\s|\<|\>|\"|\'|\&/g;
	    str = this.replace(RexStr,
	        function(MatchStr){
	            switch(MatchStr){
		            case " ":
	                    return "&nbsp;";
	                    break;
	                case "<":
	                    return "&lt;";
	                    break;
	                case ">":
	                    return "&gt;";
	                    break;
	                case "\"":
	                    return "&quot;";
	                    break;
	                case "'":
	                    return "&#39;";
	                    break;
	                case "&":
	                    return "&amp;";
	                    break;
	                default :
	                    break;
	            }
	        }
	    );
	    return str;
};

//****************************************************************
//* 功    能：计算字符串的长度(Unicode长度为2，非Unicode长度为1)
//*****************************************************************
String.prototype.asciiLen = function(){
	var intLength=0;
	for (var i=0;i<this.length;i++){
		if ((this.charCodeAt(i) < 0) || (this.charCodeAt(i) > 255))
			intLength=intLength+2;
		else
			intLength=intLength+1;
	}
	return intLength;
};

Array.prototype.add = function(o){
	this.push(o);
	return this;
};

//计算某个元素在数组中的位置
Array.prototype.indexOf=function(o){
	for(var i=0;i<this.length;i++){
		if(this[i]==o)
			return i;
	}
	return-1;
};

//计算某个元素在数组中最后的位置
Array.prototype.lastIndexOf=function(o){
	for(var i=this.length-1;i>=0;i--){
		if(this[i]==o)
			return i;
	}
	return-1;
};

//判断数组是否包含某个元素
Array.prototype.contains=function(o){
	return this.indexOf(o)!= -1;
};

//向数组增加一个数组中所有元素
Array.prototype.addAll=function(newArray){
	for(var i=0;i<newArray.length;i++){
		this.push(newArray[i]);
	}
};

//合并数组
Array.prototype.merge = function(newArray){
	for(var i=0;i<newArray.length;i++){
		if(this.contains(newArray[i])==false)
			this.push(newArray[i]);
	}
};


//复制数组
Array.prototype.copy=function(o){
	return this.concat();
};

//在数组指定位置插入元素
Array.prototype.insertAt=function(o,i){
	this.splice(i,0,o);
};

//在数组中某元素之前插入新元素
Array.prototype.insertBefore=function(o,o2){
	var i=this.indexOf(o2);
	if(i== -1)
		this.push(o);
	else
		this.splice(i,0,o);
};

//删除指定位置的元素
Array.prototype.removeAt=function(i){
	this.splice(i,1);
};

//删除指定元素
Array.prototype.remove=function(o){
	var i=this.indexOf(o);
	if(i!= -1)
		this.splice(i,1);
};

/**
 * 比较器，用于数组排序。
 * @param pName，假如希望数组中的json对象，按照某个属性排序，则先var comp = new PropComparator(propName),然后array.sort(comp.comparator);
 * @returns {PropComparator}
 */
function PropComparator(pName){
	this.comparator = function(o1,o2){
		return ZY.UI.getProperty(o1,pName)> ZY.UI.getProperty(o2,pName);
	}
}


/**
* 把数字按格式变成字符串
* 1、#.00,保留2位小数
* 2、#.00%,保留2位小数的百分数
* 3、##.00,保留2位小数，2位整数，整数不足部分补零
**/
Number.prototype.format = function(f){
	var n = this;
	f = f.trim();
	if(f==null)	f = "#.00";
	var isPercent = false;
	if(f.endsWith("%")){
		isPercent = true;
		n = n*100;
		f = f.substring(0,f.length-1);
	}
	var v ;
	if(f.startsWith("#") && f.indexOf(".")<0){
		v = String(Math.round(n));	//返回整数
		var l = f.length-v.length;
		if(l>0){
			for(var i=0;i<l;i++)
				v = "0"+v;
		}
	}else if(f=="#.#"){
		v = ""+n;
	}else{
		var pre = 0;	//小数点之前的长度
		var preStr = "";
		var after = 0;	//小数点之后的长度
		var afterStr = "";

		if(f.endsWith("#")){
			after = 0;		//小数点后有几位就是几位，不补零
			v  = ""+n;
		}else{
			var index = f.indexOf(".");
			pre = index;
			preStr = f.substring(0,index);
			afterStr = f.substring(index+1);
			after = afterStr.length;
			var p = Math.pow(10,after);
			var t = p * n;
			t = Math.round(t);
			v = String(t / p);
		}

		var vpre = "";
		var vafter = "";
		if(after>0){
			if(v.indexOf(".")===-1){
				v = v+".0";
			}
			var p = v.indexOf(".")+1;
			var vafter = v.substring(p);
			if(vafter.length<after){
				var l = after-(vafter.length);
				for(var i = 0;i<l;i++){
					vafter = vafter+"0";
				}
			}
		}
		if(pre>0){
			if(v.indexOf(".")===-1){
				v = v+".0";
			}
			var p = v.indexOf(".");
			var vpre = v.substring(0, p);
			if(vpre.length<pre){
				var l = pre-(vpre.length);
				for(var i = 0;i<l;i++){
					vpre = "0"+vpre;
				}
			}
		}
		v = vpre+"."+vafter;
	}
	if(isPercent===true){
		v = v+"%";
	}

	return v;
};

//监听所有ajax请求，如果遇到会话失效的回应，则将页面跳转到登陆界面
$(document).ajaxSuccess(function(e, xhr, settings) {
	var result = xhr.responseJSON;
	if(result.sessionInvalid==true){
		var url=(result.type!=null)?url+"?type="+result.type:url;
		window.top.location.href=url;
		return false;
	}
	return true;
});




ZY = {};
ZY.UI = {};

ZY.UI.wrapperSearchBar=function(){
	var iconBar =  $(".iconBar").last();//ul
	var search = $(".iconBar .searchBar input").last();
	var searchBar = $(".iconBar .searchBar");//li

	if(search.length==0)
		return ;

	var cWidth = iconBar.width();
	var leftWidth = iconBar.find(".title").width();
	var rightWidth = iconBar.find(".icon").length*28+20;
	var width = cWidth - leftWidth - rightWidth-40;
	searchBar.width(width-2);

	var leftRealWidth = iconBar.find(".title").html().asciiLen()*8+4;
	var left = (width-searchBar.children().width())/2-(leftWidth-leftRealWidth)/2;

	var wrapper = $("<div class='wrapper' style='width:"+(search.width()+35)+"px;'></div>");
	search.wrap(wrapper);
	wrapper = searchBar.find(".wrapper");
	wrapper.append("<div></div>");

	searchBar.children().last().css("left",left+"px");
	$(window).bind("resize",function(){
		cWidth = iconBar.width();
		width = cWidth - leftWidth - rightWidth-20;
		searchBar.width(width);
		left = (width-searchBar.children().width())/2-(leftWidth-leftRealWidth)/2;
		searchBar.children().last().css("left",left+"px");
	});

	wrapper.find("div").bind("click",function(){
		wrapper.find("input").trigger("search");
	});

	search.bind("keypress",function(e){
		var key = e.which;
	    if (key == 13) {
	        search.trigger("search");
	    }
	});
}
jQuery(document).ready(function(){
	//ZY.UI.wrapperSearchBar();
});

function zy_clean(obj){
	for(p in obj){
		delete obj[p];
	}
}
ZY.UI.getObjectFromServer = function(obj){
	if(obj==undefined)
		return null;
	return obj;
}
ZY.UI.extend = function(dest,from) {
    for (var property in from) {
        dest[property] = from[property];
    }
};
ZY.UI.asListener = function(obj,fun){
	return function(event){
		return fun.call(obj, (event || window.event));
	};
};
ZY.UI.getProperty=function(obj,pName){
	var pNames = pName.split(".");
	var value = obj;
	for(var i=0;i<pNames.length;i++){
		if(value==null)
			return value;
		pNames[i] = pNames[i].trim();
		value = value[pNames[i]];
	}
	return value;
}

ZY.UI.setProperty = function(obj,pName,pValue){
	var pNames = pName.split(".");
	var value = obj;
	for(var i=0;i<pNames.length;i++){
		pNames[i] = pNames[i].trim();
		if(i==pNames.length-1)
			value[pNames[i]] = pValue;
		else{
			var c = value[pNames[i]];
			if(c==null){
				c = {};
				value[pNames[i]] = c;
			}
			value = c;
		}
	}
};

/**
 * 用于自动生成dom.id
 * @returns {Number}
 */
ZY.UI.getNextId = function(){
	if(this.domObjectId == null)
		this.domObjectId = 0;
	this.domObjectId ++;
	return this.domObjectId;
};

/**
 * 所有为tree定义的事件
 */
ZY.UI.TreeEvents = {
	clickRepeat:null,
	clickCount:0,
	onStateSwitch:function(treeId,nodeId){
		var tree = ZY.UI.trees[treeId];
		if(tree!=null){
			node = tree.getNode(nodeId);
			node.changeState();
			tree.onStateChanged(node);
		}
	},
	//如果要允许节点双击事件，请修改setTimeout的延时参数
	onNodeup:function(treeId,nodeId){
		clearTimeout(this.clickRepeat);
		this.clickCount++;
		if(this.clickCount==1){
			this.clickRepeat=setTimeout(function(){
				ZY.UI.TreeEvents.onNodeClicked(treeId,nodeId);
				ZY.UI.TreeEvents.clickCount=0;},200
			);}
		else{this.onNodeDbClicked(treeId, nodeId),this.clickCount=0;}
	},
	onNodeClicked:function(treeId,nodeId){
		var tree = ZY.UI.trees[treeId];
		if(tree!=null){
			node = tree.getNode(nodeId);
			node.focus();
		}
	},
	onNodeDbClicked:function(treeId,nodeId){
		var tree = ZY.UI.trees[treeId];
		if(tree!=null){
			node = tree.getNode(nodeId);
			node.focus();
			tree.onDbClicked(node);
		}
	},
	onCheckBoxClicked:function(treeId,nodeId){
		var tree = ZY.UI.trees[treeId];
		if(tree!=null){
			node = tree.getNode(nodeId);
			node.changeChecked();
		}
	}

};


ZY.UI.trees = {};

/**
 * 树的构建函数
 * @param parent 父容器
 * @param context 配置信息
 * @param data	节点数据
 * @param ifMultSelected 是否允许多选，允许多选的话，每个节点上有checkbox
 */
ZY.UI.Tree = function(parent,context,data,ifMultSelected){
	this.context = context;
	this.focusedNode = null;
	this.selectedNodes = [];
	this.checkedNodes = [];
	this.ifMultSelected = false || ifMultSelected;

	if(parent.constructor == String)
		parent = $("#"+parent);
	else
		parent = $(parent);

	parent.addClass("zy_tree");
	this.id = parent.attr("id");
	if(this.id==null)
		this.id = "zy_tree_"+ZY.UI.getNextId();
	ZY.UI.trees[this.id] = this;	//注册一颗树，便于根据id找回这棵树


	parent.attr("id",this.id);
	this.box = parent;

	this.children = [];
	if(data instanceof Array){
		this.data = data;
	}else{
		this.data= [];
		this.data.push(data);
	}

	for(var i=0;i<this.data.length;i++){
		var node = new ZY.UI.TreeNode(this.data[i],this);
		this.children.push(node);
	}
	for(var i=0;i<this.children.length;i++){
		this.children[i].paint();
	}

	this.getNode = function(nodeId){
		for(var i=0;i<this.children.length;i++){
			var child = this.children[i];
			if(child.id == nodeId)
				return child;
			else{
				var cc = child.getChildNode(nodeId);
				if(cc!=null)
					return cc;
			}
		}
		return null;
	};

	this.getAllChecked = function(_type){
		var types = [];
		types=types.concat(_type);
		var datas = this.children;
		var type = _type || null;
		var checkedNodes = [];
		var iterator = this.getIterator();
		var current = null;
		while((current=iterator.next())!=null){
			if(type!=null){
				if((current.checked==true|| current.checked=='true')&&(types.contains(current.data.treeNodeType))){
					checkedNodes.push(current);
				}
			}else{
				if(current.checked==true || current.checked=='true'){
					checkedNodes.push(current);
				}
			}
		}
		return 	checkedNodes;
	}


	this.onStateChanged = function(node){

	};

	this.onChanged = function(node){
	};
	this.onDbClicked =function(node){
	};
	this.unAccessibleFocused =function(node){
	};

	this.getSelectedNode = function(){
		if(this.ifMultSelected){
			return this.getAllChecked();
		}else{
			if(this.focusedNode == null || this.focusedNode.focused == false) {
				return null;
			} else {
				return this.focusedNode;
			}
		}
	};

	this.remove = function(node){
		node.remove();
	};

	this.addNode = function(data){
		var preNode = this.children[this.children.length-1];
		var node = new ZY.UI.TreeNode(data,this);
		this.children.push(node);
		node.paint();
		preNode.paint();
	};

	this.insertBefore = function(data,node){
		var parent = node.parent;
		if(parent==this){
			var p = this.children.indexOf(node);
			var newNode = new ZY.UI.TreeNode(data,this);
			this.children.insertAt(node, p);
			newNode.paint();
			node.paint();
			newNode.getElement().insertBefore(node.getElement());
		}else{
			parent.insertBefore(data,node);
		}
	};

	this.findNode = function(propertyName,value){
		var iterator = this.getIterator();
		var node = null;
		while((node=iterator.next())!=null){
			if(node.data[propertyName]==value){
				return node;
			}
		}
		return null;
	};

	this.matchCons = function(node,cons){
		var match = true;
		var s_key;
		for(s_key in cons){
			if(node.data[s_key]!= cons[s_key]){
				match = false;
				break;
			}
		}
		return match;
	}


	this.findNodeBy = function(cons){
		var iterator = this.getIterator();
		var node = null;
		while((node=iterator.next())!=null){
			if(this.matchCons(node,cons)){

				return node;
			}
		}
		return null;
	}

	this.getIterator = function(){
		return new ZY.UI.Tree.Iterator(this);
	};

};

ZY.UI.Tree.Iterator = function(tree){
	if(tree.children==null || tree.children.length<1)
		return null;

	this.tree = tree;
	this.current = tree;

	this.next = function(){
		var children = this.current.children;
		if(children!=null && children.length>0){
			this.current =children[0];
		}else{
			if(this.current==this.tree)
				this.current = null;
			else
				this.current = this.getNextBrother(this.current);
		}
		return this.current;
	};

	this.getNextBrother = function(current){
		var brothers = current.parent.children;
		var index = brothers.indexOf(current);
		if(index<brothers.length-1){
			return brothers[index+1];
		}else{
			if(current.parent == this.tree){
				return null;
			}else{
				return this.getNextBrother(current.parent);
			}
		}
	};
};

ZY.UI.TreeNode = function(data,parent){
	this.data = data;
	this.parent = parent;
	this.children = [];
	this.state = "closed";
	this.checked = data["selected"] || false ;
	this.focused = false;
	this.treeNodeType = data.treeNodeType || "";
	this.id = ZY.UI.getNextId();

	this.getChildNode = function(nodeId){
		if(this.children!=null && this.children.length>0){
			for(var i=0;i<this.children.length;i++){
				var child = this.children[i];
				if(child.id == nodeId)
					return child;
				else{
					var cc = child.getChildNode(nodeId);
					if(cc!=null)
						return cc;
				}
			}
		}
		return null;
	};
	//从子节点中找属性等于给定值的节点
	this.findChild = function(pName,value){
		if(this.children==null || this.children.length ==0)
			return null;
		for(var i=0;i<this.children.length;i++){
			var child = this.children[i];
			if(child.data[pName]==value)
				return child;
		}
		return null;
	}
	this.moveToBeforeAt = function(node){
		var p = this.parent.children.indexOf(node);
		this.moveTo (p);
	}
	this.moveTo = function(index){
		var brothers = this.parent.children;
		var oldIndex  = brothers.indexOf(this);
		if(index>brothers.length||index<0){
			alert("超出范围");
		}
		brothers.removeAt(oldIndex);
		brothers.insertAt(this,index);

		index = brothers.indexOf(this);
		if(index == brothers.length-1){//移动到最后面
			this.getElement().appendTo(this.getContainer());
			this.getChildrenBox().appendTo(this.getContainer());
			this.paint();
			if(this.getChildrenBox().length>0)
				this.refreshNodesHead(this.children);
			//原先的最后节点要重新绘制
			var brother = brothers[brothers.length-2];
			brother.paint();
			if(brother.getChildrenBox().length>0)
				brother.refreshNodesHead(brother.children);
		}else{	//移动到中间某个位置

			var brother = brothers[index+1]; //新位置之后的兄弟节点
			this.getElement().insertBefore(brother.getElement());
			var cbox = this.getChildrenBox();
			if(cbox!=null)
				cbox.insertBefore(brother.getElement());
			if(oldIndex==brothers.length-1){
				var newLastBrother = brothers[oldIndex];
				newLastBrother.paint();
				if(newLastBrother.getChildrenBox().length>0)
					newLastBrother.refreshNodesHead(newLastBrother.children);
				this.paint();
				if(this.getChildrenBox().length>0)
					this.refreshNodesHead(this.children);
			}
		}
	}



	this.moveDown = function(){
		var brothers = this.parent.children;
		var currentIndex  = brothers.indexOf(this);
		if(currentIndex >= brothers.length-1)
			return ;

		var brother = brothers[currentIndex+1];
		//调换json对象的位置
		brothers[currentIndex]=brother;
		brothers[currentIndex+1]=this;
		//调换dom对象的位置
		brother.getElement().insertBefore(this.getElement());
		brother.getChildrenBox().insertBefore(this.getElement());
		this.paint();
		brother.paint();

		if(this.getChildrenBox().length>0)	//兄弟节点的子节点已经绘制，则需要重新绘制
			this.refreshNodesHead(this.children);
		if(brother.getChildrenBox().length>0)	//兄弟节点的子节点已经绘制，则需要重新绘制
			this.refreshNodesHead(brother.children);
	}

	this.moveUp = function(){
		var brothers = this.parent.children;
		var currentIndex  = brothers.indexOf(this);
		if(currentIndex <=0)
			return ;
		var brother = brothers[currentIndex-1];
		brother.moveDown();
	}


	this.remove = function(){
		if(this.isRoot()){
			alert("不允许删除根节点");
			return ;
		}
		var isLast = this.isLast();

		if(this.getTree().focusedNode==this)
			this.getTree().focusedNode = null;
		if(this.getTree().selectedNodes!=null){
			this.getTree().selectedNodes.remove(this);
		}

		this.getElement().remove();
		this.getChildrenBox().remove();

		this.parent.children.remove(this);

		if(isLast && this.parent.children.length>0){
			//重绘新成为最后一个兄弟节点的节点
			var lastBrother = this.parent.children[this.parent.children.length-1];
			lastBrother.paint();
			if(lastBrother.getChildrenBox().length>0)	//兄弟节点的子节点已经绘制，则需要重新绘制
				this.refreshNodesHead(lastBrother.children);
		}

		if(this.parent.children.length==0){
			this.parent.paint();
			this.parent.getChildrenBox().remove();
		}

		this.parent = null;
		this.children = null;
	};

	//重绘兄弟节点的子节点头部
	this.refreshNodesHead = function(nodes){
		if(nodes!=null && nodes.length>0){
			for(var i=0;i<nodes.length;i++){
				var node = nodes[i];
				node.paint();
				if(node.getChildrenBox().length>0)
					this.refreshNodesHead(node.children);
			}
		}
	};

	this.insertBefore = function(data,node){
		var current = new ZY.UI.TreeNode(data,this);
		this.children.insertBefore(current, node);
		this.paint();
		if(this.children.length>1){
			node.paint();	//重绘兄弟节点
			if(node.getChildrenBox().length>0){	//兄弟节点的子节点已经绘制，则需要重新绘制
				this.refreshNodesHead(node.children);
			}
		}
		this.open();
		if(current.getElement().length==0){
			current.paint();
			current.getElement().insertBefore(node.getElement());
		}
	};


	this.addChild = function(data){
		var node = new ZY.UI.TreeNode(data,this);
		this.children.push(node);
		this.paint();
		if(this.children.length>1){
			var preBrother = this.children[this.children.length-2];
			preBrother.paint();	//重绘兄弟节点
			if( preBrother.getChildrenBox().length>0){	//兄弟节点的子节点已经绘制，则需要重新绘制
				this.refreshNodesHead(preBrother.children);
			}
		}
		this.open();
		if(node.getElement.length==0)
			node.paint();

	};

	/**
	 * 更新当前节点的内容，不影响子节点
	 * @param data
	 */
	this.update = function(data){
		for(var key in data){
			if(key!=this.childProperty){
				this.data[key] = data[key];
			}
		}
		this.treeNodeType = this.data.treeNodeType;
		this.paint();
	}


	this.changeState = function(){
		if(this.state=="closed"){
			this.open();
		}else{
			this.close();
		}
	};

	this.blur = function(){
		this.focused = false;
		this.getElement().removeClass("focus");
	};

	this.changeChecked = function(){
		var checkBox = this.getCheckBox().children()[0];
		this.setChecked(checkBox.checked);
		this.setChildChecked(checkBox.checked);
		this.setParentChecked();
	}

	this.ifChecked = function(){
		var ifChecked = true;
		var children = this.children;
		for(var i=0;i<children.length;i++)	{
			if(children[i].checked!=true && children[i].checked!='true'){
				ifChecked = false;
				break;
			}
		}
		return ifChecked;
	}
	this.setChecked = function(flag,downOrUp){
		var checkBox = this.getCheckBox().children()[0];
		if(checkBox!=null)
			checkBox.checked = flag;
		this.data["selected"] = this.flag;
		this.checked = flag;
		if(downOrUp=="down"){
			this.setChildChecked(flag);
		}else if(downOrUp=="up"){
			this.setParentChecked(flag);
		}
	}
	this.setChildChecked = function(flag){
		if(this.hasChildren()){
			var children = this.children;
			for(var i=0;i<children.length;i++)	{
				children[i].setChecked(flag,"down");
			}
		}
	}
	this.setParentChecked = function (){
		if(this.getParent()!=null){
			var parent = this.getParent();
			parent.setChecked(parent.ifChecked(),"up");
		}
	}

	this.setParentOpen=function(){
		var parent = this.parent;
		parent.open();
		if(parent.isRoot()==false){
			parent.setParentOpen();
		}
	}

	this.focus = function(sure){
		var isFocused = this.focused;
		//如果已经是焦点节点，则不作处理
		if(isFocused)
			return null;
		if(this.getTree().focusedNode!=null){
			this.getTree().focusedNode.blur();
		}
		this.getTree().focusedNode = this;
		if(isFocused == false || sure) {
			this.focused = true;
			if(this.isPainted()==false){
				this.setParentOpen();
			}
			this.getElement().addClass("focus");
			//判断当前节点是否在可视区域，如果不可见，则移动树使之可见
			var y = this.getY();
			var box = this.getTree().box;
			if(y<box[0].scrollTop){
				box[0].scrollTop = y;
			}else if((y+30)>box[0].scrollTop+box[0].clientHeight){
				box[0].scrollTop = y+30-box[0].clientHeight;
			}
		} else {
			this.focused = false;
			this.blur();
			if(this.getTree().focusedNode==this)
				this.getTree().focusedNode = null;
			if(this.getTree().selectedNodes!=null){
				this.getTree().selectedNodes.remove(this);
			}
		}
		if(this.data.accessible=="unAccessible"){
			//hidden right icon
			$(".rightSection .icon").hide();
			$(".leftSection .icon").hide();
			this.getTree().unAccessibleFocused(this);
		}else{
			this.getTree().onChanged(this);
			$(".rightSection .icon").show();
			$(".leftSection .icon").show();
		}
	};

	this.getTree = function(){
		if(this.parent.constructor == ZY.UI.Tree)
			return this.parent;
		else
			return this.parent.getTree();
	};
	this.getParent = function(){
		if(this.parent.constructor == ZY.UI.Tree)
			return null;
		else return this.parent;
	}

	//子节点由哪个属性决定
	var childProperty = this.getTree().context["children"];
	var cps = childProperty.split("|");
	var childrenData = [];
	for(var i=0;i<cps.length;i++){
		var childrenArr = data[cps[i]];
		if(childrenArr!=null)
			childrenData.addAll(childrenArr);
	}


	if(childrenData!=null && childrenData.length>0){
		for(var i=0;i<childrenData.length;i++){
			var child = new ZY.UI.TreeNode(childrenData[i],this);
			this.children.push(child);
		}
	}


	//父节点的childrenBox
	this.getContainer = function(){
		var containerId = null;
		if(this.parent.constructor == ZY.UI.Tree){
			containerId = this.parent.id;
		}else{
			containerId = "zy_tree_children_"+this.parent.id;
		}

		var container = $("#"+containerId);
		return container;
	};

	this.hasChildren = function(){
		if(this.children!=null && this.children.length>0)
			return true;
		else
			return false;
	};

	this.isLast = function(){
		var index = this.parent.children.indexOf(this);
		if(index==this.parent.children.length-1)
			return true;
		return false;
	};

	this.isFirst = function(){
		var index = this.parent.children.indexOf(this);
		if(index ==0)
			return true;
		return false;
	};

	this.isRoot = function(){
		if(this.parent.constructor==ZY.UI.Tree && this.parent.children.indexOf(this)==0)
			return true;
		return false;
	};

	//当前节点的dom
	this.getElement = function(){
		var e = $("#zy_tree_node_"+this.id);
		return e;
	};



	this.getChildrenBox = function(){
		var e = $("#zy_tree_children_"+this.id);
		return e;
	};

	this.getTitleBox = function(){
		var e = $("#zy_tree_title_"+this.id);
		return e;
	};
	this.getCheckBox = function(){
		var e = $("#zy_tree_checkbox_"+this.id);
		return e;
	};
	this.getImgBox = function(){
		var e = $("#zy_tree_imgbox_"+this.id);

		return e;
	};
	this.getLineBox = function(){
		var e = $("#zy_tree_linebox_"+this.id);
		return e;
	};

	//向上递归父节点
	this.paintHead = function(node){
		var sb = [];
		var className = "";
		var evtStr = "";
		if(node==null){
			if(this.hasChildren()){
				if(this.isRoot()){
					if(this.getTree().children.length>1){
						if(this.state=="opened"){
							className="minus_top";
						}else{
							className="plus_top";
						}
					}else{
						if(this.state=="opened"){
							className="minus_root";
						}else{
							className="plus_root";
						}
					}
				}else if(this.isLast()){
					if(this.state=="opened"){
						className="minus_bottom";
					}else
						className="plus_bottom";
				}else if(this.isFirst()){
					if(this.state=="opened"){
						className="minus_center";
					}else{
						className="plus_center";
					}
				} else{
					if(this.state=="opened"){
						className="minus_center";
					}else
						className="plus_center";
				}
			}else{
				if(this.isLast()){
					className="line_bottom";
				}else if(this.isFirst()){
					className="line_center";
				}else{
					className="line_center";
				}
			}
			node = this;
			var treeId = this.getTree().id;
			var nodeId = this.id;

			if(node.hasChildren())
				evtStr = 'onclick="ZY.UI.TreeEvents.onStateSwitch(\''+treeId+'\','+nodeId+')"';

		}else{
			if(node.isLast())
				className = "blank";
			else
				className = "line_conn";
		}



		sb.push("<div class='"+className+"' id='zy_tree_linebox_"+this.id+"'");
		sb.push( evtStr);
		sb.push("></div>");

		if(node==this){
			var className4Img = this.getClassName4Img();
			sb.push("<div class='nodeIcon "+className4Img+"' id='zy_tree_imgbox_"+this.id+"'");
			sb.push( evtStr);
			sb.push("></div>");
			if(this.getTree().ifMultSelected == true){
				sb.push("<div class='checkBox' id='zy_tree_checkbox_"+this.id+"'>");
				sb.push("<input type='checkbox' "+(this.checked==true?"checked":"")+" onclick='ZY.UI.TreeEvents.onCheckBoxClicked(\""+this.getTree().id+"\","+this.id+")'/>");
				sb.push("</div>");
			}

		}

		if(node.parent.constructor==ZY.UI.TreeNode)
			sb.insertAt(this.paintHead(node.parent),0);
		return sb.join("");

	};


	this.getClassName4Img = function(){
		return this.treeNodeType;
	}


	this.getY = function(){
		var box =  this.getTree().box;
		var element = this.getElement();
		var y =  element[0].offsetTop-box[0].offsetTop;
		return y;
	}
	this.getViewStr = function(){
		var viewPropertyName = this.getTree().context.view;
		var views = viewPropertyName.split("|");
		for(var i=0;i<views.length;i++){
			var s = this.data[views[i]];
			if(s!=null)
				return s;
		}
		return "";
	}

	this.isPainted = function(){
		var element = this.getElement();
		if(element.length==0){
			return false;
		}
		return true;
	}

	//绘制当前节点
	this.paint = function(){
		var element = this.getElement();
		if(element.length==0){
			element = $("<div/>");
			element.attr("id", "zy_tree_node_"+this.id);
			element.addClass("node");
			this.getContainer().append(element);
			var sb = [];
			sb.push("<div class='title' ");
			sb.push("onmouseup='ZY.UI.TreeEvents.onNodeup(\""+this.getTree().id+"\","+this.id+")'");
			sb.push("id='zy_tree_title_"+this.id+"' ");
			sb.push("name='zy_tree_title_"+this.id+"'");
			sb.push(">"+this.getViewStr());
			sb.push("</div>");
			var head = this.paintHead();
			sb.insertAt(head,0);
			element.html(sb.join(""));
		}else{
			var sb = [];
			sb.push("<div class='title' ");
			sb.push("onmouseup='ZY.UI.TreeEvents.onNodeup(\""+this.getTree().id+"\","+this.id+")'");
			sb.push("id='zy_tree_title_"+this.id);
			sb.push("'>"+this.getViewStr());
			sb.push("</div>");
			var head = this.paintHead();
			sb.insertAt(head,0);
			element.html(sb.join(""));

		}
		//判断是否需要增加扩展样式
		var context = this.getTree().context;
		if(context.extendedStyle!=null){
			var extendedStyle =this.data[context.extendedStyle];
			element.addClass(extendedStyle);
		}

		var items = element.children() ;
		var len = 2;

		for(var i=0;i<items.length;i++){
			var item = items[i];
			if(item.className=="title" || item.className=="focus")
				len += this.getViewStr().asciiLen()*6+4;
			else
				len += 30;
			}
		if(len<element.parent().width()){
			element.width("100%");
		}else{
			element.width(len);
		}

	};

	//关闭当前节点
	this.close = function(){
		var childrenBox = this.getChildrenBox();
		if(childrenBox.length>0){
			childrenBox.css("display","none");
		}
		var titleBox  = this.getTitleBox();
		var imgBox = this.getImgBox();
		var lineBox = imgBox.prev();

		if(this.isRoot()){
			if(this.parent.children.length>1){
				lineBox.attr("class","plus_top");
			}else
				lineBox.attr("class", "plus_root");
		}else if(this.isLast()) {
			lineBox.attr("class","plus_bottom");
		}else{
			lineBox.attr("class","plus_center");
		}
		this.state = "closed";
	};

	//展开当前节点
	this.open = function(){
		if(this.children==null || this.children.length<1)
			return ;
		var element = this.getElement();
		var title = this.getTitleBox();
		var imgBox = this.getImgBox();
		var lineBox = imgBox.prev();

		this.state = "opened";
		if(this.isRoot()){
			if(this.parent.children.length>1)
				lineBox.attr("class","minus_top");
			else
				lineBox.attr("class","minus_root");
		}else if(this.isLast()){
			lineBox.attr("class","minus_bottom");
		}else{
			lineBox.attr("class","minus_center");
		}

		var childrenBox = this.getChildrenBox();
		if(childrenBox.length>0){
			childrenBox.css("display","");
			return;
		}

		childrenBox = $("<div></div>");
		childrenBox.attr("id", "zy_tree_children_"+this.id);
		childrenBox.addClass("childrenBox");
		childrenBox.insertAfter(element);
		for(var i=0;i<this.children.length;i++){
			var child = this.children[i];
			child.paint();
		}
	};
};

ZY.UI.DefaultAlign = {
	"String":"center",
	"Link":"center",
	"Number":"right",
	"Boolean":"center",
	"Select":"center",
	"ProgressBar":"center",
	"Date": "center",
	"undefined": "left",
	"null": "left",
	"button":"left"
};

ZY.UI.CellRenders = {
	"TreeNode":{
		render:function(grid,row,col){
			var deep = row.data._deep;
			var data = row.getCellValue(col);
			if(data===null ||  data===undefined){
				return "&nbsp;";
			}
			data = data+"";
			data=data.replace("<","&lt;");
			data=data.replace(">","&gt;");
			if(deep>0){
				width = 24*deep;
				data = "<div style='width:"+width+"px;display:inline-block;'></div>"+data;
			}
			return data;
		},
	},
	"String":{
		render:function(grid,row,col){
			var data = row.getCellValue(col);
			if(data===null ||  data===undefined){
				return "&nbsp;";
			}
			data = data+"";
			data=data.replace("<","&lt;");
			data=data.replace(">","&gt;");
			return data;
		},
		editor:{
			render:function(grid,row,col){
				var data = row.getCellValue(col);
				if(data==null || data==undefined)
					data = "";
				var editor = [];
				editor.push("<input type='text' value='"+data+"'/>");
				return editor.join("");
			},
			getValue:function(grid,row,col){
				var v = row.getCellElement(col.name).find("input")[0].value;
				if(v==null )
					v = "";
				return v;
			}
		}
	},
	"RefObj":{
		render:function(grid,row,col){
			var data = row.getCellValue(col);
			if(data===null ||  data===undefined){
				return "&nbsp;";
			}
			data = data+"";
			data=data.replace("<","&lt;");
			data=data.replace(">","&gt;");
			return data;
		},
		editor:{
			render:function(grid,row,col){
				//直接弹出新窗口，获得引用
				var s = col.fn;
				v = eval(s);
				var data = row.getCellValue(col);
				if(data===null ||  data===undefined){
					return "&nbsp;";
				}
				return data;
			},
			getValue:function(grid,row,col){

			}
		}
	},
	"Number":{
		render:function(grid,row,col){
			var data = row.getCellValue(col);
			var n = new Number(data);
			var r ;
			if(!isFinite(n)) n = new Number(0);
			if(col.format!=null)
				r = n.format(col.format);
			else
				r = n;
			return r;
		},
		editor:{
			render:function(grid,row,col){
				var data = row.getCellValue(col);
				if(data==null)
					data = 0;
				var editor = [];
				editor.push("<input type='text' value='"+data+"'/>");
				return editor.join("");
			},
			getValue:function(grid,row,col){
				var v = row.getCellElement(col.name).find("input")[0].value;
				if(v==null || v =="")
					v = 0;
				return new Number(v);
			}
		}

	},
	"Boolean":{
		render:function(grid,row,col){
			var data = row.getCellValue(col);
			if(data==null)
				data = false;
			var c = (data===true)?'是':'否';
			return c;
		},
		editor:{
			render:function(grid,row,col){
				var data = row.getCellValue(col);
				if(data==null)
					data = false;
				var editor = [];
				editor.push("<input type='checkbox' "+(data==true?"checked":"")+"/>");
				return editor.join("");
			},
			getValue:function(grid,row,col){
				var v = row.getCellElement(col.name).find("input")[0].checked;
				return v;
			}
		}
	},
	"Link":{
		render:function(grid,row,col){
			var data = row.getCellValue(col);
			if(data===null || data===undefined) data = "";
			var t=[];
			t.push('<a style=""');
			t.push(' href="javascript:ZY.UI.GridEvents.onCellLink(\''+grid.id+'\',\''+row.index+'\',\''+col.name+'\');">');
			t.push(data);
			t.push('</a>');
			return t.join('');
		}
	},
	"button":{
		render: function(grid,row,col){
			var idArray = row.getCellValue(col);
			if(idArray===null || idArray===undefined)
				idArray = [];
			var options = col.options;
			var t=[];
			for(var i=0;i<options.length;i++){
				if(idArray.contains(options[i].id)){
					var iconName = options[i].name;
					var iconType = options[i].label;
					var iconAction = options[i].cmd;
					t.push("<a alt="+iconName+" class='icon "+iconType+"' id="+iconType+" href='###' onclick='"+iconAction+"'></a>");
				}
			}
			return t.join('');
		}
	},
	"Select":{
		render: function(grid,row,col){
			var id = row.getCellValue(col);
			var options = col.options;
			var value = null;
			for(var i=0;i<options.length;i++){
				if(options[i].id == id) {
					value=options[i];
					break;
				}
			}
			if(value!=null) return value.name;
			else return "&nbsp;";
		}
	},
	"ProgressBar":{
		render: function(grid,row,col){
			var id = row.getCellValue(col);
			var options = col.options;
			var value = null;
			for(var i=0;i<options.length;i++){
				if(options[i].id == id) {
					if(options[i].name == "图片") {
						options[i].name="<div class='taskLoading'></div>";
					}
					value=options[i];
					break;
				}
			}
			if(value!=null) return value.name;
			else return id;
		}
	},
	"Date":{
		render:function(grid,row,col){
			var date = row.getCellValue(col);
			if(date===null ||  date===undefined){
				date = "&nbsp;";
			}else{
				if(typeof(date)=="number")
					date = new Date(date);
				else if(typeof(date)=="string")
					date = date.toDate();
				else if(date instanceof Date){
				}
				if(col.format!=null){
					col.format = col.format.trim();
					col.format = col.format.replace("HH","hh");
				}
				date = date.format(col.format);
			}
			return date;
		}
	},
	"ImgButton":{
		render:function(grid,row,col){
			var data = row.getCellValue(col);
			if(data===null || data===undefined) data = "";
			var t=[];
			t.push('<a style="TEXT-DECORATION: underline;cursor:pointer;"');
			t.push(' onclick="ZY.UI.GridEvents.onImgButton(\''+data+'\');">');
			t.push('查看图片</a>');
			return t.join('');
		}
	}
};

ZY.UI.GridEvents = {
	onCellLink:function(gridId,rowIndex,colName){
		var grid = ZY.UI.Grids[gridId];
		var row = grid.getRowByIndex(rowIndex);
		var column = row.getColumnByName(colName);
		eval(column.fn);
	},
	onImgButton:function(data){
		ZY.UI.imgShow(data.split(","),0);
	},
	RowsChecked:function(gridId,inputObj){	//表头复选框事件
		var grid = ZY.UI.Grids[gridId];
		var rows = grid.rows;
		for(var i=0;i<rows.length;i++){
			if(rows[i].getRowCheckbox()[0].disabled == false){
				rows[i].setSelected(inputObj.checked);
			}
		}
	},
	onRowChecked:function(gridId,rowIndex){ //行第一列复选框事件
		var grid = ZY.UI.Grids[gridId];
		var row = grid.getRowByIndex(rowIndex);
		row.setSelected();
		grid.refreshHeadSelectStatus();
		if(typeof(grid.options.onChanged)=="function"){
			grid.options.onChanged.call(grid,row);
		}else
			grid.onChanged(row);
	},
	onClicked:function(gridId,rowIndex){
		ZY.UI.GridEvents.onRowClicked(gridId,rowIndex);
	},
	onRowClicked:function(gridId,rowIndex){
		var grid = ZY.UI.Grids[gridId];
		var row = grid.getRowByIndex(rowIndex);
		if(grid.context.selectedMode=="S"){	//单选
			var rows = grid.getSelectedRows();
			if(rows!=null && rows.length>0){
				if(row!=rows[0]){
					rows[0].setSelected(); //取消选中状态
					row.setSelected();
				}
			}else
				row.setSelected();

			//更行表头的checkbox，触发事件
			grid.refreshHeadSelectStatus();
			if(typeof(grid.options.onChanged)=="function"){
				grid.options.onChanged.call(grid,row);
			}else
				grid.onChanged(row);
		}else{
			//多选的情况下，只能用checkbox选中
			//row.setSelected();
		}

	},
	onRowdblClicked:function(gridId,rowIndex){
		var grid = ZY.UI.Grids[gridId];
		var row = grid.getRowByIndex(rowIndex);
		if(typeof(grid.options.onDbClicked)=="function"){
			grid.options.onDbClicked.call(grid,row);
		}else
			grid.onDbClicked(row);
	},
	onPageChange:function(gridId,select){
		var grid = ZY.UI.Grids[gridId];
		var pageSize = 1*select.options[select.selectedIndex].text ;
		grid.gotoPage(pageSize,0);
		grid.refreshHeadSelectStatus();
	}
};
ZY.UI.Grids = {};
ZY.UI.Grid = function(options,columns,context){
	if(options==null)
		return;
	if(options instanceof Object && options.columns!=null){
		var context = {};
		context.readonly = options.readonly;
		context.selectedMode = options.selectedMode;
		context.pageController = options.pageController;
		context.title = options.title;
		var grid =  new ZY.UI._Grid(options.box, options.columns, context);
		grid.options = options;
		return grid;
	}else{
		return new ZY.UI._Grid(options, columns, context);
	}
}

ZY.UI._Grid = function(box,columns,context){
	this.grid = this;
	this.cols = columns;
	this.rows = [];
	this.context = context;
	this.rowSeq = -1;

	this.getRowSeq=function(){
		this.rowSeq++;
		return this.rowSeq;
	}

	var element = null;
	if(typeof(box)=="string" ){
		element =$("#"+box);
	}else{
		element = $(box);
	}
	this.id = element.attr("id");

	if(this.id==null || this.id.length<1){
		this.id = ZY.UI.getNextId();
		element.attr("id",this.id);
	}
	ZY.UI.Grids[this.id] = this;

	element.html("");
	element.addClass("zy_grid");
	/*要不要去掉鼠标选择功能？
	element.bind("mousemove",function(){
		window.getSelection ? window.getSelection().removeAllRanges() : document.selection.empty();
	})*/


	this.headWide = null;
	this.headDeep = null;
	this.headHeight = null;
	this.bodyHeight = null;
	this.colsWidth = null;
	this.renders = {};



	var sb = [];
	//paint title
	if(context.title!=null){
		sb.push('<div id="title_'+this.id+'" class="title"><div>');
		sb.push(context.title);
		sb.push('</div></div>');
	}
	sb.push('<div class="box">')
	sb[sb.length] = '<div id="head_' + this.id + '"  class="head" >';
	sb[sb.length] = 'head area for column caption';
	sb[sb.length] = '</div>';
	sb[sb.length] = '<div id="body_' + this.id + '"  class="body" >';
	sb[sb.length] = '</div>';
	if(this.context==null)
		this.context = {};
	if(this.context.pageController==null){
		//sb[sb.length] = '<div id="pageEmpty_'+this.id+'"  class="pageBox" style="display:none;">';
		//sb[sb.length] = '</div>';
	}else{
		sb[sb.length] = '<div id="page_' + this.id + '"  class="pageBox" style="width:500px;">';
		sb[sb.length] = 'bottom area for page controller';
		sb[sb.length] = '</div>';
	}
	sb.push('</div>');
	element.html(sb.join(""));


	this.refreshHeadSelectStatus = function(){
		var selectedAll = true;
		for(var i=0;i<this.rows.length;i++){
			var row = this.rows[i];
			if(!row.selected){
				selectedAll=false;
				break;
			}
		}
		if(i==0)
			selectedAll = false;
		var oInput = $("#tHead_"+this.id).find("input[type=checkbox]");
		if(this.context.selectedMode!="S" ){
			oInput[0].checked = selectedAll;
		}
	};




	this.onChanged = function(row){
	};
	this.onDbClicked = function(row){
	};
	this.getElement = function(){
		return $("#"+this.id);
	};

	this.getHeadBox = function(){
		return $("#head_"+this.id);
	};

	this.getHeadTable= function(){
		return $("#tHead_"+this.id);
	};

	this.getBodyTable = function(){
		return $("#tBody_"+this.id);
	};

	this.getBodyBox= function(){
		return $("#body_"+this.id);
	};

	this.getPageBox = function(){

		if(this.context.pageController==null)
			return $("#pageEmpty_"+this.id);
		else
			return $("#page_"+this.id);
	};

	/**
	 * 获得列宽
	 */
	this.getColWidth = function(col){
		if(col.width!=null)
			return col.width;
		else
			return 100;
	};

	/**
	 * 获得整个表的宽度
	 */
	this.calcTableWidth = function(){
		var width = 0;
		var end = 0;
		if(this.leafCols[this.leafCols.length-1].caption=="&nbsp;")
			end = this.leafCols.length-1;
		else
			end = this.leafCols.length ;

		for(var i=0;i<end ;i++){
			width = width+1*this.getColWidth(this.leafCols[i])+1;
		}
		if(this.context.selectedMode!='S')
			width +=37;
		return width;
	};

	/**
	 * 新增一个空白列
	 */
	this.resetColumns = function(){
		var col = {};
		col.type = "String";
		col._rowspan = this.countHeadDeep();
		col._colspan = 1;
		col.caption = "&nbsp;";
		this.leafCols.push(col);
		this.cols.push(col);
	}

	/**
	 * 计算表头单元格占据几行
	 */
	this.calcHeadCellRowSpan = function(cols,deep){
		if(cols===undefined){
			cols = this.cols;
			deep = this.countHeadDeep();
		}
		for(var i=0;i<cols.length;i++){
			var col = cols[i];
			col._rowspan = deep-col._deep+1;
			var children = col.children;
			if(children!==undefined && children.length>0){
				this.calcHeadCellRowSpan(children,deep-1);
			}
		}
	};

	/**
	 * 判断表示是否可编辑
	 */
	this.isEditable = function(){
		if(this._isEditable)
			return this._isEditable;
		else{
			this._isEditable = this.getEditableFromColumns();
			return this._isEditable;
		}
	}

	this.getEditableFromColumns = function(columns){
		if(columns==null)
			columns = this.cols;
		for(var i=0;i<columns.length;i++){
			var col = columns[i];
			if(col.editable == true){
				return true;
			}else{
				if(col.children!=null){
					return this.getEditableFromColumns(col.children);
				}
			}
		}
		return false;
	}

	/**
	 * 计算表头深度
	 */
	this.countHeadDeep= function(){
		if(this.headDeep!==null)
			return this.headDeep;
		var children = this.cols;
		var maxDeep = 0;
		if(children!=null && children.length>0){
			for(var i=0;i<children.length;i++){
				var col = children[i];
				var deep = this.countColDeep(col);
				if(deep>maxDeep){
					maxDeep = deep;
				}
			}
		}
		this.headDeep = maxDeep;
		return this.headDeep;
	};

	/**
	 * 计算当前列向下数共有多少层
	 */
	this.countColDeep= function(col){
		var children = col.children;
		var maxDeep = 0;
		if(children!=null && children.length>0){
			for(var i=0;i<children.length;i++){
				var child = children[i];
				var deep = this.countColDeep(child);
				if(deep>maxDeep){
					maxDeep = deep;
				}
			}
		}
		col._deep =  maxDeep +1;
		return maxDeep+1;
	};

	/**
	 * 计算表总共多少列，因为子列的存在，不能用columns.length来获得
	 */
	this.countHeadWide= function(){
		if(this.headWide !=null)
			return this.headWide;
		var wide = 0;
		this.leafCols = [];	//表头的叶子节点
		var children=this.cols;
		if(children!=null && children.length>0){
			for(var i=0;i<children.length;i++){
				var col = children[i];
				col.parent = null;
				wide = wide + this.countColWide(col);
			}
		}
		this.headWide = wide;
		return wide;
	};

	/**
	 * 计算当前列包含多少叶子列
	 */
	this.countColWide = function(col){
		var children = col.children;
		if(children==null || children.length==0){
			col._colspan = 1;
			this.leafCols.push(col);
		}else{
			col._colspan=0;
			for(var i=0;i<children.length;i++){
				var child = children[i];
				child.parent = col;
				child.brotherIndex = i;
				var cw = this.countColWide(child);
				col._colspan = col._colspan+cw;
			}
		}
		return col._colspan;
	};

	/**
	 * 获得column group
	 */
	this.getColGroup= function(n) {
		if(this.colGroup!=null) return this.colGroup;
		var ua = navigator.userAgent.toLowerCase();
		var sb = [];
		sb.push("<COLGROUP id='colgroup_"+this.id+"_"+n+"'>");
		if(this.context.selectedMode!="S")
			sb.push('<COL width="36"></COL>');

		for(var i=0;i<this.leafCols.length;i++){
			if(i==this.leafCols.length-1){
				sb.push("<COL></COL>");
			}else{
				var col = this.leafCols[i];
				sb.push('<COL width="');
				sb.push(this.getColWidth(col));
				sb.push('" ');
				sb.push('/>');
			}

		}
		sb.push("</COLGROUP>");
		this.leftColGroup = sb;
		return sb;
	};






	/**
	 * 绘制表头的一个单元格
	 */
	this.paintHeadCell = function(col,rowsbuf){
		var rowbuf = rowsbuf[col._selfDeep];
		var cellbuf = [];
		cellbuf.push('<TD style="text-align:center;"');
		cellbuf.push(' colspan='+col._colspan);
		cellbuf.push(' rowspan='+col._rowspan);
		cellbuf.push('>' + col.caption);
		cellbuf.push('</TD>');
		rowbuf[rowbuf.length] = cellbuf.join("");
		if(col.children!== undefined && col.children.length>0){
			var children = col.children;
			for(var i=0;i<children.length;i++){
				var child = children[i];
				child._selfDeep = col._selfDeep +1;
				this.paintHeadCell(child,rowsbuf);
			}
		}
	};

	/**
	 * 绘制表头
	 */
	this.paintHeader = function(){
		var sb = [];
		sb.push('<TABLE class="headTable" id=tHead_'+this.id+' style="TABLE-LAYOUT:fixed;" cellSpacing=0 cellPadding=0 border=0>');
		sb.push(this.getColGroup("head").join(""));

		var rowsbuf = [];
		var deep = this.countHeadDeep();
		for(var i=0;i<deep;i++){
			var rowbuf = [];
			rowsbuf.push(rowbuf);
			rowbuf.push('<TR>');

		}
		if(this.context.selectedMode!="S" ){
			rowsbuf[0].push('<td style="text-align:center;"');
			rowsbuf[0].push(' rowspan='+deep);
			rowsbuf[0].push('><input type="checkbox"  onclick="ZY.UI.GridEvents.RowsChecked(\''+this.id+'\',this)"></td>');
		}
		for(var i=0;i<this.cols.length;i++){
			var col = this.cols[i];
			col._selfDeep = 0;
			this.paintHeadCell(col,rowsbuf);
		}
		for(var i=0;i<deep;i++){
			var rowbuf = rowsbuf[i];
			rowbuf.push('</TR>');
			rowsbuf[i]=rowbuf.join("");
		}
		sb.push(rowsbuf.join(""));
		sb.push('</TABLE>');
		return sb.join("");
	};

	this.paintPageBox = function(col){
		if(this.context.pageController!=null){
			var pageBar = $("<div>")
				.attr("id","pageBar_"+this.id)
				.addClass("pageBar");
			this.getPageBox().html("");
			this.getPageBox().html('<div class="pageCount" id="pageCount_' + this.id + '"></div>');
			this.getPageBox().append(pageBar);
			var sizeSelector = $("<select onchange='ZY.UI.GridEvents.onPageChange(\""+this.id+"\",this)'>");
			sizeSelector.attr("id","pageSize_"+this.id);
			sizeSelector.html("<option>10</option><option>15</option><option>20</option><option>30</option>");
			pageBar.append(sizeSelector);

		}
	};

	this.gotoPage = function(pageSize,pageIndex){
		var fn = this.context.pageController.pageRead;
		//fn = fn.substring(0,fn.indexOf("("));
		//fn = fn+"("+pageSize+","+pageIndex+")";
		//var page = eval(fn);
		if(pageSize==null)
			pageSize = this.currentPage.size;
		if(pageIndex==null)
			pageIndex = null;
		var page = fn(pageSize,pageIndex);
		if(page==null)
			return ;
		//alert(JSON.stringify(page));
		this.bindPage(page);
		var bodyBox=this.getBodyBox();
		this.grid.getHeadTable().css("margin-left",-bodyBox.scrollLeft());
		this.grid.refreshHeadSelectStatus();
	}



	this.insertPageNo = function(no){
		var p = $("<span>");
		p.html(no);
		this.pageBar.append(p);
		if(isNaN(no)==false){
			p.bind("click",function(){
				var span = $(this);
				var index = 1*span.html()-1;
				var pageBar = span.parent();
				var id = pageBar.attr("id");
				id = id.substring(8);
				var grid = ZY.UI.Grids[id];
				grid.gotoPage(grid.currentPage.size,index);
			});
		}
	};

	this.refreshPageBox = function(page){
		$("#pageCount_"+this.id).html("总数: "+page.elementAmount);
		var pageBar = $("#pageBar_"+this.id);
		pageBar.html("");
		this.pageBar = pageBar;
		var sections = [];

		sections.push([0,1]);
		sections.push([page.index-1,page.index,page.index+1]);
		sections.push([page.amount-2,page.amount-1]);

		if(sections[1][0]-sections[0][1]<=2){	//如果第一段和第二段距离很近，则合并这两段
			if(sections[1][0]<0){
				sections[1][0]=0;
				sections[1][1]=1;
				sections[1][2]=2;
			}
			sections[0] = [];
			for(var i=0;i<=sections[1][2];i++){
				sections[0].push(i);
			}
			sections.removeAt(1);
		}

		var pre = sections[sections.length-2];
		var next = sections[sections.length-1];
		if(next[0]-pre[pre.length-1]<=2){	//如果第二段和第三段距离比较近，则合并这两段
			if(next[0]<0)
				next[0] = 0;
			if(next[1]<0)
				next.removeAt(1);
			var j = 0;
			for(var i=pre[0];i<=next[next.length-1];i++){
				pre[j] = i;
				j = j+1;
			}
			sections.remove(next);
		}
		if(page.amount==0){
			sections = [];
		}

		var end = page.amount -1;
		for(var i=0;i<sections.length;i++){
			while(sections[i][sections[i].length-1]>end){
				sections[i].removeAt(sections[i].length-1);
			}
			if(i!=0)
				this.insertPageNo("...");
			for(var j=0;j<sections[i].length;j++){
				this.insertPageNo(sections[i][j]+1);
			}
		}

		var sizeSelector = $("<select class='pageSelect' onchange='ZY.UI.GridEvents.onPageChange(\""+this.id+"\",this)'>");
		sizeSelector.attr("id","pageSize_"+this.id);

		var sb = [];
		sb.push("<option "+(page.size==10?"selected":"")+">10</option>");
		sb.push("<option "+(page.size==20?"selected":"")+">20</option>");
		sb.push("<option "+(page.size==30?"selected":"")+">30</option>");
		sb.push("<option "+(page.size==50?"selected":"")+">50</option>");
		sizeSelector.html(sb.join(""));
		pageBar.append(sizeSelector);

		var spans = $("#pageBar_"+this.id+" > span");
		for(var i=0;i<spans.length;i++){
			var span =$(spans[i]);
			if(span.html()==""+(page.index+1)){
				span.addClass("current");
			}
		}
	};

	this.bindPage = function(page){
		if(page==null)
			return;
		this.currentPage = page;
		this.bind(page.elements);
		if(this.isCheckedIds){
			this.isChecked(this.isCheckedIds);
		}
		this.refreshPageBox(page);
	};

	/**
	 * 仅限于分页加载环境下，自动刷新当前页
	 */
	this.refresh = function(){
		var size = this.currentPage.size;
		var index = this.currentPage.index;
		this.gotoPage(size,index);
	};

	this.isChecked = function(idArray){
		this.isCheckedIds = idArray;
		for(var i=0;i<this.isCheckedIds.length;i++){
			var row = this.getRowById(this.isCheckedIds[i])
			if(row){
				row.getRowCheckbox()[0].disabled = true;
				row.getRowCheckbox().parent('span.CheckboxWrapper').addClass("csstCheckUnchange");
			}
		}
	};

	this.getCellRender = function(col){
		if(col.render==null){
			var r = ZY.UI.CellRenders[col.type];
			col.render = r;
		}
		return col.render;
	};
	this.getColumnAlign = function(col){
		var align = "center";
		if(col.align!=null)	align = col.align;
		else align = ZY.UI.DefaultAlign[col.type];
		return align;
	};

	this.getColGroupDom = function(name){
		var id = "#colgroup_"+this.id+"_"+name;
		return $(id);
	}

	this.resetLastColumn=function(caculatedTbWidth,containerWidth){

	}
	this.layout = function(){
		var container = this.getElement().children(".box");
		var bodyBox = this.getBodyBox();
		var headTable = this.getHeadTable();
		var headBox = this.getHeadBox();
		var pageBox = this.getPageBox();
		var pageHeight = 0;
		var containerWidth = container.width();

		var tbWidth = this.calcTableWidth();
		var titleBox = container.children(".title");
		var titleHeight = 0;
		if(titleBox.length>0){
			titleHeight = titleBox.height();
		}

		if(pageBox.length>0)
			pageHeight = pageBox.height();

		headBox.height(headTable.height());
		pageBox.width(container.innerWidth()-2);
		bodyBox.height(container.innerHeight()-headBox.height()-pageHeight-titleHeight-2);
		var bodyTable = this.getBodyTable();
		if(bodyTable.length>0){	//如果数据已经绑定
			if(bodyTable.height()>bodyBox.innerHeight()){
				containerWidth = containerWidth-21;
			}
			if(bodyTable.height()==0)
				bodyTable.height(1);
		}
		var width = tbWidth>containerWidth?tbWidth:containerWidth;

		//调整空白列宽度
		this.resetLastColumn(tbWidth,containerWidth);
		headTable.width(width);
		bodyTable.width(width);
	};

	$(window).bind("resize",function(){
		element.height(50);
		element.height();
		ZY.UI.Grids[element.attr("id")].layout();
	});

	this.countHeadWide();
	this.countHeadDeep();
	this.calcHeadCellRowSpan();
	this.resetColumns();
	var headerStr = this.paintHeader();
	this.getHeadBox().html(headerStr);
	this.paintPageBox();
	this.layout();
	this.getBodyBox().bind("scroll",function(){
		 var bodyBox =  $(this);
		 var id = bodyBox.attr("id");
		 id = id.substring(5);
		 var grid = ZY.UI.Grids[id];
		 grid.getHeadTable().css("margin-left",-bodyBox.scrollLeft());
	});


	this.paintRows = function(){
		var rows = this.rows;
		var sb = new Array();
		sb.push('<TABLE  class="bodyTable" id=tBody_'+this.id+' style="TABLE-LAYOUT:fixed;" cellSpacing=0 cellPadding=0 border=0>');
		sb.push(this.getColGroup("body").join(""));
		for(var i=0;i<rows.length;i++){
			var str = rows[i].paint();
			sb.push(str);
		}
		sb.push("</TABLE>");
		this.getBodyTable().remove();
		this.getBodyBox().prepend(sb.join(""));
		this.getBodyTable().bind("click",ZY.UI.asListener(this, this.cellClickHandler));
	}

	this.editorBlurHandler = function(e){
		var editor = e.currentTarget;
		var td = editor.parentElement.parentElement;
		this.blurCell(td);
	}

	this.cellClickHandler = function(e){
		var viewDiv = e.toElement||e.target;
		if(viewDiv.tagName=="INPUT")
			return true;
		var td = viewDiv.parentElement;
		var id = td.id;
		if(id.startsWith("cell")){
			if(this.isCellFocused(td) && this.grid.context.readonly!=true){

				this.viewCellEditor(td);
				var input = $(td).find("input");
				if(input.length>0){
					input[0].focus();
					input.bind("blur",ZY.UI.asListener(this,this.editorBlurHandler));
				}
			}else{
				this.focusCell(td);
			}
		}
	}

	this.isCellFocused = function(td){
		var classes = $(td).attr("class");
		classes = classes.split(" ");
		for(var i=0;i<classes.length;i++){
			if(classes[i]=="focused")
				return true;
		}
	}

	this.focusCell = function(td){
		this.getBodyTable().find("td.focused").removeClass("focused");
		$(td).addClass("focused");
	}

	this.blurCell = function(td){
		$(td).removeClass("focused");
		this.renderCell(td);
	}
	this.renderCell = function(td){
		var id = td.id;
		var addr = this.getCellAddr(id);
		var row = this.getRowByIndex(addr[1]);
		//回写编辑结果
		col = row.getColumnByName(addr[2]);
		if(col.fn==null){
			var value = this.getCellRender(col).editor.getValue(this,row,col);
			ZY.UI.setProperty(row.data, addr[2], value);
		}
		row.refreshCell(addr[2]);
	}
	this.viewCellEditor = function(td){
		var id = td.id;
		var addr = this.getCellAddr(id);
		var row = this.getRowByIndex(addr[1]);
		var col = row.getColumnByName(addr[2]);

		var addr = this.getCellAddr(id);
		if(this.options.checkEditable!=null && this.options.checkEditable(row)==false){ //根据外部提供的函数判断行是否可编辑
			return;
		}

		if(col.editable==true )
			row.renderCellEditor(addr[2]);
		else if(typeof(col.editable)=="string"){
			var data = row.data;
			var editable = eval(col.editable);
			if(editable){
				row.renderCellEditor(addr[2]);
			}
		}
	}

	this.getCellAddr = function(cellId){
		var addrs = cellId.split("@");
		return addrs;
	}

	this.getRowByIndex = function(index){
		for(var i=0;i<this.rows.length;i++){
			var row = this.rows[i];
			if(row.index==index)
				return row;
		}
		return false;
	};

	this.bind=function(data){
		if(data==null)
			data = [] ;
		this.rows = [];
		this.rowSeq=0;
		for(var i=0;i<data.length;i++){
			var row = new ZY.UI.GridRow(data[i],this.getRowSeq(),this);
			this.rows.push(row);
		}
		this.paintRows();
		this.layout();
		this.currentData = data;

		this.refreshHeadSelectStatus();
	};

	/**
	 * 根据属性值，找到某一行
	 * @param pName
	 * @param pValue
	 */
	this.findRow = function(pName,pValue){
		for(var i=0;i<this.rows.length;i++){
			var row = this.rows[i];
			var data = row.data;
			var v = ZY.UI.getProperty(data, pName);
			if(v==pValue)
				return row;
		}
		return null;
	};

	this.findRows = function(pName,pValue){
		var result = [];
		for(var i=0;i<this.rows.length;i++){
			var row = this.rows[i];
			var data = row.data;
			var v = ZY.UI.getProperty(data, pName);
			if(v==pValue)
				result.push( row);
		}
		return result;
	}


	this.appendRows = function(data){
		var tb = this.getBodyTable();
		for(var i=0;i<data.length;i++){
			var row = new ZY.UI.GridRow(data[i],this.getRowSeq(),this);
			this.currentData.push(data[i]);
			this.rows.push(row);
			tb.append(row.paint());
		}
	};
	this.appendRow = function(data){
		var tb = this.getBodyTable();
		var row = new ZY.UI.GridRow(data,this.getRowSeq(),this);
		this.currentData.push(data);
		this.rows.push(row);
		tb.append(row.paint());
	};

	this.appendRowAfter = function(row,data){
		var tr = row.getElement();
		var index = this.rows.indexOf(row);
		var newRow = new ZY.UI.GridRow(data,this.getRowSeq(),this);
		this.rows.insertAt(newRow, index+1);
		$(newRow.paint()).insertAfter(tr);
	}

	this.removeRow = function(row){
		row.getElement().remove(); //先删除dom,再删除json
		this.rows.remove(row);
		this.refreshHeadSelectStatus();
	}


	this.getSelectedRows = function(){
		var selected = [];
		for(var i=0;i<this.rows.length;i++){
			var row = this.rows[i];
			if(row.selected)
				selected.push(row);
		}
		return selected;
	}

	this.getRowById = function(id){
		for(var i=0;i<this.rows.length;i++){
			if(this.rows[i].data.id == id){
				return this.rows[i];
			}
		}
	}
	this.getSelectedRowsData = function(){
		var selected = this.getSelectedRows();
		var rows = [];
		for(var i=0;i<selected.length;i++){
			var data = selected[i].data;
			rows.push(data);
		}
		return rows;
	}
	this.getAllRowData = function(){
		var data = [];
		for(var i=0;i<this.rows.length;i++){
			data.push(this.rows[i].data);
		}
		return data;
	}

	this.update = function(data){
		for(var i=0;i<this.rows.length;i++){
			if(i < data.length){
				this.rows[i].update(data[i]);
			}
		}
	}
};

ZY.UI.GridRow = function(data,index,grid){
	this.data = data;
	this.index = index;
	this.selected = false;
	this.grid = grid;
	this.getCellValue  = function(col){
		var s = col.name;
		if(s==null)
			return null;

		var v = null;
		if(s.startsWith("fn:")){
			s = s.substring(3);
			var data = this.data;
			v = eval(s);
		}else{
			v =  ZY.UI.getProperty(this.data, s);
		}
		return v;
	};


	this.findSelfPosition = function(){
		return this.grid.rows.indexOf(this);
	}
	this.getElement = function(){
		return $(this.grid.getBodyTable().find("tr").eq(this.findSelfPosition()));
	};
	this.getCellElement = function(colName){
		var cellId = "cell@"+this.index+"@"+colName;
		var element = document.getElementById(cellId);
		return $(element);
	}
	this.getRowCheckbox = function(){
		return $(this.getElement().find("input[type=checkbox]")[0]);
	};

	/**
	 * @param selected
	 * @param withoutEvent 等于true表示不触发onSelected事件
	 */
	this.setSelected = function(selected,withoutEvent){
		if(selected==null)
			selected = !this.selected;
		this.selected = selected;

		if(this.selected==true){
			if(this.getRowCheckbox().length>0)
				this.getRowCheckbox()[0].checked = true;
			//if(this.grid.isEditable()==false)
				this.getElement().attr("class","selected");
		}else{
			if(this.getRowCheckbox().length>0)
				this.getRowCheckbox()[0].checked = false;
			//if(this.grid.isEditable()==false)
				this.getElement().attr("class",this.index%2==1?"odd":"even");
		}
		try{
			if(withoutEvent==true){
			}else if(this.onSelected!=null)
				this.onSelected();
		}catch(e){
		}
	};


	this.paint = function(){
		var sb = [];
		var className = '';
		if(this.selected)
			className = "selected";
		else
			className = this.index%2==1?"odd":"even";

		sb.push("<tr");
		sb.push(" onclick='ZY.UI.GridEvents.onClicked(\""+this.grid.id+"\","+this.index+")'");
		sb.push(" class='"+className+"'");
		sb.push(">");
		var columns =this.grid.leafCols;
		if(grid.context.selectedMode!="S"){
			sb.push("<td><div class='cellDiv'>")
			sb.push("<input type='checkbox' class='checkbox' onclick='ZY.UI.GridEvents.onRowChecked(\""+this.grid.id+"\","+this.index+")'/>");
			sb.push("</div></td>");
		}for(var i=0;i<columns.length;i++){
			sb.push(this.paintCell(columns[i]));
		}
		sb.push("</tr>");
		var r = sb.join("");
		return r;
	};

	this.getColumnByName = function(colName){
		var columns = this.grid.leafCols;
		for(var i=0;i<columns.length;i++){
			if(colName==columns[i].name)
				return columns[i];
		}
		return null;
	}

	this.getTitle = function(col){
		if(col.type=="String" || col.type=="Link"){
			return this.getCellValue(col);
		}
		return null;
	}

	this.paintCell = function(col){
		var str = null;
		if(col.caption=="&nbsp;")
			str = "&nbsp;";
		else{
			var title = this.getTitle(col);
			if(title==null)
				str = '<div class="cellDiv">'+this.grid.getCellRender(col).render(this.grid,this,col)+'</div>';
			else
				str = '<div class="cellDiv" title="'+this.getCellValue(col)+'">'+this.grid.getCellRender(col).render(this.grid,this,col)+'</div>';
		}
		var sb = [];
		var cellId = "cell@"+this.index+"@"+col.name;
		var align = this.grid.getColumnAlign(col);
		var className = "";

		sb.push('<td id="'+cellId+'" style="text-align:'+align+';"');
		sb.push(' class="'+className+'"');
		sb.push(' >');
		sb.push(str);
		sb.push('</td>');
		var r = sb.join("");
		return r;
	};
	this.renderCellEditor = function(colName){
		var td = this.getCellElement(colName);
		var col = this.getColumnByName(colName);
		var str = '<div class="cellEditorDiv" style="padding-left:1px;width:'+(td.width()-2)+'px;height:'+(td.height()-2)+'px;">'+this.grid.getCellRender(col).editor.render(this.grid,this,col)+'</div>';
		td.html(str);
	}

	/**
	 * 更新一行数据，并刷新显示该行.
	 * @param data
	 */
	this.update = function(data){
		this.data=data;
		var cols=this.grid.leafCols;
		for(var i=0;i<cols.length;i++){
			this.refreshCell(cols[i].name);
		}
	}

	this.refreshCell = function(colName){
		var col = this.getColumnByName(colName);
		//回到显示界面
		var td = this.getCellElement(colName);
		var str ='<div class="cellDiv">'+this.grid.getCellRender(col).render(this.grid,this,col)+'</div>';
		td.html(str);
	}
};

ZY.UI.dlgs = [];

window.closeDlg = function(returnValue){
	window.top._returnValue = returnValue;
	setTimeout("ZY.UI.closeDlgSure()",10);
}

ZY.UI.closeDlgSure = function(){
	dlgs = window.top.ZY.UI.dlgs;
	if(dlgs!=null && dlgs.length>0)
		+dlgs[dlgs.length-1].close(window.top._returnValue);
}



ZY.UI.openDlgBox=function(options){
	var topWin = window.top;
	if(topWin!=window){
		return topWin.ZY.UI.openDlgBox(options);
	}
	var url = options.url;
	var title = options.title;
	var dlgWidth = options.width;
	var dlgHeight = options.height;
	var type = options.type;
	var colorStyle = "";
	var msg = options.msg;

	var boxIndex = 1001;
	var cover = $("#zy_dlgCover");
	if(cover[0]==null){
		cover = null;
	}
	if(cover==null){
		cover = $("<div id='zy_dlgCover'></div>").css("z-index",1000);
		$(topWin.document.body).append(cover);
	}else{
		var zIndex = 1*cover.css("z-index")+2;
		cover.css("z-index",zIndex);
		boxIndex = zIndex+1;
	}
	var topBody = $(topWin.document.body);
	cover.height(topBody.height());
	cover.width(topBody.width());
	var dlgHandler = {};
	dlgHandler.id = boxIndex;
	dlgHandler.getContentWindow = function(){
		var iframe = $("#dlgFrame_"+this.id);
		if(iframe.length>0)
			return iframe[0].contentWindow;
		return null;
	};



	dlgHandler.close = function(returnValue){
		var closeAble = true;
		if(typeof options.closeAble == 'boolean' && options.closeAble==false)
			closeAble = false;
		if(eval(options.closeAble)==false)
			closeAble = false;

		if(closeAble==false)
			return ;


		var dlgs = window.top.ZY.UI.dlgs;
		var current = dlgs[dlgs.length-1];
		try{
			current.getContentWindow().onDlgClosed(returnValue);
		}catch(e){
		}
		dlgs.removeAt(dlgs.length-1);
		$("#zy_dlgBox_" + this.id).remove();
		if(this.id > 1001 ){
			$('#zy_dlgCover').css("z-index", $('#zy_dlgCover').css("z-index")-2);
		}else{
			$('#zy_dlgCover').remove();
		}
		this.dragger.destroy();
		zy_clean(this.dragger);
		if(options!=null && options.callback !=null)
			options.callback(returnValue);
	};
	dlgHandler.okListener = function(){
		this.close(true);
	};
	dlgHandler.closeListener = function(){
		if(type=="dialog")
			this.close(null);
		else
			this.close(false);
	};
	dlgHandler.cancelListener = function(){
		this.close(false);
	};

	dlgHandler.changeMsgContent = function(msg){
		$("#zy_dlgBox_" + this.id).find(".textBox").html(msg);
	}
	window.top.ZY.UI.dlgs.push(dlgHandler);


	var height = $(topWin.document).height();
	var width = $(topWin.document).width();
	var top = (height-dlgHeight)/2;
	var left = (width-dlgWidth)/2;

	var box = $("<div class='zy_dlg_box' id='zy_dlgBox_" + boxIndex + "'></div>")
		.height(dlgHeight)
		.width(dlgWidth)
		.css("left",left)
		.css("top",top)
		.css("z-index",boxIndex);
	$(topWin.document.body).append(box);

	var titleBox = null;
	titleBox = $("<div class='zy_dlg_title' id='zy_dlgTitle_" + boxIndex + "'></div>");
	var titleSpan = $("<span>"+title+"</span>");

	box.append(titleBox);

	var closeBtn = $("<div class='zy_dlg_closeBtn'>r</div>");
	titleBox.append(closeBtn);
	titleBox.append(titleSpan);

	var innerBox = $("<div class='zy_dlg_innerBox' ></div>");
	box.append(innerBox);

	var contentBox = null;
	this.createIframeContent = function(){
		contentBox = $("<iframe id='dlgFrame_"+boxIndex +"' ></iframe>");
		contentBox[0].src=url;
		innerBox.append(contentBox);
	}
	this.createMsgContent = function(){
		colorStyle= type;
		contentBox = $("<div class='contentBox'></div>");
		innerBox.append(contentBox);
		var sectionTop = $("<div class='sectionTop'><div class='imgBox "+colorStyle+"'></div><div class='textBox'>"+msg+"</div></div>");
		var sectionBottom = $("<div class='sectionBottom'></div>");
		contentBox.append(sectionTop);
		contentBox.append(sectionBottom);
		var btnOk = null;
		var btnCancel =null;
		if(type=="confirm"){
			btnCancel = $("<input type='button' value='取&nbsp;&nbsp;消'/>");
			btnOk = $("<input type='button' value='确&nbsp;&nbsp;认'/>");
			sectionBottom.append(btnCancel);
			sectionBottom.append(btnOk);
		}else{
			btnOk = $("<input type='button' value='确&nbsp;&nbsp;认'/>");
			sectionBottom.append(btnOk);
		}
		btnOk.bind("click",ZY.UI.asListener(dlgHandler, dlgHandler.okListener));
		if(btnCancel!=null)
			btnCancel.bind("click",ZY.UI.asListener(dlgHandler, dlgHandler.cancelListener));

		if(options.closeAble==false){

		}
	}


	if(type=="dialog"){
		this.createIframeContent();
	}else{
		this.createMsgContent();
	}

	box.focus();
	var inputs = box.find("input");
	if(inputs.length>0)
		inputs[0].focus();

	var dragger = new ZY.UI.draggable(box[0], {Handle:titleBox[0],Limit:true});
	dlgHandler.dragger = dragger;
	closeBtn.bind("click",ZY.UI.asListener(dlgHandler, dlgHandler.closeListener));
	return dlgHandler;
}

window.openDlg = function(options){
	options.type="dialog";
	options.callback = options.callback||function(returnValue){};
	options.width=options.width||600;
	options.height = options.height||450;
	return ZY.UI.openDlgBox(options);
};

ZY.UI.messageTitles = {error:"错误",warn:"警告",info:"消息",confirm:"确认",success:"操作成功"};
window.showMsg = function(options){
	options.type = options.type||"info";
	options.width = options.width||300;
	options.height = options.height||180;
	options.title = options.title||"消息";
	return ZY.UI.openDlgBox(options);
}



/**
 * 纯文本输入
 * @param jqInput
 */
ZY.UI.TextInput = function(jqInput){
	this.setReadonly = function(b){
		jqInput[0].readOnly = b;
		if(b==true)
			jqInput.parent().parent().attr("class", "inputWrap2");
		else
			jqInput.parent().parent().attr("class", "inputWrap");
	};
	this.getName = function(){
		return jqInput.attr("name");
	};
	this.getValue = function(){
		return jqInput.attr("value");
	};
	this.setValue = function(obj){
		var v = ZY.UI.getProperty(obj,jqInput.attr("name"));
		if(v==null)
			v = "";
		jqInput.attr("value",v);
	};
};



/*

ZY.UI.CheckInput = function(jqInput,form){
	this.name = jqInput.attr("name");
	this.form = form;
	this.getName = function(){
		return this.name;
	}
	this.getElements = function(){
		return this.form.getJQForm().find("input[type=checkbox][name*="+this.name+"]");
	}
	this.getValue = function(){
		var v = [];
		var elements = this.getElements();
		for(var i=0;i<elements.length;i++){
			if(elements[i].checked == true){
				v.push(elements[i].value);
			}
		}
		return v;
	}
	this.setValue = function(obj){
		var v = ZY.UI.getProperty(obj,this.name);
		var elements = this.getElements();
		if(v instanceof Array){
			for(var i=0;i<elements.length;i++){
				if(v.contains(elements[i].value)){
					elements[i].checked=true;
					$(elements[i]).parent('span.CheckboxWrapper').addClass('csstChecked');
				}else{
					elements[i].checked=false;
					$(elements[i]).parent('span.CheckboxWrapper').removeClass('csstChecked');
				}
			}
		}else{
			for(var i=0;i<elements.length;i++){
				elements[i].checked=false;
				$(elements[i]).parent('span.CheckboxWrapper').removeClass('csstChecked');
			}
		}
	}
	this.getElements().each(function(){
		$(this).addClass('csstCheckbox').wrap('<span class="CheckboxWrapper"></span>');
		$(this).bind("click",function(){
			var v = {};
			v[this.name] = form.editors[this.name].getValue();
			form.editors[this.name].setValue(v);
		});
	});
}*/


ZY.UI.indexOfSelectInput = 0;
ZY.UI.Multiple_selInput = function(jqInput,form){
	this.name = jqInput.attr("name");
	this.form = form;
	this.getName = function(){
		return this.name;
	}
	this.getValue = function(){
		var value = [];
		$('option',jqInput).each(function(index){
			var $this = $(this);
			if(this.selected == true){
				value.push(this.value);
			}
		});
		return value;
	}
	this.getSelectedOptions = function(){
		var options = [];
		$('option',jqInput).each(function(index){
			if(this.selected == true){
				var option = {};
				option.name = this.text;
				option.value = this.value;
				options.push(option);
			}
		});
		return options;
	}

	this.getOptions = function(){
		var options = [];
		$('option',jqInput).each(function(index){
				var option = {};
				option.name = this.text;
				option.value = this.value;
				options.push(option);
		});
		return options;
	}

	this.removeOptions = function(options){
		var values = [];
		for(var i=0;i<options.length;i++){
			values.push(options[i].value);
		}
		$('option',jqInput).each(function(index){
			if(values.contains(this.value)){
				this.parentNode.removeChild(this);
			}
		});
		this.repaintLi();
	}

	this.addOptions = function(options){
		for(var i=0;i<options.length;i++){
			jqInput[0].options.add(new Option(options[i].name,options[i].value));
		}
		this.repaintLi();
	}

	this.repaintLi = function(){
		var $ul = jqInput.parent().parent().parent().find("ul.wrapperList");
		$ul.empty();
		$('option',jqInput[0]).each(function(i){
			$ul.append('<li><a href="javascript:void(0);" index="'+ i +'">'+ this.text +'</a></li>');
		});

		$ul.find('a').click(function(){
			form.editors[jqInput.attr("name")].setOptionSelected(1*$(this).attr("index"));
		});

	}
	this.repaint = function(){
		var $ul = jqInput.parent().parent().parent().find("ul.wrapperList");
		var $select = $ul.parent().find("select.csstSelect");
		var options = $select[0].options;
		$ul.find('a.selected').removeClass('selected');
		for(var i=0;i<options.length;i++){
			if(options[i].selected == true){
				$($ul.find('a')[i]).addClass('selected');
			}
		}
	}

	this.setOptionSelected = function(index){
		var option = jqInput[0].options[index];
		option.selected = !option.selected;
		this.repaint();
	}

	this.isOptionSelected = function(index){
		return jqInput[0].options[index].selected;
	}


	this.setValue = function(obj){
		var v = ZY.UI.getProperty(obj,jqInput.attr("name"));
		if(v == null||v==undefined){return}
		$('option',jqInput[0]).each(function(index){
			var $this = $(this);
			if(v.contains($this.attr("value"))){
				this.selected = true;
			}
		})
		this.repaint();
	};

	jqInput.each(function(){
		var size = jqInput.attr("size")*1;
		var dd=[];
		dd.push('<span class="selectMulWrapper">');
		dd.push('<div class="wrapperTopLeft"><div class="wrapperTopRight"></div></div>');
		dd.push('<ul class="wrapperList"></ul>');
		dd.push('<div class="wrapperBtmLeft"><div class="wrapperBtmRight"></div></div>');
		dd.push('</span>');
		var selectedHtml = dd.join('');
		$(this).addClass('csstSelect').wrap(selectedHtml);
		$(this).parent().parent().parent().width(149);
		var $ul  =$(this).parent().parent().parent().children('ul.wrapperList');
		$ul.width(147);
		$ul.height(size*28);
		$('option',this).each(function(i){
			$ul.append('<li><a href="javascript:void(0);" index="'+ i +'">'+ this.text +'</a></li>');
		});

		$ul.find('a').click(function(){
			form.editors[jqInput.attr("name")].setOptionSelected(1*$(this).attr("index"));
		});
	});
}

ZY.UI.Single_selInput = function(jqInput,form){
	this.name = jqInput.attr("name");
	this.form = form;

	this.setReadonly = function(b){
		this.readonly = b;
	}

	this.getName = function(){
		return this.name;
	}
	this.getValue = function(){
		var value = null;
		$('option',jqInput).each(function(index){
			var $this = $(this);
			if(this.selected == true){
				value = this.value;
			}
		});
		return value;

	}

	this.repaint = function(){
		var $ul = jqInput.parent().find("ul.selectGroup");
		$ul.find('a.selected').removeClass('selected');
		var option = jqInput[0].options[jqInput[0].selectedIndex];
		$($ul.find('a')[jqInput[0].selectedIndex]).addClass('selected');
		$ul.parent().children("input.inputTextWrap").attr("value",option.text);
	}

	this.setSelectedIndex = function(nIndex,toTrigger){
		$('option',jqInput[0]).each(function(index){
			if(index==nIndex)
				this.selected = true;
			else
				this.selected = false;
		});
		this.repaint();
		if(toTrigger==false){

		}else
			jqInput.trigger("onchange");
	}

	this.setValue = function(obj){
		var setted = false;
		$('option',jqInput[0]).each(function(index){
			var $this = $(this);
			var v = ZY.UI.getProperty(obj,jqInput.attr("name"));
			if($this.attr("value") == v||(v==undefined && $this.attr("value").trim()=="")){
				this.selected = true;
				setted = true;
			}else{
				this.selected = false;
			}
		})
		if(!setted){
			($('option',jqInput[0]).eq(0))[0].selected = true;
		}
		this.repaint();
	};

	this.expend = function(){
		if(this.readonly==true){
			return ;
		}
		var $ul = jqInput.parent().find('ul.selectGroup');
		var h = 22*$ul[0].childNodes.length+2;
		var maxHeight = jqInput.attr("maxHeight");
		if(maxHeight==null || maxHeight==""){
			maxHeight = 136;
		}

		if(h>maxHeight){
			h = maxHeight;
		}
		$ul.height(h);
		$ul.css('display','block');
	}

	jqInput.each(function(){
		ZY.UI.indexOfSelectInput++;
		var width=$(this).attr('width');
		$(this).addClass('csstSelect').wrap('<span class="inputWrap"><span class="inputWrapInner"></span></span>');
		$('<input class="inputTextWrap" /><span class="inputTypeIcon icon_selectArrow"></span><ul class="selectGroup"></ul>').insertAfter($(this));
		$(this).parent().parent('.inputWrap').css('z-index',100-ZY.UI.indexOfSelectInput);//修复IE6,IE7下面前面一个下拉层会被后面一个select框遮盖；
		var $ul  =$(this).parent().children('ul.selectGroup');
		var $input = $(this).parent().children('input.inputTextWrap');
		var $arrowMenu = $(this).parent().children('span.icon_selectArrow');
		$input.attr('readonly','readonly');
		$('option',this).each(function(i){
			if($(this).attr("selected")=="selected")
			{
				$ul.append('<li><a class="selected" href="###" index="'+ i +'">'+ this.text +'</a></li>');
				$ul.parent().children('.inputTextWrap').val(this.text);
			}
			else
				$ul.append('<li><a href="###" index="'+ i +'">'+ this.text +'</a></li>');
		});
		if(typeof($(this).attr("width"))=="undefined") {
			$(this).parent().children('input').width(125);
		}
		else{
			$input.width(width-$arrowMenu.width());
		}
		if ($.browser.msie)
			var oWidth = $(this).parent().children('input').width();
		else
			var oWidth = $(this).parent().children('input').width()-4;
		$(this).parent().children('input').width(oWidth);
		$ul.width(oWidth+26);
		$(this).parent().children('span.inputTypeIcon').hover(
			function(){
				$(this).addClass('icon_selectArrowCur');
				$(this).parent().parent('span.inputWrap').addClass('inputWrap_hover');
			},function(){
				$(this).removeClass('icon_selectArrowCur');
				$(this).parent().parent('span.inputWrap').removeClass('inputWrap_hover');
			});
		$input.hover(
			function(){
				$(this).parent().children('span.inputTypeIcon').addClass('icon_selectArrowCur');
				$(this).parent().parent('span.inputWrap').addClass('inputWrap_hover');
			},function(){
				$(this).parent().children('span.inputTypeIcon').removeClass('icon_selectArrowCur');
				$(this).parent().parent('span.inputWrap').removeClass('inputWrap_hover');
		});
		$(this).parent().children('span.inputTypeIcon').click(function(){form.editors[jqInput.attr("name")].expend();});
		$input.bind("click",function(){form.editors[jqInput.attr("name")].expend();});

		$ul.find('a').click(function(){
			form.editors[jqInput.attr("name")].setSelectedIndex(1*$(this).attr("index"));
			$ul.css("display","none");
		});

		var checkExternalClick = function(event) {
			if(event.target==$ul[0])
				return ;
			if ($(event.target).parents('ul.selectGroup').length == 0) { $ul.hide(); }
		};

		$(document).mousedown(checkExternalClick);

	});
}

/**
 * 上传表单
 * ${_url}:请求地址
 * ${_formLIstBlock}:form列表区域
 * ${_uploadedListBlock}:上传后的文件状态区域
 * 其中form中只包含两个input字段:
 * formTag        type:'hidden';name:'formTag';value:int[0-]
 * uploadedFile   type:'file';name:'file';id:'upload_file_${formTag}'
 * 其他所需字段请以get方式拼装
 */
ZY.UI.UploadForm = function(_url,_formListBlock,_uploadedListBlock){
	var url = _url;
	var formListBlock = _formListBlock;
	var uploadedListBlock = _uploadedListBlock;
	var formTag = 0;

	doCreateForm();

	function doCreateForm() {
		var newIframe = $('<iframe style="height:0px;width:0px;" class="uploadFileIframe" name="iframe_'+formTag+'" id="iframe_'+formTag+'" '+'src="about:blank" frameborder="0" allowtransparency="ture"></iframe>');
		formListBlock.append(newIframe);

		var newForm = $('<form><input type="hidden" name="formTag" value="'+formTag+'"/></form>');
		newForm.attr("target", "iframe_" + formTag);
		newForm.attr("id", "form_" + formTag);
		newForm.attr("method", "post");
		newForm.attr("enctype", "multipart/form-data");
		newForm.attr('action', url);

		var newFileInput = $('<input/>');
		newFileInput.attr("id","upload_file_"+formTag);
		newFileInput.attr("name","uploadedFile");
		newFileInput.attr("type","file");
		newFileInput.change(function(){
			var filePath = this.value;
			var pathNameArray = filePath.split("\\");
			var fileName = pathNameArray[pathNameArray.length - 1];
			var fileDiv = $('<div class="fileDiv"><div class="filePath" id="filePath_'+formTag+'"></div><div class="fileUploadSta loading"></div></div>');
			uploadedListBlock.append(fileDiv);
			uploadedListBlock.find('#filePath_' + formTag).text(fileName);
			if (formTag % 2 != 0) {
				$("#filePath_" + formTag).parent('.fileDiv').addClass('fileDivBg');
			}
			$(this).parent().parent().submit();
			$(this).parent().parent().hide();
			doCreateForm();
		});
		newForm.append(newFileInput);
		formListBlock.append(newForm);
		new ZY.UI.Form("form_" + formTag);

		formTag++;
	}
}

ZY.UI.UploadForm.afterUpload = function(_formTag,result,uploadedListBlock){
	var formTag = _formTag + 1;
	var fileSta =uploadedListBlock.find('#filePath_'+formTag).parent().children()[1];
	fileSta = $(fileSta);
	if(result){
		fileSta.removeClass("loading");
		fileSta.addClass("loadComplete");
	}
	else{
		fileSta.removeClass("loading");
		fileSta.addClass("loadFailed");
	}
}
var Class = {
    create: function() {
        return function() { this.initialize.apply(this, arguments); }
    }
};

ZY.UI.Editor = Class.create();
ZY.UI.Editor.prototype = {
	decorate:function(){
	},
	getFormDOM:function(){
		return $( this.dom).parent( "form" )[0];
	},
	initialize: function(dom) {
		if(dom==null)
			return;
		this.dom = dom;
		this.name = $(this.dom).attr("name");
		this.tagName = dom.tagName;
		this.decorate();
	},

	setValue:function(value){
		var v =ZY.UI.getProperty(value,this.name);
		$(this.dom).val(v);
	},
	getValue:function(){
		return $(this.dom).val();
	},
	setReadonly:function(readonly){
		if(readonly==null)
			readonly = true;
		$(this.dom).attr("disabled",readonly);
	},
	focus:function(){
		this.dom.focus();
	}
};

ZY.UI.TextAreaInput = Class.create();
ZY.UI.TextAreaInput.prototype = new ZY.UI.Editor();
ZY.UI.extend(ZY.UI.TextAreaInput.prototype,{

});

ZY.UI.SelectInput = Class.create();
ZY.UI.SelectInput.prototype = new ZY.UI.Editor();
ZY.UI.extend(ZY.UI.SelectInput.prototype, {
	setValue:function(value){
		var v = ZY.UI.getProperty(value, this.name);
		var options = $(this.dom).children("option");
		for(var i=0;i<options.length;i++){
			var option = options[i];
			var $o = $(option);
			if($o.attr("value")==v){
				option.selected = true;
				break;
			}
		}
		//没有匹配的选项
		if(i==options.length && i>0){
			options[0].selected = true;
		}
	},
	getValue:function(){
		var value = null;
		var options = $(this.dom).children("option");
		for(var i=0;i<options.length;i++){
			var option = options[i];
			var $o = $(option);
			if(option.selected == true){
				value = option.value;
				break;
			}
		}
		return value;
	}
});
ZY.UI.RefInput = Class.create();
ZY.UI.RefInput.prototype = new ZY.UI.Editor();
ZY.UI.extend(ZY.UI.RefInput.prototype, {
	decorate:function(){
		$(this.dom).bind("click",function(){
			this.blur();
			var fn = this.dataset.fn;
			eval(fn);
		});
		this.dom.readOnly = true;
	},
	setValue:function(obj){
		this.value = ZY.UI.getProperty(obj,this.name);
		if(this.value==null)
			$(this.dom).val("");
		else{
			if(Object.prototype.toString.call(this.value) === '[object Array]'){
				var v = "";
				for(var i=0;i<this.value.length;i++){
					if(i==0)
						v = v+ (this.value[i].name||this.value[i].fullName||this.value.code);
					else
						v = v + ", "+(this.value[i].name||this.value[i].fullName||this.value.code);
				}
				$(this.dom).val(v);
			}else{
				$(this.dom).val(this.value.name||this.value.fullName||this.value.code);
			}
		}
	},
	getValue:function(){
		return this.value;
	}
});

ZY.UI.CheckInput = Class.create();
ZY.UI.CheckInput.prototype = new ZY.UI.Editor();
ZY.UI.extend(ZY.UI.CheckInput.prototype,{
	getElements:function(){
		return $(this.getFormDOM).find("input[type=checkbox][name="+this.name+"]");
	},
	setReadonly:function(readonly){
		if(readonly==null)
			readonly = true;
		this.getElements().attr("disabled",readonly);
	},
	getValue:function(){
		var value = [];
		var elements = this.getElements();
		for(var i=0;i<elements.length;i++){
			if(elements[i].checked == true){
				value.push(elements[i].value);
			}
		}
		return value;
	},
	setValue:function(value){
		var v = ZY.UI.getProperty(value, this.name);
		if(v==null)
			v = [];
		var elements = this.getElements();
		for(var i=0;i<elements.length;i++){
			if(v.contains(elements[i].value)){
				elements[i].checked = true;
			}
		}
	}
});

ZY.UI.RadioInput = Class.create();
ZY.UI.RadioInput.prototype = new ZY.UI.Editor();
ZY.UI.extend(ZY.UI.RadioInput.prototype, {
	getElements:function(){
		return $(this.getFormDOM).find("input[type=radio][name="+this.name+"]");
	},
	decorate:function(){
		var elements = this.getElements();
		for(var i=0;i<elements.length;i++){
			var item = elements[i];
			span = $(item).next("span").addClass("afterRadio");

		}
	},
	setReadonly:function(readonly){
		if(readonly==null)
			readonly = true;
		this.getElements().attr("disabled",readonly);
	},
	getValue:function(){
		var value = null;
		var elements = this.getElements();
		for(var i=0;i<elements.length;i++){
			if(elements[i].checked == true){
				value = elements[i].value;
				break;
			}
		}
		return value;
	},
	setValue:function(value){
		var v = ZY.UI.getProperty(value, this.name);
		if(v!=null && v.code!=null)
			v = v.code;
		var elements = this.getElements();
		for(var i=0;i<elements.length;i++){
			if(elements[i].value == ""+v){
				elements[i].checked = true;
			}
		}
	}
});


ZY.UI.Form = function(formId){
	this.formId = formId;
	this.value = null;
	var thisForm = this;
	var $form = $('#'+formId);
	this.originValue = {};
	this.editors= [];

	this.decorateNecessary = function(){
		$form.find("td.necessary").each(function(index){
			var html = $(this).html();
			if(html.startsWith("<span>*</span>")==false)
				$(this).html("<span>*</span>"+$(this).html());
		});
	}
	this.decorateNecessary();

	this.getInputs = function(){
		var inputs = [];
		$form.find("input,select,textarea").each(function(index){
			inputs.push(this);
		});
		return inputs;
	}
	this.getEditor = function(name){
		for(var i=0;i<this.editors.length;i++){
			if(this.editors[i].name == name)
				return this.editors[i];
		}
	}

	this.validate = function(config){
		this.validator = $form.validate(config);
		return this.validator;
	};
	var inputs = this.getInputs();
	for(var i=0;i<inputs.length;i++){
		if(inputs[i].name=="" || inputs[i].name==undefined)
			continue;
		var editor = null;
		if(inputs[i].tagName=="SELECT"){
			editor = new ZY.UI.SelectInput(inputs[i]);
		}else if(inputs[i].type=="radio" ||inputs[i].type=="RADIO"){
			editor = this.getEditor(inputs[i].name);
			if(editor==null){
				editor = new ZY.UI.RadioInput(inputs[i]);
			}else
				continue;
		}else if(inputs[i].type=="CHECKBOX" || inputs[i].type=="checkbox"){
			editor = this.getEditor(inputs[i].name);
			if(editor==null){
				editor = new ZY.UI.CheckInput(inputs[i]);
			}else
				continue;
		}else if(inputs[i].dataset.type=="refEditor"){
			editor = new ZY.UI.RefInput(inputs[i]);
		}else if(inputs[i].tagName=="TEXTAREA"){
			editor = new ZY.UI.TextAreaInput(inputs[i]);
		}else
			editor = new ZY.UI.Editor(inputs[i]);
		/**
		$(inputs[i]).bind("change",function(){
			editor.changed = true;
		})**/

		this.editors.push(editor);
	}


	$form.bind("reset",function(event){
		thisForm.reset();
		return false;
	});

	this.reset = function(){
		this.setValue(this.originValue);
	};

	this.setReadonly = function(readonly){
		for(var i=0;i<this.editors.length;i++){
			this.editors[i].setReadonly(readonly);
		}
	}

	this.setValue = function(obj){
		this.originValue = eval("obj="+JSON.stringify(obj));
		for(var i=0;i<this.editors.length;i++){
			var editor = this.editors[i];
			editor.setValue(obj);
		}
		this.value = obj;
	}

	this.update = function(name,value){
		ZY.UI.setProperty(this.value,name,value);
		var editor = this.getEditor(name);
		editor.setValue(this.value);
	}

	this.getValue = function(onlyChanged){
		if(onlyChanged==true){
			var v = {};
			for(var i=0;i<this.editors.length;i++){
				var editor = this.editors[i];
				var oldValue = ZY.UI.getProperty(this.value, editor.name);
				if(oldValue!=editor.getValue()){
					ZY.UI.setProperty(v,editor.name,editor.getValue());
				}
			}
			return v;
		}else{
			for(var i=0;i<this.editors.length;i++){
				var editor = this.editors[i];
				ZY.UI.setProperty(this.value,editor.name,editor.getValue());
			}
			delete this.value.treeNodeType;
			delete this.value["new"];
			return this.value;
		}
	}

	if(this.editors.length>0){
		this.editors[0].focus();
	}
};



//拖动效果

var CurrentStyle = function(element){
    return element.currentStyle || document.defaultView.getComputedStyle(element, null);
};
ZY.UI.draggable = function(drag,options){
    this.options = {
        Handle:         "",//设置触发对象（不设置则使用拖放对象）
        Limit:          false,//是否设置范围限制(为true时下面参数有用,可以是负数)
        mxLeft:         0,//左边限制
        mxRight:        $(window.top.document).width(),//右边限制
        mxTop:          0,//上边限制
        mxBottom:       $(window.top.document).height(),//下边限制
        mxContainer:    "",//指定限制在容器内
        LockX:          false,//是否锁定水平方向拖放
        LockY:          false,//是否锁定垂直方向拖放
        Lock:           false,//是否锁定
        Transparent:    false,//是否透明
        onStart:        function(){},//开始移动时执行
        onMove:         function(){},//移动时执行
        onStop:         function(){}//结束移动时执行
    };

    //修正范围
	this.repair=function() {
	    if(this.options.Limit){
	        //修正错误范围参数
	    	this.options.mxRight=$(window.top.document).width();
	    	this.options.mxBottom=$(window.top.document).height();
	        this.options.mxRight = Math.max(this.options.mxRight, this.options.mxLeft + this.drag.offsetWidth);
	        this.options.mxBottom = Math.max(this.options.mxBottom, this.options.mxTop + this.drag.offsetHeight);
	        //如果有容器必须设置position为relative来相对定位，并在获取offset之前设置
	        !this._mxContainer || (this._mxContainer.style.position = "relative");
	    }
	},
	ZY.UI.extend(this.options,options || {});
	this.drag = $(drag)[0];
    this._offsetX = this._offsetY = 0;
    this._marginLeft = this._marginTop = 0;
    this._Handle = $(this.options.Handle).length>0?$(this.options.Handle)[0]:this.drag;
    this._mxContainer = $(this.options.mxContainer)[0] || null;

    //修正范围
    this.repair();


    //准备拖动
	this.start = function(event) {
		this.repair();
	    if(this.options.Lock){ return; }

	    this._offsetX = event.clientX - this.drag.offsetLeft; //鼠标x，被拖动对象x
	    this._offsetY = event.clientY - this.drag.offsetTop;

	    //记录margin
	    this._marginLeft = parseInt(CurrentStyle(this.drag).marginLeft) || 0;
	    this._marginTop = parseInt(CurrentStyle(this.drag).marginTop) || 0;

	    this.moveListener = ZY.UI.asListener(this,this.move);
	    this.stopListener = ZY.UI.asListener(this,this.stop);
	    $(window.document).bind("mousemove",this.moveListener);
	    $(window.document).bind("mouseup",this.stopListener);
	    //遍历子窗口，并监听子窗口的mouseup
	    var iframes = $("iframe");
	    for(var i=0;i<iframes.length;i++){
	    	var iframe = iframes[i];
	    	var w = iframe.contentWindow;
	    	$(w).bind("mouseup",this.stopListener);
	    }



	    //附加程序
	    this.options.onStart();
	};

	this.startListener = ZY.UI.asListener(this,this.start);
	$(this._Handle).bind("mousedown",this.startListener);
	this.destroy = function(){
		$(this._Handle).unbind("mousedown");
	}
	//拖动
	this.move=function(event) {
	    if(this.Lock){ this.stop(); return; };
	    window.getSelection ? window.getSelection().removeAllRanges() : document.selection.empty();
	    //设置移动参数
	    var iLeft = event.clientX - this._offsetX, iTop = event.clientY - this._offsetY;
	    //设置范围限制
	    if(this.options.Limit){
	        //设置范围参数
	        var mxLeft = this.options.mxLeft, mxRight = this.options.mxRight,
	        	mxTop = this.options.mxTop, mxBottom = this.options.mxBottom;
	        //如果设置了容器，再修正范围参数
	        if(!!this._mxContainer){
	            mxLeft = Math.max(mxLeft, 0);
	            mxTop = Math.max(mxTop, 0);
	            mxRight = Math.min(mxRight, this._mxContainer.clientWidth);
	            mxBottom = Math.min(mxBottom, this._mxContainer.clientHeight);
	        };
	        //修正移动参数
	        iLeft = Math.max(Math.min(iLeft, mxRight - this.drag.offsetWidth), mxLeft);
	        iTop = Math.max(Math.min(iTop, mxBottom - this.drag.offsetHeight), mxTop);
	    }
	    //设置位置，并修正margin
	    if(!this.LockX){ this.drag.style.left = iLeft - this._marginLeft + "px"; }
	    if(!this.LockY){ this.drag.style.top = iTop - this._marginTop + "px"; }
	    //附加程序
	    this.options.onMove();
	};
	//停止拖动
	this.stop=function() {
	    //移除事件
	    $(document).unbind("mousemove", this.moveListener);
	    $(document).unbind("mouseup",this.stopListener);

	  //遍历子窗口，并监听子窗口的mouseup
	    var iframes = $("iframe");
	    for(var i=0;i<iframes.length;i++){
	    	var iframe = iframes[i];
	    	var w = iframe.contentWindow;
	    	$(w).unbind("mouseup");
	    }

	    //附加程序
	    this.options.onStop();
	};
}
//枪座或弹仓
ZY.UI.Seat = Class.create();
ZY.UI.Seat.prototype = {
	initialize:function(options,structure){
		if(options==null)
			return ;
		var structure,container;
		if(structure==null){
			structure = options.structure;
			container = options.container;
		}else{
			container = options;
		}

		if(typeof(container)=="string")
			container = $("#"+container);
		this.id = structure.id;
		this.structure = structure;
		this.container = container;
		this.options = options;
		this.paint();
	},
	hasClass:function(className){
		var classes = $(this.getDom()).attr('class');

		var classArr = [];
		if(classes!=null)
			classArr = classes.split(' ');
		for(var i=0;i<classArr.length;i++){
			if(className==classArr[i])
				return true;
		}
		return false;
	},
	isWeapon:function(){
		if(this.structure.module.indexOf("Drawer")<0){
			return true;
		}
		return false;
	},
	isBullet:function(){
		if(this.structure.module.indexOf("Drawer")>=0){
			return true;
		}
		return false;
	},
	getDom:function(){
		return this.getCabinet().container.find("#"+this.id)[0];
	},
	paint:function(){
		var dom = null;
		dom = $('<div id="'+this.id+'" class="seat"></div>');
		$(this.container.getDom()).append(dom);
		var size = this.getSize();

		dom.width(size.pixel.width-6);
		dom.height(size.pixel.height-6);


		var options = this.getCabinet().options;
		dom.bind("click",ZY.UI.asListener(this, this.defaultSeatClickHandler));

		if(options.seatClickHandler!=null){
			dom.bind("click",ZY.UI.asListener(this, options.seatClickHandler));
		}
		if(options.seatMouseOverHandler!=null)
			dom.bind("mouseover",ZY.UI.asListener(this, options.seatMouseOverHandler));

		if(options.seatMouseLeaveHandler!=null)
			dom.bind("mouseleave",ZY.UI.asListener(this,options.seatMouseLeaveHandler));
	},
	defaultSeatClickHandler:function(){
		this.focus();
	},
	focus:function(){
		$(this.getCabinet().getDom()).find(".focused").removeClass("focused");
		$(this.getDom()).addClass("focused");

	},
	blur:function(){
		$(this.getDom()).removeClass("focused");
	},

	//计算枪座或弹仓的占位空间
	getSize:function(){
		if(this.structure.size!=null)
			return this.structure.size;

		var size = {pixel:{width:0,height:0}};
		var layout = this.container.getLayout();
		var parentSize = this.container.getSize();
		if(layout=="Row"){
			width = parentSize.pixel.width;
			height = (parentSize.pixel.height) /this.container.structure.children.length;
		}else{
			height = parentSize.pixel.height;
			width = (parentSize.pixel.width) /this.container.structure.children.length ;
		}
		width = Math.floor(width);
		height = Math.floor(height);
		size.pixel.width = width;
		size.pixel.height = height;
		this.structure.size= size;
		return size;
	},
	getCabinet:function(){
		if(this.structure.module=="cabinet"){
			return this;
		}else
			return this.container.getCabinet();
	}
};

//区域
ZY.UI.SeatContainer = Class.create();
ZY.UI.SeatContainer.prototype = new ZY.UI.Seat();
ZY.UI.extend(ZY.UI.SeatContainer.prototype, {
	//获得所在容器对象的dom
	getContainerDom:function(){
		if(this.structure.module=="cabinet"){
			return this.container;
		}else
			return this.container.getDom();
	},
	//获得所在容器对象的容纳子对象的dom
	getContainerRoom:function(){
		if(this.container.structure.module=="cabinet"){
			return $(this.container.getDom()).children(".body");
		}else
			return this.container.getDom();
	},


	isCabinet:function(){
		return this.structure.module=="cabinet";
	},


	paint:function(){
		if(this.structure.module!="cabinet"){
			this.paintAsArea();
			return;
		}
		$(this.getContainerDom()).html("");
		var layout = this.getLayout()=="Row"?"column":"row";
		var dom = $('<div id="'+this.id+'"><div class="title"></div><div class="body"></div></div>');
		dom.children(".title").html(this.structure.name);
		dom.children('.body').css("display","flex");
		dom.children('.body').css("flex-flow",layout);

		$(this.getContainerDom()).append(dom);
		var className = "cabinet";
		dom.addClass(className);

		var size = this.getSize();
		dom.width(size.pixel.width);
		dom.height(size.pixel.height+30);
		dom.children('.body').height(size.pixel.height);

		var childrenData = this.structure.children;
		if(childrenData!=null ){
			this.children = [];
			for(var i=0;i<childrenData.length;i++){
				var child = null;
				var childData = childrenData[i];
				if(childData.module.endsWith("Seat")){
					child = new ZY.UI.Seat(this,childData);
				}else{
					child = new ZY.UI.SeatContainer(this,childData);
				}
				this.children.push(child);
			}
		}

	},

	paintAsArea:function(){
		var className = "area";
		var dom = $('<div id="'+this.id+'"></div>');
		$(this.getContainerRoom()).append(dom);
		dom.addClass(className);
		var size = this.getSize();
		if(this.container.getLayout()=="Row"){
			dom.height(size.pixel.height);
			dom.width(size.pixel.width);
		}else{
			dom.width(size.pixel.width);
			dom.height(size.pixel.height);
		}

		dom.css("flex-flow",this.getLayout()=="Row"?"column":"row");  //按行布局的情况下，横向固定，纵向弹性；按列布局则相反


		var childrenData = this.structure.children;
		if(childrenData!=null ){
			this.children = [];
			for(var i=0;i<childrenData.length;i++){
				var child = null;
				var childData = childrenData[i];
				if(childData.module.endsWith("Seat")){
					child = new ZY.UI.Seat(this,childData);
				}else{
					child = new ZY.UI.SeatContainer(this,childData);
				}
				this.children.push(child);
			}
		}
	},

	//计算自己的尺寸
	getSize:function(){
		if(this.size!=null)
			return this.size;
		var r = {reality:{width:0,height:0},pixel:{width:0,height:0}};
		//获取实际尺寸
		var rsize = this.getRealitySize();
		r.reality = rsize;
		//计算像素尺寸
		if(this.structure.module=="cabinet"){
			//根据容器计算出柜子像素尺寸
			var rate = 1;
			var rate1 = r.reality.width / ($(this.container).width());
			var rate2 = r.reality.height / ($(this.container).height()-30);
			rate = rate1>rate2?rate1:rate2;
			r.pixel.width = r.reality.width / rate;
			r.pixel.height =  r.reality.height / rate;
		}else{
			var cabinetSize = this.getCabinetSize();
			//根据柜子的缩放比率计算当前区域的缩放比率
			var rate = cabinetSize.pixel.height / cabinetSize.reality.height;
			r.pixel.width = r.reality.width * rate;
			r.pixel.height = r.reality.height * rate;

		}
		this.size = r;
		return r;
	},

	//获取当前布方式
	getLayout:function(structure){
		if(structure==null)
			structure = this.structure;
		var layout = structure.layout;
		if(layout == null || layout == ""){
			layout = "Row";
			structure.layout = layout;
		}
		return layout;
	},

	//通过子区域尺寸计算本区域尺寸
	getRealitySize:function(currentStructure){
		if(currentStructure==null)
			currentStructure = this.structure;
		var width = currentStructure.width, height=currentStructure.height;
		//结构中已经定义了尺寸的
		if(width!=null && width!=""){
			width = width *1;height = height*1;
			return {width:width,height:height};
		}
		width = 0;
		height = 0;
		//结构中未定义尺寸的
		var layout = this.getLayout(currentStructure);
		var children = currentStructure.children;
		if(children==null){
			alert("无法计算本区域的尺寸，区域ID="+currentStructure.id);
		}else{ //如果存在子元素，则根据子元素尺寸计算。
			if(layout=="Row"){//多行布局，宽度取值所有子容器的最大宽度，高度通过累加多个子容器的高度计算
				for(var i=0;i<children.length;i++){
					var child = children[i];
					var csize = this.getRealitySize(child);
					if(width<csize.width)
						width = csize.width;         //宽度取最大值
					height = height + csize.height;   //高度取累加值
				}
			}else{//多列布局，高度按照一个子容器计算，宽度通过累加累加做个自容器的宽度计算
				for(var i=0;i<children.length;i++){
					var child = children[i];
					var csize = this.getRealitySize(child);
					if(height<csize.height)
						height = csize.height;
					width = width + csize.width;
				}
			}
		}
		currentStructure.width = width;
		currentStructure.height = height;
		return {width:width,height:height};
	},


	//获取柜子尺寸
	getCabinetSize:function(){
		if(this.structure.module=="cabinet"){
			return this.getSize();
		}else{
			return this.container.getCabinetSize();
		}
	},

	getFocusedSeat:function(){
		var focusedDom = $(this.getContainerDom()).find(".focused")[0];
		var id = focusedDom.id;
		var seat = this.getSeat(id);
		return seat;
	},

	getSeat:function(id){
		var seat = null;
		var children = this.children;
		for(var i=0;i<children.length;i++){
			var child = children[i];
			if(child.structure.module.endsWith("Seat")){
				if(child.id == id){
					seat =  child;
				}
			}else {
				seat = child.getSeat(id);
			}
			if(seat!=null)
				break;
		}
		return seat;
	}
});

ZY.UI.iconClasses = ["add","edit","delete","stop","moveUp","moveDown","sms","audit","view","license","delay","connect","forbidden","unforbidden","load","unload"];
ZY.UI.iconListeners = {};
ZY.UI.bindIconListener=function(id,listener){
	$("#"+id).bind("click",listener);
	ZY.UI.iconListeners[id]=listener;
	listener._enabled = true;
};
ZY.UI.disableIcon = function(id){
	var listener = ZY.UI.iconListeners[id];
	if(listener._enabled==false)
		return ;
	listener._enabled = false;
	var jqDom = $("#"+id);
	jqDom.unbind("click",listener);
	var classes = jqDom.attr('class').split(" ");
	for(var i=0;i<classes.length;i++){
		var clazz = classes[i];
		var p = ZY.UI.iconClasses.indexOf(clazz);
		if(p>=0){
			var nclass = clazz+"_disabled";
			jqDom.addClass(nclass);
			break;
		}
	}
}

ZY.UI.enableIcon  = function(id){
	var listener = ZY.UI.iconListeners[id];
	if(listener._enabled)
		return;
	listener._enabled = true;
	var jqDom = $("#"+id);
	jqDom.bind("click",listener);
	var classes = jqDom.attr('class').split(" ");
	for(var i=0;i<classes.length;i++){
		var clazz = classes[i];
		if(clazz.endsWith("_disabled")){
			jqDom.removeClass(clazz);
		}
	}
}

/**
 * 发送post请求，例：
 * 		ZY.UI.sendPostRequest({
 * 			url: 'save',
 * 			data: {.....},
 * 			operation: '保存',
 * 			success: function(result){
 * 				.....
 * 			}
 * 		});
 * @param config:
 * 		  	url: 请求地址
 * 			async: 是否异步请求,true，异步, false 同步
 * 			data: 请求数据
 * 			successMessage，可选，操作成功的提示，如果该参数不设置，方法给出缺省提示；
 * 			failMessage，可选，操作失败的提示，如果该参数不设置，方法给出缺省的提示；
 * 			success: 请求成功时执行的回调，该回调入参为ajax的返回数据
 * 			operation: 当前执行的操作，如保存XX数据等。
 * 			silent: true/false,缺省false，安静模式，当开启该选项时，在成功执行后将不弹出提示框
 */
ZY.UI.sendPostRequest = function(params) {
	params.async=params.async || false;
	params.type="POST";
	var success=params.onSuccess || params.success;
	delete params.success;
	delete params.onSuccess;
	var error=params.onError || params.error;
	delete params.error;
	delete params.onError;
	params.success = function(info){
		if(info.error){
			if(error!=null){
				error.call(this,info);
			}else{
				var msg=params.failMessage || params.operation+"失败!"+info.error;
				showMsg({msg:msg,type:"error"});
			};
		}else{
			if(params.silent) {
				success.call(this,info);
			}else{
				var msg=params.successMessage || params.operation+"成功!";
				showMsg({msg:msg,type:"success",
					callback: function(result){
						success.call(this,info)
					}
				});
			}
		}
	}
	$.ajax(params);
}

/**
 * 发送get请求，例：
 * 		ZY.UI.sendGetRequest({
 * 			url: 'save',
 * 			data: {.....},
 * 			operation: '保存',
 * 			success: function(result){
 * 				.....
 * 			}
 * 		});
 * @param params:
 * 		  	url: 请求地址
 * 			async: 是否异步请求,true，异步, false 同步
 * 			data: 请求数据
 * 			success: 请求成功时执行的回调，该回调入参为ajax的返回数据
 * 			operation: 当前执行的操作，如保存XX数据等。
 */
ZY.UI.sendGetRequest = function(params) {
	params.async= params.async || false;
	params.type="GET";
	var success=params.onSuccess || params.success;
	delete params.onSuccess;
	delete params.success;
	params.success = function(info){
		if(info.error){
			showMsg({msg:params.operation+"失败!"+info.error,type:"error"});
		}else{
			success.call(this,info);
		}
	}
	$.ajax(params);
}

ZY.UI.TreeGrid = function(options){
	var grid = new ZY.UI.Grid(options);
	var childrenProperties = options.children.split("|");
	grid.getChildrenData = function(rowData){
		var children = [];
		for(var i=0;i<childrenProperties.length;i++){
			if(rowData[childrenProperties[i]]!=null){
				children.addAll(rowData[childrenProperties[i]]);
			}
		}
		return children;
	}
	var spreaded = [];
	grid.spreadData = function(data,deep){
		if(deep==null)
			deep = 0;
		//如果不是数组，直接加入，并加入子
		if(Object.prototype.toString.call(data) === '[object Array]'){
			for(var i=0;i<data.length;i++){
				var one = data[i];
				grid.spreadData(one,deep);
			}
		}else{
			data._deep = deep;
			spreaded.push(data);
			var children = this.getChildrenData(data);
			grid.spreadData(children,deep+1);
		}
	}
	grid.findParentRow = function(row){
		var p = row.findSelfPosition();
		for(var i=p-1;i>=0;i--){
			var brother = grid.rows[i];
			var deep = brother.data._deep;
			if(deep==row.data._deep-1){
				return brother;
			}
		}
	}
	/*获取所有子行*/
	grid.getChildren = function(row){
		var children = [];
		var p = row.findSelfPosition();
		for(var i =p+1;i<grid.rows.length;i++){
			var r = grid.rows[i];
			if(r.data._deep==row.data._deep+1){
				children.push(r);
			}else {
				if(r.data._deep<=row.data._deep)
					break;
			}
		}
		return children;
	}

	grid.isAllBrotherSelected = function(row){
		var p = row.findSelfPosition();
		var brotherAllChecked = row.selected;
		if(brotherAllChecked==false){
			return false;
		}
		for(var i=p-1;i>=0;i--){
			var brother = grid.rows[i];
			var deep = brother.data._deep;
			if(deep==row.data._deep){ //是兄弟节点
				if(brother.selected==false){
					return false;
				}
			}else{
				if(deep<row.data._deep) //是父节点
					break;
			}
		}

		for(var i=p+1;i<grid.rows.length;i++){
			var brother = grid.rows[i];
			var deep = brother.data._deep;
			if(deep==row.data._deep){//是兄弟节点
				if(brother.selected==false)
					return false;
			}else{
				if(deep<row.data._deep) //是父节点的兄弟节点
					break;
			}
		}
		return true;
	}

	//递归向下设置子节点选中状态
	grid.setChildrenSelected=function(row){
		var children = grid.getChildren(row);
		if(children.length<1)
			return;

		//将所有子节点设置为父节点相同的状态
		for(var i=0;i<children.length;i++){
			var child = children[i];
			child.setSelected(row.selected,true);
			grid.setChildrenSelected(child);
		}
	}
	//递归向上设置父节点的选中状态
	grid.setParentSelected = function(row){
		var parentRow = grid.findParentRow(row);
		if(parentRow==null)
			return ;
		//如果所有兄弟都选中
		if(row.selected && grid.isAllBrotherSelected(row)){
			parentRow.setSelected(row.selected,true);
			grid.setParentSelected(parentRow);
		}else{
			if(parentRow.selected){
				parentRow.setSelected(false,true);
				grid.setParentSelected(parentRow);
			}
		}
	}
	grid.rowChecked = function(row,upOrDown){
		if(options.ifMultSelected==true){
			grid.setParentSelected(row);
			grid.setChildrenSelected(row);
		}
	}

	/**
	 * 如果子节点全部选中，则只返回父节点
	 */
	grid.getSelectedRows = function(){
		var selected = [];
		for(var i=0;i<this.rows.length;i++){
			var row = this.rows[i];
			var parentRow = this.findParentRow(row);
			if(row.selected && (parentRow==null || parentRow.selected==false)){
				selected.push(row);
			}
		}
		return selected;
	};

	grid.bind = function(data){
		spreaded = [];
		this.spreadData(data);
		data = spreaded;
		this.rows = [];
		for(var i=0;i<data.length;i++){
			var row = new ZY.UI.GridRow(data[i],this.getRowSeq(),this);
			this.rows.push(row);
			row.onSelected = function(){
				grid.rowChecked(this);
			}
		}
		this.paintRows();
		this.layout();
		this.currentData = data
		this.refreshHeadSelectStatus();
	}
	return grid;
}

