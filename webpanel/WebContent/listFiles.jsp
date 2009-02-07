<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="xdisk.persistence.database.UserController"%>
<%@page import="java.util.LinkedList"%>
<%@page import="xdisk.persistence.User"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>XDISK the new sharing file</title>
<link rel="stylesheet" type="text/css" href="style.css" media="screen" />
</head>
<body>
<f:view>
	<c:import url="header.jsp"></c:import>
	<div id="page">
	<div id="content"><h:form>
	<h2><h:outputLabel value="File trovati, seleziona il file da scaricare..."></h:outputLabel></h2>
		<h:selectOneRadio title=""
			value="#{file.code}" layout="pageDirection" border="1">
			<f:selectItems value="#{itemFileS.options}" />
		</h:selectOneRadio>
		<h:commandButton value="Download"/>
		</h:form></div>
	</div>
	<c:import url="footer.jsp"></c:import>
</f:view>
</body>
</html>