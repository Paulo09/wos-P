

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Edit Aplicativo</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">Aplicativo List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New Aplicativo</g:link></span>
        </div>
        <div class="body">
            <h1>Edit Aplicativo</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${aplicativo}">
            <div class="errors">
                <g:renderErrors bean="${aplicativo}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <input type="hidden" name="id" value="${aplicativo?.id}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="nome">Nome:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:aplicativo,field:'nome','errors')}">
                                    <input type="text" id="nome" name="nome" value="${fieldValue(bean:aplicativo,field:'nome')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="tabela">Tabela:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:aplicativo,field:'tabela','errors')}">
                                    
<ul>
<g:each var="t" in="${aplicativo?.tabela?}">
    <li><g:link controller="tabela" action="show" id="${t.id}">${t?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="tabela" params="['aplicativo.id':aplicativo?.id]" action="create">Add Tabela</g:link>

                                </td>
                            </tr> 
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" value="Update" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
