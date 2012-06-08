	$(document).ready(function() {
		$('.rdfasnippet').click(function() {
			var original = this;
			var $dialog_node = $('<div id="dialog"><label for="rawcode">Rawcode:</label><br/><textarea id="rawcodeupdate" rows="5" cols="35">'+original.innerHTML+'</textarea></div>');
			var $dialog = $dialog_node 
			.dialog({
				modal: true,
				autoOpen: false,
				title: 'RDFa Editing',
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
			$dialog.dialog('option', 'close', function(ev, ui) {$(this).remove(); });
			$dialog.dialog('open'); 
		});
	});
	
	function guess_topic_request(index, rdf_url){
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
								$dialog.dialog('option', 'close', function(ev, ui) {$(this).remove(); });
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
	
	function guess_topic_with_ui(id){
			var index = id.split("\_")[1];
			var rdf_url = document.getElementById("rdfurl_"+index).value;
			if(window.location.href.endsWith("index2.html")){//TODO this is not decent.
				guess_topic_request(index, rdf_url);
			}
			else{
				if(!rdf_url.startsWith("http://") || rdf_url == "" || rdf_url == null || rdf_url == undefined){
					alert("Please input the RDF context first!");
				}
				else{
					guess_topic_request(index, rdf_url);
			    }
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
							$dialog.dialog('option', 'buttons', {"Apply this template" : function(){$(this).dialog("close");$("#template0").val(req.responseText);$("#template_0").remove();applyTemplate();}});
							$dialog.dialog('option', 'width', 800);
							$dialog.dialog('option', 'close', function(ev, ui) {$(this).remove(); });
							$dialog.dialog('open');					
						}
					}
				}
			}
			req.send(null);
		}
		
		
		function show_editor(){
			var $dialog_node = $('<div id="editor"><textarea id="richeditor" rows="10" cols="70">'+$('#xhtmlrdfa').val()+'</textarea></div>');
			var $dialog = $dialog_node
							.dialog({
									modal: true,
									autoOpen: false,
									title: 'Free Editing',
							});

			var editor = CKEDITOR.replace( 'richeditor',
			{
				fullPage : true,
				toolbar: [	
			    	['Source'],
			        ['Cut','Copy','Paste','PasteText','PasteFromWord','-','Scayt'],
			        ['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],
			        ['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
			        '/',
			        ['Styles','Format'],
			        ['Bold','Italic','Strike'],
			        ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],
			        ['Link','Unlink','Anchor'],
			    ],
				/*XHTML_CSS
				contentsCss : 'ckeditor/_samples/assets/output_xhtml.css',
				coreStyles_bold	: { element : 'span', attributes : {'class': 'Bold'} },
				coreStyles_italic	: { element : 'span', attributes : {'class': 'Italic'}},
				coreStyles_underline	: { element : 'span', attributes : {'class': 'Underline'}},
				coreStyles_strike	: { element : 'span', attributes : {'class': 'StrikeThrough'}, overrides : 'strike' },
				coreStyles_subscript : { element : 'span', attributes : {'class': 'Subscript'}, overrides : 'sub' },
				coreStyles_superscript : { element : 'span', attributes : {'class': 'Superscript'}, overrides : 'sup' },
				font_names : 'Comic Sans MS/FontComic;Courier New/FontCourier;Times New Roman/FontTimes',
				font_style :
				{
						element		: 'span',
						attributes		: { 'class' : '#(family)' },
						overrides	: [ { element : 'span', attributes : { 'class' : /^Font(?:Comic|Courier|Times)$/ } } ]
				},
				fontSize_sizes : 'Smaller/FontSmaller;Larger/FontLarger;8pt/FontSmall;14pt/FontBig;Double Size/FontDouble',
				fontSize_style :
					{
						element		: 'span',
						attributes	: { 'class' : '#(size)' },
						overrides	: [ { element : 'span', attributes : { 'class' : /^Font(?:Smaller|Larger|Small|Big|Double)$/ } } ]
					} ,
				colorButton_enableMore : false,
				colorButton_colors : 'FontColor1/FF9900,FontColor2/0066CC,FontColor3/F00',
				colorButton_foreStyle :
					{
						element : 'span',
						attributes : { 'class' : '#(color)' },
						overrides	: [ { element : 'span', attributes : { 'class' : /^FontColor(?:1|2|3)$/ } } ]
					},
				colorButton_backStyle :
					{
						element : 'span',
						attributes : { 'class' : '#(color)BG' },
						overrides	: [ { element : 'span', attributes : { 'class' : /^FontColor(?:1|2|3)BG$/ } } ]
					},
				indentClasses : ['Indent1', 'Indent2', 'Indent3'],
				justifyClasses : [ 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyFull' ],
				stylesSet :
						[
							{ name : 'Strong Emphasis', element : 'strong' },
							{ name : 'Emphasis', element : 'em' },

							{ name : 'Computer Code', element : 'code' },
							{ name : 'Keyboard Phrase', element : 'kbd' },
							{ name : 'Sample Text', element : 'samp' },
							{ name : 'Variable', element : 'var' },

							{ name : 'Deleted Text', element : 'del' },
							{ name : 'Inserted Text', element : 'ins' },

							{ name : 'Cited Work', element : 'cite' },
							{ name : 'Inline Quotation', element : 'q' }
						]
				*/
				
			});
			$dialog.dialog('option', 'buttons', {"Save" : 	function(){
																$("#xhtmlrdfa").val(editor.getData()); 
																editor.destroy();
																editor = null;
																$("richeditor").remove();
																$("editor").remove();
																update();
																$(this).dialog("close");
																$("#dialog").remove();
														 	}});
			$dialog.dialog('option', 'width', 900);
			$dialog.dialog('option', 'height', 600);
			$dialog.dialog('option', 'close', function(ev, ui) {$(this).remove(); });
			$dialog.dialog('open');	
		}
		
		function open_screencast(id){
			var $dialog_node = $("#"+id+"screencast");
			//var $dialog_node = $("foafffscreencast");
			var $dialog = $dialog_node.dialog({
							modal: true,
							autoOpen: false,
							title: "screencast",
						});
			$dialog.dialog('option', 'buttons', {"Close" : function(){$(this).dialog("close");}});
			$dialog.dialog('option', 'width', 685);
			$dialog.dialog('option', 'close', function(ev, ui) {$(this).remove();});
			$dialog.dialog('open');
		}
		
		function highlight_rdfa_snippet(){
			var all_tags = document.getElementsByTagName("span");
			for(var i = 0; i < all_tags.length; i++){
				if(all_tags[i].className == "rdfasnippet"){
					all_tags[i].setAttribute("onmouseover", "this.style.backgroundColor = \"#F5D0A9\"");
					all_tags[i].setAttribute("onmouseout", "this.style.backgroundColor = \"#FFFFFF\"");
				}
			}
		}
		window.onload = highlight_rdfa_snippet;