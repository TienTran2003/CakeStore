app.controller('order-ctrl', function ($scope, $http) {

    // Initialization function
    $scope.initialize = function () {
        $scope.loadOrders();
        $scope.loadOrderss();
        $scope.reset();
        $scope.items = [];
    }

    // Load orders
    $scope.loadOrders = function () {
        $http.get("/rest/orders").then(function (resp) {
            $scope.orders = resp.data;
        });

    }

    $scope.loadOrderss = function () {
        $http.get("/rest/orders").then(function (resp) {
            $scope.orders = resp.data;
        });
        $scope.edit = function(orderId){
            $scope.form = angular.copy(orderId);
            $(".nav-tabs a:eq(0)").tab("show");
            $scope.loadOrderDetailsByOrderId(orderId);
            $scope.loadOrderByOrderId(orderId);
        }
    }

    $scope.loadOrderByOrderId = function (orderId) {
        $http.get(`/rest/orders/${orderId}`).then(function (resp) {
            $scope.order = resp.data;
        });
    }

    $scope.loadOrderDetailsByOrderId = function (orderId) {
        $http.get(`/rest/order_detail/${orderId}`).then(function (resp) {
            $scope.orderDetails = resp.data;
        });
    }

    // Call this function to load order details for a specific order
    $scope.loadOrderDetailsForOrder = function (orderId) {
        $scope.loadOrderDetailsByOrderId(orderId);
        // Additional logic if needed
    }

    // Reset form
    $scope.reset = function () {
        $scope.form = {
            // Define your order properties here
        };
    }

    // Edit order
    $scope.edit = function (order) {
        $scope.form = angular.copy(order);
        // Additional logic for editing orders
    }


    // Update order
    $scope.update = function() {
        var item = angular.copy($scope.order); // Use $scope.order instead of $scope.form
        $http.put(`/rest/orders/${item.id}`, item).then(resp => {
            var index = $scope.items.findIndex(p => p.id == item.id);
            $scope.items[index] = item;
            alert("Cập nhật status thành công!");
        })
            .catch(error => {
                alert("Lỗi cập nhật!");
                console.log("Error", error);
            });
    }

    // Delete order
    $scope.delete = function (order) {
        if (confirm("Bạn muốn xóa đơn hàng này?")) {
            $http.delete(`/rest/orders/${order.id}`).then(function (resp) {
                // Handle success response
                var index = $scope.orders.findIndex(function (o) { return o.id == order.id; });
                $scope.orders.splice(index, 1);
                $scope.reset();
                alert("Xóa đơn hàng thành công!");
            }).catch(function (error) {
                // Handle error response
                alert("Lỗi xóa đơn hàng!");
                console.log("Error", error);
            });
        }
    }

    // Call the initialization function
    $scope.initialize();

    $scope.pager = {
        page: 0,
        size: 10,
        get orders() {
            if (this.page < 0) {
                this.last();
            }
            if (this.page >= this.count) {
                this.first();
            }
            var start = this.page * this.size;
            return $scope.orders.slice(start, start + this.size);
        },
        get count() {
            return Math.ceil(1.0 * $scope.orders.length / this.size);
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