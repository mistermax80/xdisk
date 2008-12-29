<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>XDISK the new sharing file</title>
<link rel="stylesheet" type="text/css" href="style.css" media="screen" />
</head>
<body>
<f:view>
<f:loadBundle basename="xdisk.messagesResource" var="msg"/>
	<div id="header">
	<div id="logo">
	<h1><h:outputText value="#{msg.nameProject}"/></h1>
	<p>Design by <h:outputLink value="#{msg.linkRepositoryCode}"><h:outputText value="#{msg.authors}"/></h:outputLink></p>
	</div>
	<!-- end #logo -->
	<div id="menu">
	<ul>
		<li class="active"><h:outputLink value="#{msg.linkHome}"><h:outputText value="#{msg.home}"/></h:outputLink></li>
		<li><a href="#">About Us</a></li>
		<li><a href="#">Products</a></li>
		<li><a href="#">Services</a></li>
		<li><a href="#">Contact Us</a></li>
	</ul>
	</div>
	<!-- end #menu --></div>
	<!-- end #header -->
	<div id="page">
	<div id="content">
	<div class="post">
	<h1 class="title">Benvenuti nella nuova era del file sharing</h1>
	<p class="byline"><small>Posted by mistermax80</small></p>
	<div class="entry">
	<p><strong>XDISK </strong> è un nuovo modo di condividere file, il
	progetto è rilasciato interamente sotto licenza <strong>opensource</strong>
	è in fase di sviluppo ed è realizzato da un idea di: Gigli Massimo
	(MisterMax80), Fabrizio Filieri (Biio) e da Stefano Piersanti (Stef)</p>
	</div>
	<div class="meta">
	<p class="links"><a href="http://code.google.com/p/xdisk/"
		class="google_code">Home del progetto&nbsp;&raquo;</a></p>
	</div>
	</div>
	<div class="post">
	<h2 class="title">Lorem Ipsum Dolor Volutpat</h2>
	<p class="byline"><small>Posted by FreeCssTemplates</small></p>
	<div class="entry">
	<p>MASSIMO E CIIPS Curabitur tellus. Phasellus tellus turpis, iaculis in, faucibus
	lobortis, posuere in, lorem. Donec a ante. Donec neque purus,
	adipiscing id, eleifend a, cursus vel, odio. Vivamus varius justo sit
	amet leo. Morbi sed libero. Vestibulum blandit augue at mi. Praesent
	fermentum lectus eget diam. Nam cursus, orci sit amet porttitor
	iaculis, ipsum massa aliquet nulla, non elementum mi elit a mauris.</p>
	<p>Praesent fermentum lectus eget diam. Nam cursus, orci sit amet
	porttitor iaculis, ipsum massa aliquet nulla, non elementum mi elit a
	mauris.</p>
	<ul>
		<li><a href="#">Magna lacus bibendum mauris</a></li>
		<li><a href="#">Velit semper nisi molestie</a></li>
		<li><a href="#">Magna lacus bibendum mauris</a></li>
		<li><a href="#">Velit semper nisi molestie</a></li>
	</ul>
	</div>
	<div class="meta">
	<p class="links"><a href="#" class="comments">Comments (32)</a>
	&nbsp;&bull;&nbsp;&nbsp; <a href="#" class="more">Read full post
	&raquo;</a></p>
	</div>
	</div>
	</div>
	<!-- end #content -->
	<div id="sidebar">
	<div id="sidebar-bgtop"></div>
	<div id="sidebar-content">
	<ul>
		<li id="login">
		<h2><h:outputLabel value="#{msg.login}"/></h2>
		<h:form>
			<li><h:outputLabel value="#{msg.username}"/></li>
			<li><h:inputText value="#{userBean.userName}" required="true" /></li>
			<li><h:outputLabel value="#{msg.password}"/></li>
			<li><h:inputSecret/><A>&nbsp;</A><h:commandButton action="login" value="#{msg.login}"/></li>
		</h:form></li>
		<li><h:form><h:commandLink action="register" value="#{msg.register}"></h:commandLink></h:form></li>
		<li>&nbsp;</li>
		<li id="search">
		<h2>Search File</h2>
		<form method="get" action="">
		<fieldset><input type="text" id="s" name="s" value="" /> <input
			type="submit" id="x" value="Search" /></fieldset>
		</form>
		</li>
		<li>
		<h2>titolo1</h2>
		<ul>
			<li><a href="#">link</a></li>
			<li><a href="#">link</a></li>
			<li><a href="#">link</a></li>
			<li><a href="#">link</a></li>
			<li><a href="#">link</a></li>
			<li><a href="#">link</a></li>
			<li><a href="#">link</a></li>
		</ul>
		</li>
		<li>
		<h2>titolo2</h2>
		<ul>
			<li><a href="#">link</a></li>
			<li><a href="#">link</a></li>
			<li><a href="#">link</a></li>
			<li><a href="#">link</a></li>
			<li><a href="#">link</a></li>
			<li><a href="#">link</a></li>
			<li><a href="#">link</a></li>
		</ul>
		</li>
	</ul>
	</div>
	<div id="sidebar-bgbtm"></div>
	</div>
	<!-- end #sidebar --></div>
	<!-- end #page -->
	<div id="footer">
	<p>&copy; 2008. All Rights Reserved. Design by <a
		href="http://www.freecsstemplates.org/">Free CSS Templates</a>.</p>
	</div>
	<!-- end #footer -->
</f:view>
</body>
</html>
