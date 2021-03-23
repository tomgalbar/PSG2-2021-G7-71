<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="vets">
    <h2><fmt:message key="veterinarians"/></h2>

    <table id="vetsTable" class="table table-striped">
        <thead>
        <tr>
            <th><fmt:message key="name"/></th>
            <th><fmt:message key="specialties"/></th>
            <th><fmt:message key="delete"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${vets.vetList}" var="vet">
            <tr>
                <td>
                    <c:out value="${vet.firstName} ${vet.lastName}"/>
                </td>
                <td>
                    <c:forEach var="specialty" items="${vet.specialties}">
                        <c:out value="${specialty.name} "/>
                    </c:forEach>
                    <c:if test="${vet.nrOfSpecialties == 0}">none</c:if>
                </td>
                <td>
               		<spring:url value="/vets/{vetId}/delete" var="deleteVetUrl">
                		<spring:param name="vetId" value="${vet.id}"/>
                	</spring:url>
                	<a href="${fn:escapeXml(deleteVetUrl)}"><fmt:message key="deleteVet"/></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <table class="table-buttons">
        <tr>
            <td>
                <a href="<spring:url value="/vets.xml" htmlEscape="true" />"><fmt:message key="viewas"/> XML</a>
            </td>            
        </tr>
    </table>
</petclinic:layout>
