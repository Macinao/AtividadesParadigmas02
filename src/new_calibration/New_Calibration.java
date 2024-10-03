package new_calibration;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

class ProcessaLinhaDaTarefa implements Runnable {
    private List<String> linhas;
    private int resultadoParcial;

    public ProcessaLinhaDaTarefa(List<String> linhas) {
        this.linhas = linhas;
        this.resultadoParcial = 0;
    }

    @Override
    public void run() {
        for (String nome : linhas) {
            Integer valor1 = null;
            Integer valor2 = null;

            for (int k = 0; k < nome.length(); k++) {
                char c = nome.charAt(k);
                if (Character.isDigit(c) && valor1 == null) {
                    valor1 = Character.getNumericValue(c);
                }
                if (Character.isDigit(c)) {
                    valor2 = Character.getNumericValue(c);
                }
            }

            if (valor1 != null && valor2 != null) {
                String soma = String.valueOf(valor1) + String.valueOf(valor2);
                resultadoParcial += Integer.parseInt(soma);
            }
        }
    }
    public int getResultadoParcial() {
        return resultadoParcial;
    }
}

public class New_Calibration {

    public static void main(String[] args) throws IOException, InterruptedException {
        Path path = Paths.get(System.getProperty("user.dir") + "\\src\\new_calibration\\Calibracao.txt");

        long tempoInicialSemThreads = System.currentTimeMillis();
        Integer valor2 = null;
        Integer totalSemThreads = 0;

        List<String> list = Files.readAllLines(path);

        for (String nome : list) {
            Integer valor1 = null;

            for (int k = 0; k < nome.length(); k++) {
                char c = nome.charAt(k);
                if (Character.isDigit(c) && valor1 == null) {
                    valor1 = Character.getNumericValue(c);
                }
                if (Character.isDigit(c)) {
                        valor2 = Character.getNumericValue(c);
                }
            }

            String soma = String.valueOf(valor1) + String.valueOf(valor2);
            totalSemThreads += Integer.parseInt(soma);
        }

        System.out.println("Total (sem threads): " + totalSemThreads);
        long tempoFinalSemThreads = System.currentTimeMillis();
        System.out.printf("Tempo sem threads: %.3f segundos%n", (tempoFinalSemThreads - tempoInicialSemThreads) / 1000d);

        long tempoInicialComThreads = System.currentTimeMillis();

        int meio = list.size() / 2;
        List<String> parte1 = list.subList(0, meio);
        List<String> parte2 = list.subList(meio, list.size());

        ProcessaLinhaDaTarefa tarefa1 = new ProcessaLinhaDaTarefa(parte1);
        ProcessaLinhaDaTarefa tarefa2 = new ProcessaLinhaDaTarefa(parte2);

        Thread thread1 = new Thread(tarefa1);
        Thread thread2 = new Thread(tarefa2);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        int totalComThreads = tarefa1.getResultadoParcial() + tarefa2.getResultadoParcial();

        System.out.println("Total (com threads): " + totalComThreads);
        long tempoFinalComThreads = System.currentTimeMillis();
        System.out.printf("Tempo com threads: %.3f segundos%n", (tempoFinalComThreads - tempoInicialComThreads) / 1000d);

    }
}
