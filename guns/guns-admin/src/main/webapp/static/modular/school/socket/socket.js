/**
 * 远程数据库同步管理初始化
 */
var Socket = {
    id: "SocketTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Socket.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Socket.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Socket.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加远程数据库同步
 */
Socket.openAddSocket = function () {
    var index = layer.open({
        type: 2,
        title: '添加远程数据库同步',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/socket/socket_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看远程数据库同步详情
 */
Socket.openSocketDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '远程数据库同步详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/socket/socket_update/' + Socket.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除远程数据库同步
 */
Socket.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/socket/delete", function (data) {
            Feng.success("删除成功!");
            Socket.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("socketId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询远程数据库同步列表
 */
Socket.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Socket.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Socket.initColumn();
    var table = new BSTable(Socket.id, "/socket/list", defaultColunms);
    table.setPaginationType("client");
    Socket.table = table.init();
});
