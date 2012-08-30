<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML+RDFa 1.0//EN" "http://www.w3.org/MarkUp/DTD/xhtml-rdfa-1.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:dc="http://purl.org/dc/terms/"
	xmlns:foaf="http://xmlns.com/foaf/0.1/">
<head>
<title>FOAF in RDFa</title>
</head>
<body typeof="foaf:Person" about="${personal.topicuri}">
<#if personal.img??><#list personal.img?keys as key><img rev="foaf:img" src="${personal.img[key].uri}" width="100" height="150" alt=""/><br/></#list></#if>
<#if personal.firstName?? && personal.familyName??>This is a page about <span property="foaf:firstName givenName">${personal.firstName}</span> <span property="familyName family_name Surname">${personal.familyName}</span>.<br/></#if> 
<#if personal.homepage??>Welcome to my homepage at <a rel="foaf:homepage" href="${personal.homepage}">here</a>!<br/></#if>
<#if personal.workInfoHomepage??><a rel="foaf:workInfoHomepage" href="${personal.workInfoHomepage}">This page</a> is about my work.<br/></#if>
<#if personal.workplaceHomepage??><a rel="foaf:workplaceHomepage" href="${personal.workplaceHomepage}">This page</a> is about my workpage.<br/></#if>
<#if personal.publications??><span rel="foaf:publications" resource="${personal.publications}">My publications</span> so far.<br/></#if>
<#if personal.currentProject??><a rel="foaf:currentProject" resource="${personal.currentProject}My current project</a> <br/></#if>
<#if personal.pastProject??><a rel="foaf:pastProject" resource="${personal.pastProject}">My past project</a><br/></#if>
<#if personal.plan??>My TODO list:<br/><span property="foaf:plan">${personal.plan}</span>.<br/></#if>
<#if personal.schoolHomepage??><a rel="schoolHomepage" href="${personal.schoolHomepage}">This page</a> is about places where I have been educated.<br/></#if>
<#if personal.topic_interest??>I am interested in <span property="foaf:topic_interest">${personal.topic_interest}.</span><br/></#if>
<#if personal.mbox_sha1sum??>This is my contact:<br/>Email SHA1 code: <span property="foaf:mbox_sha1sum" content="${personal.mbox_sha1sum}">${personal.mbox_sha1sum}</span><br/></#if>
<p>
The followings are people in my FOAF file. Maybe you also want to add them to your own FOAF files.<br/>
<#list knows?keys as key>
<div rel="foaf:knows">
	<div typeof="foaf:Person" <#if knows[key].knoweduri??>about="${knows[key].knoweduri}"</#if>>
		<#if knows[key].name??><span property="foaf:name">${knows[key].name}</span><br/></#if>
		<#if knows[key].mbox_sha1sum??><span property="foaf:mbox_sha1sum" content="${knows[key].mbox_sha1sum}">Email SHA1 code: ${knows[key].mbox_sha1sum}</span><br/></#if>
		<#if knows[key].seeAlso??><a rel="rdfs:seeAlso" href="${knows[key].seeAlso}">see also ${knows[key].name} here.</a></#if>
	</div>
</div>
</#list>
</p>
</body>
</html>