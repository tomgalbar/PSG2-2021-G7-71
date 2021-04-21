<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="petsInAdoption">
    <h2><fmt:message key="petsInAdoption"/></h2>

    <table id="petsInAdoptionTable" class="table table-striped">
        <thead>
        <tr>
            <th><fmt:message key="name"/></th>
            <th><fmt:message key="birthDate"/></th>
            <th><fmt:message key="type"/></th>
            <th><fmt:message key="owner"/></th>
           	<sec:authorize access="hasAnyAuthority('owner')">
            	<th><fmt:message key="adoptionRequest"/></th>
            </sec:authorize>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${pets}" var="pet">
            <tr>
                <td>
                    <c:out value="${pet.name}"/>
                </td>
                
                <td>
					<c:out value="${pet.birthDate}"/>             
                </td>
                
                <td>
					<c:out value="${pet.type}"/>             
                </td>
              	
                <td>
					<c:out value="${pet.owner.firstName}"/>             
                </td>
                
           		<sec:authorize access="hasAnyAuthority('owner')">
	                <td>
	                	<c:choose>
	                	
	                		<c:when test="${currentApplications.contains(pet)}">
	                			<b><fmt:message key="adoptionAlreadyApplied"/></b>
	                		</c:when>
	                		
	                		<c:otherwise>
	                			<spring:url value="/petsInAdoption/adoptionRequest/new/{petId}" var="adoptionRequest">
			      					<spring:param name="petId" value="${pet.id}"/>
			  					</spring:url>
			  					<a href="${fn:escapeXml(adoptionRequest)}" class="btn btn-default"><fmt:message key="adoptionRequest"/></a>  
	                		</c:otherwise>
	                	
	                	</c:choose>
	                </td>
                </sec:authorize>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</petclinic:layout>
