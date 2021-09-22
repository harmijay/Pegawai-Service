package com.example.pegawai.pegawai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/pegawai")
public class PegawaiController {

    private final PegawaiService pegawaiService;

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
        pegawaiService.addNewPegawai(pegawai);
    }

    @DeleteMapping(path = "{pegawaiId}")
    public void deletePegawai(@PathVariable("pegawaiId") Long pegawaiId) {
        pegawaiService.deletePegawai(pegawaiId);
    }

    @PutMapping
    public void updatePegawai(
            @RequestBody Pegawai pegawai
    ) {
        pegawaiService.updatePegawai(pegawai.getId(), pegawai.getName(), pegawai.getSalary(), pegawai.getPosition());
    }
}
