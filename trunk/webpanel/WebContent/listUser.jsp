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
	<div id="content">
	<h:form>
	<h:dataTable id="dt1" value="#{dataTable.users}" var="user" cellpadding="3" rules="all">
	
	<f:facet name="header">
		<h:outputText value="Utenti registrati" />
	</f:facet> 
	<h:column>
		<f:facet name="header">
			<h:outputText value="Username" />
		</f:facet>
		<h:outputText value="#{user.username}"></h:outputText>
	</h:column> 
	<h:column>
		<f:facet name="header">
			<h:outputText value="Password" />
		</f:facet>
		<h:outputText value="#{user.password}"></h:outputText>
	</h:column> 
	<h:column>
		<f:facet name="header">
			<h:outputText value="Nome" />
		</f:facet>
		<h:outputText value="#{user.name}"></h:outputText>
	</h:column> 
	<h:column>
		<f:facet name="header">
			<h:outputText value="Email" />
		</f:facet>
		<h:outputText value="#{user.email}"></h:outputText>
	</h:column> 
	<h:column>
		<f:facet name="header">
			<h:outputText value="Admin" />
		</f:facet>
		<h:selectBooleanCheckbox value="#{user.admin}" disabled="true" />
	</h:column>
	</h:dataTable>
	</h:form>
	</div>
	</div>
	<c:import url="footer.jsp"></c:import>
</f:view>
</body>
</html>