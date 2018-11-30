<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
                    <h1 class="page-header">Statistics</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-8">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <i class="fa fa-bar-chart-o fa-fw"></i> Open/Close Event
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-lg-4">
                                    <div class="table-responsive">
                                        <table class="table table-bordered table-striped">
                                            <thead>
                                                <tr>
                                                    <th>Date</th>
                                                    <th>Time</th>
                                                    <th>Event</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <tr>
                                                    <td>10/21/2013</td>
                                                    <td>3:29 PM</td>
                                                    <td>CLOSE</td>
                                                </tr>
                                                <tr>
                                                    <td>10/21/2013</td>
                                                    <td>3:20 PM</td>
                                                    <td>OPEN</td>
                                                </tr>
                                                <tr>
                                                    <td>10/21/2013</td>
                                                    <td>3:29 PM</td>
                                                    <td>CLOSE</td>
                                                </tr>
                                                <tr>
                                                    <td>10/21/2013</td>
                                                    <td>3:20 PM</td>
                                                    <td>OPEN</td>
                                                </tr>
                                                <tr>
                                                    <td>10/21/2013</td>
                                                    <td>3:29 PM</td>
                                                    <td>CLOSE</td>
                                                </tr>
                                                <tr>
                                                    <td>10/21/2013</td>
                                                    <td>3:20 PM</td>
                                                    <td>OPEN</td>
                                                </tr>
                                                <tr>
                                                    <td>10/21/2013</td>
                                                    <td>2:13 PM</td>
                                                    <td>CLOSE</td>
                                                </tr>
                                                <tr>
                                                    <td>10/21/2013</td>
                                                    <td>2:13 PM</td>
                                                    <td>OPEN</td>
                                                </tr>
                                                <tr>
                                                    <td>10/21/2013</td>
                                                    <td>2:13 PM</td>
                                                    <td>CLOSE</td>
                                                </tr>
                                                <tr>
                                                    <td>10/21/2013</td>
                                                    <td>2:13 PM</td>
                                                    <td>OPEN</td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                    <!-- /.table-responsive -->
                                </div>
                                <!-- /.col-lg-4 (nested) -->
                                <div class="col-lg-8">
                                    <div id="container" style="height: 400px; margin: auto; min-width: 310px; max-width: 600px"></div>
                                </div>
                                <!-- /.col-lg-8 (nested) -->
                            </div>
                            <!-- /.row -->
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-8 -->
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

    <!-- Morris Charts JavaScript -->
    <script src="../vendor/Highcharts-6.2.0/code/highcharts.js"></script>
    <script src="../vendor/Highcharts-6.2.0/code/highcharts-more.js"></script>
	<script src="../vendor/Highcharts-6.2.0/code/modules/exporting.js"></script>
	<script src="../vendor/Highcharts-6.2.0/code/modules/export-data.js"></script>

   
    <!-- Custom Theme JavaScript -->
    <script src="../js/sb-admin-2.js"></script>
    
    <!-- Custom Theme JavaScript -->
    <script src="../js/statistics.js"></script>
</body>
</html>