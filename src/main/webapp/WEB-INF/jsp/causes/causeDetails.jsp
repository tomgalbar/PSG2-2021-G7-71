<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="causes">

    <h2><fmt:message key="causeInformation"/></h2>


    <table class="table table-striped">
        <tr>
            <th><fmt:message key="causeName"/></th>
            <td><b><c:out value="${cause.name}"/></b></td>
        </tr>
        <tr>
            <th><fmt:message key="causeDescription"/></th>
            <td><c:out value="${cause.description}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="organization"/></th>
            <td><c:out value="${cause.organization}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="budgetTarget"/></th>
            <td><c:out value="${cause.budgetTarget}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="budgetAchieved"/></th> 
            <td><c:out value="${cause.budgetAchieved}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="isClosed"/></th> 
            <td>
            	<c:choose>
            		<c:when test="${!cause.isClosed}">
            			<fmt:message var="goalNotAchieved" key="goalNotAchieved"/>
            			<c:out value="${goalNotAchieved}"/>
            		</c:when> 
            		<c:otherwise>            			
            			<fmt:message var="goalAchieved" key="goalAchieved"/>
            			<c:out value="${goalAchieved}"/>
            		</c:otherwise>           		
            	</c:choose>           	
            </td>
        </tr>
    </table>
    
   

    <br/>
    <br/>
    <br/>

    <h2><fmt:message key="donationsList"/></h2>

    <table id="donationsTable" class="table table-striped">
        <thead>
        <tr>
            <th><fmt:message key="clientName"/></th>
            <th><fmt:message key="amount"/></th>
            <th><fmt:message key="donationDate"/></th>
            
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${cause.donations}" var="donation">
            <tr>
                <td>
                    <c:out value="${donation.client}"/>
                </td>
                <td>
                    <c:out value="${donation.amount}"/>
                </td>
                
                <td>
                    <c:out value="${donation.donationDate}"/>
                </td>   
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
