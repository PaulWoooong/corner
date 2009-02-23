ganshane = {
	all_validations:{},
	presence : function(field, options) {
		lv = this.createOrGetValidation(field);
		lv.add( Validate.Presence,options);
	},
	confirmation : function(field, options) {
		lv =  this.createOrGetValidation(field);
		lv.add( Validate.Confirmation,options);
	},
	email:function(field,options){
		lv =  this.createOrGetValidation(field);
		lv.add( Validate.Email,options);
	},
	Length:function(field,options){
		lv =  this.createOrGetValidation(field);
		lv.add( Validate.Length,options);
	},
	Numericality:function(field,options){																	
		lv =  this.createOrGetValidation(field);
		lv.add( Validate.Numericality,options);
	},
	
	createOrGetValidation:function(field){
		lv = this.all_validations[field];
		if (lv == null) {
			lv = new LiveValidation(field, {
				onValid: function(){
					this.removeMessage();
					this.addFieldClass();
				}
			});
			this.all_validations[field] = lv;
		}
		return lv;
	}
};
Tapestry.Initializer.liveValidation=function(field,validator,specs){
	var vfunc = ganshane[validator];
	vfunc.call(ganshane, field,specs);
	
}
