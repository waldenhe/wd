/**
 * 初始化远程数据库同步详情对话框
 */
var SocketInfoDlg = {
    socketInfoData : {}
};

/**
 * 清除数据
 */
SocketInfoDlg.clearData = function() {
    this.socketInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SocketInfoDlg.set = function(key, val) {
    this.socketInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SocketInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
SocketInfoDlg.close = function() {
    parent.layer.close(window.parent.Socket.layerIndex);
}

/**
 * 收集数据
 */
SocketInfoDlg.collectData = function() {
    this.set('id');
}

/**
 * 提交添加
 */
SocketInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/socket/add", function(data){
        Feng.success("添加成功!");
        window.parent.Socket.table.refresh();
        SocketInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.socketInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
SocketInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/socket/update", function(data){
        Feng.success("修改成功!");
        window.parent.Socket.table.refresh();
        SocketInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.socketInfoData);
    ajax.start();
}

$(function() {

});
