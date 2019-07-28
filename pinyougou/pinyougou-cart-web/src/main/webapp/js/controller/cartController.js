var app = new Vue({
    el: "#app",
    data: {
        //用户名
        username: "",
        //购物车列表
        cartList: []
    },
    methods: {
        //查询购物车列表
        findCartList: function () {
            axios.get("cart/findCartList.do").then(function (response) {
                app.cartList = response.data;
            });
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
    }
});