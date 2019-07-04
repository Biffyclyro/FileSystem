# com.github.biffyclyro.filesystem.FileSystem


 - O primeiro bloco será reservado para as informações do diretório (único), dentre as quais:
    - o Nome do arquivo: tamanho fixo de 8 caracteres e três para a extensão.
    - O Tamanho total do arquivo: inteiro de 32 bits.
    - O Bloco Inicial: inteiro de 32 bits.
 - Os próximos blocos serão usados para o armazenamento da FAT (calcular a quantidade de blocos necessários de acordo com o tamanho do arquivo);
 - Operações inválidas geram exceções como por exemplo: tentar ler um arquivo além
   do seu tamanho, tentar gravar mais dados do que o espaço livre permite, etc.
   Deverão ser desenvolvidos casos de teste para cada uma das chamadas implementadas;
 - O trabalho poderá ser realizado em duplas;
 - A entrega do código fonte deverá ser feita pelo Moodle até o dia 05/07/19. Na aula do
    mesmo dia deverá ser feita a apresentação ao professor;
    A implementação completa do trabalho garante 1 (um) ponto extra na 2a nota do
    Semestre.
    
  - [x] com.github.biffyclyro.filesystem.Fat
    
  # com.github.biffyclyro.filesystem.Diretorio
  
  - [ ] criar diretório
  - [ ] ler entrada do diretório
  - [ ] n niveis de diretorio
  - [ ] listar
  
  # com.github.biffyclyro.filesystem.Arquivo
  
  - 7 bytes para nome
  - 3 bytes para extensão
  
  - [ ] ler
  - [ ] adicionar
  - [ ] escrever
  - [ ] remover
  
  # com.github.biffyclyro.filesystem.Disco
  
  - [x] mostrar espaço livre