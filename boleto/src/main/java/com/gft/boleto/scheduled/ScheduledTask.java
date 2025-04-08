package com.gft.boleto.scheduled;

import com.gft.boleto.service.BoletoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

    @Autowired
    private BoletoService boletoService;

    /**
     * A anotação @Scheduled(cron = "0 0 1 * * ?") é usada em Spring Boot para agendar a execução de um método
     * em intervalos de tempo específicos usando a expressão cron.
     *
     * :: Aqui estão alguns detalhes ::
     *
     * Primeiro campo ("0"): Segundo (0-59)
     * Segundo campo ("0"): Minuto (0-59)
     * Terceiro campo ("1"): Hora (0-23)
     * Quarto campo ("*"): Dia do mês (1-31)
     * Quinto campo ("*"): Mês (1-12 ou JAN-DEC)
     * Sexto campo ("?"): Dia da semana (0-6 ou SUN-SAT)
     *
     * :: Aqui estão alguns exemplos adicionais de expressões cron e seus significados ::
     *
     * "@Scheduled(cron = "0 0 * * * ?"): Executa a cada hora, no início da hora.
     * "@Scheduled(cron = "0 0 12 * * ?"): Executa todos os dias ao meio-dia.
     * "@Scheduled(cron = "0 15 10 ? * MON-FRI"): Executa de segunda a sexta-feira, às 10:15 da manhã.
     * "@Scheduled(cron = "0 0 0 1 * ?"): Executa no primeiro dia de cada mês, à meia-noite.
     *
     * :: Se você precisa considerar fusos horários, você pode adicionar a propriedade zone na anotação ::
     *
     * Exemplo: @Scheduled(cron = "0 0 1 * * ?", zone = "America/Sao_Paulo")
     *
     * Isso garante que a tarefa seja executada à 1:00 da manhã no fuso horário especificado.
     */
    @Scheduled(cron = "0 4 1 * * ?")
    public void scheduleTaskWithCronExpression() {
        boletoService.validarEBaixarBoletosVencidos();
    }
}