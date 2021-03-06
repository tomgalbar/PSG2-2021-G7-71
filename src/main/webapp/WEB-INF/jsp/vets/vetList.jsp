<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="vets">
    <h2><fmt:message key="veterinarians"/></h2>

    <table id="vetsTable" class="table table-striped">
        <thead>
        <tr>
            <th><fmt:message key="name"/></th>
            <th><fmt:message key="specialties"/></th>
            
        	<sec:authorize access="hasAuthority('admin')">
            	<th><fmt:message key="edit"/></th>
            	<th><fmt:message key="delete"/></th>
            </sec:authorize>	
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
              
                <sec:authorize access="hasAuthority('admin')">
	                <td>
	                  <spring:url value="/vets/{vetId}/edit"
								      var="vet2Url">
								      <spring:param name="vetId" value="${vet.id}" />
							      </spring:url> <a href="${fn:escapeXml(vet2Url)}"><fmt:message key="editVet"/></a></td>
	                <td>
	               		<spring:url value="/vets/{vetId}/delete" var="deleteVetUrl">
	                		<spring:param name="vetId" value="${vet.id}"/>
	                	</spring:url>
	                	<a href="${fn:escapeXml(deleteVetUrl)}"><fmt:message key="deleteVet"/></a>
	                </td>
	        	</sec:authorize>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <sec:authorize access="hasAuthority('admin')">
	    <table class="table-buttons">
		    <tr>
			    <td>
					<a class="btn btn-default" href='<spring:url value="/vets/new" htmlEscape="true"/>'><fmt:message key="addVet"/></a>
				</td>
			</tr>
	    </table>
	</sec:authorize>
    
</petclinic:layout>
