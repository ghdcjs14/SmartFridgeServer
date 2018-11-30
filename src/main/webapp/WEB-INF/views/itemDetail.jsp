<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
                    <h1 class="page-header">상세정보</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            ${data.itemName }
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-6">
                                    <form role="form" method="post" action="itemUpdate">
                                    	<input type='hidden' name='id' value='${data.id }'>
                                    	<div class="form-group">
                                            <c:choose>
	                                   			<c:when test="${data.imageLinkURL eq null}">
	                                   				<img style='width:100px; height:100px;' src="../resources/img/NoImageAvailable.png" alt="Product Image"
	                                   				style="object-fit:contain"/>
	                                   			</c:when>
	                                   			<c:otherwise>
	                                   				<img src='${data.imageLinkURL}' alt="Product Image"/>
	                                   			</c:otherwise>
	                                   		</c:choose>
                                        </div>
                                        <div class="form-group">
                                            <label>GS1 Code</label>
                                            <p class="form-control-static">${data.fullCode }</p>
                                        </div>
                                        <div class="form-group">
                                            <label>국가코드</label>
                                            <p class="form-control-static">${data.memberOrganization }</p>
                                        </div>
                                        <div class="form-group">
                                            <label>회사코드</label>
                                            <p class="form-control-static">${data.companyNumber }</p>
                                        </div>
                                        <div class="form-group">
                                            <label>상품코드</label>
                                            <p class="form-control-static">${data.itemReference }</p>
                                        </div>
                                        <div class="form-group">
                                            <label>제조일자</label>
                                            <p class="form-control-static">${data.manufacturedDate }</p>
                                        </div>
                                        <div class="form-group">
                                            <label>유통기한</label>
                                            <p class="form-control-static">${data.expirationDate }</p>
                                        </div>
                                        <div class="form-group">
                                            <label>유통사</label>
                                            <p class="form-control-static">${data.partyContactName }</p>
                                            <p class="form-control-static">${data.partyContactAddress }</p>
                                        </div>
                                        <div class="form-group">
                                            <label>구매주소</label>
                                            <p class="form-control-static">
                                            	<a href="${data.productInformationLinkURL }" >${data.productInformationLinkURL }</a>
                                            </p>
                                        </div>
                                        <div class="form-group">
                                            <label>개수</label>
                                            <input name="count" type="text" class="form-control" value="${data.count }"/>
                                        </div>
                                        <div class="form-group">
                                            <label>비고</label>
                                            <textarea name="remark" class="form-control" rows="3" placeholder="Enter text">${data.remark }</textarea>
                                        </div>
                                      
                                        <button type="submit" class="btn btn-default">수정</button>
                                        <button class="btn btn-default" onClick="location.href='getItemList'">돌아가기</button>
                                    </form>
                                </div>
                               
                            </div>
                            <!-- /.row (nested) -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
        </div>
        <!-- /#page-wrapper -->

    </div>
    <!-- /#wrapper -->

    <!-- jQuery -->
    <script src="../vendor/jquery-3.3.1/jquery-3.3.1.js"></script>

    <!-- Metis Menu Plugin JavaScript -->
    <script src="../vendor/metisMenu/metisMenu.min.js"></script>

    <!-- Custom Theme JavaScript -->
    <script src="../js/sb-admin-2.js"></script>

</body>
</html>