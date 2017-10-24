/**
 * 教师管理初始化
 */
var Teacher = {
    id: "TeacherTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Teacher.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '主键', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '教师姓名', field: 'teachername',align: 'center', valign: 'middle'},
        {title: '身份证号', field: 'cardId', align: 'center', valign: 'middle'},
        {title: '部门', field: 'department',align: 'center', valign: 'middle'},
        {title: '职务', field: 'duty', align: 'center', valign: 'middle'},
        {title: '性别', field: 'sex',align: 'center', valign: 'middle'},
        {title: '教工号', field: 'teachernum', align: 'center', valign: 'middle'},
        {title: '类型', field: 'other5',align: 'center', valign: 'middle'},
        {title: '可打卡次数', field: 'other6',align: 'center', valign: 'middle'},
        {title: '当日剩余当卡次数', field: 'other7',align: 'center', valign: 'middle'},
        {title: '累计打卡次数', field: 'other8',align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Teacher.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Teacher.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加教师
 */
Teacher.openAddTeacher = function () {
    var index = layer.open({
        type: 2,
        title: '添加教师',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/school/teacher/teacher_add'
    });
    this.layerIndex = index;
};

/**
 * 点击批量添加教师
 */
Teacher.openBatchAddTeacher = function () {
    var index = layer.open({
        type: 2,
        title: '批量导入教师信息',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/school/teacher/teacherUpload'
    });
    this.layerIndex = index;
};

/**
 * 打开查看教师详情
 */
Teacher.openTeacherDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '教师详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/school/teacher/teacher_update/' + Teacher.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除教师
 */
Teacher.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/school/teacher/delete", function (data) {
            Feng.success("删除成功!");
            Teacher.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("teacherId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询教师列表
 */
Teacher.search = function () {
    var queryData = {};
    queryData['cardId'] = $("#cardId").val();
    queryData['teachernum'] = $("#teachernum").val();
    queryData['teachername'] = $("#teachername").val();
    queryData['department'] = $("#department").val();
    queryData['duty'] = $("#duty").val();
    queryData['sex'] = $("#sex").val();
    queryData['idType'] = $("#idType").val();
    queryData['startTime'] = $("#startTime").val();
    queryData['endTime'] = $("#endTime").val();
    Teacher.table.refresh({query: queryData});
};

/**
 * 查询表单提交参数对象
 * @returns {{}}
 */
Teacher.formParams = function() {
    var queryData = {};
    queryData['cardId'] = $("#cardId").val();
    queryData['teachernum'] = $("#teachernum").val();
    queryData['teachername'] = $("#teachername").val();
    queryData['department'] = $("#department").val();
    queryData['duty'] = $("#duty").val();
    queryData['sex'] = $("#sex").val();
    queryData['idType'] = $("#idType").val();
    return queryData;
}

Teacher.initSelect = function(id,options){
	$.each(options,function(key,value,array){
		var option = $("<option>").val(value.id).text(value.name);
		$(id).append(option)
	})
}

$(function () {
    var defaultColunms = Teacher.initColumn();
    var table = new BSTable(Teacher.id, "/school/teacher/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(Teacher.formParams());
    Teacher.table = table.init();
    
	var  params = {};
	
	/*//初始化部门下拉
	var ajax = new $ax(Feng.ctxPath + "/school/teacher/department", function(data){
		Teacher.initSelect("#department",data);
    },function(data){
        Feng.error("获取部门信息失败!" + data.responseJSON.message + "!");
    });
	params['pid'] = 39;
	ajax.set(params);
    ajax.start();
	//初始化职务下拉
    var ajax = new $ax(Feng.ctxPath + "/school/teacher/duty", function(data){
    	Teacher.initSelect("#duty",data);
    },function(data){
        Feng.error("获取职务失败!" + data.responseJSON.message + "!");
    });
    params['pid'] = 42;
    ajax.set(params);
    ajax.start();*/
	//初始化性别下拉
    var ajax = new $ax(Feng.ctxPath + "/school/teacher/duty", function(data){
    	Teacher.initSelect("#sex",data);
    },function(data){
        Feng.error("获取性别失败!" + data.responseJSON.message + "!");
    });
    params['pid'] = 29;
    ajax.set(params);
    ajax.start();
});
