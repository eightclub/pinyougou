<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Freemarker测试</title>
</head>
<body>
<h2>${name}---${message}</h2>
<br><hr><br>
<#-- 注释；在freemarker中注释的内容是不会出现在输出的文件中的 -->
assign定义变量<br>
<#assign linkman="黑马"/>
${linkman}<br>
<#assign info={"mobile":"13400000000", "address":"吉山村"} />
${info.mobile} -- ${info.address}
<br><hr><br>
include引入命令<br>
<#include "header.ftl" />

<br><hr><br>
条件控制语句if<br>
<#assign bool=true/>
<#if bool>
    bool是true
<#else>
    bool是false

</#if>
<br><hr><br>
list循环控制语句<br>
<#list goodsList as goods>
    ${goods_index} -- ${goods.name} -- ${goods.price}<br>
</#list>
<br><hr><br>
?size可以获取集合的大小<br>
goodsList的集合大小为：${goodsList?size}<br>
<hr>
?eval将json字符串转换为对象<br>
<#assign objStr='{"name":"黑马","age":13}'/>
<#assign obj=objStr?eval />

${obj.name} --- ${obj.age}<br>

<hr>
.now表示当前日期: ${.now}<br>
当前的日期为：${today?date}<br>
当前的时间为：${today?time}<br>
当前的日期时间为：${today?datetime}<br>
格式化显示日期：${today?string("yyyy年MM月dd日 HH:mm:ss SSSS")}

<hr>
如果是长整型的数据直接显示：${number}；可以使用?c将数值转换为字符串：${number?c}

<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
<br>
</body>
</html>