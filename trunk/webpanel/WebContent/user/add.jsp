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
	<c:import url="../header.jsp"></c:import>
	<div id="page">
	<div id="content">
		<h2><h:outputLabel value="Aggiungi Utente" /></h2>
		<h:form>
			<li><h:outputLabel value="Username" /></li>
			<li><h:inputText id="username" value="#{userBean.user.username}"/></li>
			<li><h:outputLabel value="Password" /></li>
			<li><h:inputText id="password" value="#{userBean.user.password}"/></li>
			<li><h:outputLabel value="Nome" /></li>
			<li><h:inputText id="name" value="#{userBean.user.name}"/></li>
			<li><h:outputLabel value="Email" /></li>
			<li><h:inputText id="email" value="#{userBean.user.email}"/></li>
			<li><h:outputLabel value="Admin" /></li>
			<li><h:inputText id="admin" value="#{userBean.user.admin}"/></li>
			<li><A>&nbsp;</A>
			<h:commandButton id="addBu" value="Click">
				
			</h:commandButton></li>
				<li><h:outputLabel value="errore" /></li>
		</h:form>
	</div>
	</div>
	<c:import url="../footer.jsp"></c:import>
</f:view>
</body>
</html>