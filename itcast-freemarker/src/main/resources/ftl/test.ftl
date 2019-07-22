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