app.controller("account-ctrl", function($scope, $http) {

    $scope.initialize = function () {
        $scope.loadOrders();
    }

    $scope.initialize = function() {
        $http.get("/rest/accounts/list").then(function(resp) {
            $scope.items = resp.data;
        });
    };

    $scope.delete = function(item) {
        if (confirm("Bạn muốn xóa tài khoản này?")) {
            $http.delete(`/rest/accounts/${item.username}`).then(function(resp) {
                var index = $scope.items.findIndex(function(p) {
                    return p.username == item.username;
                });
                $scope.items.splice(index, 1);
                alert("Xóa tài khoản thành công!");
            }).catch(function(error) {
                alert("Lỗi xóa tài khoản!");
                console.log("Error", error);
            });
        }
    };

    $scope.initialize();

    $scope.pager = {
        page: 0,
        size: 10,
        get items() {
            if (this.page < 0) {
                this.last();
            }
            if (this.page >= this.count) {
                this.first();
            }
            var start = this.page * this.size;
            return $scope.items.slice(start, start + this.size);
        },
        get count() {
            return Math.ceil(1.0 * $scope.items.length / this.size);
        },
        first() {
            this.page = 0;
        },
        last() {
            this.page = this.count - 1;
        },
        next() {
            this.page++;
        },
        prev() {
            this.page--;
        }
    };
});