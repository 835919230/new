window.onload = function(){

	var loginArea = document.getElementById('loginArea');
	var overhide = document.getElementById('overhide');
	var closeX = document.getElementById('closeX');

	closeX.onclick = function(){
		startMove(loginArea,{opacity:0},startMove(overhide,{opacity:0}));

	}

	var loginButton = document.getElementById('loginButton');
	loginButton.onclick = function(){
		overhide.style.display = 'block';
		loginArea.style.display = 'block';
		startMove(loginArea,{opacity:100});
		startMove(overhide,{opacity:50});
	}

	var oA = document.getElementById('drop');
	var oDM = document.getElementById('dropMenu');
	var oDl = document.getElementById('dl1');
	
	if(oA!=null){
		oA.onmouseout = function(){
			disappear(oDM);
		}

		oA.onmouseover = function(){
			show(oDM);
		}
	}
	if(oDM!=null){
		oDM.onmouseout = function(){
			disappear(oDM);
		}

		oDM.onmouseover = function(){
			show(oDM);
		}
	}
	

	 var logo = document.getElementById('logo');
	 if(logo!=null){logo.onclick = function(){
		 location.href='index.jsp';
	 };}
}