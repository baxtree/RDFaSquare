<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML+RDFa 1.0//EN" "http://www.w3.org/MarkUp/DTD/xhtml-rdfa-1.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:dc="http://purl.org/dc/terms/"
	xmlns:foaf="http://xmlns.com/foaf/0.1/">
<head>
<title>FOAF in RDFa</title>
</head>
<body typeof="foaf:Person" about="${personal.topicuri}">
<p>
	<img rev="foaf:img"  src="${personal.img}" alt=""/>
</p>
<p>
This is a page about <span property="foaf:firstName">${personal.firstname}</span> <span property="familyName">${personal.lastname}</span>. Welcome to my
homepage at <a rel="foaf:homepage" href="${personal.homepage}">here</a>!<br/>
This is my contact:<br/>
Email SHA1 code: <span property="foaf:mbox_sha1sum" content="${personal.mboxsha1sum}">${personal.mboxsha1sum}</span>
</p>
<p>
The followings are people in my FOAF file. Maybe you also want to add them to your own FOAF files.<br/>
<#list knows?keys as key>
<div rel="foaf:knows">
	<span typeof="foaf:Person">
		<span property="foaf:name">${knows[key].name}</span><br/>
		<span property="foaf:mbox_sha1sum" content="${knows[key].mboxsha1sum}">Email SHA1 code: ${knows[key].mboxsha1sum}</span><br/>
		<a rel="rdfs:seeAlso" href="${knows[key].seealso}">see also ${knows[key].name} here.</a>
	</span>
</div>
</#list>
</p>
</body>
</html>