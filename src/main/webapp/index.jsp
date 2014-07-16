<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String basePath = request.getContextPath() + "/";
%>

<!DOCTYPE html>
<html>
<head>
<title></title>
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/base.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/nav.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/box.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>css/table.css">
<style type="text/css">
	.ui-content-main {
		width: 80%;
		min-height: 200px;
		margin: 10px auto;
		padding: 15px 20px;
		background: #fff;
		border: 1px solid #ccc;
	}
	.ui-content-main .ui-content-harf {
		width: 44em;
		display: inline-block;
	}
</style>
</head>

<body>
	<div class="ui-nav">
		<ul class="ui-nav-main">
			<li class="ui-nav-item"><a href="#">基础设置</a></li>
			<li class="ui-nav-item ui-nav-item-current"><a href="#">活动管理</a></li>
		</ul>
	</div>
	<div class="ui-content-main">
		<div class="ui-box ui-box-noborder">
		    <div class="ui-box-head">
		        <h3 class="ui-box-head-title">区块标题</h3>
		        <span class="ui-box-head-text">其他文字</span>
		        <a href="#" class="ui-box-head-more">更多</a>
		    </div>
		    <div class="ui-box-container">
		        <div class="ui-box-content"></div>
		        <div class="ui-table-container">
				    <table class="ui-table ui-table-inbox">
				        <thead>
				            <tr>
				                <th>创建时间</th>
				                <th>名称<em class="ft-bar">|</em>交易号</th>
				                <th>对方</th>
				                <th>金额<em class="ft-bar">|</em>明细</th>
				                <th>状态</th>
				                <th>操作</th>
				                <th>备注</th>
				            </tr>
				        </thead><!-- 表头可选 -->
				        <tbody>
				            <tr>
				                <td>Data</td>
				                <td>Data</td>
				                <td>Data</td>
				                <td>Data</td>
				                <td>Data</td>
				                <td>Data</td>
				                <td>Data</td>
				            </tr>
				            <tr class="ui-table-split">
				                <td>Data</td>
				                <td>Data</td>
				                <td>Data</td>
				                <td>Data</td>
				                <td>Data</td>
				                <td>Data</td>
				                <td>Data</td>
				            </tr>
				            <tr>
				                <td>Data</td>
				                <td>Data</td>
				                <td>Data</td>
				                <td>Data</td>
				                <td>Data</td>
				                <td>Data</td>
				                <td>Data</td>
				            </tr>
				            <tr class="ui-table-split">
				                <td>Data</td>
				                <td>Data</td>
				                <td>Data</td>
				                <td>Data</td>
				                <td>Data</td>
				                <td>Data</td>
				                <td>Data</td>
				            </tr>
				        </tbody>
				    </table>
				</div>
		    </div>
		</div>
	</div>
	<div class="ui-content-main">
		<div class="ui-content-harf">
			<div class="ui-box ui-box-noborder">
			    <div class="ui-box-head">
			        <h3 class="ui-box-head-title">区块标题</h3>
			        <span class="ui-box-head-text">其他文字</span>
			        <a href="#" class="ui-box-head-more">更多</a>
			    </div>
			    <div class="ui-box-container">
			        <div class="ui-box-content"></div>
			        <div class="ui-table-container">
					    <table class="ui-table ui-table-inbox">
					        <thead>
					            <tr>
					                <th>创建时间</th>
					                <th>名称<em class="ft-bar">|</em>交易号</th>
					                <th>对方</th>
					                <th>金额<em class="ft-bar">|</em>明细</th>
					                <th>状态</th>
					                <th>操作</th>
					                <th>备注</th>
					            </tr>
					        </thead><!-- 表头可选 -->
					        <tbody>
					            <tr>
					                <td>Data</td>
					                <td>Data</td>
					                <td>Data</td>
					                <td>Data</td>
					                <td>Data</td>
					                <td>Data</td>
					                <td>Data</td>
					            </tr>
					            <tr class="ui-table-split">
					                <td>Data</td>
					                <td>Data</td>
					                <td>Data</td>
					                <td>Data</td>
					                <td>Data</td>
					                <td>Data</td>
					                <td>Data</td>
					            </tr>
					        </tbody>
					    </table>
					</div>
			    </div>
			</div>
		</div>
		<div class="ui-content-harf">
			<div class="ui-box ui-box-noborder">
			    <div class="ui-box-head">
			        <h3 class="ui-box-head-title">区块标题</h3>
			        <span class="ui-box-head-text">其他文字</span>
			        <a href="#" class="ui-box-head-more">更多</a>
			    </div>
			    <div class="ui-box-container">
			        <div class="ui-box-content"></div>
			        <div class="ui-table-container">
					    <table class="ui-table ui-table-inbox">
					        <thead>
					            <tr>
					                <th>创建时间</th>
					                <th>名称<em class="ft-bar">|</em>交易号</th>
					                <th>对方</th>
					                <th>金额<em class="ft-bar">|</em>明细</th>
					                <th>状态</th>
					                <th>操作</th>
					                <th>备注</th>
					            </tr>
					        </thead><!-- 表头可选 -->
					        <tbody>
					            <tr>
					                <td>Data</td>
					                <td>Data</td>
					                <td>Data</td>
					                <td>Data</td>
					                <td>Data</td>
					                <td>Data</td>
					                <td>Data</td>
					            </tr>
					            <tr class="ui-table-split">
					                <td>Data</td>
					                <td>Data</td>
					                <td>Data</td>
					                <td>Data</td>
					                <td>Data</td>
					                <td>Data</td>
					                <td>Data</td>
					            </tr>
					        </tbody>
					    </table>
					</div>
			    </div>
			</div>
		</div>
	</div>
</body>
</html>
