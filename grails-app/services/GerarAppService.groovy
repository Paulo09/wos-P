import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
class GerarAppService {

    boolean transactional = false

    def void criarApp(String nomeScript,String nomeApp) {
	
	try {
			Runtime.getRuntime().exec("cmd /c grails "+nomeScript+" "+nomeApp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
	
	def void gerarScript(String caminho,String nomeArquivo){
	    def d="Rock";
		String textoQueSeraEscrito = 
				"import org.codehaus.groovy.grails.commons.GrailsClassUtils as GCU\n"+
		        "grailsAppName = \"\" \n"+ 
				"Ant.property(environment:\"env\")\n"+
		        "grailsHome = Ant.antProject.properties.\"env.GRAILS_HOME\" \n"+
				"includeTargets << new File ( \"\${grailsHome}/scripts/Package.groovy\")\n"+
		        "target ( \"default\" : \"Creates a Grails project, including the necessary directory structure and commons files\"){\n"+
				"createApp()}\n"+
		        "target( createApp: \"The implementation target\"){\n"+
				"depends( appName, createStructure, updateAppProperties,init)\n"+
				"createIDESupportFiles()\n"+
				"classpath()\n"+
				"Ant.propertyfile(file:\"\${basedir}/application.properties\"){\n"+
				"entry(key:\"app.version\", value:\"0.1\")\n"+
				"entry(key:\"app.servlet.version\", value:servletVersion)}\n"+
				"event(\"StatusFinal\", [\"Aplicacao Criada - Wos-P \$basedir\"])}\n"+
				"target( createIDESupportFiles: \"Creates the IDE suppot files (Eclipse, TextMate etc.) project files\"){\n"+
				"Ant.copy(todir:\"\${basedir}\"){\n"+
				"fileset(dir:\"\${grailsHome}/src/grails/templates/ide-support/eclipse\",\n"+
				"excludes:\".launch\")}\n"+
				"Ant.copy(todir:\"\${basedir}\", file:\"\${grailsHome}/src/grails/build.xml\")\n"+
				"Ant.copy(file:\"\${grailsHome}/src/grails/templates/ide-support/eclipse/.launch\",\n"+
				"tofile:\"\${basedir}/\${grailsAppName}.launch\", overwrite:true)\n"+
				"Ant.copy(file:\"\${grailsHome}/src/grails/templates/ide-support/textmate/project.tmproj\",\n"+
				"tofile:\"\${basedir}/\${grailsAppName}.tmproj\", overwrite:true)\n"+
				"Ant.replace(dir:\"\${basedir}\",includes:\"*.*\",\n"+
				"token:\"@grails.libs@\", value:\"\${getGrailsLibs()}\" )\n"+
				"Ant.replace(dir:\"\${basedir}\", includes:\"*.*\",\n"+
				"token:\"@grails.jar@\", value:\"\${getGrailsJar()}\" )\n"+
				"Ant.replace(dir:\"\${basedir}\", includes:\"*.*\",\n"+
				"token:\"@grails.version@\", value:\"\${grailsVersion}\")\n"+
				"def appKey = grailsAppName.replaceAll( /\\s/,'.').toLowerCase()\n"+
				"Ant.replace(dir:\"\${basedir}\", includes:\"*.*\",\n"+
				"token:\"@grails.project.name@\", value:\"\${grailsAppName}\")\n"+
				"Ant.replace(dir:\"\${basedir}\", includes:\"*.*\",\n"+
				"token:\"@grails.project.key@\", value:\"\${appKey}\" )}\n"+
				"target ( appName : \"Evaluates the application name\"){\n"+
				"if(!args){Ant.input(message:\"Application name not specified. Please enter:\",\n"+
				"addProperty:\"grails.app.name\")grailsAppName = Ant.antProject.properties.\"grails.app.name\"}\n"+
				"else{\n"+
				"grailsAppName = args.trim()\n"+
				"if(grailsAppName.indexOf('\\n') > -1)\n"+
				"grailsAppName = grailsAppName.replaceAll(/\\n/, \" \")}\n"+
				"basedir = \"\${basedir}/web-app/"+caminho+"/\${grailsAppName}\"\n"+
				"appClassName = GCU.getClassNameRepresentation(grailsAppName)}\n";				
		FileWriter arquivo;
		try {
			arquivo = new FileWriter(new File(caminho+"\\"+nomeArquivo+".groovy"));
			arquivo.write(textoQueSeraEscrito);
			arquivo.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
