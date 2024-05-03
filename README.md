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
- [x] O trabalho está funcionando corretamente
- [x] O trabalho está completo.
- [X] O trabalho é original (100%).


  
