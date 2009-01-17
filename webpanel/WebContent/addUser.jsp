<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>XDISK the new sharing file</title>
<link rel="stylesheet" type="text/css" href="style.css" media="screen" />
</head>
<body>
<f:view>
	<c:import url="/header.jsp"></c:import>
	<div id="page">
	<div id="content">
		<h2><h:outputLabel value="Aggiungi Utente" /></h2>
		<h:form>
		<h:panelGrid columns="2" cellpadding="3" title="Compila i campi">
			<h:outputLabel value="Username" />
			<h:inputText id="username" value="#{newUserBean.username}"/>
			<h:outputLabel value="Password" />
			<h:inputText id="password" value="#{newUserBean.password}"/>
			<h:outputLabel value="Nome" />
			<h:inputText id="name" value="#{newUserBean.name}"/>
			<h:outputLabel value="Email" />
			<h:inputText id="email" value="#{newUserBean.email}"/>
			<h:outputLabel value="Admin" />
			<h:selectBooleanCheckbox id="checkbox" value="#{newUserBean.admin}" title="click it to select or deselect"/>
			<h:commandButton value="Indietro" action="return"/>
			<h:commandButton value="Aggiungi" action="#{actionAddNewUser.selectPage}" actionListener="#{actionAddNewUser.saveUser}"/>
		</h:panelGrid>
		</h:form>
	</div>
	</div>
	<c:import url="/footer.jsp"></c:import>
</f:view>
</body>
</html>