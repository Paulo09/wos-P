import java.sql.*;
import groovy.sql.Sql
import java.io.File;
class RelacaoController {
    def GerarAppService
    def con = Sql.newInstance("jdbc:postgresql://localhost:5432/app","postgres","root","org.postgresql.Driver")
	def sql = Sql.newInstance("jdbc:sapdb://localhost:7203/NSP","SAPNSP", "Branco09", "com.sap.dbtech.jdbc.DriverSapDB")
    def index = { redirect(action:list,params:params) }

    // Wosp-Core 12/10/2017 16:10 - Paulo Castro
    def allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {

        if(!params.max) params.max = 100
        [ relacaoList: Relacao.list( params ) ]
    }

    def show = {
	    def nomeScript='marina'
		def nomeApp='Paulo'
	    GerarAppService.criarApp("${nomeScript}","${nomeApp}");					 
	   
	     def cont=0;def contTab=0;def res1=[];def cls='';def tabArr=[];def tabList='';
		 def valTab='';def valTabArray=[];def valRows ='';def valRowsArray =[]
         def relacao = Relacao.get( params.id )
		 def tab = con.rows("select tabela_id FROM relacao where aplicativo_id= '"+Relacao.get( params.id ).aplicativo.id+"'")		
		 valRowsArray = sql.rows("select DATATYPE,COLUMNNAME FROM COLUMNS WHERE tablename = '"+valTabArray.toString().replaceAll('\\{nome=','').replaceAll('\\}','').replaceAll('\\[','').replaceAll('\\]','')+"'")
		 
		tab.each{
		 
		     valTabArray=con.rows("select nome from tabela where id = '"+tab[contTab].toString().replaceAll("\\{tabela_id=","").replaceAll("\\}","")+"'")
			 
			 valTabArray.each{			 
			   valRowsArray = sql.rows("select DATATYPE,COLUMNNAME FROM COLUMNS WHERE tablename = '"+valTabArray.toString().replaceAll('\\{nome=','').replaceAll('\\}','').replaceAll('\\[','').replaceAll('\\]','')+"'")
			   valRows=valRowsArray.toString().replaceAll("\\{DATATYPE=","").replaceAll("COLUMNNAME=","").replaceAll("}","").replaceAll("VARCHAR","String").replaceAll("FIXED","Integer").replaceAll(",","").replaceAll("Integer IDCONTA","").replaceAll("Integer ID","").replaceAll("Integer IDCONTA","").replaceAll("Integer VERSION","").replaceAll("String",";String").replaceAll("\\[ ;","").replaceAll("\\[  ;","").replaceAll("\\[;","").replaceAll("\\]","").replaceAll(" ;",";")
			   
				def objClasse = "class ${valTabArray.toString().replaceAll('\\{nome=','').replaceAll('\\}','').replaceAll('\\[','').replaceAll('\\]','').substring(0,1).toUpperCase()+valTabArray.toString().replaceAll('\\{nome=','').replaceAll('\\}','').replaceAll('\\[','').replaceAll('\\]','').substring(1,valTabArray.toString().replaceAll('\\{nome=','').replaceAll('\\}','').replaceAll('\\[','').replaceAll('\\]','').size()).toLowerCase()} {\n"+
				                 valRows+"\n"+
								 "static mapping = {\n"+
								 "version false"+
								 "\n}"+
								 "\n}";			   
			   
			    def criarapp = new File("C:\\Users\\PauloCastro\\Desktop\\testes\\${valTabArray.toString().replaceAll('\\{nome=','').replaceAll('\\}','').replaceAll('\\[','').replaceAll('\\]','').substring(0,1).toUpperCase()+valTabArray.toString().replaceAll('\\{nome=','').replaceAll('\\}','').replaceAll('\\[','').replaceAll('\\]','').substring(1,valTabArray.toString().replaceAll('\\{nome=','').replaceAll('\\}','').replaceAll('\\[','').replaceAll('\\]','').size()).toLowerCase()}.groovy");	
			        criarapp.write(objClasse); 
			   
			   contTab++
			}
			 		  
		}

		 		 
		if(!relacao) {
            flash.message = "Relacao not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ relacao : relacao ] }
    }

    def delete = {
        def relacao = Relacao.get( params.id )
        if(relacao) {
            relacao.delete()
            flash.message = "Relacao ${params.id} deleted"
            redirect(action:list)
        }
        else {
            flash.message = "Relacao not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def relacao = Relacao.get( params.id )

        if(!relacao) {
            flash.message = "Relacao not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ relacao : relacao ]
        }
    }

    def update = {
          def relacao = Relacao.get( params.id )
        if(relacao) {
            relacao.properties = params
            if(!relacao.hasErrors() && relacao.save()) {
                flash.message = "Relacao ${params.id} updated"
                redirect(action:show,id:relacao.id)
            }
            else {
                render(view:'edit',model:[relacao:relacao])
            }
        }
        else {
            flash.message = "Relacao not found with id ${params.id}"
            redirect(action:edit,id:params.id)
        }
    }

    def create = {
        def relacao = new Relacao()
        relacao.properties = params
        return ['relacao':relacao]
    }
    def save = { 
       def cont=0
	   def msg=''	
	   def relacao = new Relacao()
    try {
		   params.tabela.id.each{
		     def aux1=0
		     def id = Relacao.executeQuery("select count(*) from Relacao")
			 def valida = Relacao.executeQuery("select count(*) from Relacao where aplicativo_id='"+params.aplicativo.id.toInteger()+"' and tabela_id='"+params.tabela.id[cont].toInteger()+"'")
			 if(valida[0]==0){
			     con.execute("insert into relacao values(?,?,?,?)", [id[0].plus(1),1,params.aplicativo.id.toInteger(),params.tabela.id[cont].toInteger()])
			     params.tabela.id.each{aux1++}
			   if(aux1.minus(1)==cont){
			     flash.message = "Tabela ${valida[0]} CRIADO COM SUCESSO!!!"
			     redirect(action:show,id:id[0]+1)
			    }
			   }
			 else{
			     con.eachRow("select nome from tabela where id in('"+params.tabela.id[cont].toInteger()+"')",{msg+="${it.nome},"});		  
			     params.tabela.id.each{aux1++}
			    if(aux1.minus(1)==cont){
				 flash.message = "Tabela(s) ${msg} Já estão Atribuída(s)!!!" 
			     redirect(action:create)
			    }
			 }	
	         cont++
		     }
		}
		catch(Exception e1){
		
		     if(params.tabela==null){
			   flash.message = "Atribua ao menos uma Tabela!"
			   redirect(action:create)
			 }
			 
		     params.tabela.id.each{
		     def id = Relacao.executeQuery("select count(*) from Relacao")
			 def valida = Relacao.executeQuery("select count(*) from Relacao where aplicativo_id='"+params.aplicativo.id.toInteger()+"' and tabela_id='"+params.tabela.id.toInteger()+"'")
			 if(valida[0]==0){
			   con.execute("insert into relacao values(?,?,?,?)", [id[0]+1,1,params.aplicativo.id.toInteger(),params.tabela.id.toInteger()])
			   flash.message = "Tabela ${valida[0]} CRIADO COM SUCESSO!!!"
			   redirect(action:show,id:id[0]+1)}
			 else{
			   con.eachRow("select nome from tabela where id in('"+params.tabela.id.toInteger()+"')",{msg="${it.nome}"});		  
			   flash.message = "Tabela(s) ${msg}  Atribuída(s)!!!"
			   redirect(action:create)
			 }	
	         cont++
		     }
		}
		catch(NullPointerException e){
			  flash.message = "Tabela(s) ${e}  Atribuída(s)!!!"
			  redirect(action:create)
		}
    		
	   
	   
	  
	   
	}
}
