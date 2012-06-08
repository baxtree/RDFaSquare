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
			var guess_loader = document.getElementById("guessloader_"+index);
			guess_loader.style.display = "block";
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