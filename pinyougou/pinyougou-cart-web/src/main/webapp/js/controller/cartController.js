var app = new Vue({
    el:"#app",
    data:{
        //用户名
        username:""
    },
    methods:{
        //查询用户名的方法
        getUsername: function () {
            axios.get("cart/getUsername.do").then(function (response) {
                app.username = response.data.username;
            });

        }
    },
    created(){
        //查询用户名
        this.getUsername();
    }
});