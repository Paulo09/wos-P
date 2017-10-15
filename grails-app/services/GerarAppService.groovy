import net.jimmc.jshortcut.JShellLink;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
class GerarAppService {

    //*******************************************************************************//
	//                                Wosp-Core                                      //
	//*******************************************************************************//
	//  Nome autor: Paulo Castro                                                     //
	//  Descricao: Metodo que compoe a classe geradora de aplicativos dinamicos.     //
	//  Metodos estaticos onde ficarao todas as regras de negocios.                  // 
	//*******************************************************************************//
	//                        Endereço Repositorio GitHub                            // 
	//*******************************************************************************//
	//    GitHub:https://github.com/Paulo09/wos-P/tree/master/grails-app/services    //
    //    Data ultimo comit: 13/10/2017 - 14/:49                                     // 	
	//*******************************************************************************//
	
	boolean transactional = false
	
	// Metodo criacao script para criacao do aplicativo - Autor: Paulo Castro
	// Data criacao: 13/10/2017 - As 15:16
	
	def void gerarScript(String caminhoDiretorio,String caminhoApp,String nomeArquivo)
	{
		String textoQueSeraEscrito = 
				"import org.codehaus.groovy.grails.commons.GrailsClassUtils as GCU\n"+
		        "grailsAppName = \"\" \n"+ 
				"Ant.property(environment:\"env\")\n"+
		        "grailsHome = Ant.antProject.properties.\"env.GRAILS_HOME\"\n"+
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
				"basedir = \"\${basedir}/web-app/"+caminhoApp+"/\${grailsAppName}\"\n"+
				"appClassName = GCU.getClassNameRepresentation(grailsAppName)}\n";				
		FileWriter arquivo;
		try {
			arquivo = new FileWriter(new File(caminhoDiretorio+"\\"+nomeArquivo+".groovy"));
			arquivo.write(textoQueSeraEscrito);
			arquivo.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	// Metodo rodar o script para ageracao do aplicativo - Autor: Paulo Castro
	// Data criacao: 13/10/2017 - As 15:00
	
	 def void criarApp(String nomeScript,String nomeApp) {
	
	try {
			Runtime.getRuntime().exec("cmd /c grails "+nomeScript+" "+nomeApp);
			Thread.sleep(6000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
	
	// Metodo criacao diretorio - Autor: Paulo Castro
	// Data criacao: 13/10/2017 - As 15:15
	
	def void gerarDiretorio(String caminhoDiretorio,String nomeDiretorio){
	
		try {	 
			 File diretorio = new File(caminhoDiretorio+"//"+nomeDiretorio);
	         diretorio.mkdir();}
		catch (Exception e) {System.out.println(e);}
	
	}
	
	// Metodo criacao diretorio - Autor: Paulo Castro
	// Data criacao: 15/10/2017 - As 20:04
	
	JShellLink link;
	String filePath;

	def atalho(String nome, String caminho,String folder) 
	{
	try {
	    link = new JShellLink();	    
	    filePath = JShellLink.getDirectory("") + caminho;
	    link.setPath(caminho);
	    link.setFolder(folder);
	    link.setName(nome);
	    link.save();
	    }
	catch (Exception e){}}
}
