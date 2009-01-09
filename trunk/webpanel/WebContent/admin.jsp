<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="xdisk.persistence.User"%>
<%@page import="xdisk.persistence.database.UserController"%>
<%@page import="xdisk.UserBean"%>
<%@page import="javax.faces.context.FacesContext"%>
<%@page import="java.util.*"%><html>
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
	<div id="content">
	<h2><h:outputText value="#{msg.welcome} #{userBean.username}" /></h2>
	<h3>Informazioni Amministratore</h3>
	<%
	FacesContext facesContext = FacesContext.getCurrentInstance();
	//facesContext.getExternalContext().getRequestParameterMap().get();
	Map<String,Object> sss = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
	//String usern = facesContext.getExternalContext().getRequestParameterMap().get("userBean"); 
	UserBean us = (UserBean)sss.get("userBean");
	User user = us.getUser();
//	UserController.load(user);	
	%>
	<table>
		<tr>
			<td>Username</td>
			<td><%=user.getUsername() %></td>
		</tr>
		<tr>
			<td>Nome</td>
			<td><%=user.getName() %></td>
		</tr>
		<tr>
			<td>Password</td>
			<td><%=user.getPassword() %></td>
		</tr>
		<tr>
			<td>Email</td>
			<td><%=user.getEmail() %></td>
		</tr>
	</table>
	</div>
	<div id="sidebar">
		<div id="sidebar-bgtop"></div>
		<div id="sidebar-content">
			<ul>
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
						<li><h:commandLink value="#{msg.folder}" action="listUser"></h:commandLink></li>
						<li><h:commandLink value="#{msg.add} #{msg.folder}" action="listUser"></h:commandLink></li>
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