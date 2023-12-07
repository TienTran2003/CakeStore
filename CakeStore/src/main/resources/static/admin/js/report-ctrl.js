app.controller('report-ctrl', function ($scope, $http) {

    // Initialization function
    $scope.initialize = function () {
        $scope.loadOrders();
        $scope.items = [];
    }

    $scope.loadOrders = function () {
        $http.get("/rest/orders/total").then(function (resp) {
            $scope.total = resp.data;
        });
        $http.get("/rest/products/total").then(function (resp) {
            $scope.totalProduct = resp.data;
        });
        $http.get("/rest/order_detail/total-revenue").then(function (resp) {
            $scope.totalPrice = resp.data;
        });
        $http.get("/rest/accounts/total").then(function (resp) {
            $scope.totalAccount = resp.data;
        });
    }
    $scope.initialize();
});