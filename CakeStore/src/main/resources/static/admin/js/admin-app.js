app = angular.module("app", ["ngRoute"]);

app.run(function($http, $rootScope){
    $http.get(`/rest/security/authentication`).then(resp => {
        if(resp.data){
            $auth = $rootScope.$auth = resp.data;
            $http.defaults.headers.common["Authorization"] = $auth.token;
        }
    });
})

app.config(function ($routeProvider) {
    $routeProvider
        .when("/product", {
            templateUrl: "/admin/product/index.html",
            controller: "product-ctrl"
        })
        .when("/authorize", {
            templateUrl: "/admin/authority/index.html",
            controller: "authority-ctrl"
        })
        .when("/unauthorized", {
            templateUrl: "/admin/authority/unauthorized.html",
            controller: "authority-ctrl"
        })
        .when('/order', {
            templateUrl: "/admin/order/index.html",
            controller: "order-ctrl"
        })
        .when('/account', {
            templateUrl: "/admin/account/index.html",
            controller: "account-ctrl"
        })
        .when('/report', {
            templateUrl: "/admin/report/index.html",
            controller: "report-ctrl"
        })
        .otherwise({
            template: "<h1 class='text-center'>Barykey Store Administration</h1>"
        });
});
