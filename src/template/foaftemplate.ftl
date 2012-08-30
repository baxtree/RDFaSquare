<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML+RDFa 1.0//EN" "http://www.w3.org/MarkUp/DTD/xhtml-rdfa-1.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
rdfs="http://www.w3.org/2000/01/rdf-schema#"
foaf="http://xmlns.com/foaf/0.1/"
bio="http://purl.org/vocab/bio/0.1/"
rdf="http://www.w3.org/1999/02/22-rdf-syntax-ns#"
>
<head>
<title></title>
</head>
<body>
<div about="http://richard.cyganiak.de/foaf.rdf#cygri">
<#if topic.bio_event??>
	<#list topic.bio_event?keys as key>
		<#if topic.bio_event[key].uri??>
			<span rel="bio:event" resource="${topic.bio_event[key].uri}"></span><br/>
		</#if>
	</#list>
</#if>
<#if topic.rdf_type??>
	<#list topic.rdf_type?keys as key>
		<#if topic.rdf_type[key].uri??>
			<span rel="rdf:type" resource="${topic.rdf_type[key].uri}"></span><br/>
		</#if>
	</#list>
</#if>
<#if topic.rdfs_seeAlso??>
	<#list topic.rdfs_seeAlso?keys as key>
		<#if topic.rdfs_seeAlso[key].uri??>
			<span rel="rdfs:seeAlso" resource="${topic.rdfs_seeAlso[key].uri}"></span><br/>
		</#if>
	</#list>
</#if>
<#if topic.foaf_homepage??>
	<#list topic.foaf_homepage?keys as key>
		<#if topic.foaf_homepage[key].uri??>
			<span rel="foaf:homepage" resource="${topic.foaf_homepage[key].uri}"></span><br/>
		</#if>
	</#list>
</#if>
<#if topic.foaf_img??>
	<#list topic.foaf_img?keys as key>
		<#if topic.foaf_img[key].uri??>
			<span rel="foaf:img" resource="${topic.foaf_img[key].uri}"></span><br/>
		</#if>
	</#list>
</#if>
<#if topic.foaf_knows??>
	<#list topic.foaf_knows?keys as key>
		<#if topic.foaf_knows[key].uri??>
			<span rel="foaf:knows" resource="${topic.foaf_knows[key].uri}"></span><br/>
		</#if>
	</#list>
</#if>
<#if topic.foaf_mbox_sha1sum??>
	<#list topic.foaf_mbox_sha1sum?keys as key>
		<#if topic.foaf_mbox_sha1sum[key].val??>
			<span property="foaf:mbox_sha1sum">${topic.foaf_mbox_sha1sum[key].val}</span><br/>
		</#if>
	</#list>
</#if>
<#if topic.foaf_name??>
	<#list topic.foaf_name?keys as key>
		<#if topic.foaf_name[key].val??>
			<span property="foaf:name">${topic.foaf_name[key].val}</span><br/>
		</#if>
	</#list>
</#if>
<#if topic.foaf_nick??>
	<#list topic.foaf_nick?keys as key>
		<#if topic.foaf_nick[key].val??>
			<span property="foaf:nick">${topic.foaf_nick[key].val}</span><br/>
		</#if>
	</#list>
</#if>
<#if topic.foaf_weblog??>
	<#list topic.foaf_weblog?keys as key>
		<#if topic.foaf_weblog[key].uri??>
			<span rel="foaf:weblog" resource="${topic.foaf_weblog[key].uri}"></span><br/>
		</#if>
	</#list>
</#if>
<#if topic.foaf_workplaceHomepage??>
	<#list topic.foaf_workplaceHomepage?keys as key>
		<#if topic.foaf_workplaceHomepage[key].uri??>
			<span rel="foaf:workplaceHomepage" resource="${topic.foaf_workplaceHomepage[key].uri}"></span><br/>
		</#if>
	</#list>
</#if>
</div>
</body>
</html>