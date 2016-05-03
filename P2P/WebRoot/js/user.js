window.onload = function(){
	var dropMenu 	=  document.getElementById('dropMenu');
	var triangle 	=  document.getElementById('triangle');
	var cubeA 	 	=  document.getElementById('cubeA');
	var tableA 	 	=  document.getElementById('tableA');
	var cube 	 	=  document.getElementById('cube');
	var table 	 	=  document.getElementById('table');
	var tbBox 	 	=  document.getElementById('tbBox');
	var tbArea   	=  tbBox.children[0];
	var tb 		 	=  tbArea.children[0];
	var tBody 	 	=  tb.tBodies[0];
	var aTr 	 	=  tBody.getElementsByTagName('tr');
	var list 	 	=  document.getElementById('list');
 	var aL   	 	=  list.getElementsByTagName('li');
	var aLi      	=  list.getElementsByTagName('li');
	var select   	=  document.getElementById('select');
	var sel_type 	=  document.getElementById('select_type');
	var oTxt 	 	=  document.getElementById('sear_text');
	var obtn 	 	=  document.getElementById('sear_btn');
	var overhide 	=  document.getElementById('overhide');
	var rbWindow 	=  document.getElementById('rbWindow');
	var closeX 	 	=  document.getElementById('closeX');
	var btn_recycle =  document.getElementById('btn_recycle');
	var allSel 		=  document.getElementById('allSel');
	var btnTable 	=  document.getElementById('btn-table');
	var oContent    =  document.getElementById('content');

	//alert(parseInt(dropMenu.style.height));
	triangle.onclick = function(){
		if(triangle.className ==='triangle-up'){
			triangle.className = 'triangle-down';
			dropMenu.children[0].style.display = 'none';
			startMove(dropMenu,{height:0},startMove(dropMenu,{opacity:0}));
		}else{
			triangle.className = 'triangle-up';
			dropMenu.style.display = 'block';
			dropMenu.children[0].style.display = 'block';
			startMove(dropMenu,{height:120},startMove(dropMenu,{opacity:100}));
		}
	}

	oContent.onclick = function(){
		if(triangle.className ==='triangle-up'){
			triangle.className = 'triangle-down';
			dropMenu.children[0].style.display = 'none';
			startMove(dropMenu,{height:0},startMove(dropMenu,{opacity:0}));
		}
	}

	/*用js实现表格鼠标覆盖样式*/
	for(var i = 0;i<aTr.length;i++){
		aTr[i].onmouseover = function(){
			this.style = 'background:rgb(236,250,255); border-left:3px rgb(83,164,244) solid;';
			this.children[0].style = 'padding-left:28px;'
		}
		aTr[i].onmouseout = function(){
			this.style = 'background:white; border-left:0px;';
			this.children[0].style = 'padding-left:30px;'
		}
	}
			
	/*END用js实现表格鼠标覆盖样式*/


	//用js改变筛选按钮的样式及选择显示方式
	cubeA.onclick=function(){
		cubeA.className='none';
		tableA.className = 'none';
		cube.style= "background:url('css/img/cube2.png') no-repeat;margin: 4px 0 4px 15px;";
		table.style = "background: url('css/img/table1.png') no-repeat;margin: 4px 0 4px 15px;";
		cubeA.className='active';
		if(cubeA.className==='active'&&tableA.className!='active'){
			list.style.display='block';
			tbBox.style.display = 'none';
			btnTable.style.display ='none';
		}
		return false;
	};
	cubeA.onmouseover=function(){
		cube.style = "background:url('css/img/cube2.png') no-repeat;margin: 4px 0 4px 15px;";
	};
	cubeA.onmouseout=function(){
		if(cubeA.className!='active'){
			cube.style = "background: url('css/img/cube1.png') no-repeat;margin: 4px 0 4px 15px;";
		}
	};

	tableA.onclick=function(){
		cubeA.className='none';
		tableA.className = 'none';
		cube.style = "background: url('css/img/cube1.png') no-repeat;margin: 4px 0 4px 15px;";
		table.style = "background: url('css/img/table2.png') no-repeat;margin: 4px 0 4px 15px;";
		tableA.className='active';
		if(tableA.className==='active'&&cubeA.className!='active'){
			list.style.display='none';
			tbBox.style.display = 'block';
			btnTable.style.display ='block';
		}
		return false;
	};
	tableA.onmouseover=function(){
		table.style = "background: url('css/img/table2.png') no-repeat;margin: 4px 0 4px 15px;";
	};
	tableA.onmouseout=function(){
		if(tableA.className!='active'){
			table.style = "background: url('css/img/table1.png') no-repeat;margin: 4px 0 4px 15px;";
		}
	};
	//END用js改变筛选按钮的样式及选择显示方式

	//用js强加li块样式
	for(var i=0;i<aL.length;i++){
		var t = aL[i];
		if(t.className==='form'){
			t.style="border-top: 3px solid #7acc5a;";
		}
		if(t.className==='survey'){
			t.style="border-top: 3px solid #53a4f4;"
		}
		if(t.className==='test'){
			t.style="border-top: 3px solid #ffc539;"
		}
	}
	//END用js强加li块样式

	//用来筛选的js代码
	function show(obj){
		if(obj!='all'){
			console.log(obj);
			for (var i = 1; i < aLi.length; i++) {
				var t = aLi[i];
				if(t.className!=obj){
					t.style.display = 'none';
				}else{
					t.style.display = 'block';
				}
			}
			for (var i = 0; i < aTr.length; i++) {
				var t = aTr[i];
				if(t.className!=obj){
					t.style.display = 'none';
				}else{
					t.style.display = 'table-row';
				}
			}
		}else{
			for (var i = 1; i < aLi.length; i++){
				aLi[i].style.display = 'block';
			}
			for (var i = 0; i < aTr.length; i++){
				aTr[i].style.display = 'table-row';
			}
		}
	}

			sel_type.onchange = function(){
				if(sel_type.value==='all'){
					show('all');
				}else if(sel_type.value==='form'){
					show('form');
				}else if(sel_type.value==='test'){
					show('test');
				}else if(sel_type.value==='survey'){
					show('survey');
				}
			}

			/*排序部分*/
			var arr = [];
			for(var i = 1;i<aLi.length;i++){
				arr.push(aLi[i]);
			}

			var Arr = [];
			for(var i=0;i<aTr.length;i++){
				Arr.push(aTr[i]);
			}
	
			select.onchange = function(){
				if(select.value==='nto'){
					arr.sort(function(a,b){return parseInt(a.childNodes[3].innerHTML.charAt(0))-parseInt(b.childNodes[3].innerHTML.charAt(0))});
					Arr.sort(function(a,b){return(parseInt(a.children[4].innerHTML.charAt(0))-parseInt(b.children[4].innerHTML.charAt(0)))});
					for(var i = 0;i < arr.length;i++){
						document.getElementById('theUl').appendChild(arr[i]);
					}
					for(var i=0;i<Arr.length;i++){
						tBody.appendChild(Arr[i]);
					}
				}else{
					arr.sort(function(a,b){return parseInt(b.childNodes[3].innerHTML.charAt(0))-parseInt(a.childNodes[3].innerHTML.charAt(0))});
					Arr.sort(function(a,b){return(parseInt(b.children[4].innerHTML.charAt(0))-parseInt(a.children[4].innerHTML.charAt(0)))});
					for(var i = 0;i < arr.length;i++){
						document.getElementById('theUl').appendChild(arr[i]);
					}
					for(var i=0;i<Arr.length;i++){
						tBody.appendChild(Arr[i]);
					}
				}
			}
			/*END排序部分*/

			/*搜索功能的js实现*/
			obtn.onclick = function(){
				sTxt = oTxt.value.toLowerCase();
				var arr = sTxt.split(' ');
				if(cubeA.className==='active'){
					for(var i = 1;i < aLi.length;i++){
						var title = aLi[i].children[0].innerHTML.toLowerCase();
						aLi[i].style.display = 'none';
						for(var j = 0;j<arr.length;j++){
							if(title.search(arr[j])!=-1){
								aLi[i].style.display = 'block';
							}
						}
					}
				}else{
					for(var i=0;i<aTr.length;i++){
						var title = aTr[i].children[0].innerHTML.toString().toLowerCase();
						aTr[i].style.display='none';
						for(var j=0;j<arr.length;j++){
							if(title.search(arr[j])!=-1){
								aTr[i].style.display = 'table-row';
							}
						}
					}
				}
			}
			//console.log(aTr[0].children[0].innerHTML.toString());
			oTxt.onkeydown = function(ev){
				var e = ev||event;
				if(parseInt(e.keyCode)===13){
					sTxt = oTxt.value.toLowerCase();
					var arr = sTxt.split(' ');
					if(cubeA.className==='active'){
						for(var i = 1;i < aLi.length;i++){
							var title = aLi[i].children[0].innerHTML.toLowerCase();
							aLi[i].style.display = 'none';
							for(var j = 0;j<arr.length;j++){
								if(title.search(arr[j])!=-1){
									aLi[i].style.display = 'block';
								}
							}
						}
					}else{
						for(var i=0;i<aTr.length;i++){
							var title = aTr[i].children[0].innerHTML.toString().toLowerCase();
							aTr[i].style.display='none';
							for(var j=0;j<arr.length;j++){
								if(title.search(arr[j])!=-1){
									aTr[i].style.display = 'table-row';	
								}
							}
						}
					}
				}
			}
			/*END搜索功能的js实现*/

			//实现开关回收站小窗口的js代码
			closeX.onclick = function(){
				startMove(rbWindow,{opacity:0},startMove(overhide,{opacity:0}));
			}

			btn_recycle.onclick = function(){
				overhide.style.display = 'block';
				rbWindow.style.display = 'block';
				startMove(rbWindow,{opacity:100},startMove(overhide,{opacity:50}));
			}

			allSel.onclick = function(){
				allSel.checked != allSel.checked;
				var tbody = rbWindow.getElementsByTagName('tbody')[0];
				var aCh = tbody.getElementsByTagName('input');
				if(allSel.checked===true){
					for(var i = 0;i<aCh.length;i++){
						aCh[i].checked = true;
					}
				}else{	
					for(var i = 0;i<aCh.length;i++){
						aCh[i].checked = false;
					}
				}
			}
			//END实现开关回收站小窗口的js代码
}