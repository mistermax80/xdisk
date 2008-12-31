<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<f:view>
	<f:subview id="header">
		<f:loadBundle basename="xdisk.messagesResource" var="msg" />
		<div id="header">
		<div id="logo">
		<h1><h:outputText value="#{msg.nameProject}" /></h1>
		<p>Design by <h:outputLink value="#{msg.linkRepositoryCode}">
			<h:outputText value="#{msg.authors}" />
		</h:outputLink></p>
		</div>
		<!-- end #logo -->
		<div id="menu">
		<ul>
			<li class="active"><h:outputLink value="#{msg.linkHome}">
				<h:outputText value="#{msg.home}" />
			</h:outputLink></li>
			<li><a href="#">About Us</a></li>
			<li><a href="#">Products</a></li>
			<li><a href="#">Services</a></li>
			<li><a href="#">Contact Us</a></li>
		</ul>
		</div>
		<!-- end #menu --></div>
		<!-- end #header -->
	</f:subview>
</f:view>