<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<input type="hidden" name="eimvalidation" id="eimvalidation" value="${stat}">
<c:if test="${stat eq 1}">
 <font color="red">System EIM already registered in Juniper</font>
</c:if>

<c:if test="${stat eq 0}">
 <font color="green">System EIM is unique</font>
</c:if>