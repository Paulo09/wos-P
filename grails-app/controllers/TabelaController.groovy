import java.sql.*;
import groovy.sql.Sql
class TabelaController {
    def con = Sql.newInstance("jdbc:postgresql://localhost:5432/app","postgres","root","org.postgresql.Driver")
    def sql = Sql.newInstance("jdbc:sapdb://localhost:7203/NSP","SAPNSP", "Branco09", "com.sap.dbtech.jdbc.DriverSapDB")
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    def allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
	      def rows = sql.rows("SELECT TABLENAME FROM TABLES where TABLENAME LIKE ('Z%')")
		  def res='';def valida='';def cont=0	  
		  rows.each{
		     res = rows[cont].toString().replaceAll("TABLENAME=","").replaceAll('\\{','').replaceAll('\\}','')
			 def opc = new Tabela(nome:res)
			 valida=con.rows("select NOME FROM tabela where nome='"+res+"'")
			 if(!valida){opc.save()}
			 cont++
		  }
        if(!params.max) params.max = 10
        [ tabelaList: Tabela.list( params ) ]
    }

    def show = {
        def tabela = Tabela.get( params.id )

        if(!tabela) {
            flash.message = "Tabela not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ tabela : tabela ] }
    }

    def delete = {
        def tabela = Tabela.get( params.id )
        if(tabela) {
            tabela.delete()
            flash.message = "Tabela ${params.id} deleted"
            redirect(action:list)
        }
        else {
            flash.message = "Tabela not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def tabela = Tabela.get( params.id )

        if(!tabela) {
            flash.message = "Tabela not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ tabela : tabela ]
        }
    }

    def update = {
        def tabela = Tabela.get( params.id )
        if(tabela) {
            tabela.properties = params
            if(!tabela.hasErrors() && tabela.save()) {
                flash.message = "Tabela ${params.id} updated"
                redirect(action:show,id:tabela.id)
            }
            else {
                render(view:'edit',model:[tabela:tabela])
            }
        }
        else {
            flash.message = "Tabela not found with id ${params.id}"
            redirect(action:edit,id:params.id)
        }
    }

    def create = {
        def tabela = new Tabela()
        tabela.properties = params
        return ['tabela':tabela]
    }

    def save = {
        def tabela = new Tabela(params)
        if(!tabela.hasErrors() && tabela.save()) {
            flash.message = "Tabela ${tabela.id} created"
            redirect(action:show,id:tabela.id)
        }
        else {
            render(view:'create',model:[tabela:tabela])
        }
    }
}
