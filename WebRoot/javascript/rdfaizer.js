		var xhtmlrdfa_backup = "";
		
		function transform(type) {
			var complete_page_loader = document.getElementById("complete_page_loader");
			complete_page_loader.style.display = "block";
			var flag = document.getElementById("addtopiccontext").disabled;
			var req = null;
			var url = "";
			var para = "";
			if(window.XMLHttpRequest){
            //alert("firefox");
                req = new XMLHttpRequest();
            }
            else if(window.ActiveXObject){
            //alert("ie");
                req = new ActiveXObject("Microsoft.XMLHTTP");
            }
			if(flag == true){
				var rdfurl = document.getElementById("rdfurl_0").value;
				var topicuris = document.getElementsByName("topicuri");
				var topic_uri = "";
				for(var i = 0; i < topicuris.length; i++){
					topic_uri += topicuris[i].value + ",rdfa2delimiter,";
				}	
				url = "/rdfasquare/baxtree/apis/SingleCotextTransformer";
				para = "rdfurl="+encodeURIComponent(rdfurl)+"&topicuris="+encodeURIComponent(topic_uri)+"&type="+type;
			}
			else if(flag == false){
				var rdfurls = document.getElementsByName("rdfurl");
				var rdf_url = "";
				for(var i = 0; i < rdfurls.length; i++){
					rdf_url += rdfurls[i].value + ",rdfa2delimiter,";
				}
				var topicuris = document.getElementsByName("topicuri");
				var topic_uri = "";
				for(var i = 0; i < topicuris.length; i++){
					topic_uri += topicuris[i].value + ",rdfa2delimiter,";
				}	
				url = "/rdfasquare/baxtree/apis/FederatedTransformer";
				para = "rdfurls="+encodeURIComponent(rdf_url)+"&topicuris="+encodeURIComponent(topic_uri)+"&type="+type;
	        }
	        else{
	        	alert("error!")
	        }
	        if(req){
	                req.open("POST", url, true);
	                req.onreadystatechange = function(){
	                    if(req.readyState == 4){
	                        if(req.status == 200){
								document.getElementById("xhtmlrdfa").value = req.responseText;
								xhtmlrdfa_backup = req.responseText;
								update();
								complete_page_loader.style.display = "none";
							}
						}
					}
				}
			req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	        req.setRequestHeader("Content-length", para.length);
	        req.setRequestHeader("Connection", "close");
	        req.send(para);
		}
		
		function vocab_transform(type) {
			var vocabulary_loader = document.getElementById("vocabulary_loader");
			vocabulary_loader.style.display = "block";
			var rdfurl = document.getElementById("rdfurl_0").value;
			var para = "";
			//alert(rdfurl);
			var req = null;
			if(window.XMLHttpRequest){
            //alert("firefox");
                req = new XMLHttpRequest();
            }
            else if(window.ActiveXObject){
            //alert("ie");
                req = new ActiveXObject("Microsoft.XMLHTTP");
            }
			var url = "/rdfasquare/baxtree/apis/VocabTransformer";
			para = "rdfurl="+encodeURIComponent(rdfurl)+"&type="+type;
			if(req){
                req.open("POST", url, true);
                req.onreadystatechange = function(){
                    if(req.readyState == 4){
                        if(req.status == 200){
							document.getElementById("xhtmlrdfa").value = req.responseText;
							xhtmlrdfa_backup = req.responseText;
							update();
							vocabulary_loader.style.display = "none";
						}
					}
				}
			}
            req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	        req.setRequestHeader("Content-length", para.length);
	        req.setRequestHeader("Connection", "close");
	        req.send(para);
		}
		
		function guess_topic(id){
			var guess_loader = document.getElementById("guess_loader");
			guess_loader.style.display = "block";
			var index = id.split("\_")[1];
			var rdf_url = document.getElementById("rdfurl_"+index).value;
			var req1 = null;
			if(window.XMLHttpRequest){
            //alert("firefox");
                req1 = new XMLHttpRequest();
            }
            else if(window.ActiveXObject){
            //alert("ie");
                req1 = new ActiveXObject("Microsoft.XMLHTTP");
            }
			var url = "/rdfasquare/baxtree/apis/TopicURIGuesser";
			var para = "rdfurl=" + encodeURIComponent(rdf_url) + "&index=" + index;
			if(req1){
				req1.open("POST", url, true);
                req1.onreadystatechange = function(){
                    if(req1.readyState == 4){
                        if(req1.status == 200){
							var div_suggestion = document.getElementById("suggestion_"+index);
							div_suggestion.innerHTML = req1.responseText;
							div_suggestion.style.display = "block";
							guess_loader.style.display = "none";
						}
					}
				}
			}
			req1.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	        req1.setRequestHeader("Content-length", para.length);
	        req1.setRequestHeader("Connection", "close");
	        req1.send(para);
		}
		
		function select_topic(uri, index){
			document.getElementById("topicuri_"+index).value = uri;
		}
		
		var form_count = 1;
		function add_url_context(){
			document.getElementById("addtopic").disabled = true;
			var forms = document.getElementById("forms");
			var new_form = document.createElement("form");
			var hr = document.createElement("hr");
			var label1 = document.createElement("label");
			label1.appendChild(document.createTextNode("RDF context file URL: "));
			var input1 = document.createElement("input");
			input1.name = "rdfurl";
			input1.id = "rdfurl_"+form_count;
			input1.size = 100;
			var label2 = document.createElement("label");
			label2.appendChild(document.createTextNode("Topic URI: "));
			var input2 = document.createElement("input");
			input2.name = "topicuri";
			input2.id = "topicuri_"+form_count;
			input2.size = 100;
			var div_suggestion = document.createElement("div");
			div_suggestion.id = "suggestion_"+form_count;
			div_suggestion.style.display = "none";
			var guess_button = document.createElement("input");
			guess_button.id = "guess_"+form_count;
			guess_button.type = "button";
			guess_button.value = "Make a guess!";
			guess_button.onclick = function(){guess_topic(this.id)};
			guess_button.appendChild(document.createTextNode("Make a guess!"));
			new_form.appendChild(hr);
			new_form.appendChild(label1);
			new_form.appendChild(document.createElement("br"));
			new_form.appendChild(input1);
			new_form.appendChild(document.createElement("br"));
			new_form.appendChild(label2);
			new_form.appendChild(guess_button);
			new_form.appendChild(document.createElement("br"));
			new_form.appendChild(input2);
			new_form.appendChild(document.createElement("br"));
			new_form.appendChild(div_suggestion);
			forms.appendChild(new_form);
			form_count++;
		}
		
		function add_input_context(){
			document.getElementById("addtopic").disabled = true;
			var forms = document.getElementById("forms");
			var new_form = document.createElement("form");
			var hr = document.createElement("hr");
			var label1 = document.createElement("label");
			label1.appendChild(document.createTextNode("RDF context from direct input: "));
			var input1 = document.createElement("textarea");
			input1.name = "rdfurl";
			input1.id = "rdfurl_"+form_count;
			input1.cols = 90;
			input1.rows = 20;
			var label2 = document.createElement("label");
			label2.appendChild(document.createTextNode("Topic URI: "));
			var input2 = document.createElement("input");
			input2.name = "topicuri";
			input2.id = "topicuri_"+form_count;
			input2.size = 100;
			var div_suggestion = document.createElement("div");
			div_suggestion.id = "suggestion_"+form_count;
			div_suggestion.style.display = "none";
			var guess_button = document.createElement("input");
			guess_button.id = "guess_"+form_count;
			guess_button.type = "button";
			guess_button.value = "Make a guess!";
			guess_button.onclick = function(){guess_topic(this.id)};
			guess_button.appendChild(document.createTextNode("Make a guess!"));
			new_form.appendChild(hr);
			new_form.appendChild(label1);
			new_form.appendChild(document.createElement("br"));
			new_form.appendChild(input1);
			new_form.appendChild(document.createElement("br"));
			new_form.appendChild(label2);
			new_form.appendChild(guess_button);
			new_form.appendChild(document.createElement("br"));
			new_form.appendChild(input2);
			new_form.appendChild(document.createElement("br"));
			new_form.appendChild(div_suggestion);
			forms.appendChild(new_form);
			form_count++;
		}
		
		var topic_count = 1;
		function add_topic(){
			document.getElementById("addtopiccontext").disabled = true;
			var form = document.getElementById("firstform");
			var label = document.createElement("label");
			label.appendChild(document.createTextNode("Topic URI: "));
			var input = document.createElement("input");
			input.name = "topicuri";
			input.id = "topicuri_" + topic_count;
			input.size = 100;
			form.appendChild(label);
			form.appendChild(document.createElement("br"));
			form.appendChild(input);
			form.appendChild(document.createElement("br"));
			topic_count++;
		}
		
		function strip_indicators(){
			var xhtmlrdfa = document.getElementById("xhtmlrdfa");
			//alert(xhtmlrdfa.value);
			if(xhtmlrdfa.value == "" || xhtmlrdfa == null){
				alert("Please make sure the XHTML+RDFa source code is not null");
			}
			else{
				xhtmlrdfa_backup = xhtmlrdfa.value; //TODO striper is not working very well.
				xhtmlrdfa.value = xhtmlrdfa.value.replace(/\[[^\[\]]+\]/g, "");
				update();
			}
		}
		
		function show_indicators(){
			if(confirm("This will distroy part of your preexisting annotations. Are you sure?")){
				var xhtmlrdfa = document.getElementById("xhtmlrdfa");
				if(xhtmlrdfa.value == "" || xhtmlrdfa == null){
					alert("Please make sure the XHTML+RDFa source code is not null");
				}
				else{
					xhtmlrdfa.value = xhtmlrdfa_backup;
					update();
				}
			}
			else{
				
			}
		}
		
		function createTemplate() {
			document.getElementById("rdfurl").disabled = "true";
			document.getElementById("topicuri").disabled = "true";
			var rdf_url = document.getElementById("rdfurl").value;
			var topic_uri = document.getElementById("topicuri").value;
			var req = null;
			if(window.XMLHttpRequest){
            //alert("firefox");
                req = new XMLHttpRequest();
            }
            else if(window.ActiveXObject){
            //alert("ie");
                req = new ActiveXObject("Microsoft.XMLHTTP");
            }
			url = "/rdfasquare/baxtree/apis/TemplateCreator?rdfurl="+encodeURIComponent(rdf_url)+"&topicuri="+encodeURIComponent(topic_uri);
			if(req){
                req.open("GET", url, true);
                req.onreadystatechange = function(){
                    if(req.readyState == 4){
                        if(req.status == 200){
							document.getElementById("template").value = req.responseText;
						}
					}
				}
			}
			req.send(null);
		}
		
		function applyTemplate(){
			document.getElementById("template").disabled = "true";
			var rdf_url = document.getElementById("rdfurl").value;
			var topic_uri = document.getElementById("topicuri").value;
			var template = document.getElementById("template").value;
			var req;
			if(window.XMLHttpRequest){
				req = new XMLHttpRequest();
			}
			else if(window.ActiveXObject){
				req = new ActiveXObject("Microsoft.XMLHTTP");
			}
			var url = "/rdfasquare/baxtree/apis/TemplateApplier";
			var para = "template="+encodeURIComponent(template)+"&rdfurl="+encodeURIComponent(rdf_url)+"&topic="+encodeURIComponent(topic_uri);
			if(req){
				req.open("POST", url, true);
				req.onreadystatechange = function () {
					if(req.readyState == 4){
						if(req.status == 200){
							document.getElementById("xhtmlrdfa").value = req.responseText;
							xhtmlrdfa_backup = req.responseText;
							update();
						}
					}
				}
			}
			req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
            req.setRequestHeader("Content-length", para.length);
            req.setRequestHeader("Connection", "close");
            req.send(para);
		}
		
		function update(){
			window.frames["preview"].document.write(document.getElementById("xhtmlrdfa").value);
			window.frames["preview"].document.close();
			window.document.close();
		}
				
		function copy_annotations(){//TODO this does not work and need fixed
			var annotations = document.getElementById("xhtmlrdfa").value;
			setClipboard(annotations);
		}
		
		function setClipboard(maintext) {
		   if (window.clipboardData) {
		      return (window.clipboardData.setData("Text", maintext));
		   }
		   else if (window.netscape) {
		      netscape.security.PrivilegeManager.enablePrivilege('UniversalXPConnect');
		      var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
		      if (!clip) return;
		      var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
		      if (!trans) return;
		      trans.addDataFlavor('text/unicode');
		      var str = new Object();
		      var len = new Object();
		      var str = Components.classes["@mozilla.org/supports-string;1"].createInstance(Components.interfaces.nsISupportsString);
		      var copytext=maintext;
		      str.data=copytext;
		      trans.setTransferData("text/unicode",str,copytext.length*2);
		      var clipid=Components.interfaces.nsIClipboard;
		      if (!clip) return false;
		      clip.setData(trans,null,clipid.kGlobalClipboard);
		      return true;
		   }
		   return false;
		}
		
		function getClipboard() {
		   if (window.clipboardData) {
		      return(window.clipboardData.getData('Text'));
		   }
		   else if (window.netscape) {
		      netscape.security.PrivilegeManager.enablePrivilege('UniversalXPConnect');
		      var clip = Components.classes['@mozilla.org/widget/clipboard;1'].createInstance(Components.interfaces.nsIClipboard);
		      if (!clip) return;
		      var trans = Components.classes['@mozilla.org/widget/transferable;1'].createInstance(Components.interfaces.nsITransferable);
		      if (!trans) return;
		      trans.addDataFlavor('text/unicode');
		      clip.getData(trans,clip.kGlobalClipboard);
		      var str = new Object();
		      var len = new Object();
		      try {
		         trans.getTransferData('text/unicode',str,len);
		      }
		      catch(error) {
		         return null;
		      }
		      if (str) {
		         if (Components.interfaces.nsISupportsWString) str=str.value.QueryInterface(Components.interfaces.nsISupportsWString);
		         else if (Components.interfaces.nsISupportsString) str=str.value.QueryInterface(Components.interfaces.nsISupportsString);
		         else str = null;
		      }
		      if (str) {
		         return(str.data.substring(0,len.value / 2));
		      }
		   }
		   return null;
		}
		
/*		function glean_triples(){
			var triples_loader = document.getElementById("triples_loader");
			triples_loader.style.display = "block";
			var format_element = document.getElementById("serialization");
			var format = format_element.options[format_element.selectedIndex].text;
			var annotations = document.getElementById("xhtmlrdfa").value;
			if(window.XMLHttpRequest){
				req = new XMLHttpRequest();
			}
			else if(window.ActiveXObject){
				req = new ActiveXObject("Microsoft.XMLHTTP");
			}
			var url = "/rdfasquare/baxtree/apis/TriplesGleaner";
			var para = "rdfacontent="+encodeURIComponent(annotations)+"&baseuri="+""+"&format="+encodeURI(format);
			if(req){
				req.open("POST", url, true);
				req.onreadystatechange = function () {
					if(req.readyState == 4){
						if(req.status == 200){
							alert(req.responseText);
							triples_loader.style.display = "none";
						}
					}
				}
				req.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
	            req.setRequestHeader("Content-length", para.length);
	            //req.setRequestHeader("Connection", "close");
	            req.send(para);
			}
		}
*/
		
		function glean_triples(){
			var glean_form = document.getElementById("glean_form"); 
			glean_form.rdfacontent.value = document.getElementById("xhtmlrdfa").value;
			glean_form.baseuri.value = ""; //TODO this needs to be improved. 
			var format_element = document.getElementById("serialization"); 
			glean_form.format.value = format_element.options[format_element.selectedIndex].text; 
			glean_form.submit();
		}