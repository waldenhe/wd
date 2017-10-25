/**
 * 食堂信息管理初始化
 */
var Canteen = {
    id: "CanteenTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Canteen.initColumn = function () {
	return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '食堂名称', field: 'name', align: 'center', valign: 'middle'},
        {title: '食堂MAC地址', field: 'macaddr', align: 'center', valign: 'middle'},
        {title: '食堂地址', field: 'addr', align: 'center', valign: 'middle'},
        {title: '创建时间', field: 'createtime',align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
Canteen.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Canteen.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加食堂信息
 */
Canteen.openAddCanteen = function () {
    var index = layer.open({
        type: 2,
        title: '添加食堂信息',
        area: ['800px', '520px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/school/canteen/canteen_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看食堂信息详情
 */
Canteen.openCanteenDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '食堂信息详情',
            area: ['800px', '520px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/school/canteen/canteen_update/' + Canteen.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除食堂信息
 */
Canteen.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/school/canteen/delete", function (data) {
            Feng.success("删除成功!");
            Canteen.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("canteenId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询食堂信息列表
 */
Canteen.search = function () {
    var queryData = {};
    queryData['name'] = $("#name").val();
    queryData['mac'] = $("#mac").val();
    Canteen.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Canteen.initColumn();
    var table = new BSTable(Canteen.id, "/school/canteen/list", defaultColunms);
    table.setPaginationType("server");
    Canteen.table = table.init();
});
