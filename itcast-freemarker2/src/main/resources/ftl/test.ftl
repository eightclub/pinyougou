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
<#assign linkman="黑马"/>
${linkman}<br>
<#assign info={"mobile":"13400000000", "address":"吉山村"} />
${info.mobile} -- ${info.address}
<br><hr><br>
<#include "header.ftl" />

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