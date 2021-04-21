<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="adoptionApplication">
	<h2>
		<fmt:message key="adoptionRequest"/>
	</h2>
	<form:form modelAttribute="adoptionApplication" class="form-horizontal" id="add-adoptionApplication-form">
		<div class="form-group has-feedback">
			<fmt:message var="descriptionOfCare" key="descriptionOfCare"/>
			<petclinic:inputField label="${descriptionOfCare}" name="description" />
			<input type="hidden" name="ownerId" value="${adoptionApplication.owner.id}">
			
		</div>
		<div class="form-group">
			<div class="col-sm-offset-2 col-sm-10">
				<button class="btn btn-default" type="submit"><fmt:message key="sendAdoptionRequest"/></button>
			</div>
		</div>
	</form:form>
</petclinic:layout>
