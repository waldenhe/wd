/**
 * 打卡记录管理初始化
 */
var Record = {
    id: "RecordTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Record.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '姓名', field: 'teachername', align: 'center', valign: 'middle'},
        {title: '身份证号', field: 'cardId', align: 'center', valign: 'middle'},
        {title: '食堂名称', field: 'name',align: 'center', valign: 'middle'},
        {title: '刷卡日期', field: 'createTime',align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Record.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Record.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加打卡记录
 */
Record.openAddRecord = function () {
    var index = layer.open({
        type: 2,
        title: '添加打卡记录',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/school/record/record_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看打卡记录详情
 */
Record.openRecordDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '打卡记录详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/school/record/record_update/' + Record.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除打卡记录
 */
Record.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/school/record/delete", function (data) {
            Feng.success("删除成功!");
            Record.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("recordId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询打卡记录列表
 */
Record.search = function () {
    var queryData = {};
    queryData['cardId'] = $("#cardId").val();
    queryData['mac'] = $("#mac").val();
    queryData['idType'] = $("#idType").val();
    queryData['startTime'] = $("#startTime").val();
    queryData['endTime'] = $("#endTime").val();
    Record.table.refresh({query: queryData});
};

Record.formParams = function () {
    var queryData = {};
    queryData['cardId'] = $("#cardId").val();
    queryData['mac'] = $("#mac").val();
    queryData['idType'] = $("#idType").val();
    queryData['startTime'] = $("#startTime").val();
    queryData['endTime'] = $("#endTime").val();
    return queryData;
};

//开始刷卡
Record.startRecord = function () {
    var index = layer.open({
    	  type: 2,
    	  content: '/school/record/record_update/1',
    	  area: ['320px', '195px'],
    	  maxmin: true,
    	  title:false
    	});
    layer.full(index);
    this.layerIndex = index;
};

Record.initSelect = function(id,options){
	$.each(options,function(key,value,array){
		var option = $("<option>").val(value.macaddr).text(value.name);
		$(id).append(option)
	})
}


$(function () {
    var defaultColunms = Record.initColumn();
    var table = new BSTable(Record.id, "/school/record/list", defaultColunms);
    table.setPaginationType("server");
    table.setQueryParams(Record.formParams());
    Record.table = table.init();
    
    //初始食堂下拉
    var ajax = new $ax(Feng.ctxPath + "/school/canteen/listAll", function(data){
    	Record.initSelect("#mac",data);
    },function(data){
        Feng.error("获取食堂信息失败!" + data.responseJSON.message + "!");
    });
    ajax.start();
    
});
