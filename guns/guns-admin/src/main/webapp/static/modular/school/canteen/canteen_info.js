/**
 * 初始化食堂信息详情对话框
 */
var CanteenInfoDlg = {
    canteenInfoData : {}
};

/**
 * 清除数据
 */
CanteenInfoDlg.clearData = function() {
    this.canteenInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CanteenInfoDlg.set = function(key, val) {
    this.canteenInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CanteenInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CanteenInfoDlg.close = function() {
    parent.layer.close(window.parent.Canteen.layerIndex);
}

/**
 * 收集数据
 */
CanteenInfoDlg.collectData = function() {
    this.set('id').set('name').set('macaddr').set('createtime').set('addr');
}

/**
 * 提交添加
 */
CanteenInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/school/canteen/add", function(data){
        Feng.success("添加成功!");
        window.parent.Canteen.table.refresh();
        CanteenInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.canteenInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
CanteenInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/school/canteen/update", function(data){
        Feng.success("修改成功!");
        window.parent.Canteen.table.refresh();
        CanteenInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.canteenInfoData);
    ajax.start();
}

$(function() {

});
