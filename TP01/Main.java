
import java.io.*;

import classes.Arquivo;
import classes.Livro;

class Main {

  public static void main(String args[]) {

    new File("dados/livros.db").delete();
    new File("dados/livros.hash_d.db").delete();
    new File("dados/livros.hash_c.db").delete();

    Arquivo<Livro> arqLivros;
    Livro l1 = new Livro(-1, "10", "Odisseia", 15.99F);
    Livro l2 = new Livro(-1, "11", "Ensino Híbrido", 39.90F);
    Livro l3 = new Livro(-1, "12", "Memória", 55.58F);

    Livro l4 = new Livro(-1, "16", "Teste de Mon", 55.58F);
    Livro l5 = new Livro(-1, "17", "Teste", 39.90F);
    
    int id1, id2, id3, id4, id5;

    try {
      arqLivros = new Arquivo<>("livros", Livro.class.getConstructor());

      
     // teste de inclusão
      id1 = arqLivros.create(l1);
      System.out.println("Livro criado com o ID: " + id1);

      id2 = arqLivros.create(l2);
      System.out.println("Livro criado com o ID: " + id2);

      id3 = arqLivros.create(l3);
      System.out.println("Livro criado com o ID: " + id3);



      // teste de exclusão
      if (arqLivros.delete(id3))
        System.out.println("Livro de ID " + id3 + " excluído!");
      else
        System.out.println("Livro de ID " + id3 + " não encontrado!");

        if (arqLivros.delete(id2))
        System.out.println("Livro de ID " + id2 + " excluído!");
      else
        System.out.println("Livro de ID " + id2 + " não encontrado!");

      // teste de inclusão com lixos
      id4 = arqLivros.create(l4);
      System.out.println("Livro criado com o ID: " + id4);

      id5 = arqLivros.create(l5);
      System.out.println("Livro criado com o ID: " + id5);


      // teste de update com lixos
      l4.setTitulo("Update de Livro");
      if (arqLivros.update(l4))
      System.out.println("Livro de ID " + l4.getID() + " alterado!");
      else
      System.out.println("Livro de ID " + l4.getID() + " não encontrado!");

      

      System.out.println("\nLivro 1:\n" + arqLivros.read(1));
      System.out.println("\nLivro 2:\n" + arqLivros.read(2));
      System.out.println("\nLivro 3:\n" + arqLivros.read(3));
      System.out.println("\nLivro 4:\n" + arqLivros.read(4));
      System.out.println("\nLivro 5:\n" + arqLivros.read(5));
      

      System.out.println( "\nEndereçodo Primeiro Lixo " + arqLivros.read_lixo());

      arqLivros.close();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}