<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="owners">

    <h2><fmt:message key="ownerInformation"/></h2>


    <table class="table table-striped">
        <tr>
            <th><fmt:message key="name"/></th>
            <td><b><c:out value="${owner.firstName} ${owner.lastName}"/></b></td>
        </tr>
        <tr>
            <th><fmt:message key="address"/></th>
            <td><c:out value="${owner.address}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="city"/></th>
            <td><c:out value="${owner.city}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="telephone"/></th> 
            <td><c:out value="${owner.telephone}"/></td>
        </tr>
    </table>

    <spring:url value="{ownerId}/edit" var="editUrl">
        <spring:param name="ownerId" value="${owner.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(editUrl)}" class="btn btn-default"><fmt:message key="updateOwner"/></a>

    <spring:url value="{ownerId}/pets/new" var="addUrl">
        <spring:param name="ownerId" value="${owner.id}"/>
    </spring:url>
 
    <a href="${fn:escapeXml(addUrl)}" class="btn btn-default"><fmt:message key="addPet"/></a>
    <spring:url value="/owners/{ownerId}/delete" var="deleteUrl">
        <spring:param name="ownerId" value="${owner.id}"/>
    </spring:url>
    <a href="${fn:escapeXml(deleteUrl)}" class="btn btn-default"><fmt:message key="deleteOwner"/></a>

    <br/>
    <br/>
    <br/>

    <h2><fmt:message key="petvisitbooking"/></h2>

    <table class="table table-striped">
        <c:forEach var="pet" items="${owner.pets}">

            <tr>
                <td valign="top">
                    <dl class="dl-horizontal">
                        <dt><fmt:message key="name"/></dt>
                        <dd><c:out value="${pet.name}"/></dd>
                        <dt><fmt:message key="birthDate"/></dt>
                        <dd><petclinic:localDate date="${pet.birthDate}" pattern="yyyy-MM-dd"/></dd>
                        <dt><fmt:message key="petInformation"/></dt>
                        <dd><c:out value="${pet.type.name}"/></dd>
                    </dl>
                </td>
                <td valign="top">
                    <table class="table-condensed">
                        <thead>
                        <tr>
                            <th><fmt:message key="visitDate"/></th>
                            <th><fmt:message key="description"/></th>
                            <th><fmt:message key="deletePet"/></th>
                        </tr>
                        </thead>
                        <c:forEach var="visit" items="${pet.visits}">
                            <tr>
                                <td><petclinic:localDate date="${visit.date}" pattern="yyyy-MM-dd"/></td>
                                <td><c:out value="${visit.description}"/></td>
                                <td><spring:url value="/owners/{ownerId}/pets/{petId}/visits/{visitId}/delete" var="deleteVisitUrl">
                                	<spring:param name="ownerId" value="${owner.id}"/>
                                    <spring:param name="petId" value="${pet.id}"/>
                                    <spring:param name="visitId" value="${visit.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(deleteVisitUrl)}"><fmt:message key="deleteVisit"/></a>
                                </td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td>
                                <spring:url value="/owners/{ownerId}/pets/{petId}/edit" var="petUrl">
                                    <spring:param name="ownerId" value="${owner.id}"/>
                                    <spring:param name="petId" value="${pet.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(petUrl)}"><fmt:message key="updatePet"/></a>
                            </td>
                            <td>
                                <spring:url value="/owners/{ownerId}/pets/{petId}/visits/new" var="visitUrl">
                                    <spring:param name="ownerId" value="${owner.id}"/>
                                    <spring:param name="petId" value="${pet.id}"/>
                                </spring:url>
                              
                                <a href="${fn:escapeXml(visitUrl)}"><fmt:message key="addVisit"/></a> 
                            </td>
                            <td>
                                <spring:url value="/owners/{ownerId}/pets/{petId}/delete" var="deleteUrl">
                                    <spring:param name="ownerId" value="${owner.id}"/>
                                    <spring:param name="petId" value="${pet.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(deleteUrl)}"><fmt:message key="deletePet"/></a>
                            </td>
                        </tr>
                    </table>
                </td>
                
                
               <td valign="top">
                    <table class="table-condensed">
                        <thead>
                        <tr>
                            <th><fmt:message key="startDate"/></th>
                            <th><fmt:message key="finishDate"/></th>
                            <th><fmt:message key="details"/></th>
                        </tr>
                        </thead>
                        <c:forEach var="booking" items="${pet.bookings}">
                            <tr>
                                <td><petclinic:localDate date="${booking.startDate}" pattern="yyyy-MM-dd"/></td>
                                <td><petclinic:localDate date="${booking.finishDate}" pattern="yyyy-MM-dd"/></td>
                                <td><c:out value="${booking.details}"/></td>
                                <td>
	                                <spring:url value="/owners/{ownerId}/pets/{petId}/bookings/{bookingId}/delete" var="deleteBookingUrl">
	                                    <spring:param name="ownerId" value="${owner.id}"/>
	                                    <spring:param name="petId" value="${pet.id}"/>
	                                    <spring:param name="bookingId" value="${booking.id}"/>
	                                </spring:url>
	                                <a href="${fn:escapeXml(deleteBookingUrl)}"><fmt:message key="deleteBooking"/></a>
                                </td>
                            </tr>
                        </c:forEach>
                        <tr>
                            <td>
                                <spring:url value="/owners/{ownerId}/pets/{petId}/bookings/new" var="bookingUrl">
                                    <spring:param name="ownerId" value="${owner.id}"/>
                                    <spring:param name="petId" value="${pet.id}"/>
                                </spring:url>
                                <a href="${fn:escapeXml(bookingUrl)}"><fmt:message key="addBooking"/></a>
                            </td>
                        </tr>
                    </table>
                </td>
                
                
            </tr>

        </c:forEach>
    </table>

</petclinic:layout>
