

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Tabela List</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New Tabela</g:link></span>
        </div>
        <div class="body">
            <h1>Tabela List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	        <g:sortableColumn property="id" title="Id" />
                        
                   	        <g:sortableColumn property="nome" title="Nome" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${tabelaList}" status="i" var="tabela">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" id="${tabela.id}">${fieldValue(bean:tabela, field:'id')}</g:link></td>
                        
                            <td>${fieldValue(bean:tabela, field:'nome')}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${Tabela.count()}" />
            </div>
        </div>
    </body>
</html>
