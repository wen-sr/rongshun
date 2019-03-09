$(function () {
})

function go() {
    var skuName = $.trim($("#skuName").val());
    window.open('inventoryDetail.html?skuName=' + skuName ,'_self');
}

function getSku() {
    tools.request({
        url: tools.getServerUrl("/sku/skuTips"),
        method: "POST",
        success: function (data, msg) {
            var skuNameArr = [];
            for(var i in data){
                skuNameArr.push(data[i].name);
            }
            $("#skuName").picker({
                title: "请选择配件",
                inputReadOnly: false,
                cols: [
                    {
                        textAlign: 'center',
                        values: skuNameArr
                    }
                ],
                onChange: function(p, v, dv) {
                    // getDetail();
                },
                onClose: function(p, v, d) {
                    // getDetail();
                }
            });
        }
    })
}
