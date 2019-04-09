<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<input type="hidden" name="psidvalidation" id="psidvalidation" value="${stat}">
<c:if test="${stat eq 1}">
 <font color="red">PSID already registered in Juniper</font>
</c:if>

<c:if test="${stat eq 0}">
 <font color="green">Valid PSID</font>
</c:if>