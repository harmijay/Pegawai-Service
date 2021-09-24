package com.example.pegawai.pegawai;

import com.example.pegawai.KantorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/pegawai")
public class PegawaiController {

    private final PegawaiService pegawaiService;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    public PegawaiController(PegawaiService pegawaiService) {
        this.pegawaiService = pegawaiService;
    }

    @GetMapping
    public List<Pegawai> getPegawai() {
        return pegawaiService.getPegawai();
    }

    @PostMapping
    public void registerNewPegawai(@RequestBody Pegawai pegawai) {
        /*WebClient*/
        KantorResponse response = webClientBuilder.build()
                .get()
                .uri("http://10.10.30.35:7006/kantor/validasiIdKantor/"+pegawai.getIdKantor())
                .retrieve()
                .bodyToMono(KantorResponse.class)
                .block();
        if (response != null) {
            if (response.getMessage().equals("id kantor ada")) {
                pegawaiService.addNewPegawai(pegawai);
            } else {
                throw new IllegalStateException(response.getMessage());
            }
        }else{
            throw new IllegalStateException("Failed to retrieve Kantor information");
        }
    }

    @DeleteMapping(path = "{pegawaiId}")
    public void deletePegawai(@PathVariable("pegawaiId") Long pegawaiId) {
        pegawaiService.deletePegawai(pegawaiId);
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("nomorNasabah",232);
        data.put("jenisTransaksi",2);
        data.put("waktuTransaksi", LocalDateTime.now());
        data.put("statusTransaksi",1);
        data.put("logTransaksi","Pegawai with id "+pegawaiId+" has been deleted");
        webClientBuilder.build()
                .post()
                .uri("http://10.10.30.49:7007/api/transaksi/")
                .body(Mono.just(data),HashMap.class)
                .retrieve();
    }

    @PutMapping
    public void updatePegawai(
            @RequestBody Pegawai pegawai
    ) {
        pegawaiService.updatePegawai(pegawai.getId(), pegawai.getName(), pegawai.getSalary(), pegawai.getPosition());
    }
}
