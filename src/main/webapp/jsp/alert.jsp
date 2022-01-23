<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript" src="<c:url value="/js/index.js"/>"></script>
<c:if test="${requestScope.infoMessage != null}">
	<script type="text/javascript">
		alertFunc("${requestScope.infoMessage}");
	</script>
</c:if>