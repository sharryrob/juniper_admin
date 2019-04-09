
<fieldset class="fs">
	<div>
		<div id="dyn1">
			<div class="form-group row">
				<div class="col-sm-6">
					<label>Project ID*</label> <input type="text" class="form-control"
						id="project_id" name="project_id" placeholder="Project ID"
						value="${project_id}" readonly="readonly">
				</div>
				<div class="col-sm-6">
					<label>Project Name*</label> <input type="text"
						class="form-control" id="project_name" name="project_name"
						placeholder="Project Name" value="${project_name}">
				</div>
			</div>
			<div class="form-group row">
				<div class="col-sm-6">
					<label>Project Owner*</label> <input type="text"
						class="form-control" id="project_owner" name="project_owner"
						placeholder="Project Owner" value="${project_owner}">
				</div>
				<div class="col-sm-6">
					<label>Project Description*</label> <input type="text"
						class="form-control" id="project_description"
						name="project_description" placeholder="Project Description"
						value="${project_description}">
				</div>
			</div>
			<div class="form-group row">
				<div class="col-sm-6">
					<label>GSD Group *</label> <input type="text" class="form-control"
						id="gsd_group" name="gsd_group" placeholder="GSD Group"
						value="${gsd_group}">
				</div>
				<div class="col-sm-6">
					<label>GSD Key *</label> <input type="text" class="form-control"
						id="gsd_key" name="gsd_key" placeholder="GSD Key"
						value="${gsd_key}">
				</div>
			</div>
		</div>
	</div>
</fieldset>
<button onclick="jsonconstruct('upd');"
	class="btn btn-rounded btn-gradient-info mr-2">Update</button>
<!-- <button onclick="jsonconstruct('del');"
			class="btn btn-rounded btn-gradient-info mr-2">Delete</button> -->