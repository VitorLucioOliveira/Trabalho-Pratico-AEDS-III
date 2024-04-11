
import java.io.*;
import java.util.Scanner;

import classes.Arquivo;
import classes.Livro;

class Main {

  public static void main(String args[]) {

    new File("dados/livros.db").delete();
    new File("dados/livros.hash_d.db").delete();
    new File("dados/livros.hash_c.db").delete();

    Arquivo<Livro> arqLivros;
    Livro l1 = new Livro(-1, "9788563560278", "Odisseia", 15.99F);
    Livro l2 = new Livro(-1, "9788584290482", "Ensino Híbrido", 39.90F);
    Livro l3 = new Livro(-1, "9786559790005", "Modernidade Líquida", 48.1F);
    Livro l4 = new Livro(-1, "9788582714911", "Memória", 55.58F);
    Livro l5 = new Livro(-1, "9786587150062", "Com Amor", 48.9F);

    int id1, id2, id3, id4, id5;

    try {
      arqLivros = new Arquivo<>("livros", Livro.class.getConstructor());
      Scanner scan = new Scanner(System.in);
      int pum;

      do {
        System.out.println("------------------------");
        System.out.println("          MENU");
        System.out.println("------------------------");

        System.out.println("1) Criar Livro");
        System.out.println("2) Deletar Livro");

        System.out.println("3) Teste de Usabilidade");
        System.out.println("0) Sair");
        System.out.println(" ");

        System.out.print("Opção: ");

        while (!scan.hasNextInt()) {
          System.out.println("Valor inválido!");
          System.out.print("Opção: ");
          scan.next();
        }

        pum = scan.nextInt();

        switch (pum) {

          case 1:

            System.out.println("\n------------------------");
            System.out.println("     Criando Livro");
            System.out.println("------------------------");

            System.out.print("\nISBN: ");
            String numero = scan.next();

            System.out.print("Titulo: ");
            String titulo = scan.next();

            System.out.print("Preço: ");
            float preco = scan.nextFloat();

            Livro ln = new Livro(-1, numero, titulo, preco);
            int idn = arqLivros.create(ln);
            System.out.println("\nLivro criado com Sucesso!!\n");
            System.out.println("~~~~~~~~LIVRO~~~~~~~~\n" + arqLivros.read(idn) + "\n~~~~~~~~~~~~~~~~~~~~~~\n");

            break;

          case 2:
            System.out.println("\n------------------------");
            System.out.println("     Deletando Livro");
            System.out.println("------------------------");

            System.out.print("\nID do Livro: ");
            int id = scan.nextInt();

            if (arqLivros.delete(id))
              System.out.println("Livro (" + id + ") excluído!\n");
            else
              System.out.println("Livro de ID " + id + " não encontrado!\n");

            break;

          case 3:
          System.out.println("\n------------------------");
          System.out.println("  Teste de Usabilidade");
          System.out.println("------------------------\n");


            // teste de inclusão
            id1 = arqLivros.create(l1);
            System.out.println("Livro criado ID: " + id1);

            id2 = arqLivros.create(l2);
            System.out.println("Livro criado ID: " + id2);

            id3 = arqLivros.create(l3);
            System.out.println("Livro criado ID: " + id3);

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

            break;

        }
      } while (pum != 0);

      arqLivros.close();
      scan.close();

    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}