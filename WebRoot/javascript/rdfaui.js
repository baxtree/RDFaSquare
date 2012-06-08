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
																		$(this).dialog("distroy");
																		$("#dialog").remove();
																		parent.document.getElementById("xhtmlrdfa").value = document.documentElement.innerHTML;
																		//alert(document.documentElement.innerHTML);
																	}, 
												"Cancel" : function(){$(this).dialog("close");$(this).dialog("distroy");$("#dialog").remove();}});
			$dialog.dialog('open');
		});
	});