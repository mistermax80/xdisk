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
	<h:outputLabel value="Seleziona estensione che vuoi eliminare:"></h:outputLabel>
			<ul>
				<h:selectOneMenu title="Seleziona l'estensione che vuoi eliminare"
					value="#{extension.name}">
					<f:selectItems value="#{itemExtension.options}" />
				</h:selectOneMenu>
				<h:commandButton value="Elimina" action="listExtension" actionListener="#{actionAddNew.delExtension}"/>
			</ul>
			</h:form>
			<h:form>
			<h4><h:outputLabel value="Aggiungi estensione"/></h4>
			<h:outputLabel value="Nome"/>
			<h:inputText value="#{extension.name}"/>
			<ul>
			<h:outputLabel value="Permesso"/>
			<h:selectBooleanCheckbox value="#{extension.allow}"/>
			</ul>
			<h:commandButton value="Aggiungi" action="listExtension" actionListener="#{actionAddNew.saveExtension}"/>
			</h:form>
	</div>
	</div>
	<c:import url="footer.jsp"></c:import>
</f:view>
</body>
</html>