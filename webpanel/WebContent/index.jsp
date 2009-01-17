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
	<f:loadBundle basename="xdisk.messagesResource" var="msg" />
	<c:import url="header.jsp"></c:import>
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
	</div>
	<!-- end #content -->
	<div id="sidebar">
	<div id="sidebar-bgtop"></div>
	<div id="sidebar-content">
	<ul>
		<li id="login">
		<!--<h2><h:outputLabel value="#{msg.login}" /></h2>
		<h:outputText value="PASSWORD(minimum 6 characters)"/><br>
<h:inputSecret id="PWD" value="gagag" required="true" >
<f:validateLength maximum="15" minimum="6"/>
</h:inputSecret> 
<h:message for="PWD"/>
-->
		<h:form>
			<li><h:outputLabel value="#{msg.username}" /></li>
			<li><h:inputText id="user" value="#{userBean.username}">
			<f:validateLength minimum="1"/></h:inputText><h:message for="user"/>
			</li>
			<li><h:outputLabel value="#{msg.password}" /></li>
			<li><h:inputSecret value="#{userBean.password}"/><A>&nbsp;</A><h:commandButton
				action="#{userBean.selectPage}" value="#{msg.login}" /></li>
				<li><h:outputLabel value="#{userBean.msgError}" /></li>
		</h:form></li>
		<h:form><li><h:commandLink action="register" value="#{msg.register}"></h:commandLink></li></h:form>
		<li>&nbsp;</li>
		<li id="search">
		<h2>Search File</h2>
		<form method="get" action="">
		<fieldset><input type="text" id="s" name="s" value="" /> <input
			type="submit" id="x" value="Search" /></fieldset>
		</form>
		</li>
	</ul>
	</div>
	<div id="sidebar-bgbtm"></div>
	</div>
	<!-- end #sidebar --></div>
	<c:import url="footer.jsp"></c:import>
</f:view>
</body>
</html>
