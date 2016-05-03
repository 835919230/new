window.onload=function(){
/*显示登录小窗口*/
	var loginButton = document.getElementById('loginButton');
	var overhide = document.getElementById('overhide');

	loginButton.onclick = function(){
		var loginArea = document.getElementById('loginArea');
		loginArea.style.display='block';
		loginArea.style.opacity = '1';
		overhide.style.opacity = '0.5';
	}
	/*END显示登陆小窗口*/

	/*关闭登录小窗口*/
	var closeX = document.getElementById('closeX');

	closeX.onclick = function(){
		var loginArea = document.getElementById('loginArea');
		loginArea.style.opacity = '0';
		loginArea.style.display='none';
		overhide.style.opacity = '0';
		return false;
	}
	/*END关闭登录小窗口*/
}