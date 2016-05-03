/**
 * 创建 性别 选择题
 * created by 何肸
 * 2016/1/24
 */

function createSex(){

	$('.edit-content').append('<div class="item" id="single">'+
						//'<input type="hidden" name="hidden" value="性别"><!-- 隐藏域记录该题目的性质 -->'+
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
									'<div class="item-title"><input type="text" value="性别" class="h2" name="itemTitle"></div>'+
								'</div>'+
								'<div class="item-choice">'+
									'<ul>'+
										'<li>'+
											'<input type="radio" name="radio" value="男">'+
											'<input type="text" value="男" class="label" name="cName">'+
											'<input type="hidden" id="choiceId" value="" name="choiceId">'+
										'</li>'+
										'<li>'+
											'<input type="radio" name="radio" value="女">'+
											'<input type="text" value="女" class="label" name="cName">'+
											'<input type="hidden" id="choiceId" value="" name="choiceId">'+
										'</li>'+
									'</ul>'+
								'</div>'+
							'</div>'+
						'</div>');
	again();
}

$('.choice-bar').find('#sex_tp').click(function(event) {

	$.ajax({
		type:"post",
		url:"http://localhost:8080/P2P/InsertNew",
		data:{type:"性别",tid:$('#tempId').val()},
		dataType:"json",
		success:function(data){
			if(data.success){
				createSex();
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