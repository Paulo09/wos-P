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
	
	def void executarBat(String caminho){
	
	  try {
			Runtime.getRuntime().exec("rundll32 SHELL32.DLL,ShellExec_RunDLL "+caminho);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	}
}
