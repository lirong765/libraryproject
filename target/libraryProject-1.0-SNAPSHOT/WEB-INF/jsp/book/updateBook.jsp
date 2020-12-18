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
    <title>修改图书</title>
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
    <input type="hidden" name="id" value="${bookInfo.id}">
    <div class="layui-form-item">
        <label class="layui-form-label required">图书名称</label>
        <div class="layui-input-block">
            <input type="text" name="name" lay-verify="required" lay-reqtext="图书名称不能为空" placeholder="请输入图书名称" value="${bookInfo.name}" class="layui-input">
            <tip>填写自己图书名称。</tip>
        </div>
    </div>
    <div class="layui-form-item">
        <label class="layui-form-label required">图书编号</label>
        <div class="layui-input-block">
            <input type="text" name="isbn" lay-verify="required" lay-reqtext="图书编号不能为空" placeholder="请输入图书编号" value="${bookInfo.isbn}" class="layui-input">
            <tip>填写自己图书编号。</tip>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label required">图书类型</label>
        <div class="layui-input-block">
            <select name="typeId" id="typeId" lay-verify="required">
                <option value="${bookInfo.typeId}"></option>
            </select>
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label required">图书作者</label>
        <div class="layui-input-block">
            <input type="text" name="author" lay-verify="required" value="${bookInfo.author}" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label required">图书出版社</label>
        <div class="layui-input-block">
            <input type="text" name="publish" lay-verify="required" value="${bookInfo.publish}" class="layui-input">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label required">图书语言</label>
        <div class="layui-input-block">
            <input type="text" name="language" class="layui-input" value="${bookInfo.language}">
        </div>
    </div>

    <div class="layui-form-item">
        <label class="layui-form-label required">图书价格</label>
        <div class="layui-input-block">
            <input type="number" name="price" class="layui-input" value="${bookInfo.price}">
        </div>
    </div>

    <div class="layui-form-item">
            <label class="layui-form-label">出版日期</label>
            <div class="layui-input-inline" >
                <input type="text" class="layui-input" id="date" value="<fmt:formatDate value="${bookInfo.pubDate}" pattern="yyyy-MM-dd"></fmt:formatDate> "placeholder="yyyy-MM-dd">
            </div>
    </div>

    <div class="layui-form-item layui-form-text">
        <label class="layui-form-label">图书介绍</label>
        <div class="layui-input-block">
            <textarea name="introduction" class="layui-textarea" placeholder="请输入图书介绍">${bookInfo.introduction}</textarea>
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
            elem: '#date',
            trigger: 'click'
        });

        //监听提交
        form.on('submit(saveBtn)', function (data) {
            var datas = data.field;     //form表单中的数据信息

            //向后台发送数据提交添加
            $.ajax({
                url: "updateBookInfoSubmit",
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

        //动态获取图书类型的数据
        $.get("findAllList", {}, function (data) {
            //获取图书类型的值
            var typeId=$("#typeId")[0].value;
            var list = data;
            var select = document.getElementById("typeId");
            if(list!=null||list.size()>0){
                for (var x in list) {
                    var option=document.createElement("option");
                    option.setAttribute("value", list[x].id);
                    option.innerText = list[x].name;
                    select.appendChild(option);
                    //如果类型和循环的类型id一致，选中
                    if(list[x].id == typeId){
                        option.setAttribute("selected", "selected");
                        layui.form.render('select');
                    }
                }
            }
            form.render('select');
        }, "json")
    });
</script>

</body>
</html>