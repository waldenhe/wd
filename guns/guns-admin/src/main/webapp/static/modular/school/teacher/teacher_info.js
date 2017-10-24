/**
 * 初始化教师详情对话框
 */
var TeacherInfoDlg = {
    teacherInfoData : {},
    validateFields: {
    	cardId: {
            validators: {
                notEmpty: {
                    message: '身份证号不能为空'
                }
            }
        },
        teachername: {
            validators: {
                notEmpty: {
                    message: '姓名不能为空'
                }
            }
        },
        department: {
            validators: {
                notEmpty: {
                    message: '部门不能为空'
                }
            }
        },
        duty: {
            validators: {
                notEmpty: {
                    message: '职务不能为空'
                }
            }
        },
        teachernum: {
            validators: {
                notEmpty: {
                    message: '教工号不能为空'
                }
            }
        },
        sex: {
            validators: {
                notEmpty: {
                    message: '性别不能为空'
                }
            }
        },
        idType: {
            validators: {
                notEmpty: {
                    message: '类型不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
TeacherInfoDlg.clearData = function() {
    this.teacherInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TeacherInfoDlg.set = function(key, val) {
    this.teacherInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

TeacherInfoDlg.download = function(){
	window.location.href=Feng.ctxPath + "/school/teacher/downloadModel";
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TeacherInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TeacherInfoDlg.close = function() {
    parent.layer.close(window.parent.Teacher.layerIndex);
}

/**
 * 收集数据
 */
TeacherInfoDlg.collectData = function() {
    this.set('id').set('cardId').set('teachernum').set('teachername').set('department').set('duty').set('sex').set('idType');
}

/**
 * 提交添加
 */
TeacherInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();
    
    if(!this.validate()){
    	return;
    }
    
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/school/teacher/add", function(data){
        Feng.success("添加成功!");
        window.parent.Teacher.table.refresh();
        TeacherInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.teacherInfoData);
    ajax.start();
}

/**
 * 提交批量添加
 */
TeacherInfoDlg.addBatchSubmit = function() {
	//如果是IE
	if(window.ActiveXObject){
		
		var ajax_option={
				url: Feng.ctxPath + "/school/teacher/upload",
				dataType:"application/json",
				success:function(data){
			    	Feng.success(data.responseText.message | data.message);
			        $('#ensure').removeAttr("disabled");
			        layer.closeAll("loading");
			    },
			    error:function(data){
			        $('#ensure').removeAttr("disabled");
			        layer.closeAll("loading");
			        data = JSON.parse(data.responseText);
			        layer.alert(data.message,{icon:2});
			    }
		};
			
		if(!$('#file').val()){
		    layer.alert("请先选择文件，再点击上传",{icon:0});
		    return false;
		}
		layer.load();
		$('#uploadInfo').ajaxSubmit(ajax_option);
		$('#ensure').attr('disabled',true);//将按钮不可用
		
		/*// Get form
	    var form = $('#uploadInfo')[0];
	 
	    var data = new FormData(form);
	 
	    data.append("file", "This is some extra data, testing");
	 
	    $("#ensure").prop("disabled", true);
	 
	    $.ajax({
	        type: "POST",
	        enctype: 'multipart/form-data',
	        url: Feng.ctxPath + "/school/teacher/upload",
	        data: data,
	        processData: false, //prevent jQuery from automatically transforming the data into a query string
	        contentType: false,
	        cache: false,
	        timeout: 600000,
	        success: function (data) {
	 
	        	Feng.success(data);
	            console.log("SUCCESS : ", data);
	            $("#ensure").prop("disabled", false);
	 
	        },
	        error: function (e) {
	 
	        	Feng.success(e.responseText);
	            console.log("ERROR : ", e);
	            $("#ensure").prop("disabled", false);
	 
	        }
	    });*/
	}else{

		   $.ajaxFileUpload
	            (
	                {
	                    url: Feng.ctxPath + "/school/teacher/upload", //用于文件上传的服务器端请求地址
	                    secureuri: false, //是否需要安全协议，一般设置为false
	                    fileElementId: 'file', //文件上传域的ID
	                    dataType: 'json', //返回值类型 一般设置为json
	                    success: function (data)  //服务器成功响应处理函数
	                    {
	                    	if(data.code===200){
	                    		Feng.success(data.message);
	                    		window.parent.Teacher.table.refresh();
	                    	}else{
	                    		Feng.error(data.message);
	                    	}
	                    },
	                    error: function (data)//服务器响应失败处理函数
	                    {	
	                    	Feng.error(data);
	                    }
	                }
	            )

	}
}


/**
 * 提交修改
 */
TeacherInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/school/teacher/update", function(data){
        Feng.success("修改成功!");
        window.parent.Teacher.table.refresh();
        TeacherInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.teacherInfoData);
    ajax.start();
}

/**
 * 验证数据是否为空
 */
TeacherInfoDlg.validate = function () {
    $('#teacherInfoForm').data("bootstrapValidator").resetForm();
    $('#teacherInfoForm').bootstrapValidator('validate');
    return $("#teacherInfoForm").data('bootstrapValidator').isValid();
};

TeacherInfoDlg.initSelect = function(id,options){
	$.each(options,function(key,value,array){
		var option = $("<option>").val(value.id).text(value.name);
		$(id).append(option)
	})
}

$(function() {
	Feng.initValidator("teacherInfoForm", TeacherInfoDlg.validateFields);
	
	var  params = {};
	
	/*//初始化部门下拉
	var ajax = new $ax(Feng.ctxPath + "/school/teacher/department", function(data){
		TeacherInfoDlg.initSelect("#department",data);
    },function(data){
        Feng.error("获取部门信息失败!" + data.responseJSON.message + "!");
    });
	params['pid'] = 39;
	ajax.set(params);
    ajax.start();
	//初始化职务下拉
    var ajax = new $ax(Feng.ctxPath + "/school/teacher/duty", function(data){
    	TeacherInfoDlg.initSelect("#duty",data);
    },function(data){
        Feng.error("获取职务失败!" + data.responseJSON.message + "!");
    });
    params['pid'] = 42;
    ajax.set(params);
    ajax.start();*/
	//初始化性别下拉
    var ajax = new $ax(Feng.ctxPath + "/school/teacher/duty", function(data){
    	TeacherInfoDlg.initSelect("#sex",data);
    },function(data){
        Feng.error("获取性别失败!" + data.responseJSON.message + "!");
    });
    params['pid'] = 29;
    ajax.set(params);
    ajax.start();
    
});
