var app = new Vue({
    el:"#app",
    data:{
        //查询条件对象
        searchMap:{"keywords":"","category":"", "brand":"","spec":{}},
        //返回结果对象
        resultMap:{"itemList":[]}
    },
    methods: {
        //移除过滤条件
        removeSearchItem: function(key){
            if("brand"==key || "category"==key){
                this.searchMap[key] = "";
            } else {
                //规格
                //this.searchMap.spec[key] = value;
                //设置对象的属性值；参数1：对象，参数2：属性名，参数3：属性值
                this.$set(this.searchMap.spec, key, null);

                delete this.searchMap.spec[key];
            }

            this.search();
        },
        //添加过滤条件
        addSearchItem:function(key, value){
            if("brand"==key || "category"==key){
                this.searchMap[key] = value;
            } else {
                //规格
                //this.searchMap.spec[key] = value;
                //设置对象的属性值；参数1：对象，参数2：属性名，参数3：属性值
                this.$set(this.searchMap.spec, key, value);
            }

            this.search();
        },
        //查询
        search:function () {
            axios.post("itemSearch/search.do", this.searchMap).then(function (response) {
                app.resultMap = response.data;
            });

        }
    },
    created(){
        //查询
        this.search();
    }
});