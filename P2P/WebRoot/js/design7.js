/**
*创建多项填空题
*create by 何肸
*2016/1/23/22:50
**/

function createMultipleLineWrite(){

	$('.edit-content').append('<div class="item" id="multipleLine">'+
						//'<input type="hidden" name="hidden" value="多项填空题"><!-- 隐藏域记录该题目的性质 -->'+
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
									'<div class="item-title"><input type="text" value="多项填空题" class="h2" name="itemTitle"></div>'+
								'</div>'+
								'<div class="item-choice">'+
									'<ul>'+
										'<li>'+
											'<input type="text" value="" class="label" name="cName">'+
											'<input type="hidden" id="choiceId" value="" name="choiceId">'+
											'<a href="javascript:;" class="del">删除</a>'+
										'</li>'+
										'<li>'+
											'<input type="text" value="" class="label" name="cName">'+
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

$('.choice-bar').find('#mc_wr_tp').click(function(event) {

	$.ajax({
		type:"post",
		url:"http://localhost:8080/P2P/InsertNew",
		data:{type:"多项填空题",tid:$('#tempId').val()},
		dataType:"json",
		success:function(data){
			if(data.success){
				createMultipleLineWrite();
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