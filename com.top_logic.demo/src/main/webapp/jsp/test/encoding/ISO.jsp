<%@page language="java" session="true" extends="com.top_logic.util.TopLogicJspBase"
%><%@page language="java" contentType="text/html; charset=ISO-8859-1"%>
<html>
	<head>
		<title>
			H�llo W�rld!
		</title>
	</head>
	<body>
		Normal: H�llo W�rld!
		<br/>
		Java: <%="H�llo W�rld"%>
		<jsp:include page="<%= \"UTF-8.inc.jsp\" %>"/>
	</body>
</html>