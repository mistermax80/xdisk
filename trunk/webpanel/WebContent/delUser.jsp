<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="d"
	uri="http://java.sun.com/blueprints/ee5/components/ui"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>XDISK the new sharing file</title>
<link rel="stylesheet" type="text/css" href="style.css" media="screen" />
</head>
<body>
<f:view>
	<f:loadBundle basename="xdisk.messagesResource" var="msg" />
	<c:import url="header.jsp"></c:import>
	<div id="page">
	<div id="content"><h:form>
	<h:outputLabel value="Seleziona l'utente da eliminare:"></h:outputLabel>
			<ul>
				<h:selectOneMenu title="Seleziona un utente"
					value="#{newUserBean.username}">
					<f:selectItems value="#{itemUser.options}" />
				</h:selectOneMenu>
			</ul>
			<ul>
				<h:commandButton value="#{msg.del}" action="listUser"
					actionListener="#{actionAddNew.deleteUser}" />
			</ul>
	</h:form></div>
	</div>
	<c:import url="footer.jsp"></c:import>
</f:view>
</body>
</html>