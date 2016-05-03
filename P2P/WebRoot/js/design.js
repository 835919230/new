
var items = $('.item');

items.find('#moveUp').click(function(event) {
	var This = $(this);
	var oItem = This.parent().parent();
	var oClone = This.parent().parent().clone(true);
	var oBrother = oItem.prev();
	if(!(oBrother.find('#itemId').val()===undefined)){
		$.ajax({
			type:"post",
			url:"http://localhost:8080/P2P/MoveUp",
			data:{
					itemId:    oItem.find("#itemId").val(),
					brotherId: oItem.prev().find("#itemId").val()
				 },
			dataType:"json",
			success:function(data){
						if(data.success){
							oItem.remove();
							oItem = null;
							oClone.insertBefore(oBrother);
							$("#update-status").html(data.msg).fadeIn(100);

							oClone.find("#itemId").val(data.itemId).
																		parent().next().find("#itemId").val(data.brotherId);
							setTimeout(function(){$("#update-status").fadeOut(100);},2000);
						}
					},
			error:function(){}
		});
	}else{
		alert("已经是第一个了");
	}
});

items.find('#moveDown').click(function(event) {
	var This = $(this);
	var oItem = This.parent().parent();
	var oClone = This.parent().parent().clone(true);
	var oBrother = oItem.next();
	if(!(oBrother.find('#itemId').val()===undefined)){
		$.ajax({
			type:"post",
			url:"http://localhost:8080/P2P/MoveDown",
			data:{itemId:This.parent().parent().find("#itemId").val(),brotherId:This.parent().parent().next().find("#itemId").val()},
			dataType:"json",
			success:function(data){
						if(data.success){
							oItem.remove();
							oClone.insertAfter(oBrother);
							oItem = null;
							$("#update-status").html(data.msg).fadeIn(100);
							oClone.find("#itemId").val(data.itemId).
							parent().prev().find("#itemId").val(data.brotherId);
							setTimeout(function(){$("#update-status").fadeOut(100);},2000);
						}
					},
			error:function(){alert("保存失败TAT");}
		});
	}else{
		alert("已经是最后一个了");
	}
});

items.find('#copy').click(function(event) {
	var This = $(this);
	$.ajax({
		type:"post",
		url:"http://localhost:8080/P2P/CopyItem",
		data:{itemId:This.parent().parent().find("#itemId").val()},
		dataType:"json",
		success:function(data){
					if(data.success){
						This.parent().parent().parent().append(This.parent().parent().clone(true));
						$("#update-status").html(data.msg).fadeIn(100);
						
						//$('.item').last();
						
						var last = $('.item').last();
						last.find("#itemId").val(data.newItemId);
						
						//alert(last.find("#itemId").val());
						
						var length = last.find("li").length;
						var lastId = data.maxCid;
						alert(lastId);
						var choiceIds = last.find("li #choiceId");
						console.log(choiceIds);
						for(var i = length-1;i >= 0;i--){
							choiceIds[i].setAttribute("value",lastId--);
						}
						setTimeout(function(){$("#update-status").fadeOut(100);},2000);
					}
				},
		error:function(){alert("保存失败TAT");}
	});
});

items.find('#delete').click(function(event) {
	var This = $(this);
	if(confirm("确定删除吗?")){
		$.ajax({
			type:"post",
			url:"http://localhost:8080/P2P/DeleteItem",
			data:{itemId:$(this).parent().parent().find("#itemId").val()},
			dataType:"json",
			success:function(data){
						if(data.success){
							$("#update-status").html(data.msg).fadeIn(100);
							This.parent().parent().remove();
							$("#update-status").html(data.msg).fadeIn(100);
							setTimeout(function(){$("#update-status").html("").fadeOut(100);},2000);
						}
					},
			error:function(){alert("保存失败TAT");}
		});
	}
});

items.find('.item-add a').click(function(event) {
	var This = $(this);
	var cId = This.parent().prev().find("#choiceId").last().val();
	$.ajax({
		type:"post",
		url:"http://localhost:8080/P2P/AddChoice",
		data:{choiceId:cId},
		dataType:"json",
		success:function(data){
					if(data.success){
						var itemchoices = This.parent().parent().find('.item-choice');
						itemchoices.find('li').last().after(This.parent().parent().find('.item-choice').find('li').last().clone(true));
						itemchoices.last().find('li #choiceId').val(data.newChoiceId);
						
						$("#update-status").html(data.msg).fadeIn(100);
						setTimeout(function(){$("#update-status").fadeOut(100);},2000);
					}
				},
		error:function(){alert("保存失败TAT");}
	});
});

items.find('li .del').click(function(event){
	var This = $(this);
	if(This.parent().parent().find('li').length>1){		
		$.ajax({
			type:"post",
			url:"http://localhost:8080/P2P/DeleteChoice",
			data:{choiceId:This.parent().find("#choiceId").val()},
			dataType:"json",
			success:function(data){
						if(data.success){
							This.parent().remove();
							$("#update-status").html(data.msg).fadeIn(100);
							setTimeout(function(){$("#update-status").fadeOut(100);},2000);
						}
					},
			error:function(){alert("保存失败TAT");}
		});
	}else{
		confirm("规定最后一个选项不能删除哦");
	}
});

$('.edit-area-box .edit-area .edit-motal input').blur(function(e){
	var This = $(this);
	//alert(This.val())
	$.ajax({
		url:"http://localhost:8080/P2P/UpdateMotal",
		type:"post",
		data:{tId:$('#tempId').val(),motal:This.val()},
		dataType:"json",
		success:function(data){
			if(data.success){
				$("#update-status").html(data.msg).fadeIn(100);
				setTimeout(function(){$("#update-status").fadeOut(100);},2000);
			}
		},
		error:function(){alert("保存失败TAT");}
	})
});

$('.edit-area-box .edit-area .edit-title input').blur(function(e){
	var This = $(this);
	//alert(This.val())
	$.ajax({
		url:"http://localhost:8080/P2P/UpdateTitle",
		type:"post",
		data:{tId:$('#tempId').val(),title:This.val()},
		dataType:"json",
		success:function(data){
			if(data.success){
				$("#update-status").html(data.msg).fadeIn(100);
				setTimeout(function(){$("#update-status").fadeOut(100);},2000);
			}
		},
		error:function(){alert("保存失败TAT");}
	})
});

items.find("li .label").blur(function(e){
	var This = $(this);
	console.log(This.parent());
	$.ajax({
		type:"post",
		url:"http://localhost:8080/P2P/UpdateChoice",
		data:{choiceId:This.parent().find('#choiceId').val(),cName:This.val()},
		dataType:"json",
		success:function(data){
					if(data.success){
						$("#update-status").html(data.msg).fadeIn(100);
						setTimeout(function(){$("#update-status").fadeOut(100);},2000);
					}else{
						$("#update-status").html(data.msg).fadeIn(100);
						setTimeout(function(){$("#update-status").fadeOut(100);},2000);
					}
				},
		error:function(){alert("保存失败TAT")}
	});
});

$('.item').find('.item-content .item-title .h2').blur(function(event){
	var This = $(this);
	var Id = This.parent().parent().parent().parent().find('#itemId').val();
	$.ajax({
		type:"post",
		url:"http://localhost:8080/P2P/UpdateItem",
		data:{itemId:Id,qName:This.val()},
		dataType:"json",
		success:function(data){
					if(data.success){
						$("#update-status").html(data.msg).fadeIn(100);
						setTimeout(function(){$("#update-status").fadeOut(100);},2000);
					}else{
						$("#update-status").html(data.msg).fadeIn(100);
						setTimeout(function(){$("#update-status").fadeOut(100);},2000);
					}
				},
		error:function(){alert("保存失败TAT")}
	});
})

function fn(){
	var height = $('.edit-area').height()+130;
	console.log(height);
	document.getElementById('overhide').style.height = height+'px';
}


/**
 * 用来追加增加的选项，给予事件，与后台交互
 */
function again(){

	var length = $('.item').length;
	var last = $('.item').last();
	
	last.find('#moveUp').click(function(event){
		var This = $(this);
		var oItem = This.parent().parent();
		var oClone = This.parent().parent().clone(true);
		var oBrother = oItem.prev();
		if(!(oBrother.find('#itemId').val()===undefined)){
			$.ajax({
				type:"post",
				url:"http://localhost:8080/P2P/MoveUp",
				data:{
						itemId:    oItem.find("#itemId").val(),
						brotherId: oItem.prev().find("#itemId").val()
					 },
				dataType:"json",
				success:function(data){
							if(data.success){
								oItem.remove();
								oClone.insertBefore(oBrother);
								$("#update-status").html(data.msg).fadeIn(100);

								oClone.find("#itemId").val(data.itemId).
																			parent().next().find("#itemId").val(data.brotherId);
								setTimeout(function(){$("#update-status").fadeOut(100);},2000);
							}
						},
				error:function(){}
			});
		}else{
			alert("已经是第一个了");
		}
	});

	last.find('#moveDown').click(function(event){
		var This = $(this);
		var oItem = This.parent().parent();
		var oClone = This.parent().parent().clone(true);
		var oBrother = oItem.next();
		if(!(oBrother.find('#itemId').val()===undefined)){
			$.ajax({
				type:"post",
				url:"http://localhost:8080/P2P/MoveDown",
				data:{itemId:This.parent().parent().find("#itemId").val(),brotherId:This.parent().parent().next().find("#itemId").val()},
				dataType:"json",
				success:function(data){
							if(data.success){
								oItem.remove();
								oClone.insertAfter(oBrother);
								
								$("#update-status").html(data.msg).fadeIn(100);
								oClone.find("#itemId").val(data.itemId).
								parent().prev().find("#itemId").val(data.brotherId);
								setTimeout(function(){$("#update-status").fadeOut(100);},2000);
							}
						},
				error:function(){alert("保存失败TAT");}
			});
		}else{
			alert("已经是最后一个了");
		}
});

last.find('#copy').click(function(event) {
	var This = $(this);
	$.ajax({
		type:"post",
		url:"http://localhost:8080/P2P/CopyItem",
		data:{itemId:This.parent().parent().find("#itemId").val()},
		dataType:"json",
		success:function(data){
					if(data.success){
						This.parent().parent().parent().append(This.parent().parent().clone(true));
						$("#update-status").html(data.msg).fadeIn(100);
						var copyItem = This.parent().parent().next();
						copyItem.find("#itemId").val(data.newItemId);
						
						alert(copyItem.find("#itemId").val())
						var length = copyItem.find("li").length;
						var lastId = parseInt(data.maxCid);
						console.log(lastId)
						
						var choiceIds = copyItem.find("li #choiceId");
						console.log(choiceIds);
						for(var i = length-1;i >= 0;i--){
							choiceIds[i].setAttribute("value",lastId--);
						}
						setTimeout(function(){$("#update-status").fadeOut(100);},2000);
					}
				},
		error:function(){alert("保存失败TAT");}
	});
});

last.find('#delete').click(function(event) {
	var This = $(this);
	if(confirm("确定删除吗?")){
		$.ajax({
			type:"post",
			url:"http://localhost:8080/P2P/DeleteItem",
			data:{itemId:$(this).parent().parent().find("#itemId").val()},
			dataType:"json",
			success:function(data){
						if(data.success){
							This.parent().parent().remove();
							$("#update-status").html(data.msg).fadeIn(100);
							setTimeout(function(){$("#update-status").fadeOut(100);},2000);
						}
					},
			error:function(){alert("保存失败TAT");}
		});
	}
});

last.find('.item-add a').click(function(event) {
	var This = $(this);
	var cId = This.parent().prev().find("#choiceId").last().val();
	$.ajax({
		type:"post",
		url:"http://localhost:8080/P2P/AddChoice",
		data:{choiceId:cId},
		dataType:"json",
		success:function(data){
					if(data.success){
						var itemchoices = This.parent().parent().find('.item-choice');
						itemchoices.find('li').last().after(This.parent().parent().find('.item-choice').find('li').last().clone(true));
						itemchoices.find('li').last().find('#choiceId').val(data.newChoiceId);
						
						$("#update-status").html(data.msg).fadeIn(100);
						setTimeout(function(){$("#update-status").fadeOut(100);},2000);
					}
				},
		error:function(){alert("保存失败TAT");}
	});
});

last.find('li .del').click(function(event) {
	var This = $(this);
	if(This.parent().parent().find('li').length>1){		
		$.ajax({
			type:"post",
			url:"http://localhost:8080/P2P/DeleteChoice",
			data:{choiceId:This.parent().find("#choiceId").val()},
			dataType:"json",
			success:function(data){
						if(data.success){
							This.parent().remove();
							$("#update-status").html(data.msg).fadeIn(100);
							setTimeout(function(){$("#update-status").fadeOut(100);},2000);
						}
					},
			error:function(){alert("保存失败TAT");}
		});
	}else{
		confirm("规定最后一个选项不能删除哦");
	}
});

//$('.item').last().find("li input[type!='hidden']").blur(function(e){
last.find("li .label").blur(function(e){
	var This = $(this);
	console.log(This.parent());
	$.ajax({
		type:"post",
		url:"http://localhost:8080/P2P/UpdateChoice",
		data:{choiceId:This.parent().find('#choiceId').val(),cName:This.val()},
		dataType:"json",
		success:function(data){
					if(data.success){
						$("#update-status").html(data.msg).fadeIn(100);
						setTimeout(function(){$("#update-status").fadeOut(100);},2000);
					}else{
						$("#update-status").html(data.msg).fadeIn(100);
						setTimeout(function(){$("#update-status").fadeOut(100);},2000);
					}
				},
		error:function(){alert("保存失败TAT")}
	});
});



last.find('.item-content .item-title .h2').blur(function(event){
	var This = $(this);
	var Id = This.parent().parent().parent().parent().find('#itemId').val();
	$.ajax({
		type:"post",
		url:"http://localhost:8080/P2P/UpdateItem",
		data:{itemId:Id,qName:This.val()},
		dataType:"json",
		success:function(data){
					if(data.success){
						$("#update-status").html(data.msg).fadeIn(100);
						setTimeout(function(){$("#update-status").fadeOut(100);},2000);
					}else{
						$("#update-status").html(data.msg).fadeIn(100);
						setTimeout(function(){$("#update-status").fadeOut(100);},2000);
					}
				},
		error:function(){alert("保存失败TAT")}
	});
});

//var oEdit = document.getElementById('editContent');
//var sort = new Sortable(oEdit, {
//		handle: ".item", // Restricts sort start click/touch to the specified element
//		draggable: ".item", // Specifies which items inside the element should be sortable
//		onUpdate: function (evt){
// 					var item = evt.item; // the current dragged HTMLElement
//					}
//	});
}