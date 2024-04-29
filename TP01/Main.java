
import java.io.*;

import java.util.Scanner;

import classes.Arquivo;

import classes.Livro;

class Main {

  public static void main(String args[]) {

    new File("dados/livros.db").delete();
    new File("dados/livros.hash_d.db").delete();
    new File("dados/livros.hash_c.db").delete();
    new File("dados/blocos.listainv.db").delete();
    new File("dados/dicionario.listainv.db").delete();

    Arquivo<Livro> arqLivros;

    try {

      arqLivros = new Arquivo<>("livros", Livro.class.getConstructor());
      Scanner scan = new Scanner(System.in);
      int pum;

      do {
        System.out.println("\n------------------------");
        System.out.println("          MENU");
        System.out.println("------------------------");

        System.out.println("1 - Inserir Livro");
        System.out.println("2 - Excluir Livro");
        System.out.println("3 - Buscar Livro");
        System.out.println("4 - Alterar Livro");
        System.out.println("5 - Imprimir Lista");
        System.out.println("0 - Sair");
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

            System.out.println("------------------------");
            System.out.println("     Inserir Livro");
            System.out.println("------------------------");

            // Limpar buffer
            scan.nextLine();

            System.out.print("ISBN: ");
            String numero = scan.nextLine();

            System.out.print("Titulo: ");
            String titulo = scan.nextLine();

            System.out.print("Preço: ");
            float preco = scan.nextFloat();

            Livro ln = new Livro(-1, numero, titulo, preco);
            int idn = arqLivros.create(ln);
            System.out.println("\nLivro criado com Sucesso!!\n");
            System.out.println("~~~~~~~~LIVRO~~~~~~~~\n" + arqLivros.read(idn) + "\n~~~~~~~~~~~~~~~~~~~~~~\n");

            break;

          case 2:
            System.out.println("------------------------");
            System.out.println("     Excluindo Livro");
            System.out.println("------------------------");

            System.out.print("ID do Livro: ");
            int id = scan.nextInt();

            if (arqLivros.delete(id))
              System.out.println("Livro (" + id + ") excluído!\n");
            else
              System.out.println("Livro de ID " + id + " não encontrado!\n");

            break;
          case 3:
            System.out.println("------------------------");
            System.out.println("     Buscando Livro");
            System.out.println("------------------------");
            System.out.print("Chave de Busca: ");

            // Limpar buffer
            scan.nextLine();

            // ler as chaves e buscar na lista invertida
            String chave = scan.nextLine();
            arqLivros.busca_lista(chave);

            break;

          case 4:
            System.out.println("------------------------");
            System.out.println("     Alterar Livro");
            System.out.println("------------------------");

            // Pega o ID do livro pra ter a chave do nome antigo
            System.out.print("ID do Livro: ");
            int ida = scan.nextInt();
            Livro livrin = arqLivros.read(ida);

            if (livrin != null) {
              // Removendo as chaves antigas da lista invertida
              arqLivros.deleteChaves(livrin.getTitulo(), ida);

              // Limpar buffer
              scan.nextLine();

              System.out.print("Novo nome: ");
              String new_nome = scan.nextLine();
              livrin.setTitulo(new_nome);

              // Atualiza o livro com o novo nome
              if (arqLivros.update(livrin))
                System.out.println("\nLivro alterado com Sucesso!!");
              else
                System.out.println("\nLivro não encontrado!!");
            }

            break;

          case 5:

            arqLivros.printLista();

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