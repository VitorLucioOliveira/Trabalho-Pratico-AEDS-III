package classes;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Arquivo<T extends Registro> {

  final protected int HEADER_SIZE = 4 + 8;// tamanho do cabeçalho do arquivo

  protected Constructor<T> Construtor;// construtor de objetos da classe T(generica)

  protected RandomAccessFile file;// arquivo de acesso aleatório usado

  protected String Nome = "";// nome do arquivo

  protected HashExtensivel<ParIDEndereco> indiceDireto;// obj para manipular o indice direto

  // --------------Construtor da classe----------------//
  public Arquivo(String nome, Constructor<T> construtor) throws Exception {

    this.Nome = nome;// seta o nome
    this.Construtor = construtor;// seta o construtor

    /*
     * 1. Abre o arquivo de dados com o nome escolhido
     * 2. Se o arquivo estiver vazio, escreve o cabeçalho no inicio
     * 3. Cria o indice direto com 3 bits por cesto, arq de dados e arq de cesto
     */

    file = new RandomAccessFile("dados/" + nome + ".db", "rw");

    if (file.length() < HEADER_SIZE) {
      file.seek(0);
      file.writeInt(0);
      file.writeLong(-1);// Endereço do primeiro excluido
    }

    indiceDireto = new HashExtensivel<>(
        ParIDEndereco.class.getConstructor(),
        3,
        "dados/" + nome + ".hash_d.db",
        "dados/" + nome + ".hash_c.db");
  }

  // -------------------Método CREATE-------------------//
  public int create(T obj) throws Exception {

    // Vai para o inicio do arquivo e Pega o ultimo ID + 1
    file.seek(0);
    int ultimoID = file.readInt();
    ultimoID++;

    // Volta para o inicio e atualiza o ID
    file.seek(0);
    file.writeInt(ultimoID);

    // Seta o ID do objeto e converte para array de bytes e pega o tamanho
    obj.setID(ultimoID);
    byte[] registro = obj.toByteArray();
    short length_registro = (short) registro.length;

    // Pega o endereço do primeiro lixo e o lixo anterior ao que a gente vai usar
    long primeiro_lixo = read_lixo();
    long lixo_anterior = busca_lixo(primeiro_lixo, length_registro);
    
    
    //Se tiver, vai para o endereço do lixo anterior e vamos redirecionar o ponteiro
    if (primeiro_lixo != -1 && lixo_anterior != -1) {
      
      // Garante que o lixo anterior não esta no cabeçalho
      if(lixo_anterior != 4) {
        lixo_anterior +=3;
      }
      
      // Vai para o endereço do lixo anterior e pega o endereço do  lixo que vamos usar
      file.seek(lixo_anterior );
      long lixo_usado = file.readLong();

      // Vai para o endereço do lixo usado e pega o endereço do próximo lixo
      file.seek(lixo_usado + 3);
      long proximo_lixo = file.readLong();
      
      // Redirecionamos lixo_anterior ---> proximo_lixo
      file.seek(lixo_anterior );
      file.writeLong(proximo_lixo);
      
      // Criamos o registro no endereço do lixo usado
      file.seek(lixo_usado);
      file.writeByte(' ');
      file.readShort();
      file.write(registro);
      indiceDireto.create(new ParIDEndereco(obj.getID(), lixo_usado));

    }
    //Se não vai para o final e salva o endereço
    else {
      
      file.seek(file.length());
      long endereco_fim = file.getFilePointer();

      file.writeByte(' '); // lápide
      file.writeShort(length_registro);
      file.write(registro);
      indiceDireto.create(new ParIDEndereco(obj.getID(), endereco_fim));
    }

    return obj.getID();
  }

  //-------------------Método BUSCA_LIXO-------------------//
  private long busca_lixo(long primeiro_lixo, short tamanho_registro) {
    long endereco = primeiro_lixo;
    long endereco_anterior = 4; // Inicializa com -1, indicando que não há endereço anterior no início

    try {
        while (endereco != -1) {
            file.seek(endereco);
            file.readByte();
            short tamanho_lixo = file.readShort();

            if (tamanho_lixo >= tamanho_registro) {
                
                return endereco_anterior;
            }

            // Salva o endereço atual como o endereço anterior
            endereco_anterior = endereco;

            // Lê o próximo endereço
            endereco = file.readLong();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    // Retorna o endereço encontrado e o endereço anterior
    return -1;
}

  // -------------------Método Primeiro_LIXO-------------------//
  public long read_lixo() {
    long result = -1;

    try {

      file.seek(0);
      file.readInt();

      result = file.readLong();
      

    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  // -------------------Método READ-------------------//
  public T read(int id) throws Exception {

    T obj = Construtor.newInstance();
    short size_registro;
    byte[] registro;

    ParIDEndereco pie = indiceDireto.read(id);
    long endereco = pie != null ? pie.getEndereco() : -1;

    if (endereco != -1) {

      file.seek(endereco + 1); // pula o lápide também

      size_registro = file.readShort();
      registro = new byte[size_registro];

      // Le a armaneza no objeto
      file.read(registro);
      obj.fromByteArray(registro);

      return obj;
    }
    return null;
  }

  // -------------------Método DELETE-------------------//
  public boolean delete(int id) throws Exception {

    ParIDEndereco pie = indiceDireto.read(id);
    long endereco = pie != null ? pie.getEndereco() : -1;

    if (endereco != -1) {

      // Excluiu normalmente
      file.seek(endereco);
      file.writeByte('*');
      indiceDireto.delete(id);

      file.seek(4);
      Long inicio_lixos = file.readLong();

      if (inicio_lixos == -1) {

        // Se não tiver lixos, salva o endereço do registro excluido
        file.seek(4);
        file.writeLong(endereco);

        // Escreve o endereço do lixo final
        file.seek(endereco);
        file.writeByte('*');
        file.readShort();
        file.writeLong(-1);

      } else {

        // No enderoço do registro que vou excluir eu salvo o endereço do primeiro lixo
        file.seek(endereco);
        file.readByte();
        file.readShort();
        file.writeLong(inicio_lixos);

        // E atualizo o inicio dos lixos com o endereço do registro que exclui
        file.seek(4);
        file.writeLong(endereco);
      }

      return true;
    } else
      return false;
  }

  // -------------------Método UPDATE-------------------//
  public boolean update(T novoObj) throws Exception {
    T obj = Construtor.newInstance();
    short tam, tam2;
    byte[] ba, ba2;
    ParIDEndereco pie = indiceDireto.read(novoObj.getID());
    long endereco = pie != null ? pie.getEndereco() : -1;

    if (endereco != -1) {
      file.seek(endereco + 1); // pula o campo lápide
      tam = file.readShort();
      ba = new byte[tam];
      file.read(ba);
      obj.fromByteArray(ba);
      ba2 = novoObj.toByteArray();
      tam2 = (short) ba2.length;
      
      
      if (tam2 <= tam) {
        file.seek(endereco + 1 + 2);
        file.write(ba2);
      }
      else {
        long primeiro_lixo = read_lixo();
        long lixo_anterior = busca_lixo(primeiro_lixo, tam2);
        
        
        //Se tiver, vai para o endereço do lixo anterior e vamos redirecionar o ponteiro
        if (primeiro_lixo != -1 && lixo_anterior != -1) {
          
          // Garante que o lixo anterior não esta no cabeçalho
          if(lixo_anterior != 4) {
            lixo_anterior +=3;
          }
          
          // Vai para o endereço do lixo anterior e pega o endereço do  lixo que vamos usar
          file.seek(lixo_anterior );
          long lixo_usado = file.readLong();
    
          // Vai para o endereço do lixo usado e pega o endereço do próximo lixo
          file.seek(lixo_usado + 3);
          long proximo_lixo = file.readLong();
          
          // Redirecionamos lixo_anterior ---> proximo_lixo
          file.seek(lixo_anterior );
          file.writeLong(proximo_lixo);
          
          // Criamos o registro no endereço do lixo usado
          file.seek(lixo_usado);
          file.writeByte(' ');
          file.readShort();
          file.write(ba2);
          indiceDireto.create(new ParIDEndereco(obj.getID(), lixo_usado));
    
        }
        else{

        file.seek(endereco);
        file.writeByte('*');
        file.seek(file.length());
        long endereco2 = file.getFilePointer();
        file.writeByte(' ');
        file.writeShort(tam2);
        file.write(ba2);
        indiceDireto.update(new ParIDEndereco(novoObj.getID(), endereco2));
      }
    }
      return true;
    }
    return false;
  }

  public void close() throws Exception {
    file.close();
    indiceDireto.close();
  }

  

  // REORGANIZAR - VERSÃO QUE REORDENA O ARQUIVO, USANDO INTERCALAÇÃO BALANCEADA
  // Recebe um objeto vazio para auxiliar na reorganização
  @SuppressWarnings("unchecked")
  public void reorganizar() throws Exception {

    int tamanhoBlocoMemoria = 3;

    // Lê o cabeçalho
    file.seek(0);
    byte[] ba_cabecalho = new byte[HEADER_SIZE];
    file.read(ba_cabecalho);

    // ---------------------------------------------------------------------
    // Primeira etapa (distribuição)
    // ---------------------------------------------------------------------
    List<T> registrosOrdenados = new ArrayList<>();

    int contador = 0, seletor = 0;
    short tamanho;
    byte lapide;
    byte[] dados;
    T r1 = Construtor.newInstance(),
        r2 = Construtor.newInstance(),
        r3 = Construtor.newInstance();
    T rAnt1, rAnt2, rAnt3;

    // Abre três arquivos temporários para escrita (1º conjunto)
    DataOutputStream out1 = new DataOutputStream(new FileOutputStream("dados/temp1.db"));
    DataOutputStream out2 = new DataOutputStream(new FileOutputStream("dados/temp2.db"));
    DataOutputStream out3 = new DataOutputStream(new FileOutputStream("dados/temp3.db"));
    DataOutputStream out = null;

    try {
      contador = 0;
      seletor = 0;
      while (true) {

        // Lê o registro no arquivo de dados
        lapide = file.readByte();
        tamanho = file.readShort();
        dados = new byte[tamanho];
        file.read(dados);
        r1.fromByteArray(dados);

        // Adiciona o registro ao vetor
        if (lapide == ' ') {
          registrosOrdenados.add((T) r1.clone());
          contador++;
        }
        if (contador == tamanhoBlocoMemoria) {

          switch (seletor) {
            case 0:
              out = out1;
              break;
            case 1:
              out = out2;
              break;
            default:
              out = out3;
          }
          seletor = (seletor + 1) % 3;

          Collections.sort(registrosOrdenados);
          for (T r : registrosOrdenados) {
            dados = r.toByteArray();
            out.writeShort(dados.length);
            out.write(dados);
          }
          registrosOrdenados.clear();

          contador = 0;
        }

      }

    } catch (EOFException eof) {
      // Descarrega os últimos registros lidos
      if (contador > 0) {
        switch (seletor) {
          case 0:
            out = out1;
            break;
          case 1:
            out = out2;
            break;
          default:
            out = out3;
        }
        Collections.sort(registrosOrdenados);
        for (T r : registrosOrdenados) {
          dados = r.toByteArray();
          out.writeShort(dados.length);
          out.write(dados);
        }
      }
    }
    out1.close();
    out2.close();
    out3.close();

    // ---------------------------------------------------------------------
    // Segunda etapa (intercalação)
    // ---------------------------------------------------------------------
    DataInputStream in1, in2, in3;
    boolean sentido = true; // true: conj1 -> conj2 | false: conj2 -> conj1
    boolean maisIntercalacoes = true;
    boolean compara1, compara2, compara3;
    boolean terminou1, terminou2, terminou3;

    while (maisIntercalacoes) {

      maisIntercalacoes = false;
      compara1 = false;
      compara2 = false;
      compara3 = false;
      terminou1 = false;
      terminou2 = false;
      terminou3 = false;

      // Seleciona as fontes e os destinos
      if (sentido) {
        in1 = new DataInputStream(new FileInputStream("dados/temp1.db"));
        in2 = new DataInputStream(new FileInputStream("dados/temp2.db"));
        in3 = new DataInputStream(new FileInputStream("dados/temp3.db"));
        out1 = new DataOutputStream(new FileOutputStream("dados/temp4.db"));
        out2 = new DataOutputStream(new FileOutputStream("dados/temp5.db"));
        out3 = new DataOutputStream(new FileOutputStream("dados/temp6.db"));
      } else {
        in1 = new DataInputStream(new FileInputStream("dados/temp4.db"));
        in2 = new DataInputStream(new FileInputStream("dados/temp5.db"));
        in3 = new DataInputStream(new FileInputStream("dados/temp6.db"));
        out1 = new DataOutputStream(new FileOutputStream("dados/temp1.db"));
        out2 = new DataOutputStream(new FileOutputStream("dados/temp2.db"));
        out3 = new DataOutputStream(new FileOutputStream("dados/temp3.db"));
      }
      sentido = !sentido;
      seletor = 0;

      // novos registros anteriores vazios
      r1 = Construtor.newInstance();
      r2 = Construtor.newInstance();
      r3 = Construtor.newInstance();

      // Inicia a intercalação dos segmentos
      boolean mudou1 = true, mudou2 = true, mudou3 = true;
      while (!terminou1 || !terminou2 || !terminou3) {

        if (!compara1 && !compara2 && !compara3) {
          // Seleciona o próximo arquivo de saída
          switch (seletor) {
            case 0:
              out = out1;
              break;
            case 1:
              out = out2;
              break;
            default:
              out = out3;
          }
          seletor = (seletor + 1) % 3;

          if (!terminou1)
            compara1 = true;
          if (!terminou2)
            compara2 = true;
          if (!terminou3)
            compara3 = true;
        }

        // le o próximo registro da última fonte usada
        if (mudou1) {
          System.out.println(r1);
          rAnt1 = (T) (r1.clone());
          try {
            tamanho = in1.readShort();
            dados = new byte[tamanho];
            in1.read(dados);
            r1.fromByteArray(dados);
            if (r1.compareTo(rAnt1) < 0)
              compara1 = false;
          } catch (EOFException e) {
            compara1 = false;
            terminou1 = true;
          }
          mudou1 = false;
        }
        if (mudou2) {
          rAnt2 = (T) r2.clone();
          try {
            tamanho = in2.readShort();
            dados = new byte[tamanho];
            in2.read(dados);
            r2.fromByteArray(dados);
            if (r2.compareTo(rAnt2) < 0)
              compara2 = false;
          } catch (EOFException e) {
            compara2 = false;
            terminou2 = true;
          }
          mudou2 = false;
        }
        if (mudou3) {
          rAnt3 = (T) r3.clone();
          try {
            tamanho = in3.readShort();
            dados = new byte[tamanho];
            in3.read(dados);
            r3.fromByteArray(dados);
            if (r3.compareTo(rAnt3) < 0)
              compara3 = false;
          } catch (EOFException e) {
            compara3 = false;
            terminou3 = true;
          }
          mudou3 = false;
        }

        // Escreve o menor registro
        if (compara1 && (!compara2 || r1.compareTo(r2) <= 0) && (!compara3 || r1.compareTo(r3) <= 0)) {
          dados = r1.toByteArray();
          out.writeShort(dados.length);
          out.write(dados);
          mudou1 = true;
        } else if (compara2 && (!compara1 || r2.compareTo(r1) <= 0) && (!compara3 || r2.compareTo(r3) <= 0)) {
          dados = r2.toByteArray();
          out.writeShort(dados.length);
          out.write(dados);
          mudou2 = true;
        } else if (compara3 && (!compara1 || r3.compareTo(r1) <= 0) && (!compara2 || r3.compareTo(r2) <= 0)) {
          dados = r3.toByteArray();
          out.writeShort(dados.length);
          out.write(dados);
          mudou3 = true;
        }

        // Testa se há mais intercalações a fazer
        if (seletor > 1)
          maisIntercalacoes = true;
      }

      in1.close();
      in2.close();
      in3.close();
      out1.close();
      out2.close();
      out3.close();
    }

    // return;

    // fecha e apaga todos os arquivos vinculados
    file.close();
    new File("dados/" + Nome + ".db").delete();
    new File("dados/" + Nome + ".hash_d.db").delete();
    new File("dados/" + Nome + ".hash_c.db").delete();

    // copia os registros de volta para o arquivo original
    if (sentido)
      in1 = new DataInputStream(new FileInputStream("dados/temp1.db"));
    else
      in1 = new DataInputStream(new FileInputStream("dados/temp4.db"));

    DataOutputStream ordenado = new DataOutputStream(new FileOutputStream(Nome));
    ordenado.write(ba_cabecalho);

    indiceDireto = new HashExtensivel<>(ParIDEndereco.class.getConstructor(),
        3,
        "dados/" + this.Nome + ".hash_d.db",
        "dados/" + this.Nome + ".hash_c.db");

    try {
      while (true) {
        tamanho = in1.readShort();
        dados = new byte[tamanho];
        in1.read(dados);
        r1.fromByteArray(dados);

        long endereco = ordenado.size(); // recupera o ponteiro do arquivo.
        ordenado.writeByte(' '); // lápide
        ordenado.writeShort(tamanho);
        ordenado.write(dados);
        indiceDireto.create(new ParIDEndereco(r1.getID(), endereco));
      }
    } catch (EOFException e) {
      // saída normal
    }
    ordenado.close();
    in1.close();
    (new File("dados/temp1.db")).delete();
    (new File("dados/temp2.db")).delete();
    (new File("dados/temp3.db")).delete();
    (new File("dados/temp4.db")).delete();
    (new File("dados/temp5.db")).delete();
    (new File("dados/temp6.db")).delete();
    file = new RandomAccessFile(Nome, "rw");
  }

}