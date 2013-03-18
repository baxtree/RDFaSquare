<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML+RDFa 1.0//EN" "http://www.w3.org/MarkUp/DTD/xhtml-rdfa-1.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:dc="http://purl.org/dc/terms/">
<head>
	<title>Publications in RDFa</title>
</head>
<body>
	<#list publications?keys as key>
		<div typeof="dc:publication">
			<span property="dc:creator">${publications[key].author}</span>
			<span property="dc:title">${publications[key].title}</span>
			<#if publications[key].type == "conference" && publications[key].booktitle??><span property="dc:description">${publications[key].booktitle}</span></#if>
			<#if publications[key].type == "article" && publications[key].journal??><span property="dc:description">${publications[key].journal}</span></#if>
			<#if publications[key].year??><span property="dc:date">${publications[key].year}</span></#if>
		</div>
	</#list>
</body>
</html>