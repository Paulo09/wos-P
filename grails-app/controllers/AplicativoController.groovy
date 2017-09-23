import java.sql.*;
import groovy.sql.Sql
class AplicativoController {
    def con = Sql.newInstance("jdbc:postgresql://localhost:5432/app","postgres","root","org.postgresql.Driver")
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    def allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
        if(!params.max) params.max = 10
        [ aplicativoList: Aplicativo.list( params ) ]
    }

    def show = {
	    def ar='';def tabNome='';def res='';def quantTabela=0 
        def aplicativo = Aplicativo.get( params.id )
		def tab = con.rows("select tabela_id FROM relacao where aplicativo_id= '"+Aplicativo.get( params.id ).id+"'")
		
		if(tab)
		{
		ar  = tab.toString().replaceAll("\\{tabela_id=","'").replaceAll("\\}","'").replaceAll("\\[","").replaceAll("\\]","").replaceAll(" ","")
		tabNome = con.rows("select nome FROM tabela where id in("+ar+")")
		res=tabNome.toString().replaceAll("\\{nome=","").replaceAll("\\}","").replaceAll("\\[","").replaceAll("\\]","").replaceAll(" ","").replaceAll(",","<li>")
		    tabNome.each{quantTabela++}
		}
		else{
		 res='Sem tabela Associada'
     	}
		
        if(!aplicativo) {
            flash.message = "Aplicativo not found with id ${params.id}"
            redirect(action:list)
        }
        else { return [ aplicativo : aplicativo,res:res,quantTabela:quantTabela ] }
    }

    def delete = {
        def aplicativo = Aplicativo.get( params.id )
        if(aplicativo) {
            aplicativo.delete()
            flash.message = "Aplicativo ${params.id} deleted"
            redirect(action:list)
        }
        else {
            flash.message = "Aplicativo not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def aplicativo = Aplicativo.get( params.id )

        if(!aplicativo) {
            flash.message = "Aplicativo not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ aplicativo : aplicativo ]
        }
    }

    def update = {
        def aplicativo = Aplicativo.get( params.id )
        if(aplicativo) {
            aplicativo.properties = params
            if(!aplicativo.hasErrors() && aplicativo.save()) {
                flash.message = "Aplicativo ${params.id} updated"
                redirect(action:show,id:aplicativo.id)
            }
            else {
                render(view:'edit',model:[aplicativo:aplicativo])
            }
        }
        else {
            flash.message = "Aplicativo not found with id ${params.id}"
            redirect(action:edit,id:params.id)
        }
    }

    def create = {
        def aplicativo = new Aplicativo()
        aplicativo.properties = params
        return ['aplicativo':aplicativo]
    }

    def save = {
        def aplicativo = new Aplicativo(params)
        if(!aplicativo.hasErrors() && aplicativo.save()) {
            flash.message = "Aplicativo ${aplicativo.id} created"
            redirect(action:show,id:aplicativo.id)
        }
        else {
            render(view:'create',model:[aplicativo:aplicativo])
        }
    }
}
