var app = new Vue({
    el: "#app",
    data: {
        //用户名
        username: "",
        //购物车列表
        cartList: [],
        //总价格和总数
        totalValue:{"totalNum":0, "totalMoney":0},
        //地址列表
        addressList:[]
    },
    methods: {
        //查询用户收件人地址列表
        findAddressList: function(){
            axios.get("address/findAddressList.do").then(function (response) {
                app.addressList = response.data;
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
        sumTotalValue: function(cartList){
            var totalValue = {"totalNum":0, "totalMoney":0};
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
        //this.findCartList();
        //查询用户收件人地址列表
        this.findAddressList();
    }
});