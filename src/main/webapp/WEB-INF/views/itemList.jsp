<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Smart Fridge</title>
		<jsp:directive.include file="/WEB-INF/views/headScript.jsp" />
		<jsp:directive.include file="/WEB-INF/views/navigation.jsp" />
	</head>
	<body>
		<div id="wrapper">
	        <div id="page-wrapper">
	            <div class="row">
	                <div class="col-lg-12">
	                    <h1 class="page-header">Smart Fridge</h1>
	                </div>
	                <!-- /.col-lg-12 -->
	            </div>
	            <!-- /.row -->
	            <div class="row">
	                <div class="col-lg-12">
	                    <div class="panel panel-default">
	                        <div class="panel-heading">
	                            냉장고 아이템 목록
	                        </div>
	                        <!-- /.panel-heading -->
	                        <div class="panel-body">
	                            <table width="100%" class="table table-striped table-bordered table-hover" id="dataTables-example">
	                                <thead>
	                                    <tr>
	                                        <th>상품명</th>
	                                        <th>개수</th>
	                                        <th>유통기한</th>
	                                        <th>남은날짜</th>
	                                        <th>비고</th>
	                                        <th></th>
	                                    </tr>
	                                </thead>
	                                <tbody>
	                                	<c:forEach var="itemList" items="${dataList }">
		                                    <tr class="odd gradeX" >
		                                        <td onClick="location.href='itemDetail?id=${itemList.id}'">${itemList.itemName }</td>
		                                        <td onClick="location.href='itemDetail?id=${itemList.id}'">${itemList.count }</td>
		                                        <td onClick="location.href='itemDetail?id=${itemList.id}'">${itemList.expirationDate }</td>
		                                        <td onClick="location.href='itemDetail?id=${itemList.id}'">0</td>
		                                        <td onClick="location.href='itemDetail?id=${itemList.id}'">${itemList.remark }</td>
		                                        <td><button type="button" class="btn btn-danger" onClick="location.href='deleteItem?id=${itemList.id}'">삭제</button></td>
		                                    </tr>
	                                    </c:forEach>
	                                </tbody>
	                            </table>
	                        </div>
	                        <!-- /.panel-body -->
	                    </div>
	                    <!-- /.panel -->
	                </div>
	                <!-- /.col-lg-12 -->
	            </div>
	        </div>
	        <!-- /#page-wrapper -->

	    </div>
	    <!-- /#wrapper -->
	
	
	    <!-- jQuery -->
	    <script src="../vendor/jquery-3.3.1/jquery-3.3.1.js"></script>
	
	    <!-- Bootstrap Core JavaScript -->
	    <!-- <script src="../vendor/bootstrap-3.3.2-dist/js/bootstrap.min.js"></script>  -->
	
	    <!-- Metis Menu Plugin JavaScript -->
	    <script src="../vendor/metisMenu/metisMenu.min.js"></script>
	
	    <!-- Custom Theme JavaScript -->
	    <script src="../js/sb-admin-2.js"></script>
		
	</body>
</html>