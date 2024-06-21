#  Trabalho Pratico AEDS III💻

##  Equipe 💂‍♂️

- Vitor Lúcio de Oliveira
- Bruno Braga Guimarães Alves
- Vitor Dias de Britto Militão


## Descrição📝 
### Espaços Vazios (Parte 1)
- Nesse parte foi desenvolvido metedos para lidar com os espaços vazios usando como base os codigos desenvolvidos em sala.
- Nosso grupo implementou uma Lista, que foi abstraida dentro os espaços vazios.
- Tambem foi criado um pequeno menu, onde há as opções de criar um registro de Livros, deletar um registro de livros usando o id, e uma rotina de testes, que seriam inclusão/exclusão/Inclusão/update de certos registros.

### Busca por palavras (Parte 2)
- Nesta parte foram desenvolvidos métodos para fazer buscas em lista invertida, usando como base os códigos desenvolvidos em sala.
- Nosso grupo implementou, assim, funções que foram incorporadas nos métodos Create, Delete e Update.

  - getChaves --> Usada para receber um String e retornar um ArrayList de chaves;
  - createChaves --> Usada para inserir uma lista de chaves na lista invertida;
  - deleteChaves--> Usada para deletar uma lista de chaves da lista invertida;
  - busca_lista --> Busca na Lista Invertida os ids que possuem chaves correspondentes à chave de busca, nela já há o tratamento de interseções e printa os livros achados;

- Adicionalmente, para ficar mais legivel, seguindo as correções da parte 1, criamos o metodo colocar_noLixo() e tirar_doLixo();
- Alem de corrigir um erro no update de lixos;

### Backup compactado (Parte 3)
- Nesta parte foram desenvolvidos métodos para fazer e restauras backup dos arquivos de dados, utilizando compactação LZW.
- Nosso grupo implementou, assim, funções para realizar essa tarefa:
  - doBackup --> Usando uma lista com os arquivos de dados, faz um backup dos arquivos compactados em LZW, dividindo em um arquivo da lista com o nome e tamanho dos backup e um diretorio com os arquivoss em si;
  -  doRestore --> Usando os dois arquivoss de backup (Lista, Diretorio), decodifica e restaura eles no arquivos de dados;
  - listar_pastas --> Cria uma lista das pastas ou arquivos do diretorio em questão;
  - pegar_dataHora --> Pega e formata da data local do sistema ("dd-MM-yyyy-hh-mm");
  

- Tambem fizemos uma função para calcular a taxa de compressão (taxa_cp) e leves alterações no Menu;

### Criptografia (Parte 4)
- Nesta parte foi desenvolvido uma classe para cifrar e decifrar os dados das entidades, feito nos métodos toByteArray() e fromByteArray() da classe Livro.

- Nosso grupo implementou, assim, a classe Criptografia com metodos:
  - cifrar --> Recebe um array de bytes, cifrando eles usando transposição e substituição com a chave MEDATOTAL;

  - decifrar --> Recebe um array de bytes, decifrando eles usando substituição e transposição com a chave MEDATOTAL;

  - substituicao  --> Aplica uma operação de soma ou subtração a cada byte dos dados com base na chave fornecida. Para cifrar, o valor do byte da chave correspondente é somado ao byte dos dados; para decifrar, o valor é subtraído. A chave é repetida ciclicamente se for mais curta que os dados. Isso altera o valor de cada byte, tornando os dados cifrados.;
  - transposicao --> Reorganiza os bytes dos dados com base em uma permutação gerada pela chave. Primeiro, um array de posições é criado, contendo índices sequenciais. A chave é então usada para determinar uma permutação destes índices de forma determinística: cada caractere da chave influencia a troca de posições no array. Para cifrar, os dados são reorganizados conforme a nova ordem de índices; para decifrar, o processo é invertido, restaurando a ordem original. Isso embaralha a ordem dos bytes, contribuindo para a segurança da cifragem.;
  

##  Experiência do Grupo 🔍

### Espaços Vazios (Parte 1)

\- **Vitor Lucio**: "A implementação que fizemos se mostrou bastante simples e facil de fazer-se com base nos codigos feitos em sala. A abordagem de abstração de lista foi desafiadora somente em seu raciocínio, enquanto a parte do codigo em si foi bem fluido "

####  Vocês implementaram todos os requisitos?

\- **Vitor Lucio**: "Quase todos, pois falta adicionar métodos para controlar o desperdício do espaço reusado. No nosso caso seria somente implementar uma forma de ordenação nos espaçõs vazios."

####  Houve alguma operação mais difícil?

\- **Vitor Lucio**: "Sim, a operação DELETE foi mais difícil, sendo ela a base para implementação de lista nos arquivos. A maior parte do tempo foi gasta nela."
  
####  Vocês enfrentaram algum desafio na implementação?
\- **Vitor Lucio**: "Sim, a implementação mais difícil foi o redirecionamento das 'células lixo' ao serem reusadas. Na abordagem que usei faltou conseguir ordenar de forma crescente a lista abstraida no arquivo."

#### Os resultados foram alcançados?
\- **Vitor Lucio**: "Não encontramos nenhum erro nos testes realizados, então com base nisso, o objetivo principal foi 90% concluído."

### Busca por palavras (Parte 2)

####  Vocês implementaram todos os requisitos?
\- **Vitor Lucio**: " Sim, acreditamos que todos foram implementados"

####  Houve alguma operação mais difícil?
\- **Vitor Lucio**: “A parte mais dificil foi a função inicial para lidar com as strings para se tornarem chaves e a forma de lidar com caracteres especiais.”

####  Vocês enfrentaram algum desafio na implementação?
\- **Vitor Lucio**: “Nessa parte do trabalho em específico, não creio que houve nenhum desafio em especial.”

#### Os resultados foram alcançados?
\- **Vitor Lucio**: "Não encontramos nenhum erro nos testes realizados, então com base nisso, o objetivo principal foi 100% concluído."


### Backup compactado (Parte 3)

####  Vocês implementaram todos os requisitos?
\- **Vitor Lucio**: " Sim, acreditamos que todos foram implementados"

####  Houve alguma operação mais difícil?
\- **Vitor Lucio**: “A parte mais dificil foi a função para decodificar e depois restaurar os arquivos de backup.”

####  Vocês enfrentaram algum desafio na implementação?
\- **Vitor Lucio**: “Nessa parte, a maior dificuldade foi utilizar o codigo de LZW para descompactar. Aparentemente ele estava erRado e eu demorei pra notar que o erro era ele e não à implementação. Além de tentar resolve-lo e não conseguir.”

#### Os resultados foram alcançados?
\- **Vitor Lucio**: "Não encontramos nenhum erro nos testes realizados, então com base nisso, o objetivo principal foi 100% concluído."

### Criptografia (Parte 4)

####  Vocês implementaram todos os requisitos?
\- **Vitor Lucio**: " Sim, acreditamos que todos foram implementados"

####  Houve alguma operação mais difícil?
\- **Vitor Lucio**: “A parte mais dificil foi a função para transposição.”

####  Vocês enfrentaram algum desafio na implementação?
\- **Vitor Lucio**: “Nessa parte, não tive nenhum desafio que valha a pena constar.”

#### Os resultados foram alcançados?
\- **Vitor Lucio**: "Não encontramos nenhum erro nos testes realizados, então com base nisso, o objetivo principal foi 100% concluído."

##  Checklist 📋

### Espaços Vazios (Parte 1)
- [X] Definimos critérios para a gestão de espaços vazios.
- [X] O código do CRUD está funcionando corretamente.
- [ ] Implementamos um índice direto com tabela hash extensível.
- [X] A operação de inclusão busca o espaço vazio "aceitavel".
- [X] A operação de alteração gerencia espaços vazios quando o registro cresce.
- [X] As operações de alteração e exclusão gerenciam espaços vazios para reutilização.
- [X] O trabalho está funcionando.
- [ ] O trabalho está completo.
- [X] O trabalho é original (100%).

### Busca por palavras (Parte 2)

- [X] A inclusão de um livro acrescenta os termos do seu título à lista invertida.
- [X] A alteração de um livro modifica a lista invertida removendo **e** acrescentando termos do título.
- [x] A remoção de um livro gera a remoção dos termos do seu título na lista invertida.
- [X] Há uma busca por palavras que retorna os livros que possuam essas palavras.
- [X] Essa busca pode ser feita com mais de uma palavra.
- [X] As stop words foram removidas de todo o processo.
- [x] Fez modificação, se alguma, para além dos requisitos mínimos desta tarefa.
- [x] O trabalho está funcionando corretamente.
- [x] O trabalho está completo.
- [X] O trabalho é original (100%).

### Backup compactado (Parte 3)

- [X] Há uma rotina de compactação usando o algoritmo LZW para fazer backup dos arquivos.
- [X] Há uma rotina de descompactação usando o algoritmo LZW para recuperação dos arquivos.
- [X] O usuário pode escolher a versão a recuperar.
- [x] O trabalho está funcionando corretamente.
- [x] O trabalho está completo.
- [X] O trabalho é original (100%).

- EXTRA: Taxa de compressão alcançada por esse backup ≅ 66,66% 

### Criptografia (Parte 4)

- [X] Há uma função de cifragem em todas as classes de entidades, envolvendo pelo menos duas operações diferentes e usando uma chave criptográfica.
- [X] Uma das operações de cifragem é baseada na substituição e a outra na transposição.
- [x] O trabalho está funcionando corretamente.
- [x] O trabalho está completo.
- [X] O trabalho é original (100%).
