/**
 * 创建婚姻状况选项
 */

function createMarrige(){

	$('.edit-content').append('<div class="item" id="single">'+
						//'<input type="hidden" name="hidden" value="婚姻状况"><!-- 隐藏域记录该题目的性质 -->'+
						//'<input type="hidden" name="itemNumber" id="itemNumber" value="2">'+
							'<input type="hidden" id="itemId" value="">'+
							'<div class="item-setup">'+
								'<a href="javascript:;" id="moveUp">上移</a>'+
								'<a href="javascript:;" id="moveDown">下移</a>'+
								'<a href="javascript:;" id="copy">复制</a>'+
								'<a href="javascript:;" id="delete">删除</a>'+
							'</div>'+
							'<div class="item-content">'+
								'<div class="drag">'+
									'<div class="item-title"><input type="text" value="婚姻状况" class="h2" name="itemTitle"></div>'+
								'</div>'+
								'<div class="item-choice">'+
									'<ul>'+
										'<li>'+
											'<input type="radio" name="radio" value="radio">'+
											'<input type="text" value="已婚" class="label" name="cName">'+
											'<input type="hidden" id="choiceId" value="" name="choiceId">'+
											'<a href="javascript:;" class="del">删除</a>'+
										'</li>'+
										'<li>'+
											'<input type="radio" name="radio" value="radio">'+
											'<input type="text" value="单身" class="label" name="cName">'+
											'<input type="hidden" id="choiceId" value="" name="choiceId">'+
											'<a href="javascript:;" class="del">删除</a>'+
										'</li>'+
									'</ul>'+
								'</div>'+
							'</div>'+
							'<div class="item-add"><a href="javascript:;">+添加选项</a></div>'+
						'</div>');
	again();
}

$('.choice-bar').find('#mar_tp').click(function(event) {

	$.ajax({
		type:"post",
		url:"http://localhost:8080/P2P/InsertNew",
		data:{type:"婚姻状况",tid:$('#tempId').val()},
		dataType:"json",
		success:function(data){
			if(data.success){
				createMarrige();
				$('.edit-content').find('.item').last().find('#itemId').val(data.itemId);
				var choiceIds = $('.edit-content').find('.item').last().find('#choiceId');
				choiceIds[0].value = data.cid1;
				choiceIds[1].value = data.cid2;
				
				$("#update-status").html(data.msg).fadeIn(100);
				setTimeout(function(){$("#update-status").html("").fadeOut(100);},2000);
			}
		},
		error:function(jqXHR){alert("服务器错误")}
	});
});