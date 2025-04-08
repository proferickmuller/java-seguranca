package br.com.fiap.pessoa.client;

import br.com.fiap.pessoa.client.dto.BoletoDTO;
import br.com.fiap.pessoa.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "boleto",
        url = "${BOLETO_SERVICE_URL:https://localhost:8081}",
        configuration = FeignConfig.class
)
public interface BoletosClient {

    @GetMapping("/boleto/pessoa/{pessoaId}")
    List<BoletoDTO> getBoletosByPessoaId(@PathVariable("pessoaId") long pessoaId);

}