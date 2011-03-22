
Calendar2 = function(min,max,inputField,trigger,dateFormat,showTime,weekNumbers,language,onSelectFunc,minuteStep){
   var inputField_date = Calendar.setup({
						  min:        min,
						  max:        max,
						  inputField: inputField,
						  trigger:    inputField,
					      onSelect:   function(cal) {cal.hide() },
					      dateFormat: dateFormat,
					      showTime:   showTime,
					      weekNumbers:weekNumbers,
					      minuteStep:minuteStep
						});
								   
	inputField_date.setLanguage(language);
	
	//增加监听事件
	if(onSelectFunc && onSelectFunc != "" && onSelectFunc != null){
		inputField_date.addEventListener("onSelect",onSelectFunc);	
	}
}