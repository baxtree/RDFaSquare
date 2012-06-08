$(document).ready(function() {
		$('.rdfasnippet').click(function() {
			var original = this;
			var $dialog_node = $('<div id="dialog"><label for="rawcode">Rawcode:</label><br/><textarea id="rawcodeupdate" rows="5" cols="25">'+original.innerHTML+'</textarea></div>');
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
			$dialog.dialog('open');
		});
	});