<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--在JSP文件开头文件声明标签库--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page isELIgnored="false" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <meta charset="utf-8">
    <title>修改读者</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="<%= basePath%>/lib/layui-v2.5.5/css/layui.css" media="all">
    <link rel="stylesheet" href="<%= basePath%>/css/public.css" media="all">
    <style>
        body {
            background-color: #ffffff;
        }
    </style>
</head>
<body>
<div class="layui-form layuimini-form">
    <input type="hidden" name="id" value="${info.id}">
    <div class="layui-form-item">
        <label class="layui-form-label required">卡号</label>
        <div class="layui-input-block">
            <input type="text" name="cardnumber" lay-verify="required" value="${info.cardnumber}" class="layui-input">
            <tip>填写自己卡号，建议使用身份证号码。</tip>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label required">用户名</label>
        <div class="layui-input-block">
            <input type="text" name="username" lay-verify="required" value="${info.username}" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label required">真实姓名</label>
        <div class="layui-input-block">
            <input type="text" name="name" lay-verify="required" value="${info.name}" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label required">性别</label>
        <div class="layui-input-block">
            <input type="radio" name="sex" value="男" title="男" ${"男" eq info.sex ?"checked='checked'":''}>
            <input type="radio" name="sex" value="女" title="女" ${"女" eq info.sex ?"checked='checked'":''}>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label required">出生时间</label>
        <div class="layui-input-block">
            <input type="text" name="birthday" id="birthday" autocomplete="off" class="layui-input" value="<fmt:formatDate value='${info.birthday}' pattern='yyyy-MM-dd'/>">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label required">联系方式</label>
        <div class="layui-input-block">
            <input type="text" name="tel" lay-verify="required" value="${info.tel}" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label required">邮箱地址</label>
        <div class="layui-input-block">
            <input type="text" name="email" lay-verify="required" value="${info.email}" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label required">最多借书</label>
        <div class="layui-input-block">
            <input type="number" name="number" lay-verify="required" value="${info.number}" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <div class="layui-input-block">
            <button class="layui-btn layui-btn-normal" lay-submit lay-filter="saveBtn">确认修改</button>
        </div>
    </div>
</div>
<script src="<%= basePath%>/lib/layui-v2.5.5/layui.js" charset="utf-8"></script>
<script>
    layui.use(['form','laydate'], function () {
        var form = layui.form,
            layer = layui.layer,
            laydate = layui.laydate;
            $ = layui.$;

        //时间渲染
        laydate.render({
            elem: '#birthday',
            trigger: 'click'
        });

        //监听提交
        form.on('submit(saveBtn)', function (data) {
            var datas = data.field;     //form表单中的数据信息

            //向后台发送数据提交添加
            $.ajax({
                url: "updateReaderInfoSubmit",
                type:"POST",     //get:向浏览器请求并响应数据，请求长度有限制，可变大小约为 2000 个字符；
                // Post:向浏览器请求并响应数据，请求长度有限制，最多允许 8 Mb 的可变大小，所以使用post可将前端大量文本框的数据返回给后端
                contentType: 'application/json',
                data:JSON.stringify(datas),     //规定要发送的数据
                success: function (result) {       //当请求成功时运行的函数。
                    if(result.code==0){
                        layer.msg('修改成功!', {
                            icon: 6,
                            time: 1000
                        }, function () {
                            parent.window.location.reload();    //重载，刷新父页面

                            var iframeIndex = parent.layer.getFrameIndex(window.name);  //获取窗口索引
                            parent.layer.close(iframeIndex);    //执行父页面的事件，关闭弹出层
                        })
                    }else {
                        layer.msg('类型添加失败!');
                    }
                }
            })

            return false;
        });
    });
</script>

</body>
</html>