//定义处理器
app.controller("brandController", function ($scope,$controller,brandService) {

    //继承处理器
    $controller("baseController", {$scope:$scope});

    $scope.findAll = function () {
        //发送请求到后台获取数据
        brandService.findAll().success(function (response) {
            //将返回结果设置到一个上下文变量中
            $scope.list = response;
        }).error(function () {
            alert("加载数据失败");
        });

    };

    //根据分页信息查询数据
    $scope.findPage = function (pageNo, rows) {
        brandService.findPage(pageNo,rows).success(function (response) {
            //response是分页对象（列表，总记录数）
            $scope.list = response.rows;
            //给分页组件重新设置最新的总记录数
            $scope.paginationConf.totalItems = response.total;
        });

    };


    //修改后保存
    $scope.save = function () {
        var obj;
        if($scope.entity.id != null) {
            //修改
            obj = brandService.update($scope.entity);
        }else {
            //添加
            obj = brandService.add($scope.entity);
        }

        obj.success(function (response) {
            if(response.success) {
                //如果操作成功,则刷新列表
                $scope.reloadList();
            }else {
                alert(response.message);
            }
        });
    };

    //根据主键查询
    $scope.findOne = function (id) {
        brandService.findOne(id).success(function (response) {
            $scope.entity = response;
        });
    };

    // 批量删除
    $scope.delete = function () {
        if ($scope.selectedIds.length < 1) {
            alert(" 请选择要删除的记录");
            return;
        }
        if(confirm(" 确定要删除选中的记录吗？")){
            brandService.delete($scope.selectedIds).success(function (response) {
                if(response.success){
                    $scope.reloadList();
                    $scope.selectedIds = [];
                } else {
                    alert(response.message);
                }
            });
        }
    };

    // 搜索
    // 定一个空的搜索条件对象
    $scope.searchEntity = {};
    $scope.search = function (page, rows) {
        brandService.search(page,rows,$scope.searchEntity).success(function (response) {
            $scope.list = response.rows;
            $scope.paginationConf.totalItems = response.total;
        });
    };
});