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
	<c:import url="header.jsp"></c:import>
	<div id="page">
	<div id="content"><h:form>
		<h:panelGrid columns="2">
		<h:panelGroup>
			<d:stylesheet path="/tree-control-test.css" />
			<ul>
				<d:graph_menutree id="menu4" value="#{FolderBean.treeGraph}"
					styleClass="tree-control" selectedClass="tree-control-selected"
					unselectedClass="tree-control-unselected" immediate="true"
					actionListener="#{FolderBean.processGraphEvent}" />
			</ul>
			</h:panelGroup>
			<h:panelGroup>
			<h:outputLabel value="Aggiungi cartella sotto a:"></h:outputLabel>
			<ul>
				<h:selectOneListbox size="5" title="Seleziona una cartella"
					value="#{folder.parent}">
					<f:selectItems value="#{itemFolders.options}" />
				</h:selectOneListbox>
			</ul>
			<ul>
				<h:outputText value="Nome nuova cartella:" />
				<h:inputText value="#{folder.nome}" />
				<h:commandButton value="Aggiungi" action="okNewFolder"
					actionListener="#{actionAddNew.saveFolder}" />
			</ul>
			</h:panelGroup>
		</h:panelGrid>
	</h:form></div>
	</div>
	<c:import url="footer.jsp"></c:import>
</f:view>
</body>
</html>