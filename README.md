# ComUniShare Backend

This repository represents the backend application of ComUniShare project, built on Java SpringBoot.
(on building)

[Frontend Repository](https://github.com/pedromotta462/ComUniShare/)

## UML Diagram

```mermaid
classDiagram
  class Usuario {
    -id: int
    -nome: string
    -email: string
    -senha: string
    -endereco: string
    -contato: string
    -perfil: string
    +registrar(email: string, senha: string)
    +autenticar(email: string, senha: string)
    +listarItensCompartilhados()
    +listarServicosOferecidos()
    +realizarTransacao(compartilhavel: Compartilhavel)
    +iniciarChat(destinatario: Usuario)
    +enviarMensagem(destinatario: Usuario, mensagem: string)
    +criarCompartilhavel(tipo: string, nome: string, descricao: string, preco: double)
  }

  class Compartilhavel {
    -id: int
    -nome: string
    -descricao: string
    -dono: Usuario
    -tipo: string
    -preco: double
    +listar()
    +adicionar()
    +remover()
    +compartilhar(redesSociais: string)
  }

  class Transacao {
    -id: int
    -data: Date
    -usuarioRequisitante: Usuario
    -usuarioOfertante: Usuario
    -compartilhavel: Compartilhavel
    +registrar()
    +aprovar()
    +rejeitar()
    +finalizar()
  }

  class Feedback {
    -id: int
    -avaliacao: int
    -comentario: string
    -usuario: Usuario
    -compartilhavel: Compartilhavel
    +registrar()
    +editar()
    +excluir()
  }

  class Chat {
    -id: int
    -mensagens: string[]
    -participantes: Usuario[]
    +iniciar(participante1: Usuario, participante2: Usuario)
    +enviarMensagem(remetente: Usuario, mensagem: string)
  }

  class NotificacoesService {
    -id: int
    -titulo: string
    -descricao: string
    -data: Date
    +enviarNotificacaoEmail(destinatario: Usuario)
  }

  Usuario "1" -- "N" Compartilhavel : possui
  Usuario "1" -- "N" Transacao : participa
  Usuario "1" -- "N" Feedback : escreve
  Usuario "1" -- "N" Chat : inicia, enviaMensagem
  Transacao "1" -- "1" Compartilhavel : inclui
  Transacao "1" -- "N" Usuario : requisitante
  Transacao "1" -- "N" Usuario : ofertante
  Feedback "1" -- "1" Compartilhavel : referenteA
  Chat "N" -- "N" Usuario : participa, enviaMensagem
  NotificacoesService "N" -- "1" Usuario : enviarNotificacaoEmail

```

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.5/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.5/maven-plugin/reference/html/#build-image)
* [Spring Data JPA](https://docs.spring.io/spring-boot/docs/3.1.5/reference/htmlsingle/index.html#data.sql.jpa-and-spring-data)
* [Spring Web](https://docs.spring.io/spring-boot/docs/3.1.5/reference/htmlsingle/index.html#web)
* [Flyway Migration](https://docs.spring.io/spring-boot/docs/3.1.5/reference/htmlsingle/index.html#howto.data-initialization.migration-tool.flyway)
* [Spring Security](https://docs.spring.io/spring-boot/docs/3.1.5/reference/htmlsingle/index.html#web.security)
* [Validation](https://docs.spring.io/spring-boot/docs/3.1.5/reference/htmlsingle/index.html#io.validation)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.1.5/reference/htmlsingle/index.html#using.devtools)

