import junit.framework.Test;

import com.thoughtworks.selenium.DefaultSelenium

def checkTheSeleniumServer = {
	final String timeout = "1000"
	selenium = new DefaultSelenium("localhost", 4444, "*firefox", "http://demos.inf.ed.ac.uk:8836/")
	selenium.start()
	selenium.setSpeed(timeout)
}

def openTheHomePage = {
	selenium.open("/rdfasquare/")
}

def typeInRDFFileURL = {
	selenium.type("id=rdfurl_0", "http://dig.csail.mit.edu/2008/webdav/timbl/foaf.rdf")
}

def clickTheGuessButton = {
	selenium.click("id=guess_0")
}

def showTheGuessList = {
	//check if something is shown here
}

def closePopupForTopicNode = {
	selenium.click("link=i")
	selenium.click("xpath=(//button[@type='button'])[4]")
}

def selectToipcNodeAndGenerateTemplate = {
	selenium.click("link=i");
	selenium.click("xpath=(//button[@type='button'])[2]")
	selenium.click("id=strip");
}

def applyTemplate = {
	selenium.click("xpath=(//button[@type='button'])[2]")
}

def clickPageGenerationButton = {
	selenium.click("id=strip");
}

def showAnnotationPage = {
	//check if something is shown here
}

def selectICDtab = {
	selenium.click("link=Input Context Directly");
}

def selectCTtab = {
	selenium.click("link=Customize Template");
}

def typeInRDFXML = {
	selenium.type("id=rdfurl_0", "<rdf:RDF\n xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\"\n xmlns:dc=\"http://purl.org/dc/elements/1.1/\"\n xmlns:cc=\"http://creativecommons.org/ns#\"\n xmlns:foaf=\"http://xmlns.com/foaf/0.1/\"\n xmlns:s=\"http://www.w3.org/2000/01/rdf-schema#\"\n xmlns:owl=\"http://www.w3.org/2002/07/owl#\"\n xmlns:con=\"http://www.w3.org/2000/10/swap/pim/contact#\"\n xmlns:geo=\"http://www.w3.org/2003/01/geo/wgs84_pos#\">\n    <rdf:Description rdf:about=\"http://dig.csail.mit.edu/2008/2002/01/tr-automation/tr.rdf\">\n       <dc:title>W3C Standards and Technical Reports</dc:title>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://dig.csail.mit.edu/2008/webdav/timbl/foaf.rdf\">\n        <cc:license rdf:resource=\"http://creativecommons.org/licenses/by-nc/3.0/\"/>\n        <dc:title>Tim Berners-Lee's FOAF file</dc:title>\n        <rdf:type rdf:resource=\"http://xmlns.com/foaf/0.1/PersonalProfileDocument\"/>\n        <foaf:maker rdf:resource=\"http://www.w3.org/People/Berners-Lee/card#i\"/>\n        <foaf:primaryTopic rdf:resource=\"http://www.w3.org/People/Berners-Lee/card#i\"/>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://dig.csail.mit.edu/2008/webdav/timbl/foaf.rdf#cm\">\n        <rdf:type rdf:resource=\"http://xmlns.com/foaf/0.1/Person\"/>\n        <s:seeAlso rdf:resource=\"http://www.koalie.net/foaf.rdf\"/>\n        <foaf:name>Coralie Mercier</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://dig.csail.mit.edu/2008/webdav/timbl/foaf.rdf#dj\">\n        <rdf:type rdf:resource=\"http://xmlns.com/foaf/0.1/Person\"/>\n        <s:seeAlso rdf:resource=\"http://www.grorg.org/dean/foaf.rdf\"/>\n        <foaf:homepage rdf:resource=\"http://www.grorg.org/dean/\"/>\n        <foaf:mbox rdf:resource=\"mailto:dean@w3.org\"/>\n        <foaf:mbox rdf:resource=\"mailto:dino@grorg.org\"/>\n        <foaf:mbox_sha1sum>6de4ff27ef927b9ba21ccc88257e41a2d7e7d293</foaf:mbox_sha1sum>\n        <foaf:name>Dean Jackson</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://dig.csail.mit.edu/2008/webdav/timbl/foaf.rdf#edd\">\n        <rdf:type rdf:resource=\"http://xmlns.com/foaf/0.1/Person\"/>\n        <s:seeAlso rdf:resource=\"http://heddley.com/edd/foaf.rdf\"/>\n        <foaf:homepage rdf:resource=\"http://heddley.com/edd/\"/>\n        <foaf:mbox rdf:resource=\"mailto:edd@usefulinc.com\"/>\n        <foaf:mbox rdf:resource=\"mailto:edd@xml.com\"/>\n        <foaf:mbox rdf:resource=\"mailto:edd@xmlhack.com\"/>\n        <foaf:name>Edd Dumbill</foaf:name>\n        <foaf:nick>edd</foaf:nick>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://dig.csail.mit.edu/2008/webdav/timbl/foaf.rdf#libby\">\n        <rdf:type rdf:resource=\"http://xmlns.com/foaf/0.1/Person\"/>\n        <owl:sameAs rdf:resource=\"http://swordfish.rdfweb.org/people/libby/rdfweb/webwho.xrdf#me\"/>\n        <foaf:img rdf:resource=\"http://swordfish.rdfweb.org/~libby/libby.jpg\"/>\n        <foaf:mbox rdf:resource=\"mailto:libby.miller@bristol.ac.uk\"/>\n        <foaf:name>Libby Miller</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://dig.csail.mit.edu/2008/webdav/timbl/foaf.rdf#ss\">\n        <foaf:name>Susie Stephens</foaf:name>\n        <foaf:organization rdf:resource=\"http://dbpedia.org/resource/Eli_Lilly_and_Company\"/>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://bblfish.net/people/henry/card#me\">\n       <foaf:name>Henry Story</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://dbpedia.org/resource/John_Gage\">\n        <rdf:type rdf:resource=\"http://xmlns.com/foaf/0.1/Person\"/>\n        <foaf:img rdf:resource=\"http://upload.wikimedia.org/wikipedia/commons/d/de/John_Gage.jpg\"/>\n        <foaf:name>John Gage</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://dbpedia.org/resource/John_Klensin\">\n        <rdf:type rdf:resource=\"http://xmlns.com/foaf/0.1/Person\"/>\n        <foaf:name>John Klensin</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://dbpedia.org/resource/John_Markoff\">\n        <rdf:type rdf:resource=\"http://xmlns.com/foaf/0.1/Person\"/>\n        <foaf:name>John Markoff</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://dbpedia.org/resource/John_Seely_Brown\">\n        <rdf:type rdf:resource=\"http://xmlns.com/foaf/0.1/Person\"/>\n        <owl:sameAs rdf:resource=\"http://www4.wiwiss.fu-berlin.de/bookmashup/persons/John+Seely+Brown\"/>\n        <foaf:homepage rdf:resource=\"http://www.johnseelybrown.com/\"/>\n        <foaf:img rdf:resource=\"http://transliteracies.english.ucsb.edu/images/participants/t/brown-john-seely-2.jpg\"/>\n        <foaf:name>John Seely Brown</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://dbpedia.org/resource/Tim_Bray\">\n        <rdf:type rdf:resource=\"http://xmlns.com/foaf/0.1/Person\"/>\n        <foaf:name>Tim Bray</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://dig.csail.mit.edu/2007/01/camp/data#course\">\n       <foaf:maker rdf:resource=\"http://www.w3.org/People/Berners-Lee/card#i\"/>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://dig.csail.mit.edu/2007/wiki/people/JoeLambda#JL\">\n       <foaf:firstName>Joe</foaf:firstName><foaf:name>Joe Lambda</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://dig.csail.mit.edu/2007/wiki/people/RobertHoffmann#RMH\">\n        <rdf:type rdf:resource=\"http://xmlns.com/foaf/0.1/Person\"/>\n        <foaf:name>Robert Hoffmann</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://dig.csail.mit.edu/breadcrumbs/blog/4\">\n        <dc:title>timbl's blog</dc:title>\n        <s:seeAlso rdf:resource=\"http://dig.csail.mit.edu/breadcrumbs/blog/feed/4\"/>\n        <foaf:maker rdf:resource=\"http://www.w3.org/People/Berners-Lee/card#i\"/>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://dig.csail.mit.edu/data#DIG\">\n       <foaf:member rdf:resource=\"http://www.w3.org/People/Berners-Lee/card#i\"/>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://eikeon.com/foaf.rdf#eikeon\">\n       <foaf:name>Daniel Krech</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://hometown.aol.com/chbussler/foaf/chbussler.foaf#me\">\n       <foaf:name>Christoph Bussler</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://id.ecs.soton.ac.uk/person/1269\">\n       <foaf:name>Nicholas Gibbins</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://id.ecs.soton.ac.uk/person/1650\">\n       <foaf:name>Wendy Hall</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://id.ecs.soton.ac.uk/person/2686\">\n       <foaf:name>Nigel Shadbolt</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://id.ecs.soton.ac.uk/person/60\">\n       <foaf:name>Les Carr</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://inamidst.com/sbp/foaf#Sean\">\n        <rdf:type rdf:resource=\"http://xmlns.com/foaf/0.1/Person\"/>\n        <foaf:name>Sean Palmer</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://my.opera.com/chaals/xml/foaf#me\">\n       <foaf:name>Charles McCathieNevile</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://my.opera.com/danbri/xml/foaf#me\">\n        <s:seeAlso rdf:resource=\"http://danbri.livejournal.com/data/foaf\"/>\n        <owl:sameAs rdf:resource=\"http://danbri.org/foaf.rdf#danbri\"/>\n        <owl:sameAs rdf:resource=\"http://www4.wiwiss.fu-berlin.de/dblp/resource/person/336851\"/>\n        <foaf:mbox_sha1sum>70c053d15de49ff03a1bcc374e4119b40798a66e</foaf:mbox_sha1sum>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://my.opera.com/howcome/xml/foaf#howcome\">\n       <foaf:name>Håkon Wium Lie</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://myopenlink.net/dataspace/person/kidehen#this\">\n        <rdf:type rdf:resource=\"http://xmlns.com/foaf/0.1/Person\"/>\n        <foaf:name>Kingsley Idehen</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://norman.walsh.name/knows/who#norman-walsh\">\n        <rdf:type rdf:resource=\"http://xmlns.com/foaf/0.1/Person\"/>\n        <foaf:name>Norman Walsh</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://people.apache.org/~oshani/foaf.rdf#me\">\n       <foaf:name>Oshani Seneviratne</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://people.csail.mit.edu/lkagal/foaf#me\">\n        <rdf:type rdf:resource=\"http://xmlns.com/foaf/0.1/Person\"/>\n        <foaf:mailbox rdf:resource=\"mailto:lalana@csail.mit.edu\"/>\n        <foaf:name>Lalana Kagal</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://people.csail.mit.edu/psz/foaf.rdf#me\">\n       <foaf:name>Peter Szolovits</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://people.w3.org/simon/foaf#i\">\n        <rdf:type rdf:resource=\"http://xmlns.com/foaf/0.1/Person\"/>\n        <foaf:name>Simon J. Hernandez</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://qdos.com/people/tom.xrdf#me\">\n        <rdf:type rdf:resource=\"http://xmlns.com/foaf/0.1/Person\"/>\n        <foaf:name>Tom Ilube</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://research.microsoft.com/~henrikn/foaf.xml#me\">\n       <foaf:name>Henrik Nielsen</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://rit.mellon.org/Members/ihf/foaf.rdf#me\">\n        <rdf:type rdf:resource=\"http://xmlns.com/foaf/0.1/Person\"/>\n        <foaf:homepage rdf:resource=\"http://www.mellon.org/about_foundation/staff/program-area-staff/irafuchs\"/>\n        <foaf:img rdf:resource=\"http://www.sun.com/products-n-solutions/edu/images/jelc/fuchs.jpg\"/>\n        <foaf:name>Ira Fuchs</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://teole.jfouffa.org/People/Teole/card.rdf#me\">\n       <foaf:name>Philippe Le Hégaret</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://users.ecs.soton.ac.uk/mc/mcfoaf.rdf#me\">\n       <foaf:name>mc schraefel</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://web.mit.edu/shinnyih/foaf.rdf#\">\n       <foaf:name>Shinnyih Huang</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://wiki.ontoworld.org/index.php/_IRW2006\">\n        <dc:title>Identity, Reference and the Web workshop 2006</dc:title>\n        <con:participant rdf:resource=\"http://www.w3.org/People/Berners-Lee/card#i\"/>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://www.aaronsw.com/about.xrdf#aaronsw\">\n        <rdf:type rdf:resource=\"http://xmlns.com/foaf/0.1/Person\"/>\n        <s:seeAlso rdf:resource=\"http://www.aaronsw.com/about.xrdf\"/>\n        <foaf:mbox rdf:resource=\"mailto:me@aaronsw.com\"/>\n        <foaf:name>Aaron Swartz</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://www.cambridgesemantics.com/people/about/lee\">\n       <foaf:name>Lee Feigenbaum</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://www.cs.umd.edu/~hendler/2003/foaf.rdf#jhendler\">\n       <foaf:name>Jim Hendler</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://www.dajobe.org/foaf.rdf#i\">\n        <rdf:type rdf:resource=\"http://xmlns.com/foaf/0.1/Person\"/>\n        <foaf:mailbox rdf:resource=\"mailto:dave@dajobe.org\"/>\n        <foaf:name>Dave Beckett</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://www.ecs.soton.ac.uk/~dt2/dlstuff/www2006_data#panel-panelk01\">\n        <s:label>The Next Wave of the Web (Plenary Panel)</s:label>\n        <con:participant rdf:resource=\"http://www.w3.org/People/Berners-Lee/card#i\"/>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://www.isi.edu/~gil/foaf.rdf#me\">\n        <rdf:type rdf:resource=\"http://xmlns.com/foaf/0.1/Person\"/>\n        <foaf:name>Yolanda Gill</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://www.ivan-herman.net/foaf.rdf#me\">\n        <foaf:mbox_sha1sum>5ac8032d5f6012aa1775ea2f63e1676bafd5e80b</foaf:mbox_sha1sum>\n        <foaf:mbox_sha1sum>c21b7ed00d78a35efcd8e567f8fd9cca71058c5</foaf:mbox_sha1sum>\n        <foaf:mbox_sha1sum>eccd01ba8ce2391a439e9b052a9fbf37eae9f732</foaf:mbox_sha1sum>\n        <foaf:name>Ivan Herman</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://www.kjetil.kjernsmo.net/foaf#me\">\n       <foaf:name>Kjetil Kjernsmo</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://www.lassila.org/ora.rdf#me\">\n       <foaf:name>Ora Lassila</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://www.mindswap.org/2004/owl/mindswappers#Bijan.Parsia\">\n       <foaf:name>Bijan Parsia</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://www.mindswap.org/2004/owl/mindswappers#Jennifer.Golbeck\">\n       <foaf:name>Jennifer Golbeck</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://www.w3.org/People/Berners-Lee/card#amy\">\n        <rdf:type rdf:resource=\"http://xmlns.com/foaf/0.1/Person\"/>\n        <s:label>Amy van der Hiel</s:label>\n        <s:seeAlso rdf:resource=\"http://people.w3.org/amy/foaf.rdf\"/>\n        <con:familyName>van der Hiel</con:familyName>\n        <con:givenName>Amy</con:givenName>\n        <foaf:mbox rdf:resource=\"mailto:amy@w3.org\"/>\n        <foaf:mbox_sha1sum>1839a1cc2e719a85ea7d9007f587b2899cd94064</foaf:mbox_sha1sum>\n        <foaf:name>Amy van der Hiel</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://www.w3.org/People/Berners-Lee/card#i\">\n        <foaf:img rdf:resource=\"http://www.w3.org/Press/Stock/Berners-Lee/2001-europaeum-eighth.jpg\"/>\n        <foaf:knows rdf:resource=\"http://bblfish.net/people/henry/card#me\"/>\n        <foaf:knows rdf:resource=\"http://danbri.org/foaf#danbri\"/>\n        <foaf:knows rdf:resource=\"http://dbpedia.org/resource/John_Gage\"/>\n        <foaf:knows rdf:resource=\"http://dbpedia.org/resource/John_Klensin\"/>\n        <foaf:knows rdf:resource=\"http://dbpedia.org/resource/John_Markoff\"/>\n        <foaf:knows rdf:resource=\"http://dbpedia.org/resource/John_Seely_Brown\"/>\n        <foaf:knows rdf:resource=\"http://dbpedia.org/resource/Tim_Bray\"/>\n        <foaf:knows rdf:resource=\"http://dig.csail.mit.edu/2007/wiki/people/JoeLambda#JL\"/>\n        <foaf:knows rdf:resource=\"http://dig.csail.mit.edu/2007/wiki/people/RobertHoffmann#RMH\"/>\n        <foaf:knows rdf:resource=\"http://dig.csail.mit.edu/2008/webdav/timbl/foaf.rdf#cm\"/>\n        <foaf:knows rdf:resource=\"http://dig.csail.mit.edu/2008/webdav/timbl/foaf.rdf#edd\"/>\n        <foaf:knows rdf:resource=\"http://dig.csail.mit.edu/2008/webdav/timbl/foaf.rdf#libby\"/>\n        <foaf:knows rdf:resource=\"http://dig.csail.mit.edu/2008/webdav/timbl/foaf.rdf#ss\"/>\n        <foaf:knows rdf:resource=\"http://dig.csail.mit.edu/People/RRS\"/>\n        <foaf:knows rdf:resource=\"http://dig.csail.mit.edu/People/yosi#YES\"/>\n        <foaf:knows rdf:resource=\"http://eikeon.com/foaf.rdf#eikeon\"/>\n        <foaf:knows rdf:resource=\"http://heddley.com/edd/foaf.rdf#edd\"/>\n        <foaf:knows rdf:resource=\"http://hometown.aol.com/chbussler/foaf/chbussler.foaf#me\"/>\n        <foaf:knows rdf:resource=\"http://id.ecs.soton.ac.uk/person/1269\"/>\n        <foaf:knows rdf:resource=\"http://id.ecs.soton.ac.uk/person/1650\"/>\n        <foaf:knows rdf:resource=\"http://id.ecs.soton.ac.uk/person/2686\"/>\n        <foaf:knows rdf:resource=\"http://id.ecs.soton.ac.uk/person/60\"/>\n        <foaf:knows rdf:resource=\"http://inamidst.com/sbp/foaf#Sean\"/>\n        <foaf:knows rdf:resource=\"http://my.opera.com/chaals/xml/foaf#me\"/>\n        <foaf:knows rdf:resource=\"http://my.opera.com/howcome/xml/foaf#howcome\"/>\n        <foaf:knows rdf:resource=\"http://myopenlink.net/dataspace/person/kidehen#this\"/>\n        <foaf:knows rdf:resource=\"http://norman.walsh.name/knows/who#norman-walsh\"/>\n        <foaf:knows rdf:resource=\"http://people.apache.org/~oshani/foaf.rdf#me\"/>\n        <foaf:knows rdf:resource=\"http://people.csail.mit.edu/crowell/foaf.rdf#crowell\"/>\n        <foaf:knows rdf:resource=\"http://people.csail.mit.edu/lkagal/foaf#me\"/>\n        <foaf:knows rdf:resource=\"http://people.csail.mit.edu/psz/foaf.rdf#me\"/>\n        <foaf:knows rdf:resource=\"http://people.csail.mit.edu/ryanlee/about#ryanlee\"/>\n        <foaf:knows rdf:resource=\"http://people.w3.org/simon/foaf#i\"/>\n        <foaf:knows rdf:resource=\"http://presbrey.mit.edu/foaf.rdf#presbrey\"/>\n        <foaf:knows rdf:resource=\"http://qdos.com/people/tom.xrdf#me\"/>\n        <foaf:knows rdf:resource=\"http://research.microsoft.com/~henrikn/foaf.xml#me\"/>\n        <foaf:knows rdf:resource=\"http://rit.mellon.org/Members/ihf/foaf.rdf#me\"/>\n        <foaf:knows rdf:resource=\"http://teole.jfouffa.org/People/Teole/card.rdf#me\"/>\n        <foaf:knows rdf:resource=\"http://users.ecs.soton.ac.uk/mc/mcfoaf.rdf#me\"/>\n        <foaf:knows rdf:resource=\"http://web.mit.edu/ruthdhan/www/foaf.rdf#ruthdhan\"/>\n        <foaf:knows rdf:resource=\"http://web.mit.edu/shinnyih/foaf.rdf#\"/>\n        <foaf:knows rdf:resource=\"http://www.aaronsw.com/about.xrdf#aaronsw\"/>\n        <foaf:knows rdf:resource=\"http://www.aaronsw.com/about.xrdf#aaronsw\"/>\n        <foaf:knows rdf:resource=\"http://www.cambridgesemantics.com/people/about/lee\"/>\n        <foaf:knows rdf:resource=\"http://www.cs.umd.edu/~hendler/2003/foaf.rdf#jhendler\"/>\n        <foaf:knows rdf:resource=\"http://www.dajobe.org/foaf.rdf#i\"/>\n        <foaf:knows rdf:resource=\"http://www.isi.edu/~gil/foaf.rdf#me\"/>\n        <foaf:knows rdf:resource=\"http://www.ivan-herman.net/foaf.rdf#me\"/>\n        <foaf:knows rdf:resource=\"http://www.kjetil.kjernsmo.net/foaf#me\"/>\n        <foaf:knows rdf:resource=\"http://www.lassila.org/ora.rdf#me\"/>\n        <foaf:knows rdf:resource=\"http://www.mindswap.org/2004/owl/mindswappers#Bijan.Parsia\"/>\n        <foaf:knows rdf:resource=\"http://www.mindswap.org/2004/owl/mindswappers#Jennifer.Golbeck\"/>\n        <foaf:knows rdf:resource=\"http://www.w3.org/People/Berners-Lee/card#amy\"/>\n        <foaf:knows rdf:resource=\"http://www.w3.org/People/Connolly/#me\"/>\n        <foaf:knows rdf:resource=\"http://www.w3.org/People/EM/contact#me\"/>\n        <foaf:knows rdf:resource=\"http://www.w3.org/People/Jacobs/contact.rdf#IanJacobs\"/>\n        <foaf:knows rdf:resource=\"http://www.w3.org/People/djweitzner/foaf#DJW\"/>\n        <foaf:knows rdf:resource=\"http://www.w3.org/People/karl/karl-foaf.xrdf#me\"/>\n        <foaf:knows rdf:parseType=\"Resource\">\n            <rdf:type rdf:resource=\"http://xmlns.com/foaf/0.1/Person\"/>\n            <s:seeAlso rdf:resource=\"http://dannyayers.com/me.rdf\"/>\n            <foaf:mbox_sha1sum>669fe353dbef63d12ba11f69ace8acbec1ac8b17</foaf:mbox_sha1sum>\n            <foaf:name>Danny Ayers</foaf:name>\n        </foaf:knows>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://www.w3.org/People/Connolly/#me\">\n        <rdf:type rdf:resource=\"http://xmlns.com/foaf/0.1/Person\"/>\n        <s:seeAlso rdf:resource=\"http://www.w3.org/People/Connolly/home-smart.rdf\"/>\n        <foaf:mbox rdf:resource=\"mailto:connolly@w3.org\"/>\n        <foaf:name>Dan Connolly</foaf:name>\n        <foaf:nick>DanCon</foaf:nick>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://www.w3.org/People/EM/contact#me\">\n        <rdf:type rdf:resource=\"http://xmlns.com/foaf/0.1/Person\"/>\n        <s:seeAlso rdf:resource=\"http://www.w3.org/People/EM/contact\"/>\n        <foaf:homepage rdf:resource=\"http://purl.org/net/eric/\"/>\n        <foaf:img rdf:resource=\"http://www.ilrt.bristol.ac.uk/people/cmdjb/events/dc7/orig/eric.png\"/>\n        <foaf:img rdf:resource=\"http://www.oclc.org/~emiller/capture.jpg\"/>\n        <foaf:mbox rdf:resource=\"mailto:em@w3.org\"/>\n        <foaf:name>Eric Miller</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://www.w3.org/People/Jacobs/contact.rdf#IanJacobs\">\n       <foaf:name>Ian Jacobs</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://www.w3.org/People/djweitzner/foaf#DJW\">\n        <rdf:type rdf:resource=\"http://xmlns.com/foaf/0.1/Person\"/>\n        <foaf:mbox_sha1sum>032c319f439f63efba54f4fa51bfb3a3fafedfbe</foaf:mbox_sha1sum>\n        <foaf:name>Daniel J Weitzner</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://www.w3.org/People/karl/karl-foaf.xrdf#me\">\n        <s:seeAlso rdf:resource=\"http://www.w3.org/People/karl/karl-foaf.xrdf\"/>\n        <foaf:mbox rdf:resource=\"mailto:karl@w3.org\"/>\n        <foaf:name>Karl Dubost</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://www.w3.org/data#W3C\">\n        <s:label>W3C</s:label>\n        <s:seeAlso rdf:resource=\"http://dig.csail.mit.edu/2008/2002/01/tr-automation/tr.rdf\"/>\n        <con:publicHomePage rdf:resource=\"http://www.w3.org/\"/>\n        <foaf:homepage rdf:resource=\"http://dig.csail.mit.edu/2008/\"/>\n        <foaf:logo rdf:resource=\"http://dig.csail.mit.edu/2008/Icons/w3c_home\"/>\n        <foaf:name>World Wide Web Consortium</foaf:name>\n    </rdf:Description>\n    <rdf:Description rdf:about=\"http://www4.wiwiss.fu-berlin.de/booksMeshup/books/006251587X\">\n        <dc:creator rdf:resource=\"http://www.w3.org/People/Berners-Lee/card#i\"/>\n        <dc:title>Weaving the Web: The Original Design and Ultimate Destiny of the World Wide Web</dc:title>\n    </rdf:Description>\n</rdf:RDF>")
}

def closeTheBrowser = {
	selenium.stop()
}

def clickOtherTabsAndLinks = {
	selenium.click("link=About");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=Contact");
		selenium.waitForPageToLoad("30000");
		selenium.click("//div[@id='container']/div[2]/div/ul/li[6]/a/span");
		selenium.waitForPageToLoad("30000");
		selenium.click("link=v1.3.0.280912_alpha");
		selenium.waitForPageToLoad("30000");
		selenium.click("css=img");
		selenium.waitForPageToLoad("30000");
}

before "start selenium", {
	
	given "selenium is up and running", checkTheSeleniumServer
	
	and "open the RDFaSquare page", openTheHomePage
}

scenario "recommend topic nodes based on inputed RDF files and generate the XHTML+RDFa page based the selected topic node", {
	
	when " type in a URL of an RDF file", typeInRDFFileURL
	
	and "click the guess button", clickTheGuessButton
	
	then "it should show the poped-up list of recommended topic nodes", showTheGuessList
	
	when "select a topic node from the list and close the topic recommendation popup", closePopupForTopicNode
	
	and "click the page generation button", clickPageGenerationButton
	
	then "it should show the generated XHTML+RDFa page", showAnnotationPage
}

scenario "recommend topic nodes based on RDF/XML and generate the XHTML+RDFa page based the selected topic node", {
	
	when "select the Input Cotext Directly tab", selectICDtab 
	
	and "type in RDF/XML content", typeInRDFXML
	
	and "click the guess button", clickTheGuessButton
	
	then "it should show the poped-up list of recommended topic nodes", showTheGuessList
	
	when "select a topic node from the list and close the topic recommendation popup", closePopupForTopicNode
	
	and "click the page generation button", clickPageGenerationButton
	
	then "it should show the generated XHTML+RDFa page", showAnnotationPage
}

scenario "generate a customisable template and apply it to the selected context to generate the XHTML+RDFa page", {
	
	when "selecte the Customize Template tab", selectCTtab
	
	and "type in a URL of an RDF file", typeInRDFFileURL
	
	and "click the guess button", clickTheGuessButton
	
	then "it should show the poped-up list of recommended topic nodes", showTheGuessList
	
	when "select a topic node from the list and generate the template", selectToipcNodeAndGenerateTemplate
	
	and "apply the template to generate the XHTML+RDFa page", applyTemplate
	
	then "it should show the generated XHTML+RDFa page", showAnnotationPage
}

scenario "test other tabs and links", {
	
	then "click other tabls and links", clickOtherTabsAndLinks	
}

after "stop selenium", {
	
	then "close the browser", closeTheBrowser	
}
