<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="adoptionApplicationsByPet">
    <h2><fmt:message key="adoptionApplicationsByPet"/></h2>

    <table id="petsInAdoptionTable" class="table table-striped">
        <thead>
        <tr>
            <th><fmt:message key="nameOfApplicant"/></th>
            <th><fmt:message key="descriptionOfCare"/></th>
            <th><fmt:message key="acceptApplication"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${adoptionApplicationsByPet}" var="adoptionApplication">
            <tr>
                <td>
                    <c:out value="${adoptionApplication.owner.firstName} ${adoptionApplication.owner.lastName}"/>
                </td>
                
                <td>
					<c:out value="${adoptionApplication.description}"/>             
                </td>
              	
                <td>
					<spring:url value="/petsInAdoption/confirmPetAdoption/{petId}/{ownerId}" var="confirmPetAdoption">
		      			<spring:param name="petId" value="${adoptionApplication.pet.id}"/>
		      			<spring:param name="ownerId" value="${adoptionApplication.owner.id}"/>
		  			</spring:url>
		  			<a href="${fn:escapeXml(confirmPetAdoption)}" class="btn btn-default"><fmt:message key="confirmPetAdoption"/></a>         
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</petclinic:layout>
