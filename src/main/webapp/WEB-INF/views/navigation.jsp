<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- Navigation -->
<nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
	<ul class="nav navbar-top-links navbar-left">
		<!-- /.dropdown -->
		<li class="dropdown">
	        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
	            <i class="fa fa-navicon fa-fw"></i>
	        </a>
	        <ul class="dropdown-menu dropdown-user">
	            <div class="navbar-default sidebar" role="navigation">+
				    <div class="sidebar-nav navbar-collapse">
				        <ul class="nav" id="side-menu">
				        	<li>
				                <a href="../Item/getItemList"><i class="fa fa-table fa-fw"></i> Item List</a>
				            </li>
				            <li>
				                <a href="../FridgeCam/showImage"><i class="fa fa-camera fa-fw"></i> Fridge Cam</a>
				            </li>
				            <li>
				                <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i> Statistics<span class="fa arrow"></span></a>
				                <ul class="nav nav-second-level">
				                    <li>
				                        <a href="flot.html">Flot Charts</a>
				                    </li>
				                    <li>
				                        <a href="morris.html">Morris.js Charts</a>
				                    </li>
				                </ul>
				            </li>
				        </ul>
				    </div>
				</div>
	        </ul>
	        <!-- /.dropdown-user -->
	    </li>
	</ul>
	<div class="navbar-header">
	    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
	        <span class="sr-only">Toggle navigation</span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	        <span class="icon-bar"></span>
	    </button>
	    <a class="navbar-brand" href="getItemList">Smart Fridge v1.0</a>
	</div>
	<!-- /.navbar-header -->
	<ul class="nav navbar-top-links navbar-right">
	    <!-- /.dropdown -->
	    <li class="dropdown"> 
	        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
	            <i class="fa fa-tasks fa-fw"></i> <i class="fa fa-caret-down"></i>
	        </a>
	        <ul class="dropdown-menu dropdown-tasks">
	            <li>
	                <a href="#">
	                    <div>
	                        <p>
	                            <strong>Task 1</strong>
	                            <span class="pull-right text-muted">40% Complete</span>
	                        </p>
	                        <div class="progress progress-striped active">
	                            <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40" aria-valuemin="0" aria-valuemax="100" style="width: 40%">
	                                <span class="sr-only">40% Complete (success)</span>
	                            </div>
	                        </div>
	                    </div>
	                </a>
	            </li>
	            <li class="divider"></li>
	            <li>
	                <a href="#">
	                    <div>
	                        <p>
	                            <strong>Task 2</strong>
	                            <span class="pull-right text-muted">20% Complete</span>
	                        </p>
	                        <div class="progress progress-striped active">
	                            <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100" style="width: 20%">
	                                <span class="sr-only">20% Complete</span>
	                            </div>
	                        </div>
	                    </div>
	                </a>
	            </li>
	            <li class="divider"></li>
	            <li>
	                <a href="#">
	                    <div>
	                        <p>
	                            <strong>Task 3</strong>
	                            <span class="pull-right text-muted">60% Complete</span>
	                        </p>
	                        <div class="progress progress-striped active">
	                            <div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="60" aria-valuemin="0" aria-valuemax="100" style="width: 60%">
	                                <span class="sr-only">60% Complete (warning)</span>
	                            </div>
	                        </div>
	                    </div>
	                </a>
	            </li>
	            <li class="divider"></li>
	            <li>
	                <a href="#">
	                    <div>
	                        <p>
	                            <strong>Task 4</strong>
	                            <span class="pull-right text-muted">80% Complete</span>
	                        </p>
	                        <div class="progress progress-striped active">
	                            <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" style="width: 80%">
	                                <span class="sr-only">80% Complete (danger)</span>
	                            </div>
	                        </div>
	                    </div>
	                </a>
	            </li>
	            <li class="divider"></li>
	            <li>
	                <a class="text-center" href="#">
	                    <strong>See All Tasks</strong>
	                    <i class="fa fa-angle-right"></i>
	                </a>
	            </li>
	        </ul>
	        <!-- /.dropdown-tasks -->
	    </li>
	    <!-- /.dropdown -->
	    <li class="dropdown">
	        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
	            <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
	        </a>
	        <ul class="dropdown-menu dropdown-user">
	            <li><a href="#"><i class="fa fa-user fa-fw"></i> User Profile</a>
	            </li>
	            <li><a href="#"><i class="fa fa-gear fa-fw"></i> Settings</a>
	            </li>
	            <li class="divider"></li>
	            <li><a href="login.html"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
	            </li>
	        </ul>
	        <!-- /.dropdown-user -->
	    </li>
	    <!-- /.dropdown -->
	</ul>
	<!-- /.navbar-top-links -->
	
	<!-- 
	<div class="navbar-default sidebar" role="navigation">
	    <div class="sidebar-nav navbar-collapse">
	        <ul class="nav" id="side-menu">
	        	<li>
	                <a href="../Item/getItemList"><i class="fa fa-table fa-fw"></i> Item List</a>
	            </li>
	            <li>
	                <a href="../FridgeCam/showImage"><i class="fa fa-camera fa-fw"></i> Fridge Cam</a>
	            </li>
	            <li>
	                <a href="#"><i class="fa fa-bar-chart-o fa-fw"></i> Statistics<span class="fa arrow"></span></a>
	                <ul class="nav nav-second-level">
	                    <li>
	                        <a href="flot.html">Flot Charts</a>
	                    </li>
	                    <li>
	                        <a href="morris.html">Morris.js Charts</a>
	                    </li>
	                </ul>
	            </li>
	        </ul>
	    </div>
	</div>
	 -->
	<!-- /.navbar-static-side -->
</nav>
