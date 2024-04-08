#  Trabalho Pratico AEDS III💻

##  Equipe 💂‍♂️

- Vitor Lúcio de Oliveira
- Bruno Braga Guimarães Alves
- Vitor Dias de Britto Militão




##  Observações 💭

**"Vocês implementaram todos os requisitos?"**
* Quase todos, pois falta adicionar métodos para controlar o desperdício do espaço reusado.

**"Houve alguma operação mais difícil?"**
* Sim, a operação DELETE foi mais difícil.
  
**"Vocês enfrentaram algum desafio na implementação?"**
* Sim, a implementação mais difícil foi o redirecionamento das 'células lixo' ao serem reusadas;

**"Os resultados foram alcançados?"**
* Não encontramos nenhum erro nos testes realizados, então com base nisso, o objetivo principal foi concluído.
  
##  Checklist 📋

**"O que você considerou como perda aceitável para o reuso de espaços vazios, isto é, quais são os critérios para a gestão dos espaços vazios?"**
*  Eu utilizei uma abstração de uma lista dentro do arquivo de dados. Na qual um "registro de lixo" armazena um ponteiro para outro "registro de lixo". Por isso, infelizmente, não tive tempo de implementar uma forma de ordenar a lista e assim, conseguir gerir os espaços que sobram ao reusar um registro.

**"O código do CRUD com arquivos de tipos genéricos está funcionando corretamente?"**
* Sim, ele tá sim.
  
**"O CRUD tem um índice direto implementado com a tabela hash extensível?"**
* Não tem não.

**"A operação de inclusão busca o espaço vazio mais adequado para o novo registro antes de acrescentá-lo ao fim do arquivo?"**
* Infelizmente não, ela busca o espaço vazio aceitalvel não o mais adequado.

**"A operação de alteração busca o espaço vazio mais adequado para o registro quando ele cresce de tamanho antes de acrescentá-lo ao fim do arquivo?"**
* Tambem não, ela busca o primeiro que é aceitavel.
  
**"As operações de alteração (quando for o caso) e de exclusão estão gerenciando os espaços vazios para que possam ser reaproveitados?"**
* Sim, estão sim.

**"O trabalho está funcionando corretamente?"**
* Creio que sim.

**"O trabalho está completo?"**
* Como não implementei a busca de espaços mais adequados, diria que tá 80% completo.

**"O trabalho é original e não a cópia de um trabalho de um colega?"**
* Prometo que sim (fiz 100% sozinho).
  
