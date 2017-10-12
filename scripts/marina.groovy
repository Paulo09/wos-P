import org.codehaus.groovy.grails.commons.GrailsClassUtils as GCU
grailsAppName = ""
appName=""
Ant.property(environment:"env")
grailsHome = Ant.antProject.properties."env.GRAILS_HOME"
includeTargets << new File ( "${grailsHome}/scripts/Package.groovy")

target ( "default" : "Creates a Grails project, including the necessary directory structure and commons files"){
    print "**************** Aqui 1 *********************"
	createApp()
}
target( createApp: "The implementation target")
{
    print "++++ Target Create ++++:"+appName
	
	depends( appName, createStructure, updateAppProperties,init)
	createIDESupportFiles()
	classpath()

Ant.propertyfile(file:"${basedir}/application.properties")
{
    print "**************** Aqui 3 *********************"+basedir
	entry(key:"app.version", value:"0.1")
	entry(key:"app.servlet.version", value:servletVersion)
}
event("StatusFinal", ["Aplicacao Criada - jMob!le Developer $basedir"])
}
target( createIDESupportFiles: "Creates the IDE suppot files (Eclipse, TextMate etc.) project files")
{
    print "**************** Aqui 4 *********************"
	Ant.copy(todir:"${basedir}"){
	fileset(dir:"${grailsHome}/src/grails/templates/ide-support/eclipse",
	excludes:".launch")}
	Ant.copy(todir:"${basedir}", file:"${grailsHome}/src/grails/build.xml")
	Ant.copy(file:"${grailsHome}/src/grails/templates/ide-support/eclipse/.launch", 
	tofile:"${basedir}/${grailsAppName}.launch", overwrite:true)
	Ant.copy(file:"${grailsHome}/src/grails/templates/ide-support/textmate/project.tmproj",
	tofile:"${basedir}/${grailsAppName}.tmproj", overwrite:true)
	Ant.replace(dir:"${basedir}",includes:"*.*",
	token:"@grails.libs@", value:"${getGrailsLibs()}" )
	Ant.replace(dir:"${basedir}", includes:"*.*",
	token:"@grails.jar@", value:"${getGrailsJar()}" )
	Ant.replace(dir:"${basedir}", includes:"*.*",
	token:"@grails.version@", value:"${grailsVersion}")
	def appKey = grailsAppName.replaceAll( /\s/,'.').toLowerCase()
	Ant.replace(dir:"${basedir}", includes:"*.*",
	token:"@grails.project.name@", value:"${grailsAppName}")
	Ant.replace(dir:"${basedir}", includes:"*.*",
	token:"@grails.project.key@", value:"${appKey}" )}
	target ( appName : "Evaluates the application name")
{

print "**************** Aqui 5 *********************"

if(!args)
{
	Ant.input(message:"Application name not specified. Please enter:",
	addProperty:"grails.app.name")grailsAppName = Ant.antProject.properties."grails.app.name"
}
else{
grailsAppName = args.trim()
if(grailsAppName.indexOf('\n') > -1)
grailsAppName = grailsAppName.replaceAll(/\n/, "")}
basedir = "C:/Users/PauloCastro/Desktop/Marcelo/${grailsAppName}"
appClassName = GCU.getClassNameRepresentation(grailsAppName)}
