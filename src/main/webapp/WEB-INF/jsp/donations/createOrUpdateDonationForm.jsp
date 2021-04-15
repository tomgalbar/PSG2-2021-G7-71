<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="donations">
    <h2>
        <fmt:message key="newDonation"/>
    </h2>
    <form:form modelAttribute="donation" class="form-horizontal" id="add-donation-form">
        <div class="form-group has-feedback">
        	<fmt:message var="clientName" key="clientName"/>
        	<fmt:message var="amount" key="amount"/>
        	
            <petclinic:inputField label="${clientName}" name="client"/>
            <petclinic:inputField label="${amount}" name="amount"/>

        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
            	<button class="btn btn-default" type="submit"><fmt:message key="addDonation"/></button>
            </div>
        </div>
    </form:form>
</petclinic:layout>