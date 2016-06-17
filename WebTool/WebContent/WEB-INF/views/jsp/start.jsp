<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>


<html ng-app="phonecatApp">

<head>
<title>Spring MVC Form Handling Example</title>

<spring:url value="/resources/core/css/hello.css" var="coreCss" />
<spring:url value="/resources/core/css/bootstrap.min.css"
	var="bootstrapCss" />
<spring:url value="/resources/core/js/angular/angular.js" var="ag" />
<spring:url value="/resources/core/js/angular/jquery-1.12.2.js" var="jq" />
<spring:url value="/resources/core/js/angular/tut/controller.js"
	var="ct" />

<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${coreCss}" rel="stylesheet" />

<script src="${ag}"></script>
<script src="${jq}"></script>
<script src="${ct}"></script>

</head>

<body ng-controller="PhoneListCtrl">

	<h1>start</h1>

	<ul>
		<li ng-repeat="phone in phones"><span>{{phone.name}}</span>
			<p>{{phone.snippet}}</p></li>
	</ul>

	<hr>

	<ul class="phones">
		<li ng-repeat="phone in phones | filter:query | orderBy:orderProp" class="thumbnail"><a href="#/phones/{{phone.id}}" class="thumb">
			<img ng-src="{{phone.imageUrl}}" alt="{{phone.name}}"></a> 
				<a href="#/phones/{{phone.id}}">{{phone.name}}</a>
			<p>{{phone.snippet}}</p></li>
	</ul>


</body>
</html>