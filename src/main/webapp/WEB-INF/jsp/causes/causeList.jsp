<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="causes">
    <h2><fmt:message key="causes"/></h2>

    <table id="causesTable" class="table table-striped">
        <thead>
        <tr>
            <th><fmt:message key="name"/></th>
            <th><fmt:message key="budgetAchieved"/></th>
            <th><fmt:message key="budgetTarget"/></th>
            <th><fmt:message key="donations"/></th>
            <th><fmt:message key="causeDetailsTitle"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${causes}" var="cause">
            <tr>
                <td>
                    <c:out value="${cause.key.name}"/>
                </td>
                <td>
                    <c:out value="${cause.value}"/>
                </td>
                
                <td>
                    <c:out value="${cause.key.budgetTarget}"/>
                </td>
                
                <td>
                    <c:out value="${cause.key.isClosed}"/>
                </td>
                
                <td>
                    <spring:url value="/causes/{causeId}/donations/new" var="donationUrl">
                        <spring:param name="causeId" value="${cause.key.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(donationUrl)}"><fmt:message key="addDonation"/></a>
                </td>
                
                <td>
                    <spring:url value="/causes/{causeId}" var="causeUrl">
                        <spring:param name="causeId" value="${cause.key.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(causeUrl)}"><fmt:message key="causeDetails"/></a>
                </td>
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
    <table class="table-buttons">
    	<tr>
    		<td>
				<a class="btn btn-default" href='<spring:url value="/causes/new" htmlEscape="true"/>'><fmt:message key="addCause"/></a>
			</td>
		</tr>
    </table>

</petclinic:layout>