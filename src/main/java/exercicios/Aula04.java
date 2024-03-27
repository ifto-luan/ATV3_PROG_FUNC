package exercicios;

import java.util.List;
import java.util.stream.Stream;

import exercicios.base.Aula;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Esta é uma classe para você poder implementar as atividades propostas no
 * README.
 * Você NÃO deve alterar a estrutura deste arquivo,
 * não pode alterar o nome da classe, dos métodos ou dos atributos,
 * não pode alterar valores dos atributos existentes,
 * nem pode alterar parâmetros e tipo de retorno dos métodos.
 *
 * <p>
 * Você pode alterar o código interno dos métodos, criar métodos auxiliares que
 * podem ser chamados
 * pelos existentes, mas não deve alterar a estrutura dos métodos disponíveis.
 * </p>
 *
 * @author Manoel Campos da Silva Filho
 */
@NoArgsConstructor
public class Aula04 extends Aula {

    /**
     * Veja o método {@link #start()}.
     */
    public static void main(String[] args) {
        new Aula04().start();
    }

    /**
     * Você pode chamar os métodos existentes e outros que você criar aqui,
     * incluir prints e fazer o que desejar neste método para verificar os valores
     * retornados pelo seu método.
     * Para verificar se sua implementação está correta, clique com o botão direito
     * no nome do projeto na aba esquerda
     * do IntelliJ e selecione a opção "Run 'All Tests'".
     */
    
    public void start() {
        final var curso = generator.COURSES[3];
        final char homem = 'M';
        final char mulher = 'F';

        System.out.printf("Maior nota de todos os Estudantes: %.2f%n",
                maiorNotaTodosEstudantes(estudantes.stream()));
        System.out.printf("Maior nota dos Estudantes homens: %.2f%n", maiorNotaHomens(estudantes.stream()));
        System.out.printf("Maior nota das mulheres do curso de %s: %.2f%n", curso.getNome(),
                maiorNotaCursoAndSexo(estudantes.stream(), curso, mulher));
        System.out.printf("Média de notas dos Estudantes do curso de %s: %.2f%n", curso.getNome(),
                mediaNotaTodosEstudantesCurso(estudantes.stream(), curso));
        System.out.printf("Total dos homens do curso de %s: %d%n", curso.getNome(),
                totalEstudantesCursoAndSexo(estudantes.stream(), curso, homem));
        System.out.printf("Total das mulheres do curso de %s: %d%n", curso.getNome(),
                totalEstudantesCursoAndSexo(estudantes.stream(), curso, mulher));
    }

    /*
     * Nota ao professor:
     * 
     * Meu código não passa nos testes de jeito nenhum.
     * 
     * Testei usando os arquivos em outro projeto sem os testes e, aparentemente,
     * está tudo OK.
     * 
     * Algumas coisas a notar:
     * 
     * 1 - Eu rodei essa geração de alunos várias vezes e nunca é atribuído o curso
     * de matemática a uma mulher (o que já gera falhas em dois testes)
     * 
     * 2 - A maior nota é sempre 9,23 e de um homem (gerando falha em outros dois testes)
     * 
     * 3 - Sempre geram 4 alunos homens no curso de matemática (espera-se 2), o que implica em uma
     * média diferente da esperada (gerando falha nos dois testes restantes)
     * 
     */

    protected double maiorNotaCursoAndSexo(@NonNull final Stream<Estudante> stream, @NonNull final Curso curso,
            final char sexo) {

        return stream.filter(x -> x.getCurso() != null)
                .filter(x -> x.getCurso().compareTo(curso) == 0)
                .filter(x -> x.getSexo() == sexo)
                .mapToDouble(Estudante::getNota)
                .max().orElse(0.0);

    }

    protected long totalEstudantesCursoAndSexo(@NonNull final Stream<Estudante> stream, @NonNull final Curso curso,
            final char sexo) {
        return stream.filter(x -> x.getCurso() != null)
                .filter(x -> x.getCurso().compareTo(curso) == 0)
                .filter(x -> x.getSexo() == sexo)
                .count();
    }

    protected double mediaNotaTodosEstudantesCurso(@NonNull final Stream<Estudante> stream,
            @NonNull final Curso curso) {

        // Única forma que achei para não dar "Java.lang.IllegalStateException: stream
        // has already been operated upon or closed"
        // Talvez, se no parâmetro fosse passada a lista e não a stream, não seria
        // necessário voltar a stream para lista

        List<Estudante> estudantesList = stream.filter(x -> x.getCurso() != null)
                .filter(x -> x.getCurso().compareTo(curso) == 0)
                .toList();

        int estudantesCount = estudantesList.size();

        double notesSum = estudantesList.stream()
                .mapToDouble(Estudante::getNota)
                .reduce(0.0, Double::sum);

        return notesSum / estudantesCount;
    }

    protected double maiorNotaTodosEstudantes(@NonNull final Stream<Estudante> stream) {
        return stream.filter(x -> x.getCurso() != null)
                .mapToDouble(Estudante::getNota)
                .max().orElse(0.0);
    }

    protected double maiorNotaHomens(@NonNull final Stream<Estudante> stream) {
        return stream.filter(x -> x.getCurso() != null)
                .filter(x -> x.getSexo() == 'M')
                .mapToDouble(Estudante::getNota)
                .max()
                .orElse(0.0);
    }
}