package com.ydev00.util;

import static spark.Spark.*;

import java.sql.Connection;

import com.ydev00.controller.*;

/**
 * Classe para configurar e inicializar o servidor Spark com as rotas e controladores.
 */
public class Server {
  private Connection dbConn;

  /**
   * Construtor da classe Server.
   * Configura e inicializa o servidor Spark com as rotas e controladores necessários.
   *
   * @param dbServer O servidor de banco de dados para obter a conexão com o banco de dados.
   */
  public Server(DBServer dbServer) {
    try {
      // Configura a porta do servidor
      port(8080);
      init();

      // Obtém a conexão com o banco de dados
      dbConn = dbServer.getConn();
      System.out.println(dbConn.getMetaData());

      // Inicializa os controladores necessários
      UserController userController = new UserController(dbConn);
      ThreadController threadController = new ThreadController(dbConn);
      ModUserController modUserController = new ModUserController(dbConn);

      // Configura as rotas e seus respectivos métodos de controlador

      redirect.get("/", "/home/");
      redirect.get("/home", "/home/");

      // Rotas de usuário
      get("/getAll", userController.getAll); // Obtém todos os usuários
      post("/signup", userController.signup); // Cria um novo usuário
      post("/login", "application.json", userController.login); // Faz login de um usuário
      get("/user/:username/t", "application.json", threadController.getThreadsByUser); // Obtém threads de um usuário
      get("/user/:username/getFollowers", "application.json", userController.getFollowers);  // Obtém seguidores de um usuário
      get("/user/:username/getFollowees", "application.json", userController.getFollowees); // Obtém quem o usuário está seguindo
      get("/user/:username", "application.json", userController.getByUsername); // Obtém informações de um usuário
      post("/user/follow", "application.json", userController.follow); // Segue um usuário
      post("/user/unfollow", "application.json", userController.unfollow); // Deixa de seguir um usuário
      put("/user/update", "application.json", userController.update); // Atualiza informações de um usuário
      post("/user/logout", "application.json", userController.logout); // Faz logout de um usuário

      // Rotas de threads
      get("/feed", "application.json" , threadController.loadFeed); // Carrega o feed de threads
      get("/t/:id", "application.json", threadController.loadThread); // Carrega uma thread específica
      post("/t/new", "application.json", threadController.create); // Cria uma nova thread
      post("/t/comment/:id", "application.json", threadController.comment); // Comenta em uma thread
      post("/t/like", "application.json", userController.like); // Dá like em uma thread
      post("/t/unlike", "application.json", userController.unlike); // Remove like de uma thread
      delete("/delete/user/:id", "application.json", modUserController.deleteUser); // Deleta um usuário
      delete("/delete/t/:id", "application.json", modUserController.deletePost); // Deleta uma thread

      // Exibe mensagem de conexão bem-sucedida se a conexão com o banco de dados foi estabelecida
      if(dbConn != null)
        System.out.println("[Server Connected]");
    } catch (Exception ex) {
      // Exibe mensagem de erro se ocorrer um problema ao iniciar o servidor
      System.err.println("Server could not start. Error: " + ex.getMessage());
    }
  }
}
