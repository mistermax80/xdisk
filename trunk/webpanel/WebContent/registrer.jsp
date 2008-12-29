<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>XDISK Registrazione</title>
<link rel="stylesheet" type="text/css" href="style.css" media="screen" />
</head>
<body>
<div id="header">
<div id="logo">
<h1><a href="#">XDISK</a></h1>
<p>Design by <a href="http://code.google.com/p/xdisk/">MisterMax80-Biio-Stef</a></p>
</div>
<!-- end #logo -->
<div id="menu">
<ul>
	<li class="active"><a href="home.jsp">Home</a></li>
	<li><a href="#">About Us</a></li>
	<li><a href="#">Products</a></li>
	<li><a href="#">Services</a></li>
	<li><a href="#">Contact Us</a></li>
</ul>
</div>
<!-- end #menu --></div>
<!-- end #header -->
<div id="page">
<f:view>
<h:form>
<li><h:outputText value="Nome"></h:outputText></li>
<li><h:inputText></h:inputText></li>
<li><h:outputText value="Cognome"></h:outputText></li>
<li><h:inputText></h:inputText></li>
<li><h:outputText value="email"></h:outputText></li>
<li><h:inputText></h:inputText></li>
<li><h:outputText value="sito"></h:outputText></li>
<li><h:inputText></h:inputText>
<h:commandButton value="Registra" action="login"/></li>

</h:form>
</f:view>
</div>
</body>
</html>




