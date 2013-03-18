<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML+RDFa 1.0//EN" "http://www.w3.org/MarkUp/DTD/xhtml-rdfa-1.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:dc="http://purl.org/dc/terms/"
	xmlns:foaf="http://xmlns.com/foaf/0.1/">
<head>
<title>FOAF in RDFa</title>
</head>
<#if errormsg??>
<body>
<#list errormsg?keys as key>
	<#if errormsg[key].msg??>
		${errormsg[key].msg}<p/>
	</#if>
</#list>
</body>
<#else>
<body typeof="foaf:Person" about="${personal.topicuri}">
<#if personal.img??><#list personal.img?keys as key><img rev="foaf:img" src="${personal.img[key].uri}" width="120" height="150" alt=""/></#list><br/></#if>
<#if personal.firstName??>This is a page about<#list personal.firstName?keys as key><span property="foaf:firstName givenName">${personal.firstName[key].val} </span></#list></#if>  <#if personal.familyName??><#list personal.familyName?keys as key><span property="familyName family_name Surname">${personal.familyName[key].val} </span></#list>.<br/></#if> 
<#if personal.homepage??><#list personal.homepage?keys as key>Welcome to my homepage at <a rel="foaf:homepage" href="${personal.homepage[key].uri}">here</a>!<br/></#list></#if>
<#if personal.workInfoHomepage??><#list personal.workInfoHomepage?keys as key><a rel="foaf:workInfoHomepage" href="${personal.workInfoHomepage[key].uri}">This page</a> is about my work.<br/></#list></#if>
<#if personal.workplaceHomepage??><#list personal.workplaceHomepage?keys as key><a rel="foaf:workplaceHomepage" href="${personal.workplaceHomepage[key].uri}">This page</a> is about my workpage.<br/></#list></#if>
<#if personal.publications??><#list personal.publications?keys as key><span rel="foaf:publications" href="${personal.publications[key]}.uri">My publications</span> so far.<br/></#list></#if>
<#if personal.currentProject??><#list personal.currentProject?keys as key><a rel="foaf:currentProject" href="${personal.currentProject[key].uri}">My current project</a> <br/></#list></#if>
<#if personal.pastProject??><#list personal.pastProject?keys as key><a rel="foaf:pastProject" href="<#if personal.pastProject[key].uri??>${personal.pastProject[key].uri}</#if>">My past project</a><br/></#list></#if>
<#if personal.plan??>My TODO list:<br/><#list personal.plan?keys as key><span property="foaf:plan">${personal.plan[key].val}</span>.<br/></#list></#if>
<#if personal.schoolHomepage??><#list personal.schoolHomepage?keys as key><a rel="schoolHomepage" href="${personal.schoolHomepage[key].uri}">This page</a> is about places where I have been educated.<br/></#list></#if>
<#if personal.topic_interest??>I am interested in <#list personal.topic_interest?keys as key><span property="foaf:topic_interest">${personal.topic_interest[key].uri}.</span> </#list><br/></#if>
<#if personal.mbox_sha1sum??>This is my contact:<br/><#list personal.mbox_sha1sum?keys as key>Email SHA1 code: <span property="foaf:mbox_sha1sum" content="${personal.mbox_sha1sum[key].val}">${personal.mbox_sha1sum[key].val}</span><br/></#list></#if>
<p>
The followings are people in my FOAF file. Maybe you also want to add them to your own FOAF files.<br/>
<#list knows?keys as key>
<div rel="foaf:knows">
	<div typeof="foaf:Person" <#if knows[key].knoweduri??>about="${knows[key].knoweduri}"</#if>>
		<#if knows[key].name??><#list knows[key].name?keys as kee><#if knows[key].knoweduri??><a property="foaf:name" href="${knows[key].knoweduri}">${knows[key].name[kee].val}</a><#else><span property="foaf:name">${knows[key].name[kee].val}</span></#if><br/></#list></#if>
		<#if knows[key].mbox_sha1sum??><#list knows[key].mbox_sha1sum?keys as kee><#if knows[key].mbox_sha1sum[kee].val??><span property="foaf:mbox_sha1sum" content="${knows[key].mbox_sha1sum[kee].val}">Email SHA1 code: ${knows[key].mbox_sha1sum[kee].val}</span></#if><br/></#list></#if>
		<#if knows[key].seeAlso??><#list knows[key].seeAlso?keys as kee><#if knows[key].seeAlso[kee].uri??><a rel="rdfs:seeAlso" href="${knows[key].seeAlso[kee].uri}">see also this person here.</a></#if></#list></#if>
	</div>
</div>
</#list>
</p>
</body>
</#if>
</html>