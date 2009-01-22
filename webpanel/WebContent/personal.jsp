<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>XDISK the new sharing file</title>
<link rel="stylesheet" type="text/css" href="style.css" media="screen" />
</head>
<html>
<body>
<f:view>
	<f:loadBundle basename="xdisk.messagesResource" var="msg" />
	<c:import url="header.jsp"></c:import>
	<div id="page">
	<div id="content">
	<h2><h:outputText value="#{msg.welcome} #{userBean.username}" /></h2>
	<h3>Profilo Personale</h3>
	<h:form>
	<h:panelGrid columns="2">
		
		<h:outputLabel value="Nome:"/>
		<h:inputText value="#{userBean.user.name}"/>
		<h:outputLabel value="Username:"/>
		<h:inputText value="#{userBean.user.username}"/>
		<h:outputLabel value="Password:"/>
		<h:inputText value="#{userBean.user.password}"/>
		<h:outputLabel value="Email:"/>
		<h:inputText value="#{userBean.user.email}"/>
		<h:outputLabel value="Admin:"/>
		<h:selectBooleanCheckbox value="#{userBean.user.admin}"/>
		<h:commandButton value="Salva" action="personal" actionListener="#{actionAddNew.updateUser}"/>
	</h:panelGrid>
	</h:form>
	</div>
	<div id="sidebar">
		<div id="sidebar-bgtop"></div>
		<div id="sidebar-content">
			<ul>
				<li>
					<h2><h:outputText value="Menu"/></h2>
					<ul><h:form>
						<li><h:commandLink value="Home" action="return"></h:commandLink></li>
						<li><h:commandLink value="Profilo" action="personal"></h:commandLink></li>
						</h:form>
					</ul>
				</li>
				<li>
					<h2><h:outputText value="#{msg.manage} #{msg.users}"/></h2>
					<ul><h:form>
						<li><h:commandLink value="#{msg.users}" action="listUser"></h:commandLink></li>
						<li><h:commandLink value="#{msg.add} #{msg.user}" action="newUser"></h:commandLink></li>
						</h:form>
					</ul>
				</li>
				<li>
					<h2><h:outputText value="#{msg.manage} #{msg.folders}"/></h2>
					<ul><h:form>
						<li><h:commandLink value="#{msg.folders}" action="listFolder"></h:commandLink></li>
						</h:form>
					</ul>
				</li>
			</ul>
		</div>
		<div id="sidebar-bgbtm"></div>
	</div>
	<!-- end #sidebar -->
	</div>
	<c:import url="footer.jsp"></c:import>
</f:view>
</body>
</html>