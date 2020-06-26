<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查询商品列表</title>
<script type="text/javascript">
function deleteItems(){
	document.getElementById("listForm").submit();
}
</script>
</head>
<body>
	<form
		action="${pageContext.request.contextPath}/items/queryItems"
		method="post">
		查询条件：
		<table width="100%" border=1>
			<tr>
			  <td>
			           商品名称： <input type="text" name="items.name" />
				<input type="submit" value="查询" />
			  </td>
			</tr>
		</table>
	</form>
		商品列表：
		<input type="button" value="批量删除" onclick="deleteItems()"/>
		<input type="button" value="添加商品" onclick="javascript:location='${pageContext.request.contextPath}/pages/addItems.jsp'"/>
	   <form id="listForm" action="${pageContext.request.contextPath}/items/deleteBatch" method="post">
		<table width="100%" border=1>
			<tr>
				<td>商品名称</td>
				<td>商品价格</td>
				<td>生产日期</td>
				<td>商品描述</td>
				<td>图片</td>
			</tr>
			<c:forEach var="item" items="${itemsList}">
				<tr>
					<td>${item.name}</td>
					<td>${item.price}</td>
                    <td>
                        <fmt:formatDate value="${item.createtime }" pattern="yyyy-MM-dd"/>
                    </td>
					<td>${item.detail}</td>
					<td>
						<img src="pic/${item.pic}" width="40px" height="40px"/>
					</td>
				</tr>
			</c:forEach>
		</table>
     </form>
    <a href="${pageContext.request.contextPath}/queryItems?pageNum=1" >首页</a>
    <a href="${pageContext.request.contextPath}/queryItems?pageNum=${page.prePage}">上一页</a>
    <a href="${pageContext.request.contextPath}/queryItems?pageNum=${page.nextPage}">下一页</a>
    <a href="${pageContext.request.contextPath}/queryItems?pageNum=${page.pages}">尾页</a>
</body>

</html>