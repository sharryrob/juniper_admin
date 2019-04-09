<jsp:include page="../cdg_header.jsp" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
	function jsonconstruct(val) {
		var data = {};
		document.getElementById('button_type').value = val;
		$(".form-control").serializeArray().map(function(x) {
			data[x.name] = x.value;
		});
		var x = '{"header":{"user":"info@clouddatagrid.com","service_account":"Extraction_CDG_UK","reservoir_id":"R0001","event_time":"today"},"body":{"data":'
				+ JSON.stringify(data) + '}}';
		document.getElementById('x').value = x;
		//alert(x);
		//console.log(x);
		document.getElementById('ProjectDetails').submit();
	}
	var i = 1;
	function dup_div() {
		var dyn = document.getElementById('dyn1');
		var dyndiv = dyn.cloneNode(true);
		var x = ++i;
		dyndiv.id = "dyn" + i;
		dyndiv.getElementsByTagName('input')[0].id = "target_unique_name" + i;
		dyndiv.getElementsByTagName('input')[0].name = "target_unique_name" + i;
		dyndiv.getElementsByTagName('select')[0].id = "target_type" + i;
		dyndiv.getElementsByTagName('select')[0].name = "target_type" + i;
		dyndiv.getElementsByTagName('input')[1].id = "target_project" + i;
		dyndiv.getElementsByTagName('input')[1].name = "target_project" + i;
		dyndiv.getElementsByTagName('input')[2].id = "service_account" + i;
		dyndiv.getElementsByTagName('input')[2].name = "service_account" + i;
		dyndiv.getElementsByTagName('input')[3].id = "target_bucket" + i;
		dyndiv.getElementsByTagName('input')[3].name = "target_bucket" + i;
		dyndiv.getElementsByTagName('input')[4].id = "knox_url" + i;
		dyndiv.getElementsByTagName('input')[4].name = "knox_url" + i;
		dyndiv.getElementsByTagName('input')[5].id = "hadoop_path" + i;
		dyndiv.getElementsByTagName('input')[5].name = "hadoop_path" + i;
		dyndiv.getElementsByTagName('input')[6].id = "username" + i;
		dyndiv.getElementsByTagName('input')[6].name = "username" + i;
		dyndiv.getElementsByTagName('input')[7].id = "password" + i;
		dyndiv.getElementsByTagName('input')[7].name = "password" + i;
		dyndiv.getElementsByClassName('gx')[0].id = "g" + i;
		dyndiv.getElementsByClassName('hx')[0].id = "h" + i;
		dyn.parentNode.appendChild(dyndiv);
		document.getElementById('counter').value = i;
	}
	function sys_typ(id, val) {
		var in1 = id.slice(-1);
		var in2 = id.slice(-2, -1);
		if (in2 === "e")
			;
		else {
			in1 = id.slice(-2);
		}
		var in3 = 'g' + in1;
		var in4 = 'h' + in1;
		if (val == "GCS") {
			document.getElementById(in3).style.display = "block";
			document.getElementById(in4).style.display = "none";
		} else if (val == "HDFS") {
			document.getElementById(in4).style.display = "block";
			document.getElementById(in3).style.display = "none";
		}
	}

	function chg() {
		if (document.getElementById("project_id_sel").value == "") {
			window.location.reload();
		} else {
			var projId = document.getElementById("project_id_sel").value;
			$.post('${pageContext.request.contextPath}/admin/getProjectDetails', {
				projId : projId
			}, function(data) {
				$('#cud').html(data)
			});
		}
	}

	function funccheck(val) {
		if (val == 'create') {
			window.location.reload();
		} 
		else {
			document.getElementById('projfunc').style.display = "block";
		}
	}
</script>
<div class="main-panel">
	<div class="content-wrapper">
		<div class="row">
			<div class="col-12 grid-margin stretch-card">
				<div class="card">
					<div class="card-body">
						<h4 class="card-title">On-Board Project</h4>
						<p class="card-description">Project Details</p>
						<div class="mt-3" align="center">

							<%
								if (request.getAttribute("successString") != null) {
							%>
							<p class="text-success h4">${successString}</p>
							<%
								}
							%>
							<%
								if (request.getAttribute("errorString") != null) {
							%>
							<p class="text-danger h4">${errorString}</p>
							<%
								}
							%>
						</div>
						<form class="forms-sample" id="ProjectDetails"
							name="ProjectDetails" method="POST"
						action="${pageContext.request.contextPath}/admin/addProjectDetails" enctype="application/json">
							<input type="hidden" name="x" id="x" value=""> 
							<input type="hidden" name="button_type" id="button_type" value="">
							<div class="form-group row">
								<label class="col-sm-3 col-form-label">Project</label>
								<div class="col-sm-4">
									<div class="form-check form-check-info">
										<label class="form-check-label"> <input type="radio"
											class="form-check-input" name="radio" id="radio1"
											checked="checked" value="create"
											onclick="funccheck(this.value)"> Create
										</label>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-check form-check-info">
										<label class="form-check-label"> <input type="radio"
											class="form-check-input" name="radio" id="radio2"
											value="edit" onclick="funccheck(this.value)"> Edit/View
										</label>
									</div>
								</div>
							</div>
							<div class="form-group" id="projfunc" style="display: none;">
								<label>Select Project</label> <select name="project_id_sel"
									id=project_id_sel class="form-control" onchange="chg()">
									<option value="" selected disabled>Select Project ...</option>
									<c:forEach items="${proj_val}" var="proj_val">
										<option value="${proj_val}">${proj_val}</option>
									</c:forEach>
								</select>
							</div>
							<div id="cud">					
							<fieldset class="fs">
							<div>
							<div id="dyn1">
									<div class="form-group row">
										<div class="col-sm-6">
											<label>Project ID *</label> <input type="text"
												class="form-control" id="project_id" name="project_id"
												placeholder="Project ID">
										</div>
										<div class="col-sm-6">
											<label>Project Name *</label> <input type="text"
												class="form-control" id="project_name" name="project_name"
												placeholder="Project Name">
										</div>
									</div>
									<div class="form-group row">
										<div class="col-sm-6">
											<label>Project Owner *</label> <input type="text"
												class="form-control" id="project_owner" name="project_owner"
												placeholder="Project Owner">
										</div>
										<div class="col-sm-6">
											<label>Project Description *</label> <input type="text"
												class="form-control" id="project_description"
												name="project_description" placeholder="Project Description">
										</div>
									</div>
									<div class="form-group row">
										<div class="col-sm-6">
											<label>GSD Group *</label> <input type="text"
												class="form-control" id="gsd_group" name="gsd_group"
												placeholder="GSD Group">
										</div>
										<div class="col-sm-6">
											<label>GSD Key *</label> <input type="text"
												class="form-control" id="gsd_key"
												name="gsd_key" placeholder="GSD Key">
										</div>
									</div>
								</div>
							</div>
							
							</fieldset>
							<button onclick="jsonconstruct('add');" id="save" name="save"
								class="btn btn-rounded btn-gradient-info mr-2">Save</button>
							<button id="createsystem" name="createsystem" hidden="true"
								class="btn btn-rounded btn-gradient-info mr-2">Create
								System</button>
							</div>	
						</form>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="../cdg_footer.jsp" />