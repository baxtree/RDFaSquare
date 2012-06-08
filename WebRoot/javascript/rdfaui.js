	$(document).ready(function() {
		$('.rdfasnippet').click(function() {
			var original = this;
			var $dialog_node = $('<div id="dialog"><label for="rawcode">Rawcode:</label><br/><textarea id="rawcodeupdate" rows="5" cols="35">'+original.innerHTML+'</textarea></div>');
			var $dialog = $dialog_node 
			.dialog({
				modal: true,
				autoOpen: false,
				title: 'Free Editing',
			});
			$dialog.dialog('option', 'buttons', {"Save" : function(){	
																		original.innerHTML = $("#rawcodeupdate").val(); 
																		$(this).dialog("close");
																		$("#dialog").remove();
																		var document_src = "";
																		if(window.XMLHttpRequest){
																			var serializer = new XMLSerializer();
																			document_src = serializer.serializeToString(document);
																		}
																		else if(window.ActiveXObject){
																			document_src = document.documentElement.outerHTML;  
																		}
																		//alert(document_src);
																		parent.document.getElementById("xhtmlrdfa").value = document_src;/*TODO this does not contain the whole page content (e.g., no doctype, no html tag)*/
																		//alert(document.documentElement.innerHTML);
																	}, 
												"Cancel" : function(){$(this).dialog("close");$("#dialog").remove();}});
			$dialog.dialog('option', 'width', 400);
			$dialog.dialog('open');
		});
	});
	
	function guess_topic_with_ui(id){
			var index = id.split("\_")[1];
			var rdf_url = document.getElementById("rdfurl_"+index).value;
			if(!rdf_url.startsWith("http://") || rdf_url == "" || rdf_url == null || rdf_url == undefined){
				alert("Please input the RDF context file URL first!");
			}
			else{
				var guess_loader = document.getElementById("guessloader_"+index);
				guess_loader.style.display = "block";
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
								var original = this;
								var $dialog_node = $('<div id="topicnodeselection">'+req1.responseText+'</div>');
								var $dialog = $dialog_node 
								.dialog({
									modal: true,
									autoOpen: false,
									title: 'Please select a topic node',
								});
								$dialog.dialog('option', 'buttons', {"Confirm" : function(){$(this).dialog("close");$("#topicnodeselection").remove();}});
								$dialog.dialog('option', 'width', 460);
								$dialog.dialog('open');
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
		}
		
		function create_template_with_ui(){
			document.getElementById("template_loader").style.display = "block";
			var rdf_url = document.getElementById("rdfurl_0").value;
			var topic_uri = document.getElementById("topicuri_0").value;
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
                        	document.getElementById("template_loader").style.display = "none";
							var $dialog_node = $("<textarea id=\"template_0\" rows=\"15\" cols=\"65\" value=\"\"></textarea>");
							$dialog_node.text(req.responseText);
							var $dialog = $dialog_node
							.dialog({
									modal: true,
									autoOpen: false,
									title: 'Template Editing',
							});
							$dialog.dialog('option', 'buttons', {"Apply this template" : function(){$(this).dialog("close");$("#topicnodeselection").remove();applyTemplate();}});
							$dialog.dialog('option', 'width', 800);
							$dialog.dialog('open');					
						}
					}
				}
			}
			req.send(null);
		}