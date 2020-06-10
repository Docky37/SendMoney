<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<meta name="description" content="">
<meta name="author" content="">

<title>Complete your account</title>

<link href="${contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${contextPath}/resources/css/common.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>
	<div class="container">
		<div style="text-align: center; margin-bottom: 20px;">
			<h4>
				Welcome ${pageContext.request.userPrincipal.name} | <a
					onclick="document.forms['logoutForm'].submit()">Logout</a><br />
			</h4>
		</div>
		<form:form method="POST" modelAttribute="bankAccountForm"
			class="form-signin">

			<!-- 		<form method="POST" action="${contextPath}/bank-account"
			class="form-signin"> -->

			<h2 class="form-heading">Pay My Buddy</h2>
			<h4>Ok, you have a valid PayMyBuddy account, but you need to add
				a bank account for bidirectional money transfer between bank and
				your PMB account.</h4>
			<div class="form-group ${error != null ? 'has-error' : ''}">
				<span>${message}</span>
				<spring:bind path="ibanCode">
					<form:input path="ibanCode" type="text" class="form-control"
						placeholder="IBAN" autofocus="true"></form:input>
					<form:errors path="ibanCode"></form:errors>
				</spring:bind>
				<p>&nbsp;</p>
				<spring:bind path="swift">
					<form:input path="swift" type="text" class="form-control"
						placeholder="BIC/SWIFT" autofocus="true"></form:input>
					<form:errors path="swift"></form:errors>
				</spring:bind>
				<p>&nbsp;</p>
				<spring:bind path="ibanCode">
					<form:input path="email" type="hidden" class="form-control"
						value="${pageContext.request.userPrincipal.name}"
						placeholder="email"></form:input>
					<!--<c:set var="email" value="${pageContext.request.userPrincipal.name}" />-->
					<form:errors path="email"></form:errors>
				</spring:bind>


				<button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>
			</div>

		</form:form>

	</div>
	<!-- /container -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="${contextPath}/resources/js/bootstrap.min.js"></script>
</body>
</html>
