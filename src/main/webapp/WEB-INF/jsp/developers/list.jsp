<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="developers">
    <h2><fmt:message key="developers"/></h2>

    <table id="developersTable" class="table table-striped">
        <thead>
        <tr>
            <th><fmt:message key="name"/></th>
            <th><fmt:message key="rol"/></th>
        </tr>
        </thead>
        <tbody>
	         <tr>
		         <td>
		             <c:out value="SERVANDO FIGUEROA GÓMEZ"/>
		         </td>
		         
		         <td>
		         	<c:out value="Development Team"/>
		         </td>
		         
	         </tr>
	         
	         <tr>
		         <td>
		             <c:out value="ALEJANDRO FRAILE RODRIGUEZ"/>
		         </td>
		         
		         <td>
		         	<c:out value="Development Team"/>
		         </td>
	         </tr>
	         
	         <tr>
		         <td>
		             <c:out value="TOMAS GALERA BARRERA"/>
		         </td>
		         
		         <td>
		         	<c:out value="Scrum Master"/>
		         </td>
	         </tr>
	         
	         <tr>
		         <td>
		             <c:out value="HORACIO GARCIA LERGO"/>
		         </td>
		         
		         <td>
		         	<c:out value="Development Team"/>
		         </td>
	         </tr>
	         
	         <tr>
		         <td>
		             <c:out value="ENRIQUE GONZÁLEZ BOZA"/>
		         </td>
		         
		         <td>
		         	<c:out value="Development Team"/>
		         </td>
	         </tr>
	         
	         <tr>
		         <td>
		             <c:out value="VICTOR MONTESEIRIN PUIG"/>
		         </td>
		         
		         <td>
		         	<c:out value="Development Team"/>
		         </td>
	         </tr>
        </tbody>
    </table>

</petclinic:layout>