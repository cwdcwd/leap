<h1>Leap: An Ant Library Plugin for Salesforce Apex Development</h1>

<h2>Project Goals</h2>
<ul>
<li>Accelerate the Apex learning and development process</li>
<li>Catalog Apex patterns and best practices in a template library</li>
<li>Enable continuous integration / Built around the Apex development lifecycle</li>
<li>Extensible and open</li>
</ul>

<h2>Overview</h2>
Many Force.com MVPs and Developers have written excellent articles, books, and blog posts on the topic of Apex; which Developers worldwide are able to cut-n-paste into their projects.

Leap is an attempt to package Apex best practices and patterns into a template library for use in code generation directly from any Ant command line environment.

<h2>Usage</h2>
From the command line of any Salesforce development environment:
<pre>
	~/ant leapTargetName
</pre>

<h2>List of Leap Tasks</h2>
<ul>
	<li>gensfields: Generates a class of static fields for all SObjects</li>
	<li>gentrigger: Generates trigger class(es) for all SObject</li>
	<li>gentriggerhandler: Generate trigger handler class(es)</li>
	<li>genwrapper: Generates wrapper class(es)</li>
</ul>

<h2>Getting started</h2>
<ul>
	<li>Download and copy ant-leap.jar into the local Ant library folder (on a Mac this is located at /usr/share/ant/lib).</li>
	<li>(TODO: Copy WSC jar dependencies to local folder)
	<li>Create build.properties and build.xml files within the root of any Salesforce development project (see example templates below).</li>
	<li>Enter Salesforce development credentials into build.properties.
</ul>

<h2>Example build.properties</h2>
<pre>
	# build.properties
	#
	# For server URL properties...
	# Use 'https://login.salesforce.com' for production or developer edition.
	# Use 'https://test.salesforce.com for sandbox.

	# Specify the login credentials for the Salesforce development organization
	sf.dev.username = developername@domain.com
	sf.dev.password = password
	sf.dev.url = https://login.salesforce.com

	# Specify the login credentials for the Salesforce staging/test organization
	sf.test.username = 
	sf.test.password = 
	sf.test.url = https://login.salesforce.com

	# Specify the login credentials for the Salesforce production/packaging organization
	sf.prod.username = 
	sf.prod.password = 
	sf.prod.url = https://login.salesforce.com
</pre>

<h2>Example build.xml</h2>

<pre>
<project name="Project Name" default="test" basedir="." xmlns:sf="antlib:com.salesforce" xmlns:leap="antlib:org.leap">
	<target name="leapTaskName">
		<leap:leapTaskName username="${sf.dev.username}" password="${sf.dev.password}" serverurl="${sf.dev.url}" />
	</target>
</project>
</pre>