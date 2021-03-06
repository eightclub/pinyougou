var app = new Vue({
    el: "#app",
    data: {
        //用户名
        username: "",
        //购物车列表
        cartList: [],
        //总价格和总数
        totalValue: {"totalNum": 0, "totalMoney": 0},
        //地址列表
        addressList: [],
        //当前选中的地址
        selectedAddress: {},
        //要提交的订单对象;默认支付方式：微信付款
        order: {"paymentType": "1"}
    },
    methods: {
        //提交订单
        submitOrder: function () {
            //收件人地址
            this.order.receiverAreaName = this.selectedAddress.address;
            this.order.receiverMobile = this.selectedAddress.mobile;
            this.order.receiver = this.selectedAddress.contact;

            axios.post("order/add.do", this.order).then(function (response) {
                if (response.data.success) {
                    if ("1" == app.order.paymentType) {
                        //微信付款 跳转到支付页面
                        location.href = "pay.html?outTradeNo=" + response.data.message;
                    } else {
                        //货到付款 跳转到支付成功页面
                        location.href = "paysuccess.html";
                    }
                } else {
                    alert(response.data.message);
                }
            });
        },
        //设置当前选择的地址
        selectAddress: function (address) {
            this.selectedAddress = address;
        },
        //查询用户收件人地址列表
        findAddressList: function () {
            axios.get("address/findAddressList.do").then(function (response) {
                app.addressList = response.data;

                //处理默认选中的地址；也就是默认值为1的地址
                for (var i = 0; i < response.data.length; i++) {
                    const address = response.data[i];
                    if ("1" == address.isDefault) {
                        app.selectedAddress = address;
                        break;
                    }

                }
            });
        },
        //查询购物车列表
        findCartList: function () {
            axios.get("cart/findCartList.do").then(function (response) {
                app.cartList = response.data;

                //计算总价格和总数
                app.totalValue = app.sumTotalValue(response.data);
            });
        },
        //计算总价格和总数
        sumTotalValue: function (cartList) {
            var totalValue = {"totalNum": 0, "totalMoney": 0};
            for (var i = 0; i < cartList.length; i++) {
                var cart = cartList[i];
                for (var j = 0; j < cart.orderItemList.length; j++) {
                    var orderItem = cart.orderItemList[j];
                    totalValue.totalNum += orderItem.num;
                    totalValue.totalMoney += orderItem.totalFee;
                }
            }
            return totalValue;
        },
        //查询用户名的方法
        getUsername: function () {
            axios.get("cart/getUsername.do").then(function (response) {
                app.username = response.data.username;
            });

        }
    },
    created() {
        //查询用户名
        this.getUsername();
        //查询购物车列表
        this.findCartList();
        //查询用户收件人地址列表
        this.findAddressList();
    }
});