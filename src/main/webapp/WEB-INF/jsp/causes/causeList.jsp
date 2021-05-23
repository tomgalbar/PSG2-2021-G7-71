<%@ page session="false" trimDirectiveWhitespaces="true" pageEncoding="UTF-8" %>
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
            <th><fmt:message key="causeName"/></th>
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
                    <c:out value="${cause.name}"/>
                </td>
                <td>
                    <c:out value="${cause.budgetAchieved} €"/>
                </td>
                
                <td>
                    <c:out value="${cause.budgetTarget} €"/>
                </td>
                
				<c:choose>
					<c:when test="${!cause.isClosed}">
						<td>
		                    <spring:url value="/causes/{causeId}/donations/new" var="donationUrl">
		                        <spring:param name="causeId" value="${cause.id}"/>
		                    </spring:url>
		                    <a href="${fn:escapeXml(donationUrl)}"><fmt:message key="addDonation"/></a>
	                	</td>
	              	</c:when>
	                
	                <c:otherwise>
	                	<td>
		                    <span><b><fmt:message key="goalAchieved"/></b></span>
	                	</td>
					</c:otherwise>				
				</c:choose>
                
                <td>
                    <spring:url value="/causes/{causeId}" var="causeUrl">
                        <spring:param name="causeId" value="${cause.id}"/>
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