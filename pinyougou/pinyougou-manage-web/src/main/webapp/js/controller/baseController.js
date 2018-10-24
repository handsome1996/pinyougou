//继承处理器，其它的处理器中公共使用到的配置、变量、方法都可以放置在这里，然后
//让那些处理器继承该处理器即可
app.controller("baseController",function ($scope) {
    // 初始化分页参数
    $scope.paginationConf = {
        currentPage:1,// 当前页号
        totalItems:10,// 总记录数
        itemsPerPage:10,// 页大小
        perPageOptions:[10, 20, 30, 40, 50],// 可选择的每页大小
        onChange: function () {// 当上述的参数发生变化了后触发
            $scope.reloadList();
        }
    };

    $scope.reloadList = function () {
        //$scope.findPage($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
        $scope.search($scope.paginationConf.currentPage, $scope.paginationConf.itemsPerPage);
    };

    // 定义一个放置选择了 id 的数组
    $scope.selectedIds = [];

    $scope.updateSelection = function ($event,id) {
        if ($event.target.checked) {
            $scope.selectedIds.push(id);
        } else {
            var index = $scope.selectedIds.indexOf(id);
            // 删除位置，删除个数
            $scope.selectedIds.splice(index, 1);
        }
    };

    // 将一个 json 数组格式字符串的某个 key 对应的值串起来显示，使用 , 分隔
    $scope.jsonToString = function (jsonStr, key) {
        var str = "";
        var jsonArray = JSON.parse(jsonStr);
        for(var i = 0; i < jsonArray.length; i++) {
            if(i > 0) {
                str += ",";
            }
            str += jsonArray[i][key];
        }
        return str;
    };
});