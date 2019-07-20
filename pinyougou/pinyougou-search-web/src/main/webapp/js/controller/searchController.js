var app = new Vue({
    el:"#app",
    data:{
        //查询条件对象
        searchMap:{"keywords":"","category":"", "brand":"","spec":{}, "price":"", "pageNo":1, "pageSize":20},
        //返回结果对象
        resultMap:{"itemList":[]},
        //分页导航条中的页号数组
        pageNoList:[]
    },
    methods: {
        //移除过滤条件
        removeSearchItem: function(key){
            if("brand"==key || "category"==key || "price"==key){
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
            if("brand"==key || "category"==key || "price"==key){
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

                //构建分页导航条
                app.buildPagination();
            });

        },
        //构建分页导航条
        buildPagination: function () {
            this.pageNoList = [];
            //起始页号
            var startPageNo = 1;
            //结束页号
            var endPageNo = this.resultMap.totalPages;
            //要显示的总页号数
            var showPageNoTotal = 5;

            //总页数大于 要显示的总页号数
            if (this.resultMap.totalPages > showPageNoTotal) {
                //当前页左右间隔
                var interval = Math.floor(showPageNoTotal/2);
                startPageNo = this.searchMap.pageNo - interval;
                endPageNo = this.searchMap.pageNo + interval;

                if(startPageNo > 0){
                    if (endPageNo > this.resultMap.totalPages) {
                        endPageNo = this.resultMap.totalPages;
                        startPageNo = endPageNo - showPageNoTotal + 1;
                    }
                } else {
                    //起始页必须要从1开始
                    startPageNo = 1;
                    endPageNo = showPageNoTotal;
                }
            }

            for (var i = startPageNo; i <= endPageNo; i++) {
                this.pageNoList.push(i);
            }

        }
    },
    created(){
        //查询
        this.search();
    }
});