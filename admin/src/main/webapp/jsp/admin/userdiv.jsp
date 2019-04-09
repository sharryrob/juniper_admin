<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${stat eq 0}">
	<div class="col-sm-12">
		<label>Select Groups</label> <select name="groupx" id="groupx"
			class="form-control" multiple="multiple">
			<c:forEach items="${groupp}" var="groupp">
					<option value="${groupp.group_sequence}" selected>${groupp.group_name}</option>
			</c:forEach>
			<c:forEach items="${group}" var="group">
				<option value="${group.group_sequence}">${group.group_name}</option>
			</c:forEach>
		</select>
	</div>
</c:if>
<c:if test="${stat eq 1}">
	<div class="col-sm-12">
		<font color="red">User ID does not exist</font>
	</div>
</c:if>
<script>
	var select = document.getElementById('groupx');
	multi(select, {});
</script>

