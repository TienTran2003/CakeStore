
app.controller("cart-ctrl", function ($scope, $http) {

    // quản lý giỏ hàng
    var $cart = $scope.cart = {
        items: [],
        add(id) { // thêm sản phẩm vào giỏ hàng
            var item = this.items.find(item => item.id == id);
            if (item) {
                item.qty++;
                this.saveToLocalStorage();
            } else {
                $http.get(`/rest/products/${id}`).then(resp => {
                    resp.data.qty = 1;
                    this.items.push(resp.data);
                    this.saveToLocalStorage();
                })
            }
        },
        remove(id) { // xóa sản phẩm khỏi giỏ hàng
            var index = this.items.findIndex(item => item.id === id);
            this.items.splice(index, 1);
            this.saveToLocalStorage();
        },
        clear() { // Xóa sạch các mặt hàng trong giỏ
            this.items = []
            this.saveToLocalStorage();
        },
        clearById(id) {
            // Tìm mục có ID được chỉ định
            const item = this.items.find(item => item.id === id);

            // Nếu mục được tìm thấy, hãy xóa nó khỏi giỏ hàng
            if (item) {
                this.items = this.items.filter(item => item.id !== id);
            }

            // Lưu giỏ hàng vào bộ nhớ cục bộ
            this.saveToLocalStorage();
        },
        amt_of(item) { // tính thành tiền của 1 sản phẩm
            return item.price * item.qty;
        },
        get count() { // tính tổng số lượng các mặt hàng trong giỏ
            return this.items
                .map(item => item.qty)
                .reduce((total, qty) => total += qty, 0);
        },
        get amount() { // tổng thành tiền các mặt hàng trong giỏ
            return this.items
                .map(item => this.amt_of(item))
                .reduce((total, amt) => total += amt, 0);
        },
        saveToLocalStorage() { // lưu giỏ hàng vào local storage
            var json = JSON.stringify(angular.copy(this.items));
            localStorage.setItem("cart", json);
        },
        loadFromLocalStorage() { // đọc giỏ hàng từ local storage
            var json = localStorage.getItem("cart");
            this.items = json ? JSON.parse(json) : [];
        },
    }
    $cart.loadFromLocalStorage();

    // Đặt hàng
    $scope.order = {
        get account() {
            return {username: $auth.user.username}
        },
        createDate: new Date(),
        address: "",
        get orderDetails() {
            return $cart.items.map(item => {
                return {
                    product: {id: item.id},
                    price: item.price,
                    quantity: item.qty
                }
            });
        },
        purchase() {
            var order = angular.copy(this);
            // Thực hiện đặt hàng
            $http.post("/rest/orders", order).then(resp => {
				console.log("dat hang thanh cong")
                alert("Order successful");
                $cart.clear();
                location.href = "/order/detail/" + resp.data.id;
            }).catch(error => {
                alert("Fail order!!!")
                console.log(error)
            })
        }
    }
    $scope.delete = function (order) {
        if (confirm("Bạn muốn xóa đơn hàng này?")) {
            $http.delete(`/rest/orders/${order}`).then(function (resp) {
                // Handle success response
                var index = $scope.orders.findIndex(function (o) { return o.id == order.id; });
                $scope.orders.splice(index, 1);
                alert("Xóa đơn hàng thành công!");
            }).catch(function (error) {
                // Handle error response
                alert("Lỗi xóa đơn hàng!");
                console.log("Error", error);
            });
        }
    }

})