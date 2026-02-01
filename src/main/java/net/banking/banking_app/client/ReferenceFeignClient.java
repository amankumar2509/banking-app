package net.banking.banking_app.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "reference-service", url = "http://localhost:8083")
public interface ReferenceFeignClient {
    @GetMapping("/reference/generate")
    String generateReference();
}
