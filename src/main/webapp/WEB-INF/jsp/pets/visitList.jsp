<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="visits">

    <h2>Pet Information</h2>

	<td valign="top">
	    <dl class="dl-horizontal">
	    	<dt>Name</dt>
				<dd><c:out value="${pet.name}"/></dd>
			<dt>Birth Date</dt>
				<dd><petclinic:localDate date="${pet.birthDate}" pattern="yyyy-MM-dd"/></dd>
	    	<dt>Type</dt>
	    		<dd><c:out value="${pet.type.name}"/></dd>
	    </dl>
    </td>

    <table class="table table-striped">
        <thead>
        	<tr>
       			<th>Visit Date</th>
        		<th>Description</th>
        		<th>Delete Visit</th>
        	</tr>
        </thead>
        <c:forEach var="visit" items="${pet.visits}">
        	<c:if test="${visit.id!=null}">
	        	<tr>
	        		<td>
	        			<petclinic:localDate date="${visit.date}" pattern="yyyy-MM-dd"/>
	        		</td>
	        		<td>
	        			<c:out value="${visit.description}"/>
	        		</td>
	        		<td>
	                    <spring:url value="/owners/{ownerId}/pets/{petId}/visits/{visitId}/delete" var="deleteVisitUrl">
		                    <spring:param name="ownerId" value="${ownerId}"/>
		                    <spring:param name="petId" value="${pet.id}"/>
		                    <spring:param name="visitId" value="${visit.id}"></spring:param>
	                    </spring:url>
	                    <a href="${fn:escapeXml(deleteVisitUrl)}">Delete Visit</a>
	            	</td>
	        	</tr>
         	</c:if>
         </c:forEach>
    </table>

</petclinic:layout>